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
public class PingTest extends BaseTest {

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
     * Tests navigating to ping startup via 3-dot menu
     */
    @Test(groups = {"pingTests"})
    public void navigatingToPingTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertEquals(homePageActions.navigateToPingStartup(), true, "Something went wrong in navigating to the ping option.");
            logger.info("Navigated to Ping successfully!");
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
     * Tests getting started with ping after login from the ping startup
     *
     * @param pingHM user credentials to login
     */
    @Test(groups = {"pingTests"}, dataProvider = "pingTests", dataProviderClass = DataProviders.class, dependsOnMethods = "navigatingToPingTest")
    public void gettingStartedWithPingTest(HashMap pingHM) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(pingActions.getStartedWithPing(pingHM.get("email").toString(), pingHM.get("password").toString()), true, "Something went wrong in getting started with ");
            logger.info("Skipped the welcome message to Ping successfully!");
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
     * Tests skipping cue-tip for ping at browse page after signing in
     */
    @Test(groups = {"pingTests"}, dependsOnMethods = "gettingStartedWithPingTest")
    public void skipPingCuetipOnBrowsePageTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.navigateToSearchPage());
            assertTrue(searchPageActions.search("mobiles"));
            assertEquals(pingActions.skipPingCuetip(), true, "Something went wrong in skipping the ping cue-tip at browse page.");
            logger.info("Skipped the cue-tip at browse page for ping successfully!");
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
     * Tests skipping cue-tip for ping at product page after signing in
     */
//    @Test(groups = {"pingTests"}, dependsOnMethods = "skipPingCuetipOnBrowsePageTest") //TODO : enable when fixed on app
    public void skipPingCuetipOnProductPageTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(browsePageActions.selectAnyItemFromSearchedList());
            assertEquals(pingActions.skipPingCuetip(), true, "Something went wrong in skipping the ping cue-tip at product page.");
            logger.info("Skipped the cue-tip at product page for ping successfully!");
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
