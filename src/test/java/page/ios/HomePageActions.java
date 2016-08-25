package page.ios;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public class HomePageActions extends AppiumBasePage {
    SearchPageActions searchPageActions;
    ProfilePageActions profilePageActions;
    CartPageActions cartPageActions;
    WishlistPageActions wishlistPageActions;

    public HomePageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Method to set the other page objects
     */
    public void setOtherPageObjects(SearchPageActions spActions, ProfilePageActions ppActions, CartPageActions cpActions, WishlistPageActions wpActions) {
        searchPageActions = spActions;
        profilePageActions = ppActions;
        cartPageActions = cpActions;
        wishlistPageActions = wpActions;
    }

    /***
     * Method to check Home page is loaded.
     *
     * @return if the Home page is loaded
     * @throws Exception
     */
    public boolean isHomePageloaded() throws Exception {
        if(isElementPresent(getBy(homePageLocators.get("PingImage")))) {
            driver.findElement(getBy(homePageLocators.get("PingImage"))).click();
        }
        //waitForElementPresent(homePageLocators.get("Home"), 3);
        if (!isElementPresent(getBy(homePageLocators.get("Home")))) {
            logger.warn("Home widget not loaded in the Home screen.");
            return false;
        }
        if (!isElementPresent(getBy(homePageLocators.get("Search")))) {
            logger.warn("Search widget not loaded in the Home screen.");
            return false;
        }
        if (!isElementPresent(getBy(homePageLocators.get("Wishlist")))) {
            logger.warn("Wishlist widget not loaded in the Home screen.");
            return false;
        }
        if (!isElementPresent(getBy(homePageLocators.get("Cart")))) {
            logger.warn("Cart widget not loaded in the Home screen.");
            return false;
        }
        if (!isElementPresent(getBy(homePageLocators.get("Account")))) {
            logger.warn("Account widget not loaded in the Home screen.");
            return false;
        }
        logger.info("Loaded the Home screen.");
        return true;

    }

    /***
     * Method to click Home button.
     *
     * @throws Exception
     */
    public void clickHomeButton() throws Exception {
        WebElement home = driver.findElement(getBy(homePageLocators.get("Home")));
        home.click();
        logger.info("Clicked home button");
    }

    /***
     * Method to click Search button.
     *
     * @throws Exception
     */
    public void clickSearchButton() throws Exception {
        WebElement search = driver.findElement(getBy(homePageLocators.get("SearchWidget")));
        search.click();
        logger.info("Clicked search button");
    }

    /***
     * Method to click Wishlist button.
     *
     * @throws Exception
     */
    public void clickWishlistButton() throws Exception {
        WebElement wishlist = driver.findElement(getBy(homePageLocators.get("Wishlist")));
        wishlist.click();
        logger.info("Clicked wishlist button");
    }

    /***
     * Method to click Cart button.
     *
     * @throws Exception
     */
    public void clickCartButton() throws Exception {
        WebElement cart = driver.findElement(getBy(homePageLocators.get("Cart")));
        cart.click();
        logger.info("Clicked cart button");
    }

    /***
     * Method to click Account button.
     *
     * @throws Exception
     */
    public void clickAccountButton() throws Exception {
        WebElement account = driver.findElement(getBy(homePageLocators.get("Account")));
        account.click();
        logger.info("Clicked account button");
    }

    /***
     * Method to click Back button.
     *
     * @throws Exception
     */
    public void clickBackButton() throws Exception {
        WebElement back = driver.findElement(getBy(homePageLocators.get("BackButton")));
        back.click();
        logger.info("Clicked back button");
    }

    /***
     * Method to click Deals of the Day link.
     *
     * @throws Exception
     */
    public void clickDOTDButton() throws Exception {
        WebElement dotd = driver.findElement(getBy(homePageLocators.get("DOTDButton")));
        dotd.click();
        logger.info("Clicked 'Deals of the day' button");
    }

    /***
     * Method to click Offers link.
     *
     * @throws Exception
     */
    public void clickOffersButton() throws Exception {
        WebElement offers = driver.findElement(getBy(homePageLocators.get("OffersButton")));
        offers.click();
        logger.info("Clicked Offers button");
    }

    /***
     * Method to click Matches to Catches link.
     *
     * @throws Exception
     */
    public void clickMCLink() throws Exception {
        WebElement mc = driver.findElement(getBy(homePageLocators.get("MatchesToCatches")));
        mc.click();
        logger.info("Clicked 'Matches to Catches' link");
    }

    /***
     * Method to click Home and Furniture link.
     *
     * @throws Exception
     */
    public void clickHFLink() throws Exception {
        WebElement hf = driver.findElement(getBy(homePageLocators.get("HomenFurniture")));
        hf.click();
        logger.info("Clicked 'Home & Furniture' link");
    }

    /***
     * Method to navigate to Home Page.
     *
     * @return if the home page is loaded
     * @throws Exception
     */
    public boolean navigateToHomePage() throws Exception {
        clickHomeButton();
        return isHomePageloaded();
    }

    /***
     * Method to navigate to search Page.
     *
     * @return if the search page is loaded
     * @throws Exception
     */
    public boolean navigateToSearchPage() throws Exception {
        clickSearchButton();
        return searchPageActions.isSearchPageloaded();
    }

    /***
     * Method to navigate to wishlist Page.
     *
     * @return if the wishlist page is loaded
     * @throws Exception
     */
    public boolean navigateToWishlistPage() throws Exception {
        clickWishlistButton();
        return wishlistPageActions.isGuestWishlistPageloaded();
    }

    /***
     * Method to navigate to cart Page.
     *
     * @return if the cart page is loaded
     * @throws Exception
     */
    public boolean navigateToCartPage() throws Exception {
        clickCartButton();
        return cartPageActions.isCartPageloaded();
    }

    /***
     * Method to navigate to profile Page.
     *
     * @param userType Guest User/LoggedIn user
     * @return if the profile page is loaded
     * @throws Exception
     */
    public boolean navigateToProfilePage(String userType) throws Exception {
        clickAccountButton();
        if(userType.equalsIgnoreCase("Guest")) {
            return profilePageActions.isGuestProfilePageloaded();
        } else if(userType.equals("LoggedIn")) {
            return profilePageActions.isLoggedinProfilePageloaded();
        }
        return false;
    }

    /***
     * Method to navigate to DOTD Page.
     *
     * @return if the DOTD page is loaded
     * @throws Exception
     */
    public boolean navigateDOTDPage() throws Exception {
        clickHomeButton();
        clickDOTDButton();
        Thread.sleep(1000);
        if(isElementPresent(getBy(homePageLocators.get("DOTD"))) && isElementPresent(getBy(homePageLocators.get("DOTDCollectionView")))) {
            logger.info("Loaded 'Deals of the Day' page");
            clickBackButton();
            return true;
        }
        logger.warn("'Deals of the Day' page not loaded");
        return false;
    }

    /***
     * Method to navigate to Offers Page.
     *
     * @return if the Offers page is loaded
     * @throws Exception
     */
    public boolean navigateOffersPage() throws Exception {
        clickHomeButton();
        clickOffersButton();
        Thread.sleep(1000);
        if(isElementPresent(getBy(homePageLocators.get("OfferZone"))) && isElementPresent(getBy(homePageLocators.get("OffersHeader"))) && isElementPresent(getBy(homePageLocators.get("OffersScrollView")))) {
            logger.info("Loaded Offers page");
            clickBackButton();
            return true;
        }
        logger.warn("Offers page not loaded");
        return false;
    }

    /***
     * Method to navigate to Matches to Catches  Page.
     *
     * @return if the Matches to Catches page is loaded
     * @throws Exception
     */
    public boolean navigateMCPage() throws Exception {
        clickHomeButton();
        RemoteWebElement ele = (RemoteWebElement)driver.findElement(getBy(homePageLocators.get("DOTD")));
        profilePageActions.scrollDown(driver, ele);
        clickMCLink();
        Thread.sleep(1000);
        if(isElementPresent(getBy(homePageLocators.get("MatchesToCatches"))) && isElementPresent(getBy(homePageLocators.get("MCCollectionView")))) {
            logger.info("Loaded 'Matches to Catches' page");
            clickBackButton();
            return true;
        }
        logger.warn("'Matches to Catches' page not loaded");
        return false;
    }

    /***
     * Method to navigate to Home and Furniture  Page.
     *
     * @return if the Home and Furniture page is loaded
     * @throws Exception
     */
    public boolean navigateHFPage() throws Exception {
        clickHomeButton();
        RemoteWebElement ele = (RemoteWebElement)driver.findElement(getBy(homePageLocators.get("MatchesToCatches")));
        profilePageActions.scrollDown(driver, ele);
        clickHFLink();
        Thread.sleep(1000);
        if(isElementPresent(getBy(homePageLocators.get("HomenFurniture"))) && isElementPresent(getBy(homePageLocators.get("HFCollectionView")))) {
            logger.info("Loaded 'Home & Furniture' page");
            clickBackButton();
            return true;
        }
        logger.warn("'Home & Furniture' page not loaded");
        return false;
    }
}