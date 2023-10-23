/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.mod.datajpa.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;

/**
 *
 * @author uporabnik
 */
public interface SeznamCitalcevSigovcaRepository extends JpaRepository<SeznamCitalcevSigovca, Long> {

    SeznamCitalcevSigovca findById(long id);

    List<SeznamCitalcevSigovca> findByImeInPriimekAndImeOrganizacijeAndTipAndOznakaAndDatumIzdajeAndDatumVrnitve(String imeInPriimek, String imeOrganizacije, String tip, String oznaka, Date datumIzdaje, Date datumVrnitve);

    List<SeznamCitalcevSigovca> findBySifraSodiscaAndDatumVrnitveIsNullOrderByDatumIzdajeAsc(String sifraSodisca);

    List<SeznamCitalcevSigovca> findBySifraSodiscaAndDatumVrnitveIsNotNullOrderByDatumIzdajeAsc(String sifraSodisca);

    List<SeznamCitalcevSigovca> findBySifraSodiscaOrderByDatumIzdajeAsc(String sifraSodisca);

    List<SeznamCitalcevSigovca> findBySifraSodiscaIsNull();

    List<SeznamCitalcevSigovca> findBySifraSodiscaAndDatumVrnitveIsNullAndImeInPriimekIgnoreCase(String sifraSodisca, String imeInPriimek);

    //List<SeznamCitalcevSigovca> findByOznakaContainsIgnoreCaseAndDatumVrnitveIsNull(String oznaka);
    @org.springframework.data.jpa.repository.Query(value = "SELECT c FROM SeznamCitalcevSigovca c where (lower(c.oznaka) LIKE %:iskanje% OR lower(c.imeInPriimek) LIKE %:iskanje%) AND c.datumVrnitve is null")
    List<SeznamCitalcevSigovca> findCitalecSigovcaIzdane(@Param("iskanje") String iskanje);
    
     @org.springframework.data.jpa.repository.Query(value = "SELECT c FROM SeznamCitalcevSigovca c where (lower(c.oznaka) LIKE %:iskanje% OR lower(c.imeInPriimek) LIKE %:iskanje%)")
    List<SeznamCitalcevSigovca> findCitalecSigovcaVse(@Param("iskanje") String iskanje);
     
      @org.springframework.data.jpa.repository.Query(value = "SELECT c FROM SeznamCitalcevSigovca c where (lower(c.oznaka) LIKE %:iskanje% OR lower(c.imeInPriimek) LIKE %:iskanje%) AND c.datumVrnitve is not null")
    List<SeznamCitalcevSigovca> findCitalecSigovcaVrnjene(@Param("iskanje") String iskanje);
      
      List<SeznamCitalcevSigovca> findByImetnikIdAndDatumVrnitveIsNull(long id);
      
      
}
