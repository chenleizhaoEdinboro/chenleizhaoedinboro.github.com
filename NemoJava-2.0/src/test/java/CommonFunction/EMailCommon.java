package CommonFunction;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import Pages.B2BPage;
import Pages.MailPage;

public class EMailCommon {

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Go to Email box home page
	 */
	public static void goToMailHomepage(WebDriver driver) {
		driver.get("https://www.guerrillamail.com/inbox");
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Get random email address
	 */
	public static String getRandomEmailAddress() {
		return Common.getDateTimeString() + "@sharklasers.com"; 
	}

	/**
	 * @Owner Hao
	 * @Parameter:
	 * @Usage:
	 */
	public static void createEmail(WebDriver driver, MailPage mailPage, String email) {
		driver.manage().deleteAllCookies();
		goToMailHomepage(driver);
		mailPage.Inbox_EditButton.click();
		Common.sleep(5000);
		mailPage.Inbox_InputEmail.clear();
		mailPage.Inbox_InputEmail.sendKeys(email);
		Common.sleep(1000);
		// Common.KeyEventEnter();
		mailPage.Inbox_SetButton.click();
		Common.sleep(1000);
	};

	/**
	 * @Owner Hao
	 * @Parameter:
	 * @Usage:
	 */
	public static void activeNewAccount(WebDriver driver,String mailId, int retryCount, String store)
			throws InterruptedException {
		goToMailHomepage(driver);
		driver.findElement(By.xpath(".//*[@id='inbox-id']")).click();
		driver.findElement(By.xpath(".//*[@id='inbox-id']/input")).sendKeys(mailId.replace("@sharklasers.com", ""));
		driver.findElement(By.xpath(".//*[@id='inbox-id']/button[1]")).click();
		try {
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//tbody[@id='email_list']/tr[2]")), 60);
		} catch (NoSuchElementException ex) {
			Assert.fail("Email is not recieved within 60 seconds.");
		}
		Thread.sleep(5000);
		driver.findElement(By.xpath(".//tbody[@id='email_list']/tr[1]/td[2]")).click();
		Thread.sleep(5000);
		if (!Common.isElementExist(driver, By.xpath("//a[contains(.,'Activate your account')]")) && !Common.isElementExist(driver, By.xpath("//a[contains(.,'アカウントをアクティブにする')]"))) {
			driver.findElement(By.id("back_to_inbox_link")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath(".//tbody[@id='email_list']/tr[2]")).click();
			Thread.sleep(5000);
		}
		
		if(store.contains("JP")){
			Common.scrollToElement(driver, driver.findElement(By.xpath("//a[contains(.,'アカウントをアクティブにする')]")));
			Common.sleep(2000);
			
			driver.findElement(By.xpath("//a[contains(.,'アカウントをアクティブにする')]")).click();
		}else{
			Common.scrollToElement(driver, driver.findElement(By.xpath("//a[contains(.,'Activate your account')]")));
			Common.sleep(2000);
			
			driver.findElement(By.xpath("//a[contains(.,'Activate your account')]")).click();
		}
		
//		Common.javascriptClick(driver, driver.findElement(By.xpath(".//a[contains(@href,'lenovoid.email=')]")));

	}
	
	/**
	 * @Owner Songli
	 * @Parameter:
	 * @Usage:
	 */
	public static boolean checkQuoteStatus(WebDriver driver, String mailId, String quoteId, boolean isApproved)
			throws InterruptedException {
		goToMailHomepage(driver);
		driver.findElement(By.xpath(".//*[@id='inbox-id']")).click();
		driver.findElement(By.xpath(".//*[@id='inbox-id']/input")).sendKeys(mailId.replace("@sharklasers.com", ""));
		driver.findElement(By.xpath(".//*[@id='inbox-id']/button[1]")).click();
		
		Thread.sleep(3000);
		String expected = isApproved?"was approved":"was not approved";
		List<WebElement> mailList = driver.findElements(By.xpath(".//tbody[@id='email_list']/tr"));
		for(WebElement mail : mailList)
		{
			if(mail.getText().contains(expected)) {
				mail.click();
				Thread.sleep(3000);
				if(driver.findElement(By.xpath("//div[@class='email_body']")).getText().contains(quoteId))
					return true;
				else
					return false;
			}
		}
		return false;
	}

	/**
	 * @Owner Hao
	 * @Parameter:
	 * @Usage:
	 */
	public static Boolean checkEmail(WebDriver driver, MailPage mailPage, B2BPage b2bPage, String emailSubject,
			WebElement element, String checkEmail, String emailTilte, String urlTitle, Boolean login) {
		String subject = null;
		Boolean reciverEmail = null;
		for (int i = 1; i <= 5; i++) {
			if (i == 5) {
				reciverEmail = false;
				// System.out.println("ERROR !!!! Email is not received.....!");
				System.out.println("Need Manual Validate in email " + checkEmail + ", and check email: " + emailTilte);
			} else if (Common.checkElementExists(driver, mailPage.Inbox_EmailSubject, 5) == true) {
				subject = mailPage.Inbox_EmailSubject.getText();
				System.out.println("The subject is: " + subject);
				if (subject.contains(emailSubject)) {
					reciverEmail = true;
					i = 6;
					Actions actions = new Actions(driver);
					actions.sendKeys(Keys.PAGE_UP).perform();
					mailPage.Inbox_EmailSubject.click();
					System.out.println("Clicked on email containing :" + emailSubject);
					// mailPage.Inbox_BackToLoginLink.click();
					Common.sleep(10000);
					String linkText = element.getText();
					// String parentWindowId = driver.getWindowHandle();
					element.click();
					System.out.println(linkText + " link is clicked.");
					Common.switchToWindow(driver, 1);

					Common.sleep(1000);
					if (linkText.contains("Store") == true && login == true) {
						B2BCommon.Login(b2bPage, checkEmail, "1q2w3e4r");
						Common.sleep(2000);
					}
					String linkedPageUrl = driver.getCurrentUrl();
					System.out.println("The url is: " + linkedPageUrl);
					Assert.assertTrue(linkedPageUrl.contains(urlTitle));
					driver.close();
					Common.switchToWindow(driver, 0);

				} else {
					System.out.println(
							"email with this subject is not yet received...!!! Refreshing the inbox after 10 seconds......");
					Common.sleep(10000);
				}
			}
		}
		return reciverEmail;
	}

	/**
	 * @Owner Hao
	 * @Parameter:
	 * @Usage:
	 */
	public static void checkConfirmationEmail(WebDriver driver, String mailId, String orderNum) {
		goToMailHomepage(driver);
		driver.findElement(By.xpath(".//*[@id='inbox-id']")).click();
		driver.findElement(By.xpath(".//*[@id='inbox-id']/input")).sendKeys(mailId.replace("@sharklasers.com", ""));
		driver.findElement(By.xpath(".//*[@id='inbox-id']/button[1]")).click();
		try {
			Common.waitElementClickable(driver, driver.findElement(By.xpath(".//tbody[@id='email_list']/tr[2]")), 60);
		} catch (NoSuchElementException e) {
			Assert.fail("No Email recieved!");
		}
		String confirmation = driver.findElement(By.xpath("//td[@class = 'td3']")).getText();
		Assert.assertTrue(confirmation.contains(orderNum));
	}
	
	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Check confirmation email - Yopmail
	 */
	public static void checkConfirmationEmail_Yopmail(WebDriver driver, String mailId, String orderNum) {
		driver.get("http://www.yopmail.net");
		driver.findElement(By.xpath(".//input[@id='login']")).clear();
		driver.findElement(By.xpath(".//input[@id='login']")).sendKeys(mailId.replace("@yopmail.com", ""));
		driver.findElement(By.xpath(".//input[@class='sbut']")).click();

		WebElement iframe = driver.findElement(By.id("ifinbox"));
		driver.switchTo().frame(iframe);
		try {
			Common.checkElementDisplays(driver, By.xpath(".//a[@class='lm']"), 5);
		} catch (NoSuchElementException e) {
			Assert.fail("No Email recieved!");
		}

		if(!driver.findElement(By.xpath(".//a[@class='lm']")).getText().contains(orderNum))
			Assert.fail("Order number is wrong!");
		
		driver.switchTo().defaultContent();
	}

	/**
	 * @Owner Hao
	 * @Parameter:
	 * @Usage:
	 */
	public static void checkBackToLoginLink(String mailId, int retryCount, String store, String targetUrl)
			throws InterruptedException {
		// this.goToMailHomepage();
		// this.loginInbox(mailId);
		// Thread.sleep(5000);
		// this.waitAndOpenLatestEmail(retryCount, store);
		// Thread.sleep(5000);
		// page.Mail_BackToLoginLink.click();
		//
		// //Check if go to login page
		// page.PageDriver.switchTo().window(page.PageDriver.getWindowHandles().toArray()[1].toString());
		// Assert.assertEquals(page.PageDriver.getCurrentUrl(), targetUrl);
	}

	// Function to just check if email with a specific subject is triggered or not.
	/**
	 * @Owner Hao
	 * @Parameter:
	 * @Usage:
	 */
	public static Boolean checkIfEmailReceived(WebDriver driver, MailPage mailPage, String emailSubject) {
		String subject = null;
		Boolean emailReceived = false;
		for (int i = 1; i <= 5; i++) {
			if (Common.checkElementExists(driver, mailPage.Inbox_EmailSubject, 5) == true) {
				subject = mailPage.Inbox_EmailSubject.getText();
				System.out.println("The subject is: " + subject);
				if (subject.contains(emailSubject)) {
					emailReceived = true;
					break;
				} else {
					System.out.println(
							"Email with this subject is not yet received. Refreshing the inbox in 10 seconds!");
					Common.sleep(10000);
				}
			}
		}
		if (!emailReceived) {
			System.out.println("Need Manual Validate in email. Email not received!");
		}
		return emailReceived;
	}

	// Function to just check if email with a conntry seal or not.
	/**
	 * @Owner Hao
	 * @Parameter:
	 * @Usage:
	 */
	public static Boolean checkEmailCountrySeal(WebDriver driver, MailPage mailPage, String emailSubject,
			String checkEmail, String quoteNo, boolean seal) {
		String subject = null;
		Boolean reciverEmail = false;
		Common.sleep(10000);
		for (int i = 1; i <= 10; i++) {
			if (Common.checkElementExists(driver, mailPage.Inbox_EmailSubject, 30) == true) {
				subject = mailPage.Inbox_EmailSubject.getText();
				System.out.println("The subject is: " + subject);
				if (subject.contains(emailSubject)) {
					mailPage.Inbox_EmailSubject.click();
					System.out.println("Clicked on email containing :" + emailSubject);
					Common.sleep(6000);
					if (!Common.checkElementDisplays(driver,
							By.xpath("//td[@valign='middle']/table//tr/td[contains(.,'" + quoteNo + "')]"), 10)) {
						mailPage.Mail_backToInbox.click();
						continue;
					}
					reciverEmail = true;
					if (seal) {
						Assert.assertTrue(Common.checkElementExists(driver, mailPage.email_countrySeal_1, 60),
								"Should show the country seal!");
					} else {
						Assert.assertTrue(!Common.checkElementExists(driver, mailPage.email_countrySeal_1, 60),
								"Shouldn't show the country seal!");
					}
					break;

				} else {
					System.out.println(
							"email with this subject is not yet received...!!! Refreshing the inbox after 10 seconds......");
					Common.sleep(10000);
				}
			}
		}
		if (!reciverEmail) {
			System.out.println("Need Manual Validate in email. Email not received!");
		}
		return reciverEmail;
	}
	
	// Function to check if exists email with a specified content.
	/**
	 * @Owner Hao
	 * @Parameter:
	 * @Usage:
	 */
	public static Boolean checkEmailContent(WebDriver driver,MailPage mailPage, String emailSubject, String contentXpath){
		String subject = null;
		Boolean reciverEmail=false;
		Common.sleep(10000);
		for(int i=1;i<=10;i++){
			if(Common.checkElementExists(driver, mailPage.Inbox_EmailSubject,30)==true){
				subject=mailPage.Inbox_EmailSubject.getText();
				System.out.println("The subject is: "+subject);
				if(subject.contains(emailSubject)){	
					mailPage.Inbox_EmailSubject.click();
					System.out.println("Clicked on email containing :" + emailSubject);	
					Common.sleep(10000);
					if(!Common.checkElementDisplays(driver, By.xpath(contentXpath), 10)){
						mailPage.Mail_backToInbox.click();
						continue;
					}
					reciverEmail=true;
					Assert.assertTrue(Common.checkElementExists(driver, driver.findElement(By.xpath(contentXpath)), 10), "Should show the checkContent");
					break;

				} else {
					System.out.println("email with this subject is not yet received...!!! Refreshing the inbox after 10 seconds......");
					Common.sleep(10000);
				}
			} 
		}
		if (!reciverEmail) {
			System.out.println("Need Manual Validate in email. Email not received!");
		}
		return reciverEmail;		
	}
	
	// Function to check if exists email with a specified content(in all mails).
	/**
	 * @Owner Hao
	 * @Parameter:
	 * @Usage:
	 */
	public static Boolean checkEmailContentinAllEmail(WebDriver driver, MailPage mailPage, String emailSubject,
			String contentXpath) {
		String subject = null;
		Boolean reciverEmail = false;
		Common.sleep(3000);
		for (int i = 1; i <= 10; i++) {
			List<WebElement> emailList = driver.findElements(By.xpath("//tbody[@id='email_list']/tr/td[3]"));
			if (emailList.size() > 0) {

				for (WebElement emailTitle : emailList) {
					subject = emailTitle.getText().toLowerCase();
					System.out.println("The subject is: " + subject);
					if (subject.contains(emailSubject.toLowerCase())) {
						emailTitle.click();
						System.out.println("Clicked on email containing :" + emailSubject.toLowerCase());
						Common.sleep(5000);
						if (!Common.checkElementDisplays(driver,

								By.xpath(contentXpath), 10)) {
							mailPage.Mail_backToInbox.click();
							continue;
						}
						reciverEmail = true;
						Assert.assertTrue
						(Common.checkElementExists(driver, driver.findElement(By.xpath(contentXpath)), 10),
								"Should show the checkContent");
						break;
					}
				}
			}
			if (reciverEmail)
				break;
			else {
				System.out.println(
						"email with this subject is not yet received...!!! Refreshing the inbox after 10 seconds......");
				Common.sleep(10000);
			}
		}
		if (!reciverEmail) {
			System.out.println("Need Manual Validate in email. Email not received!");
		}
		return reciverEmail;
	}
}
