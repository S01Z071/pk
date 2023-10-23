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
import si.vsrs.cif.mod.ZahtevekZaPreklic;

/**
 *
 * @author Uporabnik
 */
public interface ZahtevekZaPreklicRepository extends JpaRepository<ZahtevekZaPreklic, Long>, PagingAndSortingRepository<ZahtevekZaPreklic, Long> {

    ZahtevekZaPreklic findById(Long id);

    List<ZahtevekZaPreklic> findByStatusSifraAndSerijskaStevilka(String status, String serijska);

    List<ZahtevekZaPreklic> findBySerijskaStevilkaAndStatusSifraNot(String serijska, String status);
    
    ZahtevekZaPreklic findByCrtnaKodaIgnoreCase(String crtnaKoda);

    List<ZahtevekZaPreklic> findBySifraOrganizacijeAndStatusSifraIsNotOrderByIdDesc(String sifraSodisca, String status, Pageable p);

    @org.springframework.data.jpa.repository.Query(value = "select count(*) from ZahtevekZaPreklic z where z.sifraOrganizacije=:sodisceSifra and z.status.sifra!='04'")
    int getZahteveZaPreklicCount(@Param("sodisceSifra") String sodisceSifra);
    
     @org.springframework.data.jpa.repository.Query(value = "select z.id from ZahtevekZaPreklic z where z.imetnikID = :imetnikID")
    List<Long> getZahtevekZaPreklicIdsByImetnikId(@Param("imetnikID") Long imetnikID);
   
     List<ZahtevekZaPreklic> findByImetnikID(Long imetnikId);
     
     ZahtevekZaPreklic findByCertifikatIDAndStatusSifraIsNot(Long certifikatId, String statusSifra);

}
