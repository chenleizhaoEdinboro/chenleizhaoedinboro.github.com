package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18046Test;

public class NA18046 {
	@DataProvider(name = "18046")
	public static Object[][] Data18046() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU", "80XE0004AU", "20J4CTO1WWENAU3"},
			{ "CA_AFFINITY", "80XE0004AU", "20HDCTO1WWENCA0"},
			{ "HK", "81BF0082HH", "20H1CTO1WWENHK1"},
			{ "JP", "81BF0006JP", "20H5CTO1WWJAJP6"},
			{ "US", "80XE002WAU", "20H1CTO1WWENUS0"},
			{ "BR","80YH0006BR", "20HRCTO1WWENGB0"},
			{ "US_BPCTO","80XE002WAU", "20HRCTO1WWENGB0"},
			{ "US_OUTLET","80XE002WAU", "20HRCTO1WWENGB0"},
			},
				PropsUtils.getTargetStore("NA-18046"));
	}

	@Factory(dataProvider = "18046")
	public Object[] createTest(String store,String partnumber, String ctoProduct) {

		Object[] tests = new Object[1];

		tests[0] = new NA18046Test(store,partnumber,ctoProduct);

		return tests;
	}
}
