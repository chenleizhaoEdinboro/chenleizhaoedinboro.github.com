package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28114Test;
import TestScript.API.SWAT923Test;
import TestScript.B2C.NA15492Test;

public class SWAT923 {
	@DataProvider(name = "SWAT923")
	public static Object[][] Data28114() {
		return Common.getFactoryData(new Object[][] {
				{ "US","","","","","","" },
				{ "US","US|B2C|USWEB|EN","?contextString=US%7CB2C%7CUSWEB%7CEN" ,"THINKPAD","THINKPADT","22TP2TTTP25","22TP2TTTP2520K7"},
				{ "CA","CA|B2C|CAWEB|EN","?contextString=CA%7CB2C%7CCAWEB%7CEN","IDEAPAD","IDEAPAD-Y900","88IPY900623","88IPY90062380Q1" },
				{ "AU","AU|B2C|AUWEB|EN","?contextString=AU%7CB2C%7CAUWEB%7CEN","GAMING","IDEAPADY900","88IPY900623","88IPY90062380Q1" },
				{ "NZ","NZ|B2C|NZWEB|EN","?contextString=NZ%7CB2C%7CNZWEB%7CEN" ,"YOGA","YOGA700","88YG7000724","88YG700072480TY"},
				{ "HK","HK|B2C|HKWEB|EN","?contextString=HK%7CB2C%7CHKWEB%7CEN","THINKPAD","THINKPAD-X-SERIES","22TP2TXX13Y","22TP2TXX13Y20LF" },
				{ "SG","SG|B2C|SGWEB|EN","?contextString=SG%7CB2C%7CSGWEB%7CEN" ,"THINKPAD","THINKPAD-YOGA-SERIES","22TP2TXY370","22TP2TXY37020JH" },
				{ "GB","GB|B2C|GBWEB|EN","?contextString=GB%7CB2C%7CGBWEB%7CEN" ,"IDEAPAD","IDEAPAD-700-SERIES","88IP70S0833","88IP70S083380XC" },
				{ "JP","JP|B2C|JPWEB|JA","?contextString=JP%7CB2C%7CJPWEB%7CJA","IDEAPAD", "IDEAPAD-300","88IP3000842","88IP300084680XR"},
				{ "CO","CO|B2C|COWEB|ES","?contextString=CO%7CB2C%7CCOWEB%7CES","YOGA", "YOGA-900-SERIES","88YG9000859","88YG900085980Y7"},
				
				
		            
						},PropsUtils.getTargetStore("SWAT923"));
	}

	@Factory(dataProvider = "SWAT923")
	public Object[] createTest(String store,String contextString, String contexPara, String category,String  subCategroy, String SeriesCode,String MachineType) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT923Test(store,contextString, contexPara,category,subCategroy,SeriesCode,MachineType);

		return tests;
	}
}
