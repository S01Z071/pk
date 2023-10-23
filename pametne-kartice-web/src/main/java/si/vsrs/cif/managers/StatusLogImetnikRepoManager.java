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
import si.vsrs.cif.mod.StatusLogImetnik;
import si.vsrs.cif.mod.datajpa.repository.StatusLogImetnikRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class StatusLogImetnikRepoManager {
    @Autowired
    StatusLogImetnikRepository repository;
    
    @PreAuthorize("hasAnyRole('001,002')")
    public void insertStatusLogImetnik(StatusLogImetnik statusLog) {
       repository.save(statusLog);       
    }
    
     @PreAuthorize("hasRole('002')")
    public List<StatusLogImetnik> getStatusLogByImetnikId(long id) {
        return repository.findByImetnikLIDOrderByDatumIAsc(id);
    }
     
      @PreAuthorize("hasRole('002')")
    public StatusLogImetnik getLastChangeByImetnik(Long id){
        return repository.getLastChangeByImetnik(id);
    }
}
