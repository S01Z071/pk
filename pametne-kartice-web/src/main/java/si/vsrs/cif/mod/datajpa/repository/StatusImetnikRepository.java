/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.vsrs.cif.mod.StatusImetnik;

/**
 *
 * @author uporabnik
 */

public interface StatusImetnikRepository extends JpaRepository<StatusImetnik, Long> {
    StatusImetnik findBySifraSIm(String sifra);
        
}
