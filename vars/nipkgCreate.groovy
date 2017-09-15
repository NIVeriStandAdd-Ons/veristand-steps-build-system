def call(source_path, build_path, payload_path, staging_path, package_name, package_display_name, package_version, package_maintainer, package_display_version, package_description){
        
		//Create a generic template package at source_path
        nipkgCreateTemplate(source_path)
		
        //Make sure the build directory exists for the package
        bat "IF NOT EXIST \"${build_path}\" mkdir \"${build_path}\""
        
		//Copy the package payload to the package source staging Data folder
        bat "xcopy /E /I /Y \"${payload_path}\" \"${staging_path}\""
        
		//Update the metadata stored in the package Control file
        nipkgUpdateControlFileMetadata("${source_path}\\control\\control", package_name, package_display_name, package_version, package_display_version, package_description, package_maintainer)
        
		//Build the .nipkg
        nipkgBuild(source_path, build_path)
        
		//Remove any payload files from the staging directory to prepare for any subsequent builds
        bat "rmdir /s /q \"${staging_path}\""
		
}