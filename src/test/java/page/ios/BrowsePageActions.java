package page.ios;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BrowsePageActions extends AppiumBasePage {
    HomePageActions homePageActions;
    WishlistPageActions wishlistPageActions;
    ProductPageActions productPageActions;

    public BrowsePageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Method to set the other page objects
     */
    public void setOtherPageObjects(HomePageActions hpActions, WishlistPageActions wpActions, ProductPageActions ppActions) {
        homePageActions = hpActions;
        wishlistPageActions = wpActions;
        productPageActions = ppActions;
    }

    /***
     * Method to check Search Results page is loaded.
     *
     * @return if the Search Results page is loaded
     * @throws Exception
     */
    public boolean isBrowsePageloaded() throws Exception {
        if(isElementPresent(getBy(browsePageLocators.get("SortText")))) {
            driver.findElementByClassName("UIAWindow").click();
        }
        if(isElementPresent(getBy(browsePageLocators.get("AllowButton")))) {
            driver.findElement(getBy(browsePageLocators.get("AllowButton"))).click();
        }
        if (!isElementPresent(getBy(browsePageLocators.get("FilterOption")))) {
            logger.warn("Filter Option not loaded in the Search Results screen.");
            return false;
        }
        if (!isElementPresent(getBy(browsePageLocators.get("SortByOption")))) {
            logger.warn("SortBy Option not loaded in the Search Results screen.");
            return false;
        }
        if (!isElementPresent(getBy(browsePageLocators.get("BrowsePage")))) {
            logger.warn("CollectionView field not loaded in the Search Results screen.");
            return false;
        }
        logger.info("Loaded the Browse Page screen.");
        return true;
    }

    /***
     * Method to check Filter Options page is loaded.
     *
     * @return if the Filter Options page is loaded
     * @throws Exception
     */
    public boolean isFilterPageloaded() throws Exception {
        if (!isElementPresent(getBy(browsePageLocators.get("SubCategories")))) {
            logger.warn("SubCategories not loaded in the Filter screen.");
            return false;
        }
        if (!isElementPresent(getBy(browsePageLocators.get("Fulfilledby")))) {
            logger.warn("Fulfilled by not loaded in the Filter screen.");
            return false;
        }
        if (!isElementPresent(getBy(browsePageLocators.get("Brand")))) {
            logger.warn("Brand  not loaded in the Filter screen.");
            return false;
        }
        logger.info("Loaded the Filter screen.");
        return true;
    }

    /***
     * Method to check Sort Options page is loaded.
     *
     * @return if the Sort Options page is loaded
     * @throws Exception
     */
    public boolean isSortOptionsloaded() throws Exception {
        if (!isElementPresent(getBy(browsePageLocators.get("SortByPicker")))) {
            logger.warn("Sort By Picker not loaded in the Sort Options screen.");
            return false;
        }
        logger.info("Loaded the Sort options screen.");
        return true;
    }

    /***
     * Method to click Back button.
     *
     * @throws Exception
     */
    public void clickBackButton() throws Exception {
        WebElement back = driver.findElement(getBy(browsePageLocators.get("BackButton")));
        back.click();
        logger.info("Clicked Back button");
    }

    /***
     * Method to click Cancel button.
     *
     * @throws Exception
     */
    public void clickCancelButton() throws Exception {
        WebElement cancel = driver.findElement(getBy(browsePageLocators.get("CancelButton")));
        cancel.click();
        logger.info("Clicked Cancel button");
    }

    /***
     * Method to click Filter Option.
     *
     * @throws Exception
     */
    public void clickFilterOption() throws Exception {
        WebElement filter = driver.findElement(getBy(browsePageLocators.get("FilterOption")));
        filter.click();
        logger.info("Clicked Filter Option");
    }

    /***
     * Method to click Sort Option.
     *
     * @throws Exception
     */
    public void clickSortOption() throws Exception {
        WebElement sort = driver.findElement(getBy(browsePageLocators.get("SortByOption")));
        sort.click();
        logger.info("Clicked Sort Option");
    }

    /***
     * Method to click Wishlist Button.
     *
     * @throws Exception
     */
    public void clickWishlistButton() throws Exception {
        String wishlist_xpath = browsePageLocators.get("FirstCell") + "/UIAButton[1]";
        WebElement wishlist = driver.findElement(getBy(wishlist_xpath));
        wishlist.click();
        logger.info("Clicked Wishlist Button");
    }

    /***
     * Method to click First Item.
     *
     * @throws Exception
     */
    public void clickFirstItem() throws Exception {
        WebElement firstCell = driver.findElement(getBy(browsePageLocators.get("BrowsePage")));
        firstCell.click();
        logger.info("Clicked First Item");
    }

    /***
     * Method to navigate to Filters Page.
     *
     * @return if the filter page is loaded
     * @throws Exception
     */
    public boolean navigateToFiltersPage() throws Exception {
        clickFilterOption();
        if (isFilterPageloaded()) {
            clickCancelButton();
            Thread.sleep(3000);
            return true;
        }
        return false;
    }

    /***
     * Method to navigate to Sort Options Page.
     *
     * @return if the sort options page is loaded
     * @throws Exception
     */
    public boolean navigateToSortOptionsPage() throws Exception {
        clickSortOption();
        if (isSortOptionsloaded()) {
            clickCancelButton();
            Thread.sleep(3000);
            return true;
        }
        return false;
    }

    /***
     * Method to Add item to wishlist.
     *
     * @return if the item is added to wishlist
     * @throws Exception
     */
    public boolean addItemToWishlist() throws Exception {
        clickWishlistButton();
        homePageActions.clickWishlistButton();
        if (wishlistPageActions.isGuestWishlistPageWithItemloaded()) {
            homePageActions.clickSearchButton();
            Thread.sleep(3000);
            return true;
        }
        return false;
    }

    /***
     * Method to Remove item from wishlist.
     *
     * @return if the item is removed from wishlist
     * @throws Exception
     */
    public boolean removeItemFromWishlist() throws Exception {
        clickWishlistButton();
        homePageActions.clickWishlistButton();
        if (wishlistPageActions.isGuestWishlistPageloaded()) {
            homePageActions.clickSearchButton();
            Thread.sleep(3000);
            return true;
        }
        return false;
    }

    /***
     * Method to navigate to Product Page.
     *
     * @return if the product page is loaded
     * @throws Exception
     */
    public boolean navigateToProductPage() throws Exception {
        clickFirstItem();
        return productPageActions.isProductPageloaded();
    }
}