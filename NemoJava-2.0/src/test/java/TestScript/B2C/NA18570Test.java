package TestScript.B2C;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA18570Test extends SuperTestClass {

	public B2CPage b2cPage;
	private String number;
	private String text = "Lenovo systems do not support batteries that are not genuine Lenovo-made or authorised. Use of "
			+ "such batteries will enable systems to continue to boot, but may not charge or work effectively. Lenovo has "
			+ "no responsibility for the performance or safety of unauthorised batteries, and provides no warranties or "
			+ "liability for failures or damage arising out of their use. Battery"
			+ " life (and recharge times) will vary based on many factors, including system settings and usage. wwww";

	public NA18570Test(String store,String number) {
		this.Store = store;
		this.testName = "NA-18570";
		this.number = number;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA18570(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			String defaultMTMPN = testData.B2C.getDefaultMTMPN();
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + number);

			// Step~1 in product description
			if(Store.toLowerCase().equals("usepp")) {
				b2cPage.SerialNumberGateKeeper_SerialNumberTextBox.clear();
				b2cPage.SerialNumberGateKeeper_SerialNumberTextBox.sendKeys("STAR");
				b2cPage.SerialNumberGateKeeper_LastNameTextBox.clear();
				b2cPage.SerialNumberGateKeeper_LastNameTextBox.sendKeys("SuperStar");
				b2cPage.Product_USEPPtton.click();
			}
			Common.sleep(3000);
			setRichTextBox(text, b2cPage.Product_description);
			if(Common.checkElementDisplays(driver, b2cPage.Product_price, 5)) {
				int pricey = b2cPage.Product_price.getLocation().getY();
				int descriptiony = b2cPage.Product_description.getLocation().getY();
				Assert.assertTrue(pricey>descriptiony);
			}
			Dailylog.logInfoDB(1, "in product description", Store, testName);

			// Step~2 Go to features and add more texts
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.Product_feature, 5)) {
				Common.scrollToElement(driver, b2cPage.Product_feature);
				int addTextBefore = b2cPage.Product_img.getLocation().getX();
				setRichTextBox(text, b2cPage.Product_feature);
				int addTextAfter = b2cPage.Product_img.getLocation().getX();
				Assert.assertTrue(addTextBefore==addTextAfter);
				Dailylog.logInfoDB(2, "Go to features and add more texts", Store, testName);

			}
			
			// Step~3 In review sector, Add more text
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.Product_reviewdesc, 5)) {
				Common.scrollToElement(driver, b2cPage.Product_reviewdesc);
				int descpritionBefore = b2cPage.Product_reviewdesc.getLocation().getY();
				setRichTextBox(text, b2cPage.Product_reviewdesc);
				int descpritionAfter = b2cPage.Product_reviewdesc.getLocation().getY();
				Assert.assertTrue(descpritionBefore==descpritionAfter);
				Dailylog.logInfoDB(3, "In review sector, Add more text", Store, testName);
			}
			

			// Step~4 Scroll to the area above Models
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.Product_tech, 5)) {
				Common.scrollToElement(driver, b2cPage.Product_tech);
				int techBefore = b2cPage.Product_tech.getLocation().getY();
				setRichTextBox(text, b2cPage.Product_tech);
				int techAfter = b2cPage.Product_tech.getLocation().getY();
				Assert.assertTrue(techBefore==techAfter);
				Dailylog.logInfoDB(4, "Scroll to the area above Models", Store, testName);
			}
			// Step~5 6 Open up all the option drop-lists and verify there's no overlaying 
			Common.sleep(2000);
			if(Common.checkElementDisplays(driver, b2cPage.Product_checktext, 5)) {
				Common.scrollToElement(driver, b2cPage.Product_checktext);
				b2cPage.Product_customizeBtn.click();
				Common.sleep(3000);
				if(Common.checkElementDisplays(driver, b2cPage.Product_customize_add, 5)) {
					b2cPage.Product_customize_plus.click();
				}
				b2cPage.Product_customize_plus.click();
				Dailylog.logInfoDB(5, "Open up all the option drop-lists and verify there's no overlaying ", Store, testName);
			}

			// Step~7 Scroll to bottom and make sure what presents in attachment 8 does not happen
			Common.sleep(3000);
			if(Common.checkElementDisplays(driver, b2cPage.Product_customize_bottom, 5)) {
				Common.scrollToElement(driver, b2cPage.Product_customize_bottom);
				Dailylog.logInfoDB(7, "Scroll to bottom and make sure what presents in attachment 8 does not happen", Store, testName);
			}
			
			// Step~8 Resize window and verify there's no UI elements overlaying and pictures shrink accordingly for example : width=759, width=757
			Common.sleep(3000);
			if(Common.checkElementDisplays(driver, b2cPage.Product_customize_plus, 5)) {
				Common.scrollToElement(driver, b2cPage.Product_customize_plus);
				driver.manage().window().setSize(new Dimension(759, 757));
				Common.sleep(3000);
				((JavascriptExecutor)driver).executeScript("(window.open(''))");
				Common.switchToWindow(driver, 1);
				driver.get(testData.B2C.getHomePageUrl()+"laptops/thinkpad/thinkpad-x/c/ThinkPadX");
				driver.manage().window().setSize(new Dimension(759, 757));
				Dailylog.logInfoDB(8, "Resize window ", Store, testName);
			}
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	protected void setRichTextBox(String text, WebElement webElement) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].innerHTML = \"" + text + "\"", webElement);
	}

}
