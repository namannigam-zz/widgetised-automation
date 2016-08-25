package test.iOS;

import mobile.util.DataProviders;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Created by rathish.kannan on 3/22/16.
 */
public class HomePageTest_iOS extends BaseTest {
    LoginPageActions loginPageActions;
    SignupPageActions signupPageActions;
    HomePageActions homePageActions;
    SearchPageActions searchPageActions;
    ProfilePageActions profilePageActions;
    CartPageActions cartPageActions;
    WishlistPageActions wishlistPageActions;

    @BeforeTest
    public void setUp()
    {
        loginPageActions = new LoginPageActions(driver);
        signupPageActions = new SignupPageActions(driver);
        homePageActions = new HomePageActions(driver);
        searchPageActions = new SearchPageActions(driver);
        profilePageActions = new ProfilePageActions(driver);
        cartPageActions = new CartPageActions(driver);
        wishlistPageActions = new WishlistPageActions(driver);
        signupPageActions.setOtherPageObjects(homePageActions);
        loginPageActions.setOtherPageObjects(homePageActions, signupPageActions, profilePageActions);
        homePageActions.setOtherPageObjects(searchPageActions, profilePageActions, cartPageActions, wishlistPageActions);
        System.out.println("\n\n---Running tests to validate homepage features---");
     }

    /**
     * The test validates navigation to offers page for guest user
     */
    @Test(groups = {"smoke"}, priority = 1)
    public void OfferPageTest() throws Exception
    {
        System.out.println("\n\nValidate navigation to offers page for guest user");
        if(signupPageActions.isSignUpPageloaded()) {
            signupPageActions.skipSignup();
        }
        Assert.assertTrue(homePageActions.navigateOffersPage(), "Validating the navigation to offer page failed");
    }

    /**
     * The test validates navigation to DOTD page for guest user
     */
    @Test(groups = {"smoke"}, priority = 2)
    public void dotdPageTest() throws Exception
    {
        System.out.println("\n\nValidate navigation to DOTD page for guest user");
        if(signupPageActions.isSignUpPageloaded()) {
            signupPageActions.skipSignup();
        }
        Assert.assertTrue(homePageActions.navigateDOTDPage());
    }

    /**
     * The test validates navigation to offers page for logged in user
     *
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 3, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void loggedInOfferPageTests(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate navigation to offer page for logged in user");
        if(signupPageActions.isSignUpPageloaded()) {
            signupPageActions.skipSignup();
        }
        Assert.assertTrue(loginPageActions.loginFromProfile(loginHM.get("mobile_login").toString(), loginHM.get("mobile_password").toString()), "Validating mobile login failed");
        Assert.assertTrue(homePageActions.navigateOffersPage());
    }

    /**
     * The test validates navigation to DOTD page for logged in user
     */
    @Test(groups = {"smoke"}, priority = 4, dependsOnMethods = "loggedInOfferPageTests")
    public void loggedInDotdPageTests() throws Exception
    {
        System.out.println("\n\nValidate navigation to DOTD page for logged in user");
        Assert.assertTrue(homePageActions.navigateDOTDPage());
        homePageActions.navigateToProfilePage("LoggedIn");
        profilePageActions.logout();
    }
}
