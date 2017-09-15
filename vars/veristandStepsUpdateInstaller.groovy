def call(veristand_version_string, teststand_version_string, teststand_pub_docs_install_path, installer_path, lv_version){

	//This step updates the LabVIEW Installer by setting several property tags in the LabVIEW project file. 
	
    bat "labview-cli --kill --lv-ver $lv_version \"L:\\veristand-steps-build-system\\steps\\veristandSteps-installer-update.vi\" -- \"${veristand_version_string}\" \"${teststand_version_string}\" \"${teststand_pub_docs_install_path}\" \"${installer_path}\""
}