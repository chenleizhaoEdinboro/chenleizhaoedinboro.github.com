
package TestScript.FE;



import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import Pages.FEPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;

/*
 * @owner Ridun
 * Global search Desktop
 */

public class NA21222Test extends SuperTestClass {

	public FEPage fePage;
	String fullprice;
	Double tempPrice;
	
	public ArrayList<Double> priceList = new ArrayList<Double>();
	public ArrayList<Double> priceList1 = new ArrayList<Double>();

	public NA21222Test(String store) {
		this.Store = store;
		this.testName = "NA21222Test";
	}
	
	
		
	//-------Type Ahead and suggested results

	@Test(priority=1,alwaysRun=true,groups={"migrationgroup"})
	public void TypeAhead() throws MalformedURLException, InterruptedException
	{

		try {
			this.prepareTest();

			fePage = new FEPage(driver);

			System.out.println("inside test case");

			//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");

			driver.get(EnvData.LenovoProdUS+"us/en");

			Thread.sleep(15000);

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

		//validate remove icon displayed and functionality

			fePage.searchbar.sendKeys("thinkpad");

			Thread.sleep(2500);

			Assert.assertTrue(fePage.searchclearIcon.isDisplayed(), "Failed:Search clear icon not present");

			System.out.println("search clear Icon is displayed");

			fePage.searchclearIcon.click();
			
			System.out.println("clicked on search clear icon.."); 

			Thread.sleep(3000);

			String currentUrl = driver.getCurrentUrl();

			System.out.println("cureentURL is "+currentUrl);

			//String URL = "https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en";

			String URL = "https://www3.lenovo.com/us/en";
			
			Thread.sleep(3000);

			Assert.assertEquals(URL, currentUrl);

			System.out.println("Passed:search clear functionality");
			
			Thread.sleep(3000);

			boolean displayed = driver.findElement(By.xpath("//input[@placeholder='SEARCH']")).isDisplayed();

			System.out.println(displayed);

			System.out.println("Entered text is cleared and default text search is displayed");
		} catch (Exception e) {
			e.printStackTrace();
		}


	}  
	
	@Test(priority=2,alwaysRun=true,groups={"migrationgroup"})

	public void searchIconFunctionalities() throws MalformedURLException, InterruptedException

