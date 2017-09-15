def call(package_source, package_destination){

	//This step uses the NI Package Manager's command line utility (nipkg.exe) to run the 'pack' command on the nipkg source directory.
	
    echo "Building package from \"${package_source}\" and placing at \"${package_destination}\"."
	
    nipm_path="C:\\Program Files\\National Instruments\\NI Package Manager\\nipkg.exe"
    bat "\"${nipm_path}\" pack \"${package_source}\" \"${package_destination}\""
}