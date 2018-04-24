def call(componentName, configurationMap) {

   def buildNumber = 0
   def componentConfiguration = configurationMap.repositories[componentName]

   // If the 'build_number' key exists then read the value and increment it. 
   if(componentConfiguration.containsKey('build_number')) {
      def lastBuild = componentConfiguration['build_number'] as Integer
      buildNumber = lastBuild + 1
   }

   // Update build number in configurationMap and return current build number.
   componentConfiguration['build_number'] = buildNumber
   return buildNumber
   
}

