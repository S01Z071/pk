/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import si.vsrs.cif.mod.ZahtevekTemp;

/**
 *
 * @author uporabnik
 */

 public interface ZahtevekTempRepository extends JpaRepository<ZahtevekTemp, Long>, PagingAndSortingRepository<ZahtevekTemp, Long> {   
     ZahtevekTemp findBySifraOrganizacije(String sifra);
}
