/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.vsrs.cif.mod.Status;

/**
 *
 * @author andrej
 */
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findBySifra(String sifra);
        
}
