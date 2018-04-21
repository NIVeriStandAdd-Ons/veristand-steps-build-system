def call(nipkgVersion, tsVersions, payloadDir, vsVersion) {

   if(vsVersion == "2015") {
      vsVersion = "2015sp1"
   }

   def controlFileText = readFile "control"
   def instructionsFileText = readFile "instructions"
   echo controlFileText

   def programFilesStagingDirectory = "data\\ProgramFiles_32\\VeriStand Steps for TestStand"
   echo programFilesStagingDirectory

   tsVersions.each{tsVersion ->
      def replacementExpressionMap = ['veristand_version': vsVersion,  'teststand_version': tsVersion, 'nipkg_version': nipkgVersion] 
      def nipkgDir = "nipkg\\veristand${vsVersion}-steps-teststand${tsVersion}"
      def programFilesStagingSource = "built\\programFiles_32"
      def programFilesStagingDest = "nipkg\\data\\programFiles_32"
      def documentsStagingSource = "built\\documents"
      def documentsStagingDest = "nipkg\\documents"
      def updatedControlText = controlFileText

      bat "(robocopy \"${programFilesStagingSource}\" \"{programFilesStagingDest}\" /MIR /NFL /NDL /NJH /NJS /nc /ns /np) ^& exit 0"
      bat "(robocopy \"${documentsStagingSource}\" \"{documentsStagingDest}\" /MIR /NFL /NDL /NJH /NJS /nc /ns /np) ^& exit 0"

      replacementExpressionMap.each { replacementExpression, replacementValue ->
         updatedControlText = updatedControlText.replaceAll("\\{${replacementExpression}\\}", replacementValue)
      }
       
      dir(nipkgDir){
         writeFile file:'control\\control', text: updatedControlText
         writeFile file:'data\\instructions', text: instructionsFileText
         writeFile file:'debian-binary', text: "2.0"
      }
   }
}
