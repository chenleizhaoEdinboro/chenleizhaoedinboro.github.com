package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28114Test;
import TestScript.API.NA28797Test;
import TestScript.API.SWAT1075Test;
import TestScript.B2C.NA15492Test;

public class SWAT1075 {
	@DataProvider(name = "SWAT1075")
	public static Object[][] Data1075() {
		return Common
				.getFactoryData(
						new Object[][] {

								{
										"US",
										"22TP2TXX12Y",
										"US|B2C|AUWEB|EN",
										"?codes=22TP2TXX12Y&contextString=US%7CB2C%7CAUWEB%7CEN",
										"200", "X1 Yoga (2nd Gen)", "UPTO16GB",
										"WQHD", "3.13LBS", "i7PROCESSOR",
										"1TB", "HDMI", "HOURS", "WINDOWS" },
								{
										"US",
										"22TP2TXX12Y",
										"US|B2C|USWEB",
										"?codes=22TP2TXX12Y&contextString=US%7CB2C%7CAUWEB%7CEN",
										"200", "X1 Yoga (2nd Gen)", "UPTO16GB",
										"WQHD", "3.13LBS", "i7PROCESSOR",
										"1TB", "HDMI", "HOURS", "WINDOWS" },
								{
										"US",
										"22TP2TXX12Y",
										"US|B2C",
										"?codes=22TP2TXX12Y&contextString=US%7CB2C%7CAUWEB%7CEN",
										"200", "X1 Yoga (2nd Gen)", "UPTO16GB",
										"WQHD", "3.13LBS", "i7PROCESSOR",
										"1TB", "HDMI", "HOURS", "WINDOWS" },

								{
										"AU",
										"22TP2TX2800",
										"AU|B2C|NZWEB|EN",
										"?codes=22TP2TX2800&contextString=AU%7CB2C%7CNZWEB%7CEN",
										"200", "X280", "16GB", "anti-glare",
										"1.16 kg", "8th Gen Intel", "", "HDMI",
										"HOURS", "WINDOWS" },

								{
										"HK",
										"22TP2TT4800",
										"HK|B2C|HKWEB|JA",
										"?codes=22TP2TT4800&contextString=HK%7CB2C%7CHKWEB%7CJA",
										"", "T480", "32GB", "FHD", "1.58 kg",
										"8th Gen Intel", "", "HDMI", "HOURS",
										"WINDOWS" },
								{
										"CA",
										"22TP2TEE470",
										"CA|B2C|CAWEB|EN",
										"?codes=22TP2TEE470&contextString=CA%7CB2C%7CCAWEB%7CEN",
										"", "E470", "", "", "", "", "", "", "",
										"WINDOWS" },

								{
										"JP",
										"22TP2TT4800",
										"JP|B2C|JPWEB|ZH",
										"?codes=22TP2TT4800&contextString=JP%7CB2C%7CJPWEB%7CZH",
										"", "T480", "32GB", "IPS液晶", "1.65kg",
										"第8世代", "", "", "", "WINDOWS" },
								{
										"GB",
										"22TP2TT480S",
										"GB|B2C|GBWEB|FR",
										"?codes=22TP2TT480S&contextString=GB%7CB2C%7CGBWEB%7CFR",
										"", "T480s", "24GB", "Touch",
										"1.32 kg", "i7", "", "HDMI", "13.5",
										"WINDOWS" },

								{
										"FR",
										"88IP70S0893",
										"FR|B2C|FRWEB|EN",
										"?codes=88IP70S0893%20&contextString=FR%7CB2C%7CFRWEB%7CEN",
										"", "720s (13\")", "DDR4", "",
										"1.1 kg", "i7", "", "USB", "8 heures",
										"WINDOWS" },
								{
										"CO",
										"22TP2TXX15G",
										"CO|B2C|COWEB|PT",
										"?codes=22TP2TXX15G&contextString=CO%7CB2C%7CCOWEB%7CPT",
										"", "X1 Carbon (5th Gen)", "", "WQHD",
										"", "", "", "", "horas", "WINDOWS" },

						}, PropsUtils.getTargetStore("SWAT1075"));
	}

	@Factory(dataProvider = "SWAT1075")
	public Object[] createTest(String store, String productNo,
			String contextString, String contexPara, String Status,
			String shortName, String Memory, String DisplayType, String Weight,
			String Processor, String HardDrive, String Ports, String Battery,
			String OperatingSystem) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT1075Test(store, productNo, contextString,
				contexPara, Status, shortName, Memory, DisplayType, Weight,
				Processor, HardDrive, Ports, Battery, OperatingSystem);

		return tests;
	}
}
