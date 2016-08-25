package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

//TODO : remove Deprecated methods once moved to ToolbarActions.java

public class HomePageActions extends AppiumBasePage {

    long dgTime = 0;

    public HomePageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Moves back to HomePage from any other page on the app.
     *
     * @return if the home page is reached or not
     * @throws InterruptedException
     */
    public boolean navigateBackHome() throws InterruptedException {
        PageDefinition pageDefinition = new PageDefinition(driver);
        if(isElementPresent(getBy(homePageLocators.get("OverflowMenuOptionList")))){
            logger .info("OverflowMenu option was open since previous failure.");
            WebElement element = driver.findElement(By.xpath(""));
            String ch = element.getAttribute("checked");
            if(ch.equalsIgnoreCase("true"))
            driver.navigate().back();
        }
        if (isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.info("Already on the HomePage!");
            return true;
        }
        if (pageDefinition.isLoginPage() == PageDefinition.PAGE_TYPE.LOGIN_PAGE) {
            new LoginPageActions(driver).skip();
        }
        if (pageDefinition.isSignUpPage() == PageDefinition.PAGE_TYPE.SIGNUP_PAGE) {
            new LoginPageActions(driver).skip();
        }
        if (isElementPresent(getBy(homePageLocators.get("logo")))) {
            WebElement logo = driver.findElement(getBy(homePageLocators.get("Logo")));
            logo.click();
            logger.info("Navigated to home page by clicking F Logo !");
            return true;
        } else {
            if (!isElementPresent(getBy(homePageLocators.get("Drawer")))) {
                if (!isElementPresent(getBy(homePageLocators.get("NavigateBack")))) {
                    logger.warn("Couldn't find a way to navigate back to home.");
                    return false;
                }
                waitForElementPresent(homePageLocators.get("NavigateBack"), 2);
                driver.findElement(getBy(homePageLocators.get("NavigateBack"))).click();
            }
            return true;
        }
    }

    /***
     * Logs in using the homepage overflow menu option form home page.
     *
     * @param email    email to login
     * @param password password to login
     * @return if the login was successful or not.
     * @throws InterruptedException
     */
    public boolean loginFromMenu(String email, String password) throws InterruptedException {
        ToolbarActions toolbarActions = new ToolbarActions(driver);
        toolbarActions.navigateToLoginPage();
        LoginPageActions lp = new LoginPageActions(driver);
        if (lp.login(email, password)) {
            logger.info("Successfully logged in from overflow menu options.");
            return true;
        } else return false;
    }

    /***
     * Logs in from homepage overflow menu using the international credentials.
     *
     * @param email       email to login
     * @param password    password for the account
     * @param countryName short country name to be selected
     * @return if the login was successful or not
     * @throws InterruptedException
     */
    public boolean internationalLoginFromMenu(String email, String password, String countryName) throws InterruptedException {
        ToolbarActions toolbarActions = new ToolbarActions(driver);
        toolbarActions.navigateToLoginPage();
        LoginPageActions lp = new LoginPageActions(driver);
        if (lp.internationalLogin(email, password, countryName)) {
            logger.info("Successfully logged in using international number from overflow menu options.");
            return true;
        } else return false;
    }

    /***
     * Logs out the user using the overflow menu option on home page via profile page.
     *
     * @return if the logout was successful or not
     * @throws InterruptedException
     */
    public boolean logoutFromMenu() throws InterruptedException {
        navigateToProfilePage();
        waitForElementPresent(profilePageLocators.get("LogoutLinkProfile"), 3);
        for (int i = 0; i < 7; i++)
            swipeUp(2);
        driver.findElement(getBy(profilePageLocators.get("LogoutLinkProfile"))).click();
        Thread.sleep(3000);
        waitUntilProgressBarIsDisplayed();
        if (isElementPresent(getBy(homePageLocators.get("Drawer"))) && isElementPresent(getBy(homePageLocators.get("NotificationIcon")))
                && isElementPresent(getBy(homePageLocators.get("MoreOptions")))) {
            logger.info("Logged out successfully and returned back to the home page.");
            return true;
        } else return false;
    }

