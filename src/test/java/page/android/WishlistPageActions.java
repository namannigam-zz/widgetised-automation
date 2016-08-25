package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class WishlistPageActions extends AppiumBasePage {

    public WishlistPageActions(WebDriver driver) {
        super(driver);
    }

    long dgTime = 0;

    /***
     * Gets the list of products on the wish-list.
     *
     * @return the list of product titles on wish-list
     * @throws InterruptedException
     */
    public List<String> listProductsOnWishlist() throws InterruptedException {
        List<String> productNames = new ArrayList<>();
        List<WebElement> productTitleOnWishlist = driver.findElements(getBy(wishlistPageLocators.get("ProductListTitle")));
        List<WebElement> productSubTitleOnWishlist = driver.findElements(getBy(wishlistPageLocators.get("ProductListSubText")));
        for (int i = 0; i < productTitleOnWishlist.size(); i++) {
            String uniqueProductTitle = productTitleOnWishlist.get(i).getText();
            productNames.add(i, uniqueProductTitle);
        }
        return productNames;
    }

    /***
     * Validates wish-list count and product listed.
     *
     * @param WishlistItems list of expected wishlist products title
     * @return if the expected products are listed on the wishlist or not
     * @throws InterruptedException
     */
    public boolean validatewishlistpage(List<String> WishlistItems) throws InterruptedException {
        boolean status = true;
        int temp = driver.findElements(getBy(browsePageLocators.get("ProductMainTitleText"))).size();
        if (!(temp == WishlistItems.size())) {
            logger.info("Numbers of item mismatch in wishlist page");
            status = false;
        }
        List<WebElement> productMainTitleList = driver.findElements(getBy(browsePageLocators.get("ProductMainTitleText")));
        for (int i = 0; i < temp; i++) {
            String temp1 = productMainTitleList.get(i).getText();
            if (!(temp1.equalsIgnoreCase(WishlistItems.get(temp - i - 1)))) {
                logger.info("Items are not in the same order as they were added in wishlist");
                status = false;
            }
        }
        return status;
    }

    /***
     * Deletes a specific list of items from wish-list specified using
     *
     * @param wishListItems specific list of products to be deleted.
     * @return if the specified list of products is removed from wish-list or not
     * @throws InterruptedException
     */
    public boolean wishlistdeleteItems(List<String> wishListItems) throws InterruptedException {
        boolean status = true;
        Thread.sleep(2000);
        dgTime = System.currentTimeMillis() / 1000L;
        int temp = driver.findElements(getBy(browsePageLocators.get("AddToWishlist"))).size();
        if (!(temp == wishListItems.size())) {
            logger.info("Numbers of item mismatch in wishlist page");
            status = false;
        }
        List<WebElement> addToWishlist = driver.findElements(getBy(browsePageLocators.get("AddToWishlist")));
        for (int i = 0; i < temp; i++) {
            addToWishlist.get(0).click();
        }
        if (isElementPresent(getBy(browsePageLocators.get("ContinueShopping")))) {
            driver.findElement(getBy(browsePageLocators.get("ContinueShopping"))).click();
            status = true;
        }
        return status;
    }

    /***
     * Clears all the items added to the wish-list.
     *
     * @return if the wishlist is emptied or not
     * @throws InterruptedException
     */
    public boolean wishlistdeleteAllitems() throws InterruptedException {
        if (!isElementPresent(getBy(wishlistPageLocators.get("WishListEmptyContinueButton")))) {
            waitForElementPresent(wishlistPageLocators.get("WishListClearButton"), 3);
            dgTime = System.currentTimeMillis() / 1000L;
            driver.findElement(getBy(wishlistPageLocators.get("WishListClearButton"))).click();
            logger.info("Clearing the entire wishlist..");
            Thread.sleep(3000);
            if (isElementPresent(getBy(wishlistPageLocators.get("ClearAllYesButton"))) && isElementPresent(getBy(wishlistPageLocators.get("ClearAllNoButton"))))
                driver.findElement(getBy(wishlistPageLocators.get("ClearAllYesButton"))).click();
            Thread.sleep(3000);
            if (isElementPresent(getBy(wishlistPageLocators.get("WishListEmptyContinueButton")))) {
                logger.info("Entire wishlist is cleared.");
                return true;
            } else return false;
        } else {
            logger.info("Wishlist is already clear.");
            return true;
        }
    }

    /***
     * Validates product page navigation for products listed on the wish-list.
     *
     * @return if the product page is displayed for the product on wishlist or not
     * @throws InterruptedException
     */
    public boolean selectItemMoveToProductPage() throws InterruptedException {
        List<WebElement> productsOnWishlist = driver.findElements(getBy(wishlistPageLocators.get("ProductListLayout")));
        productsOnWishlist.get(0).click();
        logger.info("Moving to the product page for first item on the wishlist...");
        if (isElementPresent(getBy(productPageLocators.get("ProductPageSwipeCuetip")))) {
            driver.findElement(getBy(productPageLocators.get("ProductPageSwipeCuetip"))).click();
        } else if (isElementPresent(getBy(productPageLocators.get("ProductPageCueTip")))) {
            driver.findElement(getBy(productPageLocators.get("ProductPageCueTip"))).click();
        }
        Thread.sleep(1000);
        if (isElementPresent(getBy(productPageLocators.get("PullOut")))) {
            logger.info("Successfully moved to the product page for first item on the wishlist.");
            return true;
        } else return false;
    }

    /***
     * Returns the count of elements displayed on the wishlist.
     *
     * @return count of wish-list products
     * @throws InterruptedException
     */
    public int getWishlistItemsCount() throws InterruptedException {
        if (isElementPresent(getBy(browsePageLocators.get("BrowsePageWishlist")))) {
            List<WebElement> wishlistImages = driver.findElements(getBy(browsePageLocators.get("BrowsePageWishlist")));
            return wishlistImages.size();
        } else return 0;
    }
}