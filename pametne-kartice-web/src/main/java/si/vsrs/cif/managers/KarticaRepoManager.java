/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.datajpa.repository.KarticaRepository;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class KarticaRepoManager {

    @Autowired
    KarticaRepository repository;
    @Autowired
    ImetnikRepoManager imetnikRepoManager;
    @Autowired
    NastavitveHelper nastavitveHelper;

    @PreAuthorize("hasRole('002')")
    public Kartica findById(long id) {
        return repository.findById(id);
    }

    @PreAuthorize("hasRole('002')")
    public void update(Kartica kartica) {
        repository.save(kartica);

    }

    @PreAuthorize("hasRole('002')")
    public void addKartica(Kartica kartica) {
        repository.save(kartica);

    }

    @PreAuthorize("hasAnyRole('001,002')")
    public List<Kartica> getKarticaByImetnikID(long id) {
        return repository.findByImetnikId(id);
    }

    @PreAuthorize("hasRole('002')")
    public Kartica getKarticaByID(long id) {
        return repository.findById(id);
    }

    @PreAuthorize("hasRole('002')")
    public List<Kartica> findBySerijskaStevilkaKartice(String serijskaSt) {
        return repository.findBySerijskaStevilkaKarticeIgnoreCase(serijskaSt);
    }

    @PreAuthorize("hasRole('002')")
    public int countKarticaWithImetnik() {
        return repository.countKarticaWithImetnik();
    }

    public List<Kartica> getKarticeWithoutImetnik() {
        return repository.getKarticeWithoutImetnik();
    }

    @PreAuthorize("hasRole('002')")
    public List<Kartica> getKarticeWithoutCertifikat() {
        return repository.getKarticeWithoutCertifikat();
    }

    public List<Kartica> findBySerijskaStevilkaKarticeOrBarcode(String serijskaSt, String barCode) {
        return repository.findBySerijskaStevilkaKarticeIgnoreCaseOrBarcodeIgnoreCase(serijskaSt, barCode);
    }

    @PreAuthorize("hasRole('002')")
    public List<Kartica> getKartice(int pageNum) {
        Sort sort = new Sort(Sort.Direction.ASC, "barcode");
        Pageable p = new PageRequest(pageNum - 1, nastavitveHelper.getPrikazovNaStran(),sort);
        return repository.findAll(p).getContent();
    }

    public List<Kartica> getKarticeByDatumVrnitve(int pageNum, Date datumOd, Date datumDo) {
        Pageable p = new PageRequest(pageNum - 1, nastavitveHelper.getPrikazovNaStran());
        return repository.getKarticeByDatumVrnitve(datumOd, datumDo, p).getContent();
        //return repository.findAll(p).getContent();        
    }

    @PreAuthorize("hasRole('002')")
    public int getKarticeCount() {
        return (int) repository.count();
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public List<Kartica> getKarticeZaKodo(String iskano, Uporabnik uporabnik) {
        String sodisceID = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        iskano = iskano.toLowerCase();
        String[] imeInPriimek = iskano.split(" ");
        List<Kartica> kartice;
        if (imeInPriimek.length > 1) {
            if (vloga.compareTo("001") == 0) {
                kartice = repository.findKarticaPoSodiscihZaKodo(iskano, imeInPriimek[0], imeInPriimek[imeInPriimek.length - 1],  sodisceID);
            } else {
                kartice = repository.findKarticaVse(iskano, imeInPriimek[0], imeInPriimek[imeInPriimek.length - 1]);
            }
        } else {
            if (vloga.compareTo("001") == 0) {
                kartice = repository.findKarticaPoSodiscihZaKodo(iskano, iskano, iskano, sodisceID);
            } else {
                kartice = repository.findKarticaVse(iskano, iskano, iskano);
            }
        }
        return kartice;
    }

    @PreAuthorize("hasRole('002')")
    public List<Kartica> findKartica(String oznaka, Boolean datumVrnitve) {
        oznaka = oznaka.toLowerCase();
        String[] imeInPriimek = oznaka.split(" ");
        List<Kartica> kartice;
        if (imeInPriimek.length > 1) {
            if (datumVrnitve) {
                kartice = repository.findKarticaDatumVrnitveNull(oznaka, imeInPriimek[0], imeInPriimek[imeInPriimek.length - 1]);
            } else {
                kartice = repository.findKarticaVse(oznaka, imeInPriimek[0], imeInPriimek[imeInPriimek.length - 1]);
            }
        } else {
            if (datumVrnitve) {
                kartice = repository.findKarticaDatumVrnitveNull(oznaka, oznaka, oznaka);
            } else {
                kartice = repository.findKarticaVse(oznaka, oznaka, oznaka);
            }
        }
        return kartice;
    }

    public List<Kartica> findKarticaByDatumVrnitve(String oznaka, String datumOd, String datumDo) {

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date datumOdD = null;
        Date datumDoD = null;
        try {
            datumOdD = df.parse(datumOd);
            datumDoD = df.parse(datumDo);
        } catch (ParseException ex) {
            Logger.getLogger(KarticaRepoManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        oznaka = oznaka.toLowerCase();
        String[] imeInPriimek = oznaka.split(" ");
        List<Kartica> kartice;
        if (imeInPriimek.length > 1) {
            if (nastavitveHelper.getOkolje().compareTo("RAZVOJ") == 0) {
                kartice = repository.findKarticaVseByDatumVrnitveRazvoj(oznaka, imeInPriimek[0], imeInPriimek[imeInPriimek.length - 1], datumOdD, datumDoD);
            } else {
                kartice = repository.findKarticaVseByDatumVrnitve(oznaka, imeInPriimek[0], imeInPriimek[imeInPriimek.length - 1], datumOd, datumDo);
            }
        } else {
            if (nastavitveHelper.getOkolje().compareTo("RAZVOJ") == 0) {
                kartice = repository.findKarticaVseByDatumVrnitveRazvoj(oznaka, oznaka, oznaka, datumOdD, datumDoD);
            } else {
                kartice = repository.findKarticaVseByDatumVrnitve(oznaka, oznaka, oznaka, datumOd, datumDo);
            }
        }
        return kartice;
    }
}
