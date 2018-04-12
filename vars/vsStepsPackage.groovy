def call(nipkgVersion, tsVersions, payloadDir, vsVersion) {

   if(vsVersion == 2015) {
      vsVersion = "2015sp1"
   }

   def controlFileText = readFile "control"
   echo controlFileText

   def replacementExpressionMap = ['\\{veristand_version\\}': vsVersion,  '\\{teststand_version\\}': tsVersion, '\\{nipkg_version\\}': nipkgVersion] 

   def programFilesStagingDirectory = "data\\ProgramFiles_32\\VeriStand Steps for TestStand"
   echo programFilesStagingDirectory

   tsVersions.each{tsVersion ->
      
      def nipkgDir = "nipkg\\veristand${vsVersion}-steps-teststand${tsVersion}"
      def tsPublicDocs = "documents\\National Instruments\\TestStand ${tsVersion} (32-bit)\\Components\\TypePalettes"
      echo tsPublicDocs
      def updatedControlText = controlFileText

      replacementExpressionMap.each { replacementExpression ->
         updatedControlText = updatedControlText.replaceAll(replacementExpression, replacementExpressionMap[replacementExpression])
      }
    
      echo updatedControlText
       
      dir(nipkgDir){
         writeFile file:'control\\control', text: updatedControlText
         writeFile file:'debian-binary', text: "2.0"
      }
      
      echo "Creating folder ${nipkgDir}."

   }
}
