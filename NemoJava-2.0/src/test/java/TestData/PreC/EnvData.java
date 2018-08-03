package TestData.PreC;

public class EnvData extends TestData.EnvData {
	public static String HttpDomain = "http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com";
	public static String HttpsDomain = "https://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com";
	public static String TeleSalesDomain = "https://LIeCommerce:M0C0v0n3L!@test-tele-hybris.lenovo.com";
	public static String PriceServiceDomain="http://service-price.pre.online.lenovo.com/api/v1/price/getPrice?productCodes=";
	public static String ProductServiceDomain="http://service-product.pre.online.lenovo.com/api/v1/product/";
	public static String CategoryServiceAllDomain="http://service-catalog.pre.online.lenovo.com/api/v1/internal/catalog/all";
	public static String CategoryLaptopTreeDomain="ttp://service-catalog.pre.online.lenovo.com/api/v1/catalog/children/Laptops";
	public static String CategoryGetChildrenDomain="http://service-catalog.pre.online.lenovo.com/api/v1/catalog/children/";
	public static String AccountAuthenticateServiceDomain="http://service-account.pre.online.lenovo.com/api/v1/user/authenticate?id=";
	public static String AccountAuthenticateServiceDomainv2="http://service-account.pre.online.lenovo.com/api/v2/user/authenticate?";
	public static String StoreServiceDomain="http://10.99.231.74:9003/api/v1/pree";
	public static String ContentServiceDomain="http://10.99.231.74:9004/";

	public static String SitDomain = "http://web-content.pre.online.lenovo.com/";
	public static String LenovoProdUS = "https://www3.lenovo.com/";
	public static String MobileProductTreeDomain="http://service-ecomm.pre.online.lenovo.com/api/v1/mobile/product/tree/";
	public static String MobileGetProductDomain="http://service-ecomm.pre.online.lenovo.com/api/v1/mobile/product/";
	public static String MobileProductVariantDomain="http://service-ecomm.pre.online.lenovo.com/api/v1/mobile/product/variants/";
	
	
	public static String B2CUserAccountServiceDomain="http://service-account.pre.online.lenovo.com/api/v1/user/access/";
	public static String B2BUserAccountServiceDomain="http://service-account.pre.online.lenovo.com/api/v1/user/b2b/access/";
	public static String AccountAuthorizationServiceDomain="http://service-account.pre.online.lenovo.com/api/v1/user/authorization/";
	public static String AccountAuthorizationServiceDomainv2="http://service-account.pre.online.lenovo.com/api/v2/user/authorization";
	public static String AccountB2BAccessDomainv2="http://service-account.pre.online.lenovo.com/api/v2/user/authorization/access";
	
	public static String getStockDomain="http://service-stock.pre.online.lenovo.com/api/v1/stock/";
	public static String ReserveStockDomain="http://service-stock.pre.online.lenovo.com/api/v1/stock/reserve";
	public static String ReleaseStockDomain="http://service-stock.pre.online.lenovo.com/api/v1/stock/release";
	public static String purchaseStockServiceDomain="http://service-stock.pre.online.lenovo.com/api/v1/stock/purchase";
	public static String subScriptionUpdateQuantityDomain="http://service-subscription.pre.online.lenovo.com/api/v1/subscription/changeQuantity?subscriptionItemID=";
	public static String subScriptionGetOrderBySubID="http://service-subscription.pre.online.lenovo.com/api/v1/subscription/getOrderItemDetailsBySubscriptionId/";
	public static String subScriptionUpdateStatusDomain="http://service-subscription.pre.online.lenovo.com/api/v1/subscription/changeStatus?subscriptionItemID=";
	public static String subScriptionGetOrderListByCustomerIdDomain="http://service-subscription.pre.online.lenovo.com/api/v1/subscription/getOrderListByCustomerId?customerId=";
	
