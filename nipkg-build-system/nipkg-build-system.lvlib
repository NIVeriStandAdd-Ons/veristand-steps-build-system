<?xml version='1.0' encoding='UTF-8'?>
<Library LVVersion="14008000">
	<Property Name="NI.Lib.Icon" Type="Bin">&amp;!#!!!!!!!)!"1!&amp;!!!-!%!!!@````]!!!!"!!%!!!(]!!!*Q(C=\&gt;7R=2MR%!81N=?"5Q&lt;/07RB7W!,&lt;'&amp;&lt;9+K1,7Q,&lt;)%N&lt;!NMA3X)DW?-RJ(JQ"I\%%Z,(@`BA#==ZB3RN;]28_,V7@PWW`:R`&gt;HV*SU_WE@\N_XF[3:^^TX\+2YP)D7K6;G-RV3P)R`ZS%=_]J'XP/5N&lt;XH,7V\SEJ?]Z#5P?=J4HP+5JTTFWS%0?=B$DD1G(R/.1==!IT.+D)`B':\B'2Z@9XC':XC':XBUC?%:HO%:HO&amp;R7QT0]!T0]!S0I4&lt;*&lt;)?=:XA-(]X40-X40-VDSGC?"GC4N9(&lt;)"D2,L;4ZGG?ZH%;T&gt;-]T&gt;-]T?.S.%`T.%`T.)^&lt;NF8J4@-YZ$S'C?)JHO)JHO)R&gt;"20]220]230[;*YCK=ASI2F=)1I.Z5/Z5PR&amp;)^@54T&amp;5TT&amp;5TQO&lt;5_INJ6Z;"[(H#&gt;ZEC&gt;ZEC&gt;Z$"(*ETT*ETT*9^B)HO2*HO2*(F.&amp;]C20]C2)GN4UE1:,.[:/+5A?0^NOS?UJ^3&lt;*\9B9GT@7JISVW7*NIFC&lt;)^:$D`5Q9TWE7)M@;V&amp;D,6;M29DVR]6#R],%GC47T9_/=@&gt;Z5V&gt;V57&gt;V5E&gt;V5(OV?^T[FTP?\`?YX7ZRP6\D=LH%_8S/U_E5R_-R$I&gt;$\0@\W/VW&lt;[_"\Y[X&amp;],0^^+,]T_J&gt;`J@_B_]'_.T`$KO.@I"XC-_N!!!!!!</Property>
	<Property Name="NI.Lib.SourceVersion" Type="Int">335577088</Property>
	<Property Name="NI.Lib.Version" Type="Str">1.0.0.0</Property>
	<Property Name="NI.LV.All.SourceOnly" Type="Bool">false</Property>
	<Item Name="steps" Type="Folder">
		<Item Name="nipkg-createPackageTemplate.vi" Type="VI" URL="../steps/nipkg-createPackageTemplate.vi"/>
		<Item Name="nipkg-updateControlFileMetadata.vi" Type="VI" URL="../steps/nipkg-updateControlFileMetadata.vi"/>
	</Item>
	<Item Name="subVIs" Type="Folder">
		<Item Name="lv-nipkg-builder" Type="Folder">
			<Item Name="nipkg-setPackageDisplayName.vi" Type="VI" URL="../subVIs/nipkg-setPackageDisplayName.vi"/>
			<Item Name="nipkg-setPackageName.vi" Type="VI" URL="../subVIs/nipkg-setPackageName.vi"/>
			<Item Name="nipkg-setPackageVersion.vi" Type="VI" URL="../subVIs/nipkg-setPackageVersion.vi"/>
		</Item>
		<Item Name="nipm-cli" Type="Folder">
			<Item Name="nipkg-createDebianBinaryFile.vi" Type="VI" URL="../subVIs/nipkg-createDebianBinaryFile.vi"/>
			<Item Name="nipkg-createFolders.vi" Type="VI" URL="../subVIs/nipkg-createFolders.vi"/>
			<Item Name="nipkg-createPackageControlFile.vi" Type="VI" URL="../subVIs/nipkg-createPackageControlFile.vi"/>
			<Item Name="nipkg-updateControlFileDescription.vi" Type="VI" URL="../subVIs/nipkg-updateControlFileDescription.vi"/>
			<Item Name="nipkg-updateControlFileMaintainer.vi" Type="VI" URL="../subVIs/nipkg-updateControlFileMaintainer.vi"/>
			<Item Name="nipkg-updateControlFileMetadataField.vi" Type="VI" URL="../subVIs/nipkg-updateControlFileMetadataField.vi"/>
			<Item Name="nipkg-updateControlFilePackageName.vi" Type="VI" URL="../subVIs/nipkg-updateControlFilePackageName.vi"/>
			<Item Name="nipkg-updateControlFilePackageVersion.vi" Type="VI" URL="../subVIs/nipkg-updateControlFilePackageVersion.vi"/>
		</Item>
	</Item>
	<Item Name="types" Type="Folder">
		<Item Name="Package data.ctl" Type="VI" URL="../types/Package data.ctl"/>
		<Item Name="Package Metadata string constants.ctl" Type="VI" URL="../types/Package Metadata string constants.ctl"/>
	</Item>
	<Item Name="vars" Type="Folder">
		<Item Name="nipkgBuild.groovy" Type="Document" URL="../vars/nipkgBuild.groovy"/>
		<Item Name="nipkgCreate.groovy" Type="Document" URL="../vars/nipkgCreate.groovy"/>
		<Item Name="nipkgCreateTemplate.groovy" Type="Document" URL="../vars/nipkgCreateTemplate.groovy"/>
		<Item Name="nipkgUpdateControlFileMetadata.groovy" Type="Document" URL="../vars/nipkgUpdateControlFileMetadata.groovy"/>
		<Item Name="nipkgUpdateLVBuildSpec.groovy" Type="Document" URL="../vars/nipkgUpdateLVBuildSpec.groovy"/>
	</Item>
</Library>
