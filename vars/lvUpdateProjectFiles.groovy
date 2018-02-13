def call(lvVersion){
   echo "Updating project files."

   def csProjectPath = "\\Source\\CSharp\\VeristandStepsInstaller\\OpenWorkspaceDialog\\VeristandSteps.csproj"
   def typesVersion = "7.1"

   bat "labview-cli --kill --lv-ver $lvVersion \"$WORKSPACE\\commonbuild\\lv\\codegen\\lvUpdateProjectFiles.vi\" -- \"$WORKSPACE\" \"$csProjectPath\" \"$typesVersion\""

}
