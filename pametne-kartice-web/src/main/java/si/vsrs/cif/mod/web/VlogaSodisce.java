/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.web;

/**
 *
 * @author andrej
 */
public class VlogaSodisce {
    private Vloga vloga;
    private Sodisce sodisce;

    public Vloga getVloga() {
        return vloga;
    }

    public void setVloga(Vloga vloga) {
        this.vloga = vloga;
    }

    public Sodisce getSodisce() {
        return sodisce;
    }

    public void setSodisce(Sodisce sodisce) {
        this.sodisce = sodisce;
    }

    @Override
    public String toString() {
        return "VlogaSodisce{" + "vloga=" + vloga + ", sodisce=" + sodisce + '}';
    }
    
}
