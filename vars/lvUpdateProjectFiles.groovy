def call(version, lvVersion){
   echo "Updating project files."

   def csProjectPath = "\\Source\\CSharp\\VeristandStepsInstaller\\OpenWorkspaceDialog\\VeristandSteps.csproj"
   def typesVersion = version

   bat "labview-cli --kill --lv-ver $lvVersion \"$WORKSPACE\\commonbuild\\lv\\codegen\\lvUpdateProjectFiles.vi\" -- \"$WORKSPACE\" \"$csProjectPath\" \"$typesVersion\""

}
