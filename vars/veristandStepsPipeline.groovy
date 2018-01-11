def call(branch, org, release_version, buildConfiguration){
    
    vs_year_version = buildConfiguration.vs_year_version
    sp_version = buildConfiguration.sp_version
    ts_version = buildConfiguration.ts_version
    x64_build_flag = buildConfiguration.x64_build_flag
    
    //If sp_flag is true then build is for Service Pack version.  
    vs_version_string = vs_year_version
    if (sp_version){
        vs_version_string = vs_version_string + " SP${sp_version}"
    }
    
    //Build full assembly version string needed within C# project file scripting. 
    vs_gac_assembly_version = "4.0.${vs_year_version}.${sp_version}.0.0"
    vs_assembly_version = "${vs_year_version}.${sp_version}.0.0"
    vs_app_path = "c:\\Program Files (x86)\\National Instruments\\VeriStand ${vs_year_version}"
    
    switch(vs_year_version){
        case '2015':
            types_version = "15.${sp_version}.${release_version}.0"
            break
        case '2016':
            types_version = "16.${sp_version}.${release_version}.0"
            break
        case '2017':
            types_version = "17.${sp_version}.${release_version}.0"
            break
    }
    
    if (sp_version && x64_build_flag){
        package_name = "veristand${vs_year_version}sp${sp_version}-teststand${ts_version}-x64-${types_version}"
    }
    
    else if (sp_version && !x64_build_flag){
        package_name = "veristand${vs_year_version}sp${sp_version}-teststand${ts_version}-x86-${types_version}"
    }

    else if (!sp_version && x64_build_flag){
        package_name = "veristand${vs_year_version}-teststand${ts_version}-x64-${types_version}"
    }
    
    else if (!sp_version && !x64_build_flag){
        package_name = "veristand${vs_year_version}-teststand${ts_version}-x86-${types_version}"
    }
    
    switch(ts_version){
        case '2014':
            ts_assembly_version = "14.0.0.103"
            if (x64_build_flag){
                ts_pub_docs_dir = "TestStand 2014 (64-bit)"
            }
            if (!x64_build_flag){
                ts_pub_docs_dir = "TestStand 2014 (32-bit)"
            break
        }
        case '2016':
            ts_assembly_version = "16.0.0.185"
            if (x64_build_flag){
                ts_pub_docs_dir = "TestStand 2016 (64-bit)"
            }
            if (!x64_build_flag){
                ts_pub_docs_dir = "TestStand 2016 (32-bit)"
            break
        }
		case '2017':
            ts_assembly_version = "17.0.0.184"
            if (x64_build_flag){
                ts_pub_docs_dir = "TestStand 2017 (64-bit)"
            }
            if (!x64_build_flag){
                ts_pub_docs_dir = "TestStand 2017 (32-bit)"
            break
        }
    }
    
    //Define all paths needed for build. 
    
    //The pipeline uses the Visual Studio devenv command line utility to build the VeristandSteps .NET assembly.
    visual_studio_devenv="C:\\Program Files (x86)\\Microsoft Visual Studio 14.0\\Common7\\IDE\\devenv.exe"
    
    //LabVIEW source code base path.
    lv_src_path="Source\\LV"
    
    //Intermediate build files will be placed at WORKSPACE\build_temp. 
    temp_build_path="build_temp"
    
    //Path to the C# project containing the VeristandSteps.dll assembly source code.
    vs_steps_csharp_proj_path="${WORKSPACE}\\Source\\Csharp\\VeristandStepsInstaller\\OpenWorkspaceDialog\\VeristandSteps.csproj"
    
    //Path where the built VeristandSteps.dll assembly is placed.
    vs_steps_assembly_build_path="${WORKSPACE}\\Source\\CSharp\\VeristandStepsInstaller\\OpenWorkspaceDialog\\obj\\Release\\NationalInstruments.Veristand.VeristandSteps.dll"
    
    //Paths to custom palette .ini and .ico dependency files.
    step_types_palette_filepath="${WORKSPACE}\\Source\\CustomPaletteFile\\NI_VeristandTypes.ini"
    step_types_icon_filepath="${WORKSPACE}\\Source\\CustomPaletteFile\\Veristand_icon_vista.ico"
    
    //Paths to LabVIEW projects for PPLs included in the build. 
    labviewProjects=
    [
    "${lv_src_path}\\Misc\\MiscHelperVIs.lvproj",
    "${lv_src_path}\\RT Sequence\\RTSequenceVIs.lvproj",
    "${lv_src_path}\\Set Channels\\Set Channels.lvproj",
    "${lv_src_path}\\TS VS Logger\\TS VS Logger.lvproj",
    ]
    
    //Paths to all LabVIEW PPL project configuration files.
    projectConfigFilePaths=
    [
    "${WORKSPACE}\\${lv_src_path}\\Misc\\MiscHelperVIs.lvproj.config",
    "${WORKSPACE}\\${lv_src_path}\\RT Sequence\\RTSequenceVIs.lvproj.config",
    "${WORKSPACE}\\${lv_src_path}\\Set Channels\\Set Channels.lvproj.config",
    "${WORKSPACE}\\${lv_src_path}\\TS VS Logger\\TS VS Logger.lvproj.config",
    "${WORKSPACE}\\${lv_src_path}\\VeriStand Silent Start\\SilentVS.lvproj.config"
    ]
    
    //Path to the LabVIEW installer project as well as the directory where the built installer files will be placed. 
    silent_start_lv_proj_path="${lv_src_path}\\VeriStand Silent Start\\SilentVS.lvproj"
    lv_installer_proj_path="Source\\LV\\Installer\\Installer.lvproj"
    lv_installer_built_source="Source\\LV\\installer\\builds\\installer\\volume"
    
    //The build pipeline will move the built installer from lv_installer_built_source to lv_installer_build_path so that it isn't lost during Workspace Cleanup. 
    lv_installer_build_path=workspace
   
	//Get latest code from source. Doing this in every Pipeline call to avoid any stale files being left between builds, e.g. LabVIEW projects getting bumped up to higher versions. 
    checkout([$class: 'GitSCM', branches: [[name: '*/'+branch]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '12b9186f-e93f-4620-9a18-74b7287a7339', url: 'https://github.com/'+org+'/VeriStand-steps-for-TestStand']]])
    
	//Create build_temp directory.
    bat "IF NOT EXIST \"${WORKSPACE}\\${temp_build_path}\" mkdir \"${WORKSPACE}\\${temp_build_path}\""
    
    //Update all of the LabVIEW project .NET assembly version configuration files to the specified versions for VeriStand and TestStand. 
    projectConfigFilePaths.each { configFilePath ->
        veristandStepsUpdateLVDotNetConfig(vs_year_version, configFilePath, vs_assembly_version, ts_assembly_version)
    }
 
    //Update the NIVeriStand_Types.ini custom step types palette file to the specified VeriStand assembly version. 
    veristandStepsUpdateCustomPaletteFile(step_types_palette_filepath, vs_assembly_version, types_version, vs_app_path, vs_year_version)
    
    //Build all the LabVIEW packed project libraries. Build the Silent VeriStand executable.
    //Check 64-bit build flag and use appropriate lvBuild/lvBuildx64 call.
    
    if (x64_build_flag==false){
    labviewProjects.each {projectPath ->
        lvBuild(projectPath, "My Computer", "Packed Library", vs_year_version, temp_build_path)
        }
    }
    
    if (x64_build_flag==true){
    labviewProjects.each {projectPath ->
        lvBuildx64(projectPath, "My Computer", "Packed Library", vs_year_version, temp_build_path)
        }
    }
    
    //The LabVIEW bitness used does not matter for these files: SilentVS.exe, Post-Install Action.exe, Pre-Uinstall Action.exe
    lvBuild(silent_start_lv_proj_path,"My Computer","SilentVS",vs_year_version, temp_build_path)
    lvBuild(lv_installer_proj_path, "My Computer", "Post Install Action", vs_year_version, temp_build_path)
    lvBuild(lv_installer_proj_path, "My Computer", "Pre Uninstall Action", vs_year_version, temp_build_path)
    
    //Update the VeristandSteps.csproj .NET project to use the specified TestStand and VeriStand assembly versions.
    veristandStepsUpdateAssemblyProjectFile(vs_year_version, vs_steps_csharp_proj_path, ts_version, ts_assembly_version, vs_assembly_version, vs_gac_assembly_version)
    
    //Compile VeristandSteps.dll using devenv utility. 
    bat "\"${visual_studio_devenv}\" \"${vs_steps_csharp_proj_path}\" /build Release /out"
    
    //Copy remaining files needed for installer to build_temp: NIVeriStand_Types.ini, NIVeriStand_types.ico, VeristandSteps.dll 
    [step_types_palette_filepath, 
     step_types_icon_filepath, 
     vs_steps_assembly_build_path].each {dependencyFilePath ->
        bat "copy /y ${dependencyFilePath} ${WORKSPACE}\\${temp_build_path}"
    }
    
    //Create the installer build destination if it doesn't already exist.
    bat "IF NOT EXIST ${package_name}\\installer mkdir ${package_name}\\installer"
    
    //Update version strings within LabVIEW project installer build specification.
    veristandStepsUpdateInstaller(vs_version_string, ts_version, ts_pub_docs_dir, "${WORKSPACE}\\${lv_installer_proj_path}", vs_year_version)
    
    //Build the LabVIEW installer.
    lvBuildInstaller(lv_installer_proj_path, "My Computer", "VeriStand Custom Step Types", vs_year_version)
    
    //Copy the built installer files to permenant export home at $installer_build_dest. 
    bat "xcopy /E /y /I ${lv_installer_built_source} ${lv_installer_build_path}\\${package_name}\\installer"
    
    //Delete the temporary build directory to avoid conflicts during subsequent builds. 
    bat "rd /s /q \"${temp_build_path}\""
    
    zip zipFile: "${package_name}.zip", dir: "${package_name}\\installer", archive: true

    bat "move ${package_name}.zip c:\\builds"
}