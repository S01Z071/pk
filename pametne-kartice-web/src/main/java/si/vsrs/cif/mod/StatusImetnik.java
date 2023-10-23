/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author uporabnik
 */

//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Entity
@Table(name = "STATUS_IMETNIK")
public class StatusImetnik implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,generator = "SEQ_STATUSI")
    @SequenceGenerator(name="SEQ_STATUSI", sequenceName="SEQ_STATUSI")
    @Column(name = "ID",length = 8)
    private Long idSIm;
    @Column(name = "SIFRA")
    private String sifraSIm;
    @Column(name = "OPIS")
    private String opisSIm;
    @Column(name = "BARVA")
    private String barvaSIm;

    public StatusImetnik() {
    }

    public StatusImetnik(Long idSIm, String sifraSIm, String opisSIm, String barvaSIm) {
        this.idSIm = idSIm;
        this.sifraSIm = sifraSIm;
        this.opisSIm = opisSIm;
        this.barvaSIm = barvaSIm;
    }

    
    public Long getIdSIm() {
        return idSIm;
    }

    public void setIdSIm(Long idSIm) {
        this.idSIm = idSIm;
    }

    public String getSifraSIm() {
        return sifraSIm;
    }

    public void setSifraSIm(String sifraSIm) {
        this.sifraSIm = sifraSIm;
    }

    public String getOpisSIm() {
        return opisSIm;
    }

    public void setOpisSIm(String opisSIm) {
        this.opisSIm = opisSIm;
    }

    public String getBarvaSIm() {
        return barvaSIm;
    }

    public void setBarvaSIm(String barvaSIm) {
        this.barvaSIm = barvaSIm;
    }
}
