def call(version, tsVersions, payloadDir, vsVersion) {

   if(vsVersion == 2015) {
      vsVersion = "2015sp1"
   }

   def controlFileText = readFile "control"
   echo controlFileText

   tsVersions.each{tsVersion ->
      
      def nipkgDir = "nipkg\\veristand${vsVersion}-steps-teststand${tsVersion}"
      def updatedControlText = controlFileText

      updatedControlText.replaceAll("\\{veristand_version\\}", "${vsVersion}")
      updatedControlText.replaceAll("\\{teststand_version\\}", "${tsVersion}")
      updatedControlText.replaceAll("\\{nipkg_version\\}", "${nipkgVersion}")
      echo updatedControlText
       
      dir(nipkgDir){
         writeFile file:'control\control', text: updatedControlText
      }
      
      echo "Creating folder ${nipkgDir}."

   }
}
