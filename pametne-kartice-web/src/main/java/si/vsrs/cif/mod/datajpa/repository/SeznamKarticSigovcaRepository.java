/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import si.vsrs.cif.mod.SeznamKarticSigovca;

/**
 *
 * @author uporabnik
 */
public interface SeznamKarticSigovcaRepository extends JpaRepository<SeznamKarticSigovca, Long> {

    SeznamKarticSigovca findById(long id);

    List<SeznamKarticSigovca> findByImeInPriimekAndImeOrganizacijeAndTipAndOznakaAndDatumIzdajeAndDatumVrnitve(String imeInPriimek, String imeOrganizacije, String tip, String oznaka, Date datumIzdaje, Date datumVrnitve);

    List<SeznamKarticSigovca> findBySifraSodiscaAndDatumVrnitveIsNullOrderByDatumIzdajeAsc(String sifraSodisca);

    List<SeznamKarticSigovca> findBySifraSodiscaAndDatumVrnitveIsNotNullOrderByDatumIzdajeAsc(String sifraSodisca);

    List<SeznamKarticSigovca> findBySifraSodiscaOrderByDatumIzdajeAsc(String sifraSodisca);

    List<SeznamKarticSigovca> findBySifraSodiscaIsNull();

    List<SeznamKarticSigovca> findBySifraSodiscaAndDatumVrnitveIsNullAndImeInPriimekIgnoreCase(String sifraSodisca, String imeInPriimek);

    //List<SeznamKarticSigovca> findByOznakaContainsIgnoreCaseAndDatumVrnitveIsNull(String oznaka);
    @org.springframework.data.jpa.repository.Query(value = "SELECT k FROM SeznamKarticSigovca k where (lower(k.oznaka) LIKE %:iskanje% OR lower(k.imeInPriimek) LIKE %:iskanje%) AND k.datumVrnitve is null")
    List<SeznamKarticSigovca> findKarticaSigovcaIzdane(@Param("iskanje") String iskanje);

    @org.springframework.data.jpa.repository.Query(value = "SELECT k FROM SeznamKarticSigovca k where (lower(k.oznaka) LIKE %:iskanje% OR lower(k.imeInPriimek) LIKE %:iskanje%)")
    List<SeznamKarticSigovca> findKarticaSigovcaVse(@Param("iskanje") String iskanje);

    @org.springframework.data.jpa.repository.Query(value = "SELECT k FROM SeznamKarticSigovca k where (lower(k.oznaka) LIKE %:iskanje% OR lower(k.imeInPriimek) LIKE %:iskanje%) AND k.datumVrnitve is not null")
    List<SeznamKarticSigovca> findKarticaSigovcaVrnjene(@Param("iskanje") String iskanje);
}
