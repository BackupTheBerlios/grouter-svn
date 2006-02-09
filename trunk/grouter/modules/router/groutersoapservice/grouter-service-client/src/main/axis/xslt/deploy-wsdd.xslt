<?xml version="1.0" encoding="iso-8859-1"?>

<xsl:stylesheet xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
		xmlns="http://xml.apache.org/axis/wsdd/"
		xmlns:java="http://xml.apache.org/axis/wsdd/providers/java"
		xmlns:wsdlsoap="http://schemas.xmlsoap.org/wsdl/soap/"
		xmlns:schema="http://www.w3.org/2001/XMLSchema"
		exclude-result-prefixes="xsl wsdl wsdlsoap schema"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    

		
	<xsl:output method="xml" indent="yes" encoding="iso-8859-1"/> 
	
	<xsl:param name="implementationclassname"/>
	<xsl:param name="scope"/>

	<xsl:template match="/">
		<deployment>
			<service provider="java:RPC">
			   <xsl:attribute name="name"><xsl:value-of select="/wsdl:definitions/wsdl:service/@name"/></xsl:attribute>
				<parameter name="scope" value="value">
				    <xsl:attribute name="value"><xsl:value-of select="$scope"/></xsl:attribute>
				</parameter>
				<parameter name="className">
				    <xsl:attribute name="value"><xsl:value-of select="$implementationclassname"/></xsl:attribute>
				</parameter>
				<parameter name="allowedMethods">
				  <xsl:call-template name="make-allowed-methods"/>
				</parameter>
				<xsl:call-template name="make-type-mappings"/>
			</service>
		</deployment>
	</xsl:template>
	
	<!--
	  Generate the list of allowed methods
	  -->
	<xsl:template name="make-allowed-methods">
	   <xsl:attribute name="value">
	     <xsl:apply-templates select="/wsdl:definitions/wsdl:binding/wsdl:operation" mode="make-allowed-methods"/>
	   </xsl:attribute>
	</xsl:template>

	<!--
	  Match wsdl:operation in order to generate list of allowed-methods
	  -->
	<xsl:template match="wsdl:operation" mode="make-allowed-methods">
	  <xsl:value-of select="@name"/>
	  <xsl:if test="not(position() = last())">
	    <xsl:text>, </xsl:text>
	  </xsl:if>
	</xsl:template>
	
	
	<!--
	  make type mappings
	  -->
	<xsl:template name="make-type-mappings">
	  <xsl:apply-templates select="/wsdl:definitions/wsdl:types/schema:schema" mode="make-type-mapping"/>
	</xsl:template>
	
	
	<!--
	  Generate typeMapping for this schema
	  -->
	<xsl:template match="schema:schema" mode="make-type-mapping">
	  <xsl:variable name="package">
	    <xsl:call-template name="namespacetopackage">
	      <xsl:with-param name="namespace" select="@targetNamespace"/>
	    </xsl:call-template>
	  </xsl:variable>
	  <xsl:apply-templates select="schema:complexType" mode="resolve-type-mapping">
	    <xsl:with-param name="package" select="$package"/>
	    <xsl:with-param name="namespace" select="@targetNamespace"/>
	  </xsl:apply-templates>
	</xsl:template>
	
	<!--
	  Resolve which typeMapping to use
	  -->
	<xsl:template match="schema:complexType" mode="resolve-type-mapping">
	  <xsl:param name="package"/>
	  <xsl:param name="namespace"/>
	  <xsl:if test="starts-with(@name, 'ArrayOf')">
	    <xsl:apply-templates select="." mode="make-array-mapping">
	      <xsl:with-param name="package" select="$package"/>
	      <xsl:with-param name="namespace" select="$namespace"/>
	    </xsl:apply-templates>
	  </xsl:if>
	  <xsl:if test="not(starts-with(@name, 'ArrayOf'))">
	     <xsl:apply-templates select="." mode="make-bean-mapping">
	       <xsl:with-param name="package" select="$package"/>
	       <xsl:with-param name="namespace" select="$namespace"/>
	     </xsl:apply-templates>
	  </xsl:if>
	</xsl:template>
	
	<!--
	  Generate typeMapping for this array complexType
	  -->
	<xsl:template match="schema:complexType" mode="make-array-mapping">
	  <xsl:param name="package"/>
	  <xsl:param name="namespace"/>
	  <xsl:variable name="type" select="substring-after(substring-after(@name, '_'),'_')"/>
	  <xsl:variable name="type_namespace" select="//schema:schema[schema:complexType/@name=$type]/@targetNamespace"/>
	  <xsl:variable name="type_package">
	    <xsl:call-template name="namespacetopackage">
	      <xsl:with-param name="namespace" select="$type_namespace"/>
	    </xsl:call-template>
	  </xsl:variable>
	  <typeMapping
	     serializer="org.apache.axis.encoding.ser.ArraySerializerFactory"
	     deserializer="org.apache.axis.encoding.ser.ArrayDeserializerFactory"
	     encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
	    <xsl:attribute name="type">
	      <xsl:text>java:</xsl:text>
	      <xsl:value-of select="$type_package"/>
	      <xsl:value-of select="$type"/>
	      <xsl:text>[]</xsl:text>
	    </xsl:attribute>
            <xsl:attribute name="xmlns-ns">
              <xsl:value-of select="$namespace"/>
            </xsl:attribute>
	    <xsl:attribute name="qname">
	      <xsl:text>ns:</xsl:text>
	      <xsl:value-of select="@name"/>
	    </xsl:attribute>
	  </typeMapping>
	</xsl:template>
	
	<!--
	  Generate beanMapping for this complexType
	  -->
	<xsl:template match="schema:complexType" mode="make-bean-mapping">
	  <xsl:param name="package"/>
	  <xsl:param name="namespace"/>
	  <beanMapping>
	    <xsl:attribute name="type">
	      <xsl:text>java:</xsl:text>
	      <xsl:value-of select="$package"/>
	      <xsl:value-of select="@name"/>
	    </xsl:attribute>
            <xsl:attribute name="xmlns-ns">
              <xsl:value-of select="$namespace"/>
            </xsl:attribute>
	    <xsl:attribute name="qname">
	      <xsl:text>ns:</xsl:text>
	      <xsl:value-of select="@name"/>
	    </xsl:attribute>
	  </beanMapping>
	</xsl:template>
	
	<!--
	  namespacetopackage
	  
	  Converts: http://user.service.sonyericsson.com
	        to: com.sonyericsson.service.user
	  -->
    <xsl:template name="namespacetopackage">
	  <xsl:param name="namespace"/>
	  <xsl:call-template name="reverse">
	    <xsl:with-param name="source">
	      <xsl:value-of select="translate(substring-after($namespace, 'http://'), '/', '.')"/>
	    </xsl:with-param>
	    <xsl:with-param name="dest"/>
	  </xsl:call-template>
	</xsl:template>
	
	
	<!--
	  Reverse a textstring in parts.
	  
	  user.service.sonyericsson.com 
	  
	  becomes:
	  
	  com.sonyericsson.service.user
	  
	  -->
	<xsl:template name="reverse">
	  <xsl:param name="source"/>
	  <xsl:param name="dest"/>
	  
	  <!--
	    sanity check - will normally not happen
	    -->
	  <xsl:if test="string-length(source) = 0">
	    <xsl:value-of select="dest"/>
	  </xsl:if>
	  
	  <xsl:variable name="firstname">
	    <xsl:value-of select="substring-before($source, '.')"/>
	  </xsl:variable>
	  
	  <xsl:if test="string-length($firstname) = 0">
	    <xsl:if test="not(string-length($source)) = 0">
	      <xsl:value-of select="$source"/>
	      <xsl:text>.</xsl:text>
	    </xsl:if>
	    <xsl:value-of select="$dest"/>
	  </xsl:if>
	  
	  <xsl:if test="not(string-length($firstname) = 0)">
	    <xsl:call-template name="reverse">
	      <xsl:with-param name="source"><xsl:value-of select="substring-after($source, '.')"/></xsl:with-param>
	      <xsl:with-param name="dest"><xsl:value-of select="$firstname"/>.<xsl:value-of select="$dest"/></xsl:with-param>
	    </xsl:call-template>
	  </xsl:if>
	</xsl:template>
	    
</xsl:stylesheet>
