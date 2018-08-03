package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2B.NA18052Test;
import TestScript.B2C.NA18058Test;

public class NA18058 {

	@DataProvider(name = "18058")
	public static Object[][] Data18058() {
		return Common.getFactoryData(new Object[][] {
				{ "AU","NZ","auweb"} 

		},PropsUtils.getTargetStore("NA-18058"));
	}

	@Factory(dataProvider = "18058")
	public Object[] createTest(String store,String anotherStore,String webStoreId) {

		Object[] tests = new Object[1];

		tests[0] = new NA18058Test(store,anotherStore,webStoreId);

		return tests;
	}

}
