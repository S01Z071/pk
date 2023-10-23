/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.managers.ImetnikRepoManager;
import si.vsrs.cif.managers.StatusCertifikatRepoManager;
import si.vsrs.cif.managers.StatusImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogImetnikRepoManager;
import si.vsrs.cif.managers.StatusLogPreklicRepoManager;
import si.vsrs.cif.managers.StatusLogPrenosRepoManager;
import si.vsrs.cif.managers.StatusLogRepoManager;
import si.vsrs.cif.managers.StatusLogZahtevekZaKodoManager;
import si.vsrs.cif.managers.StatusRepoManager;
import si.vsrs.cif.managers.ZahtevekRepoManager;
import si.vsrs.cif.mod.Certifikat;
import si.vsrs.cif.mod.Imetnik;
import si.vsrs.cif.mod.Opomba;
import si.vsrs.cif.mod.SeznamCitalcevSigovca;
import si.vsrs.cif.mod.SeznamKarticSigovca;
import si.vsrs.cif.mod.SifrantSodiscUvoz;
import si.vsrs.cif.mod.StatusLog;
import si.vsrs.cif.mod.StatusLogImetnik;
import si.vsrs.cif.mod.StatusLogPreklic;
import si.vsrs.cif.mod.StatusLogPrenos;
import si.vsrs.cif.mod.StatusLogZahtevekZaKodo;
import si.vsrs.cif.mod.Zahtevek;
import si.vsrs.cif.mod.web.Uporabnik;

/**
 *
 * @author uporabnik
 */
public class MetodeHelper {

    public void changeStatusZahtevekIfVsiImetnikiPrejetoPotrdiloOPrejetjuKartice(Long imetnikId, ZahtevekRepoManager zahtevekRepoManager, StatusRepoManager statusRepoManager, StatusLogRepoManager statusLogRepoManager, Uporabnik uporabnik) {
        //System.out.println("changeStatusZahtevekIfVsiImetnikiPrejetoPotrdiloOPrejetjuKartice");
        Zahtevek zahtevek = zahtevekRepoManager.getZahtevek(zahtevekRepoManager.getZahtevekIDFromImetnikID(imetnikId));
        if (vsiImetnikiStatus(zahtevek, "09", "04", "10")) {
            String zSt = zahtevek.getStatus().getSifra();
            zahtevek.setStatus(statusRepoManager.getStatus("08"));
            zahtevekRepoManager.updateZahtevek(zahtevek);
            insertInStatusLog(uporabnik, zSt, "08", zahtevek.getId(), statusLogRepoManager, "Vse pametne kartice prevzete.");
        }
    }

    public void setImetnikDatumPrejemaOpreme(Imetnik imetnik, StatusImetnikRepoManager statusImetnikRepoManager, ImetnikRepoManager imetnikRepoManager, Date datumPrejemaOpreme, StatusLogImetnikRepoManager statusLogImetnikRepoManager, Uporabnik uporabnik, String opisStatusa) {
        String stI = imetnik.getStatus().getSifraSIm();
        imetnik.setStatus(statusImetnikRepoManager.getStatusImetnik("09"));
        imetnik.setDatumPrejemaOpreme(datumPrejemaOpreme);
        imetnikRepoManager.updateImetnik(imetnik);
        insertInStatusLogImetnik(uporabnik, stI, "09", imetnik.getId(), statusLogImetnikRepoManager, opisStatusa);

    }

    public void insertInStatusLog(Uporabnik uporabnik, String preS, String novS, long idZ, StatusLogRepoManager statusLogRepoManager, String opis) {
        /**
         * Status Log
         */
        StatusLog statusLog = new StatusLog();
        statusLog.setDatum(new Date());
        statusLog.setUporabnik(uporabnik.getKadrovskStevilka());
        statusLog.setZahtevekLID(idZ);
        statusLog.setPrejsnjiStatus(preS);
        statusLog.setNovStatus(novS);
        statusLog.setSodisce(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId());
        statusLog.setOpis(opis);
        statusLogRepoManager.insertStatusLog(statusLog);
    }

