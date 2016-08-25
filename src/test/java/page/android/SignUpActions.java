package page.android;

import page.AppiumBasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by naman.nigam on 17/02/16
 */
public class SignUpActions extends AppiumBasePage {

    public SignUpActions(WebDriver driver) {
        super(driver);
    }

    boolean dgStatus = false;

    public enum SIGNUP_STATUS {
        SIGNUP_SUCCESS, SIGNUP_FAILED, SIGNUP_USER_ALREADY_EXISTS
    }

    /***
     * Tries signing up using registered mobile number and password when the app is launched for the first time.
     *
     * @param email    user e-mail
     * @param password corresponding password
     * @return the status of sign up
     * @throws InterruptedException
     */
    public SIGNUP_STATUS signup(String email, String password) throws InterruptedException {
        if (isElementPresent(getBy(loginPageLocators.get("FacebookButton"))) && isElementPresent(getBy(loginPageLocators.get("GoogleButton")))) {
            driver.findElement(getBy(loginPageLocators.get("SignupButton"))).click();
            logger.info("Moving from Login to SignUp page...");
        }
        waitForElementPresent(loginPageLocators.get("LoginEmailMobileNumber"), 2);
        By loginEmailEditText = getBy(loginPageLocators.get("LoginEmailMobileNumber"));
        driver.findElement(loginEmailEditText).clear();
        driver.findElement(loginEmailEditText).sendKeys(email);

        logger.info("Entered the already existing account details to sign up.");
        driver.findElement(getBy(loginPageLocators.get("SignupButton"))).click();
        logger.info("Clicked Signup Button.");
        if (!isElementPresent(getBy(loginPageLocators.get("SignupErrorNotification")))) {
            logger.warn("Signup Error notification was not seen.");
            return SIGNUP_STATUS.SIGNUP_SUCCESS;
        } else {
            logger.info("Signup Error notification was seen.");
            return SIGNUP_STATUS.SIGNUP_USER_ALREADY_EXISTS;
        }
    }
}