    /***
     * Navigates to the search page and validates the search layout.
     *
     * @return if the search page was displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToSearchPage() throws InterruptedException {
        waitForElementPresent(homePageLocators.get("SearchWidgetTextBox"), 2);
        driver.findElement(getBy(homePageLocators.get("SearchWidgetTextBox"))).click();
        logger.info("Moving to search page...");
        waitUntilProgressBarIsDisplayed();
        if (isElementPresent(getBy(searchPageLocators.get("SearchLayout")))) {
            logger.info("Moved to search page successfully.");
            return true;
        } else return false;
    }

    /***
     * Views promotions on the home page swiping horizontally.
     *
     * @return if the promotions was viewed successfully or not
     * @throws InterruptedException
     */
    public boolean navigateToPromotions() throws InterruptedException {
        // TODO : Get the call for the type of page(foz,clp,etc..) the promotion banner should redirect to.
        for (int i = 0; i < 5; i++) {
            int k = 0;
            int count = 0;
            if (isElementPresent(getBy(homePageLocators.get("BannerIndexed").replace("|i|", "" + k + "")))) {
                logger.info("Opening a fresh Promotion Banner...");
                driver.findElement(getBy(homePageLocators.get("BannerIndexed").replace("|i|", "" + k + ""))).click();
                waitUntilProgressBarIsDisplayed();
                logger.info("Opened the " + (i + 1) + " indexed promotional banner.");
                driver.navigate().back();
                int j = k + 1;
                while (!isElementPresent(getBy(homePageLocators.get("BannerIndexed").replace("|i|", "" + j + ""))) && count < 5) {
                    swipeLeft(4);
                    k++;
                    count++;
                    Thread.sleep(1000);
                }
            } else continue;
        }
        return true;
    }

    /***
     * Selects the Deals of the Day and navigated to FOZ to validate.
     *
     * @return if the deals of the day timer is displayed or not and if deals of the days page is displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToDotd() throws InterruptedException {
        int count = 0;
        while (!isElementPresent(getBy(homePageLocators.get("DealOfTheDayTimer"))) && count < 5) {
            swipeUp(2);
            waitForElementPresent(homePageLocators.get("DealOfTheDayTimer"), 3);
            count++;
        }
        driver.findElement(getBy(homePageLocators.get("DealOfTheDayTimer"))).click();
        logger.info("Verified the DOTD timer and moving to the DOTD Page...");
        waitUntilProgressBarIsDisplayed();
        waitForElementPresent(homePageLocators.get("ProductWidgetTitle"), 2);
        if (!(driver.findElement(getBy(homePageLocators.get("ProductWidgetTitle"))).getText().contains("Deals of the Day"))) {
            logger.warn("Title not displayed properly on the deals of the day page.");
            return false;
        }
        if (isElementPresent(getBy(homePageLocators.get("LtdDealsProductTitle"))) && isElementPresent(getBy(homePageLocators.get("LtdDealsProductImage")))) {
            logger.info("Navigated successfully to the Deals of the Day Page.");
            driver.navigate().back();
            return true;
        } else return false;
    }

    /***
     * Navigates to OfferZone and validate.
     *
     * @return if the offer zone is displayed or not
     * @throws InterruptedException
     */
    public boolean navigateToOfferZone() throws InterruptedException {
        boolean offerZonePresent = false;
        int count = 0;
        while (!offerZonePresent && count < 4) {
            waitForElementPresent(homePageLocators.get("AnnouncementLayout"), 3);
            List<WebElement> announcementList = driver.findElements(getBy(homePageLocators.get("AnnouncementTitle")));
            for (WebElement anAnnouncementList : announcementList) {
                if (anAnnouncementList.getText().equalsIgnoreCase("GO TO OFFER ZONE")) {
                    anAnnouncementList.click();
                    offerZonePresent = true;
                    logger.info("Moving to the Offer Zone...");
                    break;
                }
            }
            swipeUp(2);
            count++;
        }
        if (offerZonePresent) {
            if (isElementPresent(getBy(FOZLocators.get("SubCategoryDealsTitle"))) && isElementPresent(getBy(FOZLocators.get("RecommendationsViewAll")))) {
                logger.info("Moved to the Offers Zone Successfully.");
                driver.navigate().back();
                return true;
            } else return false;
        } else {
            logger.warn("Offer Zone not present at the homepage.");
            return false;
        }
    }

