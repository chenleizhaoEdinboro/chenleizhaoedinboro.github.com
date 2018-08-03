package TestScript.B2B;

import java.util.ArrayList;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
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



public class NA13426Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BPunchoutPage punchoutPage;
	String aribaUrl;
	String defaultUnit;
	String defaultDMU;
	String AccessLevel;
	String outboundRule1FieldName = "/ItemIn/ItemDetail/Classification";
	String Target = "/ItemIn/ItemDetail/UNSPSCCopy";
	String outboundRule4Field = "CommodityCode";
	String ExtrinsicName = "commodity_code";
	String Category = "2";
	String UNSPSC = "00001";
	String CommodityCode = "00002";
	String Category2 = "1";
	String UNSPSC2 = "aaaaa";
	String CommodityCode2 = "bbbbb";
	String productNum;
	String expectUnCheckCartMessage = "<Classification domain=\"UNSPSC\">43211503</Classification>";
	String expectCheckCartMessage1 = "<Classification domain=\"UNSPSC\">"+UNSPSC+"</Classification>";
	String expectCheckCartMessage2 = "<UNSPSCCopy domain=\"UNSPSC\">"+UNSPSC+"</UNSPSCCopy>";
	String expectCheckCartMessage3 = "<Extrinsic name=\"commodity_code\">"+CommodityCode+"</Extrinsic>";
	String productPro = "20JES1X200";
	public NA13426Test(String store, String defaultUnit, String defaultDMU, String AccessLevel, String productNum){
		this.Store = store;
		this.defaultUnit = defaultUnit;
		this.defaultDMU = defaultDMU;
		this.AccessLevel = AccessLevel;
		this.productNum = productNum;
		this.testName = "NA-13426";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA13426(ITestContext ctx){
		try{
			aribaUrl = testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/ariba";
			
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);
			String defaultMTMPN1 = testData.B2B.getDefaultMTMPN1();
			String defaultMTMPN2 = testData.B2B.getDefaultMTMPN2();
			String B2BCustomer = "TEST-13426-001";
			String Code = "TEST-13426-001";
			String Domain = "NetworkID";
			String SharedSecret = "aaa";
			String UserName = "TEST-13426-001";
			String Password = "aaa";
			String Identity = "TEST-13426-001";
			String ProfileName = "TEST-13426-001";
			
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			
			Dailylog.logInfoDB(3,"Create B2BCustome, and check if exist the custome, edit it.", Store,testName);
			checkB2BCustomer(B2BCustomer);
			
			Dailylog.logInfoDB(4,"Create Punchout Credential, and check if exist, edit it.", Store,testName);
			checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			
			Dailylog.logInfoDB(6,"Create Punchout Profile, and check if exist, remove it then create again.", Store,testName);
			checkPunchoutProfile(ProfileName,B2BCustomer);
			
			Dailylog.logInfoDB(8,"Set Simulation Tool Ariba, and pubchout.", Store,testName);
			SetInSimulationToolAriba(Domain,Identity,SharedSecret,defaultUnit);
			
			Dailylog.logInfoDB(10,"Add product to cart, then check out.", Store,testName);
			AddCartInSimulationToolAriba(productPro,false);
			
			Dailylog.logInfoDB(12,"Modify punchout profile.", Store,testName);
			editPunchoutProfile(ProfileName);
			
			Dailylog.logInfoDB(13,"Set Simulation Tool Ariba, and pubchout again.", Store,testName);
			SetInSimulationToolAriba(Domain,Identity,SharedSecret,defaultUnit);
			
			Dailylog.logInfoDB(15,"Add product to cart, then check out.", Store,testName);
			AddCartInSimulationToolAriba(productPro,true);
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void SetInSimulationToolAriba(String Domain,String Identity,String SharedSecret,String defaultUnit){
		driver.manage().deleteAllCookies();
		driver.get(aribaUrl);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to aribaUrl, and checkout punchout successfully!");
		driver.findElement(By.xpath("//input[@id='userName']")).clear();
		driver.findElement(By.xpath("//input[@id='userName']")).sendKeys("LIeCommerce");
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("M0C0v0n3L!");
		punchoutPage.Ariba_DomainTextBox.clear();
		punchoutPage.Ariba_DomainTextBox.sendKeys(Domain);
		punchoutPage.Ariba_IdentityTextBox.clear();
		punchoutPage.Ariba_IdentityTextBox.sendKeys(Identity);
		punchoutPage.Ariba_SharedSecretTextBox.clear();
		punchoutPage.Ariba_SharedSecretTextBox.sendKeys(SharedSecret);
		punchoutPage.Ariba_PunchoutButton.click();
		Common.sleep(1000);
		driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		Common.sleep(5000);
		if(!Common.checkElementExists(driver, punchoutPage.Lenovo_Link, 30)){
			Dailylog.logInfoDB(8,"Punchout fail, don't open B2B website, and punchout again!", Store,testName);
			driver.switchTo().defaultContent();
			punchoutPage.Ariba_PunchoutButton.click();
			Common.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		} else if(!punchoutPage.Lenovo_Link.getAttribute("href").contains("isPunchout=true")){
			System.out.println("The href is: "+punchoutPage.Lenovo_Link.getAttribute("href"));
			Dailylog.logInfoDB(8,"Punchout fail, the B2B website is error, and punchout again!", Store,testName);
			driver.switchTo().defaultContent();
			punchoutPage.Ariba_PunchoutButton.click();
			Common.sleep(1000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		}
		Common.sleep(5000);
		System.out.println("The href is: "+punchoutPage.Lenovo_Link.getAttribute("href"));
		assert punchoutPage.Lenovo_Link.getAttribute("href").contains(defaultUnit);
		assert punchoutPage.Lenovo_Link.getAttribute("href").contains("isPunchout=true");
		System.out.println("Punchout successfully!");
		driver.switchTo().defaultContent();
	}
	
	public void AddCartInSimulationToolAriba(String productNum,Boolean whetherCheckCart){
		Common.sleep(5000);
		driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		punchoutPage.Ariba_CartButton.click();
		Common.sleep(3000);
		punchoutPage.Ariba_QuickOrderProduct.clear();
		punchoutPage.Ariba_QuickOrderProduct.sendKeys(productNum);
		punchoutPage.Ariba_AddButton.click();
		Common.sleep(9000);
		punchoutPage.Ariba_CheckOutButton.click();
		Common.sleep(3000);
		System.out.println("Click check out button!");
		driver.switchTo().defaultContent();
		String cartMessage = punchoutPage.Ariba_OrderListCartMessage.getText();
		if(!whetherCheckCart){
			Assert.assertTrue(cartMessage.contains(expectUnCheckCartMessage), "The cart message is error.");
			System.out.println("The cart message contains "+expectUnCheckCartMessage+"!");
		} else if(whetherCheckCart){
			Boolean acturalCartMessage = cartMessage.contains(expectCheckCartMessage1)&&
					cartMessage.contains(expectCheckCartMessage2)&& cartMessage.contains(expectCheckCartMessage3);
			Assert.assertTrue(acturalCartMessage,"The cart message is error.");
			System.out.println("The cart message contains "+expectCheckCartMessage1+" && "
					+expectCheckCartMessage2+" && " +expectCheckCartMessage3+"!");
		}
	}
	
	public void  checkB2BCustomer(String B2BCustomer){
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BCustomer.click();
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_SearchButton.click();
		if(Common.checkElementExists(driver, hmcPage.B2BCustomer_1stSearchedResult, 10)){
			editB2BCustomer(B2BCustomer,defaultUnit,defaultDMU,AccessLevel);
		} else {
			creatB2BCustomer(B2BCustomer,defaultUnit,defaultDMU,AccessLevel);
		}
	}
	
	public void creatB2BCustomer(String B2BCustomer,String defaultUnit,String defaultDMU,String AccessLevel){
		if(!Common.checkElementExists(driver, hmcPage.Home_B2BCustomer, 10)){
			hmcPage.Home_B2BCommerceLink.click();
		}
		Common.rightClick(driver, hmcPage.Home_B2BCustomer);
		hmcPage.Home_CreateB2BCustomer.click();
		System.out.println("Create B2BCustomer: "+B2BCustomer);
		hmcPage.B2BCustomer_IDInput.clear();
		hmcPage.B2BCustomer_IDInput.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_NameInput.clear();
		hmcPage.B2BCustomer_NameInput.sendKeys(B2BCustomer);
		if(!Common.isElementExist(driver, 
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='punchOutCustomer']"))){
			hmcPage.B2BCustomer_GroupsInput.sendKeys("punchOutCustomer");
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")).click();
		}
		hmcPage.B2BCustomer_AccessLevelTab.click();
		hmcPage.B2BCustomer_AccessLevelInput.clear();
		hmcPage.B2BCustomer_AccessLevelInput.sendKeys(AccessLevel);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")).click();
		hmcPage.B2BCustomer_DefaultUnitInput.clear();
		hmcPage.B2BCustomer_DefaultUnitInput.sendKeys(defaultUnit);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")).click();
		hmcPage.B2BCustomer_DefaultDMUInput.clear();
		hmcPage.B2BCustomer_DefaultDMUInput.sendKeys(defaultDMU);
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")), 15);
		driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")).click();
		hmcPage.B2BCustomer_AddressesTab.click();
		hmcPage.B2BCustomer_EmailInput.clear();
		hmcPage.B2BCustomer_EmailInput.sendKeys("sample@lenovo.com");
		hmcPage.B2BCustomer_PasswordTab.click();
		hmcPage.B2BCustomer_ActiveStatus.click();
		hmcPage.B2BCustomer_CreateButton.click();
		System.out.println("Create B2BCustomer successfully!");
	}
	
	public void editB2BCustomer(String B2BCustomer,String defaultUnit,String defaultDMU,String AccessLevel){
		Common.doubleClick(driver, hmcPage.B2BCustomer_1stSearchedResult);
		System.out.println("Edit B2BCustomer: "+B2BCustomer);
		hmcPage.B2BCustomer_IDEdit.clear();
		hmcPage.B2BCustomer_IDEdit.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_NameEdit.clear();
		hmcPage.B2BCustomer_NameEdit.sendKeys(B2BCustomer);
		if(!Common.isElementExist(driver, 
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='punchOutCustomer']"))){
			hmcPage.B2BCustomer_GroupsInput.sendKeys("punchOutCustomer");
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")).click();
		}
		hmcPage.B2BCustomer_AccessLevelTab.click();
		hmcPage.B2BCustomer_AccessLevelEdit.clear();
		hmcPage.B2BCustomer_AccessLevelEdit.sendKeys(AccessLevel);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")).click();
		hmcPage.B2BCustomer_DefaultUnitEdit.clear();
		hmcPage.B2BCustomer_DefaultUnitEdit.sendKeys(defaultUnit);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")).click();
		hmcPage.B2BCustomer_DefaultDMUEdit.clear();
		hmcPage.B2BCustomer_DefaultDMUEdit.sendKeys(defaultDMU);
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")), 15);
		driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")).click();
		hmcPage.B2BCustomer_AddressesTab.click();
		hmcPage.B2BCustomer_EmailEdit.clear();
		hmcPage.B2BCustomer_EmailEdit.sendKeys("sample@lenovo.com");
		hmcPage.B2BCustomer_PasswordTab.click();
		hmcPage.B2BCustomer_ActiveAccountDropdownValue.click();
		hmcPage.baseStore_save.click();
		System.out.println("Edit B2BCustomer successfully!");
	}
	
	public void  checkPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password){
		if(!Common.checkElementExists(driver, hmcPage.Home_Punchout_Credential, 10)){
			if(!Common.checkElementExists(driver, hmcPage.Home_Nemo_Punchout, 10)){
				hmcPage.Home_Nemo.click();
				hmcPage.Home_Nemo_Punchout.click();
			} else {
				hmcPage.Home_Nemo_Punchout.click();
			}
		}
		hmcPage.Home_Punchout_Credential.click();
		hmcPage.PunchoutCredential_CustomerSearch.clear();
		hmcPage.PunchoutCredential_CustomerSearch.sendKeys(B2BCustomer);
		String xpath = ".//*[@id='Content/AutocompleteReferenceEditor[in Content/GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']";
		if(Common.isElementExist(driver, By.xpath(xpath))){
			driver.findElement(By.xpath(xpath)).click();
			Common.sleep(1000);
			hmcPage.PunchoutCredential_SearchButton.click();
			if(Common.checkElementExists(driver, hmcPage.PunchoutCredential_1stSearchedResult, 10)){
				Common.doubleClick(driver, hmcPage.PunchoutCredential_1stSearchedResult);
				editPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret);
			} else {
				creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret);
			}
		} else {
			creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret);
		}
	}
	
	public void creatPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret){
		Common.rightClick(driver, hmcPage.Home_Punchout_Credential);
		hmcPage.Home_CreatePunchoutCredential.click();
		System.out.println("Create Punchout Credential!");
		hmcPage.PunchoutCredential_CustomerInput.clear();
		hmcPage.PunchoutCredential_CustomerInput.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		createCredentialAriba(Code,Domain,Identity,SharedSecret);
		hmcPage.PunchoutCredential_CreateButton.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	
	public void editPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret){
		System.out.println("Edit Punchout Credential!");
		//driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		for(int i=0;i<3;i++){
			if(Common.isElementExist(driver, By.xpath("//div[contains(text(),'PunchOut Credentials')]/../../td[last()]//table//table//table//input"))){
				Common.rightClick(driver, driver.findElement(By.xpath("//div[contains(text(),'PunchOut Credentials')]/../../td[last()]//table//table//table//input")));
				hmcPage.PunchoutCredential_RemoveAribaCredential.click();
				Common.sleep(2000);
				Alert alert =  driver.switchTo().alert();
				alert.accept();
				System.out.println("Remove Ariba/OCI/Oracle Punchout Credential!");
				Common.sleep(8000);
			}
		}	
		addCredentialAriba(Code,Domain,Identity,SharedSecret);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	
	public void createCredentialAriba(String Code,String Domain,String Identity,String SharedSecret ){
		Common.rightClick(driver, hmcPage.PunchoutCredential_AribaCredential);
		hmcPage.PunchoutCredential_CreateAribaCredential.click();
		System.out.println("Create Credential Ariba!");
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
		if(Common.checkElementExists(driver, b2bPage.PunchoutCredentials_OkButton, 10)){
			b2bPage.PunchoutCredentials_OkButton.click();
			driver.close();
			switchToWindow(0);
			addCredentialAriba(Code,Domain,Identity,SharedSecret);
		} else {
			driver.close();
			switchToWindow(0);
		}
		System.out.println("Create Credential Ariba successfully!");
	}
	
	public void addCredentialAriba(String Code,String Domain,String Identity,String SharedSecret ){
		Common.rightClick(driver, hmcPage.PunchoutCredential_AribaCredential);
		hmcPage.PunchoutCredential_AddAribaCredential.click();
		System.out.println("Add Credential Ariba!");
		switchToWindow(1);
		hmcPage.PunchoutAribaCredential_CodeSearchInput.clear();
		hmcPage.PunchoutAribaCredential_CodeSearchInput.sendKeys(Code);
		hmcPage.Contract_searchbutton.click();
		if(!Common.isElementExist(driver, By.xpath(".//*[@id='StringDisplay["+Code+"]_span']"))){
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			switchToWindow(0);
			createCredentialAriba(Code,Domain,Identity,SharedSecret);
		}else{
			driver.findElement(By.xpath(".//*[@id='StringDisplay["+Code+"]_span']")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			System.out.println("Add Credential Ariba successfully!");
			switchToWindow(0);
		}
	}
	
	public void checkPunchoutProfile(String ProfileName,String B2BCustomer){
		hmcPage.Home_Punchout_CustomerProfile.click();
		Select selScope =new Select(hmcPage.PunchoutProfile_SelectProfileNameScope);
		selScope.selectByVisibleText("is equal");
		hmcPage.PunchoutProfile_NameSearch.clear();
		hmcPage.PunchoutProfile_NameSearch.sendKeys(ProfileName);
		hmcPage.Contract_searchbutton.click();
		if(Common.checkElementExists(driver, hmcPage.PunchoutProfile_1stSearchedResult, 10)){
			Common.rightClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
			hmcPage.PunchoutProfile_removeResult.click();
			driver.switchTo().alert().accept();
			System.out.println("Remove Punchout Profile!");
			creatPunchoutProfile(ProfileName,B2BCustomer);
		} else {
			creatPunchoutProfile(ProfileName,B2BCustomer);
		}
	} 
	
	public void creatPunchoutProfile(String ProfileName,String B2BCustomer){
		Common.sleep(1000);
		Common.rightClick(driver, hmcPage.Home_Punchout_CustomerProfile);
		hmcPage.Home_CreatePunchoutProfile.click();
		System.out.println("Create Punchout Profile!");
		hmcPage.PunchoutProfile_NameInput.clear();
		hmcPage.PunchoutProfile_NameInput.sendKeys(ProfileName);
		AddCustomerForProfile(B2BCustomer);
		if(hmcPage.PunchoutProfile_WhetherActive.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_Active.click();
		}
		if(hmcPage.PunchoutProfile_WhetherActiveCxml.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOxml.click();
		}
		if(hmcPage.PunchoutProfile_WhetherActiveCustomCartCxml.getAttribute("value").equals("true")){
			hmcPage.PunchoutProfile_CustomCartCxml.click();
		}
		hmcPage.PunchoutProfile_OracleTab.click();
		if(hmcPage.PunchoutProfile_WhetherActiveOracle.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOracle.click();
		}
		if(hmcPage.PunchoutProfile_WhetherActivCustomCartOracle.getAttribute("value").equals("true")){
			hmcPage.PunchoutProfile_CustomCartOracle.click();
		}
		hmcPage.PunchoutProfile_cXMLTab.click();
		AddOutBoundRule1(outboundRule1FieldName,Target);
		AddOutBoundRule4(outboundRule4Field,ExtrinsicName);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		AndCommodityMapping(Category,UNSPSC,CommodityCode);
		AndCommodityMapping(Category2,UNSPSC2,CommodityCode2);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Profile successfully!");
	}
	
	public void editPunchoutProfile(String ProfileName){
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_Nemo_Punchout.click();
		hmcPage.Home_Punchout_CustomerProfile.click();
		Select selScope =new Select(hmcPage.PunchoutProfile_SelectProfileNameScope);
		selScope.selectByVisibleText("is equal");
		hmcPage.PunchoutProfile_NameSearch.clear();
		hmcPage.PunchoutProfile_NameSearch.sendKeys(ProfileName);
		hmcPage.Contract_searchbutton.click();
		if(Common.checkElementExists(driver, hmcPage.PunchoutProfile_1stSearchedResult, 10)){
			Common.rightClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
			hmcPage.PunchoutProfile_EditProfile.click();
			if(hmcPage.PunchoutProfile_WhetherActiveCustomCartCxmlEdit.getAttribute("value").equals("false")){
				hmcPage.PunchoutProfile_CustomCartCxmlEdit.click();
			}
			hmcPage.PunchoutProfile_OracleTab.click();
			if(hmcPage.PunchoutProfile_WhetherActivCustomCartOracleEdit.getAttribute("value").equals("false")){
				hmcPage.PunchoutProfile_CustomCartOracleEdit.click();
			}
			hmcPage.PaymentLeasing_saveAndCreate.click();
			System.out.println("Edit Punchout Profile successfully!");
		}		
	}
	
	public void AddOutBoundRule1(String outboundRule1FieldName,String Target){
		Common.rightClick(driver, hmcPage.PunchoutProfile_cXMLOutboundRule1);
		hmcPage.PunchoutProfile_cXMLCreateOutboundRule1.click();
		Select  sel =new Select(hmcPage.PunchoutProfile_cXMLOutboundRule1FiledNameSelect);
		sel.selectByVisibleText(outboundRule1FieldName);
		hmcPage.PunchoutProfile_cXMLOutboundRule1TargetInput.clear();
		hmcPage.PunchoutProfile_cXMLOutboundRule1TargetInput.sendKeys(Target);
	}
	
	
	public void AddOutBoundRule4(String outboundRule4Field,String ExtrinsicName){
		Common.rightClick(driver, hmcPage.PunchoutProfile_cXMLOutboundRule4);
		hmcPage.PunchoutProfile_cXMLCreateOutboundRule4.click();
		Select  sel =new Select(hmcPage.PunchoutProfile_cXMLOutboundRule4FiledSelect);
		sel.selectByVisibleText(outboundRule4Field);
		hmcPage.PunchoutProfile_cXMLOutboundRule4ExtrinsicNameInput.clear();
		hmcPage.PunchoutProfile_cXMLOutboundRule4ExtrinsicNameInput.sendKeys(ExtrinsicName);
	}
	
	public void AndCommodityMapping(String Category,String UNSPSC,String CommodityCode){
		Common.rightClick(driver, hmcPage.PunchoutProfile_cXMLCommodityMapping);
		hmcPage.PunchoutProfile_cXMLCreateCommodityMapping.click();
		hmcPage.PunchoutProfile_CreateMappingRulesEntry.get(0).clear();
		hmcPage.PunchoutProfile_CreateMappingRulesEntry.get(0).sendKeys(Category);
		hmcPage.PunchoutProfile_CreateMappingRulesEntry.get(1).clear();
		hmcPage.PunchoutProfile_CreateMappingRulesEntry.get(1).sendKeys(UNSPSC);
		hmcPage.PunchoutProfile_CreateMappingRulesEntry.get(2).clear();
		hmcPage.PunchoutProfile_CreateMappingRulesEntry.get(2).sendKeys(CommodityCode);
	}
	
	public void AddCustomerForProfile(String B2BCustomer){
		Common.rightClick(driver, hmcPage.PunchoutProfile_CustomerField);
		hmcPage.PunchoutCredential_AddAribaCredential.click();
		System.out.println("Add Customer For Profile!");
		switchToWindow(1);
		hmcPage.PunchoutProfile_CustomerInput.clear();
		hmcPage.PunchoutProfile_CustomerInput.sendKeys(B2BCustomer);
		Common.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='AutocompleteReferenceEditor[in GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		hmcPage.Contract_searchbutton.click();
		driver.findElement(By.xpath(".//*[contains(@id,'ItemDisplay["+B2BCustomer+"]')]")).click();
		driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
		System.out.println("Add Customer For Profile successfully!");
		switchToWindow(0);
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
}
	
			