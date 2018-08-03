package CommonFunction;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Pages.CRMPage;

public class CRMCommon {
	
	/**
	 * @Owner Qianqian
	 * @Parameter 
	 * @Usage
	 */
	public static void CRMLogin(CRMPage page,String userName,String passWord){
		page.NameInput.clear();
		page.NameInput.sendKeys(userName);
		page.PasswordInput.clear();
		page.PasswordInput.sendKeys(passWord);
		page.EnglishDropDown.click();
		page.English.click();
		page.SinIn.click();
	}
	
	/**
	 * @Owner Qianqian
	 * @Parameter:
	 * @Usage:
	 */
	public static int getErrorMumber(String err) {
		err = err.substring(1, err.length() - 1);
		return Integer.parseInt(err);

	}
	
	/**
	 * @Owner Qianqian
	 * @Parameter:
	 * @Usage:
	 */
	public static boolean verifyErrorMessage(WebElement element, String regEx) {
		boolean flag;
		System.out.println("the error message is: " + element.getText());
		if (element.getText().matches(regEx)) {
			flag = true;
			return flag;
		} else {
			flag = false;
			return flag;
		}

	}
	
	/**
	 * @Owner Qianqian
	 * @Parameter:
	 * @Usage:
	 */
	public static float GetPriceValue(String Price) {
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
	
	/**
	 * @Owner Qianqian
	 * @Parameter:
	 * @Usage:
	 */
	public static void CRMValidation(WebDriver driver, CRMPage page,
			String OrderNumber, float ProductPrice, float TaxPrice,
			float TotalPrice, String url) throws InterruptedException {
		boolean orderFlag;
		boolean flag;
		int errNum;
		String[] regExs = { "", "" };
		By byLocator1 = By.xpath(".//*[contains(@id,'CRMMessageLine')]");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(url);

		CRMLogin(page, "houqq1", "abcd12345");
		page.Role.click();
		Thread.sleep(5000);
		driver.switchTo().frame("CRMApplicationFrame");
		// driver1.switchTo().frame("HeaderFrame");
		driver.switchTo().frame(
				driver.findElement(By.xpath("//frameset/frameset/frame[1]")));
		page.SalesCycle.click();
		page.SalesOrder.click();
		page.OrderIDInput.clear();
		page.OrderIDInput.sendKeys(OrderNumber);
		page.OrderSearch.click();
		assert page.ResultId.getText().equals(OrderNumber);
		if (page.ResultStatus.getText().equals("Pending")) {
			orderFlag = false;
			flag = false;
			page.ResultId.click();
			String errorNumber;
			errorNumber = page.ErrorNumber.getText();
			errNum = getErrorMumber(errorNumber);
			System.out.println("error number: " + errNum);
			page.ErrorNumber.click();
			List<WebElement> errElements = driver.findElements(byLocator1);
			for (WebElement element : errElements) {
				for (String regEx : regExs) {
					if (verifyErrorMessage(element, regEx)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					System.out.println("the error message not in cycle: "
							+ element.getText());
				}
			}
		} else if (page.ResultStatus.getText().equals(
				"Partially Released to Manufac")
				|| page.ResultStatus.getText().equals("Order Acceptance")) {
			page.ResultId.click();
			orderFlag = true;
			flag = true;
		} else {
			System.out.println("Order status is: "
					+ page.ResultStatus.getText());
			orderFlag = false;
			flag = false;
			page.ResultId.click();
		}
		System.out.println("price no tax: " + ProductPrice + "\nprice of tax: "
				+ TaxPrice + "\nprice with tax: " + TotalPrice + "");
		assert ProductPrice == GetPriceValue(page.PriceNotTax.getText());
		assert Math.abs(TaxPrice - GetPriceValue(page.PriceOfTax.getText())) <= 0.1;
		assert Math
				.abs(TotalPrice - GetPriceValue(page.PriceWithTax.getText())) <= 0.1;
		driver.switchTo().defaultContent();
	}
	
		
	
}
