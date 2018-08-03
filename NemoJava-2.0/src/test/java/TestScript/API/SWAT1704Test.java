package TestScript.API;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import CommonFunction.HttpCommon;
import CommonFunction.JSONCommon;
import CommonFunction.DesignHandler.NavigationBar;
import CommonFunction.DesignHandler.SplitterPage;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import Pages.MailPage;
import TestScript.ServiceSuperTestClass;
import TestScript.SuperTestClass;

public class SWAT1704Test extends ServiceSuperTestClass {

	private String parameter;
	private String userid;
	private String contextString;
	private String contextPara;
	private String password;
	private String expectedname;
	private String expectedProductNumber;
	private String expectedtype;
	private String expectedconsumerId;
	private String modelParameter;
	private String VisibilityParameter;
	private String tempCode;
	private String tempID;
	private String encodedPW;
	private String authStringEnc ;
	@Autowired
	private RestTemplate restTemplate;

	public SWAT1704Test(String store, String userid, String paraString,
			String pw, String name, String type, String consumerId,String encodedpw) {
		this.Store = store;

		this.userid = userid;
		this.parameter = paraString;
		this.password = pw;
		this.expectedname = name;
		this.expectedtype = type;
		this.expectedconsumerId = consumerId;
        this.encodedPW=encodedpw;
		this.testName = "SWAT1704";
		super.serviceName = "Generate&RenewToken";
	}

	@Test(alwaysRun = true)
	public void DemoScriptRun(ITestContext ctx) {
		try {

			this.prepareTest();

			HttpCommon hCommon = new HttpCommon();
			JSONCommon JCommon = new JSONCommon();
			super.paraString = "id=" + userid + "; password =" + password
					+ ";user type is " + expectedtype;
			String serviceURL = testData.envData.getAccountGenerateTokenDomain() + expectedtype;


			try {
			   if(!parameter.equals("type=LENOVOID")){
				   authStringEnc=  encodedPW;
			   }else{
				   String authString = userid + ":" + password;

					byte[] authEncBytes = Base64
							.encodeBase64(authString.getBytes());
					 authStringEnc = new String(authEncBytes);
			   }
				
				
				String[] resultJSON=hCommon.EncodedHttpRequest("Basic "+authStringEnc, serviceURL);
				super.serviceStatus = resultJSON[0];
				assert super.serviceStatus.equals("200"):"actual status is "+super.serviceStatus;
		        assert !resultJSON[1].equals("");
			      System.out.println(resultJSON[1]);
				

			} catch (Exception e) {
				assert super.serviceStatus.equals("200"):"actual status is "+super.serviceStatus;
			}

			

		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}

}