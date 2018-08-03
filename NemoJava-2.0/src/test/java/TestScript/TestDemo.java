package TestScript;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestDemo {

	@BeforeClass
		// TODO Auto-generated method stub
//		TestData testData = new TestData(this.Environment,
//				this.getClass().getPackage().getName().replace(".", "/").split("/")[1], this.Store);
		public void setUp(){
		String message = this.getClass().getPackage().getName().replace(".", "/").split("/")[1];
		System.out.println("message is :" + message);
	}
		
	@Test
	public void Test(){
		
	}
		
		
		
	

}
