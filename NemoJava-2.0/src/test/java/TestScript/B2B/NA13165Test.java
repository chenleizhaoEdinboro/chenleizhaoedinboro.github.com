package TestScript.B2B;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA13165Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BPunchoutPage punchoutPage;
	String defaultUnit = "1213577815";
	String defaultDMU = "1213348423";
	String AccessLevel = "1213348423";
	String filePath = "d:\\na-13165-002.txt";
	String newFilePath = "d:\\na-13165-002.csv";
	String today=Common.getDateTimeString();
	int rowNum = 0;
	
	String correctDataArray[][] = 
		{{"NA-13165-002#AU#soldto06"+today,"NA-13165-002","TRUE","TRUE","TRUE",
			"1","operatingUnit","001","1213577815","2","operatingUnit","002","1213521232","3","operatingUnit","003","1213410863",
			"/body/OrderLinesDataElements/orderLine/contract/TagA","pId=#productId","FALSE","/body/OrderLinesDataElements/orderLine/TagB","abced12345!@$%^$","TRUE",
			"/body/OrderLinesDataElements/orderLine/contract","new_a","aaa=#cartId;bbb=#description","FALSE","/body/OrderLinesDataElements/orderLine/","new_b","abced12345!@$%^$","TRUE","/body/OrderLinesDataElements/orderLine/contract","new_a","aaa=#cartId;bbb=#description","FALSE",
			"Mobile Carts - Mobile Carts","aaa","a","1","bbb","","4","ccc","c"},
			{"NA-13165-002#AU#soldto07"+today,"NA-13165-002","FALSE","FALSE","FALSE",
				"1","operatingUnit","001","1213577815",
				"/body/OrderLinesDataElements/orderLine/contract/TagA","pId=#productId","FALSE",
				"/body/OrderLinesDataElements/orderLine/contract","new_a","aaa=#cartId;bbb=#description","FALSE",
				"1","bbb","","4","ccc","c"},
			{"NA-13165-002#AU#soldto08"+today,"NA-13165-002","FALSE","FALSE","FALSE"}};
	
	String correctData = "ProfileName,B2BCustomer,Profile Activate,Oracle Activate,Custom Cart,Inbound rule,Tag rule,Attribute Rule,UNSPSC mapping\r\n"
			+ "NA-13165-002#AU#soldto06"+today+",NA-13165-002 ,TRUE ,TRUE,TRUE,1+operatingUnit+001+1213577815&2+operatingUnit+002+1213521232&3+operatingUnit+003+1213410863,/body/OrderLinesDataElements/orderLine/contract/TagA+pId=#productId+FALSE&/body/OrderLinesDataElements/orderLine/TagB+abced12345!@$%^$+TRUE,/body/OrderLinesDataElements/orderLine/contract+new_a+aaa=#cartId;bbb=#description+FALSE&/body/OrderLinesDataElements/orderLine/+new_b+abced12345!@$%^$+TRUE&/body/OrderLinesDataElements/orderLine/contract+new_a+aaa=#cartId;bbb=#description+FALSE,Mobile Carts - Mobile Carts+aaa+a|1+bbb+|4+ccc+c\r\n"
			+ "NA-13165-002#AU#soldto07"+today+",NA-13165-002 ,FALSE ,FALSE,FALSE,1+operatingUnit+001+1213577815,/body/OrderLinesDataElements/orderLine/contract/TagA+pId=#productId+FALSE,/body/OrderLinesDataElements/orderLine/contract+new_a+aaa=#cartId;bbb=#description+FALSE,1+bbb+|4+ccc+c\r\n"
			+ "NA-13165-002#AU#soldto08"+today+",NA-13165-002 ,FALSE ,FALSE,FALSE,,,,FALSE\r\n";
	String errorData1 = "ProfileName,B2BCustomer,Profile Activate,Oracle Activate,Custom Cart,Inbound rule,Tag rule,Attribute Rule,UNSPSC mapping\r\n"
			+ "NA-13165-002#error#defaultSoldTo-3"+today+",NA-13165-002,TRUE,TRUE,TRUE,,,,\r\n"
			+ "NA-13165-002#error#defaultSoldTo-4"+today+",NA-13165-002-1,FALSE,FALSE,FALSE,,,,\r\n";
	String errorData2 = "ProfileName,B2BCustomer,Profile Activate,Oracle Activate,Custom Cart,Inbound rule,Tag rule,Attribute Rule,UNSPSC mapping\r\n"
			+ "NA-13165-002#error#defaultSoldTo-3"+today+",NA-13165-002,,TRUE,TRUE,,,,\r\n"
			+ "NA-13165-002#error#defaultSoldTo-4"+today+",NA-13165-002,FALSE,FALSE,FALSE,,,,\r\n";
	String errorData3 = "ProfileName,B2BCustomer,Profile Activate,Oracle Activate,Custom Cart,Inbound rule,Tag rule,Attribute Rule,UNSPSC mapping\r\n"
			+ "NA-13165-002#error#defaultSoldTo-3"+today+",NA-13165-002,TRUE,TRUE,TRUE,1+operatingUnit+aaa+bbb,,,\r\n"
			+ "NA-13165-002#error#defaultSoldTo-4"+today+",NA-13165-002,FALSE,FALSE,FALSE,,,,\r\n";
	String errorData4 = "ProfileName,B2BCustomer,Profile Activate,Oracle Activate,Custom Cart,Inbound rule,Tag rule,Attribute Rule,UNSPSC mapping\r\n"
			+ "NA-13165-002#error#defaultSoldTo-3"+today+",NA-13165-002,TRUE,TRUE,TRUE,,aa/bb/Tag+aa+bb+TRUE,,\r\n"
			+ "NA-13165-002#error#defaultSoldTo-4"+today+",NA-13165-002,FALSE,FALSE,FALSE,,,,\r\n";
	String errorData5 = "ProfileName,B2BCustomer,Profile Activate,Oracle Activate,Custom Cart,Inbound rule,Tag rule,Attribute Rule,UNSPSC mapping\r\n"
			+ "NA-13165-002#error#defaultSoldTo-3"+today+",NA-13165-002,TRUE,TRUE,TRUE,,,aa/bb/cc+aa+bb+cc+TRUE,\r\n"
			+ "NA-13165-002#error#defaultSoldTo-4"+today+",NA-13165-002,FALSE,FALSE,FALSE,,,,\r\n";
	String errorData6 = "ProfileName,B2BCustomer,Profile Activate,Oracle Activate,Custom Cart,Inbound rule,Tag rule,Attribute Rule,UNSPSC mapping\r\n"
			+ "NA-13165-002#error#defaultSoldTo-3"+today+",NA-13165-002,TRUE,TRUE,TRUE,,,,1+a+b+c\r\n"
			+ "NA-13165-002#error#defaultSoldTo-4"+today+",NA-13165-002,FALSE,FALSE,FALSE,,,,\r\n";
	String errorData7 = "ProfileName,B2BCustomer,Profile Activate,Oracle Activate,Custom Cart,Inbound rule,Tag rule,Attribute Rule,UNSPSC mapping\r\n"
			+ "NA-13165-002#error#defaultSoldTo-3"+today+",NA-13165-002,TRUE,TRUE,TRUE,,,,\r\n"
			+ "NA-13165-002#error#defaultSoldTo-4"+today+",NA-13165-002,FALSE,FALSE,FALSE,,,,\r\n"
			+ "NA-13165-002#error#defaultSoldTo-4"+today+",NA-13165-002,TRUE,TRUE,TRUE,,,,\r\n"
			+ "NA-13165-002#error#defaultSoldTo-5"+today+",NA-13165-002,FALSE,FALSE,FALSE,,,,\r\n";
	String erorProfileName = "NA-13165-002#error#";
	String information[] = {"Upload Successfully!",
			"Can not Process File with error in Line 3 : B2B Customer with ID NA-13165-002-1 not exists, please set a correct B2B Customer.",
			"Can not Process File with error in Line 2 : Input CSV data is not valid, Profile Activate should be either TRUE or FALSE",
			"Can not Process File with error in Line 2 : Sold To determination with ID bbb not exists.",
			"Can not Process File with error in Line 2 : aa/bb/Tag+aa+bb+TRUE is invalid. There should be at most 3 elements.",
			"Can not Process File with error in Line 2 : aa/bb/cc+aa+bb+cc+TRUE is invalid. There should be at most 4 elements.",
			"Can not Process File with error in Line 2 : 1+a+b+c is invalid. There should be at most 3 elements.",
			"There is duplicated Profile Name : NA-13165-002#error#defaultSoldTo-4"+today+" in CSV file."};
	public NA13165Test(String store){
		this.Store = store;
		this.testName = "NA-13165";
		
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA13165(ITestContext ctx){
		try{
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);
			
			String B2BCustomer = "NA-13165-002";
			String B2BCustomerGroups = "punchOutCustomer";
			String Code = "NA-13165-002";
			String SharedSecret = "aaa";
			String UserName = "NA-13165-002";
			String Password = "aaa";
			String Identity = "NA-13165-002";
			String dataUplodeCode = "PunchOutCustomerProfileOracleUpload";
			
			Dailylog.logInfoDB(1,"Login HMC.", Store,testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
				
			Dailylog.logInfoDB(2,"Create B2BCustome, and check if exist the custome, edit it.", Store,testName);
			checkB2BCustomer(B2BCustomer,B2BCustomerGroups);
			
			Dailylog.logInfoDB(3,"Create Punchout Credential, and check if exist, edit it.", Store,testName);
			checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Identity,SharedSecret,UserName,Password);
			
			Dailylog.logInfoDB(5,"Go to upload Profile,copy sample content to file,modify file,and upload file.", Store,testName);		
			uploadFile(dataUplodeCode, correctData,0);
			
			Dailylog.logInfoDB(9,"Check result in profile.", Store,testName);
			checkResult(correctDataArray[0][0],true);
			checkResult(correctDataArray[1][0],true);
			checkResult(correctDataArray[2][0],true);
			
			Dailylog.logInfoDB(10,"Upload Error-B2B Customer doesn't exist.", Store,testName);
			uploadFile(dataUplodeCode, errorData1,1);
			
			Dailylog.logInfoDB(11,"Check result in profile.", Store,testName);
			checkResult(erorProfileName,false);
			
			Dailylog.logInfoDB(12,"Upload Error-B2B Active is blank.", Store,testName);
			uploadFile( dataUplodeCode, errorData2,2);
			
			Dailylog.logInfoDB(13,"Check result in profile.", Store,testName);
			checkResult(erorProfileName,false);
			
			Dailylog.logInfoDB(14,"Upload Error-B2B Active is blank.", Store,testName);
			uploadFile(dataUplodeCode, errorData3,3);
			
			Dailylog.logInfoDB(15,"Check result in profile.", Store,testName);
			checkResult(erorProfileName,false);
			
			Dailylog.logInfoDB(16,"Upload Error-B2B Tag Rule error.", Store,testName);
			uploadFile( dataUplodeCode, errorData4,4);
			
			Dailylog.logInfoDB(17,"Check result in profile.", Store,testName);
			checkResult(erorProfileName,false);
			
			Dailylog.logInfoDB(18,"Upload Error-B2B Attribute Rule error.", Store,testName);
			uploadFile(dataUplodeCode, errorData5,5);
			
			Dailylog.logInfoDB(19,"Check result in profile.", Store,testName);
			checkResult(erorProfileName,false);
			
			Dailylog.logInfoDB(20,"Upload Error-B2B UNSPSC Rule error.", Store,testName);
			uploadFile(dataUplodeCode, errorData6,6);
			
			Dailylog.logInfoDB(21,"Check result in profile.", Store,testName);
			checkResult(erorProfileName,false);
			
			Dailylog.logInfoDB(22,"Upload Error-B2B Repeat error.", Store,testName);
			uploadFile(dataUplodeCode, errorData7,7);
			
			Dailylog.logInfoDB(23,"Check result in profile.", Store,testName);
			checkResult(erorProfileName,false);
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public int checkResult(String profileName,Boolean whetherExistResult){	
		System.out.println("Check whether the result exist.");
		if(whetherExistResult.equals(true)){
			driver.manage().deleteAllCookies();
			driver.get(testData.HMC.getHomePageUrl());
			while(!Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
				driver.manage().deleteAllCookies();
				driver.get(testData.HMC.getHomePageUrl());
			}
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_Nemo.click();				
		}
		if(!Common.checkElementExists(driver, hmcPage.Home_Punchout_CustomerProfile, 10)){
			hmcPage.Home_Nemo_Punchout.click();
		}
		hmcPage.Home_Punchout_CustomerProfile.click();
		System.out.println("Click customer profile.");
		if(!Common.checkElementExists(driver, hmcPage.PunchoutProfile_NameSearch, 10)){
			driver.findElement(By.xpath("//a[@id='Content/OrganizerComponent[organizersearch][PunchOutCustomerProfile]_togglelabel']")).click();
		}
		hmcPage.PunchoutProfile_NameSearch.clear();
		hmcPage.PunchoutProfile_NameSearch.sendKeys(profileName);
		hmcPage.Contract_searchbutton.click();
		System.out.println("Click search button.");
		Boolean resultExist = Common.checkElementExists(driver, hmcPage.PunchoutProfile_1stSearchedResult, 30);
		Assert.assertEquals(resultExist, whetherExistResult, "The actual result isn't same as expect result.");
		System.out.println("Wherther the result is: "+resultExist);
		if(whetherExistResult == true){		
			checkDetail(rowNum);
		}	
		return rowNum++;
	}
	
	public void checkDetail(int rowNum){
		System.out.println("Check the detail.");
		Common.doubleClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
		hmcPage.PunchoutProfile_OracleTab.click();
		System.out.println("Click the oracle tab.");
		WebElement B2BCoustomer = driver.findElement(By.xpath("//div[contains(@id,'Content/ItemDisplay["+correctDataArray[0][1]+"]')]"));
		Boolean whetherExistCustomer = Common.checkElementExists(driver, B2BCoustomer, 10);
		Assert.assertTrue(whetherExistCustomer, "The B2BCustomer is error!");
		System.out.println("The displayed B2BCustomer is correct.");
		
		for(int i=0; i<correctDataArray[rowNum].length;i++){
			System.out.println("Tht correctDataArray["+rowNum+"]["+i+"] data is: "+correctDataArray[rowNum][i]);
		}
		
		String activeProfile = hmcPage.PunchoutProfile_ActiveProfile.getAttribute("value").toUpperCase();
		Assert.assertEquals(activeProfile, correctDataArray[rowNum][2], "The active profile don't checked!");
		System.out.println("The displayed ActiveProfile is correct.");
		
		String oracleActive= hmcPage.PunchoutProfile_OracleActive.getAttribute("value").toUpperCase();
		Assert.assertEquals(oracleActive, correctDataArray[rowNum][3], "The oracle profile don't checked!");
		System.out.println("The displayed OracleActive is correct.");
		
		String customCart= hmcPage.PunchoutProfile_CustomCart.getAttribute("value").toUpperCase();
		Assert.assertEquals(customCart, correctDataArray[rowNum][4], "The custom cart don't checked!");
		System.out.println("The displayed CustomCart is correct.");
		
		List<WebElement> rules = driver.findElements(By.xpath("//input[contains(@id,'[in Content/EditableItemListEntry')][contains(@id,'input')]"));
		List<WebElement> ruleHidded = driver.findElements(By.xpath("//input[contains(@id,'[in Content/EditableItemListEntry')][contains(@id,'[isHidden]')]/preceding-sibling::*"));
		int ruleHiddenNum = 0;
		
		for(int i=0; i<rules.size(); i++){
			if(correctDataArray[rowNum].length>5){
				String getRuleInput = rules.get(i).getAttribute("value");	
				int j=i+ruleHiddenNum;
				String expectRuleInput = correctDataArray[rowNum][5+j];
				while(expectRuleInput.toUpperCase().equals("TRUE")||expectRuleInput.toUpperCase().equals("FALSE")){
					ruleHiddenNum++;
					j=i+ruleHiddenNum;
					expectRuleInput = correctDataArray[rowNum][5+j];
				}
				Assert.assertEquals(getRuleInput, expectRuleInput,"The rule value is error!");
			}
		}
		System.out.println("The displayed ruleInput is correct.");
		ruleHiddenNum = 0;
		for(int i=0; i<ruleHidded.size(); i++){
			if(correctDataArray[rowNum].length>5){
				String getRuleHidden = ruleHidded.get(i).getAttribute("value").toUpperCase();
				int j=i+ruleHiddenNum;
				String expectRuleHidden = correctDataArray[rowNum][5+j];
				while(!(expectRuleHidden.toUpperCase().equals("TRUE")||expectRuleHidden.toUpperCase().equals("FALSE"))){
					ruleHiddenNum++;
					j=i+ruleHiddenNum;
					expectRuleHidden = correctDataArray[rowNum][5+j];
				}
				Assert.assertEquals(getRuleHidden, expectRuleHidden,"The rule hidden value is error!");
			}
		}
		System.out.println("The displayed ruleHidden is correct.");
	}
	
	public void creatFile(File file) throws IOException{
		if(!file.exists()){
			file.createNewFile();
			System.out.println("Create file!");
		}
	}
	
	public void writeFile(File file, String data) throws IOException{
		FileWriter fileWritter = new FileWriter(file);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        bufferWritter.write(data);
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
	
	public void uploadFile(String dataUplodeCode, String data, int i) throws IOException{
		File file = new File(filePath);
		creatFile(file);
		//write data into file
		writeFile(file,data);
        //rename the file name
		renameFile(newFilePath, file);
		//upload file
		if(!Common.checkElementExists(driver, hmcPage.Nemo_DataUpload, 30)){
			hmcPage.Home_Nemo.click();
		}
		hmcPage.Nemo_DataUpload.click();
		if(!Common.checkElementExists(driver, hmcPage.DataUpload_UploadButton, 10)){
			hmcPage.DataUpload_inputIdentifier.clear();
			hmcPage.DataUpload_inputIdentifier.sendKeys(dataUplodeCode);
			hmcPage.DataUpload_searchButton.click();
		    System.out.println("Click search button!");
		    WebElement searchResultElement = driver.findElement(By.xpath("(//div[contains(@id,'Content/StringDisplay["+dataUplodeCode+"]')])[1]"));
			Boolean displayResult = Common.checkElementExists(driver, searchResultElement, 30);
			Assert.assertTrue(displayResult, "Don't display the search result!");               												
			Common.doubleClick(driver, searchResultElement);
		}
		hmcPage.DataUpload_UploadButton.click();
	    System.out.println("Click upload button!");
		switchToWindow(1);
		//hmcPage.Nemo_DataUploadChooseFileButton.click();
		
		WebElement uploadFile=hmcPage.UploadPage_ChooseFileButton;
		uploadFile.sendKeys(newFilePath);
		hmcPage.UploadPage_UploadButton.click();
		String returnInformation = hmcPage.UploadPage_UploadInformation.getText();
		System.out.println("The information is: "+returnInformation);
		Assert.assertTrue(returnInformation.contains(information[i]), "The actual isn't same as expect!");
		System.out.println("The actual is same as expect!");
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
	
	public void  checkPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Identity, String SharedSecret,String UserName,String Password){
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
				editPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Identity,SharedSecret,UserName,Password);
			} else {
				creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Identity,SharedSecret,UserName,Password);
			}
		} else {
			creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Identity,SharedSecret,UserName,Password);
		}
	}
	
	public void creatPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Identity, String SharedSecret,String UserName,String Password){
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
	
	public void createCredentialOracle(String Code,String Name,String Password){
		Common.rightClick(driver, hmcPage.PunchoutCredential_OracleCredential);
		hmcPage.PunchoutCredential_CreatOracleCredential.click();
		System.out.println("Create Credential Oracle!");
		Common.sleep(1000);
		switchToWindow(1);
		hmcPage.PunchoutOCIOracleCredential_CodeInput.clear();
		hmcPage.PunchoutOCIOracleCredential_CodeInput.sendKeys(Code);
		hmcPage.PunchoutOracleCredential_NameInput.clear();
		hmcPage.PunchoutOracleCredential_NameInput.sendKeys(Name);
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.clear();
		hmcPage.PunchoutOCIOracleCredential_PasswordInput.sendKeys(Password);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		if(Common.checkElementExists(driver, b2bPage.PunchoutCredentials_OkButton, 10)){
			 b2bPage.PunchoutCredentials_OkButton.click();
			 driver.close();
			 switchToWindow(0);
			 addCredentialOracle(Code,Name,Password);
		} else {
			driver.close();
			switchToWindow(0);
		}
		System.out.println("Create Credential Oracle successfully!");
	}
	
	public void editPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Identity, String SharedSecret,String UserName,String Password){
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
	
	public void addCredentialOracle(String Code,String Name,String Password){
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
	
			