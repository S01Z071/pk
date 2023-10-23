/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.pametnekartice.cucumber.at;

import cucumber.api.Scenario;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import si.vsrs.cif.pametnekartice.cucumber.Shrani;
import static si.vsrs.cif.pametnekartice.cucumber.at.WaitMethods.waitElementByClass;
import static si.vsrs.cif.pametnekartice.cucumber.at.WaitMethods.waitElementById;
import static si.vsrs.cif.pametnekartice.cucumber.at.WaitMethods.waitElementToHide;

/**
 *
 * @author uporabnik
 */
public class Metode extends WaitMethods {

    public void odpriPodrobenPregledZahtevka(String kljuc) throws Exception {
        clickById("pregledZahtevkov");
        waitElementToHide("loadingDiv");
        //ADMIN
        //if (!driver.findElements(By.xpath("//a[contains(text(),'Uvoz podatkov iz SIGOV-CA')]")).isEmpty()) {           
        // if (!driver.findElements(By.xpath("//strong[contains(text(),'Status zahtevka')]")).isEmpty()) {
        if (!driver.findElements(By.xpath("//a[contains(text(),'Uvoz podatkov iz SIGOV-CA')]")).isEmpty()) {
            //oznaciVseStatuse();
            clickById("iskanje");
            sendKeysById("iskano", Shrani.getVrednost(kljuc));
            clickById("Isci");
            waitElementToHide("loadingDiv");
        } else {
            poisciInKlikniZahtevek(kljuc);
        }
        //clickByXPath("//strong[contains(text(),'" + Shrani.getVrednost(kljuc) + "')]/../../td[3]/a");
    }

    public void poisciInKlikniZahtevek(String kljuc) throws Exception {
        try {
            clickByXPath(false, "//strong[contains(text(),'" + Shrani.getVrednost(kljuc) + "')]/../../td[3]/a");
        } catch (Exception e) {
            try {
                WebElement element = driver.findElements(By.xpath("(//a[contains(text(),'Naslednja')])")).get(0);
                if (element.isDisplayed() && element.isEnabled()) {
                    element.click();
                    poisciInKlikniZahtevek(kljuc);
                }
            } catch (Exception ex) {
                narediScreenShot(scenario);
                scenario.write("Napaka pri iskanju zahtevka");
                throw ex;
            }
        }
    }

    public void odpriPodrobenPregledImetnika(String kljuc) throws Exception {
        clickById("pregledImetnikov");
        sendKeysById("iskano", Shrani.getVrednost(kljuc));
        clickById("Isci");
        clickByXPath(true, "//button[contains(text(),'Podrobno')]");
    }

    public boolean oznaciVseStatuse() throws Exception {
        try {
            String xPath = "//strong[contains(text(),'Status zahtevka')]/../../td/input[1]";
            waitElementsByXPath(xPath);
            List<WebElement> elementi = driver.findElements(By.xpath(xPath));
            for (WebElement element : elementi) {
                if (!element.isSelected()) {
                    element.click();
                }
            }
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri izbiranju statusa zahtevka za pregled.");
            throw new Exception(e.getMessage());
        }
        clickById("Potrdi");
        return true;
    }

    public void zapomniSiCrtnoKodoZahtevka(String kljuc) throws Exception {
        try {
            waitElementByClass("info");
            clickById("pregledZahtevkov");
            waitElementByXPath("//table[1]/tbody/tr[1]/td[1]/strong");
            String crtnaKoda = driver.findElement(By.xpath("//table[1]/tbody/tr[1]/td[1]/strong")).getText();
            Shrani.addVrednost(crtnaKoda.split(" ")[2], kljuc);
            //scenario.write("Crtna koda:" + crtnaKoda);
            //clickByXPath("//table[1]/tbody/tr[1]/td[3]/a");
            //clickById("editButton");
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri branju črtne kode zahtevka.");
            throw new Exception(e.getMessage());
        }
    }

    public void zapomniSiCrtnoKodoImetnika(String kljuc) throws Exception {
        try {
            waitElementToHide("loadingDiv");
            clickById("pregledImetnikov");

            waitElementByXPath("//table[1]/tbody/tr[1]/td[1]/strong");
            String crtnaKoda = driver.findElement(By.xpath("//table[1]/tbody/tr[1]/td[1]/strong")).getText();
            Shrani.addVrednost(crtnaKoda.split(" ")[1], kljuc);
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri branju črtne kode imetnika.");
            throw new Exception(e.getMessage());
        }
    }

    public void sendKeysById(String id, String value) throws Exception {
        try {
            waitElementById(id);
            driver.findElement(By.id(id)).clear();
            driver.findElement(By.id(id)).sendKeys(value);
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri vnosu.");
            throw new Exception(e.getMessage());
        }
    }

