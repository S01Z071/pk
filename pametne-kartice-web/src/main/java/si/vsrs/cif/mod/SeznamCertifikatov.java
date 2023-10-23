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
public class SeznamCertifikatov {
    private List<Certifikat> certifikati;
    
    public List<Certifikat> getCertifikati() {
        return certifikati;
    }

    public void setCertifikati(List<Certifikat> certifikati) {
        this.certifikati = certifikati;
    }

    @Override
    public String toString() {
        return "SeznamCertifikatov{" + "certifikati=" + certifikati + '}';
    }
    
    
    
}
