def call(veristand_version_string, teststand_version_string, teststand_pub_docs_install_path, installer_path){
    bat "labview-cli --kill --lv-ver 2014 \"L:\\steps\\veristandSteps\\veristandSteps-installer-update.vi\" -- \"${veristand_version_string}\" \"${teststand_version_string}\" \"${teststand_pub_docs_install_path}\" \"${installer_path}\""
}