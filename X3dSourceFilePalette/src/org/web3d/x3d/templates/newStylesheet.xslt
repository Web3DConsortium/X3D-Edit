<?xml version="1.0" encoding="UTF-8"?>
<!--
    title       : filename.xslt
    created     : ## Month 2015
    revised     : ## Month 2015
    creator     : 
    description : 
    reference   : http://www.w3.org/TR/xslt
    reference   : http://www.web3d.org/x3d/content/examples/newStylesheet.xslt
    identifier  : http://www.web3d.org/x3d/content/examples/filename.xslt
    license     : license.html
-->

<!-- TODO authors can edit this example to customize all transformation rules -->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="html"/> <!-- output methods:  xml html text -->
    
    <!-- ===================================================== -->
    
    <xsl:template match="/"> <!-- process root of input document -->
        <html>
            <head>
                <title>TODO: stylesheet_result_name</title>
            </head>
            <body>
                <xsl:apply-templates select="* | comment()"/> <!-- process elements and comments -->
            </body>
        </html>
    </xsl:template>
    
    <!-- ===================================================== -->
    
    <xsl:template match="*"> <!-- rule to process each element -->
        
        <!-- TODO:  common initial processing for each element -->
        
        <xsl:apply-templates select="@*"/> <!-- process attributes for this element -->
        
        <xsl:apply-templates select="* | comment()"/> <!-- recurse on child elements and comments -->
        
        <!-- TODO:  common final processing for each element -->
        
    </xsl:template>
    
    <!-- ===================================================== -->
    
    <xsl:template match="@*"> <!-- rule to process each attribute -->
        
        <!-- TODO:  common processing for each attribute -->
        
    </xsl:template>
    
    <!-- ===================================================== -->
    
    <xsl:template match="comment()"> <!-- rule to process each comment -->
    
        <xsl:text disable-output-escaping="yes">&lt;!--</xsl:text>
        <xsl:value-of select="."/>
        <xsl:text disable-output-escaping="yes">--&gt;</xsl:text>
        <xsl:text>&#10;</xsl:text>
        
    </xsl:template>

</xsl:stylesheet>
