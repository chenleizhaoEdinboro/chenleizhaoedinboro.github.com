package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28114Test;
import TestScript.API.SWAT2171Test;
import TestScript.B2C.NA15492Test;

public class SWAT2171 {
	@DataProvider(name = "SWAT2171")
	public static Object[][] Data28114() {
		return Common.getFactoryData(new Object[][] {
				//{ "US","","","","","","" },
				{ "US","US|B2C|USWEB|EN","?depth=1&contextString=US%7CB2C%7CUSWEB%7CEN" ,"1","SubSeries","22TP2TT4800","MachineType","22TP2TT480020L5","","",""},
				{ "US","US|B2C|USWEB|EN","?depth=2&contextString=US%7CB2C%7CUSWEB%7CEN" ,"2","SubSeries","22TP2TT4800","MachineType","22TP2TT480020L5","CTO","20L5CTO1WWENUS4","7"},
				{ "US","US|B2C|USWEB|EN","?depth=2&contextString=US%7CB2C%7CUSWEB%7CEN" ,"2","SubSeries","22TP2TT4800","MachineType","22TP2TT480020L5","Model","20L5001GUS","7"},
				{ "US","US|B2C|USWEB|EN","?depth=2&contextString=US%7CB2C%7CUSWEB%7CEN" ,"2","SubSeries","22TP2TT4800","MachineType","22TP2TT480020L5","CTO","20L5CTO1WWENUS2","7"},	
				//category father, 1, 2
				//other countries, subseries father CA,AU,JP,HK,GB,CO,MX
				
				
				
		            
						},PropsUtils.getTargetStore("SWAT2171"));
	}

	@Factory(dataProvider = "SWAT2171")
	public Object[] createTest(String store,String contextString, String contexPara,String depth, String rootType,String  rootNo, String childrenType,String childrenNo, String grandChildrenType,String grandChildrenNo,String grandChildrenAmount) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT2171Test(store,contextString, contexPara,depth,rootType,rootNo,childrenType,childrenNo,grandChildrenType,grandChildrenNo,grandChildrenAmount);

		return tests;
	}
}
