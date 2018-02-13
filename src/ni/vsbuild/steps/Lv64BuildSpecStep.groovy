package ni.vsbuild.steps

class Lv64BuildSpecStep extends LvBuildStep {

   def target
   def spec

   Lv64BuildSpecStep(script, mapStep, lvVersion) {
      super(script, mapStep, lvVersion)
      this.target = mapStep.get('target')
      this.spec = mapStep.get('build_spec')
   }

   void executeBuildStep(String projectPath) {
      script.lv64BuildSpec(projectPath, target, spec, lvVersion)
   }
}
