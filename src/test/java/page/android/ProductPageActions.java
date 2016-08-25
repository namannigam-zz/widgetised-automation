package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

//TODO : move onto PPV3 after 100% release

@SuppressWarnings("LoopStatementThatDoesntLoop")
public class ProductPageActions extends AppiumBasePage {

    public ProductPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Validates the existence of a price type.
     *
     * @param price_type type of price to verify e.g Selling Price,MRP
     * @return if the price type exists on the product page or not
     */
    public boolean priceTypeExists(String price_type) {
        List<WebElement> priceTypes = driver.findElements(getBy(productPageLocators.get("PriceType")));
        List<WebElement> correspondingPrice = driver.findElements(getBy(productPageLocators.get("Price")));
        int totalPriceTypes = priceTypes.size();
        for (WebElement priceType : priceTypes)
            if (priceType.getText().contains(price_type))
                return true;
        return false;
    }

    /***
     * Gets the price value for a specified type for a product.
     *
     * @param price_type the price type to attain the price value
     * @return the price value as a string
     */
    public String correspondingPriceValue(String price_type) {
        List<WebElement> priceTypes = driver.findElements(getBy(productPageLocators.get("PriceType")));
        List<WebElement> correspondingPrice = driver.findElements(getBy(productPageLocators.get("Price")));
        int totalPriceTypes = priceTypes.size();
        for (int i = 0; i < totalPriceTypes; i++)
            if (priceTypes.get(i).getText().contains(price_type))
                return correspondingPrice.get(i).getText();
        return "Rs.0";
    }

    /***
     * Gets the selling price of the displayed product.
     *
     * @return the selling price of the product
     * @throws InterruptedException
     */
    public int sellingPrice() throws InterruptedException {
        int count = 0;
        while ((!isElementPresent(getBy(productPageLocators.get("Price")))) && count < 5) {
            swipeUp(5);
            count++;
        }
        String sellingPrice = correspondingPriceValue("Selling Price");
        sellingPrice = sellingPrice.substring(sellingPrice.indexOf(".") + 1).trim();
        return Integer.parseInt(sellingPrice);
    }

    /***
     * Gets the marked price of the product opened.
     *
     * @return the marked price of product
     * @throws InterruptedException
     */
    public int markedPrice() throws InterruptedException {
        int count = 0;
        while ((!isElementPresent(getBy(productPageLocators.get("Price")))) && count < 5) {
            swipeUp(5);
            count++;
        }
        String mrp = correspondingPriceValue("MRP");
        mrp = mrp.substring(mrp.indexOf(".") + 1).trim();
        return Integer.parseInt(mrp);
    }

    /***
     * Validates the discount at the product displayed.
     *
     * @return if the displayed discount on product is correct or not
     * @throws InterruptedException
     */
    public boolean validateDiscountDisplayed() throws InterruptedException {
        int count = 0;
        while ((!isElementPresent(getBy(productPageLocators.get("Discount")))) && count < 5) {
            swipeUp(5);
            count++;
        }
        String discountText = driver.findElement(getBy(productPageLocators.get("Discount"))).getText();
        discountText = discountText.substring(0, discountText.indexOf("%")).trim();
        int discountPercent = Integer.parseInt(discountText);
        int SP = sellingPrice();
        int MP = markedPrice();
        int validDiscount = Math.round(((SP - MP) / MP) * 100);
        return validDiscount == discountPercent;
    }

    /***
     * Returns the title of the product displayed on the product page.
     *
     * @return the product name as a string
     */
    public String getProductName() {
        if (isElementPresent(getBy(productPageLocators.get("ProductPageTitle")))) {
            String prodName = driver.findElement(getBy(productPageLocators.get("ProductPageTitle"))).getText();
            logger.info("Current product name : " + prodName);
            return prodName;
        } else {
            logger.error("Product Title not displayed.");
            return null;
        }
    }

