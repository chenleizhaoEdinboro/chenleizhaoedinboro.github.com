package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22330Test;
import TestScript.B2C.NA22331Test;
import TestScript.B2C.NA23007Test;
import TestScript.B2C.NA26023Test;
import TestScript.B2C.NA26085Test;

public class NA26023 {
	
	@DataProvider(name = "26023")
	public static Object[][] Data26023(){
	return Common.getFactoryData(new Object[][]{
			{"AU","00LA505","https://pre-c-hybris.lenovo.com/au/en/aupartsales"}
	},PropsUtils.getTargetStore("NA-26023"));	
	}
	
	@Factory(dataProvider = "26023")
	public Object[] createTest(String store,String product, String partSalesURL) {

		Object[] tests = new Object[1];

		tests[0] = new NA26023Test(store,product,partSalesURL);

		return tests;
	}

}
