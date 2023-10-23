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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author uporabnik
 */
@Entity
@Table(name = "CERTIFIKATI")
public class Certifikat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_KARTICA")
    @SequenceGenerator(name = "SEQ_KARTICA", sequenceName = "SEQ_KARTICA")
    private Long id;
    @Digits(fraction = Integer.SIZE, integer = Integer.SIZE)
    @Size(min = 8, max = 8)
    @Column(name = "DAVCNA_ST")
    private String davcnaSt;
    @Column(name = "IME_IN_PRIIMEK")
    private String imeInPriimek;
    @Column(name = "IME_ORGANIZACIJE")
    private String imeOrganizacije;
    @Column(name = "NASLOV")
    private String naslov;
    @Column(name = "POSTA")
    private String posta;
    @Column(name = "KRAJ")
    private String kraj;
    @Column(name = "DRZAVA")
    private String drzava;
    @Column(name = "REFERENCNA_ST")
    private String referencnaSt;
    @Column(name = "AVTORIZACIJSKA_ST")
    private String avtorizacijskaSt;
    @Column(name = "STREZNIK")
    private String streznik;
    @Column(name = "TIP_POTRDILA")
    private String tipPotrdila;
    @Column(name = "SERIJSKA_ST")
    private String serijskaStevilka;
    @Column(name = "ENASLOV")
    private String eNaslov;
    @DateTimeFormat(pattern = "DD.MM.YYYY")
    @Column(name = "DATUM_ODOBRITVE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datumOdobritve;
    @DateTimeFormat(pattern = "DD.MM.YYYY")
    @Column(name = "DATUM_PREVZEMA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datumPrevzema;
    @DateTimeFormat(pattern = "DD.MM.YYYY")
    @Column(name = "DATUM_POTEKA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datumPoteka;
    @DateTimeFormat(pattern = "DD.MM.YYYY")
    @Column(name = "DATUM_PREKLICA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datumPreklica;
    @Column(name = "SPLOSNI_NAZIV_DNS")
    private String splosniNazivDNS;
    @Column(name = "SERIAL_NUMBER_X")
    private String serialNumberX;
    @Column(name = "TIP_UPORABNIKA")
    private String tipUporabnika;
    @Column(name = "STANJE")
    private String stanje;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "STATUS_CERTIFIKAT_ID", nullable = false)
    // @Fetch(FetchMode.SELECT)
    private StatusCertifikat status;
    @OrderBy(value = "id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "IMETNIK_ID")
    private Imetnik imetnik;
    @OrderBy(value = "id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "KARTICA_ID")
    private Kartica kartica;
    @Column(name = "SIFRA_SODISCA")
    private String sifraSodisca;
    @OrderBy(value = "id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "JOBLOG_ID")
    private JobLog jobLog;
    @Column(name = "IZVOZ_PIN3")
    private Boolean izvozPin3 = false;
    // @DateTimeFormat(pattern = "DD.MM.YYYY")
    @Column(name = "DATUM_IZVOZA")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date datumIzvoza;

    public Certifikat(String davcnaSt, String serijskaStevilka, String eNaslov, StatusCertifikat status) {
        this.davcnaSt = davcnaSt;
        this.serijskaStevilka = serijskaStevilka;
        this.eNaslov = eNaslov;
        this.status = status;
    }

    public Certifikat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDavcnaSt() {
        return davcnaSt;
    }

    public void setDavcnaSt(String davcnaSt) {
        this.davcnaSt = davcnaSt;
    }

    public String getImeInPriimek() {
        return imeInPriimek;
    }

    public void setImeInPriimek(String imeInPriimek) {
        this.imeInPriimek = imeInPriimek;
    }

    public String getImeOrganizacije() {
        return imeOrganizacije;
    }

    public void setImeOrganizacije(String imeOrganizacije) {
        this.imeOrganizacije = imeOrganizacije;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getPosta() {
        return posta;
    }

    public void setPosta(String posta) {
        this.posta = posta;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getReferencnaSt() {
        return referencnaSt;
    }

    public void setReferencnaSt(String referencnaSt) {
        this.referencnaSt = referencnaSt;
    }

    public String getAvtorizacijskaSt() {
        return avtorizacijskaSt;
    }

    public void setAvtorizacijskaSt(String avtorizacijskaSt) {
        this.avtorizacijskaSt = avtorizacijskaSt;
    }

    public String getStreznik() {
        return streznik;
    }

    public void setStreznik(String streznik) {
        this.streznik = streznik;
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

    public Imetnik getImetnik() {
        return imetnik;
    }

    public String getSerialNumberX() {
        return serialNumberX;
    }

    public void setSerialNumberX(String serialNumberX) {
        this.serialNumberX = serialNumberX;
    }

    public void setImetnik(Imetnik imetnik) {
        this.imetnik = imetnik;
    }

    public Kartica getKartica() {
        return kartica;
    }

    public void setKartica(Kartica kartica) {
        this.kartica = kartica;
    }

    public String getENaslov() {
        return eNaslov;
    }

    public void setENaslov(String eNaslov) {
        this.eNaslov = eNaslov;
    }

    public Date getDatumOdobritve() {
        return datumOdobritve;
    }

    public void setDatumOdobritve(Date datumOdobritve) {
        this.datumOdobritve = datumOdobritve;
    }

    public Date getDatumPrevzema() {
        return datumPrevzema;
    }

    public void setDatumPrevzema(Date datumPrevzema) {
        this.datumPrevzema = datumPrevzema;
    }

    public Date getDatumPoteka() {
        return datumPoteka;
    }

    public void setDatumPoteka(Date datumPoteka) {
        this.datumPoteka = datumPoteka;
    }

    public Date getDatumPreklica() {
        return datumPreklica;
    }

    public void setDatumPreklica(Date datumPreklica) {
        this.datumPreklica = datumPreklica;
    }

    public String getSplosniNazivDNS() {
        return splosniNazivDNS;
    }

    public void setSplosniNazivDNS(String splosniNazivDNS) {
        this.splosniNazivDNS = splosniNazivDNS;
    }

    public String getTipUporabnika() {
        return tipUporabnika;
    }

    public void setTipUporabnika(String tipUporabnika) {
        this.tipUporabnika = tipUporabnika;
    }

    public StatusCertifikat getStatus() {
        return status;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    public void setStatus(StatusCertifikat status) {
        this.status = status;
    }

    public String getSifraSodisca() {
        return sifraSodisca;
    }

    public void setSifraSodisca(String sifraSodisca) {
        this.sifraSodisca = sifraSodisca;
    }

    public JobLog getJobLog() {
        return jobLog;
    }

    public void setJobLog(JobLog jobLog) {
        this.jobLog = jobLog;
    }

    public Boolean getIzvozPin3() {
        return izvozPin3;
    }

    public void setIzvozPin3(Boolean izvozPin3) {
        this.izvozPin3 = izvozPin3;
    }

    public Date getDatumIzvoza() {
        return datumIzvoza;
    }

    public void setDatumIzvoza(Date datumIzvoza) {
        this.datumIzvoza = datumIzvoza;
    }

  
}
