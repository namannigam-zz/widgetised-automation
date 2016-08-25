package test.iOS;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by rathish.kannan on 3/23/16.
 */
public class BrowsePageTest_iOS extends BaseTest {
    LoginPageActions loginPageActions;
    SignupPageActions signupPageActions;
    HomePageActions homePageActions;
    SearchPageActions searchPageActions;
    ProfilePageActions profilePageActions;
    CartPageActions cartPageActions;
    WishlistPageActions wishlistPageActions;
    BrowsePageActions browsePageActions;
    ProductPageActions productPageActions;
    CheckoutPageActions checkoutPageActions;

    @BeforeTest
    public void setUp()
    {
        loginPageActions = new LoginPageActions(driver);
        signupPageActions = new SignupPageActions(driver);
        homePageActions = new HomePageActions(driver);
        searchPageActions = new SearchPageActions(driver);
        profilePageActions = new ProfilePageActions(driver);
        cartPageActions = new CartPageActions(driver);
        checkoutPageActions = new CheckoutPageActions(driver);
        wishlistPageActions = new WishlistPageActions(driver);
        browsePageActions = new BrowsePageActions(driver);
        productPageActions = new ProductPageActions(driver);
        signupPageActions.setOtherPageObjects(homePageActions);
        loginPageActions.setOtherPageObjects(homePageActions, signupPageActions, profilePageActions);
        homePageActions.setOtherPageObjects(searchPageActions, profilePageActions, cartPageActions, wishlistPageActions);
        searchPageActions.setOtherPageObjects(browsePageActions);
        browsePageActions.setOtherPageObjects(homePageActions, wishlistPageActions, productPageActions);
        productPageActions.setOtherPageObjects(homePageActions, wishlistPageActions, cartPageActions, checkoutPageActions);
        System.out.println("\n\n---Running tests to validate browse page features---");
    }


    /**
     * The test validates navigation to browse page for guest user
     */
    @Test(groups = {"smoke"}, priority = 1)
    public void navigateToBrowsePageTest() throws Exception
    {
        System.out.println("\n\nValidate navigation to browse page for guest user");
        String search_str = "television";
        signupPageActions.skipSignup();
        homePageActions.clickHomeButton();
        homePageActions.navigateToSearchPage();
        Assert.assertTrue(searchPageActions.navigateToBrowsePage(search_str), "Validating navigate to browse page for guest user failed");
    }

    /**
     * The test validates navigation to filter options for guest user
     */
    @Test(groups = {"smoke"}, priority = 2, dependsOnMethods = "navigateToBrowsePageTest")
    public void navigateToFilterOptionsTest() throws Exception
    {
        System.out.println("\n\nValidate navigation to filter options for guest user");
        Assert.assertTrue(browsePageActions.navigateToFiltersPage(), "Validating navigate to filter options for guest user failed");
    }

    /**
     * The test validates navigation to sort options for guest user
     */
    @Test(groups = {"smoke"}, priority = 3, dependsOnMethods = "navigateToFilterOptionsTest")
    public void navigateToSortOptionsTest() throws Exception
    {
        System.out.println("\n\nValidate navigation to sort options for guest user");
        Assert.assertTrue(browsePageActions.navigateToSortOptionsPage(), "Validating navigate to sort options for guest user failed");
    }

    /**
     * The test validates add item to wishlist from browse page for guest user
     */
    //@Test(groups = {"smoke"}, priority = 4, dependsOnMethods = "navigateToSortOptionsTest")
    public void addItemToWishlistTest() throws Exception
    {
        System.out.println("\n\nValidate add item to wishlist from browse page for guest user");
        Assert.assertTrue(browsePageActions.addItemToWishlist(), "Validating add item to wishlist from browse page for guest user failed");
    }

    /**
     * The test validates remove item from wishlist from browse page for guest user
     */
    //@Test(groups = {"smoke"}, priority = 5, dependsOnMethods = "addItemToWishlistTest")
    public void removeItemFromWishlistTest() throws Exception
    {
        System.out.println("\n\nValidate remove item from wishlist from browse page for guest user");
        Assert.assertTrue(browsePageActions.removeItemFromWishlist(), "Validating remove item from wishlist from browse page for guest user failed");
    }
}
