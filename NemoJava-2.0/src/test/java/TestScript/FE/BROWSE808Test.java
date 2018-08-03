package TestScript.FE;

import java.net.MalformedURLException;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.FEPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;

public class BROWSE808Test  extends SuperTestClass  {
	
	public FEPage fePage;
	
	public BROWSE808Test(String store) {
		this.Store = store;
		this.testName = "BROWSE808Test";
	}

	/*
	 * Ridun
	 * Compare functionality in Mobile
	 */
	@Test(priority=1,alwaysRun=true,groups={"migrationgroup"})
	public void compareMobile() throws MalformedURLException, InterruptedException 
	
	{
		
		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case  compare - mobile..");

		driver.get(EnvData.LenovoProdUS+"us/en/laptops/thinkpad/thinkpad-x/ThinkPad-X1-Carbon-6th-Gen/p/22TP2TXX16G#tab-customize");

		Thread.sleep(12000);
		
	//validating product title,price and the product added in popup 
		
		Dimension dimension = new Dimension(360,840);
		
		driver.manage().window().setSize(dimension);
		
		Thread.sleep(10000);
		
		driver.navigate().refresh();
		
		Thread.sleep(5000);
		
		String productTitle1 = fePage.accessoriesproductTitle.get(0).getText();
		
		System.out.println("prod title is: "+productTitle1);
		
		String saleprice1 = fePage.salePrice.get(0).getText();
		
		System.out.println("saleprice is: "+saleprice1);
		
		Thread.sleep(4500);
		
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.compareCheckBox.get(0));
		
		//fePage.compareCheckBox.get(0).click();
		
		System.out.println("clicked on compare check box..");
		
		Thread.sleep(4000);
		
		System.out.println(fePage.popupProduct.size());
		
		try {
			Assert.assertEquals(fePage.popupProduct.size(), 1,"Failed:number of product added to compare");
			
			System.out.println("Passed:First product added to compare");
			
			String popupProductTitle1 = fePage.popupProductTitle.get(0).getText();
			
			System.out.println("pop up prod title is: "+popupProductTitle1);
			
			Assert.assertEquals(productTitle1, popupProductTitle1);
			
			System.out.println("Passed:Title validation");
			
			String popupSalePrice1 = fePage.popupProductTitle.get(1).getText();
			
			System.out.println("pop up saleprice is: "+popupSalePrice1);
			
			Assert.assertEquals(saleprice1, popupSalePrice1);
			
			System.out.println("Passed:price validation");
			
			//validating minimum quantity of product added message
			
			Assert.assertTrue(fePage.minQuantityMsg.isDisplayed());
			
			System.out.println("Please select at least 2 products to compare displayed");
			
			System.out.println(fePage.compareButton.isEnabled());
			
			if(fePage.compareButton.isEnabled()==false)
			{
		//add second product and validate
				
				//fePage.compareCheckBox.get(1).click();
				
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.compareCheckBox.get(1));
				
				System.out.println("added second product..");
				
				Thread.sleep(2000);
				
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				
				jse.executeScript("window.scrollBy(0,250)");
				
				System.out.println(fePage.popupProduct.size());
				
				Assert.assertTrue(fePage.popupProduct.size()== 2,"Failed:number of product added to compare");
				
				System.out.println("Added product count is 2");	
				
				String productTitle2 = fePage.accessoriesproductTitle.get(1).getText();
				
				String saleprice2 = fePage.salePrice.get(1).getText();
				
				String popupProductTitle2 = fePage.popupProductTitle.get(3).getText();
				
				String popupSalePrice2 = fePage.popupProductTitle.get(4).getText();
				
				Assert.assertEquals(productTitle2, popupProductTitle2);
				
				Assert.assertEquals(saleprice2, popupSalePrice2);
				
				Thread.sleep(2500);
				
				Assert.assertEquals(fePage.compareButton.isEnabled(), true, "failed:Second product validation");
				
				System.out.println("Passed:Second product validation");
				
			}
			else{
				System.out.println("compare button is enabled for one product");
			}
			
			//add third product and validate
			
