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
import si.vsrs.cif.mod.ZahtevekZaPrenos;

/**
 *
 * @author Uporabnik
 */
public interface ZahtevekZaPrenosRepository extends JpaRepository<ZahtevekZaPrenos, Long>, PagingAndSortingRepository<ZahtevekZaPrenos, Long> {

    ZahtevekZaPrenos findById(Long id);

    List<ZahtevekZaPrenos> findByStatusSifraAndSerijskaStevilka(String status, String serijska);

    List<ZahtevekZaPrenos> findBySerijskaStevilkaAndStatusSifraNot(String serijska, String status);

    
   //  @org.springframework.data.jpa.repository.Query(value = "select count(*) from ZahtevekZaPrenos z where z.serijskaStevilka=:serijskaStevilka and z.status.sifra!='04' and z.st")
   // int countBySerijskaStAndNotCompleted(@Param("sodisceSifra") String sodisceSifra);
    int countBySerijskaStevilkaAndStatusSifraNotIn(String serijska, List<String> statusi);

    ZahtevekZaPrenos findByCrtnaKodaIgnoreCase(String crtnaKoda);

    List<ZahtevekZaPrenos> findBySifraOrganizacijeAndStatusSifraIsNotOrderByIdDesc(String sifraSodisca, String status, Pageable p);

    @org.springframework.data.jpa.repository.Query(value = "select count(*) from ZahtevekZaPrenos z where z.sifraOrganizacije=:sodisceSifra and z.status.sifra!='04'")
    int getZahteveZaPrenosCount(@Param("sodisceSifra") String sodisceSifra);

    @org.springframework.data.jpa.repository.Query(value = "select z.id from ZahtevekZaPrenos z where z.imetnik.id = :imetnikID")
    Long getZahtevekZaPrenosIdsByImetnikId(@Param("imetnikID") Long imetnikID);

    ZahtevekZaPrenos findByImetnikId(Long imetnikId);
    
     
    @org.springframework.data.jpa.repository.Query(value = "select z from ZahtevekZaPrenos z where z.imetnik.id = :imetnikID")
    ZahtevekZaPrenos getByNovImetnikId(@Param("imetnikID") Long imetnikID);
    
     @org.springframework.data.jpa.repository.Query(value = "select z.sifraOrganizacije from ZahtevekZaPrenos z where z.id = :id")
    String getSifraOrganizacijeById(@Param("id") Long id);
}
