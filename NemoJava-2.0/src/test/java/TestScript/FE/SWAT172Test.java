package TestScript.FE;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import Pages.FEPage;
import Pages.HMCPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;
import junit.framework.Assert;

/*
 * msharma2
 * Validation for "meta data"
 */
public class  SWAT172Test  extends SuperTestClass {
	public FEPage fePage;
	public HMCPage hmcPage;

	public  SWAT172Test(String store,String country) {
		this.Store = store;
		this.testName = "SWAT172Test";

	}

	/*
	 * 	msharma2
	 * 	Validation for meta data
	 */

	
	@Test(groups={"fescripts"})
	public void metaData() throws InterruptedException, MalformedURLException {
		this.prepareTest();

		try {
			fePage = new FEPage(driver);
			hmcPage = new HMCPage(driver);
			System.out.println("inside test case");
			System.out.println(EnvData.SitDomain.replace("sit","uat")+"us/en/ww-hp#");
			driver.get(EnvData.SitDomain.replace("sit","uat")+"us/en/ww-hp#");
			Thread.sleep(20000);

			Map<String, String> hm = new HashMap<String, String>();
			hm.put("storeinfo.storeid","usweb");
			hm.put("storeinfo.storename","US Web Store");
			hm.put("storeinfo.storetype","PUBLIC_CONSUMER");
			hm.put("storeinfo.salestype","DIRECT");
			hm.put("storeType","PUBLIC_CONSUMER");
			hm.put("storeID","usweb");
//			hm.put("currencycode","USD");
//			hm.put("language","en");
//			hm.put("lenovo.country","US");
//			hm.put("country","US");
//			hm.put("cc","US");
//			hm.put("lc","en");
//			hm.put("server","");
//			hm.put("asmindicator","off");
//			hm.put("bu","PUBLIC_CONSUMER");
//			hm.put("viewport","width=device-width,initial-scale=1.0");
//			hm.put("robots","INDEX,FOLLOW");
//			hm.put("keywords","");
//			hm.put("description","Find & buy the right laptop, tablet, desktop or server. Build your own PC today or call our sales team 1-855-2-LENOVO (1-855-253-6686).");
//			hm.put("area","PUBLIC_CONSUMER");
//			hm.put("regioncode","NA");
//			hm.put("lenovo.language","en");
//			hm.put("dc.language","en");
//			hm.put("currency","USD");	
//			hm.put("host","");
//			hm.put("productcatalogid","masterMultiCountryProductCatalog");
//			hm.put("productinfo.category","masterMultiCountryProductCatalog");
//			hm.put("taxonomytype","homepage");
//			hm.put("title","Lenovo Official US Site | Computers, Smartphones, Data Center");
//			hm.put("internalsearchcanonical","/us/en/");			
//			hm.put("BPID","US00000001");		
//			hm.put("pagePath","");
//			hm.put("pageName","Lenovo Official US Site | Computers, Smartphones, Data Center");
//			hm.put("pageBreadcrumb","");
//			hm.put("PageTitle","Lenovo Official US Site | Computers, Smartphones, Data Center");
//			hm.put("siteRoot","/us/en");																			

			System.out.println("expected meta data size is: "+hm.keySet().size());

			ArrayList<String> keys=new ArrayList<>();
			Iterator<String> keyIterator = hm.keySet().iterator();
			while(keyIterator.hasNext()) {
				String key = keyIterator.next();
				//				System.out.println(key);
				keys.add(key);
			}

			System.out.println("expected meta data size is: "+keys.size());
			System.out.println("actual meta data size is: "+driver.findElements(By.xpath("//meta[@name!='']")).size());
			ArrayList<String> actualMetaData=new ArrayList<>();
			
	
			
			for(int i=0;i<driver.findElements(By.xpath("//meta[@name!='']")).size();i++) {
				actualMetaData.add(driver.findElements(By.xpath("//meta[@name!='']")).get(i).getAttribute("name"));
		}
//				String s1=driver.findElements(By.xpath("//meta[@name!='']")).get(i).getAttribute("content");
//
//				if(keys.contains(driver.findElements(By.xpath("//meta[@name!='']")).get(i).getAttribute("name"))) {
//					String	s2=hm.get(driver.findElements(By.xpath("//meta[@name!='']")).get(i).getAttribute("name"));				
//					System.out.println("actual: "+s1);
//					System.out.println("expected: "+s2);
//					
//					if(!(s1.equalsIgnoreCase(s2))){
//						System.out.println("content is not equal for: "+driver.findElements(By.xpath("//meta[@name!='']")).get(i).getAttribute("name"));
//					}
//					else {
//						System.out.println("content is equal for: "+driver.findElements(By.xpath("//meta[@name!='']")).get(i).getAttribute("name"));
//					}
//				}
//				else {
//					System.out.println("There are extra meta data in the page: "+driver.findElements(By.xpath("//meta[@name!='']")).get(i).getAttribute("name"));
//				}
//			}
//
//			System.out.println("actual meta data size is: "+actualMetaData.size());
//
//			// add for loop to check that actual meta data is containing all expected data or not
			Assert.assertTrue(actualMetaData.contains("viewport"));
			Assert.assertTrue(actualMetaData.contains("robots"));
			
			for(int i=0;i<keys.size();i++) {
				// uncomment below line for assert condition
//				Assert.assertTrue(actualMetaData.contains(keys.get(i)));
				
				if(!(actualMetaData.contains(keys.get(i)))) {
					System.out.println("missing meta data size is: "+keys.get(i));
				}
				else {
					String s1=driver.findElement(By.xpath("//meta[@name='"+keys.get(i)+"']")).getAttribute("content");
					String s2=hm.get(keys.get(i));
					boolean flag=s1.equalsIgnoreCase(s2);
					System.out.println(flag);
					Assert.assertTrue(flag);
				}
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		driver.close();
	}
	
	/*
	 * msharma2
	 * acc pdp meta data validation
	 */
	
	@Test(groups={"fescripts"})
	public void pdpmetaData() throws InterruptedException, MalformedURLException {
		this.prepareTest();

		try {
			fePage = new FEPage(driver);
			hmcPage = new HMCPage(driver);
			System.out.println("inside test case");
			System.out.println(EnvData.SitDomain+"us/en/p/60DFAAR1US");
			driver.get(EnvData.SitDomain+"us/en/p/60DFAAR1US");
	
			Thread.sleep(20000);

			Map<String, String> hm = new HashMap<String, String>();
			hm.put("storeinfo.storeid","usweb");
			hm.put("storeinfo.storename","US Web Store");
			hm.put("storeinfo.storetype","PUBLIC_CONSUMER");
			hm.put("storeinfo.salestype","DIRECT");
			hm.put("storeType","PUBLIC_CONSUMER");
			hm.put("storeID","usweb");
			
			hm.put("title","Lenovo 45W AC Wall Adapter | Lenovo US");
			hm.put("productinfo.recid","en");
			hm.put("productid","GX20K11838");
			hm.put("productcode","GX20K11838");
			hm.put("bundleid","GX20K11838");
			hm.put("configtype","Accessory");
			hm.put("productinfo.type","Accessory");
			hm.put("productinfo.saleprice","off");
			hm.put("productsaleprice","productsaleprice");
			hm.put("productprice","24.99");
			hm.put("thumbnail" ,"/medias/?context=bWFzdGVyfHJvb3R8MjI3Mjl8aW1hZ2UvanBlZ3xoNDQvaGViLzk0NjQ5NTY3NDc4MDYuanBnfDQyZjQwYmQ3YTAyN2U5YzQ4YmEwMjcwMTJkMGRlMGVlMGEzYTU4ODFlMzY5NmNhNjZjMzdiMTMzYTQ0N2NmMjc");
			hm.put("pdp","pdp");
			hm.put("subseriesPHcode","GX20K11838");
			hm.put("productcodeimpressions","");
			hm.put("bundleIDimpressions","");
			hm.put("productname","");
			hm.put("productInfo.configType","Accessory");
			hm.put("productInfo.name","Lenovo 45W AC Wall Adapter (UL)");
			hm.put("productInfo.category","");
			hm.put("productInfo.salePrice","19.99");
			hm.put("pageName","Lenovo 45W AC Wall Adapter | Lenovo US");
			hm.put("PageTitle","Lenovo 45W AC Wall Adapter | Lenovo US");
			hm.put("internalsearchcanonical","/accessories-and-monitors/chargers-and-batteries/chargers/PWR-ADP-BO-45W-Wall-Mount-AC-AdapterUL/p/GX20K11838");
			hm.put("pagePath","Lenovo 45W AC Wall Adapter (UL):Chargers:Chargers & Batteries:Accessories and Monitors");
			hm.put("pageBreadcrumb","Lenovo 45W AC Wall Adapter (UL):Chargers:Chargers & Batteries:Accessories and Monitors");
			hm.put("productinfo.pageurl","https://www3.lenovo.com/us/en/accessories-and-monitors/chargers-and-batteries/chargers/PWR-ADP-BO-45W-Wall-Mount-AC-AdapterUL/p/GX20K11838");
			hm.put("productinfo.salepriceexpiration","Sun May 21 09:50:48 UTC 2028");
			hm.put("productinfo.salepriceusd","");
			hm.put("inventory","454");
			hm.put("brand","Chargers & Batteries");
			hm.put("series","Chargers & Batteries_Chargers");																						

			System.out.println("expected meta data size is: "+hm.keySet().size());

			ArrayList<String> keys=new ArrayList<>();
			Iterator<String> keyIterator = hm.keySet().iterator();
			while(keyIterator.hasNext()) {
				String key = keyIterator.next();
				//				System.out.println(key);
				keys.add(key);
			}

			System.out.println("expected meta data size is: "+keys.size());
			System.out.println("actual meta data size is: "+driver.findElements(By.xpath("//meta[@name!='']")).size());
			ArrayList<String> actualMetaData=new ArrayList<>();
			
	
			
			for(int i=0;i<driver.findElements(By.xpath("//meta[@name!='']")).size();i++) {
				actualMetaData.add(driver.findElements(By.xpath("//meta[@name!='']")).get(i).getAttribute("name"));
		   }

			
			Assert.assertTrue(actualMetaData.contains("viewport"));
			Assert.assertTrue(actualMetaData.contains("robots"));
			ArrayList<String> missingmetaData=new ArrayList<>();
			
			// add for loop to check that actual meta data is containing all expected data or not			
			for(int i=0;i<keys.size();i++) {
				// uncomment below line for assert condition
//				Assert.assertTrue(actualMetaData.contains(keys.get(i)));
				
				if(!(actualMetaData.contains(keys.get(i)))) {
					System.out.println("missing meta data is: "+keys.get(i));
					missingmetaData.add(keys.get(i));
				}
				else {
					String s1=driver.findElement(By.xpath("//meta[@name='"+keys.get(i)+"']")).getAttribute("content");
					String s2=hm.get(keys.get(i));
					boolean flag=s1.equalsIgnoreCase(s2);
					System.out.println(keys.get(i)+" "+flag);
//					Assert.assertTrue(flag);
				}
			}
			
			System.out.println("============================================================================");
			System.out.println("missed meta data size is: "+missingmetaData.size());
			for(int i=0;i<missingmetaData.size();i++) {							
				System.out.println("missed meta data name is: "+missingmetaData.get(i));
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		driver.close();
	}
}
