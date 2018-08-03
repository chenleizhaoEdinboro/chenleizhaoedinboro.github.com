package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA20387Test;

public class NA20387 {

	@DataProvider(name = "20387")
	public static Object[][] Data20387() {
		return  Common.getFactoryData(new Object[][] { 
			{ "AU" ,"22TP2TX2700","22TP2TXX15G","80WK00HCAU"},
			{ "US" ,"22TP2TXX380","22TP2TEE480","20HRS14W00"}
		},PropsUtils.getTargetStore("NA-20387"));
	}

	@Factory(dataProvider = "20387")
	public Object[] createTest(String store,String productALL,String productCTO,String productMTM) {

		Object[] tests = new Object[1];

		tests[0] = new NA20387Test(store,productALL,productCTO,productMTM);

		return tests;
	}
}
