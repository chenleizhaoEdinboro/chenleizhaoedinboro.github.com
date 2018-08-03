package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22330Test;
import TestScript.B2C.NA22331Test;
import TestScript.B2C.NA23007Test;
import TestScript.B2C.NA26085Test;

public class NA26085 {
	
	@DataProvider(name = "26085")
	public static Object[][] Data26085(){
	return Common.getFactoryData(new Object[][]{
			{"AU","00HM888","https://pre-c-hybris.lenovo.com/au/en/aupartsales"}
	},PropsUtils.getTargetStore("NA-26085"));	
	}
	
	@Factory(dataProvider = "26085")
	public Object[] createTest(String store,String product, String partSalesURL) {

		Object[] tests = new Object[1];

		tests[0] = new NA26085Test(store,product,partSalesURL);

		return tests;
	}

}
