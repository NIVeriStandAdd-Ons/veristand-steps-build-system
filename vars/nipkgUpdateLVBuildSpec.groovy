def call(lv_version, project_path, package_name, vs_version, package_version, package_display_name){
    echo "Updating custom device NIPM build attributes."
    bat "labview-cli --kill --lv-ver ${lv_version} \"L:\\customDevice-updateNIPMspec.vi\" -- \"${project_path}\" \"${package_name}\" \"${vs_version}\" \"${package_version}\" \"${package_display_name}\""
}