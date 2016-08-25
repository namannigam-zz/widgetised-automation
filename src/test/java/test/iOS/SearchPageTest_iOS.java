package test.iOS;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by rathish.kannan on 3/23/16.
 */
public class SearchPageTest_iOS extends BaseTest {
    LoginPageActions loginPageActions;
    SignupPageActions signupPageActions;
    HomePageActions homePageActions;
    SearchPageActions searchPageActions;
    ProfilePageActions profilePageActions;
    CartPageActions cartPageActions;
    WishlistPageActions wishlistPageActions;
    BrowsePageActions browsePageActions;

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
        browsePageActions = new BrowsePageActions(driver);
        signupPageActions.setOtherPageObjects(homePageActions);
        loginPageActions.setOtherPageObjects(homePageActions, signupPageActions, profilePageActions);
        homePageActions.setOtherPageObjects(searchPageActions, profilePageActions, cartPageActions, wishlistPageActions);
        searchPageActions.setOtherPageObjects(browsePageActions);
        System.out.println("\n\n---Running tests to validate search features---");
    }


    /**
     * The test validates navigation to search page for guest user
     */
    @Test(groups = {"smoke"}, priority = 1)
    public void navigateToSearchTest() throws Exception
    {
        System.out.println("\n\nValidate navigation to search page for guest user");
        signupPageActions.skipSignup();
        homePageActions.clickHomeButton();
        Assert.assertTrue(homePageActions.navigateToSearchPage(), "Validating navigate to search page for guest user failed");
    }

    /**
     * The test validates searching the product for guest user
     */
    @Test(groups = {"smoke"}, priority = 2, dependsOnMethods = "navigateToSearchTest")
    public void searchProductTest() throws Exception
    {
        System.out.println("\n\nValidate searching the product for guest user");
        String search_str = "watch";
        Assert.assertTrue(searchPageActions.search(search_str), "Validating searching the product for guest user failed");
    }

    /**
     * The test validates clearing the entered search text for guest user
     */
    @Test(groups = {"smoke"}, priority = 3, dependsOnMethods = "searchProductTest")
    public void clearSearchTest() throws Exception
    {
        System.out.println("\n\nValidate clearing the entered search text for guest user");
        String search_str = "shoes";
        Assert.assertTrue(searchPageActions.clearSearchString(search_str), "Validating clear search text for guest user failed");
    }

    /**
     * The test validates search guide for the entered search text for guest user
     */
    @Test(groups = {"smoke"}, priority = 4, dependsOnMethods = "clearSearchTest")
    public void searchGuideTest() throws Exception
    {
        System.out.println("\n\nValidate search guide for guest user");
        String search_str = "jeans";
        Assert.assertTrue(searchPageActions.searchGuide(search_str), "Validating search guide for guest user failed");
    }

    /**
     * The test validates autofill from search guide for guest user
     */
    @Test(groups = {"smoke"}, priority = 5, dependsOnMethods = "searchGuideTest")
    public void searchGuideAutoFillTest() throws Exception
    {
        System.out.println("\n\nValidate autofill from search guide for guest user");
        String search_str = "shoes";
        Assert.assertTrue(searchPageActions.searchGuideAutofill(search_str), "Validating autofill from search guide for guest user failed");
    }

    /**
     * The test validates clearing search history
     */
    @Test(groups = {"smoke"}, priority = 6, dependsOnMethods = "searchGuideAutoFillTest")
    public void clearSearchHistoryTest() throws Exception
    {
        System.out.println("\n\nValidate clear search history for guest user");
        Assert.assertTrue(searchPageActions.clearSearchHistory(), "Validating clear search history for guest user failed");
    }
}
