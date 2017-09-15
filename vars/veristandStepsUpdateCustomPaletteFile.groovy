def call(custom_palette_filepath, vs_assembly_version, steps_version_string, vs_install_path, lv_version){


	//This step updates the NIVeristand_Types.ini TestStand custom type palette file by updating the .NET assembly versions, the VeriStand Step Types version string, and the VeriStand.exe installation path. 
	
    echo "Updating VeriStand Steps Custom Type Palette file at: ${custom_palette_filepath}."
    bat "labview-cli --kill --lv-ver ${lv_version} \"L:\\veristand-steps-build-system\\steps\\veristandSteps-typePaletteFile-update.vi\" -- \"${custom_palette_filepath}\" \"${custom_palette_filepath}\" \"${vs_assembly_version}\" \"${steps_version_string}\" \"${vs_install_path}\""
}