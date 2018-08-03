package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA25336Test;

public class NA25336 {
	@DataProvider(name = "NA25336")
	public static Object[][] Data25336() {
		return Common.getFactoryData(new Object[][] {
				{ "US","http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com/us/en/startWebForms?WebFormId=LenovoFinancialServices#"}
						},PropsUtils.getTargetStore("NA-25336"));
	}

	@Factory(dataProvider = "NA25336")
	public Object[] createTest(String store,String Url) {

		Object[] tests = new Object[1];

		tests[0] = new NA25336Test(store,Url);

		return tests;
	}

}
