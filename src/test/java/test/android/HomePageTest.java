package test.android;

import mobile.util.Common;
import org.openqa.selenium.TimeoutException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by naman.nigam on 01/03/16.
 */
public class HomePageTest extends BaseTest {

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
     * Tests navigating to home page from any other view displayed on the application
     */
    @Test(groups = {"homePageTests"})
    public void navigateToHomePage() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(homePageActions.navigateBackHome(), true, "Something went wrong in navigating to the promotions.");
            logger.info("Validated navigation to home page successfully!");
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
     * Tests selecting the promotions displayed on the home page and validating the page moved to
     */
    @Test(groups = {"homePageTests"}, dependsOnMethods = "navigateToHomePage")
    public void homePagePromotionsTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(homePageActions.navigateToPromotions(), true, "Something went wrong in navigating to the promotions.");
            logger.info("Validated navigation to promotions page successfully!");
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
     * Tests the DOTD timer displayed on the home page and navigating to the DOTD/FOZ page
     */
    @Test(groups = {"homePageTests"}, dependsOnMethods = "navigateToHomePage")
    public void homePageDealsOfTheDayTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(homePageActions.navigateToDotd(), true, "Something went wrong in navigating to the deals of the day.");
            logger.info("Validated navigation to deals of the day page successfully!");
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
     * Tests navigating to the FOZ page and displayed verticals on the FOZ page
     */
    @Test(groups = {"homePageTests"}, dependsOnMethods = "navigateToHomePage")
    public void homePageOfferZoneTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(homePageActions.navigateToOfferZone(), true, "Something went wrong in navigating to the offer zone.");
            logger.info("Validated navigation to offer zone page successfully!");
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
     * Tests the announcements on the home page displayed other than the promotions
     */
    @Test(groups = {"homePageTests"}, dependsOnMethods = "navigateToHomePage")
    public void homePageAnnouncementsTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(homePageActions.navigateToAnnouncements(), true, "Something went wrong in navigating to the announcements.");
            logger.info("Validated navigation to announcements page successfully!");
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
     * Tests selecting one of the OMU products displayed on the home page
     * and viewing the product/browse page for it.
     */
    @Test(groups = {"homePageTests"}, dependsOnMethods = "navigateToHomePage")
    public void homePageOMUTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(homePageActions.verifyHomePageProducts(), true, "Something went wrong in verifying the PMU products on homepage.");
            logger.info("Validated PMU products on home page successfully!");
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
     * Tests navigating to the legal docs option listed on the 3-dot menu
     */
    @Test(groups = {"homePageTests"}, dependsOnMethods = "navigateToHomePage")
    public void homePageLegalDocTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(homePageActions.navigateToLegalPage(), true, "Something wrong in navigating to legal page.");
            logger.info("Validated navigation to legal page successfully.");
            assertTrue(homePageActions.navigateBackHome());
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
     * Test navigating to the help center option listed on 3-dot menu
     */
    @Test(groups = {"homePageTests"}, dependsOnMethods = "navigateToHomePage")
    public void homePageHelpCentreTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(homePageActions.navigateToHelpCentre(), true, "Something wrong in navigating to help and centre page.");
            logger.info("Validated navigation to help centre page successfully.");
            assertTrue(homePageActions.navigateBackHome());
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
     * Tests the 'television' category displayed in the landing page and navigating to the CLP for that
     */
    @Test(groups = {"homePageTests"}, dependsOnMethods = "navigateToHomePage")
    public void landingPageTests() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(homePageActions.navigateToCLP("Televisions"), true, "Something went wrong while navigating to the CLP for Televisions");
            logger.info("Verified navigating to the CLP successfully!");
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