    /***
     * Navigates to Announcements and validate.
     *
     * @return if the announcements are viewed or not
     * @throws InterruptedException
     */
    //TODO : can have a flag if the announcements should be present or not
    public boolean navigateToAnnouncements() throws InterruptedException {
        waitForElementPresent(homePageLocators.get("AnnouncementLayout"), 3);
        List<WebElement> announcementList = driver.findElements(getBy(homePageLocators.get("AnnouncementTitle")));
        for (WebElement anAnnouncementList : announcementList) {
            if (!(anAnnouncementList.getText().equalsIgnoreCase("GO TO OFFER ZONE"))) {
                anAnnouncementList.click();
                logger.info("Moving to the Announcements...");
                break;
            }
        }
        Thread.sleep(3000);
        if (isElementPresent(getBy(homePageLocators.get("AnnouncementsContent"))) && !isElementPresent(getBy(homePageLocators.get("Drawer")))) {
            logger.info("Moved to the Announcements Successfully.");
            driver.navigate().back();
            return true;
        } else {
            logger.info("Announcements are not present on the homepage.");
            return false;
        }
    }

    /***
     * Gets the cart count displayed in the toolbar.
     *
     * @return the count of cart items displayed on home page
     * @throws InterruptedException
     */
    public int getCartCountOnHomePage() throws InterruptedException {
        String cartCount = "0";
        Thread.sleep(2000);
        if (isElementPresent(getBy(homePageLocators.get("CartCountHomepage"))))
            cartCount = driver.findElement(getBy(homePageLocators.get("CartCountHomepage"))).getText();
        if (cartCount.equalsIgnoreCase("")) {
            logger.info("Cart element count displayed on homepage : 0");
            return 0;
        } else {
            logger.info("Cart element count displayed on homepage : " + Integer.parseInt(cartCount));
            return Integer.parseInt(cartCount);
        }
    }

    /***
     * Navigates to specific FOZ category from the HomePage.
     *
     * @param categoryName the category to be viewed
     * @return if the category was reached on FOZ or not
     * @throws InterruptedException
     */
    public boolean navigateToFOZPage(String categoryName) throws InterruptedException {
        FOZPageActions fozPageActions = new FOZPageActions(driver);
        int countCategoryItem = 0;
        waitForElementPresent(homePageLocators.get("AnnouncementLayout"), 3);
        List<WebElement> announcementList = driver.findElements(getBy(homePageLocators.get("AnnouncementTitle")));
        for (WebElement anAnnouncementList : announcementList) {
            if (anAnnouncementList.getText().equalsIgnoreCase("GO TO OFFER ZONE")) {
                anAnnouncementList.click();
                logger.info("Moving to the Offer Zone...");
            }
        }
        int numberOftabs = fozPageActions.numberOfTabsDisplayed();
        logger.info("Number of tabs displayed under FOZ page are : " + numberOftabs);
        while (!(driver.findElement(getBy(FOZLocators.get("HorizontalTabs").replace("|i|", "" + countCategoryItem + ""))).getText().equalsIgnoreCase(categoryName) && countCategoryItem < 10)) {
            if (countCategoryItem % numberOftabs == (numberOftabs - 1))
                swipeLeft(10);
            countCategoryItem++;
        }
        driver.findElement(getBy(FOZLocators.get("HorizontalTabs").replace("|i|", "" + countCategoryItem + ""))).click();
        waitUntilProgressBarIsDisplayed();
        if (isElementPresent(getBy(FOZLocators.get("SubCategoryDealsTitle"))) && isElementPresent(getBy(FOZLocators.get("RecommendationsViewAll")))) {
            logger.info("successfully navigated to the FOZ tab for " + categoryName);
            return true;
        } else return false;
    }

    /**
     * Verifies the PMU products and view all option on the homepage.
     *
     * @return if the PMU and View All option are displayed on home page or not
     * @throws InterruptedException
     */
    public boolean verifyHomePageProducts() throws InterruptedException {
        int count = 0;
        boolean pmuPresence = false;
        waitForElementPresent(homePageLocators.get("Drawer"), 3);
        while (!pmuPresence && count < 5) {
            if (isElementPresent(getBy(homePageLocators.get("LtdProductLayout"))) && isElementPresent(getBy(homePageLocators.get("ViewAllLink")))) {
                pmuPresence = true;
                break;
            }
            swipeUp(2);
            count++;
        }
        if (pmuPresence) {
            logger.info("Verified the presence of PMU and View All option.");
            while (!isElementPresent(getBy(homePageLocators.get("SearchWidgetTextBox"))))
                swipeDown();
            return true;
        } else return false;
    }

