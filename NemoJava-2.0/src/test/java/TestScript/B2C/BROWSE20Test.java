package TestScript.B2C;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class BROWSE20Test extends SuperTestClass {
	B2CPage b2cPage;
	HMCPage hmcPage;

	public BROWSE20Test(String store){
		this.Store = store;
		this.testName = "BROWSE-20";
		
	}

	@Test(priority = 0, enabled = true, alwaysRun = true, groups = {"browsegroup","uxui",  "p2", "b2c"})
	public void BROWSE20(ITestContext ctx) {
		try {
			this.prepareTest();
			hmcPage = new HMCPage(driver);
			b2cPage = new B2CPage(driver);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchOnlineProduct(driver, hmcPage, testData.B2C.getDefaultMTMPN());
			hmcPage.Catalog_PMITab.click();
			Common.sleep(5000);
			//check the search function can  show the default catalog version
			
			Dailylog.logInfoDB(1, "check the search zSpecificationImage", Store,testName);
			Common.scrollAndClick(driver, hmcPage.catalog_PMI_specificationImage_search);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create zSpecificationImage", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_specificationImage_search);
			Common.mouseHover(driver, hmcPage.catalog_PMI_TableCreate);
			Common.javascriptClick(driver, hmcPage.catalog_PMI_TableCreateMedia);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the search colorSwatchImage ", Store,testName);
			Common.scrollAndClick(driver, hmcPage.catalog_PMI_colorSwatchImage_search);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create colorSwatchImage ", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_colorSwatchImage_search);
			Common.mouseHover(driver, hmcPage.catalog_PMI_TableCreate);
			Common.javascriptClick(driver, hmcPage.catalog_PMI_TableCreateMedia);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the search variantThumbnailImage ", Store,testName);
			Common.scrollAndClick(driver, hmcPage.catalog_PMI_variantThumbnailImage_search);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create variantThumbnailImage ", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_variantThumbnailImage_search);
			Common.mouseHover(driver, hmcPage.catalog_PMI_TableCreate);
			Common.javascriptClick(driver, hmcPage.catalog_PMI_TableCreateMedia);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the search mkt_image_list ", Store,testName);
			Common.scrollAndClick(driver, hmcPage.catalog_PMI_mkt_image_list_search);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create mkt_image_list ", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_mkt_image_list_search);
			Common.mouseHover(driver, hmcPage.catalog_PMI_TableCreate);
			Common.javascriptClick(driver, hmcPage.catalog_PMI_TableCreateMedia);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the search mkt_image_hero ", Store,testName);
			Common.scrollAndClick(driver, hmcPage.catalog_PMI_mkt_image_hero_search);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create mkt_image_hero ", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_mkt_image_hero_search);
			Common.mouseHover(driver, hmcPage.catalog_PMI_TableCreate);
			Common.javascriptClick(driver, hmcPage.catalog_PMI_TableCreateMedia);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the search processor_logo ", Store,testName);
			Common.scrollAndClick(driver, hmcPage.catalog_PMI_processor_logo_search);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create processor_logo ", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_processor_logo_search);
			Common.mouseHover(driver, hmcPage.catalog_PMI_TableCreate);
			Common.javascriptClick(driver, hmcPage.catalog_PMI_TableCreateMedia);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the search mkt_video_hero ", Store,testName);
			Common.scrollAndClick(driver, hmcPage.catalog_PMI_mkt_video_hero_search);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create mkt_video_hero", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_mkt_video_hero_search);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_mkt_video_hero_Create );
			checkTheCatalogVersion();			
			Dailylog.logInfoDB(1, "check the create ProductFeatures", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_ProductFeatures);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_ProductFeatures_Create );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the add ProductFeatures", Store, testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_ProductFeatures);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_Add );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create MarketingHeroBannerImages", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_MarketingHeroBannerImages);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_MarketingHeroBannerImages_Create );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the add MarketingHeroBannerImages", Store, testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_MarketingHeroBannerImages);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_Add );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create GalleryImages", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_GalleryImages);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_GalleryImages_Create );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the add GalleryImages", Store, testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_GalleryImages);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_Add );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create GalleryVideos", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_GalleryVideos);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_GalleryVideos_Create );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the add GalleryVideos", Store, testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_GalleryVideos);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_Add );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create PMIResources", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_PMIResources);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_PMIResources_Create );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the add PMIResources", Store, testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_PMIResources);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_Add );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create WhatsInTheBox ", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_WhatsInTheBox);
			Common.mouseHover(driver, hmcPage.catalog_PMI_TableCreate);
			Common.javascriptClick(driver, hmcPage.catalog_PMI_TableCreateMedia);
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the add WhatsInTheBox", Store, testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_WhatsInTheBox);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_Add );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the create CTALink ", Store,testName);
			Common.rightClick(driver, hmcPage.CTALink_Search);
			Common.javascriptClick(driver,hmcPage.Products_PMI_CTALinkCreate );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the add CTALink", Store, testName);
			hmcPage.CTALink_Search.click();
			checkTheCatalogVersion();		
			Dailylog.logInfoDB(1, "check the create CTAButtonLink ", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_CTAButtonLink_Search);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_CTAButtonLink_Create );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the add CTAButtonLink", Store, testName);
			hmcPage.catalog_PMI_CTAButtonLink_Search.click();
			checkTheCatalogVersion();
			String baseProduct = HMCCommon.getBaseProduct(hmcPage);
			
			
			hmcPage.Home_EndSessionLink.click();
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchOnlineProduct(driver, hmcPage, baseProduct);
			hmcPage.Catalog_PMITab.click();
			Common.sleep(5000);
			
			Dailylog.logInfoDB(1, "check the create productOptiontemplate ", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_productOptiontemplate_Search);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_productOptiontemplate_Create );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the add productOptiontemplate", Store, testName);
			hmcPage.catalog_PMI_productOptiontemplate_Search.click();
			checkTheCatalogVersion();

			Dailylog.logInfoDB(1, "check the create promotedOptions ", Store,testName);
			Common.rightClick(driver, hmcPage.catalog_PMI_promotedOptions_Search);
			Common.javascriptClick(driver,hmcPage.catalog_PMI_promotedOptions_Create );
			checkTheCatalogVersion();
			Dailylog.logInfoDB(1, "check the add promotedOptions", Store, testName);
			hmcPage.catalog_PMI_promotedOptions_Search.click();
			checkTheCatalogVersion();			
			
			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
		
		
	}
	public void checkTheCatalogVersion(){
		Common.sleep(3000);
		Common.switchToWindow(driver, 1);
		Assert.assertTrue(Common.checkElementDisplays(driver, hmcPage.catalog_PMI_MasterMultiCountryProductCatalogOnline, 3), "check the catalog version of cta search");
		driver.close();
		Common.switchToWindow(driver, 0);
	}
	
	
}
