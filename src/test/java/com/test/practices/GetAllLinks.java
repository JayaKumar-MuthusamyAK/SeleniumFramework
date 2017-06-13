package com.test.practices;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpConnection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class GetAllLinks {

	WebDriver driver;
	HttpURLConnection conn=null;
	//HashMap<Integer, String> links = new HashMap<Integer, String>();
	@Test
	public void getLinks() throws InterruptedException, IOException{
		
		driver = new FirefoxDriver();
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		
		driver.get("http://54.169.220.31");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//a[@data-target='#login']")).click();
		driver.findElement(By.id("email_login")).sendKeys("data@gmail.com");
		driver.findElement(By.id("password_login")).sendKeys("12345678");
		driver.findElement(By.xpath("//*[@id='login_form']/button")).click();
		
		Thread.sleep(10000);
		
		List<String> listOfNames = new ArrayList<String>();
		
		List<WebElement> elements = driver.findElements(By.tagName("a"));
		elements.addAll(driver.findElements(By.tagName("img")));
		for(WebElement ele : elements){
			//Thread.sleep(2000);
			System.out.println(ele.getAttribute("href"));
			listOfNames.add(ele.getAttribute("href"));
		}
		System.out.println("----------------------------------------------------------------------");
		System.out.println(listOfNames);
		
		Object[] st = listOfNames.toArray();
		for(Object s : st){
			if(listOfNames.indexOf(s)!=listOfNames.lastIndexOf(s)){
				listOfNames.remove(listOfNames.lastIndexOf(s));
			}
			
			
		}
		System.out.println("----------------------------------------------------------------------");
		//System.out.println(listOfNames);
		
		for(int i=0; i<listOfNames.size();i++){
			
			if(listOfNames.get(i)!=null){
				if(listOfNames.get(i).startsWith("http")){
					System.out.println(listOfNames.get(i));
					//driver.get(listOfNames.get(i));
					CheckingLink(listOfNames.get(i));
					//return listOfNames.get(i);
					//Thread.sleep(7000);
					
			}
			}
			
	
		}
		//return "";
	}
	
	public void CheckingLink(String URL) throws InterruptedException, IOException{
		
		driver.navigate().to(URL);
		Thread.sleep(20000);
		
		List<WebElement> listOfaTag = driver.findElements(By.tagName("a"));

		listOfaTag.addAll(driver.findElements(By.tagName("img")));

		System.out.println(listOfaTag.size());
		//int count=0;
		for (WebElement lst : listOfaTag) {

				if (lst.getAttribute("href")!=null) {
					//links.put(count, lst.getAttribute("href"));
				    System.out.println(lst.getAttribute("href"));
					check_Status(lst.getAttribute("href"));
					//count++;
				}
		
	}
}


	private void check_Status(String attribute) throws IOException, InterruptedException {
		
		if (attribute.startsWith("http")) {
			URL url = new URL(attribute);

			conn = (HttpURLConnection) url.openConnection();

			try {
				conn.connect();
				Thread.sleep(2000);
				// System.out.println(conn.getResponseCode());
				if (conn.getResponseCode() == 200) {
					System.out.println("---------------------------------------------------------------------");
					System.out.println(
							attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
					
				} else if (conn.getResponseCode() == 500) {
					System.out.println("---------------------------------------------------------------------");
					System.out.println(
							attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
				
				} else if (conn.getResponseCode() == 404) {
					System.out.println("---------------------------------------------------------------------");
					System.out.println(
							attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
					
				} else if (conn.getResponseCode() == 402) {
					System.out.println("---------------------------------------------------------------------");
					System.out.println(
							attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
					
				} else {
					System.out.println("---------------------------------------------------------------------");
					System.out.println(
							attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
					
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
		} 
		//else {
			//System.out.println("---------------------------------------------------------------------");
			//System.out.println(
			//		attribute + "->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + conn.getResponseCode());
			
		//}

	}
	
	
	}

