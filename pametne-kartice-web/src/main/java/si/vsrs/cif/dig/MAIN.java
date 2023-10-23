/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.dig;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import si.vsrs.cif.mod.Zahtevek;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author uporabnik
 */
public class MAIN {

    public static void main(String[] args) throws Exception {
        //Dec to hex
        BigInteger bi = new BigInteger("1399350541");
        System.out.println("INFO: " + bi.toString(16));

        //Hex to dec 
        //Ce je napaka, ni hex
        int i = Integer.parseInt("005368650d", 16);
        System.out.println("INFO: " + i);


        //===================================//
        //POVEZAVA NA BAZO
       /* Connection con = null;
         Properties connectionProps = new Properties();
         connectionProps.put("user", "app");
         connectionProps.put("password", "sa");
         con = DriverManager.getConnection("jdbc:derby://localhost:1527/SpringTest;create=true", connectionProps);
         String query = "select * from zahtevek";
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(query);
        
         while(rs.next()){
         System.out.println(rs.getString("Naselje"));
         }*/
        //===================================//
        // Runtime.getRuntime().exec("taskkill /f /t /im firefox.exe");

        // String springConfig = "classpath:spring/certfStatusUpdateJob.xml";

        // ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);


        /*  try {

         Security.addProvider(new BouncyCastleProvider());      
         String ALIAS = "halcomcertf"; // keystore alias

         KeyStore keystore = KeyStore.getInstance("JKS");
         keystore.load(new FileInputStream("src/main/resources/keystore.store"), null);
         X509Certificate cert = (X509Certificate) keystore.getCertificate(ALIAS);
         Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
         rsaCipher.init(Cipher.ENCRYPT_MODE, cert);
           

         File f = new File("src/main/resources/test.txt");
         int sizecontent = ((int) f.length());
         byte[] data = new byte[sizecontent];

         try {
         FileInputStream freader = new FileInputStream(f);
         System.out.println("\nContent Bytes: " + freader.read(data, 0, sizecontent));
         freader.close();
         } catch (IOException ioe) {
         System.out.println(ioe.toString());
         return;
         }

         byte[] encrypteddata = rsaCipher.doFinal(data);
         MAIN.displayData(encrypteddata);
            
          
         FileOutputStream sigfos = new FileOutputStream("src/main/resources/rsaJencrypted");
         sigfos.write(encrypteddata);
         sigfos.close();
         } catch (Exception e) {
         System.err.println("Caught exception " + e.toString());
         }
         */
    }

    /* private static void displayData(byte[] data) {
     System.out.println("Size of encrypted data: " + data.length);
     int bytecon = 0;    //to get unsigned byte representation
     for (int i = 0; i < data.length; i++) {
     bytecon = data[i] & 0xFF;   // byte-wise AND converts signed byte to unsigned.
     if (bytecon < 16) {
     System.out.print("0" + Integer.toHexString(bytecon).toUpperCase() + " ");   // pad on left if single hex digit.
     } else {
     System.out.print(Integer.toHexString(bytecon).toUpperCase() + " ");   // pad on left if single hex digit.
     }
     }
     }*/
}