    public void sendKeysByXPath(String xPath, String value) throws Exception {
        try {
            waitElementByXPath(xPath);
            driver.findElement(By.xpath(xPath)).clear();
            driver.findElement(By.xpath(xPath)).sendKeys(value);
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri vnosu.");
            throw new Exception(e.getMessage());
        }
    }

    public void clickById(String id) throws Exception {
        try {
            waitElementById(id);
            driver.findElement(By.id(id)).click();
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri kliku na gumb.");
            throw new Exception(e.getMessage());
        }
    }

    public void clickByXPath(Boolean showMsg, String xPath) throws Exception {
        try {
            //if (showMsg) {
            waitElementByXPath(xPath);
            //}
            driver.findElement(By.xpath(xPath)).click();
        } catch (Exception e) {
            if (showMsg) {
                narediScreenShot(scenario);
                scenario.write("Napaka pri kliku na gumb.");
            }
            throw new Exception(e.getMessage());
        }
    }

    public void selecValueById(String id, String value) throws Exception {
        try {
            waitElementById(id);
            Select select = new Select(driver.findElement(By.id(id)));
            waitElementBySelectVisibleText(select, value);
            select.selectByVisibleText(value);
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri izbiri vrednosti.");
            throw new Exception(e.getMessage());
        }
    }

    public void checkMsgByXpath(String xPath, String msg) throws Exception {
        try {
            waitElementByXPath(xPath);
            Assert.assertEquals(msg, driver.findElement(By.xpath(xPath)).getText());
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri preverjanju sporočila.");
            throw new Exception(e.getMessage());
        }
    }

    public void narediScreenShot(Scenario scenario) {
        try {
            scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
        } catch (Exception e) {
            scenario.write("Napaka pri izdelovanju screenshota.");
        }
    }

    public boolean obstajaZahtevek(String crtnaKoda) {
        String xPath = "//strong[contains(text(),'" + crtnaKoda + "')]";
        for (;;) {
            List<WebElement> elementiZ = driver.findElements(By.xpath(xPath));
            if (elementiZ.isEmpty()) {
                List<WebElement> elementiN = driver.findElements(By.xpath("(//a[contains(text(),'Naslednja')])"));
                if (!elementiN.isEmpty() && elementiN.get(0).isDisplayed() && elementiN.get(0).isEnabled()) {
                    elementiN.get(0).click();
                    waitElementsByXPath("//table[@class='table']");
                } else {
                    return false;
                }
            } else {
                elementiZ.get(0).click();
                return true;
            }
        }
    }

    public void zapomniSiCrtnoKodoZahtevkaKoda(String kljuc) throws Exception {
        try {
            //waitElementByClass("info");
            clickById("pregledZahtevkov");
            clickByXPath(true, "//li[@id='zahtevkiKodaTab']/a");

            waitElementByXPath("//table[1]/tbody/tr[1]/td[1]/strong[contains(text(),'KS')]");
            String crtnaKoda = driver.findElement(By.xpath("//table[1]/tbody/tr[1]/td[1]/strong[contains(text(),'KS')]")).getText();
            Shrani.addVrednost(crtnaKoda.split(" ")[2], kljuc);
            //scenario.write("Crtna koda:" + crtnaKoda);
            //clickByXPath("//table[1]/tbody/tr[1]/td[3]/a");
            //clickById("editButton");
        } catch (Exception e) {
            narediScreenShot(scenario);
            scenario.write("Napaka pri branju črtne kode zahtevka za pridobitev kode za odklepanje kartice.");
            throw new Exception(e.getMessage());
        }
    }

  

    public void odpriPodrobenPregledZahtevkaKoda(String kljuc) throws Exception {
        clickById("pregledZahtevkov");
        clickByXPath(true, "//li[@id='zahtevkiKodaTab']/a");
        waitElementById("zahtevkiKoda");
        //Admin
        if (!driver.findElements(By.xpath("//a[contains(text(),'Uvoz podatkov iz SIGOV-CA')]")).isEmpty()) {
            clickById("iskanje");
            sendKeysById("iskano", Shrani.getVrednost(kljuc));
            clickById("Isci");
            waitElementToHide("loadingDiv");
        } else {
            poisciInKlikniZahtevekKoda(kljuc);
        }
        //clickByXPath("//strong[contains(text(),'" + Shrani.getVrednost(kljuc) + "')]/../../td[3]/a");
    }
    
     public void odpriPodrobenPregledZahtevkaPreklic(String kljuc) throws Exception {
        clickById("pregledZahtevkov");
        clickByXPath(true, "//li[@id='zahtevkiPreklicTab']/a");
        waitElementByXPath("(//button[contains(text(),'Podrobno')])[1]");
        //Admin
        if (!driver.findElements(By.xpath("//a[contains(text(),'Uvoz podatkov iz SIGOV-CA')]")).isEmpty()) {
            clickById("iskanje");
            sendKeysById("iskano", Shrani.getVrednost(kljuc));
            clickById("Isci");
            waitElementToHide("loadingDiv");
        } else {
            poisciInKlikniZahtevekPreklic(kljuc);
        }
        //clickByXPath("//strong[contains(text(),'" + Shrani.getVrednost(kljuc) + "')]/../../td[3]/a");
    }
     
