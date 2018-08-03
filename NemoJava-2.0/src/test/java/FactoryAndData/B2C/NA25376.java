package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25376Test;

public class NA25376 {
	@DataProvider(name = "NA25376")
	public static Object[][] Data25376() {
		return Common.getFactoryData(new Object[][] {
				{ "AU"},
				{ "HK"},
				{ "JP"},
				{ "GB"},
				{ "CA"},
				{ "BR"}
						},PropsUtils.getTargetStore("NA-25376"));
	}

	@Factory(dataProvider = "NA25376")
	public Object[] createTest(String store) {

		Object[] tests = new Object[1];

		tests[0] = new NA25376Test(store);

		return tests;
	}

}
