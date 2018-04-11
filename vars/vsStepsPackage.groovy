def call(version, tsVersions, payloadDir, vsVersion) {

   if(vsVersion == 2015) {
      vsVersion = "2015sp1"
   }

   tsVersions.each{tsVersion ->
      
      def nipkgDir = "nipkg\\veristand${vsVersion}-steps-teststand${tsVersion}"
      
      bat 'dir $nipkgDir'
      
      echo "Creating folder ${nipkgDir}."

   }
}
