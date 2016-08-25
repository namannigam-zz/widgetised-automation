package page.android;

import page.AppiumBasePage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

public class LoginPageActions extends AppiumBasePage {

    public LoginPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Skips Login page or Signup page after application launch.
     *
     * @return if the login was skipped and home page was reached or not
     * @throws InterruptedException
     */
    public boolean skip() throws InterruptedException {
        PingActions pingActions = new PingActions(driver);
        Thread.sleep(3000);
        waitUntilSplashScreenIsDisplayed();
        waitForElementPresent(loginPageLocators.get("SkipButton"), 3);
        driver.findElement(getBy(loginPageLocators.get("SkipButton"))).click();
        waitUntilProgressBarIsDisplayed();
        if (isElementPresent(getBy(homePageLocators.get("HomePageCuetip"))) || isElementPresent(getBy(homePageLocators.get("HomePagePingCuetip"))))
            skipHomePageOverlay();
        Thread.sleep(2000);
        if (isElementPresent(getBy(socialCollabLocators.get("WelcomeMessageLayout"))))
            pingActions.removeWelcomeMsg();
        Thread.sleep(2000);
        waitForElementPresent(homePageLocators.get("Drawer"), 5);
        if (isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.info("Skipped the login screen.");
            return true;
        } else return false;
    }

    /***
     * Logs in the user using registered mobile and password when the app is launched for the first time and skips the cue-tips.
     *
     * @param mobile   number to login
     * @param password password to login
     * @return if the login was successful and home page is reached or not
     * @throws InterruptedException
     */
    public boolean firstLogin(String mobile, String password) throws InterruptedException {
        PingActions pingActions = new PingActions(driver);
        waitForElementPresent(loginPageLocators.get("LoginButton"), 2);
        WebElement etPass = driver.findElement(getBy(loginPageLocators.get("LoginPassword")));
        WebElement emLogin = driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber")));
        PageDefinition pageDefinition = new PageDefinition(driver);
        if (pageDefinition.isSignUpPage() == PageDefinition.PAGE_TYPE.SIGNUP_PAGE) {
            driver.findElement(getBy(loginPageLocators.get("LoginButton"))).click();
            logger.info("Moved to Login Page fro SignUp page...");
        }
        waitForElementPresent(loginPageLocators.get("LoginEmailMobileNumber"), 2);
        emLogin.clear();
        emLogin.sendKeys(mobile);
        logger.info("Entered the mobile/email to login.");
        etPass.clear();
        etPass.sendKeys(password);
        logger.info("Entered the password to login.");
        driver.findElement(getBy(loginPageLocators.get("LoginButton"))).click();
        waitUntilProgressBarIsDisplayed();
        if (isElementPresent(getBy(loginPageLocators.get("ContinueButton"))))
            skipSecureAccount();
        if (isElementPresent(getBy(homePageLocators.get("HomePageCuetip"))) || isElementPresent(getBy(homePageLocators.get("HomePagePingCuetip"))))
            skipHomePageOverlay();
        if (isElementPresent(getBy(socialCollabLocators.get("WelcomeMessageLayout"))))
            pingActions.removeWelcomeMsg();
        Thread.sleep(2000);
        if (isElementPresent(getBy(homePageLocators.get("Drawer"))) && isElementPresent(getBy(homePageLocators.get("NotificationIcon")))
                && isElementPresent(getBy(homePageLocators.get("MoreOptions")))) {
            logger.info("Reached to the homepage after login.");
            return true;
        } else return false;
    }

