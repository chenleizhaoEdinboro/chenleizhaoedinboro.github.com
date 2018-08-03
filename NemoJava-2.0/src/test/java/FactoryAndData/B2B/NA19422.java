package FactoryAndData.B2B;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import CommonFunction.DesignHandler.PaymentType;
import TestData.PropsUtils;
import TestScript.B2B.NA19422Test;

public class NA19422 {

	@DataProvider(name = "NA19422")
	public static Object[][] Data19422() {
		return Common.getFactoryDataPayment(new Object[][] { 
//			{ "US", PaymentType.Amazon_B2B },
			
			{ "AU", PaymentType.VISA_B2B },
			{ "AU", PaymentType.AMEX_B2B }, 
			{ "AU", PaymentType.Master_B2B }, 
			{ "AU", PaymentType.Deposit_B2B }, 
			{ "AU", PaymentType.IGF_B2B }, 
			{ "AU", PaymentType.LFS_B2B }, 
			{ "AU", PaymentType.Party_B2B }, 
			{ "AU", PaymentType.PayPal_B2B }, 
			{ "AU", PaymentType.PurchaseOrder_B2B }, 
			
			{ "JP", PaymentType.VISA_B2B },
			{ "JP", PaymentType.Deposit_B2B },
			{ "JP", PaymentType.Master_B2B },
			{ "JP", PaymentType.PurchaseOrder_B2B },
			{ "JP", PaymentType.AMEX_B2B },

			{ "US", PaymentType.VISA_B2B },
			{ "US", PaymentType.AMEX_B2B }, 
			{ "US", PaymentType.Master_B2B },
			{ "US", PaymentType.Amazon_B2B },
			{ "US", PaymentType.Deposit_B2B },
			{ "US", PaymentType.Discover_B2B },
			{ "US", PaymentType.IGF_B2B },
			{ "US", PaymentType.LFS_B2B },
			{ "US", PaymentType.Party_B2B },
			{ "US", PaymentType.PayPal_B2B },
			{ "US", PaymentType.PurchaseOrder_B2B }
		}, PropsUtils.getTargetStorePayment("B2B"));
	}

	@Factory(dataProvider = "NA19422")
	public Object[] createTest(String store, PaymentType paymentType) {

		Object[] tests = new Object[1];

		tests[0] = new NA19422Test(store, paymentType);

		return tests;
	}

}