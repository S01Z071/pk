/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

/**
 *
 * @author uporabnik
 */
public class IskalniPogoji {

    private Boolean zahtevekCrtnaKoda = false;
    private Boolean zahtevekSodisce = false;
    private Boolean imetnikCrtnaKoda = false;
    private Boolean imetnikSodisce = false;

    public Boolean getZahtevekCrtnaKoda() {
        return zahtevekCrtnaKoda;
    }

    public void setZahtevekCrtnaKoda(Boolean zahtevekCrtnaKoda) {
        this.zahtevekCrtnaKoda = zahtevekCrtnaKoda;
    }

    public Boolean getZahtevekSodisce() {
        return zahtevekSodisce;
    }

    public void setZahtevekSodisce(Boolean zahtevekSodisce) {
        this.zahtevekSodisce = zahtevekSodisce;
    }

    public Boolean getImetnikCrtnaKoda() {
        return imetnikCrtnaKoda;
    }

    public void setImetnikCrtnaKoda(Boolean imetnikCrtnaKoda) {
        this.imetnikCrtnaKoda = imetnikCrtnaKoda;
    }

    public Boolean getImetnikSodisce() {
        return imetnikSodisce;
    }

    public void setImetnikSodisce(Boolean imetnikSodisce) {
        this.imetnikSodisce = imetnikSodisce;
    }
}
