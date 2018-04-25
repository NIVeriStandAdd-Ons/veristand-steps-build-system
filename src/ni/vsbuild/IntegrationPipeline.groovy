package ni.vsbuild

import ni.vsbuild.stages.*

class IntegrationPipeline implements Serializable {

   private static final String JSON_FILE = 'build.json'

   def script
   PipelineInformation pipelineInformation
   def stages = []

   static class Builder implements Serializable {

      def script
      BuildConfiguration buildConfiguration
      String lvVersion
      def stages = []

      Builder(def script, BuildConfiguration buildConfiguration, String lvVersion) {
         this.script = script
         this.buildConfiguration = buildConfiguration
         this.lvVersion = lvVersion
      }

      def withTestStage() {
         stages << new Test(script, buildConfiguration, lvVersion)
      }

      def buildPipeline() {         
         if(buildConfiguration.testInfo) {
            withTestStage() 
         }

         return stages
      }
   }

   IntegrationPipeline(script, PipelineInformation pipelineInformation) {
      this.script = script
      this.pipelineInformation = pipelineInformation
   }

   void execute() {

      // build dependencies before starting this pipeline
      script.buildDependencies(pipelineInformation)

      def builders = [:]

      for(String version : pipelineInformation.lvVersions) {

         // need to bind the variable before the closure - can't do 'for (version in lvVersions)'
         def lvVersion = version

         builders[lvVersion] = {
            script.node("${pipelineInformation.nodeLabel} && $lvVersion") {
               setup(lvVersion)

               def configuration = BuildConfiguration.load(script, JSON_FILE)
               configuration.printInformation(script)

               def builder = new Builder(script, configuration, lvVersion)
               this.stages = builder.buildPipeline()

               executeStages()
            }
         }
      }

      script.parallel builders
   }

   protected void executeStages() {
      for (Stage stage : stages) {
         try {
            stage.execute()
         } catch (err) {
            script.failBuild(err.getMessage())
         }
      }
   }

   private void setup(lvVersion) {
      script.stage("Checkout_$lvVersion") {
         script.deleteDir()
         script.echo 'Attempting to get source from repo.'
         script.timeout(time: 5, unit: 'MINUTES'){
            script.checkout(script.scm)
         }
      }
      script.stage("Setup_$lvVersion") {
         script.cloneCommonbuild()
         script.buildSetup(lvVersion)
      }
   }
}
