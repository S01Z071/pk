/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.util.List;

/**
 *
 * @author Uporabnik
 */
public class ZgodovinaImetnik {

    private Imetnik imetnik;
    private List<Kartica> kartice;
    private List<Certifikat> certifikati;
    private List<StatusLogImetnik> statusLogImetniks;
    private List<ZahtevekZaPreklic> zahtevekZaPreklics;
    private List<ZahtevekZaKodo> zahtevekZaKodos;

    public Imetnik getImetnik() {
        return imetnik;
    }

    public void setImetnik(Imetnik imetnik) {
        this.imetnik = imetnik;
    }

    public List<Kartica> getKartice() {
        return kartice;
    }

    public void setKartice(List<Kartica> kartice) {
        this.kartice = kartice;
    }

    public List<Certifikat> getCertifikati() {
        return certifikati;
    }

    public void setCertifikati(List<Certifikat> certifikati) {
        this.certifikati = certifikati;
    }

    public List<StatusLogImetnik> getStatusLogImetniks() {
        return statusLogImetniks;
    }

    public void setStatusLogImetniks(List<StatusLogImetnik> statusLogImetniks) {
        this.statusLogImetniks = statusLogImetniks;
    }

    public List<ZahtevekZaPreklic> getZahtevekZaPreklics() {
        return zahtevekZaPreklics;
    }

    public void setZahtevekZaPreklics(List<ZahtevekZaPreklic> zahtevekZaPreklics) {
        this.zahtevekZaPreklics = zahtevekZaPreklics;
    }

    public List<ZahtevekZaKodo> getZahtevekZaKodos() {
        return zahtevekZaKodos;
    }

    public void setZahtevekZaKodos(List<ZahtevekZaKodo> zahtevekZaKodos) {
        this.zahtevekZaKodos = zahtevekZaKodos;
    }
}
