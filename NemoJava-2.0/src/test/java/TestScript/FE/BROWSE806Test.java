package TestScript.FE;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import Pages.FEPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;

/*
 * @owner Ridun
 * Global search Mobile
 */


public class BROWSE806Test extends SuperTestClass  {
	
	public FEPage fePage;
	
	public BROWSE806Test(String store) {
		this.Store = store;
		this.testName = "BROWSE806Test";
	}

	@Test(priority=1,groups={"migrationgroup"})
	public void TypeAheadMobile() throws MalformedURLException, InterruptedException
	{
		
		this.prepareTest();
		
		fePage = new FEPage(driver);

		System.out.println("inside test case");
	
		//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
		
		driver.get(EnvData.LenovoProdUS+"us/en");
		
		Dimension dimension = new Dimension(360,840);
		
		driver.manage().window().setSize(dimension);
		
		Thread.sleep(12000);
		
		WebDriverWait wait = new WebDriverWait(driver,300);
		
		//validate search bar is displayed
				wait.until(ExpectedConditions.visibilityOf(fePage.searchbar));
				
				Assert.assertTrue(fePage.searchbar.isDisplayed(), "failed:search bar is not displayed");
				
				System.out.println("Searchbar is displayed......");
				
		//validate searchbar is clickable		
				
		         wait.until(ExpectedConditions.elementToBeClickable(fePage.searchbar));
		         
		         fePage.searchbar.click();
		         
		         Thread.sleep(5000);
		         
		         System.out.println("search bar is clicked...");
		         
		         Thread.sleep(3000);	
		      
			}  
	@Test(priority=2,groups={"migrationgroup"})
	
		public void searchBoxFunctionalitiesMobile() throws MalformedURLException, InterruptedException
		
		{
		this.prepareTest();
		
		fePage = new FEPage(driver);

		System.out.println("inside test case");
	
		driver.get(EnvData.LenovoProdUS+"us/en");
		
		Dimension dimension = new Dimension(360,840);
		
		driver.manage().window().setSize(dimension);
		
		Thread.sleep(12000);
		
		// searchbox functionality -validating suggested and recommended results displayed
        
				fePage.searchbar.click();
				
				System.out.println("clicked on search bar..");
				
				Thread.sleep(1500);
				
				String keyword = "thinkpad";
				
				fePage.searchbar.sendKeys(keyword);
		        
		        Thread.sleep(2500);
		        
		        Assert.assertTrue(fePage.suggestedSearches.isDisplayed(), "Failed:suggestedSearches section not displayed");
		        
		        System.out.println("Passed:suggestedSearches section  displayed");
		        
		        Assert.assertTrue(fePage.recommendedResultSection.isDisplayed(), "Failed:recommended Result Section section not displayed");
		        
		        System.out.println("Passed:recommended Result Section  displayed");
		        
		      //vaidate background color suggested result
		        
		        String sugSearchBgColorAct = fePage.suggestedSearches.getCssValue("background");
		        
		        System.out.println("suggested Search BG color is :"+sugSearchBgColorAct);
		        
		        String sugSearchBgColorExp = "rgb(248, 248, 248)";
		        
		        Assert.assertTrue(sugSearchBgColorAct.contains(sugSearchBgColorExp),"Failed Suggested search background color validation");
		        
		        System.out.println("passed:Suggested search background color validation");
		        
		        Thread.sleep(2000);
        
	}
	
	 @Test(priority=3,groups={"migrationgroup"})

	       public void searchBoxCategoryFunctionalityMobile() throws MalformedURLException, InterruptedException

	       {
		  
		    this.prepareTest();
			
			fePage = new FEPage(driver);

			System.out.println("inside test case searchBoxCategoryFunctionalityMobile ");
		
			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Dimension dimension = new Dimension(360,840);
			
			driver.manage().window().setSize(dimension);
			
			Thread.sleep(12000);
			
	      //validate suggested category click and navigate to correspondent page 
		  
			fePage.searchbar.click();
			
			System.out.println("clicked on search bar..");
			
			Thread.sleep(2500);
			
			String keyword = "thinkpad";
			
			fePage.searchbar.sendKeys(keyword);
	        
	        Thread.sleep(4500);
	       
	       fePage.sugProdlinkMobile.click();
	       
	       System.out.println("clicked on suggested search Products Link");
	       
	       Thread.sleep(4000);
	       
	       List<WebElement> elementsList =  driver.findElements(By.xpath("//div[@id='heading']"));
	       
	       System.out.println(elementsList.size());
	       
	       Thread.sleep(3000);
	     
	     	Assert.assertTrue(elementsList.get(3).isDisplayed(),"failed:Products and result count section not displayed in search results");
	     	
	     	System.out.println("passed:Products and result count section ");
	     	
	     	Thread.sleep(2000);
	     	
	     	Assert.assertTrue(fePage.minimizeList.get(3).isDisplayed(), "Failed:Products minimize section not displayed");
	     	
	     	System.out.println("Products minimize section is displayed");
	     	 
	       System.out.println("Passed:Suggested result products -navigated to correspondent page");
	         
	       }	
	  
	  
	  @Test(priority=4,alwaysRun=true,groups={"migrationgroup"})

