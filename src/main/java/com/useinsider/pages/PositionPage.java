package com.useinsider.pages;

import com.useinsider.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Properties;

public class PositionPage {
    private WebDriver driver;

    private By jobPageTitleLocator = By.xpath("//div[contains(@class, 'posting-headline')]/h2");

    public PositionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public String getTitleOfPositionPage() {
        return driver.findElement(jobPageTitleLocator).getText();
    }
}