	public static String accountGenerateTokenDomain="http://service-account.pre.online.lenovo.com/api/v2/user/authenticate/token?type=";
	
	@SuppressWarnings("static-access")
	@Override
	public String getLenovoProdUS() {
		return this.LenovoProdUS;
	}


	@SuppressWarnings("static-access")
	@Override
	public String getSitDomain() {
		return this.SitDomain;
	}

	@SuppressWarnings("static-access")
	@Override
	public String getHttpsDomain() {
		return this.HttpsDomain;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public String getTeleSalesDomain() {
		return this.TeleSalesDomain;
	}
	@SuppressWarnings("static-access")
	@Override
	public String getPriceServiceDomain() {
		return this.PriceServiceDomain;
	}
	@SuppressWarnings("static-access")
	@Override
	public String getProductServiceDomain() {
		return this.ProductServiceDomain;
	}
	@SuppressWarnings("static-access")
	@Override
	public String getStoreServiceDomain() {
		return this.StoreServiceDomain;
	}
	@SuppressWarnings("static-access")
	@Override
	public String getContentServiceDomain() {
		return this.ContentServiceDomain;
	}
	@Override
	public String getCategoryAllServiceDomain() {
		return this.CategoryServiceAllDomain;
	}
	
	@Override
	public String getCategorygetChildrenServiceDomain() {
		return this.CategoryGetChildrenDomain;
	}
	@Override
	public String getLaptopsTreeChildrenServiceDomain() {
		return this.CategoryLaptopTreeDomain;
	}
	@Override
	public String getAccountAuthenticateServiceDomain() {
		return this.AccountAuthenticateServiceDomain;
	}
	@Override
	public String getAccountAuthenticateServiceDomainv2() {
		return this.AccountAuthenticateServiceDomainv2;
	}
	@Override
	public String getB2CUserAccountServiceDomain() {
		return this.B2CUserAccountServiceDomain;
	}
	
	@Override
	public String getB2BUserAccountServiceDomain() {
		return this.B2BUserAccountServiceDomain;
	}
	
	@Override
	public String getAccountAuthorizationServiceDomain() {
		return this.AccountAuthorizationServiceDomain;
	}
	@Override
	public String getAccountAuthorizationServiceDomainv2() {
		return this.AccountAuthorizationServiceDomainv2;
	}
	@Override
	public String getAccountB2BAccessDomainv2() {
		return this.AccountB2BAccessDomainv2;
	}
	
	@Override
	public String getStockServiceDomain() {
		return this.getStockDomain;
	}
	@Override
	public String reserveStockServiceDomain() {
		return this.ReserveStockDomain;
	}
	@Override
	public String releaseStockServiceDomain() {
		return this.ReleaseStockDomain;
	}
	@Override
	public String purchaseStockServiceDomain() {
		return this.purchaseStockServiceDomain;
	}


	@Override
	public String getHttpDomain() {
		return this.HttpDomain;
	}
	


	@Override
	public String getSubUpdateQuantityDomain() {
		return this.subScriptionUpdateQuantityDomain;
	}
	@Override
	public String getSubScriptionUpdateStatusDomain() {
		return this.subScriptionUpdateStatusDomain;
	}
	
	@Override
	public String getSubOrderBySubIDDomain() {
		return this.subScriptionGetOrderBySubID;
	}
	@Override
	public String getSubScriptionGetOrderListByCustomerIdDomainn() {
		return this.subScriptionGetOrderListByCustomerIdDomain;
	}
	
	@Override
	public String getAccountGenerateTokenDomain() {
		return this.accountGenerateTokenDomain;
	}
	@Override
	public String getMobileProductTreeDomain() {
		return this.MobileProductTreeDomain;
	}
	@Override
	public String getMobileGetProductDomain() {
		return this.MobileGetProductDomain;
	}
	@Override
	public String getMobileProductVariantDomain() {
		return this.MobileProductVariantDomain;
	}
	
}

