/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
@Table(name = "ZAHTEVEK_TEMP")
public class ZahtevekTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ZAHTEVEK_TEMP")
    @SequenceGenerator(name = "SEQ_ZAHTEVEK_TEMP", sequenceName = "SEQ_ZAHTEVEK_TEMP")
    private Long id;
    @Column(name = "SIFRA_ORGANIZACIJE")
    private String sifraOrganizacije;
    @Column(name = "IME_ORGANIZACIJE")
    private String imeOrganizacije;
    @Column(name = "NASELJE")
    private String naselje;
    @Column(name = "ULICA")
    private String ulica;
    @Column(name = "HISNA_STEVILKA")
    private String hisnaStevilka;
    @Column(name = "POSTNA_STEVILKA")
    private String postnaStevilka;
    @Column(name = "NAZIV_POSTE")
    private String nazivPoste;
    @Embedded
    private KontaktnaOseba kontaktnaOseba;
    @Embedded
    private Predstojnik predstojnik;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setData(Zahtevek zahtevek) {
        this.setHisnaStevilka(zahtevek.getHisnaStevilka());
        this.setImeOrganizacije(zahtevek.getImeOrganizacije());
        this.setKontaktnaOseba(zahtevek.getKontaktnaOseba());
        this.setNaselje(zahtevek.getNaselje());
        this.setNazivPoste(zahtevek.getNazivPoste());
        this.setPostnaStevilka(zahtevek.getPostnaStevilka());
        this.setPredstojnik(zahtevek.getPredstojnik());
        this.setUlica(zahtevek.getUlica());
        this.setSifraOrganizacije(zahtevek.getSifraOrganizacije());
    }

    public void setData(ZahtevekZaPrenos zahtevek) {
        this.setHisnaStevilka(zahtevek.getHisnaStevilka());
        this.setImeOrganizacije(zahtevek.getImeOrganizacije());
        this.setNaselje(zahtevek.getNaselje());
        this.setNazivPoste(zahtevek.getNazivPoste());
        this.setPostnaStevilka(zahtevek.getPostnaStevilka());
        Predstojnik predstojnik1 = new Predstojnik();
        predstojnik1.setImeP(zahtevek.getPredstojnikIme());
        predstojnik1.setPriimekP(zahtevek.getPredstojnikPriimek());
        predstojnik1.seteNaslovP(zahtevek.getPredstojnikENaslov());
        predstojnik1.setFunkcijaP(zahtevek.getPredstojnikFunkcija());
        this.setPredstojnik(predstojnik1);
        this.setUlica(zahtevek.getUlica());
        this.setSifraOrganizacije(zahtevek.getSifraOrganizacije());
    }

    public void setDataT(ZahtevekTemp zahtevek) {
        this.setHisnaStevilka(zahtevek.getHisnaStevilka());
        this.setImeOrganizacije(zahtevek.getImeOrganizacije());
        this.setKontaktnaOseba(zahtevek.getKontaktnaOseba());
        this.setNaselje(zahtevek.getNaselje());
        this.setNazivPoste(zahtevek.getNazivPoste());
        this.setPostnaStevilka(zahtevek.getPostnaStevilka());
        this.setPredstojnik(zahtevek.getPredstojnik());
        this.setUlica(zahtevek.getUlica());
        this.setSifraOrganizacije(zahtevek.getSifraOrganizacije());

    }
}
