package TestScript.FE;

import java.net.MalformedURLException;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import junit.framework.Assert;

import Pages.FEPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;
/*
 * @owner Ridun
 * MastHead Navigation in Homepage
 */

public class  NA26729Test  extends SuperTestClass {
	public FEPage fePage;

	public  NA26729Test(String store) {
		this.Store = store;
		this.testName = "NA26729Test";
	}

	public static String cssValue(String element){

		String[] hexValue = element.replace("rgba(", "").replace(")", "").replace(" ", "").split(",");
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

	@Test(priority=1,groups={"migrationgroup"})

	public void laptop() throws InterruptedException, MalformedURLException {

		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case");

		System.out.println(EnvData.LenovoProdUS);

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(12000);

		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");

		Thread.sleep(3000);

		action.moveToElement(fePage.productsList.get(0)).build().perform();

		Thread.sleep(3000);

		System.out.println("clicked on laptop......");

		int LaptopMainLinksSize = fePage.laptopLinks.size();

		System.out.println("laptop Main links size is ...."+LaptopMainLinksSize);

		// Validation of Laptop level-1 node navigation and color

		for(int i=0;i<LaptopMainLinksSize;i++)
		{

			System.out.println("Laptop main link :"+fePage.laptopLinks.get(i).getText());

			action.moveToElement(fePage.laptopLinks.get(i)).build().perform();

			Thread.sleep(3000);

			System.out.println("mhover on laptop Main link.....");

			String LaptopMainLinkColor = fePage.laptopLinks.get(i).findElement(By.tagName("h3")).getCssValue("color");

			String LaptopMainLinkColorValue = NA26729Test.cssValue(LaptopMainLinkColor);

			System.out.println("LaptopMainLinkColorValue is : "+LaptopMainLinkColorValue);

			String expColorvalue="#2e9dc8";

			Thread.sleep(2000);



			Assert.assertTrue(LaptopMainLinkColorValue.equals(expColorvalue));

			System.out.println("LaptopMainLinkColor validation passed....");

			Thread.sleep(2000);	

			// Validate Laptop Level-2 node and level 3 node

			if(	fePage.laptopLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("brandlinks"))
			{
				int x=i+1;

				List<WebElement> laptopSublinks = driver.findElements(By.xpath("(//ul[@class='megaMenu_subSection_list'])[1]/li["+x+"]/ul[contains(@class,'menu')]/li"));

				int laptopSublinksSize = laptopSublinks.size();
				System.out.println("laptop sublink size is"+laptopSublinksSize);

				for(int j=0; j<laptopSublinksSize; j++)
				{
					action.moveToElement(laptopSublinks.get(j)).build().perform();

					Thread.sleep(3000);

					action.release();

					if(laptopSublinks.get(j).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu"))
					{
						System.out.println("No level 3 elements present");
					}

					else if(laptopSublinks.get(j).findElement(By.tagName("a")).getAttribute("class").contains("has-submenu"))
					{

						WebElement level3Links = driver.findElement(By.xpath("(//div[@class='megaMenu_contentSection'])[1]"));

						Assert.assertTrue(level3Links.isDisplayed()); 

						Thread.sleep(4500);

						System.out.println("level3 Links are Displayed");


					}


					Thread.sleep(3000);
				}
			}

			else if(fePage.laptopLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("has-submenu-explore-all-link"))
			{
				driver.findElement(By.xpath("//div[@class='megaMenu_contentSection']")).isDisplayed();

				System.out.println("Displayed............");
			}

			else if(fePage.laptopLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu-explore-all-link"))
			{
				System.out.println("No content section present");
			}
		}    
		//			catch(Exception e) {
		//
		//				e.printStackTrace();
		//		}
		driver.close();

	}	

	@Test(priority=2,groups={"migrationgroup"})

	public void desktop() throws InterruptedException, MalformedURLException {

		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case");

		System.out.println(EnvData.LenovoProdUS);

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(12000);

		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");

		Thread.sleep(3000);

		action.moveToElement(fePage.productsList.get(1)).build().perform();

		Thread.sleep(3000);

		System.out.println("clicked on Desktop......");

		int desktopMainLinksSize = fePage.desktopLinks.size();

		System.out.println("desktop MainLinks Size is ...."+desktopMainLinksSize);


		// Validation of Desktop level-1 node navigation and color

		for(int i=0;i<desktopMainLinksSize;i++)
		{

			System.out.println("desktop MainLinks :"+fePage.desktopLinks.get(i).getText());

			action.moveToElement(fePage.desktopLinks.get(i)).build().perform();

			Thread.sleep(3000);

			System.out.println("mhover on desktop MainLinks.....");

			String desktopMainLinkColor = fePage.desktopLinks.get(i).findElement(By.tagName("h3")).getCssValue("color");

			String desktopMainLinkColorValue = NA26729Test.cssValue(desktopMainLinkColor);

			System.out.println("desktop MainLinks Color Value is : "+desktopMainLinkColorValue);

			String expColorvalue="#2e9dc8";

			Assert.assertTrue(desktopMainLinkColorValue.equals(expColorvalue));

			System.out.println("desktopMainLinkColor validation passed....");

			Thread.sleep(2000);

			// Validate Desktop Level-2 node and level 3 node

			if(	fePage.desktopLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("brandlinks"))
			{
				int x=i+1;

				List<WebElement> desktopSublinks = driver.findElements(By.xpath("(//ul[@class='megaMenu_subSection_list'])[2]/li["+x+"]/ul[contains(@class,'menu')]/li"));

				int desktopSublinksSize = desktopSublinks.size();

				System.out.println("desktop sublink size is"+desktopSublinksSize);

				for(int j=0; j<desktopSublinksSize; j++)
				{
					action.moveToElement(desktopSublinks.get(j)).build().perform();

					Thread.sleep(3000);

					action.release();

					if(desktopSublinks.get(j).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu"))
					{
						System.out.println("No level 3 elements present");
					}

					else if(desktopSublinks.get(j).findElement(By.tagName("a")).getAttribute("class").contains("has-submenu"))
					{

						WebElement desktopLevel3Links = driver.findElement(By.xpath("(//div[@class='megaMenu_contentSection'])[2]"));

						Assert.assertTrue(desktopLevel3Links.isDisplayed()); 

						Thread.sleep(4500);

						System.out.println("desktop Level3 Links Links are Displayed");

					}

				}
			}

			else if(fePage.desktopLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("has-submenu-explore-all-link"))
			{
				driver.findElement(By.xpath("//div[@class='megaMenu_contentSection']")).isDisplayed();

				System.out.println("Displayed............");
			}

			else if(fePage.desktopLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu-explore-all-link"))
			{
				System.out.println("No content section present");
			}

		}

		driver.close();
	}


	@Test(priority=3,groups={"migrationgroup"})

	public void workstations() throws InterruptedException, MalformedURLException {

		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case");

		System.out.println(EnvData.LenovoProdUS);

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(12000);

		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");

		Thread.sleep(3000);

		action.moveToElement(fePage.productsList.get(2)).build().perform();

		Thread.sleep(3000);

		System.out.println("clicked on workstations......");

		int workstationMainLinksSize = fePage.workstationLinks.size();

		System.out.println("workstation MainLinks Size is ...."+workstationMainLinksSize);

		// Validation of workstation level-1 node navigation and color

		for(int i=0;i<workstationMainLinksSize;i++)
		{

			System.out.println("workstation MainLinks :"+fePage.workstationLinks.get(i).getText());

			action.moveToElement(fePage.workstationLinks.get(i)).build().perform();

			Thread.sleep(3000);

			System.out.println("mhover on workstation MainLinks.....");

			String workstationMainLinkColor = fePage.workstationLinks.get(i).findElement(By.tagName("h3")).getCssValue("color");

			String workstationMainLinkColorValue = NA26729Test.cssValue(workstationMainLinkColor);

			System.out.println("workstation MainLinks Color Value is : "+workstationMainLinkColor);

			String expColorvalue="#2e9dc8";

			Assert.assertTrue(workstationMainLinkColorValue.equals(expColorvalue));

			System.out.println("workstation MainLinkColor validation passed....");

			Thread.sleep(2000);

			// Validate workstations Level-2 node and level 3 node

			if(	fePage.workstationLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("brandlinks"))
			{
				int x=i+1;

				List<WebElement> workstationSublinks = driver.findElements(By.xpath("(//ul[@class='megaMenu_subSection_list'])[3]/li["+x+"]/ul[contains(@class,'menu')]/li"));

				int workstationSublinksSize = workstationSublinks.size();

				System.out.println("workstation sublink size is"+workstationSublinksSize);

				for(int j=0; j<workstationSublinksSize; j++)
				{
					action.moveToElement(workstationSublinks.get(j)).build().perform();

					Thread.sleep(3000);

					action.release();

					if(workstationSublinks.get(j).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu"))
					{
						System.out.println("No level 3 elements present");
					}

					else if(workstationSublinks.get(j).findElement(By.tagName("a")).getAttribute("class").contains("has-submenu"))
					{

						WebElement workstationLevel3Links = driver.findElement(By.xpath("(//div[@class='megaMenu_contentSection'])[3]"));

						Assert.assertTrue(workstationLevel3Links.isDisplayed()); 

						Thread.sleep(4500);

						System.out.println("workstation Level3 Links Links are Displayed");

					}

				}
			}
			else if(fePage.workstationLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("has-submenu-explore-all-link"))
			{
				driver.findElement(By.xpath("//div[@class='megaMenu_contentSection']")).isDisplayed();

				System.out.println("Displayed............");
			}

			else if(fePage.workstationLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu-explore-all-link"))
			{
				System.out.println("No content section present");
			}
		}

		driver.close();
	}


	@Test(priority=4,groups={"migrationgroup"})

	public void phoneTablets() throws InterruptedException, MalformedURLException {

		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case");

		System.out.println(EnvData.LenovoProdUS);

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(12000);

		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");

		Thread.sleep(3000);

		action.moveToElement(fePage.productsList.get(3)).build().perform();

		Thread.sleep(3000);

		System.out.println("clicked on phone,Tablets......");

		int phoneTabletsMainLinksSize = fePage.phoneTabletsLinks.size();

		System.out.println("phoneTablets MainLinks Size is ...."+phoneTabletsMainLinksSize);

		// Validation of phoneTablets level-1 node navigation and color

		for(int i=0;i<phoneTabletsMainLinksSize;i++)
		{

			System.out.println("phoneTablets MainLinks :"+fePage.phoneTabletsLinks.get(i).getText());

			action.moveToElement(fePage.phoneTabletsLinks.get(i)).build().perform();

			Thread.sleep(3000);

			System.out.println("mhover on phoneTablets MainLinks.....");

			String phoneTabletsMainLinkColor = fePage.phoneTabletsLinks.get(i).findElement(By.tagName("h3")).getCssValue("color");

			String phoneTabletsMainLinkColorValue = NA26729Test.cssValue(phoneTabletsMainLinkColor);

			System.out.println("phoneTablets MainLinks Color Value is : "+phoneTabletsMainLinkColorValue);

			String expColorvalue="#2e9dc8";

			Assert.assertTrue(phoneTabletsMainLinkColorValue.equals(expColorvalue));

			System.out.println("phoneTablets MainLink Color validation passed....");

			Thread.sleep(2000);

			// Validate phoneTablets Level-2 node and level 3 node

			if(	fePage.phoneTabletsLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("brandlinks"))
			{
				int x=i+1;

				List<WebElement> phoneTabletsSublinks = driver.findElements(By.xpath("(//ul[@class='megaMenu_subSection_list'])[4]/li["+x+"]/ul[contains(@class,'menu')]/li"));

				int phoneTabletsSublinksSize = phoneTabletsSublinks.size();

				System.out.println("workstation sublink size is"+phoneTabletsSublinksSize);

				for(int j=0; j<phoneTabletsSublinksSize; j++)
				{
					action.moveToElement(phoneTabletsSublinks.get(j)).build().perform();

					Thread.sleep(3000);

					action.release();

					if(phoneTabletsSublinks.get(j).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu"))
					{
						System.out.println("No level 3 elements present");
					}

					else if(phoneTabletsSublinks.get(j).findElement(By.tagName("a")).getAttribute("class").contains("has-submenu"))
					{

						WebElement phoneTabletsLevel3Links = driver.findElement(By.xpath("(//div[@class='megaMenu_contentSection'])[4]"));

						Assert.assertTrue(phoneTabletsLevel3Links.isDisplayed()); 

						Thread.sleep(4500);

						System.out.println("phoneTablets Level3 Links Links are Displayed");

					}

				}
			}
			else if(fePage.phoneTabletsLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("has-submenu-explore-all-link"))
			{
				driver.findElement(By.xpath("//div[@class='megaMenu_contentSection']")).isDisplayed();

				System.out.println("Displayed............");
			}

			else if(fePage.phoneTabletsLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu-explore-all-link"))
			{
				System.out.println("No content section present");
			}

		}        
		driver.close();    
	}

	@Test(priority=5,groups={"migrationgroup"})

	public void gaming() throws InterruptedException, MalformedURLException {

		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case");

		System.out.println(EnvData.LenovoProdUS);

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(12000);

		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");

		Thread.sleep(3000);

		action.moveToElement(fePage.productsList.get(4)).build().perform();

		Thread.sleep(3000);

		System.out.println("clicked on Gaming......");

		int gamingMainLinksSize = fePage.gamingLinks.size();

		System.out.println("gaming MainLinks Size is ...."+gamingMainLinksSize);

		// Validation of Gaming level-1 node navigation and color

		for(int i=0;i<gamingMainLinksSize;i++)
		{

			System.out.println("gaming MainLinks :"+fePage.gamingLinks.get(i).getText());

			action.moveToElement(fePage.gamingLinks.get(i)).build().perform();

			Thread.sleep(3000);

			System.out.println("mhover on gaming MainLinks.....");

			String gamingMainLinkColor = fePage.gamingLinks.get(i).findElement(By.tagName("h3")).getCssValue("color");

			String gamingMainLinkColorValue = NA26729Test.cssValue(gamingMainLinkColor);

			System.out.println("gaming MainLinks Color Value is : "+gamingMainLinkColorValue);

			String expColorvalue="#2e9dc8";

			Assert.assertTrue(gamingMainLinkColorValue.equals(expColorvalue));

			System.out.println("gaming MainLink Color validation passed....");

			Thread.sleep(2000);

			// Validate phoneTablets Level-2 node

			if(	fePage.gamingLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("brandlinks"))
			{
				int x=i+1;

				List<WebElement> gamingSublinks = driver.findElements(By.xpath("(//ul[@class='megaMenu_subSection_list'])[5]/li["+x+"]/ul[contains(@class,'menu')]/li"));

				int gamingSublinksSublinksSize = gamingSublinks.size();

				System.out.println("Gaming sublink size is"+gamingSublinksSublinksSize);

				for(int j=0; j<gamingSublinksSublinksSize; j++)
				{
					action.moveToElement(gamingSublinks.get(j)).build().perform();

					Thread.sleep(3000);

					action.release();

					if(gamingSublinks.get(j).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu"))
					{
						System.out.println("No level 3 elements present");
					}

					else if(gamingSublinks.get(j).findElement(By.tagName("a")).getAttribute("class").contains("has-submenu"))
					{

						WebElement gamingLevel3Links = driver.findElement(By.xpath("(//div[@class='megaMenu_contentSection'])[4]"));

						Assert.assertTrue(gamingLevel3Links.isDisplayed()); 

						Thread.sleep(4500);

						System.out.println("Gaming Level3 Links Links are Displayed");

					}

				}
			}
			else if(fePage.gamingLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("has-submenu-explore-all-link"))
			{
				driver.findElement(By.xpath("//div[@class='megaMenu_contentSection']")).isDisplayed();

				System.out.println("Displayed............");
			}

			else if(fePage.gamingLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu-explore-all-link"))
			{
				System.out.println("Gaming -No content section present");
			}

		} 

		driver.close();       
	}		



	@Test(priority=6,groups={"migrationgroup"})

	public void accessoriesAndMonitors() throws InterruptedException, MalformedURLException {
		this.prepareTest();


		fePage = new FEPage(driver);

		System.out.println("inside test case");

		System.out.println(EnvData.LenovoProdUS);

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(12000);

		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");

		action.moveToElement(fePage.productsList.get(5)).build().perform();

		Thread.sleep(3000);

		System.out.println("clicked on Accessories And Monitors......");

		int accessoriesAndMonitorsSize = fePage.accessoriesAndMonitorsLinks.size();

		System.out.println("Accessories And MonitorsLinks size is ...."+accessoriesAndMonitorsSize);

		//validate Accessories and Monitors links and css validation

		for(int i=0;i<accessoriesAndMonitorsSize;i++)
		{
			action.moveToElement(fePage.accessoriesAndMonitorsLinks.get(i)).build().perform();

			Thread.sleep(3000);

			System.out.println("mhover on accessories And Monitors Links.....");

			String accessoriesAndMonitorsLinksColor = fePage.accessoriesAndMonitorsLinks.get(i).findElement(By.tagName("h3")).getCssValue("color");

			String accessoriesAndMonitorsLinksColorValue = NA26729Test.cssValue(accessoriesAndMonitorsLinksColor);

			System.out.println("accessoriesAndMonitorsLinksColor value is : "+accessoriesAndMonitorsLinksColorValue);

			String expColorvalue="#2e9dc8";

			Thread.sleep(2000);

			Assert.assertTrue(accessoriesAndMonitorsLinksColorValue.equals(expColorvalue));

			Thread.sleep(2000);

			System.out.println("Accessories And MonitorsLinksColor validation passed....");
		}
		driver.close();
	}

	@Test(priority=7,groups={"migrationgroup"})

	public void serversAndStorage() throws InterruptedException, MalformedURLException {
		this.prepareTest();


		fePage = new FEPage(driver);

		System.out.println("inside test case");


		System.out.println(EnvData.LenovoProdUS);

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(12000);

		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");

		action.moveToElement(fePage.productsList.get(6)).build().perform();

		Thread.sleep(3000);

		System.out.println("clicked on servers And Storage......");

		int serversAndStorageLinksSize = fePage.serversAndStorageLinks.size();

		System.out.println("servers And Storage Link size is ...."+serversAndStorageLinksSize);

		//validate servers And Storage links and css validation

		for(int i=0;i<serversAndStorageLinksSize;i++)
		{
			action.moveToElement(fePage.serversAndStorageLinks.get(i)).build().perform();

			Thread.sleep(3000);

			System.out.println("mhover on servers AndS torag eLinks.....");

			String serversAndStorageLinksColor = fePage.serversAndStorageLinks.get(i).findElement(By.tagName("h3")).getCssValue("color");

			String serversAndStorageLinksColorValue = NA26729Test.cssValue(serversAndStorageLinksColor);

			System.out.println("accessoriesAndMonitorsLinksColor value is : "+serversAndStorageLinksColorValue);

			String expColorvalue="#2e9dc8";

			Thread.sleep(2000);

			Assert.assertTrue(serversAndStorageLinksColorValue.equals(expColorvalue));

			Thread.sleep(2000);

			System.out.println("servers And Storage Links Color validation passed....");
		}

		driver.close();
	}

	@Test(priority=8,groups={"migrationgroup"})

	public void serviceAndWarranty() throws InterruptedException, MalformedURLException {
		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case");		

		System.out.println(EnvData.LenovoProdUS);

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(12000);

		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");

		action.moveToElement(fePage.productsList.get(7)).build().perform();

		Thread.sleep(3000);

		System.out.println("clicked on service And Warranty......");

		int serviceAndWarrantyLinksSize = fePage.serviceAndWarrantyLinks.size();

		System.out.println("service And Warranty Links Size is ...."+serviceAndWarrantyLinksSize);

		//validate service And Warranty links and css validation

		for(int i=0;i<serviceAndWarrantyLinksSize;i++)
		{
			action.moveToElement(fePage.serviceAndWarrantyLinks.get(i)).build().perform();

			Thread.sleep(3000);

			System.out.println("mhover on service And Warranty Links.....");

			String serviceAndWarrantyLinksColor = fePage.serviceAndWarrantyLinks.get(i).findElement(By.tagName("h3")).getCssValue("color");

			String serviceAndWarrantyLinksColorrValue = NA26729Test.cssValue(serviceAndWarrantyLinksColor);

			System.out.println("service And Warranty Links Colorr Value value is : "+serviceAndWarrantyLinksColorrValue);

			String expColorvalue="#2e9dc8";

			Thread.sleep(2000);

			Assert.assertTrue(serviceAndWarrantyLinksColorrValue.equals(expColorvalue));

			Thread.sleep(2000);

			System.out.println("service And Warranty Links Color Value validation passed....");
		}

		driver.close();
	}

	@Test(priority=9,groups={"migrationgroup"})	
	public void dealsValidation() throws MalformedURLException
	{
		this.prepareTest();

		try {
			fePage = new FEPage(driver);

			System.out.println("inside test case");	

			System.out.println(EnvData.LenovoProdUS);

			driver.get(EnvData.LenovoProdUS+"us/en");

			Thread.sleep(15000);

			Actions action = new Actions(driver);

			action.moveToElement(fePage.dealsLink).build().perform();

			Thread.sleep(2000);

			System.out.println("Dealslist size is"+fePage.dealsList.size());

			// Deals link and css validation

			for(int m=0;m<fePage.dealsList.size()-1;m++)
			{
				action.moveToElement(fePage.dealsList.get(m)).build().perform();

				Thread.sleep(5000);

				String expColorvalue="#2e9dc8";

				String dealLinkColor = fePage.dealsList.get(m).findElement(By.className("link_text")).getCssValue("color");

				String dealLinkColorValue = NA26729Test.cssValue(dealLinkColor);
				
				System.out.println("dealLinkColorValue: "+dealLinkColorValue);
				
				Thread.sleep(2500);

				//Assert.assertEquals(dealLinkColorValue, expColorvalue, "Sublink css value mismatch");
				
				Assert.assertTrue(dealLinkColorValue.contains(expColorvalue));

				System.out.println("Deals link color validation passed... "+fePage.dealsList.get(m).getText());


		//	}
			//deals -feature deal -validation

			Thread.sleep(2000);
			
			if(fePage.dealsList.get(m).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu"))
				{
					System.out.println("No deals present");
				}
				else{
					
					action.moveToElement(fePage.dealsList.get(m)).build().perform();

					Thread.sleep(2000);

					Assert.assertTrue(fePage.featureDeal.isDisplayed());

					System.out.println("feature deal link is displayed....");
			}
		}
		}
		catch(Exception e) {

			e.printStackTrace();
		}

		driver.close();

	}


	@Test(priority=10,groups={"migrationgroup"})	

	public void supportValidation() throws MalformedURLException
	{

		this.prepareTest();

		try {
			fePage = new FEPage(driver);

			System.out.println("inside test case");

			System.out.println(EnvData.LenovoProdUS);

			driver.get(EnvData.LenovoProdUS+"us/en");

			Thread.sleep(15000);

			Actions action = new Actions(driver);

			action.moveToElement(fePage.supportLink).build().perform();

			Thread.sleep(2000);

			System.out.println("support list size is"+fePage.supportList.size());

			//Support link and css validation

			for(int n=0;n<fePage.supportList.size();n++)
			{
				action.moveToElement(fePage.supportList.get(n)).build().perform();

				Thread.sleep(2000);

				String expColorvalue="#2e9dc8";

				String supportLinkcolor = fePage.supportList.get(n).findElement(By.className("link_text")).getCssValue("color");

				String supportLinkcolorValue = NA26729Test.cssValue(supportLinkcolor);
				
				Thread.sleep(3000);

				Assert.assertTrue(supportLinkcolorValue.contains(expColorvalue));

				System.out.println("SupportLink  color validation passed... "+fePage.supportList.get(n).getText());
			}

			Thread.sleep(2000);
		}


		catch(Exception e) {

			e.printStackTrace();
		}
	}

	@Test(priority=11,groups={"migrationgroup"})	

	public void solutionValidation() throws MalformedURLException, InterruptedException
	{

		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case");

		System.out.println(EnvData.LenovoProdUS);

		driver.get(EnvData.LenovoProdUS+"us/en");			
		Thread.sleep(15000);

		Actions action = new Actions(driver);

		action.moveToElement(fePage.solutionLink).build().perform();  

		Thread.sleep(2000);

		System.out.println("solution list size is"+fePage.solutionList.size());

		// Solution Link and css validation

		for(int p=0;p<fePage.solutionList.size();p++)
		{
			action.moveToElement(fePage.solutionList.get(p)).build().perform();

			Thread.sleep(2000);

			String expColorvalue="#2e9dc8";

			String solutionLinkcolor = fePage.solutionList.get(p).findElement(By.className("link_text")).getCssValue("color");

			String solutionLinkcolorValue = NA26729Test.cssValue(solutionLinkcolor);

			Assert.assertTrue(solutionLinkcolorValue.contains(expColorvalue));

			System.out.println("SolutionLink  color validation passed... "+fePage.solutionList.get(p).getText());
		}	

		driver.close();
	}


	//validating Master header width in  home page	and other pages

	@Test(priority=12,groups={"migrationgroup"})

	public void mastheadWidthPagesValidation() throws InterruptedException, MalformedURLException
	{

		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case");


		System.out.println(EnvData.LenovoProdUS);

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(12000);

		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");

		Thread.sleep(3000);

		String expMatheadWidth = "75px";

		String  actMastheadWidth = fePage.mastheadWidth.getCssValue("height");

		System.out.println("Mast head width in Home page is "+actMastheadWidth);

		// Assert.assertEquals(expMatheadWidth, actMastheadWidth ,"failed:Masthead width validation Homepage");  

		Assert.assertTrue(expMatheadWidth.equals(actMastheadWidth));

		Thread.sleep(2000);

		driver.navigate().to("https://www3.lenovo.com/us/en/think?menu-id=Thinkpad_Laptops");

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		String  actMastheadWidth2 = fePage.mastheadWidth.getCssValue("height");

		Assert.assertTrue(expMatheadWidth.equals(actMastheadWidth2));

		System.out.println("Passed:Masthead width validation series page");

		driver.navigate().to("https://pre-c-hybris.lenovo.com/us/en/laptops/thinkpad/thinkpad-x/ThinkPad-X1-Carbon-6th-Gen/p/20KHCTO1WWENUS1/customize?");

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		String  actMastheadWidth3 = fePage.mastheadWidth.getCssValue("height");

		Assert.assertTrue(expMatheadWidth.equals(actMastheadWidth3));

		System.out.println("passed:Masthead width validation builder page");

	}	


}