		public void recommendedSearchMobile() throws MalformedURLException, InterruptedException

		{
	  
		  	this.prepareTest();
			
			fePage = new FEPage(driver);

			System.out.println("inside test case recommendedSearchMobile ");
			
			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Dimension dimension = new Dimension(360,840);
			
			driver.manage().window().setSize(dimension);
			
			Thread.sleep(12000);
			
			fePage.searchbar.click();

			System.out.println("clicked on search bar..");

			Thread.sleep(1500);

			String keyword = "thinkpad";

			fePage.searchbar.sendKeys(keyword);

			Thread.sleep(2500);

			int recomSize = fePage.recommendedResults.size();

			System.out.println("recommended result size is "+recomSize);

			if(recomSize<=1)
			{
				System.out.println("Recommended result size is 1");
			}
			else{
				System.out.println("recommended result size is greater than 3");
			}
	  
		//valdate recommended results are relevant  
			System.out.println("recommended result text is "+fePage.recommendedResultText.getText().replaceAll(" ", ""));

			String reccomendTextHomepage = fePage.recommendedResultText.getText().replaceAll(" ", "");

			Thread.sleep(2000);

			fePage.recommendedResultText.click();

			System.out.println("clicked on recommended result");

			WebDriverWait wait = new WebDriverWait(driver,200);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='mobileHeader']")));

			System.out.println(fePage.recommendedResultsearchresultTextmobile.getText().replaceAll(" ", ""));

			String reccomendTextSearchpage = fePage.recommendedResultsearchresultTextmobile.getText().replaceAll(" ", "");

