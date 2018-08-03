package TestScript.B2C;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import CommonFunction.B2CCommon;
import CommonFunction.Common;
import CommonFunction.EMailCommon;
import CommonFunction.HMCCommon;
import Logger.Dailylog;
import Pages.B2CPage;
import Pages.HMCPage;
import TestScript.SuperTestClass;


public class ACCT32Test extends SuperTestClass {
	
	public HMCPage hmcPage;
	public B2CPage b2cPage;
	public String alcURL="https://account.lenovo.com/au/en";
	
	public ACCT32Test(String store) {
		this.Store = store;
		this.testName = "ACCT-32";
	}

	@Test(priority = 0, enabled = true, alwaysRun = true)
	public void ACCT32(ITestContext ctx) {
		try {
			
			this.prepareTest();
			b2cPage = new B2CPage(driver);
			hmcPage = new HMCPage(driver);
			
			//1, Go to HMC and Set eService SSO Toggle
			Dailylog.logInfoDB(1, "Go to HMC and Set eService SSO Toggle", Store, testName);
			
			driver.get(testData.HMC.getHomePageUrl());
			HMCCommon.Login(hmcPage, testData);
			HMCCommon.searchB2CUnit(hmcPage, testData);
			hmcPage.B2CUnit_FirstSearchResultItem.click();
			hmcPage.B2CUnit_SiteAttributeTab.click();
			
			// set eService SSO Toggle : yes 
			hmcPage.eServiceToggle.click();
			hmcPage.Common_SaveButton.click();
			hmcPage.Home_closeSession.click();
			Thread.sleep(10000);
			
			
			//2, test My Account-> Sign in
			//New account    registration:           
			//1) Click my account button-->sign in
			Dailylog.logInfoDB(2, " test My Account-> Sign in ", Store, testName);
			
			String tempEmailAddress = EMailCommon.getRandomEmailAddress();

			Common.NavigateToUrl(driver, Browser, testData.B2C.getHomePageUrl());
			
			B2CPage b2cPage = new B2CPage(driver);

			B2CCommon.handleGateKeeper(b2cPage, testData);

			Common.javascriptClick(driver, b2cPage.Navigation_SignInLink);

			B2CCommon.login(b2cPage, "invalidid@mailinator.com", "1q2w3e4r");
			Thread.sleep(5000);

			if (b2cPage.Login_ErrorMsg.getText()
					.equals(getErrMsgPerStore(testData.Store, EnumErrMsg.incorrectPassErr))) {
			} else if (b2cPage.Login_ErrorMsg.getText().equals(
					"Sorry! It's not you... it's us. Please call the phone number at the top of the page and we'll fix this right away.")
					|| b2cPage.Login_ErrorMsg.getText().equals("Oops! You need a special account to login to this store. If you need help, please call the phone number at the top of the page.")) {
			} else {
				Assert.fail("Incorrect messgae is wrong: " + b2cPage.Login_ErrorMsg.getText());
			}
			
			//3, click create an account button 
			Dailylog.logInfoDB(3, "click create an account button", Store, testName);

			if (Common.checkElementExists(driver, b2cPage.Login_CreateAnAccountButton, 5)){
				b2cPage.Login_CreateAnAccountButton.click();
			}
			else
				b2cPage.RegisterGateKeeper_CreateAnAccountButton.click();
			Common.waitElementVisible(driver, b2cPage.RegistrateAccount_CreateAccountButton);

			// Validate all default errors
			
			//4, Nothing input 
			Dailylog.logInfoDB(4, "Nothing input", Store, testName);
			
			b2cPage.RegistrateAccount_CreateAccountButton.click();
			Assert.assertEquals(b2cPage.RegistrateAccount_EmailError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.emailErr));
			Assert.assertEquals(b2cPage.RegistrateAccount_ConfirmEmailError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.emailErr));
			Assert.assertEquals(b2cPage.RegistrateAccount_FirstNameError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.fNameErr));
			Assert.assertEquals(b2cPage.RegistrateAccount_LastNameError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.lNameErr));
			Assert.assertEquals(b2cPage.RegistrateAccount_PasswordError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.passwordErr));
			Assert.assertEquals(b2cPage.RegistrateAccount_ConfirmPasswordError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.confirmPassErr));
			if(Common.checkElementExists(driver, b2cPage.RegistrateAccount_AcceptTermsError, 1))
			Assert.assertEquals(b2cPage.RegistrateAccount_AcceptTermsError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.accecptErr));

			// Validate Email format error
			//5, input wrong Email format for email related field
			Dailylog.logInfoDB(5, "input wrong Email format for email related field", Store, testName);
			
			b2cPage.RegistrateAccount_EmailIdTextBox.sendKeys("InvalidEmailId");
			b2cPage.RegistrateAccount_ConfirmEmailTextBox.sendKeys("InvalidEmailId");
			b2cPage.RegistrateAccount_FirstNameTextBox.sendKeys("AutoFirstName");
			b2cPage.RegistrateAccount_LastNameTextBox.sendKeys("AutoLastName");
			b2cPage.RegistrateAccount_PasswordTextBox.sendKeys("1q2w3e4r");
			b2cPage.RegistrateAccount_ConfirmPasswordTextBox.sendKeys("1q2w3e4r");
			b2cPage.RegistrateAccount_OptionCheckBox.click();
			if(Common.checkElementExists(driver, b2cPage.RegistrateAccount_AcceptTermsError, 1))
			b2cPage.RegistrateAccount_AcceptTermsCheckBox.click();
			b2cPage.RegistrateAccount_CreateAccountButton.click();
			Thread.sleep(5000);
			Assert.assertEquals(b2cPage.RegistrateAccount_EmailError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.emailErr));

			// Validate Email Confirmation error
			//6, Re-enter Email address not match original Email address
			Dailylog.logInfoDB(6, "Re-enter Email address not match original Email address", Store, testName);
			
			inputValuesAndClickCreate(b2cPage, "autotest@lenovo.com", "autotest2@lenovo.com", "1q2w3e4r", "1q2w3e4r");
			Assert.assertEquals(b2cPage.RegistrateAccount_ConfirmEmailError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.emailNotMatchErr));

			// Input Password less than 8 characters
			//7, Input password less than 8 character
			Dailylog.logInfoDB(7, "Input password less than 8 character", Store, testName);
			
			inputValuesAndClickCreate(b2cPage, "autotest@lenovo.com", "autotest@lenovo.com", "1234567", "1234567");
			Assert.assertEquals(b2cPage.RegistrateAccount_PasswordError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.passwordErr));

			// Input different password in the confirm password field
			//8, Re-enter password not match original password 
			Dailylog.logInfoDB(8, "Re-enter password not match original password", Store, testName);
			
			inputValuesAndClickCreate(b2cPage, "autotest@lenovo.com", "autotest@lenovo.com", "1q2w3e4r", "1q2weeee");
			Assert.assertEquals(b2cPage.RegistrateAccount_ConfirmPasswordError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.passwordNotMatchErr));

			// Input already registered email ID
			//9,  User input email address already existed and fill in all related information.
			Dailylog.logInfoDB(9, "User input email address already existed and fill in all related information.", Store, testName);
			
			inputValuesAndClickCreate(b2cPage, "deshansh.sahu@accenture.com", "deshansh.sahu@accenture.com", "1q2w3e4r",
					"1q2w3e4r");
			Assert.assertEquals(b2cPage.RegistrateAccount_AlreadyRegisEmailError.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.emailAlreadyExistsErr));

			// Input all of the valid details then click create account
			//10, User go to registration page, input valid email, first name, Lastname, password fit the rule,click I accept, create my account button
			Dailylog.logInfoDB(10, "User go to registration page, input valid email, first name, Lastname, password fit the rule,click I accept, create my account button", Store, testName);
			
			inputValuesAndClickCreate(b2cPage, tempEmailAddress, tempEmailAddress, "1q2w3e4r", "1q2w3e4r");
			Assert.assertEquals(b2cPage.RegistrateAccount_ThankYouMessage.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.accountCreatedThankYouMsg));

			// "click sign into my account button" and go to log in page, input new user ID
			// and password without activate account
			//11 "click sign into my account button" and  go to log in page, input new user ID and password without activate account
			Dailylog.logInfoDB(11, "click sign into my account button and  go to log in page, input new user ID and password without activate account", Store, testName);
			
			b2cPage.RegistrateAccount_SigninToMyAccountButton.click();
			b2cPage.Login_LenovoIDTextBox.clear();
			b2cPage.Login_LenovoIDTextBox.sendKeys(tempEmailAddress);
			b2cPage.Login_LenovoPasswordTextBox.clear();
			b2cPage.Login_LenovoPasswordTextBox.sendKeys("1q2w3e4r");
			b2cPage.Login_LogInButton.click();
			Thread.sleep(3000);
			Assert.assertEquals(b2cPage.Login_ErrorMsg.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.accountNotVerifiedErr));

			// Active account
			//12, Activate account by click the link in Email
			Dailylog.logInfoDB(12, "Activate account by click the link in Email", Store, testName);
			
			System.out.println("tempEmailAddress is :" + tempEmailAddress);
			EMailCommon.activeNewAccount(driver,tempEmailAddress, 1, testData.Store);

			// Log in with new account
			//13, Login website by using activate account and password
			Dailylog.logInfoDB(13, "Login website by using activate account and password", Store, testName);
			
			Common.NavigateToUrl(driver, Browser, testData.B2C.getloginPageUrl());
			
			B2CCommon.handleGateKeeper(b2cPage, testData);
			
			b2cPage.Login_LenovoIDTextBox.clear();
			b2cPage.Login_LenovoIDTextBox.sendKeys(tempEmailAddress);
			b2cPage.Login_LenovoPasswordTextBox.clear();
			b2cPage.Login_LenovoPasswordTextBox.sendKeys("1q2w3e4r");
			b2cPage.Login_LogInButton.click();
			
			Thread.sleep(4000);
			if(Common.checkElementDisplays(driver, b2cPage.Login_LogInButton, 5))
				b2cPage.Login_LogInButton.click();
	
			(new WebDriverWait(driver, 500)).until(ExpectedConditions.urlContains("my-account"));

			System.out.println("url is :" + driver.getCurrentUrl());
			
			if (!driver.getCurrentUrl().contains("my-account"))
				Assert.fail("User is not redirected to my account page after loging in!");
			
			//14,*test single sign on accross eService and eCommerce*
			//open account.lenovo.com in the same browser
			Dailylog.logInfoDB(14, "*test single sign on accross eService and eCommerce* open account.lenovo.com in the same browser", Store, testName);
			
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("window.open('"+ alcURL + "')");
			
			Common.switchToWindow(driver, 1);
			
			driver.get(alcURL + "/myorders");
			Assert.assertTrue(!driver.getCurrentUrl().contains("signin") && Common.isElementExist(driver, By.xpath("//div[@id='header-desktop']//li[@class='account-logout']/a")), "after refresh , alc is not logined");
			

			

	/*		// Log out
//			b2cPage.Navigation_MyAccountIcon.click();
//			if (Common.checkElementExists(driver, b2cPage.Navigation_SignOutLink, 5))
//				b2cPage.Navigation_SignOutLink.click();
			Common.javascriptClick(driver, b2cPage.Navigation_SignOutLink);

			// Log in with invalid password
			driver.manage().deleteAllCookies();
			B2CCommon.handleGateKeeper(b2cPage, testData);
			Common.javascriptClick(driver, b2cPage.Navigation_SignInLink);
			B2CCommon.login(b2cPage, tempEmailAddress, "1q222222");
			Thread.sleep(5000);
			Assert.assertEquals(b2cPage.Login_ErrorMsg.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.incorrectPassErr));

			// Input wrong password for 5 times
			for (int index = 0; index < 5; index++) {
				B2CCommon.login(b2cPage, tempEmailAddress, "1q222222");
				Thread.sleep(3000);
			}
			Assert.assertEquals(b2cPage.Login_ErrorMsg.getText(),
					getErrMsgPerStore(testData.Store, EnumErrMsg.accountLockedMsg));*/

			
		} catch (Throwable e) {
			handleThrowable(e, ctx);
		}
	}
	
	public static void inputValuesAndClickCreate(B2CPage b2cPage, String email, String conEmail, String password,
			String conPassword) throws InterruptedException {
		b2cPage.RegistrateAccount_EmailIdTextBox.clear();
		b2cPage.RegistrateAccount_EmailIdTextBox.sendKeys(email);
		b2cPage.RegistrateAccount_ConfirmEmailTextBox.clear();
		b2cPage.RegistrateAccount_ConfirmEmailTextBox.sendKeys(conEmail);
		b2cPage.RegistrateAccount_FirstNameTextBox.clear();
		b2cPage.RegistrateAccount_FirstNameTextBox.sendKeys("AutoFirstName");
		b2cPage.RegistrateAccount_LastNameTextBox.clear();
		b2cPage.RegistrateAccount_LastNameTextBox.sendKeys("AutoLastName");
		b2cPage.RegistrateAccount_PasswordTextBox.clear();
		b2cPage.RegistrateAccount_PasswordTextBox.sendKeys(password);
		b2cPage.RegistrateAccount_ConfirmPasswordTextBox.clear();
		b2cPage.RegistrateAccount_ConfirmPasswordTextBox.sendKeys(conPassword);
		b2cPage.RegistrateAccount_CreateAccountButton.click();
		Thread.sleep(5000);
	}

	private String getErrMsgPerStore(String store, EnumErrMsg msgName) {
		if (store.equals("JP")) {
			switch (msgName) {
			case emailErr:
				return "正しいEメールアドレスを入力ください。";
			case fNameErr:
				return "正しい氏名（名）を入力してください。";
			case lNameErr:
				return "正しい氏名（姓）を入力してください。";
			case passwordErr:
				return "パスワードを入力してください（半角英数記号8桁～20桁で、英字、数字、記号の2種類以上を組み合わせてください）";
			case confirmPassErr:
				return "パスワードをご確認ください。";
			case accecptErr:
				return "販売規約とプライバシー・ポリシーに同意をお願いします。";
			case emailNotMatchErr:
				return "入力されたEメールアドレスが一致しません。";
			case passwordNotMatchErr:
				return "パスワードとパスワードの確認が一致しません";
			case emailAlreadyExistsErr:
				// return "申し訳ございません。この E メールアドレスを持つアカウントはすでに存在しています。このアカウントを使用してログインするか、別の E
				// メールアドレスを使用して新しいアカウントを作成してください。サポートが必要な場合は、ページ上部に記載された電話番号にご連絡ください。";
//				return "ご入力いただいたメールアドレスは、すでにLenovo ID アカウントとして登録されております。そのアカウントを使用してログインを行っていただくか、別の メールアドレスを使用して新しいアカウントを作成してください。サービス＆サポートなど、レノボが提供しているウェブサイト、もしくは、ツールでご利用いただいているアカウントがLenovo IDである場合があります.";
				return "ご入力いただいたメールアドレスは、すでにLenovo　IDとしてご登録されております。 このメールアドレスをご利用の上ログインし登録情報を追加入力いただくか、別メールアドレスにて 新しいアカウントを作成してください。 サービス&サポートなど、レノボが提供しているwebサイト、もしくはツールでご利用いただいている アカウントがLenovo　IDである場合があります。";
			case accountCreatedThankYouMsg:
				return "アカウント作成、ありがとうございました。";
			case accountNotVerifiedErr:
				// return "申し訳ございません。お客様のアカウントはまだ確認されていません。登録時には、アカウント確認用の E
				// メールが送信されます。このステップが実行されていないようですが、簡単に修正できます。Lenovo ID からの E
				// メールをチェックしてアカウントのアクティベーションを実行してください。[パスワードを忘れた場合] リンクを使用すると、E
				// メールを再度受信することができます。";
				return "お客様のLenovo IDアカウントの有効化がまだ完了していないようです。登録時にアカウント確認用メール（Activationメール）が送付されております。いま一度、そのメールをご確認いただき、アカウントの有効化を行う手順の実行をお願いいたします。アカウント確認用メールが見当たらない場合は、下記　[パスワードを忘れた場合] よりアカウント確認用メールを再送信いただけます.";
			case incorrectPassErr:
				// return "入力されたレノボIDとパスワードの組み合わせが見つかりません。再試行するか、以下のリンクを使用して、新しいアカウントを作成して下さい。";
				return "ご入力されたLenovo ID アカウントとパスワードの組み合わせが誤っているか、もしくはアカウントが存在しておりません。改めてご確認の上、ご入力をお願いします.";
			case accountLockedMsg:
				// return "連続してログインに失敗したため、セキュリティ保護の目的により、お客様のアカウントは一時的にロックされています。15
				// 分お待ちいただいてから、再度ログインしてください。問題が解決しない場合は、ページ上部に記載された電話番号にご連絡ください。";
//				return "連続してログインに失敗したため、セキュリティ保護の目的により、お客様のアカウントは一時的にロックされています。しばらくお時間を置いた後に、、再度ログインをお試しください.";
				return "連続してログインに失敗したため、お客様のセキュリティ保護のため、アカウントを一時的にロックしています。15分ほど経ってからログインをお試しください。なお、この間にパスワードリセットを行った場合も、15分ほどの間はログインできません。ご了承ください。それでも問題が解決しない場合は、ページ上部の電話番号にお問い合わせください。";
			default:
				return null;
			}
		} else if (store.equals("HKZF")) {
			switch (msgName) {
			case emailErr:
				return "請輸入有效的email";
			case fNameErr:
				return "請輸入名字";
			case lNameErr:
				return "請輸入姓氏";
			case passwordErr:
				return "請輸入強式密碼。(密碼必須包含 8 至 20 個字元，且包含下列兩種類型的字元: 字母、數字或符號。)";
			case confirmPassErr:
				return "請確認您的密碼";
			case accecptErr:
				return "創建帳號前您必須同意使用條款與隱私權條款";
			case emailNotMatchErr:
				return "重新輸入的電郵不符";
			case passwordNotMatchErr:
				return "密碼和密碼確認不相符";
			case emailAlreadyExistsErr:
				return "糟糕！使用電郵地址的帳戶已經存在。請登入此帳戶，或使用其他電郵地址建立新帳戶。如果需要幫助，請撥打網頁上方的電話號碼。";
			case accountCreatedThankYouMsg:
				return "感謝您建立帳戶";
			case accountNotVerifiedErr:
				return "糟糕！您的帳戶尚未驗證。註冊時，我們會寄送一封電郵給您以驗證您的帳戶。似乎您沒有完成此步驟，但修正很簡單。請檢查 Lenovo ID 的電郵以啟用您的帳戶，或使用「忘記密碼」連結，然後我們就會寄送新的電郵。";
			case incorrectPassErr:
				return "密碼不正確，請再試一次。如果忘記密碼，請使用下方連結重設密碼。";
			case accountLockedMsg:
				return "由於登入失敗次數過多，為保護您的安全，我們已暫時封鎖您的帳戶。請等候 15 分鐘，然後再試一次。如果問題仍然無法解決，請撥打網頁上方的電話號碼。";
			default:
				return null;
			}
		} else if (store.equals("CO")) {
			switch (msgName) {
			case emailErr:
				return "Por favor, ingresa una dirección de correo electrónico válida";
			case fNameErr:
				return "Por favor, ingresa tu nombre";
			case lNameErr:
				return "Por favor, ingresa tu apellido";
			case passwordErr:
				return "Por favor, ingresa una contraseña segura. (La contraseña debe contener de 8 a 20 caracteres e incluir dos de los siguientes tipos: letras, números o símbolos).";
			case confirmPassErr:
				return "Por favor, confirma tu contraseña";
			case accecptErr:
				return "Debes aceptar las condiciones de uso y las políticas de privacidad antes de crear una cuenta.";
			case emailNotMatchErr:
				return "El correo electrónico reingresado no concuerda con el correo electrónico";
			case passwordNotMatchErr:
				return "La información introducida en los campos de contraseña y confirmación de contraseña no coinciden.";
			case emailAlreadyExistsErr:
				return "¡Ups! Ya existe una cuenta con esta dirección de correo electrónico. Por favor, accede con esta cuenta o utiliza una dirección de correo electrónico diferente para crear una cuenta nueva. Si necesitas ayuda, por favor llama a número de teléfono en la parte superior de la página.";
			case accountCreatedThankYouMsg:
				return "Gracias por crear una cuenta";
			case accountNotVerifiedErr:
				return "Disculpa, tu cuenta no ha sido verificada todavía. Al registrarte, te hemos enviado un mail con un link para verificar la cuenta. Revisa tu casilla de mail o usa el link de \"¿Has olvidado tu contraseña?\" y te enviaremos un nuevo mail.";
			case incorrectPassErr:
				return "Contraseña incorrecta, inténtalo de nuevo. Si olvidaste la contraseña, usa el siguiente enlace para restablecer la contraseña.";
			case accountLockedMsg:
				return "Para tu seguridad hemos bloqueado temporalmente tu cuenta debido a varios intentos de acceso fallidos. Por favor, aguarda 15 minutos antes de volver a intentar. Si todavía tienes problemas, por favor llama a número de teléfono en la parte superior de la página.";
			default:
				return null;
			}
		} else {
			switch (msgName) {
			case emailErr:
				return "Please enter a valid email";
			case fNameErr:
				return "Please enter a first name";
			case lNameErr:
				return "Please enter a last name";
			case passwordErr:
				return "Please enter a strong password. (Passwords must contain 8-20 characters and include two of the following character types: letters, numbers, or symbols.)";
			case confirmPassErr:
				return "Please confirm your password";
			case accecptErr:
				return "You must accept Terms of Use and Privacy Policy before creating an account.";
			case emailNotMatchErr:
				return "Re-entered email does not match email";
			case passwordNotMatchErr:
				return "Password and password confirmation do not match";
			case emailAlreadyExistsErr:
				return "Oops! An account with this email address already exists. Please login with this account or use a different email address to create a new account. If you need help, please call the phone number at the top of the page.";
			case accountCreatedThankYouMsg:
				return "Thank You For Creating An Account";
			case accountNotVerifiedErr:
				return "Oops! Your account hasn't been verified yet. When you signed up, we sent you an email to verify your account. It looks like you missed this step but it's easy to fix. Please check for an email from Lenovo ID to activate your account or use the Forgot Your Password link and we'll send a new email.";
			case incorrectPassErr:
				return "Incorrect password, please try again. If you forgot your password, please use the link below to reset your password.";
			case accountLockedMsg:
				return "For your security, we've temporarily locked your account as several login attempts failed. Please wait 15 minutes before you try again. If you're still having problems please call the phone number at the top of the page.";
			default:
				return null;
			}
		}
	}

	private enum EnumErrMsg {
		emailErr, fNameErr, lNameErr, passwordErr, confirmPassErr, accecptErr, emailNotMatchErr, passwordNotMatchErr, emailAlreadyExistsErr, accountCreatedThankYouMsg, accountNotVerifiedErr, incorrectPassErr, accountLockedMsg;
	}
}

