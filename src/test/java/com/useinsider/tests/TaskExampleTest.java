package com.useinsider.tests;
import com.useinsider.pages.*;
import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TaskExampleTest extends BaseTest{
    private MainPage mainPage;
    private CareersPage careersPage;
    private InsiderQAPage insiderQAPage;
    private InsiderCareersQAPage insiderCareersQAPage;
    private PositionPage positionPage;


        @Test
        // 1. Test that Insider page is opened
        public void canOpenInsiderPage() {
            mainPage = new MainPage(driver);
            Assert.assertNotNull(mainPage
                                        .navigateByInsiderLink()
                                        .getMainPageTitle());
        }


        @Test
        public void checkCareerPage() {
        //2. Test Career page
            mainPage = new MainPage(driver);
            careersPage = mainPage.navigateByInsiderLink()
                                        .companyLinkClick()
                                        .careersLinkClick();

            Assert.assertTrue(careersPage.ourLocationHeaderIsDisplayed());
            Assert.assertTrue(careersPage.seeAllTeamsBtnIsDisplayed());
            Assert.assertTrue(careersPage.lifeAtInsiderIsDisplayed());
        }

        @Test
        public void checkAllQAPositionFilters() {
            //3. Test all QA positions filters
            insiderQAPage = new InsiderQAPage(driver)
                    .navigateByInsiderQAPageLink()
                    .onlyNecessaryBtnClick()
                    .seeAllQAButtonClick()
                    .setLocationFilter("Istanbul, Turkey")
                    .setDepartmentFilter("Quality Assurance")
                    //Scroll to be able see/reach jobs elements
                    .scrollForSpecificDistance(500);

            Assert.assertTrue(insiderQAPage.getNumberOfPosition() > 0, "There are more then 0 positions");
        }

        @Test
        public void checkQAPositionsDetails() {
            //4. Test positions, department and location is related to QA

            String location = "Istanbul, Turkey";
            String department = "Quality Assurance";

            insiderCareersQAPage = new InsiderCareersQAPage(driver)
                                            .navigateByInsiderCareersQAPageLink()
                                            .onlyNecessaryBtnClick()
                                            .scrollForSpecificDistance(300)
                                            .setLocationFilter(location)
                                            .setDepartmentFilter(department)
                                            .sleepForNumberOfSeconds(1);

            List<WebElement> positionsEnum = insiderCareersQAPage.getAllPositionsEnum();
            for (WebElement position : positionsEnum) {
                //Checking Position Title
                Assert.assertTrue(insiderCareersQAPage.getPositionTitle(position).contains(department));
                //Checking Position Department
                Assert.assertTrue(insiderCareersQAPage.getPositionDepartment(position).contains(department));
                //Checking Position Location
                Assert.assertTrue(insiderCareersQAPage.getPositionLocation(position).contains(location));
            }
        }

        @Test
        public void checkViewRolePage() {
            //5. Test View Role page
            insiderCareersQAPage = new InsiderCareersQAPage(driver);

            WebElement jobButtonContainer = insiderCareersQAPage
                    .navigateByInsiderCareersQAPageLink()
                    .onlyNecessaryBtnClick()
                    .sleepForNumberOfSeconds(1)
                    .setLocationFilter("Istanbul, Turkey")
                    .setDepartmentFilter("Quality Assurance")
                    .scrollForSpecificDistance(500)
                    .sleepForNumberOfSeconds(1)
                    .getAllPositionsEnum().get(0);

            insiderCareersQAPage
                    .scrollToItemWithAction(jobButtonContainer)
                    .sleepForNumberOfSeconds(1);


            //Save jobTitle to compare it with it's page title
            String jobTitle = insiderCareersQAPage.getJobTitle(jobButtonContainer);

            //Click on View Role button for particular job
            positionPage = insiderCareersQAPage.jobButtonClick(jobButtonContainer);

            //Looking for title of job position
            String jobPageTitle = positionPage.getTitleOfPositionPage();

            //Compare Job title and Job position title are equal
            Assert.assertEquals(jobTitle, jobPageTitle, "Titles on page and on job offer are not the same");

        }
    }
