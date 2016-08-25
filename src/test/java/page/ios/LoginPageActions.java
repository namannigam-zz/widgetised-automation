package page.ios;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.Exception;


public class LoginPageActions extends AppiumBasePage {
    HomePageActions homePageActions;
    SignupPageActions signupPageActions;
    ProfilePageActions profilePageActions;

    public LoginPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Method to set the other page objects
     */
    public void setOtherPageObjects(HomePageActions hpActions, SignupPageActions spActions, ProfilePageActions ppActions) {
        homePageActions = hpActions;
        signupPageActions = spActions;
        profilePageActions = ppActions;
    }

    /***
     * Method to check Login page is loaded.
     *
     * @return if the login page is loaded
     * @throws Exception
     */
    public boolean isLoginPageloaded() throws Exception {
        //waitForElementPresent(loginPageLocators.get("LoginScreenLogo"), 3);
        if (!isElementPresent(getBy(loginPageLocators.get("LoginScreenLogo")))) {
            logger.warn("Login Screen Logo not loaded in the Login screen.");
            return false;
        }
        if (!isElementPresent(getBy(loginPageLocators.get("SkipButton")))) {
            logger.warn("Skip Button not loaded in the Login screen.");
            return false;
        }
        if (!isElementPresent(getBy(loginPageLocators.get("LoginButton")))) {
            logger.warn("Login Button not loaded in the Login screen.");
            return false;
        }
        if (!isElementPresent(getBy(loginPageLocators.get("LoginEmailMobileNumber")))) {
            logger.warn("Email/Mobile Number Field not loaded in the Login screen.");
            return false;
        }
        if (!isElementPresent(getBy(loginPageLocators.get("LoginPassword")))) {
            logger.warn("Password Field not loaded in the Login screen.");
            return false;
        }
        logger.info("Loaded Login Screen");
        return true;
    }

    /***
     * Method to skip Login on application launch.
     *
     * @return if the login was skipped and home page was reached or not
     * @throws Exception
     */
    public boolean skipLogin() throws Exception {
        if (!isLoginPageloaded()) {
            return false;
        }
        driver.findElement(getBy(loginPageLocators.get("SkipButton"))).click();
        Thread.sleep(3000);
        if (homePageActions.isHomePageloaded()) {
            logger.info("Skipped the login screen.");
            return true;
        } else {
            logger.warn("Home page not loaded after skipping the login screen");
            return false;
        }
    }

    /***
     * Method to Enter mobile/email number.
     *
     * @param user email/mobile to login
     * @throws Exception
     */
    public void enterEmailMobileNum(String user) throws Exception {
        WebElement emailmobnum = driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber")));
        emailmobnum.clear();
        emailmobnum.sendKeys(user);
        logger.info("Entered the mobile/email to login.");
    }

    /***
     * Method to Enter password.
     *
     * @param password password to login
     * @throws Exception
     */
    public void enterPassword(String password) throws Exception {
        WebElement pass = driver.findElement(getBy(loginPageLocators.get("LoginPassword")));
        pass.click();
        pass.clear();
        pass.sendKeys(password);
        logger.info("Entered the password to login.");
    }

    /***
     * Method to click login button.
     *
     * @throws Exception
     */
    public void clickLoginButton() throws Exception {
        WebElement login = driver.findElement(getBy(loginPageLocators.get("LoginButton")));
        login.click();
        logger.info("Clicked login button");
    }

    /***
     * Method to click signup button.
     *
     * @throws Exception
     */
    public void clickSignupButton() throws Exception {
        WebElement signup = driver.findElement(getBy(loginPageLocators.get("SignUpButton")));
        signup.click();
        logger.info("Clicked signup button");
    }

    /***
     * Method to click change user button.
     *
     * @throws Exception
     */
    public void clickChangeUserButton() throws Exception {
        if (isElementPresent(getBy(loginPageLocators.get("ChangeUserButton")))) {
            WebElement changeuser = driver.findElement(getBy(loginPageLocators.get("ChangeUserButton")));
            changeuser.click();
            logger.info("Clicked Change User button");
        }
    }

