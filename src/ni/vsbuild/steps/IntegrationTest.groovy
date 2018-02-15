package ni.vsbuild.steps

import ni.vsbuild.BuildConfiguration

class IntegrationTest extends AbstractStep {

   def seqPath
   def tsVersion

   IntegrationTest(script, mapStep, lvVersion) {
      super(script, mapStep, lvVersion)
      this.seqPath = mapStep.get('sequence_path')
      this.tsVersion = mapStep.get('teststand_version')
   }

   void executeStep(BuildConfiguration buildConfiguration) {
      script.runIntegrationTest(seqPath, tsVersion)
   }
}
