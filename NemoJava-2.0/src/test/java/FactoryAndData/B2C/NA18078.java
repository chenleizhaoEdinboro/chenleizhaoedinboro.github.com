package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA18078Test;


public class NA18078 {
	@DataProvider(name = "18078")
	public static Object[][] Data18078() {
		return Common.getFactoryData(new Object[][] {				
				{ "AU" ,"auweb"},				
				{ "US" ,"usweb"},
				{ "USEPP","lenovousepp"},				
				{ "CA_AFFINITY" ,"oma"},							
				{ "HK","hkweb"},				
				{ "GB" ,"gbweb"}			
		},PropsUtils.getTargetStore("NA-18078"));
	}

	@Factory(dataProvider = "18078")
	public Object[] createTest(String store,String baseStoreName) {

		Object[] tests = new Object[1];

		tests[0] = new NA18078Test(store,baseStoreName);

		return tests;
	}

}
