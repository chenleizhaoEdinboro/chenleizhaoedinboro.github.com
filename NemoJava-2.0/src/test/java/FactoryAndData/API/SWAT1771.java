package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28114Test;
import TestScript.API.SWAT1771Test;
import TestScript.API.SWAT802Test;
import TestScript.B2C.NA15492Test;

public class SWAT1771 {
	@DataProvider(name = "SWAT1771")
	public static Object[][] Data28114() {
		return Common.getFactoryData(new Object[][] {
			
			{ "US","20KNCTO1WWENUS0","US|B2C|USWEB|EN","&contextString=US%7CB2C%7CUSWEB%7CEN","CTO","ACV","NB_DISPLAY","14.0_FHD_IPS_AG","","","","","","","USD" },
			{ "US","20KNCTO1WWENUS0","US|B2C|USWEB|EN","&contextString=US%7CB2C%7CUSWEB%7CEN","CTO","MainCV","NB_DISPLAY","14.0_HD_AG","","","","","","","USD" },
			{ "AU","20L5CTO1WWENAU1","AU|B2C|AUWEB|EN","&contextString=AU%7CB2C%7CAUWEB%7CEN","CTO","MainCV","NB_DISPLAY","14.0_HD_TN_AG_220N","","","","","","","AUD" },
			{ "JP","20LDCTO1WWJAJP2","JP|B2C|JPWEB|JA","&contextString=JP%7CB2C%7CJPWEB%7CJA","CTO","MainCV","NB_MEM","8GB_LPDDR3_2133_MB","","","","","","","JPY" },
			{ "TW","20KHCTO1WWZHTW2","TW|B2C|TWWEB|ZF","&contextString=TW%7CB2C%7CTWWEB%7CZF","CTO","MainCV","NB_CM","720P_HD_CAMERA_MIC","","","","","","","TWD" },
			{ "HK","20L7CTO1WWENHK0","HK|B2C|HKWEB|ZF","&contextString=HK%7CB2C%7CHKWEB%7CZF","CTO","MainCV","NB_MEM","4GB(4_MB+0_DIMM)_DDR4_2400","","","","","","","HKD" },
			{ "FR","20LDCTO1WWFRFR1","FR|B2C|FRWEB|FR","&contextString=FR%7CB2C%7CFRWEB%7CFR","CTO","MainCV","NB_HDD","512GB_SSD_M.2_2280_NVME_OPAL2","","","","","","","EUR" },
			{ "MX","20L7CTO1WWESMX0","MX|B2C|MXWEB|ES","&contextString=MX%7CB2C%7CMXWEB%7CES","CTO","MainCV","NB_DISPLAY","14.0_FHD_IPS_AG_250N","","","","","","","MXN" },
			{ "CO","20L7CTO1WWESMX0","CO|B2C|COWEB|ES","&contextString=CO%7CB2C%7CCOWEB%7CES","CTO","MainCV","NB_MEM","4GB(4_MB+0_DIMM)_DDR4_2400","","","","","","","COP" },
						},PropsUtils.getTargetStore("SWAT1771"));
	}

	@Factory(dataProvider = "SWAT1771")
	public Object[] createTest(String store,String productCode,String contextString, String contexPara,String productType,String cvType,String characteristics1,String ChaValue1,String couponP,String salesP , String listP,String webP,String tax,String SystemDiscount, String currency) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1771Test(store,productCode,contextString, contexPara, productType,cvType,characteristics1, ChaValue1, couponP, salesP ,  listP, webP, tax,SystemDiscount,currency);

		return tests;
	}
}
