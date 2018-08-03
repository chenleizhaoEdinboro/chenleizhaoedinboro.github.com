package TestScript.B2C;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class PartNoRetrieval extends SuperTestClass {
	private boolean loginFlag = false;
	private String ProductCategory;
	private String XMLLocation;
	public B2CPage b2cPage;
	private List<WebElement> laptopPartNo;
	private String temp;
	private int result;
	private String InsertStmt;
	private PreparedStatement ps;

	public PartNoRetrieval(String store, String Category) {
		this.Store = store;
		this.ProductCategory = Category;
		this.testName = "PartNoRetrieval";
	}

	@Test(alwaysRun= true)
	public void getPartNumber(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://100.67.28.132:3306/necommerce-auto-report",
					"root", "admin");

			// contains product series url
			String[] seriesURL;
			// contains actual products url after view series
			ArrayList<String> learnMoreURL;
			String SeriesName;
			String SeriesProduct;
			// Control multiple pages
			By nextPage = By.xpath("//a[contains(text(),'Next')]");
			// identify items not found page
			By itemNotFound = By.xpath(".//*[@id='ourApologisePage']/span");
			// Identify when models are availible
			By ModelTitle = By
					.xpath(".//*[@id='tab-customize']/div[2]/div[2]/ol//div[1]/h3/a");
			// Verify subseries are availible
			By locator4 = By
					.xpath(".//*[@id='mainContent']/div[3]/ol//div[4]/a");
			By locator5 = By
					.xpath(".//*[@id='mainContent']/div[3]/ol//div[5]/a");
			By ViewAccessory = By
					.xpath("//div[@class='facetedResults-footer']//a[contains(text(),'View')]");
			By locator6 = By
					.xpath(".//*[@id='mainContent']/div[1]/div/div[2]/p[2]/span");
			By ViewAndCustomize = By.xpath(".//*[@id='view-customize']");
			By LearnMoreText = By.xpath("//a[contains(text(),'Learn more')]");
			XMLLocation = "C:\\jenkins\\workspace\\ProductRetrieve\\AUB2C"
					+ ProductCategory + ".xml";

			System.out.println(Store + " " + ProductCategory + " " + "CTO="
					+ testData.B2C.getDefaultCTOPN() + " MTM="
					+ testData.B2C.getDefaultMTMPN());
			System.out.println(Store + " " + ProductCategory + " " + "CTO="
					+ testData.B2C.getDefaultCTOPN() + " MTM="
					+ testData.B2C.getDefaultMTMPN());
			driver.get(testData.B2C.getHomePageUrl());
			driver.manage().window().maximize();
			closePromotion(driver, b2cPage);
			new WebDriverWait(driver, 300).until(ExpectedConditions
					.presenceOfElementLocated(By
							.xpath("//a[contains(@href,'menu-id=Products')]")));
			b2cPage.Navigation_ProductsLink.click();
			Thread.sleep(2000);
			if (ProductCategory.equals("Laptops")) {
				b2cPage.Navigation_Laptop.click();
			} else if (ProductCategory.equals("Tablet")) {
				b2cPage.Navigation_Tablets.click();
			} else if (ProductCategory.equals("Desktop")) {
				b2cPage.Navigation_Desktops.click();
			} else {
				b2cPage.Navigation_Accessory.click();
			}

			new WebDriverWait(driver, 300).until(ExpectedConditions
					.presenceOfElementLocated(By
							.cssSelector("h1.bar_3-heading")));

			SeriesName = b2cPage.ProductCategory.getText().toString();

			Thread.sleep(5000);

			// to test accessory disable tablets and desktop
			if (!(SeriesName.equals("Lenovo Services and Warranty")
					|| SeriesName.equals("Workstations") || SeriesName
						.equals("Systems: Servers, Storage, Networking"))) {
				seriesURL = Common.getSeriesURL(b2cPage, driver, SeriesName);

				for (int j = 0; j < seriesURL.length; j++) {
					if (!StringContain(seriesURL[j], "lenovo.com")) {
						// depends on environment
						driver.get(testData.envData.getHttpsDomain()
								+ seriesURL[j]);

					} else {
						driver.get(seriesURL[j]);
					}
					new WebDriverWait(driver, 300).until(ExpectedConditions
							.presenceOfElementLocated(By
									.cssSelector(".bar_3-heading")));
					SeriesProduct = b2cPage.ProductCategory.getText()
							.toString();

					if (Common.isElementExist(driver, locator4)
							|| Common.isElementExist(driver, locator5)
							|| Common.isElementExist(driver, ViewAccessory)
							|| Common.isElementExist(driver, LearnMoreText)) {
						learnMoreURL = getLearnMoreURL(b2cPage, driver,
								SeriesProduct, nextPage);

						for (int q = 0; q < learnMoreURL.size(); q++) {
							if (!(SeriesName.equals("Accessories and upgrades")
									|| SeriesName
											.equals("Compare and Buy Laptops") || SeriesName
										.equals("Laptops"))) {

								driver.get(learnMoreURL.get(q));

								if (!Common
										.isElementExist(driver, itemNotFound)) {

									new WebDriverWait(driver, 300)
											.until(ExpectedConditions
													.presenceOfElementLocated(By
															.cssSelector(".bar_3-heading")));

									if (Common.isElementExist(driver,
											ModelTitle)) {
										List<WebElement> DetailedProduct = driver
												.findElements(By
														.xpath(".//*[@id='tab-customize']/div[2]/div[2]/ol//div[1]/h3/a"));

										for (WebElement e : DetailedProduct) {

											InsertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,TimeAdded ) values('"
													+ Store
													+ "','"
													+ ProductCategory
													+ "','"
													+ TrimAccessoryType(SeriesProduct)
													+ "','"
													+ getProductNo(e
															.getAttribute(
																	"href")
															.toString())
													+ "','"
													+ getStringDateShort(0)
													+ "');";
											ps = conn
													.prepareStatement(InsertStmt);
											result = ps.executeUpdate();
										}

									} else if (Common.isElementExist(driver,
											locator6)) {
										List<WebElement> DetailedProduct = driver
												.findElements(By
														.xpath(".//*[@id='mainContent']/div[1]/div/div[2]/p[2]/span"));
										System.out.println("locator6 "
												+ DetailedProduct.size());
										for (WebElement e : DetailedProduct) {

											InsertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,TimeAdded ) values('"
													+ Store
													+ "','"
													+ ProductCategory
													+ "','"
													+ TrimAccessoryType(SeriesProduct)
													+ "','"
													+ getProductNo(e
															.getAttribute(
																	"href")
															.toString())
													+ "','"
													+ getStringDateShort(0)
													+ "');";
											ps = conn
													.prepareStatement(InsertStmt);
											result = ps.executeUpdate();
										}

									} else if (Common.isElementExist(driver,
											ViewAndCustomize)) {

										InsertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,TimeAdded ) values('"
												+ Store
												+ "','"
												+ ProductCategory
												+ "','"
												+ TrimAccessoryType(SeriesProduct)
												+ "','"
												+ getProductNo(learnMoreURL
														.get(q))
												+ "','"
												+ getStringDateShort(0) + "');";
										ps = conn.prepareStatement(InsertStmt);
										result = ps.executeUpdate();

									} else {

										InsertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,TimeAdded ) values('"
												+ Store
												+ "','"
												+ ProductCategory
												+ "','"
												+ TrimAccessoryType(SeriesProduct)
												+ "','"
												+ getProductNo(learnMoreURL
														.get(q))
												+ "','"
												+ getStringDateShort(0) + "');";
										ps = conn.prepareStatement(InsertStmt);
										result = ps.executeUpdate();

									}
								} else {

									System.out.println("SubSeries"
											+ getProductNo(learnMoreURL.get(q)
													+ "Does not exist"));
								}

							} else if (SeriesName
									.equals("Compare and Buy Laptops")
									|| SeriesName.equals("Laptops")) {

								if (!StringContain(learnMoreURL.get(q),
										"lenovo.com")) {
									// depends on environment
									driver.get(testData.envData.getHttpsDomain()
											+ learnMoreURL.get(q));

								} else {
									driver.get(learnMoreURL.get(q));
								}
								Thread.sleep(5000);
								laptopPartNo = driver
										.findElements(By
												.xpath(".//*[@id='addToCartButtonTop' and @onclick!='']"));
								for (WebElement e : laptopPartNo) {
									temp = getLapTopPartNo(e
											.getAttribute("onclick"));
									if (temp.contains("CTO")) {
										/*
										 * AddXMLElement( temp, "CTO_" +
										 * TrimAccessoryType(SeriesProduct),
										 * XMLLocation);
										 */
										InsertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,TimeAdded ) values('"
												+ Store
												+ "','"
												+ "CTO"
												+ "','"
												+ SeriesProduct
												+ "','"
												+ temp
												+ "','"
												+ getStringDateShort(0)
												+ "');";
										ps = conn.prepareStatement(InsertStmt);
										result = ps.executeUpdate();

									} else {
										InsertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,TimeAdded ) values('"
												+ Store
												+ "','"
												+ "MTM"
												+ "','"
												+ SeriesProduct
												+ "','"
												+ temp
												+ "','"
												+ getStringDateShort(0)
												+ "');";
										ps = conn.prepareStatement(InsertStmt);
										result = ps.executeUpdate();
									}

								}

							} else {
								// apology page
								// Common.AddXMLElement("Product Does not exist","SubSeries"+Common.getProductNo(learnMoreURL.get(q)),
								// XMLListLocationA);
								/*
								 * AddXMLElement(
								 * getProductNo(learnMoreURL.get(q)),
								 * TrimAccessoryType(b2cPage.ProductCategory
								 * .getText().toString()), XMLLocation);
								 */
								InsertStmt = "INSERT INTO product_number_b2c (Country,ProductType,ProductName,ProductNO,TimeAdded ) values('"
										+ Store
										+ "','"
										+ ProductCategory
										+ "','"
										+ TrimAccessoryType(b2cPage.ProductCategory
												.getText().toString())
										+ "','"
										+ getProductNo(learnMoreURL.get(q))
										+ "','" + getStringDateShort(0) + "');";
								ps = conn.prepareStatement(InsertStmt);
								result = ps.executeUpdate();
							}
						}
					} else {
						/*
						 * AddXMLElement("does NOT have subseries",
						 * TrimAccessoryType(SeriesProduct), XMLLocation);
						 */
						System.out.println(TrimAccessoryType(SeriesProduct)
								+ "does NOT have subseries");
					}

				}

			}
			conn.close();
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

	public boolean StringContain(String Source, String sub) {
		if (Source.indexOf(sub) != -1) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<String> getLearnMoreURL(B2CPage page, WebDriver driver,
			String type, By locator) {
		// String[] urlArray=null;
		ArrayList<String> urlArray = new ArrayList<String>();
		List<WebElement> urlItems;
		By Lernmore = By.xpath(".//*[@id='mainContent']/div[3]/ol//div[4]/a");
		By LearnmoreAccessory = By
				.xpath(".//*[@id='mainContent']/div[3]/ol//div[5]/a");
		By ViewAccessory = By
				.xpath("//div[@class='facetedResults-footer']//a[contains(text(),'View')]");
		By ViewNext = By.xpath("(//a[contains(text(),'Next Page')])[1]");
		By LearnMoreText = By.xpath("//a[contains(text(),'Learn more')]");
		if (Common.isElementExist(driver, Lernmore)) {

			urlItems = driver.findElements(By
					.xpath(".//*[@id='mainContent']/div[3]/ol//div[4]/a"));

			for (WebElement e : urlItems) {
				urlArray.add(e.getAttribute("href").toString());
			}

			while (Common.isElementExist(driver, locator)) {
				page.NextPage.click();
				new WebDriverWait(driver, 300).until(ExpectedConditions
						.presenceOfElementLocated(By
								.cssSelector(".bar_3-heading")));
				urlItems = driver.findElements(By
						.xpath(".//*[@id='mainContent']/div[3]/ol//div[4]/a"));
				for (WebElement e : urlItems) {
					urlArray.add(e.getAttribute("href").toString());
				}

			}
		}

		else if (Common.isElementExist(driver, LearnmoreAccessory)) {

			urlItems = driver.findElements(By
					.xpath(".//*[@id='mainContent']/div[3]/ol//div[5]/a"));

			for (WebElement e : urlItems) {
				urlArray.add(e.getAttribute("href").toString());
			}
			while (Common.isElementExist(driver, locator)) {
				page.NextPage.click();
				new WebDriverWait(driver, 300).until(ExpectedConditions
						.presenceOfElementLocated(By
								.cssSelector(".bar_3-heading")));
				urlItems = driver.findElements(By
						.xpath(".//*[@id='mainContent']/div[3]/ol//div[5]/a"));
				for (WebElement e : urlItems) {
					urlArray.add(e.getAttribute("href").toString());
				}

			}
		} else if (Common.isElementExist(driver, ViewAccessory)) {

			urlItems = driver
					.findElements(By
							.xpath("//div[@class='facetedResults-footer']//a[contains(text(),'View')]"));

			for (WebElement e : urlItems) {
				urlArray.add(e.getAttribute("href").toString());
			}
			while (Common.isElementExist(driver, ViewNext)) {
				WebElement ViewNextPage = driver.findElement(ViewNext);
				ViewNextPage.click();
				new WebDriverWait(driver, 300).until(ExpectedConditions
						.presenceOfElementLocated(By
								.cssSelector(".bar_3-heading")));
				urlItems = driver
						.findElements(By
								.xpath("//div[@class='facetedResults-footer']//a[contains(text(),'View')]"));
				for (WebElement e : urlItems) {
					urlArray.add(e.getAttribute("href").toString());
				}

			}
		} else if (Common.isElementExist(driver, LearnMoreText)) {

			urlItems = driver.findElements(By
					.xpath("//a[contains(text(),'Learn more')]"));

			for (WebElement e : urlItems) {
				urlArray.add(e.getAttribute("href").toString());
			}

			while (Common.isElementExist(driver, locator)) {
				page.NextPage.click();
				new WebDriverWait(driver, 300).until(ExpectedConditions
						.presenceOfElementLocated(By
								.cssSelector(".bar_3-heading")));
				urlItems = driver.findElements(By
						.xpath("//a[contains(text(),'Learn more')]"));
				for (WebElement e : urlItems) {
					urlArray.add(e.getAttribute("href").toString());
				}

			}
		}

		return urlArray;
	}

	public String getProductNo(String hrefValue) {
		String[] productNoArray = hrefValue.split("/");
		return productNoArray[productNoArray.length - 1];
	}

	public String TrimAccessoryType(String sourceString) {
		String result = sourceString.replaceAll("&", "and");
		result = result.replaceAll("\\|", "");
		result = result.replaceAll(",", "");
		result = result.replaceAll(":", "");
		result = result.replaceAll(" ", "");
		result = result.replaceAll("\\(", "");
		result = result.replaceAll("\\)", "");
		result = result.replaceAll("\n", "");
		result = result.replaceAll("\\/", "");
		if (StartOfString(result)) {
			result = "Model" + result;
		}
		return result;
	}

	public boolean StartOfString(String source) {

		int indexOfNum = 0;
		for (; indexOfNum < source.length(); indexOfNum++) {
			if (!Character.isDigit(source.charAt(indexOfNum))) {
				break;
			}
		}
		if (indexOfNum != 0) {// start with number
			return true;
		} else {
			return false;
		}
	}

	public void AddXMLElement(String partnum, String productType,
			String XMLLocation) {
		XMLWriter writer = null;
		SAXReader reader = new SAXReader();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");
		String filepath = XMLLocation;
		File xmlfile = new File(filepath);
		try {
			Document xmlDocument = reader.read(xmlfile);
			Element root = xmlDocument.getRootElement();
			Element part = root.addElement("product");
			part.addAttribute(productType, partnum);
			writer = new XMLWriter(new FileWriter(filepath), format);
			writer.write(xmlDocument);
			writer.close();
		} catch (Exception e) {

		}

	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public static String getLapTopPartNo(String source) {
		String result = "";
		String[] splitSource;
		splitSource = source.split("Top");
		if (splitSource.length > 0) {
			result = splitSource[splitSource.length - 1];
		}
		return result.replaceAll("\\)", "").replaceAll("\\'", "");

	}

	public static String getStringDateShort(int gap) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, gap);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}
}
