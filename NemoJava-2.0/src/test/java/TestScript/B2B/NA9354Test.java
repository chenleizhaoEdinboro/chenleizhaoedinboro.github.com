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

public class NA9354Test extends SuperTestClass {
	public B2BPage b2bPage;
	public HMCPage hmcPage;
	public B2BPunchoutPage punchoutPage;
	String today = Common.getDateTimeString();
	String profile = "user-";
	String filePath3 = "d:\\NA9354-TESTCASE-010-3-new.txt";
	String newFilePath3 = "d:\\NA9354-TESTCASE-010-3-new.csv";
	String searchProfile = "B2BCustomerUpload";
	String newData = "uid,email,name,groups(uid),defaultB2BUnit(uid),accessLevel,consumerID\r\n"
			+ "user-a,user-a@nemo.com,a,punchOutCustomer,1213577815,1213348423,2802222222\r\n"
			+ "user-b,user-b@nemo.com,b,punchOutCustomer,1213577815,1213348423,2802222222\r\n"
			+ "user-c,user-c@nemo.com,c,punchOutCustomer,1213577815,1213348423,2802222222\r\n"
			+ "user-d,user-d@nemo.com,d,punchOutCustomer,1213577815,1213348423,2802222222";

	String newData2 = "uid,email,name,groups(uid),defaultB2BUnit(uid),accessLevel,consumerID\r\n"
			+ "user-a,user-a@nemo.com,a,punchOutCustomer,1213577815,1213348423,2802222222\r\n"
			+ "user-b,user-b@nemo.com,b,punchOutCustomer,1213577815,1213348423,2802222222\r\n"
			+ "user-c,user-c@nemo.com,ccc,punchOutCustomer,1213577815,1213348423,2802222222\r\n"
			+ "user-d,user-d@nemo.com,ddd,punchOutCustomer,1213577815,1213348423,2802222222";

	String newData3 = "uid,email,name,groups(uid),defaultB2BUnit(uid),accessLevel,consumerID\r\n"
			+ "user-e,user-a@nemo.com,aaaa,punchOutCustomer,1213577815,1213348423,2802222222\r\n"
			+ "user-f,user-b@nemo.com,bbbb,punchOutCustomer,1213577815,1213348423,2802222222\r\n"
			+ "user-g,user-c@nemo.com,cccc,punchOutCustomer,1213577815,1213348423,2802222222\r\n"
			+ "user-h,user-d@nemo.com,,123456,1213577815,1213348423,2802222222";

	public NA9354Test(String store) {
		this.Store = store;
		this.testName = "NA-9354";
	}

	@Test(alwaysRun = true, groups = { "contentgroup", "b2bpunchout", "p2", "b2b" })
	public void NA9354(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			punchoutPage = new B2BPunchoutPage(driver);

			String filePath = "d:\\NA9354-TESTCASE-010-1-new.txt";
			String newFilePath = "d:\\NA9354-TESTCASE-010-1-new.csv";
			String filePath2 = "d:\\NA9354-TESTCASE-010-2-new.txt";
			String newFilePath2 = "d:\\NA9354-TESTCASE-010-2-new.csv";

			Dailylog.logInfoDB(1, "Login HMC.", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);

			Dailylog.logInfoDB(2, "delete", Store, testName);
			deleteProfile(2);

			Dailylog.logInfoDB(3, "Data Upload ", Store, testName);
			File file = new File(filePath);
			creatFile(file);
			// write data into file
			writeFile(file, newData);
			// rename the file name
			renameFile(newFilePath, file);
			// upload file
			uploadProfile(searchProfile, newFilePath,3);

			Dailylog.logInfoDB(7, "search", Store, testName);
			hmcPage.HMC_Logout.click();
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			deleteProfile(7);

			Dailylog.logInfoDB(8, "Copy \"NA9354-TESTCASE-010-1-new.csv\" to \"NA265-TESTCASE-010-2-new.csv", Store,
					testName);
			File file2 = new File(filePath2);
			creatFile(file2);
			// write data into file
			writeFile(file2, newData2);
			// rename the file name
			renameFile(newFilePath2, file2);
			// upload file
			uploadProfile(searchProfile, newFilePath2,8);

			Dailylog.logInfoDB(10, "Copy \"NA9354-TESTCASE-010-2-new.csv\" to \"NA9354-TESTCASE-010-3-new.csv", Store,testName);
			Common.scrollToElement(driver, hmcPage.HMC_Logout);
			hmcPage.HMC_Logout.click();
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			File file3 = new File(filePath3);
			creatFile(file3);
			// write data into file
			writeFile(file3, newData3);
			// rename the file name
			renameFile(newFilePath3, file3);
			// upload file
			uploadProfile(searchProfile, newFilePath3,10);
			Dailylog.logInfoDB(11, "Upload", Store, testName);

			Dailylog.logInfoDB(12, "search", Store, testName);
			Common.scrollToElement(driver, hmcPage.HMC_Logout);
			hmcPage.HMC_Logout.click();
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			deleteProfile(7);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public void renameFile(String filePath, File file) {
		File newFile = new File(filePath);
		if (newFile.exists()) {
			newFile.delete();
			System.out.println("Delete the exist csv file!");
		}
		file.renameTo(newFile);
		System.out.println("Rename the file name!");
	}

	public void creatFile(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
			System.out.println("Create file!");
		}
	}

