def call(path, target_name, spec_name, lv_version, temp_build_path) {

		//This step calls the 64-bit LabVIEW application to build the specified build spec within the project.
		
        echo "Build the build spec: ${spec_name} under target ${target_name} in project at ${path}"
        bat "labview-cli --kill --x64 --lv-ver ${lv_version} \"L:\\lv-build-system\\steps\\lv-build.vi\" -- \"${path}\" \"${target_name}\" \"${spec_name}\" \"${temp_build_path}\" \"${WORKSPACE}\""
}