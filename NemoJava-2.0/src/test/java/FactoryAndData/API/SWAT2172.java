package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28114Test;
import TestScript.API.SWAT2171Test;
import TestScript.API.SWAT2172Test;
import TestScript.B2C.NA15492Test;

public class SWAT2172 {
	@DataProvider(name = "SWAT2172")
	public static Object[][] Data28114() {
		return Common.getFactoryData(new Object[][] {
				//{ "US","","","","","","" },
				{ "US","US|B2C|USWEB|EN","?contextString=US%7CB2C%7CUSWEB%7CEN" ,"7","SubSeries","22TP2TT4800","CTO","20L5CTO1WWENUS4","ThinkPad T480","0","$1,102.00"},
				{ "US","US|B2C|USWEB|EN","?contextString=US%7CB2C%7CUSWEB%7CEN" ,"7","SubSeries","22TP2TT4800","Model","20L5001GUS","ThinkPad T480","0","$1,359.00"},
				
		            
						},PropsUtils.getTargetStore("SWAT2172"));
	}

	@Factory(dataProvider = "SWAT2172")
	public Object[] createTest(String store,String contextString, String contexPara,String numberOfNode, String rootType,String  rootNo, String childrenType,String childrenNo, String childrenSummary,String availableAmount,String WebPrice) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT2172Test(store,contextString, contexPara,numberOfNode,rootType,rootNo,childrenType,childrenNo,childrenSummary,availableAmount,WebPrice);

		return tests;
	}
}
