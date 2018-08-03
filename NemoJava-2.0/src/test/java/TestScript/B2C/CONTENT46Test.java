package TestScript.B2C;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class CONTENT46Test extends SuperTestClass {
	public HMCPage hmcPage;
	private B2CPage b2cPage;
	String patnumber;
	private B2BPage b2bPage;
	String auurl = "https://pre-c-hybris.lenovo.com/le/adobe_global/au/en/1213654197/login";
	String usurl = "https://pre-c-hybris.lenovo.com/le/1213348423/us/en/1213577815/login";

	public CONTENT46Test(String store,String patnumber) {
		this.Store = store;
		this.testName = "CONTENT-46";
		this.patnumber = patnumber;
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "uxui", "p2", "b2c"})
	public void CONTENT46(ITestContext ctx) {
		try {
			this.prepareTest();
			
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			b2bPage = new B2BPage(driver);
			
			//1.open b2c site
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+patnumber);
			Common.sleep(3000);
			Dailylog.logInfoDB(1, "open b2c website", Store, testName);
			
			//2.Go to HMC-->Catlog --> Products ,input patnumber
			searchPartnumber();
			
			//3.Open the product info,go to [PMI] tab
			editMedia("","Lenovo ThinkPad X1 Carbon (5th Gen)");
			
			//6.Go to B2C site,find the media,check alt name.
			Common.sleep(8000);
			checkB2CAlt("","Lenovo ThinkPad X1 Carbon (5th Gen)");
			
			//7.Go to step 5,modify 	Alternative text and Localized Alternative Text,then click save
			switchToWindow(0);
			hmcPage.HMC_Logout.click();
			searchPartnumber();
			editMedia("Lenovo ThinkPad X1 Carbon (5th Gen)_en","Lenovo ThinkPad X1 Carbon (5th Gen)");
			Dailylog.logInfoDB(7, "Go to step 5,modify 	Alternative text and Localized Alternative Text,then click save", Store, testName);
			
			//8.Go to B2C site,find the media,check alt name.
			checkB2CAlt("Lenovo ThinkPad X1 Carbon (5th Gen)_en","Lenovo ThinkPad X1 Carbon (5th Gen)");
			Dailylog.logInfoDB(8, "Go to B2C site,find the media,check alt name.", Store, testName);
			
			//9.Go to B2B site
			switchToWindow(0);
			if(Store.toLowerCase().equals("au")) {
				driver.get(auurl);
				B2BCommon.Login(b2bPage, "aubuyer@sharklasers.com", "1q2w3e4r");
			}else {
				driver.get(usurl);
				B2BCommon.Login(b2bPage, "usbuyer@sharklasers.com", "1q2w3e4r");
			}
			String attribute = driver.findElement(By.xpath("//*[@id=\"hero-link-1\"]/img")).getAttribute("src");
			String[] split = attribute.split("\\?");
			String[] split2 = split[0].split("/");
			String replaceAll = split2[split2.length-1].replaceAll("-", "_");
			Dailylog.logInfoDB(9, "Go to B2B site", Store, testName);
			//10.Go to hmc.Multimedia->	Media,option Attribute ,add a new field 
			checkMedia(replaceAll,replaceAll,"3.14 au b2b");
			Dailylog.logInfoDB(10, "Go to hmc.Multimedia->	Media,option Attribute ,add a new field ", Store, testName);
			
			//13.Go to B2B  AU site,find the media,check alt name.
			checkB2BAlt("3.14 au b2b");
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}


	private void checkB2BAlt(String LocalizedAlternativeText) {
		if(Store.toLowerCase().equals("au")) {
			driver.get(auurl);
			B2BCommon.Login(b2bPage, "aubuyer@sharklasers.com", "1q2w3e4r");
		}else {
			driver.get(usurl);
			B2BCommon.Login(b2bPage, "usbuyer@sharklasers.com", "1q2w3e4r");
		}
		String attribute = driver.findElement(By.xpath("//a[@id='hero-link-1']/img")).getAttribute("alt");
		Assert.assertEquals(attribute, LocalizedAlternativeText);
		Dailylog.logInfoDB(13, "Go to B2B  AU site,find the media,check alt name.", Store, testName);
	}

	private void checkMedia(String attribute, String Alternativetext, String LocalizedAlternativeText) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Multimedia_menu.click();
		hmcPage.Multimedia_media.click();
		Common.sleep(2000);
		driver.findElement(By.xpath("//select[@id='Content/AbstractGenericConditionalSearchToolbarChip$3[attributeselect][Media]_select']/option[@value='altText']")).click();
		Dailylog.logInfoDB(10, "Go to hmc.Multimedia->	Media,option Attribute ,add a new field ", Store, testName);
		
		//11.Input Alternative text,click search 
		driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/GenericCondition[Media.altText]]_input']")).sendKeys(attribute);
