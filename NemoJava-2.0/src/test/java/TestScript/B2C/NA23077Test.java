package TestScript.B2C;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.HMCPage;
import Pages.B2CPage;

import TestScript.SuperTestClass;

public class NA23077Test extends SuperTestClass {
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public int listPrice=8000;
	public int consumer=6900;
	public int business=7900;
	public String pn;
	public String partNumber="20HM0045BR";
	private String filePath = "D:\\NA23077.xlsx";

	public NA23077Test(String store) {
		this.Store = store;
		this.testName = "NA-23077";
	}

	@Test(alwaysRun = true,groups={"shopgroup","pricingpromot","p2","b2c"})
	public void NA22685(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);			
			b2cPage = new B2CPage(driver);
			
			//get valid partNumber
			driver.get(testData.B2C.getHomePageUrl()+"/p/"+testData.B2C.getDefaultMTMPN());
			String xpath="//input[@value='"+testData.B2C.getDefaultMTMPN()+"']/../button[@id='addToCartButtonTop' and not(@disabled)]";
			if(!Common.checkElementDisplays(driver, By.xpath(xpath), 120)){
				pn=partNumber;
			}else{
				pn=testData.B2C.getDefaultMTMPN();
			}
			System.out.println(pn);

			//upload file
			File file = new File(filePath);
			FileOutputStream excelFileInputStream = new FileOutputStream(file);
			writeExcel(excelFileInputStream, "xlsx");
			excelFileInputStream.close();			
			uploadFile(file);
			
			//consumer  business
			double consumerPrice=checkDebugPrice("consumer",consumer);
			double businessPrice=checkDebugPrice("business",business);
//			double consumerPrice=6890.0;
//			double businessPrice=7890.0;
			
			System.out.println("consumerPrice is "+consumerPrice);
			System.out.println("businessPrice is "+businessPrice);
			
			//check on website
			driver.get(testData.B2C.getHomePageUrl()+"/cart");
						
			boolean newCartFlag=false;
			if (Common.isElementExist(driver, By.xpath("//button[contains(@class,'Submit_Button')]"))){
				newCartFlag=true;
			}
			System.out.println("newCartFlag is "+newCartFlag);
			

			double CartTotalPrice;
								
			if(newCartFlag){
				if(Common.isElementExist(driver, By.xpath(".//*[@id='emptyCartItemsForm']/a/img"))){
					b2cPage.NewCart_DeleteButton.click();
					b2cPage.NewCart_ConfirmDelete.click();					
				}
				b2cPage.NewCart_PartNumberTextBox.clear();
				b2cPage.NewCart_PartNumberTextBox.sendKeys(pn);
				b2cPage.NewCart_Submit.click();
				
				if(Common.checkElementDisplays(driver, By.xpath("//div/input[@id='active_button']"), 10)){
					driver.findElement(By.xpath("//div/input[@id='active_button']")).click();
				}
				
				CartTotalPrice=GetPriceValue1(b2cPage.NewCart_PriceTotal.getText());
				System.out.println("CartTotalPrice is "+CartTotalPrice);
				Assert.assertEquals(CartTotalPrice, consumerPrice, .01);
				
				//select profile and state
				Select select = new Select(b2cPage.CartPage_PriceProfile);
				Select select1 = new Select(b2cPage.CartPage_PriceState);
				
				select.selectByValue("0");
				select1.selectByValue("BR-SP");
				Common.sleep(20000);
				CartTotalPrice=GetPriceValue1(b2cPage.NewCart_PriceTotal.getText());
				Assert.assertEquals(CartTotalPrice, consumerPrice, .01);
				
				select.selectByValue("1");
				Common.sleep(20000);
				CartTotalPrice=GetPriceValue1(b2cPage.NewCart_PriceTotal.getText());
				Assert.assertEquals(CartTotalPrice, businessPrice, .01);
				
				select.selectByValue("2");
				Common.sleep(20000);
				CartTotalPrice=GetPriceValue1(b2cPage.NewCart_PriceTotal.getText());
				Assert.assertEquals(CartTotalPrice, consumerPrice, .01);
				

			}else{
				if (Common.isElementExist(driver,
						By.xpath("//a[contains(text(),'Empty cart')]"))) {
					driver.findElement(
							By.xpath("//a[contains(text(),'Empty cart')]")).click();
				}
				B2CCommon.addPartNumberToCart(b2cPage, testData.B2C.getDefaultMTMPN());
				if(Common.isElementExist(driver,By.id("active_button"))){
					driver.findElement(By.id("active_button")).click();
					Common.sleep(10000);
				}
				if(Common.checkElementDisplays(driver, By.xpath("//div/input[@id='active_button']"), 10)){
					driver.findElement(By.xpath("//div/input[@id='active_button']")).click();
				}
				
				CartTotalPrice=GetPriceValue1(b2cPage.CartPage_totalPrice.getText());
				Assert.assertEquals(CartTotalPrice, consumerPrice, .01);
				
				//select profile and state
				Select select = new Select(b2cPage.CartPage_PriceProfile);
				Select select1 = new Select(b2cPage.CartPage_PriceState);
				
				select.selectByValue("0");
				select1.selectByValue("BR-SP");
				Common.sleep(20000);
				CartTotalPrice=GetPriceValue1(b2cPage.CartPage_totalPrice.getText());
				Assert.assertEquals(CartTotalPrice, consumerPrice, .01);
				
				select.selectByValue("1");
				Common.sleep(20000);
				CartTotalPrice=GetPriceValue1(b2cPage.CartPage_totalPrice.getText());
				Assert.assertEquals(CartTotalPrice, businessPrice, .01);
				
				select.selectByValue("2");
				Common.sleep(20000);
				CartTotalPrice=GetPriceValue1(b2cPage.CartPage_totalPrice.getText());
				Assert.assertEquals(CartTotalPrice, consumerPrice, .01);
				
			}	
			
