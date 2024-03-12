package com.useinsider.pages;

import com.useinsider.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.useinsider.utils.TestHelper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

public class InsiderQAPage extends MainPage {
    private WebDriver driver;
    protected final Properties config = Config.loadProperties("test.properties");

    @FindBy(linkText = "See all QA jobs")
    private WebElement SeeAllQABtn;

    @FindBy(xpath = "//*[@name='filter-by-location']/following-sibling::span//*[@class='select2-selection__rendered']")
    private WebElement locationFilterSelectedOption;

    @FindBy(xpath = "//*[@name='filter-by-location']/following-sibling::span//b[contains(@role, 'presentation')]")
    private WebElement locationFilterExpandListArrow;

    @FindBy(xpath = "//*[@name='filter-by-department']/following-sibling::span//*[@class='select2-selection__rendered']")
    private WebElement departmentFilterSelectedOption;

    @FindBy(xpath = "//*[@name='filter-by-department']/following-sibling::span//b[contains(@role, 'presentation')]")
    private WebElement departmentFilterExpandListArrow;

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
        int locationSearchCount = 0;

        while (locationOptionAbsent) {
            if (!locationFilterSelectedOption.getAttribute("title").contains(locationFilterValue)) {
                locationFilterExpandListArrow.click();
                try {
                    WebElement locationFilterOptionToSelect = driver.findElement(By.xpath("//li[contains(text(), '"
                            + locationFilterValue+"')]"));
                    locationFilterOptionToSelect.click();
                    break;
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    e.printStackTrace();
                    locationFilterExpandListArrow.click();
                    locationSearchCount++;
                    if (locationSearchCount > 3 ) {
                        throw new NoSuchElementException("There is no Option in the Location Filter with the name : "
                                + locationFilterValue + ". Please check the spelling!");
                    }
                }
            } else {
                System.out.println("Expected location " + locationFilterValue + " is already selected");
                break;
            }

        }
        return this;
    }

    public InsiderQAPage setDepartmentFilter (String departmentFilterValue) {
        boolean departmentOptionAbsent = true;
        int departmentSearchCount = 0;


        while (departmentOptionAbsent) {
            if (!departmentFilterSelectedOption.getAttribute("title").contains(departmentFilterValue)) {
                departmentFilterExpandListArrow.click();
                try {
                    WebElement locationFilterOptionToSelect = driver.findElement(By.xpath("//li[contains(text(), '"
                            +departmentFilterValue+"')]"));
                    locationFilterOptionToSelect.click();
                    break;
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    e.printStackTrace();
                    departmentFilterExpandListArrow.click();
                    departmentSearchCount++;
                    if (departmentSearchCount > 3 ) {
                        throw new NoSuchElementException("There is no Option in the Location Filter with the name : "
                                + departmentFilterValue + ". Please check the spelling!");
                    }
                }
            } else {
                System.out.println("Expected department "+ departmentFilterValue +" is already selected");
                break;
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
