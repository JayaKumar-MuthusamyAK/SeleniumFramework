package com.test.practices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.net.HttpURLConnection;

import org.apache.http.HttpConnection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class SampleTest {
	
	WebDriver driver;
	HttpURLConnection conn =null;
	public Logger APP_Logs=Logger.getLogger("devpinoyLogger");
	
	
	@Test
	public void BrokenLink() throws IOException, InterruptedException{
		
	    
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		driver = new ChromeDriver(cap);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		driver.get("http://54.169.220.31/projects?utf8=%E2%9C%93&city_id=bangalore&q=&search_filter%5Bsales_type_any%5D%5B%5D=new&keywords=&search_filter%5Bprice_range_any%5D%5B%5D=&search_filter%5Bprice_range_any%5D%5B%5D=");
		
		//List<WebElement> links = driver.findElements(By.xpath("//*[@href or @src]"));
		//System.out.println(links.size());
		
		Actions act = new Actions(driver);
		
		List<WebElement> projectNamelink= driver.findElements(By.xpath("//*[@id='project_listing']/div/div[1]/h2/a"));
		System.out.println(projectNamelink.size());
		APP_Logs.info("Size is"+projectNamelink.size());
		int oldLink = projectNamelink.size();
		int newLink =0;
		while(oldLink!=newLink){
			act.moveToElement(projectNamelink.get(oldLink-1)).build().perform();
			Thread.sleep(5000);
			act.moveToElement(driver.findElement(By.xpath("html/body/footer/div[1]/div/aside[2]/h3"))).build().perform();
			act.moveToElement(driver.findElement(By.xpath("html/body/footer/div[1]/div/aside[2]/h4[4]/a"))).build().perform();
			Thread.sleep(5000);
			newLink = oldLink;
			projectNamelink= driver.findElements(By.xpath("//*[@id='project_listing']/div/div[1]/h2/a"));
			oldLink = projectNamelink.size();
			
			System.out.println(oldLink);
			APP_Logs.info("Size is"+oldLink);
		}
		
		System.out.println("******************************************************************");
		List<WebElement> listOfaTag= driver.findElements(By.tagName("a"));
		
		
		listOfaTag.addAll(driver.findElements(By.tagName("img")));
		
		System.out.println(listOfaTag.size());
		APP_Logs.info("Links Size is"+listOfaTag.size());
		for(WebElement lst:listOfaTag)
		{ 
		
		if(lst.getAttribute("href")!=null)
		{
			
		//System.out.println(lst.getAttribute("href"));
		
		check_Status(lst.getAttribute("href"));
		
	
		}
		}
		
	}
	

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
					APP_Logs.info("---------------------------------------------------------------------");
					APP_Logs.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
				}
				else if(conn.getResponseCode()==500){
					System.out.println("---------------------------------------------------------------------");
					System.out.println(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
					APP_Logs.info("---------------------------------------------------------------------");
					APP_Logs.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
				}
				else if(conn.getResponseCode()==404){
					System.out.println("---------------------------------------------------------------------");
					System.out.println(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
					APP_Logs.info("---------------------------------------------------------------------");
					APP_Logs.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
				}
				else if(conn.getResponseCode()==402){
					System.out.println("---------------------------------------------------------------------");
					System.out.println(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
					APP_Logs.info("---------------------------------------------------------------------");
					APP_Logs.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
				}
				else 
				{
					System.out.println("---------------------------------------------------------------------");
					System.out.println(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
					APP_Logs.info("---------------------------------------------------------------------");
					APP_Logs.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
				}
			}
			catch(IOException e){
				System.out.println(e.getMessage());
			}
		}
			else{
			System.out.println("---------------------------------------------------------------------");	
			System.out.println(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
			APP_Logs.info("---------------------------------------------------------------------");
			APP_Logs.info(attribute +"->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+conn.getResponseCode());
		}
		
		
	}

	

}
