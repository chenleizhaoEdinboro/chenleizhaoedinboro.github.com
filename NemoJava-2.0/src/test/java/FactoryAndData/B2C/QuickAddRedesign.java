package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.CartRedesignTest;
import TestScript.B2C.NA16390Test;
import TestScript.B2C.NA21981Test;
import TestScript.B2C.QuickAddRedesignTest;

public class QuickAddRedesign {

	@DataProvider(name = "QuickAddRedesign")
	public static Object[][] Data16390() {
		return Common.getFactoryData(new Object[][] { { "AU", "65C5KAC1AU",".//*[@id='c-ct']/option[contains(text(),'VISA') or contains(text(),'Visa')]", },
				{ "US", "GX20K11838",".//*[@id='c-ct']/option[contains(text(),'VISA') or contains(text(),'Visa')]", },
				{ "CA", "GX20K74302",".//*[@id='c-ct']/option[contains(text(),'VISA') or contains(text(),'Visa')]", },
				{ "NZ", "0B47032",".//*[@id='c-ct']/option[contains(text(),'VISA') or contains(text(),'Visa')]", },
				{ "HK", "4XD0J65078",".//*[@id='c-ct']/option[contains(text(),'VISA') or contains(text(),'Visa')]", },
				{ "JP", "60DFAAR1JP",".//*[@id='c-ct']/option[contains(text(),'VISA') or contains(text(),'Visa')]", },
		}, PropsUtils.getTargetStore("QuickAddRedesign"));
	}

	@Factory(dataProvider = "QuickAddRedesign")
	public Object[] createTest(String store,String accessoryNo,String paymentElement) {

		Object[] tests = new Object[1];

		tests[0] = new QuickAddRedesignTest(store, accessoryNo,paymentElement);

		return tests;
	}

}