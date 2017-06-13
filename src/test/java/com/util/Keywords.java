package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.testcases.Login_Page;

public class Keywords {

	public static Xls_Reader xls = new Xls_Reader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\Test Suite1.xlsx");

	HttpURLConnection conn=null;
	public Logger APP_LOGS=null;

	public WebDriver dr;
	public Properties OR;
	public DesiredCapabilities cap;
	public static Keywords keywords = null;
	ObjectMap obj = new ObjectMap(System.getProperty("user.dir") + "\\src\\test\\java\\com\\config\\OR.properties");

	boolean isTaken = false;
	File errorscr;
	DateFormat date = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
	
	WebDriverWait wait = null;
	
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

		APP_LOGS=Logger.getLogger("devpinoyLogger");

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
					
				case "linkCheck":
					result = linkCheck();
					break;
					
				case "checkNavigateURL":
					result = checkNavigateURL(data.get(datakeys));
					break;
					
				case "checkPagination":
					result = checkPagination(objectkeys,data.get(datakeys));
					break;
					
				case "waitUtilFind":
					result = waitUtilFind(obj.getLocator(objectkeys));
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


	private String waitUtilFind(By locator) {
		wait = new WebDriverWait(dr, 60);
		wait.until(ExpectedConditions.visibilityOf(dr.findElement(locator)));
		return "PASS";
	}

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
			dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		} else {
			System.out.println("Not Matching the any Browser");
			// APP_LOGS.debug("Not Matching the any Browser");
		}

		return "PASS";
	}

	public String navigateURL(String keys) {

		if(keys.startsWith("http")){
			dr.get(keys);
		}
		else{
		dr.get(OR.getProperty(keys));
		}
		// APP_LOGS.debug("Navigation is done");
		return "PASS";
	}

	public String checkNavigateURL(String keys1) throws InterruptedException, Exception{
	
		System.out.println(keys1.split("\\|")[0]);
		System.out.println(keys1.split("\\|")[1]);
		
		if(!keys1.split("\\|")[0].equals("Y")){
			navigateURL(keys1.split("\\|")[1]);
		}
		else{
			
			navigateURL(keys1.split("\\|")[1]);
			click(obj.getLocator("login_link"));
			input(obj.getLocator("emailtxt_box"), "jakay34@gmail.com");
			input(obj.getLocator("pwdtxt_box"), "12345678");
			click(obj.getLocator("submit_btn"));
			//navigateURL(keys1.split("\\|")[1]);
		}
		return "PASS";
	}
	

	public String checkPagination(String objectkeys,String keys) throws Exception {
		
		 String text1= objectkeys.split("\\|")[0];
		 String text2= objectkeys.split("\\|")[1];
		 String text3= objectkeys.split("\\|")[2];
		 System.out.println(text3);
		 System.out.println(keys);
		if(keys.equals("Y")){
			Actions act = new Actions(dr);
			List<WebElement> projectNamelink= dr.findElements(obj.getLocator(text1));
			System.out.println(projectNamelink.size());
			APP_LOGS.info("Size is"+projectNamelink.size());
			int oldLink = projectNamelink.size();
			int newLink =0;
			while(oldLink!=newLink){
				
				act.moveToElement(projectNamelink.get(oldLink-1)).build().perform();
				Thread.sleep(5000);
				act.moveToElement(dr.findElement(obj.getLocator(text2))).build().perform();
				act.moveToElement(dr.findElement(obj.getLocator(text3))).build().perform();
				Thread.sleep(5000);
				newLink = oldLink;
				projectNamelink=  dr.findElements(obj.getLocator(text1));
				oldLink = projectNamelink.size();
				
				System.out.println(oldLink);
				APP_LOGS.info("Size is"+oldLink);
			}
		}
		
		
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
		waitUtilFind(locator);
		dr.findElement(locator).click();
		return "PASS";
	}

	public String input(By locator, String data) throws InterruptedException {
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
	
	public String linkCheck() throws IOException, InterruptedException{
		
		
      List<WebElement> listOfaTag= dr.findElements(By.tagName("a"));
		
		//for(int i=0; i< listOfaTag.size();i++)
		//{
			//System.out.println(listOfaTag.get(i));
			
			listOfaTag.addAll(dr.findElements(By.tagName("img")));
			System.out.println(listOfaTag.size());
			for(WebElement lst:listOfaTag){ 
				if(lst.getAttribute("href")!=null){
				//System.out.println(lst.getAttribute("href"));
					
						check_Status(lst.getAttribute("href"));
					
				}
			//}
		}
	
		return "PASS";
		
	}

	/*
	 * ADDITIONAL FUNCTIONALITY KEYWORDS*
	 */

		public void check_Status(String attribute) throws IOException {
			
			if(attribute.startsWith("http")){
				URL url = new URL(attribute);
				
				 conn = (HttpURLConnection)url.openConnection();
				
				try{
					conn.connect();
					//System.out.println(conn.getResponseCode());
					if(conn.getResponseCode()==200){
						System.out.println("---------------------------------------------------------------------");
						System.out.println(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
						APP_LOGS.info("---------------------------------------------------------------------");
						APP_LOGS.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
					}
					else if(conn.getResponseCode()==500){
						System.out.println("---------------------------------------------------------------------");
						System.out.println(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
						APP_LOGS.info("---------------------------------------------------------------------");
						APP_LOGS.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
					}
					else if(conn.getResponseCode()==404){
						System.out.println("---------------------------------------------------------------------");
						System.out.println(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
						APP_LOGS.info("---------------------------------------------------------------------");
						APP_LOGS.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
					}
					else if(conn.getResponseCode()==402){
						System.out.println("---------------------------------------------------------------------");
						System.out.println(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
						APP_LOGS.info("---------------------------------------------------------------------");
						APP_LOGS.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
					}
					else 
					{
						System.out.println("---------------------------------------------------------------------");
						System.out.println(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
						APP_LOGS.info("---------------------------------------------------------------------");
						APP_LOGS.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
					}
				}
				catch(IOException e){
					System.out.println(e.getMessage());
				}
			}
				else{
					System.out.println("---------------------------------------------------------------------");
					System.out.println(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
					APP_LOGS.info("---------------------------------------------------------------------");
					APP_LOGS.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
			}
			
			
		}
		

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