    /***
     * Logs in using registered mobile/email number and password.
     *
     * @param email    email to login
     * @param password password
     * @return if the login was successful or not
     * @throws InterruptedException
     */
    public boolean login(String email, String password) throws InterruptedException {
        Thread.sleep(3000);
        waitForElementPresent(loginPageLocators.get("LoginButton"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginButton"))).click();
        logger.info("Moved to Login Page.");
        waitForElementPresent(loginPageLocators.get("LoginEmailMobileNumber"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber"))).clear();
        driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber"))).sendKeys(email);
        logger.info("Entered the mobile/email to login.");
        waitForElementPresent(loginPageLocators.get("LoginPassword"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginPassword"))).click();
        driver.findElement(getBy(loginPageLocators.get("LoginPassword"))).sendKeys(password);
        logger.info("Entered the password to login.");
        waitForElementPresent(loginPageLocators.get("LoginButton"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginButton"))).click();
        Thread.sleep(3000);
        if (isElementPresent(getBy(homePageLocators.get("Drawer"))) && isElementPresent(getBy(homePageLocators.get("NotificationIcon")))
                && isElementPresent(getBy(homePageLocators.get("MoreOptions")))) {
            logger.info("Login was successful.");
            return true;
        } else return false;
    }

    /***
     * Moves to the signup page clicking signup button
     *
     * @return if signup page is loaded or not
     */
    public boolean navigateToSignup() {
        logger.info("Waiting for Login Page.");
        waitForElementPresent(loginPageLocators.get("SignupButton"), 2);
        logger.info("Detected Signup Button");
        driver.findElement(getBy(loginPageLocators.get("SignupButton"))).click();
        logger.info("Clicked Signup Button");
        return true;
    }

    /**
     * Checks the status of use log in
     *
     * @param email    email/mobile of the user
     * @param password password for the account
     * @return if the user is signed in or not
     * @throws InterruptedException
     */
    public void checkLoginStatus(String email, String password) throws InterruptedException {
        WebElement moreOptions = driver.findElement(getBy(homePageLocators.get("MoreOptions")));
        moreOptions.click();
        List<WebElement> overFlowMenu = driver.findElements(getBy(homePageLocators.get("OverflowMenuOptionList")));
        String menuOption = overFlowMenu.get(0).getText();
        HomePageActions homePageActions = new HomePageActions(driver);
        if (menuOption.equalsIgnoreCase("Login")) {
            driver.navigate().back();
            homePageActions.loginFromMenu(email, password);
            skipHomePageOverlay();
            return;
        } else {
            driver.navigate().back();
            return;
        }
    }

    /***
     * Logs in using registered international mobile number and password.
     *
     * @param mobile           number to login
     * @param password         password lo login
     * @param countryShortName short country name to be selected
     * @return if the international number login was successful or not
     * @throws InterruptedException
     */
    public boolean internationalLogin(String mobile, String password, String countryShortName) throws InterruptedException {
        Thread.sleep(3000);
        waitUntilSplashScreenIsDisplayed();
        waitForElementPresent(loginPageLocators.get("LoginButton"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginButton"))).click();
        logger.info("Moved to Login Page.");
        if (!verifyCountryNameAndCode()) {
            logger.error("Some issues with international number login.");
            return false;
        }
        waitForElementPresent(loginPageLocators.get("LoginEmailMobileNumber"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber"))).clear();
        driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber"))).sendKeys("4");
        logger.info("The country code and name are displayed now. ");
        driver.findElement(getBy(loginPageLocators.get("CountryCodeInfo"))).click();
        waitForElementPresent(loginPageLocators.get("CountrySelectionPanel"), 2);
        logger.info("Custom Panel for country list is displayed. Please select one!");

        driver.findElement(getBy(loginPageLocators.get("CountrySearchTextBox"))).sendKeys(countryShortName);
        List<WebElement> countryNameList = driver.findElements(getBy(loginPageLocators.get("CountryName")));
        int index;
        for (index = 0; index < countryNameList.size(); index++) {
            String completeCountryName = countryNameList.get(index).getText();
            String selectedShortName = completeCountryName.substring(completeCountryName.indexOf('(') + 1, completeCountryName.indexOf(')'));
            if (selectedShortName.equalsIgnoreCase(countryShortName))
                break;
        }
        countryNameList.get(index).click();
        waitForElementPresent(loginPageLocators.get("LoginEmailMobileNumber"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber"))).clear();
        driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber"))).sendKeys(mobile);
        logger.info("Entered the international mobile/email to login.");
        waitForElementPresent(loginPageLocators.get("LoginPassword"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginPassword"))).click();
        waitForElementPresent(loginPageLocators.get("LoginPassword"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginPassword"))).sendKeys(password);
        logger.info("Entered the password to login.");
        waitForElementPresent(loginPageLocators.get("LoginButton"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginButton"))).click();
        Thread.sleep(3000);
        if (isElementPresent(getBy(homePageLocators.get("Drawer"))) && isElementPresent(getBy(homePageLocators.get("NotificationIcon")))
                && isElementPresent(getBy(homePageLocators.get("MoreOptions")))) {
            logger.info("Reached to the homepage after login.");
            return true;
        } else return false;
    }

    /***
     * Skips the Secure account pop-up on Login.
     *
     * @throws InterruptedException
     */
    public void skipSecureAccount() throws InterruptedException {
        waitForElementPresent(loginPageLocators.get("ContinueButton"), 2);
        driver.findElement(getBy(loginPageLocators.get("SkipButton"))).click();
        logger.info("Skipping the secure account pop-up message.");
    }

    /***
     * Skips the overlay displayed on the first load of Home Page.
     *
     * @throws InterruptedException
     */
    public void skipHomePageOverlay() throws InterruptedException {
        if (isElementPresent(getBy(homePageLocators.get("HomePageCuetip"))))
            driver.findElement(getBy(homePageLocators.get("HomePageCuetip"))).click();
        if (isElementPresent(getBy(homePageLocators.get("HomePagePingCuetip"))))
            driver.findElement(getBy(homePageLocators.get("HomePagePingCuetip"))).click();
//        if (isElementPresent(getBy(homePageLocators.get("ChatContainer"))))
//            driver.findElement(getBy(homePageLocators.get("ChatContainer"))).click();
        logger.info("Skipping the homepage overlay...");
    }

    /***
     * Verifies the Forgot password link at login and the timer running after resend code is clicked.
     *
     * @param email to verify the forgot link against
     * @return if the forgot link takes to verification page or not
     * @throws InterruptedException
     */
    public boolean forgotPasswordLink(String email) throws InterruptedException {
        waitUntilSplashScreenIsDisplayed();
        waitForElementPresent(loginPageLocators.get("LoginButton"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginButton"))).click();
        waitForElementPresent(loginPageLocators.get("LoginEmailMobileNumber"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber"))).clear();
        waitForElementPresent(loginPageLocators.get("LoginEmailMobileNumber"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber"))).sendKeys(email);
        waitForElementPresent(loginPageLocators.get("ForgotPasswordLink"), 2);
        driver.findElement(getBy(loginPageLocators.get("ForgotPasswordLink"))).click();
        logger.info("Clicked on the Forgot Password Link");
        Thread.sleep(4000);
        if (isElementPresent(getBy(loginPageLocators.get("ResendCodeLink"))) && isElementPresent(getBy(loginPageLocators.get("VerificationCodeIndex")))) {
            driver.getKeyboard();
            driver.hideKeyboard();
            if (resendCodeVerify()) {
                driver.navigate().back();
                logger.info("Navigated successfully to verification page.");
                return true;
            } else return false;
        } else if (isElementPresent(getBy(loginPageLocators.get("VerificationUnsuccessfulContact")))) {
            driver.navigate().back();
            logger.info("Maximum attempts for verification reached");
            return true;
        } else {
            logger.error("Unable to navigate to verification page.");
            return false;
        }
    }

    /***
     * Verifies the resendCodeTimer at forgot link page.
     *
     * @return if the timer is displayed for a resend or not.
     * @throws InterruptedException
     */
    public boolean resendCodeVerify() throws InterruptedException {
        waitForElementPresent(loginPageLocators.get("ResendCodeLink"), 2);
        driver.findElement(getBy(loginPageLocators.get("ResendCodeLink"))).click();
        if (isElementPresent(getBy(loginPageLocators.get("ResendCodeTimer")))) {
            logger.info("Resend Code Timer displayed successfully.");
            return true;
        } else return false;
    }

    /***
     * Validates and skip the on-boarding page.
     *
     * @return if the on-boarding is skipped successfully or not
     * @throws InterruptedException
     */
    public boolean skipOnboarding() throws InterruptedException {
        if (isElementPresent(getBy(loginPageLocators.get("OnBoardingImageView"))))
            driver.findElement(getBy(loginPageLocators.get("OnBoardingCloseButton"))).click();
        wait(2000);
        return isElementPresent(getBy(loginPageLocators.get("LoginButton")));
    }

    /***
     * Selects 5 random countries from the list and verifies their name and code displayed for international number login.
     *
     * @return if the displayed values are correct or not
     * @throws InterruptedException
     */
    public boolean verifyCountryNameAndCode() throws InterruptedException {
        HashMap<String, String> countryLocaleMap = interLoginMapping();
        waitForElementPresent(loginPageLocators.get("LoginEmailMobileNumber"), 2);
        driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber"))).clear();
        driver.findElement(getBy(loginPageLocators.get("LoginEmailMobileNumber"))).sendKeys("7");
        logger.info("The country code and name are displayed now. ");
        for (int i = 0; i < 5; i++) {
            driver.findElement(getBy(loginPageLocators.get("CountryCodeInfo"))).click();
            waitForElementPresent(loginPageLocators.get("CountrySelectionPanel"), 2);
            List<WebElement> countryNameList = driver.findElements(getBy(loginPageLocators.get("CountryName")));
            List<WebElement> countryCodeList = driver.findElements(getBy(loginPageLocators.get("CountryTelephoneCode")));
            int accessItem = randomNumber(countryNameList.size() - 1);
            String completeCountryName = countryNameList.get(accessItem).getText();
            String selectedCountryCode = countryCodeList.get(accessItem).getText();
            String selectedCountryName = completeCountryName.substring(completeCountryName.indexOf('(') + 1, completeCountryName.indexOf(')'));
            String expectedCountryNameCode = countryLocaleMap.get(selectedCountryName) + " +" + selectedCountryCode;
            countryCodeList.get(accessItem).click();
            Thread.sleep(1000);
            String displayedCountryNameCode = countryNameCodeDisplayed();
            if (!expectedCountryNameCode.equals(displayedCountryNameCode)) {
                logger.error("Country code and name displayed incorrectly.");
                return false;
            }
            logger.info("Country code " + selectedCountryCode + " and country name " + selectedCountryName + " are displayed correctly for the selection.");
        }
        return true;
    }

    /***
     * Verifies if the show password option is working
     *
     * @param password password entered by the user
     * @return if the password was displayed or not
     * @throws InterruptedException
     */
    public boolean showPassword(String password) throws InterruptedException {
        WebElement etPass = driver.findElement(getBy(loginPageLocators.get("LoginPassword")));
        etPass.sendKeys(password);
        WebElement showPassword = driver.findElement(getBy(loginPageLocators.get("PasswordShowButton")));
        showPassword.click();
        waitForElementPresent(loginPageLocators.get("LoginPassword"), 2);
        String actualPassword = etPass.getText();
        if (actualPassword.equals(password)) {
            logger.info("Show password is working correctly.");
            showPassword.click();
            return true;
        } else return false;
    }

    /***
     * Gets the country name and code displayed on login
     *
     * @return the country name and code displayed as a string
     */
    public String countryNameCodeDisplayed() {
        return (driver.findElement(getBy(loginPageLocators.get("CountryCodeInfo"))).getText());
    }

    /***
     * Gets the map of short country name and their locale for international login
     *
     * @return map of locale and short country name
     */
    public HashMap<String, String> interLoginMapping() {
        HashMap<String, String> interLoginHashMap = new HashMap();
        try {
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/ui-test/src/main/resources/data/" +
                    "InternationalLoginDetail.json"));
//            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/resources/data/" + "InternationalLoginDetail.json"));
            JsonObject jsonObject = (JsonObject) obj;
            JsonArray jsonArray = (JsonArray) jsonObject.get("countries");
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject countryDetails = (JsonObject) jsonArray.get(i);
                String fullCountryName = countryDetails.get("fullCountryName").toString();
                String shortCountryName = countryDetails.get("shortCountryName").toString();
                String locale = countryDetails.get("locale").toString();
                String countryTelephonyCode = countryDetails.get("countryTelephonyCode").toString();
                shortCountryName = shortCountryName.replace("\"", "").trim();
                locale = locale.replace("\"", "").trim();
                interLoginHashMap.put(shortCountryName, locale);
            }
        } catch (FileNotFoundException except) {
            except.printStackTrace();
        }
        return interLoginHashMap;
    }
}