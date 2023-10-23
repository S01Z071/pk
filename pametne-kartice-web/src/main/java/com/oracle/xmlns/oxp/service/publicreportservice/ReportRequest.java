
package com.oracle.xmlns.oxp.service.publicreportservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReportRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReportRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attributeFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attributeLocale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attributeTemplate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flattenXML" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="parameterNameValues" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}ArrayOfParamNameValue"/>
 *         &lt;element name="reportAbsolutePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reportData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="reportDataFileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sizeOfDataChunkDownload" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportRequest", propOrder = {
    "attributeFormat",
    "attributeLocale",
    "attributeTemplate",
    "flattenXML",
    "parameterNameValues",
    "reportAbsolutePath",
    "reportData",
    "reportDataFileName",
    "sizeOfDataChunkDownload"
})
public class ReportRequest {

    @XmlElement(required = true, nillable = true)
    protected String attributeFormat;
    @XmlElement(required = true, nillable = true)
    protected String attributeLocale;
    @XmlElement(required = true, nillable = true)
    protected String attributeTemplate;
    protected boolean flattenXML;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfParamNameValue parameterNameValues;
    @XmlElement(required = true, nillable = true)
    protected String reportAbsolutePath;
    @XmlElement(required = true, nillable = true)
    protected byte[] reportData;
    @XmlElement(required = true, nillable = true)
    protected String reportDataFileName;
    protected int sizeOfDataChunkDownload;

    /**
     * Gets the value of the attributeFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeFormat() {
        return attributeFormat;
    }

    /**
     * Sets the value of the attributeFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeFormat(String value) {
        this.attributeFormat = value;
    }

    /**
     * Gets the value of the attributeLocale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeLocale() {
        return attributeLocale;
    }

    /**
     * Sets the value of the attributeLocale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeLocale(String value) {
        this.attributeLocale = value;
    }

    /**
     * Gets the value of the attributeTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeTemplate() {
        return attributeTemplate;
    }

    /**
     * Sets the value of the attributeTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeTemplate(String value) {
        this.attributeTemplate = value;
    }

    /**
     * Gets the value of the flattenXML property.
     * 
     */
    public boolean isFlattenXML() {
        return flattenXML;
    }

    /**
     * Sets the value of the flattenXML property.
     * 
     */
    public void setFlattenXML(boolean value) {
        this.flattenXML = value;
    }

    /**
     * Gets the value of the parameterNameValues property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfParamNameValue }
     *     
     */
    public ArrayOfParamNameValue getParameterNameValues() {
        return parameterNameValues;
    }

    /**
     * Sets the value of the parameterNameValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfParamNameValue }
     *     
     */
    public void setParameterNameValues(ArrayOfParamNameValue value) {
        this.parameterNameValues = value;
    }

    /**
     * Gets the value of the reportAbsolutePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportAbsolutePath() {
        return reportAbsolutePath;
    }

    /**
     * Sets the value of the reportAbsolutePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportAbsolutePath(String value) {
        this.reportAbsolutePath = value;
    }

    /**
     * Gets the value of the reportData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getReportData() {
        return reportData;
    }

    /**
     * Sets the value of the reportData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setReportData(byte[] value) {
        this.reportData = ((byte[]) value);
    }

    /**
     * Gets the value of the reportDataFileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportDataFileName() {
        return reportDataFileName;
    }

    /**
     * Sets the value of the reportDataFileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportDataFileName(String value) {
        this.reportDataFileName = value;
    }

    /**
     * Gets the value of the sizeOfDataChunkDownload property.
     * 
     */
    public int getSizeOfDataChunkDownload() {
        return sizeOfDataChunkDownload;
    }

    /**
     * Sets the value of the sizeOfDataChunkDownload property.
     * 
     */
    public void setSizeOfDataChunkDownload(int value) {
        this.sizeOfDataChunkDownload = value;
    }

}
