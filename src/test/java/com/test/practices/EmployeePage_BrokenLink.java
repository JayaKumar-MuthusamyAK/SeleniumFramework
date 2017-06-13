package com.test.practices;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class EmployeePage_BrokenLink {

	WebDriver driver;
	HttpURLConnection conn = null;
	public Logger APP_Logs = Logger.getLogger("devpinoyLogger");

	@Test
	public void BrokenLink() throws IOException, InterruptedException {

		//System.setProperty("webdriver.chrome.driver",
				//System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
		//DesiredCapabilities cap = DesiredCapabilities.chrome();
		//cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		//driver = new ChromeDriver(cap);
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.get("http://54.169.220.31");
		
		driver.findElement(By.xpath("//a[@data-target='#login']")).click();
		driver.findElement(By.id("email_login")).sendKeys("jakay34@gmail.com");
		driver.findElement(By.id("password_login")).sendKeys("12345678");
		driver.findElement(By.xpath("//*[@id='login_form']/button")).click();

		System.out.println("******************************************************************");
		
		
		List<WebElement> listOfaTag = driver.findElements(By.tagName("a"));

		listOfaTag.addAll(driver.findElements(By.tagName("img")));

		System.out.println(listOfaTag.size());
		APP_Logs.info("Links Size is" + listOfaTag.size());
		for (WebElement lst : listOfaTag) {

				if (lst.getAttribute("href")!=null) {
					// System.out.println(lst.getAttribute("href"));
					check_Status(lst.getAttribute("href"));
				}
		}

	}

	public void check_Status(String attribute) throws IOException {

		if (attribute.startsWith("http")) {
			URL url = new URL(attribute);

			conn = (HttpURLConnection) url.openConnection();

			try {
				conn.connect();
				// System.out.println(conn.getResponseCode());
				if (conn.getResponseCode() == 200) {
					System.out.println("---------------------------------------------------------------------");
					System.out.println(
							attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
					APP_Logs.info("---------------------------------------------------------------------");
					APP_Logs.info(attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
				} else if (conn.getResponseCode() == 500) {
					System.out.println("---------------------------------------------------------------------");
					System.out.println(
							attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
					APP_Logs.info("---------------------------------------------------------------------");
					APP_Logs.info(attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
				} else if (conn.getResponseCode() == 404) {
					System.out.println("---------------------------------------------------------------------");
					System.out.println(
							attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
					APP_Logs.info("---------------------------------------------------------------------");
					APP_Logs.info(attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
				} else if (conn.getResponseCode() == 402) {
					System.out.println("---------------------------------------------------------------------");
					System.out.println(
							attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
					APP_Logs.info("---------------------------------------------------------------------");
					APP_Logs.info(attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
				} else {
					System.out.println("---------------------------------------------------------------------");
					System.out.println(
							attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
					APP_Logs.info("---------------------------------------------------------------------");
					APP_Logs.info(attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("---------------------------------------------------------------------");
			System.out.println(
					attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
			APP_Logs.info("---------------------------------------------------------------------");
			APP_Logs.info(attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
		}

	}

}
