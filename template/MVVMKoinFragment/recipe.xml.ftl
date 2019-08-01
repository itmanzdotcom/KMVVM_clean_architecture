<?xml version="1.0"?>
<recipe>

	<instantiate from="root/src/app_package/Fragment.kt.ftl"
					to="${featureFolder}/${capFeatureName}Fragment.kt" />

	<instantiate from="root/res/layout/layout.xml.ftl"
					 to="${escapeXmlAttribute(resOut)}/layout/fragment_${featureName?lower_case}.xml" />

	<open file="${featureFolder}/${capFeatureName}Fragment.kt" />

	<instantiate from="root/src/app_package/ViewModel.kt.ftl"
                   to="${featureFolder}/${capFeatureName}ViewModel.kt" />   
 			   
</recipe>
