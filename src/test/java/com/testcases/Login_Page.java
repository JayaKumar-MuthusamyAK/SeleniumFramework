package com.testcases;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.Keywords;
import com.util.TestUtil;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.util.Hashtable;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;

public class Login_Page {
	WebDriver driver;

	public static ExtentReports report = new ExtentReports(
			"C://Users//admin//housingman//workspace//HMan_Desktop//extented_Report_sample1.html");
	public static ExtentTest logger;

	@BeforeSuite
	public void extentReportStart() {
		report = new ExtentReports(
				"C://Users//admin//housingman//workspace//HMan_Desktop//extented_Report_sample1.html");
		logger = report.startTest("Login Page Test", "Automation Test");

		logger.assignAuthor("JAYAKUMAR MUTHUSAMY");
		logger.assignCategory("Loginn Page automation test for all scenarios");
	}

	@Test(dataProvider = "getData")
	public void testcase1(Hashtable<String, String> data) throws Exception {

		if (!TestUtil.isTestcaseExecutable("LoginTest", Keywords.xls)) {
			logger.log(LogStatus.SKIP, "Test case run mode is no");
			throw new SkipException("Test case run mode is no");
		}
		// Needs to check whether the test data runmode is no also should run
		// the test steps.
		if (!data.get("Runmode").equals("Y")) {
			logger.log(LogStatus.SKIP, "Test data run mode is no");
			throw new SkipException("Test data run mode is no");

		} else {
			Keywords k = Keywords.getInstance();
			k.executeKeywords("LoginTest", data);
		}

	}

	@AfterSuite
	public void extentReportEnd() {
		report.endTest(logger);
		report.flush();
		report.close();
	}

	@DataProvider
	public Object[][] getData() {

		return TestUtil.getData("LoginTest", Keywords.xls);
	}

}
