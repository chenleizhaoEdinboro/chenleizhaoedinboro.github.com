package TestData.QAS.B2B.AU;

import TestData.B2BData;

public class Data extends B2BData{

	@Override
	public String getHomePageUrl() {
		return TestData.QAS.EnvData.HttpsDomain + "/le/adobe_global/au/en/1213654197";
	}

	@Override
	public String getDefaultPassword() {
		return "1q2w3e4r";
	}

	@Override
	public String getBuyerId() {
		return "aubuyer@sharklasers.com";
	}

	@Override
	public String getBuilderId() {
		return "aubuilder@sharklasers.com";
	}

	@Override
	public String getApproverId() {
		return "auapprover@sharklasers.com";
	}
	
	@Override
	public String getApproverId2() {
		return "auapprover2@sharklasers.com";
	}

	@Override
	public String getAdminId() {
		return "auadmin@sharklasers.com";
	}
	
	@Override
	public String getTelesalesId() {
		return "autelesales@sharklasers.com";
	}	

	@Override
	public String getDefaultMTMPN1() {
		return "20HGS0CB0P";
	}

	@Override
	public String getDefaultMTMPN2() {
		return "40A40090AU";
	}

	@Override
	public String getDefaultMTMPN3() {
		return "40A90090AU";
	}

	@Override
	public String getOverwritePN() {
		return "0A36188";
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
		return "Adobe Systems Pty Ltd";
	}
	
	@Override
	public String getAddressLine1(){
		return "L13, Tower B, 821 Pacific Highway";
	}
	
	@Override
	public String getAddressCity(){
		return "Chatswood";
	}
	
	@Override
	public String getAddressState(){
		return "New South Wales";
	}
	
	@Override
	public String getPostCode(){
		return "2319";
	}
	@Override
	public String getPhoneNum(){
		return "9835080087";
	}

	@Override
	public String getDefaultAccessoryPN1() {
		// TODO Auto-generated method stub
		return "61A6MAR3AU";
	}

	@Override
	public String getResetPasswordId() {
		return "aupassword@sharklasers.com";
	}
}
