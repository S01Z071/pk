/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.vsrs.cif.pametnekartice.cucumber.at;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import si.vsrs.cif.pametnekartice.cucumber.BaseScenarijDsl;

/**
 *
 * @author uporabnik
 */
public class WaitMethods extends BaseScenarijDsl {

    public static void waitElementById(String id) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    public static void waitElementByClass(String cls) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className(cls)));
    }

    public static void waitElementToHide(String id) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
    }
    
   /* public static void waitElementToHideLonger(String id) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
    }
*/
    public void waitElementByXPath(String xPath) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
    }
    
    public void waitElementsByXPath(String xPath) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> elementi = wait.until(              
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xPath)));
        
    }

    //Pocaka da se v select seznamu pojavi elemet s textom = "text"
    public void waitElementBySelectVisibleText(final Select select, final String text) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<Boolean> conditionToCheck = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return checkIfVisibleTextIsInSelectElement(select, text);
            }
        };
        wait.until(conditionToCheck);
    }

    //Preveri ce je v Select elementu "text"
    private Boolean checkIfVisibleTextIsInSelectElement(Select select, String text) {
        List<WebElement> elementi = select.getOptions();
        for (int i = 0; i < elementi.size(); i++) {
            if (elementi.get(i).getText().compareTo(text) == 0) {
                return true;
            }
        }
        return false;
    }
}
