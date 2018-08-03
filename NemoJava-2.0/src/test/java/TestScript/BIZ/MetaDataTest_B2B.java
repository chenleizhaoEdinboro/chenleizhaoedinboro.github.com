package TestScript.BIZ;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import CommonFunction.Common;
import Pages.B2BPage;
import Pages.B2CPage;
import TestData.PropsUtils;

public class MetaDataTest_B2B {
	private WebDriver driver;
	private String Url;
	private String RowIndex;
	private static ArrayList<String[]> outputData = new ArrayList<String[]>();
	private String result;
	private String userName;
	private String passWord;
	
	private B2BPage b2bPage;
	private String CurrentUrl_homepage;

	public MetaDataTest_B2B(String rowIndex, String homePage_url,String userName,String passWord) {
		this.Url = homePage_url;
		this.RowIndex = rowIndex;
		this.userName = userName;
		this.passWord = passWord;
	}

	@BeforeClass
	private void setupDriver() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void MetaData_Test() {
		
		// b2b metadata testing 
		b2bPage = new B2BPage(driver);
		
		
		//•	User Registration
		//		1.	Go to lenovo.com/le
		//		2.	Select Products: Laptops
		
		try{
			redirectedToUrl(driver,this.Url);
			clickElement(driver,b2bPage.createAnAccount);
			
			String expectedStr = getExpectedResult(0).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("3",result);
			
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "User Registration check Exception");
			result = "Block";
			saveResult("3",result);
		}
		
		//•	Login
		//		1.	Go to lenovo.com/le
		//		2.	Select Products: Laptops
		try{
			redirectedToUrl(driver,this.Url);
			//login(b2bPage,userName,passWord);
			//clickElement(driver,b2bPage.HomePage_productsLink);
			//clickElement(driver,driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")));
			
			String expectedStr = getExpectedResult(1).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("4",result);
			
		}catch(Throwable e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Login check Exception");
			result = "Block";
			saveResult("4",result);
		}

		//•	Homepage
		//		1.	Go to lenovo.com/le
		//		2.	Select Products: Laptops

		try{
			redirectedToUrl(driver,this.Url);
			login(b2bPage,userName,passWord);
//			clickElement(driver,b2bPage.HomePage_productsLink);
//			clickElement(driver,driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")));
			CurrentUrl_homepage = getCurrentUrl_prod();
			
			
			String expectedStr = getExpectedResult(2).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("5",result);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Homepage check Exception");
			result = "Block";
			saveResult("5",result);
		}
		
		//		1.	Go to lenovo.com/le
		//		2.	Select Products: Laptops
		
		try{
			clickElement(driver,b2bPage.HomePage_productsLink);
			clickElement(driver,driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")));
			
			String expectedStr = getExpectedResult(3).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("6",result);
			
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "PLP check Exception");
			result = "Block";
			saveResult("6",result);
		}

		
		//•	PDP
		//		1.	Go to lenovo.com/le
		//		2.	Select Products: Laptops
		//		3.	Click any one of the products listed
		
