
package com.oracle.xmlns.oxp.service.publicreportservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeliveryRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeliveryRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="emailOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}EMailDeliveryOption"/>
 *         &lt;element name="faxOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}FaxDeliveryOption"/>
 *         &lt;element name="ftpOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}FTPDeliveryOption"/>
 *         &lt;element name="localOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}LocalDeliveryOption"/>
 *         &lt;element name="printOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}PrintDeliveryOption"/>
 *         &lt;element name="webDAVOption" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}WebDAVDeliveryOption"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliveryRequest", propOrder = {
    "emailOption",
    "faxOption",
    "ftpOption",
    "localOption",
    "printOption",
    "webDAVOption"
})
public class DeliveryRequest {

    @XmlElement(required = true, nillable = true)
    protected EMailDeliveryOption emailOption;
    @XmlElement(required = true, nillable = true)
    protected FaxDeliveryOption faxOption;
    @XmlElement(required = true, nillable = true)
    protected FTPDeliveryOption ftpOption;
    @XmlElement(required = true, nillable = true)
    protected LocalDeliveryOption localOption;
    @XmlElement(required = true, nillable = true)
    protected PrintDeliveryOption printOption;
    @XmlElement(required = true, nillable = true)
    protected WebDAVDeliveryOption webDAVOption;

    /**
     * Gets the value of the emailOption property.
     * 
     * @return
     *     possible object is
     *     {@link EMailDeliveryOption }
     *     
     */
    public EMailDeliveryOption getEmailOption() {
        return emailOption;
    }

    /**
     * Sets the value of the emailOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link EMailDeliveryOption }
     *     
     */
    public void setEmailOption(EMailDeliveryOption value) {
        this.emailOption = value;
    }

    /**
     * Gets the value of the faxOption property.
     * 
     * @return
     *     possible object is
     *     {@link FaxDeliveryOption }
     *     
     */
    public FaxDeliveryOption getFaxOption() {
        return faxOption;
    }

    /**
     * Sets the value of the faxOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaxDeliveryOption }
     *     
     */
    public void setFaxOption(FaxDeliveryOption value) {
        this.faxOption = value;
    }

    /**
     * Gets the value of the ftpOption property.
     * 
     * @return
     *     possible object is
     *     {@link FTPDeliveryOption }
     *     
     */
    public FTPDeliveryOption getFtpOption() {
        return ftpOption;
    }

    /**
     * Sets the value of the ftpOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link FTPDeliveryOption }
     *     
     */
    public void setFtpOption(FTPDeliveryOption value) {
        this.ftpOption = value;
    }

    /**
     * Gets the value of the localOption property.
     * 
     * @return
     *     possible object is
     *     {@link LocalDeliveryOption }
     *     
     */
    public LocalDeliveryOption getLocalOption() {
        return localOption;
    }

    /**
     * Sets the value of the localOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalDeliveryOption }
     *     
     */
    public void setLocalOption(LocalDeliveryOption value) {
        this.localOption = value;
    }

    /**
     * Gets the value of the printOption property.
     * 
     * @return
     *     possible object is
     *     {@link PrintDeliveryOption }
     *     
     */
    public PrintDeliveryOption getPrintOption() {
        return printOption;
    }

    /**
     * Sets the value of the printOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrintDeliveryOption }
     *     
     */
    public void setPrintOption(PrintDeliveryOption value) {
        this.printOption = value;
    }

    /**
     * Gets the value of the webDAVOption property.
     * 
     * @return
     *     possible object is
     *     {@link WebDAVDeliveryOption }
     *     
     */
    public WebDAVDeliveryOption getWebDAVOption() {
        return webDAVOption;
    }

    /**
     * Sets the value of the webDAVOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebDAVDeliveryOption }
     *     
     */
    public void setWebDAVOption(WebDAVDeliveryOption value) {
        this.webDAVOption = value;
    }

}
