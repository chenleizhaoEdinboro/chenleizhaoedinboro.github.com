package TestScript.B2B;

import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2BCommon;
import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2BPage;
import Pages.B2BPunchoutPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;



public class NA13181Test extends SuperTestClass {
	public String Punchout_Ariba_URL;
	public String HMCURL;
	
	public NA13181Test(String Store){
		this.Store = Store;
		this.testName = "NA-13181";
	}
	
	@Test(alwaysRun = true, groups = {"contentgroup", "b2bpunchout", "p2", "b2b"})
	public void NA13181(ITestContext ctx){
		try{
			this.prepareTest();
			HMCPage hmcPage = new HMCPage(driver);
			B2BPunchoutPage page = new B2BPunchoutPage(driver);
			B2BPage b2bPage = new B2BPage(driver);
			HMCURL = testData.HMC.getHomePageUrl();
			Boolean isContract =  true;
			String B2BCustomer = "9963-001";
			String defaultUnit = "1213577815";
			String defaultDMU = "1213348423";
			String AccessLevel = "1213348423";
			String Domain = "NetworkID";
			String Identity = "9963-001";
			String SharedSecret = "aaa";
			String Code = "9963-001";
			String ProfileName = "9963-001#NA#defaultsoldto-No2";
			String[]  BundleRules= {"1_No_002_1213410863",
					"2_suppCode_bbb_1213410863",
					"3_Country_AU_1213410863",
					"4_No_003_1213521232",
					"5_suppCode_ccc_1213521232",
					"6_Country_NZ_1213521232"
					};
			
			String[]  BundleRules2= {"4_No_003_1213521232",
					"5_suppCode_ccc_1213521232",
					"6_Country_NZ_1213521232"
					};
			Punchout_Ariba_URL = testData.B2B.getHomePageUrl().split("le/")[0]+"nemopunchouttool/ariba";
			driver.get(HMCURL);
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2BCustomer(hmcPage, B2BCustomer);
			if(Common.isElementExist(driver, 
					By.xpath(".//tr[contains(@id,'Content/OrganizerListEntry')]//div[text()='"+B2BCustomer+"']/../../../td[3]"))){
				driver.findElement(By.xpath(".//tr[contains(@id,'Content/OrganizerListEntry')]//div[text()='"+B2BCustomer+"']/../../../td[3]")).click();
				EditCustomer(driver,hmcPage, B2BCustomer, defaultUnit, defaultDMU, AccessLevel);
			}else{
				CreateCustomer(driver,hmcPage, B2BCustomer, defaultUnit, defaultDMU, AccessLevel);
			}
			
			Dailylog.logInfoDB(1,"Create Customer Successfully",Store,testName);
			SearchCredential(driver,hmcPage,B2BCustomer);
			if(Common.isElementExist(driver, 
					By.xpath(".//*[@id='Content/ItemDisplay["+B2BCustomer+"]_div2']"))){
				Common.doubleClick(driver, driver.findElement(By.xpath(".//*[@id='Content/ItemDisplay["+B2BCustomer+"]_div2']")));
				EditCredential(driver,hmcPage, B2BCustomer, Code, Domain, Identity,SharedSecret);
			}else{
				CreateCredential(driver,hmcPage, B2BCustomer, Code, Domain, Identity,SharedSecret);
			}
			Dailylog.logInfoDB(2,"Create Credential Successfully",Store,testName);
			SearchProfile(driver,hmcPage,ProfileName);
			DeleteProfile(driver,hmcPage,ProfileName);
			CreateProfile(driver,hmcPage,ProfileName,B2BCustomer,BundleRules);
			Dailylog.logInfoDB(3,"Create Profile Successfully",Store,testName);
			hmcPage.Home_EndSessionLink.click();
			driver.get(Punchout_Ariba_URL);
			B2BCommon.punchoutLogin(driver,page);
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
			Common.sleep(18000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
			System.out.println("::::"+page.Lenovo_Link.getAttribute("href"));
			assert page.Lenovo_Link.getAttribute("href").contains("1213577815");
			driver.switchTo().defaultContent();
			Dailylog.logInfoDB(4,"punchout to default sold to successfully",Store,testName);
			page.Ariba_DomainTextBox.clear();
			page.Ariba_DomainTextBox.sendKeys(Domain);
			page.Ariba_IdentityTextBox.clear();
			page.Ariba_IdentityTextBox.sendKeys(Identity);
			page.Ariba_SharedSecretTextBox.clear();
			page.Ariba_SharedSecretTextBox.sendKeys(SharedSecret);
			page.Ariba_SuppCode.clear();
			page.Ariba_SuppCode.sendKeys("bbb");
			page.Ariba_PunchoutButton.click();
			Common.sleep(8000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
			System.out.println("::::"+page.Lenovo_Link.getAttribute("href"));
			assert page.Lenovo_Link.getAttribute("href").contains("1213410863");
			driver.switchTo().defaultContent();
			Dailylog.logInfoDB(5,"punchout to suppCode sold to successfully",Store,testName);
			
			page.Ariba_DomainTextBox.clear();
			page.Ariba_DomainTextBox.sendKeys(Domain);
			page.Ariba_IdentityTextBox.clear();
			page.Ariba_IdentityTextBox.sendKeys(Identity);
			page.Ariba_SharedSecretTextBox.clear();
			page.Ariba_SharedSecretTextBox.sendKeys(SharedSecret);
			page.Ariba_AddExtrinsicButton.click();
			page.Ariba_SuppCode.clear();
			page.Ariba_InboundKeyTextBox.clear();
			page.Ariba_InboundKeyTextBox.sendKeys("No");
			page.Ariba_InboundValueTextBox.clear();
			page.Ariba_InboundValueTextBox.sendKeys("003");
			page.Ariba_PunchoutButton.click();
			Common.sleep(8000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
			assert page.Lenovo_Link.getAttribute("href").contains("1213521232");
			Dailylog.logInfoDB(6,"punchout to Extrinsic sold to successfully",Store,testName);
			b2bPage.HomePage_productsLink.click();
			b2bPage.HomePage_Laptop.click();
			Common.sleep(16000);
			b2bPage.productPage_agreementsAndContract.click();
	        try{
	        	b2bPage.productPage_raidoContractButton.click();
	        }catch(Exception e){
	        	isContract = false;
	        }
	        if(isContract){
	        	if(Common.isElementExist(driver,By.xpath("//img[@title='Add to Cart']"))){
	        		b2bPage.ProductPage_details.click();
	        		try {
	                    Thread.sleep(8000);
	                    b2bPage.PDPPage_AddtoCart.click();
	                }catch(ElementNotVisibleException e){
	                    driver.navigate().refresh();
	                    Thread.sleep(8000);
	                    b2bPage.PDPPage_AddtoCart.click();
	                }catch(TimeoutException e){
	                    driver.navigate().refresh();
	                    Thread.sleep(8000);
	                    b2bPage.PDPPage_AddtoCart.click();
	                }catch(NoSuchElementException e){
	                    driver.navigate().refresh();
	                    Thread.sleep(8000);
	                    b2bPage.PDPPage_AddtoCart.click();
	                }
	        		b2bPage.productPage_AlertAddToCart.click();
	                Thread.sleep(8000);
	                b2bPage.ProductPage_AlertGoToCart.click();
	        	}else {
	            	 Dailylog.logInfoDB(7,"No product can be used",Store,testName);
		                assert false;
		            }
	        }else {
	        	if(Common.isElementExist(driver,By.xpath("//img[@title='Customize and buy']"))){
	        		b2bPage.ProductPage_details.click();
	                Thread.sleep(12000);
	                if(driver.findElement(By.xpath("//button[text()='Customize']")).isDisplayed()){
						System.out.println("here is ------");
						driver.findElement(By.xpath("(//button[@class='close' and text()='×'])[2]")).click();
						Thread.sleep(5000);
					}
	                try {
	                    Thread.sleep(8000);
	                    b2bPage.Agreement_agreementsAddToCart.click();
	                }catch(ElementNotVisibleException e){
	                    driver.navigate().refresh();
	                    Thread.sleep(8000);
	                    b2bPage.Agreement_agreementsAddToCart.click();
	                }catch(TimeoutException e){
	                    driver.navigate().refresh();
	                    Thread.sleep(8000);
	                    b2bPage.Agreement_agreementsAddToCart.click();
	                }catch(NoSuchElementException e){
	                    driver.navigate().refresh();
	                    Thread.sleep(8000);
	                    b2bPage.Agreement_agreementsAddToCart.click();
	                }
	                Thread.sleep(3000);
	                if(Common.isElementExist(driver, By.cssSelector("div>button.pricingSummary-button.button-called-out.button-full"))){
	                	b2bPage.Agreement_addToCartAccessoryBtn.click();
	                    Thread.sleep(5000);
	                } 
	        	}else {
	            	 Dailylog.logInfoDB(7,"No product can be used",Store,testName);
		                assert false;
		            }
	        }
	        Common.sleep(12000);
	        b2bPage.cartPage_LenovoCheckout.click();
	        Thread.sleep(8000);
	        driver.switchTo().defaultContent();
	        page.Ariba_EditButton.click();
	        Thread.sleep(8000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
			Common.sleep(8000);
			assert page.Lenovo_Link.getAttribute("href").contains("1213521232");
			assert driver.findElement(By.xpath(".//*[@class='cart-item']")).isDisplayed();
			b2bPage.cartPage_LenovoCheckout.click();
			Thread.sleep(8000);
			driver.switchTo().defaultContent();		       
	        page.Ariba_InspectButton.click();
	        Thread.sleep(8000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
			Common.sleep(5000);
	        assert page.Lenovo_Link.getAttribute("href").contains("1213521232");
	        assert driver.findElement(By.xpath(".//*[@class='cart-item']")).isDisplayed();
	        driver.switchTo().defaultContent();
	        Dailylog.logInfoDB(8,"Checkout and edit, inspect successfully",Store,testName);
	        driver.get(HMCURL);
			HMCCommon.Login(hmcPage, testData);
			SearchProfile(driver,hmcPage,ProfileName);
			DeleteProfile(driver,hmcPage,ProfileName);
			CreateProfile(driver,hmcPage,ProfileName,B2BCustomer,BundleRules2);
			Dailylog.logInfoDB(9,"Change Profile Successfully",Store,testName);
			hmcPage.Home_EndSessionLink.click();
			
			
			
			driver.get(Punchout_Ariba_URL);
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
			page.Ariba_SuppCode.clear();
			page.Ariba_SuppCode.sendKeys("bbb");
			page.Ariba_AddExtrinsicButton.click();
			page.Ariba_InboundKeyTextBox.clear();
			page.Ariba_InboundKeyTextBox.sendKeys("No");
			page.Ariba_InboundValueTextBox.clear();
			page.Ariba_InboundValueTextBox.sendKeys("002");
			page.Ariba_PunchoutButton.click();
			Common.sleep(8000);
			driver.switchTo().frame(driver.findElement(By.id("displayFrame")));
			Common.sleep(3000);
			System.out.println(page.Lenovo_Link.getAttribute("href"));
			assert page.Lenovo_Link.getAttribute("href").contains("1213577815");
			driver.switchTo().defaultContent();
			
	        
		}catch(Throwable e){
			handleThrowable(e, ctx);			
		}
		
	}
	public void SearchCredential(WebDriver driver,HMCPage hmcPage,String B2BCustomer){
		hmcPage.Home_Nemo.click();
		hmcPage.Home_Nemo_Punchout.click();
		hmcPage.Home_Punchout_Credential.click();
		hmcPage.PunchoutCredential_CustomerSearch.clear();
		hmcPage.PunchoutCredential_CustomerSearch.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		hmcPage.Contract_searchbutton.click();	
		
	}
	
	
	public void SearchProfile(WebDriver driver,HMCPage page1,String ProfileName){
		if(!Common.checkElementExists(driver, page1.Home_Nemo_Punchout, 5)){
			page1.Home_Nemo.click();
			System.out.println(Common.checkElementExists(driver, page1.Home_Punchout_CustomerProfile, 5));
			if(!Common.checkElementExists(driver, page1.Home_Punchout_CustomerProfile, 5)){
				page1.Home_Nemo_Punchout.click();
			}
		}
		page1.Home_Punchout_CustomerProfile.click();
		page1.PunchoutProfile_NameEqual.click();
		page1.PunchoutProfile_NameSearch.clear();
		page1.PunchoutProfile_NameSearch.sendKeys(ProfileName);
		//driver.findElement(By.xpath(".//*[@id='Content/StringDisplay["+ProfileName+"]_span']")).click();
		page1.Contract_searchbutton.click();	
		
	}
	
	public void EditCustomer(WebDriver driver, HMCPage page1,String B2BCustomer,String defaultUnit,String defaultDMU,String AccessLevel){
		page1.B2BCustomer_IDEdit.clear();
		page1.B2BCustomer_IDEdit.sendKeys(B2BCustomer);
		page1.B2BCustomer_NameEdit.clear();
		page1.B2BCustomer_NameEdit.sendKeys(B2BCustomer);
		if(!Common.isElementExist(driver, 
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='punchOutCustomer']"))){
			page1.B2BCustomer_GroupsInput.sendKeys("punchOutCustomer");
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")).click();
		}
		page1.B2BCustomer_AccessLevelTab.click();
		page1.B2BCustomer_AccessLevelEdit.clear();
		page1.B2BCustomer_AccessLevelEdit.sendKeys(AccessLevel);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")).click();
		page1.B2BCustomer_DefaultUnitEdit.clear();
		page1.B2BCustomer_DefaultUnitEdit.sendKeys(defaultUnit);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")).click();
		page1.B2BCustomer_DefaultDMUEdit.clear();
		page1.B2BCustomer_DefaultDMUEdit.sendKeys(defaultDMU);
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")), 15);
		driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")).click();
		page1.B2BCustomer_AddressesTab.click();
		page1.B2BCustomer_EmailEdit.clear();
		page1.B2BCustomer_EmailEdit.sendKeys("sample@lenovo.com");
		page1.B2BCustomer_PasswordTab.click();
		page1.B2BCustomer_ActiveAccountDropdownValue.click();
		page1.Common_SaveButton.click();
	}
	
	public void CreateCustomer(WebDriver driver,HMCPage page1,String B2BCustomer,String defaultUnit,String defaultDMU,String AccessLevel){
		Common.rightClick(driver, page1.Home_B2BCustomer);
		page1.Home_CreateB2BCustomer.click();
		page1.B2BCustomer_IDInput.clear();
		page1.B2BCustomer_IDInput.sendKeys(B2BCustomer);
		page1.B2BCustomer_NameInput.clear();
		page1.B2BCustomer_NameInput.sendKeys(B2BCustomer);
		if(!Common.isElementExist(driver, 
				By.xpath(".//*[contains(text(),'Groups:')]/../..//tbody/tr/td[3]//div[text()='punchOutCustomer']"))){
			page1.B2BCustomer_GroupsInput.sendKeys("punchOutCustomer");
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")), 15);
			driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_punchOutCustomer')][text()='punchOutCustomer']")).click();
		}
		page1.B2BCustomer_AccessLevelTab.click();
		page1.B2BCustomer_AccessLevelInput.clear();
		page1.B2BCustomer_AccessLevelInput.sendKeys(AccessLevel);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+AccessLevel+"')][text()='"+AccessLevel+"']")).click();
		page1.B2BCustomer_DefaultUnitInput.clear();
		page1.B2BCustomer_DefaultUnitInput.sendKeys(defaultUnit);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'_ajaxselect_"+defaultUnit+"')][text()='"+defaultUnit+"']")).click();
		page1.B2BCustomer_DefaultDMUInput.clear();
		page1.B2BCustomer_DefaultDMUInput.sendKeys(defaultDMU);
		Common.waitElementClickable(driver, driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")), 15);
		driver.findElement(By.xpath("(.//*[contains(@id,'_ajaxselect_"+defaultDMU+"')][text()='"+defaultDMU+"'])[2]")).click();
		page1.B2BCustomer_AddressesTab.click();
		page1.B2BCustomer_EmailInput.clear();
		page1.B2BCustomer_EmailInput.sendKeys("sample@lenovo.com");
		page1.B2BCustomer_PasswordTab.click();
		page1.B2BCustomer_ActiveStatus.click();
		page1.B2BCustomer_CreateButton.click();
	}
	
	
	public void CreateCredential(WebDriver driver,HMCPage page1,String B2BCustomer,String Code,String Domain,String Identity,String SharedSecret){
		page1.Home_Punchout_Credential.click();
		Common.rightClick(driver, page1.Home_Punchout_Credential);
		page1.Home_CreatePunchoutCredential.click();
		page1.PunchoutCredential_CustomerInput.clear();
		page1.PunchoutCredential_CustomerInput.sendKeys(B2BCustomer);
		driver.findElement(By.xpath(".//*[@id='Content/AutocompleteReferenceEditor[in Content/Attribute[.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		AddAribaCredential(driver,page1,Code,Domain,Identity,SharedSecret);
		page1.PunchoutCredential_CreateButton.click();
	}
	
	public void EditCredential(WebDriver driver,HMCPage page1,String B2BCustomer,String Code,String Domain,String Identity,String SharedSecret){
		if(Common.isElementExist(driver, By.xpath("(.//div[contains(text(),'PunchOut Credentials')])[2]/../../td[last()]//table//table//table//input"))){
			Common.rightClick(driver, driver.findElement(By.xpath("(.//div[contains(text(),'PunchOut Credentials')])[2]/../../td[last()]//table//table//table//input")));
			page1.PunchoutCredential_RemoveAribaCredential.click();
			Common.sleep(2000);
			Alert alert =  driver.switchTo().alert();
			alert.accept();
			Common.sleep(8000);
		}
		AddAribaCredential(driver,page1,Code,Domain,Identity,SharedSecret);
		
		page1.PaymentLeasing_saveAndCreate.click();
	}
	
	
	public void AddAribaCredential(WebDriver driver,HMCPage page1,String Code,String Domain,String Identity,String SharedSecret){
		String mainHandle = driver.getWindowHandle();
		Common.rightClick(driver, page1.PunchoutCredential_AribaCredential);
		page1.PunchoutCredential_AddAribaCredential.click();
		Set<String> allHandles = driver.getWindowHandles();
		for(String secondHandle:allHandles){
			if(secondHandle!=mainHandle){
				driver.switchTo().window(secondHandle);
			}	
		}
		page1.PunchoutAribaCredential_CodeSearchInput.clear();
		page1.PunchoutAribaCredential_CodeSearchInput.sendKeys(Code);
		page1.Contract_searchbutton.click();
		if(!Common.isElementExist(driver, By.xpath(".//*[@id='StringDisplay["+Code+"]_span']"))){
			driver.findElement(By.xpath(".//span[contains(text(),'Cancel')]")).click();
			driver.switchTo().window(mainHandle);
			CreateAribaCredential(driver,page1,Code,Domain,Identity,SharedSecret);
		}else{
			driver.findElement(By.xpath(".//*[@id='StringDisplay["+Code+"]_span']")).click();
			driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
			driver.switchTo().window(mainHandle);
		}
	}
	
	public void CreateAribaCredential(WebDriver driver,HMCPage page1,String Code,String Domain,String Identity,String SharedSecret){
		
		String mainHandle = driver.getWindowHandle();
		Common.rightClick(driver, page1.PunchoutCredential_AribaCredential);
		page1.PunchoutCredential_CreateAribaCredential.click();
		Set<String> allHandles = driver.getWindowHandles();
		for(String secondHandle:allHandles){
			if(secondHandle!=mainHandle){
				driver.switchTo().window(secondHandle);
			}	
		}
		page1.PunchoutAribaCredential_CodeInput.clear();
		page1.PunchoutAribaCredential_CodeInput.sendKeys(Code);
		page1.PunchoutAribaCredential_DomainInput.clear();
		page1.PunchoutAribaCredential_DomainInput.sendKeys(Domain);
		page1.PunchoutAribaCredential_IdentityInput.clear();
		page1.PunchoutAribaCredential_IdentityInput.sendKeys(Identity);
		page1.PunchoutAribaCredential_SharedSecretInput.clear();
		page1.PunchoutAribaCredential_SharedSecretInput.sendKeys(SharedSecret);
		page1.PaymentLeasing_saveAndCreate.click();
		driver.switchTo().window(mainHandle);
	}
	
	public void CreateProfile(WebDriver driver,HMCPage page1,String ProfileName,String B2BCustomer,String[] InboundRule) throws InterruptedException{
		page1.Home_Punchout_CustomerProfile.click();
		Common.rightClick(driver, page1.Home_Punchout_CustomerProfile);
		page1.Home_CreatePunchoutProfile.click();
		page1.PunchoutProfile_NameInput.clear();
		page1.PunchoutProfile_NameInput.sendKeys(ProfileName);
		/*if(driver.findElement(By.xpath(".//*[@id='Content/BooleanEditor[in Content/Attribute[.isActivate]]_checkbox']/../input[1]")).equals("true")){
			page1.PunchoutProfile_Active.click();	
		}*/
		AddCustomerForProfile(driver,page1,B2BCustomer);
		/*if(driver.findElement(By.xpath(".//*[@id='Content/BooleanEditor[in Content/Attribute[.cxmlActive]]_checkbox']/../input[1]")).equals("true")){
			page1.PunchoutProfile_ActiveOxml.click();	
		}*/
		page1.PunchoutProfile_ActiveOxml.click();
		for(int i = 0;i<InboundRule.length;i++){
			AddBoundRule(driver,page1,InboundRule[i]);
		}
		page1.PunchoutCredential_CreateButton.click();
	}
	
	public void AddBoundRule(WebDriver driver,HMCPage page1,String BundleRule){
		String id = BundleRule.split("_")[0];
		String name = BundleRule.split("_")[1];
		String value = BundleRule.split("_")[2];
		String unit = BundleRule.split("_")[3];
		Common.rightClick(driver, driver.findElement(By.xpath(".//*[text()='Group ID']")));
		driver.findElement(By.xpath(".//*[text()='Create Sold To Determination']")).click();
		Common.sleep(2000);
		driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[1]")).clear();
		driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[1]")).sendKeys(id);
		driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[2]")).clear();
		driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[2]")).sendKeys(name);
		driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[3]")).clear();
		driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[3]")).sendKeys(value);
		driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[4]")).clear();
		driver.findElement(By.xpath("(.//*[contains(text(),'Inbound Rules:')]/../../td[last()]//table//table/tbody/tr[last()]//table//input)[4]")).sendKeys(unit);
		Common.waitElementClickable(driver, driver.findElement(By.xpath(".//*[contains(@id,'Content/AutocompleteReferenceEditor[in Content/CreateItemListEntry[n/a]')][contains(@id,'"+unit+"')]")), 15);
		driver.findElement(By.xpath(".//*[contains(@id,'Content/AutocompleteReferenceEditor[in Content/CreateItemListEntry[n/a]')][contains(@id,'"+unit+"')]")).click();
	}
	
	public void AddCustomerForProfile(WebDriver driver,HMCPage page1,String B2BCustomer) throws InterruptedException{
		String mainHandle = driver.getWindowHandle();
		Common.rightClick(driver, page1.PunchoutProfile_CustomerField);
		page1.PunchoutCredential_AddAribaCredential.click();
		Set<String> allHandles = driver.getWindowHandles();
		for(String secondHandle:allHandles){
			if(secondHandle!=mainHandle){
				driver.switchTo().window(secondHandle);
			}	
		}
		page1.PunchoutProfile_CustomerInput.clear();
		page1.PunchoutProfile_CustomerInput.sendKeys(B2BCustomer);
		Common.scrollToElement(driver, driver.findElement(By.xpath(".//*[@id='AutocompleteReferenceEditor[in GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")));
		driver.findElement(By.xpath(".//*[@id='AutocompleteReferenceEditor[in GenericCondition[B2BCustomerPunchOutCredentialMapping.b2bCustomer]]_ajaxselect_"+B2BCustomer+"']")).click();
		page1.Contract_searchbutton.click();
		driver.findElement(By.xpath(".//*[contains(@id,'ItemDisplay["+B2BCustomer+"]')]")).click();
		driver.findElement(By.xpath(".//span[contains(text(),'Use')]")).click();
		driver.switchTo().window(mainHandle);
	}
	
	public void DeleteProfile(WebDriver driver,HMCPage page1,String ProfileName){
		while(Common.isElementExist(driver, By.xpath(".//td[contains(@id,'Content/StringDisplay["+ProfileName+"]')]"))){
			Common.rightClick(driver, driver.findElement(By.xpath(".//td[contains(@id,'Content/StringDisplay["+ProfileName+"]')]")));
			page1.PunchoutCredential_RemoveAribaCredential.click();
			Common.sleep(2000);
			Alert alert =  driver.switchTo().alert();
			alert.accept();
			Common.sleep(8000);
			
		}
	}
	
	

}
