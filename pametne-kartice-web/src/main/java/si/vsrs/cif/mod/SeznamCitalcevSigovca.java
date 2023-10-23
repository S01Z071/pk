/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author uporabnik
 */
@Entity
@Table(name = "SEZNAM_CITALCEV_SIGOVCA")
public class SeznamCitalcevSigovca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_KARTICA")
    @SequenceGenerator(name = "SEQ_KARTICA", sequenceName = "SEQ_KARTICA")
    private Long id;
    @Column(name = "IME_ORGANIZACIJE")
    private String imeOrganizacije;
    @Column(name = "IME_IN_PRIIMEK")
    private String imeInPriimek;
    @Column(name = "TIP")
    private String tip;
    @Column(name = "OZNAKA")
    private String oznaka;
    @DateTimeFormat(pattern = "DD.MM.YYYY")
    @Column(name = "DATUM_IZDAJE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datumIzdaje;
    @DateTimeFormat(pattern = "DD.MM.YYYY")
    @Column(name = "DATUM_VRNITVE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datumVrnitve;
    @Column(name = "SIFRA_SODISCA")
    private String sifraSodisca;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "IMETNIK_ID")
    private Imetnik imetnik;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImeOrganizacije() {
        return imeOrganizacije;
    }

    public void setImeOrganizacije(String imeOrganizacije) {
        this.imeOrganizacije = imeOrganizacije;
    }

    public String getImeInPriimek() {
        return imeInPriimek;
    }

    public void setImeInPriimek(String imeInPriimek) {
        this.imeInPriimek = imeInPriimek;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public Date getDatumIzdaje() {
        return datumIzdaje;
    }

    public void setDatumIzdaje(Date datumIzdaje) {
        this.datumIzdaje = datumIzdaje;
    }

    public Date getDatumVrnitve() {
        return datumVrnitve;
    }

    public void setDatumVrnitve(Date datumVrnitve) {
        this.datumVrnitve = datumVrnitve;
    }

    public String getSifraSodisca() {
        return sifraSodisca;
    }

    public void setSifraSodisca(String sifraSodisca) {
        this.sifraSodisca = sifraSodisca;
    }

    public Imetnik getImetnik() {
        return imetnik;
    }

    public void setImetnik(Imetnik imetnik) {
        this.imetnik = imetnik;
    }    
}
