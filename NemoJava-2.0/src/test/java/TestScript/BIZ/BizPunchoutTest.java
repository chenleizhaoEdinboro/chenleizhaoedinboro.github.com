package TestScript.BIZ;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.B2BPunchoutPage;
import TestData.PropsUtils;

public class BizPunchoutTest {
	private WebDriver driver;
	private String Url;
	private String Domain;
	private String ID;
	private String Password;
	private String InboundKey;
	private String InboundValue;
	private String SoldtoNum;
	private String PartNum;
	private String RowIndex;
	private static ArrayList<String[]> outputData = new ArrayList<String[]>();
	private boolean isPunchoutPass;
	private boolean isSoldToPass;
	private boolean isCheckoutPass;
	B2BPage page;

	public BizPunchoutTest(String rowIndex, String url, String domain, String id, String password, String inboundKey,
			String inboundValue, String soldtoNum) {
		this.Url = url;
		this.Domain = domain.equals("-") ? "" : domain;
		this.ID = id;
		this.Password = password;
		this.InboundKey = inboundKey.equals("-") ? "" : inboundKey;
		this.InboundValue = inboundValue.equals("-") ? "" : inboundValue;
		this.SoldtoNum = soldtoNum;
		this.RowIndex = rowIndex;
	}

	@Test
	public void BizPunchout() {
		isPunchoutPass = false;
		isSoldToPass = false;
		isCheckoutPass = false;
		PartNum = "No Products";
		switch (this.getProtocol(this.Url).toLowerCase()) {
		case "ariba":
			testARIBA();
			break;
		case "oci":
			testOCI();
			break;
		case "oxml":
			testOXML();
			break;
		default:
			break;
		}
	}

	private void testARIBA() {
		try {
			driver.get(this.Url);
			B2BPunchoutPage page = new B2BPunchoutPage(driver);
			page.Ariba_DomainTextBox.clear();
			page.Ariba_DomainTextBox.sendKeys(this.Domain);
			page.Ariba_IdentityTextBox.clear();
			page.Ariba_IdentityTextBox.sendKeys(this.ID);
			page.Ariba_SharedSecretTextBox.clear();
			page.Ariba_SharedSecretTextBox.sendKeys(this.Password);
			if (!this.InboundKey.equals("")) {
				page.Ariba_AddExtrinsicButton.click();
				page.Ariba_InboundKeyTextBox.clear();
				page.Ariba_InboundKeyTextBox.sendKeys(this.InboundKey);
			}
			if (!this.InboundValue.equals("")) {
				page.Ariba_InboundValueTextBox.clear();
				page.Ariba_InboundValueTextBox.sendKeys(this.InboundValue);
			}
			page.Ariba_PunchoutButton.click();
			try {
				Common.checkElementExists(driver, page.Ariba_Frame, 120);
				driver.switchTo().frame(page.Ariba_Frame);
				if (this.getSoldtoNum(page).equals(this.SoldtoNum)) {
					System.out.println(RowIndex + " sold to correct");
					isSoldToPass = true;
				} else {
					System.out.println(RowIndex + " Solto Number Wrong!" + " Real:" + this.getSoldtoNum(page) + " "
							+ this.SoldtoNum);
				}
				System.out.println(RowIndex + " punchout pass");
				isPunchoutPass = true;

				if (FactoryAndData.BIZ.BizPunchout.testCheckout) {
					this.checkout();
					driver.switchTo().defaultContent();
					if (this.isElementExsit(driver, By.xpath("//div[@id='orderList']/h3")))
						isCheckoutPass = true;
					System.out.println(RowIndex + " checkout pass");
				}
			} catch (Throwable e) {
				System.out.println(RowIndex + " Punchout Fail! " + e.getMessage());
			}
		} catch (Throwable e) {
			System.out.println(RowIndex + " Exception!  " + e.getMessage());
		}
		saveResult();
	}

	private void testOCI() {
		try {
			driver.get(this.Url);
			B2BPunchoutPage page = new B2BPunchoutPage(driver);
			page.OCI_UserNameTextBox.clear();
			page.OCI_UserNameTextBox.sendKeys(this.ID);
			page.OCI_PasswordTextBox.clear();
			page.OCI_PasswordTextBox.sendKeys(this.Password);
			if (!this.InboundKey.equals("")) {
				page.OCI_InboundKeyTextBox.clear();
				page.OCI_InboundKeyTextBox.sendKeys(this.InboundKey);
			}
			if (!this.InboundValue.equals("")) {
				page.OCI_InboundValueTextBox.clear();
				page.OCI_InboundValueTextBox.sendKeys(this.InboundValue);
			}
			page.OCI_PunchoutButton.click();

			try {
				Thread.sleep(30000);
				this.switchWindow(driver);

				if (this.getSoldtoNum(page).equals(this.SoldtoNum)) {
					System.out.println(RowIndex + " sold to correct");
					isSoldToPass = true;
				} else {
					System.out.println(RowIndex + " Solto Number Wrong!" + " Real:" + this.getSoldtoNum(page) + " "
							+ this.SoldtoNum);
				}
				System.out.println(RowIndex + " punchout pass");
				isPunchoutPass = true;

				if (FactoryAndData.BIZ.BizPunchout.testCheckout) {
					this.checkout();
					this.switchWindow(driver);
					if (this.isElementExsit(driver, By.xpath("//div[@id='orderList']/h3")))
						isCheckoutPass = true;
					System.out.println(RowIndex + " checkout pass");
				}
			} catch (Throwable e) {
				System.out.println(RowIndex + " Punchout Fail! " + e.getMessage());
			}
		} catch (Throwable e) {
			System.out.println(RowIndex + " Exception!  " + e.getMessage());
		}
		saveResult();
	}

