package FactoryAndData.FE;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.NA22735Test;


public class NA22735 {
	@DataProvider(name = "test")
	public static Object[][] Data22735() {
		return Common.getFactoryData(new Object[][] { { "US" } },
				PropsUtils.getTargetStore("NA22735"));
	}
	@Factory(dataProvider = "test")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];
		
		tests[0] = new NA22735Test(store);
		return tests;

}
}
