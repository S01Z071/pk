/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.mod.StatusImetnik;
import si.vsrs.cif.mod.datajpa.repository.StatusImetnikRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class StatusImetnikRepoManager {

    @Autowired
    StatusImetnikRepository repository;

    /*--*/
    @PreAuthorize("hasAnyRole('001,002')")
    public StatusImetnik getStatusImetnik(String sifra) {
        if (repository.count() < 1) {
            insertStatus();
        }
        return repository.findBySifraSIm(sifra);
    }

    private void insertStatus() {
        StatusImetnik s1 = new StatusImetnik();
        s1.setOpisSIm("V pripravi");
        s1.setSifraSIm("01");
        s1.setBarvaSIm("#ccc9bf");
        StatusImetnik s2 = new StatusImetnik();
        s2.setOpisSIm("Zaključen");
        s2.setSifraSIm("02");
        s2.setBarvaSIm("#b99de0");
        StatusImetnik s3 = new StatusImetnik();
        s3.setOpisSIm("Posredovano na CIF");
        s3.setSifraSIm("03");
        s3.setBarvaSIm("#7874bf");
        StatusImetnik s4 = new StatusImetnik();
        s4.setOpisSIm("Izbrisano");
        s4.setSifraSIm("04");
        s4.setBarvaSIm("#f2f0e8");
        StatusImetnik s5 = new StatusImetnik();
        s5.setOpisSIm("Potrjen");
        s5.setSifraSIm("05");
        s5.setBarvaSIm("#4cbf3d");
        StatusImetnik s6 = new StatusImetnik();
        s6.setOpisSIm("Zavrnjen");
        s6.setSifraSIm("06");
        s6.setBarvaSIm("#c14545");
        StatusImetnik s7 = new StatusImetnik();
        s7.setOpisSIm("Posredovano na SIGOV-CA");
        s7.setSifraSIm("07");
        s7.setBarvaSIm("#2ac7d6");
        StatusImetnik s8 = new StatusImetnik();
        s8.setOpisSIm("Pametna kartica odpremljena");
        s8.setSifraSIm("08");
        s8.setBarvaSIm("#0d5cc4");
        StatusImetnik s9 = new StatusImetnik();
        s9.setOpisSIm("Pametna kartica prevzeta");
        s9.setSifraSIm("09");
        s9.setBarvaSIm("#1b6625");
        
        StatusImetnik s10 = new StatusImetnik();
        s10.setOpisSIm("Imetnik prenešen na drugo sodišče");
        s10.setSifraSIm("10");
        s10.setBarvaSIm("#CCFFCC");

        repository.save(s1);
        repository.save(s2);
        repository.save(s3);
        repository.save(s4);
        repository.save(s5);
        repository.save(s6);
        repository.save(s7);
        repository.save(s8);
        repository.save(s9);
        repository.save(s10);
    }
}