package org.com.friday.carlookup.helper;

import org.com.friday.carlookup.utils.WebDriverWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

public class CarInsuranceHelper {
	
	private int timeOutPeriod = 30;
	private WebDriver driver;
	private WebDriverWrapper wrapper;
	
	@FindBy(id = "uc-btn-accept-banner")
	WebElement txtPickup;
	
	@FindBy(xpath = "//button[@value='keepingCar']")
	WebElement rdbtnKeepCar;
	
	@FindBy(xpath = "//button[@value='buyingCar']")
	WebElement rdbtnBuyCar;
	
	@FindBy(name = "inceptionDate")
	WebElement txtDate;
	
	@FindBy(xpath = "//input[@name='inceptionDate']/../following-sibling::div/span")
	WebElement lblErrorDateMsg;
	
	
	@FindBy(xpath = "//button[@type='submit']")
	WebElement btnSubmit;
	
	@FindBy(xpath = "//span[contains(text(),'Weiter')]")
	WebElement btnSubmit2;
	
	
	@FindBy(xpath = "(//*[@name='registeredOwner'])[1]")
	WebElement btnIsRegsitered;
	
	@FindBy(xpath = "(//*[@name='registeredOwner'])[2]")
	WebElement btnIsNotRegsitered;
	
	@FindBy(xpath = "//*[@name='newOrUsed' and @value='used']")
	WebElement btnUsed;
	
	@FindBy(xpath = "//*[@name='newOrUsed' and @value='brandNew']")
	WebElement btnNew;
	
	//to identify page
	@FindBy(xpath = "//button[@name='list']")
	WebElement lnkVehicle;
	
	@FindBy(name = "makeFilter")
	WebElement txtEnterMake;
	
	@FindBy(xpath = "(//button[@name='make'])[1]")
	WebElement selectMake;
	
	@FindBy(name = "modelFilter")
	WebElement txtEnterModel;
	
	@FindBy(xpath = "(//button[@name='model'])[1]")
	WebElement selectModel;
	
	@FindBy(name = "bodyType")
	WebElement lstBodyType;
	
	@FindBy(name = "fuelType")
	WebElement lstFuelType;
	
	@FindBy(name = "enginePower")
	WebElement lstEnginePower;
	
	@FindBy(name = "engine")
	WebElement lstEngine;
	
	@FindBy(name = "monthYearFirstRegistered")
	WebElement txtRegDate;
	
	@FindBy(name = "monthYearOwnerRegistered")
	WebElement txtOwnerDate;
		
	@FindBy(name = "birthDate")
	WebElement txtDOB;
	
	@FindBy(xpath = "//div[@data-test-id='wizardTitle']")
	WebElement lblDOB;
	
	@FindBy(name = "hsnTsn")
	WebElement btnHsn;
	
	@FindBy(name = "monthYear")
	WebElement txthsnDate;
	
	@FindBy(name = "hsn")
	WebElement txtHsn;
	
	@FindBy(name = "tsn")
	WebElement txtTsn;
	
	@FindBy(xpath = "//span[contains(text(),'Wir konnten kein passendes Fahrzeug ermitteln')]")
	WebElement lblHsnErrorMsg;
	
	@FindBy(xpath = "//span[contains(text(),'18 Jahre sind')]")
	WebElement lblDOBErrorMsg;
	
