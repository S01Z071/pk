/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.mod.Opomba;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.datajpa.repository.OpombaRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class OpombaRepoManager {

    @Autowired
    OpombaRepository repository;
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;

    @PreAuthorize("hasAnyRole('001,002')")
    public void insertOpomba(Opomba opomba, long id) {   
        opomba.setZahtevek(zahtevekRepoManager.getZahtevek(id)); 
        repository.save(opomba);
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public Opomba getOpomba(long id) {
        return repository.findById(id);
    }
}
