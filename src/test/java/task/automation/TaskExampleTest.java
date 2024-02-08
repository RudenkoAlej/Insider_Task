package task.automation;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.junit.Assert;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TaskExampleTest {

    public static final String INSIDER_LINK = "https://useinsider.com/";
    public static final String INSIDER_CAREERS_QA = "https://useinsider.com/careers/quality-assurance/";

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\TOOLS\\DRIVERS\\chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver", System.getenv("CHROMEDRIVER"));

        WebDriver driver = new ChromeDriver();


        // 1. Test that Insider page is opened
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.MINUTES);
        driver.manage().window().maximize();
        driver.navigate().to(INSIDER_LINK);
        Assert.assertNotNull(driver.getTitle());

        //2. Test Career page
        driver.findElement(By.linkText("Company")).click();
        driver.findElement(By.linkText("Careers")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//h3[contains(@class, 'ml-0')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(), 'See all teams')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Life at Insider')]")).isDisplayed());

        //3. Test all QA positions filters
        driver.navigate().to(INSIDER_CAREERS_QA);
        driver.findElement(By.linkText("Only Necessary")).click();
        driver.findElement(By.linkText("See all QA jobs")).click();
        driver.findElement(By.xpath("//b[contains(@role, 'presentation')]")).click();
        // Following 2 lines is a workround to see all the items list (without this only All option is reachable)
        driver.findElement(By.xpath("//b[contains(@role, 'presentation')]")).click();
        driver.findElement(By.xpath("//b[contains(@role, 'presentation')]")).click();
        //To do: find way to select dinamic web element from the list
//        driver.findElement(By.xpath("//span[contains(@aria-activedescendant, 'select2-filter-by-location-result-i4v9-Istanbul, Turkey')]")).click();

        List<WebElement> positionsEnum = driver.findElements(By.xpath("//div[contains(@class, 'position-list-item-wrapper')]"));
        Assert.assertTrue("There are more then 0 positions", positionsEnum.size()>0);

        //4. Test positions, department and location is related to QA

        for (WebElement position: positionsEnum) {
            //Checking Position Title
            WebElement positionTitle = position.findElement(By.xpath("//p[contains(@class, 'position-title')]"));
            Assert.assertTrue(positionTitle.getAttribute("innerHTML").contains("Quality Assurance"));
            //Checking Position Department
            WebElement positionDepartment = position.findElement(By.xpath("//span[contains(@class, 'position-department')]"));
            Assert.assertTrue(positionDepartment.getAttribute("innerHTML").contains("Quality Assurance"));
            //Checking Position Location
            WebElement positionLocation = position.findElement(By.xpath("//div[contains(@class, 'position-location')]"));
            Assert.assertTrue(positionLocation.getAttribute("innerHTML").contains("Istanbul, Turkey"));
        }

        //5. Test View Role page

        //Scroll to be able see/reach jobs elements
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");

        //Move cursor to job container element to be able see/reach View Role button
        Actions action = new Actions(driver);
        WebElement jobButtonContainer = positionsEnum.get(0);
        action.moveToElement(jobButtonContainer).perform();

        //Save jobTitle to compare it with it's page title
        String jobTitle = jobButtonContainer.findElement(By.xpath("//p[contains(@class, 'position-title')]")).getText();

        //Click on View Role button for particular job
        WebElement jobButton = jobButtonContainer.findElement(By.xpath("//a[text()='View Role']"));
        jobButton.click();

        //Get title of opened page
        //To be able to switch on new opened page - collect into Array number of pages and switch to second/active
        ArrayList<String> wid = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(wid.get(1));
        //Looking for title of job position
        WebElement jobPageHeader = driver.findElement(By.xpath("//div[contains(@class, 'posting-headline')]/h2"));
        String jobPageTitle = jobPageHeader.getText();
        //Compare Job title and Job position title are equal
        Assert.assertEquals("Titles on page and on job offer are not the same", jobTitle, jobPageTitle);


        driver.quit();
    }

}
