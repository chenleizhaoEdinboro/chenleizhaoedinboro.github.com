package FactoryAndData.B2C;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import CommonFunction.Common;
import CommonFunction.DesignHandler.PaymentType;
import TestData.PropsUtils;
import TestScript.B2C.NA19353Test;

public class NA19353 {

	@DataProvider(name = "NA19353")
	public static Object[][] Data19353() {
		return Common
				.getFactoryDataPayment(
						new Object[][] {
//							{"AU", PaymentType.Paypal_B2C},
								{"AU", PaymentType.Visa_B2C},
								{"AU", PaymentType.AmericanExpress_B2C},
								{"AU", PaymentType.Master_B2C},
								{"AU", PaymentType.Discover_B2C},
								{"AU", PaymentType.Paypal_B2C},
								{"AU", PaymentType.Deposit_B2C},
								{"AU", PaymentType.Wire_B2C},
								{"AU", PaymentType.PurchaseOrder_B2C},
								{"AU", PaymentType.Visa3DS_B2C},
								{"AU", PaymentType.Master3DS_B2C},
								{"AU", PaymentType.LFS_B2C},
								{"AU", PaymentType.IGF_B2C},
								{"AU", PaymentType.Party_B2C},
								
								{"NZ", PaymentType.Visa_B2C},
								{"NZ", PaymentType.AmericanExpress_B2C},
								{"NZ", PaymentType.Master_B2C},
								{"NZ", PaymentType.Discover_B2C},
								{"NZ", PaymentType.Paypal_B2C},
								{"NZ", PaymentType.Deposit_B2C},
								{"NZ", PaymentType.Wire_B2C},
								{"NZ", PaymentType.Visa3DS_B2C},
								{"NZ", PaymentType.Master3DS_B2C},
								{"NZ", PaymentType.LFS_B2C},
								{"NZ", PaymentType.IGF_B2C},
								{"NZ", PaymentType.Party_B2C},
								
//								{"MY", PaymentType.Visa_B2C},
//								{"MY", PaymentType.AmericanExpress_B2C},
//								{"MY", PaymentType.Master_B2C},
//								{"MY", PaymentType.Deposit_B2C},
								
								{"SG", PaymentType.Visa_B2C},
								{"SG", PaymentType.AmericanExpress_B2C},
								{"SG", PaymentType.Master_B2C},
								{"SG", PaymentType.Deposit_B2C},
								
								{"US", PaymentType.Visa_B2C},
								{"US", PaymentType.AmericanExpress_B2C},
								{"US", PaymentType.Master_B2C},
								{"US", PaymentType.Discover_B2C},
								{"US", PaymentType.Paypal_B2C},
								{"US", PaymentType.Deposit_B2C},
								{"US", PaymentType.Wire_B2C},
								{"US", PaymentType.TwoCards_B2C},
								{"US", PaymentType.Klarna_B2C},
								{"US", PaymentType.Zibby_B2C},
								{"US", PaymentType.PurchaseOrder_B2C},
								{"US", PaymentType.Visa3DS_B2C},
								{"US", PaymentType.Master3DS_B2C},
								{"US", PaymentType.Amazon_B2C},
								{"US", PaymentType.LFS_B2C},
								{"US", PaymentType.IGF_B2C},
								{"US", PaymentType.Party_B2C},
								
								{"CA", PaymentType.Visa_B2C},
								{"CA", PaymentType.AmericanExpress_B2C},
								{"CA", PaymentType.Master_B2C},
								{"CA", PaymentType.Discover_B2C},
								{"CA", PaymentType.Paypal_B2C},
								{"CA", PaymentType.Deposit_B2C},
								{"CA", PaymentType.Wire_B2C},
								{"CA", PaymentType.TwoCards_B2C},
								{"CA", PaymentType.Visa3DS_B2C},
								{"CA", PaymentType.Master3DS_B2C},
								
								{"JP", PaymentType.Visa_B2C},
								{"JP", PaymentType.AmericanExpress_B2C},
								{"JP", PaymentType.Master_B2C},
								{"JP", PaymentType.Discover_B2C},
								{"JP", PaymentType.JCB_B2C},
								{"JP", PaymentType.Deposit_B2C},
								{"JP", PaymentType.Wire_B2C},
								{"JP", PaymentType.JACCS_B2C},
								{"JP", PaymentType.Visa3DS_B2C},
								{"JP", PaymentType.Master3DS_B2C},
								
								{"HK", PaymentType.Visa_B2C},
								{"HK", PaymentType.AmericanExpress_B2C},
								{"HK", PaymentType.Master_B2C},
								{"HK", PaymentType.Discover_B2C},
								{"HK", PaymentType.Deposit_B2C},
								{"HK", PaymentType.Wire_B2C},
								{"HK", PaymentType.Visa3DS_B2C},
								{"HK", PaymentType.Master3DS_B2C},
								
								{"TW", PaymentType.Visa_B2C},
								{"TW", PaymentType.AmericanExpress_B2C},
								{"TW", PaymentType.Master_B2C},
								{"TW", PaymentType.Discover_B2C},
								{"TW", PaymentType.Deposit_B2C},
								{"TW", PaymentType.Wire_B2C},
								{"TW", PaymentType.TwoCards_B2C},
								{"TW", PaymentType.Visa3DS_B2C},
								{"TW", PaymentType.Master3DS_B2C},

								{"CO", PaymentType.Deposit_B2C},
								{"CO", PaymentType.Master_B2C},
								{"CO", PaymentType.PayU_B2C},
								
								{"BR", PaymentType.Visa_B2C},
								{"BR", PaymentType.AmericanExpress_B2C},
								{"BR", PaymentType.Master_B2C},
								{"BR", PaymentType.Discover_B2C},
								{"BR", PaymentType.TwoCards_B2C},
								{"BR", PaymentType.PayU_B2C},
								{"BR", PaymentType.Boleto_B2C},
								
								{"MX", PaymentType.Visa_B2C},
								{"MX", PaymentType.AmericanExpress_B2C},
								{"MX", PaymentType.Master_B2C},
								{"MX", PaymentType.Discover_B2C},
								{"MX", PaymentType.TwoCards_B2C},
								{"MX", PaymentType.PayU_B2C},
								{"MX", PaymentType.Visa3DS_B2C},
								{"MX", PaymentType.Master3DS_B2C},
								{"MX", PaymentType.Mercado_B2C},
						}, PropsUtils.getTargetStorePayment("B2C"));
	}

	@Factory(dataProvider = "NA19353")
	public Object[] createTest(String store, PaymentType paymentType) {

		Object[] tests = new Object[1];

		tests[0] = new NA19353Test(store, paymentType);

		return tests;
	}

}