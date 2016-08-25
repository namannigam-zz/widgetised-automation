package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.*;

import java.util.List;
import java.util.Objects;

/**
 * Created by vishwanath.s on 18/02/16.
 */
@SuppressWarnings("RedundantIfStatement")
public class ProductPageV3 extends AppiumBasePage {

    //TODO add to cart
    //TODO buy now

    public enum ProductPageParameters {
        SUCCESS, FAILURE, UNKNOWN_STATUS,
        OFFERS_AVAILABLE, OFFERS_NOT_AVAILABLE, SELLER_SERVICES_DETAILS_NOT_FOUND, OFFERS_NOT_DISPLAYED, OFFERS_COUNT_MISMATCH,
        PINCODE_LAYOUT_NOT_FOUND, INVALID_PINCODE, NOT_SERVICEABLE, SERVICEABLE,
        PRODUCT_DETAILS_PANEL_NOT_FOUND,
        WARRANTY_DETAILS_NOT_AVAILABLE, WARRANTY_DETAILS_DATA_ERROR,
        SPECIFICATION_DETAILS_NOT_AVAILABLE, SPECIFICATION_DETAILS_DATA_ERROR,
        RECO_PRODUCT_IMAGE_NOT_DISPLAYED, RECO_PRODUCT_TITLE_NOT_DISPLAYED, RECO_PRODUCT_PRICE_NOT_DISPLAYED, RECO_LAYOUT_NOT_AVAILABLE,
        SIMILAR_PRODUCT_LAYOUT_NOT_AVAILABLE, SIMILAR_PRODUCT_IMAGE_NOT_DISPLAYED, SIMILAR_PRODUCT_TITLE_NOT_DISPLAYED, SIMILAR_PRODUCT_PRICE_NOT_DISPLAYED,
        SELLER_LAYOUT_NOT_FOUND, SELLER_NAME_NOT_FOUND, SELLER_RATING_NOT_FOUND, SELLER_PRODUCT_PRICE_NOT_FOUND, SELLER_PRODUCT_DELIVERY_INFO_NOT_FOUND,
        PRODUCT_IMAGES_NOT_AVAILABLE, SLIDER_NOT_FOUND
    }

    /**
     *
     */
    public class Price {
        private int fsp;
        private int mrp;
        private int discount;

        public int getFSP() {
            return this.fsp;
        }

        public int getMRP() {
            return this.mrp;
        }

        public int getDicountedPercentage() {
            return this.discount;
        }

        public void setFSP(int fsp) {
            this.fsp = fsp;
        }

        public void setMRP(int mrp) {
            this.mrp = mrp;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }
    }

    public ProductPageV3(WebDriver driver) {
        super(driver);
    }

    boolean dgStatus = false;

    /***
     * Gets the selling price of the displayed product.
     *
     * @return the selling price of the product
     * @throws InterruptedException
     */
    public Price getProductPrice() throws InterruptedException {

        WebElement priceLayout = driver.findElementById(productPageLocators.get("PPv3ProductPriceId"));
        if (priceLayout != null) {
            Price p = new Price();
            List<WebElement> elements = priceLayout.findElements(By.className(productPageLocators.get("AndroidTextView")));

            if (elements != null && elements.size() > 0) {
                int fsp = Integer.parseInt(elements.get(0).getText().replace("Rs.", "").replace(",", "").trim());
                int mrp = Integer.parseInt(elements.get(1).getText().replace("Rs.", "").replace(",", "").trim());
                String a = elements.get(2).getText();
                String b = a.replace("%", "");
                String c = b.replace("OFF", "");
                int discount = Integer.parseInt(elements.get(2).getText().replace("%", "").replace("OFF", "").trim());

                p.setFSP(fsp);
                p.setMRP(mrp);
                p.setDiscount(discount);

                return p;
            }
        }

        return null;
    }

