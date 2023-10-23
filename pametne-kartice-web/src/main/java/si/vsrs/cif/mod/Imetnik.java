/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import si.vsrs.cif.validators.CaseMode;
import si.vsrs.cif.validators.EMSO;

/**
 *
 * @author uporabnik
 */
@Entity
@Table(name = "IMETNIK")
public class Imetnik implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_IMETNIK")
    @SequenceGenerator(name = "SEQ_IMETNIK", sequenceName = "SEQ_IMETNIK")
    private Long id;
    @Column(name = "KADR_ST")
    private String kadrovskaSt;
    @Size(min = 2, max = 255)
    @Column(name = "IME")
    private String ime;
    @Size(min = 2, max = 255)
    @Column(name = "PRIIMEK")
    private String priimek;
    @NotBlank()
    @Email()
    @Column(name = "ENASLOV")
    private String eNaslov;
    @Digits(fraction = Integer.SIZE, integer = Integer.SIZE)
    @Size(min = 8, max = 8)
    @Column(name = "DAVCNA")
    private String davcna;
    // @Digits(fraction = Integer.SIZE, integer = Integer.SIZE)
    // @Size(min = 13, max = 13)
    @EMSO(CaseMode.EMPTY)
    @Column(name = "EMSO")
    private String emso;
    @Column(name = "POTRDILO")
    private String potrdilo = "Spletno";
    @Size(min = 3, max = 255)
    @Column(name = "GESLO_ZA_PRIKLIC")
    private String gesloZaPreklic;
    @Column(name = "OBRAZLOZITEV")
    private String obrazlozitev = "";
    @Column(name = "POT_NA_PAMETNI_KARTICI")
    private String potNaPametnikKartici = "DA";
    @Column(name = "IMA_PAMETNO_KARTICO")
    private String imaPametnoKartico;
    @Column(name = "IMA_PAMETNO_KARTICO_VSRS")
    private String imaPametnoKarticoVSRS;
    @Column(name = "IMA_CITALEC")
    private String imaCitalec = "DA";
    @Column(name = "IMA_CITALEC_VSRS")
    private String imaCitalecVSRS = "DA";
    @Column(name = "NATISNJENO")
    private Boolean natisnjeno = false;
    @Column(name = "CRTNA_KODA", unique = true)
    private String crtnaKoda;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "STATUS_IMETNIK_ID", nullable = false)
    private StatusImetnik status;
    @Column(name = "IZVOZI")
    private Boolean izvozi = false;
    @Column(name = "NATISNJENO_KONCNO")
    private Boolean natisnjenoKonco = false;
    @DateTimeFormat(pattern = "DD.MM.YYYY")
    @Column(name = "DATUM_PREJEMA_OPREME")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datumPrejemaOpreme;
    @OneToMany(mappedBy = "imetnik", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<SeznamCitalcevSigovca> seznamCitalcevSigovca;
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
    @Transient
    private String imaKarticoVSRSBrezCertf;
    //@NotNull
    @Column(name = "SIFRA_ORGANIZACIJE")
    private String sifraOrganizacije;
    @Column(name = "PRENESEN_IMETNIK")
    private Boolean prenesenImetnik = false;

    public Imetnik() {
    }

    public Imetnik(String ime, String priimek, String eNaslov, String davcna, String emso, String gesloZaPreklic) {
        this.ime = ime;
        this.priimek = priimek;
        this.eNaslov = eNaslov;
        this.davcna = davcna;
        this.emso = emso;
        this.gesloZaPreklic = gesloZaPreklic;
    }

    public Imetnik(Imetnik imetnik) {
        this.davcna = imetnik.getDavcna();
        this.eNaslov = imetnik.getENaslov();
        this.emso = imetnik.getEmso();
        this.gesloZaPreklic = imetnik.getGesloZaPreklic();
        this.imaCitalec = imetnik.getImaCitalec();
        this.imaPametnoKartico = imetnik.getImaPametnoKartico();
        this.ime = imetnik.getIme();
        this.potNaPametnikKartici = imetnik.getPotNaPametnikKartici();
        this.potrdilo = imetnik.getPotrdilo();
        this.priimek = imetnik.getPriimek();
        this.kadrovskaSt = imetnik.getKadrovskaSt();
        this.natisnjeno = imetnik.isNatisnjeno();
        this.imaCitalecVSRS = imetnik.getImaCitalecVSRS();
        this.obrazlozitev = imetnik.getObrazlozitev();
        this.imaPametnoKarticoVSRS = imetnik.getImaPametnoKarticoVSRS();
        this.izvozi = imetnik.getIzvozi();
        this.natisnjenoKonco = imetnik.getNatisnjenoKonco();
        this.seznamCitalcevSigovca = imetnik.getSeznamCitalcevSigovca();
        this.datumPrejemaOpreme = imetnik.getDatumPrejemaOpreme();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getENaslov() {
        return eNaslov;
    }

    public void setENaslov(String eNaslov) {
        this.eNaslov = eNaslov;
    }

    public String getDavcna() {
        return davcna;
    }

    public void setDavcna(String davcna) {
        this.davcna = davcna;
    }

    public String getEmso() {
        return emso;
    }

    public void setEmso(String emso) {
        this.emso = emso;
    }

    public String getPotrdilo() {
        return potrdilo;
    }

    public void setPotrdilo(String potrdilo) {
        this.potrdilo = potrdilo;
    }

    public String getGesloZaPreklic() {
        return gesloZaPreklic;
    }

    public void setGesloZaPreklic(String gesloZaPreklic) {
        this.gesloZaPreklic = gesloZaPreklic;
    }

    public String getObrazlozitev() {
        return obrazlozitev;
    }

    public void setObrazlozitev(String obrazlozitev) {
        this.obrazlozitev = obrazlozitev;
    }

    public String getPotNaPametnikKartici() {
        return potNaPametnikKartici;
    }

    public void setPotNaPametnikKartici(String potNaPametnikKartici) {
        this.potNaPametnikKartici = potNaPametnikKartici;
    }

    public String getImaPametnoKartico() {
        return imaPametnoKartico;
    }

    public void setImaPametnoKartico(String imaPametnoKartico) {
        this.imaPametnoKartico = imaPametnoKartico;
    }

    public String getImaPametnoKarticoVSRS() {
        return imaPametnoKarticoVSRS;
    }

    public void setImaPametnoKarticoVSRS(String imaPametnoKarticoVSRS) {
        this.imaPametnoKarticoVSRS = imaPametnoKarticoVSRS;
    }

    public String getImaCitalec() {
        return imaCitalec;
    }

    public void setImaCitalec(String imaCitalec) {
        this.imaCitalec = imaCitalec;
    }

    public String getKadrovskaSt() {
        return kadrovskaSt;
    }

    public void setKadrovskaSt(String kadrovskaSt) {
        this.kadrovskaSt = kadrovskaSt;
    }

    public Boolean isNatisnjeno() {
        return natisnjeno;
    }

    public void setNatisnjeno(Boolean natisnjeno) {
        this.natisnjeno = natisnjeno;
    }

    public String getCrtnaKoda() {
        return crtnaKoda;
    }

    public void setCrtnaKoda(String crtnaKoda) {
        this.crtnaKoda = crtnaKoda;
    }

    public StatusImetnik getStatus() {
        return status;
    }

    public void setStatus(StatusImetnik status) {
        this.status = status;
    }

    public Boolean getIzvozi() {
        return izvozi;
    }

    public void setIzvozi(Boolean izvozi) {
        this.izvozi = izvozi;
    }

    public Boolean getNatisnjenoKonco() {
        return natisnjenoKonco;
    }

    public void setNatisnjenoKonco(Boolean natisnjenoKonco) {
        this.natisnjenoKonco = natisnjenoKonco;
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

    public String getImaCitalecVSRS() {
        return imaCitalecVSRS;
    }

    public void setImaCitalecVSRS(String imaCitalecVSRS) {
        this.imaCitalecVSRS = imaCitalecVSRS;
    }

    public Date getDatumPrejemaOpreme() {
        return datumPrejemaOpreme;
    }

    public void setDatumPrejemaOpreme(Date datumPrejemaOpreme) {
        this.datumPrejemaOpreme = datumPrejemaOpreme;
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

    public Set<SeznamCitalcevSigovca> getSeznamCitalcevSigovca() {
        return seznamCitalcevSigovca;
    }

    public void setSeznamCitalcevSigovca(Set<SeznamCitalcevSigovca> seznamCitalcevSigovca) {
        this.seznamCitalcevSigovca = seznamCitalcevSigovca;
    }

    public String getImaKarticoVSRSBrezCertf() {
        return imaKarticoVSRSBrezCertf;
    }

    public void setImaKarticoVSRSBrezCertf(String imaKarticoVSRSBrezCertf) {
        this.imaKarticoVSRSBrezCertf = imaKarticoVSRSBrezCertf;
    }

    public String getSifraOrganizacije() {
        return sifraOrganizacije;
    }

    public void setSifraOrganizacije(String sifraOrganizacije) {
        this.sifraOrganizacije = sifraOrganizacije;
    }

    public Boolean getPrenesenImetnik() {
        return prenesenImetnik;
    }

    public void setPrenesenImetnik(Boolean prenesenImetnik) {
        this.prenesenImetnik = prenesenImetnik;
    }

    /*public void update(Imetnik imetnik) {
     this.davcna = imetnik.getDavcna();
     this.eNaslov = imetnik.getENaslov();
     this.emso = imetnik.getEmso();
     this.gesloZaPreklic = imetnik.getGesloZaPreklic();
     this.imaCitalec = imetnik.getImaCitalec();
     this.imaPametnoKartico = imetnik.getImaPametnoKartico();
     this.ime = imetnik.getIme();
     this.potNaPametnikKartici = imetnik.getPotNaPametnikKartici();
     this.potrdilo = imetnik.getPotrdilo();
     this.priimek = imetnik.getPriimek();
     this.kadrovskaSt = imetnik.getKadrovskaSt();
     this.natisnjeno = imetnik.isNatisnjeno();
     this.imaCitalecVSRS = imetnik.getImaCitalecVSRS();
     this.obrazlozitev = imetnik.getObrazlozitev();
     //this.imaPametnoKarticoVSRS = imetnik.getImaPametnoKarticoVSRS();
     }*/
    @Override
    public String toString() {
        return "Imetnik{" + "id=" + id + ", kadrovskaSt=" + kadrovskaSt + ", ime=" + ime + ", priimek=" + priimek + ", eNaslov=" + eNaslov + ", davcna=" + davcna + ", emso=" + emso + ", potrdilo=" + potrdilo + ", gesloZaPreklic=" + gesloZaPreklic + ", obrazlozitev=" + obrazlozitev + ", potNaPametnikKartici=" + potNaPametnikKartici + ", imaPametnoKartico=" + imaPametnoKartico + ", imaPametnoKarticoVSRS=" + imaPametnoKarticoVSRS + ", imaCitalec=" + imaCitalec + ", imaCitalecVSRS=" + imaCitalecVSRS + ", natisnjeno=" + natisnjeno + ", crtnaKoda=" + crtnaKoda + ", status=" + status + ", izvozi=" + izvozi + ", natisnjenoKonco=" + natisnjenoKonco + ", datumPrejemaOpreme=" + datumPrejemaOpreme + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy=" + lastModifiedBy + '}';
    }
}
