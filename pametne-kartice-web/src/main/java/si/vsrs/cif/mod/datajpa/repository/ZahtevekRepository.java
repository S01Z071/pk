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
import si.vsrs.cif.mod.Zahtevek;

/**
 *
 * @author andrej
 */
public interface ZahtevekRepository extends JpaRepository<Zahtevek, Long>, PagingAndSortingRepository<Zahtevek, Long> {

    List<Zahtevek> findByUlicaContains(String id);

    List<Zahtevek> findByStatusSifra(String sifra);

    Zahtevek findByCrtnaKodaIgnoreCase(String crtnaKoda);

    Zahtevek findById(long id);

    @org.springframework.data.jpa.repository.Query(value = "select count(*) from Zahtevek z, Status s where status_ID = s.id and s.sifra!='04' and z.sifraOrganizacije=:sodisceSifra")
    int getZahtevekCount(@Param("sodisceSifra") String sodisceSifra);

    @org.springframework.data.jpa.repository.Query(value = "select z from Zahtevek z, Status s where status_ID = s.id and lower(z.sifraOrganizacije)=:sodisceSifra and s.sifra!='04' order by z.id desc")
    Page<Zahtevek> getZahtevekBySodisce(@Param("sodisceSifra") String sodisceSifra, Pageable p);

    @org.springframework.data.jpa.repository.Query(value = "select z from Zahtevek z inner join z.opombe o, Status s WHERE z.status.id = s.id AND s.sifra!='04' order by o.datum desc")
    Page<Zahtevek> getZahtevkeOpombaAdmin(Pageable p);

    @org.springframework.data.jpa.repository.Query(value = "select z from Zahtevek  z inner join z.opombe o, Status s WHERE z.status.id = s.id AND s.sifra!='04' AND z.sifraOrganizacije =:sodisceSifra order by o.datum desc")
    Page<Zahtevek> getZahtevkeOpomba(@Param("sodisceSifra") String sodisceSifra, Pageable p);

    Page<Zahtevek> findByCrtnaKodaContainsIgnoreCase(String crtnaKoda, Pageable p);

    @org.springframework.data.jpa.repository.Query(value = " select z.id from Zahtevek z inner join z.imetniki i where i.id = :imetnikID")
    Long getZahtevekIDFromImetnikID(@Param("imetnikID") long imetnikID);

    Page<Zahtevek> findByStatusSifraIn(List<String> sifra, Pageable p);

    List<Zahtevek> findByStatusSifraIn(List<String> sifra);

    int countByStatusSifraIn(List<String> sifra);

    @org.springframework.data.jpa.repository.Query(value = "select count(*) from Zahtevek z where z.status.sifra = :sifra")
    int countByStatusSifra(@Param("sifra") String sifra);

    @org.springframework.data.jpa.repository.Query(value = "select z.sifraOrganizacije from Zahtevek z where z.id = :id")
    String getSifraOrganizacijeById(@Param("id") Long id);
}
