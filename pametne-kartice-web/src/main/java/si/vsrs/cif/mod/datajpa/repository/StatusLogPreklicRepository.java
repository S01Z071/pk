/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import si.vsrs.cif.mod.StatusLogPreklic;

/**
 *
 * @author uporabnik
 */
public interface StatusLogPreklicRepository extends JpaRepository<StatusLogPreklic, Long>, PagingAndSortingRepository<StatusLogPreklic, Long> {

    StatusLogPreklic findById(long id);

    List<StatusLogPreklic> findByZahtevekPreklicLID(long id);
    
   
}
