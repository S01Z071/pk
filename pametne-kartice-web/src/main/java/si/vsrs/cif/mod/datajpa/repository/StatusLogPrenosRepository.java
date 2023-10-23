/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import si.vsrs.cif.mod.StatusLogPrenos;

/**
 *
 * @author uporabnik
 */
public interface StatusLogPrenosRepository extends JpaRepository<StatusLogPrenos, Long>, PagingAndSortingRepository<StatusLogPrenos, Long> {

    StatusLogPrenos findById(long id);

    List<StatusLogPrenos> findByZahtevekPrenosLID(long id);
    
   
}