    /**
     * @return
     * @throws InterruptedException
     */
    public boolean getDiscount() throws InterruptedException {
        Price p = getProductPrice();
        int priceDiscount = p.getMRP() - p.getFSP();
        int discountPercentage = (int) Math.floor((float) priceDiscount / p.getMRP() * 100);
        return (p.getFSP() < p.getMRP()) && (discountPercentage == p.getDicountedPercentage());
    }


    /***
     * Returns the name of the product displayed on the product page.
     *
     * @return the product name as a string
     */
    public String getProductName() {
        WebElement layout = driver.findElementById(productPageLocators.get("PPv3ProductTitleId"));

        if (layout == null)
            return null;

        WebElement title = layout.findElement(By.className(productPageLocators.get("AndroidTextView")));

        if (title != null)
            return title.getText();

        return null;
    }

    /**
     * @return
     */
    private Point getProductImageLocation() {
        Point p = null;
        WebElement productImage = driver.findElementById(productPageLocators.get("PPv3ProductImageView"));
        if (productImage != null) {
            p = productImage.getLocation();
        }
        return p;
    }

    /***
     * Displays a slide show of the product images.
     *
     * @return if all the images were displayed properly for a product or not
     * @throws InterruptedException
     */
    public ProductPageParameters slideshowProductImage() throws InterruptedException {
        if (!isElementPresent(By.id(productPageLocators.get("PPv3SliderId")))) {
            return ProductPageParameters.SLIDER_NOT_FOUND;
        }
        WebElement slider = driver.findElement(getBy(productPageLocators.get("PPv3SliderId")));
        List<WebElement> images = slider.findElements(By.className(productPageLocators.get("AndroidViewClass")));
        if (images.size() == 0) {
            return ProductPageParameters.PRODUCT_IMAGES_NOT_AVAILABLE;
        }
        Point p = getProductImageLocation();
        for (WebElement e : images) {
            driver.swipe(screenWidth() - 200, p.y + 20, 20, p.y + 20, 0);
            //TODO How do we validate new product image?
        }
        return ProductPageParameters.SUCCESS;
    }

    /***
     * Swipes to the next product to view on the product page.
     *
     * @return true if the product is swiped (determined using the product title)
     * @throws InterruptedException
     */
    public boolean swipeNextProduct() throws InterruptedException {
        String previousProductTitle = getProductName();
        logger.info("Product active on the app: " + previousProductTitle);

        WebElement element = driver.findElementById(productPageLocators.get("PPv3ProductTitleId"));
        Point p = element.getLocation();
        int x = p.getX();
        int y = p.getY();
        int w = screenWidth();

        driver.swipe(screenWidth() - 50, y - 20, 20, y - 20, 0);

        //TODO Find better way to know new product page is loaded. Probably wait till Waiting image disappears?
        Thread.sleep(2000);

        String currentProduct = getProductName();
        logger.info("Product active on the app after swipe: " + currentProduct);

        return !previousProductTitle.equals(currentProduct);
    }

    /***
     * Swipes to the previous product to view on the product page.
     *
     * @return true if the product is swiped (determined using the product title)
     * @throws InterruptedException
     */
    public boolean swipePreviousProduct() throws InterruptedException {
        String previousProductTitle = getProductName();
        logger.info("Product active on the app: " + previousProductTitle);

        WebElement element = driver.findElementById(productPageLocators.get("PPv3ProductTitleId"));
        Point p = element.getLocation();
        int x = p.getX();
        int y = p.getY();
        int w = screenWidth();

        driver.swipe(20, y - 20, screenWidth() - 50, y - 20, 0);

        //TODO Find better way to know new product page is loaded. Probably wait till Waiting image disappears?
        Thread.sleep(2000);

        String currentProduct = getProductName();
        logger.info("Product active on the app after swipe: " + currentProduct);

        return !previousProductTitle.equals(currentProduct);
    }

