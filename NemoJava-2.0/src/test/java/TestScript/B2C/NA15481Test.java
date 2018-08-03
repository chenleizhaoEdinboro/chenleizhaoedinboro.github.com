package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class NA15481Test extends SuperTestClass {

	public NA15481Test(String store) {
		this.Store = store;
		this.testName = "NA-15481";
	}

	private String getCurrentDomain(String url) {
		String[] strs = url.split("/");
		return strs[2];
	}

	private double String2Num(String valueString) {
		String price = valueString.replace("$", "").replace(",", "").replace("-", "").replace("￥", "").replace("HK", "")
				.replace("SG", "").replace("£", "").replace("€", "");
		return Double.parseDouble(price);
	}

	private double findMinPirce(String xpath, String name, boolean isProductPage) {
		List<WebElement> minPrices = driver.findElements(By.xpath(xpath));
		double minPrice = 0d;
		if (minPrices.size() == 0) {
			setManualValidateLog(name + "have price,but sub page no price !!");
		}
		for (int y = 1; y <= minPrices.size(); y++) {
			double prePrice;
			if (isProductPage) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						driver.findElement(By.xpath("(" + xpath + ")[" + y + "]")));
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
			if(!Common.checkElementDisplays(driver, By.xpath("(" + xpath + ")[" + y + "]"), 10)) {
				driver.findElement(By.xpath("//button[@class='js-next button-called-out-alt tabbedBrowse-productListings-controls-next']")).click();
			}
			String itemName = driver.findElement(By.xpath("(" + xpath + ")[" + y + "]")).getText().toString();
//			System.out.println("itemName:"+itemName);
			if(itemName =="" || itemName == null){
				continue;
			}
			prePrice = String2Num(itemName);
			if (y == 1) {
				minPrice = prePrice;
			}
			if (minPrice > prePrice || y == 1) {
				minPrice = prePrice;
			}
		}
		System.out.println("miniPrice:"+minPrice);
		return minPrice;
	}

	private int checkHasNoProduct(String xpath) {
		List<WebElement> prices = driver.findElements(By.xpath(xpath));
		return prices.size();
	}

	// check level 3 price
	private void checkSubPrice() {
		String level3url = driver.getCurrentUrl();
		List<WebElement> subItems = driver.findElements(By.xpath("//h3[@class='seriesListings-title']/a"));
//		System.out.println("Level 3 page series title num:" + subItems.size());
		for (int x = 1; x <= subItems.size(); x++) {

			String itemName = driver.findElement(By.xpath("(//h3[@class='seriesListings-title']/a)[" + x + "]"))
					.getText().toString();
			// get level 3 page series price

			double seriesPrice = 0;
			// check is this series have value
			if (Common.isElementExist(driver, By.xpath("(//h3[@class='seriesListings-title']/a)[" + x
					+ "]/../../../div[@class='seriesListings-body']//dd[contains(@class,'final-price')]"))) {
//				System.out.println("sub series title :" + itemName + " have value");

				// remeber level 3 price
				String seriesPriceString = driver
						.findElement(By.xpath("(//h3[@class='seriesListings-title']/a)[" + x
								+ "]/../../../div[@class='seriesListings-body']//dd[contains(@class,'final-price')]"))
						.getText().toString();
				seriesPrice = String2Num(seriesPriceString);
				driver.findElement(By.xpath("(//h3[@class='seriesListings-title']/a)[" + x + "]")).click();
				double minPirce = findMinPirce("//li[contains(@id,'currentmodels')]//dd[contains(@class,'final-price')]", itemName, true);
				if (minPirce != seriesPrice) {
//					setManualValidateLog(itemName + "price is not correct!!");
					setManualValidateLog(itemName + "price is not correct!!");
				}

			} else {
//				System.out.println("sub series title :" + itemName + " no value");
				driver.findElement(By.xpath("(//h3[@class='seriesListings-title']/a)[" + x + "]")).click();
				int proNum = checkHasNoProduct("//li[contains(@id,'currentmodels')]//dd[contains(@class,'final-price')]");

				if (proNum > 0) {
					setManualValidateLog(itemName + " no price,but have product!!!");
				}

			}
			// back to Level 3
//			System.out.println("back to Level 3 page");
			driver.get(level3url);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void checkPrice(int level) {
		if (level == 1) {
			String level1url = driver.getCurrentUrl();
			List<WebElement> items = driver.findElements(By.xpath("//h1[@class='seriesPreview-title']"));
			for (int x = 1; x <= items.size(); x++) {
				String itemName = driver.findElement(By.xpath("(//h1[@class='seriesPreview-title'])[" + x + "]"))
						.getText().toString();
				// get level 1 page series price

				double seriesPrice = 0;
				// check is this series have value
				if (Common.isElementExist(driver,
						By.xpath("(//h1[@class='seriesPreview-title'])[" + x + "]/..//dd[contains(@class,'final-price')]"))) {

					// remeber level 1 price
					String seriesPriceString = driver
							.findElement(By.xpath(
									"(//h1[@class='seriesPreview-title'])[" + x + "]/..//dd[contains(@class,'final-price')]"))
							.getText().toString();
					seriesPrice = String2Num(seriesPriceString);
					driver.findElement(By.xpath("(//h1[@class='seriesPreview-title'])[" + x + "]")).click();
					double minPirce = findMinPirce("//dd[contains(@class,'final-price')]", itemName, false);
					if (minPirce != seriesPrice) {
						setManualValidateLog(itemName + "price is not correct!!");
					}
					checkSubPrice();

				} else {
//					System.out.println("series title :" + itemName + " no value");
					driver.findElement(By.xpath("(//h1[@class='seriesPreview-title'])[" + x + "]")).click();
					int proNum = checkHasNoProduct("//dd[contains(@class,'final-price')]");

					if (proNum > 0) {
						setManualValidateLog(itemName + " no price,but have product!!!");
					}
					checkSubPrice();

				}
				// back to Level 1
				driver.get(level1url);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} else {
			// only have level 2 and level 3
			String level2url = driver.getCurrentUrl();
			List<WebElement> items = driver.findElements(By.xpath("//h3[@class='brandListings-title']/a"));
			for (int x = 1; x <= items.size(); x++) {
				String itemName = driver.findElement(By.xpath("(//h3[@class='brandListings-title']/a)[" + x + "]"))
						.getText().toString();
				// get level 2 page series price

				double seriesPrice = 0;
				// check is this series have value
				if (Common.isElementExist(driver, By.xpath("(//h3[@class='brandListings-title']/a)[" + x
						+ "]/../../../div[contains(@class,'pricing')]//dd[contains(@class,'final-price')]"))) {
//					System.out.println("series title :" + itemName + " have value");

					// remeber level 2 price
					String seriesPriceString = driver
							.findElement(By.xpath("(//h3[@class='brandListings-title']/a)[" + x
									+ "]/../../../div[contains(@class,'pricing')]//dd[contains(@class,'final-price')]"))
							.getText().toString();
					seriesPrice = String2Num(seriesPriceString);
					driver.findElement(By.xpath("(//h3[@class='brandListings-title']/a)[" + x + "]")).click();
					double minPirce = findMinPirce("//dd[contains(@class,'final-price')]", itemName, false);
					if (minPirce != seriesPrice) {
						setManualValidateLog(itemName + "price is not correct!!");
					}
					checkSubPrice();

				} else {
//					System.out.println("series title :" + itemName + " no value");
					driver.findElement(By.xpath("(//h3[@class='brandListings-title']/a)[" + x + "]")).click();
					int proNum = checkHasNoProduct("//dd[contains(@class,'final-price')]");

					if (proNum > 0) {
						setManualValidateLog(itemName + " no price,but have product!!!");
					}
					checkSubPrice();

				}
				// back to Level 2
//				System.out.println("back to Level 2 page");
				driver.get(level2url);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public double getMiniPrice(double[] price) {
		double miniPrice=price[0];
		for(int i=0;i<price.length;i++) {
			if(price[i]<miniPrice) {
				miniPrice=price[i];		
			}
		}
		return miniPrice;
	}
	public void checkPriceNewUI() {	
		String url1=driver.getCurrentUrl();
		List<WebElement> elems=driver.findElements(By.xpath("//div[@class='viewmodeltabs-row qa-splitter-viewmodel-tabs']/ul/li"));
		for(WebElement elem:elems) {
			if(!elem.getAttribute("class").contains("vam-active")) {
				elem.click();
			}
			List<WebElement> eles=driver.findElements(By.xpath("//div[@class='viewmodel-row']//div[@class='model-platform']/div[@class='pricingSummary']/dl"));			
			List<WebElement> titles=driver.findElements(By.xpath("//div[@class='viewmodel-row']/div/div/div/div/div[@class='model-title']"));
			int j=0;
			for(int i=1;i<=eles.size();i++) {
				String xpath="(//div[@class='viewmodel-row']//div[@class='model-platform']/div[@class='pricingSummary']/dl)["+i+"]/dd";
				String xpath1="(//div[@class='viewmodel-row']//div[@class='list-box populate-product'])["+i+"]/a[";
				String xpath2="(//div[@class='viewmodel-row']//div[@class='list-box populate-product'])["+i+"]/a";
				List<WebElement> els=driver.findElements(By.xpath(xpath2));
				
				
				if(Common.checkElementDisplays(driver, By.xpath(xpath), 15)) {
					double homePrice=String2Num(driver.findElement(By.xpath(xpath)).getText());	
					System.out.println("homePrice:"+homePrice);
					double PLPprice[]=new double[els.size()];
					for(int k=1;k<=eles.size();k++) {
						JavascriptExecutor js = (JavascriptExecutor) driver;
						js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(xpath1+k+"]")));
						String title=driver.findElement(By.xpath(xpath1+k+"]")).getAttribute("href");
						title=title.split("/")[title.split("/").length-3];
						System.out.println("title:"+title);
						js.executeScript("arguments[0].click();", driver.findElement(By.xpath(xpath1+k+"]")));
						Common.sleep(50000);
						double miniPrice=checkPricePLP(homePrice,title);
						PLPprice[j]=miniPrice;
						j++;
						driver.get(url1);
						Common.sleep(50000);
					}
					//validate homePrice
					if(homePrice!=getMiniPrice(PLPprice)) {
						setManualValidateLog(titles.get(i).getText() + " HomePrice is not eque to PLP miniPrice");
					}
					
				}else {
					for(WebElement el:els) {
						el.click();
						String title=el.getAttribute("href");
						title=title.split("/")[title.split("/").length-3];
						checkPricePLP(0,title);
						driver.get(url1);
						Common.sleep(50000);
					}
				}
			}		
		}
		
	}
	
	public double checkPricePLP(double price,String title) {
		String xpath="//div[@class='cta-group-price']/dl[@class='cta-price']/dd";
		List<WebElement> eles=driver.findElements(By.xpath("//div[contains(@class,'productListing')]/div/dl[@class='pricingSummary-details']/dd[@class='saleprice pricingSummary-details-final-price']"));
		
		if(price!=0) {
			
			double startPrice=String2Num(driver.findElement(By.xpath(xpath)).getText());
		    if(startPrice!=findMinPirce("//dd[contains(@class,'final-price')]", title, false)) {
					setManualValidateLog(title + ":Start price is not eque to  miniPrice");	
		    }
//		    System.out.println(startPrice);
			return startPrice;
		}else {
			if(Common.checkElementDisplays(driver, By.xpath(xpath), 10)) {
				setManualValidateLog(title + ":Should not have start price");				
			}
			if(eles.size()>0) {
				setManualValidateLog(title + ":Should not have products");				
			}
			return 0;
		}
	}

	//need run serival hous
	@Test(alwaysRun= true)
	public void NA15481(ITestContext ctx) {
		try {
			this.prepareTest();
			
			driver.get(testData.B2C.getHomePageUrl());
			B2CPage b2cPage = new B2CPage(driver);
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			// For New UI
			// click laptops
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Laptops);
			Dailylog.logInfoDB(1,"Navigate to laptops",Store,testName);				
			Thread.sleep(20000);
			if(Common.checkElementDisplays(driver, b2cPage.Home_activeLaptops, 15)) {
				Dailylog.logInfoDB(2,"Check Laptops page on New UI",Store,testName);	
				checkPriceNewUI();
			}else {
				checkPrice(1);
			}
			
			// click Tablets
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.Tablets);
			Thread.sleep(2000);
			checkPrice(2);
			
			// click Desktops
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.AllInOnes);
			Thread.sleep(2000);
			checkPrice(2);
			
			// click Smartphones
			NavigationBar.goToSplitterPageUnderProducts(b2cPage, SplitterPage.SmartPhones);
			Thread.sleep(2000);
			checkPrice(2);
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}

}
