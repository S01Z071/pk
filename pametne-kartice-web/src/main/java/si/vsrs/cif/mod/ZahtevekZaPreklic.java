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
@Table(name = "ZAHTEVEK_ZA_PREKLIC")
public class ZahtevekZaPreklic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ZAHTEVEK_ZA_PREKLIC")
    @SequenceGenerator(name = "SEQ_ZAHTEVEK_ZA_PREKLIC", sequenceName = "SEQ_ZAHTEVEK_ZA_PREKLIC")
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
    @Column(name = "PODATKI_PREKLIC_POSLATI_P")
    private String podatkiPreklicPoslatiP = "E-naslov";
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
    @Column(name = "RAZLOG_PREKLICA")
    private String razlogPreklica = "Prenehanje uporabe";
    @Column(name = "OBRAZLOZITEV_PREKLICA")
    private String obrazlozitevPreklica;
    @Column(name = "PODATKI_PREKLIC_POSLATI_I")
    private String podatkiPreklicPoslatiI = "E-naslov";
    @Column(name = "PREKLIC_IZDAL")
    private String preklicIzdal = "Predstojnik";
    @Column(name = "OPREMA")
    private String oprema = "Opremo vračam";
    @Column(name = "OBRAZLOZITEV_OPREME")
    private String obrazlozitevOprema;
    @NotNull()
    @Column(name = "ZAHTEVEK_ID")
    private Long zahtevekID;
    @NotNull()
    @Column(name = "IMETNIK_ID")
    private Long imetnikID;
    @NotNull()
    @Column(name = "CERTIFIKAT_ID")
    private Long certifikatID;
    @Column(name = "CRTNA_KODA", unique = true)
    private String crtnaKoda;
    @Column(name = "NATISNJENO")
    private Boolean natisnjeno = false;
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

    public ZahtevekZaPreklic(Certifikat certifikat, Imetnik imetnik, ZahtevekTemp zahtevekTemp, Long zahtevekId) {
        this.imeOrganizacije = zahtevekTemp.getImeOrganizacije();
        // this.imetnikEMSO = imetnik.getEmso();
        this.imetnikEnaslov = imetnik.getENaslov();
        this.imetnikID = imetnik.getId();
        this.imetnikIme = imetnik.getIme();
        this.imetnikPriimek = imetnik.getPriimek();
        this.certifikatID = certifikat.getId();
        this.naselje = zahtevekTemp.getNaselje();
        this.nazivPoste = zahtevekTemp.getNazivPoste();
        this.postnaStevilka = zahtevekTemp.getPostnaStevilka();
        this.predstojnikIme = zahtevekTemp.getPredstojnik().getImeP();
        this.predstojnikPriimek = zahtevekTemp.getPredstojnik().getPriimekP();
        this.predstojnikFunkcija = zahtevekTemp.getPredstojnik().getFunkcijaP();
        this.predstojnikENaslov = zahtevekTemp.getPredstojnik().geteNaslovP();
        this.serijskaStevilka = certifikat.getSerijskaStevilka();
        this.sifraOrganizacije = zahtevekTemp.getSifraOrganizacije();
        this.ulica = zahtevekTemp.getUlica();
        this.hisnaStevilka = zahtevekTemp.getHisnaStevilka();
        this.zahtevekID = zahtevekId;//zahtevek.getId();
    }

    public ZahtevekZaPreklic() {
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

    public String getPodatkiPreklicPoslatiP() {
        return podatkiPreklicPoslatiP;
    }

    public void setPodatkiPreklicPoslatiP(String podatkiPreklicPoslatiP) {
        this.podatkiPreklicPoslatiP = podatkiPreklicPoslatiP;
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

    public Long getZahtevekID() {
        return zahtevekID;
    }

    public void setZahtevekID(Long zahtevekID) {
        this.zahtevekID = zahtevekID;
    }

    public Long getImetnikID() {
        return imetnikID;
    }

    public void setImetnikID(Long imetnikID) {
        this.imetnikID = imetnikID;
    }

    public Long getCertifikatID() {
        return certifikatID;
    }

    public void setCertifikatID(Long certifikatID) {
        this.certifikatID = certifikatID;
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

    public String getRazlogPreklica() {
        return razlogPreklica;
    }

    public void setRazlogPreklica(String razlogPreklica) {
        this.razlogPreklica = razlogPreklica;
    }

    public String getObrazlozitevPreklica() {
        return obrazlozitevPreklica;
    }

    public void setObrazlozitevPreklica(String obrazlozitevPreklica) {
        this.obrazlozitevPreklica = obrazlozitevPreklica;
    }

    public String getPodatkiPreklicPoslatiI() {
        return podatkiPreklicPoslatiI;
    }

    public void setPodatkiPreklicPoslatiI(String podatkiPreklicPoslatiI) {
        this.podatkiPreklicPoslatiI = podatkiPreklicPoslatiI;
    }

    public String getPreklicIzdal() {
        return preklicIzdal;
    }

    public void setPreklicIzdal(String preklicIzdal) {
        this.preklicIzdal = preklicIzdal;
    }

    public String getOprema() {
        return oprema;
    }

    public void setOprema(String oprema) {
        this.oprema = oprema;
    }

    public String getObrazlozitevOprema() {
        return obrazlozitevOprema;
    }

    public void setObrazlozitevOprema(String obrazlozitevOprema) {
        this.obrazlozitevOprema = obrazlozitevOprema;
    }
}
