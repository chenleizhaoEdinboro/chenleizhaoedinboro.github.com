package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17360Test;

public class NA17360 {
	@DataProvider(name = "17360")
	public static Object[][] Data17360() {
		return Common.getFactoryData(new Object[][] { 
				{ "AU","auContentCatalog","22TP2TX2700"},
				{ "CA","masterContentCatalog","22TP2TX2700"},
				{ "NZ","masterContentCatalog","22TP2TX2700"},
				{ "US","masterContentCatalog","22TP2TX2700"},
				{ "USEPP","masterContentCatalog","22TP2TX2700"}
				},
				PropsUtils.getTargetStore("NA-17360"));
	}

	@Factory(dataProvider = "17360")
	public Object[] createTest(String store,String version,String partnum) {

		Object[] tests = new Object[1];

		tests[0] = new NA17360Test(store,version,partnum);

		return tests;
	}
}
