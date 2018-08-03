package TestScript.B2C;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import CommonFunction.B2CCommon;
import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import Pages.PartSalesPage;
import TestScript.SuperTestClass;
import junit.framework.Assert;

public class SHOPE348Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public PartSalesPage partPage;
	public MailPage mailPage;
	String partNumber;
	public float webprice;
	public float yourprice;
	
	public SHOPE348Test(String store) {
		this.Store = store;
		this.testName = "SHOPE348";
	}
	
	@Test(priority = 0, enabled = true, alwaysRun = true, groups = { "shopgroup", "p2", "b2c" })
	public void SHOPE348 (ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			partPage = new PartSalesPage(driver);
			partNumber = "00HM888";
			noTeleTest();
			teleNoOverwriteTest();
			teleOverwriteTest();
			
		}catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void noTeleTest() {
		//TODO no tele quote
		loginSeleCountry();
		addToCartEsupport(partPage);
		Common.sleep(1000);
		partPage.viewMyCart.click();
		Common.sleep(1000);
		if (Store.equals("AU")) {
			partPage.partSales_LoginID.sendKeys("testau@sharklasers.com");
			Dailylog.logInfoDB(1,"No ASM AU login successfully.", Store,testName);
		}else if (Store.equals("US")) {
			partPage.partSales_LoginID.sendKeys("testus@sharklasers.com");
			Dailylog.logInfoDB(2,"No ASM US login successfully.", Store,testName);
		}else if (Store.equals("CA")) {
			partPage.partSales_LoginID.sendKeys("testca@sharklasers.com");
			Dailylog.logInfoDB(2,"No ASM CA login successfully.", Store,testName);
		}else if (Store.equals("NZ")) {
			partPage.partSales_LoginID.sendKeys("testnz@sharklasers.com");
			Dailylog.logInfoDB(2,"No ASM NZ login successfully.", Store,testName);
		}
		Common.sleep(1000);
		partPage.partSales_Password.sendKeys("1q2w3e4r");
		Common.sleep(1000);
		driver.findElement(By.xpath("//input[@name='RememberMe']")).click();
		partPage.partSales_Signin.click();
		Common.sleep(1000);
		//driver.findElement(By.xpath("//a[@class='cartlink']")).click();
		Common.sleep(1000);
		driver.findElement(By.xpath("//input[@id='quantity0']")).clear();
		driver.findElement(By.xpath("//input[@id='quantity0']")).sendKeys("1");
		driver.findElement(By.xpath( "//input[@value='Update']")).click();
		String cartWebPrice = driver.findElement(By.xpath("//span[@class='price-calculator-cart-summary-subTotalWithoutCoupon']")).getText().toString();
		float cartwebprice = B2CCommon.GetPriceValue(cartWebPrice);
		Assert.assertTrue(cartwebprice==webprice);
		String cartYourPrice = driver.findElement(By.xpath("//span[@class='price-calculator-cart-summary-totalPriceWithTax qa_estimatedTotal_Price']")).getText().toString();
		yourprice = B2CCommon.GetPriceValue(cartYourPrice);
		driver.findElement(By.xpath("//input[@id='quote_button']")).click();
		Common.sleep(1000);
		driver.findElement(By.xpath("//input[@class='checkoutForm-formInput']")).click();
		driver.findElement(By.xpath("//input[@class='checkoutForm-formInput']")).sendKeys("1234567890");
		Common.sleep(2000);
		driver.findElement(By.xpath("//input[@id='submit-quote-button']")).click();
		Dailylog.logInfoDB(2,"No ASM Quote request.", Store,testName);
		String quoteNum = driver.findElement(By.xpath("//td[contains(text(),'Quote Number')]/following-sibling::td[1]")).getText().toString();
		System.out.println(quoteNum);
		openMail();
		checkContent(quoteNum,webprice,yourprice);
		checkDetailHyperLink(Store,quoteNum);	
			
	}
	
	public void teleNoOverwriteTest() {
		//TODO Tele no overwrite price
		loginSeleCountry();
		addToCartEsupport(partPage);
		Common.sleep(1000);
		partPage.viewMyCart.click();
		Common.sleep(1000);
		driver.findElement(By.xpath("//div[@class='btn-Checkout']/span")).click();
		WebElement myAccount = driver.findElement(By.xpath("(//a[contains(@href,'partsales/my-account/') and contains(@class,'has-submenu')])[1]"));
		Common.mouseHover(driver, myAccount);
		Common.sleep(1000);
		driver.findElement(By.xpath("(//div[contains(text(),'	Sign In')])[1]")).click();
		Common.sleep(3000);

		driver.findElement(By.xpath("//*[@id='login.email.id']")).sendKeys("youngmeng_n");
		driver.findElement(By.xpath("//*[@id='login.password']")).sendKeys("Lenovo-1");
		driver.findElement(By.xpath("//label[@class='signInForm-checkbox']")).click();
		driver.findElement(By.xpath("//*[@id='nemoLoginForm']/button")).click();
		//driver.findElement(By.xpath("//input[@id='UserID']")).sendKeys("youngmeng_n");
		//driver.findElement(By.xpath("//input[@id='Password']")).sendKeys("Lenovo-1");
		//driver.findElement(By.xpath("//div[@id='btnsignin']")).click();
		
		Common.sleep(10000);
		driver.findElement(By.xpath("//a[contains(@href,'activateASM')]")).click();
		Common.sleep(5000);
		
		if (Store.equals("AU")) {
			driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys("testau@sharklasers.com");
		}else if (Store.equals("US")) {
			driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys("testus@sharklasers.com");
		}else if (Store.equals("CA")) {
			driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys("testca@sharklasers.com");
		}else if (Store.equals("NZ")) {
			driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys("testnz@sharklasers.com");
		}
		
		//driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys("aubuyer@yopmail.com");
		//driver.findElement(By.xpath("//a[@id='showCustomerSearch']/span")).click();
		//driver.findElement(By.xpath("//input[@id='id']")).sendKeys("usbuyer@yopmail.com");
		Common.sleep(15000);
		driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//button[@class='ASM-btn ASM-btn-start-session vAlignTop']")).click();
		
		Common.sleep(5000);
		driver.findElement(By.xpath("//a[@class='cartlink']")).click();
		String cartWebPrice = driver.findElement(By.xpath("//span[@class='price-calculator-cart-summary-subTotalWithoutCoupon']")).getText().toString();
		float cartwebprice = B2CCommon.GetPriceValue(cartWebPrice);
		Assert.assertTrue(cartwebprice==webprice);
		String cartYourPrice = driver.findElement(By.xpath("//span[@class='price-calculator-cart-summary-totalPriceWithTax qa_estimatedTotal_Price']")).getText().toString();
		yourprice = B2CCommon.GetPriceValue(cartYourPrice);
		
		//Not overwrite price
		driver.findElement(By.xpath("//input[@id='quote_button']")).click();
		Common.sleep(1000);
		driver.findElement(By.xpath("//input[@class='checkoutForm-formInput']")).click();
		driver.findElement(By.xpath("//input[@class='checkoutForm-formInput']")).sendKeys("1234567890");
		Common.sleep(2000);
		driver.findElement(By.xpath("//input[@id='submit-quote-button']")).click();
		String quoteNum = driver.findElement(By.xpath("//td[contains(text(),'Quote Number')]/following-sibling::td[1]")).getText().toString();
		System.out.println(quoteNum);
		//send email to user 
		driver.findElement(By.xpath("//a[@id='emailQuoteButton']")).click();
		if (Store.equals("AU")) {
			driver.findElement(By.xpath("//textarea[@id='quote-email-to-addresses']")).sendKeys("testau@sharklasers.com");
		}else if (Store.equals("US")) {
			driver.findElement(By.xpath("//textarea[@id='quote-email-to-addresses']")).sendKeys("testus@sharklasers.com");
		}else if (Store.equals("CA")) {
			driver.findElement(By.xpath("//textarea[@id='quote-email-to-addresses']")).sendKeys("testca@sharklasers.com");
		}else if (Store.equals("NZ")) {
			driver.findElement(By.xpath("//textarea[@id='quote-email-to-addresses']")).sendKeys("testnz@sharklasers.com");
		}
		driver.findElement(By.xpath("//textarea[@id='quote-email-description']")).sendKeys("aaa");
		driver.findElement(By.xpath("//input[@id='quote-send-email']")).click();
		Common.sleep(2000);
		openMail();
		checkDetailHyperLink(Store,quoteNum);
		checkContent(quoteNum,webprice,yourprice);
		
	}
	
	public void teleOverwriteTest() {
		//TODO Tele overwrite price
		loginSeleCountry();
		addToCartEsupport(partPage);
		Common.sleep(1000);
		partPage.viewMyCart.click();
		Common.sleep(1000);
		driver.findElement(By.xpath("//div[@class='btn-Checkout']/span")).click();
		WebElement myAccount = driver.findElement(By.xpath("(//a[contains(@href,'partsales/my-account/') and contains(@class,'has-submenu')])[1]"));
		Common.mouseHover(driver, myAccount);
		Common.sleep(1000);
		driver.findElement(By.xpath("(//div[contains(text(),'	Sign In')])[1]")).click();
		Common.sleep(3000);
		driver.findElement(By.xpath("//*[@id='login.email.id']")).sendKeys("youngmeng_n");
		driver.findElement(By.xpath("//*[@id='login.password']")).sendKeys("Lenovo-1");
		driver.findElement(By.xpath("//label[@class='signInForm-checkbox']")).click();
		driver.findElement(By.xpath("//*[@id='nemoLoginForm']/button")).click();
		Common.sleep(10000);
		driver.findElement(By.xpath("//a[contains(@href,'activateASM')]")).click();
		Common.sleep(5000);
		if (Store.equals("AU")) {
			driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys("testau@sharklasers.com");
		}else if (Store.equals("US")) {
			driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys("testus@sharklasers.com");
		}else if (Store.equals("CA")) {
			driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys("testca@sharklasers.com");
		}else if (Store.equals("NZ")) {
			driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys("testnz@sharklasers.com");
		}
		//driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys("aubuyer@yopmail.com");
		//driver.findElement(By.xpath("//a[@id='showCustomerSearch']/span")).click();
		//driver.findElement(By.xpath("//input[@id='id']")).sendKeys("usbuyer@yopmail.com");
		Common.sleep(15000);
		driver.findElement(By.xpath("//input[@id='customerFilter']")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//button[@class='ASM-btn ASM-btn-start-session vAlignTop']")).click();
		
		Common.sleep(5000);
		driver.findElement(By.xpath("//a[@class='cartlink']")).click();
		String cartWebPrice = driver.findElement(By.xpath("//span[@class='price-calculator-cart-summary-subTotalWithoutCoupon']")).getText().toString();
		float cartwebprice = B2CCommon.GetPriceValue(cartWebPrice);
		Assert.assertTrue(cartwebprice==webprice);
		String cartYourPrice = driver.findElement(By.xpath("//span[@class='price-calculator-cart-summary-totalPriceWithTax qa_estimatedTotal_Price']")).getText().toString();
		yourprice = B2CCommon.GetPriceValue(cartYourPrice);
		
		String beforPrice = driver.findElement(By.xpath("//dd[@class='cartDetails-tsPrice']")).getText().toString();
		float inputPrice = B2CCommon.GetPriceValue(beforPrice)-10;
		String InputPrice = inputPrice +"";
		webprice=webprice -10;
		yourprice= yourprice -10;
		//Over write the price
		driver.findElement(By.xpath("//input[@name='price']" )).sendKeys(InputPrice);
		driver.findElement(By.xpath("//select[@name='reasonNum']")).click();
		driver.findElement(By.xpath("//option[@value='2']")).click();
		driver.findElement(By.xpath("//input[@id='reasonText']")).sendKeys("Changeprice");
		driver.findElement(By.xpath("(//input[@value='Update'])[2]")).click();
		//Click request quote
		driver.findElement(By.xpath("//input[@id='quote_button']")).click();
		Common.sleep(1000);
		driver.findElement(By.xpath("//input[@class='checkoutForm-formInput']")).click();
		driver.findElement(By.xpath("//input[@class='checkoutForm-formInput']")).sendKeys("1234567890");
		Common.sleep(2000);
		driver.findElement(By.xpath("//input[@id='submit-quote-button']")).click();
		String quoteNum = driver.findElement(By.xpath("//td[contains(text(),'Quote Number')]/following-sibling::td[1]")).getText().toString();
		System.out.println(quoteNum);
		
		//stop session
		driver.findElement(By.xpath("//*[@id='stopEmulate']/span")).click();
		alertHandle();
		//Input quote number
		Common.sleep(10000);
		driver.findElement(By.xpath("//input[@name='transactionId']")).sendKeys(quoteNum);
		Common.sleep(15000);
		driver.findElement(By.xpath("//input[@name='transactionId']")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//button[@class='ASM-btn ASM-btn-start-session vAlignTop']")).click();
		//Approve the quote
		Common.sleep(10000);
		driver.findElement(By.xpath("//button[@id='quoteStatusChange']")).click();
		driver.findElement(By.xpath("//textarea[@id='quote-comment-textarea-id']")).sendKeys("Approve");
		driver.findElement(By.xpath("//input[@id='tele-quote-approve-btn']")).click();
		Common.sleep(2000);
		//Send email
		driver.findElement(By.xpath("//button[@id='emailQuoteButton']")).click();
		if (Store.equals("AU")) {
			driver.findElement(By.xpath("//textarea[@id='quote-email-to-addresses']")).sendKeys("testau@sharklasers.com");
		}else if (Store.equals("US")) {
			driver.findElement(By.xpath("//textarea[@id='quote-email-to-addresses']")).sendKeys("testus@sharklasers.com");
		}else if (Store.equals("CA")) {
			driver.findElement(By.xpath("//textarea[@id='quote-email-to-addresses']")).sendKeys("testca@sharklasers.com");
		}else if (Store.equals("NZ")) {
			driver.findElement(By.xpath("//textarea[@id='quote-email-to-addresses']")).sendKeys("testnz@sharklasers.com");
		}
		
		driver.findElement(By.xpath("//textarea[@id='quote-email-description']")).sendKeys("aaa");
		driver.findElement(By.xpath("//input[@id='quote-send-email']")).click();
		Common.sleep(2000);
	
		openMail();
		checkDetailHyperLink(Store,quoteNum);
		checkContent(quoteNum,webprice,yourprice);
		
	}
	
	public void addToCartEsupport(PartSalesPage PSPage){
		closePromotion(driver,b2cPage);
		PSPage.partNumber.sendKeys(partNumber);
		Common.sleep(2000);
		Common.javascriptClick(driver, PSPage.partLookUp);
		Common.sleep(3000);
		String partsPrice=driver.findElement(By.xpath("(//span[@class='span-8'])[2]")).getText().toString();
		webprice = B2CCommon.GetPriceValue(partsPrice);
		Common.mouseHover(driver, PSPage.addToCartNew);
		Common.javascriptClick(driver, PSPage.addToCartNew);
		//TODO Add to cart
	}

	public String getEmailHyperLink(String xpathName,String type) {
		String xpath="";
		if(type.equals("image")) {
			xpath = "//img[@alt='" + xpathName+ "']/..";
		}else if (type.equals("text")) {
			xpath = "//div[@id='display_email']//a[contains(text(),'" + xpathName + "')]";
		}
		String hyperLink = driver.findElement(By.xpath(xpath)).getAttribute("href");
		System.out.println(xpathName + "hyperlink is:" + hyperLink);
		return hyperLink;
		
	}
	
	public void alertHandle() {
		try{
			Alert alert = driver.switchTo().alert();
		if(alert != null) {
		alert.accept();
		}
		}catch(Throwable e) {
			System.out.println("Alter not exist");
		}
	}
	
	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath("//a[@id='oo_no_thanks']");
		if (Common.isElementExist(driver, Promotion)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(Promotion)).click().perform();
		}
	}
	
	public void openMail() {
		//TODO Open mail
		driver.get("https://www.guerrillamail.com/inbox");
		Common.sleep(3000);
		WebElement a = driver.findElement(By.xpath("//span[@class='editable button']"));
		Common.javascriptClick(driver, a);
		
		//mailPage.Inbox_EditButton.click();
		Common.sleep(3000);
		driver.findElement(By.xpath("//*[@id='inbox-id']/input")).clear();
		if(Store.equals("AU")) {
			driver.findElement(By.xpath("//*[@id='inbox-id']/input")).sendKeys("testau");
			Dailylog.logInfoDB(3,"AU Email opened successfully.", Store,testName);
		}else if (Store.equals("US")) {
			driver.findElement(By.xpath("//*[@id='inbox-id']/input")).sendKeys("testus");
			Dailylog.logInfoDB(3,"US Email opened successfully.", Store,testName);
		}else if (Store.equals("CA")) {
			driver.findElement(By.xpath("//*[@id='inbox-id']/input")).sendKeys("testca");
			Dailylog.logInfoDB(3,"CA Email opened successfully.", Store,testName);
		}else if (Store.equals("NZ")) {
			driver.findElement(By.xpath("//*[@id='inbox-id']/input")).sendKeys("testnz");
			Dailylog.logInfoDB(3,"NZ Email opened successfully.", Store,testName);
		}
		//mailPage.Inbox_InputEmail.sendKeys("testps");
		Common.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='inbox-id']/button[1]")).click();
		//mailPage.Inbox_SetButton.click();
		Common.sleep(3000);
		if (!Common.isElementExist(driver, By.xpath("//*[contains(text(),'lenovo.orders@lenovo.com ')]")))
		{
			for (int i = 0; i < 6; i++) {
				System.out.println("Email not received, will check 10s later");
				Common.sleep(10000);
				if (Common.isElementExist(driver, By.xpath("//*[contains(text(),'lenovo.orders@lenovo.com ')]")))
					break;
			}
		}
		Common.sleep(12000);
		
		driver.findElement(By.xpath("//tbody[@id='email_list']/tr[1]//td[contains(text(),'lenovo.orders@lenovo.com ')]")).click();
		//mailPage.Email_Subject.click();
		
		driver.findElement(By.xpath("//a[@id='display_images']")).click();
		Common.sleep(5000);
		List <WebElement> emailEle = driver.findElements(By.xpath("//a[contains(@href,'lenovo.com')]"));
		for(WebElement Ele:emailEle) {
			String link = Ele.getAttribute("href");
			System.out.println(link);
		}
	}
	
	public void checkDetailHyperLink(String store,String quoteNum) {
		//TODO check email hyperlink
		String dynamic1="";
		String dynamic2="";
		String dynamic3=this.Store.toLowerCase();
		if (store.equals("US")) {
			//TODO check us hyperlink
			dynamic1="us/en";
			dynamic2="NA_US_2016";
			Dailylog.logInfoDB(4,"Check US hyperlink", Store,testName);
			//Check Facebook
			String Facebook=getEmailHyperLink("Facebook","image");
			Assert.assertTrue(Facebook.equalsIgnoreCase("http://www.facebook.com/lenovous?CID=EDM_"+dynamic2+"_QTCON"));
			//common  link
			//check Lenovo Image
			String LenovoImageLink= getEmailHyperLink("Lenovo","image");
			Assert.assertTrue(LenovoImageLink.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"?CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("1");
			//check click here image
			String clickHere= getEmailHyperLink("Did We Get it Right? Ready to Order? Click Here.","image");
			Assert.assertTrue(clickHere.equals("https://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/my-account/quote/"+ quoteNum+ "?CID=EDM_"+dynamic2+"_QTCON"));
			//Check contact us
			String ContactUS=getEmailHyperLink("Chat","image");
			Assert.assertTrue(ContactUS.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/ordersupport/?menu-id=Order_Support&CID=EDM_"+dynamic2+"_QTCON"));
			//Check Tweet at Us
			String TweetUS=getEmailHyperLink("Tweet at Us","image");
			Assert.assertTrue(TweetUS.equals("https://twitter.com/lenovohelp?CID=EDM_"+dynamic2+"_QTCON"));
			//Check Microsoft Office Suite
			String MOSuite=getEmailHyperLink("Microsoft Office Suite","image");
			Assert.assertTrue(MOSuite.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/search/viacategorytext?q=microsoft%3Aprice-asc%3AfacetAcc-Brand%3ASoftware%3AcategoryCode%3ArootOptionCategory&uq=microsoft&text=microsoft&CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("2");
			//Check Find the perfect accessory
			String accessory=getEmailHyperLink("Find the perfect accessory.","image");
			Assert.assertTrue(accessory.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/accessories-and-upgrade/accessories/c/ACCESSORY?menu-id=Accessories_Upgrades&CID=EDM_"+dynamic2+"_QTCON"));
			//Check Everyone needs one.
			String needsOne=getEmailHyperLink("Everyone needs one.","image");
			Assert.assertTrue(needsOne.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/tablets/c/TABLETS?menu-id=Tablets&CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("3");
			//Check Twitter
			String Twitter=getEmailHyperLink("Twitter","image");
			Assert.assertTrue(Twitter.equalsIgnoreCase("https://twitter.com/lenovous?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Google+
			String Google=getEmailHyperLink("Google+","image");
			Assert.assertTrue(Google.contains("https://plus.google.com/+Lenovo?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check YouTube
			String YouTube=getEmailHyperLink("YouTube","image");
			Assert.assertTrue(YouTube.equals("http://www.youtube.com/lenovovision/?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Pinterest
			String Pinterest=getEmailHyperLink("Pinterest","image");
			Assert.assertTrue(Pinterest.equals("http://www.pinterest.com/lenovous?CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("4");
			//Check Flickr
			String Flickr=getEmailHyperLink("Flickr","image");
			Assert.assertTrue(Flickr.equals("http://www.flickr.com/photos/lenovophotolibrary?CID=EDM_"+dynamic2+"_QTCON")
					|| Flickr.equals("https://www.flickr.com/photos/lenovophotolibrary?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Instagram
			String Instagram=getEmailHyperLink("Instagram","image");
			Assert.assertTrue(Instagram.equals("http://www.instagram.com/lenovous?CID=EDM_"+dynamic2+"_QTCON")
					|| Instagram.equals("https://www.instagram.com/lenovous/?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check text HyperLinks
			//Check MY ACCOUNT
			String myAccount=getEmailHyperLink("MY ACCOUNT","text");
			Assert.assertTrue(myAccount.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/my-account?CID=EDM_"+dynamic2+"_QTCON")
					||myAccount.equals("https://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/my-account?CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("5");
			//Check SUBSCRIBE
			String SUBSCRIBE=getEmailHyperLink("SUBSCRIBE","text");
			Assert.assertTrue(SUBSCRIBE.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/portals/communications/?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check MANAGE PREFERENCES
			String MANAGEPREFERENCES=getEmailHyperLink("MANAGE PREFERENCES","text");
			Assert.assertTrue(MANAGEPREFERENCES.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/my-account?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check 1-855-253-6686
			String Num=getEmailHyperLink("1-855-253-6686","text");
			Assert.assertTrue(Num.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsalestel:1-855-253-6686"));
			
			//Check LIVE CHAT
			String liveChat=getEmailHyperLink("LIVE CHAT","text");
			Assert.assertTrue(liveChat.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/ordersupport/?menu-id=Order_Support&CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Support image
			String Support=getEmailHyperLink("Support","image");
			Assert.assertTrue(Support.equalsIgnoreCase("http://support.lenovo.com/en_US/?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Laptops image
			String Laptops=getEmailHyperLink("Laptops","image");
			Assert.assertTrue(Laptops.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/laptops/c/LAPTOPS?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Tablets image
			String Tablets=getEmailHyperLink("Tablets","image");
			Assert.assertTrue(Tablets.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/tablets/c/TABLETS?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Desktops image
			String Desktops=getEmailHyperLink("Desktops","image");
			Assert.assertTrue(Desktops.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/desktops-and-all-in-ones/c/DESKTOPS?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Warranty Services
			String Warranty=getEmailHyperLink("Warranty Services","text");
			Assert.assertTrue(Warranty.equals("http://services.lenovo.com/irj/portal/anonymous?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Premium Support
			String Premium=getEmailHyperLink("Premium Support","text");
			Assert.assertTrue(Premium.equals("https://lenovopfs.suth.com/?CID=EDM_"+dynamic2+"_ORDCON"));
			System.out.println("=====================");
			System.out.println("6");
			//Check Damage Protection
			String Damage=getEmailHyperLink("Damage Protection","text");
			Assert.assertTrue(Damage.equals("http://services.lenovo.com/irj/portal/anonymous?CID=EDM_"+dynamic2+"_ORDCON"));
			System.out.println("=====================");
			System.out.println("7");
			//Check Tablet Services
			String Tablet=getEmailHyperLink("Tablet Services","text");
			Assert.assertTrue(Tablet.equals("http://services.lenovo.com/irj/portal/anonymous?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Business
			String Business=getEmailHyperLink("Business","text");
			Assert.assertTrue(Business.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/landingpage/small-business/?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Home Office
			String Home=getEmailHyperLink("Home Office","text");
			Assert.assertTrue(Home.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Student
			String Student=getEmailHyperLink("Student","text");
			Assert.assertTrue(Student.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/education/gatekeeper/showpage?toggle=RegistrationGatekeeper&CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Entertainment
			String Entertainment=getEmailHyperLink("Entertainment","text");
			Assert.assertTrue(Entertainment.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Windows Tablet
			String Windows=getEmailHyperLink("Windows Tablet","text");
			Assert.assertTrue(Windows.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/tablets/windows-tablets/c/WINDOWSTABLETS?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Android Tablet
			String Android=getEmailHyperLink("Android Tablet","text");
			Assert.assertTrue(Android.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/tablets/android-tablets/c/ANDROIDTABLETS?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Two-in-Ones
			String twoINOne=getEmailHyperLink("Two-in-Ones","text");
			Assert.assertTrue(twoINOne.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/tablets/convertibles/c/CONVERTIBLES?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check All-in-Ones
			String allInOne=getEmailHyperLink("All-in-Ones","text");
			Assert.assertTrue( allInOne.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/desktops-and-all-in-ones/all-in-one-pcs/c/AIO?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Compacts
			String Compacts=getEmailHyperLink("Compacts","text");
			Assert.assertTrue(Compacts.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/desktops?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Small Form Factors
			String small=getEmailHyperLink("Small Form Factors","text");
			Assert.assertTrue(small.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/desktops-and-all-in-ones/small-form-factor-pcs/c/SFF?CID=EDM_"+dynamic2+"_ORDCON"));
			System.out.println("=====================");
			System.out.println("8");
			//Check Towers
			String Towers=getEmailHyperLink("Towers","text");
			Assert.assertTrue(Towers.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/desktops-and-all-in-ones/computer-towers/c/COMPUTERTOWER?CID=EDM_"+dynamic2+"_ORDCON"));
			System.out.println("=====================");
			System.out.println("9");
			//Check here
			//String here=getEmailHyperLink("here","text");
			//Assert.assertTrue(here.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/legal/sales-agreement/?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Privacy Policy
			String Privacy=getEmailHyperLink("Privacy Policy","text");
			Assert.assertTrue(Privacy.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/privacy?CID=EDM_"+dynamic2+"_QTCON"));
			
		}else if (store.equals("CA")) {
			//TODO check CA hyperlink
			Dailylog.logInfoDB(4,"Check CA hyperlink", Store,testName);
			dynamic1="ca/en";
			dynamic2="NA_CA_EN_2016";
			//Check Facebook
			String Facebook=getEmailHyperLink("Facebook","image");
			Assert.assertTrue(Facebook.equalsIgnoreCase("https://www.facebook.com/lenovous?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check YouTube
			String YouTube=getEmailHyperLink("YouTube","image");
			Assert.assertTrue(YouTube.equals("https://www.youtube.com/user/LenovoVision%E2%80%8E?CID=EDM_NA_CA_EN_2016_QTCON"));
			System.out.println("=====================");
			System.out.println("01");
			//Assert.assertTrue(YouTube.contains("www.youtube.com/user/LenovoVision")&&YouTube.contains("?CID=EDM_"+dynamic2+"_QTCON"));
			String Pinterest=getEmailHyperLink("Pinterest","image");
			Assert.assertTrue(Pinterest.contains("https://www.pinterest.com/lenovous/?CID=EDM_NA_CA_EN_2016_QTCON%E2%80%8E"));
			System.out.println("=====================");
			System.out.println("02");
			//Check SUPPORT
			String SUPPORT=getEmailHyperLink("SUPPORT","text");
			Assert.assertTrue(SUPPORT.equals("http://pre-c-hybris.lenovo.com/ca/en/portals/communications/?CID=EDM_NA_CA_EN_2016_QTCON"));
			
			System.out.println("=====================");
			System.out.println("03");
			
			//check Lenovo Image
			String LenovoImageLink= getEmailHyperLink("Lenovo","image");
			Assert.assertTrue(LenovoImageLink.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"?CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("1");
			//check click here image
			String clickHere= getEmailHyperLink("Did We Get it Right? Ready to Order? Click Here.","image");
			Assert.assertTrue(clickHere.equals("https://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/my-account/quote/"+ quoteNum+ "?CID=EDM_"+dynamic2+"_QTCON"));
			//Check contact us
			String ContactUS=getEmailHyperLink("Chat","image");
			Assert.assertTrue(ContactUS.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/ordersupport/?menu-id=Order_Support&CID=EDM_"+dynamic2+"_QTCON"));
			//Check Tweet at Us
			String TweetUS=getEmailHyperLink("Tweet at Us","image");
			Assert.assertTrue(TweetUS.equals("https://twitter.com/lenovohelp?CID=EDM_"+dynamic2+"_QTCON"));
			//Check Microsoft Office Suite
			String MOSuite=getEmailHyperLink("Microsoft Office Suite","image");
			Assert.assertTrue(MOSuite.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/search/viacategorytext?q=microsoft%3Aprice-asc%3AfacetAcc-Brand%3ASoftware%3AcategoryCode%3ArootOptionCategory&uq=microsoft&text=microsoft&CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("2");
			//Check Find the perfect accessory
			String accessory=getEmailHyperLink("Find the perfect accessory.","image");
			Assert.assertTrue(accessory.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/accessories-and-upgrade/accessories/c/ACCESSORY?menu-id=Accessories_Upgrades&CID=EDM_"+dynamic2+"_QTCON"));
			//Check Everyone needs one.
			String needsOne=getEmailHyperLink("Everyone needs one.","image");
			Assert.assertTrue(needsOne.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/tablets/c/TABLETS?menu-id=Tablets&CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("3");
			//Check Twitter
			String Twitter=getEmailHyperLink("Twitter","image");
			Assert.assertTrue(Twitter.equalsIgnoreCase("https://twitter.com/lenovous?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Google+
			String Google=getEmailHyperLink("Google+","image");
			Assert.assertTrue(Google.contains("https://plus.google.com/+Lenovo?CID=EDM_"+dynamic2+"_QTCON"));
			
			System.out.println("=====================");
			System.out.println("4");
			//Check Flickr
			String Flickr=getEmailHyperLink("Flickr","image");
			Assert.assertTrue(Flickr.equals("http://www.flickr.com/photos/lenovophotolibrary?CID=EDM_"+dynamic2+"_QTCON")
					|| Flickr.equals("https://www.flickr.com/photos/lenovophotolibrary?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Instagram
			String Instagram=getEmailHyperLink("Instagram","image");
			Assert.assertTrue(Instagram.equals("http://www.instagram.com/lenovous?CID=EDM_"+dynamic2+"_QTCON")
					|| Instagram.equals("https://www.instagram.com/lenovous/?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check text HyperLinks
			//Check MY ACCOUNT
			String myAccount=getEmailHyperLink("MY ACCOUNT","text");
			Assert.assertTrue(myAccount.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/my-account?CID=EDM_"+dynamic2+"_QTCON")
					||myAccount.equals("https://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/my-account?CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("5");

			//Check MANAGE PREFERENCES
			String MANAGEPREFERENCES=getEmailHyperLink("MANAGE PREFERENCES","text");
			//Assert.assertTrue(MANAGEPREFERENCES.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/my-account?CID=EDM_"+dynamic2+"_QTCON"));
			Assert.assertTrue(MANAGEPREFERENCES.equals("https://pre-c-hybris.lenovo.com/ca/en/capartsales/my-account?CID=EDM_NA_CA_EN_2016_QTCON"));

			//Check LIVE CHAT
			String liveChat=getEmailHyperLink("LIVE CHAT","text");
			//Assert.assertTrue(liveChat.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/ordersupport/?menu-id=Order_Support&CID=EDM_"+dynamic2+"_QTCON"));
			Assert.assertTrue(liveChat.equals("http://pre-c-hybris.lenovo.com/ca/en/ordersupport/?menu-id=Order_Support&CID=EDM_NA_CA_EN_2016_QTCON"));
			
			//Check Support image
			String Support=getEmailHyperLink("Support","image");
			//Assert.assertTrue(Support.equalsIgnoreCase("http://support.lenovo.com/en_US/?CID=EDM_"+dynamic2+"_ORDCON"));
			Assert.assertTrue(Support.equalsIgnoreCase("http://support.lenovo.com/ca/fr/?menu-id=laptop,_desktop,_tablet,_workstation_and_thinkserver_support?CID=EDM_NA_CA_EN_2016_QTCON"));
			//Check About Lenovo image
			String Tablets=getEmailHyperLink("About Lenovo","image");
			Assert.assertTrue(Tablets.equals("http://pre-c-hybris.lenovo.com/ca/en?CID=EDM_NA_CA_EN_2016_QTCON"));
			System.out.println("=====================");
			System.out.println("5.5");
			//Check Our Company
			String Warranty=getEmailHyperLink("Our Company","text");
			Assert.assertTrue(Warranty.equals("http://pre-c-hybris.lenovo.com/ca/en/lenovo/?CID=EDM_NA_CA_EN_2016_QTCON"));
			System.out.println("=====================");
			System.out.println("5.6");
			//Check Community
			String Premium=getEmailHyperLink("Community","text");
			Assert.assertTrue(Premium.equals("http://www.lenovoconnection.com/?CID=EDM_NA_CA_EN_2016_QTCON"));
			System.out.println("=====================");
			System.out.println("6");
			//Check News
			String Damage=getEmailHyperLink("News","text");
			Assert.assertTrue(Damage.equals("http://news.lenovo.com/news?CID=EDM_NA_CA_EN_2016_QTCON"));

			//Check Social Responsibility
			String Tablet=getEmailHyperLink("Social Responsibility","text");
			Assert.assertTrue(Tablet.equals("http://www.lenovo.com/lenovo/ca/en/index.shtml#social-responsibility?CID=EDM_NA_CA_EN_2016_QTCON"));
			
			//Check How To's
			String howTo = driver.findElement(By.xpath("//div[@id='display_email']//a[contains(text(),\"How To's\")]")).getAttribute("href");
			Assert.assertTrue(howTo.equals("http://pre-c-hybris.lenovo.com/ca/en/?CID=EDM_NA_CA_EN_2016_QTCON"));
			System.out.println("=====================");
			System.out.println("7");
			//Check Solutions Center
			String Home=getEmailHyperLink("Solutions Center","text");
			Assert.assertTrue(Home.equals("http://pre-c-hybris.lenovo.com/ca/en/lenovodiagnosticsolutions?CID=EDM_NA_CA_EN_2016_QTCON"));
			
			//Check Windows
			String Student=getEmailHyperLink("Windows","text");
			Assert.assertTrue(Student.equals("http://pre-c-hybris.lenovo.com/ca/en/windows-support?CID=EDM_NA_CA_EN_2016_QTCON"));
			
			//Check Warranty
			String Entertainment=getEmailHyperLink("Warranty","text");
			Assert.assertTrue(Entertainment.equals("https://pre-c-hybris.lenovo.com/ca/en/warrantylookup?CID=EDM_NA_CA_EN_2016_QTCON"));
			
			//Check Resources image
			String Resources=getEmailHyperLink("Resources","image");
			Assert.assertTrue(Resources.equals("http://pre-c-hybris.lenovo.com/ca/en/?menu-id=laptop,_desktop,_tablet,_workstation_and_thinkserver_support/?CID=EDM_NA_CA_EN_2016_QTCON"));
			
			//Check Forums
			String Windows=getEmailHyperLink("Forums","text");
			Assert.assertTrue(Windows.equals("https://forums.lenovo.com/?CID=EDM_NA_CA_EN_2016_QTCON"));
			
			//Check Blogs
			String Android=getEmailHyperLink("Blogs","text");
			Assert.assertTrue(Android.equals("http://blog.lenovo.com/?CID=EDM_NA_CA_EN_2016_QTCON"));
			
			//Check Trainings
			String twoINOne=getEmailHyperLink("Trainings","text");
			Assert.assertTrue(twoINOne.equals("http://training.lenovo.partner-management.com/?CID=EDM_NA_CA_EN_2016_QTCON"));
			//Check here
			String here=getEmailHyperLink("here","text");
			Assert.assertTrue(here.equals("http://pre-c-hybris.lenovo.com/ca/en/capartsales/legal/sales-agreement?CID=EDM_NA_CA_EN_2016_QTCON"));
			
			//Check Privacy Policy
			String Privacy=getEmailHyperLink("Privacy Policy","text");
			Assert.assertTrue(Privacy.equals("http://pre-c-hybris.lenovo.com/ca/en/capartsales/privacy?CID=EDM_NA_CA_EN_2016_QTCON"));
			
		}else if (store.equals("NZ")) {
			dynamic1="nz/en";
			dynamic2="NZ_2017";
			//TODO check NZ hyperlink
			Dailylog.logInfoDB(4,"Check NZ hyperlink", Store,testName);
			//Check Facebook
			String Facebook=getEmailHyperLink("Facebook","image");
			Assert.assertTrue(Facebook.equalsIgnoreCase("http://facebook.com/LenovoANZ?CID=EDM_NZ_2017_QTCON"));
			
			//Check YouTube
			String YouTube=getEmailHyperLink("YouTube","image");
			Assert.assertTrue(YouTube.equals("https://www.youtube.com/user/LenovoANZ"));
			System.out.println("=====================");
			System.out.println("0.9");
			//common  link
			//check Lenovo Image
			String LenovoImageLink= getEmailHyperLink("Lenovo","image");
			Assert.assertTrue(LenovoImageLink.equals("http://pre-c-hybris.lenovo.com/nz/en?CID=EDM_NZ_2017_QTCON"));
			System.out.println("=====================");
			System.out.println("1");
			//check Here is what you requested
			String clickHere= getEmailHyperLink("Here is what you requested","image");
			Assert.assertTrue(clickHere.equals("https://pre-c-hybris.lenovo.com/nz/en/nzpartsales/my-account?CID=EDM_NZ_2017_QTCON"));
			//Check contact us
			String ContactUS=getEmailHyperLink("Chat","image");
			Assert.assertTrue(ContactUS.equals("http://pre-c-hybris.lenovo.com/nz/en/#r=directchat&qu=lenovo-anz-queue-nz&appId=LENOVO_APP01_01?CID=EDM_NZ_2017_QTCON"));
			//Check Twitter
			String TweetUS=getEmailHyperLink("Twitter","image");
			Assert.assertTrue(TweetUS.equals("https://twitter.com/lenovoANZ"));
			//Check You may also like these guys
			String MOSuite=getEmailHyperLink("You may also like these guys","image");
			Assert.assertTrue(MOSuite.equals("http://pre-c-hybris.lenovo.com/nz/en?CID=EDM_NZ_2017_QTCON"));
			System.out.println("=====================");
			System.out.println("2");
			//Check Everyone needs one. Everyone needs tablet.
			String needsOne=getEmailHyperLink("Everyone needs one. Everyone needs tablet.","image");
			Assert.assertTrue(needsOne.equals("http://pre-c-hybris.lenovo.com/nz/en/tablets/c/TABLETS?CID=EDM_NZ_2017_QTCON"));
			System.out.println("=====================");
			System.out.println("3");
			
			//Check Monitors
			String Monitors=getEmailHyperLink("Monitors","image");
			Assert.assertTrue(Monitors.equals("http://pre-c-hybris.lenovo.com/nz/en/accessories/monitors/c/Monitors?CID=EDM_NZ_2017_QTCON"));
			
			//Check Find the perfect accessory.
			String findAcc=getEmailHyperLink("Find the perfect accessory.","image");
			Assert.assertTrue(findAcc.equals("http://pre-c-hybris.lenovo.com/nz/en/accessories/?CID=EDM_NZ_2017_QTCON"));
			
			//Check Twitter
			String Twitter=getEmailHyperLink("Twitter","image");
			Assert.assertTrue(Twitter.equalsIgnoreCase("https://twitter.com/lenovoANZ"));
			
			//Check Google+
			String Google=getEmailHyperLink("Google+","image");
			Assert.assertTrue(Google.contains("http://plus.google.com/+LenovoAuNZ/posts"));

			//Check Pinterest
//			String Pinterest=getEmailHyperLink("Pinterest","image");
//			Assert.assertTrue(Pinterest.equals("http://www.pinterest.com/lenovous?CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("4");
			//Check Flickr
			String Flickr=getEmailHyperLink("Flickr","image");
			Assert.assertTrue(Flickr.equals("https://www.flickr.com/photos/lenovophotolibrary"));

			//Check text HyperLinks
			//Check MY ACCOUNT
			String myAccount=getEmailHyperLink("MY ACCOUNT","text");
			Assert.assertTrue(myAccount.equals("http://pre-c-hybris.lenovo.com/nz/en/nzpartsales/my-account/?CID=EDM_NZ_2017_QTCON"));
			System.out.println("=====================");
			System.out.println("5");
			//Check SUBSCRIBE
			String SUBSCRIBE=getEmailHyperLink("SUBSCRIBE","text");
			Assert.assertTrue(SUBSCRIBE.equals("http://pre-c-hybris.lenovo.com/nz/en/email-signup/?CID=EDM_NZ_2017_QTCON"));
			//Check LIVE CHAT
			String liveChat=getEmailHyperLink("LIVE CHAT","text");
			Assert.assertTrue(liveChat.equals("http://pre-c-hybris.lenovo.com/nz/en/#r=directchat&qu=lenovo-anz-queue-au&appId=LENOVO_APP01_01?CID=EDM_NZ_2017_QTCON"));
			
			//Check Support image
			String Support=getEmailHyperLink("Support","image");
			Assert.assertTrue(Support.equalsIgnoreCase("http://support.lenovo.com/en/au/?CID=EDM_NZ_2017_QTCON"));
			
			//Check Laptops image
			String Laptops=getEmailHyperLink("Laptops","image");
			Assert.assertTrue(Laptops.equals("http://pre-c-hybris.lenovo.com/nz/en/laptops/c/LAPTOPS?CID=EDM_NZ_2017_QTCON"));
			
			//Check Tablets image
			String Tablets=getEmailHyperLink("Tablets","image");
			Assert.assertTrue(Tablets.equals("http://pre-c-hybris.lenovo.com/nz/en/tablets/c/TABLETS/?CID=EDM_NZ_2017_QTCON"));
			
			//Check Desktops image
			String Desktops=getEmailHyperLink("Desktops","image");
			Assert.assertTrue(Desktops.equals("http://pre-c-hybris.lenovo.com/nz/en/desktops-and-all-in-ones/c/DESKTOPS?CID=EDM_NZ_2017_QTCON"));
			
			//Check Warranty Services
			String Warranty=getEmailHyperLink("Warranty Services","text");
			Assert.assertTrue(Warranty.equals("http://pre-c-hybris.lenovo.com/nz/en/services-warranty/?CID=EDM_NZ_2017_QTCON"));
			
			//Check Solution Centre
			String Premium=getEmailHyperLink("Solution Centre","text");
			Assert.assertTrue(Premium.equals("https://support.lenovo.com/nz/en/lenovodiagnosticsolutions?CID=EDM_NZ_2017_QTCON"));
			System.out.println("=====================");
			System.out.println("6");
			//Check Warranty Status
			String Damage=getEmailHyperLink("Warranty Status","text");
			Assert.assertTrue(Damage.equals("https://support.lenovo.com/nz/en/warrantylookup?CID=EDM_NZ_2017_QTCON"));
			System.out.println("=====================");
			System.out.println("7");
			
			//Check Business
			String Business=getEmailHyperLink("Business","text");
			Assert.assertTrue(Business.equals("http://pre-c-hybris.lenovo.com/nz/en/business-store/?CID=EDM_NZ_2017_QTCON"));
			
			//Check Home & Entertainment
			String Home=getEmailHyperLink("Home & Entertainment","text");
			Assert.assertTrue(Home.equals("http://pre-c-hybris.lenovo.com/nz/en/deals/current-offers/laptop-for-home-deals/c/DEALS1?CID=EDM_NZ_2017_QTCON"));
			
			//Check Student
			String Student=getEmailHyperLink("Student","text");
			Assert.assertTrue(Student.equals("http://pre-c-hybris.lenovo.com/nz/en/education/?CID=EDM_NZ_2017_QTCON"));
			
			//Check Gaming
			String Entertainment=getEmailHyperLink("Gaming","text");
			Assert.assertTrue(Entertainment.equals("http://pre-c-hybris.lenovo.com/nz/en/gaming/laptops/?CID=EDM_NZ_2017_QTCON"));
			
			//Check Windows Tablet
			String Windows=getEmailHyperLink("Windows Tablet","text");
			Assert.assertTrue(Windows.equals("http://pre-c-hybris.lenovo.com/nz/en/tablets/windows-tablets/c/WINDOWSTABLETS/?CID=EDM_NZ_2017_QTCON"));
			
			//Check Android Tablet
			String Android=getEmailHyperLink("Android Tablet","text");
			Assert.assertTrue(Android.equals("http://pre-c-hybris.lenovo.com/nz/en/tablets/android-tablets/c/ANDROIDTABLETS/?CID=EDM_NZ_2017_QTCON"));
			
			//Check Convertibles
			String twoINOne=getEmailHyperLink("Convertibles","text");
			Assert.assertTrue(twoINOne.equals("http://pre-c-hybris.lenovo.com/nz/en/tablets/convertibles/c/CONVERTIBLES/?CID=EDM_NZ_2017_QTCON"));
			
			//Check All-in-Ones
			String allInOne=getEmailHyperLink("All-in-Ones","text");
			Assert.assertTrue( allInOne.equals("http://pre-c-hybris.lenovo.com/nz/en/desktops-and-all-in-ones/all-in-one-pcs/c/AIO?CID=EDM_NZ_2017_QTCON"));
			
			//Check Tinys
			String Compacts=getEmailHyperLink("Tinys","text");
			Assert.assertTrue(Compacts.equals("http://pre-c-hybris.lenovo.com/nz/en/desktops-and-all-in-ones/mini-computers/c/TINY?CID=EDM_NZ_2017_QTCON"));
			
			//Check Small Form Factors
			String small=getEmailHyperLink("Small Form Factors","text");
			Assert.assertTrue(small.equals("http://pre-c-hybris.lenovo.com/nz/en/desktops-and-all-in-ones/small-form-factor-pcs/c/SFF?CID=EDM_NZ_2017_QTCON"));
			System.out.println("=====================");
			System.out.println("8");
			//Check Towers
			String Towers=getEmailHyperLink("Towers","text");
			Assert.assertTrue(Towers.equals("http://pre-c-hybris.lenovo.com/nz/en/desktops-and-all-in-ones/computer-towers/c/COMPUTERTOWER?CID=EDM_NZ_2017_QTCON"));
			System.out.println("=====================");
			System.out.println("9");
			//Check here
			//String here=getEmailHyperLink("here","text");
			//Assert.assertTrue(here.equals("http://pre-c-hybris.lenovo.com/nz/en/legal/New-Products-and-Warranty-Services/?CID=EDM_NZ_2017_QTCON"));
			
			//Check Privacy Policy
			String Privacy=getEmailHyperLink("Privacy Policy","text");
			Assert.assertTrue(Privacy.equals("http://www.lenovo.com/privacy/nz/en/?CID=EDM_NZ_2017_QTCON"));
				
		}else if (store.equals("AU")) {
			//TODO chekc AU hyperlink
			Dailylog.logInfoDB(4,"Check AU hyperlink", Store,testName);
			dynamic1="au/en";
			dynamic2="AU_2017";
			//check Lenovo Image
			String LenovoImageLink= getEmailHyperLink("Lenovo","image");
			Assert.assertTrue(LenovoImageLink.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"?CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("1");
			//check click here image
			//String clickHere= getEmailHyperLink("We've got it","image");
			String clickHere = driver.findElement(By.xpath("//img[@alt=\"We've got it\"]/..")).getAttribute("href");
			Assert.assertTrue(clickHere.equals("http://pre-c-hybris.lenovo.com/au/en?CID=EDM_AU_2017_QTCON"));
			System.out.println("=====================");
			System.out.println("11");
			//Check contact us
			String ContactUS=getEmailHyperLink("Chat","image");
			//Assert.assertTrue(ContactUS.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/ordersupport/?menu-id=Order_Support&CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("12");
			//Check Tweet at Us
			String TweetUS=getEmailHyperLink("Tweet at Us","image");
			Assert.assertTrue(TweetUS.equals("https://twitter.com/lenovohelp?CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("13");
			//Check Microsoft Office Suite
			String MOSuite=getEmailHyperLink("Microsoft Office Suite","image");
			Assert.assertTrue(MOSuite.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/search/viacategorytext?q=microsoft%3Aprice-asc%3AfacetAcc-Brand%3ASoftware%3AcategoryCode%3ArootOptionCategory&uq=microsoft&text=microsoft&CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("2");
			//Check Find the perfect accessory
			String accessory=getEmailHyperLink("Find the perfect accessory.","image");
			Assert.assertTrue(accessory.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/accessories-and-upgrade/accessories/c/ACCESSORY?menu-id=Accessories_Upgrades&CID=EDM_"+dynamic2+"_QTCON"));
			//Check Everyone needs one.
			String needsOne=getEmailHyperLink("Everyone needs one.","image");
			Assert.assertTrue(needsOne.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/tablets/c/TABLETS?menu-id=Tablets&CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("3");
			//Check Twitter
			String Twitter=getEmailHyperLink("Twitter","image");
			Assert.assertTrue(Twitter.equalsIgnoreCase("https://twitter.com/lenovous?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Google+
			String Google=getEmailHyperLink("Google+","image");
			Assert.assertTrue(Google.contains("https://plus.google.com/+Lenovo?CID=EDM_"+dynamic2+"_QTCON"));
			
		//	//Check YouTube
		//	String YouTube=getEmailHyperLink("YouTube","image");
		//	Assert.assertTrue(YouTube.equals("http://www.youtube.com/lenovovision/?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Pinterest
//			String Pinterest=getEmailHyperLink("Pinterest","image");
//			Assert.assertTrue(Pinterest.equals("http://www.pinterest.com/lenovous?CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("4");
			//Check Flickr
			String Flickr=getEmailHyperLink("Flickr","image");
			Assert.assertTrue(Flickr.equals("http://www.flickr.com/photos/lenovophotolibrary?CID=EDM_"+dynamic2+"_QTCON")
					|| Flickr.equals("https://www.flickr.com/photos/lenovophotolibrary?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Instagram
			String Instagram=getEmailHyperLink("Instagram","image");
			Assert.assertTrue(Instagram.equals("http://www.instagram.com/lenovous?CID=EDM_"+dynamic2+"_QTCON")
					|| Instagram.equals("https://www.instagram.com/lenovous/?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check text HyperLinks
			//Check MY ACCOUNT
			String myAccount=getEmailHyperLink("MY ACCOUNT","text");
			Assert.assertTrue(myAccount.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/my-account?CID=EDM_"+dynamic2+"_QTCON")
					||myAccount.equals("https://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/my-account?CID=EDM_"+dynamic2+"_QTCON"));
			System.out.println("=====================");
			System.out.println("5");
		/*	//Check SUBSCRIBE
			String SUBSCRIBE=getEmailHyperLink("SUBSCRIBE","text");
			Assert.assertTrue(SUBSCRIBE.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/portals/communications/?CID=EDM_"+dynamic2+"_QTCON"));
			*/ //for us
			
			//Check MANAGEPREFERENCES
			String MANAGEPREFERENCES=getEmailHyperLink("MANAGEPREFERENCES","text");
			Assert.assertTrue(MANAGEPREFERENCES.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/my-account?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check 1-855-253-6686
			String Num=getEmailHyperLink("1-855-253-6686","text");
			Assert.assertTrue(Num.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsalestel:1-855-253-6686"));
			
			//Check LIVE CHAT
			String liveChat=getEmailHyperLink("LIVE CHAT","text");
			Assert.assertTrue(liveChat.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/ordersupport/?menu-id=Order_Support&CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Support image
			String Support=getEmailHyperLink("Support","image");
			Assert.assertTrue(Support.equalsIgnoreCase("http://support.lenovo.com/en_US/?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Laptops image
			String Laptops=getEmailHyperLink("Laptops","image");
			Assert.assertTrue(Laptops.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/laptops/c/LAPTOPS?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Tablets image
			String Tablets=getEmailHyperLink("Tablets","image");
			Assert.assertTrue(Tablets.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/tablets/c/TABLETS?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Desktops image
			String Desktops=getEmailHyperLink("Desktops","image");
			Assert.assertTrue(Desktops.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/desktops-and-all-in-ones/c/DESKTOPS?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Warranty Services
			String Warranty=getEmailHyperLink("Warranty Services","text");
			Assert.assertTrue(Warranty.equals("http://services.lenovo.com/irj/portal/anonymous?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Premium Support
			String Premium=getEmailHyperLink("Premium Support","text");
			Assert.assertTrue(Premium.equals("https://lenovopfs.suth.com/?CID=EDM_"+dynamic2+"_ORDCON"));
			System.out.println("=====================");
			System.out.println("6");
			//Check Damage Protection
			String Damage=getEmailHyperLink("Damage Protection","text");
			Assert.assertTrue(Damage.equals("http://services.lenovo.com/irj/portal/anonymous?CID=EDM_"+dynamic2+"_ORDCON"));
			System.out.println("=====================");
			System.out.println("7");
			//Check Tablet Services
			String Tablet=getEmailHyperLink("Tablet Services","text");
			Assert.assertTrue(Tablet.equals("http://services.lenovo.com/irj/portal/anonymous?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Business
			String Business=getEmailHyperLink("Business","text");
			Assert.assertTrue(Business.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/landingpage/small-business/?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Home Office
			String Home=getEmailHyperLink("Home Office","text");
			Assert.assertTrue(Home.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Student
			String Student=getEmailHyperLink("Student","text");
			Assert.assertTrue(Student.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/education/gatekeeper/showpage?toggle=RegistrationGatekeeper&CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Entertainment
			String Entertainment=getEmailHyperLink("Entertainment","text");
			Assert.assertTrue(Entertainment.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Windows Tablet
			String Windows=getEmailHyperLink("Windows Tablet","text");
			Assert.assertTrue(Windows.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/tablets/windows-tablets/c/WINDOWSTABLETS?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Android Tablet
			String Android=getEmailHyperLink("Android Tablet","text");
			Assert.assertTrue(Android.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/tablets/android-tablets/c/ANDROIDTABLETS?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Two-in-Ones
			String twoINOne=getEmailHyperLink("Two-in-Ones","text");
			Assert.assertTrue(twoINOne.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/tablets/convertibles/c/CONVERTIBLES?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check All-in-Ones
			String allInOne=getEmailHyperLink("All-in-Ones","text");
			Assert.assertTrue( allInOne.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/desktops-and-all-in-ones/all-in-one-pcs/c/AIO?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Compacts
			String Compacts=getEmailHyperLink("Compacts","text");
			Assert.assertTrue(Compacts.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/desktops?CID=EDM_"+dynamic2+"_ORDCON"));
			
			//Check Small Form Factors
			String small=getEmailHyperLink("Small Form Factors","text");
			Assert.assertTrue(small.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/desktops-and-all-in-ones/small-form-factor-pcs/c/SFF?CID=EDM_"+dynamic2+"_ORDCON"));
			System.out.println("=====================");
			System.out.println("8");
			//Check Towers
			String Towers=getEmailHyperLink("Towers","text");
			Assert.assertTrue(Towers.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/desktops-and-all-in-ones/computer-towers/c/COMPUTERTOWER?CID=EDM_"+dynamic2+"_ORDCON"));
			System.out.println("=====================");
			System.out.println("9");
			//Check here
			//String here=getEmailHyperLink("here","text");
			//Assert.assertTrue(here.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/legal/sales-agreement/?CID=EDM_"+dynamic2+"_QTCON"));
			
			//Check Privacy Policy
			String Privacy=getEmailHyperLink("Privacy Policy","text");
			Assert.assertTrue(Privacy.equals("http://pre-c-hybris.lenovo.com/"+dynamic1+"/"+dynamic3+"partsales/privacy?CID=EDM_"+dynamic2+"_QTCON"));

		}

	}

	public void loginSeleCountry() {

		driver.get(testData.B2C.getPartSalesUrl());
		partPage.partSales_SelectCountry.click();	
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(//a[@data-code='"+this.Store.toLowerCase()+"'])[1]")), 15);
		driver.findElement(By.xpath("(//a[@data-code='"+this.Store.toLowerCase()+"'])[1]")).click();
		driver.findElement(By.xpath("(//p[@class='selected-language'])[1]")).click();
		driver.findElement(By.xpath("(//div[@class='language-list']//a[@data-code='en'])[1]")).click();
		Common.sleep(12000);
		
		}

	public void checkContent(String quoteNum,float webprice, float yourprice) {
		//TODO check email contents
		Dailylog.logInfoDB(5,"Check Email contents", Store,testName);
		String quoteNumxpath ="//div[@id='display_email']//td[contains(text(),'Quote Number:')]/../following-sibling::tr[2]/td";
		String quoteNum1 = driver.findElement(By.xpath(quoteNumxpath)).getText().toString();
		System.out.println(quoteNum1);
		System.out.println(quoteNum);
		//Check quote number
		Assert.assertTrue(quoteNum1.equals(quoteNum));
		//Assert.assertTrue(quoteNum==quoteNum1);
		String emaiPartNo= driver.findElement(By.xpath("//td[contains(text(),'Part No:')]")).getText().toString().trim().replace("&nbsp;&nbsp;", "");
		System.out.println(emaiPartNo);
		String emailQty= driver.findElement(By.xpath("//td[contains(text(),'Qty:')]/../following-sibling::tr[1]/td[1]")).getText().toString();
		System.out.println(emailQty);
		String emailPrice = driver.findElement(By.xpath("//td[contains(text(),'Price:')]/../following-sibling::tr[1]/td[1]")).getText().toString();
		System.out.println(emailPrice);
		//check price displayed on email
		String emailSubTotal = driver.findElement(By.xpath("//td[contains(text(),'Sub total:')]/../../../../following-sibling::td[1]//tr[1]")).getText().toString();
		System.out.println(emailSubTotal);
		float emailswebprice=B2CCommon.GetPriceValue(emailSubTotal);
		Assert.assertTrue(webprice==emailswebprice);
		String emailTotal = driver.findElement(By.xpath("//td[contains(text(),'Sub total:')]/../../../../following-sibling::td[1]//tr[2]")).getText().toString();
		System.out.println(emailTotal);
		float emailyourprice= B2CCommon.GetPriceValue(emailTotal);
		Assert.assertTrue(yourprice== emailyourprice);	
	}
}
	
	
