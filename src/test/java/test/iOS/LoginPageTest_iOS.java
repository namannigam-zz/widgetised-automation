package test.iOS;

import mobile.util.DataProviders;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Created by rathish.kannan on 3/22/16.
 */
public class LoginPageTest_iOS extends BaseTest {
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
        System.out.println("\n\n---Running tests to validate login features---");
    }

    /**
     * The test validates login using invalid email format
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 1, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void invalidEmailLoginTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate login with invalid email format");
        Assert.assertTrue(loginPageActions.invalidLogin("test@test.test", credentialsHM.get("password").toString(), "InvalidEmailMobile"), "Validating login with invalid email format failed");
    }

    /**
     * The test validates login using non existing email user
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 2, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void nonExistingEmailLoginTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate login with non existing email account");
        Assert.assertTrue(loginPageActions.invalidLogin("1@test.com", credentialsHM.get("password").toString(), "InvalidAccount"), "Validating login with invalid email account failed");
    }

    /**
     * The test validates login using valid email user and invalid password
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 3, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void invalidPasswordEmailLoginTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate login with valid email account and invalid password");
        Assert.assertTrue(loginPageActions.invalidLogin(credentialsHM.get("email").toString(), "test/test", "InvalidPassword"), "Validating login with invalid password for email account failed");
    }

    /**
     * The test validates login using invalid mobile number
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 4, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void invalidMobileLoginTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate login with invalid mobile number");
        Assert.assertTrue(loginPageActions.invalidLogin("1000000001", loginHM.get("mobile_password").toString(), "InvalidAccount"), "Validating login with invalid mobile account failed");
    }

    /**
     * The test validates login using valid mobile user and invalid password
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 5, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void invalidPasswordMobileLoginTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate login with valid mobile account and invalid password");
        Assert.assertTrue(loginPageActions.invalidLogin(loginHM.get("mobile_login").toString(), "test/test", "InvalidPassword"), "Validating login with invalid password for mobile account failed");
    }

    /**
     * The test validates show password in login page
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 6, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void showPasswordTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate show password");
        Assert.assertTrue(loginPageActions.showPassword(loginHM.get("mobile_login").toString(), loginHM.get("mobile_password").toString()), "Validating show password failed");
    }

    /**
     * The test validates hide password in login page
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 7, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void hidePasswordTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate hide password");
        Assert.assertTrue(loginPageActions.hidePassword(loginHM.get("mobile_login").toString(), loginHM.get("mobile_password").toString()), "Validating hide password failed");
    }

    /**
     * The test validates forgot password for invalid mobile user
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 8, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void invalidMobileForgotPassTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate forgot password with invalid mobile number");
        Assert.assertTrue(loginPageActions.invalidForgotPassword(loginHM.get("mobile_login").toString(), "InvalidMobileAccount"), "Validating forgot password for invalid mobile user failed");
    }

    /**
     * The test validates forgot password for invalid mobile user
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 9, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void invalidAccountForgotPassTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate forgot password with invalid mobile number");
        Assert.assertTrue(loginPageActions.invalidForgotPassword("1000000001", "InvalidAccount"), "Validating forgot password for invalid mobile user failed");
    }

    /**
     * The test validates forgot password for invalid mobile user
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 10, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void invalidEmailForgotPassTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate forgot password with invalid email user");
        Assert.assertTrue(loginPageActions.invalidForgotPassword("test@test.test", "InvalidEmailAccount"), "Validating forgot password for invalid email user failed");
    }

    /**
     * The test validates forgot password multiple attempts
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    //@Test(groups = {"smoke"}, priority = 11, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void multipleForgotPasswordTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate max attempts for forgot password of email user failed");
        Assert.assertTrue(loginPageActions.invalidForgotPassword(credentialsHM.get("email").toString(), "MaxAttempts"), "Validating max attempts for forgot password of email user failed");
    }

    /**
     * The test validates forgot password for mobile user
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 12, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void mobileForgotPasswordTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate forgot password of mobile user");
        Assert.assertTrue(loginPageActions.forgotPassword("1000000000", "MobileAccount"), "Validating forgot password of mobile user failed");
    }

    /**
     * The test validates forgot password for email user
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 13, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void emailForgotPasswordTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate forgot password of email user");
        Assert.assertTrue(loginPageActions.forgotPassword("seltestautomation@gmail.com", "EmailAccount"), "Validating forgot password for email user failed");
    }

    /**
     * The test validates email login
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 14, dataProvider = "loginTests", dataProviderClass = DataProviders.class)
    public void emailLoginTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate email login");
        Assert.assertTrue(loginPageActions.login(credentialsHM.get("email").toString(), credentialsHM.get("password").toString()), "Validating email login failed");
    }

    /**
     * The test validates logout
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 15, dataProvider = "loginTests", dataProviderClass = DataProviders.class, dependsOnMethods = "emailLoginTest")
    public void logoutTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate logout");
        homePageActions.navigateToProfilePage("LoggedIn");
        Assert.assertTrue(profilePageActions.logout(), "Logout Failed for mobile login");
    }

    /**
     * The test validates mobile login from profile page
     * @param credentialsHM  data provider passes email login credentials to the test
     * @param loginHM        data provider passes mobile login credentials to the test
     */
    @Test(groups = {"smoke"}, priority = 16, dataProvider = "loginTests", dataProviderClass = DataProviders.class, dependsOnMethods = "logoutTest")
    public void mobileloginTest(HashMap credentialsHM, HashMap loginHM) throws Exception
    {
        System.out.println("\n\nValidate mobile login from profile");
        Assert.assertTrue(loginPageActions.loginFromProfile(loginHM.get("mobile_login").toString(), loginHM.get("mobile_password").toString()), "Validating mobile login from profile failed");
        homePageActions.navigateToProfilePage("LoggedIn");
        Assert.assertTrue(profilePageActions.logout(), "Logout Failed for mobile login");
    }
}
