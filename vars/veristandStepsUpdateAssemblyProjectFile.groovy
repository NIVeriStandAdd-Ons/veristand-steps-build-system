def call(lv_version,vs_steps_csproj_path, teststand_version, teststand_assembly_version, veristand_assembly_version, veristand_gac_assembly_version){

		//This step updates the Visual Studio C# .csproj project file by replacing the VeriStand and TestStand reference assembly version numbers with those specified. 
		
        bat "labview-cli --kill --lv-ver ${lv_version} \"L:\\veristand-steps-build-system\\steps\\veristandSteps-assemblyProjectFile-update.vi\" -- \"${vs_steps_csproj_path}\" \"${teststand_version}\" \"${teststand_assembly_version}\" \"${veristand_assembly_version}\" \"${veristand_gac_assembly_version}\""
}