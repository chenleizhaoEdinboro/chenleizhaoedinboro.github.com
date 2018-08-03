package CommonFunction;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Logger.Dailylog;
import Pages.B2CPage;
import TestData.PropsUtils;

public class Common {

	private static BigDecimal Decimal1;
	private static BigDecimal Decimal2;

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Get current date time as string yyyyMMddHHmmssSSS
	 */
	public static String getDateTimeString() {
		Date datetime = new Date();
		DateFormat milliFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return milliFormat.format(datetime);
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Wait WebElement to be clickable within timeout
	 */
	public static void waitElementClickable(WebDriver driver, WebElement element, int timeoutInSeconds)
			throws NoSuchElementException {
		new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Wait WebElement to be visible with default timeout (probably 60
	 *        seconds)
	 */
	public static void waitElementVisible(WebDriver driver, WebElement element) throws InterruptedException {
		Thread.sleep(3000);
		new WebDriverWait(driver, PropsUtils.getDefaultTimeout()).until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Return if element editable or not
	 */
	public static boolean isEditable(WebElement element) throws InterruptedException {
		return element.isEnabled() && element.getAttribute("readonly") == null;// &&
																				// !element.getAttribute("readonly").equals("readonly");
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Wait WebElement disappear with default timeout (probably 60 seconds)
	 */
	public static void waitElementDisappear(WebDriver driver, WebElement element, int timeoutInSecond)
			throws InterruptedException {
		Thread.sleep(3000);
		new WebDriverWait(driver, timeoutInSecond).until(ExpectedConditions.invisibilityOf(element));
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Check if WebElement exists or not within timeout
	 */
	public static boolean checkElementExists(WebDriver driver, WebElement element, int timeoutInSeconds) {
		try {
			driver.manage().timeouts().implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
			Common.scrollToElement(driver, element);
			// if scroll succeed, means exists, but not sure if it is displayed or not.
			return true;
		} catch (NoSuchElementException | InterruptedException e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		}
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Check if WebElement exists or not within timeout
	 */
	public static boolean checkElementDisplays(WebDriver driver, WebElement element, int timeoutInSeconds) {
		try {
			driver.manage().timeouts().implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
			Common.scrollToElement(driver, element);
			return element.isDisplayed();
		} catch (NoSuchElementException | InterruptedException e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		}
	}

	/**
	 * @throws InterruptedException
	 * @Owner Songli
	 * @Parameter
	 * @Usage Check if WebElement exists or not within timeout
	 */
	public static boolean checkElementInvisible(WebDriver driver, WebElement element, int timeoutInSeconds) {
		try {
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			int time = 0;
			while (element.isDisplayed() && 2 * time <= timeoutInSeconds) {
				Thread.sleep(2000);
				time++;
			}
			return false;
		} catch (NoSuchElementException | InterruptedException e) {
			return true;
		} finally {
			driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		}
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Check if Element exist
	 */
	public static boolean isElementExist(WebDriver driver, By by) {
		boolean flag = false;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			WebElement element = driver.findElement(by);
			flag = element != null;
		} catch (Exception e) {
		}
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		return flag;
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Check if element exist within timeout
	 */
	public static boolean isElementExist(WebDriver driver, By by, int timeoutInSecond) {
		boolean flag = false;
		driver.manage().timeouts().implicitlyWait(timeoutInSecond, TimeUnit.SECONDS);
		try {
			WebElement element = driver.findElement(by);
			flag = element != null;
		} catch (Exception e) {
		}
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		return flag;
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Check if element exist under a parent element within timeout
	 */
	public static boolean isElementExist(WebDriver driver, WebElement parentElement, By by, int timeoutInSecond) {
		boolean flag = false;
		driver.manage().timeouts().implicitlyWait(timeoutInSecond, TimeUnit.SECONDS);
		try {
			WebElement element = parentElement.findElement(by);
			flag = element != null;
		} catch (Exception e) {
		}
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		return flag;
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Subtract method for floats
	 */
	public static float floatSubtract(float num1, float num2) {
		Decimal1 = new BigDecimal(Float.toString(num1));
		Decimal2 = new BigDecimal(Float.toString(num2));
		return Decimal1.subtract(Decimal2).floatValue();
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Mouse hover on an element
	 */
	public static void mouseHover(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Double click on a element
	 */
	public static void doubleClick(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.doubleClick(element);
		actions.perform();
		// ((JavascriptExecutor) driver).executeScript("var evt =
		// document.createEvent('MouseEvents');"+
		// "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false,
		// false, false, false, 0,null);"+
		// "arguments[0].dispatchEvent(evt);", element);
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Right click on an element
	 */
	public static void rightClick(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.contextClick(element);
		actions.perform();
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Wait alert window appears within timeout
	 */
	public static void waitAlertPresent(WebDriver driver, int timeoutInSecond) {
		new WebDriverWait(driver, timeoutInSecond).until(ExpectedConditions.alertIsPresent());
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Send enter key event
	 */
	public static void KeyEventEnter() {
		try {
			Robot rebot = new Robot();
			rebot.keyPress(KeyEvent.VK_ENTER);
		} catch (AWTException e) {

		}
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Sleep in millisecond
	 */
	public static void sleep(int milliSecond) {
		try {
			Thread.sleep(milliSecond);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @Owner Zhongxu
	 * @Parameter:
	 * @Usage:
	 */
	public static boolean switchToWindow_Title(WebDriver driver, String windowTitle) {
		boolean flag = false;
		try {
			String currentHandle = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();
			for (String s : handles) {
				if (s.equals(currentHandle))
					continue;
				else {
					driver.switchTo().window(s);
					if (driver.getTitle().contains(windowTitle)) {
						flag = true;
						Dailylog.logInfo("Switch to Window: " + windowTitle + "  successfully~~~!");
						break;
					} else
						continue;
				}
			}
		} catch (NoSuchWindowException e) {
			Dailylog.logInfo("Window: " + windowTitle + " cound not find!!!" + e.fillInStackTrace());
			flag = false;
		}
		return flag;
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Scroll to make sure target element is on screen and clickable
	 */
	public static void scrollToElement(WebDriver driver, WebElement element) throws InterruptedException {
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
		js.executeScript("window.scrollBy(0, -100)");
		Thread.sleep(2000);
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Used in data-driven
	 */
	public static Object[][] getFactoryData(Object[][] fullList, String target) {
		if (target.toUpperCase().equals("ALL")) {
			return fullList;
		} else {
			List<String> targetList = Arrays.asList(target.split(","));
			List<Object[]> result = new ArrayList<Object[]>();
			for (int i = 0; i < fullList.length; i++) {
				if (targetList.contains(fullList[i][0].toString())) {
					result.add(fullList[i]);
				}
			}
			return result.toArray(new Object[0][]);
		}
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Used in data-driven for 19353 and 19422 specifically
	 */
	public static Object[][] getFactoryDataPayment(Object[][] fullList, ArrayList<String[]> target) {
		if (target.get(0)[0].toUpperCase().equals("ALL")) {
			if (System.getProperty("targetStore").toUpperCase().equals("ALL")) // false - ALL
				return fullList;
			else {
				// false - CA CA,JP
				String[] targetList = System.getProperty("targetStore").split(",");
				List<Object[]> result = new ArrayList<Object[]>();
				for (String tar : targetList) {
					for (int i = 0; i < fullList.length; i++) {
						if (fullList[i][0].equals(tar)) {
							result.add(fullList[i]);
						}
					}
				}
				return result.toArray(new Object[0][]);
			}
		} else {
			// true - ALL & CA CA,JP
			List<Object[]> result = new ArrayList<Object[]>();
			for (int i = 0; i < fullList.length; i++) {
				if (isTargetListContains(target, fullList[i])) {
					result.add(fullList[i]);
				}
			}
			return result.toArray(new Object[0][]);
		}
	}

	private static boolean isTargetListContains(ArrayList<String[]> target, Object[] item) {
		for (String[] tar : target) {
			if (tar[0].equals(item[0]) && tar[1].equals(item[1].toString()))
				return true;
		}
		return false;
	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static String[] getSeriesURL(B2CPage page, WebDriver driver, String type) {
		String[] urlArray = null;
		By ViewSeriesButton = By.xpath(".//*[@id='product-']/div/div[6]/a");

		if (type.equals("Laptops") || type.equals("Compare and Buy Laptops") || type.equals("Laptops & Ultrabooks")) {
			List<WebElement> urlItems = driver
					.findElements(By.cssSelector(".seriesPreview-viewLink.seriesPreview-fakeLink"));

			urlArray = new String[urlItems.size()];
			int i = 0;
			for (WebElement e : urlItems) {
				urlArray[i] = e.getAttribute("href").toString();
				i++;
			}
		} else if (type.equals("Accessories and upgrades") || type.equals("Accessories & Software")) {

			List<WebElement> urlItems = driver
					.findElements(By.xpath(".//*[@id='productGrid-target']//div[1]/div[2]/h2/a"));
			urlArray = new String[urlItems.size()];
			int i = 0;
			for (WebElement e : urlItems) {
				urlArray[i] = e.getAttribute("href").toString();
				i++;
			}

		} else if (type.equals("Tablets") || type.equals("Desktops & All-in-ones")
				|| type.equals("Windows & Android Tablet PCs") || type.equals("Tablets & Detachable PCs")) {
			if (isElementExist(driver, ViewSeriesButton)) {
				List<WebElement> urlItems = driver.findElements(By.xpath(".//*[@id='product-']/div/div[6]/a"));
				urlArray = new String[urlItems.size()];
				int i = 0;
				for (WebElement e : urlItems) {
					urlArray[i] = e.getAttribute("href").toString();
					i++;
				}
			} else {
				List<WebElement> urlItems = driver
						.findElements(By.cssSelector(".seriesPreview-viewLink.seriesPreview-fakeLink"));

				urlArray = new String[urlItems.size()];
				int i = 0;
				for (WebElement e : urlItems) {
					urlArray[i] = e.getAttribute("href").toString();
					i++;
				}
			}

		}

		return urlArray;
	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static void NavigateToUrl(WebDriver driver, String Browser, String url) {
		if (Browser.toLowerCase().startsWith("ie")) {
			if (url.contains("LIeCommerce")) {
				url = url.replace("LIeCommerce:M0C0v0n3L!@", "");
			}
		}
		// Common.IElogin(url, driver);
		// } else {
		driver.get(url);
		if (Common.isAlertPresent(driver)) {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
//
//	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static void IElogin(String URL, WebDriver driver) {
		// driver.get(URL);
		//
		// if (Common.isAlertPresent(driver)) {
		// Alert alert = driver.switchTo().alert();
		// alert.accept();
		// }

		// Alert aa = driver.switchTo().alert();
		// aa.sendKeys(Keys.ENTER);
		// try {
		// Thread.sleep(1000);
		// driver.switchTo().alert().accept();
		// }catch (Exception e)
		// {}
		// aa.sendKeys("ENTER");
		// final Runtime runtime = Runtime.getRuntime();
		//
		// try {
		// runtime.exec("src/test/resources/NemoLogin.exe");
		//
		// } catch (final Exception e) {
		// System.out.println("Error exec!");
		// }
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e1) {
		// e1.printStackTrace();
		// }

	}

	public static void sendFieldValue(WebElement locator, String value) {
		locator.clear();
		locator.sendKeys(value);
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Click on an element with Javascript
	 */
	public static void javascriptClick(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", element);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void switchToWindow(WebDriver driver, int windowNo) {
		Common.sleep(2000);
		ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(windows.get(windowNo));
	}

	/**
	 * @Owner Pan
	 * @Parameter
	 * @Usage get the text when the element is not visible
	 */

	public static String javaScriptGetText(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String text = (String) js.executeScript("var result = arguments[0].innerHTML;return result", element);

		return text;
	}

	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static void deleteFile(String path) {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}
	}

	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static String[] GetAllFilePath(String folderpath) {
		File file = new File(folderpath);
		String FindName[];
		FindName = file.list();
		String[] path = new String[FindName.length];

		for (int i = 0; i < FindName.length; i++) {
			path[i] = folderpath + "\\" + FindName[i];

		}
		return path;
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Wait for element text change to target text
	 */
	public static void waitElementText(WebDriver driver, By by, String targetText) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(by, targetText));
	}

	/**
	 * @Owner Pan
	 * @Parameter:
	 * @Usage:
	 */
	public static Boolean isAttributePresent(WebElement element, String attribute) {
		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Conver Element locator to xpath (only works for elements located with
	 *        xpath)
	 */
	public static String convertWebElementToString(WebElement locatorAsWebElement) {
		String temp1[] = locatorAsWebElement.toString().split("xpath: ");
		int temp2 = temp1[1].lastIndexOf("]");
		String locatorAsString = temp1[1].substring(0, temp2);
		// String after = temp1[1].substring(temp2 + 1);
		return locatorAsString;
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Check element displays within timeout
	 */
	public static boolean checkElementDisplays(WebDriver driver, By by, int timeoutInSeconds) {
		try {
			driver.manage().timeouts().implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
			Common.scrollToElement(driver, driver.findElement(by));
			return driver.findElement(by).isDisplayed();
		} catch (NoSuchElementException | InterruptedException e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		}
	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (Exception Ex) {
			return false;
		}
	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage:
	 */
	public static void WaitUntilSpinner(WebDriver driver) {
		String spinner = "//*[@id='full_preloader']/i[2]";
		if (Common.isElementExist(driver, By.xpath(spinner))) {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(spinner))));
		} else {
			Common.sleep(3000);
		}
	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage: scroll to the right within pages
	 */
	public static void InnerScrollRight(WebDriver driver, String className, String scrollDistance) {
		JavascriptExecutor j = (JavascriptExecutor) driver;

		j.executeScript("document.getElementsByClassName('" + className + "')[0].scrollLeft=" + scrollDistance);
	}

	/**
	 * @Owner Xianen
	 * @Parameter:
	 * @Usage: scroll to the bottom within pages
	 */
	public static void InnerScrollTop(WebDriver driver, String className, String scrollDistance) {
		JavascriptExecutor j = (JavascriptExecutor) driver;

		j.executeScript("document.getElementsByClassName('" + className + "')[0].scrollTop=" + scrollDistance);
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Open a new browser and return the driver, but remember to quit it at
	 *        the end of case and catch clause
	 */
	public static WebDriver openNewBrowser() {
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(PropsUtils.getDefaultTimeout(), TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;
	}

	/**
	 * @Owner Songli
	 * @Parameter
	 * @Usage Scroll to element to make sure it is in screen, and then use selenium normal click
	 */
	public static void scrollAndClick(WebDriver driver, WebElement element)
	{
		try {
			Common.scrollToElement(driver, element);
			element.click();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @Owner gaopan
	 * @Parameter
	 * @Usage return float value with two digits
	 */
	public static float getFloat(float f){
		
		 DecimalFormat df=new DecimalFormat(".##");
		 String st=df.format(f);
		 float ff = Float.parseFloat(st);
		
		 return ff;		 
	 }
	
	
	
}
