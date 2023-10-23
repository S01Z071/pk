
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
 *         &lt;element name="getFolderContentsReturn" type="{http://xmlns.oracle.com/oxp/service/PublicReportService}ItemData" maxOccurs="unbounded"/>
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
    "getFolderContentsReturn"
})
@XmlRootElement(name = "getFolderContentsResponse")
public class GetFolderContentsResponse {

    @XmlElement(required = true)
    protected List<ItemData> getFolderContentsReturn;

    /**
     * Gets the value of the getFolderContentsReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the getFolderContentsReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGetFolderContentsReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemData }
     * 
     * 
     */
    public List<ItemData> getGetFolderContentsReturn() {
        if (getFolderContentsReturn == null) {
            getFolderContentsReturn = new ArrayList<ItemData>();
        }
        return this.getFolderContentsReturn;
    }

}
