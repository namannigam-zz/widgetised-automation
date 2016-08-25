package test.android;

import mobile.util.Common;
import mobile.util.DataProviders;

import org.openqa.selenium.TimeoutException;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by naman.nigam on 19/02/16
 */
public class SmokeTestsv2 extends BaseTest {
    BrowsePageActions browsePageActions;
    CartPageActions cartPageActions;
    CheckoutPageActions checkoutPageActions;
    FOZPageActions fozPageActions;
    HomePageActions homePageActions;
    LoginPageActions loginPageActions;
    NotificationPageActions notificationPageActions;
    ProductPageActions productPageActions;
    ProfilePageActions profilePageActions;
    SearchPageActions searchPageActions;
    SignUpActions signUpActions;
    PingActions pingActions;
    WishlistPageActions wishlistPageActions;
    ToolbarActions toolbarActions;
    ProductPageV3 productPageV3;
    Common common = new Common();
    int index = 0;

    /***
     * Method to be executed before the Test
     *
     * @throws Exception
     */
    @BeforeTest
    public void preTestMethod() throws Exception {
        browsePageActions = new BrowsePageActions(driver);
        cartPageActions = new CartPageActions(driver);
        checkoutPageActions = new CheckoutPageActions(driver);
        fozPageActions = new FOZPageActions(driver);
        homePageActions = new HomePageActions(driver);
        loginPageActions = new LoginPageActions(driver);
        notificationPageActions = new NotificationPageActions(driver);
        productPageActions = new ProductPageActions(driver);
        profilePageActions = new ProfilePageActions(driver);
        searchPageActions = new SearchPageActions(driver);
        pingActions = new PingActions(driver);
        wishlistPageActions = new WishlistPageActions(driver);
        signUpActions = new SignUpActions(driver);
        toolbarActions = new ToolbarActions(driver);
        productPageV3 = new ProductPageV3(driver);
        logger.info("Before Test Setup Completed!");
    }

