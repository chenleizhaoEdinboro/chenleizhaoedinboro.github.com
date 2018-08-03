package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.BROWSE201Test;

public class BROWSE201 {
	@DataProvider(name = "201")
	public static Object[][] Data201() {
		return Common.getFactoryData(new Object[][] { 
			{ "AU","1213286247","10FGS00F0L","zhaoss5@lenovo.com" }, 
			{ "US","","","" }
		},PropsUtils.getTargetStore("BROWSE-201"));
	}

	@Factory(dataProvider = "201")
	public Object[] createTest(String store, String unit, String BundleMTM,
			String userName) {

		Object[] tests = new Object[1];

		tests[0] = new BROWSE201Test(store,  unit,  BundleMTM,
				userName);

		return tests;
	}
}
