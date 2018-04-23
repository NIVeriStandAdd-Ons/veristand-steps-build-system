def call(lvVersion) {
   configurationTOMLPath = "commonbuild-configuration\\configuration_${lvVersion}.toml"
   bat "commonbuild\\resources\\buildSetup.bat $configurationTOMLPath"
}
