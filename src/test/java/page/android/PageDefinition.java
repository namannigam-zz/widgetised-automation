package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;

public class PageDefinition extends AppiumBasePage {

    public PageDefinition(WebDriver driver) {
        super(driver);
    }

    /***
     * Enum for the type of Pages
     */
    public enum PAGE_TYPE {
        LOGIN_PAGE, SIGNUP_PAGE, BROWSE_PAGE, HOME_PAGE, SEARCH_PAGE, PROFILE_PAGE, PRODUCT_PAGE, CART_PAGE, CHECKOUT_PAGE, LANDING_PAGE, TRACKINGORDER_PAGE, WISHLIST_PAGE,
        MY_ORDER_PAGE, PING_STARTUP_PAGE, PING_PAGE, INVITE_EARN_PAGE, HELP_CENTRE, LEGAL_PAGE, NOTIFICATION_PAGE,
        LOGIN_PAGE_NOT_FOUND, SIGNUP_PAGE_NOT_FOUND, BROWSE_PAGE_NOT_FOUND, HOME_PAGE_NOT_FOUND, SEARCH_PAGE_NOT_FOUND, PROFILE_PAGE_NOT_FOUND, PRODUCT_PAGE_NOT_FOUND, CART_PAGE_NOT_FOUND,
        CHECKOUT_PAGE_NOT_FOUND, LANDING_PAGE_NOT_FOUND, TRACKINGORDER_PAGE_NOT_FOUND, WISHLIST_PAGE_NOT_FOUND, MY_ORDER_PAGE_NOT_FOUND, PING_STARTUP_PAGE_NOT_FOUND, PING_PAGE_NOT_FOUND,
        INVITE_EARN_PAGE_NOT_FOUND, HELP_CENTRE_NOT_FOUND, LEGAL_PAGE_NOT_FOUND, NOTIFICATION_PAGE_NOT_FOUND
    }

