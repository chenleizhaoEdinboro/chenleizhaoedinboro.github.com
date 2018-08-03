package CommonFunction;

import Pages.CTOPage;
import TestData.TestData;

public class CTOCommon {
	
	/**
	 * @Owner Qianqian
	 * @Parameter:
	 * @Usage:
	 */
	public static void Login(CTOPage ctoPage, TestData data) {
		ctoPage.Login_IDTextBox.clear();
		Common.sleep(5000);
		ctoPage.Login_IDTextBox.sendKeys(data.CTO.getLoginId());
		ctoPage.Login_PasswordTextBox.clear();
		ctoPage.Login_PasswordTextBox.sendKeys(data.CTO.getPassword());
		ctoPage.Login_LoginButton.click();
	}
	
}
