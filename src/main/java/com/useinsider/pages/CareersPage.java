package com.useinsider.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CareersPage {
    private WebDriver driver;

    @FindBy(xpath = "//h3[contains(@class, 'ml-0')]")
    private WebElement ourLocationHeader;

    @FindBy(xpath = "//a[contains(text(), 'See all teams')]")
    private WebElement seeAllTeamsBtn;

    @FindBy(xpath = "//h2[contains(text(), 'Life at Insider')]")
    private WebElement lifeAtInsiderLabel;

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean ourLocationHeaderIsDisplayed() {
        return ourLocationHeader.isDisplayed();
    }

    public boolean seeAllTeamsBtnIsDisplayed() {
        return seeAllTeamsBtn.isDisplayed();
    }

    public boolean lifeAtInsiderIsDisplayed() {
        return lifeAtInsiderLabel.isDisplayed();
    }
}
