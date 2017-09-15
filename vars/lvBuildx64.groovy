def call(path, target_name, spec_name, lv_version) {

		//This step calls the 64-bit LabVIEW application to build the specified build spec within the project.
		
        echo "Build the build spec: ${spec_name} under target ${target_name} in project at ${path}"
        bat "labview-cli --kill --lv-ver --x64 ${lv_version} \"L:\\lv-build-system\\steps\\lv-build.vi\" -- \"${path}\" \"${target_name}\" \"${spec_name}\" \"build_temp\" \"${WORKSPACE}\""
}