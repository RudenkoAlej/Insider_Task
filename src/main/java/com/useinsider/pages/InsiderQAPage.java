package com.useinsider.pages;

import com.useinsider.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.useinsider.utils.TestHelper;

import java.util.List;
import java.util.Properties;

public class InsiderQAPage extends MainPage {
    private WebDriver driver;
    protected final Properties config = Config.loadProperties("test.properties");

    @FindBy(linkText = "See all QA jobs")
    private WebElement SeeAllQABtn;

    public InsiderQAPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public InsiderQAPage navigateByInsiderQAPageLink() {
        driver.navigate().to(config.getProperty("insiderQAPage"));
        return this;
    }

    @Override
    public InsiderQAPage onlyNecessaryBtnClick() {
        super.onlyNecessaryBtnClick();
        return this;
    }

    public InsiderQAPage seeAllQAButtonClick() {
        SeeAllQABtn.click();
        return this;
    }

    public InsiderQAPage setLocationFilter (String locationFilterValue) {
        boolean locationOptionAbsent = true;

        while (locationOptionAbsent) {
            WebElement locationFilterSelectedOption = driver.findElement(By.xpath("//*[@name='filter-by-location']/following-sibling::span//*[@class='select2-selection__rendered']"));
            if (!locationFilterSelectedOption.getAttribute("title").contains(locationFilterValue)) {
                driver.findElement(By.xpath("//*[@name='filter-by-location']/following-sibling::span//b[contains(@role, 'presentation')]")).click();
                try {
                    WebElement locationFilterOptionToSelect = driver.findElement(By.xpath("//li[contains(text(), '"+ locationFilterValue+"')]"));
                    locationFilterOptionToSelect.click();
                    locationOptionAbsent = false;
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    e.printStackTrace();
                    driver.findElement(By.xpath("//*[@name='filter-by-location']/following-sibling::span//b[contains(@role, 'presentation')]")).click();
                }
            } else {
                System.out.println("Expected location " + locationFilterValue + " is already selected");
                locationOptionAbsent = false;
            }

        }
        return this;
    }

    public InsiderQAPage setDepartmentFilter (String departmentFilterValue) {
        boolean departmentOptionAbsent = true;


        while (departmentOptionAbsent) {
            WebElement departmentFilterSelectedOption = driver.findElement(By.xpath("//*[@name='filter-by-department']/following-sibling::span//*[@class='select2-selection__rendered']"));
            if (!departmentFilterSelectedOption.getAttribute("title").contains(departmentFilterValue)) {
                driver.findElement(By.xpath("//*[@name='filter-by-department']/following-sibling::span//b[contains(@role, 'presentation')]")).click();
                try {
                    WebElement locationFilterOptionToSelect = driver.findElement(By.xpath("//li[contains(text(), '"+departmentFilterValue+"')]"));
                    locationFilterOptionToSelect.click();
                    departmentOptionAbsent = false;
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    e.printStackTrace();
                    driver.findElement(By.xpath("//*[@name='filter-by-department']/following-sibling::span//b[contains(@role, 'presentation')]")).click();
                }
            } else {
                System.out.println("Expected department "+departmentFilterValue+" is already selected");
                departmentOptionAbsent = false;
            }

        }
        return this;
    }

    public List<WebElement> getAllPositionsEnum() {
        return driver.findElements(By.xpath("//div[contains(@class, 'position-list-item-wrapper')]"));
    }

    public int getNumberOfPosition(){
        return getAllPositionsEnum().size();
    }

    //*********************************** Wrappers for test helper methods ************************************************
    public InsiderQAPage sleepForNumberOfSeconds(int numberOfSeconds) {
        new TestHelper(driver).sleepNumberOfSeconds(numberOfSeconds);
        return this;
    }

    public InsiderQAPage scrollForSpecificDistance(int numberOfPixels) {
        new TestHelper(driver).scrollForSpecificDistance(numberOfPixels);
        return this;
    }

}
