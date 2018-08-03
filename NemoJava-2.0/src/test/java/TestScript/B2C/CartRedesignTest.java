package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import Logger.Dailylog;
import Pages.B2CPage;
import TestScript.SuperTestClass;

public class CartRedesignTest extends SuperTestClass {

	B2CPage b2cPage = null;

	Actions actions = null;
	String orderNum = null;
	String productType = null;
	String productName = null;
	float webPrice = 0;
	float cartWebPrice = 0;
	private String productNO;
	private boolean flag;

	public CartRedesignTest(String store, String productCategory) {
		this.Store = store;
		this.testName = "CartCheckoutTest";

		this.productType = productCategory;
	}

	@Test(alwaysRun= true)
	public void CartCheckout(ITestContext ctx) {
		try {

			By addToCart = By.xpath("//*[@id='addToCartButtonTop']");
			By PBContinue = By
					.xpath("//*[@id='product-builder-form']/descendant::div/button");
			By viewModels = By.id("tab-a-nav-currentmodels");
			By customise = By.xpath("//button[contains(.,'Customi')]");
			By addBundle = By.xpath(".//*[@id='addToCartButton4Bundle']");
			By MTMInstantSaving = By
					.xpath("(//dd[@class='instant instant']/span)[2]");
			By MTMName = By.xpath(".//*[@id='tab-li-models']/h2");

			Dailylog.logInfo("Test run for " + Store);

			this.prepareTest();
			b2cPage = new B2CPage(driver);
			actions = new Actions(driver);
			switch (productType) {
			case "MTM":
				productNO = testData.B2C.getDefaultMTMPN();
				break;
			case "CTO":
				productNO = testData.B2C.getDefaultCTOPN();
				break;
			case "ConvenienceBundle":
				productNO = "80VF002JUS_GX40M66708";
				break;
			default:
				System.out.println("default");
				break;
			}

			driver.get(testData.B2C.getHomePageUrl() + "/p/" + productNO);
			closePromotion(driver, b2cPage);
			// MTM
			if (Common.isElementExist(driver, addToCart)
					&& !Common.isElementExist(driver, customise)) {
				if (Common.isElementExist(driver, viewModels)) {
					b2cPage.PDP_ViewCurrentModelTab.click();
				}
				if (Common.isElementExist(driver, MTMName)) {
					productName = b2cPage.CC_MTMModel.getText().toString();
				} else {
					productName = b2cPage.ProductCategory.getText().toString();
				}

				b2cPage.PDP_AddToCartButton.click();
				if (Common.isElementExist(driver, MTMInstantSaving)) {
					webPrice = GetPriceValue(b2cPage.InstanSavingPrice
							.getText().toString().replaceAll("HK", "")
							.replaceAll("SG", "").replace("£", "")
							.replace("€", "").replaceAll("￥", "")
							.replaceAll("NT", "").replaceAll("₩", ""));
				} else {
					webPrice = GetPriceValue(b2cPage.CC_WebPrice.getText()
							.toString().replaceAll("HK", "")
							.replaceAll("SG", "").replace("£", "")
							.replace("€", "").replaceAll("￥", "")
							.replaceAll("NT", "").replaceAll("₩", ""));
				}
				while (Common.isElementExist(driver, PBContinue)) {
					b2cPage.Product_Continue.click();
				}

			}
			// CTO
			if (Common.isElementExist(driver, customise)) {
				if (Common.isElementExist(driver, viewModels)) {
					b2cPage.PDP_ViewCurrentModelTab.click();
				}
				productName = b2cPage.CC_MTMModel.getText().toString();
				webPrice = GetPriceValue(b2cPage.CC_WebPrice.getText()
						.toString().replaceAll("HK", "").replaceAll("SG", "")
						.replace("£", "").replace("€", "").replaceAll("￥", "")
						.replaceAll("NT", "").replaceAll("₩", ""));

				b2cPage.Product_customizeBtn.click();
				Thread.sleep(5000);
				b2cPage.addTocart_configPage.click();
				Thread.sleep(4000);
				if (Common.isElementExist(driver, MTMInstantSaving)) {
					webPrice = GetPriceValue(b2cPage.InstanSavingPrice
							.getText().toString().replaceAll("HK", "")
							.replaceAll("SG", "").replace("£", "")
							.replace("€", "").replaceAll("￥", "")
							.replaceAll("NT", "").replaceAll("₩", ""));
				}
				while (Common.isElementExist(driver, PBContinue)) {
					b2cPage.Product_Continue.click();
				}

			}
			//bundle
			if (Common.isElementExist(driver, addBundle)) {

				webPrice = GetPriceValue(b2cPage.ConvenienceBundle_PBPrice
						.getText().toString().replaceAll("HK", "")
						.replaceAll("SG", "").replace("£", "").replace("€", "")
						.replaceAll("￥", "").replaceAll("NT", "")
						.replaceAll("₩", ""));
				b2cPage.ConvenienceBundle_Add.click();
				Thread.sleep(4000);
				while (Common.isElementExist(driver, PBContinue)) {
					b2cPage.Product_Continue.click();
				}

			}
			if (!productType.equals("ConvenienceBundle")) {
				Thread.sleep(5000);
				cartWebPrice = GetPriceValue(b2cPage.Cart_WebPrice.getText()
						.toString().replaceAll("HK", "").replaceAll("SG", "")
						.replace("£", "").replace("€", "").replaceAll("￥", "")
						.replaceAll("NT", "").replaceAll("₩", ""));
				assert productName.replaceAll(" ", "").contains(b2cPage.Cart_MTMModel.getText()
						.toString())||b2cPage.Cart_MTMModel.getText()
						.toString().replaceAll(" ", "").contains(productName.replaceAll(" ", "")) : "model names do not match";

				assert cartWebPrice == webPrice : "prices do not match";
			} else {
				cartWebPrice = GetPriceValue(b2cPage.ConvenienceBundle_SubTotal
						.getText().toString().replaceAll("HK", "")
						.replaceAll("SG", "").replace("£", "").replace("€", "")
						.replaceAll("￥", "").replaceAll("NT", "")
						.replaceAll("₩", ""));
				assert cartWebPrice == webPrice : "prices do not match";

			}

			assert b2cPage.Cart_quantity.getAttribute("value").equals("1") : "Quantity is not correct";
			assert driver.getCurrentUrl().endsWith("cart")
					|| driver.getCurrentUrl().endsWith(
							"cart?transMessageFlag=true");

		} catch (Throwable e) {
			System.out
					.println(Store + " " + productType + " " + e.getMessage());
			handleThrowable(e, ctx);
		}
	}

	public void closePromotion(WebDriver driver, B2CPage page) {
		By Promotion = By.xpath(".//*[@title='Close (Esc)']");

		if (Common.isElementExist(driver, Promotion)) {

			Actions actions = new Actions(driver);

			actions.moveToElement(page.PromotionBanner).click().perform();

		}
	}

	public float GetPriceValue(String Price) {
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

}
