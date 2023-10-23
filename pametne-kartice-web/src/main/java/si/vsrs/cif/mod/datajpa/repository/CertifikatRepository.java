/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import si.vsrs.cif.mod.Certifikat;

/**
 *
 * @author uporabnik
 */
public interface CertifikatRepository extends JpaRepository<Certifikat, Long> {

    Certifikat findById(long id);

    List<Certifikat> findByImetnikId(long id);

    List<Certifikat> findByENaslovIgnoreCase(String eNaslov);

    Certifikat findBySerijskaStevilkaIgnoreCase(String serijskaSt);

    List<Certifikat> findByStatusSifraOrderByIdAsc(String sifra);

    List<Certifikat> findByStatusSifraInAndSifraSodiscaIgnoreCase(List<String> sifra, String sifraSodisca);

    @org.springframework.data.jpa.repository.Query(value = "SELECT count(c.id) FROM Certifikat c where c.status.sifra != '01' and c.status.sifra != '02' and c.status.sifra != '03' and c.kartica.datumVrnitve is null and (c.imetnik.davcna = :davcnaSt or c.imetnik.eNaslov = :eNaslov )")
    int findNeveljavneCertifikateAndKarticaNiVrnjena(@Param("davcnaSt") String davcnaSt, @Param("eNaslov") String eNaslov);

    @org.springframework.data.jpa.repository.Query(value = "SELECT c FROM Certifikat c where c.status.sifra != '01' and c.status.sifra != '02' and c.status.sifra != '03' and c.kartica.datumVrnitve is null order by c.kartica.barcode")
    List<Certifikat> findNeveljavneCertifikateAndKarticaNiVrnjena();

    @org.springframework.data.jpa.repository.Query(value = "select c from Certifikat c where imetnik_ID = :imetnikID and kartica_id is null")
    List<Certifikat> getCertifikatByImetnikID(@Param("imetnikID") long imetnikID);

    List<Certifikat> findByStatusSifraAndKarticaBarcodeAndImetnikStatusSifraSIm(String status, String crtnaKoda, String statusImetnik);

    //List<Certifikat> findByStatusSifraAndImetnikCrtnaKodaAndImetnikStatusSifraSIm(String status, String crtnaKoda, String statusImetnik);
    List<Certifikat> findByImetnikCrtnaKodaAndImetnikStatusSifraSIm(String crtnaKoda, String statusImetnik);

    // List<Certifikat> findByStatusSifraAndImetnikStatusSifraSImOrderByIdAsc(String statusC, String statusI);
    List<Certifikat> findByImetnikStatusSifraSImOrderByIdAsc(String statusI);

    List<Certifikat> findBySifraSodiscaIsNull();

    List<Certifikat> findByKarticaDatumInitBetweenAndStatusSifraOrderByKarticaDatumInitAsc(Date d1, Date d2, String sifra);

   // List<Certifikat> findByIzvozPin3IsNullOrIzvozPin3False();
    
    @org.springframework.data.jpa.repository.Query(value = "select c from Certifikat c where c.status.sifra = '03' and c.kartica is not null and (c.izvozPin3 is null or c.izvozPin3 =0)")
    List<Certifikat> getNeizvozene();
    
    @org.springframework.data.jpa.repository.Query(value = "select count(*) from Certifikat c where c.status.sifra = :sifra and datumPrevzema > to_date('01.10.2013','dd.MM.yyyy')")
    int countByStatusSifra(@Param("sifra") String sifra);

    @org.springframework.data.jpa.repository.Query(value = "select count(*) from Certifikat c where c.status.sifra = :sifra and datumPrevzema > '01.10.2013'")
    int countByStatusSifraRazvoj(@Param("sifra") String sifra);
    //////////

    List<Certifikat> findByENaslovIgnoreCaseAndKarticaIsNull(String eNaslov);

    List<Certifikat> findByStatusSifraAndSifraSodisca(String sifra, String sifraSodisca);

    List<Certifikat> findBySerialNumberX(String serialNumberX);

    //V statusu prevzet
    // @org.springframework.data.jpa.repository.Query(value = "SELECT c FROM Certifikat c where (lower(c.serijskaStevilka) LIKE %:iskanje% OR lower(c.imetnik.ime) LIKE %:ime% OR lower(c.imetnik.priimek) LIKE %:priimek% OR lower(c.imetnik.eNaslov) LIKE %:iskanje%) AND c.imetnik.crtnaKoda LIKE :crtnaKoda% AND c.status.sifra = '03'")
    // List<Certifikat> findCertifikatZaPreklic(@Param("iskanje") String iskanje, @Param("ime") String ime, @Param("priimek") String priimek, @Param("crtnaKoda") String crtnaKoda);
    @org.springframework.data.jpa.repository.Query(value = "SELECT c FROM Certifikat c where (lower(c.serijskaStevilka) LIKE %:iskanje% OR lower(c.imetnik.ime) LIKE %:ime% OR lower(c.imetnik.priimek) LIKE %:priimek% OR lower(c.imetnik.eNaslov) LIKE %:iskanje%) AND c.imetnik.sifraOrganizacije LIKE :sifraOrganizacije AND c.status.sifra = '03'")
    List<Certifikat> findCertifikatZaPreklic(@Param("iskanje") String iskanje, @Param("ime") String ime, @Param("priimek") String priimek, @Param("sifraOrganizacije") String sifraOrganizacije);

    @org.springframework.data.jpa.repository.Query(value = "SELECT c FROM Certifikat c where c.kartica.datumVrnitve is null and (c.serijskaStevilka =:iskano OR c.kartica.barcode=:iskano) and c.status.sifra = '03' and c.imetnik.status.sifraSIm = '09'")
    Certifikat findCertifikatZaPrenos(@Param("iskano") String iskano);
}
