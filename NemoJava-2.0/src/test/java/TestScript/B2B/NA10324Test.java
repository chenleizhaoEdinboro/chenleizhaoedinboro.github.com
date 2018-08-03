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

public class NA10324Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BPunchoutPage punchoutPage;
	String ociUrl;
	String defaultUnit = "1213577815";
	String defaultDMU = "1213348423";
	String AccessLevel = "1213348423";
	String today=Common.getDateTimeString();
	String newData = "ProfileName,B2BCustomer,Profile Activate,OCI Activate,Inbound rule,Outbound rule,UNSPSC mapping\r\n"
			+ "NA-8939-002#AU#soldto06"+today+",NA-8939-002 ,TRUE ,TRUE,1+View"+today+"+test1+1213577815,ITEM_1=#cartId&ITEM_2=#description&ITEM_3=#sorg&ITEM_4=#cartId;Commoditycode=#sorg+TRUE&ITEM_5++FALSE,Keyboards & Mice++E16|Accessories+43211505+|8+43211500+E35\r\n"
			+ "NA-8939-002#AU#soldto07"+today+",NA-8939-002 ,TRUE ,TRUE,1+VIEW"+today+"+test2+1213521232,ITEM_1=#cartId;#description;end+TRUE,8+88+8888|2+22+2222\r\n"
			+ "NA-8939-002#AU#soldto08"+today+",NA-8939-002 ,TRUE ,TRUE,1+VIEW"+today+"+test3+1213410863,NEW_ITEM-DESCRIPTION++FALSE&NEW_ITEM-LONGTEXT_1++FALSE&NEW_ITEM-QUANTITY++FALSE,\r\n"
			+ "NA-8939-002#AU#soldtoNone"+today+",,FALSE ,FALSE ,,,";
	String [] soldTo1 = {"View"+today,"test1","1213577815"};
	String [] soldTo2 = {"VIEW"+today,"test2","1213521232"};
	String [] soldTo3 = {"VIEW"+today,"test3","1213410863"};
	
	public NA10324Test(String store){
		this.Store = store;
		this.testName = "NA-10324";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA10324(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);
			
			ociUrl = testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/oci";
			String B2BCustomer = "NA-8939-002";
			String B2BCustomerGroups = "punchOutCustomer";
			String Code = "NA-8939-002";
			String Domain = "NetworkID";
			String SharedSecret = "aaa";
			String UserName = "NA-8939-002";
			String Password = "aaa";
			String Identity = "NA-8939-002";
			String filePath = "d:\\na-8939-002.txt";
			String newFilePath = "d:\\na-8939-002.csv";
			String dataUplodeCode = "PunchOutCustomerProfileSAPOCIUpload";
			
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
				
			Dailylog.logInfoDB(2,"Create B2BCustome, and check if exist the custome, edit it.", Store,testName);
			checkB2BCustomer(B2BCustomer,B2BCustomerGroups);
			
			Dailylog.logInfoDB(3,"Create Punchout Credential, and check if exist, edit it.", Store,testName);
			checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			
			Dailylog.logInfoDB(5,"Go to upload Profile,copy sample content to file,modify file,and upload file.", Store,testName);		
			File file = new File(filePath);
			creatFile(file);
			//write data into file
			writeFile(file);
            //rename the file name
			renameFile(newFilePath, file);
			//upload file
            uploadFile(dataUplodeCode, newFilePath);
			
			Dailylog.logInfoDB(9,"Simulation Tool OCI, and set rule 1.", Store,testName);
			SimulationToolOCI(UserName, Password,soldTo1[0],soldTo1[1], soldTo1[2]);
			
			Dailylog.logInfoDB(11,"Simulation Tool OCI, and set rule 2.", Store,testName);
			SimulationToolOCI(UserName, Password,soldTo2[0],soldTo2[1], soldTo2[2]);
			
			Dailylog.logInfoDB(12,"Simulation Tool OCI, and set rule 3.", Store,testName);
			SimulationToolOCI(UserName, Password,soldTo3[0],soldTo3[1], soldTo3[2]);
								
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void creatFile(File file) throws IOException{
		if(!file.exists()){
			file.createNewFile();
			System.out.println("Create file!");
		}
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
	
	public void SimulationToolOCI(String UserName,String Password,String viewType, String viewName,String defaultUnit){
		driver.manage().deleteAllCookies();
		driver.get(ociUrl);
		System.out.println("Go to ociUrl, and checkout punchout successfully!");
		B2BCommon.punchoutLogin(driver, punchoutPage);
		punchoutPage.OCI_UserNameTextBox.clear();
		punchoutPage.OCI_UserNameTextBox.sendKeys(UserName);
		punchoutPage.OCI_PasswordTextBox.clear();
		punchoutPage.OCI_PasswordTextBox.sendKeys(Password);
		punchoutPage.OCI_InboundKeyTextBox.clear();
		punchoutPage.OCI_InboundKeyTextBox.sendKeys(viewType);
		punchoutPage.OCI_InboundValueTextBox.clear();
		punchoutPage.OCI_InboundValueTextBox.sendKeys(viewName);
		punchoutPage.OCI_PunchoutButton.click();
		
		Common.sleep(1000);
		switchToWindow(1);
		String currentUrl = punchoutPage.Lenovo_Link.getAttribute("href");
//		System.out.println("The href is: "+currentUrl);
//		Assert.assertTrue(currentUrl.contains("isPunchout=true"), "Punchout filed!");
//		System.out.println("Punchout successfully!");
		Assert.assertTrue(currentUrl.contains(defaultUnit), "Set filed!");
		System.out.println("Set successfully!");
		driver.close();
		switchToWindow(0);
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
}
	
			