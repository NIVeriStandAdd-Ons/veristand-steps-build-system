def call(branch, org, release_version, buildConfiguration){
    
    def veristandVersion = buildConfiguration.veristandYear_version
    def servicePackVersion = buildConfiguration.sp_version
    def teststandVersion = buildConfiguration.ts_version
    def x64bitBuildFlag = buildConfiguration.x64_build_flag
    def servicePackVersionString = ''

    if(servicePackVersion) {
        servicePackVersionString = "SP${servicePackVersion}"
    }
    
    def veristandVersionString = veristandVersion

    if (servicePackVersion){	
        veristandVersionString = veristandVersionString + " ${servicePackVersionString}"
    }
    
    def veristandFullAssemblyVersion = "4.0.${veristandVersion}.${servicePackVersion}.0.0"
    def veristandAssemblyVersion = "${veristandVersion}.${servicePackVersion}.0.0"
    def veristandApplicationPath = "c:\\Program Files (x86)\\National Instruments\\VeriStand ${veristandVersion}"
    def veristandYear = veristandVersion.split('0')[1]
    def typesVersion = "${veristandYear}.${servicePackVersion}.${release_version}"
    def bitnessString = 'x86'
    def teststandAssemblyVersionString = "14.0.0.103"

    if(x64bitBuildFlag) {
        bitnessString = 'x64'
    }
    
    def zipFileName = "veristand${veristandVersion}${servicePackVersionString}-teststand${teststandVersion}-${bitnessString}-${typesVersion}"
    def teststandPubDocsBitnessString = '(32-bit)'
    
    if(x64bitBuildFlag) {
        teststandPubDocsBitnessString = '(64-bit)'
    }    

    def teststandPubDocsDirectory = "TestStand ${teststandVersion} ${teststandPubDocsBitnessString}"
    
    def visualStudioDevEnvPath="C:\\Program Files (x86)\\Microsoft Visual Studio 14.0\\Common7\\IDE\\devenv.exe" //Use devenv CLI to build the VeristandSteps.dll C# assembly.
    def lvSourcePath="Source\\LV"
    def tempBuildPath="build_temp"
    def csharpAssemblyProjectPath="${WORKSPACE}\\Source\\Csharp\\VeristandStepsInstaller\\OpenWorkspaceDialog\\VeristandSteps.csproj"
    def csharpAssemblyBuildPath="${WORKSPACE}\\Source\\CSharp\\VeristandStepsInstaller\\OpenWorkspaceDialog\\obj\\Release\\NationalInstruments.Veristand.VeristandSteps.dll"
    def customPalettleFilePath="${WORKSPACE}\\Source\\CustomPaletteFile\\NI_VeristandTypes.ini"
    def customPaletteIconPath="${WORKSPACE}\\Source\\CustomPaletteFile\\Veristand_icon_vista.ico"
    def lvInstallerProjectPath="Source\\LV\\Installer\\Installer.lvproj"
    def lvInstallerBuildPath="Source\\LV\\installer\\builds\\installer\\volume"
    

    def labviewProjects=
    ["${lvSourcePath}\\Misc\\MiscHelperVIs.lvproj",
    "${lvSourcePath}\\RT Sequence\\RTSequenceVIs.lvproj",
    "${lvSourcePath}\\Set Channels\\Set Channels.lvproj",
    "${lvSourcePath}\\TS VS Logger\\TS VS Logger.lvproj",]
    
    //Paths to all LabVIEW PPL project configuration files.
    def projectConfigFilePaths=
    ["${WORKSPACE}\\${lvSourcePath}\\Misc\\MiscHelperVIs.lvproj.config",
    "${WORKSPACE}\\${lvSourcePath}\\RT Sequence\\RTSequenceVIs.lvproj.config",
    "${WORKSPACE}\\${lvSourcePath}\\Set Channels\\Set Channels.lvproj.config",
    "${WORKSPACE}\\${lvSourcePath}\\TS VS Logger\\TS VS Logger.lvproj.config",]
   
    checkout([$class: 'GitSCM', branches: [[name: '*/'+branch]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '12b9186f-e93f-4620-9a18-74b7287a7339', url: 'https://github.com/'+org+'/VeriStand-steps-for-TestStand']]])
    
    bat "IF NOT EXIST \"${WORKSPACE}\\${tempBuildPath}\" mkdir \"${WORKSPACE}\\${tempBuildPath}\""	//Create build_temp directory.
    
    //Update all of the LabVIEW project .NET assembly version configuration files to the specified versions for VeriStand and TestStand. 
    projectConfigFilePaths.each { configFilePath ->
        veristandStepsUpdateLVDotNetConfig(veristandVersion, configFilePath, veristandAssemblyVersion, teststandAssemblyVersionString)
    }
 
    veristandStepsUpdateCustomPaletteFile(customPalettleFilePath, veristandAssemblyVersion, typesVersion, veristandApplicationPath, veristandVersion)
        
    if (x64bitBuildFlag==false){
        labviewProjects.each {projectPath ->
            lvBuild(projectPath, "My Computer", "Packed Library", veristandVersion, tempBuildPath)
        }
    }
    
    if (x64bitBuildFlag==true){
        labviewProjects.each {projectPath ->
            lvBuildx64(projectPath, "My Computer", "Packed Library", veristandVersion, tempBuildPath)
        }
    }
    
    lvBuild(lvInstallerProjectPath, "My Computer", "Post Install Action", veristandVersion, tempBuildPath)
    lvBuild(lvInstallerProjectPath, "My Computer", "Pre Uninstall Action", veristandVersion, tempBuildPath)
    
    veristandStepsUpdateAssemblyProjectFile(veristandVersion, csharpAssemblyProjectPath, teststandVersion, teststandAssemblyVersionString, veristandAssemblyVersion, veristandFullAssemblyVersion)
    
    bat "\"${visualStudioDevEnvPath}\" \"${csharpAssemblyProjectPath}\" /build Release /out"
    
    [customPalettleFilePath, 
    customPaletteIconPath, 
    csharpAssemblyBuildPath].each {
        dependencyFilePath ->
            bat "copy /y ${dependencyFilePath} ${WORKSPACE}\\${tempBuildPath}"
    }
    
    bat "IF NOT EXIST ${zipFileName}\\installer mkdir ${zipFileName}\\installer"

    veristandStepsUpdateInstaller(veristandVersionString, teststandVersion, teststandPubDocsDirectory, "${WORKSPACE}\\${lvInstallerProjectPath}", veristandVersion)

    lvBuildInstaller(lvInstallerProjectPath, "My Computer", "VeriStand Custom Step Types", veristandVersion)
    
    bat "xcopy /E /y /I ${lvInstallerBuildPath} ${workspace}\\${zipFileName}\\installer"

    bat "rd /s /q \"${tempBuildPath}\""
    
    zip zipFile: "${zipFileName}.zip", dir: "${zipFileName}\\installer", archive: true

    bat "move ${zipFileName}.zip c:\\builds"
}