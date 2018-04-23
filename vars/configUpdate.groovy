def call(configurationJSON, lvVersion) {

   def configurationJsonFileName = "configuration_${lvVersion}.json"   

   // Write configuration to JSON file and then convert it back to TOML. 
   writeJSON file: configurationJsonFileName, json: configurationJSON, pretty: 4
   bat "commonbuild\\resources\\configUpdate.bat $configurationJsonFileName"

}

