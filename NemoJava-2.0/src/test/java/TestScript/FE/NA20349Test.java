package TestScript.FE;


import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import Pages.FEPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class  NA20349Test  extends SuperTestClass {
	public FEPage fePage;
	public  NA20349Test(String store,String country) {
		this.Store = store;
		this.testName = "NA20349Test";

	}

	/*
	 * 	avalsan
	 * 	validation of Print Cart, Email Cart, Save Cart, Empty Cart icons and other functionality
	 **/

	@Test(groups={"migrationgroup"})
	public void cartPageValidation() throws InterruptedException, MalformedURLException {
		this.prepareTest();

		try {
			fePage = new FEPage(driver);
			boolean bItemDiscount = false;
			driver.get("https://www3.lenovo.com/us/en/laptops/thinkpad/thinkpad-t-series/ThinkPad-T480/p/22TP2TT4800?menu-id=T480");
			Thread.sleep(30000);

			Robot rb = new Robot();
			rb.keyPress(KeyEvent.VK_ESCAPE); 
			rb.keyPress(KeyEvent.VK_ENTER); 
			fePage.view_CustomizeButton.click();

			Thread.sleep(10000);	

			String sItemSalePriceOnSubSeries = null;
			String sProuctTitleOnSubSeries = fePage.prouctTitleOnSubSeries.get(0).getText();
			String sDiscountOnSubSeries = null;
			String sWebPriceOnSubSeries = null;
			double dCalculatedSalePrice = 0.0d;
			//Checking whether item is having an Instant savings or not
			if(fePage.pricingSummaryDetails.get(4).findElements(By.tagName("dd")).size()>1)
			{
				sWebPriceOnSubSeries = String.valueOf(B2CCommon.GetPriceValue(fePage.pricingSummaryDetails.get(4).findElements(By.tagName("dd")).get(0).getText().replace(",", "")));
				sDiscountOnSubSeries = String.valueOf(B2CCommon.GetPriceValue(fePage.pricingSummaryDetails.get(4).findElements(By.tagName("dd")).get(2).getText().replace(",", "")));
				dCalculatedSalePrice = Double.parseDouble(sWebPriceOnSubSeries) - Double.parseDouble(sDiscountOnSubSeries);
				sItemSalePriceOnSubSeries = String.valueOf(B2CCommon.GetPriceValue(fePage.pricingSummaryDetails.get(4).findElements(By.tagName("dd")).get(1).getText().replace(",", "")));
				Assert.assertEquals(Double.parseDouble(sItemSalePriceOnSubSeries), dCalculatedSalePrice);
				bItemDiscount = true;
			}
			else
			{
				//Without Instant Savings Item
				sItemSalePriceOnSubSeries = String.valueOf(B2CCommon.GetPriceValue(fePage.pricingSummaryDetails.get(4).findElements(By.tagName("dd")).get(0).getText().replace(",", "")));
			}
			fePage.addOrCustomizeButton.get(0).click();
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.addToCartButtonOnConfigurator);
			Thread.sleep(10000); 
			Assert.assertTrue(sProuctTitleOnSubSeries.contains(fePage.itemNameOnCartPage.getText()));
			String sItemPartNumber = fePage.itemPartNumber.getText();
			Assert.assertTrue(sItemPartNumber.length() > 0 & sItemPartNumber!= null);
			Assert.assertTrue(fePage.itemImage.isDisplayed());
			Thread.sleep(5000);
			//Item Price check for discount product check discount, web price
			if(bItemDiscount)
			{	
				Assert.assertEquals(String.valueOf(B2CCommon.GetPriceValue(fePage.itemWebPriceOnCartPage.getText().replace(",", ""))), sWebPriceOnSubSeries);
				Assert.assertEquals(String.valueOf(B2CCommon.GetPriceValue(fePage.itemDiscountOnCartPage.getText().replace(",", ""))), sDiscountOnSubSeries);
			}
			else
			{
				Assert.assertEquals(String.valueOf(B2CCommon.GetPriceValue(fePage.itemSalePriceOnCartPage.getText())), sItemSalePriceOnSubSeries);
			}

			//Item subtotal 
			Assert.assertEquals(String.valueOf(B2CCommon.GetPriceValue(fePage.itemSubtotal.getText())), sItemSalePriceOnSubSeries);
			//Estimated Total
			Assert.assertEquals(String.valueOf(B2CCommon.GetPriceValue(fePage.estimatedTotal.getText())), sItemSalePriceOnSubSeries);
			// upating the quantity field and checking the estimate total subtotal value
			fePage.quantityInputText.clear();
			int iUpdateQuantity = 4;
			fePage.quantityInputText.sendKeys(String.valueOf(iUpdateQuantity));
			fePage.updateLink.click();
			Thread.sleep(5000);
			Assert.assertTrue(fePage.alertPositiveCartPage.isDisplayed());
			String sColor = fePage.alertPositiveCartPage.getCssValue("color");
			String sActualColor = cssValue(sColor);
			Assert.assertEquals(sActualColor,testData.B2C.getCssValueAlertPositive());
			double dCalculateWebPrice = 0.0d;

			DecimalFormat df = new DecimalFormat("#.##");
			dCalculateWebPrice = (Double.parseDouble(sItemSalePriceOnSubSeries)) * iUpdateQuantity;

			if(bItemDiscount)
			{	
				Assert.assertEquals(Double.parseDouble(df.format(B2CCommon.GetPriceValue(fePage.itemSalePriceOnCartPage.getText().replace(",", "")))), Double.parseDouble(String.format("%.2f", dCalculateWebPrice)));
				Double dUpdatedPrice = (Double.parseDouble(sWebPriceOnSubSeries)) * iUpdateQuantity;
				Assert.assertEquals(Double.parseDouble(df.format(B2CCommon.GetPriceValue(fePage.itemWebPriceOnCartPage.getText().replace(",", "")))), dUpdatedPrice);
			}
			else
			{
				Double d1 = Double.parseDouble(df.format(B2CCommon.GetPriceValue(fePage.itemSalePriceOnCartPage.getText().replace(",", ""))));
				Double d2 = Double.parseDouble(String.format("%.2f", dCalculateWebPrice));
				Assert.assertEquals(d1, d2);
			}

			//updating quantity with invalid quantity 
			int iInvalidQuantity = testData.B2C.getInvalidQuantity();
			fePage.quantityInputText.clear();
			fePage.quantityInputText.sendKeys(String.valueOf(iInvalidQuantity));
			fePage.itemLimitPopup.isDisplayed();

			// removing item from cart
			fePage.removeLink.click();
			fePage.alertPositiveCartPage.isDisplayed();

			//	adding item by providing part number
			String newPartNumber = testData.B2C.getNewPartNumber();
			fePage.partNumberInputText.sendKeys(newPartNumber);
			fePage.submitButtonCartPage.click();
			Assert.assertEquals(fePage.cartItem.size(),1);

			//To edit the item
			fePage.editLink.click();
			Thread.sleep(3000);
			fePage.warrantyTab.click();
			String warrantyOptionValue = null;
			try 
			{
				if(fePage.warrantyOption.isDisplayed())
				{
					System.out.println("Warranty Option displayed!!");
				}
			}
			catch(Exception e)
			{
				((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.changeButton);
				System.out.println("Change button clicked - Exception thrown");
			}
			warrantyOptionValue = String.valueOf(B2CCommon.GetPriceValue(fePage.warrantyOption.getText().replace("+", "").trim()));
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.warrantyOption);
			String yourPriceOnConfigurator = String.valueOf(B2CCommon.GetPriceValue(fePage.yourPriceOnConfigurator.getText()));
			fePage.addToCartButtonOnConfigurator.click();
			Thread.sleep(5000);

			//Item subtotal after editing
			String newSubtotal = String.valueOf(B2CCommon.GetPriceValue(fePage.itemSubtotal.getText()));
			Assert.assertEquals(newSubtotal, yourPriceOnConfigurator);
			//Estimated Total after editing
			Assert.assertEquals(String.valueOf(B2CCommon.GetPriceValue(fePage.estimatedTotal.getText())), yourPriceOnConfigurator);

			// checking the new attribute
			Assert.assertEquals(String.valueOf(B2CCommon.GetPriceValue(fePage.cartItemAddedItemPrice.getText())),warrantyOptionValue);
			Assert.assertTrue(fePage.cartItemAddedItemTitle.getText()!= null);
			Assert.assertTrue(!fePage.cartItemAddedItemTitle.getText().equals(""));
			Thread.sleep(10000);

			//Sign In
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.myAccountLink);
			fePage.signInLink.click();
			Thread.sleep(5000);
			fePage.username.sendKeys(testData.B2C.getUserName());
			fePage.password.sendKeys(testData.B2C.getPassword());
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.signInButton);
			Thread.sleep(3000);
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.cartLink);

			//SAve for  later 
			fePage.saveForLaterLink.click();
			Assert.assertTrue(fePage.savedCartItemSection.isDisplayed());
			Assert.assertTrue(fePage.saveCartItemLabel.isDisplayed());
			Assert.assertEquals(fePage.saveCartPartNumber.getText(), newPartNumber);
			String saveCartWebPrice = String.valueOf(B2CCommon.GetPriceValue(fePage.saveCartWebPrice.getText()));
			Assert.assertEquals(saveCartWebPrice, newSubtotal);
			Assert.assertTrue(fePage.saveCartRemoveLink.isDisplayed());
			Assert.assertTrue(fePage.saveCartAddToCartLink.isDisplayed());
			//System.out.println(fePage.saveForLaterLink.isDisplayed());
			fePage.saveCartAddToCartLink.click();

			Assert.assertTrue(fePage.cartItem.get(0).isDisplayed());
			Assert.assertEquals(fePage.itemPartNumber.getText(), newPartNumber);

			//Click onEmail Link
			String winHandleBefore = driver.getWindowHandle();
			fePage.emailLink.click();
			Set<String> allWindows = driver.getWindowHandles();
			for(String currentWindow : allWindows)
			{
				driver.switchTo().window(currentWindow);
			}
			Assert.assertTrue(driver.getCurrentUrl().contains("inputEmailCartInfo"));
			driver.close();
			driver.switchTo().window(winHandleBefore);	
			Thread.sleep(3000);

			//Click on Save icon link
			fePage.saveiconLink.click();
			Assert.assertTrue(fePage.cartNamePopup.isDisplayed());
			String sCartName = testData.B2C.getCartName();
			fePage.realsavecartnameInputText.sendKeys(sCartName);
			fePage.saveCartButton.click();
			Assert.assertTrue(fePage.savedCartBody.isDisplayed());
			String CartNameInCartHistory = fePage.cartNameInCartHistory.getText();
			Assert.assertEquals(CartNameInCartHistory, sCartName);
			String totalInCartHistory = String.valueOf(B2CCommon.GetPriceValue(fePage.TotalInCartHistory.getText()));
			Assert.assertEquals(totalInCartHistory, newSubtotal);
			fePage.cartLink.click();

			//Click onTrash link
			fePage.trashLink.click();
			Assert.assertTrue(fePage.trashPopup.isDisplayed());
			fePage.saveLaterButtonInPopup.click();
			Assert.assertTrue(fePage.cartNamePopup.isDisplayed());
			fePage.saveLaterPopupCloseLink.click();
			fePage.deleteCartButtonInPopup.click();
			boolean isPresent = fePage.cartEmptyLabel.getText().length() > 0;
			Assert.assertTrue(isPresent);

			// Adding new product to cart
			fePage.partNumberInputText.sendKeys(newPartNumber);
			fePage.submitButtonCartPage.click();
			//Click on Print Link
			//Robot rb1 = new Robot();
			fePage.printerLink.click();
			//			Thread.sleep(5000);
			//			rb1.keyPress(KeyEvent.VK_ESCAPE);
			//			rb1.keyPress(KeyEvent.VK_ESCAPE);
			//			rb1.keyPress(KeyEvent.VK_ESCAPE);
			//			rb1.keyPress(KeyEvent.VK_ESCAPE);
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ESCAPE).build().perform();;
			Thread.sleep(5000);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		driver.close();
	}

	/*
	 * for fetching the css value from the page
	 */
	static String cssValue(String element){

		String[] hexValue;
		if(element.contains("a")) {
			hexValue = element.replace("rgba(", "").replace(")", "").replace(" ", "").split(",");
		}
		else {
			hexValue = element.replace("rgb(", "").replace(")", "").replace(" ", "").split(",");
		}
		System.out.println(hexValue[0]);
		System.out.println(hexValue[1]);
		System.out.println(hexValue[2]);
		hexValue[0] = hexValue[0].trim();
		int hexValue0 = Integer.parseInt(hexValue[0]);
		hexValue[1] = hexValue[1].trim();
		int hexValue1 = Integer.parseInt(hexValue[1]);
		hexValue[2] = hexValue[2].trim();
		int hexValue2 = Integer.parseInt(hexValue[2]);
		String actualColor = String.format("#%02x%02x%02x", hexValue0, hexValue1, hexValue2);
		return actualColor;
	}
}
