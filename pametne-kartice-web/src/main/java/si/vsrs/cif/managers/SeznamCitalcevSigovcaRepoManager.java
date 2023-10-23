/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;
import si.vsrs.cif.mod.SifrantSodiscUvoz;
import si.vsrs.cif.mod.datajpa.repository.SeznamCitalcevSigovcaRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class SeznamCitalcevSigovcaRepoManager {

    @Autowired
    SeznamCitalcevSigovcaRepository repository;
    @Autowired
    SifrantSodiscUvozRepoManager sifrantSodiscUvozRepoManager;
    @Autowired
    MetodeHelper metodeHelper;

    @PreAuthorize("hasRole('002')")
    public List<Integer> addCitalecSigovcaFlushAndClear(List<SeznamCitalcevSigovca> citalci) {
        int usp = 0, neusp = 0, neNajdenih = 0;
        List<SeznamCitalcevSigovca> citalciToSave = new ArrayList();
        List<SifrantSodiscUvoz> sifrantSodisc = sifrantSodiscUvozRepoManager.getSifrantSodisc();
        for (int i = 0; i < citalci.size(); i++) {
            SeznamCitalcevSigovca citalec = citalci.get(i);
            String sifraSodisca = metodeHelper.getSifraSodiscaFromNaziv(sifrantSodisc, citalec.getImeOrganizacije());
            List<SeznamCitalcevSigovca> citalciToUpdate = findDuplicate(citalec);
            if (citalciToUpdate == null) {
                if (sifraSodisca == null) {
                    neNajdenih++;
                } else {
                    citalec.setSifraSodisca(sifraSodisca);
                }
                citalciToSave.add(citalec);
                usp++;
            } else {
                for (int j = 0; j < citalciToUpdate.size(); j++) {
                    if (sifraSodisca != null && (citalciToUpdate.get(j).getSifraSodisca() == null || citalciToUpdate.get(j).getSifraSodisca().isEmpty())) {
                        citalciToUpdate.get(j).setSifraSodisca(sifraSodisca);
                        citalciToSave.add(citalciToUpdate.get(j));
                        usp++;
                    } else {
                        neusp++;
                    }

                }

            }
        }

        repository.save(citalciToSave);
        List<Integer> uspNeusp = new ArrayList();
        uspNeusp.add(usp);
        uspNeusp.add(neusp);
        uspNeusp.add(neNajdenih);
        return uspNeusp;
    }

    @PreAuthorize("hasRole('002')")
    public List<SeznamCitalcevSigovca> findDuplicate(SeznamCitalcevSigovca seznamCitalcevSigovca) {
        List<SeznamCitalcevSigovca> retTemp = repository.findByImeInPriimekAndImeOrganizacijeAndTipAndOznakaAndDatumIzdajeAndDatumVrnitve(seznamCitalcevSigovca.getImeInPriimek(), seznamCitalcevSigovca.getImeOrganizacije(), seznamCitalcevSigovca.getTip(), seznamCitalcevSigovca.getOznaka(), seznamCitalcevSigovca.getDatumIzdaje(), seznamCitalcevSigovca.getDatumVrnitve());
        if (retTemp.isEmpty()) {
            return null;
        }
        return retTemp;
    }

    @PreAuthorize("hasRole('002')")
    public List<SeznamCitalcevSigovca> findBySifraSodiscaIsNull() {
        return repository.findBySifraSodiscaIsNull();
    }

    @PreAuthorize("hasRole('002')")
    public List<SeznamCitalcevSigovca> getSeznamCitalcevSigovca(String sifraSodisca, boolean vrnjeno, boolean izdano) {
        List<SeznamCitalcevSigovca> result = new ArrayList();
        //Vrnjeno
        if (vrnjeno && !izdano) {
            //return repository.findBySifraSodiscaAndDatumVrnitveIsNotNullOrderByDatumIzdajeAsc(sifraSodisca);
            result = repository.findBySifraSodiscaAndDatumVrnitveIsNotNullOrderByDatumIzdajeAsc(sifraSodisca);
            if (result.isEmpty()) {
                result = repository.findCitalecSigovcaVrnjene(sifraSodisca.toLowerCase());
            }
        }
        //Izdano
        if (!vrnjeno && izdano) {
            //return repository.findBySifraSodiscaAndDatumVrnitveIsNullOrderByDatumIzdajeAsc(sifraSodisca);
            result = repository.findBySifraSodiscaAndDatumVrnitveIsNullOrderByDatumIzdajeAsc(sifraSodisca);
            if (result.isEmpty()) {
                result = repository.findCitalecSigovcaIzdane(sifraSodisca.toLowerCase());
            }
        } //Vse
        if ((vrnjeno && izdano) || (!vrnjeno && !izdano)) {
            //return repository.findBySifraSodiscaOrderByDatumIzdajeAsc(sifraSodisca);
            result = repository.findBySifraSodiscaOrderByDatumIzdajeAsc(sifraSodisca);
            if (result.isEmpty()) {
                result = repository.findCitalecSigovcaVse(sifraSodisca.toLowerCase());
            }
        }

        return result;
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public List<SeznamCitalcevSigovca> findBySifraSodiscaAndDatumVrnitveIsNullAndImeInPriimekIgnoreCase(String sifraSodisca, String imeInPriimek) {
        return repository.findBySifraSodiscaAndDatumVrnitveIsNullAndImeInPriimekIgnoreCase(sifraSodisca, imeInPriimek);
    }

    @PreAuthorize("hasRole('002')")
    public List<SeznamCitalcevSigovca> findCitalecSigovcaIzdane(String oznaka) {
        return repository.findCitalecSigovcaIzdane(oznaka.toLowerCase());
    }

    @PreAuthorize("hasRole('002')")
    public List<SeznamCitalcevSigovca> findCitalecSigovcaVse(String oznaka) {
        return repository.findCitalecSigovcaVse(oznaka.toLowerCase());
    }

    @PreAuthorize("hasRole('002')")
    public List<SeznamCitalcevSigovca> findCitalecSigovcaVrnjene(String oznaka) {
        return repository.findCitalecSigovcaVrnjene(oznaka.toLowerCase());
    }

    @PreAuthorize("hasRole('002')")
    public SeznamCitalcevSigovca findById(long id) {
        return repository.findById(id);
    }

    @PreAuthorize("hasRole('002')")
    public void update(SeznamCitalcevSigovca citalec) {
        repository.save(citalec);
    }

    @PreAuthorize("hasRole('002')")
    public void add(SeznamCitalcevSigovca citalec) {
        repository.save(citalec);
    }

    //Vrne samo citalce, ki so vrnjeni. Odstrani duplikate.
    @PreAuthorize("hasRole('002')")
    public List<SeznamCitalcevSigovca> findCitalecSigovcaZaDodeljevanje(String oznaka) {
        List<SeznamCitalcevSigovca> citalci = findCitalecSigovcaVrnjene(oznaka);
        HashMap<String, SeznamCitalcevSigovca> temp = new HashMap();
        for (SeznamCitalcevSigovca cit : citalci) {
            if (!temp.containsKey(cit.getOznaka())) {
                temp.put(cit.getOznaka(), cit);
            }
        }
        citalci = new ArrayList(temp.values());
        for (int i = 0; i < citalci.size(); i++) {
            if (!findCitalecSigovcaIzdane(citalci.get(i).getOznaka()).isEmpty()) {
                citalci.remove(i);
                i--;
            }
        }
        return citalci;
    }
    
     @PreAuthorize("hasRole('002')")
    public List<SeznamCitalcevSigovca> findByImetnikIdAndDatumVrnitveIsNull(long id) {
        return repository.findByImetnikIdAndDatumVrnitveIsNull(id);
    }
}
