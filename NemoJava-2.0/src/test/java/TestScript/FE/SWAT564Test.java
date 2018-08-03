package TestScript.FE;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.util.FileCopyUtils;
import org.testng.annotations.Test;

import Pages.FEPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;

public class  SWAT564Test  extends SuperTestClass {
	public FEPage b2cPage;
	public HMCPage hmcPage;

	public  SWAT564Test(String store) {
		this.Store = store;
		this.testName = "SWAT564Test";

	}

	/*
	 * 	msharma2
	 * 	validation for hero section carousel images and espot images in home page
	 */


	@Test(groups={"fescripts"})
	public void contentMigration() throws InterruptedException, IOException {
		this.prepareTest();
		
		new File("C:\\Users\\msharma2\\Desktop\\differences").mkdir();
		FileUtils.cleanDirectory(new File("C:\\Users\\msharma2\\Desktop\\differences"));
		FileUtils.cleanDirectory(new File("PageSource"));
		
		try {
			b2cPage = new FEPage(driver);
			hmcPage = new HMCPage(driver);
			System.out.println("inside test case");
			DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date dateobj = new Date();
			int i=0;
			JavascriptExecutor jse = (JavascriptExecutor)driver;

			System.out.println("urls size is: "+testData.B2C.getContentMigrationURLs().size());
			
			while(i<testData.B2C.getContentMigrationURLs().size()-1){
				driver.navigate().to(testData.B2C.getContentMigrationURLs().get(i));	
				Thread.sleep(10000);
				new File("C:\\Users\\msharma2\\Desktop\\differences\\diff-"+i+"").mkdir();
				FileUtils.cleanDirectory(new File("C:\\Users\\msharma2\\Desktop\\differences\\diff-"+i+""));
				
				File scrFile =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				String current = "C:\\Users\\msharma2\\Desktop\\differences\\diff-"+i+"\\url-"+i+"_"+df.format(dateobj)+".jpg";
				FileCopyUtils.copy(scrFile,new File(current));
				
				jse.executeScript("window.scrollBy(0,500)", "");
				Thread.sleep(1000);
				scrFile =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				String current1 = "C:\\Users\\msharma2\\Desktop\\differences\\diff-"+i+"\\url_halfscroll"+i+"_"+df.format(dateobj)+".jpg";
				FileCopyUtils.copy(scrFile,new File(current1));
				
				jse.executeScript("window.scrollBy(0,5000)", "");
				Thread.sleep(1000);
				scrFile =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				String current2 = "C:\\Users\\msharma2\\Desktop\\differences\\diff-"+i+"\\url_fullscroll"+i+"_"+df.format(dateobj)+".jpg";
				FileCopyUtils.copy(scrFile,new File(current2));
				
				String pageSource = driver.getPageSource();
				// println pageSource
				System.out.println("page length is: "+pageSource.length());
				System.out.println("=======================================================================\n");

//				FileUtils.cleanDirectory(new File("PageSource"));
				File newTextFile = new File("PageSource\\Pagesrc"+i+".txt");


				try {
					FileWriter fw = new FileWriter(newTextFile);
					fw.write(pageSource);
					fw.close();

				} catch (IOException iox) {
					//do stuff with exception
					iox.printStackTrace();
				}

				driver.navigate().to(testData.B2C.getContentMigrationURLs().get(i+1));
				Thread.sleep(10000);
				File scrFile1 =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				int j=i+1;
				String exp = "C:\\Users\\msharma2\\Desktop\\differences\\diff-"+i+"\\url-"+j+"_"+df.format(dateobj)+".jpg";
				FileCopyUtils.copy(scrFile1,new File(exp));
				
				jse.executeScript("window.scrollBy(0,500)", "");
				Thread.sleep(1000);
				scrFile1 =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				String exp1 = "C:\\Users\\msharma2\\Desktop\\differences\\diff-"+i+"\\url_halfscroll"+j+"_"+df.format(dateobj)+".jpg";
				FileCopyUtils.copy(scrFile1,new File(exp1));
				
				jse.executeScript("window.scrollBy(0,5000)", "");
				Thread.sleep(1000);
				scrFile1 =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				String exp2 = "C:\\Users\\msharma2\\Desktop\\differences\\diff-"+i+"\\url_fullscroll"+j+"_"+df.format(dateobj)+".jpg";
				FileCopyUtils.copy(scrFile1,new File(exp2));
				
				boolean compareSuccess =compareImages(exp,current, "C:\\Users\\msharma2\\Desktop\\differences\\diff-"+i+"\\difference_"+i+"_"+df.format(dateobj)+".jpg");
				System.out.println("compare success is: "+compareSuccess);
				boolean compareSuccess1 =compareImages(exp1,current1, "C:\\Users\\msharma2\\Desktop\\differences\\diff-"+i+"\\difference_halfscroll_"+i+"_"+df.format(dateobj)+".jpg");
				System.out.println("compare success scroll is: "+compareSuccess1);
				boolean compareSuccess2 =compareImages(exp2,current2, "C:\\Users\\msharma2\\Desktop\\differences\\diff-"+i+"\\difference_fullscroll"+i+"_"+df.format(dateobj)+".jpg");
				System.out.println("compare success is: "+compareSuccess2);
			

				pageSource = driver.getPageSource();
				// println pageSource
				System.out.println("page length is: "+pageSource.length());
				System.out.println("=======================================================================\n");

				//			FileUtils.cleanDirectory(new File("PageSource"));
	//			int j=i+1;
				newTextFile = new File("PageSource\\Pagesrc"+j+".txt");

				try {
					FileWriter fw = new FileWriter(newTextFile);
					fw.write(pageSource);
					fw.close();

				} catch (IOException iox) {
					//do stuff with exception
					iox.printStackTrace();
				}

				BufferedReader br1 = null;
				BufferedReader br2 = null;
				String sCurrentLine;
				List<String> list1 = new ArrayList<String>();
				List<String> list2 = new ArrayList<String>();
				br1 = new BufferedReader(new FileReader("PageSource\\Pagesrc"+i+".txt"));
				br2 = new BufferedReader(new FileReader("PageSource\\Pagesrc"+j+".txt"));

				while ((sCurrentLine = br1.readLine()) != null) {
					list1.add(sCurrentLine);
				}
				while ((sCurrentLine = br2.readLine()) != null) {
					list2.add(sCurrentLine);
				}

				List<String> tmpList = new ArrayList<String>(list1);
				tmpList.removeAll(list2);
				System.out.println(tmpList.size());
				System.out.println("content from Pagesrc1 which is not there in Pagesrc2 is in diff1.txt");

				try ( BufferedWriter bw = 
						new BufferedWriter (new FileWriter(new File("PageSource\\diff"+i+".txt"))))
				{			
					for (String line : tmpList) {
						bw.write (line + "\n");
					}

					bw.close ();

				} catch (IOException e) {
					e.printStackTrace ();
				}
				tmpList = list2;
				tmpList.removeAll(list1);
				System.out.println(tmpList.size());
				System.out.println("content from Pagesrc2 which is not there in Pagesrc1 is in diff2.txt");

				try ( BufferedWriter bw = 
						new BufferedWriter (new FileWriter(new File("PageSource\\diff"+j+".txt")))) 
				{			
					for (String line : tmpList) {
						bw.write (line + "\n");
					}

					bw.close ();

				} catch (IOException e) {
					e.printStackTrace ();
				}
				br1.close();
				br2.close();
				i=i+2;
			}
			
			/*

						Thread.sleep(15000);
						ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \"C:\\wraith\" && wraith capture configs/capture.yaml");
								builder.redirectErrorStream(true);
								Process p = builder.start();
								BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
								String line;
								while (true){
									line = r.readLine();
									if (line == null) { break; }
									System.out.println("command prompt text"+line);
								}

						String pageSource = driver.getPageSource();
						// println pageSource
						System.out.println("page length is: "+pageSource.length());
						System.out.println("=======================================================================\n");

						FileUtils.cleanDirectory(new File("PageSource"));
						File newTextFile = new File("PageSource\\Pagesrc1.txt");


						try {
							FileWriter fw = new FileWriter(newTextFile);
							fw.write(pageSource);
							fw.close();

						} catch (IOException iox) {
							//do stuff with exception
							iox.printStackTrace();
						}

						driver.navigate().to("https://www3.lenovo.com/ca/en/");
						Thread.sleep(10000);
						String pageSource1 = driver.getPageSource();
						// println pageSource
						System.out.println("page length is: "+pageSource1.length());
						System.out.println("=======================================================================\n");

			//			FileUtils.cleanDirectory(new File("PageSource"));
						File newTextFile1 = new File("PageSource\\Pagesrc2.txt");


						try {
							FileWriter fw = new FileWriter(newTextFile1);
							fw.write(pageSource1);
							fw.close();

						} catch (IOException iox) {
							//do stuff with exception
							iox.printStackTrace();
						}


						BufferedReader br1 = null;
			            BufferedReader br2 = null;
			            String sCurrentLine;
			            List<String> list1 = new ArrayList<String>();
			            List<String> list2 = new ArrayList<String>();
			            br1 = new BufferedReader(new FileReader("PageSource\\Pagesrc1.txt"));
			            br2 = new BufferedReader(new FileReader("PageSource\\Pagesrc2.txt"));

			            while ((sCurrentLine = br1.readLine()) != null) {
			                list1.add(sCurrentLine);
			            }
			            while ((sCurrentLine = br2.readLine()) != null) {
			                list2.add(sCurrentLine);
			            }

			            List<String> tmpList = new ArrayList<String>(list1);
			            tmpList.removeAll(list2);
			            System.out.println(tmpList.size());
			            System.out.println("content from Pagesrc1 which is not there in Pagesrc2");
			//            for(int i=0;i<tmpList.size();i++){
			//                System.out.println("difference is:"+tmpList.get(i)); //content from Pagesrc1 us which is not there in Pagesrc2 ca
			//            }

			    		try ( BufferedWriter bw = 
			    				new BufferedWriter (new FileWriter(new File("PageSource\\diff1.txt"))) ) 
			    		{			
			    			for (String line : tmpList) {
			    				bw.write (line + "\n");
			    			}

			    			bw.close ();

			    		} catch (IOException e) {
			    			e.printStackTrace ();
			    		}


			            tmpList = list2;
			            tmpList.removeAll(list1);
			            System.out.println(tmpList.size());
			            System.out.println("content from Pagesrc2 which is not there in Pagesrc1");
			//            for(int i=0;i<tmpList.size();i++){
			//                System.out.println(tmpList.get(i)); //content from Pagesrc2 ca which is not there in Pagesrc1 us
			//            }

			    		try ( BufferedWriter bw = 
			    				new BufferedWriter (new FileWriter(new File("PageSource\\diff2.txt"))) ) 
			    		{			
			    			for (String line : tmpList) {
			    				bw.write (line + "\n");
			    			}

			    			bw.close ();

			    		} catch (IOException e) {
			    			e.printStackTrace ();
			    		}


			            br1.close();
			            br2.close();
			 */
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		driver.close();
	}

	boolean compareImages (String exp, String cur, String diff) {
		// This instance wraps the compare command
		CompareCmd compare = new CompareCmd();

		// For metric-output
		compare.setErrorConsumer(StandardStream.STDERR);
		IMOperation cmpOp = new IMOperation();
		// Set the compare metric
		cmpOp.metric("mae");

		// Add the expected image
		cmpOp.addImage(exp);

		// Add the current image
		cmpOp.addImage(cur);

		// This stores the difference
		cmpOp.addImage(diff);

		try {
			// Do the compare
			compare.run(cmpOp);
			return true;
		}
		catch (Exception ex) {
			return false;
		}
	}
}