    //*Od vseh imetnikov zapise v status log razen od imetnikov s statusom "statNot"
    public void insertInStatusLogImetniki(Uporabnik uporabnik, List<Imetnik> imetniki, String novS, StatusLogImetnikRepoManager statusLogImetnikRepoManager, String opis, String statNot) {
        for (int i = 0; i < imetniki.size(); i++) {
            String preS = imetniki.get(i).getStatus().getSifraSIm();
            if (preS.compareTo(statNot) != 0) {
                long idI = imetniki.get(i).getId();
                insertInStatusLogImetnik(uporabnik, preS, novS, idI, statusLogImetnikRepoManager, opis);
            }
        }
    }
    //*

    public void insertInStatusLogImetnik(Uporabnik uporabnik, String preS, String novS, long idI, StatusLogImetnikRepoManager statusLogImetnikRepoManager, String opis) {
        /*Status Log*/
        StatusLogImetnik statusLog = new StatusLogImetnik();
        statusLog.setDatumI(new Date());
        statusLog.setUporabnikI(uporabnik.getKadrovskStevilka());
        statusLog.setImetnikLID(idI);
        statusLog.setPrejsnjiStatusI(preS);
        statusLog.setNovStatusI(novS);
        statusLog.setSodisce(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId());
        statusLog.setOpis(opis);
        statusLogImetnikRepoManager.insertStatusLogImetnik(statusLog);
    }
    //*

    public void insertInStatusLogPreklic(HttpSession session, String preS, String novS, long idZ, StatusLogPreklicRepoManager statusLogPreklicRepoManager, String opis) {
        /*Status Log*/
        Uporabnik uporabnik = (Uporabnik) session.getAttribute("uporabnik");
        StatusLogPreklic statusLogPreklic = new StatusLogPreklic();
        statusLogPreklic.setDatum(new Date());
        statusLogPreklic.setUporabnik(uporabnik.getKadrovskStevilka());
        statusLogPreklic.setZahtevekPreklicLID(idZ);
        statusLogPreklic.setPrejsnjiStatus(preS);
        statusLogPreklic.setNovStatus(novS);
        statusLogPreklic.setSodisce(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId());
        statusLogPreklic.setOpis(opis);
        statusLogPreklicRepoManager.insertStatusLogPreklic(statusLogPreklic);
    }

    //*Spremeni samo enemu imetniku
    public List<Imetnik> spremeniImetnikNatisnjeno(List<Imetnik> imetniki, long imetnikID, boolean nat) {
        for (int i = 0; i < imetniki.size(); i++) {
            if (imetniki.get(i).getId().equals(imetnikID)) {
                imetniki.get(i).setNatisnjeno(nat);
                return imetniki;
            }
        }
        return imetniki;
    }

    //*Spremeni vsem imetnikom
    public List<Imetnik> spremeniImetnikiNatisnjeno(List<Imetnik> imetniki, boolean nat) {
        for (int i = 0; i < imetniki.size(); i++) {
            imetniki.get(i).setNatisnjeno(nat);
        }
        return imetniki;
    }
    //*

    public boolean soVsiImetnikiNatisnjeni(List<Imetnik> imetniki) {
        for (int i = 0; i < imetniki.size(); i++) {
            if (!imetniki.get(i).isNatisnjeno() && imetniki.get(i).getStatus().getSifraSIm().compareTo("04") != 0) {
                return false;
            }
        }
        return true;
    }