	private void testOXML() {
		try {
			driver.get(this.Url);
			B2BPunchoutPage page = new B2BPunchoutPage(driver);
			String xml = page.Oracle_LoginRequestTextBox.getText();
			xml = xml.replace("<username />", "<username>" + this.ID + "</username>");
			xml = xml.replace("<password>welcome</password>", "<password>" + this.Password + "</password>");

			if (!this.InboundValue.equals("")) {
				xml = xml.replace("<operatingUnit>204</operatingUnit>",
						"<operatingUnit>" + this.InboundValue + "</operatingUnit>");
			}
			page.Oracle_LoginRequestTextBox.clear();
			page.Oracle_LoginRequestTextBox.sendKeys(xml);
			page.Oracle_PunchoutButton.click();

			try {
				Common.checkElementExists(driver, page.Oracle_Frame, 120);
				driver.switchTo().frame(page.Oracle_Frame);

				if (this.getSoldtoNum(page).equals(this.SoldtoNum)) {
					System.out.println(RowIndex + " sold to correct");
					isSoldToPass = true;
				} else {
					System.out.println(RowIndex + " Solto Number Wrong!" + " Real:" + this.getSoldtoNum(page) + " "
							+ this.SoldtoNum);
				}
				System.out.println(RowIndex + " punchout pass");
				isPunchoutPass = true;

				if (FactoryAndData.BIZ.BizPunchout.testCheckout) {
					this.checkout();
					driver.switchTo().defaultContent();
					if (this.isElementExsit(driver, By.xpath("//div[@id='orderList']/h3")))
						isCheckoutPass = true;
					System.out.println(RowIndex + " checkout pass");
				}
			} catch (Throwable e) {
				System.out.println(RowIndex + " Punchout Fail! " + e.getMessage());
			}
		} catch (Throwable e) {
			System.out.println(RowIndex + " Exception!  " + e.getMessage());
		}
		saveResult();
	}

	@BeforeClass
	private void setupDriver() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().window().maximize();

