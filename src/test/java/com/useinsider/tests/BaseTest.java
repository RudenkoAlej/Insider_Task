package com.useinsider.tests;

import com.useinsider.utils.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public WebDriver driver;
    protected final Properties config = Config.loadProperties("test.properties");


    @BeforeMethod(alwaysRun = true)
    public void setup() {
        WebDriverManager
                .chromedriver()
                .browserVersion(config.getProperty("chromedriver.version"))
                .setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.MINUTES);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void cleanup() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.quit();
        }
    }

}