    /***
     * Checks the availability of the product on given PIN Code in product page.
     *
     * @param pincode input tp change the pincode
     * @return if the availability was checked or not for the product
     * @throws InterruptedException
     */
    public ProductPageParameters checkAvailability(String pincode) throws InterruptedException {
        //TODO Add steps to open an active product if current product is in Coming Soon state

        scrollToSellerSection();

        WebElement pincodeLayout = driver.findElementById(productPageLocators.get("PPv3PincodeLayout"));

        //In case existing pincode is not serviceable, we get error layout
        if (pincodeLayout == null)
            pincodeLayout = driver.findElementById(productPageLocators.get("PPv3PincodeErrorLayout"));

        if (pincodeLayout == null)
            return ProductPageParameters.PINCODE_LAYOUT_NOT_FOUND;

        pincodeLayout.click();
        logger.info("Clicked on pincode layout");

        if (!isElementPresent(By.id(productPageLocators.get("PPv3PincodeErrorDialog")))) {
            return ProductPageParameters.PINCODE_LAYOUT_NOT_FOUND;
        }

        WebElement pincodeTextView = driver.findElementById(productPageLocators.get("PPv3PincodeEditTextId"));
        pincodeTextView.sendKeys(pincode);

        WebElement submitButton = driver.findElementById(productPageLocators.get("PPv3PincodeSubmitButtonId"));
        submitButton.click();

        if (isElementPresent(By.id(productPageLocators.get("PPv3PincodeErrorTextViewId")))) {
            String error = driver.findElementById(productPageLocators.get("PPv3PincodeErrorTextViewId")).getText();

            if (error.equals("Please enter a valid pincode")) {
                driver.navigate().back();
                return ProductPageParameters.INVALID_PINCODE;
            }
        }

        if (isElementPresent(By.id(productPageLocators.get("PPv3PincodeErrorLayoutId")))) {
            String pincodeError = driver.findElementById(productPageLocators.get("PPv3PincodeErrorLayoutId")).getText();
            if (pincodeError.contains("No Seller Ships To " + pincode)) {
                return ProductPageParameters.NOT_SERVICEABLE;
            }
        }
        pincodeTextView = driver.findElementById(productPageLocators.get("PPv3PincodeTextView"));
        if (pincodeTextView.getText().contains("seller delivers to " + pincode))
            return ProductPageParameters.SERVICEABLE;

        return ProductPageParameters.UNKNOWN_STATUS;
    }

    /***
     * Validates the Seller information for the product.
     *
     * @return if the details displayed are correc for seller or not
     * @throws InterruptedException
     */
    public ProductPageParameters validateSeller() throws InterruptedException {
        scrollToSellerSection();

        WebElement sellerTab = driver.findElementById(productPageLocators.get("PPv3SellerTextView"));
        sellerTab.click();

        //TODO checkAvailability to set serviceable pincode to get valid seller data

        scrollToSellerLayout();

        //Select default seller (to hide the information popup on the seller layout)
        List<WebElement> sellers = driver.findElementsById(productPageLocators.get("PPv3SelectSellerCheckboxId"));
        if (sellers.size() == 0)
            return ProductPageParameters.SELLER_SERVICES_DETAILS_NOT_FOUND;

        sellers.get(0).click();

        if (!isElementPresent(By.id(productPageLocators.get("PPv3SellerLayoutId")))) {
            return ProductPageParameters.SELLER_LAYOUT_NOT_FOUND;
        }

        if (!isElementPresent(By.id(productPageLocators.get("PPv3SellerName")))) {
            return ProductPageParameters.SELLER_NAME_NOT_FOUND;
        }

        if (!isElementPresent(By.id(productPageLocators.get("PPv3SellerScore")))) {
            return ProductPageParameters.SELLER_RATING_NOT_FOUND;
        }

        if (!isElementPresent(By.id(productPageLocators.get("PPv3SellerProductFinalPriceId")))) {
            return ProductPageParameters.SELLER_PRODUCT_PRICE_NOT_FOUND;
        }

        if (!isElementPresent(By.id(productPageLocators.get("PPv3SellerDeliverySpeed")))) {
            return ProductPageParameters.SELLER_PRODUCT_DELIVERY_INFO_NOT_FOUND;
        }

        //TODO callouts to be validated - delivery, replacement, cod
        //TODO Multiple seller validations

        return ProductPageParameters.SUCCESS;
    }

