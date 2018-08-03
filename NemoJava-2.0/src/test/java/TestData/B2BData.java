package TestData;

public abstract class B2BData{
	public String getB2BUnit() {
		return getHomePageUrl().split("/")[7];
	}
	public String getLoginUrl() {		
		return getHomePageUrl() + "/login";
	}
	public String getDMULoginUrl(){
		return this.getHomePageUrl().replaceAll(this.getB2BUnit(), "") + "login";
	}
	public String getRepID(){
		return "2900718028";
	}
	public abstract String getHomePageUrl();
	public abstract String getDefaultPassword();
	public abstract String getBuyerId();
	public abstract String getBuilderId();
	public abstract String getApproverId();
	public abstract String getApproverId2();
	public abstract String getAdminId();
	public abstract String getTelesalesId();
	public abstract String getDefaultMTMPN1();
	public abstract String getDefaultMTMPN2();
	public abstract String getDefaultMTMPN3();
	public abstract String getOverwritePN();
	public abstract String getFirstName();
	public abstract String getLastName();
	public abstract String getCompany();
	public abstract String getAddressLine1();
	public abstract String getAddressCity();
	public abstract String getAddressState();
	public abstract String getPostCode();
	public abstract String getPhoneNum();
	public abstract String getDefaultAccessoryPN1();
	public abstract String getResetPasswordId();
}