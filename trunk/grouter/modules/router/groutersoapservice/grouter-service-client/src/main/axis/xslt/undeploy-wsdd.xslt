<?xml version="1.0" encoding="iso-8859-1"?>

<xsl:stylesheet
		xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
		xmlns:wsdd="http://xml.apache.org/axis/wsdd/"
		exclude-result-prefixes="xsl wsdl"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="xml" indent="yes" encoding="iso-8859-1"/> 
	
	<xsl:template match="/">
		<wsdd:undeployment>
			<wsdd:service>
			   <xsl:attribute name="name"><xsl:value-of select="/wsdl:definitions/wsdl:service/@name"/></xsl:attribute>
			</wsdd:service>
		</wsdd:undeployment>
	</xsl:template>
	
</xsl:stylesheet>
