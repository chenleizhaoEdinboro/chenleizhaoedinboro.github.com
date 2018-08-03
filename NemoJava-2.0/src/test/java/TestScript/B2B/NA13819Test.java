package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA13819Test extends SuperTestClass {
	private String HMCURL;
	private String aribaUrl ;
	private String ociUrl ;
	private String oxmlUrl ;
	private String defaultUnit;
	private String defaultDMU;
	private String AccessLevel;
	private String product1;
	private String product2;

	public NA13819Test(String store, String defaultUnit, String defaultDMU, String AccessLevel, String product1,
			String product2) {
		this.Store = store;
		this.testName = "NA-13819";
		this.defaultUnit = defaultUnit;
		this.defaultDMU = defaultDMU;
		this.AccessLevel = AccessLevel;
		this.product1 = product1;
		this.product2 = product2;
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p1", "b2b"})
	public void NA13819(ITestContext ctx) {
		try {
			this.prepareTest();
			aribaUrl = testData.B2B.getHomePageUrl().split("le/")[0] + "nemopunchouttool/ariba";
			ociUrl = testData.B2B.getHomePageUrl().split("le/")[0] + "/nemopunchouttool/oci";
			oxmlUrl = testData.B2B.getHomePageUrl().split("le/")[0] + "/nemopunchouttool/oxml";
			
			HMCPage hmcPage = new HMCPage(driver);
			B2BPunchoutPage punchoutPage = new B2BPunchoutPage(driver);
			B2BPage b2bPage = new B2BPage(driver);
			HMCURL = testData.HMC.getHomePageUrl();
			String B2BCustomer = "PUNCHOUT-RE";
			String Code = "PUNCHOUT-RE";
			String Domain = "NetworkID";
			String Identity = "PUNCHOUT-RE";
			String SharedSecret = "aaa";
			String password = "aaa";
			String ProfileName = "PUNCHOUT-RE#AU#default";
			String quantity = "3";
			boolean isDisplayed;
			String cartName = "UXUI-Ariba-1";

			// 1. Use admin user to logon HMC
			driver.get(HMCURL);
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1, "login hmc", Store, testName);

			// 2. Set B2B Customer
			HMCCommon.searchB2BCustomer(hmcPage, B2BCustomer);
			if (Common.isElementExist(driver, By.xpath(".//tr[contains(@id,'Content/OrganizerListEntry')]//div[text()='"
					+ B2BCustomer + "']/../../../td[3]"))) {
				driver.findElement(By.xpath(".//tr[contains(@id,'Content/OrganizerListEntry')]//div[text()='"
						+ B2BCustomer + "']/../../../td[3]")).click();
				EditCustomer(driver, hmcPage, B2BCustomer, defaultUnit, defaultDMU, AccessLevel);
			} else {
				CreateCustomer(driver, hmcPage, B2BCustomer, defaultUnit, defaultDMU, AccessLevel);
			}
			Dailylog.logInfoDB(2, "Set B2B Customer", Store, testName);

			// 2. Set Credential -- Ariba,OCI,Oracle
			SearchCredential(driver, hmcPage, B2BCustomer);
			if (Common.isElementExist(driver, By.xpath(".//*[@id='Content/ItemDisplay[" + B2BCustomer + "]_div2']"))) {
				Common.doubleClick(driver,
						driver.findElement(By.xpath(".//*[@id='Content/ItemDisplay[" + B2BCustomer + "]_div2']")));
				EditCredential(driver, hmcPage, B2BCustomer, Code, Domain, Identity, SharedSecret, B2BCustomer,
						password);
			} else {
				CreateCredential(driver, hmcPage, B2BCustomer, Code, Domain, Identity, SharedSecret, B2BCustomer,
						password);
			}
			Dailylog.logInfoDB(2, "Set Credential -- Ariba,OCI,Oracle", Store, testName);

			// 2. Create punchout profile default soldto
			checkPunchoutProfile(driver, hmcPage, ProfileName, B2BCustomer);
			Dailylog.logInfoDB(2, "Create punchout profile default soldto", Store, testName);

			// 3~15 Ariba punchout TODO
			SimulationToolAriba(driver, punchoutPage, Domain, Identity, SharedSecret);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));

			// confirm Homepage Contains
			confirmHomepageContains(driver, punchoutPage);
			Dailylog.logInfoDB(6, "Confirm Home Page Contains", Store, testName);

			// check Exit
			checkExit(driver, punchoutPage, product1);
			driver.switchTo().defaultContent();
			// Assert Return to Simulation tool.
			isDisplayed = Common.checkElementExists(driver, punchoutPage.Ariba_returnedMsg, 5);
			if (!isDisplayed)
				Assert.fail("Order returned from Nemo Punchout message is not displayed");
			else
				System.out.println("Order returned from Nemo Punchout message is displayed");

			// Assert Show empty cart.
			String message = punchoutPage.Ariba_OrderListCartMessage.getText();
			if (message.indexOf("cart=") >= 0) {
				Dailylog.logInfoDB(7, message, Store, testName);
				Assert.fail("Cart id is not empty");
			} else
				System.out.println("Cart id is empty");

			// Assert And no "Edit" button
			isDisplayed = Common.checkElementExists(driver, punchoutPage.Ariba_EditButton, 10);
			if (!isDisplayed) {
				Assert.fail("Edit button is displayed");
			} else
				System.out.println("Edit button is not displayed");
			Dailylog.logInfoDB(7, "Check Exit", Store, testName);

			// Click "Punchout"
			punchoutPage.Ariba_PunchoutButton.click();
			Common.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
			Dailylog.logInfoDB(8, "Click \"Punchout\" ", Store, testName);

			// Check Cart function "UPDATE" "Edit "REMOVE"
			checkCartFunction(driver, b2bPage, punchoutPage, product1, product2, quantity);

			// Assert Return to Simulation tool. Show cart details.
			Common.sleep(1000);
			driver.switchTo().defaultContent();
			isDisplayed = Common.checkElementExists(driver, punchoutPage.Ariba_returnedMsg, 10);
			if (!isDisplayed)
				Assert.fail("Order returned from Nemo Punchout message is not displayed");
			else
				System.out.println("Order returned from Nemo Punchout message is not displayed");

			// product number
			message = punchoutPage.Ariba_OrderListCartMessage.getText();
			if (message.indexOf("<SupplierPartID>" + product2 + "</SupplierPartID>") < 0) {
				Dailylog.logInfoDB(7, message, Store, testName);
				Assert.fail("<SupplierPartID>" + product2 + "</SupplierPartID> is not found in simulation tool msg");
			}

			// quantity
			if (message.indexOf("<ItemIn quantity=" + '"' + quantity + '"' + ">") < 0) {
				Dailylog.logInfoDB(7, message, Store, testName);
				Assert.fail("<ItemIn quantity=" + '"' + quantity + '"' + "> is not found in simulation tool msg");
			}

			// Click "Punchout"
			punchoutPage.Ariba_PunchoutButton.click();
			Common.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));

			// Check "Save cart"
			checkSavedcart(driver, b2bPage, punchoutPage, product1, product2, cartName);
			driver.switchTo().defaultContent();

			// Step21~25:Oracle punchout TODO
			SimulationToolOracle(driver, punchoutPage, B2BCustomer, password);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));

			// confirm Homepage Contains
			confirmHomepageContains(driver, punchoutPage);

			// check Exit
			checkExit(driver, punchoutPage, product1);
			driver.switchTo().defaultContent();

			// Assert Return to Simulation tool.
			isDisplayed = Common.checkElementExists(driver, punchoutPage.Ariba_returnedMsg, 5);
			if (!isDisplayed)
				Assert.fail("Order returned from Nemo Punchout message is not displayed");

			// Assert Show empty cart.
			message = punchoutPage.Ariba_OrderListCartMessage.getText();
			if (message.indexOf("cart=") >= 0 || message.indexOf("itemID") >= 0) {
				Dailylog.logInfoDB(25, message, Store, testName);
				Assert.fail("Cart id is not empty");
			}

			// Click "Punchout"
			punchoutPage.Ariba_PunchoutButton.click();
			Common.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));

			// Check Cart function "UPDATE" "Edit "REMOVE"
			checkCartFunction(driver, b2bPage, punchoutPage, product1, product2, quantity);

			// Assert Return to Simulation tool. Show cart details.
			driver.switchTo().defaultContent();
			isDisplayed = Common.checkElementExists(driver, punchoutPage.Ariba_returnedMsg, 10);
			if (!isDisplayed)
				Assert.fail("Order returned from Nemo Punchout message is not displayed");

			// product number
			message = punchoutPage.Ariba_OrderListCartMessage.getText();
			if (message.indexOf("<itemID>" + product2 + "</itemID>") < 0) {
				Dailylog.logInfoDB(25, message, Store, testName);
				Assert.fail("<itemID>" + product2 + "</itemID> is not found in simulation tool msg");
			}

			// quantity
			if (message.indexOf("quantity=" + '"' + quantity + '"') < 0) {
				Dailylog.logInfoDB(25, message, Store, testName);
				Assert.fail("quantity=" + '"' + quantity + '"' + " is not found in simulation tool msg");
			}

			// Click "Punchout"
			punchoutPage.Ariba_PunchoutButton.click();
			Common.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));

			// Check "Save cart"
			checkSavedcart(driver, b2bPage, punchoutPage, product1, product2, cartName);
			driver.switchTo().defaultContent();

			// 16~20 OCI punchout TODO
			SimulationToolOCI(driver, punchoutPage, B2BCustomer, password);
			switchToWindow(1);

			// confirm Homepage Contains
			confirmHomepageContains(driver, punchoutPage);

			// check Exit
			checkExit(driver, punchoutPage, product1);
			Common.sleep(5000);
			new WebDriverWait(driver, 30).until(ExpectedConditions.numberOfWindowsToBe(1));
			switchToWindow(0);

			// Assert Return to Simulation tool and show empty cart
			isDisplayed = Common.checkElementExists(driver, punchoutPage.Oci_returnedBlankMsg, 5);
			if (!isDisplayed)
				Assert.fail("Exit -- Return from Hybris with blank form message is not displayed");

			// Click "Punchout"
			punchoutPage.OCI_PunchoutButton.click();
			Common.sleep(1000);
			switchToWindow(1);

			// Check Cart function "UPDATE" "Edit "REMOVE" -> checkout
			checkCartFunction(driver, b2bPage, punchoutPage, product1, product2, quantity);
			Common.sleep(1000);
			new WebDriverWait(driver, 30).until(ExpectedConditions.numberOfWindowsToBe(1));
			switchToWindow(0);

			// Assert Return to Simulation tool. Show cart details.
			isDisplayed = Common.checkElementExists(driver, punchoutPage.Oci_returnedMsg, 5);
			if (!isDisplayed)
				Assert.fail("Form returned from Nemo Punchout message is not displayed");

			// product number
			message = punchoutPage.Oci_OrderListCartMessage.getText();
			if (message.indexOf("NEW_ITEM-EXT_PRODUCT_ID[1]=" + product2) < 0) {
				Dailylog.logInfoDB(20, message, Store, testName);
				Assert.fail("NEW_ITEM-EXT_PRODUCT_ID[1]=" + product2 + " is not found in simulation tool msg");
			}

			// quantity
			if (message.indexOf("NEW_ITEM-QUANTITY[1]=" + quantity) < 0) {
				Dailylog.logInfoDB(20, message, Store, testName);
				Assert.fail("NEW_ITEM-QUANTITY[1]=" + quantity + " is not found in simulation tool msg");
			}

			// Click "Punchout"
			punchoutPage.OCI_PunchoutButton.click();
			Common.sleep(1000);
			switchToWindow(1);

			// Check "Save cart"
			checkSavedcart(driver, b2bPage, punchoutPage, product1, product2, cartName);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

	public void EditCustomer(WebDriver driver, HMCPage hmcPage, String B2BCustomer, String defaultUnit,
			String defaultDMU, String AccessLevel) {
		// <General>tab： id, name, groups
		hmcPage.B2BCustomer_IDEdit.clear();
		hmcPage.B2BCustomer_IDEdit.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_NameEdit.clear();
		hmcPage.B2BCustomer_NameEdit.sendKeys(B2BCustomer);
		if (!Common.isElementExist(driver,
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='punchOutCustomer']"))) {
			hmcPage.B2BCustomer_GroupsInput.sendKeys("punchOutCustomer");
			Common.waitElementClickable(driver,
					driver.findElement(
							By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")),
					15);
			driver.findElement(
					By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")).click();
		}
		// <Access Level>tab
		hmcPage.B2BCustomer_AccessLevelTab.click();
		hmcPage.B2BCustomer_AccessLevelEdit.clear();
		hmcPage.B2BCustomer_AccessLevelEdit.sendKeys(AccessLevel);
		Common.waitElementClickable(driver,
				driver.findElement(By
						.xpath(".//*[contains(@id,'_ajaxselect_" + AccessLevel + "')][text()='" + AccessLevel + "']")),
				15);
		driver.findElement(
				By.xpath(".//*[contains(@id,'_ajaxselect_" + AccessLevel + "')][text()='" + AccessLevel + "']"))
				.click();
		hmcPage.B2BCustomer_DefaultUnitEdit.clear();
		hmcPage.B2BCustomer_DefaultUnitEdit.sendKeys(defaultUnit);
		Common.waitElementClickable(driver,
				driver.findElement(By
						.xpath(".//*[contains(@id,'_ajaxselect_" + defaultUnit + "')][text()='" + defaultUnit + "']")),
				15);
		driver.findElement(
				By.xpath(".//*[contains(@id,'_ajaxselect_" + defaultUnit + "')][text()='" + defaultUnit + "']"))
				.click();
		hmcPage.B2BCustomer_DefaultDMUEdit.clear();
		hmcPage.B2BCustomer_DefaultDMUEdit.sendKeys(defaultDMU);
		Common.waitElementClickable(driver, driver.findElement(
				By.xpath("(.//*[contains(@id,'_ajaxselect_" + defaultDMU + "')][text()='" + defaultDMU + "'])[2]")),
				15);
		driver.findElement(
				By.xpath("(.//*[contains(@id,'_ajaxselect_" + defaultDMU + "')][text()='" + defaultDMU + "'])[2]"))
				.click();
		// <Addresses>tab
		hmcPage.B2BCustomer_AddressesTab.click();
		hmcPage.B2BCustomer_EmailEdit.clear();
		hmcPage.B2BCustomer_EmailEdit.sendKeys("sample@lenovo.com");
		// <Password>tab
		hmcPage.B2BCustomer_PasswordTab.click();
		hmcPage.B2BCustomer_ActiveAccountDropdownValue.click();
		hmcPage.Common_SaveButton.click();
	}

	public void CreateCustomer(WebDriver driver, HMCPage hmcPage, String B2BCustomer, String defaultUnit,
			String defaultDMU, String AccessLevel) {
		Common.rightClick(driver, hmcPage.Home_B2BCustomer);
		hmcPage.Home_CreateB2BCustomer.click();
		hmcPage.B2BCustomer_IDInput.clear();
		hmcPage.B2BCustomer_IDInput.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_NameInput.clear();
		hmcPage.B2BCustomer_NameInput.sendKeys(B2BCustomer);
		if (!Common.isElementExist(driver,
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='punchOutCustomer']"))) {
			hmcPage.B2BCustomer_GroupsInput.sendKeys("punchOutCustomer");
			Common.waitElementClickable(driver,
					driver.findElement(
							By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")),
					15);
			driver.findElement(
					By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")).click();
		}
		hmcPage.B2BCustomer_AccessLevelTab.click();
		hmcPage.B2BCustomer_AccessLevelInput.clear();
		hmcPage.B2BCustomer_AccessLevelInput.sendKeys(AccessLevel);
		Common.waitElementClickable(driver,
				driver.findElement(By
						.xpath(".//*[contains(@id,'_ajaxselect_" + AccessLevel + "')][text()='" + AccessLevel + "']")),
				15);
		driver.findElement(
				By.xpath(".//*[contains(@id,'_ajaxselect_" + AccessLevel + "')][text()='" + AccessLevel + "']"))
				.click();
		hmcPage.B2BCustomer_DefaultUnitInput.clear();
		hmcPage.B2BCustomer_DefaultUnitInput.sendKeys(defaultUnit);
		Common.waitElementClickable(driver,
				driver.findElement(By
						.xpath(".//*[contains(@id,'_ajaxselect_" + defaultUnit + "')][text()='" + defaultUnit + "']")),
				15);
		driver.findElement(
				By.xpath(".//*[contains(@id,'_ajaxselect_" + defaultUnit + "')][text()='" + defaultUnit + "']"))
				.click();
		hmcPage.B2BCustomer_DefaultDMUInput.clear();
		hmcPage.B2BCustomer_DefaultDMUInput.sendKeys(defaultDMU);
		Common.waitElementClickable(driver, driver.findElement(
				By.xpath("(.//*[contains(@id,'_ajaxselect_" + defaultDMU + "')][text()='" + defaultDMU + "'])[2]")),
				15);
		driver.findElement(
				By.xpath("(.//*[contains(@id,'_ajaxselect_" + defaultDMU + "')][text()='" + defaultDMU + "'])[2]"))
				.click();
		hmcPage.B2BCustomer_AddressesTab.click();
		hmcPage.B2BCustomer_EmailInput.clear();
		hmcPage.B2BCustomer_EmailInput.sendKeys("sample@lenovo.com");
		hmcPage.B2BCustomer_PasswordTab.click();
		hmcPage.B2BCustomer_ActiveStatus.click();
		hmcPage.B2BCustomer_CreateButton.click();
	}

	public void SearchCredential(WebDriver driver, HMCPage hmcPage, String B2BCustomer) {
		hmcPage.Home_Nemo.click();
		hmcPage.Home_Nemo_Punchout.click();
		hmcPage.Home_Punchout_Credential.click();
		hmcPage.PunchoutCredential_CustomerSearch.clear();
		hmcPage.PunchoutCredential_CustomerSearch.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(
				".//*[@id='Content/AutocompleteReferenceEditor[in Content/GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"
						+ B2BCustomer + "']"))
				.click();
		hmcPage.Contract_searchbutton.click();

	}

	public void EditCredential(WebDriver driver, HMCPage hmcPage, String B2BCustomer, String Code, String Domain,
			String Identity, String SharedSecret, String uName, String uPwd) {

		for (int i = 0; i < 3; i++) {
			if (Common.isElementExist(driver, By.xpath(
					"//div[contains(text(),'PunchOut Credentials')]/../../td[last()]//table//table//table//input"))) {
				Common.rightClick(driver, driver.findElement(By.xpath(
						"//div[contains(text(),'PunchOut Credentials')]/../../td[last()]//table//table//table//input")));
				hmcPage.PunchoutCredential_RemoveAribaCredential.click();
				Common.sleep(2000);
				Alert alert = driver.switchTo().alert();
				alert.accept();
				System.out.println("Remove Ariba/OCI/Oracle Punchout Credential!");
				Common.sleep(8000);
			}
		}

		// Create Credential - Ariba
		AddAribaCredential(driver, hmcPage, Code, Domain, Identity, SharedSecret);

		// Create Credential - OCI

		addCredentialOCI(driver, hmcPage, Code, uName, uPwd);

		// Create Credential - Oracle
		addCredentialOracle(driver, hmcPage, Code, uName, uPwd);

		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Credential successfully!");
	}

	public void AddAribaCredential(WebDriver driver, HMCPage hmcPage, String Code, String Domain, String Identity,
			String SharedSecret) {
		System.out.println("AddAribaCredential Start");
		Common.rightClick(driver, hmcPage.PunchoutCredential_AribaCredential);
		hmcPage.PunchoutCredential_AddAribaCredential.click();
		switchToWindow(1);
		hmcPage.PunchoutAribaCredential_CodeSearchInput.clear();
		hmcPage.PunchoutAribaCredential_CodeSearchInput.sendKeys(Code);
		hmcPage.Contract_searchbutton.click();
		if (!Common.isElementExist(driver, By.xpath(".//*[@id='StringDisplay[" + Code + "]_span']"))) {
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			switchToWindow(0);
			CreateAribaCredential(driver, hmcPage, Code, Domain, Identity, SharedSecret);
		} else {
			driver.findElement(By.xpath(".//*[@id='StringDisplay[" + Code + "]_span']")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			switchToWindow(0);
		}
		System.out.println("AddAribaCredential End");
	}

	public void CreateAribaCredential(WebDriver driver, HMCPage hmcPage, String Code, String Domain, String Identity,
			String SharedSecret) {
		System.out.println("CreateAribaCredential");
		String mainHandle = driver.getWindowHandle();
		Common.rightClick(driver, hmcPage.PunchoutCredential_AribaCredential);
		hmcPage.PunchoutCredential_CreateAribaCredential.click();
		switchToWindow(1);
		hmcPage.PunchoutAribaCredential_CodeInput.clear();
		hmcPage.PunchoutAribaCredential_CodeInput.sendKeys(Code);
		hmcPage.PunchoutAribaCredential_DomainInput.clear();
		hmcPage.PunchoutAribaCredential_DomainInput.sendKeys(Domain);
		hmcPage.PunchoutAribaCredential_IdentityInput.clear();
		hmcPage.PunchoutAribaCredential_IdentityInput.sendKeys(Identity);
		hmcPage.PunchoutAribaCredential_SharedSecretInput.clear();
		hmcPage.PunchoutAribaCredential_SharedSecretInput.sendKeys(SharedSecret);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		driver.switchTo().window(mainHandle);
	}

	public void CreateCredential(WebDriver driver, HMCPage hmcPage, String B2BCustomer, String Code, String Domain,
			String Identity, String SharedSecret, String uName, String uPwd) {
		System.out.println("CreateCredential");
		hmcPage.Home_Punchout_Credential.click();
		Common.rightClick(driver, hmcPage.Home_Punchout_Credential);
		hmcPage.Home_CreatePunchoutCredential.click();
		hmcPage.PunchoutCredential_CustomerInput.clear();
		hmcPage.PunchoutCredential_CustomerInput.sendKeys(B2BCustomer);
		driver.findElement(
				By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"
						+ B2BCustomer + "']"))
				.click();
		// Create Credential - Ariba
		AddAribaCredential(driver, hmcPage, Code, Domain, Identity, SharedSecret);

		// Create Credential - OCI
		createCredentialOCI(driver, hmcPage, Code, uName, uPwd);

		// Create Credential - Oracle
		createCredentialOracle(driver, hmcPage, Code, uName, uPwd);
		hmcPage.PunchoutCredential_CreateButton.click();
	}

	public void addCredentialOCI(WebDriver driver, HMCPage hmcPage, String Code, String UserName, String Password) {
		Common.rightClick(driver, hmcPage.PunchoutCredential_OCICredential);
		hmcPage.PunchoutCredential_AddOCICredential.click();
		System.out.println("Add Credential OCI!");
		switchToWindow(1);
		hmcPage.PunchoutOCICredential_CodeSearchInput.clear();
		hmcPage.PunchoutOCICredential_CodeSearchInput.sendKeys(Code);
		hmcPage.Contract_searchbutton.click();
		if (!Common.isElementExist(driver, By.xpath(".//div[contains(@id,'StringDisplay[" + Code + "]')]"))) {
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			switchToWindow(0);
			createCredentialOCI(driver, hmcPage, Code, UserName, Password);
		} else {
			driver.findElement(By.xpath(".//div[contains(@id,'StringDisplay[" + Code + "]')]")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			System.out.println("Add Credential OCI successfully!");
			switchToWindow(0);
		}
	}

	public void createCredentialOCI(WebDriver driver, HMCPage hmcPage, String Code, String UserName, String Password) {
		System.out.println("createCredentialOCI Start");
		Common.rightClick(driver, hmcPage.PunchoutCredential_OCICredential);
		hmcPage.PunchoutCredential_CreatOCICredential.click();
		switchToWindow(1);
		hmcPage.PunchoutOCIOracleCredential_CodeInput.clear();
		hmcPage.PunchoutOCIOracleCredential_CodeInput.sendKeys(Code);
		hmcPage.PunchoutOCICredential_UserNameInput.clear();
		hmcPage.PunchoutOCICredential_UserNameInput.sendKeys(UserName);
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.clear();
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.sendKeys(Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		driver.close();
		switchToWindow(0);
		System.out.println("createCredentialOCI End");
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			WebDriverWait wait = new WebDriverWait(driver, 20);// seconds
			if (windowNo != 0)
				wait.until(ExpectedConditions.numberOfWindowsToBe(windowNo + 1));
			driver.switchTo().window(windows.get(windowNo));
		} catch (TimeoutException e) {
			System.out.println("Swith to window timeout, windowNo: " + windowNo);
		} catch (InterruptedException e) {

		}
	}

	public void createCredentialOracle(WebDriver driver, HMCPage hmcPage, String Code, String Name, String Password) {
		System.out.println("createCredentialOracle Start");
		Common.rightClick(driver, hmcPage.PunchoutCredential_OracleCredential);
		hmcPage.PunchoutCredential_CreatOracleCredential.click();
		Common.sleep(1000);
		switchToWindow(1);
		hmcPage.PunchoutOCIOracleCredential_CodeInput.clear();
		hmcPage.PunchoutOCIOracleCredential_CodeInput.sendKeys(Code);
		hmcPage.PunchoutOracleCredential_NameInput.clear();
		hmcPage.PunchoutOracleCredential_NameInput.sendKeys(Name);
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.clear();
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.sendKeys(Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Credential Oracle successfully!");
		driver.close();
		switchToWindow(0);
		System.out.println("createCredentialOracle End");
	}

	public void addCredentialOracle(WebDriver driver, HMCPage hmcPage, String Code, String Name, String Password) {
		Common.rightClick(driver, hmcPage.PunchoutCredential_OracleCredential);
		hmcPage.PunchoutCredential_AddOracleCredential.click();
		System.out.println("Add Credential Oracle!");
		switchToWindow(1);
		hmcPage.PunchoutOracleCredential_CodeSearchInput.clear();
		hmcPage.PunchoutOracleCredential_CodeSearchInput.sendKeys(Code);
		hmcPage.Contract_searchbutton.click();
		if (!Common.isElementExist(driver, By.xpath(".//div[contains(@id,'StringDisplay[" + Code + "]')]"))) {
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			switchToWindow(0);
			createCredentialOCI(driver, hmcPage, Code, Name, Password);
		} else {
			driver.findElement(By.xpath(".//div[contains(@id,'StringDisplay[" + Code + "]')]")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			System.out.println("Add Credential Oracle successfully!");
			switchToWindow(0);
		}
	}

	public void checkPunchoutProfile(WebDriver driver, HMCPage hmcPage, String ProfileName, String B2BCustomer) {
		hmcPage.Home_Punchout_CustomerProfile.click();
		Select selScope = new Select(hmcPage.PunchoutProfile_SelectProfileNameScope);
		selScope.selectByVisibleText("is equal");
		hmcPage.PunchoutProfile_NameSearch.clear();
		hmcPage.PunchoutProfile_NameSearch.sendKeys(ProfileName);
		hmcPage.Contract_searchbutton.click();
		if (Common.checkElementExists(driver, hmcPage.PunchoutProfile_1stSearchedResult, 10)) {
			Common.rightClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
			hmcPage.PunchoutProfile_removeResult.click();
			driver.switchTo().alert().accept();
			System.out.println("Remove Punchout Profile!");
			creatPunchoutProfile(driver, hmcPage, ProfileName, B2BCustomer);
		} else {
			System.out.println("Don't need to remove Punchout Profile!");
			creatPunchoutProfile(driver, hmcPage, ProfileName, B2BCustomer);
		}
	}

	public void creatPunchoutProfile(WebDriver driver, HMCPage hmcPage, String ProfileName, String B2BCustomer) {
		System.out.println("creatPunchoutProfile Start");
		Common.sleep(1000);
		Common.rightClick(driver, hmcPage.Home_Punchout_CustomerProfile);
		hmcPage.Home_CreatePunchoutProfile.click();
		System.out.println("Create Punchout Profile!");
		hmcPage.PunchoutProfile_NameInput.clear();
		hmcPage.PunchoutProfile_NameInput.sendKeys(ProfileName);

		// B2B Customers :PUNCHOUT-RE
		AddCustomerForProfile(driver, hmcPage, B2BCustomer);

		// Activate Profile :checked
		if (hmcPage.PunchoutProfile_WhetherActive.getAttribute("value").equals("false")) {
			hmcPage.PunchoutProfile_Active.click();
		}

		// Ariba Active :checked CXML profile
		if (hmcPage.PunchoutProfile_WhetherActiveCxml.getAttribute("value").equals("false")) {
			hmcPage.PunchoutProfile_ActiveOxml.click();
		}

		// OCI Active :checked
		hmcPage.PunchoutProfile_OCITab.click();
		if (hmcPage.PunchoutProfile_WhetherActiveOCI.getAttribute("value").equals("false")) {
			hmcPage.PunchoutProfile_ActiveOCI.click();
		}

		// Oracle Active :checked
		hmcPage.PunchoutProfile_OracleTab.click();
		if (hmcPage.PunchoutProfile_WhetherActiveOracle.getAttribute("value").equals("false")) {
			hmcPage.PunchoutProfile_ActiveOracle.click();
		}

		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("creatPunchoutProfile End");
	}

	public void AddCustomerForProfile(WebDriver driver, HMCPage hmcPage, String B2BCustomer) {
		Common.rightClick(driver, hmcPage.PunchoutProfile_CustomerField);
		hmcPage.PunchoutCredential_AddAribaCredential.click();
		System.out.println("Add Customer For Profile!");
		switchToWindow(1);
		hmcPage.PunchoutProfile_CustomerInput.clear();
		hmcPage.PunchoutProfile_CustomerInput.sendKeys(B2BCustomer);
		Common.sleep(1000);
		driver.findElement(By.xpath(
				".//*[@id='AutocompleteReferenceEditor[in GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"
						+ B2BCustomer + "']"))
				.click();
		hmcPage.Contract_searchbutton.click();
		driver.findElement(By.xpath(".//*[contains(@id,'ItemDisplay[" + B2BCustomer + "]')]")).click();
		driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
		System.out.println("Add Customer For Profile successfully!");
		switchToWindow(0);
	}

	public void SimulationToolAriba(WebDriver driver, B2BPunchoutPage punchoutPage, String Domain, String Identity,
			String SharedSecret) {
		driver.manage().deleteAllCookies();
		driver.get(aribaUrl);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to aribaUrl, and checkout punchout successfully!");
		driver.findElement(By.xpath("//input[@id='userName']")).clear();
		driver.findElement(By.xpath("//input[@id='userName']")).sendKeys("lenovopunchouttester");
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("lenovopunchouttester");
		punchoutPage.Ariba_DomainTextBox.clear();
		punchoutPage.Ariba_DomainTextBox.sendKeys(Domain);
		punchoutPage.Ariba_IdentityTextBox.clear();
		punchoutPage.Ariba_IdentityTextBox.sendKeys(Identity);
		punchoutPage.Ariba_SharedSecretTextBox.clear();
		punchoutPage.Ariba_SharedSecretTextBox.sendKeys(SharedSecret);
		punchoutPage.Ariba_PunchoutButton.click();
		Common.sleep(1000);
	}

	public void SimulationToolOCI(WebDriver driver, B2BPunchoutPage punchoutPage, String UserName, String Password) {
		driver.manage().deleteAllCookies();
		driver.get(ociUrl);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to ociUrl, and checkout punchout successfully!");
		punchoutPage.OCI_UserNameTextBox.clear();
		punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
		punchoutPage.OCI_PasswordTextBox.clear();
		punchoutPage.OCI_PasswordTextBox.sendKeys(Password);
		punchoutPage.OCI_PunchoutButton.click();
		Common.sleep(1000);
	}

	public void SimulationToolOracle(WebDriver driver, B2BPunchoutPage punchoutPage, String Name, String Password) {
		driver.manage().deleteAllCookies();
		driver.get(oxmlUrl);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to oxmlUrl, and checkout punchout successfully!");
		String loginRequestText = punchoutPage.Oracle_LoginRequestTextBox.getText().replaceFirst("<username />",
				"<username>" + Name + "</username>");
		loginRequestText = loginRequestText.replaceFirst("<password>welcome</password>",
				"<password>" + Password + "</password>");
		punchoutPage.Oracle_LoginRequestTextBox.clear();
		punchoutPage.Oracle_LoginRequestTextBox.sendKeys(loginRequestText);
		punchoutPage.Oracle_PunchoutButton.click();
		Common.sleep(1000);
	}

	public void confirmHomepageContains(WebDriver driver, B2BPunchoutPage punchoutPage) {
		Boolean isDisplayed;
		By signOut = By.xpath("//li[contains(@class,'signout_menu')]//span[contains(text(),'Sign Out')]");
		By MyAccount = By.xpath("//li[contains(@class,'myaccount_menu')]//*[contains(text(),'My Account')]");
		By blockChangeStore = By.xpath("//div[contains(@class,'dropdown-menu store-selector')]");

		// SHOW 'Exit' button
		Common.sleep(12000);
		isDisplayed = Common.checkElementExists(driver, punchoutPage.Ariba_exit, 5);
		if (!isDisplayed)
			Assert.fail("Exit is not displayed");
		else
			System.out.println("Exit is displayed");

		// Show "Saved Cart"
		isDisplayed = Common.checkElementExists(driver, punchoutPage.Ariba_savedCarts, 5);
		if (!isDisplayed)
			Assert.fail("Saved Cart is not displayed");
		else
			System.out.println("Saved Cart is displayed");

		// HIDE 'Sign Out' button
		isDisplayed = Common.isElementExist(driver, signOut);
		if (isDisplayed)
			Assert.fail("signOut is not hidden");
		else
			System.out.println("signOut is hidden");

		// HIDE 'My Account' button
		isDisplayed = Common.isElementExist(driver, MyAccount);
		if (isDisplayed)
			Assert.fail("My Accoun is not hidden");
		else
			System.out.println("My Account is hidden");

		// HIDE change Sold To (as a punchout user I can't change sites)
		isDisplayed = Common.checkElementExists(driver, punchoutPage.Ariba_blockChangeStore, 5);
		if (!isDisplayed)
			Assert.fail("blockChangeStoren is not hidden");
		else
			System.out.println("blockChangeStore is hidden");

		// if (punchoutPage.Ariba_diffMenu.size() != 1) {
		// for (int i = 0; i < punchoutPage.Ariba_diffMenu.size(); i++)
		// Dailylog.logInfoDB(6, punchoutPage.Ariba_diffMenu.get(i).getText(), Store,
		// testName);
		// Assert.fail("Other buttons display except exit and saved cart!");
		// }
	}

	public void checkExit(WebDriver driver, B2BPunchoutPage punchoutPage, String productNum) {
		// Add one Product to Cart then Click Exit
		punchoutPage.Ariba_CartButton.click();
		Common.sleep(5000);
		punchoutPage.Ariba_QuickOrderProduct.clear();
		punchoutPage.Ariba_QuickOrderProduct.sendKeys(productNum);
		punchoutPage.Ariba_AddButton.click();
		Common.sleep(1000);
		punchoutPage.Ariba_exit.click();
	}

	public void checkCartFunction(WebDriver driver, B2BPage b2bPage, B2BPunchoutPage punchoutPage, String product1,
			String product2, String quantity) {
		boolean isDisplayed;
		// Add two products to cart
		Common.sleep(9000);
		punchoutPage.Ariba_CartButton.click();
		Common.sleep(9000);
		punchoutPage.Ariba_QuickOrderProduct.clear();
		punchoutPage.Ariba_QuickOrderProduct.sendKeys(product1);
		punchoutPage.Ariba_AddButton.click();
		Common.sleep(1000);
		punchoutPage.Ariba_QuickOrderProduct.clear();
		punchoutPage.Ariba_QuickOrderProduct.sendKeys(product2);
		punchoutPage.Ariba_AddButton.click();
		Common.sleep(9000);
		// Assert Two Products in cart.
		List<WebElement> items = driver
				.findElements(By.xpath("//div[@class='cart-item']//p[@class='cart-item-partNumber']/span"));
		if (items.size() != 2) {
			for (int i = 0; i < items.size(); i++) {
				Dailylog.logInfoDB(8, items.get(i).getText(), Store, testName);
			}
			Assert.fail("Incorrect product items in cart");
		} else
			System.out.println("correct product items in cart");
		// remove the first product
		WebElement remove = driver
				.findElement(By.xpath("//span[text()='" + product1 + "']/../..//a[@class='submitRemoveProduct']"));
		remove.click();

		// Assert product deleted
		Common.sleep(1000);
		isDisplayed = Common.isElementExist(driver, By
				.xpath("//div[@class='cart-item']//p[@class='cart-item-partNumber']/span[text()='" + product1 + "']"));
		if (isDisplayed)
			Assert.fail("Product remove error: " + product1);
		else
			System.out.println("Product remove successfully: " + product1);

		// Assert show:"Product has been removed from your basket."
		isDisplayed = Common.checkElementExists(driver,
				driver.findElement(By.xpath("// div[contains(text(),'Product has been removed from your basket.')]")),
				5);
		if (!isDisplayed)
			Assert.fail("mesage is not displayed:Product has been removed from your basket.");
		else
			System.out.println("mesage is displayed:Product has been removed from your basket.");

		// update the Quantity of the second product to 3
		WebElement quantityInput = driver
				.findElement(By.xpath("//span[text()='" + product2 + "']/../../../..//input[@name='quantity']"));
		quantityInput.clear();
		quantityInput.sendKeys(quantity);
		WebElement update = driver
				.findElement(By.xpath("//span[text()='" + product2 + "']/../../../..//input[@value='Update']"));
		update.click();
		System.out.println("quantity updated to 3");

		// Assert The product quantity has been updated to "3".
		Common.sleep(1000);
		isDisplayed = Common.checkElementExists(driver,
				driver.findElement(By.xpath("// div[contains(text(),'Product quantity has been updated.')]")), 5);
		if (!isDisplayed)
			Assert.fail("mesage is not displayed:Product quantity has been updated. ");
		else
			System.out.println("mesage is displayed:Product quantity has been updated.");

		quantityInput = driver
				.findElement(By.xpath("//span[text()='" + product2 + "']/../../../..//input[@name='quantity']"));
		Assert.assertEquals(quantityInput.getAttribute("value"), quantity, "quantity is not updated successfully");

		// Click "LENOVO CHECKOUT" button
		b2bPage.cartPage_LenovoCheckout.click();
	}

	public void checkSavedcart(WebDriver driver, B2BPage b2bPage, B2BPunchoutPage punchoutPage, String product1,
			String product2, String cartName) {
		boolean isDisplayed;
		// Add two products to cart
		punchoutPage.Ariba_CartButton.click();
		punchoutPage.Ariba_QuickOrderProduct.clear();
		punchoutPage.Ariba_QuickOrderProduct.sendKeys(product1);
		punchoutPage.Ariba_AddButton.click();
		Common.sleep(1000);
		punchoutPage.Ariba_QuickOrderProduct.clear();
		punchoutPage.Ariba_QuickOrderProduct.sendKeys(product2);
		punchoutPage.Ariba_AddButton.click();
		Common.sleep(1000);
		// Click "Save cart"
		b2bPage.cartPage_saveCartButton.click();
		// Enter "UXUI-Ariba-1" into "Cart name"
		b2bPage.cartPage_SaveCart_cartNameField.clear();
		b2bPage.cartPage_SaveCart_cartNameField.sendKeys(cartName);
		// Click "Save"
		b2bPage.cartPage_SaveCart_save.click();
		// Assert Show saved cart detail page.
		Common.sleep(1000);
		isDisplayed = Common.checkElementExists(driver,
				driver.findElement(By.xpath("// td[@data-title='Cart Name']/h3[contains(text(),'" + cartName + "')]")),
				10);
		if (!isDisplayed)
			Assert.fail("save cart page is not displayed correctly");

	}
}
