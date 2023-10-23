/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author uporabnik
 */
@Entity
@Table(name = "JOBLOG")
public class JobLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_JOBLOG")
    @SequenceGenerator(name = "SEQ_JOBLOG", sequenceName = "SEQ_JOBLOG")
    private Long id;
    
    //@DateTimeFormat(pattern = "DD.MM.YYYY")
    @Column(name = "START_DATE")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "JOB_ID")
    private Long jobId;
    @Column(name = "STATUS")
    private String status;

    @Column(name = "NUMBER_OF_UPDATES")
    private Long numberOfUpdates;
    
    @OneToMany(mappedBy = "jobLog", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Certifikat> certifikati;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getNumberOfUpdates() {
        return numberOfUpdates;
    }

    public void setNumberOfUpdates(Long numberOfUpdates) {
        this.numberOfUpdates = numberOfUpdates;
    }

   public List<Certifikat> getCertifikati() {
        return certifikati;
    }

    public void setCertifikati(List<Certifikat> certifikati) {
        this.certifikati = certifikati;
    }    
    
}
