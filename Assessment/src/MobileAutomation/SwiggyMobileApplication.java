package MobileAutomation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class SwiggyMobileApplication 
{
	String DeviceName="emulator-5554";
	public AndroidDriver<AndroidElement> driver;
	@BeforeClass
	public void OpenDevice() throws MalformedURLException, InterruptedException
	{
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "9.0");
		caps.setCapability("deviceName", DeviceName);
		caps.setCapability("udid", DeviceName);
		
		caps.setCapability("appPackage", "in.swiggy.android");
		caps.setCapability("appActivity", "in.swiggy.android.activities.HomeActivity");
		Thread.sleep(2000);
		driver = new AndroidDriver(new URL("http://localhost:4444/wd/hub"),caps);	
		Thread.sleep(20000);
	}
	
	@Test
	public void TC_SwiggyApp() throws InterruptedException
	{
		
		AndroidElement location_access ;
		if(driver.findElementsById("in.swiggy.android:id/enter_location_header_textview").size()!=0)
		{
			location_access= driver.findElementById("in.swiggy.android:id/enter_location_header_textview");
			Assert.assertTrue(location_access.isDisplayed());
			location_access.click();
		}
		driver.findElementById("in.swiggy.android:id/enter_location_header_textview");
		AndroidElement searchBox,location_match;
		Scanner sc= new Scanner(System.in);
		List<AndroidElement> locations = null ; 
		String temp = "Chandni Chowk",temp2=null;
		
		
		
		Thread.sleep(5000);
		searchBox = driver.findElementById("in.swiggy.android:id/location_description");
		Thread.sleep(2000);
		searchBox.sendKeys("Chandni Chowk");
	    Thread.sleep(5000);
		//locations = driver.findElementsByClassName("android.widget.TextView");
	    locations = driver.findElementsById("in.swiggy.android:id/google_place_search_title_text");
		
	    
		
		for(AndroidElement iter : locations)
		{
			System.out.println(iter.getText());
			if(iter.getText().toLowerCase().contains(temp.toLowerCase()))
			{
				System.out.println(iter.getText().toLowerCase().contains(temp.toLowerCase()));
				temp2="text(\""+iter.getText()+ "\")";
				System.out.println(temp2);
				location_match=driver.findElementByAndroidUIAutomator(temp2);
				Thread.sleep(4000);
				location_match.click();
				Thread.sleep(4000);
				if(driver.findElementsByAndroidUIAutomator(" text(\"CONFIRM LOCATION\") ").size()!=0)
				{
					driver.findElementByAndroidUIAutomator(" text(\"CONFIRM LOCATION\") ").click();
				}
				break;
			}
			
		}
		Thread.sleep(4000);
		AndroidElement searchFood_Bar,searchFood;
		searchFood_Bar=driver.findElementById("in.swiggy.android:id/disc_search_bar_container");
		searchFood_Bar.click();
		Thread.sleep(5000);
		//in.swiggy.android:id/search_query_hint
		searchFood = driver.findElementById("in.swiggy.android:id/et_search_query_v2");
		System.out.println("A0");
		Thread.sleep(2000);
		searchFood.sendKeys("Dosa");
		System.out.println("A1");
		Thread.sleep(4000);
		Assert.assertTrue( driver.findElementByAccessibilityId("Dosa").isDisplayed());
			
		driver.findElementByAccessibilityId("Dosa").click();
		
		
		WebDriverWait w1 = new WebDriverWait(driver,20);
		
		w1.until(ExpectedConditions.visibilityOfElementLocated(By.id("in.swiggy.android:id/restaurant_details")));
		
		Assert.assertTrue( driver.findElementById("in.swiggy.android:id/restaurant_details").isDisplayed(),"Restaurants Available");
		
		List<AndroidElement> restaurants=driver.findElementsById("in.swiggy.android:id/textView_restaurant_name");
		for(AndroidElement r : restaurants)
		{
			System.out.println(r.getText());
		}
		
		String Restaurant_Name=restaurants.get(0).getText();
		restaurants.get(0).click();
		
		//If switch to Faster Outlet Option appears we need to go back by hitting cross sign
		Thread.sleep(5000);
		if(driver.findElementsById("in.swiggy.android:id/full_width_button_label").size()!=0)
		{
			driver.findElementByAccessibilityId("Go Back").click();
		}
		
		Assert.assertTrue(driver.findElementById("in.swiggy.android:id/item_detail").isDisplayed(),"Food Items available");
		
		AndroidElement FoodItem = driver.findElementById("in.swiggy.android:id/item_name");
		String FoodItemName = FoodItem.getText();
		AndroidElement Addbutton = driver.findElementById("in.swiggy.android:id/quantity_text_1");
		Addbutton.click();
		
		//If Customize section if available we will skip it
		if(driver.findElementsByAndroidUIAutomator(" text(\"Customise as per your taste\") ").size()!=0)
		{
			if(driver.findElementByClassName("android.widget.CheckBox").getAttribute("checked").equalsIgnoreCase("false"))
			{
				driver.findElementByClassName("android.widget.CheckBox").click();
			}
			Thread.sleep(2000);
			driver.findElementByAndroidUIAutomator(" text(\"Add Item to Cart\") ").click();
		}
		
		
		w1.until(ExpectedConditions.visibilityOfElementLocated(By.id("in.swiggy.android:id/tv_checkout")));
		Assert.assertTrue(driver.findElementByAndroidUIAutomator(" text(\"View Cart\") ").isDisplayed());
		driver.findElementByAndroidUIAutomator(" text(\"View Cart\") ").click();
		
		//WebDriverWait w2 = new WebDriverWait(driver,40);
		Thread.sleep(10000);
		if(driver.findElementsByAccessibilityId("Go Back").size()!=0)
		{
			driver.findElementByAccessibilityId("Go Back").click();
			Thread.sleep(2000);
		}
		
		//w2.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-renderer-container")));
		Thread.sleep(4000);
		Boolean b1=false, b2=false;
		String s1,s2;
		
		System.out.println(Restaurant_Name+" - - - - "+ FoodItemName);
		
		s1="text(\""+ Restaurant_Name + "\")";
		b1= driver.findElementByAndroidUIAutomator(s1).isDisplayed();
		/*
		s2="text(\""+ FoodItemName + "\")";
		
		System.out.println(s1+" - - - - - "+s2);
		b1= driver.findElementByAndroidUIAutomator(s1).isDisplayed();
		b2=driver.findElementByAndroidUIAutomator(s1).isDisplayed();
		
		
		List<AndroidElement> text = driver.findElementsByClassName("android.view.View");
		for(AndroidElement t : text)
		{
			if(t.getText().contains(Restaurant_Name))
			{
				System.out.println(t.getText());
				b1=true;
			}
		}
		*/
		
		
		List<AndroidElement> texts = driver.findElementsByClassName("android.view.View");
		
		for(AndroidElement t : texts)
		{
			if(t.getText().contains(FoodItemName) || FoodItemName.contains(t.getText()))
			{
				System.out.println(t.getText());
				b2=true;
			}
		}
		
		if(b1 && b2)
		{
			Assert.assertTrue(b1&&b2,"Food Item :"+FoodItemName+" from Restaurant :"+Restaurant_Name+" is added in Cart");
			
		}
		else
		{
			if(!b1)
			{
				Assert.assertTrue(b1);
			}
			if(!b2)
			{
				Assert.assertTrue(b2);
			}
		}
	}
	

}
