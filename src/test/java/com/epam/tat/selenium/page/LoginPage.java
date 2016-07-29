package com.epam.tat.selenium.page;


import com.epam.tat.selenium.entities.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{
    private static final String INPUT_LOGIN_ID = "mailbox__login";
    private static final String INPUT_PASSWORD_ID = "mailbox__password";
    private static final String LOGIN_BUTTON_ID = "mailbox__auth__button";

    @FindBy(id = INPUT_LOGIN_ID)
    private WebElement inputUser;
    @FindBy(id = INPUT_PASSWORD_ID)
    private WebElement inputPassword;
    @FindBy(id = LOGIN_BUTTON_ID)
    private WebElement loginButton;

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
    	 super(driver);
         this.driver = driver;
    }

    public void loginAs(User user) {
        super.clearField(inputUser);
        inputUser.sendKeys(user.getUsername());
        super.clearField(inputPassword);
        inputPassword.sendKeys(user.getPassword());
        loginButton.click();
    }
}
