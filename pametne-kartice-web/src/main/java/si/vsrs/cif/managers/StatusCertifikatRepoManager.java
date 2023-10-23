/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.mod.StatusCertifikat;
import si.vsrs.cif.mod.datajpa.repository.StatusCertifikatRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class StatusCertifikatRepoManager {

    @Autowired
    StatusCertifikatRepository repository;

    @PreAuthorize("hasAnyRole('001,002')")
    public StatusCertifikat getStatusCertifikat(String sifra) {
        if (repository.count() < 1) {
            insertStatus();
        }
        return repository.findBySifra(sifra);
    }

    private void insertStatus() {
        StatusCertifikat s1 = new StatusCertifikat();
        s1.setOpis("V pripravi");
        s1.setSifra("01");
        StatusCertifikat s2 = new StatusCertifikat();
        s2.setOpis("Prevzet na VSRS");
        s2.setSifra("02"); 
        StatusCertifikat s3 = new StatusCertifikat();
        s3.setOpis("Prevzet");
        s3.setSifra("03"); 
        StatusCertifikat s4 = new StatusCertifikat();
        s4.setOpis("Preklican");
        s4.setSifra("04");     
        StatusCertifikat s5 = new StatusCertifikat();
        s5.setOpis("Pretekel");
        s5.setSifra("05");  
        StatusCertifikat s6 = new StatusCertifikat();
        s6.setOpis("Zavrnjen");
        s6.setSifra("06");  
        StatusCertifikat s7 = new StatusCertifikat();
        s7.setOpis("Ostalo");
        s7.setSifra("07");  
        repository.save(s1);
        repository.save(s2);
        repository.save(s3);
        repository.save(s4);
        repository.save(s5);
        repository.save(s6);
        repository.save(s7);
       
    }
}
