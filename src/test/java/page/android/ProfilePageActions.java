package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;

public class ProfilePageActions extends AppiumBasePage {
    public ProfilePageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Displays the view for all the orders made previously using the account.
     *
     * @return if the orders are listed or not
     * @throws InterruptedException
     */
    public boolean viewAllOrder() throws InterruptedException {
        waitForElementPresent(profilePageLocators.get("OrderViewAllOrders"), 3);
        driver.findElement(getBy(profilePageLocators.get("OrderViewAllOrders"))).click();
        waitUntilProgressBarIsDisplayed();
        if (isElementPresent(getBy(profilePageLocators.get("OrdersContentDisplayed")))) {
            logger.info("Orders history opened.");
            return true;
        } else return false;
    }

    /***
     * Views the addresses linked to the profile of the user.
     *
     * @param isAddressSaved flag if the addresses are already saved or not
     * @return if the addresses are listed or not
     * @throws InterruptedException
     */
    public boolean viewAddresses(boolean isAddressSaved) throws InterruptedException {
        if (isAddressSaved) {
            if (isElementPresent(getBy(profilePageLocators.get("AddressViewMore"))))
                driver.findElement(getBy(profilePageLocators.get("AddressViewMore"))).click();
            else if (isElementPresent(getBy(profilePageLocators.get("AddressView1more"))))
                driver.findElement(getBy(profilePageLocators.get("AddressView1more"))).click();
            waitUntilProgressBarIsDisplayed();
            Thread.sleep(2000);
            if (isElementPresent(getBy(profilePageLocators.get("AddressAddAddress")))) {
                logger.info("Orders history opened.");
                return true;
            } else return false;
        } else {
            logger.info("Address is not yet saved with the current user profile.");
            return true;
        }
    }

    /***
     * Cancels and adds addresses to a user profile.
     *
     * @return if the addresses are added or not
     * @throws InterruptedException
     */
    public boolean addAddress() throws InterruptedException {
        waitForElementPresent(profilePageLocators.get("AddressAddAddress"), 3);
        driver.findElement(getBy(profilePageLocators.get("AddressAddAddress"))).click();
        waitUntilProgressBarIsDisplayed();
        waitForElementPresent(profilePageLocators.get("AddressFirstName"), 3);
        driver.findElement(getBy(profilePageLocators.get("AddressAddCancel"))).click();
        Thread.sleep(2000);
        if (!isElementPresent(getBy(profilePageLocators.get("AddressAddAddress"))))
            return false;
        waitForElementPresent(profilePageLocators.get("AddressAddAddress"), 3);
        driver.findElement(getBy(profilePageLocators.get("AddressAddAddress"))).click();
        waitUntilProgressBarIsDisplayed();
        waitForElementPresent(profilePageLocators.get("AddressFirstName"), 3);
        driver.findElement(getBy(profilePageLocators.get("AddressFirstName"))).click();
        driver.findElement(getBy(profilePageLocators.get("AddressFirstName"))).sendKeys("Automation");
        Thread.sleep(2000);
        driver.findElement(getBy(profilePageLocators.get("AdresssLastName"))).click();
        driver.findElement(getBy(profilePageLocators.get("AdresssLastName"))).sendKeys("Work");
        Thread.sleep(2000);
        driver.findElement(getBy(profilePageLocators.get("AddressPinCode"))).click();
        driver.findElement(getBy(profilePageLocators.get("AddressPinCode"))).sendKeys("560037");
        Thread.sleep(2000);
        driver.findElement(getBy(profilePageLocators.get("AddressDetails"))).click();
        driver.findElement(getBy(profilePageLocators.get("AddressDetails"))).sendKeys("PW Office,Outer Ring Road");
        Thread.sleep(2000);
        driver.findElement(getBy(profilePageLocators.get("AddressLandmark"))).click();
        driver.findElement(getBy(profilePageLocators.get("AddressLandmark"))).sendKeys("Cessna Business Tech Park");
        Thread.sleep(2000);
        driver.findElement(getBy(profilePageLocators.get("AddressPhoneNumber"))).click();
        driver.findElement(getBy(profilePageLocators.get("AddressPhoneNumber"))).sendKeys("7100400150");
        Thread.sleep(2000);
        waitForElementPresent(profilePageLocators.get("AddressAddSave"), 3);
        driver.findElement(getBy(profilePageLocators.get("AddressAddSave"))).click();
        Thread.sleep(2000);
        waitUntilProgressBarIsDisplayed();
        if (isElementPresent(getBy(profilePageLocators.get("AddressAddAddress")))) {
            logger.info("Added an address to the list.");
            return true;
        } else return false;
    }

