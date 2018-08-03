package TestScript.B2B;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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



public class NA13518Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BPunchoutPage punchoutPage;
	String oxmlUrl;
	String defaultUnit = "1213577815";
	String defaultDMU = "1213348423";
	String AccessLevel = "1213348423";
	String B2BCustomer1 = "NA-9374-001";
	String B2BCustomer2 = "NA-9374-002";
	String B2BCustomerGroups = "punchOutCustomer";
	List<String[]> tagRules =new ArrayList<String[]>();
	List<String[]> attributeRules =new ArrayList<String[]>();
	public NA13518Test(String store){
		this.Store = store;
		this.testName = "NA-13518";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA13518(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);
			
			oxmlUrl= testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/oxml";

			String Code1 = "NA-9374-001";
			String Domain1 = "NetworkID";
			String SharedSecret1 = "aaa";
			String UserName1 = "NA-9374-001";
			String Password1 = "aaa";
			String Identity1 = "NA-9374-001";
			
			String Code2 = "NA-9374-002";
			String Domain2 = "NetworkID";
			String SharedSecret2 = "aaa";
			String UserName2 = "NA-9374-002";
			String Password2 = "aaa";
			String Identity2 = "NA-9374-002";
			String ProfileName1 = "9374-001#NA#defaultsoldto-1";
			String ProfileName2 = "9374-001#NA#defaultsoldto-2";
			String ProfileName3 = "9374-002#NA#defaultsoldto-1";
			String colum = "OracleNameOraclePasswordOracleOeratingUnitOracleSoldToOracleStoreNameOracleActive";
			
			String[]  BundleRules1= {"2_operatingUnit_002_1213410863"};
			String[]  BundleRules2= {"3_operatingUnit_003_1213410863","4_operatingUnit_004_1213521232"};
			
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
				
			Dailylog.logInfoDB(2,"Create B2BCustome, and check if exist the custome, edit it.", Store,testName);
			checkB2BCustomer(B2BCustomer1,B2BCustomerGroups);
			hmcPage.HMC_Logout.click();
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(3,"Create B2BCustome, and check if exist the custome, edit it.", Store,testName);
			checkB2BCustomer(B2BCustomer2,B2BCustomerGroups);
			
			Dailylog.logInfoDB(4,"5Create Punchout Credential, and check if exist, edit it.", Store,testName);
			checkPunchoutCredential(driver,hmcPage,B2BCustomer1,Code1,Domain1,Identity1,SharedSecret1,UserName1,Password1);
			hmcPage.HMC_Logout.click();
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			Dailylog.logInfoDB(6,"7Create Punchout Credential, and check if exist, edit it.", Store,testName);
			checkPunchoutCredential(driver,hmcPage,B2BCustomer2,Code2,Domain2,Identity2,SharedSecret2,UserName2,Password2);
	
			Dailylog.logInfoDB(8,"Create punchout profile①，and check if exist, edit it.", Store,testName);
			checkPunchoutProfile(ProfileName1,B2BCustomer1,BundleRules1);
			
			loginHmc();
			Dailylog.logInfoDB(9,"Create punchout profile①，and check if exist, edit it.", Store,testName);
			checkPunchoutProfile(ProfileName2,B2BCustomer1,BundleRules2);
			
			loginHmc();
			Dailylog.logInfoDB(10,"Create punchout profile①，and check if exist, edit it.", Store,testName);
			checkPunchoutProfile(ProfileName3,B2BCustomer2,BundleRules2);
			
			Dailylog.logInfoDB(11,"Hmc->Nemo->Punch Out -> Search Profile ->\n" + 
					"Show search criteria", Store,testName);
			loginHmc();
			hmcPage.searchProfile.click();
			Dailylog.logInfoDB(12,"Search by:Profile Name <contains> 9374", Store,testName);
			selectSearchProfile(hmcPage.soldToIdSelect,"contains","9374",hmcPage.soldToId);
			List<WebElement> findElements = driver.findElements(By.xpath("//table[@id='Content/McSearchListConfigurable[NemoB2BPunchOutCustomerProfileSearch]_innertable']//tr//td[6][contains(.,'NA-9374-001')]"));
			Assert.assertEquals(findElements.size(), 3);
			List<WebElement> findElements2 = driver.findElements(By.xpath("//table[@id='Content/McSearchListConfigurable[NemoB2BPunchOutCustomerProfileSearch]_innertable']//tr//td[6][contains(.,'NA-9374-002')]"));
			Assert.assertEquals(findElements2.size(), 2);
			
			Dailylog.logInfoDB(13,"Search by:Profile Name <contains> 9374", Store,testName);
			selectSearchProfile(hmcPage.punchOutUser,"is equal","NA-9374-001",hmcPage.punchOutUserInput);
			List<WebElement> findElements3 = driver.findElements(By.xpath("//table[@id='Content/McSearchListConfigurable[NemoB2BPunchOutCustomerProfileSearch]_innertable']//tr//td[6][contains(.,'NA-9374-001')]"));
			Assert.assertEquals(findElements3.size(), 3);
			
			Dailylog.logInfoDB(14,"Search by:Profile Name <contains> 9374", Store,testName);
			loginHmc();
			hmcPage.searchProfile.click();
			selectSearchProfile(hmcPage.oracleSoldTo,"is equal","1213521232",hmcPage.oracleSoldToInput);
			Assert.assertEquals(driver.findElements(By.xpath("//table[@id='Content/McSearchListConfigurable[NemoB2BPunchOutCustomerProfileSearch]_innertable']//tr//td[6][contains(.,'NA-9374-001')]")).size(), 1);
			Assert.assertEquals(driver.findElements(By.xpath("//table[@id='Content/McSearchListConfigurable[NemoB2BPunchOutCustomerProfileSearch]_innertable']//tr//td[6][contains(.,'NA-9374-002')]")).size(), 1);
			
			Dailylog.logInfoDB(15,"Search by:Profile Name <contains> 9374", Store,testName);
			loginHmc();
			hmcPage.searchProfile.click();
			selectSearchProfile(hmcPage.oracleStoreName,"contains","Purchase",hmcPage.oracleStoreNameInput);
			Assert.assertEquals(driver.findElements(By.xpath("//table[@id='Content/McSearchListConfigurable[NemoB2BPunchOutCustomerProfileSearch]_innertable']//tr//td[6][contains(.,'NA-9374-001')]")).size(), 1);
			Assert.assertEquals(driver.findElements(By.xpath("//table[@id='Content/McSearchListConfigurable[NemoB2BPunchOutCustomerProfileSearch]_innertable']//tr//td[6][contains(.,'NA-9374-002')]")).size(), 1);
			
			Dailylog.logInfoDB(16,"Search by:Profile Name <contains> 9374", Store,testName);
			loginHmc();
			hmcPage.searchProfile.click();
			selectSearchProfile(hmcPage.oracleName,"contains","9374",hmcPage.oracleNameInput);
			Assert.assertEquals(driver.findElements(By.xpath("//table[@id='Content/McSearchListConfigurable[NemoB2BPunchOutCustomerProfileSearch]_innertable']//tr//td[6][contains(.,'NA-9374-001')]")).size(), 3);
			Assert.assertEquals(driver.findElements(By.xpath("//table[@id='Content/McSearchListConfigurable[NemoB2BPunchOutCustomerProfileSearch]_innertable']//tr//td[6][contains(.,'NA-9374-002')]")).size(), 2);
			
			Dailylog.logInfoDB(17,"Check result column:", Store,testName);
			Common.doubleClick(driver, hmcPage.searchResult);
			Common.sleep(3000);
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//table[contains(@title,'oracleName')]\n" + 
					"")), 10));
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//table[contains(@title,'oraclePassword')]\n" + 
					"")), 10));
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//table[contains(@title,'oracleSoldTo')]\n" + 
					"")), 10));
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//table[contains(@title,'oracleSoldToFieldValue')]\n" + 
					"")), 10));
			Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath("//table[contains(@title,'oracleActive')]\n" + 
					"")), 10));
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public static String[] readCSV(String fileName) throws Exception {
        List<String[]> records = new ArrayList<String[]>();
        String record;
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        file.readLine();                            
        while ((record=file.readLine())!=null){     
            String fields[] =  record.split("，");  
            records.add(fields);                    
        }
        file.close();  //关闭文件对象
		return null;
    }
	
	public void selectSearchProfile(WebElement soldToIdSelect, String searchIF2, String soldname2, WebElement soldToId) {
		driver.navigate().refresh();
		Select selScope =new Select(soldToIdSelect);
		selScope.selectByVisibleText(searchIF2);
		soldToId.clear();
		soldToId.sendKeys(soldname2);
		if(soldname2.equals("1213521232")) {
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldname2+"')][text()='"+soldname2+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldname2+"')][text()='"+soldname2+"']")).click();
		}
		hmcPage.Contract_searchbutton.click();
	}

	public void loginHmc() {
		hmcPage.HMC_Logout.click();
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_Nemo_Punchout.click();
	}

	public void checkPunchoutProfile(String ProfileName,String B2BCustomer,String[] InboundRule){
		hmcPage.Home_Punchout_CustomerProfile.click();
		Select selScope =new Select(hmcPage.PunchoutProfile_SelectProfileNameScope);
		selScope.selectByVisibleText("is equal");
		hmcPage.PunchoutProfile_NameSearch.clear();
		hmcPage.PunchoutProfile_NameSearch.sendKeys(ProfileName);
		hmcPage.Contract_searchbutton.click();
		if(Common.checkElementExists(driver, hmcPage.PunchoutProfile_copy1, 10)) {
			Common.rightClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
			hmcPage.PunchoutProfile_removeResult.click();
			driver.switchTo().alert().accept();
		}
		if(Common.checkElementExists(driver, hmcPage.PunchoutProfile_1stSearchedResult, 10)){
			Common.rightClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
			hmcPage.PunchoutProfile_removeResult.click();
			driver.switchTo().alert().accept();
			System.out.println("Remove Punchout Profile!");
			creatPunchoutProfile(ProfileName,B2BCustomer,InboundRule);
		} else {
			creatPunchoutProfile(ProfileName,B2BCustomer,InboundRule);
		}
	}
	
	public void creatPunchoutProfile(String ProfileName,String B2BCustomer,String[] InboundRule){
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
		
		hmcPage.PunchoutProfile_OracleTab.click();
		if(hmcPage.PunchoutProfile_WhetherActiveOracle.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOracle.click();
		}
		
		for(int i = 0;i<InboundRule.length;i++){
			AddBoundRule(driver,hmcPage,InboundRule[i]);
		}
		
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Profile successfully!");
	}
	
	public void AddBoundRule(WebDriver driver,HMCPage page1,String BundleRule){
		String id = BundleRule.split("_")[0];
		String name = BundleRule.split("_")[1];
		String value = BundleRule.split("_")[2];
		String unit = BundleRule.split("_")[3];
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
		Common.scrollToElement(driver, hmcPage.PunchoutCredential_CustomerSearch);
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
				creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			}
		} else {
			creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
		}
	}
	
	public void creatPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password) throws InterruptedException{
		Common.rightClick(driver, hmcPage.Home_Punchout_Credential);
		hmcPage.Home_CreatePunchoutCredential.click();
		System.out.println("Create Punchout Credential!");
		hmcPage.PunchoutCredential_CustomerInput.clear();
		hmcPage.PunchoutCredential_CustomerInput.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
	
		createCredentialOracle(Code,UserName, Password);
		hmcPage.PunchoutCredential_CreateButton.click();
		System.out.println("Create Punchout Credential successfully!");
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
		addCredentialOracle(Code,UserName, Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	public void createCredentialOracle(String Code,String Name,String Password) throws InterruptedException{
		Common.rightClick(driver, hmcPage.PunchoutCredential_OracleCredential);
		hmcPage.PunchoutCredential_CreatOracleCredential.click();
		System.out.println("Create Credential Oracle!");
		Thread.sleep(1000);
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
			createCredentialOracle(Code,Name,Password);
		}else{
			driver.findElement(By.xpath(".//div[contains(@id,'StringDisplay["+Code+"]')]")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			System.out.println("Add Credential Oracle successfully!");
			switchToWindow(0);
		}
	}
	
	public void  checkB2BCustomer(String B2BCustomer, String group){
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BCustomer.click();
		if(!Common.checkElementExists(driver, hmcPage.B2BCustomer_SearchIDTextBox, 60)){
			hmcPage.B2BCustomer_SearchToggle.click();
		}
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_SearchButton.click();
		if(Common.checkElementExists(driver, hmcPage.B2BCustomer_1stSearchedResult, 10)){
			editB2BCustomer(B2BCustomer,group);
		} else {
			creatB2BCustomer(B2BCustomer,group);
		}
	}
	
	public void editB2BCustomer(String B2BCustomer,String group){
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
		hmcPage.B2BCustomer_EmailEdit.sendKeys("test@nemo.com");
		hmcPage.B2BCustomer_PasswordTab.click();
		hmcPage.B2BCustomer_ActiveAccountDropdownValue.click();
		hmcPage.baseStore_save.click();
		System.out.println("Edit B2BCustomer successfully!");
	}
	
	public void creatB2BCustomer(String B2BCustomer,String group){
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
		hmcPage.B2BCustomer_EmailInput.sendKeys("test@nemo.com");
		hmcPage.B2BCustomer_PasswordTab.click();
		hmcPage.B2BCustomer_ActiveStatus.click();
		hmcPage.B2BCustomer_CreateButton.click();
		System.out.println("Create B2BCustomer successfully!");
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
	
			
