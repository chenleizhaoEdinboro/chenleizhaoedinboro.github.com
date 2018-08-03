package TestScript.B2C;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.CTOCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.CTOPage;
import Pages.HMCPage;
import TestData.PropsUtils;
import TestScript.SuperTestClass;

public class NA18781Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public CTOPage ctoPage;
	public String GROUP = "1################################";
	public String BRAND = "11TC#############################";
	public String SERIES = "11TC1MD##########################";
	public String SUB_SERIES = "11TC1MD910T######################";
	public String MACHINE_TYPE = "11TC1MD910T10MN##################";
	public String R3MATNR = "11TC1MD910T10MN10MNCTO1WW########";
	public String BUNDLE = "11TC1MD910T10MN10MNCTO1WWENUS0~~~";
	
	public NA18781Test(String store){
		this.Store = store;
		this.testName = "NA-18781";
	}

	@Test(alwaysRun = true, groups = {"shopgroup", "cto", "p2", "b2c"})
	public void NA18781(ITestContext ctx) {
		try {
			this.prepareTest();
			ctoPage = new CTOPage(driver);
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			String OptionTitleSub ="AutoTest";
			String OptionTitle = OptionTitleSub+"_"+Common.getDateTimeString();
			String TempleteTitle = "template-for-Desktop";
			String AccessoryCategories="Storage_Optical Drives - External";
			String URLMtach = "ca/nomenclature=";
			editTemplate(TempleteTitle,AccessoryCategories,OptionTitleSub,OptionTitle);
			driver.get(testData.CTO.getHomePageUrl());
			CTOCommon.Login(ctoPage,testData);
			Common.waitElementClickable(driver, ctoPage.Home_SearchButton, 30);
			Dailylog.logInfoDB(0, "Create templete successfully",Store,testName);
			//Added Option on ph=1 level
			driver.manage().timeouts().pageLoadTimeout(240, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);
			AddOptionOnEngine(testData.CTO.getHomePageUrl()+URLMtach+GROUP,OptionTitle);
			Dailylog.logInfoDB(1,"Add option on ph1 level successfully",Store,testName);
			CheckOptionsOnDifferentLevels("BRAND",testData.CTO.getHomePageUrl()+URLMtach+BRAND,OptionTitle,true);
			CheckOptionsOnDifferentLevels("SERIES",testData.CTO.getHomePageUrl()+URLMtach+SERIES,OptionTitle,true);
			CheckOptionsOnDifferentLevels("SUB_SERIES",testData.CTO.getHomePageUrl()+URLMtach+SUB_SERIES,OptionTitle,true);
			CheckOptionsOnDifferentLevels("MACHINE_TYPE",testData.CTO.getHomePageUrl()+URLMtach+MACHINE_TYPE,OptionTitle,true);
			CheckOptionsOnDifferentLevels("R3MATNR",testData.CTO.getHomePageUrl()+URLMtach+R3MATNR,OptionTitle,true);
			CheckOptionsOnDifferentLevels("BUNDLE",testData.CTO.getHomePageUrl()+URLMtach+BUNDLE,OptionTitle,false);
			ApproverOptionOnBundle(testData.CTO.getHomePageUrl()+URLMtach+BUNDLE,OptionTitle);
			CheckOptionsOnDifferentLevels("BUNDLE",testData.CTO.getHomePageUrl()+URLMtach+BUNDLE,OptionTitle,true);
			DeleteOptionOnEngine(testData.CTO.getHomePageUrl()+URLMtach+SUB_SERIES,OptionTitle);
			CheckOptionsOnDifferentLevels("BRAND",testData.CTO.getHomePageUrl()+URLMtach+BRAND,OptionTitle,true);
			CheckOptionsOnDifferentLevels("SERIES",testData.CTO.getHomePageUrl()+URLMtach+SERIES,OptionTitle,true);
			CheckOptionsOnDifferentLevels("SUB_SERIES",testData.CTO.getHomePageUrl()+URLMtach+SUB_SERIES,OptionTitle,false);
			CheckOptionsOnDifferentLevels("MACHINE_TYPE",testData.CTO.getHomePageUrl()+URLMtach+MACHINE_TYPE,OptionTitle,false);
			CheckOptionsOnDifferentLevels("R3MATNR",testData.CTO.getHomePageUrl()+URLMtach+R3MATNR,OptionTitle,false);
			CheckOptionsOnDifferentLevels("BUNDLE",testData.CTO.getHomePageUrl()+URLMtach+BUNDLE,OptionTitle,false);
			DeleteOptionOnEngine(testData.CTO.getHomePageUrl()+URLMtach+GROUP,OptionTitle);
			CheckOptionsOnDifferentLevels("BRAND",testData.CTO.getHomePageUrl()+URLMtach+BRAND,OptionTitle,false);
			CheckOptionsOnDifferentLevels("SERIES",testData.CTO.getHomePageUrl()+URLMtach+SERIES,OptionTitle,false);
			driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
				
		}catch (Throwable e)
		{
			handleThrowable(e, ctx);
		}
	}
	
	
	
		
	public void ApproverOptionOnBundle(String BundleUrl,String OptionTitle) throws InterruptedException{
		driver.get(BundleUrl);
		Dailylog.logInfoDB(2, "Open Group URL : "+BundleUrl,Store,testName);
		Common.sleep(18000);
		if(Common.checkElementDisplays(driver, By.xpath("//button[contains(@class,'dismiss-invalid-modal')]"),120)){   		
	    	 ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[contains(@class,'dismiss-invalid-modal')]")));			
			Dailylog.logInfoDB(8, "OK button click on pop up invalid configuration window",Store, testName);				 
	 	}
		
		//handle warning message
		if (isAlertPresent())
			driver.switchTo().alert().accept();
		ArrayList<WebElement> alerts=new ArrayList<WebElement>(driver.findElements(By.xpath("//button[@id='alert_button' and @class='close']")));
		for(WebElement e:alerts){
			Common.sleep(3000);
			e.click();
		}
		
		Common.waitElementClickable(driver, ctoPage.Home_SearchButton, 30);
		ctoPage.BundlePage_approveRulesBtn.click();
		Common.waitElementVisible(driver, ctoPage.BundlePage_rulesPanel);
		Common.sleep(12000);
		By OptionEle = By.xpath("//input[@targetid='DUM_OPT_" + OptionTitle + "']");
		if (Common.isElementExist(driver, OptionEle, 5)) {
			driver.findElement(OptionEle).click();
			ctoPage.BundlePage_approvebtn.click();
			Common.waitElementVisible(driver, ctoPage.BundlePage_dialogMsg);
			Assert.assertEquals(ctoPage.BundlePage_dialogMsg.getText(),
					"Selected rules have been approved successfully");
			String ApproveOK = "//span[contains(@class,'glyphicon glyphicon-ok-circle')]/..";
			driver.findElement(By.xpath(ApproveOK)).click();
		} else {
			Dailylog.logInfoDB(0, "The option is not displayed while approing new option in bundle!", Store,
					testName);
			// Assert.fail("The option is not displayed while approing new option in
			// bundle!");
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.BundlePage_saveBtn);
		Dailylog.logInfoDB(2,"Clicked save button",Store,testName);
		try{
			Common.waitElementVisible(driver, ctoPage.ModalPage_saveMsg);
		    String saveMsg = ctoPage.ModalPage_saveMsg.getText();
		    Dailylog.logInfoDB(2,"saveMsg: " + saveMsg,Store,testName);
		}catch(Exception e){
			Dailylog.logInfoDB(2,"saveMsg: timeout",Store,testName);
		}
		Common.sleep(12000);
		By OptionText = By.xpath("//*[text()='DUM_OPT_" + OptionTitle + "']");
		Assert.assertTrue(Common.checkElementDisplays(driver, OptionText, 5), "newTemplate should present in bundle");
		
	}
	
	public void DeleteOptionOnEngine(String LevelUrl,String OptionTitle){
		driver.get(LevelUrl);
		Dailylog.logInfoDB(2, "Open Group URL : "+LevelUrl,Store,testName);
		Common.sleep(18000);
		
		if(Common.checkElementDisplays(driver, By.xpath("//button[contains(@class,'dismiss-invalid-modal')]"),120)){   		
	    	 ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[contains(@class,'dismiss-invalid-modal')]")));			
			Dailylog.logInfoDB(8, "OK button click on pop up invalid configuration window",Store, testName);				 
	 	}
		
		//handle warning message
		if (isAlertPresent())
			driver.switchTo().alert().accept();
		ArrayList<WebElement> alerts=new ArrayList<WebElement>(driver.findElements(By.xpath("//button[@id='alert_button' and @class='close']")));
		for(WebElement e:alerts){
			Common.sleep(3000);
			e.click();
		}
		
		Common.waitElementClickable(driver, ctoPage.Home_SearchButton, 30);
		By OptionEle = By.xpath("//*[@groupid='DUM_OPT_" + OptionTitle + "']/td/span");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(OptionEle));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(OptionEle));
		Common.sleep(10000);
		Dailylog.logInfoDB(3,"Open Option Section",Store,testName);
		try{
		   Common.waitElementVisible(driver, ctoPage.ModalPage_deleteOptionsBtn);
		}catch(Exception e){
			Dailylog.logInfoDB(3,"Delete Option doesnot Exist: and retry wait next 30 seconds ",Store,testName);
			Common.waitElementClickable(driver, ctoPage.ModalPage_deleteOptionsBtn, 30);
			
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.ModalPage_deleteOptionsBtn);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.ModalPage_deleteOptionsOK);
		Dailylog.logInfoDB(3,"Delete Option successfully",Store,testName);
		Common.waitElementClickable(driver, ctoPage.BundlePage_saveBtn, 100);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.BundlePage_saveBtn);
		Dailylog.logInfoDB(2,"Clicked save button",Store,testName);
		try{
			Common.waitElementVisible(driver, ctoPage.ModalPage_saveMsg);
		    String saveMsg = ctoPage.ModalPage_saveMsg.getText();
		    Dailylog.logInfoDB(2,"saveMsg: " + saveMsg,Store,testName);
		}catch(Exception e){
			Dailylog.logInfoDB(2,"saveMsg: timeout",Store,testName);
		}
	}
	
	public void CheckOptionsOnDifferentLevels(String LevelName,String LevelUrl,String OptionTitle,boolean ExpectedResult){
		boolean isExist;
		driver.get(LevelUrl);
		Common.sleep(18000);
		if (isAlertPresent())
			driver.switchTo().alert().accept();
		Common.waitElementClickable(driver, ctoPage.Home_SearchButton, 30);
		By OptionEle = By.xpath("//*[text()='DUM_OPT_" + OptionTitle + "']");
		if (Common.isElementExist(driver, OptionEle, 5)) {
			isExist= true;
		}else{
			isExist = false;
		}
		System.out.println("Check Option exist on Level : "+LevelUrl +isExist);
		Assert.assertEquals(isExist, ExpectedResult,"Check Option exist on Level : "+LevelUrl +" but it is not" +ExpectedResult);
	}
	public void AddOptionOnEngine(String LevelUrl,String OptionTitle){
		
		driver.get(LevelUrl);
		
		Dailylog.logInfoDB(2, "Open Group URL : "+LevelUrl,Store,testName);
		Common.sleep(18000);
		
		if(Common.checkElementDisplays(driver, By.xpath("//button[contains(@class,'dismiss-invalid-modal')]"),120)){   		
	    	 ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[contains(@class,'dismiss-invalid-modal')]")));			
			Dailylog.logInfoDB(8, "OK button click on pop up invalid configuration window",Store, testName);				 
	 	}
		ArrayList<WebElement> alerts=new ArrayList<WebElement>(driver.findElements(By.xpath("//button[@id='alert_button' and @class='close']")));
		for(WebElement e:alerts){
			e.click();
		}
		if (isAlertPresent())
			driver.switchTo().alert().accept();
		
		Common.waitElementClickable(driver, ctoPage.Home_SearchButton, 30);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				ctoPage.ModalPage_addOptionsBtn);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.ModalPage_addOptionsBtn);
		Dailylog.logInfoDB(2,"Clicked Add Options",Store,testName);
		Common.waitElementClickable(driver, ctoPage.ModalPage_selectOptions, 5);
		ctoPage.ModalPage_selectOptions.click();
		// verify the new created template is displayed in select options list
		By newTemplateX = By.xpath("//*[@class='dropdown-menu open']//span[text()='" + OptionTitle + "']");
		Assert.assertTrue(Common.checkElementDisplays(driver, newTemplateX, 5),
				"newTemplate should display in select options list");
		// select the new created template
		Common.waitElementClickable(driver, driver.findElement(newTemplateX), 5);
		driver.findElement(newTemplateX).click();
		ctoPage.ModalPage_addOptionsOK.click();
		Dailylog.logInfoDB(2,"Added new template",Store,testName);
		// verify the new created template is displayed modal
		By newTemplateX2 = By.xpath("//span[text()='" + OptionTitle + "']");
		Assert.assertTrue(Common.checkElementDisplays(driver, newTemplateX2, 5),
				"newTemplate should display ");
		Common.sleep(58000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				ctoPage.BundlePage_saveBtn);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ctoPage.BundlePage_saveBtn);
		Dailylog.logInfoDB(2,"Clicked save button",Store,testName);
		try{
			Common.sleep(50000);			
			Common.waitElementClickable(driver, ctoPage.ModalPage_saveMsg,50);
		    String saveMsg = ctoPage.ModalPage_saveMsg.getText();
		    Dailylog.logInfoDB(2,"saveMsg: " + saveMsg,Store,testName);
		}catch(Exception e){
			Dailylog.logInfoDB(2,"saveMsg: timeout",Store,testName);
		}
		
		
	}
	
		public void editTemplate(String template, String optionSupercategories, String OptionTitleSub,String OptionTitle) {
			
			By targetTemplateX = By.xpath("(//a[contains(.,'" + template + "')])[1]");
			By testTabX = By.xpath("(//*[contains(@id,'EditableItemListEntry[" + OptionTitleSub + "')])[1]");
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Nemo.click();
			Common.waitElementClickable(driver, hmcPage.Nemo_productBuilder, 5);
			hmcPage.Nemo_productBuilder.click();
			Common.waitElementClickable(driver, hmcPage.Nemo_template, 5);
			hmcPage.Nemo_template.click();
			Common.sleep(1000);
			hmcPage.Catalog_CatalogVersion.click();
			hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
			Common.sleep(1000);
			hmcPage.Contract_searchbutton.click();
			Common.sleep(2000);

			// open target template
			WebElement targetTemplate = driver.findElement(targetTemplateX);
			targetTemplate.click();
			Common.sleep(2000);

			// if the test tab exists, delete it
			if (Common.isElementExist(driver, testTabX)){
				//String InvalidTabName = driver.findElement(testTabX).getAttribute("value");
				delteTemplate(OptionTitleSub);
			}
			// Create Product Option Tab
			Common.rightClick(driver, hmcPage.Template_tabTitle);
			Common.waitElementClickable(driver, hmcPage.Template_createProductOption, 5);
			hmcPage.Template_createProductOption.click();
			switchToWindow(1);

			// fill in tab title and tab code
			hmcPage.Template_titleInput.sendKeys(OptionTitle);
			hmcPage.Template_codeInput.sendKeys(OptionTitle);

			// select cto flag
			hmcPage.Template_ctoFlagCheckbox.click();

			// Create Product Option Section
			Common.rightClick(driver, hmcPage.Template_sectionsTable);
			hmcPage.Template_createProductOption.click();
			switchToWindow(2);

			// fill in section title and tab code
			hmcPage.Template_titleInput.sendKeys(OptionTitle);
			hmcPage.Template_codeInput.sendKeys(OptionTitle);

			// Create Product Option Group
			Common.rightClick(driver, hmcPage.Template_groupsTable);
			hmcPage.Template_createProductOption.click();
			switchToWindow(3);

			// fill in group title and tab code
			hmcPage.Template_titleInput.sendKeys(OptionTitle);
			hmcPage.Template_codeInput.sendKeys(OptionTitle);

			// Add [Hierarchy]
			Common.rightClick(driver, hmcPage.Template_categoriesTable);
			Common.waitElementClickable(driver, hmcPage.Template_addHierarchy, 5);
			hmcPage.Template_addHierarchy.click();
			switchToWindow(4);
			hmcPage.Template_identifier.sendKeys(optionSupercategories);
			Common.sleep(500);
			Common.waitElementClickable(driver, hmcPage.Template_selCatalogVersionOnline, 5);
			hmcPage.Template_selCatalogVersionOnline.click();
			hmcPage.Template_searchBtn.click();
			hmcPage.Template_searchedHierarchy.click();
			hmcPage.Template_useBtn.click();

			// save
			switchToWindow(3);
			hmcPage.Template_saveBtn.click();
			driver.close();
			switchToWindow(2);
			hmcPage.Template_saveBtn.click();
			driver.close();
			switchToWindow(1);
			hmcPage.Template_saveBtn.click();
			driver.close();
			switchToWindow(0);

			// verify template is created successfully
			hmcPage.Template_reloadBtn.click();
			if (isAlertPresent())
				driver.switchTo().alert().accept();
			
			Assert.assertTrue(Common.checkElementDisplays(driver, testTabX, 5), "tab create failure 2");

			// Sign-out
			hmcPage.hmcHome_hmcSignOut.click();
			
		}

		public void delteTemplate(String tabTitle) {
			By testTabX = By.xpath("(//*[contains(@id,'EditableItemListEntry[" + tabTitle + "')])[1]");
			WebElement testTabE = driver.findElement(testTabX);
			Common.rightClick(driver, testTabE);
			hmcPage.B2CLeasing_remove.click();
			if (isAlertPresent())
				driver.switchTo().alert().accept();
			hmcPage.Catalog_save.click();
		}
		public boolean isAlertPresent() {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 10);// seconds
				wait.until(ExpectedConditions.alertIsPresent());
				return true;
			} catch (TimeoutException e) {
				return false;
			}
		}

		
		private void switchToWindow(int windowNo) {
			try {
				Thread.sleep(2000);
				ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
				// System.out.println(windows.size());
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