	{

		try {
			this.prepareTest();

			fePage = new FEPage(driver);

			System.out.println("inside test case");

			//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");

			driver.get(EnvData.LenovoProdUS+"us/en");

			Thread.sleep(15000);

			WebDriverWait wait = new WebDriverWait(driver,200);

		//validate search icon functionality -redirecting to the correct results

			fePage.searchbar.click();

			Thread.sleep(1500);

			String keyword = "thinkpad";

			fePage.searchbar.sendKeys(keyword);

			Thread.sleep(2500);

			fePage.seachIcon.click();

			System.out.println("clicked on search icon");

			wait.until(ExpectedConditions.elementToBeClickable(fePage.searchbar));

			String SearchResultTitle = fePage.seachresultTitle.getText();

			System.out.println(SearchResultTitle);

			Assert.assertTrue(SearchResultTitle.contains(keyword),"Failed:search results validation");

			System.out.println("Passed:search results validation");
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}


	@Test(priority=3,alwaysRun=true,groups={"migrationgroup"})

	public void searchBoxFunctionalities() throws MalformedURLException, InterruptedException

	{
		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case");

		//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(10000);

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

	 @Test(priority=4,alwaysRun=true,groups={"migrationgroup"})

	public void searchBoxCategoryFunctionality() throws MalformedURLException, InterruptedException

	{
	//validate suggested category click and navigate to correspondent page 

		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case");

		//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(10000);	

		fePage.searchbar.click();

		System.out.println("clicked on search bar..");

		Thread.sleep(1500);

		String keyword = "thinkpad";

		fePage.searchbar.sendKeys(keyword);

		Thread.sleep(2500);

		String expProductText = fePage.suggestedProductsText.getText().replaceAll(" ", "");

		System.out.println("Product link text in home page is :"+expProductText);

		fePage.suggestedProductsLink.click();

		System.out.println("clicked on suggested search Products Link");

		Thread.sleep(3000);

		List<WebElement> elementsList =  driver.findElements(By.xpath("//div[@id='heading']"));

		System.out.println(elementsList.size());

		Thread.sleep(4000);

		Assert.assertTrue(elementsList.get(2).isDisplayed(),"failed:Products and result count section not displayed in search results");

		Thread.sleep(4000);

		Assert.assertTrue(fePage.minimizeList.get(2).isDisplayed(), "Failed:Products minimize section not displayed");

		System.out.println("Products minimize section is displayed");

		System.out.println("Passed:Suggested result products -navigated to correspondent page");

	//validating explore and minimize

		fePage.minimizeList.get(2).click();

		System.out.println("clicked on minimize product results..");

		Thread.sleep(4000);

		Assert.assertTrue(fePage.exploreList.isDisplayed(), "Failed:Products explore section not displayed");

		System.out.println("passed:Products explore section is displayed");
	}	

	 @Test(priority=5,alwaysRun=true,groups={"migrationgroup"})
	public void recommendedSearch() throws MalformedURLException, InterruptedException

	{

		//validating recommended search result size in homepage  

		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside test case");

		//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");

		driver.get(EnvData.LenovoProdUS+"us/en");

		Thread.sleep(10000);	

		fePage.searchbar.click();

		System.out.println("clicked on search bar..");

		Thread.sleep(1500);

		String keyword = "thinkpad";

		fePage.searchbar.sendKeys(keyword);

		Thread.sleep(2500);

		int recomSize = fePage.recommendedResults.size();

		System.out.println("recommended result size is "+recomSize);

		if(recomSize<=3)
		{
			System.out.println("Recommended result size is less than 3");
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

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='desktopHeader']")));

		System.out.println(fePage.recommendedResultsearchresultText.getText().replaceAll(" ", ""));

		String reccomendTextSearchpage = fePage.recommendedResultsearchresultText.getText().replaceAll(" ", "");

		Assert.assertTrue(reccomendTextSearchpage.contains(reccomendTextHomepage), "Failed:Recommended result navigation");

	}   

	@Test(priority=6)

//validate DCG products don't have Free shipping and rating in Typehead and search results - CONTENT-290
	public void dcgHideRatingFreeshipping() throws MalformedURLException, InterruptedException

	{

		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside DCG hide rating test case");
		
		int urlLength =7;
		
		for(int n=0;n<urlLength;n++)
		{
			if(n==0){
				driver.get(EnvData.LenovoProdUS+"us/en");
			}else if (n==1){
				driver.get(EnvData.LenovoProdUS+"ca/en");
			}else if(n==2){
				driver.get(EnvData.LenovoProdUS+"gb/en");
			}else if(n==3){
				driver.get(EnvData.LenovoProdUS+"de/de");
			}else if(n==4){
				driver.get(EnvData.LenovoProdUS+"jp/ja");;
			}else if(n==5){
				driver.get(EnvData.LenovoProdUS+"au/en");
			}else if(n==6){
				driver.get(EnvData.LenovoProdUS+"br/pt");
			}

	
		Thread.sleep(15000);	

		fePage.searchbar.click();

		System.out.println("clicked on search bar..");

		Thread.sleep(2500);


			String[] keyword = {"data center","data center services","software defined","big data","cloud","hpc","mission critical servers","high density servers",
					     "storage","direct attach storage","fibre channel switch","tape storage","san","networking","rack server","dense server","xclarity","thinksystem",
						"thinksystem sn550","thinksystem sd650","converged","flash storage","oem","network management","data center security","red hat","suse","rackswitch",
					    "tower server","blades"};

		int length = keyword.length;

		System.out.println("Length is "+length);

		for(int i=0;i<length;i++){
			
			fePage.searchbar.clear();

			fePage.searchbar.sendKeys(keyword[i]);

			System.out.println("keyword passed is "+keyword[i]);

			Thread.sleep(2500);

			System.out.println("recommendedresultSection size is "+fePage.recommendedresultSection.size());
     // validating price is visible or not in recommended results
			try{

				for(int m=0;m<fePage.recommendedresultSection.size();m++){
					try{
						if(fePage.recommendedresultPrice.get(m).isDisplayed()){
							System.out.println("its' a non dcg product");

						}

					}
					catch(Exception e){

						fePage.recommendedResultsRating.get(m).isDisplayed();

					}
				}
			}
			catch(Exception e) {
				System.out.println("there is no rating for dcg prodcut");
			}


			fePage.seachIcon.click();

			Thread.sleep(6000);
			
			System.out.println("clicken on search icon..");

		//validate the price,rating and free shipping presence in search results
			
			fePage.exploreList.click();
			
			System.out.println("clicked on explore list button..");

			Thread.sleep(2000);

			JavascriptExecutor jse = (JavascriptExecutor) driver;

			Thread.sleep(4000);
			try{
				while(fePage.LoadmoreButton.isDisplayed()){
					jse.executeScript("arguments[0].scrollIntoView(true);",fePage.LoadmoreButton);
					fePage.LoadmoreButton.click(); 
					Thread.sleep(6000);

				}	
			}
				catch(Exception e){
					System.out.println("inside catch");
				}

			Thread.sleep(6000);
			System.out.println("List count of product is"+fePage.searchResults.size());


			try{

			for(int k=0;k<fePage.searchResults.size();k++)
			{
				System.out.println("attribute is...." +fePage.searchResults.get(k).getAttribute("data-dcg"));
				String dcg=fePage.searchResults.get(k).getAttribute("data-dcg");
				try {					
					if(dcg.equalsIgnoreCase("true"))
					{
						System.out.println("inside if");
						
						 Dimension size4 = fePage.searchResults.get(k).findElement(By.xpath("//div[@class='product-card__reviews']")).getSize();
						
						System.out.println("size of review is ####"+size4);
						
						if(size4.equals("(0, 20)")){
							System.out.println("Review is not displayed");
						}else{
						
							System.out.println("Review is displayed");
						}
							
					System.out.println("FreeShipping: "+fePage.searchResults.get(k).findElement(By.xpath("//p[@class='product-card__label']")).isDisplayed());
					}
					
				} catch (Exception e) {

					System.out.println("Failed:Review is visible");
					e.printStackTrace();
				}

			}

		}
			catch (Exception e) {

			System.out.println("Failed:Review is visible");
			e.printStackTrace();

		}
		}
	}
	}
	
	//Search results - Grid view
	@Test(priority=7,alwaysRun=true,groups={"migrationgroup"})
	//NA-21222
	public void searchGridview() throws MalformedURLException, InterruptedException
	{
		
		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside -searchGridview- test case");

		//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
		
		driver.get(EnvData.LenovoProdUS+"us/en");
		
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
		
		System.out.println("search filter size is:"+fePage.searchFilters.size());
		
		for(int i=0;i<fePage.searchFilters.size()-1;i++)
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
		
	//validate filter option selection and remove filter
		
		Thread.sleep(2500);
		
			fePage.processorFilter.click();
			
			System.out.println("clicked on processor filter");
			
			Thread.sleep(3000);
			
			String processorFilterOptionTextselected = fePage.processorFilterOptionText.getText().replaceAll(" ", "");
			
			System.out.println("processorFilterOptionText is: "+processorFilterOptionTextselected);
			
			fePage.processorFilterOption.click();
			
			Thread.sleep(3000);
		
			String selectedFilterTextResult = fePage.selectedFilter.getText().replaceAll(" ", "");
			
			System.out.println(selectedFilterTextResult);
			
			Assert.assertTrue(processorFilterOptionTextselected.contains(selectedFilterTextResult),"failed:Results not according to the selected filter");
			
			System.out.println("Passed:Filter options validation");
			
			fePage.clearAll.click();
			
			Thread.sleep(3000);
			
			try{
				if(fePage.clearAll.isDisplayed()){
			
					System.out.println("failed:clear all button functionality");

				}
				else{
					System.out.println("passed:clear all button functionality");
				}

			}
			catch(Exception e){

				System.out.println("failed:");
				
			}
	}
	//-----validate sorting(low,High)	
	@Test(priority=8,alwaysRun=true,groups={"migrationgroup"})
	
	public void sort() throws MalformedURLException, InterruptedException
	{
		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside -searchGridview- test case");

		driver.get(EnvData.LenovoProdUS+"us/en");
		
		Thread.sleep(3000);
		
		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.searchbar));
		
		fePage.searchbar.click();

		System.out.println("clicked on search bar..");

		Thread.sleep(2500);

		String keyword = "yoga";
		
		fePage.searchbar.clear();

		fePage.searchbar.sendKeys(keyword);

		Thread.sleep(8500);
		
		fePage.seachIcon.click();
		
		System.out.println("clicked search icon");	
		
		Thread.sleep(15000);
		
		fePage.exploreList.click();
		
		System.out.println("clicked on explore list button..");
		
		Thread.sleep(3000);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		
		jse.executeScript("window.scrollBy(0,250)");

		Thread.sleep(5000);
		try{
			while(fePage.LoadmoreButton.isDisplayed()){
				jse.executeScript("arguments[0].scrollIntoView(true);",fePage.LoadmoreButton);
				fePage.LoadmoreButton.click(); 
				Thread.sleep(6000);

			}	
		}
			catch(Exception e){
				System.out.println("inside catch");
			}

		Thread.sleep(6000);
		
		System.out.println("Price size is "+fePage.productsPrice.size());
		
	//--validate Low and High filter in search results by comparing the price	
		
		for (int i = 0; i<fePage.productsPrice.size();i++)
		{
		
		String fullprice = fePage.productsPrice.get(i).getText().replace("$", "").replaceAll(",", "");
		
		Double tempPrice=Double.parseDouble(fullprice);
		
		priceList.add(tempPrice);
		priceList1.add(tempPrice);
		
		Collections.sort(priceList);
		
		Collections.sort(priceList1, Collections.reverseOrder());
		
		}
		
		System.out.println(priceList);
		System.out.println("reverse sorted list");
		
		System.out.println( priceList1);
		
		Thread.sleep(5000);
		
		fePage.searchbar.clear();

		fePage.searchbar.sendKeys(keyword);

		Thread.sleep(8500);
		
		fePage.seachIcon.click();
		
		Thread.sleep(5000);
		
		fePage.Sort.click();
		
		System.out.println("clicked on sortby drop down..");
		
		Thread.sleep(4000);
		
		Select sortby = new Select(fePage.SortbyList);
		
		sortby.selectByIndex(1);
		
		Thread.sleep(4000);
		
		fePage.exploreList.click();
		
		System.out.println("clicked on explore list button..");
		
		Thread.sleep(3000);
		
		jse.executeScript("window.scrollBy(0,250)");

		Thread.sleep(5000);
		try{
			while(fePage.LoadmoreButton.isDisplayed()){
				jse.executeScript("arguments[0].scrollIntoView(true);",fePage.LoadmoreButton);
				fePage.LoadmoreButton.click(); 
				Thread.sleep(6000);

			}	
		}
			catch(Exception e){
				System.out.println("inside catch");
			}

		Thread.sleep(6000);
		
		System.out.println("Price size is "+fePage.productsPrice.size());
		
		ArrayList<Double> priceListLow = new ArrayList<Double>();
		for (int j = 0; j<fePage.productsPrice.size();j++)
		{
			String fullpriceLow = fePage.productsPrice.get(j).getText().replace("$", "").replaceAll(",", "");
			Double tempPrice1=Double.parseDouble(fullpriceLow);
		
			priceListLow.add(tempPrice1);
		}
	
		Iterator<Double> itr3=priceListLow.iterator();  
		while(itr3.hasNext()){  
			System.out.println(itr3.next()); 
			
				}
		
		
		
		fePage.seachIcon.click();
		
		Thread.sleep(5000);
		
		fePage.Sort.click();
		
		System.out.println("clicked on sortby drop down..");
		
		Thread.sleep(4000);
		
		Select sortby1 = new Select(fePage.SortbyList);
		
		sortby1.selectByIndex(2);
		
		Thread.sleep(4000);
		
		fePage.exploreList.click();
		
		System.out.println("clicked on explore list button..");
		
		Thread.sleep(3000);
		
		jse.executeScript("window.scrollBy(0,250)");

		Thread.sleep(5000);
		try{
			while(fePage.LoadmoreButton.isDisplayed()){
				jse.executeScript("arguments[0].scrollIntoView(true);",fePage.LoadmoreButton);
				fePage.LoadmoreButton.click(); 
				Thread.sleep(6000);

			}	
		}
			catch(Exception e){
				System.out.println("inside catch");
			}

		Thread.sleep(6000);
		
		System.out.println("Price size is "+fePage.productsPrice.size());
		
		ArrayList<Double> priceListHigh = new ArrayList<Double>();
		for (int j = 0; j<fePage.productsPrice.size();j++)
		{
			String fullpriceHigh = fePage.productsPrice.get(j).getText().replace("$", "").replaceAll(",", "");
			Double tempPrice2=Double.parseDouble(fullpriceHigh);
		
			priceListHigh.add(tempPrice2);
		}
	
		Iterator<Double> itr4=priceListLow.iterator();  
		while(itr4.hasNext()){  
			System.out.println(itr4.next()); 
			
				}
		
		
		Assert.assertTrue(priceList.equals(priceListLow),"failed:Low filter");
		
		System.out.println("passed low filter validation");
		
		Assert.assertTrue(priceList1.equals(priceListHigh),"failed:Low filter");
		
		System.out.println("passed High filter validation");
			
		
	}
	
