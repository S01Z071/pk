/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author uporabnik
 */
//@Entity
//@Table(name="oseba")
@Embeddable
public class KontaktnaOseba implements Serializable {

    @Size(min = 2, max = 255)
    @Column(name = "IME_K")
    private String imeK;
    @Size(min = 2, max = 255)
    @Column(name = "PRIIMEK_K")
    private String priimekK;
    @NotBlank()
    @Email()
    @Column(name = "ENASLOV_K")
    private String eNaslovK;
    @Size(min = 3, max = 255)
    @Column(name = "FUNKCIJA_K")
    private String funkcijaK;
    @NotBlank()
    @Digits(fraction = Integer.SIZE, integer = Integer.SIZE)
    @Column(name = "TELEFON_K")
    private String telefonK;

    public KontaktnaOseba() {
    }

    public KontaktnaOseba(String imeK, String priimekK, String eNaslovK, String funkcijaK, String telefonK) {
        this.imeK = imeK;
        this.priimekK = priimekK;
        this.eNaslovK = eNaslovK;
        this.funkcijaK = funkcijaK;
        this.telefonK = telefonK;
    }

    public String getImeK() {
        return imeK;
    }

    public void setImeK(String imeK) {
        this.imeK = imeK;
    }

    public String getPriimekK() {
        return priimekK;
    }

    public void setPriimekK(String priimekK) {
        this.priimekK = priimekK;
    }

    public String geteNaslovK() {
        return eNaslovK;
    }

    public void seteNaslovK(String eNaslovK) {
        this.eNaslovK = eNaslovK;
    }

    public String getFunkcijaK() {
        return funkcijaK;
    }

    public void setFunkcijaK(String funkcijaK) {
        this.funkcijaK = funkcijaK;
    }

    public String getTelefonK() {
        return telefonK;
    }

    public void setTelefonK(String telefonK) {
        this.telefonK = telefonK;
    }
}
