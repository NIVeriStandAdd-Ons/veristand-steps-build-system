import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

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
      def updatedControlText = controlFileText

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
