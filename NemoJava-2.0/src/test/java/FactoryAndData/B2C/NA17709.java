package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.NA17709Test;

public class NA17709 {

	@DataProvider(name = "NA17709")
	public static Object[][] Data17709() {
		return Common.getFactoryData(new Object[][] {		
			 { "AU","20LDCTO1WWENAU1" ,"Australia"},//20J4CTO1WWENAU3	 
			 { "US","20HHCTO1WWENUS0","United States" },//20M5CTO1WWENUS1
			 { "JP","20M5CTO1WWJAJP1","Japan" },//20HHCTO1WWJAJP2
			 { "GB","20KSCTO1WWENGB0","United Kingdom"}
						},PropsUtils.getTargetStore("NA-17709"));
	}

	@Factory(dataProvider = "NA17709")
	public Object[] createTest(String store, String ctoProduct,String country) {

		Object[] tests = new Object[1];

		tests[0] = new NA17709Test(store,ctoProduct,country);

		return tests;
	}

}