    /***
     * Adds an item to the wish-list from the product page.
     *
     * @return if the product was successfully added to wish-list or not
     * @throws InterruptedException
     */
    public boolean productAddToWishlist() throws InterruptedException {
        WishlistPageActions wishlistPageActions = new WishlistPageActions(driver);
        HomePageActions homePageActions = new HomePageActions(driver);
        String prodTitle = driver.findElement(getBy(productPageLocators.get("ProductPageTitle"))).getText();
        String prodSubtitle = driver.findElement(getBy(productPageLocators.get("ProductPageSubTitle"))).getText();
        String prodName = prodTitle + prodSubtitle;
        waitForElementPresent(productPageLocators.get("ProductPageWishlistLayout"), 5);
        driver.findElement(getBy(productPageLocators.get("ProductPageWishlistLayout"))).click();
        logger.info("Adding to the wishlist..");
        Thread.sleep(3000);
        if (isElementPresent(getBy(wishlistPageLocators.get("WishListCueTip"))))
            driver.findElement(getBy(wishlistPageLocators.get("WishListCueTip"))).click();
        homePageActions.navigateToWishlist();
        List<String> prodOnWishlist = wishlistPageActions.listProductsOnWishlist();
        if (prodOnWishlist.contains(prodName)) {
            logger.info("Product successfully added to the wishlist.");
            return true;
        } else return false;
    }

    /***
     * Shows a slide show of the product images.
     *
     * @return if all the images were displayed properly for a product or not
     * @throws InterruptedException
     */
    public boolean slideshowProductImage() throws InterruptedException {
        if (isElementPresent(getBy(productPageLocators.get("ProductPageSampleImage")))) {
            List<WebElement> sampleImages = driver.findElements(getBy(productPageLocators.get("ProductPageSampleImage")));
            for (int i = 0; i < sampleImages.size(); i++) {
                sampleImages.get(i).click();
                logger.info("Moving to the view for image " + i + 1);
                Thread.sleep(2000);
                if (!isElementPresent(getBy(productPageLocators.get("ProductPageMainImage")))) {
                    logger.error("The product image is not displayed properly for the thumbnail slideshow.");
                    return false;
                }
            }
            sampleImages.get(0).click();
            logger.info("Moving back to the first image..");
        } else {
            logger.error("There are no thumbnail to view for the product displayed.");
            return false;
        }
        return true;
    }

    /***
     * Swipes to the next product to view on the product page.
     *
     * @return the title of the next product
     * @throws InterruptedException
     */
    public String swipeProductNext() throws InterruptedException {
        String currentProd = getProductName();
        String finalProd = currentProd;
        while (finalProd.equalsIgnoreCase(currentProd)) {
            swipeLeft(3);
            finalProd = getProductName();
        }
        logger.info("Moved to the product : " + finalProd);
        logger.info("Swiped to the next product.");
        return finalProd;
    }

    /***
     * Swipes to the previous product to view on the product page.
     *
     * @return the title of the previous product
     * @throws InterruptedException
     */
    public String swipeProductPrev() throws InterruptedException {
        String currentProd = getProductName();
        String finalProd = currentProd;
        while (finalProd.equalsIgnoreCase(currentProd)) {
            swipeRight(3);
            finalProd = getProductName();
        }
        logger.info("Moved to the product : " + finalProd);
        logger.info("Swiped to the previous product.");
        return finalProd;
    }

