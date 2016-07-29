package com.epam.tat.selenium.tests;


import com.epam.tat.selenium.page.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MailTest extends BasicTest {

    private static final String MAIL_TITLE_XPATH = "//a[@title=\"";
    private String subject;
    private String text;

    @Test(description = "input login, password, press login button, check that login was successful", groups = "login test")
    public void loginTest() {
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginAs(user1);
        basePage = PageFactory.initElements(driver, BasePage.class);
        basePage.checkLogin();
    }

    @Test(description = "compose email to be send to user2, subject and text fields should be generated automaticly", dependsOnGroups = "login test")
    public void composeMailTest() {
        subject = RandomStringUtils.randomAlphabetic(8);
        text = RandomStringUtils.randomAlphabetic(12);
        basePage.composeNewMail();
        composeMailPage = PageFactory.initElements(driver, ComposeMailPage.class);
        composeMailPage.composeMail(user2.getUsername() + "@mail.ru", subject, text);
    }

    @Test(description = "after composing email press \"save mail\" button", groups = "save as draft", dependsOnMethods = "composeMailTest")
    public void saveMail() {
        composeMailPage.saveAsDraft();
    }

    @Test(description = "go to draft folder and check that email presents there", dependsOnMethods = "saveMail")
    public void checkDraftExists() {
        composeMailPage.goToDraft();
        handleAlert();
        draftsPage = PageFactory.initElements(driver, DraftsPage.class);
        Assert.assertTrue(draftsPage.checkIfMailSaved(user2.getUsername() + "@mail.ru"), "Drafts folder is empty");
    }

    @Test(description = "open previously saved mail and check if content was saved properly", dependsOnMethods = "checkDraftExists")
    public void checkDraftContentTest() {
        draftsPage.openSavedMail();
        composeMailPage = PageFactory.initElements(driver, ComposeMailPage.class);
        Assert.assertTrue(composeMailPage.checkMailContent(user2.getUsername() + "@mail.ru", subject, text), "Not all elements were found successfully.");
    }

    @Test(description = "press \"send\" button to send the mail", dependsOnMethods = "checkDraftExists")
    public void sendEmail() {
        composeMailPage.sendMail();
    }

    @Test(description = "go to draft and make sure that email no longer presents at this folder", dependsOnMethods = "sendEmail")
    public void checkThatEmailWasSend() {
        composeMailPage.goToSent();
        handleAlert();
        sentPage = PageFactory.initElements(driver, SentPage.class);
        Assert.assertTrue(sentPage.checkIfMailSaved(user2 + "@mail.ru"), "Sent folder is empty");
    }

    @Test(description = "go to \"sent\" folder and check if message presents there", dependsOnMethods = "sendEmail")
    public void checkEmailNotAtDrafts() {
        sentPage.goToDraft();
        handleAlert();
        draftsPage = PageFactory.initElements(driver, DraftsPage.class);
        Assert.assertFalse(draftsPage.checkIfMailSaved(user2.getUsername() + "@mail.ru"), "Drafts folder is not empty");
    }
}