    /**
     * @return
     */
    private ProductPageParameters openProductDetails() {
        WebElement detailsPanel = null;
        int scrollCount = 0;
        while (detailsPanel == null && scrollCount++ < 6) {
            detailsPanel = driver.findElementById(productPageLocators.get("PPv3ProductDetailsTextView"));
            swipeUp(5);
        }

        swipeUp(5);

        if (detailsPanel == null)
            return ProductPageParameters.PRODUCT_DETAILS_PANEL_NOT_FOUND;

        detailsPanel.click();

        return ProductPageParameters.SUCCESS;
    }

    /**
     * @return
     */
    private boolean scrollToSellerSection() {
        List<WebElement> sellerDetails = null;
        int scrollCount = 0;

        do {
            sellerDetails = driver.findElements(By.id(productPageLocators.get("PPv3SellerTextView")));
            swipeUp(5);
            scrollCount++;
        } while (sellerDetails.size() == 0 && scrollCount < 6);

        return sellerDetails.size() != 0;

    }

    /**
     * @return
     */
    private boolean scrollToSellerLayout() {
        List<WebElement> sellerDetails = null;
        int scrollCount = 0;

        do {
            sellerDetails = driver.findElements(By.id(productPageLocators.get("PPv3CalloutViewId")));
            swipeUp(5);
            scrollCount++;
        } while (sellerDetails.size() == 0 && scrollCount < 6);

        return sellerDetails.size() != 0;

    }

    /**
     * @return
     */
    private boolean scrollToWarrantyDetails() {
        List<WebElement> warrantyDetails = null;
        int scrollCount = 0;

        do {
            warrantyDetails = driver.findElements(By.id(productPageLocators.get("PPv3ProductDescriptionId")));
            swipeUp(5);
            scrollCount++;
        } while (warrantyDetails.size() == 0 && scrollCount < 6);

        return warrantyDetails != null;

    }

    /**
     * @return
     */
    private boolean scrollToSpecificationSection() {
        List<WebElement> warrantyDetails = null;
        int scrollCount = 0;

        do {
            warrantyDetails = driver.findElements(By.id(productPageLocators.get("PPv3ProductSpecLayoutId")));
            swipeUp(5);
            scrollCount++;
        } while (warrantyDetails.size() == 0 && scrollCount < 6);

        return warrantyDetails != null;

    }


    /***
     * Validates the warranty text displayed against the product item searched.
     *
     * @return if the warranty text displayed is correct or not
     * @throws InterruptedException
     */
    public ProductPageParameters validateWarranty() throws InterruptedException {
        openProductDetails();

        if (!scrollToWarrantyDetails())
            return ProductPageParameters.WARRANTY_DETAILS_NOT_AVAILABLE;

        WebElement warrantyBlock = driver.findElementById(productPageLocators.get("PPv3ProductDescriptionId"));
        List<WebElement> warrantyLayout = warrantyBlock.findElements(By.className(productPageLocators.get("AndroidLinearLayout")));

        if (warrantyLayout.size() == 0)
            return ProductPageParameters.FAILURE;

        if (warrantyLayout == null || warrantyLayout.size() == 0)
            return ProductPageParameters.WARRANTY_DETAILS_NOT_AVAILABLE;

        List<WebElement> warrantyElements = warrantyLayout.get(0).findElements(By.className(productPageLocators.get("AndroidTextView")));

        if (warrantyElements.size() == 0)
            return ProductPageParameters.WARRANTY_DETAILS_NOT_AVAILABLE;

        if (warrantyElements.get(0).getText().equalsIgnoreCase("WARRANTY") && !warrantyElements.get(1).getText().isEmpty())
            return ProductPageParameters.SUCCESS;

        return ProductPageParameters.WARRANTY_DETAILS_DATA_ERROR;

        //TODO Click on more info and validate details on the popout layout
    }

