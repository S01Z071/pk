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
@Table(name = "STATUS_LOG_IMETNIK")
public class StatusLogImetnik implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,generator = "SEQ_STATUS_LOG_IMETNIK")
    @SequenceGenerator(name="SEQ_STATUS_LOG_IMETNIK", sequenceName="SEQ_STATUS_LOG_IMETNIK")
    private Long id;
    
    @Column(name = "PREJSNJI_STATUS")
    private String prejsnjiStatusI;
    
    @Column(name = "NOV_STATUS")
    private String novStatusI;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "DATUM")
    private Date datumI;
    
    @Column(name = "KADR_ST")
    private String uporabnikI;

    @Column(name = "IMETNIK_ID")
    private long imetnikLID;
    
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

    public String getPrejsnjiStatusI() {
        return prejsnjiStatusI;
    }

    public void setPrejsnjiStatusI(String prejsnjiStatusI) {
        this.prejsnjiStatusI = prejsnjiStatusI;
    }

    public String getNovStatusI() {
        return novStatusI;
    }

    public void setNovStatusI(String novStatusI) {
        this.novStatusI = novStatusI;
    }

    public Date getDatumI() {
        return datumI;
    }

    public void setDatumI(Date datumI) {
        this.datumI = datumI;
    }   

    public String getUporabnikI() {
        return uporabnikI;
    }

    public void setUporabnikI(String uporabnikI) {
        this.uporabnikI = uporabnikI;
    }

    public long getImetnikLID() {
        return imetnikLID;
    }

    public void setImetnikLID(long imetnikLID) {
        this.imetnikLID = imetnikLID;
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
