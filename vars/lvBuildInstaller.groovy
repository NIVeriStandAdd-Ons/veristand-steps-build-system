def call(path, target_name, spec_name, lv_version) {

		//This step is designed to build a LabVIEW installer. It is identical to the lvBuild step except that it does not move built files after the build. 
		
        echo "Build the build spec: ${spec_name} under target ${target_name} in project at ${path}"
        bat "labview-cli --kill --lv-ver ${lv_version} \"L:\\lv-build-system\\steps\\lv-installer-build.vi\" -- \"${path}\" \"${target_name}\" \"${spec_name}\" \"${WORKSPACE}\""
}