    /***
     * Validates the specification of the product specified in the product page.
     *
     * @return specification of the product are displayed for the same product or not
     * @throws InterruptedException
     */
    public ProductPageParameters validateSpecifications() throws InterruptedException {
        openProductDetails();

        if (!scrollToSpecificationSection())
            return ProductPageParameters.SPECIFICATION_DETAILS_NOT_AVAILABLE;

        WebElement specificationsBlock = driver.findElementById(productPageLocators.get("PPv3ProductSpecLayoutId"));
        List<WebElement> specificationsLayout = specificationsBlock.findElements(By.className(productPageLocators.get("AndroidLinearLayout")));

        if (specificationsLayout.size() == 0)
            return ProductPageParameters.FAILURE;

        if (specificationsLayout == null || specificationsLayout.size() == 0)
            return ProductPageParameters.SPECIFICATION_DETAILS_NOT_AVAILABLE;

        List<WebElement> specificationElements = specificationsLayout.get(0).findElements(By.className(productPageLocators.get("AndroidTextView")));

        if (specificationElements.size() == 0)
            return ProductPageParameters.SPECIFICATION_DETAILS_NOT_AVAILABLE;

        return ProductPageParameters.SUCCESS;
        //TODO Click on the specs and validate details on the popout layout
    }

    /**
     * @return
     */
    private boolean scrollToRecommendationsSection() {
        List<WebElement> recoLayout;
        int scrollCount = 0;

        do {
            recoLayout = driver.findElementsById(productPageLocators.get("PPv3RecoTab"));
            swipeUp(5);
        } while (recoLayout.size() == 0 && scrollCount < 6);

        if (recoLayout.size() == 0)
            return false;

        swipeUp(5);

        return true;
    }

    /***
     * Validates the recommendations for products.
     *
     * @return if the recommendations are displayed for the product or not
     * @throws InterruptedException
     */
    public ProductPageParameters validateRecommendations() throws InterruptedException {
        if (!scrollToRecommendationsSection())
            return ProductPageParameters.RECO_LAYOUT_NOT_AVAILABLE;

        WebElement recoLayout = driver.findElementById(productPageLocators.get("PPv3RecoTab"));
        recoLayout.click();

        List<WebElement> productImages = driver.findElementsById(productPageLocators.get("PPv3RecoProductImageViewId"));
        if (productImages.size() == 0)
            return ProductPageParameters.RECO_PRODUCT_IMAGE_NOT_DISPLAYED;

        List<WebElement> productTitle = driver.findElementsById(productPageLocators.get("PPv3RecoProductNameId"));
        if (productTitle.size() == 0)
            return ProductPageParameters.RECO_PRODUCT_TITLE_NOT_DISPLAYED;

        List<WebElement> productPrice = driver.findElementsById(productPageLocators.get("PPv3RecoProductPriceId"));
        if (productPrice.size() == 0)
            return ProductPageParameters.RECO_PRODUCT_PRICE_NOT_DISPLAYED;

        return ProductPageParameters.SUCCESS;
    }

    /***
     * Verifies the similar searches for a lifestyle product.
     *
     * @return if the similar searches were listed or not
     * @throws InterruptedException
     */
    public ProductPageParameters validateSimilarProductSearches() throws InterruptedException {
        if (!scrollToRecommendationsSection())
            return ProductPageParameters.SIMILAR_PRODUCT_LAYOUT_NOT_AVAILABLE;

        WebElement similarProductLayout = driver.findElementById(productPageLocators.get("PPv3RecoSimilarProductTabId"));
        similarProductLayout.click();

        List<WebElement> productImages = driver.findElementsById(productPageLocators.get("PPv3RecoProductImageViewId"));
        if (productImages.size() == 0)
            return ProductPageParameters.SIMILAR_PRODUCT_IMAGE_NOT_DISPLAYED;

        List<WebElement> productTitle = driver.findElementsById(productPageLocators.get("PPv3RecoProductNameId"));
        if (productTitle.size() == 0)
            return ProductPageParameters.SIMILAR_PRODUCT_TITLE_NOT_DISPLAYED;

        List<WebElement> productPrice = driver.findElementsById(productPageLocators.get("PPv3RecoProductPriceId"));
        if (productPrice.size() == 0)
            return ProductPageParameters.SIMILAR_PRODUCT_PRICE_NOT_DISPLAYED;

        return ProductPageParameters.SUCCESS;
    }