		page = new B2BPage(driver);
	}

	private String getSoldtoNum(B2BPunchoutPage page) {
		Common.checkElementExists(driver, page.Comm_Logo, 120);
		return page.Comm_Logo.getAttribute("href").split("/")[7].replaceAll("[?]isPunchout=true", "");
	}

	private String getProtocol(String url) {
		return url.split("/")[url.split("/").length - 1];
	}

	private void switchWindow(WebDriver driver) {
		String currentWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		handles.remove(currentWindow);
		driver.switchTo().window(handles.iterator().next());
		driver.manage().window().maximize();
	}

	@AfterClass
	private void close() {
		driver.quit();
	}

	private void saveResult() {
		outputData.add(new String[] { RowIndex, isPunchoutPass ? "Success" : "Fail", isSoldToPass ? "Success" : "Fail",
				isCheckoutPass ? "Success" : "Fail", PartNum });
	}

	@AfterSuite
	private void pushResult() {
		try {
			FileInputStream excelFileInPutStream = new FileInputStream(FactoryAndData.BIZ.BizPunchout.filePath);
			Workbook workbook = WorkbookFactory.create(excelFileInPutStream);
			Sheet sheet = workbook.getSheetAt(0);

			int rowIndex;
			String punchOut, soldTo, checkOut, partNum;
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
				punchOut = outputData.get(i)[1];
				soldTo = outputData.get(i)[2];
				checkOut = outputData.get(i)[3];
				partNum = outputData.get(i)[4];

				try {
					// Punchout result
					sheet.getRow(rowIndex).getCell(8).setCellValue(punchOut);
					if (punchOut.equals("Success"))
						sheet.getRow(rowIndex).getCell(8).setCellStyle(greenStyle);
					else
						sheet.getRow(rowIndex).getCell(8).setCellStyle(yellowStyle);
				} catch (Exception e) {
				}
				try {
					// Soldto result
					sheet.getRow(rowIndex).getCell(9).setCellValue(soldTo);
					if (soldTo.equals("Success"))
						sheet.getRow(rowIndex).getCell(9).setCellStyle(greenStyle);
					else
						sheet.getRow(rowIndex).getCell(9).setCellStyle(yellowStyle);
				} catch (Exception e) {
				}
				try {
					// Checkout result
					if(FactoryAndData.BIZ.BizPunchout.testCheckout)
					{
					sheet.getRow(rowIndex).getCell(10).setCellValue(checkOut);
					sheet.getRow(rowIndex).getCell(11).setCellValue(partNum);
					if (checkOut.equals("Success")) {
						sheet.getRow(rowIndex).getCell(10).setCellStyle(greenStyle);
						sheet.getRow(rowIndex).getCell(11).setCellStyle(greenStyle);
					} else {
						sheet.getRow(rowIndex).getCell(10).setCellStyle(yellowStyle);
						sheet.getRow(rowIndex).getCell(11).setCellStyle(yellowStyle);
					}
					}else
					{
						sheet.getRow(rowIndex).getCell(10).setCellValue("Skipped");
						sheet.getRow(rowIndex).getCell(11).setCellValue("Skipped");
						sheet.getRow(rowIndex).getCell(10).setCellStyle(blankStyle);
						sheet.getRow(rowIndex).getCell(11).setCellStyle(blankStyle);
					}
				} catch (Exception e) {
				}
			}

			FileOutputStream excelFileOutPutStream = new FileOutputStream(FactoryAndData.BIZ.BizPunchout.filePath);
			workbook.write(excelFileOutPutStream);
			excelFileOutPutStream.flush();
			excelFileOutPutStream.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String fileName = "\\\\10.62.6.95\\B2BPunchout\\AutoTestInProgress.txt";
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
			fileName = "\\\\10.62.6.95\\B2BPunchout\\AutoTestDone.txt";
			file = new File(fileName);

			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkout() throws InterruptedException {

		By CustomiseAdd = By.xpath(".//*[@id='resultList']/div/div[4]/a");
		By customiseAddtoCart = By.xpath("//button[@class='add-to-cart']");
		By AddtoCartStatus = By.xpath("//h2[contains(text(),'Adding to Cart')]");
		By closeBundle = By.xpath(".//*[@id='bundleAlert']/div/div/div[1]/button");
		By warningAdd = By.xpath("//button[@id='b_alert_add_to_cart']");

		try {
			page.HomePage_productsLink.click();
			page.HomePage_Laptop.click();
			if (this.isElementExsit(driver, CustomiseAdd)) {
				page.customize.click();
				Thread.sleep(6000);
				if (this.isElementExsit(driver, warningAdd) && page.warningAdd.isDisplayed()) {
					System.out.println(3);
					driver.findElement(By.xpath("//button[@id='b_alert_add_to_cart']")).click();
					Thread.sleep(6000);
					try {
						if (page.getAddtoCartPB.isDisplayed() && page.getAddtoCartPB.isEnabled()) {
							page.getAddtoCartPB.click();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					if (this.isElementExsit(driver, customiseAddtoCart)) {
						((JavascriptExecutor) driver).executeScript("scroll(0,250)");
						page.agreementsAddToCart.click();
						Thread.sleep(3000);
						try {
							if (page.accessoryTabAdd.isEnabled()) {
								page.accessoryTabAdd.click();
							}
						} catch (Exception e) {
							System.out.println("accessoryTabAdd not exist");
						}
					} else {
						if (this.isElementExsit(driver, closeBundle)) {
							driver.findElement(By.xpath(".//*[@id='bundleAlert']/div/div/div[1]/button")).click();
						}
						page.CTOAddtoCartPDP.click();
						if (page.getAddtoCartPB.isEnabled()) {
							page.getAddtoCartPB.click();
						}
					}
				}
			} else {
				page.addToCartBtn.click();
				page.addtoCartPOP.click();
				new WebDriverWait(driver, 500).until(ExpectedConditions.invisibilityOfElementLocated(AddtoCartStatus));
				Thread.sleep(12000);
				page.goToCartPop.click();
			}
			new WebDriverWait(driver, 500).until(ExpectedConditions.elementToBeClickable(page.lenovoCheckout));
			PartNum = page.cartPage_partnum.getText().replace("Part No.", "");
			page.lenovoCheckout.click();
		} catch (Exception e) {
			System.out.println(RowIndex + " checkout fail");
			System.out.println(RowIndex + " Exception!  " + e.getMessage());
		}
	}

	private boolean isElementExsit(WebDriver driver, By locator) {
		boolean flag = false;
		try {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			WebElement element = driver.findElement(locator);
			flag = null != element;
		} catch (Exception e) {
		}
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		return flag;
	}

}