//		driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/GenericCondition[Media.code]]_input']")).clear();
//		driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/GenericCondition[Media.code]]_input']")).sendKeys(attribute+"");
//		driver.findElement(By.xpath("//select[@id='Content/AllInstancesSelectEditor[in Content/GenericCondition[Media.catalogVersion]]_select']/option[contains(.,'\n" + 
//				"							nemob2bContentCatalog - Online\n" + 
//				"						')]")).click();
		hmcPage.B2BUnit_SearchButton.click();
		Common.doubleClick(driver, driver.findElement(By.xpath("//div[contains(@id,'nemob2bContentCatalog - Online') and contains(@id,'Online') and @class='olecEntry']")));
		Dailylog.logInfoDB(11, "Input Alternative text,click search", Store, testName);
		
		//12.Open the result,go to [Metadata] tab,modify Alternative text and Localized Alternative Text,then click save
		Common.sleep(3000);
		driver.findElement(By.xpath("//span[@id='Content/EditorTab[Media.tab.media.metadata]_span']")).click();
		
		driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/LocalizableAttribute[Media.zAlternativeTextLoc]]_input']\n" + 
				"")).clear();
		driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/LocalizableAttribute[Media.zAlternativeTextLoc]]_input']\n" + 
				"")).sendKeys(LocalizedAlternativeText);
		driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/Attribute[Media.alttext]]_input']")).clear();
		
		driver.findElement(By.xpath("//input[@id='Content/StringEditor[in Content/Attribute[Media.alttext]]_input']")).sendKeys(Alternativetext);
		hmcPage.HMC_Save.click();
		Dailylog.logInfoDB(12, "Open the result,go to [Metadata] tab,modify Alternative text and Localized Alternative Text,then click save", Store, testName);
	}

	private void checkB2CAlt(String LocalizedAlternativeText, String Alternativetext) {
		driver.get(testData.B2C.getHomePageUrl()+"/p/"+patnumber);
		Common.sleep(3000);
		String attribute = driver.findElement(By.xpath("//img[contains(@class,'rollovercartItemImg no-margin')]")).getAttribute("alt");
	    Assert.assertEquals(attribute, Alternativetext);
	    Dailylog.logInfoDB(6, "Go to B2C site,find the media,check alt name", Store, testName);
	    driver.close();
	}

	private void editMedia(String LocalizedAlternativeText, String Alternativetext) throws InterruptedException {
		hmcPage.Catalog_PMITab.click();
		Common.scrollToElement(driver, driver.findElement(By.xpath("//table[@title='mkt_image_hero']//table[@class='readable']//tr[1]")));
		Common.rightClick(driver, driver.findElement(By.xpath("//table[@title='mkt_image_hero']//table[@class='readable']//tr[1]")));
		Dailylog.logInfoDB(3, "Open the product info,go to [PMI] tab", Store, testName);
		//4.Right click in the media,select edit in new window
		driver.findElement(By.xpath("//td[contains(.,'Edit in new window')]")).click();
		Dailylog.logInfoDB(4, "Right click in the media,select edit in new window", Store, testName);
		
		//5.In the popup,go to [Metadata] tab
		switchToWindow(1);
		driver.findElement(By.xpath("//span[@id='EditorTab[Media.tab.media.metadata]_span']")).click();
		driver.findElement(By.xpath("//input[@id='StringEditor[in NemoLocalizableAttribute[Media.zAlternativeTextLoc]]_input']")).clear();
		driver.findElement(By.xpath("//input[@id='StringEditor[in NemoLocalizableAttribute[Media.zAlternativeTextLoc]]_input']")).sendKeys(LocalizedAlternativeText);
		driver.findElement(By.xpath("//input[@id='StringEditor[in Attribute[Media.alttext]]_input']")).clear();
		driver.findElement(By.xpath("//input[@id='StringEditor[in Attribute[Media.alttext]]_input']")).sendKeys(Alternativetext);
		hmcPage.HMC_Save.click();
		Dailylog.logInfoDB(5, "In the popup,go to [Metadata] tab", Store, testName);
		
		cleanProductCache(patnumber);
	}
	
	private void cleanProductCache(String partnumber){	
		Common.sleep(5000);
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_System.click();
		hmcPage.Home_RadisCacheCleanLink.click();
		driver.switchTo().frame(0);
		hmcPage.Radis_CleanProductTextBox.sendKeys(partnumber);
		hmcPage.Radis_CleanButton.click();
		Common.sleep(15000);
		driver.switchTo().alert().accept();
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

	private void searchPartnumber() {
		driver.manage().deleteAllCookies();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_CatalogLink.click();
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(patnumber);
		Common.sleep(1000);
		hmcPage.Catalog_CatalogVersion.click();
		hmcPage.Catalog_MasterMultiCountryProductCatalogOnline.click();
		Common.sleep(1000);
		hmcPage.Catalog_SearchButton.click();
		Common.sleep(2000);
		Common.doubleClick(driver, hmcPage.Catalog_MultiCountryCatOnlineLinkInSearchResult);
		Common.sleep(2000);
		Dailylog.logInfoDB(2, "Go to HMC-->Catlog --> Products ,input patnumber", Store, testName);
	}

}
