package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class NotificationPageActions extends AppiumBasePage {

    public NotificationPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Skips the cue-tip overlay on notification page.
     *
     * @throws InterruptedException
     */
    public void skipNotificationPageOverlay() throws InterruptedException {
        while (isElementPresent(getBy(notificationPageLocators.get("NotificationCuetip")))) {
            driver.findElement(getBy(notificationPageLocators.get("NotificationCuetip"))).click();
        }
    }

    /***
     * Gets the count of notifications at NotificationPage
     *
     * @return the count of notifications on notification page
     * @throws InterruptedException
     */
    public int getNotificationCountOnNotificationPage() throws InterruptedException {
        //TODO : Make API call to get the count of unread/read notifications
        Thread.sleep(2000);
        List<WebElement> unsharableNotificaitons = driver.findElements(getBy(notificationPageLocators.get("NotificationInsidePage")));
        List<WebElement> sharableNotificaitons = driver.findElements(getBy(notificationPageLocators.get("NotificationWithShareInsidePage")));
        return (unsharableNotificaitons.size() + sharableNotificaitons.size());
    }

    /***
     * Logs the user in from notification page if the account is not signed in and validate the login.
     *
     * @param email    email to login
     * @param password password to login
     * @return if the login from notification page was successful or not
     * @throws InterruptedException
     */
    public boolean loginFromNotificationPage(String email, String password) throws InterruptedException {
        if (isElementPresent(getBy(notificationPageLocators.get("NotificationShopNow"))) ||
                isElementPresent(getBy(notificationPageLocators.get("NotificationsTitle"))) ||
                isElementPresent(getBy(notificationPageLocators.get("NotificationCuetip")))) {
            logger.info("User already logged in!.");
            return true;
        }
        if (isElementPresent(getBy(notificationPageLocators.get("NotificationPageWithoutLogin")))) {
            driver.findElement(getBy(notificationPageLocators.get("NotificationPageWithoutLogin"))).click();
            logger.info("Logging in from the Notification page...");
            LoginPageActions lp = new LoginPageActions(driver);
            lp.login(email, password);
            if (isElementPresent(getBy(notificationPageLocators.get("NotificationCuetip")))) {
                skipNotificationPageOverlay();
                logger.info("Logged in successfully from the Notification Page!");
                return true;
            } else if (isElementPresent(getBy(notificationPageLocators.get("NotificationsTitle")))) {
                logger.info("Logged in successfully from the Notification Page!");
                return true;
            } else if (isElementPresent(getBy(notificationPageLocators.get("NotificationShopNow")))) {
                logger.info("There are no notifications on the notification page");
                return true;
            } else return false;
        } else {
            logger.warn("No Login option on the Notification Page.");
            return false;
        }
    }

    /***
     * Shares a notification from the listed notifications. Precondition : Shareable notification on the list.
     *
     * @return if the share options were displayed or not
     * @throws InterruptedException
     */
    public boolean shareFromNotificationPage() throws InterruptedException {
        if (isElementPresent(getBy(notificationPageLocators.get("NotificationCuetip"))))
            skipNotificationPageOverlay();
        Thread.sleep(1000);
        if (isElementPresent(getBy(notificationPageLocators.get("NotificationWithShareInsidePage")))) {
            waitForElementPresent(notificationPageLocators.get("NotificationShare"), 3);
            driver.findElement(getBy(notificationPageLocators.get("NotificationShare"))).click();
            logger.info("Sharing a notification from the page..");
            if (isElementPresent(getBy(notificationPageLocators.get("ShareOptionsNotification")))) {
                driver.navigate().back();
                logger.info("Moved to the share option successfully.");
                return true;
            } else return false;
        } else {
            logger.info("There are no sharable notifications on the page.");
            return true;
        }
    }

    /***
     * Deletes a notification from the list dragging the notification on either side.
     *
     * @return if the list of notifications is empty or not
     * @throws InterruptedException
     */
    public boolean deleteNotification() throws InterruptedException {
        Thread.sleep(2000);
        if (isElementPresent(getBy(notificationPageLocators.get("NotificationCuetip"))))
            skipNotificationPageOverlay();
        Thread.sleep(1000);
        if (!isElementPresent(getBy(notificationPageLocators.get("NotificationShopNow")))) {
            WebElement one = driver.findElement(getBy(notificationPageLocators.get("NotificationMessageImage")));
            WebElement two = driver.findElement(getBy(notificationPageLocators.get("NotificationTime")));
            drag(one, two);
        }
        if (isElementPresent(getBy(notificationPageLocators.get("NotificationShopNow")))) {
            logger.info("All the notifications from the notification page are gone.");
            return true;
        } // TODO : Validate the Undo option finding the element
        return true;
    }
}