			if(Common.checkElementDisplays(driver, By.xpath("//div[@class='close-lnv-call-center']"), 5)){
				driver.findElement(By.xpath("//div[@class='close-lnv-call-center']")).click();
			}
			
			b2cPage.Cart_CheckoutButton.click();
			
			if(Common.checkElementDisplays(driver, b2cPage.Checkout_StartCheckoutButton, 5)){
				b2cPage.Checkout_StartCheckoutButton.click();
			}
			if(Common.checkElementDisplays(driver, b2cPage.Shipping_editAddress, 5)){
				b2cPage.Shipping_editAddress.click();
			}
			
			double ShippingTotalPrice;

			
			if(Common.checkElementDisplays(driver, b2cPage.NewShipping__TotalPrice, 5)){

				ShippingTotalPrice=GetPriceValue1(b2cPage.NewShipping__TotalPrice.getText());
				System.out.println("ShippingTotalPrice:"+ShippingTotalPrice);
				Assert.assertEquals(ShippingTotalPrice, consumerPrice, .01);
				
				//select profile and state
				Select select = new Select(b2cPage.NewShippingPage_PriceProfile);
				Select select1 = new Select(b2cPage.ShippingPage_PriceState);
				
				select.selectByValue("0");
				select1.selectByValue("BR-SP");
				Common.sleep(20000);
				ShippingTotalPrice=GetPriceValue1(b2cPage.NewShipping__TotalPrice.getText());
				System.out.println("ShippingTotalPrice:"+ShippingTotalPrice);
				Assert.assertEquals(ShippingTotalPrice, consumerPrice, .01);
				
				select.selectByValue("1");
				Common.sleep(20000);
				ShippingTotalPrice=GetPriceValue1(b2cPage.NewShipping__TotalPrice.getText());
				System.out.println("ShippingTotalPrice:"+ShippingTotalPrice);
				Assert.assertEquals(ShippingTotalPrice, businessPrice, .01);
				

								
			}else{
				ShippingTotalPrice = GetPriceValue1(b2cPage.Shipping_PriceTotal.getText());
				System.out.println("ShippingTotalPrice:"+ShippingTotalPrice);
				Assert.assertEquals(ShippingTotalPrice, consumerPrice, .01);
				
				//select profile and state
				Select select = new Select(b2cPage.ShippingPage_PriceProfile);
				Select select1 = new Select(b2cPage.ShippingPage_PriceState);
				
				select.selectByValue("0");
				select1.selectByValue("BR-SP");
				Common.sleep(20000);
				ShippingTotalPrice=GetPriceValue1(b2cPage.Shipping_PriceTotal.getText());
				System.out.println("ShippingTotalPrice:"+ShippingTotalPrice);
				Assert.assertEquals(ShippingTotalPrice, consumerPrice, .01);
				
				select.selectByValue("1");
				Common.sleep(20000);
				ShippingTotalPrice=GetPriceValue1(b2cPage.Shipping_PriceTotal.getText());
				System.out.println("ShippingTotalPrice:"+ShippingTotalPrice);
				Assert.assertEquals(ShippingTotalPrice, businessPrice, .01);
				
			}
						

