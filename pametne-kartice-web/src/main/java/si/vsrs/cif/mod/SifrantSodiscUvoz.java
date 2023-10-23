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
@Table(name = "SIFRANT_SODISC_UVOZ")
public class SifrantSodiscUvoz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_KARTICA")
    @SequenceGenerator(name = "SEQ_KARTICA", sequenceName = "SEQ_KARTICA")
    private Long id;
    @Column(name = "NAZIV")
    private String naziv;
    @Column(name = "SIFRA")
    private String sifra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
  
   
}
