package TestScript.B2C;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA14919Test extends SuperTestClass {
	private boolean loginFlag = false;
	public NA14919Test(String store) {
		this.Store = store;
		this.testName = "NA-14919";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void NA14919(ITestContext ctx) {
		try {
			this.prepareTest();
			// Update HMC configurations; 
			HMCPage hmcPage = new HMCPage(driver);
		    driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			hmcPage.B2CUnit_NoHideThankyouPageRadioButton1.click();
			hmcPage.Common_SaveButton.click();
			Thread.sleep(10000);	
			
			// Guest checkout
			driver.manage().deleteAllCookies();
			B2CPage b2cPage = new B2CPage(driver);
			driver.get(testData.B2C.getHomePageUrl());
//			if (!driver.getCurrentUrl().endsWith("RegistrationGatekeeper")) {
				B2CCommon.handleGateKeeper(b2cPage, testData);
//				B2CCommon.ClickToCartPage(b2cPage);
				driver.get(driver.getCurrentUrl() + "/cart");
//				b2cPage.Navigation_CartIcon.click();
//				if(Common.checkElementExists(driver, b2cPage.Navigation_ViewCartButton, 10))
//					b2cPage.Navigation_ViewCartButton.click();
//				if (Common.isElementExist(driver, By.xpath("//form[@id='emptyCartItemsForm']/a"))) {
//					driver.findElement(By.xpath("//form[@id='emptyCartItemsForm']/a")).click();
//				}
				//BR Date is null,so judgment
//				if(Store.equals("BR")){
//					addPartNumberToCart(b2cPage, "81A20000BR");
//				}else{
//					addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
//				}
				B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
				Thread.sleep(10000);
				b2cPage.Cart_CheckoutButton.click();
				
				// If already login in register gate keeper, then no
				// StartCheckout button and ShippingInfo is pre-filled
				String tempEmail = EMailCommon.getRandomEmailAddress();
				String firstName = Common.getDateTimeString();
				String lastName = Common.getDateTimeString();
				if (Common.checkElementExists(driver, b2cPage.Checkout_StartCheckoutButton, 3)) {
					b2cPage.Checkout_StartCheckoutButton.click();
				}else{loginFlag = true;}

				// Fill shipping info
				if(Store.equals("BR")){
					B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, "Brasília-Distrito Federal",
							"Rio de Janeiro", "Brasilia", "70443900",
							"6121958200", tempEmail, "84511773521");
					
				}else{
					B2CCommon.fillShippingInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
							testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
							testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone(), tempEmail);
				}
		
				Common.javascriptClick(driver, b2cPage.Shipping_ContinueButton);
//				if (Common.checkElementExists(driver, b2cPage.Shipping_AddressMatchOKButton, 10))
//					b2cPage.Shipping_AddressMatchOKButton.click();
				B2CCommon.handleAddressVerify(driver, b2cPage);

				// Fill payment info
				B2CCommon.fillDefaultPaymentInfo(b2cPage, testData);
				B2CCommon.fillPaymentAddressInfo(b2cPage, firstName, lastName, testData.B2C.getDefaultAddressLine1(),
						testData.B2C.getDefaultAddressCity(), testData.B2C.getDefaultAddressState(),
						testData.B2C.getDefaultAddressPostCode(), testData.B2C.getDefaultAddressPhone());
				b2cPage.Payment_ContinueButton.click();

				B2CCommon.handleVisaVerify(b2cPage);
				
				// Place Order
				Common.javascriptClick(driver, b2cPage.OrderSummary_AcceptTermsCheckBox);
				B2CCommon.clickPlaceOrder(b2cPage);

				// Get Order number
				String orderNum = B2CCommon.getOrderNumberFromThankyouPage(b2cPage);
				Dailylog.logInfoDB(1, "Order number is: " + orderNum,Store,testName);

				if (!loginFlag) {
				// Check Pre-filled fields
				Assert.assertEquals(b2cPage.RegistrateAccount_FirstNameTextBox.getAttribute("value"), firstName
						.substring(0, b2cPage.RegistrateAccount_FirstNameTextBox.getAttribute("value").length()));
				Assert.assertEquals(b2cPage.RegistrateAccount_LastNameTextBox.getAttribute("value"), lastName
						.substring(0, b2cPage.RegistrateAccount_LastNameTextBox.getAttribute("value").length()));
				Assert.assertEquals(b2cPage.RegistrateAccount_EmailIdTextBox.getAttribute("value"), tempEmail);
				Assert.assertEquals(b2cPage.RegistrateAccount_ConfirmEmailTextBox.getAttribute("value"), tempEmail);
				b2cPage.RegistrateAccount_PasswordTextBox.clear();
				b2cPage.RegistrateAccount_PasswordTextBox.sendKeys("1q2w3e4r");
				b2cPage.RegistrateAccount_ConfirmPasswordTextBox.clear();
				b2cPage.RegistrateAccount_ConfirmPasswordTextBox.sendKeys("1q2w3e4r");
				b2cPage.RegistrateAccount_AcceptTermsCheckBox.click();
				b2cPage.OrderThankyou_CreateAccountButton.click();
				Thread.sleep(10000);
				EMailCommon.activeNewAccount(driver,tempEmail, 1, Store);
				
				//Login in new account
				driver.get(testData.B2C.getloginPageUrl());
				B2CCommon.login(b2cPage, tempEmail, "1q2w3e4r");
				b2cPage.MyAccount_ViewOrderHistoryLink.click();
				if(!B2CCommon.checkOrderInHistroyPage(b2cPage, orderNum))
					Assert.fail(this.Store + " order " + orderNum + " doesn't exist!");
				
			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
//public void addPartNumberToCart(B2CPage b2cPage, String partNum) {
		
//		b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//		b2cPage.Cart_AddButton.click();
//		if(Common.checkElementExists(driver, b2cPage.Cart_CheckoutButton, 10)){
//			Dailylog.logInfo("PartNumber(" + partNum
//					+ ") is valid,  Congratulation to you!");
//		}else{
//			if(Store.equals("AU")){
//				partNum = "4XD0K74703";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("BR")){
//				partNum = "80YM0000BR";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("CA")){
//				partNum = "4XD0K25031";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("CA_AFFINITY")){
//				partNum = "60F3JAR2US";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("HK")){
//				partNum = "10R0PAR1WW";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("HKZF")){
//				partNum = "ZG38C01327";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("JP")){
//				partNum = "4X40E77329";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("NZ")){
//				partNum = "61ABMAR1AU";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("SG")){
//				partNum = "20GQA00DSG";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("US")){
//				partNum = "20HKCTO1WW";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("USEPP")){
//				partNum = "4XD0K25029";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("US_BPCTO")){
//				partNum = "80SV0057US";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//				
//			}else if(Store.equals("US_OUTLET")){
//				partNum = "80VJ0000US";
//				b2cPage.Cart_QuickOrderTextBox.sendKeys(partNum);
//				b2cPage.Cart_AddButton.click();
//			}
//		}
//		try {
//			Common.waitElementClickable(b2cPage.PageDriver, b2cPage.PageDriver
//					.findElement(By.xpath("//div[@data-p-code='" + partNum
//							+ "']")), 8);
//		} catch (NoSuchElementException e) {
//			Assert.fail("PartNumber(" + partNum
//					+ ") is invalid, need to update test data!");
//		}
//	}
}

