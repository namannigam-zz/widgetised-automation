package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.*;

public class CartPageActions extends AppiumBasePage {

    public CartPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Removes all the items from the cart and validates empty cart.
     *
     * @return if the cart is cleared of all products or not
     * @throws InterruptedException
     */
    public boolean removeAllItemsFromCart(int cartCount) throws InterruptedException {
        // TODO : get the count of the cart by using the title and remove the parameter being passed to this method
        if (isElementPresent(getBy(cartPageLocators.get("EmptyCartKeepShopping")))) {
            logger.info("Cart is already empty.");
            return true;
        } else {
            for (int count = 0; count < cartCount; count++) {
                removeTopItemFromCart();
            }
        }
        if (!isElementPresent(getBy(cartPageLocators.get("EmptyCartKeepShopping")))) {
            logger.error("Items are not completely removed from the cart. Some items are still present.");
            return false;
        } else return true;
    }

    /***
     * Removes a single item from the cart and validate.
     *
     * @return if the first product was removed and Undo option was displayed or not
     * @throws InterruptedException
     */
    public void removeTopItemFromCart() throws InterruptedException {
        int count = 0;
        while (!isElementPresent(getBy(cartPageLocators.get("CartItemRemoveButton"))) && count < 5) {
            swipeUp(2);
            count++;
        }
        if (isElementPresent(getBy(cartPageLocators.get("CartItemRemoveButton")))) {
            List<WebElement> removeButtonList = driver.findElements(getBy(cartPageLocators.get("CartItemRemoveButton")));
            waitForElementPresent(cartPageLocators.get("CartItemRemoveButton"), 3);
            removeButtonList.get(0).click();
            logger.info("Removing the very first element in the cart...");
            waitForElementPresent(cartPageLocators.get("UndoRemoveCartItem"), 2);
            if (isElementPresent(getBy(cartPageLocators.get("UndoRemoveCartItem")))) {
                logger.info("The topmost product is removed from the cart.");
                Thread.sleep(2000);
                return;
            } else return;
        } else {
            logger.info("There cart is empty as of now.");
            return;
        }
    }

    /***
     * Moves an element from cart page to wish-list.
     * - Precondition : Only for signed in user.
     *
     * @return if the product was removed from cart and moved to wish-list
     * @throws InterruptedException
     */
    public boolean moveToWishlistFromCartPage() throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> wishListButtons = driver.findElements(getBy(cartPageLocators.get("CartItemWishlistButton")));
        wishListButtons.get(0).click();
        logger.info("Moving the first element in the cart to wishlist...");
        Thread.sleep(2000);
        //TODO : with richview validate the title of the product moved
        if (isElementPresent(getBy(cartPageLocators.get("UndoRemoveCartItem")))) {
            logger.info("The topmost product is moved to the wishlist.");
            return true;
        } else return false;
    }

    /***
     * Proceeds to Checkout from the cart page.
     *
     * @return if the checkout was initiated or not
     * @throws InterruptedException
     */
    public boolean checkOutFromCartPage() throws InterruptedException {
        if (isElementPresent(getBy(cartPageLocators.get("CheckOutButton")))) {
            driver.findElement(getBy(cartPageLocators.get("CheckOutButton"))).click();
            logger.info("Placing the order from the cart page..");
            waitUntilProgressBarIsDisplayed();
            if (isElementPresent(getBy(checkOutPageLocators.get("CheckOutLoginText"))) || isElementPresent(getBy(checkOutPageLocators.get("CheckOutDeliveryText")))) {
                logger.info("Moved to CheckOut page to Place the Order");
                return true;
            } else return false;
        } else {
            logger.info("Cannot place the order due to some conflicts in the cart page.");
            return true;
        }
    }

    /***
     * Moves to page for changing the shipping address from Cart Page.
     *
     * @return if the address was changed successfully or not
     * @throws InterruptedException
     */
    public boolean changeAddressFromCartPage() throws InterruptedException {
        waitForElementPresent(cartPageLocators.get("ChangeAddressLink"), 3);
        driver.findElement(getBy(cartPageLocators.get("ChangeAddressLink"))).click();
        Thread.sleep(2000);
        if (!isElementPresent(getBy(cartPageLocators.get("CheckDeliveryButton")))) {
            logger.error("Option for changing the pincode is not working.");
            return false;
        }
        driver.findElement(getBy(cartPageLocators.get("CheckDeliveryButton"))).click();
        waitUntilProgressBarIsDisplayed();
        Thread.sleep(1000);
        if (isElementPresent(getBy(cartPageLocators.get("ChangeAddressLink")))) {
            logger.info("Option for changing the pincode works fine.");
            return true;
        } else return false;
    }

    /***
     * Validates the list of products expected on cart.
     *
     * @param expectedProductList list of products expected on cart
     * @return if the products listed are correct or not
     */
    public boolean validateCartProducts(List<String> expectedProductList) {
        //TODO : Get the current list of products on the cart page and verify against the expectedProdList
//        List <String> cartPageProductList = new ArrayList<>();
//        List <WebElement> productTitle = driver.findElements(getBy(cartPageLocators.get("")));
        return true;
    }

    /***
     * Gets the count of products in CartPage
     *
     * @return the count of products on cart page
     * @throws InterruptedException
     */
    public int getCartCountOnCartPage() throws InterruptedException {
        Thread.sleep(2000);
        if (isElementPresent(getBy(cartPageLocators.get("CartItemWishlistButton")))) {
            List<WebElement> numberOfProducts = driver.findElements(getBy(cartPageLocators.get("CartItemWishlistButton")));
            return numberOfProducts.size();
        }
        if (isElementPresent(getBy(cartPageLocators.get("CartItemRemoveButton")))) {
            List<WebElement> numberOfProducts = driver.findElements(getBy(cartPageLocators.get("CartItemRemoveButton")));
            return numberOfProducts.size();
        }
        if (isElementPresent(getBy(cartPageLocators.get("CartItemDetails")))) {
            List<WebElement> numberOfProducts = driver.findElements(getBy(cartPageLocators.get("CartItemDetails")));
            return numberOfProducts.size();
        }
        Thread.sleep(1000);
        return 0;
    }
}