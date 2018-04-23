def call(buildNumber, componentID, lvVersion) {

   // Push the updated build configuration back to GitHub configuration repository. 
   echo "Updating build number for ${componentID} (${lvVersion}) to ${buildNumber} in commonbuild-configuration repository."
   def commitMessage = "Updating ${componentID} for VeriStand ${lvVersion} to build number ${buildNumber}."
   bat "commonbuild\\resources\\configPush.bat \"$commitMessage\""
}