    //*Iz seznama izbrise imetnike s statusom 04 in 10
    public List<Zahtevek> updateDeletedImetniki(List<Zahtevek> zahtevki) {
        List<Zahtevek> noviZahtevki = new ArrayList<>();
        for (int j = 0; j < zahtevki.size(); j++) {
            if (!zahtevki.get(j).getImetniki().isEmpty()) {
                List<Imetnik> imetniki = zahtevki.get(j).getImetniki();
                List<Imetnik> imetnikiRet = new ArrayList();
                for (int i = 0; i < imetniki.size(); i++) {
                    if (imetniki.get(i).getStatus() != null && imetniki.get(i).getStatus().getSifraSIm().compareTo("04") != 0 && imetniki.get(i).getStatus().getSifraSIm().compareTo("10") != 0) {
                        imetnikiRet.add(imetniki.get(i));
                    }
                }
                zahtevki.get(j).setImetniki(imetnikiRet);
            }
            noviZahtevki.add(zahtevki.get(j));
        }

        return noviZahtevki;
    }
    //*Vrne zahtevek brez imetnikov s statusom 04

    public Zahtevek updateDeletedImetniki(Zahtevek zahtevek) {
        Zahtevek noviZahtevek = zahtevek;
        List<Imetnik> imetniki = noviZahtevek.getImetniki();
        List<Imetnik> imetnikiRet = new ArrayList();
        for (int j = 0; j < imetniki.size(); j++) {
            if (imetniki.get(j).getStatus() != null && imetniki.get(j).getStatus().getSifraSIm().compareTo("04") != 0 && imetniki.get(j).getStatus().getSifraSIm().compareTo("10") != 0) {
                imetnikiRet.add(imetniki.get(j));
            }
        }
        noviZahtevek.setImetniki(imetnikiRet);

        return noviZahtevek;
    }

    //*Opombam doda novo vrstico, ce je to potrebno
    public Zahtevek opombeNewLine(Zahtevek zahtevek) {
        Set<Opomba> opombe = zahtevek.getOpombe();
        for (Opomba o : opombe) {
            String vsebina = o.getVsebina();
            if (vsebina.length() > 100) {
                String vsebinaTemp = "";
                for (int i = 0; i < vsebina.length(); i += 100) {
                    int endIndex = i + 100;
                    if (endIndex > vsebina.length()) {
                        endIndex = vsebina.length();
                    }
                    vsebinaTemp += vsebina.substring(i, endIndex) + "<br>";
                }
                o.setVsebina(vsebinaTemp);
            }
        }
        zahtevek.setOpombe(opombe);
        return zahtevek;

    }

