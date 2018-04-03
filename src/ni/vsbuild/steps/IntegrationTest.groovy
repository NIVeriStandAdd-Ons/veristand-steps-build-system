package ni.vsbuild.steps

import ni.vsbuild.BuildConfiguration

class IntegrationTest extends AbstractStep {

   def seqPath
   def tsVersion
   def lvVersion

   IntegrationTest(script, mapStep, lvVersion) {
      super(script, mapStep)
      this.seqPath = mapStep.get('sequence_path')
      this.tsVersion = mapStep.get('teststand_version')
      this.lvVersion = lvVersion
   }

   void executeStep(BuildConfiguration buildConfiguration) {
      script.runIntegrationTest(seqPath, tsVersion)
   }
}
