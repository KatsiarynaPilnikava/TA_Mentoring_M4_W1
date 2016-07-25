package com.epam.tat.selenium.tests;

import com.epam.tat.selenium.entities.User;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;


public class BasicTest {
    protected static final String DRAFTS_XPATH = "//a[@href=\"/messages/drafts/\"]";
    protected static final String SENT_XPATH = "//a[@href=\"/messages/sent/\"]";
    protected static final String LOGOUT_LINK_ID = "PH_logoutLink";
    protected WebDriver driver;
    protected User user1;
    protected User user2;

    @BeforeClass
    public void initUsers() {
        user1 = new User("pilnikava_1", "1UserPassword");
        user2 = new User("pilnikava_2", "2UserPassword");
    }

    @BeforeTest
    @Parameters({"url", "browser"})
    public void initStartPage(String url, String browser) {
        System.out.println("Initializing " + browser + " browser driver");
        driver = initDriver(browser);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        System.out.println("Browser driver for  " + browser + " browser was successfully initialized");
        driver.get(url);

    }

    @AfterTest
    public void clearEmailAndDisposeDriver() {
        goToDraft();
        driver.findElement(By.xpath("//body")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(By.xpath("//body")).sendKeys(Keys.DELETE);
        goToSent();
        driver.findElement(By.xpath("//body")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(By.xpath("//body")).sendKeys(Keys.DELETE);
        driver.findElement(By.id(LOGOUT_LINK_ID)).click();
        driver.close();
    }

    private static WebDriver initDriver(String browser) {
        WebDriver driver = new HtmlUnitDriver();
        if (browser.equals("opera")) {
            driver = new OperaDriver();
        } else if (browser.equals("google_chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
            driver = new ChromeDriver();
        } else if (browser.equals("mozilla")) {
            driver = new FirefoxDriver();
        }
        return driver;
    }

    protected void goToDraft() {
        driver.findElement(By.xpath(DRAFTS_XPATH)).click();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
        }
    }

    protected void goToSent() {
        try {
            driver.findElement(By.xpath(SENT_XPATH)).click();
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
        }
    }
}
