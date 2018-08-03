package TestScript.B2B;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.SuperTestClass;

public class CONTENT316Test extends SuperTestClass {
	private B2BPage b2bPage;
	private HMCPage hmcPage;
	public MailPage mailPage;
	String axXpath = ".//*[contains(text(),'Email to Axway')]";
	String emailToAxway = ".//*[contains(text(),'Create [CTOIssueEmailToAxway]')]";
	String emailToOrder = ".//*[contains(text(),'Email to Order Desk')]";
	String createToOrder = ".//*[contains(text(),'Create [CTOIssueEmailToOrderDesk]')]";
	String inputEmail = "//input[@id='Content/StringEditor[in Content/GenericCondition[CTOIssueEmailToAxway.emailUrl]]_input']";
	String inputEmailOrder ="//input[@id='Content/StringEditor[in Content/GenericCondition[CTOIssueEmailToOrderDesk.emailUrl]]_input']";
	String Punchout_Ariba_URL;
	private B2BPunchoutPage page;
	String B2BCustomer = "PUNCHOUT-316";
	String B2BCustomerGroups = "punchOutCustomer";
	String Code = "PUNCHOUT-316";
	String Domain = "NetworkID";
	String Identity = "PUNCHOUT-316";
	String SharedSecret = "aaa";
	String UserName = "PUNCHOUT-316";
	String Password = "aaa";
	String ProfileName = "PUNCHOUT-316";
	String defaultUnit = "1213286247";
	String defaultDMU = "1213286247";
	String AccessLevel = "1213286247";
	String serviceNumber = "";
	public CONTENT316Test(String store) {
		this.Store = store;
		this.testName = "CONTENT-316";
	}

	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void CONTENT316(ITestContext ctx) {
		try {
			this.prepareTest();
			b2bPage = new B2BPage(driver);
			hmcPage = new HMCPage(driver);
			page = new B2BPunchoutPage(driver);
			String axwayEmail = testData.B2B.getBuyerId();
			Punchout_Ariba_URL = testData.B2B.getHomePageUrl().split("le/")[0] + "nemopunchouttool/ariba";
			// Step~1 : creat CTOIssueEmail to Axway
			configureEmail(axXpath, axwayEmail, emailToAxway,inputEmail);
			Dailylog.logInfoDB(1, "creat CTOIssueEmail to Axway", Store, testName);

			// step-2:creat CTOIssueEmail to Orderdesk
			configureEmail(emailToOrder, axwayEmail, createToOrder,inputEmailOrder);
			Dailylog.logInfoDB(2, "creat CTOIssueEmail to Orderdesk", Store, testName);

			// step-3 create b2b unit
			createB2BUnit();
			Dailylog.logInfoDB(3, "create b2b unit", Store, testName);

			// step-4 GO to hmc->orders,enter quoteid,click search button
			String cardId = driver.findElement(By.xpath(".//*[@id='orderList']/xmp[1]")).getText().split("cart=\"")[1]
					.split("\"")[0];

			// step-5 send request
//			sendRequest(cardId);

			driver.get(testData.HMC.getHomePageUrl());
			checkoutTraceLog(cardId, UserName);
			

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	public void checkoutTraceLog(String CardId, String UserName) throws InterruptedException {
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_Nemo_Punchout.click();
		hmcPage.Home_Punchout_Tracelog.click();

		driver.findElement(
				By.xpath("//select[contains(@id,'[PunchOutTraceLog]_select')]//option[contains(text(),'Protocol')]"))
				.click();

		Thread.sleep(3000);
		driver.findElement(By.xpath(
				"//select[contains(@id,'[PunchOutTraceLog.protocol][1]]_select')]//option[contains(text(),'Oracle')]"))
				.click();
		Dailylog.logInfoDB(5, "CardId:" + CardId, Store, testName);
		driver.findElement(By
				.xpath(".//*[@id='Content/StringEditor[in Content/GenericCondition[PunchOutTraceLog.cartId]]_input']"))
				.sendKeys(CardId);
		hmcPage.Home_Punchout_Tracelog_Search.click();

		// TraceLog result one
		if (Common.checkElementExists(driver, driver.findElement(By.xpath(".//*[@id='Content/StringDisplay[]_span']")),
				10)) {
			Common.doubleClick(driver, driver.findElement(By.xpath(".//*[@id='Content/StringDisplay[]_span']")));
		}

		Assert.assertTrue(driver.findElement(By.xpath("//textarea[contains(@id,'[PunchOutTraceLog.cartXml]]_textarea')]"))
						.getText().contains(CardId));
	}

	
	private void sendRequest(String cardId) throws Exception {
		String url = "https://pre-c-hybris.lenovo.com/le/punchout/cto";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<cXML timestamp=\"2018-03-22T17:10:07 -04:00\" payloadID=\"1521753009405.477812546694736473@sciquest.com\" version=\"1.2.011\">\n"
				+ "	<Header>\n" + "		<From>\n" + "			<Credential domain=\"NetworkID\">\n"
				+ "				<Identity>UMINN</Identity>\n" + "			</Credential>\n" + "		</From>\n"
				+ "		<To>\n" + "			<Credential domain=\"DUNS\">\n" + "				<Identity>Lenovo</Identity>\n"
				+ "			</Credential>\n" + "		</To>\n" + "		<Sender>\n"
				+ "			<Credential domain=\"NetworkID\">\n" + "				<Identity>SciQuest</Identity>\n"
				+ "				<SharedSecret>lenovob2b</SharedSecret>\n" + "			</Credential>\n"
				+ "			<UserAgent>SciQuest</UserAgent>\n" + "		</Sender>\n" + "	</Header>\n"
				+ "	<Request deploymentMode=\"production\">\n" + "		<OrderRequest>\n"
				+ "			<OrderRequestHeader orderDate=\"2018-03-22T17:10:07-0400\" orderID=\"0001549923\" type=\"new\">\n"
				+ "				<Total>\n" + "					<Money currency=\"AUD\">2906.32</Money>\n"
				+ "				</Total>\n" + "				<ShipTo>\n"
				+ "					<Address isoCountryCode=\"AU\" addressID=\"1213286247\">\n"
				+ "						<Name xml:lang=\"en-AU\">University of Minnesota</Name>\n"
				+ "						<PostalAddress name=\"University of Minnesota\">\n"
				+ "							<DeliverTo>Michael Nichols / Michael Nichols</DeliverTo>\n"
				+ "							<DeliverTo>.</DeliverTo>\n"
				+ "							<Street>RM160 CHILDRENS REHAB CTR</Street>\n"
				+ "							<Street>426 CHURCH ST SE MMC 210</Street>\n"
				+ "							<City>MINNEAPOLIS</City>\n" + "							<State>SA</State>\n"
				+ "							<PostalCode>55455</PostalCode>\n"
				+ "							<Country isoCountryCode=\"AU\">AU\n" + "</Country>\n"
				+ "						</PostalAddress>\n"
				+ "						<Email name=\"default\">tangling1@lenovo.com</Email>\n"
				+ "						<Phone name=\"work\">\n" + "							<TelephoneNumber>\n"
				+ "								<CountryCode isoCountryCode=\"AU\">1</CountryCode>\n"
				+ "								<AreaOrCityCode>612</AreaOrCityCode>\n"
				+ "								<Number>16126264251</Number>\n"
				+ "							</TelephoneNumber>\n" + "						</Phone>\n"
				+ "						<Fax name=\"\">\n" + "							<TelephoneNumber>\n"
				+ "								<CountryCode isoCountryCode=\"\"/>\n"
				+ "								<AreaOrCityCode/>\n" + "								<Number/>\n"
				+ "							</TelephoneNumber>\n" + "						</Fax>\n"
				+ "					</Address>\n" + "				</ShipTo>\n" + "				<BillTo>\n"
				+ "					<Address isoCountryCode=\"AU\" addressID=\"1213286247\">\n"
				+ "						<Name xml:lang=\"en-US\">AP University of Minnesota</Name>\n"
				+ "						<PostalAddress name=\"U Market\">\n"
				+ "							<DeliverTo>U Market</DeliverTo>\n"
				+ "							<Street>2901 Talmage Avenue SE</Street>\n"
				+ "							<City>Minneapolis</City>\n" + "							<State>SA</State>\n"
				+ "							<PostalCode>55414</PostalCode>\n"
				+ "							<Country isoCountryCode=\"AU\">AU</Country>\n"
				+ "						</PostalAddress>\n" + "						<Email name=\"\"/>\n"
				+ "						<Phone name=\"AP\">\n" + "							<TelephoneNumber>\n"
				+ "								<CountryCode isoCountryCode=\"1\">1</CountryCode>\n"
				+ "								<AreaOrCityCode>612</AreaOrCityCode>\n"
				+ "								<Number>1612625-1015</Number>\n"
				+ "							</TelephoneNumber>\n" + "						</Phone>\n"
				+ "						<Fax name=\"\">\n" + "							<TelephoneNumber>\n"
				+ "								<CountryCode isoCountryCode=\"\"/>\n"
				+ "								<AreaOrCityCode/>\n" + "								<Number/>\n"
				+ "							</TelephoneNumber>\n" + "						</Fax>\n"
				+ "					</Address>\n" + "				</BillTo>\n"
				+ "				<Contact role=\"User\">\n"
				+ "					<Name xml:lang=\"en-US\">Michael Nichols</Name>\n"
				+ "					<PostalAddress name=\"\">\n" + "						<Street/>\n"
				+ "						<City/>\n" + "						<Country isoCountryCode=\"US\"/>\n"
				+ "					</PostalAddress>\n"
				+ "					<Email name=\"work\">tangling1@lenovo.com</Email>\n"
				+ "					<Phone name=\"\">\n" + "						<TelephoneNumber>\n"
				+ "							<CountryCode isoCountryCode=\"\">1</CountryCode>\n"
				+ "							<AreaOrCityCode>612</AreaOrCityCode>\n"
				+ "							<Number>16126264251</Number>\n" + "						</TelephoneNumber>\n"
				+ "					</Phone>\n" + "				</Contact>\n" + "				<Comments/>\n"
				+ "				<Extrinsic name=\"SOLD TO ID\">1213286247</Extrinsic>\n"
				+ "				<Extrinsic name=\"Payer ID\">1213871427</Extrinsic>\n"
				+ "				<Extrinsic name=\"PO Leasing TAG\">PO</Extrinsic>\n" + "			</OrderRequestHeader>\n"
				+ "			<ItemOut quantity=\"1\" lineNumber=\"1\">\n" + "				<ItemID>\n"
				+ "					<SupplierPartID>10FGCTO1WWENAU0</SupplierPartID>\n"
				+ "					<SupplierPartAuxiliaryID>contract=\"542237399B\" cart=" + cardId
				+ "</SupplierPartAuxiliaryID>\n" + "				</ItemID>\n" + "				<ItemDetail>\n"
				+ "					<UnitPrice>\n" + "						<Money currency=\"USD\">2906.32</Money>\n"
				+ "					</UnitPrice>\n"
				+ "					<Description xml:lang=\"en-US\">ThinkPad X1 Carbon 6G</Description>\n"
				+ "					<UnitOfMeasure>EA</UnitOfMeasure>\n"
				+ "					<Classification domain=\"UNSPSC\">43211503</Classification>\n"
				+ "					<ManufacturerPartID/>\n" + "					<ManufacturerName/>\n"
				+ "					<Extrinsic name=\"Contract Number\"/>\n" + "				</ItemDetail>\n"
				+ "			</ItemOut>\n" + "		</OrderRequest>\n" + "	</Request>\n" + "</cXML>\n" + "\n" + "";

		CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);          
        httpPost.addHeader("Content-Type","text/html;charset=UTF-8");
        StringEntity stringEntity = new StringEntity(xml,"UTF-8");
        stringEntity.setContentEncoding("UTF-8");  
        httpPost.setEntity(stringEntity);
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(final HttpResponse response)
                    throws ClientProtocolException, IOException {//                 
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException(
                            "Unexpected response status: " + status);
                }
            }
        };          
        String responseBody = httpclient.execute(httpPost, responseHandler);
		System.out.println("responseBody---======"+responseBody);
	}
	
	private void createB2BUnit() throws InterruptedException {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		
		checkB2BCustomer(B2BCustomer,B2BCustomerGroups,"B2BCustomer");
		
		checkPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
		
		checkPunchoutProfile(ProfileName,B2BCustomer);
		
		SimulationToolAriba(8,Domain,Identity,SharedSecret,defaultUnit);
		saveOCICart(serviceNumber);
		
	}
	
	public void SimulationToolAriba(int step, String Domain,String Identity,String SharedSecret,String defaultUnit){
		driver.manage().deleteAllCookies();
		driver.get(Punchout_Ariba_URL);
		B2BCommon.punchoutLogin(driver,page);
		System.out.println("Go to aribaUrl, and checkout punchout successfully!");
		driver.findElement(By.xpath("//input[@id='userName']")).clear();
		driver.findElement(By.xpath("//input[@id='userName']")).sendKeys("LIeCommerce");
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("M0C0v0n3L!");
		page.Ariba_DomainTextBox.clear();
		page.Ariba_DomainTextBox.sendKeys(Domain);
		page.Ariba_IdentityTextBox.clear();
		page.Ariba_IdentityTextBox.sendKeys(Identity);
		page.Ariba_SharedSecretTextBox.clear();
		page.Ariba_SharedSecretTextBox.sendKeys(SharedSecret);
		page.Ariba_PunchoutButton.click();
		Common.sleep(1000);
		driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
	}
	
	public void saveOCICart(String partNumber) throws InterruptedException{
		page.B2Bsite_service.click();
		Common.sleep(12000);
		driver.findElement(By.xpath(".//*[@id='addToCartForm"+partNumber+"']/button")).click();
		driver.findElement(By.xpath(".//*[@id='addtoCartModal"+partNumber+"']/aside/div[3]/button")).click();
		
		Thread.sleep(5000);
		driver.findElement(By.xpath((".//*[@id='addtoCartModal"+partNumber+"']/aside/div[3]/a[2]"))).click();
		
		Common.isElementExist(driver, By.id("validateDateformatForCheckout"), 20);
		driver.findElement(By.id("validateDateformatForCheckout")).click();
		
		switchToWindow(0);
		Thread.sleep(5000);
		
	}

	public void checkPunchoutProfile(String ProfileName,String B2BCustomer) throws InterruptedException{
		hmcPage.Home_Punchout_CustomerProfile.click();
		Select selScope =new Select(hmcPage.PunchoutProfile_SelectProfileNameScope);
		selScope.selectByVisibleText("is equal");
		hmcPage.PunchoutProfile_NameSearch.clear();
		hmcPage.PunchoutProfile_NameSearch.sendKeys(ProfileName);
		hmcPage.Contract_searchbutton.click();
		if(Common.checkElementExists(driver, hmcPage.PunchoutProfile_1stSearchedResult, 10)){
			Common.rightClick(driver, hmcPage.PunchoutProfile_1stSearchedResult);
			hmcPage.PunchoutProfile_removeResult.click();
			driver.switchTo().alert().accept();
			System.out.println("Remove Punchout Profile!");
			creatPunchoutProfile(ProfileName,B2BCustomer);
		} else {
			System.out.println("Don't need to remove Punchout Profile!");
			creatPunchoutProfile(ProfileName,B2BCustomer);
		}
	} 
	
	public void creatPunchoutProfile(String ProfileName,String B2BCustomer) throws InterruptedException{
		Thread.sleep(1000);
		Common.rightClick(driver, hmcPage.Home_Punchout_CustomerProfile);
		hmcPage.Home_CreatePunchoutProfile.click();
		System.out.println("Create Punchout Profile!");
		hmcPage.PunchoutProfile_NameInput.clear();
		hmcPage.PunchoutProfile_NameInput.sendKeys(ProfileName);
		AddCustomerForProfile(B2BCustomer);
		if(hmcPage.PunchoutProfile_WhetherActive.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_Active.click();
		}
		if(hmcPage.PunchoutProfile_WhetherActiveCxml.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOxml.click();
		}
		hmcPage.PunchoutProfile_OCITab.click();
		if(hmcPage.PunchoutProfile_WhetherActiveOCI.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOCI.click();
		}
		hmcPage.PunchoutProfile_OracleTab.click();
		if(hmcPage.PunchoutProfile_WhetherActiveOracle.getAttribute("value").equals("false")){
			hmcPage.PunchoutProfile_ActiveOracle.click();
		}
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Profile successfully!");
	}
	
	public void AddCustomerForProfile(String B2BCustomer) throws InterruptedException{
		Common.rightClick(driver, hmcPage.PunchoutProfile_CustomerField);
		hmcPage.PunchoutCredential_AddAribaCredential.click();
		System.out.println("Add Customer For Profile!");
		switchToWindow(1);
		hmcPage.PunchoutProfile_CustomerInput.clear();
		hmcPage.PunchoutProfile_CustomerInput.sendKeys(B2BCustomer);
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='AutocompleteReferenceEditor[in GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		hmcPage.Contract_searchbutton.click();
		driver.findElement(By.xpath(".//*[contains(@id,'ItemDisplay["+B2BCustomer+"]')]")).click();
		driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
		System.out.println("Add Customer For Profile successfully!");
		switchToWindow(0);
	}
	public void switchToWindow(int windowNo) {
		try {
			Thread.sleep(2000);
			ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());		
			driver.switchTo().window(windows.get(windowNo));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void  checkPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password) throws InterruptedException{
		if(!Common.checkElementExists(driver, hmcPage.Home_Punchout_Credential, 10)){
			if(!Common.checkElementExists(driver, hmcPage.Home_Nemo_Punchout, 10)){
				hmcPage.Home_Nemo.click();
				hmcPage.Home_Nemo_Punchout.click();
			} else {
				hmcPage.Home_Nemo_Punchout.click();
			}
		}
		hmcPage.Home_Punchout_Credential.click();
		hmcPage.PunchoutCredential_CustomerSearch.clear();
		hmcPage.PunchoutCredential_CustomerSearch.sendKeys(B2BCustomer);
		String xpath = ".//*[@id='Content/AutocompleteReferenceEditor[in Content/GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']";
		if(Common.isElementExist(driver, By.xpath(xpath))){
			driver.findElement(By.xpath(xpath)).click();
			Common.sleep(1000);
			hmcPage.PunchoutCredential_SearchButton.click();
			if(Common.checkElementExists(driver, hmcPage.PunchoutCredential_1stSearchedResult, 10)){
				Common.doubleClick(driver, hmcPage.PunchoutCredential_1stSearchedResult);
				editPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			} else {
				creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
			}
		} else {
			creatPunchoutCredential(driver,hmcPage,B2BCustomer,Code,Domain,Identity,SharedSecret,UserName,Password);
		}
	}
	
	public void editPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password){
		System.out.println("Edit Punchout Credential!");
		//driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		for(int i=0;i<3;i++){
			if(Common.isElementExist(driver, By.xpath("//div[contains(text(),'PunchOut Credentials')]/../../td[last()]//table//table//table//input"))){
				Common.rightClick(driver, driver.findElement(By.xpath("//div[contains(text(),'PunchOut Credentials')]/../../td[last()]//table//table//table//input")));
				hmcPage.PunchoutCredential_RemoveAribaCredential.click();
				Common.sleep(2000);
				Alert alert =  driver.switchTo().alert();
				alert.accept();
				System.out.println("Remove Ariba/OCI/Oracle Punchout Credential!");
				Common.sleep(8000);
			}
		}	
		addCredentialAriba(Code,Domain,Identity,SharedSecret);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	
	public void addCredentialAriba(String Code,String Domain,String Identity,String SharedSecret ){
		Common.rightClick(driver, hmcPage.PunchoutCredential_AribaCredential);
		hmcPage.PunchoutCredential_AddAribaCredential.click();
		System.out.println("Add Credential Ariba!");
		switchToWindow(1);
		hmcPage.PunchoutAribaCredential_CodeSearchInput.clear();
		hmcPage.PunchoutAribaCredential_CodeSearchInput.sendKeys(Code);
		hmcPage.Contract_searchbutton.click();
		if(!Common.isElementExist(driver, By.xpath(".//*[@id='StringDisplay["+Code+"]_span']"))){
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			switchToWindow(0);
			createCredentialAriba(Code,Domain,Identity,SharedSecret);
		}else{
			driver.findElement(By.xpath(".//*[@id='StringDisplay["+Code+"]_span']")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			System.out.println("Add Credential Ariba successfully!");
			switchToWindow(0);
		}
	}
	
	public void createCredentialAriba(String Code,String Domain,String Identity,String SharedSecret ){
		Common.rightClick(driver, hmcPage.PunchoutCredential_AribaCredential);
		hmcPage.PunchoutCredential_CreateAribaCredential.click();
		System.out.println("Create Credential Ariba!");
		switchToWindow(1);
		hmcPage.PunchoutAribaCredential_CodeInput.clear();
		hmcPage.PunchoutAribaCredential_CodeInput.sendKeys(Code);
		hmcPage.PunchoutAribaCredential_DomainInput.clear();
		hmcPage.PunchoutAribaCredential_DomainInput.sendKeys(Domain);
		hmcPage.PunchoutAribaCredential_IdentityInput.clear();
		hmcPage.PunchoutAribaCredential_IdentityInput.sendKeys(Identity);
		hmcPage.PunchoutAribaCredential_SharedSecretInput.clear();
		hmcPage.PunchoutAribaCredential_SharedSecretInput.sendKeys(SharedSecret);
		hmcPage.PaymentLeasing_saveAndCreate.click();
		System.out.println("Create Credential Ariba successfully!");
		driver.close();
		switchToWindow(0);
	}
	
	public void creatPunchoutCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer,String Code,String Domain,String Identity, String SharedSecret,String UserName,String Password) throws InterruptedException{
		Common.rightClick(driver, hmcPage.Home_Punchout_Credential);
		hmcPage.Home_CreatePunchoutCredential.click();
		System.out.println("Create Punchout Credential!");
		hmcPage.PunchoutCredential_CustomerInput.clear();
		hmcPage.PunchoutCredential_CustomerInput.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		createCredentialAriba(Code,Domain,Identity,SharedSecret);
		hmcPage.PunchoutCredential_CreateButton.click();
		System.out.println("Create Punchout Credential successfully!");
	}
	public void  checkB2BCustomer(String B2BCustomer, String group, String customerType){
		if(!Common.checkElementExists(driver, hmcPage.Home_B2BCustomer, 5)){
			hmcPage.Home_B2BCommerceLink.click();
		}
		hmcPage.Home_B2BCustomer.click();
		if(!Common.checkElementExists(driver, hmcPage.B2BCustomer_SearchIDTextBox, 5)){
			hmcPage.B2BCustomer_SearchToggle.click();
		}
		hmcPage.B2BCustomer_SearchIDTextBox.clear();
		hmcPage.B2BCustomer_SearchIDTextBox.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_SearchButton.click();
		if(Common.checkElementExists(driver, hmcPage.B2BCustomer_1stSearchedResult, 10)){
			editB2BCustomer(B2BCustomer,group,customerType);
		} else {
			creatB2BCustomer(B2BCustomer,group,customerType);
		}
	}
	
	public void creatB2BCustomer(String B2BCustomer,String group, String customerType){
		if(!Common.checkElementExists(driver, hmcPage.Home_B2BCustomer, 10)){
			hmcPage.Home_B2BCommerceLink.click();
		}
		Common.rightClick(driver, hmcPage.Home_B2BCustomer);
		hmcPage.Home_CreateB2BCustomer.click();
		System.out.println("Create B2BCustomer: "+B2BCustomer);
		hmcPage.B2BCustomer_IDInput.clear();
		hmcPage.B2BCustomer_IDInput.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_NameInput.clear();
		hmcPage.B2BCustomer_NameInput.sendKeys(B2BCustomer);
		if(!Common.isElementExist(driver, 
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='"+group+"']"))){
			hmcPage.B2BCustomer_GroupsInput.sendKeys(group);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")).click();
		}	
		hmcPage.B2BCustomer_AccessLevelTab.click();
		hmcPage.B2BCustomer_AccessLevelInput.clear();
		hmcPage.B2BCustomer_AccessLevelInput.sendKeys(AccessLevel);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")).click();
		hmcPage.B2BCustomer_DefaultUnitInput.clear();
		hmcPage.B2BCustomer_DefaultUnitInput.sendKeys(defaultUnit);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")).click();
		hmcPage.B2BCustomer_DefaultDMUInput.clear();
		hmcPage.B2BCustomer_DefaultDMUInput.sendKeys(defaultDMU);
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")), 15);
		driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")).click();
		hmcPage.B2BCustomer_AddressesTab.click();
		hmcPage.B2BCustomer_EmailInput.clear();
		hmcPage.B2BCustomer_EmailInput.sendKeys("sample@lenovo.com");
		hmcPage.B2BCustomer_PasswordTab.click();
		hmcPage.B2BCustomer_ActiveStatus.click();
		hmcPage.B2BCustomer_CreateButton.click();
		System.out.println("Create B2BCustomer successfully!");
	}
	
	public void editB2BCustomer(String B2BCustomer,String group, String customerType){
		Common.doubleClick(driver, hmcPage.B2BCustomer_1stSearchedResult);
		System.out.println("Edit B2BCustomer: "+B2BCustomer);
		hmcPage.B2BCustomer_IDEdit.clear();
		hmcPage.B2BCustomer_IDEdit.sendKeys(B2BCustomer);
		hmcPage.B2BCustomer_NameEdit.clear();
		hmcPage.B2BCustomer_NameEdit.sendKeys(B2BCustomer);
		if(!Common.isElementExist(driver, 
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='"+group+"']"))){
			hmcPage.B2BCustomer_GroupsInput.sendKeys(group);
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+group+"')][text()='"+group+"']")).click();
		}	
		hmcPage.B2BCustomer_AccessLevelTab.click();
		hmcPage.B2BCustomer_AccessLevelEdit.clear();
		hmcPage.B2BCustomer_AccessLevelEdit.sendKeys(AccessLevel);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")).click();
		hmcPage.B2BCustomer_DefaultUnitEdit.clear();
		hmcPage.B2BCustomer_DefaultUnitEdit.sendKeys(defaultUnit);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")).click();
		hmcPage.B2BCustomer_DefaultDMUEdit.clear();
		hmcPage.B2BCustomer_DefaultDMUEdit.sendKeys(defaultDMU);
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")), 15);
		driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")).click();
		hmcPage.B2BCustomer_AddressesTab.click();
		hmcPage.B2BCustomer_EmailEdit.clear();
		hmcPage.B2BCustomer_EmailEdit.sendKeys("sample@lenovo.com");
		hmcPage.B2BCustomer_PasswordTab.click();
		hmcPage.B2BCustomer_ActiveAccountDropdownValue.click();
		hmcPage.baseStore_save.click();
		System.out.println("Edit B2BCustomer successfully!");
	}
	
	
	private void configureEmail(String axXpath, String axwayEmail, String emailToAxway, String inputEmail) {
		driver.get(testData.HMC.getHomePageUrl());
		HMCCommon.Login(hmcPage, testData);
		hmcPage.Home_Nemo.click();
		hmcPage.Home_Nemo_Punchout.click();
		driver.findElement(By.xpath(".//*[contains(text(),'CTO Issue Notificaiton Email')]")).click();
		driver.findElement(By.xpath(axXpath)).click();
		driver.findElement(By.xpath(inputEmail)).clear();
		driver.findElement(By.xpath(inputEmail)).sendKeys(axwayEmail);
		hmcPage.B2BUnit_SearchButton.click();
		if(!Common.checkElementExists(driver, driver.findElement(By.xpath("//div[contains(text(),'"+axwayEmail+"')]")), 10)) {
			Common.rightClick(driver, driver.findElement(By.xpath(axXpath)));
			driver.findElement(By.xpath(emailToAxway)).click();
			driver.findElement(By.xpath("//input[contains(@id,'Content/StringEditor[in Content/Attribute[.emailUrl]')]")).clear();
			driver.findElement(By.xpath("//input[contains(@id,'Content/StringEditor[in Content/Attribute[.emailUrl]')]")).sendKeys(axwayEmail);
			hmcPage.PunchoutCredential_CreateButton.click();
		}
		Common.sleep(3000);
		hmcPage.HMC_Logout.click();
	}


}
