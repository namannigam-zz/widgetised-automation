package test.iOS;

import mobile.util.DataProviders;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Created by rathish.kannan on 3/22/16.
 */
public class SignupPageTest_iOS extends BaseTest {
    SignupPageActions signupPageActions;
    HomePageActions homePageActions;
    SearchPageActions searchPageActions;
    ProfilePageActions profilePageActions;
    CartPageActions cartPageActions;
    WishlistPageActions wishlistPageActions;

    @BeforeTest
    public void setUp()
    {
        signupPageActions = new SignupPageActions(driver);
        homePageActions = new HomePageActions(driver);
        signupPageActions.setOtherPageObjects(homePageActions);
        homePageActions.setOtherPageObjects(searchPageActions, profilePageActions, cartPageActions, wishlistPageActions);
        System.out.println("\n\n---Running tests to validate sign up on new app---");
    }

    /**
     * This test checks for signup for the existing users
     * It relies on the error message that appears on signup page
     *
     * @param credentialsHM User login credentials
     * @param signUpHM        data provider passes existing_user_mobile_number value to the test
     */
    @Test(groups = {"smoke"}, priority = 1, dataProvider = "signUpTests", dataProviderClass = DataProviders.class)
    public void existingUserSignupTest(HashMap credentialsHM, HashMap signUpHM) throws Exception
    {
        System.out.println("\n\nValidate sign up for existing users");
        Assert.assertTrue(signupPageActions.invalidSignup(signUpHM.get("existing_user_mobile_number").toString(), "AccountExists"), "Validating sign up for already existing account failed");
    }

}
