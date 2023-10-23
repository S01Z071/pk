/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.vsrs.cif.mod.KontaktnaOseba;
import si.vsrs.cif.mod.Predstojnik;
import si.vsrs.cif.mod.ZahtevekTemp;
import si.vsrs.cif.mod.datajpa.repository.ZahtevekTempRepository;

/**
 *
 * @author uporabnik
 */
@Service
@Transactional
@PreAuthorize("denyAll")
public class ZahtevekTempRepoManager {

    @Autowired
    ZahtevekTempRepository repository;
    
    
 
    @PreAuthorize("hasAnyRole('001,002')")
    public void addZahtevekTemp(ZahtevekTemp zahtevekTemp) {
        ZahtevekTemp zahtevekTemp1 = repository.findBySifraOrganizacije(zahtevekTemp.getSifraOrganizacije());
       if(zahtevekTemp1 ==null){
           repository.save(zahtevekTemp);
       }else{
           zahtevekTemp1.setDataT(zahtevekTemp);
           repository.save(zahtevekTemp1);
       }       
    }

   
    @PreAuthorize("hasAnyRole('001,002')")
    public ZahtevekTemp getZahtevekTemp(String sodisceID) {
       ZahtevekTemp zahtevekTemp = repository.findBySifraOrganizacije(sodisceID);
       if(zahtevekTemp == null){
           zahtevekTemp = new ZahtevekTemp();
           zahtevekTemp.setKontaktnaOseba(new KontaktnaOseba());
           zahtevekTemp.setPredstojnik(new Predstojnik());
       }
        return zahtevekTemp;  
    }
}
