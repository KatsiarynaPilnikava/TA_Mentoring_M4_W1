package com.epam.tat.selenium.tests;


import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MailTest extends BasicTest {

    private static final String INPUT_LOGIN_ID = "mailbox__login";
    private static final String INPUT_PASSWORD_ID = "mailbox__password";
    private static final String LOGIN_BUTTON_ID = "mailbox__auth__button";
    private static final String NEW_MAIL_XPATH = "//span[@class=\"b-toolbar__btn__text b-toolbar__btn__text_pad\"]";
    private static final String MAIL_SENDTO_XPATH = "//textarea[@class=\"js-input compose__labels__input\"]";
    private static final String MAIL_SUBJECT_XPATH = "//input[@name=\"Subject\"]";
    private static final String SAVE_AS_DRAFT_XPATH = "//div[@data-name=\"saveDraft\"]";
    private static final String MAIL_TITLE_XPATH = "//a[@title=\"";
    private static final String MAIL_SENDTO_CHECK_XPATH = "//span[@data-text=\"";
    private static final String MAIL_TEXT_CHECK_XPATH = "//body[@id=\"tinymce\"]/div/div/div/*[@text=\"";
    private static final String SEND_MAIL_XPATH = "//div[@class=\"message-sent__title\"]";

    private String subject;
    private String text;

    @Test(description = "input login, password, press login button, check that login was successful", groups = "login test")
    public void loginTest() {
        logInAs();
        checkLogin();
    }

    @Test(description = "compose email to be send to user2, subject and text fields should be generated automaticly", dependsOnGroups = "login test")
    public void composeMailTest() {
        composeEmail();
    }

    @Test(description = "after composing email press \"save mail\" button", groups = "save as draft", dependsOnMethods = "composeMailTest")
    public void saveMail() {
        saveMailAsDraft();
    }

    @Test(description = "go to draft folder and check that email presents there", dependsOnMethods = "saveMail")
    public void checkDraftExists() {
        Assert.assertTrue(checkDraft(), "Drafts folder is empty");
    }

    @Test(description = "open previously saved mail and check if content was saved properly", dependsOnMethods = "checkDraftExists")
    public void checkDraftContentTest() {
        Assert.assertTrue(checkDraftContent(), "Not all elements were found successfully.");
    }

    @Test(description = "press \"send\" button to send the mail", dependsOnMethods = "checkDraftExists")
    public void sendEmail() {
        sendMail();
    }

    @Test(description = "go to draft and make sure that email no longer presents at this folder", dependsOnMethods = "sendEmail")
    public void checkThatEmailWasSend() {
        checkEmail();
    }

    @Test(description = "go to \"sent\" folder and check if message presents there", dependsOnMethods = "sendEmail")
    public void checkEmailNotAtDrafts() {
        checkNoDraft();
    }

    private void logInAs() {
        WebElement name_input = driver.findElement(By.id(INPUT_LOGIN_ID));
        name_input.clear();
        name_input.sendKeys(user1.getUsername());
        WebElement pass_input = driver.findElement(By.id(INPUT_PASSWORD_ID));
        pass_input.clear();
        pass_input.sendKeys(user1.getPassword());
        driver.findElement(By.id(LOGIN_BUTTON_ID)).click();
    }

    private void checkLogin() {
        WebElement name_element = driver.findElement(By.id(LOGOUT_LINK_ID));
        Assert.assertNotNull(name_element, "Login insuccessful");
    }

    private void composeEmail() {
        subject = RandomStringUtils.randomAlphabetic(8);
        text = RandomStringUtils.randomAlphabetic(12);
        driver.findElement(By.xpath(NEW_MAIL_XPATH)).click();
        WebElement emailTo = driver.findElement(By.xpath(MAIL_SENDTO_XPATH));
        emailTo.clear();
        emailTo.sendKeys(user2.getMail());
        WebElement subjectField = driver.findElement(By.xpath(MAIL_SUBJECT_XPATH));
        subjectField.clear();
        subjectField.sendKeys(subject);
        subjectField.sendKeys(Keys.TAB, text);
    }

    private void saveMailAsDraft() {
        driver.findElement(By.xpath(SAVE_AS_DRAFT_XPATH)).click();
    }

    private boolean checkDraft() {
        goToDraft();
        try {
            driver.findElement(By.xpath(MAIL_TITLE_XPATH + user2.getMail() + "\"]"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean checkDraftContent() {
        driver.findElement(By.xpath(MAIL_TITLE_XPATH + user2.getMail() + "\"]")).click();
        try {
            driver.findElement(By.xpath(MAIL_SENDTO_CHECK_XPATH + user2.getMail() + "\"]"));
            driver.findElement(By.xpath(MAIL_TEXT_CHECK_XPATH + text + "\"]"));
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Element was not found\n" + e);
            return false;
        }
    }

    private void sendMail() {
        driver.findElement(By.xpath("//body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
        driver.findElement(By.xpath(SEND_MAIL_XPATH));
    }

    private boolean checkEmail() {
        goToSent();
        try {
            driver.findElement(By.xpath(MAIL_TITLE_XPATH + user2.getMail() + "\"]"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean checkNoDraft() {
        try {
            driver.findElement(By.xpath(MAIL_TITLE_XPATH + user2.getMail() + "\"]"));
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

}
