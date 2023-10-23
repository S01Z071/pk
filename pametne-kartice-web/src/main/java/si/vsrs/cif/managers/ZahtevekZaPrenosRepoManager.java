/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.mod.ZahtevekZaPrenos;
import si.vsrs.cif.mod.datajpa.repository.ZahtevekZaPrenosRepository;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author Uporabnik
 */
@Service
@Transactional
public class ZahtevekZaPrenosRepoManager {

    @Autowired
    ZahtevekZaPrenosRepository repository;
    @Autowired
    StatusRepoManager statusRepoManager;
    @Autowired
    NastavitveHelper nastavitveHelper;

    public ZahtevekZaPrenos findById(long id) {
        return repository.findById(id);
    }

    public void addZahtevekZaPrenos(ZahtevekZaPrenos zahtevekZaPrenos) {
        zahtevekZaPrenos.setStatus(statusRepoManager.getStatus("01"));
        long d = new Date().getTime();
        String s = Long.toString(d);
        s = new StringBuffer(s).reverse().toString();
        zahtevekZaPrenos.setCrtnaKoda("T" + zahtevekZaPrenos.getSifraOrganizacije() + s);
        repository.save(zahtevekZaPrenos);
    }

    public void updateZahtevekZaPrenos(ZahtevekZaPrenos zahtevekZaPrenos) {
        repository.save(zahtevekZaPrenos);
    }

    public List<ZahtevekZaPrenos> getZahtevkeZaPrenos(int pageNum, Uporabnik uporabnik) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable p = new PageRequest(pageNum - 1, nastavitveHelper.getPrikazovNaStran(), sort);
        String sodisceID = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (vloga.compareTo("001") == 0) {
            return repository.findBySifraOrganizacijeAndStatusSifraIsNotOrderByIdDesc(sodisceID, "04", p);
        } else {
            return repository.findAll(p).getContent();
        }
    }

    public int getZahtevkeZaPrenosCount(Uporabnik uporabnik) {
        String sodisceID = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (vloga.compareTo("001") == 0) {
            return (int) repository.getZahteveZaPrenosCount(sodisceID);
        } else {
            return (int) repository.count();
        }
    }

    public List<ZahtevekZaPrenos> findByStatusSifraAndSerijskaStevilka(String serijskaStevilka) {
        return repository.findByStatusSifraAndSerijskaStevilka("01", serijskaStevilka);
    }

    public boolean zahtevekExistsAndNotCompleted(String serijskaStevilka) {
        return (repository.countBySerijskaStevilkaAndStatusSifraNotIn(serijskaStevilka, Arrays.asList(new String[]{"04", "07"})) > 0);
    }

    public List<ZahtevekZaPrenos> findBySerijskaStevilkaAndStatusSifraNot(String serijskaStevilka) {
        return repository.findBySerijskaStevilkaAndStatusSifraNot(serijskaStevilka, "04");
    }

    public ZahtevekZaPrenos findByCrtnaKoda(String crtnaKoda) {
        return repository.findByCrtnaKodaIgnoreCase(crtnaKoda);
    }

    public Long getZahtevekZaPrenosIdsByImetnikId(long imetnikId) {
        return repository.getZahtevekZaPrenosIdsByImetnikId(imetnikId);
    }

    public ZahtevekZaPrenos findByImetnikId(long imetnikId) {
        return repository.findByImetnikId(imetnikId);
    }

    public long getZahtevekIdByNovImetnikId(long imetnikId) {
        Long zahtevekId = null;
        Long imetnikIdTemp = imetnikId;
        while (zahtevekId == null) {
            ZahtevekZaPrenos zahtevekZaPrenos = repository.getByNovImetnikId(imetnikIdTemp);
            if (zahtevekZaPrenos == null) {
                throw new RuntimeException("Napaka v poslovni logiki!");
            }
            if (zahtevekZaPrenos.getZahtevekID() != null) {
                return zahtevekZaPrenos.getZahtevekID();
            }
            imetnikIdTemp = zahtevekZaPrenos.getImetnikID();

        }
        throw new RuntimeException("Napaka v poslovni logiki!");


    }

    public String getSifraOrganizacijeById(Long id) {
        return repository.getSifraOrganizacijeById(id);
    }
}
