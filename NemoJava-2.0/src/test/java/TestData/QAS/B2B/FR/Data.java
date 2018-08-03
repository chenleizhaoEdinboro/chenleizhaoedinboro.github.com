package TestData.QAS.B2B.FR;

import TestData.B2BData;

// This is a dummy data for place holder of DR75-B2B
public class Data extends B2BData{
	
	@Override
	public String getHomePageUrl() {
//		return TestData.QAS.EnvData.HttpsDomain + "/le/1214206755/jp/en/1214210743";
		return "";
	}

	@Override
	public String getDefaultPassword() {
		return "1q2w3e4r";
	}

	@Override
	public String getBuyerId() {
//		return "jpbuyer@sharklasers.com";
		return "";
	}

	@Override
	public String getBuilderId() {
//		return "jpbuilder@sharklasers.com";
		return "";
	}

	@Override
	public String getApproverId() {
		// TODO Auto-generated method stub
//		return "jpapprover@sharklasers.com";
		return "";
	}
	
	@Override
	public String getApproverId2() {
		// TODO Auto-generated method stub
//		return "jpapprover2@sharklasers.com";
		return "";
	}

	@Override
	public String getAdminId() {
		// TODO Auto-generated method stub
//		return "jpadmin@sharklasers.com";
		return "";
	}

	@Override
	public String getTelesalesId() {
		// TODO Auto-generated method stub
//		return "jptelesales@sharklasers.com";
		return "";
	}

	@Override
	public String getDefaultMTMPN1() {
//		return "61A6MAR3JP";
		return "";
	}

	@Override
	public String getDefaultMTMPN2() {
//		return "4XH0L55146";
		return "";
	}

	@Override
	public String getDefaultMTMPN3() {
//		return "4X30H56841";
		return "";
	}

	@Override
	public String getOverwritePN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFirstName() {
		// TODO Auto-generated method stub
		return "John";
	}

	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return "Snow";
	}

	@Override
	public String getCompany() {
		// TODO Auto-generated method stub
		return "東京都荒川";
	}

	@Override
	public String getAddressLine1() {
		// TODO Auto-generated method stub
		return "東京都荒川区西尾久二丁目17番4号";
	}

	@Override
	public String getAddressCity() {
		// TODO Auto-generated method stub
		return "荒川";
	}

	@Override
	public String getAddressState() {
		// TODO Auto-generated method stub
		return "Tokyo";
	}

	@Override
	public String getPostCode() {
		// TODO Auto-generated method stub
		return "1060032";
	}

	@Override
	public String getPhoneNum() {
		// TODO Auto-generated method stub
		return "0432535196";
	}

	@Override
	public String getDefaultAccessoryPN1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResetPasswordId() {
		return "jppassword@sharklasers.com";
	}
}
