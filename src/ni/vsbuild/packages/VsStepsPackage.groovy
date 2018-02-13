package ni.vsbuild.packages

class VsStepsPackage extends AbstractPackage {

   def version
   def tsVersions

   VsStepsPackage(script, packageInfo, payloadDir) {
      super(script, packageInfo, payloadDir)
      this.version = packageInfo.get('version')
      this.tsVersions = packageInfo.get('teststand_versions')
   }

   void buildPackage(lvVersion) {
      def packageInfo = """
         Building package $name from $payloadDir
         Package version: $version
         TestStand versions: $tsVersions
         """.stripIndent()

      script.echo packageInfo
      script.vsStepsPackage(version, tsVersions, payloadDir, lvVersion)

   }
}
