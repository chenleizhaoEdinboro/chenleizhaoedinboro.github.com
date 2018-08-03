package TestData.PreC.B2B.US;

import TestData.B2BData;

public class Data extends B2BData{
	
	@Override
	public String getHomePageUrl() {
		return TestData.PreC.EnvData.HttpsDomain + "/le/1213348423/us/en/1213577815";
	}

	@Override
	public String getDefaultPassword() {
		return "1q2w3e4r";
	}

	@Override
	public String getBuyerId() {
		return "usbuyer@sharklasers.com";
	}

	@Override
	public String getBuilderId() {
		return "usbuilder@sharklasers.com";
	}

	@Override
	public String getApproverId() {
		return "usapprover@sharklasers.com";
	}
	
	@Override
	public String getApproverId2() {
		return "usapprover2@sharklasers.com";
	}

	@Override
	public String getAdminId() {
		return "usadmin@sharklasers.com";
	}
	
	@Override
	public String getTelesalesId() {
		return "ustelesales@sharklasers.com";
	}

	@Override
	public String getDefaultMTMPN1() {
		return "20JES1X200";
	}

	@Override
	public String getDefaultMTMPN2() {
		return "20JES1X200";
	}

	@Override
	public String getDefaultMTMPN3() {
		return "20JES1X200";
	}

	@Override
	public String getOverwritePN() {
		return "60F5MAR6US";
	}
	
	@Override
	public String getFirstName(){
		return "John";
	}
	
	@Override
	public String getLastName(){
		return "Snow";
	}
	
	@Override
	public String getCompany(){
		return "ROWAN UNIVERSITY CENTRAL RECEIVING";
	}
	
	@Override
	public String getAddressLine1(){
		return "201 MULICA HILL RD";
	}
	
	@Override
	public String getAddressCity(){
		return "Glassboro";
	}
	
	@Override
	public String getAddressState(){
		return "New Jersey";
	}
	
	@Override
	public String getPostCode(){
		return "08028-1700";
	}
	@Override
	public String getPhoneNum(){
		return "11122233301";
	}

	@Override
	public String getDefaultAccessoryPN1() {
		// TODO Auto-generated method stub
		return "61A6MAR3AU";
	}

	@Override
	public String getResetPasswordId() {
		return "uspassword@sharklasers.com";
	}
}