			file.delete();

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}

	}
	public void fillRuleInfo(WebDriver driver, HMCPage hmcPage, String name, String dataInput, WebElement ele1,
			String xpath) throws InterruptedException {
		WebElement target;
		Common.waitElementClickable(driver, ele1, 30);
		Thread.sleep(1000);
		ele1.click();
		Common.waitElementVisible(driver, hmcPage.B2CPriceRules_SearchInput);
		hmcPage.B2CPriceRules_SearchInput.clear();
		hmcPage.B2CPriceRules_SearchInput.sendKeys(dataInput);
		target = driver.findElement(By.xpath(xpath));
		Common.waitElementVisible(driver, target);
		target.click();
		System.out.println(name + ": " + dataInput);
		Thread.sleep(5000);
	}
	
	public double checkDebugPrice(String type,double price) throws InterruptedException{
		// Go to HMC -> Price Settings -> Pricing Cockpit -> B2B Price
		Dailylog.logInfo("Price simulate---");
		driver.get(testData.HMC.getHomePageUrl());
		if(Common.checkElementExists(driver, hmcPage.Login_IDTextBox, 10)){
			HMCCommon.Login(hmcPage, testData);
		}
		hmcPage.Home_PriceSettings.click();
		hmcPage.Home_PricingCockpit.click();
		driver.switchTo().frame(hmcPage.PricingCockpit_iframe);
		Thread.sleep(3000);
		if (Common.isElementExist(driver, By.xpath("//div[1]/input[@name='j_username']"))) {
			hmcPage.PricingCockpit_username.sendKeys(testData.HMC.getLoginId());
			hmcPage.PricingCockpit_password.sendKeys(testData.HMC.getPassword());
			hmcPage.PricingCockpit_Login.click();
		}

		Thread.sleep(3000);
		// Simulator

		hmcPage.PricingCockpit_B2CPriceSimulator.click();
		Thread.sleep(5000);

		// Country
		String dataInput = "[" + testData.Store.substring(0, 2).toUpperCase() + "] "
				+ "Brazil";
		String xpath = "//span[text()='" + dataInput + "' and @class='select2-match']/../../*[not(text())]";
		fillRuleInfo(driver, hmcPage, "Country", dataInput, hmcPage.B2CPriceSimulator_country, xpath);

		
		hmcPage.B2BpriceSimulate_StateButton.click();
		System.out.println("sate click:");
		Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_StateButton, 3);
		hmcPage.B2BpriceSimulate_CountryText.sendKeys("SP");
		hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
		System.out.println("state sendkey:");
		Thread.sleep(10000);
		
		//consumer  business
		hmcPage.B2BpriceSimulate_CusromerTypeButton.click();
		System.out.println("Type click:");
		Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CusromerTypeButton, 3);
		hmcPage.B2BpriceSimulate_CountryText.sendKeys(type);
		hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
		System.out.println("Consumer Type sendkey:");
		Thread.sleep(10000);
		
		
		// Store -> aule
		Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_StoreButton, 120);
		hmcPage.B2BpriceSimulate_StoreButton.click();
		System.out.println("store click:");
		Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 120);
		hmcPage.B2BpriceSimulate_CountryText.sendKeys("brweb");
		hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
		System.out.println("store sendkey:");
		Thread.sleep(3000);
		// Catalog version -> Nemo Master Multi Country Product Catalog -
		// Online
		Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CatalogButton, 120);
		hmcPage.B2BpriceSimulate_CatalogButton.click();
		System.out.println("catalog click:");
		Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 3);
		hmcPage.B2BpriceSimulate_CountryText.sendKeys("Nemo Master Multi Country Product Catalog - Online");
		hmcPage.B2BpriceSimulate_CountryText.sendKeys(Keys.ENTER);
		System.out.println("catalog sendkey:");
		Thread.sleep(30000);

		// Price Date -> 2016-04-28
		Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_DateButton, 120);
		hmcPage.B2BpriceSimulate_DateButton.click();
		System.out.println("data click:");
		hmcPage.B2BpriceSimulate_DateButton.sendKeys(Keys.ENTER);
		System.out.println("data sendkey:");
		Thread.sleep(3000);
		// Product -> 20FAS0S010
		Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_ProductButton, 120);
		hmcPage.B2BpriceSimulate_ProductButton.click();
		System.out.println("product click:");
		Common.waitElementClickable(driver, hmcPage.B2BpriceSimulate_CountryText, 3);
		hmcPage.B2BpriceSimulate_CountryText.sendKeys(pn);
		Thread.sleep(5000);
		WebElement productResult = driver.findElement(By.xpath("//div[text()='[']/span[text()='" + pn + "']"));
		Common.waitElementVisible(driver, productResult);
		productResult.click();
		System.out.println("product sendkey:");
		Thread.sleep(3000);
		hmcPage.B2BpriceSimulate_DebugButton.click();
		Thread.sleep(3000);
		Common.waitElementVisible(driver, hmcPage.B2BpriceSimulate_webPrice);
		String debugPriceText = hmcPage.B2BpriceSimulate_webPrice.getText().toString();
		double debugPrice = GetPriceValue(debugPriceText);	
		Dailylog.logInfo("product debug price : " + debugPrice);
		Assert.assertEquals(debugPrice, price, 0.1);
		xpath="//td[@class='web']/div/div/samp/i[text()='"+type.toUpperCase()+"_PUBLIC(SP)']";
		Assert.assertTrue(Common.checkElementDisplays(driver, driver.findElement(By.xpath(xpath)), 10));;
		
		String totalPriceText = hmcPage.B2CpriceSimulate_TotalPrice.getText().toString();
		double totalPrice = GetPriceValue(totalPriceText);	
		driver.switchTo().defaultContent();
		hmcPage.hmcHome_hmcSignOut.click();
		return totalPrice;
	}

	public double GetPriceValue(String Price) {
		if (Price.contains("FREE")||Price.contains("INCLUDED")){
			return 0;
		}
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		String pattern = "\\d+\\.*\\d*";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(Price);
    	float priceValue;
        if (m.find( )) {
    		priceValue = Float.parseFloat( m.group());
    		return priceValue;
        } else {
        	return 0;
        }
	}
	public double GetPriceValue1(String Price) {
		if (Price.contains("FREE")||Price.contains("INCLUDED")){
			return 0;
		}
//		System.out.println(Price);
		String pattern = "\\d+\\.*+.*";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(Price);
    	float priceValue;
        if (m.find( )) {
//        	System.out.println(m.group());
    		priceValue = Float.parseFloat( m.group().replace(".", "").replace(",", "."));
    		return priceValue;
        } else {
        	return 0;
        }

	}
	
	
	
	private void uploadFile(File file) throws InterruptedException{
		
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_PriceSettings.click();
		hmcPage.PriceSettings_BrazilPriceImport.click();
		Common.waitElementClickable(driver, hmcPage.SMBGroupUpload_upload, 3);
		hmcPage.SMBGroupUpload_upload.click();
		Common.switchToWindow(driver, 1);
		if (file.exists()) {
			hmcPage.SMBGroupUpload_selectFile.sendKeys(filePath);
			hmcPage.SMBGroupUpload_uploadBtn.click();
			Common.switchToWindow(driver, 0);
			hmcPage.SMBGroupUpload_update.click();
			Common.switchToWindow(driver, 1);
			try {
				Common.waitElementVisible(driver, hmcPage.BrPriceUpload_successResult);
			} catch (TimeoutException e) {
				Assert.fail("upload success message is not displayed");
			}
			hmcPage.SMBGroupUpload_close.click();
			Common.switchToWindow(driver, 0);

		} else {
			Assert.fail("upload file not eixsts!");
		}
		Dailylog.logInfoDB(1,"Upload file finished", Store,testName);
		hmcPage.hmcHome_hmcSignOut.click();
	}

	private void writeExcel(FileOutputStream os, String excelExtName) throws IOException {
		Workbook wb = null;
		try {
			if ("xls".equals(excelExtName)) {
				wb = new HSSFWorkbook();
			} else if ("xlsx".equals(excelExtName)) {
				wb = new XSSFWorkbook();
			} else {
				throw new Exception("file extention not correct");
			}

			Sheet sheet = wb.createSheet();
			Row row1 = sheet.createRow(0);

			Cell colCell = row1.createCell(0);
			colCell.setCellValue(1);
			Cell colCell1 = row1.createCell(1);
			colCell1.setCellValue(pn);
			Cell colCell12 = row1.createCell(2);
			colCell12.setCellValue("SP");
			Cell colCell3 = row1.createCell(3);
			colCell3.setCellValue(listPrice);
			Cell colCell14 = row1.createCell(4);
			colCell14.setCellValue(consumer);
			Cell colCell5 = row1.createCell(5);
			colCell5.setCellValue(business);
			Cell colCell16 = row1.createCell(6);
			colCell16.setCellValue(1111);
			Cell colCell7 = row1.createCell(7);
			colCell7.setCellValue("Public");
		
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
	}


}