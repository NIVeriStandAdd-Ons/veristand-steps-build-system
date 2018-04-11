def call(version, tsVersions, payloadDir, vsVersion) {

   if(vsVersion == 2015) {
      vsVersion = "2015sp1"
   }

   tsVersions.each{tsVersion ->
      
      def nipkgDir = "nipkg\\veristand${vsVersion}-steps-teststand${tsVersion}"
      
      dir('test'){
         writeFile file:'dummy', text:''
      }
      
      echo "Creating folder ${nipkgDir}."

   }
}
