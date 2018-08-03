package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA28215Test;

public class NA28215 {
	@DataProvider(name = "28215")
	public static Object[][] Data28205() {
		return Common.getFactoryData(new Object[][] { 
				{"USEPP","uslenovousepp_unit","lenovousepp","star","superstar","us welcome to"},
				{"JP","jpaffinity_unit","jpaffinity","niuxi1@126.com","P@ssword01","よこそうよこそう"},
				{"AU","aupremium_unit","aupremium","lisong2@lenovo.com","1q2w3e4r","au welcome to"}
			},PropsUtils.getTargetStore("NA-28215"));
		
	}

	@Factory(dataProvider = "28215")
	public Object[] createTest(String store,String unit,String url,String account,String password,String message) {

		Object[] tests = new Object[1];

		tests[0] = new NA28215Test( store, unit, url, account, password, message);

		return tests;
	}
  
}
