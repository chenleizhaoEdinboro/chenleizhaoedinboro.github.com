package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA27196Test extends SuperTestClass {
	public HMCPage hmcPage;

	public NA27196Test(String store) {
		this.Store = store;
		this.testName = "NA-27196";
	}

	@Test(alwaysRun = true,groups = {"contentgroup", "storemgmt", "p2", "b2b"})
	public void NA27196(ITestContext ctx) {

		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);

			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2BUnit(hmcPage, testData);
			hmcPage.B2BUnit_siteAttribute.click();
			String tableTitle = "needApproveUserRoles";
			editBlankOutInheritance(tableTitle, "select");
			removeTableItems(tableTitle);
			String[] tableItems = new String[] { "buyerDefault", "browserDefault", "approverDefault", "builderDefault", "customAdminControlled" };
			addTableItems(tableTitle, tableItems);
			editBlankOutInheritance(tableTitle, "unselect");

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void removeTableItems(String testItem) {
		By tagetTableItems = By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody//tr[@class]//td[1]");
		if (Common.checkElementDisplays(driver, tagetTableItems, 5)) {
			List<WebElement> tagetTableItem = driver.findElements(tagetTableItems);
			int size = tagetTableItem.size();
			for (int i = 0; i < size; i++) {
				tagetTableItem = driver.findElements(tagetTableItems);
				Common.rightClick(driver, tagetTableItem.get(0));
				Common.waitElementClickable(driver, hmcPage.BaseStore_remove, 10);
				hmcPage.BaseStore_remove.click();
				if (Common.isAlertPresent(driver))
					driver.switchTo().alert().accept();
			}
		}
		hmcPage.SaveButton.click();
		hmcPage.Template_reloadBtn.click();
		if (Common.isAlertPresent(driver))
			driver.switchTo().alert().accept();
		Assert.assertTrue(!Common.checkElementDisplays(driver, tagetTableItems, 5), "tableItems should be removed");
	}

	private void addTableItems(String testItem, String[] tableItems) {
		WebElement tagetTable = driver.findElement(By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody"));
		Common.rightClick(driver, tagetTable);
		Common.waitElementClickable(driver, hmcPage.HMC_add, 5);
		hmcPage.HMC_add.click();
		switchToWindow(driver, 1);
		Actions a = new Actions(driver);
		a.keyDown(Keys.CONTROL).perform();
		for (String newValue : tableItems) {
			By results = By.xpath("//div[text()='" + newValue + "']");
			driver.findElement(results).click();
		}
		a.keyUp(Keys.CONTROL).perform();
		hmcPage.HMC_use.click();
		switchToWindow(driver, 0);
		hmcPage.SaveButton.click();
		hmcPage.Template_reloadBtn.click();
		if (Common.isAlertPresent(driver))
			driver.switchTo().alert().accept();
		for (String newValue : tableItems) {
			By tagetTableItems = By.xpath("//table[@title='" + testItem + "']//table[@class='listtable selecttable']//tbody//tr[@class]//td[contains(text(),'" + newValue + "')]");
			Assert.assertTrue(Common.checkElementDisplays(driver, tagetTableItems, 5), "testItems should be added: " + newValue);
		}
	}

	private void editBlankOutInheritance(String testItem, String action) {
		By xpath = By.xpath("//input[contains(@id,'" + testItem + "') and contains(@id,'checkbox')]");
		WebElement target;
		try {
			target = driver.findElement(xpath);
			if ((!target.isSelected() && action.equals("select")) || (target.isSelected() && action.equals("unselect"))) {
				target.click();
				hmcPage.SaveButton.click();
			}
		} catch (NoSuchElementException e) {
			Assert.fail("NoSuchElementException: " + xpath.toString());
		}
	}
	
	private void switchToWindow(WebDriver driver, int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
			WebDriverWait wait = new WebDriverWait(driver, 20);// seconds
			if (windowNo != 0)
				wait.until(ExpectedConditions.numberOfWindowsToBe(windowNo + 1));
			driver.switchTo().window(windows.get(windowNo));
		} catch (TimeoutException e) {
			System.out.println("Swith to window timeout, window: " + windowNo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
