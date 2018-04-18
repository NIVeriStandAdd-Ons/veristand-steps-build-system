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
      def ts32PublicDocs = "documents\\National Instruments\\TestStand ${tsVersion} (32-bit)\\Components\\TypePalettes"
      def ts64PublicDocs = "documents\\National Instruments\\TestStand ${tsVersion} (64-bit)\\Components\\TypePalettes"
      def runtimeLib32Source = "build_temp\\lvlibp\\x86\\ni-veristand-steps-runtime-lib.lvlibp"
      def runtimeLib32Source = "build_temp\\lvlibp\\x64\\ni-veristand-steps-runtime-lib.lvlibp"
      def updatedControlText = controlFileText

      replacementExpressionMap.each { replacementExpression, replacementValue ->
         updatedControlText = updatedControlText.replaceAll("\\{${replacementExpression}\\}", replacementValue)
      }

      Files.copy(runtimeLib32Source, ts32PublicDocs)
       
      dir(nipkgDir){
         writeFile file:'control\\control', text: updatedControlText
         writeFile file:'data\\instructions', text: instructionsFileText
         writeFile file:'debian-binary', text: "2.0"
      }
   }
}
