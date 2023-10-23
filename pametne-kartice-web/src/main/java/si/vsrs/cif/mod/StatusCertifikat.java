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
@Entity
@Table(name = "STATUS_CERTIFIKAT")
public class StatusCertifikat implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_STATUSI")
    @SequenceGenerator(name = "SEQ_STATUSI", sequenceName = "SEQ_STATUSI")
    private Long id;
    @Column(name = "SIFRA")
    private String sifra;
    @Column(name = "OPIS")
    private String opis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    
    
    
}
