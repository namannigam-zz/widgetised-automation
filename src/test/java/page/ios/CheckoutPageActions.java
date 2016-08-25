package page.ios;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPageActions extends AppiumBasePage {

    public CheckoutPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Method to check Guest Checkout page is loaded.
     *
     * @return if the guest checkout page is loaded
     * @throws Exception
     */
    public boolean isGuestCheckoutPageloaded() throws Exception {
        if (!isElementPresent(getBy(checkOutPageLocators.get("Login")))) {
            logger.warn("Login not loaded in the Checkout screen.");
            return false;
        }
        if (!isElementPresent(getBy(checkOutPageLocators.get("Delivery")))) {
            logger.warn("Delivery not loaded in the Checkout screen.");
            return false;
        }
        if (!isElementPresent(getBy(checkOutPageLocators.get("Payment")))) {
            logger.warn("Payment not loaded in the Checkout screen.");
            return false;
        }
        if (!isElementPresent(getBy(checkOutPageLocators.get("EmailMobile")))) {
            logger.warn("EmailMobile not loaded in the Checkout screen.");
            return false;
        }
        if (!isElementPresent(getBy(checkOutPageLocators.get("Continue")))) {
            logger.warn("Continue not loaded in the Checkout screen.");
            return false;
        }
        logger.info("Loaded the Checkout Page screen.");
        return true;
    }

    /***
     * Method to check Cart page with items is loaded.
     *
     * @return if the Cart page with items is loaded
     * @throws Exception
     */
    public boolean isCartPageWithItemloaded() throws Exception {
        waitForElementPresent(cartPageLocators.get("Cart"), 3);
        if (!isElementPresent(getBy(cartPageLocators.get("Cart")))) {
            logger.warn("Cart static text not loaded in the Cart screen.");
            return false;
        }
        if (!isElementPresent(getBy(cartPageLocators.get("Remove")))) {
            logger.warn("Remove link not loaded in the Cart screen.");
            return false;
        }
        if (!isElementPresent(getBy(cartPageLocators.get("MoveToWishlist")))) {
            logger.warn("Move to wishlist link not loaded in the Cart screen.");
            return false;
        }
        logger.info("Loaded the Cart screen.");
        return true;
    }

    /***
     * Method to click Remove Item Link.
     *
     * @throws Exception
     */
    public void clickRemoveItemLink() throws Exception {
        WebElement remove_item = driver.findElement(getBy(cartPageLocators.get("Remove")));
        remove_item.click();
        logger.info("Clicked Remove Item link");
    }
}