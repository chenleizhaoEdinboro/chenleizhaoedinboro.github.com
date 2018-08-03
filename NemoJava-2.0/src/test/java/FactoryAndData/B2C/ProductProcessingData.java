package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.ProductFileProcess;

public class ProductProcessingData {

	@DataProvider(name = "ProductProcessingData")
	public static Object[][] Data16452() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoAU-ProductGenerationCronJob')]/..","auPublic_unit" }, 
			 	{ "NZ",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoNZ-ProductGenerationCronJob')]/..","nzPublic_unit" }, 
				{ "US" ,".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoUS-ProductGenerationCronJob')]/..","usweb_unit"},
				{ "USEPP",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoUSEPP-ProductGenerationCronJob')]/..","uslenovousepp_unit" },
				{ "CA",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoCA-ProductGenerationCronJob')]/.." ,"cawebca_unit"},
				{ "CA_AFFINITY",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoCA-ProductGenerationCronJob')]/..","caoma_unit" },
				{ "US_BPCTO",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoUS-ProductGenerationCronJob')]/..","bpcto_us_insight_direct_usa_unit" },
				{ "US_OUTLET",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoUS-ProductGenerationCronJob')]/..","usoutlet" },
				{ "HK",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoHK-ProductGenerationCronJob')]/..","hkpublic_unit" },
				{ "HKZF",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoHK-ProductGenerationCronJob')]/..","hkpublic_unit" },
				{ "SG",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoSG-ProductGenerationCronJob')]/..","sgpublic_unit" },
				//{ "KR",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoKR-ProductGenerationCronJob')]/..","krpublic_unit" },
				{ "JP",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoJP-ProductGenerationCronJob')]/..","jppublic_unit" },
				{ "IE",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoIE-ProductGenerationCronJob')]/..","ieweb" },
				{ "GB",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoGB-ProductGenerationCronJob')]/..","gbweb" },
				{ "TW",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoTW-ProductGenerationCronJob')]/..","twpublic_unit" },
				{ "CO",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoCO-ProductGenerationCronJob')]/..","coweb" },
				{ "BR",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoBR-ProductGenerationCronJob')]/..","brweb" },
				{ "MY",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoMY-ProductGenerationCronJob')]/..","mypublic_unit" },
				{ "MX",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoMX-ProductGenerationCronJob')]/..","mxweb" },
				{ "FR",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoFR-ProductGenerationCronJob')]/..","frweb" },
				{ "IN",".//*[@id='resultlist_Content/McSearchListConfigurable[CronJob]']//tr/td//div[contains(text(),'nemoIN-ProductGenerationCronJob')]/..","India" }
		},PropsUtils.getTargetStore("ProductProcessingData"));
	}

	@Factory(dataProvider = "ProductProcessingData")
	public Object[] createTest(String store,String Conronjob, String Unit ) {

		Object[] tests = new Object[1];

		tests[0] = new ProductFileProcess(store,Conronjob,Unit);

		return tests;
	}

}
