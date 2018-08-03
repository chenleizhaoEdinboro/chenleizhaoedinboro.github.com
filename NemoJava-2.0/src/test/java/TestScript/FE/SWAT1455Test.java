package TestScript.FE;

import java.net.MalformedURLException;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.FEPage;
import Pages.HMCPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;

public class SWAT1455Test extends SuperTestClass{
	public FEPage fePage;
	public HMCPage hmcPage;
	public SWAT1455Test(String store,String country) {
		this.Store = store;
		this.testName = "SWAT1455Test";	
	}

	/*
	 * msharma2
	 * Entire Banner clickable
	 */
	@Test(groups={"fescripts"})
	public void BannerTest() throws InterruptedException, MalformedURLException {
		this.prepareTest();

		fePage = new FEPage(driver);
		hmcPage = new HMCPage(driver);
		System.out.println("inside test case");
		System.out.println(EnvData.SitDomain+"us/en/ww-hp#");


		driver.get(EnvData.SitDomain+"us/en/ww-hp#");	

		Thread.sleep(20000);	

		try{
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.pager.get(0));
			Thread.sleep(1500);
//			System.out.println(fePage.FirstBanner.getAttribute("data-url").split("IPromoID=")[1]);
//			String urlFirstBanner=fePage.FirstBanner.getAttribute("data-url").split("IPromoID=")[1];
			fePage.heroSectionImg.get(0).click();
			Thread.sleep(10000);
			String url = driver.getCurrentUrl();
			System.out.println(url);
//			Assert.assertTrue(url.contains(urlFirstBanner));
			System.out.println("Banner is completed");

			driver.navigate().back();
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.pager.get(0));
			Thread.sleep(1500);
			try {
				System.out.println(fePage.firstCTA.size());
				if(fePage.firstCTA.size()==2) {
					System.out.println("2 CTA are present");
				}
				else if (fePage.firstCTA.size()==1) {
					System.out.println("1 CTA are present");
				}
				else {
					System.out.println("No CTA or more than 2 CTA present ");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				boolean flag=fePage.firstCTA.get(0).isDisplayed();
				if(flag==true && fePage.firstCTA.get(0).getAttribute("title")!=" " || fePage.firstCTA.get(0).getAttribute("title")!=null) {
//					System.out.println(fePage.firstCTA.getAttribute("href").split("IPromoID=")[1]);
//					String urlFirstCTA=fePage.firstCTA.getAttribute("href").split("IPromoID=")[1];
					fePage.firstCTA.get(0).click();
					Thread.sleep(30000);
					String url1 = driver.getCurrentUrl();
					System.out.println(url1);
//					Assert.assertTrue(url1.contains(urlFirstCTA));
					Assert.assertTrue(url1.contains(url));
					System.out.println("First CTA is completed");
				}
			}
			catch(Exception e){
				System.out.println("first CTA is not present");
				e.printStackTrace();
			}


			driver.navigate().back();
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.pager.get(0));
			Thread.sleep(1500);

/*
			//scroll down to the second hero image
			driver.navigate().back();
			Thread.sleep(15000);
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",fePage.secondBanner);
			Thread.sleep(5000);
			String urlSecondBanner=fePage.secondBanner.getAttribute("data-url").split("IPromoID=")[1];
			System.out.println(fePage.secondBanner.getAttribute("data-url").split("IPromoID=")[1]);
			fePage.secondBanner.click();
			Thread.sleep(50000);
			String url2 = driver.getCurrentUrl();
			System.out.println(url2);			
			Assert.assertTrue(url2.contains(urlSecondBanner));
	*/
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		driver.close();
	}
}
