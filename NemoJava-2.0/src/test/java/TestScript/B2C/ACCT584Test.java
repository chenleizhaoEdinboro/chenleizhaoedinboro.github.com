package TestScript.B2C;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class ACCT584Test extends SuperTestClass {
    public HMCPage hmcPage;
    public B2CPage b2cPage;

    public ACCT584Test(String store) {
        this.Store = store;
        this.testName = "ACCT-584";
    }

    @Test(priority = 0, enabled = true, alwaysRun = true)
    public void ACCT584(ITestContext ctx) throws Exception{

        try {
            this.prepareTest();

            hmcPage = new HMCPage(driver);
            //1, open HMC homepage
            driver.get(testData.HMC.getHomePageUrl());

            if (Common.isElementExist(driver, By.id("Main_user"))) {
                HMCCommon.Login(hmcPage, testData);
                driver.navigate().refresh();
            }
            hmcPage.Home_B2CCommercelink.click();
            hmcPage.Home_B2CUnitLink.click();
            hmcPage.B2CUnit_IDTextBox.sendKeys("us_smb");
            hmcPage.B2CUnit_SearchButton.click();
            hmcPage.B2CUnit_SearchResultItem.click();
            hmcPage.B2CUnit_SiteAttributeTab.click();
            Common.scrollToElement(hmcPage.PageDriver,hmcPage.PageDriver.findElement(By.xpath("//input[@id='Content/IntegerEditor[in Content/Attribute[B2CUnit.zSesssionTimerAlertMins]]_input']")));
            hmcPage.B2CUnit_SessionTimerAlert_TextField.clear();

            hmcPage.B2CUnit_SessionTimerAlert_TextField.sendKeys("120000");

            hmcPage.B2CUnit_SessionTimerMsg_TextField.clear();
            hmcPage.B2CUnit_SessionTimerMsg_TextField.sendKeys("Your secure Connection is about to time-out. <span id='timer1'>${minSessionTime}</span> Would you like to remain logged on?");

            //Save the change on HMC page
            hmcPage.SaveButton.click();

            b2cPage = new B2CPage(driver);
            driver.get("https://pre-c-hybris.lenovo.com/us/en/smbpro/gatekeeper/showpage?toggle=GatekeeperSMB");
            b2cPage.SMB_LoginButton.click();
            b2cPage.SMB_uName.sendKeys("brassgeko@consultant.com");
            b2cPage.SMB_uPwd.sendKeys("r3d@C0rd");
            b2cPage.SMB_signIn.click();
           // b2cPage.myAccount_link.click();
            //Wait the session timer pop up
            Thread.sleep(2000);
            Common.waitElementVisible(b2cPage.PageDriver, b2cPage.PageDriver.findElement(By.xpath("//input[@id='inactivity_ok']")));

            b2cPage.sessionStayLoggedIn.click();
            b2cPage.Navigation_CartIcon.click();
            Thread.sleep(3000);
            b2cPage.sessionlogOutButton.click();
            Thread.sleep(3000);

            //Login smb home page Second time,Go to PLP page.
            //Wait one minute,when the popup display,doesn’t select any option check the  status.
            driver.get("https://pre-c-hybris.lenovo.com/us/en/smbpro/gatekeeper/showpage?toggle=GatekeeperSMB");
            b2cPage.SMB_LoginButton.click();
            b2cPage.SMB_uName.sendKeys("brassgeko@consultant.com");
            b2cPage.SMB_uPwd.sendKeys("r3d@C0rd");
            b2cPage.SMB_signIn.click();
            b2cPage.Navigation_ProductsLink.click();
            b2cPage.Navigation_Laptop.click();
            b2cPage.ExploreAllLaptops.click();

            //Roll back to HMC home page and clear HMC time session settings.
            driver.get(testData.HMC.getHomePageUrl());

            if (Common.isElementExist(driver, By.id("Main_user"))) {
                HMCCommon.Login(hmcPage, testData);
                driver.navigate().refresh();
            }
            hmcPage.Home_B2CCommercelink.click();
            hmcPage.Home_B2CUnitLink.click();
            hmcPage.B2CUnit_IDTextBox.sendKeys("us_smb");
            hmcPage.B2CUnit_SearchButton.click();
            hmcPage.B2CUnit_SearchResultItem.click();
            hmcPage.B2CUnit_SiteAttributeTab.click();
            Common.scrollToElement(hmcPage.PageDriver,hmcPage.PageDriver.findElement(By.xpath("//input[@id='Content/IntegerEditor[in Content/Attribute[B2CUnit.zSesssionTimerAlertMins]]_input']")));
            hmcPage.B2CUnit_SessionTimerAlert_TextField.clear();
            hmcPage.B2CUnit_SessionTimerMsg_TextField.clear();
            //B2CCommon.handleGateKeeper(b2cPage, testData);

            //2, go to alc login page
            Dailylog.logInfoDB(2, "go to alc login page", Store, testName);

            Common.javascriptClick(driver, driver.findElement(By.xpath("//ul[contains(@class,'general_Menu')]/li[@id='myAccount']//li[contains(@class,'guest_menu_header')]//div[@class='link_text']")));

            Thread.sleep(10000);

            String alcUrl = driver.getCurrentUrl().toString();

            //Assert.assertTrue(alcUrl.contains(alcDomain) && alcUrl.contains(testData.B2C.getHomePageUrl().split("/")[3]));

            //3, login on the alc page
            Dailylog.logInfoDB(3, "login on the alc page", Store, testName);


        } catch (Throwable e) {
            handleThrowable(e, ctx);
        }
    }
}