    /***
     * Method to click show password button.
     *
     * @throws Exception
     */
    public void clickShowPassButton() throws Exception {
        if (isElementPresent(getBy(loginPageLocators.get("PasswordShowButton")))) {
            WebElement showpass = driver.findElement(getBy(loginPageLocators.get("PasswordShowButton")));
            showpass.click();
            logger.info("Clicked Show Password button");
        }
    }

    /***
     * Method to click show password button.
     *
     * @throws Exception
     */
    public void clickHidePassButton() throws Exception {
        if (isElementPresent(getBy(loginPageLocators.get("PasswordHideButton")))) {
            WebElement hidepass = driver.findElement(getBy(loginPageLocators.get("PasswordHideButton")));
            hidepass.click();
            logger.info("Clicked Hide Password button");
        }
    }

    /***
     * Method to click Forgot Password button.
     *
     * @throws Exception
     */
    public void clickForgotPassButton() throws Exception {
        WebElement forgot = driver.findElement(getBy(loginPageLocators.get("ForgotPasswordLink")));
        forgot.click();
        logger.info("Clicked Forgot Password button");
    }

    /***
     * Method to click Back button.
     *
     * @throws Exception
     */
    public void clickBackButton() throws Exception {
        WebElement back = driver.findElement(getBy(loginPageLocators.get("BackButton")));
        back.click();
        logger.info("Clicked Back button");
    }

    /***
     * Method to check Email OTP page is loaded.
     *
     * @return if the Email OTP page is loaded
     * @throws Exception
     */
    public boolean isEmailOTPPageloaded() throws Exception {
        waitForElementPresent(loginPageLocators.get("EmailOTPVerification"), 3);
        if (isElementPresent(getBy(loginPageLocators.get("EmailOTPVerification")))) {
            logger.info("Loaded the Email OTP screen.");
            return true;
        } else {
            logger.warn("Message \"Check your Inbox\" in Forgot Password screen is not loaded");
            return false;
        }
    }

    /***
     * Method to check SMS OTP page is loaded.
     *
     * @return if the SMS OTP page is loaded
     * @throws Exception
     */
    public boolean isMobileOTPPageloaded() throws Exception {
        waitForElementPresent(loginPageLocators.get("MobileOTPVerification"), 3);
        if (isElementPresent(getBy(loginPageLocators.get("MobileOTPVerification")))) {
            logger.info("Loaded the Mobile OTP screen.");
            return true;
        } else {
            logger.warn("Message \"We sent you an SMS\" in Forgot Password screen is not loaded");
            return false;
        }
    }

    public void closeAlert() throws Exception {
        if(isElementPresent(getBy(loginPageLocators.get("AlertBox")))) {
            WebElement ok = driver.findElement(getBy(loginPageLocators.get("OkButton")));
            ok.click();
            logger.info("Closed Alert Box");
        }
    }

    /***
     * Method to check Verification Unsuccessful page is loaded.
     *
     * @return if the SMS OTP page is loaded
     * @throws Exception
     */
    public boolean isVerificationUnsuccessfulPageloaded() throws Exception {
        waitForElementPresent(loginPageLocators.get("VerificationUnsuccessful"), 3);
        if (!isElementPresent(getBy(loginPageLocators.get("VerificationUnsuccessful")))) {
            logger.warn("Verification Unsuccessful static text not loaded in Verification Unsuccessful screen.");
            return false;
        }
        if (!isElementPresent(getBy(loginPageLocators.get("VerificationUnsuccessfulMsg")))) {
            logger.warn("Message \"Maximum attempts reached. Retry after 24 hours.\" not loaded in Verification Unsuccessful screen.");
            return false;
        }
        if (!isElementPresent(getBy(loginPageLocators.get("ContactUsButton")))) {
            logger.warn("Contact Us button not loaded in Verification Unsuccessful screen.");
            return false;
        }
        if (!isElementPresent(getBy(loginPageLocators.get("SkipForNowButton")))) {
            logger.warn("Skip for now Button not loaded in Verification Unsuccessful screen.");
            return false;
        }
        logger.info("Loaded Verification Unsuccessful screen.");
        return true;

    }

