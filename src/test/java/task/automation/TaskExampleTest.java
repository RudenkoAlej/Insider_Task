package task.automation;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TaskExampleTest {

    public static final String INSIDER_LINK = "https://useinsider.com/";
    public static final String INSIDER_CAREERS_QA = "https://useinsider.com/careers/quality-assurance/";

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\TOOLS\\DRIVERS\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();


        // 1. Test that Insider page is opened
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.MINUTES);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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

        boolean locationOptionAbsent = true;

        while (locationOptionAbsent) {
            WebElement locationFilterSelectedOption = driver.findElement(By.xpath("//*[@name='filter-by-location']/following-sibling::span//*[@class='select2-selection__rendered']"));
            if (!locationFilterSelectedOption.getAttribute("title").contains("Istanbul, Turkey"))
            {
                driver.findElement(By.xpath("//*[@name='filter-by-location']/following-sibling::span//b[contains(@role, 'presentation')]")).click();
                try {
                    WebElement locationFilterOptionToSelect = driver.findElement(By.xpath("//li[contains(text(), 'Istanbul, Turkey')]"));
                    locationFilterOptionToSelect.click();
                    locationOptionAbsent = false;
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    e.printStackTrace();
                    driver.findElement(By.xpath("//*[@name='filter-by-location']/following-sibling::span//b[contains(@role, 'presentation')]")).click();
                }
            } else {
                System.out.println("Expected location is already selected");
                locationOptionAbsent = false;
            }

        }

        boolean departmentOptionAbsent = true;



        while (departmentOptionAbsent){
            WebElement departmentFilterSelectedOption = driver.findElement(By.xpath("//*[@name='filter-by-department']/following-sibling::span//*[@class='select2-selection__rendered']"));
            if (!departmentFilterSelectedOption.getAttribute("title").contains("Quality Assurance"))
            {
                driver.findElement(By.xpath("//*[@name='filter-by-department']/following-sibling::span//b[contains(@role, 'presentation')]")).click();
                try {
                    WebElement locationFilterOptionToSelect = driver.findElement(By.xpath("//li[contains(text(), 'Quality Assurance')]"));
                    locationFilterOptionToSelect.click();
                    departmentOptionAbsent = false;
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    e.printStackTrace();
                    driver.findElement(By.xpath("//*[@name='filter-by-department']/following-sibling::span//b[contains(@role, 'presentation')]")).click();
                }
            } else {
                System.out.println("Expected department is already selected");
                departmentOptionAbsent = false;
            }

        }

        //Scroll to be able see/reach jobs elements
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        List<WebElement> positionsEnum = driver.findElements(By.xpath("//div[contains(@class, 'position-list-item-wrapper')]"));
        Assert.assertTrue(positionsEnum.size()>0, "There are more then 0 positions");

        //4. Test positions, department and location is related to QA

        for (WebElement position: positionsEnum) {
            //Checking Position Title
            WebElement positionTitle = position.findElement(By.xpath("//p[contains(@class, 'position-title')]"));
            String attribute = position.getAttribute("innerHTML");
            Assert.assertTrue(positionTitle.getAttribute("innerHTML").contains("Quality Assurance"));
            //Checking Position Department
            WebElement positionDepartment = position.findElement(By.xpath("//span[contains(@class, 'position-department')]"));
            Assert.assertTrue(positionDepartment.getAttribute("innerHTML").contains("Quality Assurance"));
            //Checking Position Location
            WebElement positionLocation = position.findElement(By.xpath("//div[contains(@class, 'position-location')]"));
            Assert.assertTrue(positionLocation.getAttribute("innerHTML").contains("Istanbul, Turkey"));
        }

        //5. Test View Role page

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
