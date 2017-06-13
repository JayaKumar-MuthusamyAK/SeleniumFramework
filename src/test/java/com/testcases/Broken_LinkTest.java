package com.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.util.Keywords;
import com.util.TestUtil;

public class Broken_LinkTest {


	WebDriver driver;
	
	//HttpURLConnection conn =null;
	@Test(dataProvider="getData")
	public void BrokenLink(Hashtable<String, String> data) throws Exception{
		
		if(!TestUtil.isTestcaseExecutable("BrokenLinkTest", Keywords.xls)){
			throw new SkipException("This test cases run mode is no");
		}
		

		if(!data.get("Runmode").equals("Y")){
			throw new SkipException("This test data run mode is no");
		}
		else{
			Keywords k = Keywords.getInstance();
			k.executeKeywords("BrokenLinkTest",data);
		}
	
		
		
	}
	
	@DataProvider
	public Object[][] getData(){
		
		return TestUtil.getData("BrokenLinkTest", Keywords.xls);
		
	}
	
}
