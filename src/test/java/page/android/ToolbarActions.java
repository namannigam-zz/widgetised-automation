package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ToolbarActions extends AppiumBasePage {

    boolean dgStatus = false;
    long dgTime = 0;
    PageDefinition pageDefinition = new PageDefinition(driver);

    public ToolbarActions(WebDriver driver) {
        super(driver);
    }

    public enum OverFlowMenuItem {
        LOGIN("Login"),
        WISHLIST("Wishlist"),
        TRACK_ORDER("Track Order"),
        PING("Ping"),
        INVITE_AND_EARN("Invite and Earn"),
        RATE_THE_APP("Rate the app"),
        HELP_CENTRE("Help Centre"),
        LEGAL("Legal"),
        LICENSE("License"),
        MY_ACCOUNT("My Account"),
        MY_ORDER("My Orders");

        private final String name;

        OverFlowMenuItem(String menuItemName) {
            name = menuItemName;
        }

        public boolean equalsName(String otherName) {
            return otherName != null && name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }

    /**
     * Moves after selecting a list option from the 3dot menu
     *
     * @param menuItem the item from the list to select
     */
    public void clickOnOverFlowMenuItem(OverFlowMenuItem menuItem) {
        // Click on overflow menu handle
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("MoreOptions"))).click();
        waitForElementPresent(homePageLocators.get("OverflowMenuOptionList"), 2); //for few devices the overflow menu takes time to show up
        // Select the item
        List<WebElement> moreOptionList = driver.findElements(getBy(homePageLocators.get("OverflowMenuOptionList")));
        for (WebElement aMoreOptionList : moreOptionList) {
            if (aMoreOptionList.getText().equalsIgnoreCase(menuItem.toString())) {
                dgTime = System.currentTimeMillis() / 1000L;
                aMoreOptionList.click();
                break;
            }
        }
    }

    /***
     * Navigates from home page to login page and validate.
     *
     * @return if the login page was displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToLoginPage() throws InterruptedException {
        clickOnOverFlowMenuItem(OverFlowMenuItem.LOGIN);
        logger.info("Moving to login page...");
        Thread.sleep(1500);
        if (pageDefinition.isLoginPage() == PageDefinition.PAGE_TYPE.LOGIN_PAGE) {
            logger.info("Onto the login page.");
            return true;
        } else {
            return false;
        }
    }

    /***
     * Navigates from HomePage to Profile Page and validate.  Precondition : User should be signed in.
     *
     * @return if the profile page was displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToProfilePage() throws InterruptedException {
        clickOnOverFlowMenuItem(OverFlowMenuItem.LOGIN);
        logger.info("Moving to the Profile Page...");
        Thread.sleep(1500);
        if (pageDefinition.isProfilePage() == PageDefinition.PAGE_TYPE.PROFILE_PAGE) {
            logger.info("Onto the profile page.");
            return true;
        } else {
            return false;
        }
    }

    /***
     * Navigates from HomePage to Wish-list and validate.
     *
     * @return if the wish-list was displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToWishList() throws InterruptedException {
        clickOnOverFlowMenuItem(OverFlowMenuItem.WISHLIST);
        logger.info("Moving to WishList Page...");
        Thread.sleep(1500);
        if (pageDefinition.isWishListPage() == PageDefinition.PAGE_TYPE.WISHLIST_PAGE) {
            logger.info("Onto the users wish list.");
            return true;
        } else {
            return false;
        }
    }

    /***
     * Navigates from home page to track order page and validate.
     *
     * @return if the track order page was displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToTrackOrdersPage() throws InterruptedException {
        clickOnOverFlowMenuItem(OverFlowMenuItem.TRACK_ORDER);
        logger.info("Moving to TrackOrders Page...");
        Thread.sleep(1500);
        switchContextToWebview();
        if (pageDefinition.isTrackingOrderPage() == PageDefinition.PAGE_TYPE.TRACKINGORDER_PAGE) {
            logger.info("Onto track orders page.");
            return true;
        } else {
            return false;
        }
    }

    /***
     * Navigates from home page to my orders page and validate.
     *
     * @return if the myOrders was displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToMyOrdersPage() throws InterruptedException {
        clickOnOverFlowMenuItem(OverFlowMenuItem.MY_ORDER);
        logger.info("Moving to MyOrders Page...");
        Thread.sleep(1500);
        switchContextToWebview();
        return pageDefinition.isMyOrdersPage() == PageDefinition.PAGE_TYPE.MY_ORDER_PAGE;
    }

    /***
     * Navigates from home page to ping welcome or logged in view and validate.
     *
     * @return if the welcome message ot ping is displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToPing() throws InterruptedException {
        clickOnOverFlowMenuItem(OverFlowMenuItem.PING);
        logger.info("Moving to Ping Startup Page...");
        Thread.sleep(1500);
        // Without Login
        if (pageDefinition.isPingStartupPage() == PageDefinition.PAGE_TYPE.PING_STARTUP_PAGE) {
            logger.info("Onto the Ping Startup.");
            return true;
        }
        //With Login
        else if (pageDefinition.isPingPage() == PageDefinition.PAGE_TYPE.PING_PAGE) {
            logger.info("Onto the Ping Page");
            return true;
        } else {
            return false;
        }
    }

    /***
     * Transits from HomePage to InviteEarn and validate.
     *
     * @return if the invite and earn is displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToInviteEarn() throws InterruptedException {
        clickOnOverFlowMenuItem(OverFlowMenuItem.INVITE_AND_EARN);
        logger.info("Moving to Invite and Earn Page..");
        Thread.sleep(1500);
        if (pageDefinition.isInviteEarnPage() == PageDefinition.PAGE_TYPE.INVITE_EARN_PAGE) {
            logger.info("Onto the Invite and Earn page.");
            return true;
        } else {
            return false;
        }
    }

    /***
     * Navigates from home page to help center and validate.
     *
     * @return if the help centre is displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToHelpCentre() throws InterruptedException {
        clickOnOverFlowMenuItem(OverFlowMenuItem.HELP_CENTRE);
        logger.info("Moving to Help Centre Page..");
        Thread.sleep(1500);
        if (pageDefinition.isHelpCentre() == PageDefinition.PAGE_TYPE.HELP_CENTRE) {
            logger.info("Onto the Help centre page.");
            return true;
        } else {
            return false;
        }
    }

    /***
     * Navigates from home page to legal and validate.
     *
     * @return if the legal page is displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToLegalPage() throws InterruptedException {
        clickOnOverFlowMenuItem(OverFlowMenuItem.HELP_CENTRE);
        logger.info("Moving to Legal Page..");
        Thread.sleep(1500);
        if (pageDefinition.isLegalPage() == PageDefinition.PAGE_TYPE.LEGAL_PAGE) {
            logger.info("Onto the legal page.");
            return true;
        } else {
            return false;
        }
    }

    /***
     * Navigates to Notification Page using toolbar action and validate.
     *
     * @return if the notification page was displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToNotificationPage() throws InterruptedException {
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("NotificationIcon"))).click();
        logger.info("Moving to Notification Page...");
        Thread.sleep(1500);
        if (pageDefinition.isNotificationPage() == PageDefinition.PAGE_TYPE.NOTIFICATION_PAGE) {
            logger.info("Onto the notification page.");
            return true;
        } else {
            return false;
        }
    }

    /***
     * Navigates from home page to cart page using toolbar and validates.
     *
     * @return if the cart page was displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToCartPage() throws InterruptedException {
        driver.findElement(getBy(homePageLocators.get("CartIcon"))).click();
        logger.info("Entering into the cart...");
        Thread.sleep(2000);
        if (pageDefinition.isCartPage() == PageDefinition.PAGE_TYPE.CART_PAGE) {
            logger.info("Onto the cart page.");
            return true;
        } else {
            return false;
        }
    }

    /***
     * Opens the drawer and validates.
     *
     * @return if the drawer was opened or not
     * @throws InterruptedException
     */
    public boolean openDrawer() throws InterruptedException {
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("Drawer"))).click();
        logger.info("Opening the drawer...");
        Thread.sleep(500);
        if (isElementPresent(getBy(homePageLocators.get("FlyoutTitle")))) {
            logger.info("Drawer opened.");
            return true;
        } else {
            return false;
        }
    }
}