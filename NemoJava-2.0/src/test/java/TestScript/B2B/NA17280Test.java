package TestScript.B2B;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.DesignHandler.CTOandPB;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA17280Test extends SuperTestClass {

	public NA17280Test(String Store) {
		this.Store = Store;
		this.testName = "NA-17280";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"shopgroup","Process", "p2", "b2b"})
	public void NA17280(ITestContext ctx) {
		try {
			this.prepareTest();
			B2BPage b2bPage = new B2BPage(driver);

			// Add products to cart
			driver.get(testData.B2B.getHomePageUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			Thread.sleep(2000);

			b2bPage.HomePage_productsLink.click();
			b2bPage.HomePage_Laptop.click();

			B2CPage b2cPage = new B2CPage(driver);
			float priceOffset[], priceCurrent[], priceAfter[];
			if (Common.checkElementDisplays(driver, By.xpath("//img[@alt='Customize and buy']"), 5)) {
				// CTO path
				Common.javascriptClick(driver, driver.findElement(By.xpath("//img[@alt='Customize and buy']")));
				Common.waitElementClickable(driver, b2bPage.CustomizeButtonInPopup, 10);
				b2bPage.CustomizeButtonInPopup.click();

				// Get group list, change a CTO option
				String groupsXpath = "//div[contains(@id,'list_group_') and contains(@class,'true')]";
				List<WebElement> groups = driver.findElements(By.xpath(groupsXpath));
				List<WebElement> items;
				for (int x = 0; x < 1; x++) {
					items = groups.get(x).findElements(By.xpath(".//tr[not(contains(@class,'hidden'))]"));
					if (items.get(0).getAttribute("class").contains("radio-item")) {
						// Radio button group
						for (int y = 0; y < items.size(); y++) {
							if (!items.get(y).findElement(By.xpath(".//input[@type='radio']")).isSelected()) {
								// get Offset and Current prices
								priceOffset = CTOandPB.getOffsetPriceCTOOld(driver, items.get(y));
								priceCurrent = CTOandPB.getPriceArrayFromCTOOld(b2cPage);
								// click ratio button only if it is not selected yet
								Common.javascriptClick(driver,
										items.get(y).findElement(By.xpath(".//input[@type='radio']")));
								Thread.sleep(1000);
								// check group title is changed
								Common.waitElementText(driver, By.xpath(groupsXpath + "[" + (x + 1) + "]/h4/span[3]"),
										"- " + items.get(y).findElement(By.xpath(".//span[@class='label-text']"))
												.getText());
								Thread.sleep(1000);
								// get after prices
								priceAfter = CTOandPB.getPriceArrayFromCTOOld(b2cPage);

								// validate price
								validatePriceChangeNew(items.get(y), priceOffset, priceAfter, priceCurrent);
								break;
							}
						}
					}
				}
				driver.findElement(By.id("CTO_addToCart")).click();

				if (Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@data-final='true']")), 5))
					Common.javascriptClick(driver, driver.findElement(By.xpath("//button[@data-final='true']")));
			} else {
				// MTM path
				Common.javascriptClick(driver,
						driver.findElement(By.xpath("//button[contains(text(),'Add accessories')]")));

				// Add accessory
				List<WebElement> pbTabContainers = driver
						.findElements(By.xpath(".//div[@class='tabContent-container']"));
				List<WebElement> lineItems;

				lineItems = pbTabContainers.get(1).findElements(By.xpath(
						".//div[contains(@class,'configuratorItem') and contains(@class,'group-frame') and not(contains(@class,'hide'))]//li[contains(@class,'configuratorItem-optionList-option')]"));
				for (int y = 0; y < lineItems.size(); y++) {
					if (!lineItems.get(y).findElement(By.xpath(".//input")).isSelected()) {
						// get prices
						priceOffset = CTOandPB.getOffsetPricePBOld(driver, lineItems.get(y));
						priceCurrent = CTOandPB.getPriceArrayFromPBOld(b2cPage);
						Common.javascriptClick(driver, lineItems.get(y).findElement(By.xpath(".//input")));
						Thread.sleep(1000);
						// get after prices
						priceAfter = CTOandPB.getPriceArrayFromPBOld(b2cPage);

						// validate prices
						validatePriceChangeNew(lineItems.get(y), priceOffset, priceAfter, priceCurrent);
						break;
					}
				}
				if (Common.checkElementExists(driver, driver.findElement(By.xpath("//button[@data-final='true']")), 5))
					Common.javascriptClick(driver, driver.findElement(By.xpath("//button[@data-final='true']")));
			}

			String orderNum = B2BCommon.placeOrderFromCartCheckout(driver, b2bPage, testData);
			Dailylog.logInfoDB(1, "Order Number: " + orderNum, this.Store, this.testName);
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void validatePriceChangeNew(WebElement lineItem, float[] priceOffset, float[] priceAfter,
			float[] priceCurrent) {
		if (priceOffset[0] != Math.abs(Common.floatSubtract(priceAfter[0], priceCurrent[0]))
				&& priceCurrent[0] != priceAfter[0] && priceAfter[0] != 0) {
			if (priceOffset[1] != Math.abs(Common.floatSubtract(priceAfter[0], priceCurrent[0])) || Math.abs(
					Common.floatSubtract(priceOffset[1], Common.floatSubtract(priceAfter[2], priceCurrent[2]))) != Math
							.abs(Common.floatSubtract(priceAfter[1], priceCurrent[1])))
				this.setManualValidateLog(lineItem.getText() + "price is incorrect!");
		} else {
			if (priceOffset[1] != Math.abs(Common.floatSubtract(priceAfter[1], priceCurrent[1]))
					&& priceAfter[1] != 0) {
				if (priceOffset[1] != Math.abs(Common.floatSubtract(priceAfter[1], priceCurrent[1]))
						+ Math.abs(Common.floatSubtract(priceAfter[2], priceCurrent[2])))
					this.setManualValidateLog(lineItem.getText() + "price is incorrect!");
			}
			if (priceOffset[2] != Math.abs(Common.floatSubtract(priceAfter[2], priceCurrent[2]))
					&& priceAfter[2] != 0) {
				if (priceOffset[1] != Math.abs(Common.floatSubtract(priceAfter[1], priceCurrent[1]))
						+ Math.abs(Common.floatSubtract(priceAfter[2], priceCurrent[2])))
					this.setManualValidateLog(lineItem.getText() + "price is incorrect!");
			}
		}
	}
}
