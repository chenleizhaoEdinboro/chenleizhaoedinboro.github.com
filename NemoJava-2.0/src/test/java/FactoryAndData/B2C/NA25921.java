package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA22330Test;
import TestScript.B2C.NA22331Test;
import TestScript.B2C.NA23007Test;
import TestScript.B2C.NA25921Test;
import TestScript.B2C.NA26085Test;

public class NA25921 {
	
	@DataProvider(name = "25921")
	public static Object[][] Data25921(){
	return Common.getFactoryData(new Object[][]{
			{"AU","00HM888","https://pre-c-hybris.lenovo.com/au/en/aupartsales"}
	},PropsUtils.getTargetStore("NA-25921"));	
	}
	
	@Factory(dataProvider = "25921")
	public Object[] createTest(String store,String product, String partSalesURL) {

		Object[] tests = new Object[1];

		tests[0] = new NA25921Test(store,product,partSalesURL);

		return tests;
	}

}
