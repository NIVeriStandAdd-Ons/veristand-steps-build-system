package ni.vsbuild.stages

class Test extends AbstractStage {

   Test(script, configuration, lvVersion) {
      super(script, configuration, lvVersion)
   }

   void executeStage() {
      executeSteps(configuration.test)
   }
}