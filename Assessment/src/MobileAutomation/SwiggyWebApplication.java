package MobileAutomation;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;

public class SwiggyWebApplication 
{
	public AndroidDriver<WebElement> driver;
	public String DeviceName = "emulator-5556";
	@BeforeClass
	public void OpenDevice() throws MalformedURLException, InterruptedException
	{
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browserName", "chrome");
		caps.setCapability("deviceName", DeviceName);
		caps.setCapability("udid", DeviceName);
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "9.0");

		driver = new AndroidDriver(new URL("http://localhost:4444/wd/hub"),caps);
		Thread.sleep(4000);
		driver.get("https://www.swiggy.com/");
		Thread.sleep(4000);
	}
	
	@Test
	public void TC_SwiggyWeb() throws InterruptedException
	{	
		WebDriverWait wait=new WebDriverWait(driver, 15);
		
		
		WebElement location= driver.findElement(By.xpath("//button[text()='SETUP YOUR LOCATION']"));
		Assert.assertTrue(location.isDisplayed());
		location.click();
		
		Thread.sleep(4000);
		
		WebElement location_textbox= driver.findElement(By.xpath("//input[@placeholder='Enter area, street name...']"));
		
		Assert.assertTrue(location_textbox.isDisplayed());
		
		location_textbox.sendKeys("Chandni Chowk");
		Thread.sleep(4000);
		
		wait=new WebDriverWait(driver, 20);
		List<WebElement> Location_set = driver.findElements(By.xpath("//span[@class='_1kMGJ']"));
		
		
		
		for(WebElement loc : Location_set)
		{
			if(loc.getText().contains("Chandni Chowk"))
			{
				loc.click();
				break;
			}
		}
		
		Thread.sleep(4000);
		
		WebElement Search = driver.findElement(By.xpath("//span[text()='Search']"));
		Assert.assertTrue(Search.isDisplayed());
		
		Search.click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@placeholder='Search for restaurants and food']")).sendKeys("Dosa");
		Thread.sleep(4000);
		driver.findElement(By.xpath("//b[text()='Dosa']")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@class='styles_restaurant__20fB8']"))));
		Assert.assertTrue(driver.findElement(By.xpath("//a[@class='styles_restaurant__20fB8']")).isDisplayed(),"List of Restaurants is Displayed");
		
		String RestaurantName = driver.findElement(By.xpath("//div[@class='styles_restaurantName__5VIQZ styles_restaurantNameBold__2OmFY']")).getText();	
		String FoodName = driver.findElement(By.xpath("//h3[@class='styles_itemNameText__3ZmZZ']")).getText();	
		
		driver.findElement(By.xpath("//span[text()='ADD']")).click();
		
		Thread.sleep(4000);
		
		if(driver.findElements(By.xpath("//span[text()='Add Item']")).size()!=0)
		{
			
			//if(driver.findElement(By.xpath("//input[@type='checkbox']")).getAttribute("value").equalsIgnoreCase("0"))
			//{
			//	driver.findElement(By.xpath("//input[@type='checkbox']")).click();
			//}
			
			driver.findElement(By.xpath("//span[text()='Add Item']")).click();
			Thread.sleep(4000);
		}
		
		
		driver.findElement(By.xpath("//span[text()='Checkout']")).click();
		Thread.sleep(5000);
		
		Boolean b1=false, b2=false;
		
		if(driver.findElements(By.xpath("//div[text()='"+FoodName+"']")).size()!=0)
		{
			System.out.println(driver.findElement(By.xpath("//div[text()='"+FoodName+"']")).getText());
			b1=true;
		}
		
		
		String R_name = driver.findElement(By.xpath("//div[@class='_38vxc']/span")).getText();
		if(RestaurantName.contains(R_name) || R_name.contains(RestaurantName))
		{
			b2=true;
			System.out.println(R_name);
		}
		
		Assert.assertTrue(b1&&b2, "Food Item : "+FoodName+" from following Restaurant : "+R_name+" is added to cart");
		
	}

}
