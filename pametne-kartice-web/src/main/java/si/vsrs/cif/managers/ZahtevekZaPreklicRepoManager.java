/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.mod.Status;
import si.vsrs.cif.mod.ZahtevekZaPreklic;
import si.vsrs.cif.mod.datajpa.repository.ZahtevekZaPreklicRepository;
import si.vsrs.cif.mod.web.Sodisce;
import si.vsrs.cif.mod.web.Uporabnik;
import si.vsrs.cif.mod.web.VlogaSodisce;

/**
 *
 * @author Uporabnik
 */
@Service
@Transactional
public class ZahtevekZaPreklicRepoManager {

    @Autowired
    ZahtevekZaPreklicRepository repository;
    @Autowired
    StatusRepoManager statusRepoManager;
    @Autowired
    NastavitveHelper nastavitveHelper;
    @Autowired
    MetodeHelper metodeHelper;
    @Autowired
    StatusLogPreklicRepoManager statusLogPreklicRepoManager;

    public ZahtevekZaPreklic findById(long id) {
        return repository.findById(id);
    }

    public void addZahtevekZaPreklic(ZahtevekZaPreklic zahtevekZaPreklic) {
        zahtevekZaPreklic.setStatus(statusRepoManager.getStatus("01"));
        long d = new Date().getTime();
        String s = Long.toString(d);
        s = new StringBuffer(s).reverse().toString();
        zahtevekZaPreklic.setCrtnaKoda("P" + zahtevekZaPreklic.getSifraOrganizacije() + s);
        repository.save(zahtevekZaPreklic);
    }

    public void updateZahtevekZaPreklic(ZahtevekZaPreklic zahtevekZaPreklic) {
        repository.save(zahtevekZaPreklic);
    }

    public List<ZahtevekZaPreklic> getZahtevkeZaPreklic(int pageNum, Uporabnik uporabnik) {
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC,"id");
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"status.sifra");
        List<Sort.Order> orders = new ArrayList();
        orders.add(order);
        orders.add(order1);        
        Sort sort = new Sort(orders);//new Sort(Sort.Direction.DESC, "id");
        Pageable p = new PageRequest(pageNum - 1, nastavitveHelper.getPrikazovNaStran(), sort);
        String sodisceID = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (vloga.compareTo("001") == 0) {
            return repository.findBySifraOrganizacijeAndStatusSifraIsNotOrderByIdDesc(sodisceID, "04", p);
        } else {
            return repository.findAll(p).getContent();
        }
    }

    public int getZahtevkeZaPreklicCount(Uporabnik uporabnik) {
        String sodisceID = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        if (vloga.compareTo("001") == 0) {
            return (int) repository.getZahteveZaPreklicCount(sodisceID);
        } else {
            return (int) repository.count();
        }
    }

    public List<ZahtevekZaPreklic> findByStatusSifraAndSerijskaStevilka(String serijskaStevilka) {
        return repository.findByStatusSifraAndSerijskaStevilka("01", serijskaStevilka);
    }

    public List<ZahtevekZaPreklic> findBySerijskaStevilkaAndStatusSifraNot(String serijskaStevilka) {
        return repository.findBySerijskaStevilkaAndStatusSifraNot(serijskaStevilka, "04");
    }

    public ZahtevekZaPreklic findByCrtnaKoda(String crtnaKoda) {
        return repository.findByCrtnaKodaIgnoreCase(crtnaKoda);
    }

    public List<Long> getZahtevekZaPreklicIdsByImetnikId(Long imetnikId) {
        return repository.getZahtevekZaPreklicIdsByImetnikId(imetnikId);
    }

    public List<ZahtevekZaPreklic> findByImetnikID(Long imetnikId) {
        return repository.findByImetnikID(imetnikId);
    }

    public void updateStatusToDokoncajByCertifikatIdIfExists(Long certifikatId) {
        ZahtevekZaPreklic zahtevekZaPreklic = repository.findByCertifikatIDAndStatusSifraIsNot(certifikatId,"04");
        if (zahtevekZaPreklic != null) {
            String statusPrej = zahtevekZaPreklic.getStatus().getSifra();
            zahtevekZaPreklic.setStatus(statusRepoManager.getStatus("08"));            
            updateZahtevekZaPreklic(zahtevekZaPreklic);
            Uporabnik uporabnik = new Uporabnik();
            uporabnik.setKadrovskStevilka("AVTOMAT");
            uporabnik.setIzbranaVlogaSodisce(new VlogaSodisce());
            uporabnik.getIzbranaVlogaSodisce().setSodisce(new Sodisce());
            uporabnik.getIzbranaVlogaSodisce().getSodisce().setId("S01");
            metodeHelper.insertInStatusLogZaPreklic(uporabnik, statusPrej, "08", zahtevekZaPreklic.getId(), statusLogPreklicRepoManager, "Avtomatsko spremenjen status, ker je certifikat preklican.");
        }
    }
}
