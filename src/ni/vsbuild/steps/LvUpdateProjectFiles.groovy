package ni.vsbuild.steps

import ni.vsbuild.BuildConfiguration

class LvUpdateProjectFiles extends LvStep {

    LvUpdateProjectFiles(script, mapStep, lvVersion) {
        super(script, mapStep, lvVersion)
    }

    void executeStep(BuildConfiguration configuration) {
        script.lvUpdateProjectFiles(configuration.packageInfo, lvVersion)
    }
}
    