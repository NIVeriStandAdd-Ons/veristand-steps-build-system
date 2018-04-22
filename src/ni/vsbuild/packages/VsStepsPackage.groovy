package ni.vsbuild.packages

class VsStepsPackage extends AbstractPackage {

   def nipkgVersion
   def typesVersion
   def tsVersions
   def vsVersion

   VsStepsPackage(script, packageInfo, payloadDir) {
      super(script, packageInfo, payloadDir)
      this.typesVersion = packageInfo.get('types_version')
      this.tsVersions = packageInfo.get('teststand_versions')
   }

   void buildPackage(lvVersion) {
      def packageInfo = """
         Building package $name from $payloadDir
         Package version: $nipkgVersion
         TestStand versions: $tsVersions
         """.stripIndent()
      
      nipkgVersion = typesVersion
      vsVersion = lvVersion
      script.echo packageInfo
      script.vsStepsPackage(nipkgVersion, vsVersion)

   }
}
