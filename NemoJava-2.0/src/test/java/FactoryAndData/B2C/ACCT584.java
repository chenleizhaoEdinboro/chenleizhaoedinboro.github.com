package FactoryAndData.B2C;

import CommonFunction.Common;
import TestData.PropsUtils;
import TestScript.B2C.ACCT584Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

public class ACCT584 {
    @DataProvider(name = "ACCT584")
    public static Object[][] DataACCT584() {
        return Common.getFactoryData(new Object[][] {
                { "US_SMB" }}, PropsUtils.getTargetStore("ACCT584"));
    }

    @Factory(dataProvider = "ACCT584")
    public Object[] createTest(String store) {

        Object[] tests = new Object[1];

        tests[0] = new ACCT584Test(store);

        return tests;
    }
}
