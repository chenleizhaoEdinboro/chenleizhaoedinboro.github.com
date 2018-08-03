package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28114Test;
import TestScript.API.SWAT1009Test;
import TestScript.API.SWAT802Test;
import TestScript.B2C.NA15492Test;

public class SWAT1009 {
	@DataProvider(name = "SWAT1009")
	public static Object[][] DataSWAT1009() {
		return Common.getFactoryData(new Object[][] {
				
				//{ "US","22TP2TEE480","US|B2C|USWEB|EN","&contextString=US%7CB2C%7CUSWEB%7CEN","CTO","Y","511.62","350.00","550.00","580.00","1475.00","600.00","0.00","USD" },
				{ "AU","81A5003AAU","AU|B2C|AUWEB|EN","&contextString=AU%7CB2C%7CAUWEB%7CEN","MTM","Y","319.02","399.00","381.00","411.00","449.00","431.00","44.90","AUD" },
				{ "NZ","81C3000YAU","NZ|B2C|NZWEB|EN","&contextString=NZ%7CB2C%7CNZWEB%7CEN","CTO","Y","1170.21","1300.00","2550.00","2570.00","99999.00","2600.00","14999.85","NZD" },
				{ "JP","80Y7000CJP","JP|B2C|JPWEB|JA","&contextString=JP%7CB2C%7CJPWEB%7CJA","CTO","Y","114144.00","115000.00","115700.00","117700.00","183000.00","118000.00","9600.00","JPY" },
				{ "HK","80XL006MHH","HK|B2C|HKWEB|ZF","&contextString=HK%7CB2C%7CHKWEB%7CZF","MTM","Y","3989.50","4000.00","4130.00","4160.00","5698.00","4200.00","0.00","HKD" },
				{ "GB","81AC000CUK","GB|B2C|GBWEB|EN","&contextString=GB%7CB2C%7CGBWEB%7CEN","MTM","N","615.79","650.00","760.00","960.00","99999.00","1000.00","19999.80","GBP" },
				
				//{ "JP","20H5CTO1WWJAJP3","JP|B2C|jpweb|JPY","?contextString=JP%7CB2C%7Cjpweb%7CJPY" },
				
						},PropsUtils.getTargetStore("SWAT1009"));
	}

	@Factory(dataProvider = "SWAT1009")
	public Object[] createTest(String store,String productCode,String contextString, String contexPara,String productType,String stackable,String costP,String floorP,String couponP,String salesP , String listP,String webP,String tax, String currency) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1009Test(store,productCode,contextString, contexPara, productType,stackable,costP, floorP, couponP, salesP ,  listP, webP, tax,currency);

		return tests;
	}
}
