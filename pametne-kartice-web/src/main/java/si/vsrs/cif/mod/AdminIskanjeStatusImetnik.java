/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author uporabnik
 */
public class AdminIskanjeStatusImetnik {

    private Long id;
    private Boolean vPripravi = true;
    private Boolean zakljucen = true;
    private Boolean posredovanoNaCIF = true;
    private Boolean izbrisano = true;
    private Boolean potrjeno = true;
    private Boolean zavrnjen = true;
    private Boolean posredovanoNaSIGOVCA = true;
    private Boolean karticaOdpremljena = true;
    //menjava na izdelana
    //private Boolean karticaIzdelana = true;
    private Boolean karticaPrevzeta = true;
    private Boolean imetnikPrenesen = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getvPripravi() {
        return vPripravi;
    }

    public void setvPripravi(Boolean vPripravi) {
        this.vPripravi = vPripravi;
    }

    public Boolean getZakljucen() {
        return zakljucen;
    }

    public void setZakljucen(Boolean zakljucen) {
        this.zakljucen = zakljucen;
    }

    public Boolean getPosredovanoNaCIF() {
        return posredovanoNaCIF;
    }

    public void setPosredovanoNaCIF(Boolean posredovanoNaCIF) {
        this.posredovanoNaCIF = posredovanoNaCIF;
    }

    public Boolean getIzbrisano() {
        return izbrisano;
    }

    public void setIzbrisano(Boolean izbrisano) {
        this.izbrisano = izbrisano;
    }

    public Boolean getPotrjeno() {
        return potrjeno;
    }

    public void setPotrjeno(Boolean potrjeno) {
        this.potrjeno = potrjeno;
    }

    public Boolean getZavrnjen() {
        return zavrnjen;
    }

    public void setZavrnjen(Boolean zavrnjen) {
        this.zavrnjen = zavrnjen;
    }

    public Boolean getPosredovanoNaSIGOVCA() {
        return posredovanoNaSIGOVCA;
    }

    public void setPosredovanoNaSIGOVCA(Boolean posredovanoNaSIGOVCA) {
        this.posredovanoNaSIGOVCA = posredovanoNaSIGOVCA;
    }

    public Boolean getKarticaOdpremljena() {
        return karticaOdpremljena;
    }

    public void setKarticaOdpremljena(Boolean karticaOdpremljena) {
        this.karticaOdpremljena = karticaOdpremljena;
    }

    public Boolean getKarticaPrevzeta() {
        return karticaPrevzeta;
    }

    public void setKarticaPrevzeta(Boolean karticaPrevzeta) {
        this.karticaPrevzeta = karticaPrevzeta;
    }

    public Boolean getImetnikPrenesen() {
        return imetnikPrenesen;
    }

    public void setImetnikPrenesen(Boolean imetnikPrenesen) {
        this.imetnikPrenesen = imetnikPrenesen;
    }

    public List<String> returnList() {
        List<String> vrni = new ArrayList();
        if (this.getIzbrisano()) {
            vrni.add("04");
        }
        if (this.getPosredovanoNaCIF()) {
            vrni.add("03");
        }
        if (this.getPotrjeno()) {
            vrni.add("05");
        }
        if (this.getZakljucen()) {
            vrni.add("02");
        }
        if (this.getvPripravi()) {
            vrni.add("01");
        }
        if (this.getZavrnjen()) {
            vrni.add("06");
        }
        if (this.getPosredovanoNaSIGOVCA()) {
            vrni.add("07");
        }
        if (this.getKarticaOdpremljena()) {
            vrni.add("08");
        }
        if (this.getKarticaPrevzeta()) {
            vrni.add("09");
        }
        if (this.getImetnikPrenesen()) {
            vrni.add("10");
        }

        return vrni;
    }

    public boolean allDeselected() {
        if (!this.getIzbrisano() && !this.getPosredovanoNaCIF() && !this.getPosredovanoNaSIGOVCA() && !this.getPotrjeno() && !this.getZakljucen() && !this.getZavrnjen() && !this.getvPripravi() && !this.getKarticaOdpremljena() && !this.getKarticaPrevzeta() && !this.getImetnikPrenesen()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "AdminIskanjeStatusImetnik{" + "id=" + id + ", vPripravi=" + vPripravi + ", zakljucen=" + zakljucen + ", posredovanoNaCIF=" + posredovanoNaCIF + ", izbrisano=" + izbrisano + ", potrjeno=" + potrjeno + ", zavrnjen=" + zavrnjen + ", posredovanoNaSIGOVCA=" + posredovanoNaSIGOVCA + ", karticaOdpremljena=" + karticaOdpremljena + ", karticaPrevzeta=" + karticaPrevzeta + ", imetnikPrenesen=" + imetnikPrenesen + '}';
    }
}
