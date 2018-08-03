package CommonFunction;

import static org.testng.Assert.assertTrue;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class JSONCommon {
public String  getJSONArrayValue(String JSONString,int index,String keyName){
	String keyValue="";
	JSONArray jsonArray = JSONArray.fromObject(JSONString); 
   
    JSONObject jUser = jsonArray.getJSONObject(index); 

     keyValue = jUser.getString(keyName); 
    return keyValue;
    	
}

}
