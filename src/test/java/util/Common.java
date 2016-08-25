package util;

import mobile.base.StartUp;
import mobile.base.Locator;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntry;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestResult;

/**
 * Created with IntelliJ IDEA.
 * User: shikha.agrawal
 * Date: 19/06/13
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Common extends StartUp {

    public Common() {
    }

    public By getBy(String locator) {
        return Locator.getBy(locator);
    }

    /**
     * Utility method to close and launch the application and move to the homepage skipping the login
     */
    public void relaunchApplication() {
        LoginPageActions loginPageActions = new LoginPageActions(androidDriver);
        try {
            logger.info("---------------Relaunching Application---------------");
            androidDriver.closeApp();
            Thread.sleep(3000);
            androidDriver.launchApp();
            Thread.sleep(5000);
            loginPageActions.skip();
            logger.info("----------------Relaunch Successful-----------------");
        } catch (InterruptedException iex) {
            System.err.println("Could not reset the application.");
            iex.printStackTrace();
        }
    }

    /**
     * Utility method to reset the application when required
     */
    public void resetApplication() {
        LoginPageActions loginPageActions = new LoginPageActions(driver);
        try {
            logger.info("---------------Resetting Application----------------");
            ((AppiumDriver) driver).resetApp();
            Thread.sleep(2000);
            loginPageActions.skip();
            logger.info("------------------Reset Successful------------------");
        } catch (InterruptedException iex) {
            System.err.println("Could not reset the application.");
            iex.printStackTrace();
        }
    }

    /**
     * Utility method to capture logs for the android device
     */
    public void captureDump() {
        String currentDirectory = System.getProperty("user.dir");
        File dump_directory = new File(currentDirectory + "/logs/results");
        logger.info("Saving device logs...");
        if (!dump_directory.exists()) {
            if (dump_directory.mkdirs())
                logger.debug("Created the dumps directory.");
            else
                logger.debug("Couldn't create a directory.");
        }
        String dumpFileName = systemDateTime();
        logger.info("Dump for failure :\t" + dumpFileName);
        try {
            List<LogEntry> logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
            File logFile = new File(currentDirectory + "/logs/results/logcat" + dumpFileName + ".txt");
            PrintWriter log_file_writer = new PrintWriter(logFile);
            log_file_writer.println(logEntries);
            log_file_writer.flush();
            Thread.sleep(20000);
        } catch (InterruptedException | IOException logException) {
            logException.printStackTrace();
        }
    }

    /***
     * Utility method to capture the screenshot of the android device
     *
     * @param result depicting test results
     */
    public void captureScreenshot(ITestResult result) {
        String testResult = "default";
        if (result.getStatus() == ITestResult.SUCCESS) {
            testResult = "pass";
        } else {
            testResult = "fail";
        }
        File srcFiler = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDirectory = System.getProperty("user.dir");
        File screenshot_directory = new File(currentDirectory + "/logs/results");
        if (!screenshot_directory.exists()) {
            if (screenshot_directory.mkdirs())
                System.out.println("Created the screenshots directory.");
            else
                System.out.println("Couldn't create a directory.");
        }
        String imageFileName = testResult + systemDateTime();
        logger.info("Screenshot for test result :  " + imageFileName);
        try {
            FileUtils.copyFile(srcFiler, new File(screenshot_directory.toString() + File.separator + imageFileName + ".png"));
        } catch (IOException screenshotException) {
            screenshotException.printStackTrace();
        }
    }

    /***
     * Utility method to get the system current date whenever called
     *
     * @return the system current date and time
     */
    public String systemDateTime() {
        Date date = new Date();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        return timestamp;
    }
}