package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA16390Test;
import TestScript.B2C.NA21981Test;

public class NA16390 {

	@DataProvider(name = "NA16390")
	public static Object[][] Data16390() {
		return Common.getFactoryData(new Object[][] { { "AU", ".//*[@id='state']/option[contains(text(),'Victoria')]" },
				{ "NZ", ".//*[@id='state']/option[contains(text(),'Canterbury')]" },
				{ "US", ".//*[@id='state']/option[contains(text(),'Illinois')]" },
				{ "US_SMB", ".//*[@id='state']/option[contains(text(),'New York')]" },
				{ "USEPP", ".//*[@id='state']/option[contains(text(),'New York')]" },
				{ "CA", ".//*[@id='state']/option[contains(text(),'Ontario')]" },
				{ "CA_AFFINITY", ".//*[@id='state']/option[contains(text(),'Ontario')]" },
				//{ "US_OUTLET", ".//*[@id='state']/option[contains(text(),'New York')]" },
				{ "US_BPCTO", ".//*[@id='state']/option[contains(text(),'Illinois')]" },
				{ "HK", ".//*[@id='state']/option[contains(text(),'New Territories')]" },
				{ "HKZF", ".//*[@id='state']/option[contains(text(),'New Territories')]" },
				{ "SG", ".//*[@id='state']/option[contains(text(),'Central Singapore')]" },
				{ "JP", ".//*[@id='state']/option[contains(text(),'東京都')]" },
				{ "TW", ".//*[@id='state']/option[contains(text(),'台北市')]" },
				{ "CO", ".//*[@id='state']/option[contains(text(),'東京都')]" },
				{ "BR", ".//*[@id='state']/option[contains(text(),'Alagoas')]" },
				{ "GB", ".//*[@id='billingCountry']/option[contains(text(),'United Kingdom')]" },
				{ "IE", ".//*[@id='billingCountry']/option[contains(text(),'Ireland')]" } }, PropsUtils.getTargetStore("NA16390"));
	}

	@Factory(dataProvider = "NA16390")
	public Object[] createTest(String store,String State) {

		Object[] tests = new Object[1];

		tests[0] = new NA16390Test(store, State);

		return tests;
	}

}