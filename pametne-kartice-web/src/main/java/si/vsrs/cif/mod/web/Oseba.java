/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.web;

import si.vsrs.cif.mod.common.BaseModel;


/**
 *
 * @author andrej
 */
public class Oseba extends BaseModel {

    private String ime = "";
    private String priimek = "";
    private String kadrovskStevilka = "";
    private String email = "";
    
    public Oseba() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIme() {
        return ime;
    }

    public String getKadrovskStevilka() {
        return kadrovskStevilka;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setKadrovskStevilka(String kadrovskStevilka) {
        this.kadrovskStevilka = kadrovskStevilka;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }
    @Override
    public String getPrintSimple() {
        String niz = this.getIme() + " " + this.getPriimek() + " - " + this.getKadrovskStevilka();
        return niz;
    }
    @Override
    public String getPrintInLine() {
        return getPrintSimple();
    }


}