			//fePage.compareCheckBox.get(2).click();
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.compareCheckBox.get(2));
			
			System.out.println("added third product..");
			
			Thread.sleep(2000);
			
			System.out.println(fePage.popupProduct.size());
			
			Assert.assertTrue(fePage.popupProduct.size()== 3,"Failed:number of product added to compare");
			
			System.out.println("Added product count is 3");	
			
			String productTitle3 = fePage.accessoriesproductTitle.get(2).getText();
			
			String saleprice3 = fePage.salePrice.get(2).getText();
			
			String popupProductTitle3 = fePage.popupProductTitle.get(6).getText();
			
			String popupSalePrice3 = fePage.popupProductTitle.get(7).getText();
			
			Assert.assertEquals(productTitle3, popupProductTitle3);
			
			Assert.assertEquals(saleprice3, popupSalePrice3);
			
			Assert.assertEquals(fePage.compareButton.isEnabled(), true, "failed:Third product validation");
			
			System.out.println("Passed:Third product validation");
			
		//add fourth product and validate
			
			//fePage.compareCheckBox.get(3).click();
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.compareCheckBox.get(3));
			
			System.out.println("added fourth product..");
			
			Thread.sleep(2000);
			
			System.out.println(fePage.popupProduct.size());
			
			Assert.assertTrue(fePage.popupProduct.size()== 4,"Failed:number of product added to compare");
			
			System.out.println("Added product count is 4");	
			
			JavascriptExecutor jse = (JavascriptExecutor) driver;	
			
			jse.executeScript("window.scrollBy(0,250)");
			
			String productTitle4 = fePage.accessoriesproductTitle.get(3).getText();
			
			String saleprice4 = fePage.salePrice.get(3).getText();
			
			String popupProductTitle4 = fePage.popupProductTitle.get(9).getText();
			
			String popupSalePrice4 = fePage.popupProductTitle.get(10).getText();
			
			Assert.assertTrue(productTitle4.contains(popupProductTitle4));
			
			Assert.assertEquals(saleprice4, popupSalePrice4);
			
			Assert.assertEquals(fePage.compareButton.isEnabled(), true, "failed:Fourth product validation");
			
			System.out.println("Passed:Fourth product validation");
			
		//add fifth product and validate
			
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.compareCheckBox.get(4));
			
			System.out.println("added fifth product..");
			
			Thread.sleep(2000);
			
			System.out.println(fePage.popupProduct.size());
			
			Assert.assertTrue(fePage.popupProduct.size()== 4,"Failed:number of product added to compare");
			
			System.out.println("Product not added");	
			
			Assert.assertEquals(fePage.fourItemsMsg.isDisplayed(),true);
			
			Assert.assertEquals(fePage.compareButton.isEnabled(), true, "failed:Fifth product validation");
			
			System.out.println("Passed:Fifth product validation");
			
			
	
	} catch (Exception e) {
		
		e.printStackTrace();
		
		System.out.println("Failed:number of product added to compare");
	}
	
	
	}
	
	@Test(priority=2,alwaysRun=true,groups={"migrationgroup"})
	public void customizeMobile() throws MalformedURLException, InterruptedException 
	{
		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside customize mobile Validation..");

		driver.get(EnvData.LenovoProdUS+"us/en/laptops/thinkpad/thinkpad-x/ThinkPad-X1-Carbon-6th-Gen/p/22TP2TXX16G#tab-customize");
		
		Thread.sleep(15000);
		
		Dimension dimension = new Dimension(360,840);
		
		driver.manage().window().setSize(dimension);
		
		Thread.sleep(10000);
		
		driver.navigate().refresh();
		
		Thread.sleep(5000);
		
		//validate customize button navigation
		
				int cusButtonSize =fePage.customizeButton.size();
				
				System.out.println(cusButtonSize);
				
				for(int i=0;i<cusButtonSize;i++)
				{
					
					Thread.sleep(3500);
					
					//fePage.customizeButton.get(i).click();
					
					((JavascriptExecutor)driver).executeScript("arguments[0].click();", fePage.customizeButton.get(i));
					
					System.out.println("clicked"+i);
					
					Thread.sleep(8000);
					
					Assert.assertEquals(fePage.customizeHeader.isDisplayed(),true);
					
					driver.navigate().back();
					
					Thread.sleep(8000);
				}
					
					System.out.println("Passed:customization");
			}
		
		
		
	}
	
	

