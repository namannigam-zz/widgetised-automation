package page.ios;

import page.AppiumBasePage;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;

public class ProductPageActions extends AppiumBasePage {
    HomePageActions homePageActions;
    WishlistPageActions wishlistPageActions;
    CartPageActions cartPageActions;
    CheckoutPageActions checkoutPageActions;

    public ProductPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Method to set the other page objects
     */
    public void setOtherPageObjects(HomePageActions hpActions, WishlistPageActions wpActions, CartPageActions cpActions, CheckoutPageActions copActions) {
        homePageActions = hpActions;
        wishlistPageActions = wpActions;
        cartPageActions = cpActions;
        checkoutPageActions = copActions;
    }

    /***
     * Method to check Product page is loaded.
     *
     * @return if the Product page is loaded
     * @throws Exception
     */
    public boolean isProductPageloaded() throws Exception {
        if (!isElementPresent(getBy(productPageLocators.get("ProductDetail")))) {
            logger.warn("Product Detail not loaded in the Product page screen.");
            return false;
        }
        if (!isElementPresent(getBy(productPageLocators.get("ProductName")))) {
            logger.warn("Product Page not loaded in the Product Page screen.");
            return false;
        }
        RemoteWebElement ele = (RemoteWebElement)driver.findElement(getBy(productPageLocators.get("BrowseAddToChat")));
        scrollDown(driver, ele);
        if (!isElementPresent(getBy(productPageLocators.get("BuyNow")))) {
            logger.warn("Buy Now button not loaded in the Product Page screen.");
            return false;
        }
        if (!isElementPresent(getBy(productPageLocators.get("AddToCart")))) {
            logger.warn("Buy Now button not loaded in the Product Page screen.");
            return false;
        }
        if (!isElementPresent(getBy(productPageLocators.get("Wishlist")))) {
            logger.warn("Buy Now button not loaded in the Product Page screen.");
            return false;
        }
        logger.info("Loaded the Product Page screen.");
        return true;
    }

    /***
     * Method to check Product in cart error is loaded.
     *
     * @return if the Product in cart error is loaded
     * @throws Exception
     */
    public boolean isProductInCartErrorloaded() throws Exception {
        if (!isElementPresent(getBy(productPageLocators.get("Info")))) {
            logger.warn("Info not loaded in the Product in cart error screen.");
            return false;
        }
        if (!isElementPresent(getBy(productPageLocators.get("ProductInCart")))) {
            logger.warn("ProductInCart not loaded in the Product in cart error screen.");
            return false;
        }
        if (!isElementPresent(getBy(productPageLocators.get("OKButton")))) {
            logger.warn("Ok Button not loaded in the Product in cart error screen.");
            return false;
        }
        clickOKButton();
        logger.info("Loaded the Product in cart error screen.");
        return true;
    }

    /***
     * Method to scroll down to an element
     *
     * @param driver     Appium driver
     * @param element  Element to which it has to scroll down
     */
    public void scrollDown(AppiumDriver driver, RemoteWebElement element) {
        JavascriptExecutor js = driver;
        HashMap<String, String> scrollObject = new HashMap<>();
        scrollObject.put("direction", "down");
        scrollObject.put("test/element", element.getId());
        js.executeScript("mobile: scroll", scrollObject);
    }

    /***
     * Method to click Buy Now button.
     *
     * @throws Exception
     */
    public void clickBuyNowButton() throws Exception {
        WebElement buyNow = driver.findElement(getBy(productPageLocators.get("BuyNow")));
        buyNow.click();
        logger.info("Clicked Buy Now button");
    }

    /***
     * Method to click Add to Cart Button.
     *
     * @throws Exception
     */
    public void clickAddToCartButton() throws Exception {
        WebElement addToCart = driver.findElement(getBy(productPageLocators.get("AddToCart")));
        addToCart.click();
        logger.info("Clicked Add to Cart Button");
    }

    /***
     * Method to click Add to Wishlist Button.
     *
     * @throws Exception
     */
    public void clickAddToWishlistButton() throws Exception {
        WebElement addToWishlist = driver.findElement(getBy(productPageLocators.get("Wishlist")));
        addToWishlist.click();
        logger.info("Clicked Add to Wishlist Button");
    }

    /***
     * Method to click OK button.
     *
     * @throws Exception
     */
    public void clickOKButton() throws Exception {
        WebElement ok = driver.findElement(getBy(productPageLocators.get("OKButton")));
        ok.click();
        logger.info("Clicked OK button");
    }

    /***
     * Method to Add item to wishlist.
     *
     * @return if the item is added to wishlist
     * @throws Exception
     */
    public boolean addItemToWishlist() throws Exception {
        clickAddToWishlistButton();
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
        clickAddToWishlistButton();
        homePageActions.clickWishlistButton();
        if (wishlistPageActions.isGuestWishlistPageloaded()) {
            homePageActions.clickSearchButton();
            Thread.sleep(3000);
            return true;
        }
        return false;
    }

    /***
     * Method to Add item to Cart.
     *
     * @return if the item is added to cart
     * @throws Exception
     */
    public boolean addItemToCart() throws Exception {
        clickAddToCartButton();
        homePageActions.clickCartButton();
        if (cartPageActions.isCartPageWithItemloaded()) {
            homePageActions.clickSearchButton();
            Thread.sleep(3000);
            return true;
        }
        return false;
    }

    /***
     * Method to Add item to Cart again.
     *
     * @return if the item is added to cart again
     * @throws Exception
     */
    public boolean addSameItemToCart() throws Exception {
        clickAddToCartButton();
        if(isProductInCartErrorloaded()) {
            homePageActions.clickCartButton();
            if (cartPageActions.isCartPageWithItemloaded()) {
                cartPageActions.clickRemoveItemLink();
            }
            return true;
        }
        return false;
    }

    /***
     * Method to Buy Now the item.
     *
     * @return if the item is added to cart and taken to checkout page
     * @throws Exception
     */
    public boolean buyNow() throws Exception {
        clickBuyNowButton();
        if (checkoutPageActions.isGuestCheckoutPageloaded()) {
            homePageActions.clickSearchButton();
            Thread.sleep(3000);
            return true;
        }
        return false;
    }
}