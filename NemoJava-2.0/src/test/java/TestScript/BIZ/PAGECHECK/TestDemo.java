package TestScript.BIZ.PAGECHECK;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import TestData.PropsUtils;

public class TestDemo {
	private WebDriver driver;
	
	
	
	
	@BeforeClass
		// TODO Auto-generated method stub
//		TestData testData = new TestData(this.Environment,
//				this.getClass().getPackage().getName().replace(".", "/").split("/")[1], this.Store);
		public void setUp(){
		
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
		
	@Test
	public void Test() throws Exception{
		
		String url = "http://LIeCommerce:M0C0v0n3L!@pre-c-hybris.lenovo.com/us/en/";
		
		driver.get(url);
		
		Thread.sleep(5000);
		String pageSource = driver.getPageSource().toString();
		
		System.out.println("pageSource is :" + pageSource);
		
		Document document = DocumentHelper.parseText(pageSource);
		
		Element root = document.getRootElement();		
		
		List nodes = root.elements("meta");
		
		System.out.println("size is :" + nodes.size());
		
		String target = "";
		
		for(Iterator it = nodes.iterator(); it.hasNext();){
			
			Element ele = (Element) it.next();
			
			String str = ele.attributeValue("name");
			
			System.out.println("str is :" + str);
			
			
			
		}
		
		
		
		
		
		
		
		
	}
	
	@AfterClass
	public void closeDriver(){
		driver.close();
	}
		
		
		
	

}
