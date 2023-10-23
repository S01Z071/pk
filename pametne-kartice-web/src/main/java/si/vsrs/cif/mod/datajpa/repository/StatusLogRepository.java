/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import si.vsrs.cif.mod.StatusLog;

/**
 *
 * @author uporabnik
 */
public interface StatusLogRepository extends JpaRepository<StatusLog, Long>, PagingAndSortingRepository<StatusLog, Long> {

    StatusLog findById(long id);

    List<StatusLog> findByZahtevekLIDOrderByDatumAsc(long id);

    @org.springframework.data.jpa.repository.Query(value = "select s from StatusLog s where zahtevekLID = :sifra and datum = (select max(s1.datum) from StatusLog s1 where s1.zahtevekLID = :sifra)")
    StatusLog getLastChangeByZahtevek(@Param("sifra") Long sifra);
}
