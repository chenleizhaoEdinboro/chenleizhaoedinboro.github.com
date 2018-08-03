package TestScript.FE;


import java.net.MalformedURLException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.FEPage;
import Pages.HMCPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;

public class  SWAT487Test  extends SuperTestClass {
	public FEPage b2cPage;
	public HMCPage hmcPage;
	public String storeValue;

	public  SWAT487Test(String store,String country) {
		this.Store = store;
		this.testName = "SWAT487Test";
		storeValue=store;

	}

	/*
	 * 	msharma2
	 * 	validation for hero section carousel images and espot images in home page
	 */


	@Test(groups={"fescripts"})
	public void homePage() throws InterruptedException, MalformedURLException {
		this.prepareTest();

		try {
			b2cPage = new FEPage(driver);
			hmcPage = new HMCPage(driver);
			System.out.println("inside test case");

			System.out.println(EnvData.SitDomain+"us/en/ww-hp");
			driver.get(EnvData.SitDomain+"us/en/ww-hp");
			Thread.sleep(10000);
			// validate that hero section image is active one only at a time(by clicking next carousel)


			System.out.println("hero section image size is: "+b2cPage.heroSectionImg.size());

			Thread.sleep(1000);

			for(int i=0;i<b2cPage.heroSectionImg.size();i++) {
				System.out.println("hero section image: "+b2cPage.heroSectionImg.get(i).getAttribute("class"));
				System.out.println("hero section pager: "+b2cPage.pager.get(i).getAttribute("class"));

				if(b2cPage.heroSectionImg.get(i).getAttribute("class").contains("active")){
					Assert.assertTrue(b2cPage.pager.get(i).getAttribute("class").contains("active"),"respective pager is not active");

					JavascriptExecutor je = (JavascriptExecutor)driver;
					((JavascriptExecutor)driver).executeScript("window.scrollBy(0,80)", "");
					je.executeScript("arguments[0].click();",b2cPage.next.get(0));
//					b2cPage.next.get(0).click();

					if(i!=b2cPage.heroSectionImg.size()-1) { 					 
						Thread.sleep(1000);
						System.out.println(b2cPage.heroSectionImg.get(i+1).getAttribute("class"));
						Assert.assertTrue(b2cPage.heroSectionImg.get(i+1).getAttribute("class").contains("active"),"next image is not active after clicking on next carousel");
						Assert.assertTrue(b2cPage.pager.get(i+1).getAttribute("class").contains("active"),"next pager is not highlighted");
						System.out.println("next image and pager is active after clicking next carousel");
					}
					else {
						System.out.println("inside else");
						Assert.assertTrue(b2cPage.heroSectionImg.get(0).getAttribute("class").contains("active"));
						Assert.assertTrue(b2cPage.pager.get(0).getAttribute("class").contains("active"));
						System.out.println("next image and pager is active after clicking next carousel : inside else");
					}
				}

			}

			// validate the first espot section below the hero section

			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,400)", "");
			Thread.sleep(5000);
			System.out.println("espot without slider size is: "+b2cPage.espot.get(0).findElements(By.xpath("div/div[contains(@class,'m-espot')]")).size());
			int espotSize=b2cPage.espot.get(0).findElements(By.xpath("div/div[contains(@class,'m-espot')]")).size();
			Assert.assertEquals(4,espotSize);
			Thread.sleep(5000);

			for(int i=0;i<b2cPage.espot.get(0).findElements(By.xpath("div/div[contains(@class,'m-espot')]")).size();i++) {

				Thread.sleep(3000);

				// validate title, href and desc is present for each espot image				
				try {
					//System.out.println(b2cPage.espotNoSlider.get(i).findElement(By.tagName("img")).isDisplayed());
					Assert.assertTrue(b2cPage.espot.get(0).findElements(By.xpath("div/div[contains(@class,'m-espot')]")).get(i).findElement(By.tagName("img")).isDisplayed(),"espot img is not present");
					Assert.assertTrue(b2cPage.espot.get(0).findElements(By.xpath("div/div[contains(@class,'m-espot')]")).get(i).findElement(By.xpath("p[@class='m-espot__title']")).isDisplayed(),"espot title is not present");
					System.out.println(b2cPage.espot.get(0).findElements(By.xpath("div/div[contains(@class,'m-espot')]")).get(i).findElement(By.xpath("p[@class='m-espot__desc']")).getAttribute("innerHTML"));
					System.out.println(b2cPage.espot.get(0).findElements(By.xpath("div/div[contains(@class,'m-espot')]")).get(i).findElement(By.xpath("p[@class='m-espot__desc']")).getAttribute("class"));
					Assert.assertEquals(b2cPage.espot.get(0).findElements(By.xpath("div/div[contains(@class,'m-espot')]")).get(i).findElement(By.xpath("p[@class='m-espot__desc']")).getAttribute("class"),"m-espot__desc","desc paragraph tag is not present");
					Assert.assertTrue(b2cPage.espot.get(0).findElements(By.xpath("div/div[contains(@class,'m-espot')]")).get(i).findElement(By.xpath("p[@class='m-espot__desc']")).getAttribute("innerHTML")!="","desc is not present");
				}
				catch(Exception e) {
					System.out.println("inside catch");
					e.printStackTrace();
				}

				String Imghref=null;
				try {
					Imghref=b2cPage.espot.get(0).findElements(By.xpath("div/div[contains(@class,'m-espot')]")).get(i).findElement(By.tagName("a")).getAttribute("href");
					System.out.println(Imghref);
				}
				catch(Exception e) {

				}


				// validate clicking on each espot is navigating to correct url or not
				b2cPage.espot.get(0).findElements(By.xpath("div/div[contains(@class,'m-espot')]")).get(i).findElement(By.className("m-espot__link")).click();
				Thread.sleep(3000);
//				System.out.println("new page: "+driver.findElement(By.xpath("//div[@class='navWrapper']")).isDisplayed());
//				//				String url=driver.getCurrentUrl();
//				Assert.assertTrue(driver.findElement(By.xpath("//div[@class='navWrapper']")).isDisplayed());
//				driver.navigate().back();
//				Thread.sleep(5000);
				String winHandleBefore = driver.getWindowHandle();
				Set<String> allWindows = driver.getWindowHandles();
				System.out.println("all windows handle size is : "+allWindows.size());
				if(allWindows.size()>1){
					for(String currentWindow : allWindows){
						driver.switchTo().window(currentWindow);
					}
					System.out.println("new page: "+driver.findElement(By.xpath("//div[@class='navWrapper']")).isDisplayed());
					Assert.assertTrue(driver.findElement(By.xpath("//div[@class='navWrapper']")).isDisplayed());
					driver.close();
					// Switch back to original browser (first window)
					driver.switchTo().window(winHandleBefore);
				}

				else {
					System.out.println("new page: "+driver.findElement(By.xpath("//div[@class='navWrapper']")).isDisplayed());
					Assert.assertTrue(driver.findElement(By.xpath("//div[@class='navWrapper']")).isDisplayed());
					driver.navigate().back();
				}


			}

			// scroll down to the second hero image
			//			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",b2cPage.secondHeroImgDiv.get(1));
			//			Assert.assertTrue(b2cPage.secondHeroImg.isDisplayed(),"second hero img is not present");

			// validate that image and links are present in second hero section
			//			System.out.println(b2cPage.secondHeroImgDiv.get(1).findElement(By.tagName("img")).isDisplayed());
			//			System.out.println(b2cPage.secondHeroImgDiv.get(1).findElement(By.tagName("img")).getAttribute("title"));
			//			System.out.println(b2cPage.secondHeroImgDiv.get(1).findElements(By.tagName("a")).size());
			//			Assert.assertTrue(b2cPage.secondHeroImgDiv.get(1).findElements(By.tagName("img")).size()==1,"one image in not present in second hero section");
			//			String imgTitle=b2cPage.secondHeroImgDiv.get(1).findElement(By.tagName("img")).getAttribute("title");
			//
			//			//		Assert.assertTrue(b2cPage.secondHeroImgDiv.get(1).findElements(By.tagName("a")).size()==2,"two links are not present in second hero section");
			//			Assert.assertTrue(b2cPage.secondHeroImgDiv.get(1).findElements(By.xpath("//a[contains(@class,bannerLink)]")).get(0).isEnabled()," first link inside second hero section is not enabled");
			//			Assert.assertTrue(b2cPage.secondHeroImgDiv.get(1).findElements(By.xpath("//a[contains(@class,bannerLink)]")).get(1).isEnabled(),"second link inside second hero section is not enabled");

			//clicking on link in second hero section and validate the title
			//		Actions a=new Actions(driver);
			//		a.moveToElement(b2cPage.secondHeroImgDiv.get(1).findElements(By.tagName("a")).get(2)).click().build().perform();
			//		Thread.sleep(3000);
			//		System.out.println(driver.getTitle());
			//		System.out.println(imgTitle);
			//		Assert.assertTrue(driver.getTitle().contains(imgTitle));
			//
			//		driver.navigate().back();
			//		Thread.sleep(10000);

			driver.navigate().refresh();
			Thread.sleep(15000);

			// scroll down to second espot section
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,2000)", "");
			Thread.sleep(20000);


