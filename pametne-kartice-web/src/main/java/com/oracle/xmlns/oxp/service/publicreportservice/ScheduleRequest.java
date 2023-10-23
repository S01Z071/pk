
package com.oracle.xmlns.oxp.service.publicreportservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ScheduleRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScheduleRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cronExpression" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deliveryRequest" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}DeliveryRequest"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="jobCalendar" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="jobLocale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="jobTZ" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="notificationTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="notifyWhenFailed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="notifyWhenSuccess" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="notifyWhenWarning" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="repeatCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="repeatInterval" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reportRequest" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}ReportRequest"/>
 *         &lt;element name="saveDataOption" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="saveOutputOption" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="scheduleBurstringOption" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="schedulePublicOption" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="useUTF8Option" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="userJobName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleRequest", propOrder = {
    "cronExpression",
    "deliveryRequest",
    "endDate",
    "jobCalendar",
    "jobLocale",
    "jobTZ",
    "notificationTo",
    "notifyWhenFailed",
    "notifyWhenSuccess",
    "notifyWhenWarning",
    "repeatCount",
    "repeatInterval",
    "reportRequest",
    "saveDataOption",
    "saveOutputOption",
    "scheduleBurstringOption",
    "schedulePublicOption",
    "startDate",
    "useUTF8Option",
    "userJobName"
})
public class ScheduleRequest {

    @XmlElement(required = true, nillable = true)
    protected String cronExpression;
    @XmlElement(required = true, nillable = true)
    protected DeliveryRequest deliveryRequest;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(required = true, nillable = true)
    protected String jobCalendar;
    @XmlElement(required = true, nillable = true)
    protected String jobLocale;
    @XmlElement(required = true, nillable = true)
    protected String jobTZ;
    @XmlElement(required = true, nillable = true)
    protected String notificationTo;
    protected boolean notifyWhenFailed;
    protected boolean notifyWhenSuccess;
    protected boolean notifyWhenWarning;
    protected int repeatCount;
    protected int repeatInterval;
    @XmlElement(required = true, nillable = true)
    protected ReportRequest reportRequest;
    protected boolean saveDataOption;
    protected boolean saveOutputOption;
    protected boolean scheduleBurstringOption;
    protected boolean schedulePublicOption;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    protected boolean useUTF8Option;
    @XmlElement(required = true, nillable = true)
    protected String userJobName;

    /**
     * Gets the value of the cronExpression property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * Sets the value of the cronExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCronExpression(String value) {
        this.cronExpression = value;
    }

    /**
     * Gets the value of the deliveryRequest property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryRequest }
     *     
     */
    public DeliveryRequest getDeliveryRequest() {
        return deliveryRequest;
    }

    /**
     * Sets the value of the deliveryRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryRequest }
     *     
     */
    public void setDeliveryRequest(DeliveryRequest value) {
        this.deliveryRequest = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the jobCalendar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobCalendar() {
        return jobCalendar;
    }

    /**
     * Sets the value of the jobCalendar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobCalendar(String value) {
        this.jobCalendar = value;
    }

    /**
     * Gets the value of the jobLocale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobLocale() {
        return jobLocale;
    }

    /**
     * Sets the value of the jobLocale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobLocale(String value) {
        this.jobLocale = value;
    }

    /**
     * Gets the value of the jobTZ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobTZ() {
        return jobTZ;
    }

    /**
     * Sets the value of the jobTZ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobTZ(String value) {
        this.jobTZ = value;
    }

    /**
     * Gets the value of the notificationTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotificationTo() {
        return notificationTo;
    }

    /**
     * Sets the value of the notificationTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotificationTo(String value) {
        this.notificationTo = value;
    }

    /**
     * Gets the value of the notifyWhenFailed property.
     * 
     */
    public boolean isNotifyWhenFailed() {
        return notifyWhenFailed;
    }

    /**
     * Sets the value of the notifyWhenFailed property.
     * 
     */
    public void setNotifyWhenFailed(boolean value) {
        this.notifyWhenFailed = value;
    }

    /**
     * Gets the value of the notifyWhenSuccess property.
     * 
     */
    public boolean isNotifyWhenSuccess() {
        return notifyWhenSuccess;
    }

    /**
     * Sets the value of the notifyWhenSuccess property.
     * 
     */
    public void setNotifyWhenSuccess(boolean value) {
        this.notifyWhenSuccess = value;
    }

    /**
     * Gets the value of the notifyWhenWarning property.
     * 
     */
    public boolean isNotifyWhenWarning() {
        return notifyWhenWarning;
    }

    /**
     * Sets the value of the notifyWhenWarning property.
     * 
     */
    public void setNotifyWhenWarning(boolean value) {
        this.notifyWhenWarning = value;
    }

    /**
     * Gets the value of the repeatCount property.
     * 
     */
    public int getRepeatCount() {
        return repeatCount;
    }

    /**
     * Sets the value of the repeatCount property.
     * 
     */
    public void setRepeatCount(int value) {
        this.repeatCount = value;
    }

    /**
     * Gets the value of the repeatInterval property.
     * 
     */
    public int getRepeatInterval() {
        return repeatInterval;
    }

    /**
     * Sets the value of the repeatInterval property.
     * 
     */
    public void setRepeatInterval(int value) {
        this.repeatInterval = value;
    }

    /**
     * Gets the value of the reportRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ReportRequest }
     *     
     */
    public ReportRequest getReportRequest() {
        return reportRequest;
    }

    /**
     * Sets the value of the reportRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportRequest }
     *     
     */
    public void setReportRequest(ReportRequest value) {
        this.reportRequest = value;
    }

    /**
     * Gets the value of the saveDataOption property.
     * 
     */
    public boolean isSaveDataOption() {
        return saveDataOption;
    }

    /**
     * Sets the value of the saveDataOption property.
     * 
     */
    public void setSaveDataOption(boolean value) {
        this.saveDataOption = value;
    }

    /**
     * Gets the value of the saveOutputOption property.
     * 
     */
    public boolean isSaveOutputOption() {
        return saveOutputOption;
    }

    /**
     * Sets the value of the saveOutputOption property.
     * 
     */
    public void setSaveOutputOption(boolean value) {
        this.saveOutputOption = value;
    }

    /**
     * Gets the value of the scheduleBurstringOption property.
     * 
     */
    public boolean isScheduleBurstringOption() {
        return scheduleBurstringOption;
    }

    /**
     * Sets the value of the scheduleBurstringOption property.
     * 
     */
    public void setScheduleBurstringOption(boolean value) {
        this.scheduleBurstringOption = value;
    }

    /**
     * Gets the value of the schedulePublicOption property.
     * 
     */
    public boolean isSchedulePublicOption() {
        return schedulePublicOption;
    }

    /**
     * Sets the value of the schedulePublicOption property.
     * 
     */
    public void setSchedulePublicOption(boolean value) {
        this.schedulePublicOption = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the useUTF8Option property.
     * 
     */
    public boolean isUseUTF8Option() {
        return useUTF8Option;
    }

    /**
     * Sets the value of the useUTF8Option property.
     * 
     */
    public void setUseUTF8Option(boolean value) {
        this.useUTF8Option = value;
    }

    /**
     * Gets the value of the userJobName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserJobName() {
        return userJobName;
    }

    /**
     * Sets the value of the userJobName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserJobName(String value) {
        this.userJobName = value;
    }

}
