#!/usr/bin/env groovy

def call(veristand_version, teststand_version, x64_build_flag, types_version, veristand_assembly_version, teststand_pub_docs_install_dir, teststand_assembly_version, installer_build_dest, lv_version, vs_install_path){
    
    //Define all paths needed for build. 
    
    //The pipeline uses the Visual Studio devenv command line utility to build the VeristandSteps .NET assembly.
    visual_studio_devenv="C:\\Program Files (x86)\\Microsoft Visual Studio 14.0\\Common7\\IDE\\devenv.exe"
    
    //LabVIEW source code base path.
    lv_src_path="Source\\LV"
    
    //Intermediate build files will be placed at WORKSPACE\build_temp. 
    temp_build_path="build_temp"
    
    //Path to the C# project containing the VeristandSteps.dll assembly source code.
    vs_steps_csharp_proj_path="${WORKSPACE}\\Source\\Csharp\\VeristandStepsInstaller\\OpenWorkspaceDialog\\VeristandSteps.csproj"
    
    //Path where the built VeristandSteps.dll assembly can be found. 
    vs_steps_assembly_build_path="${WORKSPACE}\\Source\\CSharp\\VeristandStepsInstaller\\OpenWorkspaceDialog\\obj\\Release\\NationalInstruments.Veristand.VeristandSteps.dll"
    
    //Paths to custom palette .ini and .ico files.
    step_types_palette_filepath="${WORKSPACE}\\Source\\CustomPaletteFile\\NI_VeristandTypes.ini"
    step_types_icon_filepath="${WORKSPACE}\\Source\\CustomPaletteFile\\Veristand_icon_vista.ico"
    
    //Paths to all LabVIEW projects included in the build. 
    miscVIs_lv_project_path="${lv_src_path}\\Misc\\MiscHelperVIs.lvproj"
    miscHelperVIs_proj_dotNet_config_path="${WORKSPACE}\\${miscVIs_lv_project_path}.config"
    rtSequenceVIs_lv_project_path="${lv_src_path}\\RT Sequence\\RTSequenceVIs.lvproj"
    rtSequenceVIs_proj_dotNet_config_path="${WORKSPACE}\\${rtSequenceVIs_lv_project_path}.config"
    setChannels_lv_project_path="${lv_src_path}\\Set Channels\\Set Channels.lvproj"
    setChannels_proj_dotNet_config_path="${WORKSPACE}\\${setChannels_lv_project_path}.config"
    logging_lv_project_path="${lv_src_path}\\TS VS Logger\\TS VS Logger.lvproj"
    logging_proj_dotNet_config_path="${WORKSPACE}\\${logging_lv_project_path}.config"
    silent_start_lv_project_path="${lv_src_path}\\VeriStand Silent Start\\SilentVS.lvproj"
    silent_start_proj_dotNet_config_path="${WORKSPACE}\\${silent_start_lv_project_path}.config"
    
//    Delete this?    
//    post_install_action_app_path="${WORKSPACE}\\Source\\LV\\Installer\\builds\\PostInstallAction.exe"
//    pre_uninstall_action_app_path="${WORKSPACE}\\Source\\LV\\Installer\\builds\\PreUninstallAction.exe"
    
    //Path to the LabVIEW installer project as well as the directory where the built installer files will be placed. 
    lv_installer_proj_path="Source\\LV\\Installer\\Installer.lvproj"
    lv_installer_built_source="Source\\LV\\installer\\builds\\installer\\volume"
    
    //The build pipeline will move the built installer from lv_installer_built_source to lv_installer_build_path so that it isn't lost during Workspace Cleanup. 
    lv_installer_build_path="C:\\builds\\veristand-step-types"
    
    //Create build_temp directory.
    bat "IF NOT EXIST \"${WORKSPACE}\\${temp_build_path}\" mkdir \"${WORKSPACE}\\${temp_build_path}\""
    
    //Update all of the LabVIEW project .NET assembly version configuration files to the specified versions for VeriStand and TestStand. 
    veristandStepsUpdateLVDotNetConfig(miscHelperVIs_proj_dotNet_config_path, veristand_assembly_version, teststand_assembly_version)
    veristandStepsUpdateLVDotNetConfig(rtSequenceVIs_proj_dotNet_config_path, veristand_assembly_version, teststand_assembly_version)
    veristandStepsUpdateLVDotNetConfig(setChannels_proj_dotNet_config_path, veristand_assembly_version, teststand_assembly_version)
    veristandStepsUpdateLVDotNetConfig(logging_proj_dotNet_config_path, veristand_assembly_version, teststand_assembly_version)
    veristandStepsUpdateLVDotNetConfig(silent_start_proj_dotNet_config_path, veristand_assembly_version, teststand_assembly_version)
    
    //Update the NIVeriStand_Types.ini custom step types palette file to the specified VeriStand assembly version. 
    veristandStepsUpdateCustomPaletteFile(step_types_palette_filepath, veristand_assembly_version, types_version, vs_install_path)
    
    //Build all the LabVIEW packed project libraries. Build the Silent VeriStand executable.
    //Check 64-bit build flag and use appropriate lvBuild/lvBuildx64 call.
    if (x64_build_flag==false){
    
    lvBuild(miscVIs_lv_project_path,"My Computer","MiscHelperVIs PPL",lv_version, temp_build_path)
    lvBuild(rtSequenceVIs_lv_project_path,"My Computer","RTSequenceVIs PPL",lv_version, temp_build_path)
    lvBuild(setChannels_lv_project_path,"My Computer","Set Channels PPL",lv_version, temp_build_path)
    lvBuild(logging_lv_project_path,"My Computer","Logging PPL",lv_version, temp_build_path)
    
    }

    if (x64_build_flag==true){
    
    lvBuildx64(miscVIs_lv_project_path,"My Computer","MiscHelperVIs PPL",lv_version, temp_build_path)
    lvBuildx64(rtSequenceVIs_lv_project_path,"My Computer","RTSequenceVIs PPL",lv_version, temp_build_path)
    lvBuildx64(setChannels_lv_project_path,"My Computer","Set Channels PPL",lv_version, temp_build_path)
    lvBuildx64(logging_lv_project_path,"My Computer","Logging PPL",lv_version, temp_build_path)
    
    }
    
    //The LabVIEW bitness used does not matter for these files: SilentVS.exe, Post-Install Action.exe, Pre-Uinstall Action.exe
    lvBuild(silent_start_lv_project_path,"My Computer","SilentVS",lv_version, temp_build_path)
    lvBuild(lv_installer_proj_path, "My Computer", "Post Install Action", lv_version, temp_build_path)
    lvBuild(lv_installer_proj_path, "My Computer", "Pre Uninstall Action", lv_version, temp_build_path)
    
    //Update the VeristandSteps.csproj .NET project to use the specified TestStand and VeriStand assembly versions.
    veristandStepsUpdateAssemblyProjectFile(lv_version, vs_steps_csharp_proj_path, teststand_version, teststand_assembly_version, veristand_assembly_version, veristand_gac_assembly_version)
    
    //Compile VeristandSteps.dll using devenv utility. 
    bat "\"${visual_studio_devenv}\" \"${vs_steps_csharp_proj_path}\" /build Release /out"
    
    //Copy remaining files needed for installer to build_temp: NIVeriStand_Types.ini, NIVeriStand_types.ico, VeristandSteps.dll 
    bat "copy /y ${step_types_palette_filepath} ${WORKSPACE}\\${temp_build_path}"
    bat "copy /y ${step_types_icon_filepath} ${WORKSPACE}\\${temp_build_path}"
    bat "copy /y ${vs_steps_assembly_build_path} ${WORKSPACE}\\${temp_build_path}"
    
    //Create the installer build destination if it doesn't already exist.
    bat "IF NOT EXIST \"${installer_build_dest}\" mkdir \"${installer_build_dest}\""
    
    //Update version strings within LabVIEW project installer build specification.
    veristandStepsUpdateInstaller(veristand_version, teststand_version, teststand_pub_docs_install_dir, "${WORKSPACE}\\${lv_installer_proj_path}")
    
    //Build the LabVIEW installer.
    lvBuildInstaller(lv_installer_proj_path, "My Computer", "VeriStand Custom Step Types", lv_version)
    
    //Copy the built installer files to permenant export home at $installer_build_dest. 
    bat "xcopy /E /y /I \"${lv_installer_built_source}\" \"${lv_installer_build_path}\\${installer_build_dest}\""
    
    //Delete the temporary build directory to avoid conflicts during subsequent builds. 
    bat "rd /s /q \"${temp_build_path}\""
    
}