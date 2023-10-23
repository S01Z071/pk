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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import si.vsrs.cif.validators.CaseMode;
import si.vsrs.cif.validators.EMSO;

/**
 *
 * @author Uporabnik
 */
@Entity
@Table(name = "ZAHTEVEK_ZA_PRENOS")
public class ZahtevekZaPrenos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ZAHTEVEK_ZA_PRENOS")
    @SequenceGenerator(name = "SEQ_ZAHTEVEK_ZA_PRENOS", sequenceName = "SEQ_ZAHTEVEK_ZA_PRENOS")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "STATUS_ID", nullable = false)
    @Fetch(FetchMode.SELECT)
    private Status status;
    @Column(name = "SIFRA_ORGANIZACIJE")
    private String sifraOrganizacije;
    @Size(min = 3, max = 255)
    @Column(name = "IME_ORGANIZACIJE")
    private String imeOrganizacije;
    @Size(min = 3, max = 255)
    @Column(name = "NASELJE")
    private String naselje;
    @Size(min = 3, max = 255)
    @Column(name = "ULICA")
    private String ulica;
    @NotBlank()
    @Column(name = "HISNA_STEVILKA")
    private String hisnaStevilka;
    @NotBlank()
    @Digits(fraction = Integer.SIZE, integer = Integer.SIZE)
    @Column(name = "POSTNA_STEVILKA")
    private String postnaStevilka;
    @Size(min = 3, max = 255)
    @Column(name = "NAZIV_POSTE")
    private String nazivPoste;
    @Size(min = 2, max = 255)
    @Column(name = "PREDSTOJNIK_IME")
    private String predstojnikIme;
    @Size(min = 2, max = 255)
    @Column(name = "PREDSTOJNIK_PRIIMEK")
    private String predstojnikPriimek;
    @Size(min = 3, max = 255)
    @Column(name = "PREDSTOJNIK_FUNKCIJA")
    private String predstojnikFunkcija;
    @Size(min = 3, max = 255)
    @Column(name = "PREDSTOJNIK_ENASLOV")
    private String predstojnikENaslov;
    @Size(min = 2, max = 255)
    @Column(name = "IMETNIK_IME")
    private String imetnikIme;
    @Size(min = 2, max = 255)
    @Column(name = "IMETNIK_PRIIMEK")
    private String imetnikPriimek;
    @EMSO(CaseMode.EMPTY)
    @Column(name = "IMETNIK_EMSO")
    private String imetnikEMSO;
    @NotBlank()
    @Email()
    @Column(name = "IMETNIK_ENASLOV")
    private String imetnikEnaslov;
    @Column(name = "TIP_POTRDILA")
    private String tipPotrdila = "Spletno";
    @Size(min = 0, max = 255)
    @Column(name = "SERIJSKA_STEVILKA")
    private String serijskaStevilka;
    @Column(name = "OBRAZLOZITEV_PRENOSA")
    private String obrazlozitevPrenosa;
    @Column(name = "PRENOS_IZDAL")
    private String prenosIzdal = "Predstojnik";
    //@NotNull()
    @Column(name = "ZAHTEVEK_ID")
    private Long zahtevekID;
    @Column(name = "ZAHTEVEK_PRENOS_ID")
    private Long zahtevekPrenosID;
    @NotNull()
    @Column(name = "IMETNIK_PRENESEN_ID")
    private Long imetnikID;
    @NotNull()
    @Column(name = "CERTIFIKAT_ID")
    private Long certifikatID;
    @Column(name = "KARTICA_ID")
    private Long karticaID;
    @Column(name = "CRTNA_KODA", unique = true)
    private String crtnaKoda;
    @Column(name = "NATISNJENO")
    private Boolean natisnjeno = false;
    // @OrderBy(value = "id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "NOV_IMETNIK_ID")
    private Imetnik imetnik;
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

    public ZahtevekZaPrenos(Certifikat certifikat, Imetnik imetnik, ZahtevekTemp zahtevekTemp, Long zahtevekId, Long zahtevekPrenosId) {

        this.imetnikEMSO = imetnik.getEmso();
        this.imetnikEnaslov = imetnik.getENaslov();
        this.imetnikID = imetnik.getId();

        this.imetnikIme = imetnik.getIme();
        this.imetnikPriimek = imetnik.getPriimek();
        this.certifikatID = certifikat.getId();
        if (certifikat.getKartica() != null) {
            this.karticaID = certifikat.getKartica().getId();
        }
        this.imeOrganizacije = zahtevekTemp.getImeOrganizacije();
        this.naselje = zahtevekTemp.getNaselje();
        this.nazivPoste = zahtevekTemp.getNazivPoste();
        this.postnaStevilka = zahtevekTemp.getPostnaStevilka();
        this.predstojnikIme = zahtevekTemp.getPredstojnik().getImeP();
        this.predstojnikPriimek = zahtevekTemp.getPredstojnik().getPriimekP();
        this.predstojnikFunkcija = zahtevekTemp.getPredstojnik().getFunkcijaP();
        this.predstojnikENaslov = zahtevekTemp.getPredstojnik().geteNaslovP();
        this.sifraOrganizacije = zahtevekTemp.getSifraOrganizacije();
        this.ulica = zahtevekTemp.getUlica();
        this.hisnaStevilka = zahtevekTemp.getHisnaStevilka();

        this.serijskaStevilka = certifikat.getSerijskaStevilka();
        this.zahtevekID = zahtevekId;
        this.zahtevekPrenosID = zahtevekPrenosId;
    }

    public ZahtevekZaPrenos() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSifraOrganizacije() {
        return sifraOrganizacije;
    }

    public void setSifraOrganizacije(String sifraOrganizacije) {
        this.sifraOrganizacije = sifraOrganizacije;
    }

    public String getImeOrganizacije() {
        return imeOrganizacije;
    }

    public void setImeOrganizacije(String imeOrganizacije) {
        this.imeOrganizacije = imeOrganizacije;
    }

    public String getNaselje() {
        return naselje;
    }

    public void setNaselje(String naselje) {
        this.naselje = naselje;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getHisnaStevilka() {
        return hisnaStevilka;
    }

    public void setHisnaStevilka(String hisnaStevilka) {
        this.hisnaStevilka = hisnaStevilka;
    }

    public String getPostnaStevilka() {
        return postnaStevilka;
    }

    public String getPrenosIzdal() {
        return prenosIzdal;
    }

    public void setPrenosIzdal(String prenosIzdal) {
        this.prenosIzdal = prenosIzdal;
    }

    public void setPostnaStevilka(String postnaStevilka) {
        this.postnaStevilka = postnaStevilka;
    }

    public String getNazivPoste() {
        return nazivPoste;
    }

    public void setNazivPoste(String nazivPoste) {
        this.nazivPoste = nazivPoste;
    }

    public String getPredstojnikIme() {
        return predstojnikIme;
    }

    public void setPredstojnikIme(String predstojnikIme) {
        this.predstojnikIme = predstojnikIme;
    }

    public String getPredstojnikPriimek() {
        return predstojnikPriimek;
    }

    public void setPredstojnikPriimek(String predstojnikPriimek) {
        this.predstojnikPriimek = predstojnikPriimek;
    }

    public String getPredstojnikFunkcija() {
        return predstojnikFunkcija;
    }

    public void setPredstojnikFunkcija(String predstojnikFunkcija) {
        this.predstojnikFunkcija = predstojnikFunkcija;
    }

    public String getPredstojnikENaslov() {
        return predstojnikENaslov;
    }

    public void setPredstojnikENaslov(String predstojnikENaslov) {
        this.predstojnikENaslov = predstojnikENaslov;
    }

    public String getImetnikIme() {
        return imetnikIme;
    }

    public void setImetnikIme(String imetnikIme) {
        this.imetnikIme = imetnikIme;
    }

    public String getImetnikPriimek() {
        return imetnikPriimek;
    }

    public void setImetnikPriimek(String imetnikPriimek) {
        this.imetnikPriimek = imetnikPriimek;
    }

    public String getImetnikEMSO() {
        return imetnikEMSO;
    }

    public void setImetnikEMSO(String imetnikEMSO) {
        this.imetnikEMSO = imetnikEMSO;
    }

    public String getImetnikEnaslov() {
        return imetnikEnaslov;
    }

    public void setImetnikEnaslov(String imetnikEnaslov) {
        this.imetnikEnaslov = imetnikEnaslov;
    }

    public String getTipPotrdila() {
        return tipPotrdila;
    }

    public void setTipPotrdila(String tipPotrdila) {
        this.tipPotrdila = tipPotrdila;
    }

    public String getSerijskaStevilka() {
        return serijskaStevilka;
    }

    public void setSerijskaStevilka(String serijskaStevilka) {
        this.serijskaStevilka = serijskaStevilka;
    }

    public String getObrazlozitevPrenosa() {
        return obrazlozitevPrenosa;
    }

    public void setObrazlozitevPrenosa(String obrazlozitevPrenosa) {
        this.obrazlozitevPrenosa = obrazlozitevPrenosa;
    }

    public Long getZahtevekID() {
        return zahtevekID;
    }

    public void setZahtevekID(Long zahtevekID) {
        this.zahtevekID = zahtevekID;
    }

    public Long getCertifikatID() {
        return certifikatID;
    }

    public void setCertifikatID(Long certifikatID) {
        this.certifikatID = certifikatID;
    }

    public Long getKarticaID() {
        return karticaID;
    }

    public void setKarticaID(Long karticaID) {
        this.karticaID = karticaID;
    }

    public String getCrtnaKoda() {
        return crtnaKoda;
    }

    public void setCrtnaKoda(String crtnaKoda) {
        this.crtnaKoda = crtnaKoda;
    }

    public Boolean getNatisnjeno() {
        return natisnjeno;
    }

    public void setNatisnjeno(Boolean natisnjeno) {
        this.natisnjeno = natisnjeno;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getImetnikID() {
        return imetnikID;
    }

    public void setImetnikID(Long imetnikID) {
        this.imetnikID = imetnikID;
    }

    public Imetnik getImetnik() {
        return imetnik;
    }

    public void setImetnik(Imetnik imetnik) {
        this.imetnik = imetnik;
    }

    public Long getZahtevekPrenosID() {
        return zahtevekPrenosID;
    }

    public void setZahtevekPrenosID(Long zahtevekPrenosID) {
        this.zahtevekPrenosID = zahtevekPrenosID;
    }
}