	@Test(priority=9,alwaysRun=true,groups={"migrationgroup"})

	//validating accessories,news,Support sections displayed and validating color
	public void support_News() throws MalformedURLException, InterruptedException
	{
		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside Search test case -support_News");
		
		driver.get(EnvData.LenovoProdUS+"us/en");
		
		Thread.sleep(5000);
		
		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.searchbar));
		
		fePage.searchbar.click();

		System.out.println("clicked on search bar..");

		Thread.sleep(2500);

		String keyword = "Yoga";
		
		fePage.searchbar.clear();

		fePage.searchbar.sendKeys(keyword);

		Thread.sleep(8500);
		
		fePage.seachIcon.click();
		
		Thread.sleep(3500);
		
		System.out.println("clicked search icon");	
		
		fePage.gridView.click();
		
		System.out.println("clicked Grid view..");	
		
		Thread.sleep(3500);
		
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
		
	@Test(priority=10,alwaysRun=true,groups={"migrationgroup"})
	
	//validate List view thumb nails,price,Title,ratings
	public void listViewElemValidations() throws MalformedURLException, InterruptedException
	{
		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside Search test case - List view");
		
		driver.get(EnvData.LenovoProdUS+"us/en");
		
		Thread.sleep(5000);
		
		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.searchbar));
		
		fePage.searchbar.click();

		System.out.println("clicked on search bar..");

		Thread.sleep(2500);

		String keyword = "Yoga";
		
		fePage.searchbar.clear();

		fePage.searchbar.sendKeys(keyword);

		Thread.sleep(8500);
		
		fePage.seachIcon.click();
		Thread.sleep(3500);
		
		System.out.println("clicked search icon");
		
		fePage.listViewButton.click();
		
		Thread.sleep(2000);
		
		if(fePage.listViewThumbnail.isDisplayed())
			
		{
			Assert.assertTrue(fePage.listViewprice.isDisplayed(), "Failed:Price is not displayed");
			
			Assert.assertTrue(fePage.listProductTitle.isDisplayed(), "Failed:Product Title not displayed");
			
			Assert.assertTrue(fePage.listviewRating.isDisplayed(), "Failed:List view Rating not displayed");
			
			System.out.println("Displayed:price,Title,Rating");
		}
	
	
	}
	
	@Test(priority=11,alwaysRun=true,groups={"migrationgroup"})
	
	// validate load more button in List view
	public void ListviewLoadmoreValidation() throws MalformedURLException, InterruptedException
	{
		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside Search test case - List view");

		//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
		
		driver.get(EnvData.LenovoProdUS+"us/en");
		
		Thread.sleep(5000);
		
		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.searchbar));
		
		fePage.searchbar.click();

		System.out.println("clicked on search bar..");

		Thread.sleep(2500);

		String keyword = "Yoga";
		
		fePage.searchbar.clear();

		fePage.searchbar.sendKeys(keyword);

		Thread.sleep(8500);
		
		fePage.seachIcon.click();
		
		Thread.sleep(3500);
		
		System.out.println("clicked search icon");
		
		fePage.listViewButton.click();
		
		Thread.sleep(4000);
		
		
		try{
			while(fePage.listviewLoadmoreButton.isDisplayed()){
				//jse.executeScript("arguments[0].scrollIntoView(true);",fePage.listviewLoadmoreButton);
				fePage.listviewLoadmoreButton.click(); 
				Thread.sleep(6000);

			}	
		}
			catch(Exception e){
				System.out.println("inside catch-Load more button is not there");
			}

	}
		

	@Test(priority=12,alwaysRun=true,groups={"migrationgroup"})
	
	public void noResults() throws MalformedURLException, InterruptedException
	{
		this.prepareTest();

		fePage = new FEPage(driver);

		System.out.println("inside Search test case - No results");

		//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
		
		driver.get(EnvData.LenovoProdUS+"us/en");
		
		Thread.sleep(5000);
		
		WebDriverWait wait = new WebDriverWait(driver,300);

		wait.until(ExpectedConditions.visibilityOf(fePage.searchbar));
		
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
	
	@Test(priority=13,alwaysRun=true,groups={"migrationgroup"})
		//check recent search title,validate prod-price,title and rating
		public void recentSearch() throws MalformedURLException, InterruptedException
		{
			this.prepareTest();

			fePage = new FEPage(driver);

			System.out.println("inside Search test case - recent search");

			//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");
			
			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Thread.sleep(8000);
			
			WebDriverWait wait = new WebDriverWait(driver,300);

			wait.until(ExpectedConditions.visibilityOf(fePage.searchbar));
			
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
	
		
		@Test(priority=14,alwaysRun=true,groups={"migrationgroup"})
		
	//validate-pagination
		
		public void pagination() throws MalformedURLException, InterruptedException
		{
			this.prepareTest();

			fePage = new FEPage(driver);

			System.out.println("inside Search test case - pagination");
			
			driver.get(EnvData.LenovoProdUS+"us/en");
			
			Thread.sleep(8000);
			
			WebDriverWait wait = new WebDriverWait(driver,300);

			wait.until(ExpectedConditions.visibilityOf(fePage.searchbar));
			
			fePage.searchbar.click();

			System.out.println("clicked on search bar..");

			Thread.sleep(2500);
			
			fePage.searchbar.clear();
		
			Thread.sleep(2000);
		
			fePage.searchbar.sendKeys("yoga");
			
			fePage.seachIcon.click();
		
			Thread.sleep(4000);
			
			try{
				while(fePage.prodNxtButton.isDisplayed()){
					fePage.prodNxtButton.click(); 
					Thread.sleep(6000);

				}	
				
				
			}
				catch(Exception e){
					System.out.println("inside catch-nxt button is not displayed");
				}
			System.out.println("passed:next pagination");
			try{
				while(fePage.prodprevButton.isDisplayed()){
					fePage.prodprevButton.click(); 
					Thread.sleep(6000);

				}	
				
				
			}
				catch(Exception e){
					System.out.println("inside catch-prev button is not displayed");
				}
			System.out.println("passed:Previous pagination");
		}
		
	
	 @AfterMethod
	public void browserClose() throws InterruptedException
	{
		Thread.sleep(3000);

		driver.quit();


	}		

}


