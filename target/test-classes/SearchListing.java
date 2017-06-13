import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SearchListing {

	WebDriver dr;
	
	@Test
	public void SearchPage(){
		
		dr = new FirefoxDriver();
		dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		dr.get("https://www.housingman.com/");
		
		String text1 = dr.findElement(By.xpath("//*[@class='report-box']/article[2]/p/span")).getText();
		System.out.println(text1);
	}
}
