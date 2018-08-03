package CommonFunction.DesignHandler;

import org.openqa.selenium.By;

import CommonFunction.Common;
import Pages.B2CPage;

public class CartCheckOut {
    //partNumber can be set as empty
	
	/**
	 * @Owner Haiyang
	 * @Parameter:
	 * @Usage:
	 */
	public static void CheckOutUIHandler(B2CPage page, String userAction,String partNumber)
			throws InterruptedException {
		float Price=0;
		if (Common.isElementExist(page.PageDriver, By.xpath("//button[contains(@class,'Submit_Button')]"))) {
			// New navigation
			switch (userAction.toLowerCase()) {
			case "override":
				 Price = GetPriceValue(page.NewCart_SalesPrice.getText().toString()
						.replaceAll("HK", "").replaceAll("SG", "").replace("£", "")
						.replace("€", "").replaceAll("￥", "").replaceAll("NT", "")
						.replaceAll("₩", ""));
				Price = Price - 1;
				page.OverrideValue.sendKeys((int) (Price) + "");
				page.OverrideDropdown.click();
				page.OverrideCheckbox.sendKeys("xxxxx");
				page.NewCart_UpdatePrice.click();
				System.out.println("Override performs successfully");
				Thread.sleep(5000);
				page.Override_RequestQuote.click();
				Thread.sleep(6000);
				page.Override_RepID.clear();
				Thread.sleep(4000);
				page.Override_RepID.sendKeys("2900718028");
				Thread.sleep(6000);
				page.Override_SubmitQuote.click();
				Thread.sleep(6000);
				break;
			case "checkout":
				page.Cart_CheckoutButton.click();
				break;
			case "quickadd":
				page.NewCart_PartNumberTextBox.clear();
				page.NewCart_PartNumberTextBox.sendKeys(partNumber);
				page.NewCart_Submit.click();
				break;
			default:
				System.out.println("Not implemented yet");
				break;
			}
		} else {
			// Old Navigation
		
			switch (userAction.toLowerCase()) {
			case "override":
				 Price = GetPriceValue(page.SalesPrice.getText().toString()
						.replaceAll("HK", "").replaceAll("SG", "").replace("£", "")
						.replace("€", "").replaceAll("￥", "").replaceAll("NT", "")
						.replaceAll("₩", ""));
				Price = Price - 1;
				page.OverrideValue.sendKeys((int) (Price) + "");
				page.OverrideDropdown.click();
				page.OverrideCheckbox.sendKeys("xxxxx");
				page.OverrideUpdate.click();
				System.out.println("Override performs successfully");
				Thread.sleep(5000);
				page.Override_RequestQuote.click();
				Thread.sleep(6000);
				page.Override_RepID.clear();
				Thread.sleep(4000);
				page.Override_RepID.sendKeys("2900718028");
				Thread.sleep(6000);
				page.Override_SubmitQuote.click();
				Thread.sleep(6000);
				break;
			case "checkout":
				page.Cart_CheckoutButton.click();
				break;
			case "quickadd":
				page.Payment_QuickAddBox.sendKeys(partNumber);
				page.Cart_AddButton.click();
				break;
			default:
				System.out.println("Not implemented yet");
				break;
			}
		}
	}

	/**
	 * @Owner Shuangshuang
	 * @Parameter:String including Price 
	 * @Usage:Convert String to Price with float type for example:$12.0(String)->12.0(float)
	 */
	public static float GetPriceValue(String Price) {
		if (Price.contains("FREE")||Price.contains("Ships")){
			return 0;
		}
		Price = Price.replaceAll("\\$", "");
		Price = Price.replaceAll("CAD", "");
		Price = Price.replaceAll("$", "");
		Price = Price.replaceAll(",", "");
		Price = Price.replaceAll("\\*", "");
		Price = Price.replaceAll("HK", "");
		Price = Price.replaceAll("SG", "");
		Price = Price.replaceAll("€", "");
		Price = Price.replaceAll("£", "");
		Price = Price.replaceAll("￥", "");
		Price = Price.trim();
		float priceValue;
		priceValue = Float.parseFloat(Price);
		return priceValue;
	}

}
