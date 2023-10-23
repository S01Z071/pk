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
import si.vsrs.cif.mod.StatusLog;
import si.vsrs.cif.mod.datajpa.repository.StatusLogRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class StatusLogRepoManager {

    @Autowired
    StatusLogRepository repository;

    @PreAuthorize("hasAnyRole('001,002')")
    public void insertStatusLog(StatusLog statusLog) {
        repository.save(statusLog);
    }

    @PreAuthorize("hasRole('002')")
    public List<StatusLog> getStatusLogByZahtevekId(long id) {
        return repository.findByZahtevekLIDOrderByDatumAsc(id);
    }
    
    @PreAuthorize("hasRole('002')")
    public StatusLog getLastChangeByZahtevek(Long id){
        return repository.getLastChangeByZahtevek(id);
    }
}
