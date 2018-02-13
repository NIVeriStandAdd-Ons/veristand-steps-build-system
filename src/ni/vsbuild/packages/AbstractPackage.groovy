package ni.vsbuild.packages

abstract class AbstractPackage implements Buildable {

   def script
   def name
   def type
   def payloadDir

   AbstractPackage(script, packageInfo, payloadDir) {
      this.script = script
      this.name = packageInfo.get('name')
      this.type = packageInfo.get('type')
      this.payloadDir = payloadDir
   }

   void build(lvVersion) {
      script.echo "Building package $name.$type..."
      buildPackage(lvVersion)
      script.echo "Package $name.$type built."
   }

   abstract void buildPackage(lvVersion)

}
