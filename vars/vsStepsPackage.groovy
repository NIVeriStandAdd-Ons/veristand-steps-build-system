def call(version, tsVersions, payloadDir, lvVersion) {

   def vsVersion = lvVersion
   
   if(vsVersion == 2015) {
      vsVersion = "2015sp1"
   }

   tsVersions.each{tsVersion ->
      def packageSource = "Source\\LV\\Installer\\built\\teststand${tsVersion}\\volume" //Example: 'Source\\LV\\Installer\\Built\\teststand2014\\volume'
      def zipFileName = "veristand${vsVersion}-steps-teststand${tsVersion}-v${version}.zip" //Example: 'veristand2015sp1-steps-for-teststand2014-v7.1'
      
      echo "Building ${zipFileName} from ${packageSource}."

      zip zipFile: "Built\\${zipFileName}", dir: "${packageSource}"

   }
}
