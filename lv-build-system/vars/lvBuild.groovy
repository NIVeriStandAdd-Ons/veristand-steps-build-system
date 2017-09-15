def call(path, target_name, spec_name, lv_version, build_temp) {

		//This step builds the input specification in the specified project.
		
        echo "Build the build spec: ${spec_name} under target ${target_name} in project at ${path}"
        bat "labview-cli --kill --lv-ver ${lv_version} \"L:\\lv-build-system\\steps\\lv-build.vi\" -- \"${path}\" \"${target_name}\" \"${spec_name}\" \"${build_temp}\" \"${WORKSPACE}\""
}