package TestScript.FE;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.FEPage;
import Pages.HMCPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;

/*
 * msharma2
 * SWAT-500
 */
public class  SWAT500Test  extends SuperTestClass {
	public FEPage fePage;
	public HMCPage hmcPage;

	public  SWAT500Test(String store,String country) {
		this.Store = store;
		this.testName = "SWAT500Test";

	}

	/*
	 * 	msharma2
	 * 	validation deals section first tab
	 */


	@Test(groups={"fescripts"})
	public void dealsPage() throws InterruptedException, MalformedURLException {
		this.prepareTest();

		try {
			fePage = new FEPage(driver);
			hmcPage = new HMCPage(driver);
			System.out.println("inside test case");
	
			System.out.println(EnvData.SitDomain+"us/en/deals-and-coupons/laptop-deals-2");
			driver.get(EnvData.SitDomain+"us/en/deals-and-coupons/laptop-deals-2");

//			driver.get("http://web-content.sit.online.lenovo.com/us/en/test-new-deals");
			Thread.sleep(20000);
			int count=0;
			System.out.println(fePage.activenavItems.size());

			System.out.println("first div size is: "+driver.findElements(By.xpath("//div[contains(@id,'mainContent')]//div[contains(@class,'-singleLayout')]//div[contains(@class,'o-productSummary')]")).size());
			System.out.println("second div size is: "+driver.findElements(By.xpath("//div[contains(@id,'mainContent')]//div[contains(@class,'-doubleLayout')]//div[contains(@class,'o-productSummary')]")).size());
			System.out.println("third div size is: "+driver.findElements(By.xpath("//div[contains(@id,'mainContent')]//div[contains(@class,'-fourLayout')]//div[contains(@class,'o-productSummary')]")).size());

			Assert.assertEquals(driver.findElements(By.xpath("//div[contains(@id,'mainContent')]//div[contains(@class,'singleLayout')]//div[contains(@class,'o-productSummary')]")).size(),1);
			Assert.assertEquals(driver.findElements(By.xpath("//div[contains(@id,'mainContent')]//div[contains(@class,'doubleLayout')]//div[contains(@class,'o-productSummary')]")).size(),2);
			//			Assert.assertEquals(driver.findElements(By.xpath("//div[contains(@id,'dealsMainContainer')]/div[3]/div")).size(),4);

			// to check all 4 tabs color 
			for(int i=0;i<fePage.activenavItems.size();i++) {
				System.out.println(fePage.activenavItems.get(i).findElement(By.tagName("span")).getText());
				System.out.println(fePage.activenavItems.get(i).getCssValue("border-bottom"));
				System.out.println(fePage.activenavItems.get(i).findElement(By.tagName("span")).getCssValue("color"));

				// to validate color and border of active tab
				if(fePage.activenavItems.get(i).getAttribute("class").contains("active")) {
					String s1=cssValue(fePage.activenavItems.get(i).findElement(By.tagName("span")).getCssValue("color"));
					String s2=fePage.activenavItems.get(i).getCssValue("border-bottom");

					System.out.println("split:"+s2.split(" ")[0]);
					System.out.println("split:"+s2.split(" ")[1]);

					s2=s2.split(" ")[0]+" "+s2.split(" ")[1]+" "+s1;

					System.out.println(s1);
					System.out.println(s2);
					Assert.assertEquals(s1,"#ff6a00");
					Assert.assertEquals(s2,"6px solid #ff6a00");
					count++;
				}
			}

			// to validate that only 1 tab is active at one time
			Assert.assertTrue(count==1);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		driver.close();
	}		

	// to validate the single layout of deals page
	@Test(groups={"fescripts"})
	public void dealsSingleLayout() throws InterruptedException, MalformedURLException {
		this.prepareTest();
		boolean pass=false;
		try {
			fePage = new FEPage(driver);
			hmcPage = new HMCPage(driver);
			System.out.println(EnvData.SitDomain+"us/en/deals-and-coupons/laptop-deals-2");
			driver.get(EnvData.SitDomain+"us/en/deals-and-coupons/laptop-deals-2");
			Thread.sleep(30000);

			// to validate single layout products
			System.out.println("slicky dots size is: "+fePage.carousel1.findElement(By.className("slick-dots")).findElements(By.tagName("li")).size());

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,250)", "");

			for(int i=0;i<fePage.carousel1.findElement(By.className("slick-dots")).findElements(By.tagName("li")).size();i++) {				
				Thread.sleep(2000);
				String s1=fePage.carousel1.findElement(By.className("slick-dots")).findElements(By.tagName("li")).get(i).getAttribute("class");	
				Assert.assertTrue(s1.contains("active"));

				String s2=fePage.carousel1.findElement(By.xpath("//div[@class='slick-list draggable']")).findElement(By.xpath("//div[@class='slick-track']")).findElements(By.xpath("//div[contains(@id,'slick-slide0')]")).get(i).getAttribute("class");

				Assert.assertTrue(s2.contains("active"));

				((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.carousel1.findElement(By.xpath("//button[contains(@class,'slick-next')]")));
			}



			Assert.assertTrue(fePage.singleLayout.findElement(By.xpath("//div[@class='m-productTitle']")).findElement(By.tagName("h4")).isDisplayed());
			Assert.assertTrue(fePage.singleLayout.findElement(By.xpath("//div[@class='m-productTitle']")).findElement(By.tagName("a")).isDisplayed());			
			Assert.assertTrue(fePage.singleLayout.findElement(By.xpath("//div[@class='m-productTitle']")).findElement(By.tagName("a")).isDisplayed());	
			Assert.assertTrue(fePage.singleLayout.findElement(By.xpath("//div[@class='m-productTitle']")).findElement(By.tagName("a")).isDisplayed());	
			Thread.sleep(5000);
			String productTitle=fePage.singleLayout.findElement(By.xpath("//div[@class='m-productTitle']")).findElement(By.tagName("a")).getAttribute("href");

			fePage.singleLayout.findElement(By.xpath("//div[@class='m-productTitle']")).findElement(By.tagName("a")).click();
			Thread.sleep(5000);
			System.out.println(driver.findElement(By.xpath("//meta[@name='productcode']")).getAttribute("content"));
			String s1=driver.findElement(By.xpath("//meta[@name='productcode']")).getAttribute("content");
			Assert.assertTrue(productTitle.contains(s1));
			driver.navigate().back();
			Thread.sleep(5000);

			Assert.assertTrue(fePage.singleLayout.findElement(By.xpath("//div[@class='m-priceSummary']")).isDisplayed());
			fePage.singleLayout.findElement(By.xpath("//div[@class='m-priceSummary']")).findElement(By.xpath("//a[contains(@class,'a-button')]")).click();
			Thread.sleep(5000);

			System.out.println(driver.findElement(By.xpath("//meta[@name='productcode']")).getAttribute("content"));
			String s2=driver.findElement(By.xpath("//meta[@name='productcode']")).getAttribute("content");
			Assert.assertTrue(productTitle.contains(s2));
			driver.navigate().back();
			Thread.sleep(5000);

			pass=true;

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(pass);
		driver.close();
	}

	// to validate the double layout of deals page
	@Test(groups={"fescripts"})
	public void dealsDoubleLayout() throws InterruptedException, MalformedURLException {
		this.prepareTest();
		boolean pass=false;
		try {
			fePage = new FEPage(driver);
			hmcPage = new HMCPage(driver);
			System.out.println(EnvData.SitDomain+"us/en/deals-and-coupons/laptop-deals-2");
			driver.get(EnvData.SitDomain+"us/en/deals-and-coupons/laptop-deals-2");
			
			// uncomment below line to execute in mobile
//			driver.manage().window().setSize(new Dimension(375, 812));
			
			Thread.sleep(20000);
			int j=0;
			for(int k=0;k<driver.findElements(By.xpath("//div[contains(@class,'o-productSummary')]")).size();k++) {
				for(int i=0;i<fePage.slickyDots.get(k).findElements(By.tagName("li")).size();i++){
					((JavascriptExecutor)driver).executeScript("arguments[0].click();",fePage.slickyDots.get(k).findElements(By.tagName("li")).get(i));
					Thread.sleep(20000);					
					System.out.println("images size is:"+driver.findElements(By.xpath("//div[contains(@id,'slick-slide')]")).get(j).getAttribute("class"));
					String s=driver.findElements(By.xpath("//div[contains(@id,'slick-slide')]")).get(j).getAttribute("class");
					Thread.sleep(2000);
					System.out.println("String: "+j+"  "+s);
					Assert.assertTrue(s.contains("active"));
					// uncomment this line to check product image is present or not
					// Assert.assertTrue(driver.findElements(By.xpath("//div[contains(@id,'slick-slide')]")).get(j).findElement(By.tagName("img")).isDisplayed());
					j=j+1;						
				}

				Assert.assertTrue(driver.findElements(By.xpath("//h3[@class='m-productTitle__name']")).get(k).isDisplayed());
				Assert.assertTrue(driver.findElements(By.xpath("//h4[@class='m-productTitle__desc']")).get(k).isDisplayed());
				Assert.assertTrue(driver.findElements(By.xpath("//ul[@class='m-pricingSummary__details']")).get(k).isDisplayed());
				Assert.assertTrue(driver.findElements(By.xpath("//a[@class='a-link a-fullSpecs']")).get(k).isDisplayed());
				
				try {
				Assert.assertTrue(driver.findElements(By.xpath("//dd[@class='bv-rating-ratio']")).get(k).isDisplayed());
				Assert.assertTrue(driver.findElements(By.xpath("//dd[@class='bv-rating-ratio-number']")).get(k).isDisplayed());
				Assert.assertTrue(driver.findElements(By.xpath("//dd[@class='bv-rating-ratio-count']")).get(k).isDisplayed());
				}
				catch(Exception e) {
					System.out.println("Ratings are not present for "+k+"product");
				}
				Thread.sleep(3000);

				// clicking on product title and validate the navigated page
				String productTitle=driver.findElements(By.xpath("//h3[@class='m-productTitle__name']")).get(k).findElement(By.tagName("a")).getAttribute("href");
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElements(By.xpath("//h3[@class='m-productTitle__name']")).get(k).findElement(By.tagName("a")));
				Thread.sleep(10000);

				String s1=driver.findElement(By.xpath("//meta[@name='productcode']")).getAttribute("content");
				Assert.assertTrue(productTitle.contains(s1));
				driver.navigate().back();
				Thread.sleep(10000);	

				// clicking on shop now button and validate the navigated page
				try {
					int c=k+1;
					System.out.println(driver.findElement(By.xpath("(//div[contains(@class,'o-productSummary')])["+c+"]//a[contains(@class,'a-button')]")).isDisplayed());
					System.out.println(driver.findElement(By.xpath("(//div[contains(@class,'o-productSummary')])["+c+"]//a[contains(@class,'a-button')]")).isEnabled());
					boolean enable=driver.findElement(By.xpath("(//div[contains(@class,'o-productSummary')])["+c+"]//a[contains(@class,'a-button')]")).isDisplayed();
					System.out.println(enable);
					if(enable){
						((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("(//div[contains(@class,'o-productSummary')])["+c+"]//a[contains(@class,'a-button')]")));
						Thread.sleep(10000);
						String s2=driver.findElement(By.xpath("//meta[@name='productcode']")).getAttribute("content");
						Assert.assertTrue(productTitle.contains(s2));
						driver.navigate().back();
						Thread.sleep(10000);
					}
				}
				catch(Exception e) {
					System.out.println("OUT OF STOCK!!! Shop now is disabled for "+k+" product");
				}

				//clicking on full specs link and validate the navigated page
				String winHandleBefore = driver.getWindowHandle();
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElements(By.xpath("//a[@class='a-link a-fullSpecs']")).get(k));
				Thread.sleep(10000);

				Set<String> allWindows = driver.getWindowHandles();
				for(String currentWindow : allWindows){
					driver.switchTo().window(currentWindow);
				}

				// uncomment this linw to check that tab specs tab is active or not
				// Assert.assertTrue(driver.findElement(By.id("tab-li-nav-techspec")).findElement(By.tagName("span")).getAttribute("class").contains("active"));
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


	// to validate the inventory msg in deals page
	@Test(groups={"fescripts"})
	public void dealsInvetoryValidation() throws InterruptedException, MalformedURLException {
		this.prepareTest();
		boolean pass=false;
		try {
			fePage = new FEPage(driver);
			hmcPage = new HMCPage(driver);
			System.out.println(EnvData.SitDomain+"us/en/deals-and-coupons/laptop-deals-2");
			driver.get(EnvData.SitDomain+"us/en/deals-and-coupons/laptop-deals-2");
			Thread.sleep(20000);
			boolean flag=false;
			boolean CTOFlag=true;
			String productType=null;
			int count=0;

			ArrayList<String> inventoryClass=new ArrayList<>();
			inventoryClass.add(driver.findElement(By.xpath("//div[contains(@id,'mainContent')]//div[contains(@class,'-singleLayout')]//div[contains(@class,'o-productSummary')]//div[contains(@class,'m-priceSummary')]/div[1]")).getAttribute("class"));
			inventoryClass.add(driver.findElement(By.xpath("//div[contains(@id,'mainContent')]//div[contains(@class,'-doubleLayout')]//div[contains(@class,'o-productSummary')][1]//div[contains(@class,'m-priceSummary')]/div[1]")).getAttribute("class"));
			inventoryClass.add(driver.findElement(By.xpath("//div[contains(@id,'mainContent')]//div[contains(@class,'-doubleLayout')]//div[contains(@class,'o-productSummary')][2]//div[contains(@class,'m-priceSummary')]/div[1]")).getAttribute("class"));

			int size=driver.findElements(By.xpath("//div[contains(@id,'mainContent')]//div[contains(@class,'-fourLayout')]//div[contains(@class,'o-productSummary')]")).size();
			for(int i=1;i<=size;i++) {
				inventoryClass.add(driver.findElement(By.xpath("//div[contains(@id,'mainContent')]//div[contains(@class,'-fourLayout')]//div[contains(@class,'o-productSummary')]["+i+"]//div[contains(@class,'m-priceSummary')]/div[1]")).getAttribute("class"));
				//			inventoryClass.add(driver.findElement(By.xpath("//div[contains(@id,'DealsMainContainerNewComponents1')]/div[3]/div["+i+"]/div[3]/div[1]")).getAttribute("class"));
				//			inventoryClass.add(driver.findElement(By.xpath("//div[contains(@id,'DealsMainContainerNewComponents1')]/div[3]/div["+i+"]/div[3]/div[1]")).getAttribute("class"));
				//			inventoryClass.add(driver.findElement(By.xpath("//div[contains(@id,'DealsMainContainerNewComponents1')]/div[3]/div["+i+"]/div[3]/div[1]")).getAttribute("class"));
			}

			System.out.println(inventoryClass.size());

			for(int k=0;k<driver.findElements(By.xpath("//div[contains(@class,'o-productSummary')]")).size();k++) {				
				try {
					int c=k+1;		

					//System.out.println(driver.findElements(By.xpath("//a[contains(@class,'a-button')]")).get(k).isEnabled());
					((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElements(By.xpath("//h3[@class='m-productTitle__name']")).get(k).findElement(By.tagName("a")));
					Thread.sleep(10000);
					//					((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElements(By.xpath("//a[contains(@class,'a-button')]")).get(k));
					//					Thread.sleep(10000);
					//clicking on add to cart for the product and validate the pop up

					//					System.out.println(driver.findElement(By.xpath("//a[@id='view-customize']")).getText());

					productType=driver.findElement(By.xpath("//a[@id='view-customize']")).getText();
					System.out.println(productType.toLowerCase());
					driver.navigate().back();
					Thread.sleep(5000);

					boolean enable=driver.findElement(By.xpath("(//div[contains(@class,'o-productSummary')])["+c+"]//a[contains(@class,'a-button')]")).isDisplayed();
					System.out.println(enable);
					System.out.println(inventoryClass.get(k));
					
					if(productType.toLowerCase().contains("models")) {	
						Assert.assertEquals(inventoryClass.get(k),"m-productStock");
						ArrayList<String> expectedinventoryMsg=new ArrayList<>();
						//ArrayList<String> actualinventoryMsg=new ArrayList<>();
						expectedinventoryMsg.add("Limited Stock,buy soon!");
						expectedinventoryMsg.add("Quantity Available");
						expectedinventoryMsg.add("In Stock");
						String regex = "\\d+";

						String s=driver.findElements(By.xpath("//div[@class='m-productStock']")).get(count).getText();
						System.out.println(s);

						if(s.equalsIgnoreCase(expectedinventoryMsg.get(0))) {
							System.out.println(s);
							flag=true;
						}
						else if(s.contains(expectedinventoryMsg.get(1))) {
							System.out.println(s);
							System.out.println(s.split(" ")[0]);
							System.out.println(s.split(" ")[0].matches(regex));//true  
							flag=true;
						}
						else if(s.equalsIgnoreCase(expectedinventoryMsg.get(2))) {
							System.out.println(s);
							flag=true;
						}			
						count++;						

					}
					else {
						if(inventoryClass.get(k).equalsIgnoreCase("m-productStock")) {
							System.out.println("inventory msg is present for "+k+" CTO product");
							count++;
							CTOFlag=false;
						}
						Assert.assertEquals(inventoryClass.get(k),"m-review");
					}

				}

				catch(Exception e) {
					if(productType.toLowerCase().contains("models")) {
						count++;
					}
					System.out.println("OUT OF STOCK!!! Shop now is disabled for "+k+" product");
				} 
			}

			System.out.println("count is: "+count);
			int number=driver.findElements(By.xpath("//div[@class='m-productStock']")).size();
			System.out.println(driver.findElements(By.xpath("//div[@class='m-productStock']")).size());
			System.out.println(count+"  "+" "+number+" "+flag+" "+CTOFlag);
			Assert.assertEquals(count,number);
			Assert.assertTrue(flag);
			Assert.assertTrue(CTOFlag);
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
