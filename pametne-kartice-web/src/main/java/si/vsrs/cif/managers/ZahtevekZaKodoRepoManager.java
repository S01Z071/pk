/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.mod.ZahtevekZaKodo;
import si.vsrs.cif.mod.datajpa.repository.ZahtevekZaKodoRepository;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author Uporabnik
 */
@Service
@Transactional
public class ZahtevekZaKodoRepoManager {

    @Autowired
    ZahtevekZaKodoRepository repository;
    @Autowired
    StatusRepoManager statusRepoManager;
    @Autowired
    NastavitveHelper nastavitveHelper;

    public ZahtevekZaKodo getZahtevekZaKodo(long id) {
        return repository.findById(id);
    }

    public void addZahtevekZaKodo(ZahtevekZaKodo zahtevekZaKodo) {
        zahtevekZaKodo.setStatus(statusRepoManager.getStatus("01"));
        long d = new Date().getTime();
        String s = Long.toString(d);
        s = new StringBuffer(s).reverse().toString();
        zahtevekZaKodo.setCrtnaKoda("K" + zahtevekZaKodo.getSifraOrganizacije() + s);
        repository.save(zahtevekZaKodo);
    }

    public void updateZahtevekZaKodo(ZahtevekZaKodo zahtevekZaKodo) {
        repository.save(zahtevekZaKodo);
    }

    public List<ZahtevekZaKodo> getZahtevkeZaKodo(int pageNum, Uporabnik uporabnik) {
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

    public int getZahtevkeZaKodoCount(Uporabnik uporabnik) {
        String sodisceID = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (vloga.compareTo("001") == 0) {
            return (int) repository.getZahteveZaKodokCount(sodisceID);
        } else {
            return (int) repository.count();
        }
    }

    public List<ZahtevekZaKodo> findByStatusSifraAndSerijskaStevilka(String serijskaStevilka) {
        return repository.findByStatusSifraAndSerijskaStevilka("01", serijskaStevilka);
    }

    public ZahtevekZaKodo findByCrtnaKoda(String crtnaKoda) {
        return repository.findByCrtnaKodaIgnoreCase(crtnaKoda);
    }

    public List<ZahtevekZaKodo> findByImetnikId(Long imetnikId) {
        return repository.findByImetnikID(imetnikId);
    }
}
