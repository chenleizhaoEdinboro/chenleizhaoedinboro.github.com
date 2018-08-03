package TestScript.FE;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.Set;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import Pages.FEPage;
import TestScript.SuperTestClass;

public class COMM712Test extends SuperTestClass{

	public FEPage fePage;
	public   COMM712Test(String store,String country) {
		this.Store = store;
		this.testName = "COMM712Test";

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
			driver.manage().window().setSize(new Dimension(360, 640));
			driver.get("https://www3.lenovo.com/us/en/laptops/thinkpad/thinkpad-t-series/ThinkPad-T480/p/22TP2TT4800?menu-id=T480");
			
			Thread.sleep(20000);
			
			Robot rb = new Robot();
			rb.keyPress(KeyEvent.VK_ESCAPE); 
			rb.keyPress(KeyEvent.VK_ENTER); 
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", fePage.view_CustomizeButton);
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
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", fePage.addOrCustomizeButton.get(0));
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.addOrCustomizeButton.get(0));
			Thread.sleep(10000); 
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.addToCartButtonOnConfigurator);
			Thread.sleep(10000); 
			
			Assert.assertTrue(sProuctTitleOnSubSeries.contains(fePage.itemNameOnCartPage.getText()));
			String sItemPartNumber = fePage.itemPartNumber.getText();
			Assert.assertTrue(sItemPartNumber.length() > 0 & sItemPartNumber!= null);
			Assert.assertTrue(fePage.itemImage.isDisplayed());
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
			fePage.quantityInputText.clear();
			fePage.quantityInputText.sendKeys(String.valueOf(iUpdateQuantity));
			// removing item from cart
			fePage.editButtonCartPage.click();
			fePage.removeLink.click();
			fePage.alertPositiveCartPage.isDisplayed();
			
			//	adding item by providing part number
			String newPartNumber = testData.B2C.getNewPartNumber();
			fePage.partNumberInputText.sendKeys(newPartNumber);
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.submitButtonCartPage);
			Thread.sleep(10000);
			driver.navigate().refresh();
			Assert.assertEquals(fePage.cartItem.size(),1);
			
			//To edit the item
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.editButtonCartPage);
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.editLink);
			Thread.sleep(3000);
			
			fePage.warrantyTab.click();
			String warrantyOptionValue = null;
			fePage.changeButton.click();
			fePage.warrantyTab.click();
			Thread.sleep(2000);
			warrantyOptionValue = String.valueOf(B2CCommon.GetPriceValue(fePage.warrantyOption.getText().replace("+", "").trim()));
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.mobileWarrantyOption);
			String yourPriceOnConfigurator = String.valueOf(B2CCommon.GetPriceValue(fePage.yourPriceOnConfigurator.getText()));
			fePage.addToCartButtonOnConfigurator.click();
			
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
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.signInButton);
			Thread.sleep(3000);
			fePage.cartLink.click();
			
			//SAve for  later 
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.editButtonCartPage);
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.saveForLaterLink);
			Assert.assertTrue(fePage.savedCartItemSection.isDisplayed());
			Assert.assertTrue(fePage.saveCartItemLabel.isDisplayed());
			Assert.assertEquals(fePage.saveCartPartNumber.getText(), newPartNumber);
			String saveCartWebPrice = String.valueOf(B2CCommon.GetPriceValue(fePage.saveCartWebPrice.getText()));
			Assert.assertEquals(saveCartWebPrice, newSubtotal);
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.editButtonCartPage);
			Assert.assertTrue(fePage.saveCartRemoveLink.isDisplayed());
			Assert.assertTrue(fePage.saveCartAddToCartLink.isDisplayed());
			//System.out.println(fePage.saveForLaterLink.isDisplayed());
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.mobilesaveCartAddToCartLink);
			
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
//		    fePage.trashLink.click();
//		     Assert.assertTrue(fePage.trashPopup.isDisplayed());
//		    fePage.saveLaterButtonInPopup.click();
//		    Assert.assertTrue(fePage.cartNamePopup.isDisplayed());
//		     fePage.saveLaterPopupCloseLink.click();
//		    fePage.deleteCartButtonInPopup.click();
//		    boolean isPresent = fePage.cartEmptyLabel.getText().length() > 0;
//		    //boolean isPresent = driver.findElements(By.xpath("//div[@class='cart-item']")).size() > 0;
//		    Assert.assertTrue(isPresent);
//		    //Assert.assertTrue(!driver.findElement(By.xpath("//div[@class='cart-item']")).isDisplayed());
//		    
//		  // Adding new product to cart
//			fePage.partNumberInputText.sendKeys(newPartNumber);
//			fePage.submitButtonCartPage.click();
//		    
//			//Click on Print Link
//			fePage.printerLink.click();
////			Thread.sleep(5000);
////			rb1.keyPress(KeyEvent.VK_ESCAPE);
////			rb1.keyPress(KeyEvent.VK_ESCAPE);
////			rb1.keyPress(KeyEvent.VK_ESCAPE);
////			rb1.keyPress(KeyEvent.VK_ESCAPE);
//			Actions action = new Actions(driver);
//			action.sendKeys(Keys.ESCAPE).build().perform();;
//			Thread.sleep(5000);
			
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
