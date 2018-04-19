def call(packageInfo, lvVersion){
   echo "Updating project files."

   def csProjectPath = "\\Source\\CSharp\\VeristandStepsInstaller\\OpenWorkspaceDialog\\VeristandSteps.csproj"
   def typesVersion = packageInfo.get('types_version')
   def tsVersions = packageInfo.get('teststand_versions')

   bat "labview-cli --kill --lv-ver $lvVersion \"$WORKSPACE\\commonbuild\\lv\\codegen\\lvUpdateProjectFiles.vi\" -- \"$WORKSPACE\" \"$csProjectPath\" \"$typesVersion\" \"$tsVersions\""

}