    /**
     * @return
     */
    public boolean findPingButton() {
        //Find ping button on the product page
        WebElement pingButton = driver.findElementById(productPageLocators.get("PPv3PingButtonId"));
        while (!isElementPresent(getBy(productPageLocators.get("PPv3PingButtonId")))) {
            swipeUp(2);
        }
        if (pingButton == null) {
            logger.error("Ping button was not found on the product page");
            return false;
        }

        pingButton.click();
        WebElement chatHeadContainer = driver.findElementById(productPageLocators.get("PPv3PingChatHeader"));
        if (chatHeadContainer != null) {
            driver.navigate().back();
            //TODO Close the chat header
            return true;
        }

        return false;
    }

    /**
     * @return
     */
    public boolean addToWishList() {
        WebElement wishlistButton = driver.findElementById(productPageLocators.get("PPv3AddToWishListButtonId"));

        if (wishlistButton == null) {
            logger.error("Wishlist button was not found on the product page");
            return false;
        }

        wishlistButton.click();
        //TODO Verify toast message
        //TODO Click on add to wishlist again and verify item is removed from wishlist
        //TODO Validate wishlist addition from WishlistPage
        return true;
    }

    /**
     * @return
     */
    public boolean isProductImageAvailable() {
        return isElementPresent(By.id(productPageLocators.get("PPv3ProductImageView")));
    }

    /**
     * @return
     */
    public int getReviewCountInRatingBar() {
        WebElement ratingBar = driver.findElementById(productPageLocators.get("PPv3ProductRatingBarId"));

        if (ratingBar != null) {
            WebElement reviewElement = ratingBar.findElement(By.className(productPageLocators.get("AndroidTextView")));

            if (reviewElement != null) {
                String reviewCount = reviewElement.getText();

                if (reviewCount == null)
                    return -1;

                reviewCount = reviewCount.replace("(", "").replace(")", "").trim();

                return Integer.parseInt(reviewCount);
            }
        }

        return -1;
    }

    /**
     * @return
     */
    private boolean clickOnReviewTab() {
        int scrollCount = 0;
        WebElement reviewTab = null;
        while (reviewTab == null && scrollCount++ < 5) {
            logger.error("Swatches layout not displayed on the screen.");
            reviewTab = driver.findElementById(productPageLocators.get("PPv3ReviewTabId"));
            swipeUp(5);
        }

        if (reviewTab == null)
            return false;

        reviewTab.click();

        return true;
    }

    /**
     * @return
     */
    public int getTotalNumberOfReviews() {

        if (!clickOnReviewTab())
            return -1;

        WebElement totalReviews = driver.findElementById(productPageLocators.get("PPv3NumberOfReviewsId"));

        if (totalReviews == null)
            return -1;

        String reviewCount = totalReviews.getText();

        if (reviewCount == null)
            return -1;

        reviewCount = reviewCount.replace("from", "").replace("customers", "").trim();

        return Integer.parseInt(reviewCount);
    }

    /**
     * @return
     */
    public float getProductRating() {

        if (!clickOnReviewTab())
            return -1;

        WebElement totalReviews = driver.findElementById(productPageLocators.get("PPv3ProductRating"));

        if (totalReviews == null)
            return -1;

        String rating = totalReviews.getText();

        if (rating == null)
            return -1;

        rating = rating.substring(0, totalReviews.getText().indexOf('/'));

        return Float.parseFloat(rating);
    }

    /**
     * @return
     */
    public ProductPageParameters isOfferAvailable() {
        WebElement offerIndicator = driver.findElementById(productPageLocators.get("PPv3OffersTagId"));

        if (offerIndicator == null)
            return ProductPageParameters.OFFERS_NOT_AVAILABLE;

        return ProductPageParameters.OFFERS_AVAILABLE;
    }

