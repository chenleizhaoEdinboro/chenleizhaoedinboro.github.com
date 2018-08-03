package TestScript.B2C;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Pages.HMCPage;
import TestData.PropsUtils;
import TestScript.SuperTestClass;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SHOP34Test extends SuperTestClass {
	public HMCPage hmcPage;
	private String siteType = "B2C";
	private String mt1 = "22TP2TXX13Y20LD";
	private String mt2 = "22TP2TXX13Y20LE";
	private String mt3 = "22TP2TXX13Y20LF";
	private String mt = "22TP2TXX13Y20LD,22TP2TXX13Y20LE,22TP2TXX13Y20LF";
	private String optionID = "4X90L66916";
	private String store = "usweb";
	private String b2bUnit = "";
	private String columnTitle = "store,unit,machineType,optionPartNum,optionDesc,optionType,baseWarrantyKey,supportOS,templateName,groupName,assignedStatus,validateRuleStatus";

	public SHOP34Test(String store) {
		this.Store = store;
		this.testName = "SHOPE-34";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "productbuilder", "p2", "b2c" })
	public void SHOP34(ITestContext ctx) {
		try {

			this.prepareTest();

			String filePath = "D:\\SHOP34\\";

			deleteFile(filePath);

			changeChromeDefDownFolder(filePath);
			hmcPage = new HMCPage(driver);

			// B2C
			String catalog = "masterMultiCountryProductCatalog-Online";

			downloadPBValidateCSV(catalog, siteType, mt, optionID, store, b2bUnit);

			ArrayList<String> hmcData = convertHmcValue();

			for (String text : hmcData)
				System.out.println(text);

			Common.sleep(6000);
			String downloadFile = filePath + "ProductBuilderValidateResult.csv";
			File file = new File(downloadFile);
			Assert.assertTrue(file.exists());

			ArrayList<String> csvData = convertCSV(downloadFile);
			for (String text : csvData)
				System.out.println(text);
			Assert.assertEquals(hmcData, csvData);
			driver.switchTo().defaultContent();
			hmcPage.hmcHome_hmcSignOut.click();

			// OUTLET
			siteType = "OUTLET";
			store = "outletus";

			deleteFile(filePath);
			downloadPBValidateCSV(catalog, siteType, mt, optionID, store, b2bUnit);

			hmcData = convertHmcValue();

			for (String text : hmcData)
				System.out.println(text);

			Common.sleep(6000);
			downloadFile = filePath + "ProductBuilderValidateResult.csv";
			file = new File(downloadFile);
			Assert.assertTrue(file.exists());

			csvData = convertCSV(downloadFile);
			for (String text : csvData)
				System.out.println(text);
			Assert.assertEquals(hmcData, csvData);
			driver.switchTo().defaultContent();
			hmcPage.hmcHome_hmcSignOut.click();

			// B2B
			siteType = "B2B";
			mt1 = "22TP2TT570020HA";
			mt2 = "22TP2TXX12Y20JE";
			mt3 = "22TP2TXX15G20HQ";
			mt = "22TP2TT570020HA,22TP2TXX12Y20JE,22TP2TXX15G20HQ";
			optionID = "4X30H56867";
			b2bUnit = "1213577815";
			store = "usle";

			deleteFile(filePath);
			downloadPBValidateCSV(catalog, siteType, mt, optionID, store, b2bUnit);

			hmcData = convertHmcValue();

			for (String text : hmcData)
				System.out.println(text);

			Common.sleep(6000);
			downloadFile = filePath + "ProductBuilderValidateResult.csv";
			file = new File(downloadFile);
			Assert.assertTrue(file.exists());

			csvData = convertCSV(downloadFile);
			for (String text : csvData)
				System.out.println(text);
			Assert.assertEquals(hmcData, csvData);
			driver.switchTo().defaultContent();
			hmcPage.hmcHome_hmcSignOut.click();
			
			deleteFile(filePath);

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	private ArrayList<String> convertHmcValue() {
		ArrayList<String> hmcData = new ArrayList<String>();
		int lineTotal = hmcPage.PBValidate_storeColumn.size();
		for (int i = 0; i < lineTotal; i++) {
			if (i != 0) {
				Assert.assertEquals(hmcPage.PBValidate_storeColumn.get(i).getText(), store);
				String mtText = hmcPage.PBValidate_machineTypeColumn.get(i).getText().trim();
				Assert.assertTrue(mtText.equals(mt1) || mtText.equals(mt2) || mtText.equals(mt3), "mtText: " + mtText + " is not in " + mt);
				Assert.assertEquals(hmcPage.PBValidate_optionPartNumColumn.get(i).getText().trim(), optionID);
				if(siteType.equals("B2B")) {
					Assert.assertEquals(hmcPage.PBValidate_unitColumn.get(i).getText().trim(), b2bUnit);
				}
			}
			String temp = getValue(hmcPage.PBValidate_storeColumn.get(i).getText(), i) + ",";
			temp = temp + getValue(hmcPage.PBValidate_unitColumn.get(i).getText(), i) + ",";
			temp = temp + getValue(hmcPage.PBValidate_machineTypeColumn.get(i).getText(), i) + ",";
			temp = temp + getValue(hmcPage.PBValidate_optionPartNumColumn.get(i).getText(), i) + ",";
			temp = temp + getValue(hmcPage.PBValidate_optionDescColumn.get(i).getText(), i) + ",";
			temp = temp + getValue(hmcPage.PBValidate_optionTypeColumn.get(i).getText(), i) + ",";
			temp = temp + getValue(hmcPage.PBValidate_baseWarrantyKeyColumn.get(i).getText(), i) + ",";
			temp = temp + getValue(hmcPage.PBValidate_supportOSColumn.get(i).getText(), i) + ",";
			temp = temp + getValue(hmcPage.PBValidate_templateNameColumn.get(i).getText(), i) + ",";
			temp = temp + getValue(hmcPage.PBValidate_groupNameColumn.get(i).getText(), i) + ",";
			temp = temp + getValue(hmcPage.PBValidate_assignedStatusColumn.get(i).getText(), i) + ",";
			temp = temp + getValue(hmcPage.PBValidate_validateRuleStatusColumn.get(i).getText(), i);
			hmcData.add(temp);
			if (i == 0)
				Assert.assertEquals(temp, columnTitle);
		}
		return hmcData;
	}

	private String getValue(String text, int i) {
		if (text.equals("N/A"))
			text = "";
		else if (i == 0) {
			Pattern p = Pattern.compile("\\s*");
			Matcher m = p.matcher(text);
			text = m.replaceAll("");
		} else {
			Pattern p = Pattern.compile("\\n*");
			Matcher m = p.matcher(text);
			text = m.replaceAll("");
		}
		return text;
	}

	private void deleteFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		if (file.exists()) {
			File[] files = file.listFiles();
			for (File file1 : files) {
				file1.delete();
			}
		}
	}

	private void changeChromeDefDownFolder(String downloadFilepath) throws IOException {
		driver.close();

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(cap);

		driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	private void downloadPBValidateCSV(String catalog, String siteType, String mt, String optioinID, String store, String b2bUnit) throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Nemo_productBuilder.click();
		hmcPage.productBuilder_productBuilderValidate.click();
		driver.switchTo().frame(hmcPage.PBValidate_iframe);
		Select catalogSel = new Select(hmcPage.PBValidate_catalogVersion);
		catalogSel.selectByVisibleText(catalog);
		Select siteTypeSel = new Select(hmcPage.PBValidate_siteType);
		siteTypeSel.selectByVisibleText(siteType);
		hmcPage.PBValidate_machineTypes.clear();
		hmcPage.PBValidate_machineTypes.sendKeys(mt);
		if (optioinID != "")
			hmcPage.PBValidate_optionCode.sendKeys(optioinID);
		hmcPage.PBValidate_store.sendKeys(store);
		if (b2bUnit != "")
			hmcPage.PBValidate_b2bunit.sendKeys(b2bUnit);
		hmcPage.PBValidate_validateButton.click();
		Common.sleep(12000);
		hmcPage.PBValidate_downloadButton.click();

	}

	private ArrayList<String> convertCSV(String csvFile) {
		BufferedReader br = null;
		String line = "";
		ArrayList<String> data = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader(csvFile));
			String temp = "";
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				if (line.contains("store,unit,machineType"))
					data.add(line);
				else if (!line.startsWith("\","))
					temp = temp + line;
				else {
					data.add(temp.replaceAll("\"", ""));
					temp = "";
				}
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
}
