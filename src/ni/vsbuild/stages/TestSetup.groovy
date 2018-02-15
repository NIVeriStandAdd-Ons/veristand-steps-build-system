package ni.vsbuild.stages

class TestSetup extends AbstractStage {

   TestSetup(script, configuration, lvVersion) {
      super(script, configuration, lvVersion)
   }

   void executeStage() {
      executeSteps(configuration.test)
   }
}