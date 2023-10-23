/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author uporabnik
 */

@Entity
@Table(name = "STATUS_LOG_PREKLIC")
public class StatusLogPreklic implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,generator = "SEQ_STATUS_LOG")
    @SequenceGenerator(name="SEQ_STATUS_LOG", sequenceName="SEQ_STATUS_LOG")
    private Long id;
    
    @Column(name = "PREJSNJI_STATUS")
    private String prejsnjiStatus;
    
    @Column(name = "NOV_STATUS")
    private String novStatus;
    
    @Column(name = "DATUM")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date datum;
    
    @Column(name = "KADR_ST")
    private String uporabnik;

    @Column(name = "ZAHTEVEK_PREKLIC_ID")
    private long zahtevekPreklicLID;
    
    @Column(name = "OPIS")
    private String opis;
    
    @Column(name = "SIF_SODISCE")
    private String sodisce;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrejsnjiStatus() {
        return prejsnjiStatus;
    }

    public void setPrejsnjiStatus(String prejsnjiStatus) {
        this.prejsnjiStatus = prejsnjiStatus;
    }

    public String getNovStatus() {
        return novStatus;
    }

    public void setNovStatus(String novStatus) {
        this.novStatus = novStatus;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(String uporabnik) {
        this.uporabnik = uporabnik;
    }

    public long getZahtevekPreklicLID() {
        return zahtevekPreklicLID;
    }

    public void setZahtevekPreklicLID(long zahtevekPreklicLID) {
        this.zahtevekPreklicLID = zahtevekPreklicLID;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSodisce() {
        return sodisce;
    }

    public void setSodisce(String sodisce) {
        this.sodisce = sodisce;
    }

}
