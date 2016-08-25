package test.android;

import mobile.util.Common;
import mobile.util.DataProviders;
import org.openqa.selenium.TimeoutException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Created by naman.nigam on 01/03/16.
 */
public class ProfilePageTest extends BaseTest {

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
    public void setBrowsePageActions() {
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
     * Tests navigating to the profile page from the home page via 3-dot menu
     * after logging in from the login page
     * @param myAccountsHM user credentials to log in
     */
    @Test(groups = {"profileTests"}, dataProvider = "myAccountsTests", dataProviderClass = DataProviders.class)
    public void navigateToProfileTest(HashMap myAccountsHM) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.loginFromMenu(myAccountsHM.get("email").toString(), myAccountsHM.get("password").toString()));
            assertEquals(homePageActions.navigateToProfilePage(), true, "Something went wrong in navigating to the Profile Page.");
            logger.info("Navigated to the profile page of the user successfully!");
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
     * Tests the view all order option on the profile page for the signed in user
     * - user signed in
     */
    @Test(groups = {"profileTests"}, testName = "myAccountTests", description = "", dependsOnMethods = "navigateToProfileTest")
    public void viewAllOrderTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(profilePageActions.viewAllOrder(), true, "Unable to view all the orders from the profile page.");
            driver.navigate().back();
            logger.info("Validated view all orders from profile page successfully.");
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
     * Tests the view profile addresses option from the profile page for a user
     * - user signed in
     */
    @Test(groups = {"profileTests"}, testName = "myAccountTests", description = "", dependsOnMethods = "navigateToProfileTest")
    public void viewProfileAddressesTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(profilePageActions.viewAddresses(true), true, "Unable to view all the addresses from the profile page.");
            logger.info("Validated view address from profile page successfully.");
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
     * Tests the add address to a user profile options
     * - user signed in
     */
    @Test(groups = {"profileTests"}, testName = "myAccountTests", description = "", dependsOnMethods = "navigateToProfileTest")
    public void addAddressToProfileTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(profilePageActions.addAddress(), true, "Unable to add address to the user's list from the profile page.");
            driver.navigate().back();
            logger.info("Validated add address from profile page successfully.");
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
     * Test the view wallets attached to  a user profile
     * - user signed in
     */
    @Test(groups = {"profileTests"}, testName = "myAccountTests", description = "", dependsOnMethods = "navigateToProfileTest")
    public void viewProfileWalletTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(profilePageActions.viewWalletDetails(true), true, "Unable to view wallet details from the profile page.");
            driver.navigate().back();
            logger.info("Validated wallet details from profile page successfully.");
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
     * Tests validating the profile subscription for a user
     * - user signed in
     */
    @Test(groups = {"profileTests"}, testName = "myAccountTests", description = "", dependsOnMethods = "navigateToProfileTest")
    public void viewProfileSubscriptionTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(profilePageActions.viewSubscriptions(true), true, "Unable to view subscriptions from the profile page.");
            driver.navigate().back();
            logger.info("Validated subscriptions from profile page successfully.");
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
     * Tests the profile setting page displayed for the user
     * - user signed in
     */
    @Test(groups = {"profileTests"}, testName = "myAccountTests", description = "", dependsOnMethods = "navigateToProfileTest")
    public void viewProfileSettingsTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(profilePageActions.accountSettings(), true, "Unable to view account settings from the profile page.");
            driver.navigate().back();
            logger.info("Validate account settings successfully");
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
     * Tests the display of orders placed by the user on the My Orders page
     * - user signed in
     */
    @Test(groups = {"profileTests"}, dependsOnMethods = "navigateToProfileTest")
    public void myOrdersTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertEquals(homePageActions.navigateToMyOrdersPage(), true, "Something went wrong in navigating to the My Orders Page.");
            assertTrue(homePageActions.navigateBackHome());
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
