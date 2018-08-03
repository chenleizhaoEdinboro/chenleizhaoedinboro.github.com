package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA18237Test extends SuperTestClass {
	public B2CPage b2cPage;
	private String laptops ="/laptops/c/LAPTOPS";
	
	public NA18237Test(String store) {
		this.Store = store;
		this.testName = "NA-18237";
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA18237(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);

			// Step~1 : open the website
			driver.get(testData.B2C.getHomePageUrl());
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.sleep(3000);
			Dailylog.logInfoDB(1, "load b2c: ", Store, testName);

			// step~2 : click "PRODUCTS" AND then click "LAPTOPS & ULTRABOOKS"
			if(Common.checkElementExists(driver, b2cPage.Navigation_ProductsLink, 5)) {
				b2cPage.Navigation_ProductsLink.click();
				Common.mouseHover(driver, b2cPage.Navigation_ProductsLink);
				if(Common.checkElementExists(driver,  b2cPage.Navigation_Laptop, 5)) {
					Common.javascriptClick(driver, b2cPage.Navigation_Laptop);
					Common.sleep(3000);
					driver.get(testData.B2C.getHomePageUrl()+laptops);
					if(Store.toLowerCase().equals("hk")) {
						driver.get(testData.B2C.getHomePageUrl()+"/laptops/thinkpad/c/thinkpad?menu-id=ThinkPad_Laptops");
					}
					Common.sleep(3000);
					Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("laptops"));
				}else {
					Assert.fail("has no LAPTOPS & ULTRABOOKS");
				}
				Dailylog.logInfoDB(2, "click PRODUCTS  ", Store, testName);
			}else {
				Assert.fail("has no PRODUCTS");
			}
			
			// Step~3 : click "THINKPAD" AND then click "THINKPAD 13 SERIES"
			if(Store.toLowerCase().equals("au")  || Store.toLowerCase().equals("hk") || Store.toLowerCase().equals("br")) {
				if(Common.checkElementExists(driver, b2cPage.Product_thinkpad_au, 5)) {
					b2cPage.Product_thinkpad_au.click();
					Common.sleep(3000);
					if(Common.checkElementExists(driver, b2cPage.View_think13, 5)) {
						Common.javascriptClick(driver, b2cPage.View_think13);
						Common.sleep(3000);
						if(Common.checkElementExists(driver, b2cPage.Servers_Learn, 5)) {
							b2cPage.Servers_Learn.click();
							Dailylog.logInfoDB(3, "click THINKPAD  THINKPAD 13 SERIES", Store, testName);
							Dailylog.logInfoDB(4, "click Learn more button", Store, testName);
							
							setTep5();
						}
					}else {
						Dailylog.logInfoDB(4, "has no 13", Store, testName);
					}
				}
			}else if(Store.toLowerCase().equals("us") || Store.toLowerCase().equals("jp")) {
				if (Common.checkElementExists(driver, b2cPage.Product_thinkpad_us1, 5)) {
					Common.javascriptClick(driver, b2cPage.Product_thinkpad_us1);
					if(Common.checkElementExists(driver, b2cPage.View_think13_us, 5)) {
						List<WebElement> findElements = driver.findElements(By.xpath("//a[contains(@href,'thinkpad-13-series')]"));
						Common.javascriptClick(driver, findElements.get(findElements.size()-1));
						Common.sleep(3000);
						if(Common.checkElementExists(driver, b2cPage.Servers_Learn, 5)) {
							b2cPage.Servers_Learn.click();
							Dailylog.logInfoDB(3, "click THINKPAD  THINKPAD 13 SERIES", Store, testName);
							Dailylog.logInfoDB(4, "click Learn more button", Store, testName);
							setTep5();
						}
					}
				}
			}
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void setTep5() throws InterruptedException {
		// Step~5 : check the "compare" checkboxes under product
					Common.sleep(3000);
					if(Common.checkElementDisplays(driver, b2cPage.Compare, 5)) {
						List<WebElement> checkbox = driver.findElements(By.xpath("//div[@class='comparecheckbox']/label[@class='com_unselected']"));
						for (int i = 0; i < checkbox.size(); i++) {
							if (!checkbox.get(i).isSelected()) {
								checkbox.get(i).click();
								Common.sleep(3000);
								Common.scrollToElement(driver, b2cPage.Remove);
								b2cPage.Remove_icon.click();
								Common.sleep(3000);
							}
						}
					}
					Dailylog.logInfoDB(5, "check the compare checkboxes ", Store, testName);
					 
					// step~6 click "x" icon at the left side of the checked products in the window
					if(Common.checkElementDisplays(driver, b2cPage.Compare, 5)) {
						List<WebElement> checkbox = driver.findElements(By.xpath("//div[@class='comparecheckbox']/label[@class='com_unselected']"));
						if(checkbox.size()>1) {
							for (int i = 0; i < checkbox.size(); i++) {
								checkbox.get(i).click();
							}
							checkbox.get(0).click();
							Common.sleep(3000);
							if(Common.checkElementDisplays(driver, b2cPage.Remove, 5) && Common.checkElementDisplays(driver, b2cPage.Compare_items, 5)) {
								b2cPage.Compare_items.click();
							}
						}
						
					}
					Dailylog.logInfoDB(6, "7,8 click x ", Store, testName);
					
					// step~9 at the right corner of the comparison page , "the windows can shink"
					Common.sleep(4000);
					if(Common.checkElementDisplays(driver, b2cPage.CompareBtn, 5)) {
						Common.scrollToElement(driver, b2cPage.CompareBtn);
						b2cPage.CompareBtn.click();
						Assert.assertTrue(Common.checkElementDisplays(driver, b2cPage.CompareBtn, 5));
						Dailylog.logInfoDB(9, "the windows can shink", Store, testName);
						
						// step~10 click "Clear all item(s)"
						Common.sleep(4000);
						b2cPage.Clear.click();
						driver.navigate().refresh();
						Assert.assertTrue(!Common.checkElementDisplays(driver, b2cPage.Products_clear, 5));
						Dailylog.logInfoDB(10, "click Clear all item(s)", Store, testName);
					}
					

	}

}