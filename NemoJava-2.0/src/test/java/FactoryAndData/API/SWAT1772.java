package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28114Test;
import TestScript.API.SWAT1772Test;
import TestScript.API.SWAT802Test;
import TestScript.B2C.NA15492Test;

public class SWAT1772 {
	@DataProvider(name = "SWAT1772")
	public static Object[][] Data28114() {
		return Common.getFactoryData(new Object[][] {

				{ "US", "20JES1X200", "US|B2B|USLE|EN|1213577815",
						"&contextString=US%7CB2B%7CUSLE%7CEN%7C1213577815",
						"CONTRACT", "", "", "", "", "$1,454.00", "$0.00", "",
						"USD" },
				{ "AU", "20HGS0CB0P", "AU|B2B|AULE|EN|1213654197",
						"&contextString=AU%7CB2B%7CAULE%7CEN%7C1213654197",
						"CONTRACT", "", "", "", "", "$1,915.75", "$0.00", "",
						"AUD" },

		}, PropsUtils.getTargetStore("SWAT1772"));
	}

	@Factory(dataProvider = "SWAT1772")
	public Object[] createTest(String store, String productCode,
			String contextString, String contexPara, String productType,
			String stackable, String costP, String floorP, String listP,
			String webP, String tax, String SystemDiscount, String currency) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1772Test(store, productCode, contextString,
				contexPara, productType, stackable, costP, floorP, listP, webP,
				tax, SystemDiscount, currency);

		return tests;
	}
}
