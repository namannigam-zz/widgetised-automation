package test.iOS;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;

/**
 * Created by rathish.kannan on 3/23/16.
 */
public class ProductPageTest_iOS extends BaseTest {
    LoginPageActions loginPageActions;
    SignupPageActions signupPageActions;
    HomePageActions homePageActions;
    SearchPageActions searchPageActions;
    ProfilePageActions profilePageActions;
    CartPageActions cartPageActions;
    CheckoutPageActions checkoutPageActions;
    WishlistPageActions wishlistPageActions;
    BrowsePageActions browsePageActions;
    ProductPageActions productPageActions;

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
        productPageActions.setOtherPageObjects(homePageActions, wishlistPageActions, cartPageActions, checkoutPageActions);
        browsePageActions.setOtherPageObjects(homePageActions, wishlistPageActions, productPageActions);
        System.out.println("\n\n---Running tests to validate product page features---");
    }


    /**
     * The test validates navigation to product page for guest user
     */
    //@Test(groups = {"smoke"}, priority = 1)
    public void navigateToProductPageTest() throws Exception
    {
        System.out.println("\n\nValidate navigation to product page for guest user");
        signupPageActions.skipSignup();
        if(!browsePageActions.isBrowsePageloaded()) {
            String search_str = "television";
            homePageActions.clickHomeButton();
            homePageActions.navigateToSearchPage();
            searchPageActions.navigateToBrowsePage(search_str);
        }
        Assert.assertTrue(browsePageActions.navigateToProductPage(), "Validating navigate to product page for guest user failed");
    }

    /**
     * The test validates adding item to wishlist for guest user
     */
    //@Test(groups = {"smoke"}, priority = 2, dependsOnMethods = "navigateToProductPageTest")
    public void addItemToWishlistTest() throws Exception
    {
        System.out.println("\n\nValidate adding item to wishlist from product page for guest user");
        Assert.assertTrue(productPageActions.addItemToWishlist(), "Validating add item to wishlist from product page for guest user failed");
    }

    /**
     * The test validates remove item from wishlist for guest user
     */
    //@Test(groups = {"smoke"}, priority = 3, dependsOnMethods = "addItemToWishlistTest")
    public void removeItemToWishlistTest() throws Exception
    {
        System.out.println("\n\nValidate removing item from wishlist from product page for guest user");
        Assert.assertTrue(productPageActions.removeItemFromWishlist(), "Validating remove item from wishlist from product page for guest user failed");
    }

    /**
     * The test validates add to cart for guest user
     */
    //@Test(groups = {"smoke"}, priority = 4)
    public void addItemToCartTest() throws Exception
    {
        System.out.println("\n\nValidate adding item to cart from product page for guest user");
        Assert.assertTrue(productPageActions.addItemToCart(), "Validating add item to cart from product page for guest user failed");
    }

    /**
     * The test validates adding same item to cart again for guest user
     */
    //@Test(groups = {"smoke"}, priority = 5, dependsOnMethods = "addItemToCartTest")
    public void addItemAgainToCartTest() throws Exception
    {
        System.out.println("\n\nValidate adding same item to cart again from product page for guest user");
        Assert.assertTrue(productPageActions.addSameItemToCart(), "Validating add same item to cart again from product page for guest user failed");
    }

    /**
     * The test validates Buy Now of item for guest user
     */
    //@Test(groups = {"smoke"}, priority = 6)
    public void buyNowItemTest() throws Exception
    {
        System.out.println("\n\nValidate buy now item from product page for guest user");
        Assert.assertTrue(productPageActions.buyNow(), "Validating buy now from product page for guest user failed");
    }
}
