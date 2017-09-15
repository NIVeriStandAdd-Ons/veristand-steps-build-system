def call(dotnet_config_filepath, veristand_version, teststand_version) {

		//This build step updates the .NET assembly binding redirection configuration files that allow LabVIEW to load the specified version of the VeriStand and TestStand assemblies. 
		//Use the LabVIEW version that matches VeriStand because you know that version will be available on the node where this executes. 
		
        echo "Updating \"${dotnet_config_filepath}\" assembly versions to Veristand ${veristand_version} and TestStand ${teststand_version}."
        bat "labview-cli --kill --lv-ver ${veristand_version} \"L:\\veristand-steps-build-system\\steps\\veristandSteps-lvDotNetConfigFile-update.vi\" -- \"${dotnet_config_filepath}\" \"${dotnet_config_filepath}\" \"${teststand_version}\" \"${veristand_version}\""
}