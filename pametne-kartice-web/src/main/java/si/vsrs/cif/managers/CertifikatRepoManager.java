/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.helper.MetodeHelper;
import si.vsrs.cif.helper.NastavitveHelper;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Kartica;
import si.vsrs.cif.mod.SifrantSodiscUvoz;
import si.vsrs.cif.mod.ZahtevekZaPrenos;
import si.vsrs.cif.mod.datajpa.repository.CertifikatRepository;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
public class CertifikatRepoManager {

    @Autowired
    CertifikatRepository repository;
    @Autowired
    ImetnikRepoManager imetnikRepoManager;
    @Autowired
    MetodeHelper metodeHelper;
    @Autowired
    StatusCertifikatRepoManager statusCertifikatRepoManager;
    @Autowired
    ZahtevekRepoManager zahtevekRepoManager;
    @Autowired
    SifrantSodiscUvozRepoManager sifrantSodiscUvozRepoManager;
    @Autowired
    NastavitveHelper nastavitveHelper;

    public void addCertifikat(Certifikat certifikat) {
        repository.save(certifikat);
    }

    public List<Integer> addCertifikatFlushAndClear(List<Certifikat> certifikati) {
        int usp = 0, neusp = 0, neNajdenih = 0;
        List<Certifikat> certifikatiToSave = new ArrayList();
        List<SifrantSodiscUvoz> sifrantSodisc = sifrantSodiscUvozRepoManager.getSifrantSodisc();
        for (int i = 0; i < certifikati.size(); i++) {
            Certifikat certifikat = certifikati.get(i);
            String sifraSodisca = metodeHelper.getSifraSodiscaFromNaziv(sifrantSodisc, certifikat.getImeOrganizacije());
            Certifikat certifikatiToUpdate = findDuplicate(certifikat);
            if (certifikatiToUpdate == null) {
                if (sifraSodisca == null) {
                    neNajdenih++;
                } else {
                    certifikat.setSifraSodisca(sifraSodisca);
                }
                certifikat.setStatus(statusCertifikatRepoManager.getStatusCertifikat(metodeHelper.getCertifikatStatusSifraFromStanje(certifikat.getStanje())));
                certifikatiToSave.add(certifikat);
                usp++;
            } else {
                if (sifraSodisca != null && (certifikatiToUpdate.getSifraSodisca() == null || certifikatiToUpdate.getSifraSodisca().isEmpty())) {
                    certifikatiToUpdate.setSifraSodisca(sifraSodisca);
                    certifikatiToUpdate.setStatus(statusCertifikatRepoManager.getStatusCertifikat(metodeHelper.getCertifikatStatusSifraFromStanje(certifikatiToUpdate.getStanje())));
                    certifikatiToSave.add(certifikatiToUpdate);
                    usp++;
                } else {
                    neusp++;
                }
            }
        }
        repository.save(certifikatiToSave);
        List<Integer> uspNeusp = new ArrayList();
        uspNeusp.add(usp);
        uspNeusp.add(neusp);
        uspNeusp.add(neNajdenih);
        return uspNeusp;

    }

    public List<Certifikat> getCertifikatByImetnikID(long id) {
        return repository.getCertifikatByImetnikID(id);
    }

    public List<Certifikat> getAllCertifikatByImetnikID(long id) {
        return repository.findByImetnikId(id);
    }

    public Certifikat getCertifikatByID(long id) {
        return repository.findById(id);
    }

    public void updateCertifikat(Certifikat certifikat) {
        repository.save(certifikat);
    }

    public List<Certifikat> findByEMail(String eNaslov) {
        return repository.findByENaslovIgnoreCase(eNaslov);
    }

    public Certifikat findBySerijskaStevilka(String serijskaSt) {
        return repository.findBySerijskaStevilkaIgnoreCase(serijskaSt);
    }

    public List<Certifikat> findByStatusSifraOrderByIdAsc(String sifra) {
        return repository.findByStatusSifraOrderByIdAsc(sifra);
    }

    public List<Certifikat> findByStatusSifraAndKarticaBarcodeAndImetnikStatusSifraSIm(String status, String crtnaKoda, String statusImetnik) {
        return repository.findByStatusSifraAndKarticaBarcodeAndImetnikStatusSifraSIm(status, crtnaKoda, statusImetnik);
    }

    /* public List<Certifikat> findByStatusSifraAndImetnikCrtnaKodaAndImetnikStatusSifraSIm(String status, String crtnaKoda, String statusImetnik) {
     return repository.findByStatusSifraAndImetnikCrtnaKodaAndImetnikStatusSifraSIm(status, crtnaKoda, statusImetnik);
     }*/
    public List<Certifikat> findByImetnikCrtnaKodaAndImetnikStatusSifraSIm(String crtnaKoda, String statusImetnik) {
        return repository.findByImetnikCrtnaKodaAndImetnikStatusSifraSIm(crtnaKoda, statusImetnik);
    }

    /* public List<Certifikat> findByStatusSifraAndImetnikStatusSifraSImOrderByIdAsc(String statusC, String statusI) {
     return repository.findByStatusSifraAndImetnikStatusSifraSImOrderByIdAsc(statusC, statusI);
     }*/
    public List<Certifikat> findByImetnikStatusSifraSImOrderByIdAsc(String statusI) {
        return repository.findByImetnikStatusSifraSImOrderByIdAsc(statusI);
    }

    public Certifikat findById(long id) {
        return repository.findById(id);
    }

