package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.B2BPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA15490Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	private String partNum = "";
	private String initalCount = "";
	private String newCount = "";
	private String priceRlueID = "";
	private String ruleName = "FP_" + Common.getDateTimeString();
	private String cartError = "";
	private String pdpError = "";

	public NA15490Test(String store) {
		this.Store = store;
		this.testName = "NA-15490";
	}

	private enum EnumTestData {
		country, errMsg;
	}

	private String getData(String store, EnumTestData dataName) {
		if (store.equals("AU")) {
			switch (dataName) {
			case country:
				return "Australia";
			case errMsg:
				return "was removed from your cart as it is no longer available.";
			default:
				return null;
			}
		} else if (store.equals("US")) {
			switch (dataName) {
			case country:
				return "United States";
			case errMsg:
				return "was removed from your cart as it is no longer available.";
			default:
				return null;
			}
		} else if (store.equals("JP")) {
			switch (dataName) {
			case country:
				return "Japan";
			case errMsg:
				return "was removed from your cart as it is no longer available.";
			default:
				return null;
			}
		} else {
			return null;
		}
	}

	@Test(priority = 0,alwaysRun = true, groups = { "shopgroup", "pricingpromot", "p1", "b2b" })
	public void NA15490(ITestContext ctx) {

		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			String plpURL;
			String pdpURL;

			// Login B2B store and find an agreement product
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
			HandleJSpring(driver);
			b2bPage.HomePage_productsLink.click();
			driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")).click();
			b2bPage.Laptops_contractAgreementFilter.click();
			Thread.sleep(1000);
			System.out.println("Click Laptops_contractAgreementFilter");
			if (!Common.isElementExist(driver, By.xpath("(//label[contains(.,'Agreement')]/input)[last()]"))) {
				cartError = "No agreement product";
				pdpError = "No agreement product";
				System.out.println("No agreement product");
				// setManualValidateLog("No Agreement product in this region!");
			} else {
				String[] segments;
				segments = b2bPage.Laptops_agreementTxt.getText().split("\\(");
				segments = segments[1].split("\\)");
				initalCount = segments[0];
				System.out.println("Initial agreement products count is: " + initalCount);
				b2bPage.Laptops_agreementChk.click();
				Thread.sleep(1000);
				// get PLP url
				plpURL = driver.getCurrentUrl();
				System.out.println("PLP URL: " + plpURL);

				// get PDP url of the second product
				int viewDetailsNo = b2bPage.PLPPage_viewDetails.size();
				for (int i = 1; i <= viewDetailsNo; i++) {
					driver.findElement(By.xpath("(.//a[contains(.,'View Details')])[" + i + "]")).click();
					if (isAlertPresent()) {
						System.out.println(driver.switchTo().alert().getText() + " Try next product!");
						driver.switchTo().alert().accept();
						driver.get(plpURL);
					} else if (driver.getTitle().contains("Not Found")) {
						System.out.println("Product Not Found, Try next product!");
						driver.get(plpURL);
					} else if (Common.isElementExist(driver, By.xpath("//div/button[contains(@class,'add-to-cart') and @disabled='disabled']/span[contains(.,'Add to cart')]"))) {
						System.out.println("Add to cart not clickable, Try next product!");
						driver.get(plpURL);
					} else
						break;

				}
				// get PDP url of the second product
				pdpURL = driver.getCurrentUrl();
				System.out.println("PDP URL: " + pdpURL);
				// get product number
				partNum = pdpURL.substring(pdpURL.lastIndexOf('/') + 1, pdpURL.length());
				System.out.println("Product Number: " + partNum);
				Thread.sleep(1000);
				if (Common.isElementExist(driver, By.xpath("//*[@id='b_alert_add_to_cart' or @id='b_marning_add_to_cart']"))) {
					Thread.sleep(500);
					if (b2bPage.PDPPage_agreementAddToCartOnPopup.isDisplayed())
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2bPage.PDPPage_agreementAddToCartOnPopup);
					else {
						b2bPage.Agreement_agreementsAddToCart.click();
						b2bPage.HomePage_CartIcon.click();
					}
				} else {
					// add product into cart
					b2bPage.Agreement_agreementsAddToCart.click();
					b2bPage.HomePage_CartIcon.click();
				}
				// open HMC in a new tab --- second tab
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("window.open('" + testData.HMC.getHomePageUrl() + "')");
				switchToWindow(1);
				HMCCommon.Login(hmcPage, testData);
				// add loginID as approver in HMC
				addApprover();
				// HMC -> Price Settings -> Pricing Cockpit -> B2B PriceRules'
				hmcPage.Home_PriceSettings.click();
				hmcPage.Home_PricingCockpit.click();
				driver.switchTo().frame(0);
				// loginPricingCockpit();
				// reject all pending rules of the product
				rejectAllItems(partNum);
				// create floor price rules
				// createRule(partNum, ruleName);
				priceRlueID = createRule(partNum, ruleName, ctx);
				// approve floor price rule
				hmcPage.PricingCockpit_dashboard.click();
				// Common.waitElementClickable(driver,
				// driver.findElement(By.xpath("//td[text()='" + partNum + "']/..//input")),
				// 18000);
				Common.sleep(8000);
				driver.findElement(By.xpath("//td[text()='" + partNum + "']/..//input")).click();
				hmcPage.Dashoboard_approveBtn.click();
				// Run the solr index for the B2B website
				hotUpdate(partNum);
				// Clear price cache
				startCronJob("NemoClusterClearPriceCacheCronJob");
				// Clear redis cache
				rediscacheclean(partNum);
				// Check the product in B2B
				// cart page
				switchToWindow(0);
				driver.navigate().refresh();
				Thread.sleep(5000);
				if (Common.isElementExist(driver, By.xpath("//*[@id='mainContent']/div[1]"))) {
					System.out.println("Cart errMsg: " + driver.findElement(By.xpath("//*[@id='mainContent']/div[1]")).getText());
					cartError = driver.findElement(By.xpath("//*[@id='mainContent']/div[1]")).getText();
				} else {
					setManualValidateLog("No expected error msg in cart: " + partNum);
				}

				// Assert.assertTrue(driver
				// .findElement(By.xpath("//*[@id='mainContent']/div[1]"))
				// .getText()
				// .indexOf(getData(testData.Store, EnumTestData.errMsg)) > 0);
				// System.out.println("Cart checkout disabled: "
				// + driver.findElement(By.xpath(".//*[@id='quote_button']"))
				// .getAttribute("disabled"));
				// Assert.assertEquals(
				// driver.findElement(By.xpath(".//*[@id='quote_button']"))
				// .getAttribute("disabled"), "true");
				// PDP page
				driver.get(pdpURL);
				Thread.sleep(1000);
				if (isAlertPresent()) {
					System.out.println(driver.switchTo().alert().getText());
					driver.switchTo().alert().accept();
				}
				if (Common.isElementExist(driver, By.xpath(".//*[@id='message']"))) {
					System.out.println("PDP errMsg:" + driver.findElement(By.xpath(".//*[@id='message']")).getText());
					pdpError = driver.findElement(By.xpath(".//*[@id='message']")).getText();
				} else {
					setManualValidateLog("No expected error msg in PDP: " + partNum);
				}
				// Assert.assertTrue(driver
				// .findElement(By.xpath(".//*[@id='message']")).getText()
				// .indexOf(partNum + "does not exist") > 0);
				// PLP page
				driver.get(plpURL);
				Thread.sleep(1000);
				if (Common.checkElementDisplays(driver, By.xpath("(.//label[contains(.,'Agreement')])[last()]"), 10)) {
					segments = b2bPage.Laptops_agreementTxt.getText().split("\\(");
					segments = segments[1].split("\\)");
					newCount = segments[0];
					System.out.println("PLP inital agreement count: " + initalCount);
					System.out.println("PLP final agreement count: " + newCount);
					if (Integer.parseInt(initalCount) - 1 != Integer.parseInt(newCount)) {
						setManualValidateLog("PLP inital agreement count: " + initalCount);
						setManualValidateLog("PLP final agreement count: " + newCount);
					}
				}
				// Assert.assertEquals(Integer.parseInt(initalCount) - 1,
				// Integer.parseInt(newCount), "PLP page agreement count");

			}
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void deleteRule(String ruleName) throws InterruptedException {
		// try {
		ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
		System.out.println(windows.size());
		switchToWindow(windows.size() - 1);
		System.out.println("Deleting floor price rule: " + ruleName);
		driver.get(testData.HMC.getHomePageUrl());
		driver.manage().deleteAllCookies();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_PriceSettings.click();
		hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(0);
		// loginPricingCockpit();
		hmcPage.PricingCockpit_b2bPriceRules.click();
		WebElement deleteBtn = driver.findElement(By.xpath("//td[text()='" + ruleName + "']/..//a[contains(text(),'Delete')]"));
		Common.waitElementClickable(driver, deleteBtn, 30);
		deleteBtn.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_deleteInput);
		hmcPage.B2CPriceRules_deleteInput.clear();
		hmcPage.B2CPriceRules_deleteInput.sendKeys("DELETE");
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2CPriceRules_deleteConfirm);
		// hmcPage.B2CPriceRules_deleteConfirm.click();
		System.out.println("Rule Deleted! Rule Name:" + ruleName);
		driver.switchTo().defaultContent();
		hmcPage.Home_PriceSettings.click();
		System.out.println("clicked price settings");

		// } catch (Throwable e) {
		// handleThrowable(e, ctx);
		// }
	}

	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addApprover() {
		HMCSearchB2BUnit();
		// click approver tab
		driver.findElement(By.xpath(".//a[contains(@id,'B2BUnit.tab.b2bunit.approver')]")).click();
		boolean isApproverExist = Common.isElementExist(driver, By.xpath("(//*[contains(text(),'Lenovo Price Approvers:')]/../..//div[contains(@id,'" + testData.HMC.getLoginId() + "')])[1]"));
		if (!isApproverExist) {
			Common.rightClick(driver, hmcPage.B2BUnit_priceApprovers);
			driver.findElement(By.xpath(".//*[contains(@id,'add_true_label')]")).click();
			switchToWindow(2);
			driver.findElement(By.xpath(".//input[contains(@id,'User.uid')]")).clear();
			driver.findElement(By.xpath(".//input[contains(@id,'User.uid')]")).sendKeys(testData.HMC.getLoginId());
			driver.findElement(By.xpath(".//*[contains(@id,'searchbutton')]")).click();
			driver.findElement(By.xpath("(.//div[contains(@id,'" + testData.HMC.getLoginId() + "')])[1]")).click();
			driver.findElement(By.xpath(".//*[contains(@id,'use')]")).click();
			switchToWindow(1);
			driver.findElement(By.xpath(".//a[contains(@id,'save.title')]")).click();
			driver.findElement(By.xpath(".//a[contains(@id,'reset')]")).click();
			driver.switchTo().alert().accept();
			isApproverExist = Common.isElementExist(driver, By.xpath("(//*[contains(text(),'Lenovo Price Approvers:')]/../..//div[contains(@id,'" + testData.HMC.getLoginId() + "')])[1]"));
			Assert.assertTrue(isApproverExist, "is Approver Exist");
			hmcPage.Home_B2BCommerceLink.click();
		}
	}

	public void HMCSearchB2BUnit() {
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BUnitLink.click();
		hmcPage.B2BUnit_SearchIDTextBox.clear();
		System.out.println("B2BUNIT IS :" + testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchIDTextBox.sendKeys(testData.B2B.getB2BUnit());
		hmcPage.B2BUnit_SearchButton.click();
		hmcPage.B2BUnit_ResultItem.click();
	}

	public void loginPricingCockpit() {
		if (Common.isElementExist(driver, By.xpath("//div[1]/input[@name='j_username']"))) {
			hmcPage.PricingCockpit_username.clear();
			hmcPage.PricingCockpit_username.sendKeys(testData.HMC.getLoginId());
			hmcPage.PricingCockpit_password.clear();
			hmcPage.PricingCockpit_password.sendKeys(testData.HMC.getPassword());
			hmcPage.PricingCockpit_Login.click();
		}

	}

	public void rejectAllItems(String partNum) throws InterruptedException {
		// try {
		hmcPage.PricingCockpit_dashboard.click();
		Thread.sleep(500);
		List<WebElement> allItems = driver.findElements(By.xpath("//td[text()='" + partNum + "']/..//input"));
		if (allItems.size() > 0) {
			for (int i = 0; i < allItems.size(); i++) {
				allItems.get(i).click();
			}
			Thread.sleep(500);
			driver.findElement(By.xpath(".//*[@id='btnReject']")).click();
		}
		driver.switchTo().defaultContent();
		hmcPage.Home_PriceSettings.click();
		// } catch (Throwable e) {
		// handleThrowable(e, ctx);
		// }
	}

	public void fillRuleInfo(String name, String dataInput, WebElement ele1, String xpath) throws InterruptedException {
		// try {
		WebElement target;
		Common.waitElementClickable(driver, ele1, 30);
		ele1.click();
		Common.waitElementVisible(driver, hmcPage.B2BPriceRules_SearchInput);
		hmcPage.B2BPriceRules_SearchInput.clear();
		hmcPage.B2BPriceRules_SearchInput.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.waitElementVisible(driver, target);
		target.click();
		System.out.println(name + ": " + dataInput);
		Thread.sleep(5000);

		// } catch (Throwable e) {
		// handleThrowable(e, ctx);
		// }

	}

	public String createRule(String testProduct, String ruleName, ITestContext ctx) {
		try {
			String dataInput;
			String xpath;
			hmcPage.Home_PriceSettings.click();
			hmcPage.Home_PricingCockpit.click();
			driver.switchTo().frame(0);
			// loginPricingCockpit();
			Common.sleep(8000);
			hmcPage.PricingCockpit_b2bPriceRules.click();
			Common.sleep(8000);
			// Common.waitElementClickable(driver, hmcPage.B2BPriceRules_CreateNewGroup,
			// 12000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2BPriceRules_CreateNewGroup);
			System.out.println("B2BPriceRules_CreateNewGroup");
			Common.waitElementClickable(driver, hmcPage.B2BPriceRules_SelectGroupType, 12000);
			Thread.sleep(1000);
			hmcPage.B2BPriceRules_SelectGroupType.click();
			System.out.println("B2BPriceRules_SelectGroupType");
			hmcPage.B2BPriceRules_FloorPriceOption.click();
			System.out.println("B2BPriceRules_FloorPriceOption");
			// hmcPage.B2BPriceRules_Continue.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2BPriceRules_Continue);
			System.out.println("B2BPriceRules_Continue");

			// Floor price name
			hmcPage.B2BPriceRules_PriceRuleName.clear();
			hmcPage.B2BPriceRules_PriceRuleName.sendKeys(ruleName);

			// Validate from date
			hmcPage.B2BPriceRules_ValidFrom.click();
			Thread.sleep(1000);
			int count = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).size();
			WebElement yesterday;
			if (count > 0) {
				yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/preceding-sibling::*")).get(count - 1);
				System.out.println("Valid From: " + yesterday.getText());
				yesterday.click();
				hmcPage.B2BPriceRules_ValidFrom.sendKeys(Keys.ENTER);
			} else {
				yesterday = driver.findElements(By.xpath("//td[contains(@class,'today')]/../preceding-sibling::tr/td")).get(count - 1);
				System.out.println("Valid From: " + yesterday.getText());
				yesterday.click();
				hmcPage.B2BPriceRules_ValidFrom.sendKeys(Keys.ENTER);
			}

			// Country
			dataInput = getData(testData.Store, EnumTestData.country);
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
			fillRuleInfo("Country", dataInput, hmcPage.B2BPriceRules_CountrySelect, xpath);

			// Catalog
			dataInput = "Nemo Master Multi Country Product Catalog - Online";
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
			fillRuleInfo("Catalog", dataInput, hmcPage.B2BPriceRules_CatalogSelect, xpath);

			// Material
			xpath = "//span[contains(text(),'" + testProduct + "')]";
			fillRuleInfo("Material", testProduct, hmcPage.B2BPriceRules_MaterialSelect, xpath);

			// B2Bunit
			dataInput = testData.B2B.getB2BUnit();
			xpath = "//div[@role='option']";
			fillRuleInfo("B2Bunit", dataInput, hmcPage.B2BPriceRules_B2BunitSelect, xpath);

			// Approver
			dataInput = testData.HMC.getLoginId();
			xpath = "//span[text()='" + dataInput + "' and @class='select2-match']";
			fillRuleInfo("Approver", dataInput, hmcPage.B2BPriceRules_ApproverSelect, xpath);

			// Floor price Value
			dataInput = "500000";
			hmcPage.B2BPriceRules_PriceValue.clear();
			hmcPage.B2BPriceRules_PriceValue.sendKeys(dataInput);
			System.out.println("Floor Price:" + dataInput);
			Thread.sleep(2000);

			// Add Price Rule To Group
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2BPriceRules_AddPriceRuleToGroup);
			// hmcPage.B2BPriceRules_AddPriceRuleToGroup.click();
			System.out.println("click Add Price Rule To Group --- first time");
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", hmcPage.B2BPriceRules_CreateGroup);
			// hmcPage.B2BPriceRules_CreateGroup.click();
			System.out.println("click Create New Group --- first time");
			Thread.sleep(1000);
			if (Common.isElementExist(driver, By.xpath(".//*[@data-validation='You need to add at least one Rule to create Group!']"))) {
				hmcPage.B2BPriceRules_AddPriceRuleToGroup.click();
				System.out.println("click Add Price Rule To Group --- second time");
				hmcPage.B2BPriceRules_CreateGroup.click();
				System.out.println("click Create New Group --- second time");
			}
			Thread.sleep(1000);

			// Record Price Rule ID
			if (Common.isElementExist(driver, By.xpath("//div[@class='modal-footer']//button[text()='Close']"))) {
				hmcPage.B2BPriceRules_CloseBtn.click();
				System.out.println("Clicked close!");
			}
			Thread.sleep(1000);
			WebElement filter = driver.findElement(By.xpath("//button[text()='Filter']"));
			Common.waitElementClickable(driver, filter, 10000);
			filter.click();
			System.out.println("click filter");
			Thread.sleep(1000);
			WebElement showRuleID = driver.findElement(By.xpath("//td[text()='" + ruleName + "']/..//span[text()='Show']"));
			Common.waitElementClickable(driver, showRuleID, 10000);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", showRuleID);
			Thread.sleep(1000);
			String priceRlueID = driver.findElement(By.xpath("(//td[text()='" + ruleName + "']/../../../..//tbody/tr/td[1])[2]")).getText();
			System.out.println("Price Rule ID is : " + priceRlueID);
			return priceRlueID;

		} catch (Throwable e) {
			handleThrowable(e, ctx);
			return null;
		}
	}

	public void hotUpdate(String testProduct) throws InterruptedException {
		// try {
		System.out.println("hot update start");
		driver.switchTo().defaultContent();
		// HMC -> System -> Facet search -> Indexer hot-update wizard :Solr
		// job
		hmcPage.Home_System.click();
		hmcPage.Home_facetSearch.click();
		hmcPage.Home_indexerHotUpdWiz.click();
		// Set solr facet search configuration
		switchToWindow(2);
		hmcPage.IndexerHotUpdate_solrConfigName.click();
		hmcPage.IndexerHotUpdate_mcnemob2bIndex.click();
		hmcPage.IndexerHotUpdate_nextBtn.click();
		hmcPage.IndexerHotUpdate_indexTyeDD.click();
		hmcPage.IndexerHotUpdate_productIndexType.click();
		hmcPage.IndexerHotUpdate_updateIndexRadioBtn.click();
		hmcPage.IndexerHotUpdate_nextBtn.click();
		Common.rightClick(driver, hmcPage.IndexerHotUpdate_emptyRowToAddProduct);
		hmcPage.IndexerHotUpdate_addProductOption.click();
		switchToWindow(3);
		// product number
		hmcPage.IndexerHotUpdate_articleNumber.sendKeys(testProduct);
		// catalog version
		hmcPage.IndexerHotUpdate_catalogSelect.click();
		hmcPage.IndexerHotUpdate_multiCountryOption.click();
		hmcPage.IndexerHotUpdate_searchBtn.click();
		Common.doubleClick(driver, hmcPage.IndexerHotUpdate_searchResult);
		switchToWindow(2);
		// Assert.assertEquals(hmcPage.IndexerHotUpdate_articleNum.getText(),
		// testProduct);
		hmcPage.IndexerHotUpdate_startJobBtn.click();
		// System.out.println("Clicked on the start button to start the index update
		// job!!");
		Thread.sleep(10000);
		// System.out.println("waited 240000");
		new WebDriverWait(driver, 240000).until(ExpectedConditions.visibilityOf(hmcPage.IndexerHotUpdate_indexSuccessMsgBox));
		Assert.assertEquals(hmcPage.IndexerHotUpdate_indexSuccessMsgBox.getText(), "Indexing finished successfully.");
		hmcPage.IndexerHotUpdate_doneBtn.click();
		// System.out.println("Solr Job Done!!");
		switchToWindow(1);
		hmcPage.Home_facetSearch.click();
		hmcPage.Home_System.click();
		System.out.println("hot update end");
		// } catch (Throwable e) {
		// handleThrowable(e, ctx);
		// }
	}

	public void startCronJob(String jobName) throws InterruptedException {
		// try {
		System.out.println("CronJob start");
		// naviagting to System > CronJob > singleCouponJob
		hmcPage.Home_System.click();
		hmcPage.Home_cronJob.click();
		if (!Common.isElementExist(driver, By.xpath("//a/span[contains(.,'Search')]"))) {
			driver.findElement(By.xpath("(//*[contains(@id,'[organizersearch][CronJob]')])[1]")).click();
			driver.findElement(By.xpath("(//*[contains(@id,'[organizerlist][CronJob]')])[1]")).click();
		}
		// Jobname
		hmcPage.CronJob_jobName.clear();
		hmcPage.CronJob_jobName.sendKeys(jobName);
		hmcPage.CronJob_searchButton.click();
		// Selecting the Job From Search Results
		Common.waitElementVisible(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
		Common.doubleClick(driver, driver.findElement(By.xpath("//div[text()='" + jobName + "']")));
		// Start CronJob
		hmcPage.CronJob_startCronJob.click();
		Thread.sleep(5000);
		switchToWindow(2);
		Assert.assertEquals(hmcPage.CronJob_cronJobSuccessMsg.getText(), "CronJob performed.");
		driver.close();
		switchToWindow(1);
		hmcPage.Home_System.click();
		// // Checking Job Status
		// hmcPage.CronJob_taskTab.click();
		// hmcPage.CronJob_reloadButton.click();
		// Thread.sleep(1000);
		// driver.switchTo().alert().accept();
		// Thread.sleep(3000);
		// Assert.assertEquals(hmcPage.CronJob_status, "FINISHED");
		System.out.println("CronJob end");
		// } catch (Throwable e) {
		// handleThrowable(e, ctx);
		// }
	}

	public void rediscacheclean(String testProduct) throws InterruptedException {
		// try {
		System.out.println("rediscacheclean start");
		hmcPage.Home_System.click();
		hmcPage.Home_rediscacheclean.click();
		driver.switchTo().frame(0);
		hmcPage.Rediscacheclean_productCode.sendKeys(testProduct);
		hmcPage.Rediscacheclean_clean.click();
		// System.out.println("Cleaned the product cache.");
		Thread.sleep(10000);
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
		hmcPage.Home_System.click();
		System.out.println("rediscacheclean end");
		// } catch (Throwable e) {
		// handleThrowable(e, ctx);
		// }
	}

	public boolean isAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);// seconds
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	@Test(priority = 1,alwaysRun = true, groups = { "shopgroup", "pricingpromot", "p1", "b2b" })
	public void rollback() throws InterruptedException {
		System.out.println("rollback"); // roll back
		if (!priceRlueID.equals("")) {
			if (isAlertPresent()) {
				System.out.println(driver.switchTo().alert().getText());
				driver.switchTo().alert().accept();
			}
			// delete price rule
			deleteRule(ruleName);
			// Run the solr index for the B2B website
			hotUpdate(partNum);
			// Clear price cache
			startCronJob("NemoClusterClearPriceCacheCronJob");
			// Clear redis cache
			rediscacheclean(partNum);
		}

		Assert.assertTrue(cartError.indexOf(getData(testData.Store, EnumTestData.errMsg)) > 0 || cartError.equals("No agreement product"), "is floor price msg displayed in cart");

		Assert.assertTrue(pdpError.indexOf(partNum + "does not exist") > 0 || pdpError.equals("No agreement product"), "is the product exist after floor price rule");

	}

	public void HandleJSpring(WebDriver driver) {

		if (driver.getCurrentUrl().contains("j_spring_security_check")) {
			driver.get(testData.B2B.getLoginUrl());
			B2BCommon.Login(b2bPage, testData.B2B.getBuyerId(), testData.B2B.getDefaultPassword());
		}
	}
}
