/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.pametnekartice.cucumber;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author uporabnik
 */
public class Shrani {
   static HashMap<String, String> vrednosti = new HashMap<>();

   /* public static HashMap<String, String> getVrednosti() {
        return vrednosti;
    }

    public static void setVrednosti(HashMap<String, String> vrednosti) {
        Shrani.vrednosti = vrednosti;
    }
    */
    public static void addVrednost(String vrednost, String kljuc) throws IOException{
        Shrani.vrednosti.put(kljuc, vrednost);
        writeToFile();
    }
    
    public static String getVrednost(String kljuc) throws FileNotFoundException, IOException{
        readFromFile();
        String vrednost = Shrani.vrednosti.get(kljuc);
        if(vrednost==null){
            return kljuc;
        }
        return vrednost;
    }
    
    
    private static void writeToFile() throws IOException{
        File f = new File("src/main/resources/shrani_vrednosti.txt");
        FileWriter fw = new FileWriter(f);
        String newLine = System.getProperty("line.separator");
        for(Entry e: vrednosti.entrySet()){
            fw.write(e.getKey()+"=>"+e.getValue()+newLine);
        }       
        fw.close();
    }
    
    private static void readFromFile() throws FileNotFoundException, IOException{
        File f = new File("src/main/resources/shrani_vrednosti.txt");
        BufferedReader fr = new BufferedReader(new FileReader(f));
        String line;
        while((line = fr.readLine())!=null){
            String[] splitano = line.split("=>");
            vrednosti.put(splitano[0], splitano[1]);
        }        
        fr.close();
    }
   

}
