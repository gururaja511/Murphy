package com.incture.proj.testNg;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import com.incture.utility.browser.MultipleBrowser;
import com.incture.utility.listener.CustomListeneresImplementation;
import com.incture.utility.reports.Report;

@Listeners(CustomListeneresImplementation.class)
public class TestClass {
	
	public static WebDriver driver;
	Report report;
	@BeforeTest
	public void setUp() throws InterruptedException{
		driver = new MultipleBrowser().getBrowserDriver("firefox");
		driver.get("http://192.168.5.36:9191/StudentEnrollmentWithREST/");
	}
	
	@Test(priority =0)
	public void TestHomePageHeadLine(){
		report = new Report(driver);
		String headLine =  driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/h1")).getText();
		report.conditionUpdate(headLine.equals("Welcome to Online Student Enrollment!"), "The headline is as expected", "The headline is not as expected", "Checking the Headline", true);
	}
	
	@Test(priority = 1)
	public void TestHomePageButton(){
		report = new Report(driver);
		WebElement signUpButton = driver.findElement(By.xpath("/html/body/div[2]/div[1]/a[1]"));
		WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div[1]/a[2]"));
		report.conditionUpdate(signUpButton.isEnabled() == true, "Sign Up Button is clickable", "Sign Up Button is not clickable", "Checking for Sign Up Button", true);
		report.conditionUpdate(loginButton.isEnabled() == true, "Log In Button is clickable", "Log In Button is not clickable", "Checking for Log In Button", true);
	}
	
	@Test(priority = 2)
	public void TestRegisterPage() throws InterruptedException{
		report = new Report(driver);
		WebElement signUpButton = driver.findElement(By.xpath("/html/body/div[2]/div[1]/a[1]"));
		if(signUpButton.isEnabled()){
			signUpButton.click();
			Thread.sleep(2000);
			String headerRegPage = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/h1")).getText();
			Assert.assertEquals("Welcome to Online Student Enrollment Signup", headerRegPage);
			report.conditionUpdate(headerRegPage.equals("Welcome to Student Enrollment Signup"), "The headline is as expected", "The headline is not as expected", "Checking the headline in signup page", true);
		}
		else{
			System.out.println("Register button not enabled");
		}
	}
	
	@AfterTest
	public void CloseBrowser() throws InterruptedException{
		Thread.sleep(2000);
		driver.quit();
	}
}
