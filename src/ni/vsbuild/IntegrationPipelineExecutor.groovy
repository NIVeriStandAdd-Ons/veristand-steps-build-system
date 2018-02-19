package ni.vsbuild

class PipelineExecutor implements Serializable {

   static void execute(script, List<String> lvVersions, List<String> dependencies = []) {
      def pipelineInformation = new PipelineInformation('veristand', lvVersions, dependencies)
      pipelineInformation.printInformation(script)

      def buildPipeline = new Pipeline(script, pipelineInformation)
      def integrationPipeline = new IntegrationPipeline(script, pipelineInformation)
      
      buildPipeline.execute()
      integrationPipeline.execute()
   }
}