    /***
     * Views the subscriptions details from the profile page of a user.
     *
     * @param isSubscriptionProvided flag for subscriptions
     * @return if the subscription details are displayed or not
     * @throws InterruptedException
     */
    public boolean viewSubscriptions(boolean isSubscriptionProvided) throws InterruptedException {
        if (isSubscriptionProvided) {
            waitForElementPresent(profilePageLocators.get("SubscriptionsViewDetails"), 3);
            driver.findElement(getBy(profilePageLocators.get("SubscriptionsViewDetails"))).click();
            waitUntilProgressBarIsDisplayed();
            if (isElementPresent(getBy(profilePageLocators.get("SubscriptionTitle"))) ||
                    isElementPresent(getBy(profilePageLocators.get("SubscriptionContinue"))) ||
                    isElementPresent(getBy(profilePageLocators.get("SubscriptionSignedInText")))) {
                logger.info("Subscriptions details opened.");
                return true;
            } else return false;
        } else {
            logger.info("Subscriptions not availed by the user as of now.");
            return true;
        }
    }

    /***
     * Looks into the wallet details of the user.
     *
     * @param isWalletAttached
     * @return
     * @throws InterruptedException
     */
    public boolean viewWalletDetails(boolean isWalletAttached) throws InterruptedException {
        if (isWalletAttached) {
            waitForElementPresent(profilePageLocators.get("WalletDetails"), 3);
            driver.findElement(getBy(profilePageLocators.get("WalletDetails"))).click();

            if ((isElementPresent(getBy(profilePageLocators.get("WalletCards"))) || isElementPresent(getBy(profilePageLocators.get("WalletBalance")))
                    || isElementPresent(getBy(profilePageLocators.get("WalletSavedCarts"))) || isElementPresent(getBy(profilePageLocators.get("WalletContinueShopping"))))) {
                logger.info("Subscriptions details opened.");
                return true;
            } else return false;
        } else {
            logger.info("Wallet is not yet saved with the current user profile.");
            return true;
        }
    }

    /***
     * Accesses the account settings from user profile.
     *
     * @return if the settings are accessed or not
     * @throws InterruptedException
     */
    public boolean accountSettings() throws InterruptedException {
        waitForElementPresent(profilePageLocators.get("AccountSettings"), 3);
        driver.findElement(getBy(profilePageLocators.get("AccountSettings"))).click();
        waitUntilProgressBarIsDisplayed();
        if (isElementPresent(getBy(profilePageLocators.get("AccountSubmitButton"))) || isElementPresent(getBy(profilePageLocators.get("AccountChangePassword")))
                || isElementPresent(getBy(profilePageLocators.get("AccountDeactivate")))) {
            logger.info("Successfully displaying the account settings.");
            return true;
        } else return false;
    }

    /***
     * Returns the email id used to log in and verify the same.
     *
     * @param emailUsed email used to login
     * @return if the email is displayed on profile or not
     * @throws InterruptedException
     */
    public boolean verifyEmailUsed(String emailUsed) throws InterruptedException {
        Thread.sleep(2000);
        if (isElementPresent(getBy(profilePageLocators.get(emailUsed)))) {
            logger.info(emailUsed + " is logged in currently");
            return true;
        } else return false;
    }
}