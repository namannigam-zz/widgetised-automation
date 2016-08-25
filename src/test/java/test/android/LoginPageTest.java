package test.android;

import mobile.util.Common;
import mobile.util.DataProviders;

import org.openqa.selenium.TimeoutException;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.HashMap;

/**
 * Created by naman.nigam on 01/03/16.
 */
public class LoginPageTest extends BaseTest {

    AdsActions adsAction;
    BrowsePageActions browsePageActions;
    CartPageActions cartPageActions;
    CheckoutPageActions checkoutPageActions;
    FOZPageActions fozPageActions;
    HomePageActions homePageActions;
    LoginPageActions loginPageActions;
    NotificationPageActions notificationPageActions;
    PageDefinition pageDefinition;
    PingActions pingActions;
    ProductPageActions productPageActions;
    ProductPageV3 productPageV3;
    ProfilePageActions profilePageActions;
    SearchPageActions searchPageActions;
    SignUpActions signUpActions;
    ToolbarActions toolbarActions;
    WishlistPageActions wishlistPageActions;
    Common common;

    @BeforeTest
    public void setLoginPageActions() {
        adsAction = new AdsActions(driver);
        browsePageActions = new BrowsePageActions(driver);
        cartPageActions = new CartPageActions(driver);
        checkoutPageActions = new CheckoutPageActions(driver);
        fozPageActions = new FOZPageActions(driver);
        homePageActions = new HomePageActions(driver);
        loginPageActions = new LoginPageActions(driver);
        notificationPageActions = new NotificationPageActions(driver);
        pageDefinition = new PageDefinition(driver);
        pingActions = new PingActions(driver);
        productPageActions = new ProductPageActions(driver);
        productPageV3 = new ProductPageV3(driver);
        profilePageActions = new ProfilePageActions(driver);
        searchPageActions = new SearchPageActions(driver);
        signUpActions = new SignUpActions(driver);
        toolbarActions = new ToolbarActions(driver);
        wishlistPageActions = new WishlistPageActions(driver);
        common = new Common();
        String[] className = Thread.currentThread().getStackTrace()[1].getClassName().split("\\.");
        System.out.println("************************ STARTING  :: " + className[className.length - 1] + " ***********************");
    }

