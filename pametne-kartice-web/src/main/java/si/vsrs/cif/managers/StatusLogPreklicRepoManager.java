/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.mod.StatusLogPreklic;
import si.vsrs.cif.mod.datajpa.repository.StatusLogPreklicRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class StatusLogPreklicRepoManager {
    @Autowired
    StatusLogPreklicRepository repository;
    
    
    @PreAuthorize("hasAnyRole('001,002')")
    public void insertStatusLogPreklic(StatusLogPreklic statusLogPreklic) {
        repository.save(statusLogPreklic);
    }

    @PreAuthorize("hasRole('002')")
    public List<StatusLogPreklic> getStatusLogPreklicByZahtevekId(long id) {
        return repository.findByZahtevekPreklicLID(id);
    }    
   
}
