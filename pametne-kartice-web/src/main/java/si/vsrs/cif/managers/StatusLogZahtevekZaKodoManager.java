/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.mod.StatusLogZahtevekZaKodo;
import si.vsrs.cif.mod.datajpa.repository.StatusLogZahtevekZaKodoRepository;

/**
 *
 * @author Uporabnik
 */
@Service
@Transactional
public class StatusLogZahtevekZaKodoManager {
     @Autowired
     StatusLogZahtevekZaKodoRepository repository;
     
      
    public void insertStatusLog(StatusLogZahtevekZaKodo statusLog) {
        repository.save(statusLog);
    }

    public List<StatusLogZahtevekZaKodo> getStatusLogByZahtevekId(long id) {
        return repository.findByZahtevekLIDOrderByDatumAsc(id);
    }
}
