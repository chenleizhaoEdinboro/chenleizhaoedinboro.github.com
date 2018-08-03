package CommonFunction.DesignHandler;

import org.openqa.selenium.By;

import CommonFunction.Common;
import Pages.B2CPage;

public class NavigationBar {

	/**
	 * @Owner Songli
	 * @Parameter B2CPage, SplitterPage
	 * @Usage Navigate to specific splitter page, handle both old and new navigation
	 */
	public static void goToSplitterPageUnderProducts(B2CPage page, SplitterPage spliterPage)
			throws InterruptedException {
		if (Common.isElementExist(page.PageDriver, By.xpath("//li[@class='secondary_menu']"))) {
			// New navigation
			switch (spliterPage) {
			case Laptops:
				Common.javascriptClick(page.PageDriver,
						page.Navigation_Laptop.findElement(By.xpath("./following-sibling::div//p")));
				break;
			case Accessories:
				int i = 0;
				while(i++<3){
					Common.javascriptClick(page.PageDriver,
							page.Navigation_Accessories.findElement(By.xpath("./following-sibling::div/div/a/h3")));
				}
				break;
			case Tablets:
				Common.javascriptClick(page.PageDriver,
						page.Navigation_Tablets.findElement(By.xpath("./following-sibling::div//p")));
				break;
			case AllInOnes:
				Common.javascriptClick(page.PageDriver,
						page.Navigation_Desktops.findElement(By.xpath("./following-sibling::div//p")));
				break;
			case WorkStations:
				Common.javascriptClick(page.PageDriver,
						page.Navigation_WorkStations.findElement(By.xpath("./following-sibling::div//p")));
				break;
			case SmartPhones:
				Common.javascriptClick(page.PageDriver,
						page.Navigation_SmartPhones.findElement(By.xpath("./following-sibling::div/div/a/h3")));
				break;
			case Servers:
				Common.javascriptClick(page.PageDriver, 
						page.Navigation_Servers.findElement(By.xpath("./following-sibling::div/div/a")));
				break;
			case Warranty:
				Common.javascriptClick(page.PageDriver, 
						page.Navigation_Warranty.findElement(By.xpath("./following-sibling::div/div/a")));
				break;
			default:
				System.out.println("Not implemented yet");
				break;
			}
		} else {
			// Old Navigation
			page.Navigation_ProductsLink.click();
			switch (spliterPage) {
			case Laptops:
				page.Navigation_Laptop.click();
				break;
			case Accessories:
				page.Navigation_Accessories.click();
				break;
			case Tablets:
				page.Navigation_Tablets.click();
				break;
			case AllInOnes:
				page.Navigation_Desktops.click();
				break;
			case WorkStations:
				page.Navigation_WorkStations.click();
				break;
			case SmartPhones:
				page.Navigation_SmartPhones.click();
				break;
			case Servers:
				page.Navigation_Servers.click();
				break;
			case Warranty:
				page.Navigation_Warranty.click();
				break;
			default:
				System.out.println("Not implemented yet");
				break;
			}
		}
	}

	/**
	 * @Owner Songli
	 * @Parameter B2CPage
	 * @Usage Navigate to the first link under Deals link, handle both old and new Navigation
	 */
	public static void goToDealsFirstLink(B2CPage page) throws InterruptedException {
		if (Common.isElementExist(page.PageDriver, By.xpath("//li[@class='secondary_menu']"))) {
			// New navigation
			Common.javascriptClick(page.PageDriver,
					page.Navigation_DEALS.findElement(By.xpath("./following-sibling::div//h2[1]")));
		} else {
			// Old Navigation
			page.Navigation_DEALS.click();
			if (Common.checkElementExists(page.PageDriver, page.Navigation_DEALSFirstLink, 5))
				page.Navigation_DEALSFirstLink.click();
		}

	}
}
