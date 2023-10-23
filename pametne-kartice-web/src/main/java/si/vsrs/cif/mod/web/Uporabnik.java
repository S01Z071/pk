/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.web;

import java.util.List;


/**
 *
 * @author andrej
 */
public class Uporabnik extends Oseba {

    private VlogaSodisce izbranaVlogaSodisce;
    private List<VlogaSodisce> dodeljeneVlogeSodisce;

    // seznam vlog + sodisce
    public List<VlogaSodisce> getDodeljeneVlogeSodisce() {
        return dodeljeneVlogeSodisce;
    }

    public void setDodeljeneVlogeSodisce(List<VlogaSodisce> dodeljeneVlogeSodisce) {
        this.dodeljeneVlogeSodisce = dodeljeneVlogeSodisce;
    }

    public VlogaSodisce getIzbranaVlogaSodisce() {
        return izbranaVlogaSodisce;
    }

    public void setIzbranaVlogaSodisce(VlogaSodisce izbranaVlogaSodisce) {
        this.izbranaVlogaSodisce = izbranaVlogaSodisce;
    }



    @Override
    public String getPrintSimple() {
        throw new RuntimeException("Ni implementirano");
    }
    @Override
    public String getKadrovskStevilka(){
        return super.getKadrovskStevilka().toUpperCase();
    }

    @Override
    public String toString() {
        return "Uporabnik{" + "izbranaVlogaSodisce=" + izbranaVlogaSodisce + ", dodeljeneVlogeSodisce=" + dodeljeneVlogeSodisce + '}';
    }
    
    
  
}
