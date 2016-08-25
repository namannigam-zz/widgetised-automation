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
 * Created by naman.nigam on 02/03/16.
 */
public class CheckOutTest extends BaseTest {

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
		System.out.println("************************ STARTING  :: " + className[className.length - 1] +
				" ***********************");
	}

	/**
	 * Tests moving to buy now option on the checkout page
	 */
	@Test(groups = {"checkoutTests"})
	public void buyNowTest() {
		try {
			methodStartConsole(Thread.currentThread().getStackTrace());
			assertTrue(homePageActions.navigateBackHome());
			assertTrue(homePageActions.navigateToSearchPage());
			assertTrue(searchPageActions.search("mobiles"));
			assertTrue(browsePageActions.selectAnyItemFromSearchedList());
			assertEquals(productPageActions.productBuyNow(), true, "Something went wrong in moving to buy now field.");
			logger.info("Proceeded with Buy Now option from the product page successfully!");
		} catch (NullPointerException | InterruptedException | TimeoutException testException) {
			if (testException instanceof TimeoutException) {
				logger.warn(
						"Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() +
								" for the failure.");
				logger.warn(testException.getCause());
			} else {
				logger.warn(
						"NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
				testException.printStackTrace();
			}
			assert (false);
		}
	}

	/**
	 * Tests login from the checkout page for a logged out checkout proceeded
	 *
	 * @param credentialsHM   users credentials to check in
	 * @param checkoutStrings product's vertical to perform the checkout for
	 */
	@Test(groups = {
			"checkoutTests"}, dataProvider = "checkoutTests", dataProviderClass = DataProviders.class, dependsOnMethods = "buyNowTest")
	public void loginFromCheckoutTest(HashMap credentialsHM, String[] checkoutStrings) {
		try {
			methodStartConsole(Thread.currentThread().getStackTrace());
			assertEquals(checkoutPageActions.loginFromCheckoutPage(credentialsHM.get("email").toString(),
					credentialsHM.get("password").toString()), true,
					"Something went wrong while signing in from checkout page.");
			logger.info("Logged in from checkout page was successful.");
		} catch (NullPointerException | InterruptedException | TimeoutException testException) {
			if (testException instanceof TimeoutException) {
				logger.warn(
						"Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() +
								" for the failure.");
				logger.warn(testException.getCause());
			} else {
				logger.warn(
						"NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
				testException.printStackTrace();
			}
			assert (false);
		}
	}

	/**
	 * Tests adding address on the checkout flow for delivery options
	 */
	@Test(groups = {"checkoutTests"}, dependsOnMethods = "loginFromCheckoutTest")
	public void addAddressOnCheckout() {
		try {
			methodStartConsole(Thread.currentThread().getStackTrace());
			assertEquals(checkoutPageActions.addressOnCheckout(), true,
					"Something went wrong during adding address during checkout on purchase.");
			logger.info("Address added on checkout page successfully.");
		} catch (NullPointerException | InterruptedException | TimeoutException testException) {
			if (testException instanceof TimeoutException) {
				logger.warn(
						"Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() +
								" for the failure.");
				logger.warn(testException.getCause());
			} else {
				logger.warn(
						"NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
				testException.printStackTrace();
			}
			assert (false);
		}
	}

	/**
	 * Tests proceeding to the COD option on checkout but not placing the order
	 */
	@Test(groups = {"checkoutTests"}, dependsOnMethods = "addAddressOnCheckout")
	public void cashOnDeliveryTest() {
		try {
			methodStartConsole(Thread.currentThread().getStackTrace());
			assertEquals(checkoutPageActions.continueToPay_COD(), true,
					"Something went wrong while proceeding to COD.");
			logger.info("Continued to Pay COD on checkout successfully.");
			assertTrue(homePageActions.navigateBackHome());
			assertTrue(homePageActions.logoutFromMenu());
		} catch (NullPointerException | InterruptedException | TimeoutException testException) {
			if (testException instanceof TimeoutException) {
				logger.warn(
						"Please read the Element Info** and verify the Screenshot around " + common.systemDateTime() +
								" for the failure.");
				logger.warn(testException.getCause());
			} else {
				logger.warn(
						"NullPointer or Interrupted Exception occurred. Look into the stacktrace for more details.");
				testException.printStackTrace();
			}
			assert (false);
		}
	}

	@AfterTest
	public void testEndConsole() {
		String[] className = Thread.currentThread().getStackTrace()[1].getClassName().split("\\.");
		System.out.println("************************ COMPLETED :: " + className[className.length - 1] +
				" ***********************");
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