package TestScript.B2C;

import java.util.Set;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class NA23950Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String Url;
	public String UnitID = "bpcto_us_insight_direct_usa_unit";
	public NA23950Test(String Store , String Url){
		this.Store = Store;
		this.Url = Url;
		this.testName = "NA-23950";
	}
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"accountgroup", "account", "p2", "b2c"})
	public void NA23950(ITestContext ctx){
		try{
			this.prepareTest();
			hmcPage =new HMCPage(driver);
			b2cPage =new B2CPage(driver);
			// step:1 
			Dailylog.logInfoDB(1, "Put *@yopmail.com as a valid email for registration;"
					+ "Go to HMC -> B2C Commerce -> B2C Unit -> Search BP CTO insight unit", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			driver.findElement(By.xpath("//*[@class='listtable']/tbody/tr[2]/td[4]/div/div/table/tbody/tr/td/input")).sendKeys(UnitID);
			driver.findElement(By.xpath("//*[@class='xp-button chip-event']/a")).click();
			boolean is_unit_find = Common.isElementExist(driver, By.xpath("//div/div/table/tbody/tr/td[2]/a"));
			Assert.assertTrue(is_unit_find);
			//step:2
			Dailylog.logInfoDB(2, "Double click the unitGo to Site Attribute tab->Email Domain ValidationRight click "
					+ "and choose 'Create new'", Store, testName);
			driver.findElement(By.xpath("//div/div/table/tbody/tr/td[2]/a")).click();
			driver.findElement(By.xpath("//span[contains(@id,'Content/EditorTab[B2CUnit.tab.siteattributes]')]")).click();
			String domainValue = "*@yopmail.com";
			boolean flag_before = Common.isElementExist(driver, By.xpath("//div[@id='resultlist_Content/GenericResortableList[1]']/table[@class='listtable']/tbody/tr/td/div/div/table[@class='stringLayoutChip']/tbody/tr/td/input[@value='*@yopmail.com']"));
			System.out.println("flag_before is :" + flag_before);
			if(!flag_before){
//				Common.scrollToElement(driver, driver.findElement(By.xpath(".//*[@id='resultlist_Content/GenericResortableList[1]']/table/tbody/tr/th[@class='gatlcEntry']/div[@class='gatlcEntry']")));
				Common.rightClick(driver, driver.findElement(By.xpath(".//*[@id='resultlist_Content/GenericResortableList[1]']/table/tbody/tr/th[@class='gatlcEntry']/div[@class='gatlcEntry']")));
				driver.findElement(By.xpath(".//*[@id='Content/GenericResortableList[1]_!add_true_label']")).click();
				driver.findElement(By.xpath("//input[contains(@id,'Content/StringEditor[in Content/GenericResortableList')]")).sendKeys(domainValue);
		        driver.findElement(By.xpath("//div[contains(@id,'Content/ImageToolbarAction[organizer.editor.save.title]')]")).click();
			}
			boolean flag_after = Common.isElementExist(driver, By.xpath("//div[@id='resultlist_Content/GenericResortableList[1]']/table[@class='listtable']/tbody/tr/td/div/div/table[@class='stringLayoutChip']/tbody/tr/td/input[@value='*@yopmail.com']"));
			System.out.println("flag_after is :" + flag_after);
			Assert.assertTrue(flag_after);
			driver.findElement(By.xpath("//img[contains(@id,'closesession')]")).click();
			// step: 3 and  4
			Dailylog.logInfoDB(3, "Create customer account A and step 4", Store, testName);
			String email_prefix_A = "customer"+Common.getDateTimeString();
			String account_A = email_prefix_A + "@yopmail.com";
			System.out.println("account_A is :" + account_A);
			createAccount(b2cPage,account_A);
			//step:5
			Dailylog.logInfoDB(5, " Active account created in step 3", Store, testName);
			String yopmail_url = "http://www.yopmail.com";
			activeAccount(yopmail_url,email_prefix_A);
			// Step:6
			Dailylog.logInfoDB(6, " Create approver account B", Store, testName);
			String email_prefix_B = "approver"+Common.getDateTimeString();
			String account_B = email_prefix_B + "@yopmail.com";
			System.out.println("account_B is :" + account_B);
			createAccount(b2cPage,account_B);
			activeAccount(yopmail_url,email_prefix_B);
			//Step:7
			Dailylog.logInfoDB(7, "Go to HMC -> Go to HMC -> B2C Commerce -> B2C Unit -> Search BP CTO insight unit", Store, testName);
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			hmcPage.Home_B2CCommercelink.click();
			hmcPage.Home_B2CUnitLink.click();
			driver.findElement(By.xpath("//*[@class='listtable']/tbody/tr[2]/td[4]/div/div/table/tbody/tr/td/input")).sendKeys(UnitID);
			driver.findElement(By.xpath("//*[@class='xp-button chip-event']/a")).click();	
			driver.findElement(By.xpath("//div/div/table/tbody/tr/td[2]/a")).click();
			// step:8
			Dailylog.logInfoDB(8, "Go to Approvers tab, find BPCTO Order approver "
					+ "Right click at the form to add customer. "
					+ "At popup page, search the ID of approver B"
					+ " Choose the account and click 'Use'", Store, testName);
			String winHandle_Main = driver.getWindowHandle();
			System.out.println("main winHandle is :" + winHandle_Main);
			driver.findElement(By.xpath(".//*[@id='Content/EditorTab[B2CUnit.tab.b2bunit.approver]_span']")).click();
			Common.rightClick(driver, driver.findElement(By.xpath("(//div[contains(@id,'resultlist_Content/GenericResortableItemList')]/table[contains(@id,'table_MC')])[2]")));
			driver.findElement(By.xpath("(//*[@id='Content/GenericResortableItemList[1]_!add_true_label'])[last()]")).click();
			Set<String> set_approver = driver.getWindowHandles();
			for(String str : set_approver){
				if(str.equals(winHandle_Main))
					continue;
				driver.switchTo().window(str);
			}
			driver.findElement(By.xpath(".//*[@id='StringEditor[in GenericCondition[Customer.uid]]_input']")).clear();
			driver.findElement(By.xpath(".//*[@id='StringEditor[in GenericCondition[Customer.uid]]_input']")).sendKeys(account_B);
			driver.findElement(By.xpath("//*[@id='ModalGenericFlexibleSearchOrganizerSearch[Customer]_searchbutton']")).click();
			driver.findElement(By.xpath("//*[@id='StringDisplay["+account_B+"]_span']")).click();
			driver.findElement(By.xpath("//div[@class='xp-button chip-event']/a/span[contains(text(),'Use')]")).click();
			Thread.sleep(5000);
			Set<String> set_set = driver.getWindowHandles();
			for(String str : set_set){
				System.out.println("str is :" + str);
				driver.switchTo().window(str);
			}
			//step:9
			Dailylog.logInfoDB(9, "Go back to Approvers tab, double click the approver just added", Store, testName);
			Actions action = new Actions(driver);
			action.doubleClick(driver.findElement(By.xpath("//div[contains(@id,'Content/StringDisplay["+account_B+"]_span')]")));
			action.perform();
			Thread.sleep(120000);
			System.out.println("---------------------------");
			// step:10
			Dailylog.logInfoDB(10, "Find 'Groups' to add a group", Store, testName);
			Set<String> set_addGroup = driver.getWindowHandles();
			for(String str : set_addGroup){
				if(str.equals(winHandle_Main))
					continue;
				driver.switchTo( ).window(str);
			}
			driver.findElement(By.xpath("(//input[contains(@id,'ajaxinput_AutocompleteReferenceEditor[in GenericItemList')])[1]")).sendKeys("bpctoApprover");
			Common.javascriptClick(driver, driver.findElement(By.xpath("//td[contains(.,'BPCTOApprover')]")));
			Common.javascriptClick(driver, driver.findElement(By.xpath("//img[contains(@id,'ImageToolbarAction[organizer.editor.save.title')]")));
			boolean b = driver.findElement(By.xpath("//div[contains(@id,'StringDisplay[BPCTOApprover]') and @class='gilcEntry']")).isDisplayed();
			Assert.assertTrue(b);
		}catch(Throwable e){
			handleThrowable(e, ctx);
		}
	}
    public void createAccount(B2CPage b2cPage,String account){
    	driver.get(Url);
		driver.findElement(By.xpath("//a[contains(@href,'registration')]")).click();
		b2cPage.RegistrateAccount_EmailIdTextBox.clear();
		b2cPage.RegistrateAccount_EmailIdTextBox.sendKeys(account);
		b2cPage.RegistrateAccount_ConfirmEmailTextBox.clear();
		b2cPage.RegistrateAccount_ConfirmEmailTextBox.sendKeys(account);
		b2cPage.RegistrateAccount_FirstNameTextBox.clear();
		b2cPage.RegistrateAccount_FirstNameTextBox.sendKeys("TEST");
		b2cPage.RegistrateAccount_LastNameTextBox.clear();
		b2cPage.RegistrateAccount_LastNameTextBox.sendKeys("Test");
		b2cPage.RegistrateAccount_PasswordTextBox.clear();
		b2cPage.RegistrateAccount_PasswordTextBox.sendKeys("1q2w3e4r");
		b2cPage.RegistrateAccount_ConfirmPasswordTextBox.clear();
		b2cPage.RegistrateAccount_ConfirmPasswordTextBox.sendKeys("1q2w3e4r");
		b2cPage.RegistrateAccount_AcceptTermsCheckBox.click();
		b2cPage.RegistrateAccount_OptionCheckBox.click();
		b2cPage.RegistrateAccount_CreateAccountButton.click();
    }
	public void activeAccount(String url,String account){
		driver.get(url);
		Common.sleep(60000);
		driver.findElement(By.xpath("//*[@id='login']")).clear();
		driver.findElement(By.xpath("//*[@id='login']")).sendKeys(account);
		driver.findElement(By.xpath(".//*[@id='f']//input[@class='sbut']")).click();
		Common.sleep(10000);
		driver.switchTo().frame("ifmail");
		String winHandle = driver.getWindowHandle().toString();
		System.out.println("winHandle is :" + winHandle);
		driver.findElement(By.xpath("//a[contains(.,'Activate')]")).click();
		Common.sleep(10000);
		String winHandle_1 = driver.getWindowHandle().toString();
		System.out.println("winHandle_1 is :" + winHandle_1);
		Set<String> set_A = driver.getWindowHandles();
		System.out.println("set_A size  is :" + set_A.size());
		for(String str : set_A){
			System.out.println("str is :" + str);
			if(!str.equals(winHandle)){
				driver.switchTo().window(str);
				driver.close();
			}
		}
		driver.switchTo().window(winHandle);
	}
}