    //*Ce imajo vsi imetniki dolocen status vrne true
    public boolean vsiImetnikiStatus(Zahtevek zahtevek, String... statusSifra) {
        List<Imetnik> imetniki = zahtevek.getImetniki();
        for (int i = 0; i < imetniki.size(); i++) {
            boolean flag = false;
            for (String stat : statusSifra) {
                if (imetniki.get(i).getStatus().getSifraSIm().compareTo(stat) == 0) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    public boolean vsajEnImetnikStatus(Zahtevek zahtevek, String... statusSifra) {
        List<Imetnik> imetniki = zahtevek.getImetniki();
        for (int i = 0; i < imetniki.size(); i++) {
            for (String stat : statusSifra) {
                if (imetniki.get(i).getStatus().getSifraSIm().compareTo(stat) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    //*Sort
    public List<Opomba> getSortedOpombe(Set<Opomba> opom) {
        List<Opomba> opombe = new ArrayList<>();
        for (Opomba op : opom) {
            if (!opombe.contains(op)) {
                opombe.add(op);
            }
        }
        return sortOpombe(opombe);

    }
    //*

    public List<Opomba> sortOpombe(List<Opomba> opombe) {
        Comparator<Opomba> comparator = new Comparator<Opomba>() {
            @Override
            public int compare(Opomba o1, Opomba o2) {
                return (o2.getDatum().compareTo(o1.getDatum()));
            }
        };
        Collections.sort(opombe, comparator);
        return opombe;
    }
    //*

    public void setHeaderPDF(HttpServletResponse response, String ime, ByteArrayOutputStream outPutStrem) throws IOException {
        response.addHeader("Content-Type", "application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + ime + ".pdf");
        OutputStream out = response.getOutputStream();
        out.write(outPutStrem.toByteArray());
        out.flush();
    }
    //*Nastavi opombo

    public Opomba getOpombaZavrnitev(Uporabnik uporabnik, Opomba opomba, String naslov) {
        opomba.setDatum(new Date());
        opomba.setNaslov(naslov);
        opomba.setUporabnik(uporabnik.getKadrovskStevilka());
        opomba.setUporabnikIme(uporabnik.getIme());
        opomba.setUporabnikPriimek(uporabnik.getPriimek());
        opomba.setUporabnikSodisce(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId());

        return opomba;
    }
    //*Vrne sifro certifikata glede na stanje

    public String getCertifikatStatusSifraFromStanje(String stanje) {
        //Zavrnjen,Vročen,Odobren,Zavržen,Neprevzet,Prošnja,Pretekel,Prevzet,Preklican
        if (stanje.compareTo("Prevzet") == 0) {
            return "03";
        }
        if (stanje.compareTo("Pretekel") == 0) {
            return "05";
        }
        if (stanje.compareTo("Preklican") == 0) {
            return "04";
        }
        if (stanje.compareTo("Zavrnjen") == 0) {
            return "05";
        }
        return "06";
    }
    //*

    public String getMethodName(String pol) {
        String ost = pol.substring(1);
        pol = pol.toUpperCase();
        String getMetoda = "get" + pol.charAt(0) + ost;
        return getMetoda;
    }
    //*Vsem imetnikom nastavi Izvozi na vred

    public Zahtevek setIzvoziImetniki(Zahtevek zahtevek, Boolean vred) {
        for (int i = 0; i < zahtevek.getImetniki().size(); i++) {
            zahtevek.getImetniki().get(i).setIzvozi(vred);
        }
        return zahtevek;
    }
    //*Vrne true, ce imajo vsi imetniki Izvozi true

    public boolean checkIzvoziImetnik(Zahtevek zahtevek) {
        for (int i = 0; i < zahtevek.getImetniki().size(); i++) {
            if (zahtevek.getImetniki().get(i).getStatus().getSifraSIm().compareTo("04") != 0) {
                if (zahtevek.getImetniki().get(i).getIzvozi() == null || !zahtevek.getImetniki().get(i).getIzvozi()) {
                    return false;
                }
            }
        }

        return true;
    }

    public Object setDataFromCSV(String[] podatki, String[] polja, String kateriRazred, String kateriPackage) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Class<?> cls = Class.forName(kateriPackage + "." + kateriRazred);
        Object razred = (Object) cls.newInstance();
        try {
            for (int i = 0; i < polja.length; i++) {
                String pol = polja[i];
                String setMetoda = getMethodNameSetter(pol);
                Method method;
                if (setMetoda.length() >= 8 && setMetoda.substring(0, 8).compareTo("setDatum") == 0) {
                    String datestr = podatki[i];
                    DateFormat formatter;
                    Date date;
                    formatter = new SimpleDateFormat("dd.MM.yyyy");
                    try {
                        date = (Date) formatter.parse(datestr);
                        method = razred.getClass().getMethod(setMetoda, Date.class);
                        method.invoke(razred, date);
                    } catch (ParseException ex) {
                    }

                } else {
                    method = razred.getClass().getMethod(setMetoda, String.class);
                    method.invoke(razred, podatki[i]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return razred;
    }

    public String getMethodNameSetter(String pol) {
        String ost = pol.substring(1);
        pol = pol.toUpperCase();
        String getMetoda = "set" + pol.charAt(0) + ost;
        return getMetoda;
    }

    //Preverjanje EMSO po modulu 11 => vrne true, ce je emso OK, drugace vrne false
    public boolean checkEMSOMOD11(String emso) {
        if (emso.length() != 13) {
            return false;
        }
        int vsota = 0;
        for (int i = 0; i < emso.length() - 1; i++) {
            int stevilo = Integer.parseInt(Character.toString(emso.charAt(i)));
            int mnoz;
            if (i <= 5) {
                mnoz = 7 - i;
            } else {
                mnoz = 7 - (i - 6);
            }
            vsota += stevilo * mnoz;
        }
        int kontSt = 11 - (vsota % 11);
        if (kontSt % 11 == 0) {
            kontSt = 0;
        }
        if (kontSt == Integer.parseInt(Character.toString(emso.charAt(12)))) {
            return true;
        }
        return false;
    }

    //Iskanje po LDAP-u
    public List<String> getDataFromLdapImePriimek(String priimekInIme, String attr, LdapTemplate ldapTemplate) {
        final String attr1 = attr;
        List<String> l2 = ldapTemplate.search(
                "", "(&(sn=" + priimekInIme + ")(objectClass=person))", new ContextMapper() {
            @Override
            public Object mapFromContext(Object o) {
                DirContextAdapter adapter = (DirContextAdapter) o;
                return adapter.getStringAttribute(attr1);
            }
        });

        return l2;
    }

    public List<String> getDataFromLdapENaslov(String enaslov, String attr, LdapTemplate ldapTemplate) {
        final String attr1 = attr;
        List<String> l2 = ldapTemplate.search(
                "", "(&(mail=" + enaslov + ")(objectClass=person))", new ContextMapper() {
            @Override
            public Object mapFromContext(Object o) {
                DirContextAdapter adapter = (DirContextAdapter) o;
                return adapter.getStringAttribute(attr1);
            }
        });

        return l2;
    }
    //Vrne true, ce je certifikat s to serijsko v seznamu

    public boolean isSerijskaIn(List<Certifikat> certifikati, String serijska) {
        for (int i = 0; i < certifikati.size(); i++) {
            if (certifikati.get(i).getSerijskaStevilka().compareTo(serijska) == 0) {
                return true;
            }
        }
        return false;
    }

    //Vrne sifro, ce je naziv v seznamu
    public String getSifraSodiscaFromNaziv(List<SifrantSodiscUvoz> sodisca, String naziv) {
        for (int i = 0; i < sodisca.size(); i++) {
            if (sodisca.get(i).getNaziv().compareTo(naziv) == 0) {
                return sodisca.get(i).getSifra();
            }
        }
        return null;
    }

    //Vsem certifikatom, ki imajo status 03 (Prevzet) in imajo datumPoteka manjse od danes spremeni status v 05 (Pretekel)
    public List<Certifikat> updateCertifikatStatus(List<Certifikat> certifikati, StatusCertifikatRepoManager statusCertifikatRepoManager, CertifikatRepoManager certifikatRepoManager) {
        for (int i = 0; i < certifikati.size(); i++) {
            Certifikat certifikat = certifikati.get(i);
            if (certifikat != null && certifikat.getDatumPoteka() != null && certifikat.getDatumPoteka().compareTo(new Date()) < 0 && certifikat.getStatus().getSifra().compareTo("03") == 0) {
                certifikat.setStatus(statusCertifikatRepoManager.getStatusCertifikat("05"));
                certifikatRepoManager.updateCertifikat(certifikat);
            }
        }
        return certifikati;
    }

    public boolean isDateOk(String datum) {
        if (datum == null || datum.isEmpty()) {
            return true;
        }
        if (datum.matches("\\d{1,2}.\\d{1,2}\\.\\d{4}")) {
            return true;
        }
        return false;


        /*if (datum.length() < 8 || datum.length() > 10) {
         return false;
         }
         String[] datumS = datum.split("\\.");
         if (datumS.length != 3) {
         return false;
         }
         if (datumS[0].length() < 1 || datumS[0].length() > 2) {
         return false;
         }
         if (datumS[1].length() < 1 || datumS[1].length() > 2) {
         return false;
         }
         if (datumS[2].length() < 1 || datumS[2].length() > 4) {
         return false;
         }
         return true;*/
    }
    /**/

    public boolean zahtevekStatus(Zahtevek zahtevek, String... status) {
        for (String stat : status) {
            if (zahtevek.getStatus().getSifra().compareTo(stat) == 0) {
                return true;
            }
        }
        return false;
    }

    //Preveri ali podani e-naslov obstaja v LDAP-u
    public boolean obstajaVLDAP(String mail, LdapTemplate ldapTemplate) {
        //Mail obstaja v LDAP-u      
        List l = ldapTemplate.search(
                "", "(&(mail=" + mail + ")(objectClass=person))",
                new AttributesMapper() {
            @Override
            public Object mapFromAttributes(Attributes atrbts) throws NamingException {
                return atrbts.toString();
            }
        });

        return (l.size() >= 1);
    }

    //Vrne false, ce je v seznamu certifikatov aktiven/prevzet certifikat in je do casa poteka vec kot podana vrednost(casDoPoteka).
    public boolean zeImaCertifikat(List<Certifikat> certifikati, Long casDoPoteka) {
        for (int j = 0; j < certifikati.size(); j++) {
            Certifikat certifikat = certifikati.get(j);
            if (certifikat.getDatumPoteka() != null) {
                long datumPoteka = certifikat.getDatumPoteka().getTime();
                long doPotekaSe = (new Date().getTime()) - datumPoteka;
                if (certifikat.getStatus().getSifra().compareTo("03") == 0 && doPotekaSe <= -1 * casDoPoteka) {
                    return false;
                }
            }
        }
        return true;
    }

    //Preveri ali imetnik ze obstaja.
    //Pri urejanju je dovoljeno dodati, ce je ID enak.
    public boolean imetnikZeObstaja(List<Imetnik> imetnikTemp, Imetnik imetnik) {
        if (imetnikTemp.isEmpty()) {
            return false;
        }
        for (Imetnik imetnik1 : imetnikTemp) {
            if (imetnik.getId() == null) { //Dodajanje
                if ((imetnik1.getStatus().getSifraSIm().compareTo("09") != 0 && imetnik1.getStatus().getSifraSIm().compareTo("10") != 0 && imetnik1.getStatus().getSifraSIm().compareTo("04") != 0)) {
                    return true;
                }
            } else { //Urejanje
                if (imetnik1.getId().compareTo(imetnik.getId()) != 0 && imetnik1.getStatus().getSifraSIm().compareTo("04") != 0 && imetnik1.getStatus().getSifraSIm().compareTo("09") != 0 && imetnik1.getStatus().getSifraSIm().compareTo("10") != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    //Vrne Map s kljucem ime in priimek in value z SeznamKarticaSigovca
    public Map<String, List<SeznamKarticSigovca>> sortKarticeSigovca(List<SeznamKarticSigovca> kartice) {
        Map<String, List<SeznamKarticSigovca>> karticeNove = new HashMap();
        for (SeznamKarticSigovca kartica : kartice) {
            String imeInPriimek = kartica.getImeInPriimek();
            if (karticeNove.containsKey(imeInPriimek)) {
                karticeNove.get(imeInPriimek).add(kartica);
            } else {
                List<SeznamKarticSigovca> karticeTemp = new ArrayList();
                karticeTemp.add(kartica);
                karticeNove.put(imeInPriimek, karticeTemp);
            }
        }
        return karticeNove;
    }

    //Vrne Map s kljucem ime in priimek in value z SeznamCitalcevSigovcva
    public Map<String, List<SeznamCitalcevSigovca>> sortCitalceSigovca(List<SeznamCitalcevSigovca> citalci) {
        Map<String, List<SeznamCitalcevSigovca>> citalciNovi = new HashMap();
        for (SeznamCitalcevSigovca citalec : citalci) {
            String imeInPriimek = citalec.getImeInPriimek();
            if (citalciNovi.containsKey(imeInPriimek)) {
                citalciNovi.get(imeInPriimek).add(citalec);
            } else {
                List<SeznamCitalcevSigovca> citalciTemp = new ArrayList();
                citalciTemp.add(citalec);
                citalciNovi.put(imeInPriimek, citalciTemp);
            }
        }
        return citalciNovi;
    }

    //Iz vloge uporabnika in status zahtevka preveri ali se lahko nadaljuje izvedba metode
    //=> kontrola pri dostopanju do strani prek URL-ja, npr. ce zahtevek ni v statusu 02, se mu ne more spremeniti status na 03
    //argumenti: vloga/rola; zahtevek; v katerem statusu bi moral biti zahtevek, da bi z vlogo 001 lahko dostopal; v katerem statusu bi moral biti zahtevek, da bi z ostalimi vlogami lahko dostopal
    public boolean lahkoDostopaDoStrani(String vloga, Zahtevek zahtevek, String[] statusUporabnik, String[] statusAdmin) {
        if (vloga.compareTo("001") == 0 && !zahtevekStatus(zahtevek, statusUporabnik)) {
            return false;
        }
        if (vloga.compareTo("001") != 0 && !zahtevekStatus(zahtevek, statusAdmin)) {
            return false;
        }
        return true;
    }

    public boolean lahkoDostopaDoStrani(String vloga, Imetnik imetnik, String[] statusUporabnik, String[] statusAdmin) {
        if (vloga.compareTo("001") == 0 && !imetnikStatus(imetnik, statusUporabnik)) {
            return false;
        }
        if (vloga.compareTo("001") != 0 && !imetnikStatus(imetnik, statusAdmin)) {
            return false;
        }
        return true;
    }

    public boolean imetnikStatus(Imetnik imetnik, String... status) {
        for (String stat : status) {
            if (imetnik.getStatus().getSifraSIm().compareTo(stat) == 0) {
                return true;
            }
        }
        return false;
    }

    public void insertInStatusLogZaKodo(Uporabnik uporabnik, String preS, String novS, long idZ, StatusLogZahtevekZaKodoManager statusLogRepoManager, String opis) {
        StatusLogZahtevekZaKodo statusLog = new StatusLogZahtevekZaKodo();
        statusLog.setDatum(new Date());
        statusLog.setUporabnik(uporabnik.getKadrovskStevilka());
        statusLog.setZahtevekLID(idZ);
        statusLog.setPrejsnjiStatus(preS);
        statusLog.setNovStatus(novS);
        statusLog.setSodisce(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId());
        statusLog.setOpis(opis);
        statusLogRepoManager.insertStatusLog(statusLog);
    }

    public void insertInStatusLogZaPreklic(Uporabnik uporabnik, String preS, String novS, long idZ, StatusLogPreklicRepoManager statusLogRepoManager, String opis) {
        StatusLogPreklic statusLog = new StatusLogPreklic();
        statusLog.setDatum(new Date());
        statusLog.setUporabnik(uporabnik.getKadrovskStevilka());
        statusLog.setZahtevekPreklicLID(idZ);
        statusLog.setPrejsnjiStatus(preS);
        statusLog.setNovStatus(novS);
        statusLog.setSodisce(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId());
        statusLog.setOpis(opis);
        statusLogRepoManager.insertStatusLogPreklic(statusLog);
    }

    public void insertInStatusLogZaPrenos(Uporabnik uporabnik, String preS, String novS, long idZ, StatusLogPrenosRepoManager statusLogRepoManager, String opis) {
        StatusLogPrenos statusLog = new StatusLogPrenos();
        statusLog.setDatum(new Date());
        statusLog.setUporabnik(uporabnik.getKadrovskStevilka());
        statusLog.setZahtevekPrenosLID(idZ);
        statusLog.setPrejsnjiStatus(preS);
        statusLog.setNovStatus(novS);
        statusLog.setSodisce(uporabnik.getIzbranaVlogaSodisce().getSodisce().getId());
        statusLog.setOpis(opis);
        statusLogRepoManager.insertStatusLogPrenos(statusLog);
    }
}
