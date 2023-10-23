/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.mod.Status;
import si.vsrs.cif.mod.datajpa.repository.StatusRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class StatusRepoManager {

    @Autowired
    StatusRepository repository;

    /*--*/
    @PreAuthorize("hasAnyRole('001,002')")
    public Status getStatus(String sifra) {
        if (repository.count() < 1) {
            insertStatus();
        }
        return repository.findBySifra(sifra);
    }

    private void insertStatus() {
        Status s1 = new Status();
        s1.setOpis("V pripravi");
        s1.setSifra("01");
        s1.setBarva("#ccc9bf");
        Status s2 = new Status();
        s2.setOpis("Zaključen");
        s2.setSifra("02");
        s2.setBarva("#b99de0");
        Status s3 = new Status();
        s3.setOpis("Posredovano na CIF");
        s3.setSifra("03");
        s3.setBarva("#7874bf");
        Status s4 = new Status();
        s4.setOpis("Izbrisano");
        s4.setSifra("04");
        s4.setBarva("#f2f0e8");
        Status s5 = new Status();
        s5.setOpis("Preverjen");
        s5.setSifra("05");
        s5.setBarva("#4cbf3d");
        Status s6 = new Status();
        s6.setOpis("Zavrnjen");
        s6.setSifra("06");
        s6.setBarva("#c14545");
        Status s7 = new Status();
        s7.setOpis("Posredovano na SIGOV-CA");
        s7.setSifra("07");
        s7.setBarva("#2ac7d6");
        Status s8 = new Status();
        s8.setOpis("Dokončan");
        s8.setSifra("08");
        s8.setBarva("#1b6625");
        repository.save(s1);
        repository.save(s2);
        repository.save(s3);
        repository.save(s4);
        repository.save(s5);
        repository.save(s6);
        repository.save(s7);
        repository.save(s8);
    }
}
