/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import si.vsrs.cif.mod.ZahtevekZaKodo;

/**
 *
 * @author Uporabnik
 */
public interface ZahtevekZaKodoRepository extends JpaRepository<ZahtevekZaKodo, Long>, PagingAndSortingRepository<ZahtevekZaKodo, Long> {

    ZahtevekZaKodo findById(long id);

    List<ZahtevekZaKodo> findBySifraOrganizacijeAndStatusSifraIsNotOrderByIdDesc(String sifraSodisca, String status, Pageable p);

    @org.springframework.data.jpa.repository.Query(value = "select count(*) from ZahtevekZaKodo z where z.sifraOrganizacije=:sodisceSifra and z.status.sifra!='04'")
    int getZahteveZaKodokCount(@Param("sodisceSifra") String sodisceSifra);
    
    List<ZahtevekZaKodo> findByStatusSifraAndSerijskaStevilka(String status, String serijska);
    
     ZahtevekZaKodo findByCrtnaKodaIgnoreCase(String crtnaKoda);
     
     List<ZahtevekZaKodo> findByImetnikID(Long imetnikID);
}
