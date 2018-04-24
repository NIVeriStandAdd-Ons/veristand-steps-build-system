def call(){
   echo 'Cloning commonbuild steps to workspace.'
   
   def organization = getComponentParts()['organization']
   def branch = env."library.vs-steps-build.version"
   def configBranch = env."library.commonbuild-configuration.version"
   def commonbuildDir = cloneRepo("https://github.com/$organization/veristand-steps-build-system", branch)
   def commonbuildConfigDir = cloneRepo("https://github.com/$organization/commonbuild-configuration", configBranch)
   
   //Using ROBOCOPY to avoid 255 char path limit with other commands.
   //Using compound command and forcing ERRORLEVEL 0 because ROBOCOPY does not use standard error out convention. 
   //ROBOCOPY uses non-zero return values for successful execution. 
   //This potentially masks problems with this step, but these operations should always succeed. 
   bat "(robocopy \"$commonbuildDir\" \"commonbuild\" /MIR /NFL /NDL /NJH /NJS /nc /ns /np) ^& exit 0"
   
   return commonbuildDir

}
