package CommonFunction;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpCommon {
	public static String HttpRequest(String requestUrl) {
		StringBuffer Buffer = new StringBuffer();
		InputStream input = getInputStream(requestUrl);
		InputStreamReader isreader = null;
		try {
			isreader = new InputStreamReader(input, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(isreader);
		String temp = null;
		try {
			while ((temp = bufferedReader.readLine()) != null) {
				Buffer.append(temp);
			}
			bufferedReader.close();
			isreader.close();
			input.close();
			input = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Buffer.toString();
	}

	private static InputStream getInputStream(String requestUrl) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream stream = null;
		try {
			url = new URL(requestUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.connect();

			stream = conn.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream;
	}

	public  String getJsonValue(String JsonString, String JsonId) {
		String JsonValue = "";
		if (JsonString == null || JsonString.trim().length() < 1) {
			return null;
		}
		try {
			JSONObject obj1 = new JSONObject(JsonString);
			JsonValue = (String) obj1.getString(JsonId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return JsonValue;
	}
	public static String verifyServiceStatus(String serviceUrl){
		String statusCode="";
		try {    
	          String surl=serviceUrl;
	               URL url = new URL(surl);
	               URLConnection rulConnection   = url.openConnection();
	               HttpURLConnection httpUrlConnection  =  (HttpURLConnection) rulConnection;
	               httpUrlConnection.setConnectTimeout(300000);
	               httpUrlConnection.setReadTimeout(300000);
	               httpUrlConnection.connect();
	               String code = new Integer(httpUrlConnection.getResponseCode()).toString();
	               String message = httpUrlConnection.getResponseMessage();
	               System.out.println("getResponseCode code ="+ code);
	              
	               System.out.println("getResponseMessage message ="+ message);
	               statusCode=code;
	               if(!code.startsWith("2")){
	                    throw new Exception("ResponseCode is not begin with 2,code="+code);
	                    
	               }
	               System.out.println("connection works fine ");
	          }catch(Exception ex){
	        	 
	               System.out.println(ex.getMessage());
	          }
		return statusCode;
	}
	

	public static String verifyServiceStatusPOST(String serviceUrl){
		String statusCode="";
		try {    
	          String surl=serviceUrl;
	               URL url = new URL(surl);
	               URLConnection rulConnection   = url.openConnection();
	               HttpURLConnection httpUrlConnection  =  (HttpURLConnection) rulConnection;
	               httpUrlConnection.setConnectTimeout(300000);
	               httpUrlConnection.setReadTimeout(300000);
	               httpUrlConnection.setRequestMethod("POST");
	               httpUrlConnection.connect();
	               String code = new Integer(httpUrlConnection.getResponseCode()).toString();
	               String message = httpUrlConnection.getResponseMessage();
	               System.out.println("getResponseCode code ="+ code);
	              
	               System.out.println("getResponseMessage message ="+ message);
	               statusCode=code;
	               if(!code.startsWith("2")){
	                    throw new Exception("ResponseCode is not begin with 2,code="+code);
	                    
	               }
	               System.out.println("connection works fine ");
	          }catch(Exception ex){
	        	 
	               System.out.println(ex.getMessage());
	          }
		return statusCode;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String HttpRequest(String requestUrl,String headerName, String headerValue) {
		StringBuffer Buffer = new StringBuffer();
		InputStream input = getInputStream(requestUrl,headerName,headerValue);
		InputStreamReader isreader = null;
		try {
			isreader = new InputStreamReader(input, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(isreader);
		String temp = null;
		try {
			while ((temp = bufferedReader.readLine()) != null) {
				Buffer.append(temp);
			}
			bufferedReader.close();
			isreader.close();
			input.close();
			input = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Buffer.toString();
	}

	private static InputStream getInputStream(String requestUrl,String headerName, String headerValue) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream stream = null;
		
		try {
			url = new URL(requestUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
			conn= (HttpURLConnection) url.openConnection();
            //设置http连接属性
			conn.setDoOutput(true);// http正文内，因此需要设为true, 默认情况下是false;
			conn.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setRequestMethod("GET"); // 可以根据需要 提交 GET、POST、DELETE、PUT等http提供的功能
            //connection.setUseCaches(false);//设置缓存，注意设置请求方法为post不能用缓存
            // connection.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Authorization", "Basic dGVzdHVzQHNoYXJrbGFzZXJzLmNvbToxcTJ3M2U0cg"); 
			conn.setRequestProperty("Host", "service-account.dit1.online.lenovo.com");  //设置请 求的服务器网址，域名，例如***.**.***.***
			conn.setRequestProperty("Content-Type", " application/json");//设定 请求格式 json，也可以设定xml格式的
			conn.setRequestProperty("Accept-Charset", "utf-8");  //设置编码语言
			conn.setRequestProperty("X-Auth-Token", "token");  //设置请求的token
			conn.setRequestProperty("Connection", "keep-alive");  //设置连接的状态

			conn.setRequestProperty("Transfer-Encoding", "chunked");//设置传输编码

			
			conn.setReadTimeout(10000);//设置读取超时时间          

			conn.setConnectTimeout(10000);//设置连接超时时间           

			conn.connect();  
			stream = conn.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream;
	}


	public static String verifyServiceStatus(String serviceUrl,String headerName, String headerValue){
		String statusCode="";
		try {    
			       System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
	               String surl=serviceUrl;
	               URL url = new URL(surl);
	               URLConnection rulConnection   = url.openConnection();
	               HttpURLConnection httpUrlConnection  =  (HttpURLConnection) rulConnection;
	               httpUrlConnection.setConnectTimeout(300000);
	               httpUrlConnection.setReadTimeout(300000);
	               httpUrlConnection.setRequestProperty(headerName, headerValue);
	               httpUrlConnection.connect();
	               String code = new Integer(httpUrlConnection.getResponseCode()).toString();
	               String message = httpUrlConnection.getResponseMessage();
	               System.out.println("getResponseCode code ="+ code);
	              
	               System.out.println("getResponseMessage message ="+ message);
	               statusCode=code;
	               if(!code.startsWith("2")){
	                    throw new Exception("ResponseCode is not begin with 2,code="+code);
	                    
	               }
	               System.out.println("connection works fine ");
	          }catch(Exception ex){
	        	 
	               System.out.println(ex.getMessage());
	          }
		return statusCode;
	}
	public static String[] EncodedHttpRequest(String encodedString, String requestUrl) throws IOException {
		String[] resultJSON=new String[2];
		String statusCode="";
		StringBuilder data = new StringBuilder();
		URL httpUrl = new URL(requestUrl);

		HttpURLConnection conn = (HttpURLConnection) httpUrl
				.openConnection();
		conn.setUseCaches(false);
		conn.setRequestMethod("GET");

		conn.setRequestProperty("Authorization", encodedString);
        
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		String line = null;
		while ((line = br.readLine()) != null) {
			data.append(line);
			data.append("\r\n");
		}
		statusCode=conn.getResponseCode()+"";
		resultJSON[0]=statusCode;
		conn.disconnect();
		
		resultJSON[1]=data.toString();
		return resultJSON;
		
		
		
		
		
		
		
		
		
		
	}
	public static String AddBodyHttpRequest(String requestUrl) throws IOException {
		String result="";
		
		Map<String,String> map=new HashMap<String,String>();
		
		map.put("password", "098f6bcd4621d373cade4e832627b4f6");
		map.put("name", "test");
			
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(requestUrl); 
		try{
		 
			ObjectMapper mapper = new ObjectMapper();
			String CreJSON="{\"name\":\"test\", \"password\":\"098f6bcd4621d373cade4e832627b4f6\"}"; 
			StringEntity s=new StringEntity(CreJSON, "UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
		  
		    HttpResponse response = client.execute(post);  
		    HttpEntity entity=response.getEntity();  
		    String returnMsg=EntityUtils.toString(entity,"UTF-8");
		    result=returnMsg;
		}catch (Exception e) {
			e.printStackTrace();
	    }finally{
	        /*释放链接*/
	        post.releaseConnection();
	    }
		
		
		
		return result;
		
		
	}
}
