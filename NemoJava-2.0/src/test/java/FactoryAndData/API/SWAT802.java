package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28114Test;
import TestScript.API.SWAT802Test;
import TestScript.B2C.NA15492Test;

public class SWAT802 {
	@DataProvider(name = "SWAT802")
	public static Object[][] Data28114() {
		return Common.getFactoryData(new Object[][] {
				
			{ "US","20KNCTO1WWENUS0","US|B2C|USWEB|EN","&contextString=US%7CB2C%7CUSWEB%7CEN","CTO","Y","423.20","429.00","540.00","550.00","1260.00","588.00","0.00","","USD" },
			{ "US","20L9001EUS","US|B2C|USWEB|EN","&contextString=US%7CB2C%7CUSWEB%7CEN","Deals","Y","800.16","1000.00","1420.00","1470.00","1359.00","1500.00","0.00","","USD" },
			{ "AU","81A5003AAU","AU|B2C|AUWEB|EN","&contextString=AU%7CB2C%7CAUWEB%7CEN","MTM","Y","319.56","325.00","409.00","419.00","449.00","429.00","44.90","","AUD" },
			{ "NZ","20L5CTO1WWENNZ1","NZ|B2C|NZWEB|EN","&contextString=NZ%7CB2C%7CNZWEB%7CEN","CTO","Y","829.97","899.00","1469.00","1539.00","2540.00","1599.00","381.00","","NZD" },
			{ "JP","20JHCTO1WWJAJP0","JP|B2C|JPWEB|JA","&contextString=JP%7CB2C%7CJPWEB%7CJA","CTO","Y","80717","80000","147000","148000","157000","150000","9600","","JPY" },
			{ "HK","81BR003VHH","HK|B2C|HKWEB|ZF","&contextString=HK%7CB2C%7CHKWEB%7CZF","MTM","Y","5143.07","5200.00","5350.00","5400.00","8998.00","5500.00","0.00","","HKD" },
			{ "GB","81AC000CUK","GB|B2C|GBWEB|EN","&contextString=GB%7CB2C%7CGBWEB%7CEN","MTM","Y","616.49","630.00","800.00","851.00","99999.00","900.00","19999.80","","GBP" },
			{ "CO","20H1000300","CO|B2C|COWEB|ES","&contextString=CO%7CB2C%7CCOWEB%7CES","MTM","Y","1516593.49","1600000.00","19997980.00","19999980.00","2600000.00","20000000.00","494000.00","","COP" },
			//accessory and system discount
			{ "AU","40A90090AU","AU|B2C|AUWEB|EN","&contextString=AU%7CB2C%7CAUWEB%7CEN","Accessory","Y","114.31","150.00","190.00","220.00","253.64","250.00","25.36","","AUD" },
			{ "AU","0A36270","AU|B2C|AUWEB|EN","&triggerProductCode=81B0003KAU&contextString=AU%7CB2C%7CAUWEB%7CEN","Option","Y","10.64","10.64","","","55.46","61.01","5.55","-1.00","AUD" },
			{ "NZ","40A90090AU","NZ|B2C|NZWEB|EN","&contextString=NZ%7CB2C%7CNZWEB%7CEN","Accessory","Y","123.86","130.00","289.00","299.00","297.00","300.00","44.55","","NZD" },				
			{ "NZ","40A90090AU","NZ|B2C|NZWEB|EN","&triggerProductCode=20M7CTO1WWENNZ3&contextString=NZ%7CB2C%7CNZWEB%7CEN","Option","Y","123.86","150.00","165.00","170.00","297.00","200.00","44.55","-2.00","NZD" },
			//web price restriction
			{ "US","81CU000SUS","US|B2C|USWEB|EN","&contextString=US%7CB2C%7CUSWEB%7CEN","YogaSubRestriction","Y","","","","","","1100.00","","","USD" },
			{ "US","81CU000TUS","US|B2C|USWEB|EN","&contextString=US%7CB2C%7CUSWEB%7CEN","YogaModelRestriction","Y","","","","","","1,279.99","","","USD" },
			{ "US","81CU000RUS","US|B2C|USWEB|EN","&contextString=US%7CB2C%7CUSWEB%7CEN","YogaModelRestriction","Y","","","","","","1100.00","","","USD" },
			//unit rule
			{ "US","20KH002QUS","US|B2C|USWEB|EN","&contextString=US%7CB2C%7CUSWEB%7CEN","UnitRestriction","Y","1049.43","1100.00","1700.00","1900.00","1789.00","2000.00","0.00","","USD" },
			{ "USEPP","20KH002QUS","US|B2C|LENOVOUSEPP|EN","&contextString=US%7CB2C%7CLENOVOUSEPP%7CEN","UnitRestriction","Y","1049.43","1049.43","0.00","0.00","1789.00","1789.00","0.00","","USD" },
				
						},PropsUtils.getTargetStore("SWAT802"));
	}

	@Factory(dataProvider = "SWAT802")
	public Object[] createTest(String store,String productCode,String contextString, String contexPara,String productType,String stackable,String costP,String floorP,String couponP,String salesP , String listP,String webP,String tax,String SystemDiscount, String currency) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT802Test(store,productCode,contextString, contexPara, productType,stackable,costP, floorP, couponP, salesP ,  listP, webP, tax,SystemDiscount,currency);

		return tests;
	}
}