			Assert.assertTrue(reccomendTextSearchpage.contains(reccomendTextHomepage), "Failed:Recommended result navigation");
	  
	  
		}
	  
	  //validate DCG products don't have Free shipping and rating in Typehead and search results - CONTENT-290
      
	     @Test(priority=5)

	       public void dcgHideRatingFreeshippingMobile() throws MalformedURLException, InterruptedException

	       {
	    	    this.prepareTest();
				
				fePage = new FEPage(driver);

				System.out.println("inside test case dcg Hide Rating Freeshipping Mobile..");
				
				int urlLength =7;
				
				for(int k=0;k<urlLength;k++)
				{
					if(k==0){
						driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
					}else if (k==1){
						driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/ca/en");
					}else if(k==2){
						driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/gb/en");
					}else if(k==3){
						driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/de/de");
					}else if(k==4){
						driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/jp/ja");
					}else if(k==5){
						driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/au/en");
					}else if(k==6){
						driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/br/pt");
					}
					
				
			
				//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
				
				driver.get(EnvData.LenovoProdUS+"us/en");
				
				Dimension dimension = new Dimension(360,840);
				
				driver.manage().window().setSize(dimension);
				
				Thread.sleep(12000);
				
				fePage.searchbar.click();
				
		   		System.out.println("clicked on search bar..");
				
				Thread.sleep(1500);
				
			String[] keyword = {"data center","data center services","software defined","big data","cloud","hpc","mission critical servers","high density servers",
						"storage","direct attach storage","fibre channel switch","tape storage","san","networking","rack server","dense server","xclarity","thinksystem",
						"thinksystem sn550","thinksystem sd650","converged","flash storage","oem","network management","data center security","red hat","suse","rackswitch",
						"tower server","blades"};
				
				int length = keyword.length;
				
				System.out.println("Length is "+length);
	     
				for(int i=0;i<length;i++){
				
					fePage.searchbar.sendKeys(keyword[i]);
			
					System.out.println("keyword passed is "+keyword[i]);
			
					Thread.sleep(2500);
					
					int recommendedresultPriceSize = fePage.recommendedresultPrice.size();
					System.out.println("recommendedresultPriceSize is"+recommendedresultPriceSize);
				}
	      	
			for(int j=0;j<fePage.recommendedresultPrice.size();j++)
			{
					try {
						if(fePage.recommendedresultPrice.get(j).isDisplayed())
						{
							System.out.println("its' a non dcg product");
						}
					}
						catch(Exception e) {
							System.out.println("there is no price for dcg prodcut");
						}
			}
			
				fePage.seachIcon.click();
				
				System.out.println("clicked search icon....");
				
				Thread.sleep(4000);
				
				 WebDriverWait wait = new WebDriverWait(driver,300);
					
				wait.until(ExpectedConditions.visibilityOf(fePage.listViewButtonMobile));
				
				fePage.listViewButtonMobile.click();
				
			     Thread.sleep(2000);
			    
				System.out.println("clicked on list view...");
	    
			     JavascriptExecutor jse = (JavascriptExecutor) driver; 
			     jse.executeScript("window.scrollBy(0,1550)", "");
			     Thread.sleep(4000);
			     while(fePage.loadmoreButtonMobile.isDisplayed()){
			    	 
			    	 fePage.loadmoreButtonMobile.click(); 
			    	 
			    	 Thread.sleep(6000);
			    	 jse.executeScript("arguments[0].scrollIntoView(true);",fePage.loadmoreButtonMobile);
			    	 Thread.sleep(6000);
			    	 
			     }
			    	 Thread.sleep(4000);
			    	 
			    	 System.out.println("List count of product is"+fePage.searchResults.size());
			    	 
			    	 for(int l=0;l<fePage.searchResults.size();l++)
						{	 
			    	 
			    	try {
						System.out.println("attribute is...." +fePage.searchResults.get(l).getAttribute("data-dcg"));
						 
						 Assert.assertEquals(fePage.searchResults.get(l).getAttribute("data-dcg"),"true","Failed:Freeshipping is visible");
							
						System.out.println("Free shipping is not visible.....");
						
						System.out.println(fePage.searchResults.get(l).findElement(By.xpath("//div[@class='product-card__reviews']")).getSize());
					} catch (Exception e) {
						
						System.out.println("Failed:Review is visible");
						e.printStackTrace();
					}
						}
				}
			    	 
			     }
			
	 
		//Search results - Grid view
		@Test(priority=6,alwaysRun=true,groups={"migrationgroup"})
		//NA-21222
		public void searchGridviewMobile() throws MalformedURLException, InterruptedException
		{
			this.prepareTest();
			
			fePage = new FEPage(driver);

			System.out.println("inside test case searchGridviewMobile ");
		
			//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
			
			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Dimension dimension = new Dimension(400,840);
			
			driver.manage().window().setSize(dimension);
			
			Thread.sleep(12000);
			
			fePage.searchbar.click();

			System.out.println("clicked on search bar..");

			Thread.sleep(2500);

			String keyword = "thinkpad";
			
			fePage.searchbar.clear();

			fePage.searchbar.sendKeys(keyword);

			Thread.sleep(2500);
			
			fePage.seachIcon.click();
				
			Thread.sleep(4000);
			
			fePage.morefiltersMobile.click();
			
			Thread.sleep(4000);
			
			System.out.println("search filter size is:"+fePage.searchFilters.size());
			
			for(int i=0;i<fePage.searchFilters.size();i++)
			{
				try{
					if(fePage.searchFilters.get(i).isDisplayed()){
						System.out.println("Filter displayed"+i);
					}
				}
				catch(Exception e) {
					System.out.println("Filter not displayed");
					}
			}
		
		
		//validate filter option selection
			
			Thread.sleep(2500);
			
				fePage.processorFilter.click();
				
				System.out.println("clicked on processor filter");
				
				Thread.sleep(3000);
				
				fePage.processorFilterOptionMobile.click();
				
				Thread.sleep(4000);
				
				fePage.morefiltersMobile.click();
				
				Thread.sleep(3000);
				
				try {
					if(fePage.processorFacetSub.isDisplayed())
					{
					System.out.println("passed:Filter options validation");
					}
				} catch (Exception e) {
					System.out.println("failed:Filter options validation");
				}
			
		}
		
		@Test(priority=7,alwaysRun=true,groups={"migrationgroup"})

	//validating accessories,news,Support sections displayed and validating color
		public void support_NewsMobile() throws MalformedURLException, InterruptedException
		{
			
			this.prepareTest();
			
			fePage = new FEPage(driver);

			System.out.println("inside test case support_NewsMobile ");
		
			//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
			
			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Dimension dimension = new Dimension(400,840);
			
			driver.manage().window().setSize(dimension);
			
			Thread.sleep(12000);
			
			fePage.searchbar.click();

			System.out.println("clicked on search bar..");

			Thread.sleep(2500);

			String keyword = "yoga";
			
			fePage.searchbar.clear();

			fePage.searchbar.sendKeys(keyword);

			Thread.sleep(2500);
			
			fePage.seachIcon.click();
				
			Thread.sleep(6000);
			
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,250)");
			
			Assert.assertTrue(fePage.accessories.isDisplayed());
			
			System.out.println("accessories displayed..");
			
			Assert.assertTrue(fePage.newsresourceTitle.isDisplayed());
			
			Assert.assertTrue(fePage.supportTitle.isDisplayed());
			
			System.out.println("support displayed..");
			
			String color = "rgba(255, 106, 0, 1)";
			
			Thread.sleep(3500);
			
			Assert.assertEquals(fePage.supportTitle.getCssValue("color"), color);
			Assert.assertEquals(fePage.newsresourceTitle.getCssValue("color"), color);
			Assert.assertEquals(fePage.accessories.getCssValue("color"), color);
			
			System.out.println("All title color validated..");
			
		}
		
		
	@Test(priority=8,alwaysRun=true,groups={"migrationgroup"})
		
		//validate List view thumb nails,price,Title,ratings
		public void listViewElemValidationsMobile() throws MalformedURLException, InterruptedException
		{
			
			this.prepareTest();
			
			fePage = new FEPage(driver);

			System.out.println("inside test case listViewElemValidations ");
		
			//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
			
			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Dimension dimension = new Dimension(400,840);
			
			driver.manage().window().setSize(dimension);
			
			Thread.sleep(12000);
			
			fePage.searchbar.click();

			System.out.println("clicked on search bar..");

			Thread.sleep(2500);

			String keyword = "yoga";
			
			fePage.searchbar.clear();

			fePage.searchbar.sendKeys(keyword);

			Thread.sleep(2500);
			
			fePage.seachIcon.click();
				
			Thread.sleep(6000);
			
			fePage.listViewButtonMobile.click();
			
			System.out.println("clicked on list view..");
			
			Thread.sleep(2000);
			
			try {
				if(fePage.listViewThumbnail.isDisplayed())
					
				{
					
					Assert.assertTrue(fePage.listViewprice.isDisplayed(), "Failed:Price is not displayed");
					
					Assert.assertTrue(fePage.listProductTitleMobile.isDisplayed(), "Failed:Product Title not displayed");
					
					Assert.assertTrue(fePage.listviewRatingMobile.isDisplayed(), "Failed:List view Rating not displayed");
					
					System.out.println("Displayed:price,Title,Rating");
				}
			} catch (Exception e) {
				
				System.out.println("List view thumbnails not displayed..");
			}
			
		}
		
		@Test(priority=9,alwaysRun=true,groups={"migrationgroup"})
		
	// validate load more button in List view
		public void ListviewLoadmoreValidationMobile() throws MalformedURLException, InterruptedException
		{
			
			this.prepareTest();
			
			fePage = new FEPage(driver);

			System.out.println("inside test case ListviewLoadmoreValidationMobile ");
		
			//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
			
			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Dimension dimension = new Dimension(400,840);
			
			driver.manage().window().setSize(dimension);
			
			Thread.sleep(12000);
			
			fePage.searchbar.click();

			System.out.println("clicked on search bar..");

			Thread.sleep(2500);

			String keyword = "yoga";
			
			fePage.searchbar.clear();

			fePage.searchbar.sendKeys(keyword);

			Thread.sleep(2500);
			
			fePage.seachIcon.click();
				
			Thread.sleep(6000);
			
			fePage.listViewButtonMobile.click();
			
			System.out.println("clicked on list view..");
			
			Thread.sleep(2000);
			
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			
			jse.executeScript("arguments[0].scrollIntoView(true);",fePage.listviewLoadmoreButton);
			Thread.sleep(2000);
			
			try{
				while(fePage.listviewLoadmoreButton.isDisplayed()){
					
					fePage.listviewLoadmoreButton.click(); 
					Thread.sleep(2000);
					jse.executeScript("arguments[0].scrollIntoView(true);",fePage.listviewLoadmoreButton);
					Thread.sleep(6000);

				}	
			}
				catch(Exception e){
					System.out.println("inside catch-Load more button is not there");
				}	
			
			jse.executeScript("arguments[0].scrollIntoView(true);",fePage.listviewLoadmoreButtonAccessoriesMobile);
			Thread.sleep(2000);
			
			try{
				while(fePage.listviewLoadmoreButtonAccessoriesMobile.isDisplayed()){
					
					fePage.listviewLoadmoreButtonAccessoriesMobile.click(); 
					Thread.sleep(2000);
					jse.executeScript("arguments[0].scrollIntoView(true);",fePage.listviewLoadmoreButtonAccessoriesMobile);
					Thread.sleep(6000);

				}	
			}
				catch(Exception e){
					System.out.println("inside catch-Load more button-Accessories is not there");
				}	
			
			System.out.println("Passed:Load more button validation");
		}
		
		
		@Test(priority=10,alwaysRun=true,groups={"migrationgroup"})
		
		public void noResultsMobile() throws MalformedURLException, InterruptedException
		{
			
			this.prepareTest();
			
			fePage = new FEPage(driver);

			System.out.println("inside test case noResultsMobile ");
		
			//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
			
			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Dimension dimension = new Dimension(400,840);
			
			driver.manage().window().setSize(dimension);
			
			Thread.sleep(12000);
			
			fePage.searchbar.click();

			System.out.println("clicked on search bar..");
			
			Thread.sleep(2500);
			//validate search field highlighted as yellow 
			String keyword = "fdg";
			
			fePage.searchbar.clear();

			fePage.searchbar.sendKeys(keyword);

			Thread.sleep(8500);
			
			fePage.seachIcon.click();
			
			Thread.sleep(3500);
			
			System.out.println("clicked search icon");
			
			String bgColor = "rgba(255, 253, 195, 1)";
			
			System.out.println("exp bg color is: "+fePage.searchBar.getCssValue("background-color"));
			
			Assert.assertEquals(fePage.searchBar.getCssValue("background-color"), bgColor,"Failed:bg color is not yellow");
			
			System.out.println("passed:bg color validation");
			
			Thread.sleep(3500);
			
			//checking No result text is present
			
			Assert.assertTrue(fePage.noResulttext.isDisplayed(), "failed:No result text is not visible");
			
			System.out.println("passed:No result text found is present validation");
			
			//checking We're sorry! Title
			Assert.assertTrue(fePage.sorryText.isDisplayed(), "failed:sorry Text is not visible");
			
			//Check suggestion title
			
			Assert.assertTrue(fePage.suggestionTitle.isDisplayed(), "failed:suggestion title is not visible");
			
		}
		
		@Test(priority=11,alwaysRun=true,groups={"migrationgroup"})
		//check recent search title,validate prod-price,title and rating
		
		public void recentSearchMobile() throws MalformedURLException, InterruptedException
		{
			
			this.prepareTest();
			
			fePage = new FEPage(driver);

			System.out.println("inside test case recentSearchMobile ");
		
			//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
			
			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Dimension dimension = new Dimension(400,840);
			
			driver.manage().window().setSize(dimension);
			
			Thread.sleep(12000);
			
			fePage.searchbar.click();

			System.out.println("clicked on search bar..");
			
			Thread.sleep(2500);
			
			fePage.searchbar.clear();
			
			Thread.sleep(2000);
		
			fePage.searchbar.sendKeys("yoga");
			
			fePage.seachIcon.click();
		
			Thread.sleep(4000);
		
			fePage.searchbar.clear();
		
			fePage.searchbar.sendKeys("fdg");
			
			fePage.seachIcon.click();
		
			Thread.sleep(4000);
		
		try {
			if(fePage.recentsearchTitle.isDisplayed())
					{
				Assert.assertTrue(fePage.listViewprice.isDisplayed(), "Failed:Price is not displayed");
				
				Assert.assertTrue(fePage.recentsearchProdTitle.isDisplayed(), "Failed:Product Title not displayed");
				
				Assert.assertTrue(fePage.recentsearchRating.isDisplayed(), "Failed:Recent search Rating not displayed");
					}
			
			System.out.println("passed:recent search validation");
		} catch (Exception e) {
			
			System.out.println("inside catch..Failed:Recent search is not visible");
		}
	
	
	}

		
    @AfterMethod
     public void browserClose() throws InterruptedException
     {
     	Thread.sleep(3000);
     	
     	driver.quit();
     
  
     }		
		
}