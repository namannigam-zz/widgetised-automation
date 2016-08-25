package page.ios;

import page.AppiumBasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.WebElement;

import java.util.HashMap;


public class ProfilePageActions extends AppiumBasePage {

    public ProfilePageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Method to check Profile page is loaded for guest user.
     *
     * @return if the Profile page is loaded for guest user
     * @throws Exception
     */
    public boolean isGuestProfilePageloaded() throws Exception {
        //waitForElementPresent(profilePageLocators.get("Login"), 3);
        if (!isElementPresent(getBy(profilePageLocators.get("Login")))) {
            logger.warn("Login button not loaded in the Profile screen for Guest User.");
            return false;
        }
        if (!isElementPresent(getBy(profilePageLocators.get("TrackOrder")))) {
            logger.warn("TrackOrder button not loaded in the Profile screen for Guest User.");
            return false;
        }
        if (!isElementPresent(getBy(profilePageLocators.get("ContactUs")))) {
            logger.warn("ContactUs button not loaded in the Profile screen for Guest User.");
            return false;
        }
        logger.info("Loaded the Profile screen for Guest User.");
        return true;
    }

    /***
     * Method to check Profile page is loaded for logged in user.
     *
     * @return if the Profile page is loaded for logged in user
     * @throws Exception
     */
    public boolean isLoggedinProfilePageloaded() throws Exception {
        waitForElementPresent(profilePageLocators.get("MyOrders"), 3);
        if (!isElementPresent(getBy(profilePageLocators.get("MyOrders")))) {
            logger.warn("MyOrders button not loaded in the Profile screen for Logged In User.");
            return false;
        }
        if (!isElementPresent(getBy(profilePageLocators.get("ContactUs")))) {
            logger.warn("ContactUs button not loaded in the Profile screen for Logged In User.");
            return false;
        }
        logger.info("Loaded the Profile screen for Logged In User.");
        return true;
    }

    /***
     * Method to click login button.
     *
     * @throws Exception
     */
    public void clickLoginButton() throws Exception {
        WebElement login = driver.findElement(getBy(profilePageLocators.get("Login")));
        login.click();
        logger.info("Clicked login button");
    }

    /***
     * Method to scroll down to an element
     *
     * @param driver     Appium driver
     * @param element  Element to which it has to scroll down
     */
    public void scrollDown(AppiumDriver driver, RemoteWebElement element) {
        JavascriptExecutor js = driver;
        HashMap<String, String> scrollObject = new HashMap<>();
        scrollObject.put("direction", "down");
        scrollObject.put("test/element", element.getId());
        js.executeScript("mobile: scroll", scrollObject);
    }

    /***
     * Method to click logout button.
     *
     * @throws Exception
     */
    public void clickLogoutButton() throws Exception {
        WebElement logout = driver.findElement(getBy(profilePageLocators.get("Logout")));
        logout.click();
        logger.info("Clicked logout button");
    }

    /***
     * Method to logout.
     *
     * @return if the logout was successful or not
     * @throws Exception
     */
    public boolean logout() throws Exception{
        if (!isLoggedinProfilePageloaded()) {
            return false;
        }
        RemoteWebElement ele = (RemoteWebElement)driver.findElement(getBy("Send Feedback"));
        scrollDown(driver, ele);
        clickLogoutButton();
        Thread.sleep(1000);
        if(isGuestProfilePageloaded()) {
            logger.info("Logout Successful");
            return true;
        } else {
            return false;
        }
    }
}