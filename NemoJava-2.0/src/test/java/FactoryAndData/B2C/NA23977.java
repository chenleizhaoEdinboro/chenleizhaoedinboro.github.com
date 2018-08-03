package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA23977Test;


public class NA23977 {
	@DataProvider(name = "23977")
	public static Object[][] Data23977() {
		return Common.getFactoryData(new Object[][] {				
				{ "AU" ,"Our Company Footer WE","www.bing.com"}				
						
		},PropsUtils.getTargetStore("NA-23977"));
	}

	@Factory(dataProvider = "23977")
	public Object[] createTest(String store,String accessory,String url) {

		Object[] tests = new Object[1];

		tests[0] = new NA23977Test(store,accessory,url);

		return tests;
	}

}
