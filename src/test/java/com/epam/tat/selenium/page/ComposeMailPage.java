package com.epam.tat.selenium.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class ComposeMailPage extends BasePage {
    private static final String MAIL_SENDTO_XPATH = "//textarea[@class=\"js-input compose__labels__input\"]";
    private static final String MAIL_SUBJECT_XPATH = "//input[@name=\"Subject\"]";
    private static final String SAVE_AS_DRAFT_XPATH = "//div[@data-name=\"saveDraft\"]";
    private static final String MAIL_SENDTO_CHECK_XPATH = "//span[contains(.,'%s')]";
    private static final String MAIL_TEXT_CHECK_XPATH = "//body[@id='tinymce']//div[contains(.,'%s')]";
    private static final String BODY = "//body";
    @FindBy(xpath = BODY)
    private WebElement page;
    @FindBy(xpath = MAIL_SENDTO_XPATH)
    private WebElement sendTo;
    @FindBy(xpath = SAVE_AS_DRAFT_XPATH)
    private WebElement saveAsDraftButton;
    @FindBy(xpath = MAIL_SUBJECT_XPATH)
    private WebElement mailSubjectInput;
    @FindBy(xpath = MAIL_SENDTO_CHECK_XPATH)
    private WebElement sendToCheck;
    @FindBy(xpath = MAIL_TEXT_CHECK_XPATH)
    private WebElement textCheck;
    private WebDriver driver;

    public ComposeMailPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void composeMail(String to, String subject, String text) {
        super.clearField(sendTo);
        sendTo.sendKeys(to);
        super.clearField(mailSubjectInput);
        mailSubjectInput.sendKeys(subject);
        mailSubjectInput.sendKeys(Keys.TAB, text);
    }

    public void saveAsDraft() {
        saveAsDraftButton.click();
    }

    public boolean checkMailContent(String sendTo, String subject, String text) {
        try {
            driver.findElement(By.xpath(String.format(MAIL_SENDTO_CHECK_XPATH, sendTo)));
            driver.findElement(By.xpath(String.format(MAIL_TEXT_CHECK_XPATH, text)));
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Element was not found\n" + e);
            return false;
        }
    }

    public void sendMail() {
        page.sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
    }

}
