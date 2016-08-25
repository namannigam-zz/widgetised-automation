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
import java.util.List;

/**
 * Created by naman.nigam on 01/03/16.
 */
public class WishlistTest extends BaseTest {

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
     * Tests navigating to the current wish-list using the 3-dot menu from the home page
     */
    @Test(groups = {"wishlistTest"})
    public void navigatingToWishlistTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertEquals(homePageActions.navigateToWishlist(), true, "Something went wrong in navigating to the wishlist.");
            logger.info("Navigated to the wishlist successfully!");
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
     * Tests clearing all the items in the wish-list and validate and empty wish-list for a logged out user
     */
    @Test(groups = {"wishlistTest"}, testName = "wishlistTests", description = "", dependsOnMethods = "navigatingToWishlistTest")
    public void clearingWishlistForNotLoggedInUserTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.navigateToWishlist());
            assertEquals(wishlistPageActions.wishlistdeleteAllitems(), true, "Something went wrong in deleting all the items from wishlist.");
            logger.info("Cleared the wishlist items already attached with device successfully!");
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
     * Tests clearing all the items in the wish-list and validate and empty wish-list for a logged in user
     *
     * @param credentialsHM   user credentials
     * @param wishlistStrings searching and adding a specific vertical item to wishlist
     */
    @Test(groups = {"wishlistTest"}, dataProvider = "wishlistTests", dataProviderClass = DataProviders.class, testName = "wishlistTests", description = "", dependsOnMethods = "navigatingToWishlistTest")
    public void clearingWishlistForLoggedInUserTest(HashMap credentialsHM, String[] wishlistStrings) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.loginFromMenu(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()));
            assertTrue(homePageActions.navigateToWishlist());
            assertEquals(wishlistPageActions.wishlistdeleteAllitems(), true, "Something went wrong in deleting all the items from wishlist.");
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.logoutFromMenu());
            logger.info("Cleared the wishlist items already attached with the account successfully!");
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
     * Tests adding a product from browse page to wish-list and validate the
     * item is listed on top of the existing wish0list
     *
     * @param credentialsHM   users credentials
     * @param wishlistStrings search a specific vertical to add product to wish-list
     */
    @Test(groups = {"wishlistTest"}, dataProvider = "wishlistTests", dataProviderClass = DataProviders.class, dependsOnMethods = "clearingWishlistForNotLoggedInUserTest")
    public void browsePageToWishlistTest(HashMap credentialsHM, String[] wishlistStrings) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            for (String wishlistString : wishlistStrings) {
                assertTrue(homePageActions.navigateBackHome());
                assertTrue(homePageActions.navigateToSearchPage());
                assertTrue(searchPageActions.search(wishlistString));
                assertEquals(browsePageActions.addToWishlistFromBrowsePage(), true, "Something went wrong in adding a product for " + wishlistString + " to wishlist.");
                logger.info("Successfully added product to wishlist for " + wishlistString + "!");
            }
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
     * Tests selecting products from wishlist and moving to their product page successfully
     */
    @Test(groups = {"wishlistTest"}, dependsOnMethods = "navigatingToWishlistTest")
    public void selectProductOnWishlistTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.navigateToWishlist());
            assertEquals(wishlistPageActions.selectItemMoveToProductPage(), true, "Something went wrong in navigating to the product page from wishlist.");
            logger.info("Successfully navigated to the product page for first product on wishlist page!");
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
     * Tests clearing the wish-list on logging out the existing user
     */
    @Test(groups = {"wishlistTest"}, dependsOnMethods = "browsePageToWishlistTest")
    public void clearWishlistOnLogoutTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertEquals(homePageActions.logoutFromMenu(), true, "Logout from menu failed.");
            assertEquals(homePageActions.navigateToWishlist(), true, "Navigate to wishlist failed.");
            assertEquals((wishlistPageActions.getWishlistItemsCount() == 0), true, "Wishlist items count after logout is not 0.");
            logger.info("Logging out clears the wishlist items added on the account successfully!");
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
     * Tests merging the wish-lists for a logged out user and
     * a logged in user in the same course
     *
     * @param credentialsHM   user credentials to log in
     * @param wishlistStrings search for a product to add to wish-list
     */
    @Test(groups = {"wishlistTest"}, dataProvider = "wishlistTests", dataProviderClass = DataProviders.class, dependsOnMethods = "navigatingToWishlistTest")
    public void mergeWishlistTest(HashMap credentialsHM, String[] wishlistStrings) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.loginFromMenu(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()));
            assertTrue(homePageActions.navigateToWishlist());
            assertTrue(wishlistPageActions.wishlistdeleteAllitems());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.logoutFromMenu());
            assertTrue(homePageActions.navigateToSearchPage());
            assertTrue(searchPageActions.search("mobiles"));
            assertTrue(browsePageActions.addToWishlistFromBrowsePage());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.navigateToWishlist());
            List<String> initialWishListItems = wishlistPageActions.listProductsOnWishlist();
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.loginFromMenu(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()));
            assertTrue(homePageActions.navigateToWishlist());
            List<String> finalWishListItems = wishlistPageActions.listProductsOnWishlist();
            assertEquals(((finalWishListItems.size() == initialWishListItems.size()) && finalWishListItems.containsAll(initialWishListItems)), true, "Products added to the wishlist are not merged after log in.");
            logger.info("Product added to wishlist from device were merged to the account after log in successfully!");
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