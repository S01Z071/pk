/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import si.vsrs.cif.mod.Kartica;

/**
 *
 * @author andrej
 */
public interface KarticaRepository extends JpaRepository<Kartica, Long> {

    Kartica findById(long id);

    List<Kartica> findByImetnikId(long imetnikID);

    List<Kartica> findBySerijskaStevilkaKarticeIgnoreCase(String serijskaSt);

    List<Kartica> findBySerijskaStevilkaKarticeIgnoreCaseOrBarcodeIgnoreCase(String serijskaSt, String barcode);
    
    @org.springframework.data.jpa.repository.Query(value = "select count(*) from Kartica k where k.imetnik is not null")
    int countKarticaWithImetnik();

    @org.springframework.data.jpa.repository.Query(value = "SELECT k FROM Kartica k where (lower(k.barcode) LIKE %:iskanje% OR lower(k.serijskaStevilkaKartice) LIKE %:iskanje% OR lower(k.imetnik.ime) LIKE %:ime% OR lower(k.imetnik.priimek) LIKE %:priimek% OR lower(k.imetnik.eNaslov) LIKE %:iskanje%) AND k.datumVrnitve is null AND k.imetnik is not null")
    List<Kartica> findKarticaDatumVrnitveNull(@Param("iskanje") String iskanje, @Param("ime") String ime, @Param("priimek") String priimek);

    @org.springframework.data.jpa.repository.Query(value = "SELECT k FROM Kartica k where lower(k.barcode) LIKE %:iskanje% OR lower(k.serijskaStevilkaKartice) LIKE %:iskanje% OR lower(k.imetnik.ime) LIKE %:ime% OR lower(k.imetnik.priimek) LIKE %:priimek% OR lower(k.imetnik.eNaslov) LIKE %:iskanje% OR lower(k.imetnik.crtnaKoda) LIKE %:iskanje%")
    List<Kartica> findKarticaVse(@Param("iskanje") String iskanje, @Param("ime") String ime, @Param("priimek") String priimek);

    @org.springframework.data.jpa.repository.Query(value = "SELECT k FROM Kartica k where (lower(k.barcode) LIKE %:iskanje% OR lower(k.serijskaStevilkaKartice) LIKE %:iskanje% OR lower(k.imetnik.ime) LIKE %:ime% OR lower(k.imetnik.priimek) LIKE %:priimek% OR lower(k.imetnik.eNaslov) LIKE %:iskanje% OR lower(k.imetnik.crtnaKoda) LIKE %:iskanje%) AND k.datumVrnitve>=to_date(:datumOd,'dd.MM.yyyy') AND k.datumVrnitve<=to_date(:datumDo,'dd.MM.yyyy')")
    List<Kartica> findKarticaVseByDatumVrnitve(@Param("iskanje") String iskanje, @Param("ime") String ime, @Param("priimek") String priimek, @Param("datumOd") String datumOd, @Param("datumDo") String datumDo);

     @org.springframework.data.jpa.repository.Query(value = "SELECT k FROM Kartica k where (lower(k.barcode) LIKE %:iskanje% OR lower(k.serijskaStevilkaKartice) LIKE %:iskanje% OR lower(k.imetnik.ime) LIKE %:ime% OR lower(k.imetnik.priimek) LIKE %:priimek% OR lower(k.imetnik.eNaslov) LIKE %:iskanje% OR lower(k.imetnik.crtnaKoda) LIKE %:iskanje%) AND k.datumVrnitve>=:datumOd AND k.datumVrnitve<=:datumDo")
    List<Kartica> findKarticaVseByDatumVrnitveRazvoj(@Param("iskanje") String iskanje, @Param("ime") String ime, @Param("priimek") String priimek, @Param("datumOd") Date datumOd, @Param("datumDo") Date datumDo);

    
   // @org.springframework.data.jpa.repository.Query(value = "SELECT k FROM Kartica k where (lower(k.barcode) LIKE %:iskanje% OR lower(k.serijskaStevilkaKartice) LIKE %:iskanje% OR lower(k.imetnik.ime) LIKE %:ime% OR lower(k.imetnik.priimek) LIKE %:priimek% OR lower(k.imetnik.eNaslov) LIKE %:iskanje%) AND k.imetnik.crtnaKoda LIKE :crtnaKoda%")
   // List<Kartica> findKarticaPoSodiscihZaKodo(@Param("iskanje") String iskanje, @Param("ime") String ime, @Param("priimek") String priimek, @Param("crtnaKoda") String crtnaKoda);

     @org.springframework.data.jpa.repository.Query(value = "SELECT k FROM Kartica k where (lower(k.barcode) LIKE %:iskanje% OR lower(k.serijskaStevilkaKartice) LIKE %:iskanje% OR lower(k.imetnik.ime) LIKE %:ime% OR lower(k.imetnik.priimek) LIKE %:priimek% OR lower(k.imetnik.eNaslov) LIKE %:iskanje%) AND k.imetnik.sifraOrganizacije = :sifraOrganizacije")
    List<Kartica> findKarticaPoSodiscihZaKodo(@Param("iskanje") String iskanje, @Param("ime") String ime, @Param("priimek") String priimek, @Param("sifraOrganizacije") String sifraOrganizacije);

     
    @org.springframework.data.jpa.repository.Query(value = "select k from Kartica k left join k.certifikat c where c.id is null order by k.barcode")
    List<Kartica> getKarticeWithoutCertifikat();

    @org.springframework.data.jpa.repository.Query(value = "select k from Kartica k where k.imetnik is null order by k.barcode")
    List<Kartica> getKarticeWithoutImetnik();

    @org.springframework.data.jpa.repository.Query(value = "SELECT k FROM Kartica k where k.datumVrnitve>=:datumOd AND k.datumVrnitve<=:datumDo order by k.barcode")
    Page<Kartica> getKarticeByDatumVrnitve(@Param("datumOd") Date datumOd, @Param("datumDo") Date datumDo, Pageable p);

}
