
package FactoryAndData.FE;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.FE.SWAT1752Test;

public class SWAT1752 {
	@DataProvider(name = "test")
	public static Object[][] Data1752() {
		return Common.getFactoryData(new Object[][] { { "US" } },
				PropsUtils.getTargetStore("SWAT1752"));
	}


	@Factory(dataProvider = "test")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];
		
		tests[0] = new SWAT1752Test(store);
		return tests;
	}
}
