/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author uporabnik
 */
@Entity
@Table(name = "OPOMBA")
public class Opomba implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_OPOMBA")
    @SequenceGenerator(name = "SEQ_OPOMBA", sequenceName = "SEQ_OPOMBA")
    private Long id;
    @Column(name = "DATUM")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date datum;
    @Size(min = 3, max = 255)
    @Column(name = "NASLOV")
    private String naslov;
    @Size(min = 3, max = 255)
    @Column(name = "VSEBINA")
    private String vsebina;
    @Column(name = "KADR_ST")
    private String uporabnik;
    @Column(name = "UPORABNIK_IME")
    private String uporabnikIme;
    @Column(name = "UPORABNIK_PRIIMEK")
    private String uporabnikPriimek;
    @Column(name = "UPORABNIK_SODISCE")
    private String uporabnikSodisce;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdDate;
    @LastModifiedDate
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastModifiedDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ZAHTEVEK_ID", nullable = false)
    Zahtevek zahtevek;
    @Column(name = "NASLOV_BARVA")
    private String naslovBarva = "none";

    public Opomba() {
    }

    public Opomba(String naslov, String vsebina) {
        this.naslov = naslov;
        this.vsebina = vsebina;
    }

    public Zahtevek getZahtevek() {
        return zahtevek;
    }

    public void setZahtevek(Zahtevek zahtevek) {
        this.zahtevek = zahtevek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getVsebina() {
        return vsebina;
    }

    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }

    public String getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(String uporabnik) {
        this.uporabnik = uporabnik;
    }

    public String getUporabnikIme() {
        return uporabnikIme;
    }

    public void setUporabnikIme(String uporabnikIme) {
        this.uporabnikIme = uporabnikIme;
    }

    public String getUporabnikPriimek() {
        return uporabnikPriimek;
    }

    public void setUporabnikPriimek(String uporabnikPriimek) {
        this.uporabnikPriimek = uporabnikPriimek;
    }

    public String getUporabnikSodisce() {
        return uporabnikSodisce;
    }

    public void setUporabnikSodisce(String uporabnikSodisce) {
        this.uporabnikSodisce = uporabnikSodisce;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getNaslovBarva() {
        return naslovBarva;
    }

    public void setNaslovBarva(String naslovBarva) {
        this.naslovBarva = naslovBarva;
    }

    @Override
    public String toString() {
        return "Opomba{" + "id=" + id + ", datum=" + datum + ", naslov=" + naslov + ", vsebina=" + vsebina + ", uporabnik=" + uporabnik + ", uporabnikIme=" + uporabnikIme + ", uporabnikPriimek=" + uporabnikPriimek + ", uporabnikSodisce=" + uporabnikSodisce + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy=" + lastModifiedBy + ", zahtevek=" + zahtevek + '}';
    }
}
