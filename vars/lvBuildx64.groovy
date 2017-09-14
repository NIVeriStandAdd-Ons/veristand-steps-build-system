def call(path, target_name, spec_name, lv_version, build_dest) {
        echo "Build the build spec: ${spec_name} under target ${target_name} in project at ${path}"
        bat "labview-cli --kill --lv-exe \"C:\\Program Files\\National Instruments\\LabVIEW ${lv_version}\\LabVIEW.exe\" \"L:\\steps\\lv-build.vi\" -- \"${path}\" \"${target_name}\" \"${spec_name}\" \"${build_dest}\" \"${WORKSPACE}\""
}