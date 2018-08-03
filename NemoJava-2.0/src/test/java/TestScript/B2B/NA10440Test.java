package TestScript.B2B;

import java.util.ArrayList;
import java.util.List;

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

public class NA10440Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BPunchoutPage punchoutPage;
	String ociUrl;
	String defaultUnit = "1213577815";
	String defaultDMU = "1213348423";
	String AccessLevel = "1213348423";
	String viewError = "AU-ERROR";
	String passwordErr = "aaa-err";
	String message = "Punchout Server Error: PunchOut Exception\r\n" + 
			"Detailed error message:\r\n" + 
			"java.lang.Exception: No OCI credential found for given user name and password";
	String today=Common.getDateTimeString();
	String [] soldTo1 = {"1","View"+today,"test1","1213577815"};
	String [] soldTo2 = {"1","View"+today,"test2","1213577815"};
	String [] soldTo3 = {"1","View"+today,"test3","1213577815"};
	String [] soldTo4 = {"1","View"+today,"test4","1213577815"};
			
			
	String [] message1 = {"NEW_ITEM-VENDORMAT[1]=20HAS1L000","NEW_ITEM-UNIT[1]=EA","NEW_ITEM-LONGTEXT_1:132[]=ThinkPad T570, cart=\"Q4291280246\";SORG=\"US10\";SOFF=\"US20\"","NEW_ITEM-MATGROUP[1]=",
			"NEW_ITEM-PRICE[1]=1116.0","NEW_ITEM-DESCRIPTION[1]=NoteBook TP T570 8G 500 W10P","NEW_ITEM-EXT_CATEGORY_ID[1]="
			,"NEW_ITEM-EXT_QUOTE_ID[1]=Q4291280246","NEW_ITEM-EXT_PRODUCT_ID[1]=20HAS1L000","NEW_ITEM-CUST_FIELD5[1]=","NEW_ITEM-VENDOR[1]=","NEW_ITEM-LEADTIME[1]=15"
			,"NEW_ITEM-MANUFACTMAT[1]=20HAS1L000","NEW_ITEM-CURRENCY[1]=USD","NEW_ITEM-QUANTITY[1]=1"};
	String[] message2 = {"NEW_ITEM-PRICE[1]","NEW_ITEM-CURRENCY[1]=AUD","NEW_ITEM-QUANTITY[1]=1"};

	String[] message3 = {"NEW_ITEM-VENDORMAT[1]","NEW_ITEM-UNIT[1]=EA","NEW_ITEM-LONGTEXT_1:132[]","NEW_ITEM-MATGROUP[1]"
			,"NEW_ITEM-PRICE[1]","NEW_ITEM-DESCRIPTION[1]","NEW_ITEM-BLANK[1]=","NEW_ITEM-EXT_CATEGORY_ID[1]=","NEW_ITEM-EXT_QUOTE_ID[1]"
			,"NEW_ITEM-test1[1]","NEW_ITEM-EXT_PRODUCT_ID[1]","NEW_ITEM-CUST_FIELD4[1]","NEW_ITEM-CUST_FIELD5[1]="
			,"NEW_ITEM-VENDOR[1]=","NEW_ITEM-LEADTIME[1]=15","NEW_ITEM-MANUFACTMAT[1]"};
	String [] message4 = {"NEW_ITEM-VENDORMAT[1]=ThinkPad T570","NEW_ITEM-UNIT[1]=EA","LONGTEXT_1:132[]=cart=\"0000316102\";SORG=\"AU10\";SOFF=\"AU20\"","NEW_ITEM-MATGROUP[1]=",
			"NEW_ITEM-PRICE[1]=1116.00","NEW_ITEM-DESCRIPTION[1]=No description for this product.","NEW_ITEM-EXT_CATEGORY_ID[1]="
			,"NEW_ITEM-EXT_QUOTE_ID[1]=0000316102","NEW_ITEM-EXT_PRODUCT_ID[1]=ThinkPad T570","NEW_ITEM-CUST_FIELD5[1]=","NEW_ITEM-VENDOR[1]=","NEW_ITEM-LEADTIME[1]=15"
			,"NEW_ITEM-MANUFACTMAT[1]=ThinkPad T570","NEW_ITEM-CURRENCY[1]=AUD","NEW_ITEM-QUANTITY[1]=1",};
	String [] OutboundRules = {"NEW_ITEM-VENDORMAT","NEW_ITEM-UNIT","NEW_ITEM-LONGTEXT","NEW_ITEM-MATGROUP","NEW_ITEM-DESCRIPTION","NEW_ITEM-EXT_CATEGORY_ID","NEW_ITEM-EXT_QUOTE_ID","NEW_ITEM-EXT_PRODUCT_ID","NEW_ITEM-CUST_FIELD4","NEW_ITEM-CUST_FIELD5","NEW_ITEM-VENDOR","NEW_ITEM-LEADTIME","NEW_ITEM-MANUFACTMAT"};
	String [] OutboundRules1 = {"NEW_ITEM-test1||1q2w3e4r%","NEW_ITEM-BLANK||keep blank","NEW_ITEM-LONGTEXT||cart=#cartId;SORG=#sorg;SOFF=#soff;SalesOrg=#sorg;description=#description","NEW_ITEM-NEWTEXT||productId=#productId;contractId=#contractId;mcn=#MCN"};
	String [] OutboundRules2 = {"NEW_ITEM-UNSPSC||#unspscCode","NEW_ITEM-Commodity||#Commoditycode"};
	
	String [] Mappingrules = {"2_123123_E12","8_100001_E12","X_100001_E12","Keyboards & Mice_999999_E16","Monitors_88888_E89"};
	private String defaultMTMPN1;
	private String defaultMTMPN2;
	
	public NA10440Test(String store){
		this.Store = store;
		this.testName = "NA-10440";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA10440(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);
			defaultMTMPN1 = testData.B2B.getDefaultMTMPN1();
			defaultMTMPN2 = testData.B2B.getDefaultMTMPN2();
			ociUrl = testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/oci";
			String B2BCustomer = "NA-8935-001";
			String B2BCustomerGroups = "punchOutCustomer";
			String Code = "NA-8935-001";
			String Domain = "NetworkID";
			String SharedSecret = "aaa";
			String UserName = "NA-8935-001";
			String Password = "aaa";
			String Identity = "NA-8935-001";
			String ProfileName = "na-8935-001#AU#test1-noOutbound";
			String ProfileName2 = "NA-8935-001#AU#test2-hidden";
			String ProfileName3 = "na-8935-001#AU#test3-rule1";
			String ProfileName4 = "na-8935-001#AU#test4-unspsc";
			
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
				
			Dailylog.logInfoDB(2,"Create B2BCustome, and check if exist the custome, edit it.", Store,testName);
			checkB2BCustomer(B2BCustomer,B2BCustomerGroups);
			
			Dailylog.logInfoDB(3,"4Create Punchout Credential, and check if exist, edit it.", Store,testName);
			checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			
			Dailylog.logInfoDB(5,"Create punchout profile①，and check if exist, edit it.", Store,testName);
			checkPunchoutProfile(ProfileName,B2BCustomer,0);
			
			Dailylog.logInfoDB(6,"7,8Simulation Tool---OCI", Store,testName);
			SimulationToolOCI(UserName, Password,0);
			
			Dailylog.logInfoDB(9,"10 Create punchout profile2，and check if exist, edit it.", Store,testName);
			checkPunchoutProfile(ProfileName2,B2BCustomer,1);
			
			Dailylog.logInfoDB(11,"12Set Go to sold to test2", Store,testName);
			SimulationToolOCI(UserName, Password,11);
			
			Dailylog.logInfoDB(13,"14Create punchout profile3，and check if exist, edit it.", Store,testName);
			checkPunchoutProfile(ProfileName3,B2BCustomer,3);
			
			Dailylog.logInfoDB(15,"16Set Go to sold to test3", Store,testName);
			SimulationToolOCI(UserName, Password,15);
			
			Dailylog.logInfoDB(17,"18Create punchout profile4，and check if exist, edit it.", Store,testName);
			checkPunchoutProfile(ProfileName4,B2BCustomer,4);
			
			Dailylog.logInfoDB(19,"20Set Go to sold to test4", Store,testName);
			SimulationToolOCI(UserName, Password,19);
			
			Dailylog.logInfoDB(21,"UNSPSC and Commodity Mapping-2", Store,testName);
			setCommodity(ProfileName4);
			
			Dailylog.logInfoDB(22,"23Set Go to sold to test4", Store,testName);
			SimulationToolOCI(UserName, Password,22);
								
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void setCommodity(String profileName4) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_Nemo_Punchout.click();
		hmcPage.Home_Punchout_CustomerProfile.click();
		Select selScope =new Select(hmcPage.PunchoutProfile_SelectProfileNameScope);
		selScope.selectByVisibleText("is equal");
		hmcPage.PunchoutProfile_NameSearch.clear();
		hmcPage.PunchoutProfile_NameSearch.sendKeys(profileName4);
		hmcPage.Contract_searchbutton.click();
		Common.doubleClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
		Common.sleep(4000);
		hmcPage.PunchoutProfile_OCITab.click();
		Common.scrollToElement(driver, driver.findElement(By.xpath(".//*[contains(text(),'Outbound Rules:')]/../../td[last()]//table//table/tbody//tr[1]//th[3]")));
		driver.findElement(By.xpath("(.//*[contains(text(),'Mapping Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[3]")).clear();
		hmcPage.PaymentLeasing_saveAndCreate.click();
	}

	public void saveOCICart(String partNumber, int i) {
		Common.sleep(3000);
		b2bPage.HomePage_CartIcon.click();
		Common.sleep(3000);
		b2bPage.cartPage_quickOrder.clear();
		b2bPage.cartPage_quickOrder.sendKeys(partNumber);
		if(i==15) {
			b2bPage.cartPage_quickOrder.clear();
			b2bPage.cartPage_quickOrder.sendKeys(defaultMTMPN1);
		}
		
		b2bPage.cartPage_addButton.click();
		Common.sleep(5000);
		b2bPage.lenovoCheckout.click();
		switchToWindow(0);
	}
	
	public void checkPunchoutProfile(String ProfileName,String B2BCustomer,int type){
		Common.javascriptClick(driver, hmcPage.Home_Punchout_CustomerProfile);
//		hmcPage.Home_Punchout_CustomerProfile.click();
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
			creatPunchoutProfile(ProfileName,B2BCustomer,type);
		} else {
			creatPunchoutProfile(ProfileName,B2BCustomer,type);
		}
	}

	
	public void SimulationToolOCI(String UserName,String Password,int type){
		driver.manage().deleteAllCookies();
		driver.get(ociUrl);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to ociUrl, and checkout punchout successfully!");
		if(type == 0) {
			punchoutPage.OCI_UserNameTextBox.clear();
			punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
			punchoutPage.OCI_PasswordTextBox.clear();
			punchoutPage.OCI_PasswordTextBox.sendKeys(Password);	
			punchoutPage.OCI_InboundKeyTextBox.clear();
			punchoutPage.OCI_InboundKeyTextBox.sendKeys(soldTo1[1]);
			punchoutPage.OCI_InboundValueTextBox.clear();
			punchoutPage.OCI_InboundValueTextBox.sendKeys(soldTo1[2]);
		}
		
		if(type==11) {
			punchoutPage.OCI_UserNameTextBox.clear();
			punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
			punchoutPage.OCI_PasswordTextBox.clear();
			punchoutPage.OCI_PasswordTextBox.sendKeys(Password);	
			punchoutPage.OCI_InboundKeyTextBox.clear();
			punchoutPage.OCI_InboundKeyTextBox.sendKeys(soldTo2[1]);
			punchoutPage.OCI_InboundValueTextBox.clear();
			punchoutPage.OCI_InboundValueTextBox.sendKeys(soldTo2[2]);
		}
		
		if(type==15) {
			punchoutPage.OCI_UserNameTextBox.clear();
			punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
			punchoutPage.OCI_PasswordTextBox.clear();
			punchoutPage.OCI_PasswordTextBox.sendKeys(Password);	
			punchoutPage.OCI_InboundKeyTextBox.clear();
			punchoutPage.OCI_InboundKeyTextBox.sendKeys(soldTo3[1]);
			punchoutPage.OCI_InboundValueTextBox.clear();
			punchoutPage.OCI_InboundValueTextBox.sendKeys(soldTo3[2]);
		}
		
		if(type==19) {
			punchoutPage.OCI_UserNameTextBox.clear();
			punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
			punchoutPage.OCI_PasswordTextBox.clear();
			punchoutPage.OCI_PasswordTextBox.sendKeys(Password);	
			punchoutPage.OCI_InboundKeyTextBox.clear();
			punchoutPage.OCI_InboundKeyTextBox.sendKeys(soldTo4[1]);
			punchoutPage.OCI_InboundValueTextBox.clear();
			punchoutPage.OCI_InboundValueTextBox.sendKeys(soldTo4[2]);
		}
		
		if(type==22) {
			punchoutPage.OCI_UserNameTextBox.clear();
			punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
			punchoutPage.OCI_PasswordTextBox.clear();
			punchoutPage.OCI_PasswordTextBox.sendKeys(Password);	
			punchoutPage.OCI_InboundKeyTextBox.clear();
			punchoutPage.OCI_InboundKeyTextBox.sendKeys(soldTo4[1]);
			punchoutPage.OCI_InboundValueTextBox.clear();
			punchoutPage.OCI_InboundValueTextBox.sendKeys(soldTo4[2]);
		}
		
		Common.sleep(1000);
		punchoutPage.OCI_PunchoutButton.click();
		Common.sleep(1000);
		switchToWindow(1);
		
		if(type==0) {
			assert punchoutPage.Lenovo_Link.getAttribute("href").contains(defaultUnit);
			saveOCICart(defaultMTMPN1,0);
			for (int i = 0; i < message1.length; i++) {
				Assert.assertTrue(driver.findElement(By.xpath("//div[@id='orderList']")).getText().contains(message1[i]));
			}
		}
		
		if(type==11) {
			saveOCICart(defaultMTMPN1,11);
			for (int i = 0; i < message2.length; i++) {
				Assert.assertTrue(driver.findElement(By.xpath("//div[@id='orderList']")).getText().contains(message2[i]));
			}
		}
		
		if(type==15) {
			saveOCICart(defaultMTMPN1,15);
			for (int i = 0; i < message3.length; i++) {
				Assert.assertTrue(driver.findElement(By.xpath("//div[@id='orderList']")).getText().contains(message3[i]));
			}
		}
		
		if(type==19) {
			saveOCICart(defaultMTMPN2,19);
			for (int i = 0; i < message4.length; i++) {
				Assert.assertTrue(driver.findElement(By.xpath("//div[@id='orderList']")).getText().contains(message4[i]));
			}
		}
		
		if(type==22) {
			saveOCICart(defaultMTMPN2,22);
			for (int i = 0; i < message4.length; i++) {
				Assert.assertTrue(driver.findElement(By.xpath("//div[@id='orderList']")).getText().contains(message4[i]));
			}
		}
		
		
		System.out.println("Punchout successfully!");
		driver.close();
		switchToWindow(0);
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
				editPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			} else {
				creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			}
		} else {
			creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
		}
	}
	
	public void creatPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password){
		Common.rightClick(driver, hmcPage.Home_Punchout_Credential);
		hmcPage.Home_CreatePunchoutCredential.click();
		System.out.println("Create Punchout Credential!");
		hmcPage.PunchoutCredential_CustomerInput.clear();
		hmcPage.PunchoutCredential_CustomerInput.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
	
		createCredentialOCI(Code,UserName, Password);
		hmcPage.PunchoutCredential_CreateButton.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	
	public void createCredentialOCI(String Code,String UserName,String Password){
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
		if(Common.checkElementExists(driver, b2bPage.PunchoutCredentials_OkButton, 10)){
			 b2bPage.PunchoutCredentials_OkButton.click();
			 driver.close();
			 switchToWindow(0);
			 addCredentialOCI(Code,UserName,Password);
		} else {
			driver.close();
			switchToWindow(0);
		}
		System.out.println("Create Credential OCI successfully!");
	}
	
	public void editPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password){
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
		addCredentialOCI(Code,UserName, Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	
	public void addCredentialOCI(String Code,String UserName,String Password){
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
	
	public void creatPunchoutProfile(String ProfileName,String B2BCustomer,int type){
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
		
		hmcPage.PunchoutProfile_OCITab.click();
		if(hmcPage.PunchoutProfile_WhetherActiveOCI.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOCI.click();
		}
		Common.rightClick(driver, driver.findElement(By.xpath(".//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody//tr[1]//th[3]")));
		driver.findElement(By.xpath(".//*[text()='Create Sold To Determination']")).click(); 
		Common.sleep(3000);
		if(type == 0) {
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(soldTo1[0]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(soldTo1[1]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[3]")).sendKeys(soldTo1[2]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[4]")).sendKeys(soldTo1[3]);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo1[3]+"')][text()='"+soldTo1[3]+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo1[3]+"')][text()='"+soldTo1[3]+"']")).click();
		}
		
		if(type == 1) {
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(soldTo2[0]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(soldTo2[1]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[3]")).sendKeys(soldTo2[2]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[4]")).sendKeys(soldTo2[3]);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo2[3]+"')][text()='"+soldTo2[3]+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo2[3]+"')][text()='"+soldTo2[3]+"']")).click();
			
			for (int i = 0; i < OutboundRules.length; i++) {
				setOutboundRules(OutboundRules[i]);
			}
			
		}
		if(type==3) {
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(soldTo3[0]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(soldTo3[1]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[3]")).sendKeys(soldTo3[2]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[4]")).sendKeys(soldTo3[3]);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo3[3]+"')][text()='"+soldTo3[3]+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo3[3]+"')][text()='"+soldTo3[3]+"']")).click();
			
			for (int i = 0; i < OutboundRules1.length; i++) {
				setOutboundRules1(OutboundRules1[i],13);
			}
			
		}
		
		if(type==4) {
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(soldTo4[0]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(soldTo4[1]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[3]")).sendKeys(soldTo4[2]);
			driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]"
					+ "//table//table/tbody/tr[last()]//table//input)[4]")).sendKeys(soldTo4[3]);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo4[3]+"')][text()='"+soldTo4[3]+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+soldTo4[3]+"')][text()='"+soldTo4[3]+"']")).click();
		
			for (int i = 0; i < OutboundRules2.length; i++) {
				setOutboundRules1(OutboundRules2[i],18);
			}
			
		}
		
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Profile successfully!");
	}
	
	public void setOutboundRules1(String string, int i) {
		String name = string.split("||")[0];
		String value = string.split("||")[1];
		Common.rightClick(driver, driver.findElement(By.xpath(".//*[contains(text(),'Outbound Rules:')]/../../td[last()]//table//table/tbody//tr[1]//th[3]")));
		driver.findElement(By.xpath(".//*[text()='Create Outbound Rule']")).click();
		Common.sleep(3000);
		driver.findElement(By.xpath("(.//*[contains(text(),'Outbound Rules:')]/../../td[last()]"
				+ "//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(name);
		driver.findElement(By.xpath("(.//*[contains(text(),'Outbound Rules:')]/../../td[last()]"
				+ "//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(value);
		driver.findElement(By.xpath("(.//*[contains(text(),'Outbound Rules:')]/../../td[last()]"
				+ "//table//table/tbody/tr[last()]//table//input)[3]")).click();
		if(i==18) {
			for (int j = 0; j < Mappingrules.length; j++) {
				setMappingRules(Mappingrules[j]);
			}
		}
	}

	public void setMappingRules(String mappingrules) {
		String category = mappingrules.split("_")[0];
		String unspsc = mappingrules.split("_")[1];
		String code = mappingrules.split("_")[2];
		Common.rightClick(driver, driver.findElement(By.xpath(".//*[contains(text(),'Mapping Rules:')]/../../td[last()]//table//table/tbody//tr[1]//th[3]")));
		driver.findElement(By.xpath(".//*[text()='Create OCI UNSPSC Mapping Rule']")).click();
		driver.findElement(By.xpath("(.//*[contains(text(),'Mapping Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(category);
		driver.findElement(By.xpath("(.//*[contains(text(),'Mapping Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(unspsc);
		driver.findElement(By.xpath("(.//*[contains(text(),'Mapping Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[3]")).sendKeys(code);
		
	}

	public void setOutboundRules(String outboundRules2) {
		Common.rightClick(driver, driver.findElement(By.xpath(".//*[contains(text(),'Outbound Rules:')]/../../td[last()]//table//table/tbody//tr[1]//th[3]")));
		driver.findElement(By.xpath(".//*[text()='Create Outbound Rule']")).click();
		Common.sleep(3000);
		driver.findElement(By.xpath("(.//*[contains(text(),'Outbound Rules:')]/../../td[last()]"
				+ "//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(outboundRules2);
		driver.findElement(By.xpath("//*[@id=\"Content/BooleanEditor[in Content/CreateItemListEntry[n/a]][isHidden]_checkbox\"]")).click();
		
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
}
	
			