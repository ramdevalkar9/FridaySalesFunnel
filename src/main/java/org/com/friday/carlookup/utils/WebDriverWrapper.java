package org.com.friday.carlookup.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverWrapper {
	
	/**
	 * Utility method to check if the element is visible and then click on the same.
	 * Attempts twice (in catch) before throwing exception
	 * @param driver
	 * @param element
	 * @param timeOutPeriod
	 */
	public void waitAndClick(WebDriver driver,WebElement element,int timeOutPeriod) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutPeriod);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		}
	    catch (StaleElementReferenceException sere) {
	        // simply retry finding the element in the refreshed DOM
	    	wait.until(ExpectedConditions.visibilityOf(element));
	    	wait.until(ExpectedConditions.elementToBeClickable(element));
	    	element.click();
	    }
	    catch (Exception toe) {
	        Assert.fail("Element identified by " + element.toString() + " was not displayed after 30 seconds");
	    }
	}
	
	/**
	 * Utility method to check if the element is displayed or not on page.
	 * Attempts twice (in catch) before throwing exception
	 * @param driver
	 * @param element
	 * @param timeOutPeriod
	 */
	
	public void waitForElementTobeDisplayed(WebDriver driver,WebElement element,int timeOutPeriod) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutPeriod);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		}
	    catch (TimeoutException sere) {
	    	wait.until(ExpectedConditions.visibilityOf(element));
	    }
	    catch (Exception toe) {
	        Assert.fail("Element identified by " + element.toString() + " was not displayed after 30 seconds");
	    }
	}
	
	/**
	 * Utility method to check if the element is visible and then clear and enter value.
	 * Attempts twice (in catch) before throwing exception
	 * @param driver
	 * @param element
	 * @param timeOutPeriod
	 */
	public void waitAndSendKeys(WebDriver driver,WebElement element,int timeOutPeriod,String text) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutPeriod);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(text);
		}
	    catch (TimeoutException sere) {
	    	wait.until(ExpectedConditions.visibilityOf(element));
	    	element.clear();
	    	element.sendKeys(text);
	    }
	    catch (Exception toe) {
	        Assert.fail("Element identified by " + element.toString() + " was not displayed after 30 seconds");
	    }
	}
	
	/**
	 * Returns current date.
	 * @return currentdate
	 */
	public String getCurrentDate() {
		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("ddMMyyyy").format(date);
		return modifiedDate;
	}
	
}
