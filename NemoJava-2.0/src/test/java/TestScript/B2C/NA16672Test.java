package TestScript.B2C;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.DesignHandler.CTOandPB;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.CRMPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA16672Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public CRMPage crmPage;
	double doubleWebPrice_after;
	double webPrice_builderPage_addedOption;
	Actions actions;
	ArrayList<String> al_cartConfig;
	public String product_partNumber;
	public String b2cProductUrl;
	public String ProductID;

	String itemName;

	public NA16672Test(String store) {
		this.Store = store;
		this.testName = "NA-16672";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup","process", "p2", "b2c" })
	public void NA16672(ITestContext ctx) {
		try {
			this.prepareTest();

			driver.get(testData.B2C.getHomePageUrl());
			B2CPage page = new B2CPage(driver);
			B2CCommon.handleGateKeeper(page, testData);
			driver.get(testData.B2C.getHomePageUrl() + "/p/" + testData.B2C.getDefaultCTOPN());
			Dailylog.logInfoDB(1, testData.B2C.getHomePageUrl() + "/p/" + testData.B2C.getDefaultCTOPN(), this.Store,
					this.testName);

			// get PDP prices
			List<WebElement> pricesElements = driver
					.findElements(By.xpath(".//*[@id='builderPricingSummary']/dd[not(contains(@style,'block'))]"));
			DecimalFormat DF = new DecimalFormat();
			int numOfPrice = pricesElements.size();
			float[] pricesPDP = new float[numOfPrice];
			float[] pricesPB = new float[3];
			for (int i = 0; i < pricesElements.size(); i++) {
				pricesPDP[i] = DF.parse(pricesElements.get(i).getText().substring(1)).floatValue();
			}

//			//check promotions if any
//			List<WebElement> promotions = driver.findElements(By.xpath("//div[@class='promotedOptions']//input"));
//			String[] proValues = new String[promotions.size()];
//			for (int p = 0; p < promotions.size();p++)
//			{
//				Common.javascriptClick(driver, promotions.get(p));
//				proValues[p] = promotions.get(p).getAttribute("value");
//			}
			
			B2CCommon.clickAddtocartOrCustomizeOnPDP(driver);
			Thread.sleep(3000);
			// Get group list
			String groupsXpath = "//div[contains(@id,'list_group_') and contains(@class,'true')]";
			List<WebElement> groups = driver.findElements(By.xpath(groupsXpath));

			if (Common.isElementExist(driver, By.xpath("//div[contains(@class,'group-item') and contains(@class,'visible') and @iterate='components']"), 2)) {
				// New CTO
				// Expand all group list
				page.newCTO_ExpandAll.click();
				Thread.sleep(3000);
				
				float pricesBefore[], pricesAfter[], pricesCTO[], offsetPrice[];
				
				List<WebElement> items;
				for(int g = 0;g<groups.size();g++)
				{
				items = groups.get(g).findElements(By.xpath(
						".//div[contains(@class,'group-item') and @iterate='components' and not(contains(@class,'hidden'))]"));
				for (int x = 0; x < items.size(); x++) {
					if (!items.get(x).findElement(By.xpath(".//i[contains(@class,'fa-check-circle')]")).isDisplayed()) {
						// if not selected
						// get current prices
						pricesBefore = CTOandPB.getPriceArrayFromCTOandPBNew(page);
						offsetPrice = CTOandPB.getOffsetPriceCTONew(items.get(x));
						Common.javascriptClick(driver,
								items.get(x).findElement(By.xpath(".//div[contains(@class,'price')]")));
						Thread.sleep(3000);
						// get after prices
						pricesAfter = CTOandPB.getPriceArrayFromCTOandPBNew(page);

						// validate prices
						validatePriceChangeNew(items.get(x), offsetPrice, pricesBefore, pricesAfter);
						
						//uncheck the checkbox
						Common.javascriptClick(driver,
								items.get(x).findElement(By.xpath(".//div[contains(@class,'price')]")));
					}
				}
				}
				
				//validate price
				pricesCTO = CTOandPB.getPriceArrayFromCTOandPBNew(page);
				 driver.findElement(By.xpath("//button[contains(@class,'add-to-cart')]")).click();
				 pricesPB = CTOandPB.getPriceArrayFromCTOandPBNew(page);
				 
				 validatePricesSameNew(pricesCTO,pricesPB);

				 //Expand warranty
				 page.newCTO_ExpandWarranty.click();
				 Thread.sleep(3000);
				 
				 List<WebElement> warrantyLines = driver.findElements(By.xpath(".//ul[contains(@class,'configuratorItem-optionList')]/li"));
				 for(int x = 0; x<warrantyLines.size();x++)
				 {
					 if (!warrantyLines.get(x).findElement(By.xpath(".//i[contains(@class,'fa-check-circle')]")).isDisplayed()) {
						 pricesBefore = CTOandPB.getPriceArrayFromCTOandPBNew(page);
							offsetPrice = CTOandPB.getOffsetPricePBNew(driver, warrantyLines.get(x));
							Common.javascriptClick(driver,
									warrantyLines.get(x).findElement(By.xpath(".//label/div")));
							Thread.sleep(2000);
							// get after prices
							pricesAfter = CTOandPB.getPriceArrayFromCTOandPBNew(page);

							// validate prices
							validatePriceChangeNew(warrantyLines.get(x), offsetPrice, pricesBefore, pricesAfter);
							
							//uncheck the checkbox
							Common.javascriptClick(driver,
									warrantyLines.get(x).findElement(By.xpath(".//label/div")));
					 }
				 }
				 pricesPB = CTOandPB.getPriceArrayFromCTOandPBNew(page);
				 page.cto_AddToCartButton.click();
			} else {
				// Old CTO
				float[] pricesCTO = CTOandPB.getPriceArrayFromCTOOld(page);
				// Compare prices
				validatePricesSameOld(pricesPDP, pricesCTO);
				
				// Expand all group list
				if (Common.isElementExist(driver, By.xpath(".//h4[@class='collapsed']"), 2)) {
					List<WebElement> collapsedGroups = driver.findElements(By.xpath(".//h4[@class='collapsed']"));
					// Expand
					for (int x = 0; x < collapsedGroups.size(); x++) {
						collapsedGroups.get(x).click();
					}
				}

				float priceOffset[], priceCurrent[], priceAfter[];

				// Change CTO values and check price
				List<WebElement> items;
				for (int x = 0; x < groups.size(); x++) {
					items = groups.get(x).findElements(By.xpath(".//tr[not(contains(@class,'hidden'))]"));
					if (items.get(0).getAttribute("class").contains("radio-item")) {
						// Radio button group
						for (int y = 0; y < items.size(); y++) {
							if (!items.get(y).findElement(By.xpath(".//input[@type='radio']")).isSelected()) {
								// get Offset and Current prices
								priceOffset = CTOandPB.getOffsetPriceCTOOld(driver, items.get(y));
								priceCurrent = CTOandPB.getPriceArrayFromCTOOld(page);
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
								priceAfter = CTOandPB.getPriceArrayFromCTOOld(page);

								// validate price
								validatePriceChangeNew(items.get(y), priceOffset, priceAfter, priceCurrent);
							}
						}
					} else {
						// Checkbox group
						for (int y = 0; y < items.size(); y++) {
							if (!items.get(y).findElement(By.xpath(".//input[@type='checkbox']")).isSelected()) {
								// click checkbox only if it is not selected yet
								priceOffset = CTOandPB.getOffsetPriceCTOOld(driver, items.get(y));
								priceCurrent = CTOandPB.getPriceArrayFromCTOOld(page);
								Common.javascriptClick(driver,
										items.get(y).findElement(By.xpath(".//input[@type='checkbox']")));
								Thread.sleep(1000);
								// check group title is changed
								Common.waitElementText(driver, By.xpath(groupsXpath + "[" + (x + 1) + "]/h4/span[3]"),
										items.get(y).findElement(By.xpath(".//span[@class='label-text']")).getText());
								Thread.sleep(1000);
								// get after prices
								priceAfter = CTOandPB.getPriceArrayFromCTOOld(page);

								// validate price
								validatePriceChangeNew(items.get(y), priceOffset, priceAfter, priceCurrent);

								// uncheck the checkbox
								Common.javascriptClick(driver,
										items.get(y).findElement(By.xpath(".//input[@type='checkbox']")));
							}
						}
					}
				}
				pricesCTO = CTOandPB.getPriceArrayFromCTOOld(page);

				// change PB values and check price
				page.cto_AddToCartButton.click();
				List<WebElement> pbTabHeads = driver.findElements(By.xpath(".//li[contains(@class,'stepsItem-item')]"));
				List<WebElement> pbTabContainers = driver
						.findElements(By.xpath(".//div[@class='tabContent-container']"));
				List<WebElement> lineItems;

				// get PB prices
				pricesPB = CTOandPB.getPriceArrayFromPBOld(page);
				// Compare prices
				validatePricesSameOld(pricesCTO, pricesPB);

				for (int x = 1; x < pbTabHeads.size() - 1; x++) {
					pbTabHeads.get(x).click();

					// get element list
						lineItems = pbTabContainers.get(x)
								.findElements(By.xpath(".//div[contains(@class,'configuratorItem') and contains(@class,'group-frame') and not(contains(@class,'hide'))]//li[contains(@class,'configuratorItem-optionList-option')]"));
						for (int y = 0; y < lineItems.size(); y++) {
							if (!lineItems.get(y).findElement(By.xpath(".//input")).isSelected()) {
								// get prices
								priceOffset = CTOandPB.getOffsetPricePBOld(driver, lineItems.get(y));
								priceCurrent = CTOandPB.getPriceArrayFromPBOld(page);
								Common.javascriptClick(driver,
										lineItems.get(y).findElement(By.xpath(".//input")));
								Thread.sleep(1000);
								// get after prices
								priceAfter = CTOandPB.getPriceArrayFromPBOld(page);

								// validate prices
								validatePriceChangeNew(lineItems.get(y), priceOffset, priceAfter, priceCurrent);
								
								Common.javascriptClick(driver,
										lineItems.get(y).findElement(By.xpath(".//input")));
							}
						}
				}
				// get PB prices
				pricesPB = CTOandPB.getPriceArrayFromPBOld(page);

					driver.findElement(By.xpath("//button[@data-final = 'true']")).click();
			}

			float cartPrice = DF.parse(page.cartInfo_subTotalAftAnnProd.getText().substring(1)).floatValue();
			if (cartPrice != pricesPB[1] && cartPrice != pricesPB[0]) {
				// cart price should equal after instant saving price
				// or if it is ecoupon, then cart price should equal web price
				Assert.fail("Sub total price in cart page is different from PB page, cart price: " + cartPrice
						+ ", PB price: " + pricesPB[1]);
			}
			
//			//check if all selected promotions verified pass
//			for(int p = 0;p<proValues.length;p++)
//			{
//				if (!proValues[p].equals("VerifiedPass!"))
//					Assert.fail(proValues[p] + " is not auto selected on CTO/PB page");
//			}
			
			B2CCommon.placeOrderFromClickingStartCheckoutButtonInCart(driver, page, testData);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void validatePricesSameOld(float[] previousPrices, float[] afterPrices) {
		for (int x = 0; x < previousPrices.length; x++) {
			if (previousPrices[x] != afterPrices[x] && afterPrices[x] != 0) {
				Assert.fail("Prices after switch page is wrong! Before: " + previousPrices[x] + ", After: "
						+ afterPrices[x]);
			}
		}
	}

	private void validatePriceChangeNew(WebElement lineItem, float[] priceOffset, float[] priceAfter,
			float[] priceCurrent) {		if (priceOffset[0] != Math.abs(Common.floatSubtract(priceAfter[0], priceCurrent[0])) && priceCurrent[0] != priceAfter[0] && priceAfter[0] != 0) {
			if (priceOffset[1] != Math.abs(Common.floatSubtract(priceAfter[0], priceCurrent[0])) || Math.abs(Common
					.floatSubtract(priceOffset[1], Common.floatSubtract(priceAfter[2], priceCurrent[2]))) != Math.abs(Common
							.floatSubtract(priceAfter[1], priceCurrent[1])))
				this.setManualValidateLog(lineItem.getText() + "price is incorrect!");
		} else {
			if (priceOffset[1] != Math.abs(Common.floatSubtract(priceAfter[1], priceCurrent[1]))
					&& priceAfter[1] != 0) {
				if (priceOffset[1] != Math.abs(Common.floatSubtract(priceAfter[1], priceCurrent[1])) + Math.abs(Common.floatSubtract(priceAfter[2], priceCurrent[2])))
					this.setManualValidateLog(lineItem.getText() + "price is incorrect!");
			}
			if (priceOffset[2] != Math.abs(Common.floatSubtract(priceAfter[2], priceCurrent[2]))
					&& priceAfter[2] != 0) {
				if (priceOffset[1] != Math.abs(Common.floatSubtract(priceAfter[1], priceCurrent[1])) + Math.abs(Common.floatSubtract(priceAfter[2], priceCurrent[2])))
					this.setManualValidateLog(lineItem.getText() + "price is incorrect!");
			}
		}
	}
	
	private void validatePricesSameNew(float[] previousPrices, float[] afterPrices) {
		for (int x = 0; x < previousPrices.length; x++) {
			if (previousPrices[x] != afterPrices[x] && afterPrices[x] != 0) {
				Assert.fail("Prices after switch page is wrong! Before: " + previousPrices[x] + ", After: "
						+ afterPrices[x]);
			}
		}
	}
	
//	private void checkPromotions(String[] values)
//	{
//		for(int p = 0;p<values.length;p++)
//		{
//			if(!values[p].equals("VerifiedPass!") && Common.isElementExist(driver, By.xpath("//*[@id='"+values[p]+"' or @value='"+values[p]+"']"),1))
//			{
//				if(driver.findElement(By.xpath("//*[@id='"+values[p]+"' or @value='"+values[p]+"']")).isSelected())
//					values[p] = "VerifiedPass!";
//			}
//		}
//	}
}
