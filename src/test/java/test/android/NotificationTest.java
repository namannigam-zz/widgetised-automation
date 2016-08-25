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
public class NotificationTest extends BaseTest {

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
     * Tests the navigation to notification page from home page
     * via the toolbar actions notification icon
     */
    @Test(groups = {"notificationTests"})
    public void navigateToNotificationPageTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertEquals(homePageActions.navigateToNotificationPage(), true, "Navigate to notifications failed.");
            logger.info("Navigate to the notification page successfully!");
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
     * Tests logging in from the notification page option og login now
     *
     * @param notificationHM user credentials to log in
     */
    @Test(groups = {"notificationTests"}, dataProvider = "notificationTests", dataProviderClass = DataProviders.class, dependsOnMethods = "navigateToNotificationPageTest")
    public void loginFromNotificationPageTest(HashMap notificationHM) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(notificationPageActions.loginFromNotificationPage(notificationHM.get("email").toString(), notificationHM.get("password").toString()), true, "Something went wrong in login from notification page.");
            logger.info("Validated logging in from the notification page successfully!");
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
     * Tests viewing the sharing options on notification page using the share options
     */
    @Test(groups = {"notificationTests"}, testName = "notificationPageTests", description = "", dependsOnMethods = "loginFromNotificationPageTest")
    public void shareNotificationTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(notificationPageActions.shareFromNotificationPage(), true, "Something went wrong in sharing from notification page.");
            logger.info("Sharing the notifications if available from notifications page validated successfully.");
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
     * Tests the deletion of the notification dragging it either side of the screen
     */
    @Test(groups = {"notificationTests"}, testName = "notificationPageTests", description = "", dependsOnMethods = "loginFromNotificationPageTest")
    public void deleteNotificationTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(notificationPageActions.deleteNotification(), true, "Something went wrong in deleting the notifications.");
            logger.info("Successfully deleted the notification from notification page.");
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