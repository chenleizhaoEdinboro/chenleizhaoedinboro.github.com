package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.BROWSE103Test;

public class BROWSE103 {
	@DataProvider(name = "103")
	public static Object[][] Data103() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","1213286247","10FGS00F0L","10FGCTO1WWENAU0","zhaoss5@lenovo.com" }, 
		},PropsUtils.getTargetStore("BROWSE-103"));
	}

	@Factory(dataProvider = "103")
	public Object[] createTest(String store, String unit, String BundleMTM,
			String BundleCTO, String userName) {

		Object[] tests = new Object[1];

		tests[0] = new BROWSE103Test(store,  unit,  BundleMTM,
				 BundleCTO,  userName);

		return tests;
	}
}
