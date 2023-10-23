/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import si.vsrs.cif.mod.Imetnik;

/**
 *
 * @author uporabnik
 */
public interface ImetnikRepository extends JpaRepository<Imetnik, Long>, PagingAndSortingRepository<Imetnik, Long> {

    Imetnik findById(long id);

    Imetnik findByENaslovIgnoreCaseAndStatusSifraSIm(String eNaslov, String statusSifra);

    Page<Imetnik> findByImeContainsIgnoreCaseOrPriimekContainsIgnoreCase(String ime, String priimek, Pageable p);

    List<Imetnik> findByENaslovIgnoreCase(String eNaslov);

    Imetnik findByCrtnaKodaIgnoreCase(String crtnaKoda);

   // Page<Imetnik> findByCrtnaKodaStartingWithAndStatusSifraSImNotOrderByIdDesc(String crtnaKoda, String statusSifra, Pageable p);

    Page<Imetnik> findBySifraOrganizacijeAndStatusSifraSImIsNotInOrderByIdDesc(String sifraOrganizacije, List<String> statusSifra, Pageable p);
            
    //int countByCrtnaKodaStartingWithAndStatusSifraSImNot(String crtnaKoda, String statusSifra);

    int countBySifraOrganizacijeAndStatusSifraSImIsNotIn(String sifraOrganizacije, List<String> statusSifra);
    
    Page<Imetnik> findByStatusSifraSImIn(List<String> sifra, Pageable p);

    List<Imetnik> findByENaslovIgnoreCaseOrDavcna(String eNaslov, String davcna);

    @org.springframework.data.jpa.repository.Query(value = "select i.id from Imetnik i where i.davcna = :davcnaSt or lower(i.eNaslov) = :eNaslov")
    List<Long> getIdsByENaslovOrDavcna(@Param("davcnaSt") String davcnaSt, @Param("eNaslov") String eNaslov);

    //Vrne vse in jih v manager-ju presteje
    List<Imetnik> findByStatusSifraSImIn(List<String> sifra);

    //
    int countByStatusSifraSImIn(List<String> sifra);
    //

    //ADMIN
    List<Imetnik> findByDavcnaOrENaslovIgnoreCaseOrCrtnaKodaIgnoreCaseOrEmso(String davcna, String eNaslov, String crtnaKoda, String emso);

    //USER
    //select * from Imetnik where crtna_koda LIKE ('IS23%') and status_imetnik_id != 4 and (davcna ='IS238642883131834' or enaslov = 'IS238642883131834' or crtna_koda = 'IS238642883131834' or emso = 'IS238642883131834')
   /* @org.springframework.data.jpa.repository.Query(value = "select i from Imetnik i where crtnaKoda like (:crtnaKoda) and i.status.sifraSIm != :statusSifra"
            + " and(davcna=:iskano or lower(enaslov)=:iskano or lower(crtnaKoda)=:iskano or emso=:iskano)")
    List<Imetnik> getImetnikSearchUser(@Param("crtnaKoda") String crtnaKoda, @Param("statusSifra") String statusSifra, @Param("iskano") String iskano, Pageable p);
*/
  /*  @org.springframework.data.jpa.repository.Query(value = "select i from Imetnik i where i.sifraOrganizacije = :sifraOrganizacije and i.status.sifraSIm != :statusSifra"
            + " and(davcna=:iskano or lower(enaslov)=:iskano or lower(crtnaKoda)=:iskano or emso=:iskano)")
    List<Imetnik> getImetnikSearchUser(@Param("sifraOrganizacije") String sifraOrganizacije, @Param("statusSifra") String statusSifra, @Param("iskano") String iskano, Pageable p);
*/
     @org.springframework.data.jpa.repository.Query(value = "select i from Imetnik i where i.sifraOrganizacije = :sifraOrganizacije and i.status.sifraSIm not in(:statusSifra)"
            + " and(davcna=:iskano or lower(enaslov)=:iskano or lower(crtnaKoda)=:iskano or emso=:iskano)")
    List<Imetnik> getImetnikSearchUser(@Param("sifraOrganizacije") String sifraOrganizacije, @Param("statusSifra") List<String> statusSifra, @Param("iskano") String iskano, Pageable p);

    
    
   /* @org.springframework.data.jpa.repository.Query(value = "select count(*) from Imetnik i where crtnaKoda like (:crtnaKoda) and i.status.sifraSIm != :statusSifra"
            + " and(davcna=:iskano or lower(enaslov)=:iskano or lower(crtnaKoda)=:iskano or emso=:iskano)")
    int countImetnikSearchUser(@Param("crtnaKoda") String crtnaKoda, @Param("statusSifra") String statusSifra, @Param("iskano") String iskano);
*/
     @org.springframework.data.jpa.repository.Query(value = "select count(*) from Imetnik i where i.sifraOrganizacije=:sifraOrganizacije and i.status.sifraSIm not in (:statusSifra)"
            + " and(davcna=:iskano or lower(enaslov)=:iskano or lower(crtnaKoda)=:iskano or emso=:iskano)")
    int countImetnikSearchUser(@Param("sifraOrganizacije") String sifraOrganizacije, @Param("statusSifra") List<String> statusSifra, @Param("iskano") String iskano);

    @org.springframework.data.jpa.repository.Query(value = "select count(*) from Imetnik i where i.status.sifraSIm = :sifra")
    int countByStatusSifra(@Param("sifra") String sifra);

    @org.springframework.data.jpa.repository.Query(value = "SELECT i FROM Imetnik i where lower(i.crtnaKoda) LIKE %:iskanje% OR lower(i.emso) LIKE %:iskanje% OR lower(i.davcna) LIKE %:iskanje% OR lower(i.eNaslov) LIKE %:iskanje% OR lower(i.ime) LIKE %:ime% OR lower(i.priimek) LIKE %:priimek%")
    List<Imetnik> getImetnikeSearchAdmin(@Param("iskanje") String iskanje, @Param("ime") String ime, @Param("priimek") String priimek);

    @org.springframework.data.jpa.repository.Query(value = "SELECT i FROM Imetnik i where (lower(i.crtnaKoda) LIKE %:iskanje% OR lower(i.emso) LIKE %:iskanje% OR lower(i.davcna) LIKE %:iskanje% OR lower(i.eNaslov) LIKE %:iskanje% OR lower(i.ime) LIKE %:ime% OR lower(i.priimek) LIKE %:priimek%) AND i.status.sifraSIm IN :status")
    List<Imetnik> getImetnikeSearchAdminPages(@Param("iskanje") String iskanje, @Param("ime") String ime, @Param("priimek") String priimek, @Param("status") List<String> status, Pageable p);

    @org.springframework.data.jpa.repository.Query(value = "SELECT count(*) FROM Imetnik i where (lower(i.crtnaKoda) LIKE %:iskanje% OR lower(i.emso) LIKE %:iskanje% OR lower(i.davcna) LIKE %:iskanje% OR lower(i.eNaslov) LIKE %:iskanje% OR lower(i.ime) LIKE %:ime% OR lower(i.priimek) LIKE %:priimek%) AND i.status.sifraSIm IN :status")
    int countImetnikeSearchAdmin(@Param("iskanje") String iskanje, @Param("ime") String ime, @Param("priimek") String priimek, @Param("status") List<String> status);

   
     @org.springframework.data.jpa.repository.Query(value = "select i.prenesenImetnik from Imetnik i where i.id = :id")
   Boolean isImetnikPrenesen(@Param("id") Long id);

    
}