    /***
     * Method to Login using registered mobile/email number and password.
     *
     * @param user     email/mobile to login
     * @param password password
     * @return if the login was successful or not
     * @throws Exception
     */
    public boolean login(String user, String password) throws Exception {
        if (signupPageActions.isSignUpPageloaded()) {
            signupPageActions.clickLoginButton();
            logger.info("Moved to Login Page.");
        }
        if (!isLoginPageloaded()) {
            return false;
        }
        enterEmailMobileNum(user);
        enterPassword(password);
        clickLoginButton();
        Thread.sleep(3000);
        if (homePageActions.isHomePageloaded()) {
            logger.info("Login was successful.");
            return true;
        } else {
            logger.warn("Home page not loaded after login");
            return false;
        }
    }

    /***
     * Method to Login using invalid mobile/email number and password.
     *
     * @param user     email/mobile to login
     * @param password password
     * @return if the login was successful or not
     * @throws Exception
     */
    public boolean invalidLogin(String user, String password, String scenario) throws Exception {
        if (signupPageActions.isSignUpPageloaded()) {
            signupPageActions.clickLoginButton();
            logger.info("Moved to Login Page.");
        }
        if (!isLoginPageloaded()) {
            return false;
        }
        enterEmailMobileNum(user);
        enterPassword(password);
        clickLoginButton();
        Thread.sleep(3000);
        boolean result = false;
        switch (scenario) {
            case "InvalidAccount":
                if (isElementPresent(getBy(loginPageLocators.get("InvalidAccount")))) {
                    logger.info("Login with invalid account got error.");
                    result = true;
                } else result = false;
                break;
            case "InvalidPassword":
                if (isElementPresent(getBy(loginPageLocators.get("InvalidLogin")))) {
                    logger.info("Login with invalid password got error.");
                    result = true;
                } else result = false;
                break;
            case "InvalidEmailMobile":
                if (isElementPresent(getBy(loginPageLocators.get("InvalidEmailMobile")))) {
                    logger.info("Login with invalid email format got error.");
                    result = true;
                } else result = false;
                break;
        }
        closeAlert();
        Thread.sleep(5000);
        return result;
    }

    /***
     * Method to showpassword after entering registered mobile/email number and password.
     *
     * @param user     email/mobile to login
     * @param password password
     * @return if the login was successful or not
     * @throws Exception
     */
    public boolean showPassword(String user, String password) throws Exception {
        if (signupPageActions.isSignUpPageloaded()) {
            signupPageActions.clickLoginButton();
            logger.info("Moved to Login Page.");
        }
        if (!isLoginPageloaded()) {
            return false;
        }
        enterEmailMobileNum(user);
        enterPassword(password);
        clickHidePassButton();
        clickShowPassButton();
        Thread.sleep(1000);
        if (driver.findElement(getBy(loginPageLocators.get("LoginPassword"))).getText().equals(password)) {
            logger.info("Show Password was successful.");
            return true;
        } else {
            logger.warn("Password typed and the password shown does not match");
            return false;
        }
    }

    /***
     * Method to hidepassword after entering registered mobile/email number and password.
     *
     * @param user     email/mobile to login
     * @param password password
     * @return if the login was successful or not
     * @throws Exception
     */
    public boolean hidePassword(String user, String password) throws Exception {
        if (signupPageActions.isSignUpPageloaded()) {
            signupPageActions.clickLoginButton();
            logger.info("Moved to Login Page.");
        }
        if (!isLoginPageloaded()) {
            return false;
        }
        enterEmailMobileNum(user);
        enterPassword(password);
        clickShowPassButton();
        clickHidePassButton();
        //String hided_pass = StringUtils.repeat('•',password.length());
        String hided_pass = "passwordTextField";
        Thread.sleep(1000);
        if (driver.findElement(getBy(loginPageLocators.get("LoginPassword"))).getText().equals(hided_pass)) {
            logger.info("Hide Password was successful.");
            return true;
        } else {
            logger.warn("Password is not hidden");
            return false;
        }
    }

