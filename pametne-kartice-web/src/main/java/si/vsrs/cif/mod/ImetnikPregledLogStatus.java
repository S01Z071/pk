/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.util.List;

/**
 *
 * @author uporabnik
 */
public class ImetnikPregledLogStatus {
    private List<StatusLogImetnik> statusLogImetnik;
    private String imetnikIme;
    private String imetnikPriimek;
    private String imetnikCrtnaKoda;

    public List<StatusLogImetnik> getStatusLogImetnik() {
        return statusLogImetnik;
    }

    public void setStatusLogImetnik(List<StatusLogImetnik> statusLogImetnik) {
        this.statusLogImetnik = statusLogImetnik;
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

    public String getImetnikCrtnaKoda() {
        return imetnikCrtnaKoda;
    }

    public void setImetnikCrtnaKoda(String imetnikCrtnaKoda) {
        this.imetnikCrtnaKoda = imetnikCrtnaKoda;
    }
 
    
}
