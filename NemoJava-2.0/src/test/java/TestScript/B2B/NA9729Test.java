package TestScript.B2B;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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



public class NA9729Test extends SuperTestClass {
	public String Punchout_Ariba_URL;
	public String HMCURL;
	HMCPage hmcPage ;
	B2BPunchoutPage page;
	B2BPage b2bPage;
	String B2BCustomer = "NA-253-001";
	String defaultUnit = "1213410863";
	String defaultDMU = "1213348423";
	String AccessLevel = "1213348423";
	String serviceNumber="5PS0A23193";
	public NA9729Test(String Store){
		this.Store = Store;
		this.testName = "NA-9729";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA9729(ITestContext ctx){
		try{
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			page = new B2BPunchoutPage(driver);
			b2bPage = new B2BPage(driver);
			HMCURL = testData.HMC.getHomePageUrl();
			Boolean isContract =  true;
			String Domain = "NetworkID";
			String Identity = "NA-253-001";
			String SharedSecret = "aaa";
			String Code = "NA-253-001";
			String ProfileName = "NA-253-001#AU#default";
			String ProfileName2 = "NA-253-001#AU#soldto6";
			String[]  Rules3_1= {"Contract_contract1_contract1",
					};
			String[]  Rules3_2= {"Contract_contract2_contract2",
					};
			String[]  Rules4_1= {"Constant_aaa_aaa",
					};
			String[]  Rules4_2= {"Constant_bbb_bbb",
					};
			String[]  BundleRules_1= {"000_000_000_000"
					};
			String[]  BundleRules_2= {"1_CompanyCode_002_1213577815",
					};
			Punchout_Ariba_URL = testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/ariba";
			driver.get(HMCURL);

//	
			
			
			//sss
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(1,"Login HMC successfully.", Store,testName);
			
			checkB2BCustomer(B2BCustomer,"punchOutCustomer","B2BCustomer");
			Dailylog.logInfoDB(1,"Create B2BCustome, and check if exist the custome, edit it successfully.", Store,testName);
			
			checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,B2BCustomer,"SharedSecret");
			Dailylog.logInfoDB(1,"Create Punchout Credential, and check if exist, edit it successfully.", Store,testName);

			SearchProfile(driver,hmcPage,ProfileName);
			DeleteProfile(driver,hmcPage,ProfileName);
			CreateProfile(driver,hmcPage,ProfileName,B2BCustomer,BundleRules_1,Rules3_1,Rules4_1);
			hmcPage.Home_EndSessionLink.click();
			driver.manage().deleteAllCookies();
			Common.sleep(2000);
			driver.get(HMCURL);
			HMCCommon.Login(hmcPage, testData);
			SearchProfile(driver,hmcPage,ProfileName2);
			DeleteProfile(driver,hmcPage,ProfileName2);
			CreateProfile(driver,hmcPage,ProfileName2,B2BCustomer,BundleRules_2,Rules3_2,Rules4_2);
			Dailylog.logInfoDB(3,"Create Profile Successfully",Store,testName);
			hmcPage.Home_EndSessionLink.click();  
			
			
			driver.get(Punchout_Ariba_URL);	
			B2BCommon.punchoutLogin(driver,page);
			driver.findElement(By.xpath("//input[@id='userName']")).clear();
			driver.findElement(By.xpath("//input[@id='userName']")).sendKeys("LIeCommerce");
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("M0C0v0n3L!");
			page.Ariba_DomainTextBox.clear();
			page.Ariba_DomainTextBox.sendKeys(Domain);
			page.Ariba_IdentityTextBox.clear();
			page.Ariba_IdentityTextBox.sendKeys(Identity);
			page.Ariba_SharedSecretTextBox.clear();
			page.Ariba_SharedSecretTextBox.sendKeys(SharedSecret);
			page.Ariba_PunchoutButton.click();
			Common.sleep(18000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
			assert page.Lenovo_Link.getAttribute("href").contains("1213410863");
			Dailylog.logInfoDB(5,"punchout to default sold to successfully",Store,testName);
			
			
			saveOCICart(serviceNumber);
	        Common.sleep(8000);
			
	        
	        driver.switchTo().defaultContent();
			String XML = driver.findElement(By.xpath(".//*[@id='orderList']/xmp[1]")).getText();
			assert XML.contains("contract1=\"contract1\"");
			assert XML.contains("<Extrinsic name=\"aaa\">aaa</Extrinsic>");
			
			page.Ariba_DomainTextBox.clear();
			page.Ariba_DomainTextBox.sendKeys(Domain);
			page.Ariba_IdentityTextBox.clear();
			page.Ariba_IdentityTextBox.sendKeys(Identity);
			page.Ariba_SharedSecretTextBox.clear();
			page.Ariba_SharedSecretTextBox.sendKeys(SharedSecret);
			page.Ariba_AddExtrinsicButton.click();
			page.Ariba_SuppCode.clear();
			page.Ariba_InboundKeyTextBox.clear();
			page.Ariba_InboundKeyTextBox.sendKeys("CompanyCode");
			page.Ariba_InboundValueTextBox.clear();
			page.Ariba_InboundValueTextBox.sendKeys("002");
			page.Ariba_PunchoutButton.click();
			Common.sleep(8000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
			assert page.Lenovo_Link.getAttribute("href").contains("1213577815");
			Dailylog.logInfoDB(6,"punchout to Extrinsic sold to successfully",Store,testName);
			
			saveOCICart(serviceNumber);
	        Common.sleep(8000);
	        driver.switchTo().defaultContent();
	        XML = driver.findElement(By.xpath(".//*[@id='orderList']/xmp[1]")).getText();
			assert XML.contains("contract2=\"contract2\"");
			assert XML.contains("<Extrinsic name=\"bbb\">bbb</Extrinsic>");
	        
		}catch(Throwable e){
			handleThrowable(e, ctx);			
		}
		
	}
	
	public void saveOCICart(String partNumber) throws InterruptedException{
		page.B2Bsite_service.click();
		System.out.println("xxxxxxxx'");
		Common.sleep(8000);
		driver.findElement(By.xpath(".//*[@id='addToCartForm"+partNumber+"']/button")).click();

		//driver.findElement(By.xpath(".//*[@id='addToCartForm"+partNumber+"']/button")).click();
		driver.findElement(By.xpath(".//*[@id='addtoCartModal"+partNumber+"']/aside/div[3]/button")).click();
		
		Thread.sleep(5000);
		Common.javascriptClick(driver, driver.findElement(By.xpath((".//*[@id='addtoCartModal"+partNumber+"']/aside/div[3]/a[2]"))));
		Common.isElementExist(driver, By.id("validateDateformatForCheckout"), 20);
		driver.findElement(By.id("validateDateformatForCheckout")).click();
		
		switchToWindow(0);
		Thread.sleep(5000);
	}
	public void SearchCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer){
		hmcPage.Home_Nemo.click();
		hmcPage.Home_Nemo_Punchout.click();
		hmcPage.Home_Punchout_Credential.click();
		hmcPage.PunchoutCredential_CustomerSearch.clear();
		hmcPage.PunchoutCredential_CustomerSearch.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		hmcPage.Contract_searchbutton.click();	
		
	}
	public void  checkPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password) throws InterruptedException{
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
				editPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			} else {
				creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret);
			}
		} else {
			creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret);
		}
	}
	public void creatPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret) throws InterruptedException{
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
	public void createCredentialAriba(String Code,String Domain,String Identity,String SharedSecret ) throws InterruptedException{
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
		Thread.sleep(3000);
		System.out.println("Create Credential Ariba successfully!");
		driver.close();
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
	public void  checkB2BCustomer(String B2BCustomer, String group, String customerType){

		hmcPage.Home_B2BCommerceLink.click();

		hmcPage.Home_B2BCustomer.click();
		if(!Common.checkElementExists(driver, hmcPage.B2BCustomer_SearchIDTextBox, 5)){
			hmcPage.B2BCustomer_SearchToggle.click();
		}
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_SearchButton.click();
		if(Common.checkElementExists(driver, hmcPage.B2BCustomer_1stSearchedResult, 10)){
			editB2BCustomer(B2BCustomer,group,customerType);
		} else {
			creatB2BCustomer(B2BCustomer,group,customerType);
		}
	}
	public void creatB2BCustomer(String B2BCustomer,String group, String customerType){
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
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='"+group+"']"))){
			hmcPage.B2BCustomer_GroupsInput.sendKeys(group);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")).click();
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
	public void editB2BCustomer(String B2BCustomer,String group, String customerType){
		Common.doubleClick(driver, hmcPage.B2BCustomer_1stSearchedResult);
		System.out.println("Edit B2BCustomer: "+B2BCustomer);
		hmcPage.B2BCustomer_IDEdit.clear();
		hmcPage.B2BCustomer_IDEdit.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_NameEdit.clear();
		hmcPage.B2BCustomer_NameEdit.sendKeys(B2BCustomer);
		if(!Common.isElementExist(driver, 
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='"+group+"']"))){
			hmcPage.B2BCustomer_GroupsInput.sendKeys(group);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")).click();
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
	
	public void SearchProfile(WebDriver driver,HMCPage hmcPage,String ProfileName){
		if(!Common.checkElementExists(driver, hmcPage.Home_Nemo_Punchout, 5)){
			hmcPage.Home_Nemo.click();
			System.out.println(Common.checkElementExists(driver, hmcPage.Home_Punchout_CustomerProfile, 5));
			if(!Common.checkElementExists(driver, hmcPage.Home_Punchout_CustomerProfile, 5)){
				hmcPage.Home_Nemo_Punchout.click();
			}
		}
		hmcPage.Home_Punchout_CustomerProfile.click();
		hmcPage.PunchoutProfile_NameEqual.click();
		hmcPage.PunchoutProfile_NameSearch.clear();
		hmcPage.PunchoutProfile_NameSearch.sendKeys(ProfileName);
		//driver.findElement(By.xpath(".//*[@id='Content/StringDisplay["+ProfileName+"]_span']")).click();
		hmcPage.Contract_searchbutton.click();	
		
	}
	
	public void EditCustomer(WebDriver driver, HMCPage hmcPage,String B2BCustomer,String defaultUnit,String defaultDMU,String AccessLevel){
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
		hmcPage.Common_SaveButton.click();
	}
	
	public void CreateCustomer(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String defaultUnit,String defaultDMU,String AccessLevel){
		Common.rightClick(driver, hmcPage.Home_B2BCustomer);
		hmcPage.Home_CreateB2BCustomer.click();
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
	}
	
	
	public void CreateCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity,String SharedSecret){
		hmcPage.Home_Punchout_Credential.click();
		Common.rightClick(driver, hmcPage.Home_Punchout_Credential);
		hmcPage.Home_CreatePunchoutCredential.click();
		hmcPage.PunchoutCredential_CustomerInput.clear();
		hmcPage.PunchoutCredential_CustomerInput.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		AddAribaCredential(driver,hmcPage,Code,Domain,Identity,SharedSecret);
		hmcPage.PunchoutCredential_CreateButton.click();
	}
	
	public void EditCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity,String SharedSecret){
		if(Common.isElementExist(driver, By.xpath("(.//div[contains(text(),'PunchOut Credentials')])[2]/../../td[last()]//table//table//table//input"))){
			Common.rightClick(driver, driver.findElement(By.xpath("(.//div[contains(text(),'PunchOut Credentials')])[2]/../../td[last()]//table//table//table//input")));
			hmcPage.PunchoutCredential_RemoveAribaCredential.click();
			Common.sleep(2000);
			Alert alert =  driver.switchTo().alert();
			alert.accept();
			Common.sleep(8000);
		}
		AddAribaCredential(driver,hmcPage,Code,Domain,Identity,SharedSecret);
		
		hmcPage.PaymentLeasing_saveAndCreate.click();
	}
	
	
	public void AddAribaCredential(WebDriver driver,HMCPage hmcPage,String Code,String Domain,String Identity,String SharedSecret){
		String mainHandle = driver.getWindowHandle();
		Common.rightClick(driver, hmcPage.PunchoutCredential_AribaCredential);
		hmcPage.PunchoutCredential_AddAribaCredential.click();
		Set<String> allHandles = driver.getWindowHandles();
		for(String secondHandle:allHandles){
			if(secondHandle!=mainHandle){
				driver.switchTo().window(secondHandle);
			}	
		}
		hmcPage.PunchoutAribaCredential_CodeSearchInput.clear();
		hmcPage.PunchoutAribaCredential_CodeSearchInput.sendKeys(Code);
		hmcPage.Contract_searchbutton.click();
		if(!Common.isElementExist(driver, By.xpath(".//*[@id='StringDisplay["+Code+"]_span']"))){
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			driver.switchTo().window(mainHandle);
			CreateAribaCredential(driver,hmcPage,Code,Domain,Identity,SharedSecret);
		}else{
			driver.findElement(By.xpath(".//*[@id='StringDisplay["+Code+"]_span']")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			driver.switchTo().window(mainHandle);
		}
	}
	
	public void CreateAribaCredential(WebDriver driver,HMCPage hmcPage,String Code,String Domain,String Identity,String SharedSecret){
		
		String mainHandle = driver.getWindowHandle();
		Common.rightClick(driver, hmcPage.PunchoutCredential_AribaCredential);
		hmcPage.PunchoutCredential_CreateAribaCredential.click();
		Set<String> allHandles = driver.getWindowHandles();
		for(String secondHandle:allHandles){
			if(secondHandle!=mainHandle){
				driver.switchTo().window(secondHandle);
			}	
		}
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
	
	public void CreateProfile(WebDriver driver,HMCPage hmcPage,String ProfileName,String B2BCustomer,String[] InboundRule,String[] Rule3,String[] Rule4) throws InterruptedException{
		hmcPage.Home_Punchout_CustomerProfile.click();
		Common.rightClick(driver, hmcPage.Home_Punchout_CustomerProfile);
		hmcPage.Home_CreatePunchoutProfile.click();
		hmcPage.PunchoutProfile_NameInput.clear();
		hmcPage.PunchoutProfile_NameInput.sendKeys(ProfileName);
		/*if(driver.findElement(By.xpath(".//*[@id='Content/BooleanEditor[in Content/Attribute[.isActivate]]_checkbox']/../input[1]")).equals("true")){
			hmcPage.PunchoutProfile_Active.click();	
		}*/
		AddCustomerForProfile(B2BCustomer);
		/*if(driver.findElement(By.xpath(".//*[@id='Content/BooleanEditor[in Content/Attribute[.cxmlActive]]_checkbox']/../input[1]")).equals("true")){
			hmcPage.PunchoutProfile_ActiveOxml.click();	
		}*/
		hmcPage.PunchoutProfile_ActiveOxml.click();
		hmcPage.PunchoutProfile_ActiveCustomCart.click();
		System.out.println(InboundRule.length);
		for(int i = 0;i<InboundRule.length;i++){
			AddBoundRule(driver,hmcPage,InboundRule[i]);
		}
		for(int i = 0;i<Rule3.length;i++){
			AddRule3(driver,hmcPage,Rule3[i]);
		}
		for(int i = 0;i<Rule4.length;i++){
			AddRule4(driver,hmcPage,Rule4[i]);
		}
		hmcPage.PunchoutCredential_CreateButton.click();
	}
	
	public void AddRule3(WebDriver driver,HMCPage hmcPage,String Rule3){
		
		String field = Rule3.split("_")[0];
		String name = Rule3.split("_")[1];
		String value = Rule3.split("_")[2];
		if(!field.equals(name)){
			Common.rightClick(driver, driver.findElement(By.xpath(".//*[text()='Key Name']")));
			driver.findElement(By.xpath(".//*[text()='Create Variable or Constant Key/Value Pair']")).click();
			Common.sleep(2000);
			driver.findElement(By.xpath(".//*[contains(text(),'Variable or Constant Key/Value Pairs:')]/../../td[last()]//table//table//table//table//select/option[contains(text(),'"+field+"')]")).click();
			driver.findElement(By.xpath("(.//*[contains(text(),'Variable or Constant Key/Value Pairs:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[1]")).clear();
			driver.findElement(By.xpath("(.//*[contains(text(),'Variable or Constant Key/Value Pairs:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(name);
			driver.findElement(By.xpath("(.//*[contains(text(),'Variable or Constant Key/Value Pairs:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[2]")).clear();
			driver.findElement(By.xpath("(.//*[contains(text(),'Variable or Constant Key/Value Pairs:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(value);
			
		}
	}
	public void AddRule4(WebDriver driver,HMCPage hmcPage,String Rule4){
		String field = Rule4.split("_")[0];
		String name = Rule4.split("_")[1];
		String value = Rule4.split("_")[2];
		if(!field.equals(name)){
			Common.rightClick(driver, driver.findElement(By.xpath(".//*[text()='Extrinsic Name']")));
			driver.findElement(By.xpath(".//*[text()='Create Variable or Constant Extrinsic']")).click();
			Common.sleep(2000);
			driver.findElement(By.xpath(".//*[contains(text(),'Variable or Constant Extrinsics:')]/../../td[last()]//table//table//table//table//select/option[contains(text(),'"+field+"')]")).click();
			driver.findElement(By.xpath("(.//*[contains(text(),'Variable or Constant Extrinsics:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[1]")).clear();
			driver.findElement(By.xpath("(.//*[contains(text(),'Variable or Constant Extrinsics:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(name);
			driver.findElement(By.xpath("(.//*[contains(text(),'Variable or Constant Extrinsics:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[2]")).clear();
			driver.findElement(By.xpath("(.//*[contains(text(),'Variable or Constant Extrinsics:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(value);		
		}		
	}
	
	
	
	public void AddBoundRule(WebDriver driver,HMCPage hmcPage,String BundleRule){
		String id = BundleRule.split("_")[0];
		String name = BundleRule.split("_")[1];
		String value = BundleRule.split("_")[2];
		String unit = BundleRule.split("_")[3];
		if(!id.equals(name)){
			Common.rightClick(driver, driver.findElement(By.xpath(".//*[text()='Group ID']")));
			driver.findElement(By.xpath(".//*[text()='Create Sold To Determination']")).click();
			Common.sleep(2000);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[1]")).clear();
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(id);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[2]")).clear();
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(name);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[3]")).clear();
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[3]")).sendKeys(value);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[4]")).clear();
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[4]")).sendKeys(unit);
			driver.findElement(By.xpath(".//*[contains(@id,'Content/AutocompleteReferenceEditor[in Content/CreateItemListEntry[n/a]')][contains(@id,'"+unit+"')]")).click();
			}
		}

	public void AddCustomerForProfile(String B2BCustomer) throws InterruptedException{
		Common.rightClick(driver, hmcPage.PunchoutProfile_CustomerField);
		hmcPage.PunchoutCredential_AddAribaCredential.click();
		System.out.println("Add Customer For Profile!");
		switchToWindow(1);
		hmcPage.PunchoutProfile_CustomerInput.clear();
		hmcPage.PunchoutProfile_CustomerInput.sendKeys(B2BCustomer);
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='AutocompleteReferenceEditor[in GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		hmcPage.Contract_searchbutton.click();
		driver.findElement(By.xpath(".//*[contains(@id,'ItemDisplay["+B2BCustomer+"]')]")).click();
		driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
		System.out.println("Add Customer For Profile successfully!");
		switchToWindow(0);
	}
	public void DeleteProfile(WebDriver driver,HMCPage hmcPage,String ProfileName){
		while(Common.isElementExist(driver, By.xpath(".//td[contains(@id,'Content/StringDisplay["+ProfileName+"]')]"))){
			Common.rightClick(driver, driver.findElement(By.xpath(".//td[contains(@id,'Content/StringDisplay["+ProfileName+"]')]")));
			hmcPage.PunchoutCredential_RemoveAribaCredential.click();
			Common.sleep(2000);
			Alert alert =  driver.switchTo().alert();
			alert.accept();
			Common.sleep(8000);
			
		}
	}
	public void editPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password) throws InterruptedException{
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
		addCredentialOCI(Code,UserName, Password);
		addCredentialOracle(Code,UserName, Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	
	public void addCredentialOracle(String Code,String Name,String Password) throws InterruptedException{
		Common.rightClick(driver, hmcPage.PunchoutCredential_OracleCredential);
		hmcPage.PunchoutCredential_AddOracleCredential.click();
		System.out.println("Add Credential Oracle!");
		switchToWindow(1);
		hmcPage.PunchoutOracleCredential_CodeSearchInput.clear();
		hmcPage.PunchoutOracleCredential_CodeSearchInput.sendKeys(Code);
		hmcPage.Contract_searchbutton.click();
		if(!Common.isElementExist(driver, By.xpath(".//div[contains(@id,'StringDisplay["+Code+"]')]"))){
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			switchToWindow(0);
			createCredentialOCI(Code,Name,Password);
		}else{
			driver.findElement(By.xpath(".//div[contains(@id,'StringDisplay["+Code+"]')]")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			System.out.println("Add Credential Oracle successfully!");
			switchToWindow(0);
		}
	}
	public void createCredentialOCI(String Code,String UserName,String Password) throws InterruptedException{
		Common.rightClick(driver, hmcPage.PunchoutCredential_OCICredential);
		hmcPage.PunchoutCredential_CreatOCICredential.click();
		System.out.println("Create Credential OCI!");
		switchToWindow(1);
		hmcPage.PunchoutOCIOracleCredential_CodeInput.clear();
		hmcPage.PunchoutOCIOracleCredential_CodeInput.sendKeys(Code);
		hmcPage.PunchoutOCICredential_UserNameInput.clear();
		hmcPage.PunchoutOCICredential_UserNameInput.sendKeys(UserName);
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.clear();
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.sendKeys(Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		Thread.sleep(3000);
		System.out.println("Create Credential OCI successfully!");
		driver.close();
		switchToWindow(0);
	}
	public void addCredentialOCI(String Code,String UserName,String Password) throws InterruptedException{
		Common.rightClick(driver, hmcPage.PunchoutCredential_OCICredential);
		hmcPage.PunchoutCredential_AddOCICredential.click();
		System.out.println("Add Credential OCI!");
		switchToWindow(1);
		hmcPage.PunchoutOCICredential_CodeSearchInput.clear();
		hmcPage.PunchoutOCICredential_CodeSearchInput.sendKeys(Code);
		hmcPage.Contract_searchbutton.click();
		if(!Common.isElementExist(driver, By.xpath(".//div[contains(@id,'StringDisplay["+Code+"]')]"))){
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			switchToWindow(0);
			createCredentialOCI(Code,UserName,Password);
		}else{
			driver.findElement(By.xpath(".//div[contains(@id,'StringDisplay["+Code+"]')]")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			System.out.println("Add Credential OCI successfully!");
			switchToWindow(0);
		}
	}
	public void addCredentialAriba(String Code,String Domain,String Identity,String SharedSecret ) throws InterruptedException{
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
}

