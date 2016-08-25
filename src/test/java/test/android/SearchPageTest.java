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
public class SearchPageTest extends BaseTest {

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
     * Tests moving to the search page and its layout from home page
     * on selecting the search bar displayed
     */
    @Test(groups = {"searchTest"}, testName = "searchPageTests", description = "")
    public void navigateToSearchTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertEquals(homePageActions.navigateToSearchPage(), true, "Something went wrong in navigating to search page.");
            logger.info("Successfully navigated to search page!");
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
     * Tests the searching of a product from the search page and items listed against it
     */
    @Test(groups = {"searchTest"}, testName = "searchPageTests", description = "", dependsOnMethods = "navigateToSearchTest")
    public void searchProductTest() {
        String searchStrings = "shoes";
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(searchPageActions.search(searchStrings), true, "Something went wrong in searching " + searchStrings);
            logger.info("Search for " + searchStrings + " was successful!");
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
     * Tests the guided search displayed for the search query triggered
     */
    @Test(groups = {"searchTest"}, priority = 6, testName = "searchPageTests", description = "", dependsOnMethods = "searchProductTest")
    public void showGuidesTest() {
        boolean showGuides = false;
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            //TODO : get the status of guided search
//                showGuides = new SearchAction().validateGuidedSearchKeywords();
            if (showGuides) {
                assertEquals(searchPageActions.guidedSearch(), true, "Something went wrong in searching for guides.");
                logger.info("Guided search for was successfully displayed!");
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
     * Tests removing the custom element from the search bar on the search page to modify the search
     */
//    @Test(groups = {"searchTest"}, dependsOnMethods = "searchProductTest") //TODO : enable the test is custom element removal is to be tested
    public void removingCustomElementTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(searchPageActions.removeCustomSearchElement(), true, "Something went wrong in removing the custom search tags.");
            logger.info("Removed the custom search tag successfully!");
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
     * Tests clearing the items searched for and moving back to blank search page options
     */
    @Test(groups = {"searchTest"}, dependsOnMethods = "searchProductTest")
    public void clearSearchTagTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(searchPageActions.clearSearchTag(), true, "Something went wrong in clearing search tags.");
            logger.info("Clearing search tag validated successfully!");
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
     * Tests clearing the history of the searches made on the device
     */
    @Test(groups = {"searchTest"}, dependsOnMethods = "searchProductTest")
    public void clearingSearchHistoryTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertEquals(searchPageActions.clearSearchHistory(), true, "Something went wrong while trying to clear the search history.");
            logger.info("Clearing search history validated successfully!");
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
     * Tests the subcategory search suggestions listed down for a specifc search string
     */
//    @Test(groups = {"searchTest"}, dependsOnMethods = "navigateToSearchTest")
    public void subcategorySearchTest() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            //TODO : get API data for suggested substring
            String searchString = "", searchSuggestionString = "";
            assertEquals(searchPageActions.subCategorySearch(searchString, searchSuggestionString), true, "Something went wrong in sub category searching.");
            logger.info("Sub Category Search validated successfully.");
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
     * Tests the navigation to the camera from the application for a visual search
     */
    @Test(groups = {"searchTest"}, dependsOnMethods = "navigateToSearchTest")
    public void visualSearchTests() {
        try {
            methodStartConsole(Thread.currentThread().getStackTrace());
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.navigateToSearchPage());
            //TODO : implement gallery item search
            assertEquals(searchPageActions.navigateToCamera(), true, "Something went wrong in navigating to image search.");
            logger.info("Navigating to camera for image search validated successfully.");
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