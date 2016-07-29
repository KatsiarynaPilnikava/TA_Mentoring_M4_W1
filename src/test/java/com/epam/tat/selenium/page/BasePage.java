package com.epam.tat.selenium.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class BasePage {
    private static final String NEW_MAIL_XPATH = "//span[@class=\"b-toolbar__btn__text b-toolbar__btn__text_pad\"]";
    private static final String DRAFTS_XPATH = "//a[@href=\"/messages/drafts/\"]";
    private static final String SENT_XPATH = "//a[@href=\"/messages/sent/\"]";
    private static final String LOGOUT_LINK_ID = "PH_logoutLink";
    private static final String BODY = "//body";
    @FindBy(xpath = BODY)
    private WebElement page;
    @FindBy(xpath = NEW_MAIL_XPATH)
    private WebElement newMailButton;
    @FindBy(xpath = DRAFTS_XPATH)
    private WebElement draftButton;
    @FindBy(xpath = SENT_XPATH)
    private WebElement sentButton;
    @FindBy(id = LOGOUT_LINK_ID)
    private WebElement logoutButton;
    private WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void composeNewMail() {
        newMailButton.click();
    }

    public void goToDraft() {
        draftButton.click();
    }

    public void goToSent() {
        sentButton.click();
    }

    public void logout() {
        logoutButton.click();
    }

    public void checkLogin() {
        Assert.assertNotNull(logoutButton, "Login insuccessful");
    }
    public void clearField(WebElement element){
    	element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
    }
}