    public void updateCertifikatFromCertifikat(Certifikat certifikat, Kartica kartica, Imetnik imetnik) {
        Certifikat certifikatToUpdate = findBySerijskaStevilka(certifikat.getSerijskaStevilka());
        certifikatToUpdate.setImeInPriimek(certifikat.getImeInPriimek());
        certifikatToUpdate.setENaslov(certifikat.getENaslov());
        certifikatToUpdate.setDatumPrevzema(certifikat.getDatumPrevzema());
        certifikatToUpdate.setDatumPoteka(certifikat.getDatumPoteka());
        certifikatToUpdate.setKartica(kartica);
        certifikatToUpdate.setImetnik(imetnik);
        certifikatToUpdate.setStatus(certifikat.getStatus());
        certifikatToUpdate.setSifraSodisca(zahtevekRepoManager.getZahtevek(zahtevekRepoManager.getZahtevekIDFromImetnikID(imetnik.getId())).getSifraOrganizacije());
        certifikatToUpdate.setSerialNumberX(certifikat.getSerialNumberX());
        repository.save(certifikatToUpdate);
    }

    public void updateCertifikatFromZahtevekZaPrenos(ZahtevekZaPrenos zahtevekZaPrenos) {
        Certifikat certifikatToUpdate = findById(zahtevekZaPrenos.getCertifikatID());
        certifikatToUpdate.setImetnik(zahtevekZaPrenos.getImetnik());
        //certifikatToUpdate.getKartica().setImetnik(zahtevekZaPrenos.getImetnik());
        certifikatToUpdate.setSifraSodisca(zahtevekZaPrenos.getSifraOrganizacije());
        certifikatToUpdate.setImeOrganizacije(zahtevekZaPrenos.getImeOrganizacije());
        certifikatToUpdate.setKraj(zahtevekZaPrenos.getNaselje());
        certifikatToUpdate.setNaslov(zahtevekZaPrenos.getUlica() + " " + zahtevekZaPrenos.getHisnaStevilka());
        certifikatToUpdate.setPosta(zahtevekZaPrenos.getPostnaStevilka() + " " + zahtevekZaPrenos.getNazivPoste());
        repository.save(certifikatToUpdate);
    }

    public List<Certifikat> findByStatusSifraInAndSifraSodisca(List<String> sifra, String sifraSodisca) {
        return repository.findByStatusSifraInAndSifraSodiscaIgnoreCase(sifra, sifraSodisca);
    }

    public boolean obstajajoNeVrnjeneKartice(String davcna, String eNaslov) {
        int count = repository.findNeveljavneCertifikateAndKarticaNiVrnjena(davcna, eNaslov);
        if (count != 0) {
            return true;
        }
        return false;
    }

    public List<Certifikat> findNeveljavneCertifikateAndKarticaNiVrnjena() {
        return repository.findNeveljavneCertifikateAndKarticaNiVrnjena();
    }

    public List<Certifikat> findBySifraSodiscaIsNull() {
        return repository.findBySifraSodiscaIsNull();
    }

    public Certifikat findDuplicate(Certifikat certifikat) {
        Certifikat retTemp = repository.findBySerijskaStevilkaIgnoreCase(certifikat.getSerijskaStevilka());
        return retTemp;
    }

    public List<Certifikat> findByKarticaDatumInitBetweenAndStatusSifraOrderByKarticaDatumInitAsc(Date d1, Date d2) {
        Calendar c = Calendar.getInstance();
        c.setTime(d2);
        c.add(Calendar.DATE, 1);
        d2 = c.getTime();
        return repository.findByKarticaDatumInitBetweenAndStatusSifraOrderByKarticaDatumInitAsc(d1, d2, "03");
    }

    public List<Certifikat> getNeizvozene() {
        return repository.getNeizvozene();
    }

    @PreAuthorize("hasRole('002')")
    public int countByStatusSifra(String statusSifra) {
        if (nastavitveHelper.getOkolje().compareTo("RAZVOJ") == 0) {
            return repository.countByStatusSifraRazvoj(statusSifra);
        } else {
            return repository.countByStatusSifra(statusSifra);
        }
    }

    public List<Certifikat> findByENaslovIgnoreCaseAndKarticaIsNull(String eNaslov) {
        return repository.findByENaslovIgnoreCaseAndKarticaIsNull(eNaslov);
    }

    public List<Certifikat> findBySerialNumberX(String serialNumberX) {
        return repository.findBySerialNumberX(serialNumberX);
    }

    @PreAuthorize("hasAnyRole('001,002')")
    public List<Certifikat> getCertifikatZapreklic(String iskano, Uporabnik uporabnik) {
        String sodisceID = uporabnik.getIzbranaVlogaSodisce().getSodisce().getId();
        String vloga = uporabnik.getIzbranaVlogaSodisce().getVloga().getId();
        iskano = iskano.toLowerCase();
        String[] imeInPriimek = iskano.split(" ");
        List<Certifikat> certifikati;
        if (imeInPriimek.length > 1) {
            if (vloga.compareTo("001") == 0) {
                certifikati = repository.findCertifikatZaPreklic(iskano, imeInPriimek[0], imeInPriimek[imeInPriimek.length - 1], sodisceID);
            } else {
                certifikati = repository.findCertifikatZaPreklic(iskano, imeInPriimek[0], imeInPriimek[imeInPriimek.length - 1], "%");
            }
        } else {
            if (vloga.compareTo("001") == 0) {
                certifikati = repository.findCertifikatZaPreklic(iskano, iskano, iskano, sodisceID);
            } else {
                certifikati = repository.findCertifikatZaPreklic(iskano, iskano, iskano, "%");
            }
        }
        return certifikati;
    }

    public Certifikat getCertifikatZaPrenos(String iskano) {
        return repository.findCertifikatZaPrenos(iskano);
    }
}
