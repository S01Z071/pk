/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.pametnekartice.cucumber;

import cucumber.api.Scenario;
import java.io.File;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author uporabnik
 */
public class BaseScenarijDsl {

    protected static WebDriver driver;
    // protected static Selenium selenium;
    protected Scenario scenario;

    public void startSelenium() throws Exception {
        File profileDir = new File("C:/Users/uporabnik/AppData/Roaming/Mozilla/Firefox/Profiles/Profil");
        FirefoxProfile profile = new FirefoxProfile(profileDir);

        profile.setPreference("browser.download.manager.showWhenStarting", false);
         driver = new FirefoxDriver(profile);   
        // selenium = new WebDriverBackedSelenium(driver, "http://www.sodisce.si");
        profile.setEnableNativeEvents(true);
        //selenium.setTimeout("20000");

        //Selenium grid//
        
        /*DesiredCapabilities capability = DesiredCapabilities.firefox();
        capability.setVersion("31.1.0");
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL("http://10.48.24.199:4444/wd/hub"), capability);
        remoteWebDriver.setFileDetector(new LocalFileDetector());
        driver = remoteWebDriver;
        */

    }
}
