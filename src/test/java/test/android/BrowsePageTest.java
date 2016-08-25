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
public class BrowsePageTest extends BaseTest {

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
     * Tests the navigation from home page to search and to browse page of a vertical
     *
     * @param userCredentialsHM credentials required to log in the user
     * @param browseStrings     category/verticals to search
     */
    @Test(groups = {"browseTests"}, dataProvider = "browsePageTests", dataProviderClass = DataProviders.class)
    public void navigateToBrowsePageTest(HashMap userCredentialsHM, String[] browseStrings) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.loginFromMenu(userCredentialsHM.get("email").toString(), userCredentialsHM.get("password").toString()));
            assertTrue(homePageActions.navigateToSearchPage());
            assertEquals(searchPageActions.searchFromAutoSuggestList("jeans"), true, "Something went wrong in selecting the element from auto suggest list.");
            logger.info("Search using auto suggest list was successful!");
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + "for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests the product title and prices on the browse pages
     * - should not be empty
     * - every product has a title and price attached
     */
    @Test(groups = {"browseTests"}, dependsOnMethods = "navigateToBrowsePageTest")
    public void validatePriceOnBrowsePageTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(browsePageActions.validatePriceTitle(), true, "Something went wrong in validating the product prices and titles.");
            logger.info("Verified the product titles and prices of the product displayed successfully!");
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + "for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests the sharing of a product to a friend listed on ping contacts
     * - requires sign in and ping activated
     */
//    @Test(groups = {"browseTests"}, dependsOnMethods = "navigateToBrowsePageTest") //TODO : depends on success of gettingStarted with Ping test
    public void shareFromBrowsePageUsingChatTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(browsePageActions.shareUsingChatOnly(), true, "Something went wrong while sharing using chat icon from the browse page.");
            logger.info("Successfully verified sharing using chat!");
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + "for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests that the similar searches page is displayed on clicking the icon on browse page
     * - only for lifestyle products
     */
    @Test(groups = {"browseTests"}, dependsOnMethods = {"navigateToBrowsePageTest",})
    public void similarSearchesTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            //TODO : get the flag for a product if its a Lifestyle product or not
            boolean lifestyle = true;
            if (lifestyle) {
                assertEquals(browsePageActions.similarSearches(), true, "Something went wrong while verifying the ratings and reviews on the browse page.");
                logger.info("Successfully validated similar searches !");
            }
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + "for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests applying the price and availability filters and
     * the list of products displayed past that on the browse page.
     */
//    @Test(groups = {"browseTests"}, testName = "browsePageTests", dependsOnMethods = "navigateToBrowsePageTest") //TODO : enable once fixed on GeoBrowse
    public void categoryFilterTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(browsePageActions.selectCategoryFilters(), true, "Something went wrong in selecting category filters and validating.");
            logger.info("Successfully applied price and availability sub category filters!");
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + "for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests applying a sort filter of Price and
     * validating the list of products displayed after that on the browse page.
     */
//    @Test(groups = {"browseTests"}, testName = "browsePageTests", dependsOnMethods = "navigateToBrowsePageTest") //TODO : enable once fixed on GeoBrowse
    public void categorySortTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(browsePageActions.selectCategorySort(), true, "Something went wrong in selecting category sort and validating.");
            logger.info("Successfully applied category sort!");
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {
            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + "for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
            assert (false);
        }
    }

    /**
     * Tests the toggle button click and the view displayed after that on the browse page.
     * - list to grid
     * - grid to thumbnail
     * - thumbnail to list
     */
    @Test(groups = {"browseTests"}, dependsOnMethods = "navigateToBrowsePageTest")
    public void toggleViewTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(browsePageActions.toggleView(), true, "Something went wrong in toggling view.");
            logger.info("Toggling views was successful!");
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
     * Tests the ads displayed on the browse page verifying the ads tag on the product displayed
     */
//    @Test(groups = {"browseTests"}, dependsOnMethods = "navigateToBrowsePageTest") //TODO : shall be run against the defined locations for ads
    public void browsePageAdsTests() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(browsePageActions.verifyAds(), true, "Something went wrong in verifying the ads on search page.");
            logger.info("Validating ads for browse page  was successful.");
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
     * Tests adding a product from browse page to wish-list using product options
     */
    @Test(groups = {"browseTests"}, dependsOnMethods = "toggleViewTest")
    public void browsePageAddToWishlistTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(browsePageActions.addToWishlistFromBrowsePage(), true, "Something went wrong in adding to wishlist from the browse page.");
            logger.info("Successfully added a product to wishList.");
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
     * - capturing screenshots for test results
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