    /**
     * Tests for signup for the existing users using the path Home Page, Login Page, Signup Page
     * - relies on the error message that appears on signup page
     *
     * @param userCredentials User login credentials
     * @param signupDP        data provides passes existing_user_mobile_number value to the test
     * @author Vishwanath
     */
//    @Test(groups = {"loginTest"}, priority = -1, dataProvider = "signUpTests", dataProviderClass = DataProviders.class, testName = "newAppSignUpTests", description = "")
    public void existingUserSignupNewApp(HashMap userCredentials, HashMap signupDP) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(signUpActions.signup(signupDP.get("existing_user_mobile_number").toString(), ""), SignUpActions.SIGNUP_STATUS.SIGNUP_USER_ALREADY_EXISTS, "Already existing User notification not displayed at SignUp.");
            logger.info("Sign up test on new app concluded successfully.");
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + " for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests navigating to verification page using the forgot password link
     * - also resending verification code triggering timer
     *
     * @param credentialsHM user login credentials
     * @param loginHM       alternate user credentials
     */
    @Test(groups = {"loginTest"}, priority = -1, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void forgotPasswordOnLoginTest(HashMap credentialsHM, HashMap loginHM) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(loginPageActions.forgotPasswordLink(credentialsHM.get("email").toString()), true, "Something went wrong in forgot link transmission.");
            logger.info("Forgot Password Link and Resend Verification Timer are working successfully!");
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + " for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests the show password option on the login page to assert te text of password entered
     *
     * @param credentialsHM users credentials enetered
     * @param loginHM       alternate credentials
     */
    @Test(groups = {"loginTest"}, dataProvider = "loginTests", dataProviderClass = DataProviders.class, dependsOnMethods = "forgotPasswordOnLoginTest")
    public void showPasswordTest(HashMap credentialsHM, HashMap loginHM) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(loginPageActions.showPassword(credentialsHM.get("password").toString()), true, "Something went wrong in show password.");
            logger.info("Forgot Password Link and Resend Verification Timer are working successfully!");
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + " for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests performing login using local(IND) number to the application
     *
     * @param credentialsHM user login credentials
     * @param loginHM       alternate user credentials
     */
    @Test(groups = {"loginTest"}, dataProvider = "loginTests", dataProviderClass = DataProviders.class, dependsOnMethods = "showPasswordTest")
    public void firstLoginTest(HashMap credentialsHM, HashMap loginHM) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(loginPageActions.firstLogin(loginHM.get("mobile_login").toString(), loginHM.get("mobile_password").toString()), true, "Something went wrong in login using mobile number.");
            logger.info("Login using mobile working successfully!");
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + " for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests performing logout from the home page via profile page of the user
     * - and validating landing back to the home page
     */
    @Test(groups = {"loginTest"}, dependsOnMethods = "firstLoginTest")
    public void logoutTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(homePageActions.logoutFromMenu(), true, "Something went wrong in logout from menu options.");
            logger.info("Logging out from overflow menu working successfully!");
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + " for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests performing login using international number from the 3-dot menu
     *
     * @param credentialsHM user login credentials
     * @param loginHM       alternate user login credentials
     */
    @Test(groups = {"loginTest"}, dataProvider = "loginTests", dataProviderClass = DataProviders.class, dependsOnMethods = "logoutTest")
    public void internationalLoginTest(HashMap credentialsHM, HashMap loginHM) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertEquals(homePageActions.internationalLoginFromMenu(loginHM.get("inter_number_login").toString(), loginHM.get("inter_number_password").toString(),
                    loginHM.get("inter_country").toString()), true, "Something went wrong in login using international number.");
            logger.info("Login using international number is working successfully!");
            assertTrue(homePageActions.logoutFromMenu());
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + " for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests performing login using email from the 3-dot menu
     *
     * @param credentialsHM user login credentials
     * @param loginHM       alternate user login credentials
     */
    @Test(groups = {"loginTest"}, dataProvider = "loginTests", dataProviderClass = DataProviders.class, testName = "loginTests", description = "", dependsOnMethods = "logoutTest")
    public void emailLoginTest(HashMap credentialsHM, HashMap loginHM) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertEquals(homePageActions.loginFromMenu(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()), true, "Something went wrong in login using email from overflow menu options.");
            logger.info("Login using email from overflow menu is working successfully!");
            assertTrue(homePageActions.logoutFromMenu());
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + " for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests for signup for the existing users using the path Home Page, Login Page, Signup Page
     * - relies on the error message that appears on signup page
     *
     * @param userCredentialsHM User login credentials
     * @param signupDP          data provides passes existing_user_mobile_number value to the test
     * @author Vishwanath
     */
    @Test(groups = {"loginTest"}, dataProvider = "signUpTests", dataProviderClass = DataProviders.class, testName = "signUpTests", description = "", dependsOnMethods = "logoutTest")
    public void existingUserSignUpFromHomePageTest(HashMap userCredentialsHM, HashMap signupDP) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(toolbarActions.navigateToLoginPage());
            assertTrue(loginPageActions.navigateToSignup());
            assertEquals(signUpActions.signup(signupDP.get("existing_user_mobile_number").toString(), ""), SignUpActions.SIGNUP_STATUS.SIGNUP_USER_ALREADY_EXISTS, "Observed signup success for existing user");
            assertTrue(loginPageActions.skip());
            logger.info("Verified the flow of signing up using existing user successfully!");
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + " for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    @AfterTest
    public void testEndConsole() {
        String[] className = Thread.currentThread().getStackTrace()[1].getClassName().split("\\.");
        System.out.println("************************ COMPLETED :: " + className[className.length - 1] + " ***********************");
    }

    /**
     * The method includes
     * - capturing the android logs for failure
     * - capturing screenshots for failure cases
     *
     * @param result for the test method executed
     */
    @AfterMethod
    public void afterMethod(ITestResult result) {
        common.captureScreenshot(result);
        if (result.getStatus() == ITestResult.FAILURE) {
            common.captureDump();
        }
    }
}
