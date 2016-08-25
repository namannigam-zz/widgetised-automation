package page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import mobile.driver.CustomAndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Set;

public class AppiumBasePage extends BasePage {

    protected AppiumDriver driver;

    public AppiumBasePage(WebDriver driver) {
        this.driver = (CustomAndroidDriver) driver;
    }

    /***
     * Utility method to get the height of the device screen
     *
     * @return height of the screen
     */
    public int screenHeight() {
        Dimension dimensions = driver.manage().window().getSize();
        return dimensions.getHeight();
    }

    /***
     * Utility method to get the width of the device screen
     *
     * @return width of the screen
     */
    public int screenWidth() {
        Dimension dimensions = driver.manage().window().getSize();
        return dimensions.getWidth();
    }

    /**
     * Utility method to swipe vertically on the screen using
     * Coordinates x,y base being top-left hand corner of element
     *
     * @param element     on the screen to swipe along its height
     * @param topToBottom being a boolean flag to point the direction of swipe
     */
    public void swipeVertically(WebElement element, boolean topToBottom) {
        Point elementLocation = element.getLocation();
        Dimension elementDimension = element.getSize();
        int source_width = elementLocation.getX() + elementDimension.getHeight() / 2;
        int source_height = elementLocation.getY() + elementDimension.getWidth() / 2;
        int destination_width = source_width;
        int destination_height = source_height;
        if (topToBottom)
            destination_height -= elementDimension.getHeight();
        else
            destination_height += elementDimension.getHeight();
        driver.swipe(source_width, source_height, destination_width, destination_height, 0);
    }


    /**
     * Utility method to swipe vertically on the screen using
     * Coordinates x,y base being top-left hand corner of element
     *
     * @param element     on the screen to swipe along its width
     * @param leftToRight being a boolean flag to point the direction of swipe
     */
    public void swipeHorizontally(WebElement element, boolean leftToRight) {
        Point elementLocation = element.getLocation();
        Dimension elementDimension = element.getSize();
        int source_width = elementLocation.getX() + elementDimension.getHeight() / 2;
        int source_height = elementLocation.getY() + elementDimension.getWidth() / 2;
        int destination_width = source_width;
        int destination_height = source_height;
        if (leftToRight)
            destination_width += elementDimension.getWidth();
        else
            destination_width -= elementDimension.getWidth();
        driver.swipe(source_width, source_height, destination_width, destination_height, 0);
    }

    /***
     * Utility method to swipe up on the device
     *
     * @param atWidth specifying the ratio at which swipe is to be performed
     */
    public void swipeUp(int atWidth) {
        driver.swipe(screenWidth() / atWidth, (int) Math.round(screenHeight() * 0.6), Math.round(screenWidth() / atWidth), (int) Math.round(screenHeight() * 0.4), 0);
    }

    /***
     * Utility method to swipe down on the device
     *
     */
    public void swipeDown() {
        driver.swipe(screenWidth() / 2, (int) Math.round(screenHeight() * 0.4), Math.round(screenWidth() / 2), (int) Math.round(screenHeight() * 0.6), 0);
    }

    /***
     * Utility method to swipe left on the device
     *
     * @param atHeight specifying the ratio at which swipe is to be performed
     */
    public void swipeLeft(int atHeight) {//atHeight is the height where you want to swipe as a ratio to complete screen
        driver.swipe((int) (screenWidth() * 0.8), Math.round(screenHeight() / atHeight), (int) Math.round(screenWidth() * 0.2), Math.round(screenHeight() / atHeight), 0);
    }

    /***
     * Utility method to swipe right on the device
     *
     * @param atHeight specifying the ratio at which swipe is to be performed
     */
    public void swipeRight(int atHeight) {//atHeight is the height where you want to swipe as a ratio to complete scree
        driver.swipe((int) Math.round(screenWidth() * 0.3), Math.round(screenHeight() / atHeight), (int) Math.round(screenWidth() * 0.7), Math.round(screenHeight() / atHeight), 0);
    }

    /***
     * Utility method to drag from screen position to another
     *
     * @param element1 providing source coordinates
     * @param element2 providing the destination coordinates
     */
    public void drag(WebElement element1, WebElement element2) {
        TouchAction dragNDrop = new TouchAction(driver).longPress(element1).moveTo(element2).release();
        dragNDrop.perform();
    }

    /***
     * Verifies the existence of an element on screen
     *
     * @param by specification
     * @return presence of element
     */
    public boolean isElementPresent(By by) {
        try {
            List allElements = driver.findElements(by);
            return !((allElements == null) || (allElements.size() == 0));
        } catch (java.util.NoSuchElementException e) {
            return false;
        }
    }

    /***
     * Verifies the absence of an element from the screen
     *
     * @param by specification of element
     * @return absence of element
     */
    public boolean isElementNotPresent(By by) {
        try {
            List allElements = driver.findElements(by);
            return (allElements == null) || (allElements.size() == 0);
        } catch (java.util.NoSuchElementException e) {
            return true;
        }
    }

    /***
     * Finds the element on screen specified using
     *
     * @param locator             mapping for element
     * @param timeToWaitInSeconds overall time to find the element
     */
    public void waitForElementPresent(final String locator, int timeToWaitInSeconds) {
        new WebDriverWait(driver, timeToWaitInSeconds)
                .until(new ExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver d) {
                        return driver.findElement(getBy(locator));
                    }
                });
    }

    /***
     * Switches the application context to WEBVIEW_packge
     */
    public void switchContextToWebview() {
        try {
            logger.info("Changing the context to Webview...");
            @SuppressWarnings("unchecked") Set<String> contextNames = driver.getContextHandles();
            String setContext = contextNames.toArray()[1].toString();
            driver.context(setContext);
		} catch (Exception contextException) {
            logger.info("Caught You!" + contextException.getMessage());
        }
    }

    /***
     * Switches the application context to NATIVE
     */
    public void switchContextToNative() {
        try {
            logger.info("Changing the context to Native...");
            @SuppressWarnings("unchecked") Set<String> contextNames = driver.getContextHandles();
            String setContext = contextNames.toArray()[0].toString();
            driver.context(setContext);// set context to NATIVE_APP
        } catch (Exception contextException) {
            logger.info("Caught You!" + contextException.getMessage());
        }
    }

    /***
     * Ensures wait for progress bar to disappear while loading
     *
     * @throws InterruptedException
     */
    public void waitUntilProgressBarIsDisplayed() throws InterruptedException {
        int count = 0;
        while ((isElementPresent(getBy(loginPageLocators.get("ProgressBarIcon"))) || isElementPresent(getBy(loginPageLocators.get("ProgressBarMessage")))) && count < 5) {
            logger.info("Waiting for the progress bar to disappear...");
            Thread.sleep(2000);
            count++;
        }
    }

    /***
     * Ensures wait for splash screen to disappear
     *
     * @throws InterruptedException
     */
    public void waitUntilSplashScreenIsDisplayed() throws InterruptedException {
        int count = 0;
        while (isElementPresent(getBy(loginPageLocators.get("SplashScreen"))) && count < 5) {
            logger.info("Waiting for the splash screen to disappear...");
            Thread.sleep(2000);
            count++;
        }
    }
}