def call(componentName, configurationMap, lvVersion) {
   
   def releaseBranches = [:]
   def componentConfiguration = configurationMap.repositories.get(componentName)

   // Read the release branch info from configuration.json. 
   if(componentConfiguration.containsKey('release_branches')) {
      releaseBranches = componentConfiguration.get('release_branches')
      echo "Branches configured for release: $releaseBranches"
      return releaseBranches
   } else {
      echo "No branches configured for GitHub releases."

   // Return null if the key isn't found: don't push any releases.
      return null
   }
}

   