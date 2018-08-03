package TestScript.B2C;

import java.text.DecimalFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.DesignHandler.CTOandPB;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA23668Test extends SuperTestClass {

	public NA23668Test(String store) {
		this.Store = store;
		this.testName = "NA-23668";
	}

	@Test(alwaysRun= true)
	public void NA23668(ITestContext ctx) {
		try {
			this.prepareTest();

			driver.get(
					// "http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com/ca/en/laptops/thinkpad/thinkpad-x/X1-Carbon-5th-Generation/p/20HRCTO1WWENCA0/customize");
					// "http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com/jp/ja/notebooks/thinkpad/x-series/X1-Yoga-1st-Generation/p/20FRCTO1WWJAJPB/customize");

//					"http://www3.lenovo.com/ca/en/laptops/thinkpad/thinkpad-x/X1-Carbon-5th-Generation/p/20HRCTO1WWENCA0/customize");
//			 "http://www3.lenovo.com/ca/en/p/20HRCTO1WWENCA0");
			// "http://www3.lenovo.com/gb/en/laptops/thinkpad/edge-series/Thinkpad-E570/p/20H5CTO1WWENGB0/customize");
			 "http://www3.lenovo.com/gb/en/p/20H5CTO1WWENGB0");
			B2CPage page = new B2CPage(driver);

			// get PDP prices
			List<WebElement> pricesElements = driver.findElements(By.xpath(".//*[@id='builderPricingSummary']/dd[not(contains(@style,'block'))]"));
			DecimalFormat DF = new DecimalFormat();
			int numOfPrice = pricesElements.size();
			float[] pricesPDP = new float[numOfPrice];
			for(int i = 0; i< pricesElements.size();i++)
			{
				pricesPDP[i] = DF.parse(pricesElements.get(i).getText().substring(1)).floatValue();
			}
//			float[] pricesPDP = new float[] {DF.parse(pricesElements.get(0).getText().substring(1)).floatValue(),
//					DF.parse(pricesElements.get(1).getText().substring(1)).floatValue(),
//					DF.parse(pricesElements.get(2).getText().substring(1)).floatValue()};
			
			B2CCommon.clickAddtocartOrCustomizeOnPDP(driver);
			
			// Get group list
			String groupsXpath = "//div[contains(@id,'list_group_') and contains(@class,'true')]";
			List<WebElement> groups = driver.findElements(By.xpath(groupsXpath));

			if (Common.isElementExist(driver, By.xpath(".//nav[contains(@class,'stepsItem-wrapper')]"), 2)) {
				// New CTO
				// Expand all group list
				page.newCTO_ExpandAll.click();
				driver.findElement(By.xpath("//button[contains(@class,'add-to-cart')]")).click();
				float price = CTOandPB.getYourPriceFromPBNew(page);
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

				float priceOffset[];
				float priceCurrent[];
				float priceAfter[];

				// Change CTO values and check price
				List<WebElement> items;
				for (int x = 0; x < groups.size(); x++) {
					items = groups.get(x).findElements(By.xpath(".//tr[not(contains(@class,'hidden'))]"));
					// System.out.println("get available rows done" +
					// java.util.Calendar.getInstance().getTime());
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
								validatePriceChangeOld(items.get(y), priceOffset, priceAfter, priceCurrent);
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
								validatePriceChangeOld(items.get(y), priceOffset, priceAfter, priceCurrent);
								
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
				float[] pricesPB = CTOandPB.getPriceArrayFromPBOld(page);
				// Compare prices
				validatePricesSameOld(pricesCTO, pricesPB);
				
				for (int x = 1; x < pbTabHeads.size() - 1; x++) {
					pbTabHeads.get(x).click();

					// get element list
					if (Common.isElementExist(driver, pbTabContainers.get(x), By.xpath(".//input[@type='radio']"), 1)) {
						// radio button list
						lineItems = pbTabContainers.get(x)
								.findElements(By.xpath(".//li[contains(@class,'configuratorItem-optionList-option')]"));
						for (int y = 0; y < lineItems.size(); y++) {
							if (!lineItems.get(y).findElement(By.xpath(".//input[@type='radio']")).isSelected()) {
								// get prices
								priceOffset = CTOandPB.getOffsetPricePBOld(driver, lineItems.get(y));
								priceCurrent = CTOandPB.getPriceArrayFromPBOld(page);
								Common.javascriptClick(driver,
										lineItems.get(y).findElement(By.xpath(".//input[@type='radio']")));
								Thread.sleep(1000);
								// get after prices
								priceAfter = CTOandPB.getPriceArrayFromPBOld(page);

								// validate prices
								validatePriceChangeOld(lineItems.get(y), priceOffset, priceAfter, priceCurrent);
							}
						}
					} else {
						// chckbox list
						lineItems = pbTabContainers.get(x)
								.findElements(By.xpath(".//li[contains(@class,'configuratorItem-optionList-option')]"));
						for (int y = 0; y < lineItems.size(); y++) {
							if (!lineItems.get(y).findElement(By.xpath(".//input[@type='checkbox']")).isSelected()) {
								// get prices
								priceOffset = CTOandPB.getOffsetPricePBOld(driver, lineItems.get(y));
								priceCurrent = CTOandPB.getPriceArrayFromPBOld(page);
								Common.javascriptClick(driver,
										lineItems.get(y).findElement(By.xpath(".//input[@type='checkbox']")));
								Thread.sleep(1000);
								// get after prices
								priceAfter = CTOandPB.getPriceArrayFromPBOld(page);

								// validate prices
								validatePriceChangeOld(lineItems.get(y), priceOffset, priceAfter, priceCurrent);
								
								// uncheck the check box
								Common.javascriptClick(driver,
										lineItems.get(y).findElement(By.xpath(".//input[@type='checkbox']")));
							}
						}
					}
				}
			}
			// get PB prices
			float[] pricesPB = CTOandPB.getPriceArrayFromPBOld(page);
			
			CTOandPB.addToCartFromCTOOld(driver);
			
			float cartPrice = DF.parse(page.cartInfo_subTotalAftAnnProd.getText().substring(1)).floatValue();
			if (cartPrice != pricesPB[1])
			{
				Assert.fail("Sub total price in cart page is different from PB page");
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void validatePriceChangeOld(WebElement lineItem, float[] priceOffset, float[] priceAfter,
			float[] priceCurrent) {
		if (priceOffset[0] != Common.floatSubtract(priceAfter[0], priceCurrent[0])) {
			if (priceOffset[1] != Common.floatSubtract(priceAfter[0], priceCurrent[0]) || Common
					.floatSubtract(priceOffset[1], Common.floatSubtract(priceAfter[2], priceCurrent[2])) != Common
							.floatSubtract(priceAfter[1], priceCurrent[1]))
				this.setManualValidateLog(lineItem.getText() + "price is incorrect!");
		} else {
			if (priceOffset[1] != Common.floatSubtract(priceAfter[1], priceCurrent[1])) {
				if (priceOffset[1] != Common.floatSubtract(priceAfter[0], priceCurrent[0]) || Common
						.floatSubtract(priceOffset[1], Common.floatSubtract(priceAfter[2], priceCurrent[2])) != Common
								.floatSubtract(priceAfter[1], priceCurrent[1]))
					this.setManualValidateLog(lineItem.getText() + "price is incorrect!");
			}
			if (priceOffset[2] != Common.floatSubtract(priceAfter[2], priceCurrent[2])) {
				if (priceOffset[1] != Common.floatSubtract(priceAfter[0], priceCurrent[0]) || Common
						.floatSubtract(priceOffset[1], Common.floatSubtract(priceAfter[2], priceCurrent[2])) != Common
								.floatSubtract(priceAfter[1], priceCurrent[1]))
					this.setManualValidateLog(lineItem.getText() + "price is incorrect!");
			}
		}

	}
	
	private void validatePricesSameOld(float[] previousPrices, float[] afterPrices)
	{
		for (int x = 0;x<previousPrices.length;x++)
		{
			if (previousPrices[x] != afterPrices[x])
			{
				Assert.fail("Prices after switch page is wrong!");
			}
		}
	}
}
