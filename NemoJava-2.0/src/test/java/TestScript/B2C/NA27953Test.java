package TestScript.B2C;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.Common;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

public class NA27953Test extends SuperTestClass {
	public B2CPage b2cPage;
	public HMCPage hmcPage;
	public String partNo;
	public String filePath;

	public NA27953Test(String store,String partNo,String filePath) {
		this.Store = store;
		this.testName = "NA-27953";
		this.partNo = partNo;
		this.filePath = filePath;
	}

	@Test(priority = 0,alwaysRun = true, groups = {"browsegroup","product",  "p2", "b2c"})
	public void NA27953(ITestContext ctx) {
		try {
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			String screenSizeOne = "13.3\" QHD+ (3200x1800), IPS, 10-point touch screen";
			String screenSizeTwo = "15 \" QHD+ (3200x1800), IPS, 10-point touch screen";
			File file = new File(filePath);
			String absolutePath = file.getAbsolutePath();  
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchStagedProduct(driver, hmcPage, partNo);
			hmcPage.Catalog_Administration.click();
			hmcPage.Products_Sceensize.clear();
			hmcPage.SaveButton.click();
			changeUploadFile(absolutePath, partNo, screenSizeOne);
			uploadProduct(absolutePath);
			/*driver.manage().deleteAllCookies();
			driver.get(testData.HMC.getHomePageUrl());
			driver.navigate().refresh();
			HMCCommon.Login(hmcPage, testData);	*/	
			checkProductScreenSize(partNo,"13.3");
			changeUploadFile(absolutePath, partNo, screenSizeTwo);
			uploadProduct(absolutePath);
			/*driver.manage().deleteAllCookies();
			Common.sleep(10000);
			driver.get(testData.HMC.getHomePageUrl());
			driver.navigate().refresh();
			HMCCommon.Login(hmcPage, testData);	*/
			checkProductScreenSize(partNo,"15");
			
				} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public void changeUploadFile(String absolutePath,String partNo,String screenSize) {
		try { 
            //创建Excel路径
            jxl.Workbook wb =null;                                 //创建一个workbook对象             
            System.out.println(absolutePath);
            InputStream is = new FileInputStream(absolutePath);      //创建一个文件流，读入Excel文件
            wb = Workbook.getWorkbook(is);                        //将文件流写入到workbook对象
                            
            //jxl.Workbook 对象是只读的，所以如果要修改Excel，需要创建一个可读的副本，副本指向原Excel文件
            jxl.write.WritableWorkbook wbe= Workbook.createWorkbook(new File(absolutePath), wb);//创建workbook的副本
            int sheet_size=wbe.getNumberOfSheets();
            System.out.println(sheet_size);
            WritableSheet sheet  = wbe.getSheet(0);
            String cellinfo = sheet.getCell(2, 1).getContents();
            System.out.println(sheet_size + cellinfo);
            WritableCell cell =sheet.getWritableCell(2, 1); //获取第一行的所有单元格
            jxl.format.CellFormat cf = cell.getCellFormat();//获取第一个单元格的格式
            jxl.write.Label lbl = new jxl.write.Label(2, 1, partNo);//修改後的值
            lbl.setCellFormat(cf);                          //将修改后的单元格的格式设定成跟原来一样
            sheet.addCell(lbl); 
            cellinfo = sheet.getCell(23, 1).getContents();
            System.out.println(cellinfo);
            cell =sheet.getWritableCell(23, 1); //获取第一行的所有单元格
            cf = cell.getCellFormat();//获取第一个单元格的格式
            lbl = new jxl.write.Label(23, 1, screenSize);//修改後的值
            lbl.setCellFormat(cf);                          //将修改后的单元格的格式设定成跟原来一样
            sheet.addCell(lbl);         
            wbe.write();                                            //将修改保存到workbook
            wbe.close();                                            //关闭workbook，释放内存 
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (WriteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (BiffException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public void uploadProduct(String filePath) {
		if(!Common.checkElementDisplays(driver, hmcPage.Nemo_productBuilder, 3)) {
			hmcPage.Home_Nemo.click();
		}
		hmcPage.Products_Upload.click();
		hmcPage.Products_Upload_OpenUpload.click();
		String windowHandle = driver.getWindowHandle();
		changeWindow(true,windowHandle);
		hmcPage.Products_Upload_UploadPath.clear();
		hmcPage.Products_Upload_UploadPath.sendKeys(filePath);
		hmcPage.Products_Upload_UploadButton.click();
		driver.switchTo().window(windowHandle);
		hmcPage.Products_Upload_UploadConfirmButton.click();
		windowHandle = driver.getWindowHandle();
		changeWindow(true,windowHandle);
		changeWindow(false,windowHandle);
	}
	
	public void checkProductScreenSize(String partNum,String screenSize){
		HMCCommon.searchStagedProduct(driver,hmcPage,partNum);
		hmcPage.Catalog_Administration.click();
		System.out.println("screen size is : "+hmcPage.Products_Sceensize.getAttribute("value"));
		Assert.assertTrue(screenSize.equals(hmcPage.Products_Sceensize.getAttribute("value")));
		
	}
	
	public void changeWindow(boolean flag,String currentHandle){
		Common.sleep(3000);
		Set<String> windowHandles = driver.getWindowHandles();
		if(flag){
			for(String hanedle:windowHandles){
				if(!hanedle.equals(currentHandle)){
					driver.switchTo().window(hanedle);
				}
			}
		}else{
			driver.close();
			driver.switchTo().window(currentHandle);
			
		}
	}
	
	
	

}
