package com.useinsider.pages;

import com.useinsider.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.useinsider.utils.TestHelper;

import java.util.ArrayList;
import java.util.Properties;

public class InsiderCareersQAPage extends InsiderQAPage{

    private WebDriver driver;
    protected final Properties config = Config.loadProperties("test.properties");

    private By positionTitleLocator = By.xpath("//p[contains(@class, 'position-title')]");
    private By positionDepartmentLocator = By.xpath("//span[contains(@class, 'position-department')]");
    private By positionLocationLocator = By.xpath("//div[contains(@class, 'position-location')]");
    private By jobTitleLocator = By.xpath("//p[contains(@class, 'position-title')]");
    private By jobBtnLocator = By.xpath("//a[text()='View Role']");

    public InsiderCareersQAPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public InsiderCareersQAPage navigateByInsiderCareersQAPageLink() {
        driver.navigate().to(config.getProperty("insiderCareersQA"));
        return this;
    }

    @Override
    public InsiderCareersQAPage onlyNecessaryBtnClick() {
        super.onlyNecessaryBtnClick();
        return this;
    }

    public String getPositionTitle(WebElement position) {
        WebElement positionTitle = position.findElement(positionTitleLocator);
        return position.getAttribute("innerHTML");
    }

    public String getPositionDepartment(WebElement position) {
        WebElement positionDepartment = position.findElement(positionDepartmentLocator);
        return position.getAttribute("innerHTML");
    }

    public String getPositionLocation(WebElement position) {
        WebElement positionLocation = position.findElement(positionLocationLocator);
        return position.getAttribute("innerHTML");
    }

    public String getJobTitle(WebElement position){
        return position.findElement(jobTitleLocator).getText();
    }

    public PositionPage jobButtonClick(WebElement position) {
        position.findElement(jobBtnLocator).click();
        //To be able to switch on new opened page - collect into Array number of pages and switch to second/active
        ArrayList<String> wid = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(wid.get(1));
        return new PositionPage(driver);
    }

    //*********************************** Wrappers for InsiderQAPage filter methods ***************************************
    @Override
    public InsiderCareersQAPage setLocationFilter (String locationFilterValue) {
        super.setLocationFilter (locationFilterValue);
        return this;
    }

    @Override
    public InsiderCareersQAPage setDepartmentFilter (String departmentFilterValue) {
        super.setDepartmentFilter (departmentFilterValue);
        return this;
    }

    //*********************************** Wrappers for test helper methods ************************************************
    public InsiderCareersQAPage sleepForNumberOfSeconds(int numberOfSeconds) {
        new TestHelper(driver).sleepNumberOfSeconds(numberOfSeconds);
        return this;
    }

    public InsiderCareersQAPage scrollForSpecificDistance(int numberOfPixels) {
        new TestHelper(driver).scrollForSpecificDistance(numberOfPixels);
        return this;
    }

    public InsiderCareersQAPage scrollToItemWithAction(WebElement elementToScroll) {
        new TestHelper(driver).scrollToItemWithAction(elementToScroll);
        return this;
    }

    public WebElement scrollToItemWithActionWebEl(WebElement elementToScroll) {
        new TestHelper(driver).scrollToItemWithAction(elementToScroll);
        return elementToScroll;
    }

}
