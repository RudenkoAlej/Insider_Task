package com.useinsider.pages;

import com.useinsider.utils.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Properties;

public class MainPage {
    private WebDriver driver;
    protected final Properties config = Config.loadProperties("test.properties");

    @FindBy(linkText = "Company")
    private WebElement companyMenu;

    @FindBy(linkText = "Careers")
    private WebElement careersMenu;

    @FindBy(linkText = "Only Necessary")
    private WebElement onlyNecessaryBtn;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public MainPage navigateByInsiderLink() {
        driver.navigate().to(config.getProperty("insiderLink"));
        return this;
    }

    public MainPage onlyNecessaryBtnClick() {
        onlyNecessaryBtn.click();
        return this;
    }

    public MainPage getMainPageTitle() {
        driver.getTitle();
        return this;
    }

    public MainPage companyLinkClick() {
        companyMenu.click();
        return this;
    }

    public CareersPage careersLinkClick() {
        careersMenu.click();
        return new CareersPage(driver);
    }



}
