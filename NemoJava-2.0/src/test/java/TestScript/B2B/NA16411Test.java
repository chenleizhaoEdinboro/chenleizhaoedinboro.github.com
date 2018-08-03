package TestScript.B2B;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import CommonFunction.B2BCommon.B2BRole;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA16411Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	String parentUnit = "1213350638";
	String childUnit = "1213581184";
	String DEFAULTVALUE = "default_N/A";
	String INHERITEDINFO = "It is inherited from \"1213350638\"";
	String emailDomain = "@SHARKLASERS.COM";

	public NA16411Test(String store) {
		this.Store = store;
		this.testName = "NA-16411";
	}

	private enum EnumTestItems {
		STORETYPE("storeType", "LE", "select"), FLATRATESHIPPING("flatRateShippingEnabled", "", "radio"), STATUS("status", "", "radio"), ORDERCATEGORY("orderCategory", "ZI5", "input"), ORDERTYPE("orderType", "YBOI", "input"), DISTRIBUTION("distributionChannel", "21", "input"), DIVISION("division", "00", "input"), DELIVERYMODE("deliveryModeAndFlatRate", "standard-net", "table"), PAYERID("paymentTypeAndPayerId", "PURCHASEORDER", "table"), MAILTO("paymentTypeToMailTo", "PURCHASEORDER", "table"), DISPLAYEDCREDITCARDS("displayedCreditCards", "amex", "table"), PAYMENTAUTHORIZATION("paymentAuthSettingList", "Card Payment", "table");

		private String name;
		private String value;
		private String type;

		private EnumTestItems(String name, String value, String type) {
			this.setName(name);
			this.setValue(value);
			this.setType(type);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}

	private void editBlankOutInheritance(String testItem, LinkedHashMap<String, Boolean> map, String action) {
		By xpath = By.xpath("//input[contains(@id,'" + testItem + "') and contains(@id,'checkbox')]");
		WebElement target;
		try {
			target = driver.findElement(xpath);
			map.put(testItem, target.isSelected());// get default blank inheritance
			if ((!target.isSelected() && action.equals("select")) || (target.isSelected() && action.equals("unselect"))) {
				target.click();
			}
		} catch (NoSuchElementException e) {
			Assert.fail("NoSuchElementException: " + xpath.toString());
		}
	}

	private void editBlankOutInheritance(String testItem, String action) {
		By xpath = By.xpath("//input[contains(@id,'" + testItem + "') and contains(@id,'checkbox')]");
		WebElement target;
		try {
			target = driver.findElement(xpath);
			if ((!target.isSelected() && action.equals("select")) || (target.isSelected() && action.equals("unselect"))) {
				target.click();
			}
		} catch (NoSuchElementException e) {
			Assert.fail("NoSuchElementException: " + xpath.toString());
		}
	}

	private void radioHandle(String testItem, LinkedHashMap<String, String> originMap, LinkedHashMap<String, String> currentMap) {
		WebElement selectedRadio = driver.findElement(By.xpath("//input[@type='radio' and contains(@id,'" + testItem + "') and @checked]"));
		String isRadioSelected = selectedRadio.getAttribute("value");
		if (isRadioSelected.equals("")) {
			driver.findElement(By.xpath("//input[@type='radio' and contains(@id,'" + testItem + "') and contains(@id,'true')]")).click();
			originMap.put(testItem, DEFAULTVALUE);
			currentMap.put(testItem, "true");
		} else {
			originMap.put(testItem, isRadioSelected);
			currentMap.put(testItem, isRadioSelected);
		}
	}

	private void radioHandle(String testItem, LinkedHashMap<String, String> currentMap) {
		WebElement selectedRadio = driver.findElement(By.xpath("//input[@type='radio' and contains(@id,'" + testItem + "') and @checked]"));
		String isRadioSelected = selectedRadio.getAttribute("value");
		currentMap.put(testItem, isRadioSelected);
		if (isRadioSelected.equals(""))
			currentMap.put(testItem, DEFAULTVALUE);
	}

	private void radioHandle(String testItem) {
		WebElement selectedRadio = driver.findElement(By.xpath("//input[@type='radio' and contains(@id,'" + testItem + "') and @checked]"));
		String isRadioSelected = selectedRadio.getAttribute("value");
		if (!isRadioSelected.equals(""))
			driver.findElement(By.xpath("//input[@type='radio' and contains(@id,'" + testItem + "') and contains(@id,'null')]")).click();
	}

	private void selectHandle(String testItem, LinkedHashMap<String, String> originMap, String optionText, LinkedHashMap<String, String> currentMap) {
		if (Common.checkElementDisplays(driver, By.xpath("//select[contains(@id,'" + testItem + "')]//option[@selected]"), 3)) {
			originMap.put(testItem, hmcPage.SiteAttribute_storeTypeOption.getText().trim());
			currentMap.put(testItem, hmcPage.SiteAttribute_storeTypeOption.getText().trim());
		} else {
			originMap.put(testItem, DEFAULTVALUE);
			By targetOption = By.xpath("//select[contains(@id,'" + testItem + "')]/option[contains(text(),'" + optionText + "')]");
			try {
				driver.findElement(targetOption).click();
				currentMap.put(testItem, optionText);
			} catch (NoSuchElementException e) {
				Assert.fail("NoSuchElementException: " + testItem + " : " + optionText);
			}
		}
	}

	private void selectHandle(String testItem, LinkedHashMap<String, String> currentMap) {
		if (Common.checkElementDisplays(driver, By.xpath("//select[contains(@id,'" + testItem + "')]//option[@selected]"), 3)) {
			currentMap.put(testItem, hmcPage.SiteAttribute_storeTypeOption.getText().trim());
		} else {
			currentMap.put(testItem, DEFAULTVALUE);
		}
	}

	private void selectHandle(String testItem) {
		if (Common.checkElementDisplays(driver, By.xpath("//select[contains(@id,'" + testItem + "')]//option[@selected]"), 3)) {
			driver.findElement(By.xpath("//select[contains(@id,'" + testItem + "')]//option[@value='-1']")).click();
		}
	}

	private void inputHande(String testItem, LinkedHashMap<String, String> originMap, String inputText, LinkedHashMap<String, String> currentMap) {
		WebElement targetInput = driver.findElement(By.xpath("//input[contains(@id,'" + testItem + "') and contains(@id,'input')]"));
		String defaultValue = targetInput.getAttribute("value");
		if (defaultValue.equals("")) {
			targetInput.clear();
			targetInput.sendKeys(inputText);
			originMap.put(testItem, DEFAULTVALUE);
			currentMap.put(testItem, inputText);
		} else {
			originMap.put(testItem, defaultValue);
			currentMap.put(testItem, defaultValue);
		}
	}

	private void inputHande(String testItem) {
		WebElement targetInput = driver.findElement(By.xpath("//input[contains(@id,'" + testItem + "') and contains(@id,'input')]"));
		String defaultValue = targetInput.getAttribute("value");
		if (!defaultValue.equals(""))
			targetInput.clear();
	}

	private void inputHande(String testItem, LinkedHashMap<String, String> currentMap) {
		WebElement targetInput = driver.findElement(By.xpath("//input[contains(@id,'" + testItem + "') and contains(@id,'input')]"));
		String currentValue = targetInput.getAttribute("value");
		if (!currentValue.equals(""))
			currentMap.put(testItem, currentValue);
		else
			currentMap.put(testItem, DEFAULTVALUE);
	}

	private void tableHandle(String testItem, String newValue, LinkedHashMap<String, String> originMap, LinkedHashMap<String, String> currentMap) {
		WebElement tagetTable = driver.findElement(By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody"));
		By tagetTableItems = By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody//tr[@class]");
		if (Common.checkElementDisplays(driver, tagetTableItems, 5)) {
			if (testItem.equals(EnumTestItems.DISPLAYEDCREDITCARDS.getName()) || testItem.equals(EnumTestItems.PAYMENTAUTHORIZATION.getName())) {
				tagetTableItems = By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody//td[3]/div");
			} else if (testItem.equals(EnumTestItems.DELIVERYMODE.getName()) || testItem.equals(EnumTestItems.PAYERID.getName()) || testItem.equals(EnumTestItems.MAILTO.getName())) {
				tagetTableItems = By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody//tr[@class]");
			} else {
				Assert.fail("testItem not defined: " + testItem);
			}
			List<WebElement> items = driver.findElements(tagetTableItems);
			for (int i = 0; i < items.size(); i++) {
				originMap.put(testItem + i, items.get(i).getText().trim());
				currentMap.put(testItem + i, items.get(i).getText().trim());
			}
		} else {
			Common.rightClick(driver, tagetTable);
			Common.waitElementClickable(driver, hmcPage.HMC_add, 5);
			hmcPage.HMC_add.click();
			Common.switchToWindow(driver, 1);
			hmcPage.SiteAttribute_codeInput.sendKeys(newValue);
			hmcPage.SiteAttribute_codeInput.sendKeys(Keys.ENTER);
			By results = By.xpath("//div[text()='" + newValue + "']");
			driver.findElement(results).click();
			hmcPage.HMC_use.click();
			Common.switchToWindow(driver, 0);
			hmcPage.SaveButton.click();
			originMap.put(testItem, DEFAULTVALUE);
			currentMap.put(testItem, newValue);
		}
	}

	private void tableHandle(String testItem) {
		By tagetTableItems = By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody//tr[@class]//td[1]");
		if (Common.checkElementDisplays(driver, tagetTableItems, 5)) {
			List<WebElement> tagetTableItem = driver.findElements(tagetTableItems);
			for (WebElement ele : tagetTableItem) {
				Common.rightClick(driver, ele);
				Common.waitElementClickable(driver, hmcPage.BaseStore_remove, 10);
				hmcPage.BaseStore_remove.click();
				if (Common.isAlertPresent(driver))
					driver.switchTo().alert().accept();
			}
		}
	}

	private void tableHandle(String testItem, LinkedHashMap<String, String> currentMap) {
		By tagetTableItems = By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody//tr[@class]");
		if (Common.checkElementDisplays(driver, tagetTableItems, 5)) {
			if (testItem.equals(EnumTestItems.DISPLAYEDCREDITCARDS.getName()) || testItem.equals(EnumTestItems.PAYMENTAUTHORIZATION.getName())) {
				tagetTableItems = By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody//td[3]/div");
			} else if (testItem.equals(EnumTestItems.DELIVERYMODE.getName()) || testItem.equals(EnumTestItems.PAYERID.getName()) || testItem.equals(EnumTestItems.MAILTO.getName())) {
				tagetTableItems = By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody//tr[@class]");
			} else {
				Assert.fail("testItem not defined: " + testItem);
			}
			List<WebElement> items = driver.findElements(tagetTableItems);
			for (int i = 0; i < items.size(); i++) {
				currentMap.put(testItem + i, items.get(i).getText().trim());
			}
		} else {
			currentMap.put(testItem, DEFAULTVALUE);
		}

	}

	private String checkInHeritatedInfo(String testItem) {
		By inheritatedInto = By.xpath("//input[contains(@id,'" + testItem + "') and contains(@id,'checkbox')]/..//td[@class='text']");
		boolean isDisplayed = Common.checkElementDisplays(driver, inheritatedInto, 3);
		if (isDisplayed) {
			String txt = driver.findElement(inheritatedInto).getText();
			Dailylog.logInfoDB(0, testItem + " : " + txt, Store, testName);
			return txt;
		} else {
			Dailylog.logInfoDB(0, testItem + " : " + DEFAULTVALUE, Store, testName);
			return DEFAULTVALUE;
		}
	}

	@Test(alwaysRun = true,groups = {"contentgroup", "storemgmt", "p2", "b2b"})
	public void NA16411(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);

			// Step 1~3: go to parent unit
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			searchB2BUnit(hmcPage, parentUnit);
			hmcPage.B2BUnit_siteAttribute.click();

			// Step 4: BlankOutInheritance get default and set to yes
			LinkedHashMap<String, Boolean> parentDefaultInheritance = new LinkedHashMap<String, Boolean>();
			for (EnumTestItems e : EnumTestItems.values()) {
				String testItem = e.getName();
				editBlankOutInheritance(testItem, parentDefaultInheritance, "select");
			}
			hmcPage.SaveButton.click();
			for (Entry<String, Boolean> element : parentDefaultInheritance.entrySet())
				Dailylog.logInfoDB(4, "parentDefaultInheritance: " + element.getKey() + " : " + element.getValue(), Store, testName);

			// Step 4: get default values and set if default is N/A
			LinkedHashMap<String, String> parentDefaultValue = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> parentCurrentValue = new LinkedHashMap<String, String>();
			for (EnumTestItems e : EnumTestItems.values()) {
				String testItem = e.getName();
				String testValue = e.getValue();
				String type = e.getType();
				if (type.equals("select"))
					selectHandle(testItem, parentDefaultValue, testValue, parentCurrentValue);
				else if (type.equals("radio"))
					radioHandle(testItem, parentDefaultValue, parentCurrentValue);
				else if (type.equals("input"))
					inputHande(testItem, parentDefaultValue, testValue, parentCurrentValue);
				else if (type.equals("table"))
					tableHandle(testItem, testValue, parentDefaultValue, parentCurrentValue);
			}
			hmcPage.SaveButton.click();
			for (Entry<String, String> element : parentDefaultValue.entrySet())
				Dailylog.logInfoDB(4, "parentDefaultValue: " + element.getKey() + " : " + element.getValue(), Store, testName);
			for (Entry<String, String> element : parentCurrentValue.entrySet())
				Dailylog.logInfoDB(4, "parentCurrentValue: " + element.getKey() + " : " + element.getValue(), Store, testName);
			hmcPage.hmcHome_hmcSignOut.click();

			// Step 5~6 go to child unit, set BlankOutInheritance yes
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			searchB2BUnit(hmcPage, childUnit);
			hmcPage.B2BUnit_siteAttribute.click();
			for (EnumTestItems e : EnumTestItems.values()) {
				String testItem = e.getName();
				editBlankOutInheritance(testItem, "select");
			}
			hmcPage.SaveButton.click();
			Dailylog.logInfoDB(5, "go to child unit, set BlankOutInheritance yes", Store, testName);

			// Step 5~7 remove all default values
			for (EnumTestItems e : EnumTestItems.values()) {
				String testItem = e.getName();
				String type = e.getType();
				if (type.equals("select"))
					selectHandle(testItem);
				else if (type.equals("radio"))
					radioHandle(testItem);
				else if (type.equals("input"))
					inputHande(testItem);
				else if (type.equals("table"))
					tableHandle(testItem);
			}
			hmcPage.SaveButton.click();
			Dailylog.logInfoDB(7, "go to child unit, remove all default values", Store, testName);

			// Step 8 check value equals N/A
			LinkedHashMap<String, String> childCurrentValue = new LinkedHashMap<String, String>();
			for (EnumTestItems e : EnumTestItems.values()) {
				String testItem = e.getName();
				String type = e.getType();
				if (type.equals("select"))
					selectHandle(testItem, childCurrentValue);
				else if (type.equals("radio"))
					radioHandle(testItem, childCurrentValue);
				else if (type.equals("input"))
					inputHande(testItem, childCurrentValue);
				else if (type.equals("table"))
					tableHandle(testItem, childCurrentValue);
			}
			for (Entry<String, String> element : childCurrentValue.entrySet()) {
				Assert.assertEquals(element.getValue(), DEFAULTVALUE);
			}
			Dailylog.logInfoDB(8, "check value equals N/A", Store, testName);

			// Step 8 check no inherited info
			for (EnumTestItems e : EnumTestItems.values()) {
				Assert.assertEquals(checkInHeritatedInfo(e.getName()), DEFAULTVALUE);
			}
			Dailylog.logInfoDB(8, "check no inherited info", Store, testName);

			// Step 9 cancel the selection of Blank-out Inheritance
			for (EnumTestItems e : EnumTestItems.values()) {
				String testItem = e.getName();
				editBlankOutInheritance(testItem, "unselect");
			}
			hmcPage.SaveButton.click();
			Dailylog.logInfoDB(9, "cancel the selection of Blank-out Inheritance", Store, testName);

			// Step 10 check values equals parent
			childCurrentValue = new LinkedHashMap<String, String>();
			for (EnumTestItems e : EnumTestItems.values()) {
				String testItem = e.getName();
				String type = e.getType();
				if (type.equals("select"))
					selectHandle(testItem, childCurrentValue);
				else if (type.equals("radio"))
					radioHandle(testItem, childCurrentValue);
				else if (type.equals("input"))
					inputHande(testItem, childCurrentValue);
				else if (type.equals("table"))
					tableHandle(testItem, childCurrentValue);
			}
			for (Entry<String, String> element : parentCurrentValue.entrySet()) {
				Assert.assertEquals(childCurrentValue.get(element.getKey()), element.getValue(), element.getKey());
			}

			Dailylog.logInfoDB(10, "check values equals parent", Store, testName);

			// Step 10 check Inherited info
			for (EnumTestItems e : EnumTestItems.values()) {
				Assert.assertEquals(checkInHeritatedInfo(e.getName()), INHERITEDINFO);
			}
			Dailylog.logInfoDB(10, "check Inherited info", Store, testName);

			// Step 11 Registration GateKeeper Toggle: Yes
			hmcPage.SiteAttribute_registrationGatekeeperToggleYes.click();
			hmcPage.SaveButton.click();
			Assert.assertTrue(hmcPage.SiteAttribute_registrationGatekeeperToggleYes.isSelected(), "SiteAttribute_registrationGatekeeperToggleYes not selected");
			Dailylog.logInfoDB(11, "Registration GateKeeper Toggle: Yes", Store, testName);

			// Step 12~13 Currencies tab: currencies :NULL , default currency:N/A
			if (!Common.checkElementDisplays(driver, By.xpath("//table[@title='emailDomainValidation']//input[@value='" + emailDomain + "']"), 3)) {
				Common.rightClick(driver, driver.findElement(By.xpath("//table[@title='emailDomainValidation']//div[@id='resultlist_Content/GenericResortableList']//div[contains(.,'Value')]")));
				hmcPage.B2BUnit_siteAttribute_emailDomainValidationListCreate.click();
				hmcPage.B2BUnit_siteAttribute_emailDomainValidationListInput.sendKeys(emailDomain);
				hmcPage.B2BUnit_Save.click();
				System.out.println("Add value '" + emailDomain + "' into 'Email Domain Validation list'! ");
			}
			hmcPage.hmcHome_hmcSignOut.click();
			String account = "NA16411" + emailDomain;
			String pwd = "1q2w3e4r";
			String testUrl = testData.envData.getHttpsDomain() + "/le/" + parentUnit + "/us/en/" + childUnit + "/login";
			String testUrl1 = testData.envData.getHttpsDomain() + "/le/" + parentUnit + "/us/en/" + childUnit + "/";
			driver.get(testUrl);
			B2BCommon.Login(b2bPage, account, pwd);
			Common.sleep(5000);
			if (driver.getCurrentUrl().contains("login?error=true")) {
				B2BCommon.createAccount(driver, testUrl, childUnit, b2bPage, B2BRole.Buyer, account, Browser);
				HMCCommon.activeAccount(driver, testData, account);
				hmcPage.hmcHome_hmcSignOut.click();
				driver.get(testUrl);
				B2BCommon.Login(b2bPage, account, pwd);
				Common.sleep(5000);
				Assert.assertTrue(!driver.getCurrentUrl().contains("login?error=true"));
			}
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			searchB2BUnit(hmcPage, childUnit);
			hmcPage.B2BUnit_currenciesTab.click();

			// Step 14: access website by URL, will be error after logon: login or register
			Select currency = new Select(hmcPage.B2BUnit_defaultCurrency);
			currency.selectByValue("-1");
			String testItem = "currencies - Currencies";
			tableHandle(testItem);
			hmcPage.SaveButton.click();
			hmcPage.hmcHome_hmcSignOut.click();

			driver.get(testUrl);
			B2BCommon.Login(b2bPage, account, pwd);
			Common.sleep(5000);
			Assert.assertTrue(driver.getCurrentUrl().contains("_spring_security_check"));
			By errorMsg = By.xpath("//*[contains(text(),'currency must not be null')]");
			Assert.assertTrue(Common.checkElementDisplays(driver, errorMsg, 3));

			// Step 15: Back the Currencies value:currencies :USD , default currency:USD
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			searchB2BUnit(hmcPage, childUnit);
			hmcPage.B2BUnit_currenciesTab.click();
			WebElement tagetTable = driver.findElement(By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody"));
			Common.rightClick(driver, tagetTable);
			Common.waitElementClickable(driver, hmcPage.HMC_add, 5);
			hmcPage.HMC_add.click();
			Common.switchToWindow(driver, 1);
			hmcPage.SiteAttribute_codeInput.sendKeys("USD");
			hmcPage.SiteAttribute_codeInput.sendKeys(Keys.ENTER);
			By results = By.xpath("//div[text()='USD']");
			driver.findElement(results).click();
			hmcPage.HMC_use.click();
			Common.switchToWindow(driver, 0);
			hmcPage.SaveButton.click();
			currency = new Select(hmcPage.B2BUnit_defaultCurrency);
			currency.selectByVisibleText("USD");
			hmcPage.SaveButton.click();
			hmcPage.hmcHome_hmcSignOut.click();

			driver.get(testUrl);
			B2BCommon.Login(b2bPage, account, pwd);
			HandleJSpring(driver, testUrl, account, pwd);
			Common.sleep(5000);
			Assert.assertEquals(getUrl(driver.getCurrentUrl()), getUrl(testUrl1));

			// Step 16: roll back parent to default
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			searchB2BUnit(hmcPage, parentUnit);
			hmcPage.B2BUnit_siteAttribute.click();
			for (EnumTestItems e : EnumTestItems.values()) {
				String testItem1 = e.getName();
				String type = e.getType();
				if (!parentDefaultInheritance.get(testItem1))
					editBlankOutInheritance(testItem1, "unselect");

				try {
					if (!parentDefaultInheritance.get(testItem1) || parentDefaultValue.get(testItem1).equals(DEFAULTVALUE)) {
						if (type.equals("select"))
							selectHandle(testItem1);
						else if (type.equals("radio"))
							radioHandle(testItem1);
						else if (type.equals("input"))
							inputHande(testItem1);
						else if (type.equals("table"))
							tableHandle(testItem1);
					}
				} catch (NullPointerException e1) {
					System.out.println("test item does not exit means defaut not na");
				}
			}
			hmcPage.SaveButton.click();
			hmcPage.hmcHome_hmcSignOut.click();
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private String getUrl(String url) {
		return url.replace("https://", "http://").replace("LIeCommerce:M0C0v0n3L!@", "");
	}

	public void searchB2BUnit(HMCPage hmcPage, String unit) {
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		System.out.println("B2BUNIT IS :" + unit);
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(unit);
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
	}
	public void HandleJSpring(WebDriver driver, String testUrl, String account, String pwd) {

		if (driver.getCurrentUrl().contains("j_spring_security_check")) {
			driver.get(testUrl);
			B2BCommon.Login(b2bPage, account, pwd);
		}
	}

}