	/**
	 * Helper class constructor takes driver object to set this.driver
	 * instantiates wrapper object which has utility methods
	 * and initializes page objects using initElements static method
	 * @param driver
	 */
	public CarInsuranceHelper(WebDriver driver) {
		this.driver = driver;
		wrapper = new WebDriverWrapper();
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * This method identifies if cookie banner is visible
	 * during initial load of website and clicks on accept cookies.
	 * @return null 
	 */
	public void clickOnLandingbanner() {
		Reporter.log("Inside clickOnLandingbanner method");
		wrapper.waitAndClick(driver, txtPickup, timeOutPeriod);
	}
	
	/**
	 * This method asks if car is already insured or re-registerd
	 * and selects the option accordingly
	 * @param isInsured
	 * @return null
	 */
	public void selectPreCondition(String isInsured) {
		Reporter.log("Inside selectPreCondition method");
		if("Y".equalsIgnoreCase(isInsured)) {
			wrapper.waitAndClick(driver, rdbtnKeepCar, timeOutPeriod);
		}else {
			wrapper.waitAndClick(driver, rdbtnBuyCar, timeOutPeriod);
		}
		
		String currentDate = wrapper.getCurrentDate();
		wrapper.waitAndSendKeys(driver, txtDate, timeOutPeriod, currentDate);
		clickOnSubmit(btnIsRegsitered);
	}
	
	
	/**
	 * This method checks if customer is owner and car status
	 * and selects the options based on parameters
	 * @param isOwner
	 * @param isNew
	 * @return null
	 */
	public void selectRegisteredOwner(String isOwner,String isNew) {
		Reporter.log("Inside selectRegisteredOwner method");
		
		if("Y".equalsIgnoreCase(isOwner)) {
			wrapper.waitAndClick(driver, btnIsRegsitered, timeOutPeriod);
		}else if("N".equalsIgnoreCase(isOwner)){
			wrapper.waitAndClick(driver, btnIsNotRegsitered, timeOutPeriod);
		}else {
			Assert.fail("Owner details not found. Valid values are 'Y' or 'N'");
		}
		
		if("Y".equalsIgnoreCase(isNew)) {
			wrapper.waitAndClick(driver, btnUsed, timeOutPeriod);
		}else if("N".equalsIgnoreCase(isNew)){
			wrapper.waitAndClick(driver, btnNew, timeOutPeriod);
		}else {
			Assert.fail("Car details not found. Valid values are 'Y' or 'N'");
		}
		clickOnSubmit(lnkVehicle);
	}
	
	/**
	 * This method reads all the parameters passed from 'inputDataSheet.xlsx'
	 * using dataprovider annotation, selects details accordingly
	 * and returns label text on 'DOB' entry page.(Wann wurdest du geboren?)
	 * Presence of which marks end of test.
	 * @param make
	 * @param model
	 * @param bodyType
	 * @param fuelType
	 * @param enginePower
	 * @param hsn
	 * @param regDate
	 * @return DOB_label_text
	 */
	public String selectVehicle(String make,String model,String bodyType,String fuelType,String enginePower,String hsn,String regDate) {
		Reporter.log("Inside selectVehicle method");
		wrapper.waitAndSendKeys(driver, txtEnterMake, timeOutPeriod, make);
		wrapper.waitForElementTobeDisplayed(driver, selectMake, timeOutPeriod);
		if(selectMake.getText().trim().equalsIgnoreCase(make)) {
			wrapper.waitAndClick(driver, selectMake, timeOutPeriod);
		}else {
			Assert.fail(make+" not found int Make List");
		}
		
		wrapper.waitForElementTobeDisplayed(driver, txtEnterModel, timeOutPeriod);
		txtEnterModel.sendKeys(model);
		
		driver.findElement(By.xpath("//button[@name='model']//label[text()='"+model+"']")).click();
		
		if(!("NA".equalsIgnoreCase(bodyType))) {
			wrapper.waitForElementTobeDisplayed(driver, lstBodyType, timeOutPeriod);
			driver.findElement(By.xpath("//*[@name='bodyType']//label[text()='"+bodyType+"']")).click();
		}
		
		if(!("NA".equalsIgnoreCase(fuelType))) {
			wrapper.waitForElementTobeDisplayed(driver, lstFuelType, timeOutPeriod);
			driver.findElement(By.xpath("//*[@name='fuelType']//label[text()='"+fuelType+"']")).click();
		}
		
		if(!("NA".equalsIgnoreCase(enginePower))) {
			wrapper.waitForElementTobeDisplayed(driver, lstEnginePower, timeOutPeriod);
			driver.findElement(By.xpath("//*[@name='enginePower']//label[text()='"+enginePower+"']")).click();
		}
		wrapper.waitForElementTobeDisplayed(driver, lstEngine, timeOutPeriod);
		driver.findElement(By.xpath("//button[@name='engine']//p[text()='"+hsn+"']")).click();
		
		wrapper.waitForElementTobeDisplayed(driver, txtRegDate, timeOutPeriod);
		wrapper.waitAndSendKeys(driver, txtRegDate, timeOutPeriod, regDate);
		
		if(driver.findElements(By.name("monthYearOwnerRegistered")).size()> 0) {
			wrapper.waitAndSendKeys(driver, txtOwnerDate, timeOutPeriod, regDate);
		}
		
		clickOnSubmit(txtDOB);
		
		return lblDOB.getText().trim();
	}
	
	/**
	 * Utility method to click on 'Further' or 'Weiter'
	 * on every page, wherever required.
	 * Also takes WebElement of next page as input and checks for the visibility before moving ahead.
	 * @param WebElement
	 */
	private void clickOnSubmit(WebElement element) {
		Reporter.log("Inside clickOnSubmit method");
		wrapper.waitAndClick(driver, btnSubmit2, timeOutPeriod);
		wrapper.waitForElementTobeDisplayed(driver, element, timeOutPeriod);
	}
	
	/**
	 * Used to enter registration date for error scenarios
	 * and gets error message for given input.
	 * @param date
	 * @return errorMsg
	 */
	public String enterRegistrationDate(String date) {
		Reporter.log("Inside enterRegistrationDate method");
		txtDate.clear();
		txtDate.sendKeys(date);
		return lblErrorDateMsg.getText().trim();
	}
	
	/**
	 * This method is used to enter invalid HSN/TSN values
	 * for negative scenario and fetches the error message.
	 * @param date
	 * @param hsn
	 * @param tsn
	 * @return errorMsg
	 */
	public String enterHSNTSN(String date,String hsn,String tsn) {
		Reporter.log("Inside enterHSNTSN method");
		wrapper.waitAndClick(driver, btnHsn, timeOutPeriod);
		wrapper.waitForElementTobeDisplayed(driver, txtHsn, timeOutPeriod);
		wrapper.waitAndSendKeys(driver, txthsnDate, timeOutPeriod, date);
		wrapper.waitAndSendKeys(driver, txtHsn, timeOutPeriod, hsn);
		wrapper.waitAndSendKeys(driver, txtTsn, timeOutPeriod, tsn);
		btnSubmit2.click();
		wrapper.waitForElementTobeDisplayed(driver, lblHsnErrorMsg, timeOutPeriod);
		return lblHsnErrorMsg.getText().trim();
	}
	
	/**
	 * This method is used to fetch error message
	 * by passing incorrect DOB(under 18) and validates the same.
	 * @param date
	 * @return errorMsg
	 */
	public String validateAgeCriteria(String date) {
		Reporter.log("Inside validateAgeCriteria method");
		wrapper.waitAndSendKeys(driver, txtDOB, timeOutPeriod, date);
		btnSubmit2.click();
		wrapper.waitForElementTobeDisplayed(driver, lblDOBErrorMsg, timeOutPeriod);
		
		return lblDOBErrorMsg.getText().trim();
	}
}
