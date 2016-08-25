package page.ios;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class SignupPageActions extends AppiumBasePage {
    HomePageActions homePageActions;

    public SignupPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Method to set the other page objects
     */
    public void setOtherPageObjects(HomePageActions hpActions) {
        homePageActions = hpActions;
    }

    /***
     * Method to check SignUp page is loaded.
     *
     * @return if the login page is loaded
     * @throws Exception
     */
    public boolean isSignUpPageloaded() throws Exception {
        //waitForElementPresent(signupPageLocators.get("LoginScreenLogo"), 3);
        if (!isElementPresent(getBy(signupPageLocators.get("LoginScreenLogo")))) {
            logger.warn("Login Screen Logo not loaded in the SignUp screen.");
            return false;
        }
        if (!isElementPresent(getBy(signupPageLocators.get("SkipButton")))) {
            logger.warn("Skip Button not loaded in the SignUp screen.");
            return false;
        }
        if (!isElementPresent(getBy(signupPageLocators.get("LoginButton")))) {
            logger.warn("Login Button not loaded in the SignUp screen.");
            return false;
        }
        if (!isElementPresent(getBy(signupPageLocators.get("SignUpButton")))) {
            logger.warn("Signup Button not loaded in the SignUp screen.");
            return false;
        }
        if (!isElementPresent(getBy(signupPageLocators.get("SignUpMobileNumber")))) {
            logger.warn("Mobile number text field not loaded in the SignUp screen.");
            return false;
        }
        logger.info("Loaded Signup Screen");
        return true;
    }

    /***
     * Method to check Account Exists page is loaded.
     *
     * @return if the login page is loaded
     * @throws Exception
     */
    public boolean isAccountExistsPageloaded() throws Exception {
        //waitForElementPresent(signupPageLocators.get("AccountExists"), 3);
        if (!isElementPresent(getBy(signupPageLocators.get("AccountExists")))) {
            logger.warn("Account Exists static text not loaded in the Account Exists screen.");
            return false;
        }
        if (!isElementPresent(getBy(signupPageLocators.get("SkipButton")))) {
            logger.warn("Skip Button not loaded in the Account Exists screen.");
            return false;
        }
        if (!isElementPresent(getBy(signupPageLocators.get("LoginButton")))) {
            logger.warn("Login Button not loaded in the Account Exists screen.");
            return false;
        }
        if (!isElementPresent(getBy(signupPageLocators.get("LoginEmailMobileNumber")))) {
            logger.warn("Email/Mobile Number Field not loaded in the Account Exists screen.");
            return false;
        }
        if (!isElementPresent(getBy(signupPageLocators.get("LoginPassword")))) {
            logger.warn("Password Field not loaded in the Account Exists screen.");
            return false;
        }
        logger.info("Loaded Account Exists screen");
        return true;
    }

    /***
     * Method to skip Signup on application launch.
     *
     * @return if the Signup was skipped and home page was reached or not
     * @throws Exception
     */
    public void skipSignup() throws Exception {
        if (!isSignUpPageloaded()) {
            return;
        }
        driver.findElement(getBy(signupPageLocators.get("SkipButton"))).click();
        Thread.sleep(3000);
        if (homePageActions.isHomePageloaded()) {
            logger.info("Skipped the login screen.");
            return;
        } else {
            logger.warn("Home page not loaded after skipping the login screen");
            return;
        }
    }

    /***
     * Method to Enter mobile number.
     *
     * @param user mobile to signup
     * @throws Exception
     */
    public void enterMobileNum(String user) throws Exception {
        WebElement mobnum = driver.findElement(getBy(signupPageLocators.get("SignUpMobileNumber")));
        mobnum.clear();
        mobnum.sendKeys(user);
        logger.info("Entered the mobile to signup.");
    }

    /***
     * Method to click login button.
     *
     * @throws Exception
     */
    public void clickLoginButton() throws Exception {
        WebElement login = driver.findElement(getBy(signupPageLocators.get("LoginButton")));
        login.click();
        logger.info("Clicked login button");
    }

    /***
     * Method to click signup button.
     *
     * @throws Exception
     */
    public void clickSignupButton() throws Exception {
        WebElement signup = driver.findElement(getBy(signupPageLocators.get("SignUpButton")));
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
            WebElement changeuser = driver.findElement(getBy(signupPageLocators.get("ChangeUserButton")));
            changeuser.click();
            logger.info("Clicked Change User button");
        }
    }

    /***
     * Method to click Back button.
     *
     * @throws Exception
     */
    public void clickBackButton() throws Exception {
        WebElement back = driver.findElement(getBy(signupPageLocators.get("BackButton")));
        back.click();
        logger.info("Clicked Back button");
    }

    /***
     * Method to check SMS OTP page is loaded.
     *
     * @return if the SMS OTP page is loaded
     * @throws Exception
     */
    public boolean isMobileOTPPageloaded() throws Exception {
        waitForElementPresent(signupPageLocators.get("MobileOTPVerification"), 3);
        if (isElementPresent(getBy(signupPageLocators.get("MobileOTPVerification")))) {
            logger.info("Loaded the Mobile OTP screen.");
            return true;
        } else {
            logger.warn("Message \"We sent you an SMS\" in Forgot Password screen is not loaded");
            return false;
        }
    }

    /***
     * Method to verify invalid signup.
     *
     * @param user mobile to signup
     * @return if the login was successful or not
     * @throws Exception
     */
    public boolean invalidSignup(String user, String scenario) throws Exception {
        if (!isSignUpPageloaded()) {
            return false;
        }
        enterMobileNum(user);
        clickSignupButton();
        Thread.sleep(3000);
        if (scenario.equals("AccountExists")) {
            if (isAccountExistsPageloaded()) {
                clickChangeUserButton();
                return true;
            } else return false;
        } else if (scenario.equals("InvalidMobile")) {
            if (isElementPresent(getBy(signupPageLocators.get("InvalidMobile")))) {
                logger.info("Error message \"Invalid mobile number\" is displayed");
                return true;
            } else {
                logger.warn("Error message \"Invalid mobile number\" is not displayed");
                return false;
            }
        }
        return false;
    }
}