    /***
     * Navigates to the CLP and verify the details.
     *
     * @param appliance the product to navigate to CLP for
     * @return if the CLP is displayed appropriately or not
     * @throws InterruptedException
     */
    public boolean navigateToCLP(String appliance) throws InterruptedException {
        boolean foundAppliance = false;
        int count = 0;
        waitForElementPresent(homePageLocators.get("Drawer"), 2);
        driver.findElement(getBy(homePageLocators.get("Drawer"))).click();
        /**
         * commenting out the section as if scroll is needed, the toggle is behind the toolbar actions and notification bell is clicked
         *
        while ((!foundAppliance) && count < 3) {
            List<WebElement> toggle = driver.findElements(getBy(homePageLocators.get("FlyoutToggle")));
            for (int j = 0; j < toggle.size(); j++) {
                toggle.get(j).click();
                if (!isElementPresent(getBy(homePageLocators.get("FlyoutChildContainer")))) {
                    logger.warn("Child container is not displayed after toggling the large appliances flyout option.");
                    return false;
                }
                List<WebElement> flyoutTitles = driver.findElements(getBy(homePageLocators.get("FlyoutTitle")));
                for (int i = 0; i < flyoutTitles.size(); i++) {
                    if (flyoutTitles.get(i).getText().equalsIgnoreCase(appliance)) {
                        flyoutTitles.get(i).click();
                        foundAppliance = true;
                        break;
                    }
                }
                if (foundAppliance) break;
                else toggle.get(j).click();
            }
            if (foundAppliance) break;
            else {
                swipeUp(5);
                count++;
            }
        }*/

        while ((!foundAppliance) && count < 5) {
            List<WebElement> flyoutTitles = driver.findElements(getBy(homePageLocators.get("FlyoutTitle")));
            for (WebElement flyoutTitle : flyoutTitles) {
                if (flyoutTitle.getText().equalsIgnoreCase(appliance)) {
                    flyoutTitle.click();
                    logger.info("Moving to " + appliance + " clp..");
                    foundAppliance = true;
                    break;
                }
            }
            if (foundAppliance) break;
            else {
                swipeUp(5);
                count++;
            }
        }
        if (isElementPresent(getBy(homePageLocators.get("CLPFilterWidget"))) &&
                (isElementPresent(getBy(homePageLocators.get("AnnouncementLayout"))) ||
                isElementPresent(getBy(homePageLocators.get("CLPFilterMoreOptions"))))) {
            logger.info("Successfully verified navigating to the CLP");
            navigateBackHome();
            return true;
        } else {
            navigateBackHome();
            return false;
        }
    }

