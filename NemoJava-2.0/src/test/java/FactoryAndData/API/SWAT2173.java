package FactoryAndData.API;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.API.NA28114Test;
import TestScript.API.NA28797Test;
import TestScript.API.SWAT2173Test;
import TestScript.B2C.NA15492Test;

public class SWAT2173 {
	@DataProvider(name = "SWAT2173")
	public static Object[][] Data28114() {
		return Common
				.getFactoryData(
						new Object[][] {

							{ "US", "22TP2TXX12Y", "",
										"22TP2TXX12Y",
										"Thinkpad X1 Yoga (2nd Gen)",
										"X1 Yoga (2nd Gen)", "UPTO16GB",
										"WQHD", "3.13LBS", "i7PROCESSOR",
										"1TB", "HDMI", "HOURS", "WINDOWS" },
								{
										"US",
										"22TP2TXX12Y",
										"US|B2C|USWEB|EN",
										
										"22TP2TXX12Y?contextString=US%7CB2C%7CUSWEB%7CEN",
										"Thinkpad X1 Yoga (2nd Gen)",
										"SubSeries", "16 GB LPDDR3",
										"WQHD (2560 x 1440) IPS multi-touch", "3.13LBS", "i7PROCESSOR",
										"512 GB SSD", "HDMI", "HOURS", "WINDOWS" },
								{
										"USEPP",
										"88IP5000868",
										"US|B2C|lenovousepp|EN",
										"88IP5000868?contextString=US%7CB2C%7Clenovousepp%7CEN",
										"Ideapad 520 (15_Intel)",
										"SubSeries", "8 GB DDR4 memory", "FHD",
										"2.2 kg", "7th Gen", "1TB", "HDMI",
										"6 HOURS", "WINDOWS" },
								{
										"AU",
										"22TP2TX2800",
										"AU|B2C|AUWEB|EN",
										"22TP2TX2800?contextString=AU%7CB2C%7CAUWEB%7CEN",
										"ThinkPad X280", "SubSeries", "16GB",
										"anti-glare", "1.16 kg",
										"8th Gen Intel", "", "HDMI", "HOURS",
										"WINDOWS" },
								{
										"NZ",
										"88IP30S0824",
										"NZ|B2C|NZWEB|EN",
										"88IP30S0824?contextString=NZ%7CB2C%7CNZWEB%7CEN",
										"Ideapad 320S (15_Intel)",
										"SubSeries", "8GB", "FHD", "",
										"8th Gen Intel", "", "HDMI", "HOURS",
										"WINDOWS" },
								{
										"HK",
										"22TP2TT4800",
										"HK|B2C|HKWEB|EN",
										"22TP2TT4800?contextString=HK%7CB2C%7CHKWEB%7CEN",
										"ThinkPad T480", "SubSeries", "32GB", "FHD",
										"1.58 kg", "8th generation Intel", "", "HDMI",
										"HOURS", "WINDOWS" },
								{
										"CA",
										"22TP2TEE470",
										"CA|B2C|WEBCA|EN",
										"22TP2TEE470?contextString=CA%7CB2C%7CWEBCA%7CEN",
										"ThinkPad E470", "SubSeries", "", "", "",
										"", "", "", "", "WINDOWS" },
								{
										"JP",
										"22TP2TT4800",
										"JP|B2C|JPWEB|JA",
										"22TP2TT4800?contextString=JP%7CB2C%7CJPWEB%7CJA",
										"ThinkPad T480", "SubSeries", "32GB",
										"IPS液晶", "1.65kg", "第8世代", "", "", "",
										"WINDOWS" },
								{
										"GB",
										"22TP2TT480S",
										"GB|B2C|GBWEB|EN",
										"22TP2TT480S?contextString=GB%7CB2C%7CGBWEB%7CEN",
										"ThinkPad T480s", "SubSeries", "24GB",
										"Touch", "1.32 kg", "i7", "", "HDMI",
										"13.5", "WINDOWS" },
								{
										"SG",
										"22TP2TT4800",
										"SG|B2C|SGWEB|EN",
										"22TP2TT4800?contextString=SG%7CB2C%7CSGWEB%7CEN",
										"ThinkPad T480", "SubSeries", "32GB", "FHD",
										"1.58kg", "8th generation", "", "HDMI",
										"13.9hours", "WINDOWS" },
								{
										"FR",
										"88IP70S0893",
										"FR|B2C|FRWEB|FR",
										"88IP70S0893%20?contextString=FR%7CB2C%7CFRWEB%7CFR",
										"Ideapad 720S (13_Intel)",
										"SubSeries", "DDR4", "", "1.1 kg",
										"i7", "", "USB", "8 heures", "WINDOWS" },
								{
										"CO",
										"22TP2TXX15G",
										"CO|B2C|COWEB|ES",
										"22TP2TXX15G?contextString=CO%7CB2C%7CCOWEB%7CES",
										"ThinkPad X1 Carbon (5th Gen)",
										"SubSeries", "", "WQHD", "",
										"", "", "", "horas", "WINDOWS" },

						}, PropsUtils.getTargetStore("SWAT2173"));
	}

	@Factory(dataProvider = "SWAT2173")
	public Object[] createTest(String store, String productNo,
			String contextString, String contexPara, String name,
			String subType, String Memory, String DisplayType, String Weight,
			String Processor, String HardDrive, String Ports, String Battery,
			String OperatingSystem) {

		Object[] tests = new Object[1];

		tests[0] = new SWAT2173Test(store, productNo, contextString, contexPara,
				name, subType, Memory, DisplayType, Weight, Processor,
				HardDrive, Ports, Battery, OperatingSystem);

		return tests;
	}
}
