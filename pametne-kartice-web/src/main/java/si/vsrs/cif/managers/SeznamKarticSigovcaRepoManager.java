/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.mod.SeznamKarticSigovca;
import si.vsrs.cif.mod.SifrantSodiscUvoz;
import si.vsrs.cif.mod.datajpa.repository.SeznamKarticSigovcaRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class SeznamKarticSigovcaRepoManager {

    @Autowired
    SeznamKarticSigovcaRepository repository;
    @Autowired
    SifrantSodiscUvozRepoManager sifrantSodiscUvozRepoManager;
    @Autowired
    MetodeHelper metodeHelper;

    @PreAuthorize("hasRole('002')")
    public List<Integer> addKarticaSigovcaFlushAndClear(List<SeznamKarticSigovca> kartice) {
        int usp = 0, neusp = 0, neNajdenih = 0;
        List<SeznamKarticSigovca> karticeToSave = new ArrayList();
        List<SifrantSodiscUvoz> sifrantSodisc = sifrantSodiscUvozRepoManager.getSifrantSodisc();
        for (int i = 0; i < kartice.size(); i++) {
            SeznamKarticSigovca kartica = kartice.get(i);
            String sifraSodisca = metodeHelper.getSifraSodiscaFromNaziv(sifrantSodisc, kartica.getImeOrganizacije());
            List<SeznamKarticSigovca> karticeToUpdate = findDuplicate(kartica);
            if (karticeToUpdate == null) {
                if (sifraSodisca == null) {
                    neNajdenih++;
                } else {
                    kartica.setSifraSodisca(sifraSodisca);
                }
                karticeToSave.add(kartica);
                usp++;
            } else {
                for (int j = 0; j < karticeToUpdate.size(); j++) {
                    if (sifraSodisca != null && (karticeToUpdate.get(j).getSifraSodisca() == null || karticeToUpdate.get(j).getSifraSodisca().isEmpty())) {
                        karticeToUpdate.get(j).setSifraSodisca(sifraSodisca);
                        karticeToSave.add(karticeToUpdate.get(j));
                        usp++;
                    } else {
                        neusp++;
                    }

                }
            }
        }

        repository.save(karticeToSave);
        List<Integer> uspNeusp = new ArrayList();
        uspNeusp.add(usp);
        uspNeusp.add(neusp);
        uspNeusp.add(neNajdenih);
        return uspNeusp;
    }

    @PreAuthorize("hasRole('002')")
    public List<SeznamKarticSigovca> findDuplicate(SeznamKarticSigovca seznamKarticSigovca) {
        List<SeznamKarticSigovca> retTemp = repository.findByImeInPriimekAndImeOrganizacijeAndTipAndOznakaAndDatumIzdajeAndDatumVrnitve(seznamKarticSigovca.getImeInPriimek(), seznamKarticSigovca.getImeOrganizacije(), seznamKarticSigovca.getTip(), seznamKarticSigovca.getOznaka(), seznamKarticSigovca.getDatumIzdaje(), seznamKarticSigovca.getDatumVrnitve());
        if (retTemp.isEmpty()) {
            return null;
        }
        return retTemp;
    }

    @PreAuthorize("hasRole('002')")
    public List<SeznamKarticSigovca> findBySifraSodiscaIsNull() {
        return repository.findBySifraSodiscaIsNull();
    }

    @PreAuthorize("hasRole('002')")
    public List<SeznamKarticSigovca> getSeznamKarticSigovca(String sifraSodisca, boolean vrnjeno, boolean izdano) {
        List<SeznamKarticSigovca> result = new ArrayList();        
        //Vrnjeno
        if (vrnjeno && !izdano) {
            // return repository.findBySifraSodiscaAndDatumVrnitveIsNotNullOrderByDatumIzdajeAsc(sifraSodisca);
            result = repository.findBySifraSodiscaAndDatumVrnitveIsNotNullOrderByDatumIzdajeAsc(sifraSodisca);
            if (result.isEmpty()) {
                result = repository.findKarticaSigovcaVrnjene(sifraSodisca.toLowerCase());
            }
        }
        //Izdano
        if (!vrnjeno && izdano) {
            // return repository.findBySifraSodiscaAndDatumVrnitveIsNullOrderByDatumIzdajeAsc(sifraSodisca);
            result = repository.findBySifraSodiscaAndDatumVrnitveIsNullOrderByDatumIzdajeAsc(sifraSodisca);
            if (result.isEmpty()) {
                result = repository.findKarticaSigovcaIzdane(sifraSodisca.toLowerCase());
            }
        } //Vse
        if ((vrnjeno && izdano) || (!vrnjeno && !izdano)) {
            //return repository.findBySifraSodiscaOrderByDatumIzdajeAsc(sifraSodisca);
            result = repository.findBySifraSodiscaOrderByDatumIzdajeAsc(sifraSodisca);
            if (result.isEmpty()) {
                result = repository.findKarticaSigovcaVse(sifraSodisca.toLowerCase());
            }
        }
        return result;

    }

    @PreAuthorize("hasAnyRole('001,002')")
    public List<SeznamKarticSigovca> findBySifraSodiscaAndDatumVrnitveIsNullAndImeInPriimekIgnoreCase(String sifraSodisca, String imeInPriimek) {
        return repository.findBySifraSodiscaAndDatumVrnitveIsNullAndImeInPriimekIgnoreCase(sifraSodisca, imeInPriimek);
    }

    @PreAuthorize("hasRole('002')")
    public List<SeznamKarticSigovca> findKarticaSigovcaIzdane(String oznaka) {
        return repository.findKarticaSigovcaIzdane(oznaka.toLowerCase());
    }

    @PreAuthorize("hasRole('002')")
    public SeznamKarticSigovca findById(long id) {
        return repository.findById(id);
    }

    @PreAuthorize("hasRole('002')")
    public void update(SeznamKarticSigovca kartica) {
        repository.save(kartica);
    }

    @PreAuthorize("hasRole('002')")
    public List<SeznamKarticSigovca> findKarticaSigovcaVse(String oznaka) {
        return repository.findKarticaSigovcaVse(oznaka.toLowerCase());
    }

    public List<SeznamKarticSigovca> findKarticaSigovcaVrnjene(String oznaka) {
        return repository.findKarticaSigovcaVrnjene(oznaka.toLowerCase());
    }
}
