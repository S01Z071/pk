/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

/**
 *
 * @author Uporabnik
 */
public class OpremaZaObrazec {

    String vrstaOpreme;
    String serijska;

    public OpremaZaObrazec() {
    }
    
    
    public OpremaZaObrazec(String vrstaOpreme, String serijska) {
        this.vrstaOpreme = vrstaOpreme;
        this.serijska = serijska;
    }

    
    
    public String getVrstaOpreme() {
        return vrstaOpreme;
    }

    public void setVrstaOpreme(String vrstaOpreme) {
        this.vrstaOpreme = vrstaOpreme;
    }

    public String getSerijska() {
        return serijska;
    }

    public void setSerijska(String serijska) {
        this.serijska = serijska;
    }
}
