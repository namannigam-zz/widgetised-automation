package page.ios;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPageActions extends AppiumBasePage {

    public CartPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Method to check Cart page is loaded.
     *
     * @return if the Cart page is loaded
     * @throws Exception
     */
    public boolean isCartPageloaded() throws Exception {
        waitForElementPresent(cartPageLocators.get("Cart"), 3);
        if (!isElementPresent(getBy(cartPageLocators.get("Cart")))) {
            logger.warn("Cart static text not loaded in the Cart screen.");
            return false;
        }
        if (!isElementPresent(getBy(cartPageLocators.get("KeepShopping")))) {
            logger.warn("Keep Shopping link not loaded in the Cart screen.");
            return false;
        }
        logger.info("Loaded the Cart screen.");
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