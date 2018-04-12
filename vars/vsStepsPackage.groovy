def call(version, tsVersions, payloadDir, vsVersion) {

   if(vsVersion == 2015) {
      vsVersion = "2015sp1"
   }

   def controlFileText = readFile "control"
   echo controlFileText
   
   def programFilesStagingDirectory = "data\\ProgramFiles_32\\VeriStand Steps for TestStand"
   echo programFilesStagingDirectory

   tsVersions.each{tsVersion ->
      
      def nipkgDir = "nipkg\\veristand${vsVersion}-steps-teststand${tsVersion}"
      def tsPublicDocs = "documents\\National Instruments\\TestStand ${tsVersion} (32-bit)"
      echo tsPublicDocs
      def updatedControlText = controlFileText

      updatedControlText.replaceAll("\\{veristand_version\\}", "${vsVersion}")
      updatedControlText.replaceAll("\\{teststand_version\\}", "${tsVersion}")
      updatedControlText.replaceAll("\\{nipkg_version\\}", "${nipkgVersion}")
      echo updatedControlText
       
      dir(nipkgDir){
         writeFile file:'control\\control', text: updatedControlText
         writeFile file:'debian-binary', text: "2.0"
      }
      
      echo "Creating folder ${nipkgDir}."

   }
}
