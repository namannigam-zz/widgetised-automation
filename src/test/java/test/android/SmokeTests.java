package test.android;

import page.BasePage;
import mobile.util.Common;
import mobile.util.DataProviders;
import java.util.*;

import org.openqa.selenium.TimeoutException;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class SmokeTests extends BaseTest {

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
    ProductPageV3 productPageV3;
    Common common = new Common();
    int index = 0;
    SoftAssert softAssert = new SoftAssert();

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
        logger.info("Before Test Setup Completed!");
    }

    @BeforeMethod(alwaysRun = true)
    public void preTestSetup() throws Exception {
        try {
                logger.info("DgTagMap cleared in @BeforeMethod");
        } catch (Exception e) {
            logger.warn("Exception in @BeforeMethod");
            e.printStackTrace();
        }
    }

    /**
     * This test checks for signup for the existing users using the path Home Page to Login Page to Signup Page
     * It relies on the error message that appears on signup page
     *
     * @param userCredentials User login credentials
     * @param signupDP        data provides passes existing_user_mobile_number value to the test
     * @author Vishwanath
     */
//    @Test(groups = {"smokeChecklist"}, priority = 1, dataProvider = "signUpTests", dataProviderClass = DataProviders.class, testName = "newAppSignUpTests", description = "SMT-001")
    public void existingUserSignupFromNewAppLandingPage(HashMap userCredentials, HashMap signupDP) {
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
     * The method includes
     * - navigating using the forgot password link
     * - resending verification code triggers timer
     * - login using local(IND) number
     * - login using the email
     * - logout from the profile page
     * - login using the international number(USA)
     * meanwhile skipping onBoarding, secureAccount and HomepageOverlay window to HomePageActions
     */
    @Test(groups = {"smokeChecklist"}, priority = 2, dataProvider = "loginTests", dataProviderClass = DataProviders.class, testName = "loginTests", description = "SMT-002 & SMT-003")
    public void validateLoginTests(HashMap credentialsHM, HashMap loginHM) {
        try {
            logger.info("-------------------------Running tests to validate login features-------------------------");
            assertEquals(loginPageActions.forgotPasswordLink(credentialsHM.get("email").toString()), true, "Something went wrong in forgot link transmission.");
            logger.info("Forgot Password Link and Resend Verification Timer are working successfully!");

            assertEquals(loginPageActions.firstLogin(loginHM.get("mobile_login").toString(), loginHM.get("mobile_password").toString()), true, "Something went wrong in login using mobile number.");
            logger.info("Login using mobile working successfully!");
            assertEquals(homePageActions.logoutFromMenu(), true, "Something went wrong in logout from menu options.");
            logger.info("Logging out from overflow menu working successfully!");

            assertEquals(homePageActions.loginFromMenu(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()), true, "Something went wrong in login using email from overflow menu options.");
            logger.info("Login using email from overflow menu is working successfully!");
            softAssert.assertEquals(homePageActions.logoutFromMenu(), true, "Logout from menu failed.");


            assertEquals(homePageActions.internationalLoginFromMenu(loginHM.get("inter_number_login").toString(), loginHM.get("inter_number_password").toString(),
                    loginHM.get("inter_country").toString()), true, "Something went wrong in login using international number.");
            logger.info("Login using international number is working successfully!");
            softAssert.assertEquals(homePageActions.logoutFromMenu(), true, "Logout from menu failed.");

            

            logger.info("---------------------------loginTests concluded successfully.---------------------");
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
     * The method includes
     * - getting started with ping
     * - login from ping
     * - skipping cue-tip for ping at browse page
     * - skipping the cue-tip for ping at product page
     */
    @Test(groups = {"smokeChecklist"}, priority = 3, dataProvider = "pingTests", dataProviderClass = DataProviders.class, testName = "pingTests", description = "PING")
    public void pingTests(HashMap pingHM) {
        try {
            logger.info("-------------------------Running tests to get started with ping features-------------------------");
            assertEquals(homePageActions.navigateToPingStartup(), true, "Something went wrong in navigating to the ping option.");
            logger.info("Navigated to Ping successfully!");
            assertEquals(pingActions.getStartedWithPing(pingHM.get("email").toString(), pingHM.get("password").toString()), true, "Something went wrong in getting started with ");
            logger.info("Skipped the welcome message to Ping successfully!");

            softAssert.assertEquals(homePageActions.navigateToSearchPage(), true, "Navigate to search page failed.");
            softAssert.assertEquals(searchPageActions.search("mobiles"), true, "Search for mobiles failed.");

            assertEquals(pingActions.skipPingCuetip(), true, "Something went wrong in skipping the ping cue-tip at browse page.");
            logger.info("Skipped the cue-tip at browse page for ping successfully!");
//            browsePageActions.selectAnyItemFromSearchedList();
//            assertEquals(pingActions.skipPingCuetip(), true, "Something went wrong in skipping the ping cue-tip at product page.");
//            logger.info("Skipped the cue-tip at product page for ping successfully!");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.logoutFromMenu(), true, "Logout from menu failed.");
            
            logger.info("---------------------------pingTests concluded successfully.---------------------");
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
//    @Test(groups = {"smokeChecklist"}, priority = 4, dataProvider = "signUpTests", dataProviderClass = DataProviders.class, testName = "signUpTests", description = "SMT-001")
    public void existingUserSignupFromHomePageLoginMenu(HashMap userCredentialsHM, HashMap signupDP) {
        try {
            logger.info("-------------------------Running tests to test the SignUp for existing user-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            assertTrue(new ToolbarActions(driver).navigateToLoginPage());
            assertTrue(loginPageActions.navigateToSignup());
            assertEquals(signUpActions.signup(signupDP.get("existing_user_mobile_number").toString(), ""), SignUpActions.SIGNUP_STATUS.SIGNUP_USER_ALREADY_EXISTS, "Observed signup success for existing user");
            softAssert.assertEquals(loginPageActions.skip(), true, "Login Skip failed.");
            
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
     * The method includes
     * - verifying and navigating to promotions
     * - verifying and navigating to the DOTD page
     * - verifying and navigating to the Offer zone
     * - verifying and navigating to the announcements
     * - verifying the elements on drawer
     */
//    @Test(groups = {"smokeChecklist"}, priority = 5, dataProvider = "homePageTests", dataProviderClass = DataProviders.class, testName = "homePageTests", description = "SMT-004")
    public void validateHomePage(HashMap homePageHM) {
        try {
            logger.info("-------------------------Running test to validate HomePageActions features-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            assertEquals(homePageActions.navigateToPromotions(), true, "Something went wrong in navigating to the promotions.");
            logger.info("Validated navigation to promotions page successfully!");
            assertEquals(homePageActions.navigateToDotd(), true, "Something went wrong in navigating to the deals of the day.");
            logger.info("Validated navigation to deals of the day page successfully!");
//            assertEquals(homePageActions.navigateToOfferZone(), true, "Something went wrong in navigating to the offer zone.");
//            logger.info("Validated navigation to offer zone page successfully!");
//            assertEquals(homePageActions.navigateToAnnouncements(), true, "Something went wrong in navigating to the announcements.");
//            logger.info("Validated navigation to announcements page successfully!");
//            assertEquals(homePageActions.verifyHomePageProducts(), true, "Something went wrong in verifying the PMU products on homepage.");
//            logger.info("Validated PMU products on home page successfully!");
//            assertEquals(homePageActions.navigateToLegalPage(), true, "Something wrong in navigating to legal page.");
//            homePageActions.navigateBackHome();
//            logger.info("Validated navigation to legal page successfully.");
//            assertEquals(homePageActions.navigateToHelpCentre(), true, "Something wrong in navigating to help and centre page.");
//            homePageActions.navigateBackHome();
//            logger.info("Validated navigation to help centre page successfully.");
            
            logger.info("----------------homePageTests concluded successfully.---------------------");
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
     * The method includes
     * - navigating to the search page
     * - searching a product/category/brand
     * - verify adding the guided search if they should be available for the product
     * - remove custom search tags
     * - clear search tags
     * - clear search history
     * sub category search suggestions // need data using API for the suggestions string
     */
    @Test(groups = {"smokeChecklist"}, priority = 6, dataProvider = "searchTests", dataProviderClass = DataProviders.class, testName = "searchPageTests", description = "SMT-005 & SMT-006")
    public void searchTests(String[] searchStrings) {
        try {
            logger.info("-------------------------Running test to validate Search Page features-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            assertEquals(homePageActions.navigateToSearchPage(), true, "Something went wrong in navigating to search page.");
            logger.info("Successfully navigated to search page!");

            for (index = 0; index < searchStrings.length; index++) {
                boolean showGuides = false;
                assertEquals(searchPageActions.search(searchStrings[index]), true, "Something went wrong in searching " + searchStrings[index]);
                logger.info("Search for " + searchStrings[index] + " was successful!");

                //TODO : get the status of guided search
//                showGuides = new SearchAction().validateGuidedSearchKeywords();
                if (showGuides) {
                    assertEquals(searchPageActions.guidedSearch(), true, "Something went wrong in searching " + searchStrings[index]);
                    logger.info("Guided search for " + searchStrings[index] + " was successfully displayed!");
                }

                assertEquals(searchPageActions.removeCustomSearchElement(), true, "Something went wrong in removing the custom search tags.");
                logger.info("Removed the custom search tag successfully!");

                assertEquals(searchPageActions.clearSearchTag(), true, "Something went wrong in clearing search tags.");
                logger.info("Clearing search tag validated successfully!");
            }

            assertEquals(searchPageActions.clearSearchHistory(), true, "Something went wrong while trying to clear the search history.");
            logger.info("Clearing search history validated successfully!");
            //TODO : get API data for suggested substrings
//            assertEquals(searchPageActions.subCategorySearch(searchString, searchSuggestionString), true, "Something went wrong in sub category searching.");
//            logger.info("Sub Category Search validated successfully.");
            
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

    @Test(groups = {"smokeChecklist"}, priority = 7, dataProvider = "visualSearchTests", dataProviderClass = DataProviders.class, testName = "visualSearchPageTests", description = "SMT-007 & SMT-008")
    public void visualSearchTests(String[] visualSearchStrings) {
        try {
            logger.info("-------------------------Running test to validate Visual search features-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.navigateToSearchPage(), true, "Navigate to search failed.");
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

    /**
     * The method includes
     * - searching using auto-suggested list
     * - navigating to the browse page
     * - sharing product using chat option
     * - similar search page for lifestyle product
     * - selecting category filters and verify
     * - selecting random sort and verify
     * - toggling view after search
     * ratings and reviews in browse page for list view are modified by getting the view type
     */
//    @Test(groups = {"smokeChecklist"}, priority = 8, dataProvider = "browsePageTests", dataProviderClass = DataProviders.class, testName = "browsePageTests", description = "SMT-009 & SMT-010 & SMT-013 & SMT-014 & SMT-015")
    public void browsePageTests(HashMap userCredentialsHM, String[] browseStrings) {
        try {
            logger.info("-------------------------Running test to validate Browse Page features-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
//            softAssert.assertEquals(homePageActions.loginFromMenu(userCredentialsHM.get("email").toString(), userCredentialsHM.get("password").toString()), true, "Login from menu failed."); //TODO remove comment for shareUsingChatOnly method
            softAssert.assertEquals(homePageActions.navigateToSearchPage(), true, "Navigate to search failed.");

            for (index = 0; index < browseStrings.length; index++) {
                boolean lifestyle = false;
                assertEquals(searchPageActions.searchFromAutoSuggestList(browseStrings[index]), true, "Something went wrong in selecting the element from auto suggest list.");
                logger.info("Search using auto suggest list for " + browseStrings[index] + " was successful!");

                assertEquals(browsePageActions.validatePriceTitle(), true, "Something went wrong in validating the product prices and titles.");
                logger.info("Verified the product titles and prices of the product displayed successfully!");

                //TODO : mark dependsOnMethod as getStartedWithPing
//                assertEquals(browsePageActions.shareUsingChatOnly(), true, "Something went wrong while sharing using chat icon from the browse page.");
//                logger.info("Successfully verified sharing " + browseStrings[index] + " using chat!");

                //TODO : get the flag for a product if its a Lifestyle product or not
                if (lifestyle) {
                    assertEquals(browsePageActions.similarSearches(), true, "Something went wrong while verifying the ratings and reviews on the browse page.");
                    logger.info("Successfully validated similar searches for " + browseStrings[index] + "!");
                }
                //TODO : un-comment the following post JFM Geo release for filters test
//                assertEquals(browsePageActions.selectCategoryFilters(), true, "Something went wrong in selecting category filters and validating for " + browseStrings[index]);
//                logger.info("Successfully applied price and availability sub category filters for " + browseStrings[index] + "!");

                assertEquals(browsePageActions.selectCategorySort(), true, "Something went wrong in selecting category sort and validating for " + browseStrings[index]);
                logger.info("Successfully applied category sort for " + browseStrings[index] + "!");

//                assertEquals(browsePageActions.toggleView(), true, "Something went wrong in toggling view for " + browseStrings[index]);
//                logger.info("Toggling views was successful for " + browseStrings[index] + "!");

                softAssert.assertEquals(searchPageActions.clearSearchTag(), true, "Clear search tag failed.");
//                softAssert.assertTrue(homePageActions.logoutFromMenu()); //TODO remove comment for shareUsingChatOnly method
            }
            
            logger.info("-------------------------browsePageTests concluded successfully.--------------------");
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
     * The method includes
     * - verifying the ads present on browse/search pages
     * - verifying adding the product to wishlist from browse page
     */
//    @Test(groups = {"smokeChecklist"}, priority = 9, dataProvider = "adsTest", dataProviderClass = DataProviders.class, testName = "adsTests", description = "SMT-011 & SMT-012")
    public void browsePageAdsTests(String[] adsSearchStrings) {
        try {
            logger.info("-------------------------Running test to validate Ads and Add to Wishlist for browse page-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");

            for (index = 0; index < adsSearchStrings.length; index++) {
                softAssert.assertEquals(homePageActions.navigateToSearchPage(), true, "Navigate to search failed.");
                assertEquals(searchPageActions.search(adsSearchStrings[index]), true, "Something went wrong in searching " + adsSearchStrings[index]);
                assertEquals(browsePageActions.verifyAds(), true, "Something went wrong in verifying the ads on search page.");
                logger.info("Validating ads for search page of " + adsSearchStrings[index] + " was successful.");
                assertEquals(browsePageActions.addToWishlistFromBrowsePage(), true, "Something went wrong in adding to wishlist from the browse page.");
                logger.info("Successfully added a product from " + adsSearchStrings[index] + " to wishList.");
            }
            
            logger.info("-------------------------Ads and Add to Wishlist Tests concluded successfully.--------------------");
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
     * The method includes
     * - landing on to the clp
     * - verify the filter widget,promotions and announcement presence for the clp
     */
    @Test(groups = {"smokeChecklist"}, priority = 10, dataProvider = "clpTests", dataProviderClass = DataProviders.class, testName = "landingPageTests", description = "SMT-016")
    public void landingPageTests(String[] clpAppliances) {
        try {
            logger.info("-------------------------Running test to validate the clp features-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            for (index = 0; index < clpAppliances.length; index++) {
                assertEquals(homePageActions.navigateToCLP(clpAppliances[index]), true, "Something went wrong while navigating to the CLP for " + clpAppliances[index]);
                logger.info("Verified navigating to the CLP successfully!");
            }
            
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


    /**
     * The method includes
     * - navigating to product page for a particular brand/product
     * - sharing the product using ping
     * - validating and swiping to next and previous product in same category
     * - viewing the slideshow images
     * - validating the product title
     * - validating the subtitle of the product
     * - validating the wishlist,pullout
     * - validating prices and discounts displayed
     * - validating the ratings widget
     * - validating the swatches widget
     * - validating the seller details
     * - validating the warranty details
     * - validating the specifications of product
     * - validating the summary of the product
     * - validating the offers on the product of available
     * - validating the recommendations
     * - validating the similar searches
     * - validating the availability check
     * - adding product to wishlist and verifying
     */
    //@Test(groups = {"smokeChecklist"}, priority = 11, dataProvider = "productPageTests", dataProviderClass = DataProviders.class, testName = "productPageTests", description = "SMT-017 TO SMT-031")
    public void productPage3Tests(Map<String, Map> data) {
        try {

            logger.info("-------------------------Running test to validate Product Page features-------------------------");
            homePageActions.navigateBackHome();
            // TODO : get the flag for a product if it should have rating and reviews,MRP,SP,DISCOUNT,SUMMARY,WARRANTY,OTHER SELLER etc..

            String productCategory = "Mobiles";//categories.name();
            homePageActions.navigateToSearchPage();
            searchPageActions.searchFromAutoSuggestList(productCategory);
            browsePageActions.selectAnyItemFromSearchedList();
            logger.info("Moved to the product page for " + productCategory);

            assertNotNull(productPageV3.getProductName(), productCategory + " : Validation of Title on the Product has failed");
            logger.info("Transiting to the product page for " + productCategory + " validated successfully!");

            assertTrue(productPageV3.findPingButton());
            assertTrue(productPageV3.findPingButton());
            assertEquals(productPageV3.addToWishList(), true, "Something went wrong while adding product to wishlist.");
            logger.info("Product added to wishlist from Product Page successfully!");

            String currentProductName = productPageV3.getProductName();
            assertTrue(productPageV3.swipeNextProduct(), "Something went wrong while swiping to the next product.");
            assertTrue(productPageV3.swipePreviousProduct(), "Something went wrong while swiping to the previous product.");
            logger.info("Verified navigating to next and previous product successfully!");
            logger.info("Seller information for " + productCategory + " validated successfully!");
            ProductPageV3.Price p = productPageV3.getProductPrice();

            assertTrue(p.getFSP() < p.getMRP(), " selling price is higher than MRP");

            int priceDiscount = p.getMRP() - p.getFSP();
            int discountPercentage = (int) Math.floor((float) priceDiscount / p.getMRP() * 100);
            assertEquals(discountPercentage, p.getDicountedPercentage(), "Observed discount % calculation error on product page");

            assertEquals(productPageV3.isProductImageAvailable(), true, productCategory + " : Validation of Product Page Main Image on the Product has failed");
            logger.info("Product image  " + productCategory + " validated successfully!");

            int numberOfReviewsInRatingBar = productPageV3.getReviewCountInRatingBar();
            int totalNumberOfReviews = productPageV3.getTotalNumberOfReviews();
            float rating = productPageV3.getProductRating();

            assertNotEquals(numberOfReviewsInRatingBar, -1, "Error in finding review count on the rating bar");
            assertNotEquals(totalNumberOfReviews, -1, "Error in finding review count on review tab");
            assertNotEquals(rating, -1, "Error in getting the rating on product page");
            assertEquals(numberOfReviewsInRatingBar, totalNumberOfReviews, "Mismatch in number of reviews on rating bar and reviews tab");

            assertEquals(productPageActions.validateRatingOnProductPage((Boolean) data.get(productCategory).get(DataProviders.Keys.RATINGS), (Boolean) data.get(productCategory).get(DataProviders.Keys.REVIEWS)), true, productCategory + " : Validation of Ratings and Reviews on the Product has failed");
            logger.info("Ratings and reviews for " + productCategory + " validated successfully!");


            if (productPageV3.isOfferAvailable() == ProductPageV3.ProductPageParameters.OFFERS_AVAILABLE) {
                ProductPageV3.ProductPageParameters offerTestResult = productPageV3.validateOffers();
                assertNotEquals(offerTestResult, ProductPageV3.ProductPageParameters.OFFERS_NOT_AVAILABLE, "Offers section was not found");
                assertNotEquals(offerTestResult, ProductPageV3.ProductPageParameters.SELLER_SERVICES_DETAILS_NOT_FOUND, "Seller Services information wasn't displayed on clicking 'Offers'");
                assertNotEquals(offerTestResult, ProductPageV3.ProductPageParameters.OFFERS_NOT_DISPLAYED, "Offers were not displayed on the Seller Services page");
                assertNotEquals(offerTestResult, ProductPageV3.ProductPageParameters.OFFERS_COUNT_MISMATCH, "Mismatch of offer count on offers page and offers icon");
                assertEquals(offerTestResult, ProductPageV3.ProductPageParameters.SUCCESS, "Offers section validated");
                productPageV3.validateOffers();
            } else {
                logger.warn("Offers were not available on the product (" + productPageV3.getProductName() + ") for category " + productCategory);
            }

            ProductPageV3.ProductPageParameters pincodeCheckStatus = productPageV3.checkAvailability("560037");
            assertNotEquals(pincodeCheckStatus, ProductPageV3.ProductPageParameters.PINCODE_LAYOUT_NOT_FOUND, "Pincode layout was not found");
            assertNotEquals(pincodeCheckStatus, ProductPageV3.ProductPageParameters.INVALID_PINCODE, "Invalid Pincode");
            assertEquals(pincodeCheckStatus, ProductPageV3.ProductPageParameters.SERVICEABLE, "Product is serviceable");

            pincodeCheckStatus = productPageV3.checkAvailability("781316");
            assertNotEquals(pincodeCheckStatus, ProductPageV3.ProductPageParameters.NOT_SERVICEABLE, "Product is not serviceable");

            logger.info("Similar Searches for " + productCategory + " validated successfully!");


            ProductPageV3.ProductPageParameters warrantyValidationStatus = productPageV3.validateWarranty();
            assertNotEquals(warrantyValidationStatus, ProductPageV3.ProductPageParameters.WARRANTY_DETAILS_NOT_AVAILABLE, "Unable to locate warranty details on the product page");
            assertNotEquals(warrantyValidationStatus, ProductPageV3.ProductPageParameters.WARRANTY_DETAILS_DATA_ERROR, "Warranty details is empty on product page");
            assertNotEquals(warrantyValidationStatus, ProductPageV3.ProductPageParameters.FAILURE, "Error in warranty data validation");
            assertEquals(warrantyValidationStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Warranty details validated successfully!");
            logger.info("Warranty for " + productCategory + " validated successfully!");


            ProductPageV3.ProductPageParameters specificationValidationStatus = productPageV3.validateSpecifications();
            assertNotEquals(specificationValidationStatus, ProductPageV3.ProductPageParameters.SPECIFICATION_DETAILS_NOT_AVAILABLE, "Unable to locate specification details on the product page");
            assertNotEquals(specificationValidationStatus, ProductPageV3.ProductPageParameters.SPECIFICATION_DETAILS_DATA_ERROR, "Specification details is empty on product page");
            assertNotEquals(specificationValidationStatus, ProductPageV3.ProductPageParameters.FAILURE, "Error in specification data validation");
            assertEquals(specificationValidationStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Specification details validated successfully!");
            logger.info("Specification data for " + productCategory + " validated successfully!");


            ProductPageV3.ProductPageParameters recoStatus = productPageV3.validateRecommendations();
            assertNotEquals(recoStatus, ProductPageV3.ProductPageParameters.RECO_LAYOUT_NOT_AVAILABLE, "Recommendations not shown on the product page");
            assertNotEquals(recoStatus, ProductPageV3.ProductPageParameters.RECO_PRODUCT_IMAGE_NOT_DISPLAYED, "Product images not displayed in recommendations");
            assertNotEquals(recoStatus, ProductPageV3.ProductPageParameters.RECO_PRODUCT_TITLE_NOT_DISPLAYED, "Product titles not displayed in recommendations");
            assertNotEquals(recoStatus, ProductPageV3.ProductPageParameters.RECO_PRODUCT_PRICE_NOT_DISPLAYED, "Product prices not displayed in recommendations");
            assertEquals(recoStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Recommendations details validated successfully!");
            logger.info("Recommendations data for " + productCategory + " validated successfully!");


            ProductPageV3.ProductPageParameters similarProductStatus = productPageV3.validateSimilarProductSearches();
            assertNotEquals(similarProductStatus, ProductPageV3.ProductPageParameters.SIMILAR_PRODUCT_LAYOUT_NOT_AVAILABLE, "Similar Products not shown on the product page");
            assertNotEquals(similarProductStatus, ProductPageV3.ProductPageParameters.SIMILAR_PRODUCT_IMAGE_NOT_DISPLAYED, "Similar Products images not displayed in recommendations");
            assertNotEquals(similarProductStatus, ProductPageV3.ProductPageParameters.SIMILAR_PRODUCT_TITLE_NOT_DISPLAYED, "Similar Products titles not displayed in recommendations");
            assertNotEquals(similarProductStatus, ProductPageV3.ProductPageParameters.SIMILAR_PRODUCT_PRICE_NOT_DISPLAYED, "Similar Products prices not displayed in recommendations");
            assertEquals(similarProductStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Similar Products details validated successfully!");
            logger.info("Similar Products data for " + productCategory + " validated successfully!");


            ProductPageV3.ProductPageParameters sellertStatus = productPageV3.validateSeller();
            assertNotEquals(sellertStatus, ProductPageV3.ProductPageParameters.SELLER_LAYOUT_NOT_FOUND, "Seller layout not shown on the product page");
            assertNotEquals(sellertStatus, ProductPageV3.ProductPageParameters.SELLER_NAME_NOT_FOUND, "Seller name is not displayed in the seller layout");
            assertNotEquals(sellertStatus, ProductPageV3.ProductPageParameters.SELLER_RATING_NOT_FOUND, "Seller rating is not displayed in the seller layout");
            assertNotEquals(sellertStatus, ProductPageV3.ProductPageParameters.SELLER_PRODUCT_PRICE_NOT_FOUND, "Seller product price is not displayed in the seller layout");
            assertNotEquals(sellertStatus, ProductPageV3.ProductPageParameters.SELLER_PRODUCT_DELIVERY_INFO_NOT_FOUND, "Delivery details not displayed in seller info");
            assertEquals(sellertStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Seller details validated successfully!");
            logger.info("Seller details for " + productCategory + " validated successfully!");

            ProductPageV3.ProductPageParameters slideShowStatus = productPageV3.slideshowProductImage();

            if (slideShowStatus.equals(ProductPageV3.ProductPageParameters.PRODUCT_IMAGES_NOT_AVAILABLE)) {
                assertTrue(productPageV3.isProductImageAvailable(), "Product image available. Multiple images are not avilable for this product");
                logger.warn("Product does not have multiple images to validate slide show operation");
            } else {
                assertNotEquals(slideShowStatus, ProductPageV3.ProductPageParameters.SLIDER_NOT_FOUND, "Slider was not found on the product page");
                assertEquals(slideShowStatus, ProductPageV3.ProductPageParameters.SUCCESS, "Slide show validated successfully!");
                logger.info("Slide show for " + productCategory + " validated successfully!");
            }
            
        } catch (NullPointerException | InterruptedException | TimeoutException testException) {

            if (testException instanceof TimeoutException) {
                logger.warn("Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() + " for the failure.");
                logger.warn(testException.getCause());
            } else {
                logger.warn("NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
                testException.printStackTrace();
            }
        }
    }

    //TODO : PPV3 changes yet to be implemented

    /**
     * The method includes
     * - navigating to product page for a particular brand/product
     * - sharing the product using ping
     * - validating and swiping to next and previous product in same category
     * - viewing the slideshow images
     * - validating the product title
     * - validating the subtitle of the product
     * - validating the wishlist,pullout
     * - validating prices and discounts displayed
     * - validating the ratings widget
     * - validating the swatches widget
     * - validating the seller details
     * - validating the warranty details
     * - validating the specifications of product
     * - validating the summary of the product
     * - validating the offers on the product of available
     * - validating the recommendations
     * - validating the similar searches
     * - validating the availability check
     * - adding product to wishlist and verifying
     */
//    @Test(groups = {"smokeChecklist"}, priority = 11, dataProvider = "productPageTests", dataProviderClass = DataProviders.class, testName = "productPageTests", description = "SMT-017 TO SMT-031")
    public void productPageTests(Map<String, Map> data) {
        try {
            logger.info("-------------------------Running test to validate Product Page features-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");

            // TODO : get the flag for a product if it should have rating and reviews,MRP,SP,DISCOUNT,SUMMARY,WARRANTY,OTHER SELLER etc..
            for (DataProviders.Categories categories : DataProviders.Categories.values()) {
                String productCategory = categories.name();
                softAssert.assertEquals(homePageActions.navigateToSearchPage(), true, "Navigate to search failed.");
                softAssert.assertEquals(searchPageActions.searchFromAutoSuggestList(productCategory), true, "Search from asc failed.");
                softAssert.assertEquals(browsePageActions.selectAnyItemFromSearchedList(), true, "Select item from search list failed.");

                logger.info("Moved to the product page for " + productCategory);

                assertEquals(productPageActions.shareProduct(), true, "Something went wrong in sharing " + productCategory + " from product page.");
                logger.info("Sharing for " + productCategory + " validated successfully!");

                String currentProductName = productPageActions.getProductName();
                softAssert.assertEquals(productPageActions.swipeProductNext(), true, "Swipe next failed.");
                softAssert.assertEquals(productPageActions.swipeProductPrev(), true, "Swipe prev failed.");

                assertEquals(productPageActions.getProductName(), currentProductName, "Something went wrong while swiping to next and back to same product.");
                logger.info("Verified navigating to next and previous product successfully!");

                assertEquals(productPageActions.slideshowProductImage(), true, "Something went wrong during slideshow using the thumbnails ");
                logger.info("Slideshow of the product using thumbnail images completed!");

                assertEquals(productPageActions.isElementPresent(productPageActions.getBy(BasePage.productPageLocators.get("ProductPageTitle"))), true, productCategory + " : Validation of Title on the Product has failed");
                logger.info("Transiting to the product page for " + productCategory + " validated successfully!");

                if ((Boolean) data.get(productCategory).get(DataProviders.Keys.SUBTITLE)) {
                    assertEquals(productPageActions.isElementPresent(productPageActions.getBy(BasePage.productPageLocators.get("ProductPageSubTitle"))), true, productCategory + " : Validation of SubTitle on the Product has failed");
                    logger.info("Title for " + productCategory + " validated successfully!");
                }

                assertEquals(productPageActions.isElementPresent(productPageActions.getBy(BasePage.productPageLocators.get("ProductPageWishlistLink"))), true, productCategory + " : Validation of Wishlist image on the Product has failed");
                logger.info("Wishlist icon for " + productCategory + " validated successfully!");

                assertEquals(productPageActions.isElementPresent(productPageActions.getBy(BasePage.productPageLocators.get("ProductPageImageLayout"))), true, productCategory + " : Validation of ProductPageMainImage on the Product has failed");
                logger.info("Product image  " + productCategory + " validated successfully!");

                assertEquals(productPageActions.isElementPresent(productPageActions.getBy(BasePage.productPageLocators.get("PullOut"))), true, productCategory + " : Validation of ProductPagePullout on the Product has failed");
                logger.info("Pullout button for " + productCategory + " validated successfully!");

                if ((Boolean) data.get(productCategory).get(DataProviders.Keys.MRP) || (Boolean) data.get(productCategory).get(DataProviders.Keys.SELLINGPRICE) || (Boolean) data.get(productCategory).get(DataProviders.Keys.SPECIALPRICE))
                    assertEquals(productPageActions.validatePrices((Boolean) data.get(productCategory).get(DataProviders.Keys.MRP), (Boolean) data.get(productCategory).get(DataProviders.Keys.SELLINGPRICE), (Boolean) data.get(productCategory).get(DataProviders.Keys.SPECIALPRICE), (Boolean) data.get(productCategory).get(DataProviders.Keys.EMIOPTION)), true, productCategory + " : There can not be a product without any price");
                logger.info("Prices for " + productCategory + " validated successfully!");

                if ((Boolean) data.get(productCategory).get(DataProviders.Keys.RATINGS) || (Boolean) data.get(productCategory).get(DataProviders.Keys.REVIEWS)) {
                    assertEquals(productPageActions.validateRatingOnProductPage((Boolean) data.get(productCategory).get(DataProviders.Keys.RATINGS), (Boolean) data.get(productCategory).get(DataProviders.Keys.REVIEWS)), true, productCategory + " : Validation of Ratings and Reviews on the Product has failed");
                    logger.info("Ratings and reviews for " + productCategory + " validated successfully!");
                }

                if ((Boolean) data.get(productCategory).get(DataProviders.Keys.SWATCHES)) {
                    assertEquals(productPageActions.validateSwatches(), true, productCategory + " : Validation of Swatches on the Product has failed");
                    logger.info("Swatches for " + productCategory + " validated successfully!");
                }

                assertEquals(productPageActions.validateSeller((Boolean) data.get(productCategory).get(DataProviders.Keys.OTHERSELLER)), true, productCategory + " : Validation of Seller on the Product has failed");
                logger.info("Seller information for " + productCategory + " validated successfully!");

                if ((Boolean) data.get(productCategory).get(DataProviders.Keys.WARRANTY)) {
                    assertEquals(productPageActions.validateWarranty(), true, productCategory + " : Validation of Warranty on the Product has failed");
                    logger.info("Warranty for " + productCategory + " validated successfully!");
                }

                assertEquals(productPageActions.validateSpecifications(), true, productCategory + " : Validation of Specifications on the Product has failed");
                logger.info("Specifications for " + productCategory + " validated successfully!");

                if ((Boolean) data.get(productCategory).get(DataProviders.Keys.SUMMARY)) {
                    assertEquals(productPageActions.validateSummary(), true, productCategory + " : Validation of Summary on the Product has failed");
                    logger.info("Summary for " + productCategory + " validated successfully!");
                }

                assertEquals(productPageActions.validateOfferOnProductPage(), true, productCategory + " : Validation of Offers on the Product has failed");
                logger.info("Offers for " + productCategory + " validated successfully!");

                assertEquals(productPageActions.validateRecommendations(), true, productCategory + " : Validation of Recommendations on the Product has failed");
                logger.info("Recommendations for " + productCategory + " validated successfully!");

                assertEquals(productPageActions.validateSimilarProductSearches(), true, productCategory + " : Validation of Similar Searches on the Product has failed");
                logger.info("Similar Searches for " + productCategory + " validated successfully!");

                assertEquals(productPageActions.checkAvailability("560037"), true, productCategory + " : Validation of Similar Searches on the Product has failed");
                logger.info("Similar Searches for " + productCategory + " validated successfully!");

                assertEquals(productPageActions.productAddToWishlist(), true, "Something went wrong while adding product to wishlist.");
                logger.info("Product added to wishlist from Product Page successfully!");
                homePageActions.navigateBackHome();

                
                logger.info("-------------------------ProductPageTests concluded successfully.--------------------");
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
     * This method includes
     * - navigating to the wishlist
     * - emptying the wishlist
     * - login using an account
     * - clearing the wishlist
     * - adding products to the wishlist
     * - selecting item on wishlist and moving
     * - verifying the wishlist after logging out
     * - verifying merging the wishlist for logged out and logging in user
     */
//    @Test(groups = {"smokeChecklist"}, priority = 12, dataProvider = "wishlistTests", dataProviderClass = DataProviders.class, testName = "wishlistTests", description = "SMT-032")
    public void wishlistTests(HashMap credentialsHM, String[] wishlistStrings) {
        try {
            logger.info("-------------------------Running test to validate Wishlist Page features-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");

            assertEquals(homePageActions.navigateToWishlist(), true, "Something went wrong in navigating to the wishlist.");
            logger.info("Navigated to the wishlist successfully!");

            assertEquals(wishlistPageActions.wishlistdeleteAllitems(), true, "Something went wrong in deleting all the items from wishlist.");
            logger.info("Cleared the wishlist items already attached with device successfully!");

            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.loginFromMenu(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()), true, "Something went wrong in login using email from overflow menu options.");
            softAssert.assertEquals(homePageActions.navigateToWishlist(), true, "Navigate to wishlist failed.");

            assertEquals(wishlistPageActions.wishlistdeleteAllitems(), true, "Something went wrong in deleting all the items from wishlist.");
            logger.info("Cleared the wishlist items already attached with the account successfully!");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");

            for (index = 0; index < wishlistStrings.length; index++) {
                softAssert.assertEquals(homePageActions.navigateToSearchPage(), true, "Navigate to search failed.");
                softAssert.assertEquals(searchPageActions.search(wishlistStrings[index]), true, "Search from wishlist strings failed.");

                assertEquals(browsePageActions.addToWishlistFromBrowsePage(), true, "Something went wrong in adding a product for " + wishlistStrings[index] + " to wishlist.");
                logger.info("Successfully added product to wishlist for " + wishlistStrings[index] + "!");
                softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            }

            softAssert.assertEquals(homePageActions.navigateToWishlist(), true, "Navigate to wishlist failed.");

            assertEquals(wishlistPageActions.selectItemMoveToProductPage(), true, "Something went wrong in navigating to the product page from wishlist.");
            logger.info("Successfully navigated to the product page for first product on wishlist page!");

            softAssert.assertEquals(homePageActions.logoutFromMenu(), true, "Logout from menu failed.");
            softAssert.assertEquals(homePageActions.navigateToWishlist(), true, "Navigate to wishlist failed.");
            assertEquals((wishlistPageActions.getWishlistItemsCount() == 0), true, "Wishlist items count after logout is not 0.");
            logger.info("Logging out clears the wishlist items added on the account successfully!");

            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.loginFromMenu(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()), true, "Login from menu failed.");

            softAssert.assertEquals(homePageActions.navigateToWishlist(), true, "Navigate to wishlist failed.");
            softAssert.assertEquals(wishlistPageActions.wishlistdeleteAllitems(), true, "Deleting all wishlist items failed.");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");

            softAssert.assertEquals(homePageActions.logoutFromMenu(), true, "Logout from menu failed.");
            softAssert.assertEquals(homePageActions.navigateToSearchPage(), true, "Navigate to search failed.");
            softAssert.assertEquals(searchPageActions.search(wishlistStrings[0]), true, "Search with wishlist strings failed.");
            softAssert.assertEquals(browsePageActions.addToWishlistFromBrowsePage(), true, "Add to wishlist from browse failed.");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.navigateToWishlist(), true, "Navigate to wishlist failed.");
            List<String> initialWishListItems = wishlistPageActions.listProductsOnWishlist();
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.loginFromMenu(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()), true, "Login from menu failed.");

            softAssert.assertEquals(homePageActions.navigateToWishlist(), true, "Navigate to wishlist failed.");
            List<String> finalWishListItems = wishlistPageActions.listProductsOnWishlist();
            assertEquals(((finalWishListItems.size() == initialWishListItems.size()) && finalWishListItems.containsAll(initialWishListItems)), true, "Products added to the wishlist are not merged after log in.");
            logger.info("Product added to wishlist from device were merged to the account after log in successfully!");
            softAssert.assertEquals(homePageActions.logoutFromMenu(), true, "Logout from menu failed.");
            
            logger.info("-------------------------WishlistTests concluded successfully.--------------------");
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

    //TODO : Work on GeoBrowse SMT-033
    //TODO : Skips SMT-034,035,036

    /**
     * The method includes
     * - navigating to cart page
     * - removing all the cart items one by one
     * - verifying the cart count displayed in the toolbar
     * - selecting swatches for the lifestyle product
     * - verifying adding to cart for a brand/product search and their count displayed
     * - validating the cart products [//TODO : implement webview, not as of now]
     */
    @Test(groups = {"smokeChecklist"}, priority = 13, dataProvider = "cartTests", dataProviderClass = DataProviders.class, testName = "cartTests", description = "SMT-037")
    public void cartTests(HashMap credentialsHM, String[] cartStrings) {
        try {
            logger.info("-------------------------Running test to validate Cart features-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.loginFromMenu(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()), true, "Login from menu failed.");

            int cartCount = homePageActions.getCartCountOnHomePage();
            assertEquals(homePageActions.navigateToCartPage(), true, "Something went wrong in navigating to the cart page.");
            logger.info("Navigated to the cart page successfully!");

            assertEquals(cartPageActions.removeAllItemsFromCart(cartCount), true, "Something went wrong while removing all the items from the cart.");
            logger.info("Deleted all the items from cart successfully!");

            int cartPageCartCount = cartPageActions.getCartCountOnCartPage();
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            int homePageCartCount = homePageActions.getCartCountOnHomePage();
            assertEquals(homePageCartCount == cartPageCartCount, true, "Something went wrong in counting the notifications.");
            logger.info("Cart count on cartPageActions and homePageActions validated successfully!");

            List<String> productAddedToCart = new ArrayList<>();
            int expectedCartProducts = homePageActions.getCartCountOnHomePage();
            for (index = 0; index < cartStrings.length; index++) {
                softAssert.assertEquals(homePageActions.navigateToSearchPage(), true, "Navigate to search page failed.");
                softAssert.assertEquals(searchPageActions.searchFromAutoSuggestList(cartStrings[index]), true, "Search from asc list failed.");
                softAssert.assertEquals(browsePageActions.selectAnyItemFromSearchedList(), true, "Selecting from search list failed.");
                String currProdName = productPageActions.getProductName();
                assertEquals(productPageActions.productAddToCart(), true, "Something went wrong while adding the product to the cart.");
                logger.info("Successfully added a product from " + cartStrings[index] + " category!");
                expectedCartProducts++;
                softAssert.assertEquals(productAddedToCart.add(currProdName), true, "Add to cart failed.");
                softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            }
            assertEquals(homePageActions.getCartCountOnHomePage() == expectedCartProducts, true, "All the elements supposedly not added to the cart.");
            logger.info("Added all the specified products to cart successfully!");

            softAssert.assertEquals(homePageActions.navigateToCartPage(), true, "Navigate to cart page failed.");
            assertEquals(cartPageActions.validateCartProducts(productAddedToCart), true, "Something went wrong in validating products added to the cart.");

            logger.info("Successfully verified the products specifications for the ones added to the cart.");
            assertEquals(cartPageActions.checkOutFromCartPage(), true, "Something went wrong in placing the order form the cart page.");
            logger.info("Moved to the checkout page form cart successfully!");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.logoutFromMenu(), true, "Logout from menu failed.");
            
            logger.info("-------------------------cartTests concluded successfully.--------------------");
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
     * The method includes
     * - navigating to the checkout page from cart page
     * - proceeding to buy now option from product page
     * - selecting the COD payment mode
     */
    @Test(groups = {"smokeChecklist"}, priority = 14, dataProvider = "checkoutTests", dataProviderClass = DataProviders.class, testName = "checkoutTests", description = "SMT-037")
    public void checkoutTests(HashMap credentialsHM, String[] checkoutStrings) {
        try {
            logger.info("-------------------------Running test to validate Checkout features-------------------------");

            softAssert.assertEquals(homePageActions.navigateToSearchPage(), true, "Navigate to search failed.");
            softAssert.assertEquals(searchPageActions.search(checkoutStrings[0]), true, "Search failed.");
            softAssert.assertEquals(browsePageActions.selectAnyItemFromSearchedList(), true, "Selecting item from search failed.");

            assertEquals(productPageActions.productBuyNow(), true, "Something went wrong in moving to buy now field.");
            logger.info("Proceeded with Buy Now option from the product page successfully!");

            assertEquals(checkoutPageActions.loginFromCheckoutPage(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()), true, "Something went wrong while signing in from checkout page.");
            logger.info("Logged in from checkout page was successful.");

            assertEquals(checkoutPageActions.addressOnCheckout(), true, "Something went wrong during adding address during checkout on purchase.");
            logger.info("Address added on checkout page successfully.");

            assertEquals(checkoutPageActions.continueToPay_COD(), true, "Something went wrong while proceeding to COD.");
            logger.info("Continued to Pay COD on checkout successfully.");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.logoutFromMenu(), true, "Logout from menu failed.");
            
            logger.info("-------------------------checkoutTests concluded successfully.--------------------");
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
     * The method includes
     * - viewing all orders for the user
     * - adding address for the user
     * - viewing the wallet details if added
     * - viewing subscriptions details if available
     * - viewing the account settings
     */
//    @Test(groups = {"smokeChecklist"}, priority = 15, dataProvider = "myAccountsTests", dataProviderClass = DataProviders.class, testName = "myAccountTests", description = "SMT-038")
    public void myAccountTests(HashMap myAccountsHM) {
        try {
            logger.info("-------------------------Running test to validate Profile Page features-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.loginFromMenu(myAccountsHM.get("email").toString(), myAccountsHM.get("password").toString()), true, "Login from menu failed.");

            assertEquals(homePageActions.navigateToProfilePage(), true, "Something went wrong in navigating to the Profile Page.");
            logger.info("Navigated to the profile page of the user successfully!");

            assertEquals(profilePageActions.viewAllOrder(), true, "Unable to view all the orders from the profile page.");
            driver.navigate().back();
            logger.info("Validated view all orders from profile page successfully.");

            //TODO : Use API to get flag for address,wallet etc..
            assertEquals(profilePageActions.viewAddresses(true), true, "Unable to view all the addresses from the profile page.");
            logger.info("Validated view address from profile page successfully.");

            assertEquals(profilePageActions.addAddress(), true, "Unable to add address to the user's list from the profile page.");
            driver.navigate().back();
            logger.info("Validated add address from profile page successfully.");

            assertEquals(profilePageActions.viewWalletDetails(true), true, "Unable to view wallet details from the profile page.");
            driver.navigate().back();
            logger.info("Validated wallet details from profile page successfully.");

            assertEquals(profilePageActions.viewSubscriptions(true), true, "Unable to view subscriptions from the profile page.");
            driver.navigate().back();
            logger.info("Validated subscriptions from profile page successfully.");

            assertEquals(profilePageActions.accountSettings(), true, "Unable to view account settings from the profile page.");
            driver.navigate().back();
            logger.info("Validate account settings successfully");

            softAssert.assertEquals(homePageActions.logoutFromMenu(), true, "Logout from menu failed.");
            
            logger.info("-------------------------myAccoutTests concluded successfully.--------------------");
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
     * My Orders Details
     * - moving to user profile
     * - viewing all orders
     */
//    @Test(groups = {"smokeChecklist"}, priority = 16, dataProvider = "myOrdersTests", dataProviderClass = DataProviders.class,testName = "myOrdersTest", description = "SMT-039")
    public void myOrdersTest(HashMap myOrdersHM) {
        try {
            logger.info("-------------------------Running test to validate My Orders details-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.loginFromMenu(myOrdersHM.get("email").toString(), myOrdersHM.get("password").toString()), true, "Login from menu failed.");

            assertEquals(homePageActions.navigateToMyOrdersPage(), true, "Something went wrong in navigating to the My Orders Page.");

            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");
            softAssert.assertEquals(homePageActions.logoutFromMenu(), true, "Logout from menu failed.");
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

    // TODO : SMT-40 && SMT-41 : Toast verification [Cue-tip validated in the tests.] [OR for precise cue-tip : reset the app and verify]

    /**
     * The method includes
     * - login from notification page
     * - sharing the notifications
     * - deleting the notificaition
     * - verifying the count of notification
     */
    @Test(groups = {"smokeChecklist"}, priority = 17, dataProvider = "notificationTests", dataProviderClass = DataProviders.class, testName = "notificationPageTests", description = "SMT-042")
    public void notificationTests(HashMap notificationHM) {
        try {
            logger.info("-------------------------Running test to validate Notification Page features-------------------------");
            softAssert.assertEquals(homePageActions.navigateBackHome(), true, "Navigate back Home failed.");

            softAssert.assertEquals(homePageActions.navigateToNotificationPage(), true, "Navigate to notifications failed.");
            assertEquals(notificationPageActions.loginFromNotificationPage(notificationHM.get("email").toString(), notificationHM.get("password").toString()), true, "Something went wrong in login from notification page.");
            logger.info("Validated logging in from the notification page successfully!");

            assertEquals(notificationPageActions.shareFromNotificationPage(), true, "Something went wrong in sharing from notification page.");
            logger.info("Sharing the notifications if available from notifications page validated successfully.");

            assertEquals(notificationPageActions.deleteNotification(), true, "Something went wrong in deleting the notifications.");
            logger.info("Successfully deleted the notification from notification page.");

            
            logger.info("-------------------------notificationTests concluded successfully.--------------------");
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

    // TODO : SMT-43 Update the Application if available : Requires a push from the API hit

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
    
}