    /**
     * @return
     */
    public ProductPageParameters validateOffers() {
        int scrollCount = 0;
        List<WebElement> offersSection = null;
        while (offersSection == null && scrollCount++ < 5) {
            logger.error("Offers layout not displayed on the screen.");
            offersSection = driver.findElementsById(productPageLocators.get("PPv3CalloutTextViewId"));
            swipeUp(5);
        }

        logger.info("Attemped to scroll to the offers section");

        if (offersSection == null) {
            logger.info("Offers section was not found");
            return ProductPageParameters.OFFERS_NOT_AVAILABLE;
        }

        logger.info("Offers section is available on the product page");

        // Find offers text view and click on it to launch the offers section
        List<WebElement> callouts = driver.findElements(By.id(productPageLocators.get("PPv3CalloutTextViewId")));
        for (WebElement e : callouts) {
            if (e.getText().equals("Offers")) {
                e.click();
                break;
            }
        }

        if (!isElementPresent(By.id(productPageLocators.get("PPv3ProductDetailsPageId"))))
            return ProductPageParameters.SELLER_SERVICES_DETAILS_NOT_FOUND;

        WebElement offersInfoSection = driver.findElementById(productPageLocators.get("PPv3ProductDetailsOffersDataId"));
        WebElement offersList = offersInfoSection.findElement(By.className(productPageLocators.get("AndroidLinearLayout")));
        List<WebElement> offers = offersList.findElements(By.className(productPageLocators.get("AndroidLinearLayout")));

        if (offers.size() == 0)
            return ProductPageParameters.OFFERS_NOT_DISPLAYED;

        if (!driver.findElementById(productPageLocators.get("PPv3CalloutOfferCounterId")).getText().equals(offers.size() * 2))     //There are two LinearLayout(s) for every offer display
            return ProductPageParameters.OFFERS_COUNT_MISMATCH;

        return ProductPageParameters.SUCCESS;
    }

    /**
     * Validates the swatches for the product page /v3
     *
     * @return if the swatches were displayed or not
     */
    public boolean validateSwatchesPpv3() {
        try {
            //on product page
            waitForElementPresent(productPageLocators.get("ProductTitlePpv3"), 2);
            logger.info("Located the product title");
            WebElement title = driver.findElement(getBy(productPageLocators.get("ProductTitlePpv3")));
            WebElement title1 = title.findElement(By.className("android.widget.TextView"));
            String title2 = title1.getText().replace("...", "");
            logger.info("Product name in product page : " + title2);
            swipeUp(2);

            if (isElementPresent(getBy(productPageLocators.get("SwatchLayoutPpv3"))))
                logger.info("***Located the Swatch layout");
            waitForElementPresent(productPageLocators.get("SwatchColor"), 2);
            driver.findElement(getBy(productPageLocators.get("SwatchColor"))).click();

            //inside the swatch page
            WebElement e = driver.findElement(getBy(productPageLocators.get("SwatchSummary")));
            WebElement e2 = e.findElement(By.className("android.widget.LinearLayout")).findElement(By.className("android.widget.TextView"));
            String e3 = e2.getText();
            logger.info("Product name in swatch page: " + e3);
            if (Objects.equals(e3, title2)) {
                logger.info("We have landed on the correct swatch page");
            } else {
                logger.info("Product page title: " + title2 + "/n Swatch page title: " + e3);
            }
            if (title2.contains(e3) || e3.contains(title2)) {
                logger.info("Product page title: " + title2 + "\n Swatch page title: " + e3);
            }
            WebElement parentElement = driver.findElement(getBy(productPageLocators.get("ParentElement")));
            List<WebElement> swatchList = parentElement.findElements(By.className("android.widget.TextView"));
            for (WebElement aSwatchList : swatchList) {
                aSwatchList.click();
                logger.info("Clicking on " + aSwatchList.getText());
            }
            driver.findElement(getBy(productPageLocators.get("SwatchBackButton"))).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}