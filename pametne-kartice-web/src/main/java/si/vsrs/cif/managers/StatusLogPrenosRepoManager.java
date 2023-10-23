/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.mod.StatusLogPrenos;
import si.vsrs.cif.mod.datajpa.repository.StatusLogPrenosRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class StatusLogPrenosRepoManager {

    @Autowired
    StatusLogPrenosRepository repository;

    @PreAuthorize("hasAnyRole('001,002')")
    public void insertStatusLogPrenos(StatusLogPrenos statusLogPrenos) {
        repository.save(statusLogPrenos);
    }

    @PreAuthorize("hasRole('002')")
    public List<StatusLogPrenos> getStatusLogPrenosByZahtevekId(long id) {
        return repository.findByZahtevekPrenosLID(id);
    }
}
