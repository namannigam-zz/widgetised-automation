package test.android;

import mobile.util.Common;
import mobile.util.DataProviders;
import org.openqa.selenium.TimeoutException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by naman.nigam on 01/03/16.
 */
public class CartTest extends BaseTest {

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
     * Tests the navigation to cart from home page using the toolbar icon
     * @param credentialsHM credentials for user log in
     * @param cartStrings to search for a category if required
     */
    @Test(groups = {"cartTests"}, dataProvider = "cartTests", dataProviderClass = DataProviders.class)
    public void navigateToCartTest(HashMap credentialsHM, String[] cartStrings) {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.loginFromMenu(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()));
            assertEquals(homePageActions.navigateToCartPage(), true, "Something went wrong in navigating to the cart page.");
            logger.info("Navigated to the cart page successfully!");
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
     * Tests removing all the products added to cart removing them one by one
     * and validating the cart is empty
     */
    @Test(groups = {"cartTests"}, dependsOnMethods = "navigateToCartTest")
    public void removeAllProductFromCartTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            int cartCount = homePageActions.getCartCountOnHomePage();
            assertEquals(cartPageActions.removeAllItemsFromCart(cartCount), true, "Something went wrong while removing all the items from the cart.");
            logger.info("Deleted all the items from cart successfully!");
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
     * Tests the cart count displayed on the home page and the number of items listed on the cart page
     */
    @Test(groups = {"cartTests"}, dependsOnMethods = "addToCartTest")
    public void validateCartCountTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            int cartPageCartCount = cartPageActions.getCartCountOnCartPage();
            assertTrue(homePageActions.navigateBackHome());
            int homePageCartCount = homePageActions.getCartCountOnHomePage();
            assertEquals(homePageCartCount == cartPageCartCount, true, "Something went wrong in counting the notifications.");
            logger.info("Cart count on cartPageActions and homePageActions validated successfully!");
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
     * Tests the addToCart option on the product page
     * searching a product, selecting one and adding it to cart from product page
     */
    @Test(groups = {"cartTests"}, dependsOnMethods = "navigateToCartTest")
    //TODO : split the test carrying the list of data (return the list of products added to cart from this method and pass it to another method to validate)
    public void addToCartTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            List<String> productAddedToCart = new ArrayList<>();
            int expectedCartProducts = homePageActions.getCartCountOnHomePage();
            assertTrue(homePageActions.navigateToSearchPage());
            assertTrue(searchPageActions.searchFromAutoSuggestList("mobiles"));
            assertTrue(browsePageActions.selectAnyItemFromSearchedList());
            String currProdName = productPageActions.getProductName();
            assertEquals(productPageActions.productAddToCart(), true, "Something went wrong while adding the product to the cart.");
            logger.info("Successfully added a product to cart!");
            expectedCartProducts++;
            productAddedToCart.add(currProdName);
            assertTrue(homePageActions.navigateBackHome());
            assertEquals(homePageActions.getCartCountOnHomePage() == expectedCartProducts, true, "All the elements supposedly not added to the cart.");
            logger.info("Added all the specified products to cart successfully!");
            assertTrue(homePageActions.navigateToCartPage());
            assertEquals(cartPageActions.validateCartProducts(productAddedToCart), true, "Something went wrong in validating products added to the cart.");
            logger.info("Successfully verified the products specifications for the ones added to the cart.");
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
     * Tests moving to checkout from the Cart page using the checkout button
     */
    @Test(groups = {"cartTests"}, dependsOnMethods = "navigateToCartTest")
    public void cartToCheckoutTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(cartPageActions.checkOutFromCartPage(), true, "Something went wrong in placing the order form the cart page.");
            logger.info("Moved to the checkout page form cart successfully!");
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
