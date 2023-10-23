
package com.oracle.xmlns.oxp.service.publicreportservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="getScheduledReportHistoryInfoReturn" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}JobHistoryInfo" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getScheduledReportHistoryInfoReturn"
})
@XmlRootElement(name = "getScheduledReportHistoryInfoResponse")
public class GetScheduledReportHistoryInfoResponse {

    @XmlElement(required = true)
    protected List<JobHistoryInfo> getScheduledReportHistoryInfoReturn;

    /**
     * Gets the value of the getScheduledReportHistoryInfoReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the getScheduledReportHistoryInfoReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGetScheduledReportHistoryInfoReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JobHistoryInfo }
     * 
     * 
     */
    public List<JobHistoryInfo> getGetScheduledReportHistoryInfoReturn() {
        if (getScheduledReportHistoryInfoReturn == null) {
            getScheduledReportHistoryInfoReturn = new ArrayList<JobHistoryInfo>();
        }
        return this.getScheduledReportHistoryInfoReturn;
    }

}
