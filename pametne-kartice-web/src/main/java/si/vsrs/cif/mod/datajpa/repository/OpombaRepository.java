/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import si.vsrs.cif.mod.Opomba;

/**
 *
 * @author uporabnik
 */
public interface OpombaRepository extends JpaRepository<Opomba, Long>, PagingAndSortingRepository<Opomba, Long> {
    Opomba findById(long id);
}
