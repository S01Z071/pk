/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.job;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.ZahtevekZaPreklicRepoManager;
import si.vsrs.cif.mod.Certifikat;

/**
 *
 * @author uporabnik
 */
@Component
public class JobWriter implements ItemWriter<Certifikat> {

    @Autowired
    CertifikatRepoManager certifikatRepoManager;
    @Autowired
    ZahtevekZaPreklicRepoManager zahtevekZaPreklicRepoManager;
    
    @Override
    public void write(List<? extends Certifikat> list) throws Exception { 
       
        for (Certifikat item : list) {
            certifikatRepoManager.updateCertifikat(item);           
            zahtevekZaPreklicRepoManager.updateStatusToDokoncajByCertifikatIdIfExists(item.getId());         
        }       
    }
}