    /***
     * Sharesthe product from the product page using chat option
     *
     * @return if the product is shared on ping or not
     * @throws InterruptedException
     */
    public boolean shareProduct() throws InterruptedException {
        waitForElementPresent(productPageLocators.get("ShareButton"), 5);
        driver.findElement(getBy(productPageLocators.get("ShareButton"))).click();
        logger.info("Sharing the product from the product page..");
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
     * Adds a product to the Cart.
     *
     * @return if the gotoCart option is displayed or not
     * @throws InterruptedException
     */
    public boolean productAddToCart() throws InterruptedException {
        BrowsePageActions browsePageActions = new BrowsePageActions(driver);
        Thread.sleep(2000);
        while (isElementPresent(getBy(productPageLocators.get("ComingSoonButton"))) || isElementPresent(getBy(productPageLocators.get("NotifyButton")))) {
            logger.info("Item was amongst coming up items.");
            driver.navigate().back();
            Thread.sleep(3000);
            browsePageActions.selectAnyItemFromSearchedList();
            return false;
        }
        if (driver.findElement(getBy(productPageLocators.get("AddToCartButton"))).getText().equalsIgnoreCase("GoTo Cart")) {
            logger.info("Item was already added to the Cart.");
            return true;
        } else {
            waitForElementPresent(productPageLocators.get("AddToCartButton"), 5);
            driver.findElement(getBy(productPageLocators.get("AddToCartButton"))).click();
            logger.info("Adding the product to cart...");
            while (isElementPresent(getBy(productPageLocators.get("SelectSwatchMessage")))) {
                logger.info("Product seems to be a lifestyle product. Need to select swatches before adding to the cart.");
                Assert.assertEquals(selectSwatch(), true, "Something wrong in selecting swatches for the product.");
                driver.findElement(getBy(productPageLocators.get("AddToCartButton"))).click();
            }
            Thread.sleep(3000);
            if (driver.findElement(getBy(productPageLocators.get("AddToCartButton"))).getText().equalsIgnoreCase("GoTo Cart")) {
                logger.info("Product is added to the cart.");
                return true;
            } else return false;
        }
    }

    /***
     * Moves to Buy Now for the current selected product.
     *
     * @return if the checkout page is reached or not
     * @throws InterruptedException
     */
    public boolean productBuyNow() throws InterruptedException {
        BrowsePageActions browsePageActions = new BrowsePageActions(driver);
        Thread.sleep(2000);
        while (isElementPresent(getBy(productPageLocators.get("ComingSoonButton"))) || isElementPresent(getBy(productPageLocators.get("NotifyButton")))) {
            logger.info("Item was amongst coming up items.");
            driver.navigate().back();
            browsePageActions.selectAnyItemFromSearchedList();
        }
        driver.findElement(getBy(productPageLocators.get("BuyButton"))).click();
        waitUntilProgressBarIsDisplayed();
        while (isElementPresent(getBy(productPageLocators.get("SelectSwatchMessage")))) {
            logger.info("Product seems to be a lifestyle product. Need to select swatches before adding to the cart.");
            Assert.assertEquals(selectSwatch(), true, "Something wrong in selecting swatches for the product.");
            driver.findElement(getBy(productPageLocators.get("BuyButton"))).click();
        }
        logger.info("Buying the product, moving to checkout..");
        if (isElementPresent(getBy(checkOutPageLocators.get("CheckOutLoginText"))) ||
                isElementPresent(getBy(checkOutPageLocators.get("CheckOutDeliveryText"))) ||
                isElementPresent(getBy(checkOutPageLocators.get("CheckOutPaymentText")))) {
            logger.info("Reached the checkout page after BuyNow option.");
            return true;
        } else return false;

    }

    /***
     * Determines and validates the prices listed on the product pages.
     *
     * @param markedPrice  flag for MRP
     * @param sellingPrice flag for Selling Price
     * @param specialPrice flag for Special Price
     * @param emiOption    flag for emiOption
     * @return if the prices on product page are present according to the flags or not
     * @throws InterruptedException
     */
    //TODO : Requires flags for mrp,selling price,special price and emiOptions
    public boolean validatePrices(boolean markedPrice, boolean sellingPrice, boolean specialPrice, boolean emiOption) throws InterruptedException {
        int special_price = -2, selling_price = -1, mrp_price = 0, final_price = 0, calculatedDiscount = 0;
        waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
        while (!isElementPresent(getBy(productPageLocators.get("ProductPagePriceWidget")))) {
            logger.error("Price Layout not yet visible.");
            swipeUp(2);
            Thread.sleep(2000);
        }
        // Special Price : FLAG
        if (specialPrice) {
            if (!priceTypeExists("Special Price")) {
                logger.error("Validation of Product Special Price text has failed");
                return false;
            }
            special_price = sellingPrice();
            final_price = special_price;
        } else if (priceTypeExists("Special Price")) {
            logger.error("Special Price is there on the Product Page, but special price flag is false");
            return false;
        }
        // Selling Price : FLAG
        if (sellingPrice) {
            if (specialPrice) {
                if (!priceTypeExists("Selling Price")) {
                    logger.error("Validation of Product Selling Price text has failed");
                    return false;
                }
                selling_price = sellingPrice();
            } else {
                if (!priceTypeExists("Selling Price")) {
                    logger.error("Validation of Product Selling Price text has failed,Actual Text: ");
                    return false;
                }
                selling_price = sellingPrice();
                final_price = selling_price;
            }
        } else if (priceTypeExists("Special Price")) {
            logger.error("Selling Price is there on the Product Page, but selling price flag is false");
            return false;
        }
        // Marked Price : FLAG
        if (markedPrice) {
            if (sellingPrice) {
                if (!priceTypeExists("MRP:")) {
                    logger.error("Validation of Product MRP Price has failed");
                    return false;
                }
                mrp_price = sellingPrice();
            } else {
                if (!priceTypeExists("MRP")) {
                    logger.error("Validation of Product MRP Price has failed");
                    return false;
                }
                mrp_price = sellingPrice();
                final_price = mrp_price;
            }
        } else if (priceTypeExists("MRP")) {
            logger.error("MRP Price is there on the Product Page, but MRP price flag is false");
            return false;
        }
        // EMI Option : FLAG
        if (emiOption) {
            String emiDetails = driver.findElement(getBy(productPageLocators.get("ProductEMIOptionText"))).getText();
            if (final_price < 4000) {
                logger.error("The final price of the product is less than 4000 but EMI option is being given");
                return false;
            }
            if (emiDetails.contains("EMI:") && emiDetails.contains("Rs") && emiDetails.contains("per month")) {
                driver.findElement(getBy(productPageLocators.get("ProductEMIOptionText"))).click();
                Thread.sleep(4000);
                driver.navigate().back();
                Thread.sleep(4000);
            } else {
                logger.error("There was a problem while validating EMI option");
                return false;
            }
        }

        if (markedPrice && sellingPrice && specialPrice) {
            if (!((special_price < selling_price) && (selling_price < mrp_price))) {
                logger.error("Either mrp is less than selling price or selling price is less than special price, mrp : " + mrp_price + " Selling Price : " + selling_price + " Special Price : " + special_price);
                return false;
            }
        } else if (sellingPrice && specialPrice) {
            if (!(special_price < selling_price)) {
                logger.error("Selling price is less than special price, Special Price : " + special_price + " Selling Price : " + selling_price);
                return false;
            }
        } else if (markedPrice && sellingPrice) {
            if (!(mrp_price > selling_price)) {
                logger.error("Special price is more than the mrp, MRP : " + mrp_price + " Selling Price : " + selling_price);
            }
        }

        if ((markedPrice && sellingPrice) || (markedPrice && specialPrice) || (sellingPrice && specialPrice)) {
            waitForElementPresent(productPageLocators.get("Discount"), 2);
            String discountText = driver.findElement(getBy(productPageLocators.get("Discount"))).getText();
            discountText = discountText.substring(0, discountText.indexOf("%")).trim();
            int discount = Integer.parseInt(discountText);
            if (markedPrice && specialPrice) {
                calculatedDiscount = (mrp_price - special_price) * 100 / mrp_price;
            } else if (markedPrice && sellingPrice) {
                calculatedDiscount = (mrp_price - selling_price) * 100 / mrp_price;
            } else if (sellingPrice && specialPrice) {
                calculatedDiscount = (selling_price - special_price) * 100 / selling_price;
            } else
                calculatedDiscount = 0;
            if (!(discount == calculatedDiscount && discount > 0 && discount < 100)) {
                logger.error("Discount on the product is " + discount + " this does not match with the calculated discount" + calculatedDiscount);
                return false;
            }
        }
        return true;
    }

    /***
     * Selects the size swatches for product page.
     * - Precondition : called only for lifestyle products.
     *
     * @return if the swatch is selected for the product or not
     * @throws InterruptedException
     */
    public boolean selectSwatch() throws InterruptedException {
        int count = 0;
        while ((!isElementPresent(getBy(productPageLocators.get("SwatchLayout")))) && count < 5) {
            logger.error("Swatches layout not displayed on the screen.");
            swipeUp(5);
            count++;
        }
        waitForElementPresent(productPageLocators.get("SwatchLayout"), 3);
        List<WebElement> swatchCategoryList = driver.findElements(getBy(productPageLocators.get("SwatchSizeColor")));
        for (WebElement aSwatchCategoryList : swatchCategoryList) {
            if (aSwatchCategoryList.getText().equalsIgnoreCase("Select Size"))
                aSwatchCategoryList.click();
            logger.info("Selected Size swatch for the product.");
        }
        Thread.sleep(2000);
        List<WebElement> swatchSubCategoryList = driver.findElements(getBy(productPageLocators.get("SwatchList")));
        swatchSubCategoryList.get(0).click();
        logger.info("Selected first listed size for the product.");
        Thread.sleep(2000);
        return true;
    }

    /***
     * Checks the availability of the product on given PIN Code in product page.
     *
     * @param pincode input tp change the pincode
     * @return if the availability was checked or not for the product
     * @throws InterruptedException
     */
    public boolean checkAvailability(String pincode) throws InterruptedException {
        BrowsePageActions browsePageActions = new BrowsePageActions(driver);
        Thread.sleep(2000);
        while (isElementPresent(getBy(productPageLocators.get("ComingSoonButton"))) || isElementPresent(getBy(productPageLocators.get("NotifyButton")))) {
            logger.info("Item was amongst coming up items.");
            driver.navigate().back();
            Thread.sleep(3000);
            browsePageActions.selectAnyItemFromSearchedList();
            return false;
        }
        int count = 0;
        while ((!isElementPresent(getBy(productPageLocators.get("AvailabilityCheckInput")))) && (!isElementPresent(getBy(productPageLocators.get("AvailabilityChangeLink")))) && count < 5) {
            swipeUp(5);
            count++;
        }
        Thread.sleep(1000);
        if (isElementPresent(getBy(productPageLocators.get("AvailabilityCheckInput")))) {
            driver.findElement(getBy(productPageLocators.get("AvailabilityCheckInput"))).sendKeys(pincode);
            Thread.sleep(1000);
            driver.findElement(getBy(productPageLocators.get("AvailabilityCheckButton"))).click();
            Thread.sleep(3000);
        }
        if (isElementPresent(getBy(productPageLocators.get("AvailabilityCheckSuccess"))) && isElementPresent(getBy(productPageLocators.get("AvailabilityChangeLink")))) {
            logger.info("The product is available in the specified Pincode.");
            return true;
        } else if (isElementPresent(getBy(productPageLocators.get("AvailabilityTick")))) {
            logger.info("The product is available in the specified Pincode.");
            return true;
        } else return false;
    }

    /***
     * Validates the offers on the product page.
     *
     * @return if the terms and conditions and offers title are listed or not
     * @throws InterruptedException
     */
    public boolean validateOfferOnProductPage() throws InterruptedException {
        int count = 0;
        if (isElementPresent(getBy(productPageLocators.get("ProductPageOffersTag")))) {
            while ((!isElementPresent(getBy(productPageLocators.get("ProductPageOffersLayout")))) && count < 5) {
                swipeUp(5);
                count++;
            }
            if (isElementPresent(getBy(productPageLocators.get("ProductPageOfferReadMore")))) {
                driver.findElement(getBy(productPageLocators.get("ProductPageOfferReadMore"))).click();
                Thread.sleep(2000);
            }
            List<WebElement> offerList = driver.findElements(getBy(productPageLocators.get("ProductPageOfferListTitle")));
            for (WebElement anOfferList : offerList) {
                if (anOfferList.getText() == null) {
                    logger.info("ProductOfferText is null");
                    return false;
                }
            }
            List<WebElement> tncList = driver.findElements(getBy(productPageLocators.get("ProductPageOffersTermsConditionsLink")));
            for (WebElement aTncList : tncList) {
                String TermsAndConditions = aTncList.getText();
                if (TermsAndConditions.equals("View Terms & Conditions")) {
                    aTncList.click();
                    Thread.sleep(5000);
                    waitForElementPresent(productPageLocators.get("ProductOfferTermsTitle"), 5);
                    if (isElementPresent(getBy(productPageLocators.get("ProductOfferTermsTitle")))) {
                        if (!driver.findElement(getBy(productPageLocators.get("ProductOfferTermsTitle"))).getText().equals("Offer Terms & Conditions")) {
                            logger.info("The text on the offer terms and conditions is not the expected one");
                            return false;
                        }
                        if (driver.findElement(getBy(productPageLocators.get("ProductOfferTermsContinueShopping"))).getText().equalsIgnoreCase("Continue Shopping")) {
                            driver.findElement(getBy(productPageLocators.get("ProductOfferTermsContinueShopping"))).click();
                            waitForElementPresent(productPageLocators.get("ProductPageOfferReadMore"), 5);
                            if (!isElementPresent(getBy(productPageLocators.get("ProductPageOfferReadMore")))) {
                                logger.info("We are not back on the product page after clicking the continue shopping button");
                                return false;
                            }
                        } else {
                            logger.info("Continue shopping button message is not the expected one");
                            return false;
                        }
                    }
                } else {
                    logger.info("The text on the terms and conditions bar is not View Terms & Conditions");
                    return false;
                }
            }
        } else {
            logger.info("There are no offers for this product.");
            return true;
        }
        return true;
    }

    /***
     * Validates the ratings and reviews displayed on the product page for the products.
     *
     * @param rating  flag for ratings
     * @param reviews flag for reviews
     * @return if the ratings and reviews are displayed correctly or not
     * @throws InterruptedException
     */
    public boolean validateRatingOnProductPage(boolean rating, boolean reviews) throws InterruptedException {
        String productTitle = getProductName();
        int count = 0;
        if (rating || reviews) {
            while ((!isElementPresent(getBy(productPageLocators.get("ProductPageImagePriceLayout")))) && count < 5) {
                logger.error("Rating Layout not yet visible.");
                swipeUp(5);
                count++;
            }
        } else {
            logger.info("The " + productTitle + " does not have any rating or reviews until now.");
            return true;
        }
        if ((rating) && (!driver.findElement(getBy(productPageLocators.get("ItemRatingCount"))).getText().contains("Ratings"))) {
            logger.error("Rating text is not present on the Product Page");
            return false;
        }
        if ((reviews) && (!driver.findElement(getBy(productPageLocators.get("ItemRatingCount"))).getText().contains("Reviews"))) {
            logger.error("Reviews text is not present on the Product Page");
            return false;
        }
        driver.findElement(getBy(productPageLocators.get("ReviewRatingLayout"))).click();
        waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
        if (!driver.findElement(getBy(productPageLocators.get("ProductPageTitle"))).getText().equals(productTitle)) {
            logger.error("The Product Title on the rating and review page is not same as on Product Page");
            return false;
        }
        driver.navigate().back();
        Thread.sleep(3000);
        waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
        if (!driver.findElement(getBy(productPageLocators.get("ProductPageTitle"))).getText().equals(productTitle)) {
            logger.error("We are not back on the product page after clicking back button on ratings and review page");
            return false;
        }
        return true;
    }

    /***
     * Validates the Seller information for the product.
     *
     * @param otherSeller flag if the seller is WS Retail or some other seller
     * @return if the details displayed are correc for seller or not
     * @throws InterruptedException
     */
    public boolean validateSeller(boolean otherSeller) throws InterruptedException {
        String productTitle = getProductName();
        int count = 0;
        while ((!isElementPresent(getBy(productPageLocators.get("ProductSpecificationLayout")))) && count < 5) {
            logger.error("Product Specification Layout not yet visible.");
            swipeUp(5);
            count++;
        }
        if (driver.findElement(getBy(productPageLocators.get("ProductPageSellerInfoTitle"))).getText().contains("Seller Information")
                && driver.findElement(getBy(productPageLocators.get("ProductSellerSoldByText"))).getText().contains("Sold by:")
                && (driver.findElement(getBy(productPageLocators.get("ProductPagereturnPolicyLink"))).getText().contains("30 Day Replacement Guarantee")
                || driver.findElement(getBy(productPageLocators.get("ProductPagereturnPolicyLink"))).getText().contains("30 Day Exchange Policy"))) {
            if (driver.findElement(getBy(productPageLocators.get("ProductPageSellerInfo"))).getText().contains("WS Retail")) {
                if (!driver.findElement(getBy(productPageLocators.get("ProductPageDeliveryThroughTitle"))).getText().contains("Delivered through")
                        && !isElementPresent(getBy(productPageLocators.get("ProductPageDeliveryThroughImage")))) {
                    logger.error("WS retail validation failed");
                    return false;
                }
            }
        } else {
            logger.error("Validation of Seller Information has failed");
            return false;
        }
        if (otherSeller) {
            if (!driver.findElement(getBy(productPageLocators.get("ProductPageOtherSellersText"))).getText().contains("Sellers from")
                    && driver.findElement(getBy(productPageLocators.get("ProductPageOtherSellerPriceInfo"))).getText().contains("Rs.")) {
                logger.error("Product Other Seller validation failed");
                return false;
            }
            driver.findElement(getBy(productPageLocators.get("ProducePageOtherSellersLayout"))).click();
            waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
            if (!driver.findElement(getBy(productPageLocators.get("ProductPageTitle"))).getText().contains(productTitle)) {
                logger.error("Product Title on the Other Seller Page is not same as on Product Page");
                return false;
            }
            driver.navigate().back();
            waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
            if (!driver.findElement(getBy(productPageLocators.get("ProductPageTitle"))).getText().contains(productTitle)) {
                logger.error("We are not back on the same Product Page from the Other seller Page");
                return false;
            }
        }
        return true;
    }

    /***
     * Validates the warranty text displayed against the product item searched.
     *
     * @return if the warranty text displayed is correct or not
     * @throws InterruptedException
     */
    public boolean validateWarranty() throws InterruptedException {
        waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
        int count = 0;
        while ((!isElementPresent(getBy(productPageLocators.get("ProductWarrantyLayout")))) && count < 5) {
            logger.error("Warranty Layout not yet visible.");
            swipeUp(5);
            count++;
        }
        if (!driver.findElement(getBy(productPageLocators.get("ProductWarrantyTitle"))).getText().contains("Warranty")) {
            logger.error("Product Warranty Title validation has failed");
            return false;
        }
        if (!isElementPresent(getBy(productPageLocators.get("ProductWarrantyText")))) {
            logger.error("Product Warranty text validation has failed");
            return false;
        }
        return true;
    }

    /***
     * Validates the specification of the product specified in the product page.
     *
     * @return specification of the product are displayed for the same product or not
     * @throws InterruptedException
     */
    public boolean validateSpecifications() throws InterruptedException {
        waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
        String productTitle = getProductName();
        int count = 0;
        while ((!isElementPresent(getBy(productPageLocators.get("ProductSpecificationLayout")))) && count < 5) {
            logger.error("Specification Layout not yet visible.");
            swipeUp(5);
            count++;
        }
        if (!isElementPresent(getBy(productPageLocators.get("ProductSpecification")))) {
            logger.error("Specifications is not present on the Product Page");
            return false;
        }
        if (driver.findElement(getBy(productPageLocators.get("ProductSpecification"))).getText().contains("Specifications")) {
            driver.findElement(getBy(productPageLocators.get("ProductSpecification"))).click();
            Thread.sleep(2000);
            waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
            String titleOnSpecificationsPage = driver.findElement(getBy(productPageLocators.get("ProductPageTitle"))).getText();
            if (!titleOnSpecificationsPage.equals(productTitle)) {
                logger.error("The title on Product page and on Specification page is not same");
                return false;
            }
            driver.navigate().back();
            Thread.sleep(5000);
            waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
            if (!driver.findElement(getBy(productPageLocators.get("ProductPageTitle"))).getText().contains(productTitle)) {
                logger.error("We are not back on the Product Page from the Specification Page");
                return false;
            }
        } else {
            logger.error("Product Specifications failed");
            return false;
        }
        return true;
    }

    /***
     * Validates the summary of the product displayed. Pre-Condition : Valid only for lifestyle products
     *
     * @return if the summary of the product is displayed or not
     * @throws InterruptedException
     */
    public boolean validateSummary() throws InterruptedException {
        waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
        String productTitle = getProductName();
        int count = 0;
        while ((!isElementPresent(getBy(productPageLocators.get("ProductSummaryLayout")))) && count < 5) {
            logger.error("Summary Layout not yet visible.");
            swipeUp(5);
            count++;
        }
        if (!isElementPresent(getBy(productPageLocators.get("ProductSummaryButton")))) {
            logger.error("Summary is not present on the Product Page.");
            return false;
        }
        waitForElementPresent(productPageLocators.get("ProductSummaryButton"), 2);
        if (driver.findElement(getBy(productPageLocators.get("ProductSummaryButton"))).getText().contains("Summary")) {
            driver.findElement(getBy(productPageLocators.get("ProductSummaryButton"))).click();
            waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
            if (!driver.findElement(getBy(productPageLocators.get("ProductPageTitle"))).getText().equals(productTitle)) {
                logger.error("The title on Product page and on Summary page is not same");
                return false;
            }
            driver.navigate().back();
            waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
            if (!driver.findElement(getBy(productPageLocators.get("ProductPageTitle"))).getText().equals(productTitle)) {
                logger.error("We are not back on the Product Page from summary page");
                return false;
            }
        } else {
            logger.error("Product Summary validation failed");
            return false;
        }
        return true;
    }

    /***
     * Validates the recommendations for products on the product page
     *
     * @return if the recommendations are displayed for the product or not
     * @throws InterruptedException
     */
    public boolean validateRecommendations() throws InterruptedException {
        waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
        int count = 0;
        while ((!isElementPresent(getBy(productPageLocators.get("RecommendationWidget")))) && count < 6) {
            logger.error("Recommendation Widget Layout is not yet visible.");
            swipeUp(5);
            count++;
        }
        if (!isElementPresent(getBy(productPageLocators.get("RecommendationsTitle")))) {
            logger.error("Recommendations is not present on the Product Page.");
            return false;
        }
        waitForElementPresent(productPageLocators.get("RecommendationsViewAll"), 2);
        driver.findElement(getBy(productPageLocators.get("RecommendationsViewAll"))).click();
        waitForElementPresent(productPageLocators.get("ProductItemListLayout"), 5);
        if (!isElementPresent(getBy(productPageLocators.get("ProductItemListLayout")))) {
            logger.info("Successfully navigated to the recommended products.");
            return false;
        }
        driver.navigate().back();
        waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
        if (isElementPresent(getBy(productPageLocators.get("ProductPageTitle")))) {
            logger.info("Successfully navigated back to the Product page.");
            return true;
        } else {
            logger.error("Product Summary validation failed");
            return false;
        }
    }

    /***
     * Verifies the similar searches for a lifestyle product.
     * - Precondition : lifestyle products
     *
     * @return if the similar searches were listed or not
     * @throws InterruptedException
     */
    public boolean validateSimilarProductSearches() throws InterruptedException {
        if (isElementPresent(getBy(browsePageLocators.get("ProductPageSimilarSearch")))) {
            if (isElementPresent(getBy(browsePageLocators.get("ProductPageOffersImage")))) {
                driver.findElement(getBy(productPageLocators.get("ProductPageSimilarSearch"))).click();
                Thread.sleep(1000);
                if (isElementPresent(getBy(browsePageLocators.get("SimilarSearchHeader")))) {
                    if (!driver.findElement(getBy(browsePageLocators.get("SimilarProductTitle"))).getText().equalsIgnoreCase("Similar Products")) {
                        logger.error("Successfully navigated to the similar searches for the product.");
                        return false;
                    }
                }
                driver.navigate().back();
                waitForElementPresent(productPageLocators.get("ProductPageTitle"), 5);
                if (isElementPresent(getBy(productPageLocators.get("ProductPageTitle")))) {
                    logger.info("Successfully navigated back to the Product page.");
                    return true;
                } else {
                    logger.error("Product Similar Searches validation failed");
                    return false;
                }
            } else {
                logger.error("Product Similar Searches validation failed");
                return false;
            }
        } else {
            logger.info("This is not a lifestyle product.");
            return true;
        }
    }

    /***
     * Validates the swatches for lifestyle products.
     *
     * @return if the lifestyle products has correct swatches or not
     * @throws InterruptedException
     */
    public boolean validateSwatches() throws InterruptedException {
        List<WebElement> swatchCategoryList = driver.findElements(getBy(productPageLocators.get("SwatchSizeColor")));
        List<WebElement> swatchSubCategoryList = driver.findElements(getBy(productPageLocators.get("SwatchList")));
        int count = 0;
        while ((!isElementPresent(getBy(productPageLocators.get("SwatchLayout")))) && count < 5) {
            logger.error("Swatches layout not displayed on the screen.");
            swipeUp(5);
            count++;
        }
        waitForElementPresent(productPageLocators.get("SwatchLayout"), 3);
        if (!(swatchCategoryList.get(0).getText().equalsIgnoreCase("Select Size"))) {
            logger.error("Tab for selecting size swatches not displayed.");
            return false;
        }
        if (!(swatchCategoryList.get(1).getText().equalsIgnoreCase("Select Color"))) {
            logger.error("Tab for selecting color swatches not displayed.");
            return false;
        }

        for (WebElement aSwatchCategoryList : swatchCategoryList) {
            aSwatchCategoryList.click();
            for (int i = 0; i < swatchSubCategoryList.size(); i++) {
                swatchSubCategoryList.get(i).click();
                if (!isElementPresent(getBy(productPageLocators.get("ProductPageTitle")))) {
                    logger.error(swatchSubCategoryList.get(i).getText() + " not selected for " + aSwatchCategoryList.getText());
                    return false;
                } else {
                    count = 0;
                    while ((!isElementPresent(getBy(productPageLocators.get("SwatchLayout")))) && count < 5) {
                        logger.error("Swatches layout not displayed on the screen.");
                        swipeUp(5);
                        count++;
                    }
                }
                logger.info("Selected Size swatch for the product.");
            }
        }
        return true;
    }
}