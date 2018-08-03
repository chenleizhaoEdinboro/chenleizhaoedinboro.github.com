package TestScript.FE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.FEPage;
import TestData.PreC.EnvData;
import TestScript.SuperTestClass;



/*
 * @owner Ridun
 * UX validation checklist
 */

public class SWAT1752Test extends SuperTestClass {
	public FEPage fePage;


public  SWAT1752Test (String store) {
	this.Store = store;
	this.testName = "SWAT1752Test";
}


@Test(priority=1,alwaysRun=true,groups={"migrationgroup"})
//for ux validation in sub series page

public void uxValidation() throws IOException, InterruptedException
{
	this.prepareTest();

	fePage = new FEPage(driver);

	System.out.println("inside test case");

	//driver.get("https://LIeCommerce:M0C0v0n3L!@tun-c-hybris.lenovo.com/us/en");

	driver.get(EnvData.LenovoProdUS+"us/en");

	Thread.sleep(15000);

	int count = 0;
	int imgSrcCount=0;
	String src=null;

	// for taking page source
	String pageSource = driver.getPageSource();

	// print pageSource
	System.out.println("page length is: "+pageSource.length());
	System.out.println("=======================================================================\n");

	FileUtils.cleanDirectory(new File("PageSource"));
	File newTextFile = new File("PageSource\\Pagesrc.txt");
	try {
		FileWriter fw = new FileWriter(newTextFile);
		fw.write(pageSource);
		fw.close();

	} catch (IOException iox) {
		
		iox.printStackTrace();
	}

	Scanner scanner = new Scanner(newTextFile);
	while (scanner.hasNextLine()) {
		String nextToken = scanner.next();
		if (nextToken.equalsIgnoreCase("<!--"))
			count++;
	}
	
	System.out.println("HTML comment tags count is: "+count);
	
	// check if doctype is the first tag in page or not
			
	String HTMLStartingElement[] = pageSource.toString().split(">");
	
	System.out.println( HTMLStartingElement[0]);	
	
	Assert.assertEquals(HTMLStartingElement[0], "<!DOCTYPE html","first element is not doc type in page");
	
	System.out.println("Passed:Doc type is the first tag..");
	
	// to check total number of img tag in page
	
	int imgcount=0;
	
	List<WebElement> listImages=driver.findElements(By.tagName("img"));
	
    System.out.println("HTML img tags size is: "+listImages.size());
    
    for(int i=0;i<listImages.size();i++){
		
		src=listImages.get(i).getAttribute("src").toString();
		
		if(src.contains("lenovo.com")){
			imgSrcCount++;
		}
		else{
			imgcount++;
		}
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("PageSource\\ImgSrc.txt", true)));
			out.println(src+"\n");
			out.close();
		}
		catch (IOException ioex) {
			ioex.printStackTrace();
		}
	
    }
    
    	// print number of img tags with lenovo.com
    	System.out.println("Img src with lenovo.com count is: "+imgSrcCount);

 		// print number of img tags without lenovo.com
    	System.out.println("Img src without lenovo.com count is: "+imgcount);
    
    	System.out.println("Img src with lazy load in header content is: "+fePage.imgsrcLazyloadHeader.size());
    	
    	System.out.println("Img src with lazy load in main body content is: "+fePage.imgsrcLazyloadMain.size());
    	
    	try {
    		System.out.println("Img src with lazy load in footer content is : "+fePage.imgsrcLazyloadFooter.size());
    		}
    		catch(Exception e) {
    			System.out.println("Img src with lazy load in footer content is : 0");
    		}
    	
    	// full page images
    	for(int i=0;i<listImages.size();i++){
			
			src=listImages.get(i).getAttribute("src").toString();
			if(src.contains("lazyLoadAfterDOMInteractive")){
				imgSrcCount++;
			}
			else{
				imgcount++;
			}
    	}
			// print number of img tags with lenovo.com
			System.out.println("Img src with lazy load in full page count is: "+imgSrcCount);

			// print number of img tags without lenovo.com
			System.out.println("Img src without lazy load in  full page count is: "+imgcount);

    
			
			//System.out.println("Img src with lazy load in header content is : "+fePage.imgsrcLazyloadHeader.size());
			
				for(int i=0;i<fePage.imgsrcLazyloadHeader.size();i++){
					//System.out.println("inside for loop");
					//System.out.println("img src class name :"+fePage.imgsrcLazyloadHeader.get(i).getAttribute("class"));
					src=fePage.imgsrcLazyloadHeader.get(i).getAttribute("class").toString();
					if(src.contains("lazyLoadAfterDOMInteractive")){
					imgSrcCount++;
					}
					else{
						System.out.println("inside else");
							count++;
					}
				}
		// print number of img tags with lenovo.com
				System.out.println("Img src with lazy load in header count is: "+imgSrcCount);
				
		// print number of img tags without lenovo.com
				System.out.println("Img src without lazy load in header count is: "+count);
				
						for(int i=0;i<fePage.imgsrcLazyloadFooter.size();i++){
							
								src=fePage.imgsrcLazyloadFooter.get(i).getAttribute("class").toString();
								
							if(src.contains("lazyLoadAfterDOMInteractive")){
									imgSrcCount++;
							}
								else{
									count++;
								}
							}
						
				// print number of img tags with lenovo.com
						System.out.println("Img src with lazy load in footer count is: "+imgSrcCount);
						
				// print number of img tags without lenovo.com
						System.out.println("Img src without lazy load in footer count is: "+count);
						
				// To import the js file created by Markus
						Thread.sleep(3000);
						
						String externalJS = org.apache.commons.io.FileUtils.readFileToString((new File("external.js")),Charset.forName("utf-8"));
		
				// Execute, assume no arguments, and no value to return
						Thread.sleep(3000);
						 ((JavascriptExecutor) driver).executeScript(externalJS);
						// to take screenshot of js pop up
//						FileUtils.cleanDirectory(new File("SEOCheker_Screenshot"));
//						org.apache.commons.io.FileUtils.copyFile(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE), new File("SEOCheker_Screenshot\\popUp"+".png"));


				// print js pop up text that contains all SEO checker details
					String jsPopupText = fePage.jsPopup.getText();
					
					System.out.println(jsPopupText);
		
    
}  

}

