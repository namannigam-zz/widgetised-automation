package page.ios;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;

public class WishlistPageActions extends AppiumBasePage {

    public WishlistPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Method to check Wishlist page is loaded.
     *
     * @return if the Wishlist page is loaded
     * @throws Exception
     */
    public boolean isGuestWishlistPageloaded() throws Exception {
        waitForElementPresent(wishlistPageLocators.get("Login"), 3);
        if (!isElementPresent(getBy(wishlistPageLocators.get("Login")))) {
            logger.warn("Login button not loaded in the Wishlist screen.");
            return false;
        }
        if (!isElementPresent(getBy(wishlistPageLocators.get("ContinueShopping")))) {
            logger.warn("ContinueShopping link not loaded in the Wishlist screen.");
            return false;
        }
        if (!isElementPresent(getBy(wishlistPageLocators.get("WishlistWithoutItem")))) {
            logger.warn("Number of items does not match in the Wishlist screen.");
            return false;
        }
        logger.info("Loaded the Wishlist screen.");
        return true;
    }

    /***
    * Method to check Wishlist page with 1 items is loaded.
    *
    * @return if the Wishlist page with 1 items is loaded
    * @throws Exception
    */
    public boolean isGuestWishlistPageWithItemloaded() throws Exception {
        waitForElementPresent(wishlistPageLocators.get("Login"), 3);
        if (!isElementPresent(getBy(wishlistPageLocators.get("Login")))) {
            logger.warn("Login button not loaded in the Wishlist screen.");
            return false;
        }
        if (!isElementPresent(getBy(wishlistPageLocators.get("WishlistWithItem")))) {
            logger.warn("Number of items does not match in the Wishlist screen.");
            return false;
        }
        if (!isElementPresent(getBy(wishlistPageLocators.get("WishlistCollection")))) {
            logger.warn("Wishlist collection not loaded in the Wishlist screen.");
            return false;
        }
        logger.info("Loaded the Wishlist with items screen.");
        return true;
    }
}