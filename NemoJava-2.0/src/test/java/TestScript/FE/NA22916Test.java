package TestScript.FE;


import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.FEPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;

/*
 * @owner Ridun
 * MastHead Navigation in Homepage in mobile
 */

public class NA22916Test extends SuperTestClass { 
	
	public FEPage fePage;
	
	public  NA22916Test(String store) {
		this.Store = store;
		this.testName = "NA22916Test";

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

			System.out.println(EnvData.LenovoProdUS+"us/en");

			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Dimension dimension = new Dimension(360,840);
			
			driver.manage().window().setSize(dimension);
			
			Thread.sleep(12000);
			
			WebDriverWait wait = new WebDriverWait(driver,300);

			wait.until(ExpectedConditions.visibilityOf(fePage.mainmenuIcon));
			
			fePage.mainmenuIcon.click();
			
			System.out.println("clicked main menu icon.....");

			wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

			Actions action = new Actions(driver);

			action.moveToElement(fePage.ProductsLink).build().perform();

			Thread.sleep(5000);

			System.out.println("#####Clicked on Products####");
			
			Thread.sleep(3000);
	        
	        fePage.productsList.get(0).click();
	        
	        Thread.sleep(3000);
	        
	        System.out.println("clicked on laptop......");
	        
            int LaptopMainLinksSize = fePage.laptopLinks.size();
	        
	        System.out.println("laptop Main links size is ...."+LaptopMainLinksSize);
	        
	        for(int i=0;i<LaptopMainLinksSize;i++)
	        {
	        
	        	System.out.println("Laptop main link :"+fePage.laptopLinks.get(i).getText());
        	
	        	
	        	
	        	if(	fePage.laptopLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("brandlinks"))
	 	       {
	        		fePage.laptopLinks.get(i).click();
	            	
		        	Thread.sleep(3000);
	        	
		        	System.out.println("clicked on laptop Main link.....");
	 	        		int x=i+1;
	 	        		
	 	        		List<WebElement> laptopSublinks = driver.findElements(By.xpath("(//ul[@class='megaMenu_subSection_list'])[1]/li["+x+"]/ul[contains(@class,'menu')]/li"));
	 		        	
	 		        	int laptopSublinksSize = laptopSublinks.size();
	 		        	
	 		        	System.out.println("laptop sublink size is"+laptopSublinksSize);
	 		        	fePage.laptopLinks.get(i).click();
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

}
@Test(priority=2,groups={"migrationgroup"})
	
	public void desktop() throws InterruptedException, MalformedURLException {
		
		    this.prepareTest();
		
			fePage = new FEPage(driver);

			System.out.println("inside test case");

			System.out.println(EnvData.LenovoProdUS+"us/en");

			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Dimension dimension = new Dimension(360,840);
			
			driver.manage().window().setSize(dimension);
			
			Thread.sleep(12000);
			
			WebDriverWait wait = new WebDriverWait(driver,300);

			wait.until(ExpectedConditions.visibilityOf(fePage.mainmenuIcon));
			
			fePage.mainmenuIcon.click();
			
			System.out.println("clicked main menu icon.....");

			wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

			Actions action = new Actions(driver);

			action.moveToElement(fePage.ProductsLink).build().perform();

			Thread.sleep(5000);

			System.out.println("#####Clicked on Products####");
			
			Thread.sleep(3000);
	        
			fePage.productsList.get(1).click();
	        
	        Thread.sleep(3000);
	        
	        System.out.println("clicked on Desktop......");
	        
	        int desktopMainLinksSize = fePage.desktopLinks.size();
	        
	        System.out.println("desktop MainLinks Size is ...."+desktopMainLinksSize);
	        
	        for(int i=0;i<desktopMainLinksSize;i++)
	        {
	        
	        	System.out.println("Laptop main link :"+fePage.desktopLinks.get(i).getText());
	        	
	        	if(	fePage.desktopLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("brandlinks"))
		 	       {
		        		fePage.desktopLinks.get(i).click();
		            	
			        	Thread.sleep(3000);
		        	
			        	System.out.println("clicked on desktop Main link.....");
		 	        		int x=i+1;
		 	        		
		 	        		List<WebElement> desktopSublinks = driver.findElements(By.xpath("(//ul[@class='megaMenu_subSection_list'])[2]/li["+x+"]/ul[contains(@class,'menu')]/li"));
		 		        	
		 		        	int desktopSublinksSize = desktopSublinks.size();
		 		        	
		 		        	System.out.println("desktop sublink size is"+desktopSublinksSize);
		 		        	fePage.desktopLinks.get(i).click();
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
			
	        }	

@Test(priority=3,groups={"migrationgroup"})

public void workstations() throws InterruptedException, MalformedURLException {
	
	    this.prepareTest();
	
		fePage = new FEPage(driver);

		System.out.println("inside test case");

		System.out.println(EnvData.LenovoProdUS+"us/en");

		driver.get(EnvData.LenovoProdUS+"us/en");
		
		Dimension dimension = new Dimension(360,840);
		
		driver.manage().window().setSize(dimension);
		
		Thread.sleep(12000);
		
		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.mainmenuIcon));
		
		fePage.mainmenuIcon.click();
		
		System.out.println("clicked main menu icon.....");

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");
		
		Thread.sleep(3000);
        
		fePage.productsList.get(2).click();
        
        Thread.sleep(3000);
        
        System.out.println("clicked on workstations......");
        
        int workstationMainLinksSize = fePage.workstationLinks.size();
        
        System.out.println("workstations MainLinks Size is ...."+workstationMainLinksSize);
        
        for(int i=0;i<workstationMainLinksSize;i++)
        {
        	System.out.println("workstation MainLinks :"+fePage.workstationLinks.get(i).getText());
        	
        	 if(fePage.workstationLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("brandlinks"))
		       {
        		 fePage.workstationLinks.get(i).click();
	            	
		        	Thread.sleep(3000);
	        	
		        	System.out.println("clicked on workstation Main link.....");
	 	        		int x=i+1;
	 	        		List<WebElement> workstationSublinks = driver.findElements(By.xpath("(//ul[@class='megaMenu_subSection_list'])[3]/li["+x+"]/ul[contains(@class,'menu')]/li"));
	 		        	
	 		        	int workstationSublinksSize = workstationSublinks.size();
	 		        	
	 		        	System.out.println("workstation sublink size is"+workstationSublinksSize);
	 		        	fePage.workstationLinks.get(i).click();
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
		       }
        
@Test(priority=4,groups={"migrationgroup"})

public void phoneTablets() throws InterruptedException, MalformedURLException {
	
	    this.prepareTest();
	
		fePage = new FEPage(driver);

		System.out.println("inside test case");

		System.out.println(EnvData.LenovoProdUS+"us/en");

		driver.get(EnvData.LenovoProdUS+"us/en");
		
		Dimension dimension = new Dimension(360,840);
		
		driver.manage().window().setSize(dimension);
		
		Thread.sleep(12000);
		
		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.mainmenuIcon));
		
		fePage.mainmenuIcon.click();
		
		System.out.println("clicked main menu icon.....");

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");
		
		Thread.sleep(3000);
        
		fePage.productsList.get(3).click();
        
        Thread.sleep(3000);
        
        System.out.println("clicked on phoneTablets......");
        
        int phoneTabletsMainLinksSize = fePage.phoneTabletsLinks.size();
        
        System.out.println("phoneTablets MainLinks Size is ...."+phoneTabletsMainLinksSize);
        
        for(int i=0;i<phoneTabletsMainLinksSize;i++)
		
        {
        	System.out.println("phoneTablets MainLinks :"+fePage.phoneTabletsLinks.get(i).getText());
        	
        	if(	fePage.phoneTabletsLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("brandlinks"))
		       {
        		 fePage.phoneTabletsLinks.get(i).click();
	            	
		        	Thread.sleep(3000);
	        	
		        	System.out.println("clicked on phoneTablets Main link.....");
		        	
	 	        		int x=i+1;
	 	        		
	 	        		List<WebElement> phoneTabletsSublinks = driver.findElements(By.xpath("(//ul[@class='megaMenu_subSection_list'])[4]/li["+x+"]/ul[contains(@class,'menu')]/li"));
	 		        	
	 	        		int phoneTabletsSublinksSize = phoneTabletsSublinks.size();
	 		        	
	 		        	System.out.println("phoneTablet sublink size is"+phoneTabletsSublinksSize);
	 		        	
	 		        	fePage.phoneTabletsLinks.get(i).click();
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
}

@Test(priority=5,groups={"migrationgroup"})

public void gaming() throws InterruptedException, MalformedURLException {
	
	    this.prepareTest();
	
		fePage = new FEPage(driver);

		System.out.println("inside test case");

		System.out.println(EnvData.LenovoProdUS+"us/en");

		driver.get(EnvData.LenovoProdUS+"us/en");
		
		Dimension dimension = new Dimension(360,840);
		
		driver.manage().window().setSize(dimension);
		
		Thread.sleep(12000);
		
		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.mainmenuIcon));
		
		fePage.mainmenuIcon.click();
		
		System.out.println("clicked main menu icon.....");

		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

		Actions action = new Actions(driver);

		action.moveToElement(fePage.ProductsLink).build().perform();

		Thread.sleep(5000);

		System.out.println("#####Clicked on Products####");
		
		Thread.sleep(3000);
        
		fePage.productsList.get(4).click();
        
        Thread.sleep(3000);
        
        System.out.println("clicked on Gaming......");
        
        int gamingMainLinksSize = fePage.gamingLinks.size();
        
        System.out.println("gaming MainLinks Size is ...."+gamingMainLinksSize);
        
        for(int i=0;i<gamingMainLinksSize;i++)
		{
        	
        	System.out.println("gaming MainLinks :"+fePage.gamingLinks.get(i).getText());
        	
        	 if(fePage.gamingLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("brandlinks"))
		       {
     		 fePage.gamingLinks.get(i).click();
	            	
		        	Thread.sleep(3000);
	        	
		        	System.out.println("clicked on gaming Main link.....");
		        	
	 	        		int x=i+1;
	 	        		
	 	        		List<WebElement> gamingSublinks = driver.findElements(By.xpath("(//ul[@class='megaMenu_subSection_list'])[5]/li["+x+"]/ul[contains(@class,'menu')]/li"));
	 		        	
	 	        		int gamingSublinksSize = gamingSublinks.size();
	 		        	
	 		        	System.out.println("Gaming sublink size is"+gamingSublinksSize);
	 		        	
	 		        	fePage.gamingLinks.get(i).click();
	 	       }
	 		        	
	 		        	else if(fePage.gamingLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("has-submenu-explore-all-link"))
	 			        {
	 			        	driver.findElement(By.xpath("//div[@class='megaMenu_contentSection']")).isDisplayed();
	 			        	
	 			        	System.out.println("Displayed............");
	 			        }
	 			        
	 			        else if(fePage.gamingLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu-explore-all-link"))
	 			        {
	 			        	System.out.println("No content section present");
	 			        	
	 			        }
		}
        	
		}   	
        @Test(priority=6,groups={"migrationgroup"})

        public void accessoriesAndMonitors() throws InterruptedException, MalformedURLException {
        	
        	    this.prepareTest();
        	
        		fePage = new FEPage(driver);

        		System.out.println("inside test case");

        		System.out.println(EnvData.LenovoProdUS+"us/en");

    			driver.get(EnvData.LenovoProdUS+"us/en");
        		
        		Dimension dimension = new Dimension(360,840);
        		
        		driver.manage().window().setSize(dimension);
        		
        		Thread.sleep(12000);
        		
        		WebDriverWait wait = new WebDriverWait(driver,300);

        		wait.until(ExpectedConditions.visibilityOf(fePage.mainmenuIcon));
        		
        		fePage.mainmenuIcon.click();
        		
        		System.out.println("clicked main menu icon.....");

        		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

        		Actions action = new Actions(driver);

        		action.moveToElement(fePage.ProductsLink).build().perform();

        		Thread.sleep(5000);

        		System.out.println("#####Clicked on Products####");
        		
        		Thread.sleep(3000);
                
        		fePage.productsList.get(5).click();
                
                Thread.sleep(3000);
                
                System.out.println("clicked on accessoriesAndMonitors......");
                
                int accessoriesAndMonitorsSize = fePage.accessoriesAndMonitorsLinks.size();
    	        
    	        System.out.println("Accessories And MonitorsLinks size is ...."+accessoriesAndMonitorsSize);
    	        
    	        for(int i=0;i<accessoriesAndMonitorsSize;i++)
    	        {
    	        	if(fePage.accessoriesAndMonitorsLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu-explore-all-link"))
    		        {
    		        	System.out.println("accessoriesAndMonitors -No content section present");
    		        	
    		        	Thread.sleep(2500);
    		        }
    	        }
}
        
        @Test(priority=7,groups={"migrationgroup"})

        public void serversAndStorage() throws InterruptedException, MalformedURLException {
        	
        	    this.prepareTest();
        	
        		fePage = new FEPage(driver);

        		System.out.println("inside test case");
        		
        		System.out.println(EnvData.LenovoProdUS+"us/en");

    			driver.get(EnvData.LenovoProdUS+"us/en");
        		
        		Dimension dimension = new Dimension(360,840);
        		
        		driver.manage().window().setSize(dimension);
        		
        		Thread.sleep(12000);
        		
        		WebDriverWait wait = new WebDriverWait(driver,300);

        		wait.until(ExpectedConditions.visibilityOf(fePage.mainmenuIcon));
        		
        		fePage.mainmenuIcon.click();
        		
        		System.out.println("clicked main menu icon.....");

        		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

        		Actions action = new Actions(driver);

        		action.moveToElement(fePage.ProductsLink).build().perform();

        		Thread.sleep(5000);

        		System.out.println("#####Clicked on Products####");
        		
        		Thread.sleep(3000);
                
        		fePage.productsList.get(6).click();
                
                Thread.sleep(3000);
                
                System.out.println("clicked on serversAndStorage......");
                
                int serversAndStorageLinksSize = fePage.serversAndStorageLinks.size();
    	        
    	        System.out.println("servers AndStorage Links size is ...."+serversAndStorageLinksSize);
    	        
    	        for(int i=0;i<serversAndStorageLinksSize;i++)
    	        {
    	        	if(fePage.serversAndStorageLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu-explore-all-link"))
    		        {
    		        	System.out.println("serversAndStorageLinks -No content section present");
    		        	
    		        	Thread.sleep(2500);
    		        }
    	        }
        }
        
       @Test(priority=8,groups={"migrationgroup"})

        public void serviceAndWarranty() throws InterruptedException, MalformedURLException {
        	
        	    this.prepareTest();
        	
        		fePage = new FEPage(driver);

        		System.out.println("inside test case");

        		System.out.println(EnvData.LenovoProdUS+"us/en");

    			driver.get(EnvData.LenovoProdUS+"us/en");
        		
        		Dimension dimension = new Dimension(360,840);
        		
        		driver.manage().window().setSize(dimension);
        		
        		Thread.sleep(12000);
        		
        		WebDriverWait wait = new WebDriverWait(driver,300);

        		wait.until(ExpectedConditions.visibilityOf(fePage.mainmenuIcon));
        		
        		fePage.mainmenuIcon.click();
        		
        		System.out.println("clicked main menu icon.....");

        		wait.until(ExpectedConditions.visibilityOf(fePage.ProductsLink));

        		Actions action = new Actions(driver);

        		action.moveToElement(fePage.ProductsLink).build().perform();

        		Thread.sleep(5000);

        		System.out.println("#####Clicked on Products####");
        		
        		Thread.sleep(3000);
                
        		fePage.productsList.get(7).click();
                
                Thread.sleep(3000);
                
                System.out.println("clicked on serviceAndWarranty......");
                
                int serviceAndWarrantyLinksSize = fePage.serviceAndWarrantyLinks.size();
    	        
    	        System.out.println("service And Warranty Links Size is ...."+serviceAndWarrantyLinksSize);
    	        
    	        for(int i=0;i<serviceAndWarrantyLinksSize;i++)
    	        
    	        {
    	        	if(fePage.serviceAndWarrantyLinks.get(i).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu-explore-all-link"))
    		        {
    		        	System.out.println("serviceAndWarrantyLinks -No content section present");
    		        	
    		        	Thread.sleep(2500);
    		        }
    	        }
        }

        
       @Test(priority=9,groups={"migrationgroup"})	
    	public void dealsValidation() throws MalformedURLException, InterruptedException
    	{
    		this.prepareTest();

    		
    			fePage = new FEPage(driver);

    			System.out.println("inside test case");

    			System.out.println(EnvData.LenovoProdUS+"us/en");

    			driver.get(EnvData.LenovoProdUS+"us/en");
    			
    			Dimension dimension = new Dimension(360,840);
        		
        		driver.manage().window().setSize(dimension);

    			Thread.sleep(12000); 
    			
    			WebDriverWait wait = new WebDriverWait(driver,300);

        		wait.until(ExpectedConditions.visibilityOf(fePage.mainmenuIcon));
        		
        		fePage.mainmenuIcon.click();
        		
        		System.out.println("clicked main menu icon.....");
        		
        		Actions action = new Actions(driver);

    			action.moveToElement(fePage.dealsLink).build().perform();

    			Thread.sleep(2000);

    			System.out.println("Dealslist size is"+fePage.dealsList.size());
        
    			for(int i=0;i<fePage.dealsList.size()-1;i++)
        	        
    	        {
    				if(fePage.dealsList.get(i).findElement(By.tagName("a")).getAttribute("class").contains("no-submenu-explore-all-link"))
    		        {
    		        	System.out.println("dealsLink -No content section present");
    		        	
    		        	Thread.sleep(2500);
    		        }
    	        }
        
    		}
        
    	@Test(priority=10,groups={"migrationgroup"})	
     	public void supportValidation() throws MalformedURLException, InterruptedException
     	{
     		this.prepareTest();

     		
     			fePage = new FEPage(driver);

     			System.out.println("inside test case");

     			System.out.println(EnvData.LenovoProdUS+"us/en");

    			driver.get(EnvData.LenovoProdUS+"us/en");
     			
     			Dimension dimension = new Dimension(360,840);
         		
         		driver.manage().window().setSize(dimension);

     			Thread.sleep(12000); 
     			
     			WebDriverWait wait = new WebDriverWait(driver,300);

         		wait.until(ExpectedConditions.visibilityOf(fePage.mainmenuIcon));
         		
         		fePage.mainmenuIcon.click();
         		
         		System.out.println("clicked main menu icon.....");
         		
         		Actions action = new Actions(driver);

         		action.moveToElement(fePage.supportLink).build().perform();

    			Thread.sleep(2000);

    			
    			//List<WebElement> supportListElements = driver.findElements(By.xpath("//ul/li[contains(@class,'support')][1]/div[@class='megaMenu_wrapper']/div/ul/li"));
    			
    			 int supportListElementsSize = fePage.supportList.size();
    			 
    			 System.out.println("Support links size is.."+supportListElementsSize);

    			for(int i=0;i<supportListElementsSize;i++)
    			{
    				Assert.assertTrue(fePage.supportList.get(i).isDisplayed(),"Failed:support link not displayed");
    				
    				System.out.println("support links displayed");
     	        }
     	
     		}
    	 
    	 @Test(priority=11,groups={"migrationgroup"})	
      	public void solutionValidation() throws MalformedURLException, InterruptedException
      	{
      		this.prepareTest();

      		
      			fePage = new FEPage(driver);

      			System.out.println("inside test case");

      			System.out.println(EnvData.LenovoProdUS+"us/en");

    			driver.get(EnvData.LenovoProdUS+"us/en");;
      			
      			Dimension dimension = new Dimension(360,840);
          		
          		driver.manage().window().setSize(dimension);

      			Thread.sleep(12000); 
      			
      			WebDriverWait wait = new WebDriverWait(driver,300);

          		wait.until(ExpectedConditions.visibilityOf(fePage.mainmenuIcon));
          		
          		fePage.mainmenuIcon.click();
          		
          		System.out.println("clicked main menu icon.....");
          		
          		Actions action = new Actions(driver);

          		action.moveToElement(fePage.solutionLink).build().perform();

     			Thread.sleep(2000);

     			System.out.println("solution list size is"+fePage.solutionList.size());
     			
     			//List<WebElement> solutionListElements = driver.findElements(By.xpath("//ul/li[contains(@class,'support')][2]/div[@class='megaMenu_wrapper']/div/ul/li"));
     			
     			 int solutionListElementsSize = fePage.solutionList.size();

     			for(int i=0;i<solutionListElementsSize;i++)
     			{
     				Assert.assertTrue(fePage.solutionList.get(i).isDisplayed());
     				
     				System.out.println("solution link displayed");
    				
      	        }
          
      		}
        
}