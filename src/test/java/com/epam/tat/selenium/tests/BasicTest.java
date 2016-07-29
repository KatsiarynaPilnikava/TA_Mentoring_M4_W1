package com.epam.tat.selenium.tests;

import com.epam.tat.selenium.entities.User;
import com.epam.tat.selenium.page.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

public class BasicTest {

	private static final int TIMEOUT = 20;
	protected WebDriver driver;
	protected User user1;
	protected User user2;
	protected LoginPage loginPage;
	protected BasePage basePage;
	protected ComposeMailPage composeMailPage;
	protected DraftsPage draftsPage;
	protected SentPage sentPage;

	private static WebDriver initDriver(String browser) {
		WebDriver driver = new HtmlUnitDriver();
		if (browser.equals("opera")) {
			driver = new OperaDriver();
		} else if (browser.equals("google_chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
			driver = new ChromeDriver();
		} else if (browser.equals("mozilla")) {
			driver = new FirefoxDriver();
		}
		return driver;
	}

	@BeforeClass
	public void initUsers() {
		user1 = new User("pilnikava_1", "1UserPassword");
		user2 = new User("pilnikava_2", "2UserPassword");
	}

	@BeforeTest
	@Parameters({ "url", "browser" })
	public void initStartPage(String url, String browser) {
		System.out.println("Initializing " + browser + " browser driver");
		driver = initDriver(browser);
		driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
		System.out.println("Browser driver for  " + browser
				+ " browser was successfully initialized");
		driver.get(url);

	}

	@AfterTest
	public void clearEmailAndDisposeDriver() {
		basePage = PageFactory.initElements(driver, BasePage.class);
		basePage.goToDraft();
		handleAlert();
		draftsPage = PageFactory.initElements(driver, DraftsPage.class);
		draftsPage.clearDrafts();
		draftsPage.goToSent();
		handleAlert();
		sentPage = PageFactory.initElements(driver, SentPage.class);
		sentPage.clearSentMails();
		sentPage.logout();
		driver.close();
	}

	protected void handleAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException ignore) {
		}
	}
}
