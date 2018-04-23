package ni.vsbuild.packages

import groovy.json.JsonSlurperClassic
import groovy.json.JsonOutput

class VsStepsPackage extends AbstractPackage {

   def nipkgVersion
   def typesVersion
   def tsVersions
   def vsVersion
   def configurationMap
   def configurationJson
   def componentName
   def componentBranch
   def releaseBranches
   def buildNumber

   VsStepsPackage(script, packageInfo, payloadDir) {
      super(script, packageInfo, payloadDir)
      this.typesVersion = packageInfo.get('types_version')
      this.tsVersions = packageInfo.get('teststand_versions')
      this.buildNumber = 0
   }

   void buildPackage(lvVersion) {
      def packageInfo = """
         Building package $name from $payloadDir
         Package version: $nipkgVersion
         TestStand versions: $tsVersions
         """.stripIndent()

      componentName = script.getComponentParts()['repo']
      componentBranch = script.getComponentParts()['branch']

      // Read and parse configuration.json file to get next build number. 
      script.echo "Getting 'build_number' for ${componentName}."
      configurationJsonFile = script.readJSON file: "configuration_${lvVersion}.json"
      configurationMap = new JsonSlurperClassic().parseText(configurationJsonFile.toString())
      configurationJSON = script.readJSON text: JsonOutput.toJson(configurationMap)

     if(configurationMap.repositories.containsKey(componentName)) {
         buildNumber = script.getBuildNumber(componentName, configurationMap)
         script.echo "Next build number: $buildNumber"
      } else { 
         configurationMap.repositories[componentName] = ['build_number': buildNumber] 
      }
      
      nipkgVersion = typesVersion
      vsVersion = lvVersion
      script.echo packageInfo
      nipkgInfo = script.vsStepsPackage(nipkgVersion, vsVersion)

      script.configUpdate(configurationJSON, lvVersion)
      releaseBranches = script.getReleaseInfo(componentName, configurationMap, lvVersion)
      script.configPush(buildNumber, componentName, lvVersion) 
      script.pushRelease(nipkgInfo, payloadDir, releaseBranches, lvVersion)
   }
}