    /***
     * Navigates from HomePage to Profile Page and validate.
     * - Precondition : User should be signed in.
     *
     * @return if the profile page was displayed or not
     * @throws InterruptedException
     */
    @Deprecated
    public boolean navigateToProfilePage() throws InterruptedException {
        waitForElementPresent(homePageLocators.get("MoreOptions"), 2);
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("MoreOptions"))).click();
        List<WebElement> moreOptionList = driver.findElements(getBy(homePageLocators.get("OverflowMenuOptionList")));
        Thread.sleep(1000);
        dgTime = System.currentTimeMillis() / 1000L;
        moreOptionList.get(0).click();
        logger.info("Moving to the Profile Page...");
        waitUntilProgressBarIsDisplayed();
        Thread.sleep(2000);
        waitForElementPresent(profilePageLocators.get("EditProfile"), 3);
        if (isElementPresent(getBy(profilePageLocators.get("LogoutLinkProfile")))) {
            logger.info("Navigated to ProfilePage!");
            return true;
        } else return false;
    }

    /***
     * Navigates from HomePage to Wish-list and validate.
     *
     * @return if the wish-list was displayed or not
     * @throws InterruptedException
     *
     * Please use  `ToolbarActions toolbarActions = new ToolbarActions(driver); toolbarActions.clickOnOverFlowMenuItem(ToolbarActions.OverFlowMenuItem.WISHLIST);`
     */
    @Deprecated
    public boolean navigateToWishlist() throws InterruptedException {
        Thread.sleep(2000);
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("MoreOptions"))).click();
        waitForElementPresent(homePageLocators.get("OverflowMenuOptionList"),2); //for few devices the overflow menu takes time to show up
        List<WebElement> moreOptionList = driver.findElements(getBy(homePageLocators.get("OverflowMenuOptionList")));
        for (WebElement aMoreOptionList : moreOptionList) {
            if (aMoreOptionList.getText().equalsIgnoreCase("Wishlist")) {
                dgTime = System.currentTimeMillis() / 1000L;
                aMoreOptionList.click();
                break;
            }
        }
        logger.info("Moving to Wishlist Page...");
        Thread.sleep(3000);
        if (isElementPresent(getBy(wishlistPageLocators.get("WishListCueTip"))))
            driver.findElement(getBy(wishlistPageLocators.get("WishListCueTip"))).click();
        waitUntilProgressBarIsDisplayed();
        dgTime = System.currentTimeMillis() / 1000L;
        waitForElementPresent(wishlistPageLocators.get("WishListItemCount"), 3);
        if (isElementPresent(getBy(wishlistPageLocators.get("WishListItemCount")))) {
            logger.info("Onto the users wishlist.");
            return true;
        } else return false;
    }

    /***
     * Navigates from home page to track order page and validate.
     *
     * @return if the track order page was displayed or not
     * @throws InterruptedException
     */
    @Deprecated
    public boolean navigateToTrackOrderPage() throws InterruptedException {
        Thread.sleep(2000);
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("MoreOptions"))).click();
        List<WebElement> moreOptionList = driver.findElements(getBy(homePageLocators.get("OverflowMenuOptionList")));
        for (WebElement aMoreOptionList : moreOptionList) {
            if (aMoreOptionList.getText().equalsIgnoreCase("Track Order")) {
                dgTime = System.currentTimeMillis() / 1000L;
                aMoreOptionList.click();
                break;
            }
        }
        logger.info("Moving to TrackOrder Page...");
        Thread.sleep(2000);
        waitUntilProgressBarIsDisplayed();
        waitForElementPresent(trackOrderPageLocators.get("TrackingOrderTitle"), 3);
        if (isElementPresent(getBy(trackOrderPageLocators.get("TrackingOrderButton")))) {
            logger.info("Moved to TrackOrder Page.");
            return true;
        } else
            return false;
    }

    /***
     * Navigates from home page to my orders page and validate.
     *
     * @return if the myOrders was displayed or not
     * @throws InterruptedException
     */
    @Deprecated
    public boolean navigateToMyOrdersPage() throws InterruptedException {
        Thread.sleep(2000);
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("MoreOptions"))).click();
        List<WebElement> moreOptionList = driver.findElements(getBy(homePageLocators.get("OverflowMenuOptionList")));
        for (WebElement aMoreOptionList : moreOptionList) {
            if (aMoreOptionList.getText().equalsIgnoreCase("My Orders")) {
                dgTime = System.currentTimeMillis() / 1000L;
                aMoreOptionList.click();
                break;
            }
        }
        logger.info("Moving to MyOrders Page...");
        Thread.sleep(2000);
        switchContextToWebview();
        waitForElementPresent(trackOrderPageLocators.get("EmptyOrderList"), 3);
        if (isElementPresent(getBy(trackOrderPageLocators.get("MyOrdersTitle")))) {
            logger.info("Moved to MyOrders Page. Seems like some orders have been placed.");
            return true;
        } else if (isElementPresent(getBy(trackOrderPageLocators.get("EmptyOrderList")))) {
            logger.info("Moved to MyOrders Page. No existing orders in there.");
            return true;
        } else return false;
    }

    /***
     * Navigates from home page to ping welcome or logged in view and validate.
     *
     * @return if the welcome message ot ping is displayed or not
     * @throws InterruptedException
     */
    @Deprecated
    public boolean navigateToPingStartup() throws InterruptedException {
        waitForElementPresent(homePageLocators.get("MoreOptions"), 3);
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("MoreOptions"))).click();
        List<WebElement> moreOptionList = driver.findElements(getBy(homePageLocators.get("OverflowMenuOptionList")));
        for (WebElement aMoreOptionList : moreOptionList) {
            if (aMoreOptionList.getText().equalsIgnoreCase("Ping")) {
                dgTime = System.currentTimeMillis() / 1000L;
                aMoreOptionList.click();
                break;
            }
        }
        logger.info("Moving to Ping Startup Page...");
        Thread.sleep(2000);
        waitUntilProgressBarIsDisplayed();
        waitForElementPresent(socialCollabLocators.get("PingViewPager"), 3);
        if (isElementPresent(getBy(socialCollabLocators.get("PingViewPager")))) {
            logger.info("Moved to Ping Startup Page.");
            return true;
        } else
            return false;
    }

    /***
     * Transits from HomePage to InviteEarn and validate.
     *
     * @return if the invite and earn is displayed or not
     * @throws InterruptedException
     */
    @Deprecated
    public boolean navigateToInviteEarn() throws InterruptedException {
        Thread.sleep(2000);
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("MoreOptions"))).click();
        List<WebElement> moreOptionList = driver.findElements(getBy(homePageLocators.get("OverflowMenuOptionList")));
        for (WebElement aMoreOptionList : moreOptionList) {
            if (aMoreOptionList.getText().equalsIgnoreCase("Invite & Earn")) {
                dgTime = System.currentTimeMillis() / 1000L;
                aMoreOptionList.click();
                break;
            }
        }
        logger.info("Moving to Invite and Earn Page..");
        Thread.sleep(2000);
        waitUntilProgressBarIsDisplayed();
        waitForElementPresent(wishlistPageLocators.get("InviteEarnContinueButton"), 3);
        if (isElementPresent(getBy(wishlistPageLocators.get("InviteLink")))) {
            logger.info("Verified Invite and Earn option successfully.");
            return true;
        } else {
            return isElementPresent(getBy(wishlistPageLocators.get("MyEarningLink")));
        }
    }

    /***
     * Navigates from home page to help center and validate.
     *
     * @return if the help centre is displayed or not
     * @throws InterruptedException
     */
    @Deprecated
    public boolean navigateToHelpCentre() throws InterruptedException {
        Thread.sleep(2000);
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("MoreOptions"))).click();
        List<WebElement> moreOptionList = driver.findElements(getBy(homePageLocators.get("OverflowMenuOptionList")));
        for (WebElement aMoreOptionList : moreOptionList) {
            if (aMoreOptionList.getText().equalsIgnoreCase("Help Centre")) {
                dgTime = System.currentTimeMillis() / 1000L;
                aMoreOptionList.click();
                break;
            }
        }
        logger.info("Moving to Help Centre Page..");
        Thread.sleep(3000);
        waitUntilProgressBarIsDisplayed();
        waitForElementPresent(helpCentreLocators.get("HelpLookByText"), 3);
        if (isElementPresent(getBy(helpCentreLocators.get("SomethingWentWrong"))))
            return false;
        if ((isElementPresent(getBy(helpCentreLocators.get("HelpCentreEmail"))) || isElementPresent(getBy(helpCentreLocators.get("HelpContactLink"))) || isElementPresent(getBy(helpCentreLocators.get("HelpCentreTitle"))))) {
            logger.info("Moved to the Help Center.");
            return true;
        } else return false;
    }

    /***
     * Navigates from home page to legal and validate.
     *
     * @return if the legal page is displayed or not
     * @throws InterruptedException
     */
    @Deprecated
    public boolean navigateToLegalPage() throws InterruptedException {
        Thread.sleep(2000);
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("MoreOptions"))).click();
        List<WebElement> moreOptionList = driver.findElements(getBy(homePageLocators.get("OverflowMenuOptionList")));
        for (WebElement aMoreOptionList : moreOptionList) {
            if (aMoreOptionList.getText().equalsIgnoreCase("Legal")) {
                dgTime = System.currentTimeMillis() / 1000L;
                aMoreOptionList.click();
                break;
            }
        }
        logger.info("Moving to Legal Page..");
        Thread.sleep(2000);
        waitUntilProgressBarIsDisplayed();
        waitForElementPresent(legalPageLocators.get("PoliciesText"), 3);
        if (isElementPresent(getBy(legalPageLocators.get("InfringementLink")))) {
            logger.info("Moved to the legal page.");
            return true;
        } else return false;
    }

    /***
     * Navigates to Notification Page using toolbar action and validate.
     *
     * @return if the notification page was displayed or not
     * @throws InterruptedException
     */
    @Deprecated
    public boolean navigateToNotificationPage() throws InterruptedException {
        Thread.sleep(3000);
        waitForElementPresent(homePageLocators.get("NotificationIcon"), 2);
        dgTime = System.currentTimeMillis() / 1000L;
        driver.findElement(getBy(homePageLocators.get("NotificationIcon"))).click();
        logger.info("Moving to Notification Page...");
        Thread.sleep(3000);
        waitUntilProgressBarIsDisplayed();
        if (isElementPresent(getBy(notificationPageLocators.get("NotificationCuetip")))) {
            logger.info("Navigated to Notifications Page!");
            return true;
        } else if (isElementPresent(getBy(notificationPageLocators.get("NotificationPageWithoutLogin")))) {
            logger.info("Moved to notification page but not logged in.");
            return true;
        } else if (isElementPresent(getBy(notificationPageLocators.get("NotificationsTitle")))) {
            logger.info("Navigated to Notifications Page!");
            return true;
        } else if ((isElementPresent(getBy(notificationPageLocators.get("EmptyNotificationPageBell"))))) {
            logger.info("Logged in. No notifications on the page to test.");
            return true; //Note : The tests ahead of this requiring notifications wouldn't pass
        } else return false;
    }

    /***
     * Navigates from home page to cart page using toolbar and validates.
     *
     * @return if the cart page was displayed or not
     * @throws InterruptedException
     */
    @Deprecated
    public boolean navigateToCartPage() throws InterruptedException {
        Thread.sleep(3000);
        waitForElementPresent(homePageLocators.get("CartIcon"), 2);
        driver.findElement(getBy(homePageLocators.get("CartIcon"))).click();
        logger.info("Entering into the cart...");
        Thread.sleep(3000);
        waitUntilProgressBarIsDisplayed();
        Thread.sleep(3000);
        if (isElementPresent(getBy(cartPageLocators.get("EmptyCartKeepShopping")))) {
            logger.info("Entered into the cart. It is Empty.");
            return true;
        } else if (isElementPresent(getBy(cartPageLocators.get("CartItemRemoveButton")))) {
            logger.info("Entered into the cart. Some Items in there.");
            return true;
        } else return false;
    }

    /***
     * Validates the content displayed in the drawer list fetched from the api call.
     *
     * @param flyOutValue the expected list of categories
     * @return if the displayed categories are similar to api returned or not
     * @throws InterruptedException
     */
    @Deprecated
    public boolean validateExpectedDrawerList(String[] flyOutValue) throws InterruptedException {
        if (!isElementPresent(getBy(homePageLocators.get("ExpandableItemIcon")))) {
            dgTime = System.currentTimeMillis() / 1000L;
            driver.findElement(getBy(homePageLocators.get("Drawer"))).click();
            Thread.sleep(2000);
        }
        List<WebElement> expandableList = driver.findElements(getBy(homePageLocators.get("ExpandableItemCategoryText")));
        int numberOfCategory = expandableList.size();
        logger.info("Count of categories : " + numberOfCategory);
        String[] categoryList = new String[numberOfCategory];
        for (int j = 0; j < numberOfCategory; j++) {
            categoryList[j] = expandableList.get(j).getText();
            if (flyOutValue[j].equalsIgnoreCase(categoryList[j])) {
                logger.info("Verified displaying " + flyOutValue[j] + " in the drawer.");
            } else {
                logger.error("Expected Drawer Element : " + flyOutValue[j] + " not Found in category List.");
                waitForElementPresent(homePageLocators.get("Drawer"), 5);
                driver.findElement(getBy(homePageLocators.get("Drawer"))).click();
                return false;
            }
        }
        if (isElementPresent(getBy(homePageLocators.get("ExpandableItemIcon")))) {
            driver.findElement(getBy(homePageLocators.get("Drawer"))).click();
            Thread.sleep(2000);
        }
        return true;
    }
}