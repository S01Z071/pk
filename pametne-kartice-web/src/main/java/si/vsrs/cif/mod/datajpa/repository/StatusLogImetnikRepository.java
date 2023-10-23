/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import si.vsrs.cif.mod.StatusLogImetnik;

/**
 *
 * @author uporabnik
 */
public interface StatusLogImetnikRepository extends JpaRepository<StatusLogImetnik, Long>, PagingAndSortingRepository<StatusLogImetnik, Long> {

    StatusLogImetnik findById(long id);

    List<StatusLogImetnik> findByImetnikLIDOrderByDatumIAsc(long id);

    @org.springframework.data.jpa.repository.Query(value = "select s from StatusLogImetnik s where imetnikLID = :sifra and datum = (select max(s1.datumI) from StatusLogImetnik s1 where s1.imetnikLID = :sifra)")
    StatusLogImetnik getLastChangeByImetnik(@Param("sifra") Long sifra);
}
