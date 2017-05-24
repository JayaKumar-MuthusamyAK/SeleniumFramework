package com.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.Keywords;
import com.util.TestUtil;

public class SignUp_Page {

	
	@BeforeMethod
	public void StartTest(){
		Login_Page.report.startTest("Sign Up Test");
		
	}
	
	@Test(dataProvider = "getData")
	public void testcase2(Hashtable<String, String> data) throws Exception {

		if (!TestUtil.isTestcaseExecutable("SignUpTest", Keywords.xls)) {
		   Login_Page.logger.log(LogStatus.SKIP,"Test case run mode is no");
			throw new SkipException("Test case run mode is no");
		}
		if (!data.get("Runmode").equals("Y")) {
		   Login_Page.logger.log(LogStatus.SKIP, "Test data run mode is no");
			throw new SkipException("Test data run mode is no");
		} else {
			Keywords k = Keywords.getInstance();
			k.executeKeywords("SignUpTest", data);
		}

	}

	@AfterMethod
	public void end(){
		Login_Page.report.endTest(Login_Page.logger);
	}
	
	@DataProvider
	public Object[][] getData() {

		return TestUtil.getData("SignUpTest", Keywords.xls);

	}
	
	

}