    /***
     * Method to verify forgot password.
     *
     * @param user email/mobile to login
     * @return if the login was successful or not
     * @throws Exception
     */
    public boolean forgotPassword(String user, String scenario) throws Exception {
        if(isLoginPageloaded() && !isElementPresent(getBy(loginPageLocators.get("ForgotPasswordLink")))) {
            clickSignupButton();
            logger.info("Moved to Signup Page");
        }
        if (signupPageActions.isSignUpPageloaded()) {
            signupPageActions.clickLoginButton();
            logger.info("Moved to Login Page.");
        }
        if (!isLoginPageloaded()) {
            return false;
        }
        enterEmailMobileNum(user);
        clickForgotPassButton();
        Thread.sleep(5000);
        if (scenario.equals("EmailAccount")) {
            if (isEmailOTPPageloaded()) {
                clickBackButton();
                return true;
            } else return false;
        } else if (scenario.equals("MobileAccount")) {
            if (isMobileOTPPageloaded()) {
                clickBackButton();
                return true;
            } else return false;
        }
        logger.warn("Email/Mobile OTP screen not loaded");
        return false;
    }

    /***
     * Method to verify forgot password for invalid username.
     *
     * @param user email/mobile to login
     * @return if the login was successful or not
     * @throws Exception
     */
    public boolean invalidForgotPassword(String user, String scenario) throws Exception {
        if(isLoginPageloaded() && !isElementPresent(getBy(loginPageLocators.get("ForgotPasswordLink")))) {
            clickSignupButton();
            logger.info("Moved to Signup Page");
        }
        if (signupPageActions.isSignUpPageloaded()) {
            signupPageActions.clickLoginButton();
            logger.info("Moved to Login Page.");
        }
        if (!isLoginPageloaded()) {
            return false;
        }
        enterEmailMobileNum(user);
        clickForgotPassButton();
        Thread.sleep(3000);
        boolean result = false;
        switch (scenario) {
            case "InvalidMobileAccount":
                if (isElementPresent(getBy(loginPageLocators.get("InvalidMobileForgotPass")))) {
                    logger.info("Error message Displayed");
                    result = true;
                } else {
                    logger.warn("Error message for the invalid mobile account is not displayed");
                    result = false;
                }
                break;
            case "InvalidEmailAccount":
                if (isElementPresent(getBy(loginPageLocators.get("InvalidEmailMobileForgotPass")))) {
                    logger.info("Error message Displayed");
                    result = true;
                } else {
                    logger.warn("Error message \"Invalid email/mobile\" is not displayed");
                    result = false;
                }
                break;
            case "MaxAttempts":
                for (int i = 1; i < 3; i++) {
                    clickBackButton();
                    enterEmailMobileNum(user);
                    clickForgotPassButton();
                }
                if (isVerificationUnsuccessfulPageloaded()) {
                    clickBackButton();
                    result = true;
                } else result = false;
                break;
            case "InvalidAccount":
                if (isElementPresent(getBy(loginPageLocators.get("InvalidAccountForgotPass")))) {
                    logger.info("Error message Displayed");
                    result = true;
                } else {
                    logger.warn("Error message for the invalid mobile account is not displayed");
                    result = false;
                }
                break;
            default:
                logger.warn("No error message displayed");
                break;
        }
        closeAlert();
        Thread.sleep(3000);
        return result;
    }

    public boolean loginFromProfile(String user, String password) throws Exception {
        if(signupPageActions.isSignUpPageloaded() || isLoginPageloaded()) {
            if (!skipLogin()) {
                return false;
            }
        }
        if (!homePageActions.navigateToProfilePage("Guest")) {
            return false;
        }
        profilePageActions.clickLoginButton();
        boolean result = login(user, password);
        return result;
    }
}