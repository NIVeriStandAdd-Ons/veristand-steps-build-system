def call(custom_palette_filepath, vs_assembly_version, steps_version_string, vs_install_path){
    echo "Updating VeriStand Steps Custom Type Palette file at: ${custom_palette_filepath}."
    bat "labview-cli --kill --lv-ver 2014 \"L:\\steps\\veristandSteps\\veristandSteps-typePaletteFile-update.vi\" -- \"${custom_palette_filepath}\" \"${custom_palette_filepath}\" \"${vs_assembly_version}\" \"${steps_version_string}\" \"${vs_install_path}\""
}