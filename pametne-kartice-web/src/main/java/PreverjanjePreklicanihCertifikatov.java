
import au.com.bytecode.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Uporabnik
 */
public class PreverjanjePreklicanihCertifikatov {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
       CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(new File("src/main/resources/preklicani_certifikati/seznam_potrdil_sodisca_20160322.csv"))),';');
       List<String[]> sigovcaData = reader.readAll();
       Set<String> seznamPreklicanihSigovca = new TreeSet();
       for(String[] s:sigovcaData){
          if(s[2].equalsIgnoreCase("Preklican")){
              seznamPreklicanihSigovca.add(s[0]);
          }
       }
       
        BufferedReader readerSerial = new BufferedReader(new FileReader(new File("src/main/resources/preklicani_certifikati/serial.txt")));
        String line;
        List<String> serialPreklicane = new ArrayList();
        while((line=readerSerial.readLine())!=null){
            serialPreklicane.add(line);
        }
        
        for(String s:serialPreklicane){
            System.out.println("=>"+s);
        }
        
        for(String certf:serialPreklicane){
            if(!seznamPreklicanihSigovca.contains(certf)){
                System.out.println("NE:"+certf);
            }
        }
        
        
        
       
    }   
    
   
}
