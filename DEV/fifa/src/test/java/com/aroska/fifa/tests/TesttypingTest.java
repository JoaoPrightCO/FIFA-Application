package com.aroska.fifa.tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class TesttypingTest {
//  private WebDriver driver;
//  private Map<String, Object> vars;
//  JavascriptExecutor js;
//  @Before
//  public void setUp() {
//    driver = new FirefoxDriver();
//    js = (JavascriptExecutor) driver;
//    vars = new HashMap<String, Object>();
//  }
//  @After
//  public void tearDown() {
//    driver.quit();
//  }
//  @Test
//  public void testtyping() {
//    driver.get("https://www.google.com/");
//    driver.switchTo().newWindow(WindowType.WINDOW);
//    driver.manage().window().setSize(new Dimension(550, 691));
//    driver.findElement(By.id("APjFqb")).sendKeys("test typing");
//    driver.findElement(By.cssSelector("center:nth-child(1) > .gNO89b")).click();
//    driver.findElement(By.cssSelector("#rso > .MjjYud:nth-child(1) .LC20lb")).click();
//    driver.findElement(By.cssSelector(".start-btn")).click();
//    driver.close();
//  }
  
  @Test
  public void login() {
//	System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Desktop\\projects\\Drivers\\chromedriver.exe");
//	WebDriver driver=new ChromeDriver();
	System.setProperty("webdriver.firefox.driver", "C:\\Users\\user\\Desktop\\projects\\Drivers\\geckodriver.exe");
  	WebDriver driver=new FirefoxDriver();
  	driver.manage().window().maximize();
  	driver.get("https://www.google.com");
//  	WebElement username=driver.findElement(By.id("user_email_Login"));
//  	WebElement password=driver.findElement(By.id("user_password"));
//  	WebElement login=driver.findElement(By.name("commit"));
//  	username.sendKeys("abc@gmail.com");
//  	password.sendKeys("your_password");
//  	login.click();
//  	String actualUrl="https://live.browserstack.com/dashboard";
//  	String expectedUrl= driver.getCurrentUrl();
//  	Assert.assertEquals(expectedUrl,actualUrl);
  }
}
