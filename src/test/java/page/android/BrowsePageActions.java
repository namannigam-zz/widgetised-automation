package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BrowsePageActions extends AppiumBasePage {

    public BrowsePageActions(WebDriver driver) {
        super(driver);
    }

    long dgTime = 0;


    /***
     * Finds the displayed view of products on browse page.
     *
     * @return type of view displayed as a string e.g. grid,list.
     * @throws InterruptedException
     */
    public String getViewType() throws InterruptedException {
        List<WebElement> productImages = driver.findElements(getBy(browsePageLocators.get("BrowsePageProductListImage")));
        if (isElementPresent(getBy(browsePageLocators.get("BrowsePageListThumbnailProductLayout"))) && (productImages.size() == 1)) {
            logger.info("Current view is Thumbnail.");
            return "thumbnail";
        } else if (isElementPresent(getBy(browsePageLocators.get("BrowsePageGridProductLayout")))) {
            logger.info("Current view is Grid View.");
            return "grid";
        } else {
            logger.info("Current view is List View.");
            return "list";
        }
    }

    /***
     * Toggles the view of the products displayed and validates.
     *
     * @return if the toggle was successful or not
     * @throws InterruptedException
     */
    public boolean toggleView() throws InterruptedException {
        String initialViewStatus = getViewType();
        List<String> initialProducts = browsePageProducts();
        if (isElementPresent(getBy(browsePageLocators.get("SortButtonLayout"))) && isElementPresent(getBy(browsePageLocators.get("FilterButtonLayout")))) {
            driver.findElement(getBy(browsePageLocators.get("ToggleButtonLayout"))).click();
            Thread.sleep(1000);
            String finalViewStatus = getViewType();
            List<String> finalProducts = browsePageProducts();
            if (finalViewStatus == null || initialViewStatus == null)
                return false;
            if (initialViewStatus.equalsIgnoreCase("list") && finalViewStatus.equalsIgnoreCase("grid")) {
                if (initialProducts.contains(finalProducts) || finalProducts.contains(initialProducts)) {
                    logger.info("Successfully toggled view from List view to Grid view");
                    return true;
                } else return false;
            } else if (initialViewStatus.equalsIgnoreCase("grid") && finalViewStatus.equalsIgnoreCase("thumbnail")) {
                if (initialProducts.contains(finalProducts)) {
                    logger.info("Successfully toggled view from Grid view to Thumbnail view");
                    return true;
                } else return false;
            } else if (initialViewStatus.equalsIgnoreCase("thumbnail") && finalViewStatus.equalsIgnoreCase("list")) {
                if (finalProducts.contains(initialProducts)) {
                    logger.info("Successfully toggled view from Thumbnail view to List view");
                    return true;
                } else return false;
            } else {
                logger.error("Toggle button didn't work well.");
                return false;
            }
        } else return false;
    }

    /***
     * Selects random filters form the list for products on the browse page.
     *
     * @return if the filters while applied successfully or not
     * @throws InterruptedException
     */
    public boolean selectRandomFilters() throws InterruptedException {
        String filterApplied;
        if (isElementPresent(getBy(browsePageLocators.get("SortButtonLayout")))) {
            driver.findElement(getBy(browsePageLocators.get("FilterButtonLayout"))).click();
        }
        Thread.sleep(3000);
        if (isElementPresent(getBy(browsePageLocators.get("RefineCueTipTitle")))) {
            driver.findElement(getBy(browsePageLocators.get("RefineCueTipTitle"))).click();
            Thread.sleep(1000);
        }
        waitForElementPresent(browsePageLocators.get("SearchInFilters"), 2);
        List<WebElement> filterSubCategory = driver.findElements(getBy(browsePageLocators.get("SubCategoryTitleFilterListButton")));
        int randomItem = randomNumber(filterSubCategory.size() - 1);
        filterSubCategory.get(randomItem).click();
        filterApplied = filterSubCategory.get(0).getText();
        if (filterApplied.equalsIgnoreCase("More")) {
            logger.info("Moving within the More sub category option...");
            List<WebElement> moreSubCategoryOption = driver.findElements(getBy(browsePageLocators.get("MoreSubCategoryFilterList")));
            int accessItem = randomNumber(moreSubCategoryOption.size() - 1);
            moreSubCategoryOption.get(accessItem).click();
            logger.info("Selected the sub category " + filterApplied + " for filters to be applied.");
            List<WebElement> moreSubCategoryFilters = driver.findElements(getBy(browsePageLocators.get("MoreSubCategoryFilterListCheckbox")));
            for (int i = 0; i < 5; i++) {
                accessItem = randomNumber(moreSubCategoryFilters.size() - 1);
                moreSubCategoryFilters.get(accessItem).click();
            }
            Thread.sleep(1000);
        } else {
            logger.info("Selected the sub category " + filterApplied + " for filters to be applied.");
            List<WebElement> otherSubCategoryFilters = driver.findElements(getBy(browsePageLocators.get("SubFilterToBeAppliedCheckBox")));
            for (int i = 0; i < 5; i++) {
                int accessItem = randomNumber(otherSubCategoryFilters.size() - 1);
                otherSubCategoryFilters.get(accessItem).click();
            }
            Thread.sleep(1000);
        }
        driver.findElement(getBy(browsePageLocators.get("FilterPageApply"))).click();
        Thread.sleep(4000);
        if (isElementPresent(getBy(browsePageLocators.get("ApplyAndExitButtonpply")))) {
            driver.findElement(getBy(browsePageLocators.get("ApplyAndExitButton"))).click();
            Thread.sleep(4000);
        }
        if (isElementPresent(getBy(browsePageLocators.get("SortButtonLayout")))) {
            String selectedFilterText = driver.findElement(getBy(browsePageLocators.get("FilterButtonAppliedText"))).getText();
            if (selectedFilterText.contains(filterApplied)) {
                logger.info("Random filters applied.");
                return true;
            } else return false;
        } else return false;
    }

    /***
     * Selects sub category filters for Price and Availability and verify the products listed.
     *
     * @return if the product listed are filtered or not
     * @throws InterruptedException
     */
    public boolean selectCategoryFilters() throws InterruptedException {
        int lowerPriceLimit = 0, upperPriceLimit = 0;
        if (isElementPresent(getBy(browsePageLocators.get("SortButtonLayout")))) {
            driver.findElement(getBy(browsePageLocators.get("FilterButtonLayout"))).click();
        }
        Thread.sleep(3000);
        if (isElementPresent(getBy(browsePageLocators.get("RefineCueTipTitle")))) {
            driver.findElement(getBy(browsePageLocators.get("RefineCueTipTitle"))).click();
            Thread.sleep(1000);
        }
        //Select the sub category filter group from the list
        List<WebElement> filterSubCategory = driver.findElements(getBy(browsePageLocators.get("SubCategoryTitleFilterListButton")));
        if (!isElementPresent(getBy(browsePageLocators.get("SortButtonLayout"))) && !isElementPresent(getBy(browsePageLocators.get("FilterButtonLayout")))) {
            int countSubCategories = filterSubCategory.size();
            dgTime = System.currentTimeMillis() / 1000L;
            for (int i = countSubCategories - 1; i >= 0; i--) {
                String textAtListGroupItem = filterSubCategory.get(i).getText();

                // Filter for Availability
                if (textAtListGroupItem.contains("More")) {
                    filterSubCategory.get(i).click();
                    logger.info("Moving within the More sub category option...");
                    Thread.sleep(2000);
                    List<WebElement> moreSubCategoryOption = driver.findElements(getBy(browsePageLocators.get("MoreSubCategoryFilterList")));
                    int accessItem = randomNumber(moreSubCategoryOption.size() - 1);
                    moreSubCategoryOption.get(accessItem).click();
                    if (moreSubCategoryOption.size() > 0) {
                        for (int j = 0; j < moreSubCategoryOption.size(); j++) {
                            String textAtMoreSubFilter = moreSubCategoryOption.get(j).getText();
                            if (textAtMoreSubFilter.contains("Availability")) {
                                moreSubCategoryOption.get(j).click();
                                waitForElementPresent(browsePageLocators.get("MoreSubCategoryFilterListCheckbox"), 5);
                                driver.findElement(getBy(browsePageLocators.get("MoreSubCategoryFilterListCheckbox"))).click();
                            }
                        }
                        logger.info("Availability filter under More selected for the product.");
                    } else {
                        logger.info("More Sub-filters didn't show up");
                    }
                }
                if (textAtListGroupItem.contains("Availability")) {
                    List<WebElement> filterTabSelection = driver.findElements(getBy(browsePageLocators.get("SubFilterToBeAppliedText")));
                    logger.info("Moving within the Price sub category option...");
                    Thread.sleep(2000);
                    if (isElementPresent(getBy(browsePageLocators.get("SubFilterToBeAppliedCheckBox"))))
                        filterTabSelection.get(0).click();
                }

                // Filter for Price
                if (textAtListGroupItem.contains("Price")) {
                    filterSubCategory.get(i).click();
                    List<WebElement> filterTabSelection = driver.findElements(getBy(browsePageLocators.get("SubFilterToBeAppliedText")));
                    logger.info("Moving within the Price sub category option...");
                    Thread.sleep(2000);
                    if (isElementPresent(getBy(browsePageLocators.get("SubFilterToBeAppliedCheckBox")))) {
                        String textAtFilter = filterTabSelection.get(0).getText();
                        if (textAtFilter.contains("Below")) {
                            String filterText = textAtFilter.substring(0, textAtFilter.indexOf("(")).trim();
                            String filterPriceText = filterText.replaceAll(" and Below", "");
                            upperPriceLimit = Integer.parseInt(filterPriceText.substring(filterPriceText.indexOf(".") + 1).trim());
                            lowerPriceLimit = 0;
                            filterTabSelection.get(0).click();
                            Thread.sleep(2000);
                            String productCount = textAtFilter.substring(textAtFilter.indexOf("(") + 1).trim().replaceAll(" results", "").replace(")", "");
                            int numOfProductShownInFilter = Integer.parseInt(productCount);

                        } else {
                            String filterText = textAtFilter.substring(0, textAtFilter.indexOf("(")).trim();
                            String filterPriceText = filterText.substring(0, filterText.indexOf("-")).trim();
                            lowerPriceLimit = Integer.parseInt(filterPriceText.substring(filterPriceText.indexOf(".") + 1).trim());
                            String tempB = filterText.replaceAll(filterPriceText, "");
                            upperPriceLimit = Integer.parseInt(tempB.substring(tempB.indexOf(".") + 1).trim());
                            filterTabSelection.get(0).click();
                            Thread.sleep(2000);
                            String productCount = textAtFilter.substring(textAtFilter.indexOf("(") + 1).trim().replaceAll(" results", "").replace(")", "");
                            int numOfProductShownInFilter = Integer.parseInt(productCount);
                        }
                    }
                }
            }
            driver.findElement(getBy(browsePageLocators.get("FilterPageApply"))).click();
            Thread.sleep(2000);
            if (isElementPresent(getBy(browsePageLocators.get("ApplyAndExitButton")))) {
                driver.findElement(getBy(browsePageLocators.get("ApplyAndExitButton"))).click();
                logger.info("Applying selected filters on the search...");
                Thread.sleep(2000);
            }
        }
        List<WebElement> productItemList = driver.findElements(getBy(productPageLocators.get("ProductItemListGridPrice")));
        String selectedFiltersList = driver.findElement(getBy(browsePageLocators.get("FilterButtonAppliedText"))).getText();
        if (selectedFiltersList.contains("Price")) {
            int priceListForFilter = productItemList.size();
            for (WebElement aProductItemList : productItemList) {
                String priceText = aProductItemList.getText();
                int priceForProductListed = Integer.parseInt(priceText.substring(priceText.indexOf(".") + 1).trim());
                if (priceForProductListed <= lowerPriceLimit || priceForProductListed >= upperPriceLimit) {
                    logger.info("Product price mismatch for filters on product : " + priceForProductListed);
                    return false;
                }
            }
        }
        if (selectedFiltersList.contains("Availability")) {
            List<WebElement> productOOSList = driver.findElements(getBy(browsePageLocators.get("ProductOutofStockStatus")));
            int listAvailability = productOOSList.size();
            if (listAvailability > 0) {
                for (WebElement aProductOOSList : productOOSList) {
                    boolean oosStatus = aProductOOSList.isDisplayed();
                    if (oosStatus) {
                        String textStatus = aProductOOSList.getText();
                        if (textStatus.contains("Out of Stock")) {
                            logger.info("OOS items are being displayed even after selecting filters");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /***
     * Selects the sorting options from Price - Low To High and validate the list.
     *
     * @return if the sort option is displayed and list is sorted or not
     * @throws InterruptedException
     */
    public boolean selectCategorySort() throws InterruptedException {
        boolean status = false;
        String sortOptionText = "";
        if (isElementPresent(getBy(browsePageLocators.get("FilterButtonLayout")))) {
            driver.findElement(getBy(browsePageLocators.get("SortButtonLayout"))).click();
            Thread.sleep(2000);
        }
        List<WebElement> sortOptions = driver.findElements(getBy(browsePageLocators.get("SortOptionPopUpItem")));
        for (WebElement sortOption : sortOptions) {
            sortOptionText = sortOption.getText();
            if (sortOptionText.contains("Low to High")) {
                sortOption.click();
                logger.info("Sorted the listing from Price Low to High.");
                Thread.sleep(3000);
                break;
            }
        }
        String textForSelectedSort = driver.findElement(getBy(browsePageLocators.get("SortBySelectedText"))).getText();
        if (!sortOptionText.contains(textForSelectedSort)) {
            logger.warn("Sort options not displayed on the browse page on the layout.");
            return false;
        }
        String currentViewType = getViewType();
        while (!(currentViewType.equalsIgnoreCase("list"))) {
            waitForElementPresent(browsePageLocators.get("ToggleButtonLayout"), 3);
            driver.findElement(getBy(browsePageLocators.get("ToggleButtonLayout"))).click();
            Thread.sleep(2000);
            currentViewType = getViewType();
        }
        logger.info("View switched to list view for products.");
        List<WebElement> productItemListPrice = driver.findElements(getBy(productPageLocators.get("ProductItemListGridPrice")));
        int previousPrice = 0;
        int priceListForFilter = productItemListPrice.size();
        for (WebElement aProductItemListPrice : productItemListPrice) {
            String priceText = aProductItemListPrice.getText();
            int priceForProductListed = Integer.parseInt(priceText.substring(priceText.indexOf(".") + 1).trim());
            if (priceForProductListed < previousPrice) {
                logger.info("Product price sorting mismatch for product : " + priceForProductListed);
                status = false;
            } else
                status = true;
            previousPrice = priceForProductListed;
        }
        return status;
    }

    /***
     * Selects a random item from the displayed product view.
     *
     * @return if the product page is displayed for the selected product or not
     * @throws InterruptedException
     */
    public boolean selectAnyItemFromSearchedList() throws InterruptedException {
        Thread.sleep(3000);
        String currentViewType = getViewType();
        while (!(currentViewType.equalsIgnoreCase("list"))) {
            waitForElementPresent(browsePageLocators.get("ToggleButtonLayout"), 3);
            driver.findElement(getBy(browsePageLocators.get("ToggleButtonLayout"))).click();
            Thread.sleep(3000);
            currentViewType = getViewType();
        }
        logger.info("View switched to list view for products.");
        Thread.sleep(2000);
        List<WebElement> prodList = driver.findElements(getBy(browsePageLocators.get("BrowsePageProductLayout")));
        List<WebElement> prodTitleList = driver.findElements(getBy(browsePageLocators.get("BrowsePageProductTitle")));
        int accessItem = randomNumber(prodList.size() - 1);
        String prodTitle = prodTitleList.get(accessItem).getText();
        prodList.get(accessItem).click();
        logger.info("Selected a random product from the listed ones.");
        Thread.sleep(2000);
        dgTime = System.currentTimeMillis() / 1000L;
        if (isElementPresent(getBy(productPageLocators.get("ProductPageSwipeCuetip")))) {
            driver.findElement(getBy(productPageLocators.get("ProductPageSwipeCuetip"))).click();
            return true;
        } else if (isElementPresent(getBy(productPageLocators.get("ProductPageCueTip")))) {
            driver.findElement(getBy(productPageLocators.get("ProductPageCueTip"))).click();
            return true;
        } else if (isElementPresent(getBy(productPageLocators.get("PullOut"))) && driver.findElement(getBy(productPageLocators.get("ProductPageTitle"))).getText().equals(prodTitle)) {
            logger.info("Product page opened for the selected product.");
            return true;
        } else return false;
    }

    /***
     * Clears all the filters applied to a search.
     *
     * @param searchString to search a product
     * @return if the filters were cleared or not
     * @throws InterruptedException
     */
    public boolean clearFilters(String searchString) throws InterruptedException {
        SearchPageActions searchPageActions = new SearchPageActions(driver);
        searchPageActions.search(searchString);
        if (isElementPresent(getBy(browsePageLocators.get("SortButtonLayout")))) {
            driver.findElement(getBy(browsePageLocators.get("FilterButtonLayout"))).click();
        }
        Thread.sleep(3000);
        driver.findElement(getBy(browsePageLocators.get("FilterPageClearAll"))).click();
        Thread.sleep(1000);
        if (isElementPresent(getBy(browsePageLocators.get("SubCategoryTitleFilterSelectedCount")))) {
            System.err.println("Sub filter count still displayed");
            return false;
        }
        driver.findElement(getBy(browsePageLocators.get("FilterPageApply"))).click();
        Thread.sleep(2000);
        return isElementPresent(getBy(browsePageLocators.get("FilterButtonLayout")));
    }

    /***
     * Adds the first product amongst displayed list to wish-list from the browse page.
     *
     * @return if the product was successfully added to the wishlist or not
     * @throws InterruptedException
     */
    public boolean addToWishlistFromBrowsePage() throws InterruptedException {
        HomePageActions homePageActions = new HomePageActions(driver);
        WishlistPageActions wishlistPageActions = new WishlistPageActions(driver);
        String currentViewType = getViewType();
        while (!currentViewType.equalsIgnoreCase("list")) {
            waitForElementPresent(browsePageLocators.get("ToggleButtonLayout"), 3);
            driver.findElement(getBy(browsePageLocators.get("ToggleButtonLayout"))).click();
            waitForElementPresent(browsePageLocators.get("BrowsePageProductTitle"), 2);
            currentViewType = getViewType();
        }
        waitForElementPresent(browsePageLocators.get("BrowsePageWishlist"), 3);
        List<WebElement> prodTitle = driver.findElements(getBy(browsePageLocators.get("BrowsePageProductTitle")));
        List<WebElement> wishlistImages = driver.findElements(getBy(browsePageLocators.get("BrowsePageWishlist")));
        String addedProdName = prodTitle.get(0).getText();
//        if (isElementPresent(getBy(browsePageLocators.get("BrowsePageProductSubTitle")))) {
//            List<WebElement> prodSubtitle = driver.findElements(getBy(browsePageLocators.get("BrowsePageProductSubTitle")));
//            addedProdName = addedProdName + prodSubtitle.get(0).getText().toString();
//        }
        wishlistImages.get(0).click();
        dgTime = System.currentTimeMillis() / 1000L;
        logger.info("Adding the first product to Wishlist..");
        if (isElementPresent(getBy(wishlistPageLocators.get("WishlistFromBrowseCuetip")))) {
            driver.findElement(getBy(wishlistPageLocators.get("WishlistFromBrowseCuetip"))).click();
        }
        homePageActions.navigateBackHome();
        ToolbarActions toolbarActions = new ToolbarActions(driver);
        toolbarActions.clickOnOverFlowMenuItem(ToolbarActions.OverFlowMenuItem.WISHLIST);
        if (isElementPresent(getBy(wishlistPageLocators.get("WishListCueTip"))))
            driver.findElement(getBy(wishlistPageLocators.get("WishListCueTip"))).click();
        List<String> prodOnWishlist = wishlistPageActions.listProductsOnWishlist();
        if (prodOnWishlist.get(0).equals(addedProdName)) {
            logger.info("Product successfully added to the top of wish-list.");
            homePageActions.navigateBackHome();
            return true;
        } else return false;
    }

    /***
     * Verify the presence of ads on the browse page.
     * - Requires ads campaigned product as input.
     *
     * @return if the ads are displayed or not
     * @throws InterruptedException
     */
    public boolean verifyAds() throws InterruptedException {
        String currentViewType = getViewType();
        while (!currentViewType.equalsIgnoreCase("list")) {
            waitForElementPresent(browsePageLocators.get("ToggleButtonLayout"), 3);
            driver.findElement(getBy(browsePageLocators.get("ToggleButtonLayout"))).click();
            waitForElementPresent(browsePageLocators.get("BrowsePageProductTitle"), 2);
            currentViewType = getViewType();
        }
        if (!isElementPresent(getBy(advertisementLocators.get("ProductAdImage")))) {
            logger.error("No advertisement displayed for current search page.");
            return false;
        }
        return true;
    }

    /***
     * Verifies the similar searches option for the lifestyle products.
     *
     * @return if the similar searches are displayed or not on selecting the option
     * @throws InterruptedException
     */
    public boolean similarSearches() throws InterruptedException {
        if (isElementPresent(getBy(browsePageLocators.get("BrowsePageSimilarSearch")))) {
            List<WebElement> similarSeacrhList = driver.findElements(getBy(browsePageLocators.get("BrowsePageSimilarSearch")));
            similarSeacrhList.get(randomNumber(similarSeacrhList.size() - 1)).click();
            logger.info("Looking for similar searched for the product.");
            Thread.sleep(2000);
            if (isElementPresent(getBy(browsePageLocators.get("SimilarSearchHeader")))) {
                if (driver.findElement(getBy(browsePageLocators.get("SimilarProductTitle"))).getText().equalsIgnoreCase("Similar Products")) {
                    logger.info("Similar searches page opened for the product.");
                    driver.navigate().back();
                    return true;
                } else return false;
            } else return false;
        } else {
            logger.error("The product searched for does not have any similar searches.");
            return false;
        }
    }

    /***
     * Verifies the ratings and reviews on the browse page for the List view of products.
     *
     * @return if the ratings and reviews are displayed for the specified product or not
     * @throws InterruptedException
     */
    //TODO : pass a count through MAPI as to how many elements should have the ratings and reviews on browse page
    public boolean countRatingsReviews() throws InterruptedException {
        String currentViewType = getViewType();
        while (!(currentViewType.equalsIgnoreCase("list"))) {
            waitForElementPresent(browsePageLocators.get("ToggleButtonLayout"), 3);
            driver.findElement(getBy(browsePageLocators.get("ToggleButtonLayout"))).click();
            Thread.sleep(2000);
            currentViewType = getViewType();
        }
        logger.info("View switched to list view for products.");
        int countProduct = driver.findElements(getBy(browsePageLocators.get("BrowsePageProductLayout"))).size();
        int countRatingsReviews = driver.findElements(getBy(browsePageLocators.get("BrowsePageItemRatingCount"))).size();
        if ((countProduct == countRatingsReviews) || (countProduct - 1 == countRatingsReviews)) {
            logger.info("Products having reviews and ratings on the browse page : " + countRatingsReviews);
            return true;
        } else {
            logger.error("Ratings and Reviews are missing for products on the browse page.");
            return false;
        }
    }

    /***
     * Shares a product details using share icon for ping on browse page.
     *
     * @return if the product was successfully shared on ping or not
     * @throws InterruptedException
     */
    public boolean shareUsingChatOnly() throws InterruptedException {
        waitForElementPresent(browsePageLocators.get("BrowsePageChatIcon"), 3);
        driver.findElement(getBy(browsePageLocators.get("BrowsePageChatIcon"))).click();
        logger.info("Sharing using the chat icon on product...");
        Thread.sleep(2000);
        waitForElementPresent(socialCollabLocators.get("PingShareProductHeader"), 3);
        List<WebElement> horizontalTabs = driver.findElements(getBy(socialCollabLocators.get("PingShareProductHorizontalTab")));
        for (WebElement horizontalTab : horizontalTabs) {
            if (horizontalTab.getText().equalsIgnoreCase("Chats")) {
                horizontalTab.click();
                logger.info("Onto the share via chats tab for Ping.");
            }
        }
        List<WebElement> chatList = driver.findElements(getBy(socialCollabLocators.get("PingListConversationTitle")));
        chatList.get(0).click();
        waitForElementPresent(socialCollabLocators.get("PingChatViewMoreOptions"), 2);
        if (isElementPresent(getBy(socialCollabLocators.get("PingChatBubbleLayout")))) {
            driver.navigate().back();
            logger.info("Shared the product on chat successfully.");
            Thread.sleep(2000);
            return true;
        } else return false;
    }

    /***
     * Returns the list of titles of all the products on the current browse page.
     *
     * @return list of string comprising of title of products
     * @throws InterruptedException
     */
    public List<String> browsePageProducts() throws InterruptedException {
        List<String> productList = new ArrayList<>();
        if (!isElementPresent(getBy(browsePageLocators.get("BrowsePageProductTitle")))) {
            swipeUp(2);
        }
        List<WebElement> productTitle = driver.findElements(getBy(browsePageLocators.get("BrowsePageProductTitle")));
        for (int i = 0; i < productTitle.size(); i++) {
            productList.add(i, productTitle.get(i).getText());
        }
        return productList;
    }

    /***
     * Validates the product prices and titles on the browse page.
     *
     * @return if all the product prices and titles are displayed on the browse page
     * @throws InterruptedException
     */
    public boolean validatePriceTitle() throws InterruptedException {
        String currentViewType = getViewType();
        while (!(currentViewType.equalsIgnoreCase("list"))) {
            waitForElementPresent(browsePageLocators.get("ToggleButtonLayout"), 3);
            driver.findElement(getBy(browsePageLocators.get("ToggleButtonLayout"))).click();
            Thread.sleep(2000);
            currentViewType = getViewType();
        }
        List<WebElement> productTitle = driver.findElements(getBy(browsePageLocators.get("BrowsePageProductTitle")));
        List<WebElement> productPrice = driver.findElements(getBy(browsePageLocators.get("BrowsePageProductPrice")));

        for (int i = 0; i < productPrice.size(); i++) {
            if (Objects.equals(productPrice.get(i).getText(), "")) {
                logger.warn("Product price is empty for a product.");
                return false;
            }
            if (Objects.equals(productTitle.get(i).getText(), "")) {
                logger.warn("Product title is empty for a product.");
                return false;
            }
        }
        return true;
    }
}
