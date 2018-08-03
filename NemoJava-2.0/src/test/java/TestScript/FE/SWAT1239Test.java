package TestScript.FE;

import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.FEPage;
import Pages.HMCPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;

/*
 * msharma2
 *  Validation for "Accessories Page UI for Deals Page"
 */
public class  SWAT1239Test  extends SuperTestClass {
	public FEPage fePage;
	public HMCPage hmcPage;

	public  SWAT1239Test(String store,String country) {
		this.Store = store;
		this.testName = "SWAT1239Test";

	}

	/*
	 * 	msharma2
	 * 	validation for accessories section in deals page
	 */

	
	@Test(groups={"fescripts"})
	public void deals_accessoriesTab() throws InterruptedException, MalformedURLException {
		this.prepareTest();
		boolean pass=false;
		int j=0;
		try {
			fePage = new FEPage(driver);
			hmcPage = new HMCPage(driver);
			System.out.println("inside test case");
		
			System.out.println(EnvData.SitDomain+"us/en/test-new-accessories");
			driver.get(EnvData.SitDomain+"us/en/test-new-accessories");

			fePage.activenavItems.get(3).click();
			driver.manage().timeouts().implicitlyWait(20000,TimeUnit.SECONDS);
			System.out.println("products in accessorires is: "+fePage.accessoryLayout.findElements(By.xpath("//div[contains(@class,'o-productSummary')]")).size());
			//			System.out.println("images size is:"+fePage.slickdraggable.get(0).findElement(By.xpath("//div[@class='slick-track']")).findElements(By.xpath("//div[contains(@id,'slick-slide')]")).size());

			for(int k=0;k<fePage.accessoryLayout.findElements(By.xpath("//div[contains(@class,'o-productSummary')]")).size();k++) {
				for(int i=0;i<fePage.slickyDots.get(k).findElements(By.tagName("li")).size();i++){
					((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.slickyDots.get(k).findElements(By.tagName("li")).get(i));
					Thread.sleep(5000);					
					//					System.out.println("images size is:"+driver.findElements(By.xpath("//div[contains(@id,'slick-slide')]")).get(j).getAttribute("class"));
					String s=driver.findElements(By.xpath("//div[contains(@id,'slick-slide')]")).get(j).getAttribute("class");
					Assert.assertTrue(s.contains("active"));
					// uncomment this line to check product image is present or not
					// Assert.assertTrue(driver.findElements(By.xpath("//div[contains(@id,'slick-slide')]")).get(j).findElement(By.tagName("img")).isDisplayed());
					j=j+1;						
				}

				//	System.out.println(driver.findElements(By.xpath("//h3[@class='m-productTitle__name']")).get(k).getText());
				//	System.out.println(driver.findElements(By.xpath("//ul[@class='m-pricingSummary__details']")).get(k).getText());
				Assert.assertTrue(driver.findElements(By.xpath("//h3[@class='m-productTitle__name']")).get(k).isDisplayed());
				Assert.assertTrue(driver.findElements(By.xpath("//h4[@class='m-productTitle__desc']")).get(k).isDisplayed());
				Assert.assertTrue(driver.findElements(By.xpath("//ul[@class='m-pricingSummary__details']")).get(k).isDisplayed());
				Assert.assertTrue(driver.findElements(By.xpath("//a[@class='a-link a-fullSpecs']")).get(k).isDisplayed());

				Thread.sleep(3000);
				// clicking on product title and validate the navigated page
				String productTitle=driver.findElements(By.xpath("//h3[@class='m-productTitle__name']")).get(k).findElement(By.tagName("a")).getAttribute("href");
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElements(By.xpath("//h3[@class='m-productTitle__name']")).get(k).findElement(By.tagName("a")));
				Thread.sleep(10000);

				String s1=driver.findElement(By.xpath("//meta[@name='productcode']")).getAttribute("content");
				Assert.assertTrue(productTitle.contains(s1));
				driver.navigate().back();
				Thread.sleep(5000);

				// clicking on shop now button and validate the navigated page
				try {
					System.out.println(driver.findElements(By.xpath("//a[contains(@class,'a-button')]")).get(k).isEnabled());
					boolean enable=driver.findElements(By.xpath("//a[contains(@class,'a-button')]")).get(k).isEnabled();
					if(enable){
						((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElements(By.xpath("//a[contains(@class,'a-button')]")).get(k));
						Thread.sleep(10000);
						String s2=driver.findElement(By.xpath("//meta[@name='productcode']")).getAttribute("content");
						Assert.assertTrue(productTitle.contains(s2));

						//clicking on add to cart for the product and validate the pop up

						((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.deals_addToCart);

						((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.dealsPopup_addToCart);
						Thread.sleep(5000);
						System.out.println(fePage.addedToCartMsg.getText());

						Assert.assertTrue(fePage.addedToCartMsg.isDisplayed());
						System.out.println(cssValue(fePage.addedToCartMsg.getCssValue("color")));
						Assert.assertTrue(cssValue(fePage.addedToCartMsg.getCssValue("color")).equals("#317900"));
						driver.navigate().back();
						Thread.sleep(5000);
					}
				}
				catch(Exception e) {
					System.out.println("Shop now is disabled for "+k+" product");
				}
				//clicking on full specs link and validate the navigated page
				String winHandleBefore = driver.getWindowHandle();
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElements(By.xpath("//a[@class='a-link a-fullSpecs']")).get(k));
				Thread.sleep(10000);

				Set<String> allWindows = driver.getWindowHandles();
				for(String currentWindow : allWindows){
					driver.switchTo().window(currentWindow);
				}

				// uncomment this line to check that tab specs tab is active or not
				//				Assert.assertTrue(driver.findElement(By.id("tab-li-nav-techspec")).findElement(By.tagName("span")).getAttribute("class").contains("active"));
				Assert.assertTrue(driver.getCurrentUrl().contains(s1));
				driver.close();
				// Switch back to original browser (first window)
				driver.switchTo().window(winHandleBefore);
				//				driver.navigate().back();
				Thread.sleep(5000);

			}

			pass=true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(pass);
		driver.close();
	}

	/*
	 * for fetching the css value from the page
	 */
	static String cssValue(String element){

		String[] hexValue;
		if(element.contains("a")) {
			hexValue = element.replace("rgba(", "").replace(")", "").replace(" ", "").split(",");
		}
		else {
			hexValue = element.replace("rgb(", "").replace(")", "").replace(" ", "").split(",");
		}
		System.out.println(hexValue[0]);
		System.out.println(hexValue[1]);
		System.out.println(hexValue[2]);
		hexValue[0] = hexValue[0].trim();
		int hexValue0 = Integer.parseInt(hexValue[0]);
		hexValue[1] = hexValue[1].trim();
		int hexValue1 = Integer.parseInt(hexValue[1]);
		hexValue[2] = hexValue[2].trim();
		int hexValue2 = Integer.parseInt(hexValue[2]);
		String actualColor = String.format("#%02x%02x%02x", hexValue0, hexValue1, hexValue2);
		return actualColor;
	}
}
