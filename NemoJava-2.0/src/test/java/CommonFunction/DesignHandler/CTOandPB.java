package CommonFunction.DesignHandler;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import CommonFunction.Common;
import Pages.B2CPage;
import junit.framework.Assert;

public class CTOandPB {
	public static DecimalFormat DFOrigin = new DecimalFormat();

	/**
	 * @Owner Songli
	 * @Parameter WebDriver
	 * @Usage Add a CTO product into cart via OLD CTO/PB design, with default
	 *        CV, and check product is added successfully
	 */
	public static void addToCartFromCTOOld(WebDriver driver)
			throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		B2CPage b2cPage = new B2CPage(driver);

		if (driver.getCurrentUrl().contains("CTO1")) {
			System.out.println("the product added to cart is an CTO product");
			WebElement addToCartBtn = driver.findElement(By
					.xpath("//button[contains(@class, 'add-to-cart')]"));
			Common.javascriptClick(driver, addToCartBtn);
		} else {
			System.out.println("the product added to cart is an MTM product");
		}
		Thread.sleep(3000);
		js.executeScript("window.scrollTo(0, -1600)");
		Thread.sleep(3000);
		while (Common.isElementExist(driver,
				By.xpath("//button[@data-final = 'false']"))) {
			driver.findElement(By.xpath("//button[@data-final = 'false']"))
					.click();
		}
		if (Common.isElementExist(driver,
				By.xpath("//button[@data-final = 'true']"))) {
			driver.findElement(By.xpath("//button[@data-final = 'true']"))
					.click();
		}
		new WebDriverWait(driver, 80).until(ExpectedConditions
				.elementToBeClickable(b2cPage.Cart_CheckoutButton));
		Thread.sleep(5000);
		List<WebElement> cartList = driver.findElements(By
				.xpath("//div[@class='cart-item']"));
		if (cartList.size() == 0)
			Assert.fail("Product is not added to cart");
	}

	/**
	 * @Owner Songli
	 * @Parameter WebDriver
	 * @Usage Add a CTO product into cart via OLD CTO/PB design, with default
	 *        CV, and check product is added successfully
	 */
	public static String addToCartFromCTOOld(WebDriver driver,
			boolean ifChangeCV, boolean ifAddWarranty, boolean ifAddSoftware,
			boolean ifAddAccessory) throws InterruptedException {

		B2CPage b2cPage = new B2CPage(driver);
		String result = "";

		if (driver.getCurrentUrl().contains("CTO1")) {
			System.out.println("the product added to cart is an CTO product");
			if (ifChangeCV) {
				// Expand all group list
				if (Common.isElementExist(driver,
						By.xpath(".//h4[@class='collapsed']"), 2)) {
					List<WebElement> collapsedGroups = driver.findElements(By
							.xpath(".//h4[@class='collapsed']"));
					// Expand
					for (int x = 0; x < collapsedGroups.size(); x++) {
						collapsedGroups.get(x).click();
					}
				}
				String groupsXpath = "//div[contains(@id,'list_group_') and contains(@class,'true')]";
				List<WebElement> groups = driver.findElements(By
						.xpath(groupsXpath));
				List<WebElement> items;
				boolean isDone = false;
				for (int x = 0; x < groups.size(); x++) {
					if (isDone)
						break;
					items = groups.get(x).findElements(
							By.xpath(".//tr[not(contains(@class,'hidden'))]"));
					if (items.get(0).getAttribute("class")
							.contains("radio-item")) {
						// Radio button group
						for (int y = 0; y < items.size(); y++) {
							if (!items
									.get(y)
									.findElement(
											By.xpath(".//input[@type='radio']"))
									.isSelected()) {
								// click ratio button only if it is not selected
								// yet
								Common.javascriptClick(
										driver,
										items.get(y)
												.findElement(
														By.xpath(".//input[@type='radio']")));
								Thread.sleep(1000);
								// check group title is changed
								Common.waitElementText(
										driver,
										By.xpath(groupsXpath + "[" + (x + 1)
												+ "]/h4/span[3]"),
										"- "
												+ items.get(y)
														.findElement(
																By.xpath(".//span[@class='label-text']"))
														.getText());
								Thread.sleep(1000);
								result = items
										.get(y)
										.findElement(
												By.xpath(".//span[@class='label-text']"))
										.getText();
								isDone = true;
								break;
							}
						}
					}
				}
			}

			WebElement addToCartBtn = driver.findElement(By
					.xpath("//button[contains(@class, 'add-to-cart')]"));
			Common.javascriptClick(driver, addToCartBtn);
		} else {
			System.out.println("the product added to cart is an MTM product");
		}

		// PB
		Thread.sleep(3000);
		result = result + "||";
		if (ifAddWarranty) {
			if (Common.isElementExist(driver,
					By.xpath("//div[@data-tabcode='Warranty']"), 2)) {
				// click warranty tab header
				driver.findElement(
						By.xpath("//li[contains(@class,'stepsItem-item') and @data-tabname='"
								+ driver.findElement(
										By.xpath("//div[@data-tabcode='Warranty']"))
										.getAttribute("data-tabname") + "']"))
						.click();
				List<WebElement> lineItems;
				lineItems = driver
						.findElement(
								By.xpath("//div[@data-tabcode='Warranty']"))
						.findElements(
								By.xpath(".//div[contains(@class,'configuratorItem') and contains(@class,'group-frame') and not(contains(@class,'hide'))]//li[contains(@class,'configuratorItem-optionList-option')]"));
				for (int y = 0; y < lineItems.size(); y++) {
					if (!lineItems.get(y).findElement(By.xpath(".//input"))
							.isSelected()) {
						Common.javascriptClick(driver, lineItems.get(y)
								.findElement(By.xpath(".//input")));
						Thread.sleep(1000);
						result = result
								+ lineItems.get(y)
										.findElement(By.xpath(".//input"))
										.getAttribute("id");
						break;
					}
				}
			}
		}
		result = result + "||";
		if (ifAddSoftware) {
			if (Common.isElementExist(driver,
					By.xpath("//div[@data-tabcode='Software']"), 2)) {
				// click warranty tab header
				driver.findElement(
						By.xpath("//li[contains(@class,'stepsItem-item') and @data-tabname='"
								+ driver.findElement(
										By.xpath("//div[@data-tabcode='Software']"))
										.getAttribute("data-tabname") + "']"))
						.click();
				List<WebElement> lineItems;
				lineItems = driver
						.findElement(
								By.xpath("//div[@data-tabcode='Software']"))
						.findElements(
								By.xpath(".//div[contains(@class,'configuratorItem') and contains(@class,'group-frame') and not(contains(@class,'hide'))]//li[contains(@class,'configuratorItem-optionList-option')]"));
				for (int y = 0; y < lineItems.size(); y++) {
					if (!lineItems.get(y).findElement(By.xpath(".//input"))
							.isSelected()) {
						Common.javascriptClick(driver, lineItems.get(y)
								.findElement(By.xpath(".//input")));
						Thread.sleep(1000);
						result = result
								+ lineItems.get(y)
										.findElement(By.xpath(".//input"))
										.getAttribute("id");
						break;
					}
				}
			}
		}
		result = result + "||";
		if (ifAddAccessory) {
			if (Common.isElementExist(driver,
					By.xpath("//div[@data-tabcode='Accessories']"), 2)) {
				// click warranty tab header
				driver.findElement(
						By.xpath("//li[contains(@class,'stepsItem-item') and @data-tabname='"
								+ driver.findElement(
										By.xpath("//div[@data-tabcode='Accessories']"))
										.getAttribute("data-tabname") + "']"))
						.click();
				List<WebElement> lineItems;
				lineItems = driver
						.findElement(
								By.xpath("//div[@data-tabcode='Accessories']"))
						.findElements(
								By.xpath(".//div[contains(@class,'configuratorItem') and contains(@class,'group-frame') and not(contains(@class,'hide'))]//li[contains(@class,'configuratorItem-optionList-option')]"));
				for (int y = 0; y < lineItems.size(); y++) {
					if (!lineItems.get(y).findElement(By.xpath(".//input"))
							.isSelected()) {
						Common.javascriptClick(driver, lineItems.get(y)
								.findElement(By.xpath(".//input")));
						Thread.sleep(1000);
						result = result
								+ lineItems.get(y)
										.findElement(By.xpath(".//input"))
										.getAttribute("id");
						break;
					}
				}
			}
		}

		while (Common.isElementExist(driver,
				By.xpath("//button[@data-final = 'false']"))) {
			driver.findElement(By.xpath("//button[@data-final = 'false']"))
					.click();
		}
		if (Common.isElementExist(driver,
				By.xpath("//button[@data-final = 'true']"))) {
			driver.findElement(By.xpath("//button[@data-final = 'true']"))
					.click();
		}
		new WebDriverWait(driver, 80).until(ExpectedConditions
				.elementToBeClickable(b2cPage.Cart_CheckoutButton));
		Thread.sleep(5000);
		List<WebElement> cartList = driver.findElements(By
				.xpath("//div[@class='cart-item']"));
		if (cartList.size() == 0)
			Assert.fail("Product is not added to cart");
		return result;
	}

	/**
	 * @Owner Songli
	 * @Parameter WebDriver
	 * @Usage Add a CTO product into cart via NEW CTO/PB design, with default
	 *        CV, and check product is added successfully
	 */
	public static void addToCartFromCTONew(WebDriver driver)
			throws InterruptedException {
		B2CPage b2cPage = new B2CPage(driver);

		if (driver.getCurrentUrl().contains("CTO1")) {
			System.out.println("the product added to cart is an CTO product");
			b2cPage.cto_AddToCartButton.click();
		} else {
			System.out.println("the product added to cart is an MTM product");
		}

		Thread.sleep(3000);
		if (Common.checkElementDisplays(driver,
				By.xpath("//button[@data-final = 'true']"), 3)) {

			driver.findElement(By.xpath("//button[@data-final = 'true']"))
					.click();
		}

		else {

			try {
				driver.findElement(By.id("CTO_addToCart")).click();
			} catch (Exception e) {
				
				while (clickContinue(driver)) {

				}
				if (driver.findElement(
						By.xpath("//span[contains(text(),'Add to Cart')]"))
						.isDisplayed()) {

					driver.findElement(
							By.xpath("//span[contains(text(),'Add to Cart')]"))
							.click();
				} else {

					b2cPage.cto_AddToCartButton.click();
				}
			}

		}

		new WebDriverWait(driver, 80).until(ExpectedConditions
				.elementToBeClickable(b2cPage.Cart_CheckoutButton));
		Thread.sleep(5000);
		List<WebElement> cartList = driver.findElements(By
				.xpath("//div[@class='cart-item']"));
		if (cartList.size() == 0)
			Assert.fail("Product is not added to cart");
	}

	/**
	 * @Owner Songli
	 * @Parameter WebDriver
	 * @Usage Add a CTO product into cart via NEW CTO/PB design, with option to
	 *        change CV/add warranty/add accessory, and check product is added
	 *        successfully
	 */
	public static String addToCartFromCTONew(WebDriver driver,
			boolean ifChangeCV, boolean ifAddWarranty, boolean ifAddSoftware,
			boolean ifAddAccessory) throws InterruptedException {
		B2CPage b2cPage = new B2CPage(driver);
		String result = "";
		// CTO page
		if (driver.getCurrentUrl().contains("CTO1")) {
			System.out.println("the product added to cart is an CTO product");
			if (ifChangeCV) {
				b2cPage.newCTO_ExpandAll.click();
				Thread.sleep(3000);

				List<WebElement> items;
				items = driver
						.findElements(By
								.xpath(".//div[contains(@class,'group-item') and @iterate='components' and not(contains(@class,'hidden'))]"));
				for (int x = 0; x < items.size(); x++) {
					if (!items
							.get(x)
							.findElement(
									By.xpath(".//i[contains(@class,'fa-check-circle')]"))
							.isDisplayed()
							&& !items.get(x).getText().equals("")) {
						// if not selected
						Common.javascriptClick(
								driver,
								items.get(x)
										.findElement(
												By.xpath(".//div[contains(@class,'price')]")));
						Thread.sleep(3000);
						result = items.get(x)
								.findElement(By.xpath(".//div[1]")).getText();
						break;
					}
				}
			}
			b2cPage.cto_AddToCartButton.click();
		} else {
			System.out.println("the product added to cart is an MTM product");
		}
		result = result + "||"; // add splitter

		// PB page
		Thread.sleep(3000);
		if (ifAddWarranty) {
			if (Common.isElementExist(driver,
					By.xpath(".//*[@id='upgradeWarranty']//li[@value!=''][1]"),
					3)) {
				if (Common.checkElementExists(driver,
						b2cPage.newCTO_StackableWarrantyChangeButton, 1)) {
					Common.javascriptClick(driver,
							b2cPage.newCTO_StackableWarrantyChangeButton);
					Thread.sleep(3000);
					driver.findElement(
							By.xpath(".//*[@id='upgradeWarranty']//li[@value!=''][1]"))
							.click();
					result = result
							+ driver.findElement(
									By.xpath(".//*[@id='upgradeWarranty']//li[@value!=''][1]"))
									.getText();
				}
			} else if (Common.isElementExist(driver,
					By.xpath(".//header[@id='Warranty']"), 3)) {
				if (Common.checkElementDisplays(driver,
						b2cPage.newCTO_ExpandWarranty, 1)) {
					b2cPage.newCTO_ExpandWarranty.click();
					Thread.sleep(3000);
					List<WebElement> warrantyLines = driver
							.findElements(By
									.xpath(".//ul[contains(@class,'configuratorItem-optionList')]/li[not(contains(@class,'options-disable'))]"));
					for (int x = 0; x < warrantyLines.size(); x++) {
						if (!warrantyLines
								.get(x)
								.findElement(
										By.xpath(".//i[contains(@class,'fa-check-circle')]"))
								.isDisplayed()) {
							Common.javascriptClick(driver, warrantyLines.get(x)
									.findElement(By.xpath(".//label/div")));
							Thread.sleep(2000);
							result = result
									+ warrantyLines
											.get(x)
											.findElement(
													By.xpath(".//div/div[1]"))
											.getText();
							break;
						}
					}
				}
			}
		}
		result = result + "||"; // add splitter
		if (ifAddSoftware
				&& Common
						.isElementExist(
								driver,
								By.xpath("(.//div[@id='Software']//ul[contains(@class,'configuratorItem-optionList')])[1]/li[1]//input"),
								3)) {
			Common.javascriptClick(
					driver,
					driver.findElement(By
							.xpath("(.//div[@id='Software']//ul[contains(@class,'configuratorItem-optionList')])[1]/li[1]//input")));
			result = result
					+ driver.findElement(
							By.xpath("(.//div[@id='Software']//ul[contains(@class,'configuratorItem-optionList')])[1]/li[1]//input"))
							.getAttribute("id");
		}
		result = result + "||"; // add splitter
		if (ifAddAccessory
				&& Common
						.isElementExist(
								driver,
								By.xpath("(.//div[@id='Accessories']//ul[contains(@class,'configuratorItem-optionList')])[1]/li[1]//input"),
								3)) {
			Common.javascriptClick(
					driver,
					driver.findElement(By
							.xpath("(.//div[@id='Accessories']//ul[contains(@class,'configuratorItem-optionList')])[1]/li[1]//input")));
			result = result
					+ driver.findElement(
							By.xpath("(.//div[@id='Accessories']//ul[contains(@class,'configuratorItem-optionList')])[1]/li[1]//input"))
							.getAttribute("id");
		}

		Thread.sleep(3000);
		if (Common.checkElementDisplays(driver,
				By.xpath("//button[@data-final = 'true']"), 3))
			driver.findElement(By.xpath("//button[@data-final = 'true']"))
					.click();
		else
			driver.findElement(
					By.xpath("//button[contains(@class,'qa_AddToCartButton')]"))
					.click();
		// Common.javascriptClick(driver,
		// driver.findElement(By.xpath("//button[contains(@class,'qa_AddToCartButton')]")));
		// driver.findElement(By.xpath("//button[contains(@class,'qa_AddToCartButton')]")).click();

		new WebDriverWait(driver, 80).until(ExpectedConditions
				.elementToBeClickable(b2cPage.Cart_CheckoutButton));
		Thread.sleep(5000);
		List<WebElement> cartList = driver.findElements(By
				.xpath("//div[@class='cart-item']"));
		if (cartList.size() == 0)
			Assert.fail("Product is not added to cart");
		return result;
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get Web price float from OLD CTO page
	 */
	public static float getWebPriceFromCTOOld(B2CPage page)
			throws ParseException {
		return DFOrigin.parse(
				page.cto_WebPrice.getAttribute("innerText").replace(" ", "")
						.substring(1)).floatValue();
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get AfterSaving price float from OLD CTO page
	 */
	public static float getAfterSavingPriceFromCTOOld(B2CPage page)
			throws ParseException {
		String priceStr = "";
		if (!page.cto_AfterSavingPrice.getAttribute("innerText").equals("")) {
			priceStr = page.cto_AfterSavingPrice.getAttribute("innerText");
		} else {
			priceStr = page.ctoPrice.getAttribute("innerText");
		}
		return DFOrigin.parse(priceStr.replace(" ", "").substring(1))
				.floatValue();
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get YouSave price float from OLD CTO page
	 */
	public static float getYouSavePriceFromCTOOld(B2CPage page)
			throws ParseException {
		return DFOrigin.parse(
				page.cto_YouSavePrice.getAttribute("innerText")
						.replace(" ", "").substring(1)).floatValue();
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get price Array (WebPrice, AfterSavingPrice, YouSavePrice) float
	 *        from OLD CTO page
	 */
	public static float[] getPriceArrayFromCTOOld(B2CPage page)
			throws ParseException {
		return new float[] { getWebPriceFromCTOOld(page),
				getAfterSavingPriceFromCTOOld(page),
				getYouSavePriceFromCTOOld(page) };
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get Web price float from OLD PB page
	 */
	public static float getWebPriceFromPBOld(B2CPage page)
			throws ParseException {
		return DFOrigin.parse(page.pb_WebPrice.getText()).floatValue();
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get AfterSaving price float from OLD PB page
	 */
	public static float getAfterSavingPriceFromPBOld(B2CPage page)
			throws ParseException {
		if (Common.isElementExist(page.PageDriver,
				By.xpath("//span[@class='summary-price']"), 1)) {
			return DFOrigin.parse(page.pb_AfterSavingPrice.getText())
					.floatValue();
		} else {
			return 0;
		}
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get YouSave price float from OLD PB page
	 */
	public static float getYouSavePriceFromPBOld(B2CPage page)
			throws ParseException {
		if (Common
				.isElementExist(
						page.PageDriver,
						By.xpath("//dd[contains(@class,'pricingSummary-priceList-value')]/span[@class='summary-price']"),
						1)) {
			return DFOrigin.parse(page.pb_YouSavePrice.getText()).floatValue();
		} else {
			return 0;
		}
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get price Array (WebPrice, AfterSavingPrice, YouSavePrice) float
	 *        from OLD PB page
	 */
	public static float[] getPriceArrayFromPBOld(B2CPage page)
			throws ParseException {
		return new float[] { getWebPriceFromPBOld(page),
				getAfterSavingPriceFromPBOld(page),
				getYouSavePriceFromPBOld(page) };
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get Your price float from NEW CTO/PB page
	 */
	public static float getYourPriceFromCTOandPBNew(B2CPage page)
			throws ParseException {
		if (page.newCTO_YourPrice.getText().equals(""))
			return DFOrigin.parse(
					page.PageDriver.findElement(By.id("ctoYourPrice"))
							.getText().substring(1).trim()).floatValue();
		else
			return DFOrigin.parse(
					page.newCTO_YourPrice.getText().substring(1).trim())
					.floatValue();
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get Base price float from NEW CTO/PB page
	 */
	public static float getBasePriceFromCTOandPBNew(B2CPage page)
			throws ParseException {
		if (Common
				.checkElementExists(page.PageDriver, page.newCTO_BasePrice, 1)) {
			if (page.newCTO_BasePrice.getText().equals(""))
				return 0;
			else
				return DFOrigin.parse(
						page.newCTO_BasePrice.getText().substring(1).trim())
						.floatValue();
		} else
			return 0;
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get Save price float from NEW CTO/PB page
	 */
	public static float getSavePriceFromCTOandPBNew(B2CPage page)
			throws ParseException {
		if (Common
				.checkElementExists(page.PageDriver, page.newCTO_SavePrice, 1)) {
			if (page.newCTO_SavePrice.getText().equals(""))
				return 0;
			else
				return DFOrigin.parse(
						page.newCTO_SavePrice.getText().substring(1).trim())
						.floatValue();
		} else
			return 0;
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get price Array (BasePrice, YourPrice, SavePrice) float from NEW
	 *        CTO/PB page
	 */
	public static float[] getPriceArrayFromCTOandPBNew(B2CPage page)
			throws ParseException {
		return new float[] { getBasePriceFromCTOandPBNew(page),
				getYourPriceFromCTOandPBNew(page),
				getSavePriceFromCTOandPBNew(page) };
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Get Your price float from NEW PB page
	 */
	public static float getYourPriceFromPBNew(B2CPage page)
			throws ParseException {
		return DFOrigin.parse(page.newPB_YourPrice.getText().substring(2))
				.floatValue();
	}

	/**
	 * @Owner Songli
	 * @Parameter WebDriver, WebElement - CV line item
	 * @Usage Get Offset price float from OLD CTO page for a CV line
	 */
	public static float[] getOffsetPriceCTOOld(WebDriver driver,
			WebElement lineItem) throws ParseException {
		String strikePrice = "";
		float offsetWebPrice = 0;
		boolean discountExistFlag = false;
		By by = By.xpath(".//span[@class='strikeout']");
		if (Common.isElementExist(driver, lineItem, by, 1)) {
			strikePrice = lineItem.findElement(by).getText();
			offsetWebPrice = DFOrigin.parse(
					strikePrice.replace("  ", " ").split(" ")[1]).floatValue();
			discountExistFlag = true;
		}

		String strOffsetAfterSavingPrice = "";
		if (strikePrice != "") {
			strOffsetAfterSavingPrice = lineItem
					.findElement(By.xpath(".//td[@class='price']")).getText()
					.replace(strikePrice, "");
		} else {
			strOffsetAfterSavingPrice = lineItem.findElement(
					By.xpath(".//td[@class='price']")).getText();
		}

		float offsetAfterSavingPrice = 0;
		if (strOffsetAfterSavingPrice.split(" ").length > 4)
			offsetAfterSavingPrice = DFOrigin.parse(
					strOffsetAfterSavingPrice.replace("  ", " ").split(" ")[3])
					.floatValue();
		else
			// [ $ 0.00 ]
			offsetAfterSavingPrice = DFOrigin.parse(
					strOffsetAfterSavingPrice.replace("  ", " ").split(" ")[2])
					.floatValue();

		if (!discountExistFlag)
			offsetWebPrice = offsetAfterSavingPrice;

		float offsetSavesPrice = Common.floatSubtract(offsetWebPrice,
				offsetAfterSavingPrice);

		return new float[] { offsetWebPrice, offsetAfterSavingPrice,
				offsetSavesPrice };
	}

	/**
	 * @Owner Songli
	 * @Parameter WebDriver, WebElement - CV line item
	 * @Usage Get Offset price float from OLD PB page for a CV line
	 */
	public static float[] getOffsetPricePBOld(WebDriver driver,
			WebElement lineItem) throws ParseException {
		float offsetAfterSavingPrice = DFOrigin
				.parse(lineItem.findElement(
						By.xpath(".//span[@class='option-price']"))
						.getAttribute("data-price"))// getText())
				.floatValue();
		float offsetWebPrice = offsetAfterSavingPrice;
		if (Common.isElementExist(driver, lineItem,
				By.xpath(".//del[contains(@class,'option-origin-price')]"), 1)) {
			try {
				offsetWebPrice = DFOrigin
						.parse(lineItem
								.findElement(
										By.xpath(".//del[contains(@class,'option-origin-price')]"))
								.getAttribute("innerText").replace("\\n", "")
								.replace("\t", "")).floatValue();
			} catch (Exception e) {
				offsetWebPrice = DFOrigin
						.parse(lineItem
								.findElement(
										By.xpath(".//del[contains(@class,'option-origin-price')]"))
								.getAttribute("innerText").replace("\\n", "")
								.replace("\t", "").substring(1)).floatValue();
			}

		}
		float offsetSavesPrice = Common.floatSubtract(offsetWebPrice,
				offsetAfterSavingPrice);
		return new float[] { offsetWebPrice, offsetAfterSavingPrice,
				offsetSavesPrice };
	}

	/**
	 * @Owner Songli
	 * @Parameter WebElement - CV line item
	 * @Usage Get Offset price float from NEW CTO page for a CV line
	 */
	public static float[] getOffsetPriceCTONew(WebElement lineItem)
			throws ParseException {
		float offsetAfterSavingPrice;
		if (lineItem.findElement(By.xpath(".//div[contains(@class,'price')]"))
				.getText().split(" ").length > 1) {
			offsetAfterSavingPrice = DFOrigin.parse(
					lineItem.findElement(
							By.xpath(".//div[contains(@class,'price')]"))
							.getAttribute("innerText").split(" ")[1]
							.substring(1)).floatValue();
		} else {
			if (lineItem
					.findElement(By.xpath(".//div[contains(@class,'price')]"))
					.getAttribute("innerText").split(" ").length > 1) {
				offsetAfterSavingPrice = DFOrigin.parse(
						lineItem.findElement(
								By.xpath(".//div[contains(@class,'price')]"))
								.getAttribute("innerText").split(" ")[1]
								.substring(1)).floatValue();
			} else {
				// $0.00
				offsetAfterSavingPrice = DFOrigin.parse(
						lineItem.findElement(
								By.xpath(".//div[contains(@class,'price')]"))
								.getAttribute("innerText").substring(1))
						.floatValue();
			}
		}
		float offsetWebPrice = offsetAfterSavingPrice;

		float offsetSavesPrice = Common.floatSubtract(offsetWebPrice,
				offsetAfterSavingPrice);
		return new float[] { offsetWebPrice, offsetAfterSavingPrice,
				offsetSavesPrice };
	}

	public static boolean clickContinue(WebDriver driver) {
		boolean result = true;
		try {
			driver.findElement(By.xpath("//span[contains(text(),'Continue')]"))
					.click();
		} catch (Exception e) {
			result = false;
		}
		return result;

	}

	/**
	 * @Owner Songli
	 * @Parameter WebElement - CV line item
	 * @Usage Get Offset price float from NEW PB page for a CV line
	 */
	public static float[] getOffsetPricePBNew(WebDriver driver,
			WebElement lineItem) throws ParseException {
		float offsetAfterSavingPrice = DFOrigin.parse(
				lineItem.findElement(By.xpath(".//span[@data-price]"))
						.getAttribute("data-price")).floatValue();
		float offsetWebPrice = offsetAfterSavingPrice;
		if (Common.isElementExist(driver, lineItem,
				By.xpath(".//del[contains(@class,'option-origin-price')]"), 1)) {
			try {
				offsetWebPrice = DFOrigin
						.parse(lineItem
								.findElement(
										By.xpath(".//del[contains(@class,'option-origin-price')]"))
								.getAttribute("innerText").replace("\\n", "")
								.replace("\t", "")).floatValue();
			} catch (Exception e) {
				offsetWebPrice = DFOrigin
						.parse(lineItem
								.findElement(
										By.xpath(".//del[contains(@class,'option-origin-price')]"))
								.getAttribute("innerText").replace("\\n", "")
								.replace("\t", "").substring(1)).floatValue();
			}

		}
		float offsetSavesPrice = Common.floatSubtract(offsetWebPrice,
				offsetAfterSavingPrice);
		return new float[] { offsetWebPrice, offsetAfterSavingPrice,
				offsetSavesPrice };
	}

}
