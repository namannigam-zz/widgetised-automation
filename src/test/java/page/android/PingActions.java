package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;

public class PingActions extends AppiumBasePage {

    public PingActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Removes the welcome message from ping on the bottom of the screen.
     */
    public void removeWelcomeMsg() {
        waitForElementPresent(socialCollabLocators.get("WelcomeMessageLayout"), 3);
        if (isElementPresent(getBy(socialCollabLocators.get("WelcomeMessageClose")))) {
            driver.findElement(getBy(socialCollabLocators.get("WelcomeMessageClose"))).click();
            logger.info("Skipping the welcome message from Ping...");
        }
    }

    /***
     * Gets started with Ping welcome message and login if required.
     *
     * @param email    email to login
     * @param password password to login
     * @throws InterruptedException
     */
    public boolean getStartedWithPing(String email, String password) throws InterruptedException {
        LoginPageActions loginPageActions = new LoginPageActions(driver);
        waitForElementPresent(socialCollabLocators.get("PingGetStarted"), 2);
        driver.findElement(getBy(socialCollabLocators.get("PingGetStarted"))).click();
        Thread.sleep(2000);
        if (isElementPresent(getBy(loginPageLocators.get("MobileEmailContainer")))) {
            loginPageActions.login(email, password);
        }
        if (isElementPresent(getBy(socialCollabLocators.get("ActivatePingLink"))) && isElementPresent(getBy(socialCollabLocators.get("ActivatePingMessageLine1")))) {
            driver.findElement(getBy(socialCollabLocators.get("ActivatePingLink"))).click();
        }
        while (isElementPresent(getBy(socialCollabLocators.get("PingSyncingContact1"))) && isElementPresent(getBy(socialCollabLocators.get("PingSyncingContact2")))) {
            waitForElementPresent(socialCollabLocators.get("PingActionNewConversation"), 15); //15 seconds to sync contact as of now
        }
        if (isElementPresent(getBy(socialCollabLocators.get("PingChatListContainer"))) && isElementPresent(getBy(socialCollabLocators.get("PingActionSettings")))) {
            logger.info("Got Started with the Ping");
            driver.navigate().back();
            return true;
        } else return false;
    }

    /***
     * Skips the cue-tip for sharing products using ping on browse page
     *
     * @return if the cue tip is skipped or not
     * @throws InterruptedException
     */
    public boolean skipPingCuetip() throws InterruptedException {
        if (!isElementPresent(getBy(socialCollabLocators.get("PingCuetipHand")))) {
            logger.error("Cue-tips not displayed properly at browse/product page after getting started with Ping.");
            return false;
        } else {
            driver.findElement(getBy(socialCollabLocators.get("PingCuetipText"))).click();
            waitForElementPresent(browsePageLocators.get("BrowsePageProductTitle"), 2);
            if (!isElementPresent(getBy(socialCollabLocators.get("PingCuetipHand")))) {
                logger.info("Skipped the cuetip for ping at browse/product page successfully.");
                return true;
            } else return false;
        }
    }
}