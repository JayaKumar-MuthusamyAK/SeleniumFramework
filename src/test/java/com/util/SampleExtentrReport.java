package com.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SampleExtentrReport {
	
	WebDriver driver;
	ExtentReports extent;
	ExtentTest logger;
	
	@Test
	public void ExportReport(){
		
		extent = new ExtentReports("D://extented_Report_sample2.html");
	
		
			logger = extent.startTest("Sample Test","Automation is Started");
		
		logger.assignAuthor("JAYAKUMAR MUTHUSAMY");
		logger.assignCategory("Sample categories report");
		
		System.setProperty("webdriver.chrome.driver", "D:\\build\\chromedriver.exe");
		
		driver = new ChromeDriver();
		
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		logger.log(LogStatus.INFO, "Browser is Opened");
		
		driver.get("http://54.169.220.31/");
		logger.log(LogStatus.INFO, "Testing URL is Navigated");
		System.out.println(driver.getTitle());
		
		logger.log(LogStatus.PASS, "Title is verified");
		
		driver.quit();
		extent.endTest(logger);
		extent.flush();
		extent.close();
	}

}
