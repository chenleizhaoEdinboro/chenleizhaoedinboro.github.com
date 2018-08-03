package TestScript.B2B;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

public class NA9417Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BPunchoutPage punchoutPage;
	String [] rule1Select = {"/PunchOutOrderMessageHeader/Total/Money","/ItemIn/ItemID/SupplierPartID","/ItemIn/ItemDetail/UnitPrice/Money","/ItemIn/ItemDetail/Description","/ItemIn/ItemDetail/UnitOfMeasure","/ItemIn/ItemDetail/Classification","/ItemIn/ItemDetail/ManufacturerPartID","/ItemIn/ItemDetail/ManufacturerName"};
	String [] rule1Input = {"/PunchOutOrderMessageHeader/Total/MoneyCopy","/ItemIn/ItemID/SupplierPartIDCopy","/ItemIn/ItemDetail/UnitPrice/MoneyCopy","/ItemIn/ItemDetail/DescriptionCopy","/ItemIn/ItemDetail/UnitOfMeasureCopy","/ItemIn/ItemDetail/ClassificationCopy","/ItemIn/ItemDetail/ManufacturerPartIDCopy","/ItemIn/ItemDetail/ManufacturerNameCopy"};
	String [] rule2Field = {"Field1","Field2","/PunchOutOrderMessageHeader/Field3","/ItemIn/ItemID/Field4","/ItemIn/ItemDetail/Field5"};
	String [] rule2Value = {"111","222","333","444","555"};
	String [] rule3Field = {"Constant","BasketID","SalesOrganisation","SalesOffice","Contract"};
	String [] rule3Keyname = {"Key1","Basket1","SalesOrg1","SalesOffice1","con1"};
	String [] rule3Value = {"aaa","bbb","ccc","ddd","eee"};
	String [] rule4Field = {"Constant","Constant","CommodityCode"};
	String [] rule4Keyname = {"Extrinsi1","Extrinsi2","comm1"};
	String [] rule4Value = {"fff","ggg","hhh"};
	String [] soldToDeterminations = {"1","CompanyNo","01","1213577815"};
	String ariba;
	String defaultUnit = "1213577815";
	String defaultDMU = "1213348423";
	String AccessLevel = "1213348423";
	String message = "na-8939-001#AU#SoldTo_07-copy-1";
	String today=Common.getDateTimeString();
	String [] soldTo1 = {"1","View"+today,"test1","1213577815"};
	String [] soldTo2 = {"1","VIEW"+today,"test2","1213521232"};
	String newData = "ProfileName,B2BCustomer,Profile Activate,cXML Activate,Custom Cart,Inbound rule,Rule 1,Rule 2,Rule 3,Rule 4,UNSPSC mapping\r\n"
			+ "NA-258-003#AU#defult"+today+",NA-258-003,TRUE,TRUE,TRUE,,/PunchOutOrderMessageHeader/Total/Money+/PunchOutOrderMessageHeader/Total/MoneyCopy&/ItemIn/ItemID/SupplierPartID+/ItemIn/ItemID/SupplierPartIDCopy&/ItemIn/ItemDetail/UnitPrice/Money+/ItemIn/ItemDetail/UnitPrice/MoneyCopy&/ItemIn/ItemDetail/Description+/ItemIn/ItemDetail/DescriptionCopy&/ItemIn/ItemDetail/UnitOfMeasure+/ItemIn/ItemDetail/UnitOfMeasureCopy&/ItemIn/ItemDetail/Classification+/ItemIn/ItemDetail/ClassificationCopy&/ItemIn/ItemDetail/ManufacturerPartID+/ItemIn/ItemDetail/ManufacturerPartIDCopy&/ItemIn/ItemDetail/ManufacturerName+/ItemIn/ItemDetail/ManufacturerNameCopy,Field1+111&Field2+222&/PunchOutOrderMessageHeader/Field3+333&/ItemIn/ItemID/Field4+444&/ItemIn/ItemDetail/Field5+555,Constant+Key1+aaa&BasketID+Basket1+bbb&SalesOrganisation+SalesOrg1+ccc&SalesOffice+SalesOffice1+ddd&Contract+con1+eee,Constant+Extrinsi1+fff&Constant+Extrinsi2+ggg&CommodityCode+comm1+hhh,\r\n"
			+ "NA-258-003#AU#soldto6"+today+",NA-258-003,FALSE,FALSE,FALSE,1+CompanyNo+01+1213577815,,,,,"
			;
	
	public NA9417Test(String store){
		this.Store = store;
		this.testName = "NA-9417";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA9417(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);
			ariba = testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/ariba";
			String B2BCustomer = "NA-258-003";
			String B2BCustomerGroups = "punchOutCustomer";
			String Code = "NA-258-003";
			String Domain = "NetworkID";
			String SharedSecret = "aaa";
			String UserName = "NA-258-003";
			String Password = "aaa";
			String Identity = "NA-258-003";
			String ProfileName = "NA-258-003#AU#defult";
			String ProfileName2 = "NA-258-003#AU#soldto6";
			String filePath = "d:\\na-258-003.txt";
			String newFilePath = "d:\\na-258-003.csv";
			String dataUplodeCode = "PunchOutCustomerProfileARIBAUpload";
			
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
				
			Dailylog.logInfoDB(2,"Create B2BCustome, and check if exist the custome, edit it.", Store,testName);
			checkB2BCustomer(B2BCustomer,B2BCustomerGroups);
			
			Dailylog.logInfoDB(3,"4Create Punchout Credential, and check if exist, edit it.", Store,testName);
			checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			
			Dailylog.logInfoDB(5,"6,7,8Go to upload Profile,copy sample content to file,modify file,and upload file.", Store,testName);		
			File file = new File(filePath);
			creatFile(file);
			//write data into file
			writeFile(file);
            //rename the file name
			renameFile(newFilePath, file);
			//upload file
            uploadFile(dataUplodeCode, newFilePath);
			
			Dailylog.logInfoDB(9,"search file1", Store,testName);
			serarchFile(ProfileName);
			
			Dailylog.logInfoDB(10,"check rule1", Store,testName);
			checkResult(hmcPage.rule1,10);
			
			Dailylog.logInfoDB(11,"check rule2", Store,testName);
			checkResult(hmcPage.rule2,11);
			
			Dailylog.logInfoDB(12,"check rule3", Store,testName);
			checkResult(hmcPage.rule3,12);
			
			Dailylog.logInfoDB(13,"check rule4", Store,testName);
			checkResult(hmcPage.rule4,13);
			
			Dailylog.logInfoDB(14,"search file2", Store,testName);
			hmcPage.editSearch.click();
			serarchFile(ProfileName2);
			
			Dailylog.logInfoDB(15,"check", Store,testName);
			checkResult(hmcPage.soldToDeterminations,15);
			
			Dailylog.logInfoDB(16,"17check", Store,testName);
			SimulationToolOCI(Domain,Identity,SharedSecret,16);
			
			Dailylog.logInfoDB(18,"19check", Store,testName);
			SimulationToolOCI(Domain,Identity,SharedSecret,18);
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void checkResult(WebElement webElement, int number) throws InterruptedException {
		if(number == 10) {
			Common.scrollToElement(driver, hmcPage.rule1);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlStandardFields')]//tr[2]//td[3]//select/option[@value=0]")).getText().trim(), rule1Select[0]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlStandardFields')]//tr[3]//td[3]//select/option[@value=1]")).getText().trim(), rule1Select[1]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlStandardFields')]//tr[4]//td[3]//select/option[@value=3]")).getText().trim(), rule1Select[2]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlStandardFields')]//tr[5]//td[3]//select/option[@value=4]")).getText().trim(), rule1Select[3]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlStandardFields')]//tr[6]//td[3]//select/option[@value=5]")).getText().trim(), rule1Select[4]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlStandardFields')]//tr[7]//td[3]//select/option[@value=6]")).getText().trim(), rule1Select[5]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlStandardFields')]//tr[8]//td[3]//select/option[@value=7]")).getText().trim(), rule1Select[6]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlStandardFields')]//tr[9]//td[3]//select/option[@value=8]")).getText().trim(), rule1Select[7]);
		}
		
		if(number == 11) {
			Common.scrollToElement(driver, hmcPage.rule2);
		}
		
		if(number == 12) {
			Common.scrollToElement(driver, hmcPage.rule3);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlVariableKeyValues')]//tr[2]//td[3]//select/option[@value=0]")).getText().trim(), rule3Field[0]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlVariableKeyValues')]//tr[3]//td[3]//select/option[@value=1]")).getText().trim(), rule3Field[1]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlVariableKeyValues')]//tr[4]//td[3]//select/option[@value=2]")).getText().trim(), rule3Field[2]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlVariableKeyValues')]//tr[5]//td[3]//select/option[@value=3]")).getText().trim(), rule3Field[3]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlVariableKeyValues')]//tr[6]//td[3]//select/option[@value=4]")).getText().trim(), rule3Field[4]);

		}
		
		if(number == 13) {
			Common.scrollToElement(driver, hmcPage.rule4);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlVariableExtrinsics')]//tr[2]//td[3]//select/option[@value=0]")).getText().trim(), rule4Field[0]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlVariableExtrinsics')]//tr[3]//td[3]//select/option[@value=0]")).getText().trim(), rule4Field[1]);
			Assert.assertEquals(driver.findElement(By.xpath("//table[contains(@title,'cxmlVariableExtrinsics')]//tr[4]//td[3]//select/option[@value=1]")).getText().trim(), rule4Field[2]);
		}
		
		if(number == 15) {
			Common.scrollToElement(driver, hmcPage.soldToDeterminations);
			Assert.assertEquals(driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[1]")).getAttribute("value").trim(), soldToDeterminations[0]);
			Assert.assertEquals(driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[2]")).getAttribute("value").trim(), soldToDeterminations[1]);
			Assert.assertEquals(driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[3]")).getAttribute("value").trim(), soldToDeterminations[2]);
			Assert.assertEquals(driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[4]")).getAttribute("value").trim(), soldToDeterminations[3]);
		}

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
	
	public void serarchFile(String profileName) {
		if(!Common.checkElementExists(driver, hmcPage.Home_Punchout_Credential, 10)){
			if(!Common.checkElementExists(driver, hmcPage.Home_Nemo_Punchout, 10)){
				hmcPage.Home_Nemo.click();
				hmcPage.Home_Nemo_Punchout.click();
			} else {
				hmcPage.Home_Nemo_Punchout.click();
			}
		}
		hmcPage.Home_Punchout_CustomerProfile.click();
		hmcPage.PunchoutProfile_NameSearch.clear();
		hmcPage.PunchoutProfile_NameSearch.sendKeys(profileName);
		hmcPage.Contract_searchbutton.click();
		Common.doubleClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
	}

	public void uploadFile(String dataUplodeCode, String filePath){
		hmcPage.Nemo_DataUpload.click();
		hmcPage.DataUpload_inputIdentifier.clear();
		hmcPage.DataUpload_inputIdentifier.sendKeys(dataUplodeCode);
		hmcPage.DataUpload_searchButton.click();
	    System.out.println("Click search button!");
	    WebElement searchResultElement = driver.findElement(By.xpath("(//div[contains(@id,'Content/StringDisplay["+dataUplodeCode+"]')])[1]"));
		Boolean displayResult = Common.checkElementExists(driver, searchResultElement, 30);
		Assert.assertTrue(displayResult, "Don't display the search result!");               												
		Common.doubleClick(driver, searchResultElement);
		hmcPage.DataUpload_UploadButton.click();
	    System.out.println("Click upload button!");
		switchToWindow(1);
		//hmcPage.Nemo_DataUploadChooseFileButton.click();
		
		WebElement uploadFile=hmcPage.UploadPage_ChooseFileButton;
		uploadFile.sendKeys(filePath);
		hmcPage.UploadPage_UploadButton.click();
		String returnInformation = hmcPage.UploadPage_UploadInformation.getText();
		Assert.assertTrue(returnInformation.contains("Upload Successfully!"), "Upload fail!");
		System.out.println("Upload successfully!");
		driver.close();
		switchToWindow(0);
		hmcPage.PaymentLeasing_saveAndCreate.click();
	}
	
	public void writeFile(File file) throws IOException{
		FileWriter fileWritter = new FileWriter(file);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        bufferWritter.write(newData);
        System.out.println("Write date in file!");
        bufferWritter.close();
	}

	public void renameFile(String filePath, File file){
		 File newFile = new File(filePath); 
         if(newFile.exists()){
			newFile.delete();
			System.out.println("Delete the exist csv file!");
		}
        file.renameTo(newFile);
        System.out.println("Rename the file name!");
	}
	
	public void editPunchoutProfile(String profileName2, String b2bCustomer) {
		hmcPage.editSearch.click();
		Select selScope =new Select(hmcPage.PunchoutProfile_SelectProfileNameScope);
		selScope.selectByVisibleText("is equal");
		hmcPage.PunchoutProfile_NameSearch.clear();
		hmcPage.PunchoutProfile_NameSearch.sendKeys(profileName2);
		hmcPage.Contract_searchbutton.click();
		if(Common.checkElementExists(driver, hmcPage.PunchoutProfile_1stSearchedResult, 10)){
			Common.doubleClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
			hmcPage.PunchoutProfile_OCITabedit.click();
			Common.rightClick(driver, driver.findElement(By.xpath(".//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody//tr[2]")));
			driver.findElement(By.xpath(".//*[text()='Remove']")).click(); 
			Alert alert =  driver.switchTo().alert();
			alert.accept();
			Common.sleep(3000);
			Common.rightClick(driver, driver.findElement(By.xpath(".//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody//tr[1]//th[3]")));
			driver.findElement(By.xpath(".//*[text()='Create Sold To Determination']")).click(); 
			Common.sleep(3000);
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
			
			hmcPage.PaymentLeasing_saveAndCreate.click();
			System.out.println("edit Punchout Profile successfully!");
		}
	}

	public void checkPunchoutProfile(String ProfileName,String B2BCustomer,int type){
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
			creatPunchoutProfile(ProfileName,B2BCustomer,type);
		} else {
			creatPunchoutProfile(ProfileName,B2BCustomer,type);
		}
	}

	
	public void SimulationToolOCI(String Domain,String Identity,String sharedSecret, int i){
		driver.manage().deleteAllCookies();
		driver.get(ariba);
		B2BCommon.punchoutLogin(driver,punchoutPage);
		System.out.println("Go to ociUrl, and checkout punchout successfully!");
		driver.findElement(By.xpath("//input[@id='userName']")).clear();
		driver.findElement(By.xpath("//input[@id='userName']")).sendKeys("LIeCommerce");
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("M0C0v0n3L!");
		if(i == 16) {
			punchoutPage.Ariba_DomainTextBox.clear();
			punchoutPage.Ariba_DomainTextBox.sendKeys(Domain);
			punchoutPage.Ariba_IdentityTextBox.clear();
			punchoutPage.Ariba_IdentityTextBox.sendKeys(Identity);
			punchoutPage.Ariba_SharedSecretTextBox.clear();
			punchoutPage.Ariba_SharedSecretTextBox.sendKeys(sharedSecret);
			punchoutPage.Ariba_PunchoutButton.click();
		}
		
		if(i == 18) {
			punchoutPage.Ariba_DomainTextBox.clear();
			punchoutPage.Ariba_DomainTextBox.sendKeys(Domain);
			punchoutPage.Ariba_IdentityTextBox.clear();
			punchoutPage.Ariba_IdentityTextBox.sendKeys(Identity);
			punchoutPage.Ariba_SharedSecretTextBox.clear();
			punchoutPage.Ariba_SharedSecretTextBox.sendKeys(sharedSecret);
			punchoutPage.Ariba_AddExtrinsicButton.click();
			punchoutPage.Ariba_SuppCode.clear();
			punchoutPage.Ariba_InboundKeyTextBox.clear();
			punchoutPage.Ariba_InboundKeyTextBox.sendKeys("CompanyNo");
			punchoutPage.Ariba_InboundValueTextBox.clear();
			punchoutPage.Ariba_InboundValueTextBox.sendKeys("01");
			punchoutPage.Ariba_PunchoutButton.click();
		}
		Common.sleep(5000);
		driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
		Assert.assertTrue(Common.checkElementExists(driver, punchoutPage.Lenovo_Link, 10));
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
	
		createCredentialAriba(Code,Domain,Identity,SharedSecret);
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
		addCredentialAriba(Code,Domain,Identity,SharedSecret);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Credential successfully!");
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
		
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Profile successfully!");
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
	
	public void creatFile(File file) throws IOException{
		if(!file.exists()){
			file.createNewFile();
			System.out.println("Create file!");
		}
	}
}
	
			