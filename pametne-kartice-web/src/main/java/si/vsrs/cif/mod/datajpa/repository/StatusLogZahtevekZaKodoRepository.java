/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import si.vsrs.cif.mod.StatusLogZahtevekZaKodo;

/**
 *
 * @author Uporabnik
 */
public interface StatusLogZahtevekZaKodoRepository extends JpaRepository<StatusLogZahtevekZaKodo, Long>, PagingAndSortingRepository<StatusLogZahtevekZaKodo, Long> {

    StatusLogZahtevekZaKodo findById(long id);
    List<StatusLogZahtevekZaKodo> findByZahtevekLIDOrderByDatumAsc(long id);
}