      public void odpriPodrobenPregledZahtevkaPrenos(String kljuc) throws Exception {
        clickById("pregledZahtevkov");
        clickByXPath(true, "//li[@id='zahtevkiPrenosTab']/a");
        waitElementByXPath("(//button[contains(text(),'Podrobno')])[1]");
        //Admin
        if (!driver.findElements(By.xpath("//a[contains(text(),'Uvoz podatkov iz SIGOV-CA')]")).isEmpty()) {
            clickById("iskanje");
            sendKeysById("iskano", Shrani.getVrednost(kljuc));
            clickById("Isci");
            waitElementToHide("loadingDiv");
        } else {
            poisciInKlikniZahtevekPrenos(kljuc);
        }
        //clickByXPath("//strong[contains(text(),'" + Shrani.getVrednost(kljuc) + "')]/../../td[3]/a");
    }
    

    public void poisciInKlikniZahtevekKoda(String kljuc) throws Exception {
        try {
           // scenario.write("SIZE:" + Shrani.getVrednost(kljuc));
            clickByXPath(false, "//strong[contains(text(),'" + Shrani.getVrednost(kljuc) + "')]/../../td[3]/a/button");
        } catch (Exception e) {
            try {
                List<WebElement> elementi = driver.findElements(By.xpath("(//a[contains(text(),'Naslednja')])"));
                WebElement element = elementi.get(elementi.size() - 1);
                if (element != null && element.isDisplayed() && element.isEnabled()) {
                    element.click();
                    poisciInKlikniZahtevekKoda(kljuc);
                }
            } catch (Exception ex) {
                narediScreenShot(scenario);
                scenario.write("Napaka pri iskanju zahtevka za pridobitev kode za odklepanje kartice.");
                throw ex;
            }
        }
    }
    
     public void poisciInKlikniZahtevekPreklic(String kljuc) throws Exception {
        try {           
            clickByXPath(false, "//strong[contains(text(),'" + Shrani.getVrednost(kljuc) + "')]/../../td[3]/a/button");
        } catch (Exception e) {
            try {
                List<WebElement> elementi = driver.findElements(By.xpath("(//a[contains(text(),'Naslednja')])"));
                WebElement element = elementi.get(elementi.size() - 1);
                if (element != null && element.isDisplayed() && element.isEnabled()) {
                    element.click();
                    poisciInKlikniZahtevekPreklic(kljuc);
                }
            } catch (Exception ex) {
                narediScreenShot(scenario);
                scenario.write("Napaka pri iskanju zahtevka za preklic certifikata.");
                throw ex;
            }
        }
    }
     
      public void poisciInKlikniZahtevekPrenos(String kljuc) throws Exception {
        try {           
            clickByXPath(false, "//strong[contains(text(),'" + Shrani.getVrednost(kljuc) + "')]/../../td[3]/a/button");
        } catch (Exception e) {
            try {
                List<WebElement> elementi = driver.findElements(By.xpath("(//a[contains(text(),'Naslednja')])"));
                WebElement element = elementi.get(elementi.size() - 1);
                if (element != null && element.isDisplayed() && element.isEnabled()) {
                    element.click();
                    poisciInKlikniZahtevekPrenos(kljuc);
                }
            } catch (Exception ex) {
                narediScreenShot(scenario);
                scenario.write("Napaka pri iskanju zahtevka za prenos certifikata.");
                throw ex;
            }
        }
    }

    public boolean obstajaZahtevekKoda(String crtnaKoda) {
        String xPath = "//strong[contains(text(),'" + crtnaKoda + "')]";
        for (;;) {
            List<WebElement> elementiZ = driver.findElements(By.xpath(xPath));
            if (elementiZ.isEmpty()) {
                List<WebElement> elementiN = driver.findElements(By.xpath("(//a[contains(text(),'Naslednja')])"));
                if (!elementiN.isEmpty() && elementiN.get(elementiN.size() - 1).isDisplayed() && elementiN.get(elementiN.size() - 1).isEnabled()) {
                    elementiN.get(elementiN.size() - 1).click();
                    waitElementsByXPath("//table[@class='table']");
                } else {
                    return false;
                }
            } else {
                elementiZ.get(0).click();
                return true;
            }
        }
    }

    public void executeQueryOnDatabase(String sql) throws SQLException {
       ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/datasource.xml");
        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
       // String sql = "delete from opomba";
        PreparedStatement stmt = dataSource.getConnection().prepareStatement(sql);
        stmt.execute();
        stmt.getConnection().close();
    }
}
