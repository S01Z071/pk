/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author uporabnik
 */
//@Entity
//@Table(name = "oseba")
@Embeddable
public class Predstojnik implements Serializable {

    @Size(min = 2, max = 255)
    @Column(name = "IME_P")
    private String imeP;
    @Size(min = 2, max = 255)
    @Column(name = "PRIIMEK_P")
    private String priimekP;
    @NotBlank()
    @Email()
    @Column(name = "ENASLOV_P")
    private String eNaslovP;
    @Size(min = 3, max = 255)
    @Column(name = "FUNKCIJA_P")
    private String funkcijaP;

    public Predstojnik() {
    }

    public Predstojnik(String imeP, String priimekP, String eNaslovP, String funkcijaP) {
        this.imeP = imeP;
        this.priimekP = priimekP;
        this.eNaslovP = eNaslovP;
        this.funkcijaP = funkcijaP;
    }

    public String getImeP() {
        return imeP;
    }

    public void setImeP(String imeP) {
        this.imeP = imeP;
    }

    public String getPriimekP() {
        return priimekP;
    }

    public void setPriimekP(String priimekP) {
        this.priimekP = priimekP;
    }

    public String geteNaslovP() {
        return eNaslovP;
    }

    public void seteNaslovP(String eNaslovP) {
        this.eNaslovP = eNaslovP;
    }

    public String getFunkcijaP() {
        return funkcijaP;
    }

    public void setFunkcijaP(String funkcijaP) {
        this.funkcijaP = funkcijaP;
    }
}
