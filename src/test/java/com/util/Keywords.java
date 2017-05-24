package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.testcases.Login_Page;

public class Keywords {

	public static Xls_Reader xls = new Xls_Reader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\Test Suite1.xlsx");

	// public Logger APP_LOGS=null;

	public WebDriver dr;
	public Properties OR;
	public DesiredCapabilities cap;
	public static Keywords keywords = null;
	ObjectMap obj = new ObjectMap(System.getProperty("user.dir") + "\\src\\test\\java\\com\\config\\OR.properties");

	boolean isTaken = false;
	File errorscr;
	DateFormat date = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");

	public Keywords() {
		try {
			OR = new Properties();
			FileInputStream FI = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\java\\com\\config\\OR.properties");

			OR.load(FI);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void executeKeywords(String testcases, Hashtable<String, String> data) throws Exception {

		System.out.println("Executable test case is:" + testcases);

		String keyword = null;
		String objectkeys = null;
		String datakeys = null;

		// APP_LOGS=Logger.getLogger("devpinoyLogger");

		for (int rNum = 2; rNum <= xls.getRowCount("Test Steps"); rNum++) {
			if (testcases.equals(xls.getCellData("Test Steps", "TCID", rNum))) {
				keyword = xls.getCellData("Test Steps", "Keyword", rNum);
				objectkeys = xls.getCellData("Test Steps", "Object", rNum);
				datakeys = xls.getCellData("Test Steps", "Data", rNum);

				System.out.println(keyword + "--" + objectkeys + "--" + datakeys);

				// APP_LOGS.debug(keyword + "--" + objectkeys + "--" +
				// datakeys);
				Login_Page.logger.log(LogStatus.INFO, keyword + "--" + objectkeys + "--" + datakeys);
				String result = null;
				switch (keyword) {

				case "openBrowser":
					result = openBrowser(objectkeys);
					break;
				case "navigateURL":
					result = navigateURL(objectkeys);
					break;
				case "verifyTitle":
					result = verifyTitle(objectkeys);
					break;
				case "verifyLink":
					result = verifyLink(obj.getLocator(objectkeys));
					break;
				case "click":
					result = click(obj.getLocator(objectkeys));
					break;
				case "input":
					result = input(obj.getLocator(objectkeys), data.get(datakeys));
					break;
				case "verifyPopup":
					result = verifyPopup(obj.getLocator(objectkeys));
					break;
				case "close":
					result = close();
					break;

				default:
					System.out.println("No Matching any method. Please Add new method");

				}

				if (result.equals("PASS")) {
					Login_Page.logger.log(LogStatus.PASS, keyword + "--> Pass");
				} else {
					Login_Page.logger.log(LogStatus.ERROR, keyword + "--> Fail");
				}
			}

		}

	}

	/* GENERAL KEYWORDS */

	public String openBrowser(String keys) {

		if (OR.getProperty(keys).equals("firefox")) {
			System.out.println("Opening the firefox browser");
			// APP_LOGS.debug("Opening the firefox browser");
			Login_Page.logger.log(LogStatus.INFO, "Opening the firefox browser");
			dr = new FirefoxDriver();
			dr.manage().window().maximize();
			dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		} else if (OR.getProperty(keys).equals("chrome")) {
			System.out.println("Opening the chrome browser");
			Login_Page.logger.log(LogStatus.INFO, "Opening the firefox browser");
			// APP_LOGS.debug("Opening the Chrome browser");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
			dr = new ChromeDriver();
		} else {
			System.out.println("Not Matching the any Browser");
			// APP_LOGS.debug("Not Matching the any Browser");
		}

		return "PASS";
	}

	public String navigateURL(String keys) {

		dr.get(OR.getProperty(keys));
		// APP_LOGS.debug("Navigation is done");
		return "PASS";
	}

	public String verifyTitle(String objectkeys) {

		System.out.println(dr.getTitle());
		// APP_LOGS.debug("Verifing the Title");
		Assert.assertTrue(dr.getTitle().equals(OR.getProperty(objectkeys)),
				"Title is Matching. HOUSINGMAN Portal opened Successfully!!");

		return "PASS";
	}

	public String verifyLink(By locator) {

		// APP_LOGS.debug("Verifing the Link");
		if (dr.findElements(locator).size() != 0) {
			System.out.println("Link is present");
		}

		return "PASS";
	}

	public String click(By locator) throws InterruptedException {

		// APP_LOGS.debug("Clicking the link");
		Thread.sleep(3000);
		dr.findElement(locator).click();
		return "PASS";
	}

	public String input(By locator, String data) {
		// APP_LOGS.debug("Entering the field");
		dr.findElement(locator).sendKeys(data);

		return "PASS";
	}

	public String verifyPopup(By locator) {

		// APP_LOGS.debug("Verifing the Popup");
		String text = dr.findElement(locator).getText();
		if (text.equals("New User? Sign Up!")) {
			System.out.println("Sign Up Popup is present!!!");

		}
		return "PASS";
	}

	public String close() {

		// APP_LOGS.debug("driver id Closed");
		if (dr != null) {
			dr.quit();
		}
		return "PASS";
	}

	/*
	 * ADDITIONAL FUNCTIONALITY KEYWORDS*
	 */

	public static Keywords getInstance() {

		if (keywords == null) {
			try {
				keywords = new Keywords();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return keywords;

	}

}