		try{
			clickElement(driver,driver.findElement(By.xpath("(//*[@id='resultList']/div/div/div/a)[1]")));
			
			String expectedStr = getExpectedResult(4).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("7",result);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "PDP check Exception");
			result = "Block";
			saveResult("7",result);
		}
		
		//•	Accessories Homepage
		//		1.	Go to lenovo.com/le
		//		2.	Select Products: Accessories & Upgrades
		try{
			clickElement(driver,b2bPage.MenuAccessory);
			
			String expectedStr = getExpectedResult(5).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("8",result);
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Accessories Homepage check Exception");
			result = "Block";
			saveResult("8",result);
		}
		
		//•	Category
		//		1.	Go to lenovo.com/le
		//		2.	Select Products: Accessories & Upgrades
		//		3.	Click any one of the product categories selected

		try{
			clickElement(driver,driver.findElement(By.xpath("(//div[@class='yCmsComponent']/div/div/h2/a)[1]")));
		
			String expectedStr = getExpectedResult(6).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("9",result);
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Category check Exception");
			result = "Block";
			saveResult("9",result);
		}
		
		
		//•	Cart
		//		1.	Go to lenovo.com/le
		//		2.	Select Products: Laptops
		//		3.	Select a subseries: ThinkPad T470
		//		4.	Click on Add to Cart: 
		try{
			clickElement(driver,b2bPage.HomePage_productsLink);
			clickElement(driver,driver.findElement(By.xpath("(//a[@class='products_submenu'])[1]")));
			
			b2bPage.addToCartBtn.click();
			b2bPage.addtoCartPOP.click();
			(new WebDriverWait(driver, 500)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2[contains(text(),'Adding to Cart')]")));
			
			String cartUrl = CurrentUrl_homepage + "cart";
			redirectedToUrl(driver,cartUrl);
			
			String expectedStr = getExpectedResult(7).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}	
			saveResult("10",result);
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Category check Exception");
			result = "Block";
			saveResult("10",result);
			
		}
		
		//•	Checkout
		//		1.	Go to lenovo.com/le
		//		2.	Select Products: Laptops
		//		3.	Select a subseries: ThinkPad T470
		//		4.	Click on Add to Cart: 
		//		5.	Click on Lenovo Checkout button
		//		6.	Note: Sign in, shipping, and Review pages should have checkout as the taxonomy type

		try{
			int ErrorsCount_Checkout = 0;
			
			clickElement(driver,b2bPage.cartPage_LenovoCheckout);
			
			String expectedStr = getExpectedResult(8).toLowerCase();
			int index_1 = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index_1 == -1){
				ErrorsCount_Checkout = ErrorsCount_Checkout + 1;
			}
			
			clickElement(driver,b2bPage.shippingEdit);
			clickElement(driver,b2bPage.shippingCarrotIcon);
			clickElement(driver,b2bPage.shippingSelectAddress);
			Thread.sleep(4000);
			clearTxt(driver,b2bPage.FirstName);
			sendKeysIntoElement(driver,b2bPage.FirstName,"test");
			clearTxt(driver,b2bPage.LastName);
			sendKeysIntoElement(driver,b2bPage.LastName,"test");
			clearTxt(driver,b2bPage.companyInput);
			String Account = Url.split("/")[4];
			sendKeysIntoElement(driver,b2bPage.companyInput,Account);
			clearTxt(driver,b2bPage.Mobile);
			clickElement(driver,b2bPage.shippingPage_ContinueToPayment);
			int index_2 = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index_2 == -1){
				ErrorsCount_Checkout = ErrorsCount_Checkout + 1;
			}
			
			if(!b2bPage.purchaseOrder.isSelected()){
				clickElement(driver,b2bPage.purchaseOrder);
			}
			clickElement(driver,driver.findElement(By.xpath("//span[contains(@class,'triangle')]")));
			clickElement(driver,driver.findElement(By.xpath("(//li[contains(@id,'ui-id')])[1]")));
			clearElement(driver,b2bPage.purchaseNum);
			sendKeysIntoElement(driver,b2bPage.purchaseNum,"1234567890");
			clearElement(driver,b2bPage.addressFirstName);
			sendKeysIntoElement(driver,b2bPage.addressFirstName,"test");
			clearElement(driver,b2bPage.addressLastName);
			sendKeysIntoElement(driver,b2bPage.addressLastName,"account");
			clearElement(driver,b2bPage.addressPhone);
			SimpleDateFormat dataFormat = new SimpleDateFormat("MM/dd/YYYY");
			sendKeysIntoElement(driver,b2bPage.purchaseDate,dataFormat.format(new Date()).toString());
			if(isElementExist(driver,By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a"))){
	            driver.findElements(By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a")).get(driver.findElements(By.xpath("//div[@id='ui-datepicker-div']//tr[last()]/td/a")).size()-1).click();
	        }
			clickElement(driver,b2bPage.ContinueforPayment);
			
			int index_3 = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index_3 == -1){
				ErrorsCount_Checkout = ErrorsCount_Checkout + 1;
			}

			if(ErrorsCount_Checkout == 0){
				result = "Pass";
			}else{
				result = "Fail";
			}
			saveResult("11",result);
	
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Checkout check Exception");
			result = "Block";
			saveResult("11",result);
		}
		
		
		//•	Thank You
		//		7.	Go to lenovo.com/le
		//		8.	Select Products: Laptops
		//		9.	Select a subseries: ThinkPad T470
		//		10.	Click on Add to Cart: 
		//		11.	Click on Lenovo Checkout button
		//		12.	Note: Sign in, shipping, and Review pages should have checkout as the taxonomy type
		//		13.	Place the order and check that the confirmation page has taxonomy type Thank You

		try{
			String summaryUrl = getCurrentUrl_prod();
			if(summaryUrl.contains("www3.lenovo.com")){
				result = "Skip";
			}else{
				
				if(isElementExist(driver, By.xpath("//*[@id='ccEmailAddress']"))){
					sendKeysIntoElement(driver,b2bPage.ccEmailAddress,"testlenovo@lenovo.com");
				}
				if(isElementExist(driver, By.xpath("//*[@id='commentArea']"))){
					sendKeysIntoElement(driver,b2bPage.commentArea,"good");
				}
				if(isElementExist(driver, By.xpath("//*[@id='shippingLabel']"))){
					sendKeysIntoElement(driver,b2bPage.shippingLabel,"good");
				}
				if(isElementExist(driver, By.xpath("//*[@id='invoiceInstruction']"))){
					sendKeysIntoElement(driver,b2bPage.invoiceInstruction,"good");
				}
				clickElement(driver,driver.findElement(By.xpath("//input[@id='Terms1']")));
				
				clickPlaceOrder(b2bPage);
				
				String expectedStr = getExpectedResult(9).toLowerCase();
				int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
				if(index != -1){
					result = "Pass";
				}else{
					result = "Fail";
				}
	
			}
			
			saveResult("12",result);
	
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Thank You check Exception");
			result = "Block";
			saveResult("12",result);
		}
		
		//•	Account
		//		1.	Go to lenovo.com/le
		//		2.	Go to My Account
		//		3.	Click any link, the taxonomy type value should be Account
		
		try{
			redirectedToUrl(driver,CurrentUrl_homepage);
			clickElement(driver,b2bPage.myAccount_link);
			clickElement(driver,driver.findElement(By.xpath("//a[contains(@href,'update-password')]")));
			
			String expectedStr = getExpectedResult(10).toLowerCase();
			int index = driver.getPageSource().toLowerCase().indexOf("meta name=\"taxonomytype\" content=\"" + expectedStr + "\"");
			if(index != -1){
				result = "Pass";
			}else{
				result = "Fail";
			}
			
			saveResult("13",result);
			
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("url is :" + Url + "#####" + "Account check Exception");
			result = "Block";
			saveResult("13",result);
		}
		
	}

	@AfterClass
	private void close() {
		driver.close();
		pushResult();
	}
	
	@AfterSuite
	private void doSuite(){
		markTestOver();
	}
	
	public void clickPlaceOrder(B2BPage b2bPage) {
		if (!driver.getCurrentUrl().contains("www3.lenovo.com"))
			clickElement(driver,b2bPage.OrderSummary_PlaceOrderButton);
	}
	
	
	private boolean isElementExist(WebDriver driver, By by) {
		boolean flag = false;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
		try {
			WebElement element = driver.findElement(by);
			flag = element != null;
		} catch (Exception e) {
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return flag;
	}
	
	public void login(B2BPage page, String username, String password) {
		sendKeysIntoElement(driver,page.IDcheckbox,username);
		sendKeysIntoElement(driver,page.PWcheckbox,password);
		clickElement(driver,page.signin);
		
	}

	public void clickElement(WebDriver driver,WebElement element){
		
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		try{
			element.click();
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			element.click();
		}

	}
	
	public void clearElement(WebDriver driver,WebElement element){
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		try{
			element.clear();
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			element.clear();
		}
	}
	
	
	public void clearTxt(WebDriver driver,WebElement element){
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		try{
			element.clear();
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			element.clear();
		}

	}	
	
	
	
	public void sendKeysIntoElement(WebDriver driver,WebElement element,String str){
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		try{
			element.sendKeys(str);
		}catch(Exception e){
			if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
				driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
			}
			element.sendKeys(str);
		}

	}
	
	public void redirectedToUrl(WebDriver driver,String url){
		driver.get(url);
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
	}
	
	public String getCurrentUrl_prod(){
		if(isElementExist(driver, By.xpath("//*[@id='oo_close_prompt']/span"))){
			driver.findElement(By.xpath("//*[@id='oo_close_prompt']/span")).click();
		}
		String str = driver.getCurrentUrl().toString();
		return str;
	}
	
	
	
	
	
	
	private void clickContinue(){
		while(Common.isElementExist(driver, By.xpath("//*[@id='product-builder-form']/descendant::div/button"))){
			//driver.findElement(By.xpath("//*[@id='product-builder-form']/descendant::div/button")).click();
			clickElement(driver,driver.findElement(By.xpath("//*[@id='product-builder-form']/descendant::div/button")));
		}
	}
	
	private String getCloumnNum(String startCloumn){
		int point = Integer.parseInt(startCloumn);
		int cloumn = point + 1;
		String CloumnNum = String.valueOf(cloumn);
		return CloumnNum;
	}

	private void saveResult(String columnIndex,String result) {
		outputData.add(new String[] { RowIndex, columnIndex, result});
	}
	
	private String getExpectedResult(int cloumnNum){
		String value = "";
		try{
			FileInputStream excelFileInPutStream = new FileInputStream(FactoryAndData.BIZ.MetaData_B2C.filePath);
			Workbook workbook = WorkbookFactory.create(excelFileInPutStream);
			Sheet sheet = workbook.getSheetAt(3);
			value = sheet.getRow(1).getCell(cloumnNum).getStringCellValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
		
	}
	
	private void pushResult() {
		try {
			FileInputStream excelFileInPutStream = new FileInputStream(FactoryAndData.BIZ.MetaData_B2C.filePath);
			Workbook workbook = WorkbookFactory.create(excelFileInPutStream);
			Sheet sheet = workbook.getSheetAt(1);

			int rowIndex;
			int columnIndex;
			String testresult;
			int size = outputData.size();
			CellStyle yellowStyle = workbook.createCellStyle();
			yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			yellowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			CellStyle blankStyle = workbook.createCellStyle();
			blankStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			blankStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			CellStyle greenStyle = workbook.createCellStyle();
			greenStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			greenStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			for (int i = 0; i < size; i++) {
				rowIndex = Integer.parseInt(outputData.get(i)[0]);
				columnIndex = Integer.parseInt(outputData.get(i)[1]);
				testresult = outputData.get(i)[2];
				

				try {
					// Punchout result
					Row row = sheet.getRow(rowIndex);
					Cell cell= row.createCell(columnIndex);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(testresult);
					if (testresult.equals("Pass"))
						sheet.getRow(rowIndex).getCell(columnIndex).setCellStyle(greenStyle);
					else
						sheet.getRow(rowIndex).getCell(columnIndex).setCellStyle(yellowStyle);
				} catch (Exception e) {
				}
				
			}

			FileOutputStream excelFileOutPutStream = new FileOutputStream(FactoryAndData.BIZ.MetaData_B2C.filePath);
			workbook.write(excelFileOutPutStream);
			excelFileOutPutStream.flush();
			excelFileOutPutStream.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	public void markTestOver(){
		try {
			String fileName = "\\\\10.62.6.95\\MetaDataTest\\B2BAutoTestInProgress.txt";
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
			fileName = "\\\\10.62.6.95\\MetaDataTest\\B2BAutoTestDone.txt";
			file = new File(fileName);

			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
