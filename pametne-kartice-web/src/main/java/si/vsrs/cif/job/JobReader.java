/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.job;

import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import si.vsrs.cif.managers.CertifikatRepoManager;
import si.vsrs.cif.mod.Certifikat;

/**
 *
 * @author uporabnik
 */
@Component
public class JobReader implements ItemReader<Certifikat> {

    private int position = 0;

     private List<Certifikat> certifikati = null;
     
     @Autowired
     CertifikatRepoManager certifikatRepoManager;
 
    
     
   
    @Override
    public Certifikat read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (certifikati == null) {
            certifikati = new ArrayList();
            certifikati = certifikatRepoManager.findByStatusSifraOrderByIdAsc("03");
        }
        //System.out.println("SIZE:" + certifikati.size());

        if (position >= certifikati.size()) {
            position = 0;
            certifikati = null;
            return null;
        }
        //System.out.println("POSITION:" + position);
        Certifikat certifikat = certifikati.get(position);
        position++;

        return certifikat;
    }
}
