def call(package_source_path){
		// This VI creates a generic package source directory. Subsequent steps will modify this generic template to be project-specific. 
        bat "labview-cli --kill --lv-ver 2014 \"L:\\steps\\nipkg\\nipkg-createPackageTemplate.vi\" -- \"${package_source_path}\""
}