# veristand-steps-build-system

This repository contains code which is part of an automated build system for the [VeriStand Step Types for TestStand](https://github.com/NIVeriStandAdd-Ons/VeriStand-steps-for-TestStand).

## Getting Started

*The code in this repository is only designed to build the [VeriStand Step Types for TestStand](https://github.com/NIVeriStandAdd-Ons/VeriStand-steps-for-TestStand). It is not supported by NI and should not be used for other projects.*

This code is designed to be invoked from code running as part of a [Jenkins Pipeline](https://jenkins.io/doc/book/pipeline/) style project. All code in this repository is written in LabVIEW. There is documentation within individual code modules, and more documentation will eventually be added here.

The repository comprises four top-level folders and a single LabVIEW project.

The **lv-build-system** folder contains LabVIEW VIs and controls which are used to automate tasks related to building LabVIEW code.

The **nipkg-build-system** folder contains LabVIEW VIs and controls which are used to automate tasks related to building NIPKGs.

The **veristand-steps-build-stem** folder contains LabVIEW VIs and controls which are used to automate tasks related to building the VeriStand Step Types.

The **vars** folder contains Groovy scripts to perform various tasks during the build pipeline.  

The **lv-build-system**, **nipkg-build-system**, and **veristand-steps-build-system** folders each contain a folder named **Steps**. The **Steps** folder contains the LabVIEW VIs which will be directly invoked by the build pipeline. Each VI in the **Steps** directories corresponds to a Groovy script file in the **vars** folder.

The build is initiated by a Jenkins Pipeline job configured to invoke the Jenkinsfile script in the top level of the [VeriStand Step Types](https://github.com/NIVeriStandAdd-Ons/VeriStand-steps-for-TestStand) repository. 


### Prerequisites

The complete build system for the VeriStand Step Types for TestStand requires the following applications and environments be present on the build machine. 

The versions required will depend on which version of the VeriStand Step Types the machine is being used to build.


+ NI LabVIEW Development System (version matching VeriStand)

+ [LabVIEW Command Line Interface](https://github.com/JamesMc86/LabVIEW-CLI)

+ NI VeriStand 

+ NI TestStand

+ Microsoft Visual Studio

**In addition**, the build system requires a PC configured with a Jenkins build server. The Jenkins server needs to have plugins installed to support Git, Pipeline style projects, and Global Shared Libraries for Pipelines. **#TODO:** add list of plugins.


## License

This project is licensed under the BSD 2-clause "Simplified" License - see the [LICENSE.md](LICENSE.md) file for details


