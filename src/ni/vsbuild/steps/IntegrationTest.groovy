package ni.vsbuild.steps

class IntegrationTest extends AbstractStep {

   def seqPath
   def tsVersion

   IntegrationTest(script, mapStep, lvVersion) {
      super(script, mapStep, lvVersion)
      this.seqPath = mapStep.get('sequence_path')
      this.tsVersion = mapStep.get('teststand_version')
   }

   void executeBuildStep() {
      script.runIntegrationTest(seqPath, tsVersion)
   }
}