	public void writeFile(File file, String newData) throws IOException {
		FileWriter fileWritter = new FileWriter(file);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write(newData);
		System.out.println("Write date in file!");
		bufferWritter.close();
	}

	public void uploadProfile(String searchProfile, String newFilePath, int i) {
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_DataUpload.click();
		hmcPage.DataUpload_inputIdentifier.clear();
		hmcPage.DataUpload_inputIdentifier.sendKeys(searchProfile);
		hmcPage.DataUpload_searchButton.click();
		System.out.println("Click search button!");
		WebElement searchResultElement = driver
				.findElement(By.xpath("(//div[contains(@id,'Content/StringDisplay[" + searchProfile + "]')])[1]"));
		Boolean displayResult = Common.checkElementExists(driver, searchResultElement, 30);
		Assert.assertTrue(displayResult, "Don't display the search result!");
		Common.doubleClick(driver, searchResultElement);

		hmcPage.DataUpload_UploadButton.click();
		System.out.println("Click upload button!");
		switchToWindow(1);

		WebElement uploadFile = hmcPage.UploadPage_ChooseFileButton;
		uploadFile.sendKeys(newFilePath);
		hmcPage.UploadPage_UploadButton.click();
		String returnInformation = hmcPage.UploadPage_UploadInformation.getText();
		if(i==3) {
			Assert.assertTrue(returnInformation.contains("Upload Successfully!"), "Upload fail!");
			System.out.println("Upload successfully!");
			driver.close();
			switchToWindow(0);
			hmcPage.PaymentLeasing_saveAndCreate.click();
		}
		
		if(i==8) {
			Assert.assertTrue(returnInformation.contains("PunchOut B2B Customer with uid USER-A already exists, please create another B2B Customer."));
			driver.close();
			switchToWindow(0);
		}
		
		if(i==10) {
			Assert.assertTrue(returnInformation.contains("Can not Process File: in line 5 : No result for the given example"));
			driver.close();
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

	public void deleteProfile(int i) {
		hmcPage.Home_B2BCommerceLink.click();
		hmcPage.Home_B2BCustomer.click();
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(profile);
		hmcPage.B2BCustomer_SearchButton.click();
		if (i == 2) {
			if (driver
					.findElement(By.xpath(
							"(//table[@id='Content/McSearchListConfigurable[B2BCustomer]_innertable']/tbody/tr)[3]"))
					.isDisplayed()) {
				Common.rightClick(driver, driver.findElement(By.xpath(
						"(//table[@id='Content/McSearchListConfigurable[B2BCustomer]_innertable']/tbody/tr)[3]")));
				driver.findElement(By
						.xpath("//td[@id='Content/McSearchListConfigurable[B2BCustomer]_!select_visible_true_label']"))
						.click();
				driver.findElement(By.xpath("//img[@id='Content/McSearchListConfigurable[B2BCustomer][delete]_img']"))
						.click();
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}

		}

		if (i == 7) {
			Assert.assertTrue(Common.checkElementExists(driver, hmcPage.searchUser, 10));
		}

	}

}
