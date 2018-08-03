package TestScript.B2C;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class NA20270Test extends SuperTestClass {
	private String CtaBTName="TestBtUrl";
	private String CtaBTlink="http://www.baidu.com/";
	private String CustomizeBTlink="http://www.qq.com/";
	private HMCPage hmcPage;
	private String productID;
	private String UnitID;
	private String catalogVersion;
	private B2CPage b2cPage;
	
	
	public NA20270Test(String store,String product,String UnitID,String catalogVersion) {
		this.Store = store;
		this.testName = "NA-20270";
		this.productID=product;
		this.UnitID=UnitID;
		this.catalogVersion=catalogVersion;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void NA20270(ITestContext ctx) {

		try {
			this.prepareTest();
			hmcPage =new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			SetCTAButton(UnitID);
			SetCTAButtonLink(productID,UnitID,catalogVersion,CustomizeBTlink);
			checkCTAButtonLink(CustomizeBTlink,CtaBTlink);
			
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}

	}
	
	public void SetCTAButton(String UnitID) throws InterruptedException{
		//step1   login hmc
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		Dailylog.logInfoDB(1, "Login HMC success!",Store,testName);
		
		
		//step3
		hmcPage.Home_B2CCommercelink.click();
		hmcPage.Home_B2CUnitLink.click();
		driver.findElement(By.xpath("//*[@class='listtable']/tbody/tr[2]/td[4]/div/div/table/tbody/tr/td/input")).sendKeys(UnitID);
		driver.findElement(By.xpath("//*[@class='xp-button chip-event']/a")).click();
		driver.findElement(By.xpath("//div/div/table/tbody/tr/td[2]/a")).click();
		
        driver.findElement(By.xpath(".//*[@id='Content/EditorTab[B2CUnit.tab.siteattributes]_span']")).click();
        driver.findElement(By.xpath(".//*[@id='Content/StringEditor[in Content/Attribute[B2CUnit.ctaBackgroundColors]]_input']")).clear();
        driver.findElement(By.xpath(".//*[@id='Content/StringEditor[in Content/Attribute[B2CUnit.ctaBackgroundColors]]_input']")).sendKeys("1976A1");
        driver.findElement(By.xpath(".//*[@id='Content/StringEditor[in Content/LocalizableAttribute[B2CUnit.ctaLabelName]]_input']")).clear();
        driver.findElement(By.xpath(".//*[@id='Content/StringEditor[in Content/LocalizableAttribute[B2CUnit.ctaLabelName]]_input']")).sendKeys("Store near me");
        Dailylog.logInfoDB(3, "Config for India indirect site: Maintain  CTA button for 'Store near me' button in HMC: B2C commerce->site attribute success!",Store,testName);
        
        //step4
        driver.findElement(By.xpath(".//*[@id='Content/StringEditor[in Content/Attribute[B2CUnit.customizedButtonColor]]_input']")).clear();
        driver.findElement(By.xpath(".//*[@id='Content/StringEditor[in Content/Attribute[B2CUnit.customizedButtonColor]]_input']")).sendKeys("1976A1");
        driver.findElement(By.xpath(".//*[@id='Content/StringEditor[in Content/LocalizableAttribute[B2CUnit.customizedButtonLabel]]_input']")).clear();
        driver.findElement(By.xpath(".//*[@id='Content/StringEditor[in Content/LocalizableAttribute[B2CUnit.customizedButtonLabel]]_input']")).sendKeys("Store near me");
        driver.findElement(By.xpath("//div[contains(@id,'Content/ImageToolbarAction[organizer.editor.save.title]')]")).click();
        Thread.sleep(3000);
        Dailylog.logInfoDB(4, "Maintain customized button: Under b2c commerce->site attribute success!",Store,testName);

		
	}
	
	public void SetCTAButtonLink(String productID,String UnitID,String catalogVersion,String CustomizeBTlink) throws InterruptedException{
		//step5
		hmcPage.Home_CatalogLink.click();
		hmcPage.Home_ProductsLink.click();
		hmcPage.Catalog_ArticleNumberTextBox.sendKeys(productID);
		driver.findElement(By
				.xpath(".//select[contains(@id,'in Content/GenericCondition[Product.catalogVersion]')]/option[contains(.,'masterMultiCountryProductCatalog - Online')]"))
				.click();
		hmcPage.Catalog_SearchButton.click();
		// click search item one
		driver.findElement(By.xpath(".//*[@id='Content/McSearchListConfigurable[Product]_innertable']/tbody/tr[3]/td[3]")).click();
	
		Thread.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='Content/EditorTab[NemoSubSeriesProduct.tab.product.PMI]_span']")).click();
		
		Thread.sleep(9000);
	    Common.rightClick(driver, driver.findElement(By.xpath(".//*[@id='ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[NemoSubSeriesProduct.ctaLink]]']")));
	
	    Thread.sleep(3000);
	    System.out.println(driver.findElement(By.xpath(".//*[@id='ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[NemoSubSeriesProduct.ctaLink]]']")).getText());
	    if(!driver.findElement(By.xpath(".//*[@id='ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[NemoSubSeriesProduct.ctaLink]]']")).getAttribute("value").equals("")) {
	    	
	    }else {
	    	driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[NemoSubSeriesProduct.ctaLink]]_!create_NemoLinkComponent_label']")).click();
		    Common.switchToWindow_Title(driver,"Editor: Nemo Link Component");
		    //id  
		    
		    driver.findElement(By.xpath(".//input[contains(@id,'uid]]_input')]")).clear();
		    driver.findElement(By.xpath(".//input[contains(@id,'uid]]_input')]")).sendKeys(CtaBTName);
		    //name
		    driver.findElement(By.xpath(".//input[contains(@id,'name]]_input')]")).clear();
		    driver.findElement(By.xpath(".//input[contains(@id,'name]]_input')]")).sendKeys(CtaBTName);
		    //catalog version AllInstancesSelectEditor[in Attribute[.catalogVersion]]_select
			driver.findElement(By
					.xpath(".//select[contains(@id,'catalogVersion')]/option[contains(.,'"+catalogVersion+"')]"))
					.click();
		    //visible
		    if(!driver.findElement(By.xpath(".//*[@id='BooleanEditor[in Attribute[.visible]]_checkbox']")).isSelected()) {
		    	driver.findElement(By.xpath(".//*[@id='BooleanEditor[in Attribute[.visible]]_checkbox']")).click();
		    }
		    
		    //url
		    driver.findElement(By.id("StringEditor[in Attribute[.url]]_input")).clear();
		    driver.findElement(By.id("StringEditor[in Attribute[.url]]_input")).sendKeys(CtaBTlink);
		    //saveBT
		    Common.javascriptClick(driver, driver.findElement(By.xpath(".//div[contains(@id,'organizer.editor.save.title')]/u")));
//		    driver.findElement(By.xpath(".//div[contains(@id,'organizer.editor.save.title')]/u")).click();    
		    Thread.sleep(3000);
		    Common.switchToWindow_Title(driver,"Product - hybris Management Console");
	    }
	    Thread.sleep(3000);

	    
		hmcPage.Home_ProductsLink.click();
		driver.findElement(By.id("ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[NemoSubSeriesProduct.ctaButtonLink]]")).clear();
		driver.findElement(By.id("ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[NemoSubSeriesProduct.ctaButtonLink]]")).sendKeys(CtaBTName);

		driver.findElement(By.xpath(".//input[contains(@id,'customizedButtonURL')]")).clear();
		driver.findElement(By.xpath(".//input[contains(@id,'customizedButtonURL')]")).sendKeys(CustomizeBTlink);
		driver.findElement(By.xpath(".//div[contains(@id,'organizer.editor.save.title')]")).click();
		Thread.sleep(3000);
		Dailylog.logInfoDB(6, "Maintain customized button url success!",Store,testName);
		
		
	}
	
	public void checkCTAButtonLink(String CustomizeBTlink,String CtaBTlink) throws InterruptedException{
		driver.get("https://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com"+ "/in/en/laptops/ideapad/ideapad-300-series/IdeaPad-310-15ISK/p/88IP3000706");
		Thread.sleep(3000);
		driver.findElement(By.xpath("(.//a[contains(@class,'cta-button')])[1]")).click();
		Thread.sleep(2000);
		if(Common.isElementExist(driver, By.xpath("(.//a[contains(@class,'customized-button-link')])[1]"))){
			Assert.assertEquals(true,driver.findElement(By.xpath(".//a[contains(@class,'customized-button-link')]")).getAttribute("href").equals(CustomizeBTlink));
			Dailylog.logInfoDB(7, "check on India indirect site customized-button url success!",Store,testName);
		}else{
			Dailylog.logError("customized-button isnot exist!!! please check");
		}
		if(Common.isElementExist(driver, By.xpath("(.//a[contains(@class,'cta-button')])"))){
			Assert.assertEquals(true, driver.findElement(By.xpath("(.//a[contains(@class,'cta-button')])[3]")).getAttribute("href").equals(CtaBTlink));
			Dailylog.logInfoDB(8, "check on India indirect site cta-button url success!",Store,testName);
		}else{
			Dailylog.logError("cta-button isnot exist!!! please check");
		}
	}
	
	@Test(priority = 1,alwaysRun = true,  enabled = true)
	public void rollBack() throws InterruptedException, MalformedURLException{
		driver.get(testData.HMC.getHomePageUrl());
		Common.sleep(3000);
		driver.findElement(By.id("ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[NemoSubSeriesProduct.ctaButtonLink]]")).clear();
		driver.findElement(By.xpath(".//input[contains(@id,'customizedButtonURL')]")).clear();
		driver.findElement(By.id("ajaxinput_Content/AutocompleteReferenceEditor[in Content/Attribute[NemoSubSeriesProduct.ctaLink]]")).clear();
		driver.findElement(By.xpath(".//div[contains(@id,'organizer.editor.save.title')]")).click();
		Dailylog.logInfoDB(9, "roll back success!!",Store,testName);
	}
}