    /**
     * Method to clear the DG HashMap before every @Test call
     *
     * @throws Exception
     */
    @BeforeMethod
    public void preTestSetup() throws Exception {
        try {
            logger.info("DgTagMap cleared in @BeforeMethod");
        } catch (Exception e) {
            logger.warn("Exception in @BeforeMethod");
            e.printStackTrace();
        }
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

    /**
     * The method to execute after the Test
     */
    @AfterTest
    public void afterTest() {
        logger.info("The test execution has completed.");
    }

    /**
     * This test checks for signup for the existing users using the path Home Page to Login Page to Signup Page
     * It relies on the error message that appears on signup page
     *
     * @param userCredentials User login credentials
     * @param signupDP        data provides passes existing_user_mobile_number value to the test
     * @author Vishwanath
     */
//    @Test(groups = {"loginTest"}, priority = 1, dataProvider = "signUpTests", dataProviderClass = DataProviders.class, testName = "newAppSignUpTests", description = "")
    public void existingUserSignupNewApp(HashMap userCredentials, HashMap signupDP) {
        try {
            logger.info("---------------------------Running tests to validate the sign up on new app---------------------");
            assertEquals(signUpActions.signup(signupDP.get("existing_user_mobile_number").toString(), ""), SignUpActions.SIGNUP_STATUS.SIGNUP_USER_ALREADY_EXISTS, "Already existing User notification not displayed at SignUp.");
            logger.info("---------------------------newAppSignUpTests concluded successfully.---------------------");
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
     * The test includes navigating to verification page using the forgot password link
     * Also resending verification code triggering timer
     *
     * @param credentialsHM user login credentials
     */
    @Test(groups = {"loginTest"}, dataProvider = "loginTests", dataProviderClass = DataProviders.class, testName = "loginTests", description = "")
    public void forgotPasswordOnLoginTest(HashMap credentialsHM, HashMap loginHM) {
        try {
            logger.info("-------------------------Running tests to validate login features-------------------------");
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
     * The test performs login using local(IND) number
     *
     * @param credentialsHM user login credentials
     */
    @Test(groups = {"loginTest"}, dataProvider = "loginTests", dataProviderClass = DataProviders.class, testName = "loginTests", description = "", dependsOnMethods = "forgotPasswordOnLoginTest")
    public void firstLoginTest(HashMap credentialsHM, HashMap loginHM) {
        try {
            logger.info("-------------------------Running tests to validate login features-------------------------");
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
     * The test performs logout from the profile page
     */
    @Test(groups = {"loginTest"}, testName = "loginTests", description = "", dependsOnMethods = "firstLoginTest")
    public void logoutTest() {
        try {
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
     * The test performs login using international number from the 3-dot menu
     *
     * @param credentialsHM user login credentials
     */
    @Test(groups = {"loginTest"}, priority = 2, dataProvider = "loginTests", dataProviderClass = DataProviders.class, testName = "loginTests", description = "", dependsOnMethods = "logoutTest")
    public void internationalLoginTest(HashMap credentialsHM, HashMap loginHM) {
        try {
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
     * The test performs login using email from the 3-dot menu
     *
     * @param credentialsHM user login credentials
     */
    @Test(groups = {"loginTest"}, priority = 2, dataProvider = "loginTests", dataProviderClass = DataProviders.class, testName = "loginTests", description = "", dependsOnMethods = "logoutTest")
    public void emailLoginTest(HashMap credentialsHM, HashMap loginHM) {
        try {
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
     * The method includes navigating to ping startup
     */
    @Test(groups = {"loginTest"}, testName = "pingTests", description = "")
    public void navigatingToPingTest() {
        try {
            logger.info("-------------------------Running tests to get started with ping features-------------------------");
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
     * The test performs getting started with ping after login from
     *
     * @param pingHM
     */
    @Test(groups = {"loginTest"}, dataProvider = "pingTests", dataProviderClass = DataProviders.class, testName = "pingTests", description = "", dependsOnMethods = "navigatingToPingTest")
    public void gettingStartedWithPingTest(HashMap pingHM) {
        try {
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
     * The test performs skipping cue-tip for ping at browse page
     */
    @Test(groups = {"loginTest"}, testName = "pingTests", description = "", dependsOnMethods = "gettingStartedWithPingTest")
    public void skipPingCuetipOnBrowsePageTest() {
        try {
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
     * The test performs skipping the cue-tip for ping at product page
     */
    @Test(groups = {"loginTest"}, priority = 3, testName = "pingTests", description = "", dependsOnMethods = "skipPingCuetipOnBrowsePageTest")
    public void skipPingCuetipOnProductPageTest() {
        try {
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

    /**
     * This test checks for signup for the existing users using the path Home Page to Login Page to Signup Page
     * It relies on the error message that appears on signup page
     *
     * @param userCredentialsHM User login credentials
     * @param signupDP          data provides passes existing_user_mobile_number value to the test
     * @author Vishwanath
     */
    @Test(groups = {"loginTest"}, priority = 4, dataProvider = "signUpTests", dataProviderClass = DataProviders.class, testName = "signUpTests", description = "")
    public void existingUserSignUpFromHomePageTest(HashMap userCredentialsHM, HashMap signupDP) {
        try {
            logger.info("-------------------------Running tests to test the SignUp for existing user-------------------------");
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(toolbarActions.navigateToLoginPage());
            assertTrue(loginPageActions.navigateToSignup());
            assertEquals(signUpActions.signup(signupDP.get("existing_user_mobile_number").toString(), ""), SignUpActions.SIGNUP_STATUS.SIGNUP_USER_ALREADY_EXISTS, "Observed signup success for existing user");
            assertTrue(loginPageActions.skip());
            logger.info("---------------------------existingUserSignUp tests concluded successfully.---------------------");
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
     * verifying and navigating to promotions
     */
    @Test(groups = {"smokeChecklist"}, testName = "homePageTests", description = "")
    public void homePagePromotionsTest() {
        try {
            assertTrue(homePageActions.navigateBackHome());
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
     * Validates the presence of DealOfTheDayTimer, navigates to DOTD page and verifies the presence of ProductWidgetTitle
     */
    @Test(groups = {"smokeChecklist"}, testName = "homePageTests", description = "", dependsOnMethods = "homePagePromotionsTest")
    public void homePageDealsOfTheDayTest() {
        try {
            assertTrue(homePageActions.navigateBackHome());
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
     * verifying and navigating to the Offer zone
     */
    @Test(groups = {"smokeChecklist"}, testName = "homePageTests", description = "", dependsOnMethods = "homePageDealsOfTheDayTest")
    public void homePageOfferZoneTest() {
        try {
            assertTrue(homePageActions.navigateBackHome());
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
     * verifying and navigating to the announcements
     */
    @Test(groups = {"smokeChecklist"}, testName = "homePageTests", description = "", dependsOnMethods = "homePageOfferZoneTest")
    public void homePageAnnouncementsTest() {
        try {
            assertTrue(homePageActions.navigateBackHome());
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

    @Test(groups = {"smokeChecklist"}, testName = "homePageTests", description = "", dependsOnMethods = "homePageAnnouncementsTest")
    public void homePageOMUTest() {
        try {
            assertTrue(homePageActions.navigateBackHome());
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

    @Test(groups = {"smokeChecklist"}, testName = "homePageTests", description = "", dependsOnMethods = "homePageOMUTest")
    public void homePageLegalDocTest() {
        try {
            assertTrue(homePageActions.navigateBackHome());
            assertEquals(homePageActions.navigateToLegalPage(), true, "Something wrong in navigating to legal page.");
            logger.info("Validated navigation to legal page successfully.");
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
     *
     *
     */
    @Test(groups = {"smokeChecklist"}, priority = 5, testName = "homePageTests", description = "", dependsOnMethods = "homePageLegalDocTest")
    public void homePageHelpCentreTest() {
        try {
            assertTrue(homePageActions.navigateBackHome());
            assertEquals(homePageActions.navigateToHelpCentre(), true, "Something wrong in navigating to help and centre page.");
            logger.info("Validated navigation to help centre page successfully.");
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
     * The tests method performs navigating to the search page
     */
    @Test(groups = {"smokeChecklist"}, testName = "searchPageTests", description = "")
    public void navigateToSearchTest() {
        try {
            loginPageActions.skip();
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
     * Validates the search, by entering different categories, this method lands on the browse page of the respective category
     *
     * @param searchString
     */
    @Test(groups = {"smokeChecklist"}, testName = "searchPageTests", dataProvider = "searchTests", dataProviderClass = DataProviders.class, description = "", dependsOnMethods = "navigateToSearchTest")
    public void searchProductTest(String[] searchString) {
        try {
            assertEquals(searchPageActions.search(searchString[0]), true, "Something went wrong in searching " + Arrays.toString(searchString));
            logger.info("Search for " + Arrays.toString(searchString) + " was successful!");
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

    @Test(groups = {"smokeChecklist"}, priority = 6, testName = "searchPageTests", description = "", dependsOnMethods = "searchProductTest")
    public void showGuidesTest() {
        boolean showGuides = false;
        try {
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

    @Test(groups = {"smokeChecklist"}, priority = 6, testName = "searchPageTests", description = "", dependsOnMethods = "searchProductTest")
    public void removingCustomElementTest() {
        try {
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


    @Test(groups = {"smokeChecklist"}, priority = 6, testName = "searchPageTests", description = "", dependsOnMethods = "searchProductTest")
    public void clearSearchTagTest() {
        try {
            assertEquals(searchPageActions.clearSearchTag(), true, "Something went wrong in clearing search tags.");
            logger.info("Clearing search tag validated successfully!");
            logger.info("-------------------------searchTests concluded successfully.--------------------");
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


    @Test(groups = {"smokeChecklist"}, priority = 6, testName = "searchPageTests", description = "", dependsOnMethods = "searchProductTest")
    public void clearingSearchHistoryTest() {
        try {
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

    //    @Test(groups = {"smokeChecklist"}, priority = 6, description = "", dependsOnMethods = "clearingSearchHistoryTest")
    public void subcategorySearchTest() {
        try {
            //TODO : get API data for suggested substring
            String searchString = "", searchSuggestionString = "";
            assertEquals(searchPageActions.subCategorySearch(searchString, searchSuggestionString), true, "Something went wrong in sub category searching.");
            logger.info("Sub Category Search validated successfully.");
            logger.info("-------------------------searchTests concluded successfully.--------------------");
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

    @Test(groups = {"smokeChecklist"}, priority = 7, testName = "visualSearchPageTests", description = "", dependsOnMethods = "navigateToSearchTest")
    public void visualSearchTests() {
        try {
            logger.info("-------------------------Running test to validate Visual search features-------------------------");
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.navigateToSearchPage());
            //TODO : implement gallery item search
            assertEquals(searchPageActions.navigateToCamera(), true, "Something went wrong in navigating to image search.");
            logger.info("Navigating to camera for image search validated successfully.");
            logger.info("-------------------------visualSearchTests concluded successfully.--------------------");
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

    @Test(groups = {"smokeChecklist"}, dataProvider = "browsePageTests", dataProviderClass = DataProviders.class, testName = "browsePageTests", description = "")
    public void navigateToBrowsePageTest(HashMap userCredentialsHM, String[] browseStrings) {
        try {
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.loginFromMenu(userCredentialsHM.get("email").toString(), userCredentialsHM.get("password").toString()));
            assertTrue(homePageActions.navigateToSearchPage());
            assertEquals(searchPageActions.searchFromAutoSuggestList("blue jeans"), true, "Something went wrong in selecting the element from auto suggest list.");
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

    @Test(groups = {"smokeChecklist"}, testName = "browsePageTests", description = "", dependsOnMethods = "navigateToBrowsePageTest")
    public void validatePriceOnBrowsePageTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "browsePageTests", description = "", dependsOnMethods = "navigateToBrowsePageTest")
    public void shareFromBrowsePageUsingChatTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "browsePageTests", description = "", dependsOnMethods = "navigateToBrowsePageTest")
    public void similarSearchesTest() {
        try {
            //TODO : get the flag for a product if its a Lifestyle product or not
            boolean lifestyle = false;
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

    @Test(groups = {"smokeChecklist"}, testName = "browsePageTests", description = "", dependsOnMethods = "navigateToBrowsePageTest")
    public void categoryFilterTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "browsePageTests", description = "", dependsOnMethods = "navigateToBrowsePageTest")
    public void categorySortTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, priority = 8, testName = "browsePageTests", description = "", dependsOnMethods = "navigateToBrowsePageTest")
    public void toggleViewTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "adsTests", description = "", dependsOnMethods = "navigateToBrowsePageTest")
    public void browsePageAdsTests() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "adsTests", description = "", dependsOnMethods = "navigateToBrowsePageTest")
    public void browsePageAddToWishlistTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, priority = 10, testName = "landingPageTests", description = "")
    public void landingPageTests() {
        try {
            logger.info("-------------------------Running test to validate the clp features-------------------------");
            assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            assertEquals(homePageActions.navigateToCLP("Televisions"), true, "Something went wrong while navigating to the CLP for Televisions");
            logger.info("Verified navigating to the CLP successfully!");
            logger.info("-------------------------CLPTests concluded successfully.--------------------");
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

    @Test(groups = {"smokeChecklist"}, priority = 12, testName = "wishlistTests", description = "")
    public void navigatingToWishlistTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "wishlistTests", description = "", dependsOnMethods = "navigatingToWishlistTest")
    public void clearingWishlistForNotLoggedInUserTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, dataProvider = "wishlistTests", dataProviderClass = DataProviders.class, testName = "wishlistTests", description = "", dependsOnMethods = "navigatingToWishlistTest")
    public void clearingWishlistForLoggedInUserTest(HashMap credentialsHM, String[] wishlistStrings) {
        try {
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.loginFromMenu(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()));
            assertTrue(homePageActions.navigateToWishlist());
            assertEquals(wishlistPageActions.wishlistdeleteAllitems(), true, "Something went wrong in deleting all the items from wishlist.");
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

    @Test(groups = {"smokeChecklist"}, dataProvider = "wishlistTests", dataProviderClass = DataProviders.class, testName = "wishlistTests", description = "")
    public void browsePageToWishlistTest(HashMap credentialsHM, String[] wishlistStrings) {
        try {
            assertTrue(homePageActions.navigateBackHome());
            for (index = 0; index < wishlistStrings.length; index++) {
                assertTrue(homePageActions.navigateToSearchPage());
                assertTrue(searchPageActions.search(wishlistStrings[index]));
                assertEquals(browsePageActions.addToWishlistFromBrowsePage(), true, "Something went wrong in adding a product for " + wishlistStrings[index] + " to wishlist.");
                logger.info("Successfully added product to wishlist for " + wishlistStrings[index] + "!");
                assertTrue(homePageActions.navigateBackHome());
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

    @Test(groups = {"smokeChecklist"}, testName = "wishlistTests", description = "")
    public void selectProductOnWishlistTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "wishlistTests", description = "")
    public void clearWishlistOnLogoutTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, dataProvider = "wishlistTests", dataProviderClass = DataProviders.class, testName = "wishlistTests", description = "")
    public void mergeWishlistTest(HashMap credentialsHM, String[] wishlistStrings) {
        try {
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

    @Test(groups = {"smokeChecklist"}, priority = 13, dataProvider = "cartTests", dataProviderClass = DataProviders.class, testName = "cartTests", description = "")
    public void navigateToCartTest(HashMap credentialsHM, String[] cartStrings) {
        try {
            logger.info("-------------------------Running test to validate Cart features-------------------------");
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

    @Test(groups = {"smokeChecklist"}, testName = "cartTests", description = "", dependsOnMethods = "navigateToCartTest")
    public void removeAllProductFromCartTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "cartTests", description = "", dependsOnMethods = "navigateToCartTest")
    public void validateCartCountTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "cartTests", description = "")
    //TODO : split the test carrying the list of data (return the list of products added to cart from this method and pass it to another method to validate)
    public void addToCartTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "cartTests", description = "")
    public void cartToCheckoutTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, priority = 14, testName = "checkoutTests", description = "")
    public void buyNowTest() {
        try {
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.navigateToSearchPage());
            assertTrue(searchPageActions.search("mobiles"));
            assertTrue(browsePageActions.selectAnyItemFromSearchedList());
            assertEquals(productPageActions.productBuyNow(), true, "Something went wrong in moving to buy now field.");
            logger.info("Proceeded with Buy Now option from the product page successfully!");
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

    @Test(groups = {"smokeChecklist"}, dataProvider = "checkoutTests", dataProviderClass = DataProviders.class, testName = "checkoutTests", description = "", dependsOnMethods = "buyNowTest")
    public void loginFromCheckoutTest(HashMap credentialsHM, String[] checkoutStrings) {
        try {
            assertEquals(checkoutPageActions.loginFromCheckoutPage(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()), true, "Something went wrong while signing in from checkout page.");
            logger.info("Logged in from checkout page was successful.");
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

    @Test(groups = {"smokeChecklist"}, testName = "checkoutTests", description = "", dependsOnMethods = "loginFromCheckoutTest")
    public void addAddressOnCheckout() {
        try {
            assertEquals(checkoutPageActions.addressOnCheckout(), true, "Something went wrong during adding address during checkout on purchase.");
            logger.info("Address added on checkout page successfully.");
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

    @Test(groups = {"smokeChecklist"}, testName = "checkoutTests", description = "", dependsOnMethods = "addAddressOnCheckout")
    public void cashOnDeliveryTest() {
        try {
            assertEquals(checkoutPageActions.continueToPay_COD(), true, "Something went wrong while proceeding to COD.");
            logger.info("Continued to Pay COD on checkout successfully.");
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

    @Test(groups = {"smokeChecklist"}, priority = 15, dataProvider = "myAccountsTests", dataProviderClass = DataProviders.class, testName = "myAccountTests", description = "")
    public void navigateToProfileTest(HashMap myAccountsHM) {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "myAccountTests", description = "", dependsOnMethods = "navigateToProfileTest")
    public void viewAllOrderTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "myAccountTests", description = "", dependsOnMethods = "viewAllOrderTest")
    public void viewProfileAddressesTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "myAccountTests", description = "", dependsOnMethods = "viewProfileAddressesTest")
    public void addAddressToProfileTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "myAccountTests", description = "", dependsOnMethods = "addAddressToProfileTest")
    public void viewProfileWalletTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "myAccountTests", description = "", dependsOnMethods = "viewProfileWalletTest")
    public void viewProfileSubscriptionTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "myAccountTests", description = "", dependsOnMethods = "viewProfileSubscriptionTest")
    public void viewProfileSettingsTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, priority = 16, dataProvider = "myOrdersTests", dataProviderClass = DataProviders.class, testName = "myOrdersTest", description = "")
    public void myOrdersTest(HashMap myOrdersHM) {
        try {
            logger.info("-------------------------Running test to validate My Orders details-------------------------");
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.loginFromMenu(myOrdersHM.get("email").toString(), myOrdersHM.get("password").toString()));
            assertEquals(homePageActions.navigateToMyOrdersPage(), true, "Something went wrong in navigating to the My Orders Page.");
            assertTrue(homePageActions.navigateBackHome());
            assertTrue(homePageActions.logoutFromMenu());
            logger.info("-------------------------myOrdersTests concluded successfully.--------------------");
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

    @Test(groups = {"smokeChecklist"}, priority = 17, testName = "notificationPageTests", description = "")
    public void navigateToNotificationPageTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, dataProvider = "notificationTests", dataProviderClass = DataProviders.class, testName = "notificationPageTests", description = "", dependsOnMethods = "navigateToNotificationPageTest")
    public void loginFromNotificationPageTest(HashMap notificationHM) {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "notificationPageTests", description = "", dependsOnMethods = "loginFromNotificationPageTest")
    public void shareNotificationTest() {
        try {
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

    @Test(groups = {"smokeChecklist"}, testName = "notificationPageTests", description = "", dependsOnMethods = "loginFromNotificationPageTest")
    public void deleteNotificationTest() {
        try {
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

    /**
     * Navigating to product page from home page
     */
    @Test(groups = {"smokeChecklist"}, priority = 1, testName = "productPageTests", dataProvider = "productPageV3Tests", dataProviderClass = DataProviders.class, description = "")
    public void navigateToProductPageV3(HashMap login, String[] category, String[] pincode) {
        try {
            homePageActions.navigateBackHome();
            loginPageActions.checkLoginStatus(login.get("email").toString(), login.get("password").toString());
            homePageActions.navigateToSearchPage();
            searchPageActions.searchFromAutoSuggestList(category[0]);
            browsePageActions.selectAnyItemFromSearchedList();
            logger.info("Moved to the product page for " + Arrays.toString(category));
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
     * Validating the product name
     */
    @Test(groups = {"smokeChecklist"}, priority = 2, testName = "productPageTests", description = "", dependsOnMethods = "navigateToProductPageV3")
    public void productPageName() {
        try {
            assertNotNull(productPageV3.getProductName(), "Validation of Title on the Product has failed");
            logger.info("Product Name validated successfully!");

        } catch (NullPointerException | TimeoutException testException) {
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
     * Validating the presence of Ping on the page
     */
    @Test(groups = {"smokeChecklist"}, priority = 3, testName = "productPageTests", description = "", dependsOnMethods = "productPageName")
    public void productPagePing() {
        try {
            assertTrue(productPageV3.findPingButton(), "Failed to launch ping");
        } catch (NullPointerException | TimeoutException testException) {
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
     * Swipe right/left to navigate to the next product
     */
    @Test(groups = {"smokeChecklist"}, priority = 4, testName = "productPageTests", description = "", dependsOnMethods = "productPagePing")
    public void productPageSwipeCheck() {
        try {
            String currentProductName = productPageV3.getProductName();
            assertTrue(productPageV3.swipeNextProduct(), "Something went wrong while swiping to the next product.");
            assertTrue(productPageV3.swipePreviousProduct(), "Something went wrong while swiping to the previous product.");
            logger.info("Verified navigating to next and previous product successfully!");

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
     * Checking the presence of image layout and not validating the image correctness
     */
    @Test(groups = {"smokeChecklist"}, priority = 5, testName = "productPageTests", description = "", dependsOnMethods = "productPageSwipeCheck")
    public void productPageImage() {
        try {
            assertEquals(productPageV3.isProductImageAvailable(), true, "Validation of Product Page Main Image on the Product has failed");
            logger.info("Product image validated successfully!");
        } catch (NullPointerException | TimeoutException testException) {
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
     * Product Page Serviceability
     */
    @Test(groups = {"smokeChecklist"}, priority = 6, testName = "productPageTests", dataProvider = "productPageV3Tests", dataProviderClass = DataProviders.class, description = "", dependsOnMethods = "productPageImage")
    public void productPageServiceability(HashMap login, String[] swatch, String[] pincode) {
        try {
            ProductPageV3.ProductPageParameters pincodeCheckStatus = productPageV3.checkAvailability(pincode[0]);
            assertNotEquals(pincodeCheckStatus, ProductPageV3.ProductPageParameters.PINCODE_LAYOUT_NOT_FOUND, "Pincode layout was not found");
            assertNotEquals(pincodeCheckStatus, ProductPageV3.ProductPageParameters.INVALID_PINCODE, "Invalid Pincode");
            assertEquals(pincodeCheckStatus, ProductPageV3.ProductPageParameters.SERVICEABLE, "Product is serviceable");

            //pincodeCheckStatus = productPageV3.checkAvailability(serviceable);
            assertNotEquals(pincodeCheckStatus, ProductPageV3.ProductPageParameters.NOT_SERVICEABLE, "Product is not serviceable");
            pincodeCheckStatus = productPageV3.checkAvailability(pincode[1]);
            assertEquals(pincodeCheckStatus, ProductPageV3.ProductPageParameters.NOT_SERVICEABLE, "Product is not serviceable");

            logger.info("Similar Searches for validated successfully!");
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
     * Validating the presence of Specifications section under Details tab
     */
    @Test(groups = {"smokeChecklist"}, priority = 7, testName = "productPageTests", description = "", dependsOnMethods = "productPageServiceability")
    public void productPageSpecifications() {
        try {
            ProductPageV3.ProductPageParameters specificationValidationStatus = productPageV3.validateSpecifications();
            assertNotEquals(specificationValidationStatus, ProductPageV3.ProductPageParameters.SPECIFICATION_DETAILS_NOT_AVAILABLE, "Unable to locate specification details on the product page");
            assertNotEquals(specificationValidationStatus, ProductPageV3.ProductPageParameters.SPECIFICATION_DETAILS_DATA_ERROR, "Specification details is empty on product page");
            assertNotEquals(specificationValidationStatus, ProductPageV3.ProductPageParameters.FAILURE, "Error in specification data validation");
            assertEquals(specificationValidationStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Specification details validated successfully!");
            logger.info("Specification data for validated successfully!");
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
     * Validating the presence of recommandation section on product page
     */
    @Test(groups = {"smokeChecklist"}, priority = 8, testName = "productPageTests", description = "", dependsOnMethods = "productPageSpecifications")
    public void productPageRecommandations() {
        try {
            ProductPageV3.ProductPageParameters recoStatus = productPageV3.validateRecommendations();
            assertNotEquals(recoStatus, ProductPageV3.ProductPageParameters.RECO_LAYOUT_NOT_AVAILABLE, "Recommendations not shown on the product page");
            assertNotEquals(recoStatus, ProductPageV3.ProductPageParameters.RECO_PRODUCT_IMAGE_NOT_DISPLAYED, "Product images not displayed in recommendations");
            assertNotEquals(recoStatus, ProductPageV3.ProductPageParameters.RECO_PRODUCT_TITLE_NOT_DISPLAYED, "Product titles not displayed in recommendations");
            assertNotEquals(recoStatus, ProductPageV3.ProductPageParameters.RECO_PRODUCT_PRICE_NOT_DISPLAYED, "Product prices not displayed in recommendations");
            assertEquals(recoStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Recommendations details validated successfully!");
            logger.info("Recommendations data for validated successfully!");

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
     * Validating the presence of similar section on product page
     */
    @Test(groups = {"smokeChecklist"}, priority = 9, testName = "productPageTests", description = "", dependsOnMethods = "productPageRecommandations")
    public void productPageSimilarProducts() {
        try {
            ProductPageV3.ProductPageParameters similarProductStatus = productPageV3.validateSimilarProductSearches();
            assertNotEquals(similarProductStatus, ProductPageV3.ProductPageParameters.SIMILAR_PRODUCT_LAYOUT_NOT_AVAILABLE, "Similar Products not shown on the product page");
            assertNotEquals(similarProductStatus, ProductPageV3.ProductPageParameters.SIMILAR_PRODUCT_IMAGE_NOT_DISPLAYED, "Similar Products images not displayed in recommendations");
            assertNotEquals(similarProductStatus, ProductPageV3.ProductPageParameters.SIMILAR_PRODUCT_TITLE_NOT_DISPLAYED, "Similar Products titles not displayed in recommendations");
            assertNotEquals(similarProductStatus, ProductPageV3.ProductPageParameters.SIMILAR_PRODUCT_PRICE_NOT_DISPLAYED, "Similar Products prices not displayed in recommendations");
            assertEquals(similarProductStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Similar Products details validated successfully!");
            logger.info("Similar Products data for validated successfully!");
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
     * Validating the discount and price of a product
     */
    @Test(groups = {"smokeChecklist"}, priority = 10, testName = "productPageTests", dataProvider = "productPageV3Tests", dataProviderClass = DataProviders.class, description = "", dependsOnMethods = "productPageSimilarProducts")
    public void productPagePrice(HashMap login, String[] offer, String[] pincode) {
        try {
            homePageActions.navigateBackHome();
            homePageActions.navigateToSearchPage();
            searchPageActions.search(offer[4]);
            browsePageActions.selectAnyItemFromSearchedList();
            assertEquals(productPageV3.getDiscount(), true, "Observed discount % calculation error on product page");
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
     * Validation of offers on the product page and offer callouts page
     */
    @Test(groups = {"smokeChecklist"}, priority = 11, testName = "productPageTests", description = "", dependsOnMethods = "productPagePrice")
    public void productPageOffer() {
        try {
            if (productPageV3.isOfferAvailable() == ProductPageV3.ProductPageParameters.OFFERS_AVAILABLE) {
                ProductPageV3.ProductPageParameters offerTestResult = productPageV3.validateOffers();
                assertNotEquals(offerTestResult, ProductPageV3.ProductPageParameters.OFFERS_NOT_AVAILABLE, "Offers section was not found");
                assertNotEquals(offerTestResult, ProductPageV3.ProductPageParameters.SELLER_SERVICES_DETAILS_NOT_FOUND, "Seller Services information wasn't displayed on clicking 'Offers'");
                assertNotEquals(offerTestResult, ProductPageV3.ProductPageParameters.OFFERS_NOT_DISPLAYED, "Offers were not displayed on the Seller Services page");
                assertNotEquals(offerTestResult, ProductPageV3.ProductPageParameters.OFFERS_COUNT_MISMATCH, "Mismatch of offer count on offers page and offers icon");
                assertEquals(offerTestResult, ProductPageV3.ProductPageParameters.SUCCESS, "Offers section validated");
                productPageV3.validateOffers();
            } else {
                logger.warn("Offers were not available on the product (" + productPageV3.getProductName() + ") for Mobiles");
            }
        } catch (NullPointerException | TimeoutException testException) {
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

    //TODO : Will uncomment once the test is fixed.

    /**
     * Product Page Seller Details
     */
//    @Test(groups = {"smokeChecklist"}, priority = 12, testName = "productPageTests", description = "", dependsOnMethods = "productPageOffer")
    public void productPageSellerDetails() {
        try {
            ProductPageV3.ProductPageParameters sellertStatus = productPageV3.validateSeller();
            assertNotEquals(sellertStatus, ProductPageV3.ProductPageParameters.SELLER_LAYOUT_NOT_FOUND, "Seller layout not shown on the product page");
            assertNotEquals(sellertStatus, ProductPageV3.ProductPageParameters.SELLER_NAME_NOT_FOUND, "Seller name is not displayed in the seller layout");
            assertNotEquals(sellertStatus, ProductPageV3.ProductPageParameters.SELLER_RATING_NOT_FOUND, "Seller rating is not displayed in the seller layout");
            assertNotEquals(sellertStatus, ProductPageV3.ProductPageParameters.SELLER_PRODUCT_PRICE_NOT_FOUND, "Seller product price is not displayed in the seller layout");
            assertNotEquals(sellertStatus, ProductPageV3.ProductPageParameters.SELLER_PRODUCT_DELIVERY_INFO_NOT_FOUND, "Delivery details not displayed in seller info");
            assertEquals(sellertStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Seller details validated successfully!");
            logger.info("Seller details for validated successfully!");
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
     * Displays all the images of a single product using the swipe
     */
    @Test(groups = {"smokeChecklist"}, priority = 13, testName = "productPageTests", dataProvider = "productPageV3Tests", dataProviderClass = DataProviders.class, description = "", dependsOnMethods = "productPageOffer")
    public void productPageImageSlideShow(HashMap login, String[] largeAppliance, String[] pincode) {
        try {
            homePageActions.navigateBackHome();
            homePageActions.navigateToSearchPage();
            searchPageActions.search(largeAppliance[0]);
            browsePageActions.selectAnyItemFromSearchedList();
            ProductPageV3.ProductPageParameters slideShowStatus = productPageV3.slideshowProductImage();

            if (slideShowStatus.equals(ProductPageV3.ProductPageParameters.PRODUCT_IMAGES_NOT_AVAILABLE)) {
                assertTrue(productPageV3.isProductImageAvailable(), "Product image available. Multiple images are not avilable for this product");
                logger.warn("Product does not have multiple images to validate slide show operation");
            } else {
                assertNotEquals(slideShowStatus, ProductPageV3.ProductPageParameters.SLIDER_NOT_FOUND, "Slider was not found on the product page");
                assertEquals(slideShowStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Slide show validated successfully!");
                logger.info("Slide show for validated successfully!");
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
     * Validation of warranty under the Description tab on product page
     */
    @Test(groups = {"smokeChecklist"}, priority = 14, testName = "productPageTests", description = "", dependsOnMethods = "productPageImageSlideShow")
    public void productPageWarranty() {
        try {
            ProductPageV3.ProductPageParameters warrantyValidationStatus = productPageV3.validateWarranty();
            assertNotEquals(warrantyValidationStatus, ProductPageV3.ProductPageParameters.WARRANTY_DETAILS_NOT_AVAILABLE, "Unable to locate warranty details on the product page");
            assertNotEquals(warrantyValidationStatus, ProductPageV3.ProductPageParameters.WARRANTY_DETAILS_DATA_ERROR, "Warranty details is empty on product page");
            assertNotEquals(warrantyValidationStatus, ProductPageV3.ProductPageParameters.FAILURE, "Error in warranty data validation");
            assertEquals(warrantyValidationStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Warranty details validated successfully!");
            logger.info("Warranty for validated successfully!");
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
     * Product Page Rating and Review validation on product page and Reviews tab
     */
    @Test(groups = {"smokeChecklist"}, priority = 15, testName = "productPageTests", description = "", dependsOnMethods = "productPageWarranty")
    public void productPageRatingReview() {
        try {
            int numberOfReviewsInRatingBar = productPageV3.getReviewCountInRatingBar();
            int totalNumberOfReviews = productPageV3.getTotalNumberOfReviews();
            float rating = productPageV3.getProductRating();
            assertNotEquals(numberOfReviewsInRatingBar, -1, "Error in finding review count on the rating bar");
            assertNotEquals(totalNumberOfReviews, -1, "Error in finding review count on review tab");
            assertNotEquals(rating, -1, "Error in getting the rating on product page");
            assertEquals(numberOfReviewsInRatingBar, totalNumberOfReviews, "Mismatch in number of reviews on rating bar and reviews tab");

            assertTrue(productPageActions.validateRatingOnProductPage(true, true), "Failed to return the rating n review");
            logger.info("Ratings and reviews for validated successfully!");
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

    @Test(groups = {"smokeChecklist"}, priority = 17, testName = "productPageTests", dataProvider = "productPageV3Tests", dataProviderClass = DataProviders.class, description = "", dependsOnMethods = "productPageRatingReview")
    /**
     * Product page swatches, selects every option in the swatch page
     */
    public void productPageSwatch(HashMap login, String[] swatch, String[] pincode) {
        try {
            homePageActions.navigateBackHome();
            homePageActions.navigateToSearchPage();
            searchPageActions.search(swatch[0]);
            browsePageActions.selectAnyItemFromSearchedList();
            assertEquals(productPageV3.validateSwatchesPpv3(), true, "Something went wrong in the swatch page");
            logger.info("Successfully validated the swatch page");
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

    @Test(groups = {"smokeChecklist"}, priority = 17, testName = "productPageTests", description = "", dependsOnMethods = "productPageSwatch")
    /**
     * Sharing a product from product page
     */
    public void productPageShare() {
        try {
            assertEquals(productPageActions.shareProduct(), true, "Something went wrong in sharing  from product page.");
            logger.info("Sharing for validated successfully!");

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
}