    /***
     * Method to check if the current page is a Login Page.
     *
     * @return if login page is displayed or not
     */
    public PAGE_TYPE isLoginPage() {
        if (!isElementPresent(getBy(loginPageLocators.get("LoginPassword")))) {
            logger.warn("Password field not displayed.");
            return PAGE_TYPE.LOGIN_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(loginPageLocators.get("ForgotPasswordLink")))) {
            logger.warn("Forgot Password Link not displayed.");
            return PAGE_TYPE.LOGIN_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(loginPageLocators.get("SignupButton")))) {
            logger.warn("SignUp Button not displayed.");
            return PAGE_TYPE.LOGIN_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(loginPageLocators.get("LoginEmailMobileNumber")))) {
            logger.warn("Email and Mobile Number field not displayed.");
            return PAGE_TYPE.LOGIN_PAGE_NOT_FOUND;
        }
        if (isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.error("Drawer displayed.");
            return PAGE_TYPE.LOGIN_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.LOGIN_PAGE;
    }

    /***
     * Method to check if the current page is a Browse Page.
     *
     * @return if browse page is displayed or not
     */
    public PAGE_TYPE isBrowsePage() {
        if (!isElementPresent(getBy(browsePageLocators.get("ToggleButtonLayout")))) {
            logger.warn("Toggle Button not displayed.");
            return PAGE_TYPE.BROWSE_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(browsePageLocators.get("FilterButtonLayout")))) {
            logger.warn("Filter Button not displayed.");
            return PAGE_TYPE.BROWSE_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(browsePageLocators.get("SortButtonLayout")))) {
            logger.warn("Sort Button not displayed.");
            return PAGE_TYPE.BROWSE_PAGE_NOT_FOUND;
        }
        if (isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.error("Drawer displayed.");
            return PAGE_TYPE.BROWSE_PAGE;
        }
        return PAGE_TYPE.BROWSE_PAGE;
    }

    /***
     * Method to check if the current page is a Checkout Page.
     *
     * @return if checkout page is displayed or not
     */
    public PAGE_TYPE isCheckoutPage() {
        if (!isElementPresent(getBy(checkOutPageLocators.get("ProceedToPayButton")))) {
            logger.warn("Proceed to pay option not available.");
            return PAGE_TYPE.CHECKOUT_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(checkOutPageLocators.get("CheckOutLoginText")))) {
            logger.warn("Login Text not displayed.");
            return PAGE_TYPE.CHECKOUT_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(checkOutPageLocators.get("CheckOutDeliveryText")))) {
            logger.warn("Delivery Text not displayed.");
            return PAGE_TYPE.CHECKOUT_PAGE_NOT_FOUND;
        }
        if (!isElementPresent((getBy(checkOutPageLocators.get("CheckOutPaymentText"))))) {
            logger.warn("Payment Text not displayed.");
            return PAGE_TYPE.CHECKOUT_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.CHECKOUT_PAGE;
    }

    /***
     * Method to check if the current page is a Home Page.
     *
     * @return if home page is displayed or not
     */
    public PAGE_TYPE isHomePage() {
        if (!isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.warn("Drawer not displayed.");
            return PAGE_TYPE.HOME_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(homePageLocators.get("SearchWidget")))) {
            logger.warn("Search Widget not displaye.");
            return PAGE_TYPE.HOME_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(homePageLocators.get("Toolbar")))) {
            logger.warn("Toolbar not displayed.");
            return PAGE_TYPE.HOME_PAGE_NOT_FOUND;
        }
        if (isElementPresent(getBy(loginPageLocators.get("SkipButton")))) {
            logger.error("Skip Button displayed.");
            return PAGE_TYPE.HOME_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.HOME_PAGE;
    }

    /***
     * Method to check if the current page is a Product Page.
     *
     * @return if product page is displayed or not
     */
    public PAGE_TYPE isProductPage() {
        if (!isElementPresent(getBy(productPageLocators.get("ProductPageTitle")))) {
            logger.warn("Product title not displayed.");
            return PAGE_TYPE.PRODUCT_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(productPageLocators.get("Price")))) {
            logger.warn("Product price not displayed.");
            return PAGE_TYPE.PRODUCT_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(productPageLocators.get("BuyButton")))) {
            logger.warn("Buy option not displayed.");
            return PAGE_TYPE.PRODUCT_PAGE_NOT_FOUND;
        }
        if (isElementPresent(getBy(browsePageLocators.get("Drawer")))) {
            logger.error("Drawer option displayed.");
            return PAGE_TYPE.PRODUCT_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.PRODUCT_PAGE;
    }

    /***
     * Method to check if the current page is a Search Page.
     *
     * @return if search page is displayed or not
     */
    public PAGE_TYPE isSearchPage() {
        if (!isElementPresent(getBy(searchPageLocators.get("QRCodeSearchButton")))) {
            logger.warn("QRSearch option not displayed.");
            return PAGE_TYPE.SEARCH_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(searchPageLocators.get("ImageSearchButton")))) {
            logger.warn("ImageSearch option not displayed.");
            return PAGE_TYPE.SEARCH_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(searchPageLocators.get("VoiceSearchButton")))) {
            logger.warn("VoiceSearch option not displayed.");
            return PAGE_TYPE.SEARCH_PAGE_NOT_FOUND;
        }
        if (isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.error("Drawer option displayed.");
            return PAGE_TYPE.SEARCH_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.SEARCH_PAGE;
    }

    /***
     * Method to check if the current page is a SignUp Page.
     *
     * @return if sign up page is displayed or not
     */
    public PAGE_TYPE isSignUpPage() {
        if (!isElementPresent(getBy(loginPageLocators.get("SignupButton")))) {
            logger.warn("SignUp button option not displayed.");
            return PAGE_TYPE.SIGNUP_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(loginPageLocators.get("LoginEmailMobileNumber")))) {
            logger.warn("Email/Mobile field not displayed.");
            return PAGE_TYPE.SIGNUP_PAGE_NOT_FOUND;
        }
        if (isElementPresent(getBy(loginPageLocators.get("FacebookButton")))) {
            logger.warn("Facebook login option displayed.");
            return PAGE_TYPE.SIGNUP_PAGE_NOT_FOUND;
        }
        if (isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.error("Drawer option displayed.");
            return PAGE_TYPE.SIGNUP_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.SIGNUP_PAGE;
    }

    /***
     * Method to check if the current page is a TrackingOrder Page.
     *
     * @return if tracking order page is displayed or not
     */
    public PAGE_TYPE isTrackingOrderPage() {
        if (!isElementPresent(getBy(trackOrderPageLocators.get("TrackingOrderButton")))) {
            logger.warn("Tracking order button not displayed.");
            return PAGE_TYPE.TRACKINGORDER_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(trackOrderPageLocators.get("TrackingSummaryText")))) {
            logger.warn("Tracking summary text not displayed.");
            return PAGE_TYPE.TRACKINGORDER_PAGE_NOT_FOUND;
        }
        if (isElementPresent(getBy(profilePageLocators.get("EditProfile")))) {
            logger.error("Profile Page displayed.");
            return PAGE_TYPE.TRACKINGORDER_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.TRACKINGORDER_PAGE;
    }

    /***
     * Method to check if the current page is a MyOrders Page.
     *
     * @return if tracking order page is displayed or not
     */
    public PAGE_TYPE isMyOrdersPage() {
        if (!isElementPresent(getBy(trackOrderPageLocators.get("MyOrdersTitle"))) && !isElementPresent(getBy(trackOrderPageLocators.get("EmptyOrderList")))) {
            logger.warn("Orders or Start Shopping title not displayed.");
            return PAGE_TYPE.MY_ORDER_PAGE_NOT_FOUND;
        }
        if (isElementPresent(getBy(trackOrderPageLocators.get("EmptyOrderList")))) {
            logger.info("Moved to MyOrders Page. Seems like some orders have been placed.");
        }

        return PAGE_TYPE.MY_ORDER_PAGE;
    }

    /***
     * Method to check if the current page is a Profile Page.
     *
     * @return if profile page is displayed or not
     */
    public PAGE_TYPE isProfilePage() {
        if (!isElementPresent(getBy(profilePageLocators.get("EditProfile")))) {
            logger.warn("Edit option not displayed.");
            return PAGE_TYPE.PROFILE_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(trackOrderPageLocators.get("OrderViewAllOrders")))) {
            logger.warn("View all orders option not displayed.");
            return PAGE_TYPE.PROFILE_PAGE_NOT_FOUND;
        }
        if (isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.error("Drawer option displayed.");
            return PAGE_TYPE.PROFILE_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.PROFILE_PAGE;
    }

    /***
     * Method to check if the current page is a Wishlist Page.
     *
     * @return if wishlist page is displayed or not
     */
    public PAGE_TYPE isWishListPage() {
//        if (isElementPresent(getBy(wishlistPageLocators.get("WishListCueTip"))))
//            driver.findElement(getBy(wishlistPageLocators.get("WishListCueTip"))).click();
//        if (isElementPresent(getBy(wishlistPageLocators.get("WishListItemCount"))))
        waitForElementPresent(wishlistPageLocators.get("ProductListLayout"), 3);
        if (!isElementPresent(getBy(wishlistPageLocators.get("WishlistClearButton")))) {
            logger.warn("Wishlist clear option not displayed.");
            return PAGE_TYPE.WISHLIST_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(wishlistPageLocators.get("ProductListLayout")))) {
            logger.warn("Product layout not displayed.");
            return PAGE_TYPE.WISHLIST_PAGE_NOT_FOUND;
        }
        if (isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.error("Drawer option displayed.");
            return PAGE_TYPE.WISHLIST_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.WISHLIST_PAGE;
    }

    /***
     * Method to check if the current page is a Cart Page.
     *
     * @return if cart page is displayed or not
     */
    public PAGE_TYPE isCartPage() {
        if (isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.warn("Drawer still visible on the cart page.");
            return PAGE_TYPE.CART_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(cartPageLocators.get("EmptyCartKeepShopping"))) && !isElementPresent(getBy(cartPageLocators.get("CartItemRemoveButton")))) {
            logger.warn("Cart page doesn't have either empty cart button or remove item button.");
            return PAGE_TYPE.CART_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.CART_PAGE;
    }

    /***
     * Method to check if the current page is a Ping Startup Page.
     *
     * @return if cart page is displayed or not
     */
    public PAGE_TYPE isPingStartupPage() {
        if (!isElementPresent(getBy(socialCollabLocators.get("PingViewPager")))) {
            logger.warn("Ping Startup doesn't have a view pager.");
            return PAGE_TYPE.PING_STARTUP_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.PING_STARTUP_PAGE;
    }

    /***
     * Method to check if the current page is a Ping Page.
     *
     * @return if cart page is displayed or not
     */
    public PAGE_TYPE isPingPage() {
        if (isElementPresent(getBy(socialCollabLocators.get("PingViewPager")))) {
            logger.warn("Ping Startup doesn't have a view pager.");
            return PAGE_TYPE.PING_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.PING_PAGE;
    }

    /***
     * Method to check if the current page is a Invite Earn Page.
     *
     * @return if cart page is displayed or not
     */
    public PAGE_TYPE isInviteEarnPage() {
        if (!isElementPresent(getBy(wishlistPageLocators.get("InviteLink"))) && !isElementPresent(getBy(wishlistPageLocators.get("MyEarningLink")))) {
            logger.warn("Invite link not found.");
            return PAGE_TYPE.INVITE_EARN_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.INVITE_EARN_PAGE;
    }

    /***
     * Method to check if the current page is a Help Centre Page.
     *
     * @return if cart page is displayed or not
     */
    public PAGE_TYPE isHelpCentre() {
        if (isElementPresent(getBy(helpCentreLocators.get("SomethingWentWrong")))) {
            logger.warn("Help centre not displayed.");
            return PAGE_TYPE.HELP_CENTRE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(helpCentreLocators.get("HelpCentreEmail"))) && isElementPresent(getBy(helpCentreLocators.get("HelpContactLink")))) {
            logger.warn("Help centre email or contact not displayed.");
            return PAGE_TYPE.HELP_CENTRE_NOT_FOUND;
        }
        return PAGE_TYPE.HELP_CENTRE;
    }

    /***
     * Method to check if the current page is a Legal Page.
     *
     * @return if cart page is displayed or not
     */
    public PAGE_TYPE isLegalPage() {
        if (!isElementPresent(getBy(legalPageLocators.get("InfringementLink")))) {
            logger.warn("Legal page not displayed.");
            return PAGE_TYPE.LEGAL_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.LEGAL_PAGE;
    }

    /***
     * Method to check if the current page is a Notification Page.
     *
     * @return if cart page is displayed or not
     */
    public PAGE_TYPE isNotificationPage() {
//        if (isElementPresent(getBy(notificationPageLocators.get("NotificationCuetip")))) {
//            logger.info("Navigated to Notifications Page!");;
        if (isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.warn("Drawer is visible on notification page.");
            return PAGE_TYPE.NOTIFICATION_PAGE_NOT_FOUND;
        }
        if (!isElementPresent(getBy(notificationPageLocators.get("NotificationPageWithoutLogin"))) && !isElementPresent(getBy(notificationPageLocators.get("NotificationsTitle")))) {
            logger.warn("Login option and title both not present on notification page.");
            return PAGE_TYPE.NOTIFICATION_PAGE_NOT_FOUND;
        }
        return PAGE_TYPE.NOTIFICATION_PAGE;
    }
}