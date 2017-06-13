package com.test.practices;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class SEO_BuildersURL {

	WebDriver driver;
	@Test
	public void test_URL() throws InterruptedException{
		
		//ChromeOptions options = new ChromeOptions();
		//options.addArguments("disable-infobars");
		//System.setProperty("webdriver.chrome.driver","C:\\Users\\admin\\Downloads\\chromedriverorl.exe");
		driver = new FirefoxDriver();
		
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.get("https://www.housingman.com/sitemaps/cities.xml");
		
		List<WebElement> links = driver.findElements(By.xpath("//*[name()='loc']"));
		System.out.println(links.size());
		
		for(int i=0; i<links.size();i++){
			//Thread.sleep(200);
			System.out.println(i+ "."+links.get(i).getText());
		}
	}
}
