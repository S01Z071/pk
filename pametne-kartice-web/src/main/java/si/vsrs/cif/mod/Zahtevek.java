/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import si.sodisce.skupni.lists.co.Court;

/**
 *
 * @author uporabnik
 */
@Entity
@Table(name = "ZAHTEVEK")
public class Zahtevek implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ZAHTEVEK")
    @SequenceGenerator(name = "SEQ_ZAHTEVEK", sequenceName = "SEQ_ZAHTEVEK")
    private Long id;
    //@Column(name = "status")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "STATUS_ID", nullable = false)
    @Fetch(FetchMode.SELECT) //
    private Status status;
    @Column(name = "SIFRA_ORGANIZACIJE")
    private String sifraOrganizacije;
    @Size(min = 3, max = 255)
    //@Pattern(regexp = "[a]+")
    @Column(name = "IME_ORGANIZACIJE")
    private String imeOrganizacije;
    @Size(min = 3, max = 255)
    @Column(name = "NASELJE")
    private String naselje;
    @Size(min = 3, max = 255)
    @Column(name = "ULICA")
    private String ulica;
    @NotBlank()
    //@Digits(fraction = Integer.SIZE, integer = Integer.SIZE, message = "*Samo številke.")
    @Column(name = "HISNA_STEVILKA")
    private String hisnaStevilka;
    @NotBlank()
    @Digits(fraction = Integer.SIZE, integer = Integer.SIZE)
    @Column(name = "POSTNA_STEVILKA")
    private String postnaStevilka;
    @Size(min = 3, max = 255)
    @Column(name = "NAZIV_POSTE")
    private String nazivPoste;
    @Valid
    @Embedded
    private KontaktnaOseba kontaktnaOseba;
    @Valid
    @Embedded
    private Predstojnik predstojnik;
    @OrderBy(value = "id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ZAHTEVEK_ID")//,nullable = false)
    //@OneToMany(mappedBy = "zahtevek", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private List<Imetnik> imetniki;
    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@Fetch(FetchMode.SELECT) //
    //@JoinColumn(name = "ZAHTEVEK_ID")
    @OneToMany(mappedBy = "zahtevek", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<Opomba> opombe;
    @Column(name = "NATISNJENO")
    private Boolean natisnjeno = false;
    @Column(name = "CRTNA_KODA", unique = true)
    private String crtnaKoda;
    @Column(name = "IZVOZI")
    private Boolean izvozi = false;
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

    public Zahtevek() {
    }

    public Zahtevek(String sifraOrganizacije, String imeOrganizacije, String naselje, String ulica, String hisnaStevilka, String postnaStevilka, String nazivPoste, KontaktnaOseba kontaktnaOseba, Predstojnik predstojnik) {
        this.sifraOrganizacije = sifraOrganizacije;
        this.imeOrganizacije = imeOrganizacije;
        this.naselje = naselje;
        this.ulica = ulica;
        this.hisnaStevilka = hisnaStevilka;
        this.postnaStevilka = postnaStevilka;
        this.nazivPoste = nazivPoste;
        this.kontaktnaOseba = kontaktnaOseba;
        this.predstojnik = predstojnik;
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

    public KontaktnaOseba getKontaktnaOseba() {
        return kontaktnaOseba;
    }

    public void setKontaktnaOseba(KontaktnaOseba kontaktnaOseba) {
        this.kontaktnaOseba = kontaktnaOseba;
    }

    public Predstojnik getPredstojnik() {
        return predstojnik;
    }

    public void setPredstojnik(Predstojnik predstojnik) {
        this.predstojnik = predstojnik;
    }

    public List<Imetnik> getImetniki() {
        return this.imetniki;
    }

    public void setImetniki(List<Imetnik> imetniki) {
        this.imetniki = imetniki;
    }

    public Set<Opomba> getOpombe() {
        return opombe;
    }

    public void setOpombe(Set<Opomba> opombe) {

        this.opombe = opombe;
    }

    public boolean isNatisnjeno() {
        return natisnjeno;
    }

    public void setNatisnjeno(boolean natisnjeno) {
        this.natisnjeno = natisnjeno;
    }

    public String getCrtnaKoda() {
        return crtnaKoda;
    }

    public void setCrtnaKoda(String crtnaKoda) {
        this.crtnaKoda = crtnaKoda;
    }

    public boolean isIzvozi() {
        return izvozi;
    }

    public void setIzvozi(boolean izvozi) {
        this.izvozi = izvozi;
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

    //Nastavi vsem imetnikov nov status. Ce imajo status "stat" ne nastavi
    public void setImetnikStatus(StatusImetnik status, String stat) {
        List<Imetnik> imetniki = this.getImetniki();
        for (int i = 0; i < imetniki.size(); i++) {
            if (imetniki.get(i).getStatus().getSifraSIm().compareTo(stat) != 0) {
                imetniki.get(i).setStatus(status);
            }
        }
        this.setImetniki(imetniki);
    }

    public boolean checkForImetnik(long idImetnik) {
        for (int i = 0; i < this.getImetniki().size(); i++) {
            if (this.getImetniki().get(i).getId() == idImetnik) {
                return true;
            }
        }
        return false;
    }

    public void update(Zahtevek zahtevek) {
        this.hisnaStevilka = zahtevek.getHisnaStevilka();
        this.imeOrganizacije = zahtevek.getImeOrganizacije();
        this.kontaktnaOseba = zahtevek.getKontaktnaOseba();
        this.naselje = zahtevek.getNaselje();
        this.nazivPoste = zahtevek.getNazivPoste();
        this.postnaStevilka = zahtevek.getPostnaStevilka();
        this.predstojnik = zahtevek.getPredstojnik();
        this.status = zahtevek.getStatus();
        this.ulica = zahtevek.getUlica();
        List<Imetnik> imetniki1 = zahtevek.getImetniki();
        this.imetniki = imetniki1;
        this.sifraOrganizacije = zahtevek.getSifraOrganizacije();
        Set<Opomba> opombe1 = zahtevek.getOpombe();
        this.opombe = opombe1;
        this.setNatisnjeno(zahtevek.isNatisnjeno());
    }

    //Nastavi zahtevek iz si.​sodisce.​skupni.​lists.​co
    public void setZahtevekFromSodisce(Court court) {
        this.setImeOrganizacije(court.getName());//sodisce.getNAZIV());
        this.setNaselje(court.getPostName());//sodisce.getKRAJ());
        this.setNazivPoste(court.getPostName());//sodisce.getKRAJ());
        this.setPostnaStevilka(court.getPostCode());//sodisce.getPOSTSTEV());
        String[] naslov = court.getAddress().split(" ");//sodisce.getNASLOV().split(" ");
        this.setHisnaStevilka(naslov[naslov.length - 1]);
        String ulica = "";
        for (int i = 0; i < naslov.length - 1; i++) {
            ulica += naslov[i] + " ";
        }
        this.setUlica(ulica);
        this.setSifraOrganizacije(court.getCode());//sodisce.getSIF());

    }

    //Nastavi zahtevek iz temp - samo polja, ki so prazna
    public void setZahtevekFromTemp(ZahtevekTemp zahtevekTemp) {
        //max = (a > b) ? a : b;
        this.setHisnaStevilka((this.hisnaStevilka.isEmpty()) ? zahtevekTemp.getHisnaStevilka() : this.getHisnaStevilka());
        this.setImeOrganizacije((this.imeOrganizacije.isEmpty()) ? zahtevekTemp.getImeOrganizacije() : this.getImeOrganizacije());
        this.setKontaktnaOseba((this.getKontaktnaOseba() == null) ? zahtevekTemp.getKontaktnaOseba() : this.kontaktnaOseba);
        this.setNaselje((this.naselje.isEmpty()) ? zahtevekTemp.getNaselje() : this.getNaselje());
        this.setNazivPoste((this.nazivPoste.isEmpty()) ? zahtevekTemp.getNazivPoste() : this.getNazivPoste());
        this.setPostnaStevilka((this.postnaStevilka.isEmpty()) ? zahtevekTemp.getPostnaStevilka() : this.getPostnaStevilka());
        this.setPredstojnik((this.getPredstojnik() == null) ? zahtevekTemp.getPredstojnik() : this.getPredstojnik());
        this.setSifraOrganizacije((this.sifraOrganizacije.isEmpty()) ? zahtevekTemp.getSifraOrganizacije() : this.getSifraOrganizacije());
        this.setUlica((this.ulica.isEmpty()) ? zahtevekTemp.getUlica() : this.getUlica());
    }

    @Override
    public String toString() {
        return id + ";" + sifraOrganizacije + ";" + imeOrganizacije + ";" + naselje + ";" + ulica + ";" + hisnaStevilka + ";" + postnaStevilka + ";" + nazivPoste + ";" + kontaktnaOseba.getImeK() + ";" + predstojnik.getImeP() + ";" + crtnaKoda + ";";
    }

    public Imetnik getImetnikById(Long idI) {
        List<Imetnik> imetniki = this.getImetniki();
        for (int i = 0; i < imetniki.size(); i++) {
            if (imetniki.get(i).getId() == idI) {
                return imetniki.get(i);
            }
        }
        return null;
    }
}
