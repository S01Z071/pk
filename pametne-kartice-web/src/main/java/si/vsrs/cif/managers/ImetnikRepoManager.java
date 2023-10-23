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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.mod.AdminIskanjeStatusImetnik;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.datajpa.repository.ImetnikRepository;
import si.vsrs.cif.mod.web.Uporabnik;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class ImetnikRepoManager {

    @Autowired
    ImetnikRepository repository;
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    StatusImetnikRepoManager statusImetnikRepoManager;
    @Autowired
    NastavitveHelper nastavitveHelper;
    @Autowired
    CertifikatRepoManager certifikatRepoManager;

    @PreAuthorize("hasAnyRole('001,002')")
    public void addImetnik(Imetnik imetnik, long idZ) {
        //Nov imetnik
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(idZ);
        if (imetnik.getId() == null) {
            long d = new Date().getTime();
            String s = Long.toString(d);
            s = new StringBuffer(s).reverse().toString();
            imetnik.setCrtnaKoda("I" + zahtevek.getSifraOrganizacije() + s);
            imetnik.setSifraOrganizacije(zahtevek.getSifraOrganizacije());
            zahtevek.getImetniki().add(imetnik);
            repository.save(imetnik);
            zahtevekRepoManager.updateZahtevek(zahtevek);

        } else {
            repository.save(imetnik);
        }
    }

    public void addImetnikPrenos(Imetnik imetnik) {
        long d = new Date().getTime();
        String s = Long.toString(d);
        s = new StringBuffer(s).reverse().toString();
        imetnik.setCrtnaKoda("I" + imetnik.getSifraOrganizacije() + s);
        imetnik.setSifraOrganizacije(imetnik.getSifraOrganizacije());
        repository.save(imetnik);
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public Imetnik getImetnik(long id) {
        return repository.findById(id);
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public void updateImetnik(Imetnik imetnik) {
        repository.save(imetnik);
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public void deleteImetnik(long id) {
        Imetnik imetnik = getImetnik(id);
        imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("04"));
        updateImetnik(imetnik);
    }

    @PreAuthorize("hasRole('002')")
    public List<Imetnik> getImetnikeSearchByImePriimek(String iskano, Pageable p) {
        String[] isk = iskano.split(" ");
        String ime = "";
        String priimek = "";
        if (isk.length >= 1 && !isk[0].isEmpty()) {
            ime = isk[0];
        }
        if (isk.length == 2 && !isk[1].isEmpty()) {
            priimek = isk[1];
        } else {
            priimek = ime;
        }
        return repository.findByImeContainsIgnoreCaseOrPriimekContainsIgnoreCase(ime, priimek, p).getContent();

    }

    @PreAuthorize("hasAnyRole('001,002')")
    public List<Imetnik> getImetnike(int pageNum, Uporabnik uporabnik) {
        Pageable p = new PageRequest(pageNum - 1, nastavitveHelper.getPrikazovNaStran());
          return repository.findBySifraOrganizacijeAndStatusSifraSImIsNotInOrderByIdDesc(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId(), Arrays.asList(new String[]{"04","10"}), p).getContent();
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public int getImetnikCount(Uporabnik uporabnik) {
        //String crtnaKoda = "I" + uporabnik.getIzbranaVlogaSodisce().getSodisce().getId() + "%";
        //int count = repository.countByCrtnaKodaStartingWithAndStatusSifraSImNot(crtnaKoda, "04");
        int count = repository.countBySifraOrganizacijeAndStatusSifraSImIsNotIn(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId(), Arrays.asList(new String[]{"04","10"}));
        return count;
    }

    public List<Imetnik> getImetnikeSearchUser(String iskano, Uporabnik uporabnik, int pageNum) {
        Pageable p = new PageRequest(pageNum - 1, nastavitveHelper.getPrikazovNaStran());
        // String crtnaKoda = "I" + uporabnik.getIzbranaVlogaSodisce().getSodisce().getId() + "%";
        // return repository.getImetnikSearchUser(crtnaKoda, "04", iskano.toLowerCase(), p);
        return repository.getImetnikSearchUser(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId(), Arrays.asList(new String[]{"04","10"}), iskano.toLowerCase(), p);
    }

    public int countImetnikeSearchUser(String iskano, Uporabnik uporabnik) {
        // String crtnaKoda = "I" + uporabnik.getIzbranaVlogaSodisce().getSodisce().getId() + "%";
        // return repository.countImetnikSearchUser(crtnaKoda, "04", iskano.toLowerCase());
        return repository.countImetnikSearchUser(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId(), Arrays.asList(new String[]{"04","10"}), iskano.toLowerCase());
    }

    public List<Imetnik> findByEnaslovOrDavcnaSt(String eNaslov, String davcnaSt) {
        return repository.findByENaslovIgnoreCaseOrDavcna(eNaslov, davcnaSt);
    }

    public List<Long> getIdsByENaslovOrDavcna(String eNaslov, String davcnaSt) {
        return repository.getIdsByENaslovOrDavcna(davcnaSt, eNaslov);
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public List<Imetnik> getImetnikByENaslov(String eNaslov) {
        return repository.findByENaslovIgnoreCase(eNaslov);
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public Imetnik findByENaslovAndStatusSifraSIm(String eNaslov, String statusSifraNot) {
        return repository.findByENaslovIgnoreCaseAndStatusSifraSIm(eNaslov, statusSifraNot);
    }

    @PreAuthorize("hasRole('002')")
    public Imetnik findByCrtnaKoda(String crtnaKoda) {
        return repository.findByCrtnaKodaIgnoreCase(crtnaKoda);
    }

    @PreAuthorize("hasRole('002')")
    public int countByStatusSifra(String statusSifra) {
        return repository.countByStatusSifra(statusSifra);
    }

    @PreAuthorize("hasRole('002')")
    public List<Imetnik> getImetnikeSearchAdmin(String iskano) {
        iskano = iskano.toLowerCase();
        String[] imeInPriimek = iskano.split(" ");
        List<Imetnik> imetniki;
        if (imeInPriimek.length > 1) {
            imetniki = repository.getImetnikeSearchAdmin(iskano, imeInPriimek[0], imeInPriimek[imeInPriimek.length - 1]);
        } else {
            imetniki = repository.getImetnikeSearchAdmin(iskano, iskano, iskano);
        }
        return imetniki;
    }

    public List<Imetnik> getImetnikeSearchAdminPages(String iskano, int pageNum, AdminIskanjeStatusImetnik status) {
        if (!status.allDeselected()) {
            Pageable p = new PageRequest(pageNum - 1, nastavitveHelper.getPrikazovNaStran());
            iskano = iskano.toLowerCase();
            String[] imeInPriimek = iskano.split(" ");
            List<Imetnik> imetniki;
            if (imeInPriimek.length > 1) {
                imetniki = repository.getImetnikeSearchAdminPages(iskano, imeInPriimek[0], imeInPriimek[imeInPriimek.length - 1], status.returnList(), p);
            } else {
                imetniki = repository.getImetnikeSearchAdminPages(iskano, iskano, iskano, status.returnList(), p);
            }
            return imetniki;
        }
        return new ArrayList();
    }

    public int countImetnikeSearchAdmin(String iskano, AdminIskanjeStatusImetnik status) {
        if (!status.allDeselected()) {
            iskano = iskano.toLowerCase();
            String[] imeInPriimek = iskano.split(" ");
            int count = 0;
            if (imeInPriimek.length > 1) {
                count = repository.countImetnikeSearchAdmin(iskano, imeInPriimek[0], imeInPriimek[imeInPriimek.length - 1], status.returnList());
            } else {
                count = repository.countImetnikeSearchAdmin(iskano, iskano, iskano, status.returnList());
            }
            return count;
        }
        return 0;
    }

    @PreAuthorize("hasRole('002')")
    public List<Imetnik> getImetnikeAdmin(int pageNum, AdminIskanjeStatusImetnik status) {
        if (!status.allDeselected()) {
            Pageable p = new PageRequest(pageNum - 1, nastavitveHelper.getPrikazovNaStran());
            return repository.findByStatusSifraSImIn(status.returnList(), p).getContent();
        } else {
            return new ArrayList();
        }
    }

    @PreAuthorize("hasRole('002')")
    public int getImetnikeCountAdmin(AdminIskanjeStatusImetnik status) {
        if (!status.allDeselected()) {
            // int count = repository.findByStatusSifraSImIn(status.returnList()).size();     
            //!!!!
            int count1 = repository.countByStatusSifraSImIn(status.returnList());
            return count1;
        } else {
            return 0;
        }
    }

    public boolean isImetnikPrenesen(Long id) {
        Boolean isPrenesen =  repository.isImetnikPrenesen(id);
        if(isPrenesen ==null || !isPrenesen){
            return false;
        }
        return true;
    }

}
