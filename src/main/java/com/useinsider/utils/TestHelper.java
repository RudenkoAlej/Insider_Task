package com.useinsider.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class TestHelper {
    private WebDriver driver;

    public TestHelper(WebDriver driver) {
        this.driver = driver;
    }

    public static void sleepNumberOfSeconds(int numberOfSeconds) {
        try {
            Thread.sleep(numberOfSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int randomInt() {
        Random rn = new Random();
        return rn.nextInt();
    }

    public WebElement waitUntilElementWillBeClickable(WebElement element) {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));

    }

    public void scrollToItemWithAction(WebElement elementToScroll) {
        Actions action = new Actions(driver);
        action.moveToElement(elementToScroll).perform();
    }

    public void scrollToItemWithJS(WebElement elementToScroll){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", elementToScroll);
    }

    public void scrollForSpecificDistance(int numberOfPixels){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("window.scrollBy(0," + numberOfPixels + ")", "");
    }

    public void clickViaJs(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void deleteElementFromDomDisplayNone(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].style.display='none';", element);
    }


    public void deleteElementFromDom(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].parentNode.removeChild(element);", element);
    }
}
