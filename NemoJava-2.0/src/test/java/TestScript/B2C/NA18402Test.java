package TestScript.B2C;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA18402Test extends SuperTestClass {

	public B2CPage b2cPage;
	public HMCPage hmcPage;
	private String faqNumber;
	private String b2cHomeURL;
	private String id;
	private String name;
	private String body;
	private String checkbody;
	private String hmcHomeURL;

	public NA18402Test(String store,String faqNumber,String id,String name,String body,String checkbody) {
		this.Store = store;
		this.testName = "NA-18402";
		this.faqNumber = faqNumber;
		this.id = id;
		this.name = name;
		this.body = body;
		this.checkbody = checkbody;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA18402(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			b2cHomeURL = testData.B2C.getHomePageUrl();
		    hmcHomeURL = testData.HMC.getHomePageUrl();

			// Step~1  Login HMC and navigate website flowing below steps: Hmc->Wcms -> Pages 
			driver.get(hmcHomeURL);
			HMCCommon.Login(hmcPage, testData);
			Common.sleep(3000);
			
			setMiddleTep();
			
			// Step~5  Check if it's empty in 'CSS JS Override Components'list.
			Common.sleep(3000);
			if(hmcPage.Wcms_csslist.getText().equals("The list is empty.")){
				Dailylog.logInfoDB(5, "Check if it's empty in 'CSS JS Override Components'list.", Store, testName);
				setTep6();
			}else {
				// Step~8  Check the css in Web:
				Dailylog.logInfoDB(5, "Check if it's empty in 'CSS JS Override Components'list.", Store, testName);
				setTep8();
			}
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private void setMiddleTep() throws InterruptedException {
		hmcPage.hmcHome_WCMS.click();
		hmcPage.wcmsPage_pages.click();
		Dailylog.logInfoDB(1, "Logged into HMC", Store, testName);
		
		// Step~2  Search:ID field 'faq' catalogversion field 'aucontentcatalog-online'
		Common.sleep(2000);
		hmcPage.Wcms_page_id.clear();
		hmcPage.Wcms_page_id.sendKeys(faqNumber);
		hmcPage.Wcms_category.click();
		hmcPage.wcmsPagesPage_searchButton.click();
		Common.sleep(3000);
		Assert.assertTrue(Common.checkElementDisplays(driver, hmcPage.Wcms_faq, 5));
		Dailylog.logInfoDB(2, "ID field 'faq' catalogversion field 'aucontentcatalog-online", Store, testName);
		
		// Step~3  Edit the Frequently Asked Questions page located in result list.
		Common.rightClick(driver, hmcPage.Wcms_faq);
		Common.sleep(1000);
		hmcPage.products_PB_edit.click();
		Dailylog.logInfoDB(3, "Edit the Frequently Asked Questions page located in result list.", Store, testName);
		
		// Step~4  Go to Administration tab and find the "css js override component" list 
		Common.sleep(3000);
		hmcPage.Product_admin.click();
		Common.scrollToElement(driver, hmcPage.Wcms_css);
		Dailylog.logInfoDB(4, "Go to Administration tab and find the css js override component list ", Store, testName);
	}

	private void setTep6() throws InterruptedException {
		// Step~6  Create a new css component:
		Common.sleep(3000);
		Common.scrollToElement(driver, hmcPage.hmcHome_WCMS);
        Common.rightClick(driver, hmcPage.Wcms_component);
        Common.mouseHover(driver, hmcPage.Component_create);
        hmcPage.Wcms_cssjsCreate.click();
        Common.sleep(3000);
        Common.scrollToElement(driver, hmcPage.Wcms_id);
        hmcPage.Wcms_id.clear();
        hmcPage.Wcms_id.sendKeys(id);
        hmcPage.Wcms_name.clear();
        hmcPage.Wcms_name.sendKeys(name);
        hmcPage.Wcms_category_version.click();
        Common.sleep(2000);
        hmcPage.Wcms_css_change.click();
        hmcPage.Wcms_body.clear();
        hmcPage.Wcms_body.sendKeys(body);
        hmcPage.Wcms_position.click();
        hmcPage.Component_save.click();
        Dailylog.logInfoDB(6, "Create a new css component:", Store, testName);
        
     // Step~7  Add new created css component to Frequently Asked Questions page:
        Common.sleep(3000);
//        setMiddleTep();
        Common.javascriptClick(driver, hmcPage.wcmsPage_pages);
//        hmcPage.wcmsPage_pages.click();
        Common.scrollToElement(driver, hmcPage.Wcms_css);
        Common.rightClick(driver, hmcPage.Component_emptyjscss);
        hmcPage.Wcms_create_js.click();
        Common.sleep(3000);
        Common.switchToWindow(driver, 1);
        Common.sleep(3000);
        hmcPage.Wcms_create_jsid.clear();
        hmcPage.Wcms_create_jsid.sendKeys(id);
        hmcPage.Wcms_create_jssearch.click();
        Common.sleep(3000);
        Common.doubleClick(driver, hmcPage.Wcms_create_jssuse);
        Common.switchToWindow(driver, 0);
        hmcPage.SaveButton.click();
        //hmcPage.PaymentLeasing_saveAndCreate.click();
        Common.sleep(3000);
        
        Common.scrollToElement(driver, hmcPage.Wcms_css);
        Assert.assertTrue(!hmcPage.Wcms_csslist.getText().equals("The list is empty."));
        Dailylog.logInfoDB(7, "Add new created css component to Frequently Asked Questions page:", Store, testName);
        setTep8();
	}

	private void setTep8() throws InterruptedException {
		StringSelection stringSelection = new StringSelection(checkbody);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	    Common.sleep(3000);
		((JavascriptExecutor)driver).executeScript("(window.open(''))");
		Common.switchToWindow(driver, 1);
		driver.get(b2cHomeURL+"/"+faqNumber);
		Robot robot = null;
		try {
	          robot = new Robot();
	        } catch (Exception e1) {
	          e1.printStackTrace();
	        }
		robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_U);
        Common.sleep(3000);
//        Common.switchToWindow(driver, 1);
		robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_F);
        Common.sleep(2000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);	
//        String text = hmcPage.Faq_check.getText();
//        Assert.assertTrue(text.contains(checkbody));
        Dailylog.logInfoDB(8, "Check the css in Web:", Store, testName);
	}

}
