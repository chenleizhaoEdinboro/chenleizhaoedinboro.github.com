package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA21981Test;

public class NA21981 {

	@DataProvider(name = "NA21981")
	public static Object[][] Data21981() {
		return Common.getFactoryData(new Object[][] { 
			    { "AU", "/tablets/c/TABLETS?menu-id=Tablets" },
				{ "US", "/laptops/ideapad/c/IdeaPad" },
				{ "USEPP", "/laptops/ideapad/c/IdeaPad" },
				{ "CA_AFFINITY", "/laptops/ideapad/c/IdeaPad" },
				{ "US_BPCTO", "/laptops/ideapad/c/IdeaPad" },
				{ "HK", "/laptops/ideapad/c/ideapad" },
				{ "JP", "/notebooks/ideapad/c/ideapad" }
				}, PropsUtils.getTargetStore("NA-21981"));
	}

	@Factory(dataProvider = "NA21981")
	public Object[] createTest(String store, String mtmUrl) {

		Object[] tests = new Object[1];

		tests[0] = new NA21981Test(store, mtmUrl);

		return tests;
	}

}