			// validating each espot image title, desc and href and click on each espot and validate the navigated url

			//		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",b2cPage.espot.get(1));
			System.out.println("espot size is: "+b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).size());
			Assert.assertTrue(b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).size()<=8,"there are more than 8 image in espot slider");

			for(int i=0;i<b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).size();i++){
				try {
					((JavascriptExecutor)driver).executeScript("window.scrollBy(0,2000)", "");
					System.out.println(b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).get(i).findElement(By.tagName("img")).isDisplayed());
					Assert.assertTrue(b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).get(i).findElement(By.tagName("img")).isDisplayed(),"img is not present in spot slider");
					Assert.assertTrue(b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).get(i).findElement(By.xpath("p[@class='m-espot__title']")).isDisplayed(),"title is not present in spot slider");
					System.out.println(b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).get(i).findElement(By.xpath("p[@class='m-espot__desc']")).getAttribute("innerHTML"));
					System.out.println(b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).get(i).findElement(By.xpath("p[@class='m-espot__desc']")).getAttribute("class"));
					Assert.assertEquals(b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).get(i).findElement(By.xpath("p[@class='m-espot__desc']")).getAttribute("class"),"m-espot__desc","desc paragraph tag is not present");
					Assert.assertTrue(b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).get(i).findElement(By.xpath("p[@class='m-espot__desc']")).getAttribute("innerHTML")!="","desc is not present");

					String title=b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).get(i).findElement(By.xpath("p[@class='m-espot__title']")).getAttribute("innerHTML");
					//					b2cPage.espot.get(i).findElement(By.tagName("img")).click();

					// clicking each image in espot and validate the title of navigated page
					boolean flag=b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).get(i).findElement(By.tagName("img")).isEnabled();
					System.out.println(flag);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).get(i).findElement(By.tagName("img")));
					Thread.sleep(10000);

					String winHandleBefore = driver.getWindowHandle();
					Set<String> allWindows = driver.getWindowHandles();
					System.out.println("all windows handle size is : "+allWindows.size());
					if(allWindows.size()>1){
						for(String currentWindow : allWindows){
							driver.switchTo().window(currentWindow);
						}
						System.out.println(title);
						System.out.println("new page: "+driver.findElement(By.xpath("//div[@class='navWrapper']")).isDisplayed());
						Assert.assertTrue(driver.findElement(By.xpath("//div[@class='navWrapper']")).isDisplayed());
						driver.close();
						// Switch back to original browser (first window)
						driver.switchTo().window(winHandleBefore);
					}

					else {
						System.out.println(title);
						System.out.println("new page: "+driver.findElement(By.xpath("//div[@class='navWrapper']")).isDisplayed());
						Assert.assertTrue(driver.findElement(By.xpath("//div[@class='navWrapper']")).isDisplayed());
						driver.navigate().back();
					}

					Assert.assertTrue(driver.getTitle().contains(title),"page is not navigating properly after clickingon espot slider images");

					Thread.sleep(10000);
					System.out.println(i);

					// if there are more than 4 spots then click next to see other espot images
										if(i==3 && b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).size()>4) {
											int j=(b2cPage.espot.get(1).findElements(By.xpath("div/div/div/div[contains(@class,'lslide')]")).size())-4;
											System.out.println("j value is: "+j);
											Thread.sleep(7000);
											for(int k=0;k<j;k++) {
												b2cPage.SecEspotNext.get(1).findElement(By.xpath("div/div/div[2]/a[2]")).click();		
												Thread.sleep(90000);
											}
											
										}

				}
				catch(Exception e) {
					System.out.println("inside catch");
					e.printStackTrace();
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		driver.close();
	}
}
