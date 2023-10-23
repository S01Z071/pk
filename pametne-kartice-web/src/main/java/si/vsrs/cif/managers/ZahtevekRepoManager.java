/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.mod.AdminIskanjeStatus;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Opomba;
import si.vsrs.cif.mod.Status;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.datajpa.repository.ZahtevekRepository;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class ZahtevekRepoManager {

    @Autowired
    ZahtevekRepository repository;
    @Autowired
    StatusRepoManager statusRepoManager;
    @Autowired
    NastavitveHelper nastavitveHelper;
    /*--*/
    //@PreAuthorize("hasAnyRole('001,002')")

    public void addZahtevek(Zahtevek zahtevek) {
        //Nov zahtevek
        if (zahtevek.getId() == null) {
            long d = new Date().getTime();
            String s = Long.toString(d);
            s = new StringBuffer(s).reverse().toString();
            zahtevek.setCrtnaKoda("O" + zahtevek.getSifraOrganizacije() + s);
            repository.save(zahtevek);
        } else {
            //Zahtevek ze obstaja. Posebej prepise imetnike
            Zahtevek zahtevek1 = getZahtevek(zahtevek.getId());
            Status s = zahtevek1.getStatus();
            List<Imetnik> imetniki = zahtevek1.getImetniki();
            Set<Opomba> opombe = zahtevek1.getOpombe();
            zahtevek1.update(zahtevek);
            zahtevek1.setImetniki(imetniki);
            zahtevek1.setOpombe(opombe);
            zahtevek1.setStatus(s); //Treba takole narest, drugace se status pobrise
            repository.save(zahtevek1);
        }
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public List<Zahtevek> getZahtevke(int pageNum, Uporabnik uporabnik) {
        Pageable p = new PageRequest(pageNum - 1, nastavitveHelper.getPrikazovNaStran());
        return repository.getZahtevekBySodisce(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId().toLowerCase(), p).getContent();
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public Zahtevek getZahtevek(long id) {
        return repository.findById(id);
    }

   
    @PreAuthorize("hasAnyRole('001,002')")
    public void updateZahtevek(Zahtevek zahtevek) {
        repository.save(zahtevek);
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public int getZahtevekCount(Uporabnik uporabnik) {
        return repository.getZahtevekCount(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId());
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public List<Zahtevek> getZahtevkeOpomba(Uporabnik uporabnik) {
        Pageable p = new PageRequest(0, 10);
        if (uporabnik.getIzbranaVlogaSodisce().getVloga().getId().compareTo("001") == 0) {
            return repository.getZahtevkeOpomba(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId(), p).getContent();
        } else {
            return repository.getZahtevkeOpombaAdmin(p).getContent();
        }
    }

    //5926535 8979323846 2643383279 5028841971 6939937510 5820974944 5923078164 0628620899 8628034825 3421170679
    @PreAuthorize("hasRole('002')")
    public List<Zahtevek> getZahtevkeSearchByCrtnaKoda(String iskano, Pageable p) {
        return repository.findByCrtnaKodaContainsIgnoreCase(iskano, p).getContent();
    }

    @PreAuthorize("hasRole('002')")
    public Long getZahtevekIDFromImetnikID(long imetnikID) {
        return repository.getZahtevekIDFromImetnikID(imetnikID);
    }

    @PreAuthorize("hasRole('002')")
    public List<Zahtevek> getZahtevkeAdmin(int pageNum, AdminIskanjeStatus status) {
        if (!status.allDeselected()) {
            Pageable p = new PageRequest(pageNum - 1, nastavitveHelper.getPrikazovNaStran());
            return repository.findByStatusSifraIn(status.returnList(), p).getContent();
        } else {
            return new ArrayList();
        }
    }

    @PreAuthorize("hasRole('002')")
    public int getZahtevekCountAdmin(AdminIskanjeStatus status) {
        if (!status.allDeselected()) {
           // int count = repository.findByStatusSifraIn(status.returnList()).size();
            int count = repository.countByStatusSifraIn(status.returnList());          
            return count;
        } else {
            return 0;
        }
    }

    @PreAuthorize("hasRole('002')")
    public List<Zahtevek> findByStatusSifra(String sifra) {
        return repository.findByStatusSifra(sifra);
    }

    @PreAuthorize("hasRole('002')")
    public Zahtevek findByCrtnaKoda(String crtnaKoda) {
        return repository.findByCrtnaKodaIgnoreCase(crtnaKoda);
    }

    @PreAuthorize("hasRole('002')")
    public int countByStatusSifra(String statusSifra) {
        return repository.countByStatusSifra(statusSifra);
    }
    
     public String getSifraOrganizacijeById(Long id) {
        return repository.getSifraOrganizacijeById(id);
    }
    
    
}
