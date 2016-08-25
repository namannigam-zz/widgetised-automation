package mobile.driver;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomIOSDriver extends IOSDriver implements WebElement {
    public CustomIOSDriver(HttpCommandExecutor executor,
                           Capabilities capabilities) {
        super(executor, capabilities);
    }

    public CustomIOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }

    public CustomIOSDriver(URL remoteAddress, HttpClient.Factory httpClientFactory,
                           Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory, desiredCapabilities);
    }

    public CustomIOSDriver(AppiumDriverLocalService service,
                           Capabilities desiredCapabilities) {
        super(service, desiredCapabilities);
    }

    public CustomIOSDriver(AppiumDriverLocalService service,
                           HttpClient.Factory httpClientFactory,
                           Capabilities desiredCapabilities) {
        super(service, httpClientFactory, desiredCapabilities);
    }

    public CustomIOSDriver(AppiumServiceBuilder builder,
                           Capabilities desiredCapabilities) {
        super(builder, desiredCapabilities);
    }

    public CustomIOSDriver(AppiumServiceBuilder builder,
                           HttpClient.Factory httpClientFactory,
                           Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, desiredCapabilities);
    }

    public CustomIOSDriver(HttpClient.Factory httpClientFactory,
                           Capabilities desiredCapabilities) {
        super(httpClientFactory, desiredCapabilities);
    }

    public CustomIOSDriver(Capabilities desiredCapabilities) {
        super(desiredCapabilities);
    }

    @Override
    public void click() {

    }

    @Override
    public void submit() {

    }

    @Override
    public void sendKeys(CharSequence... charSequences) {

    }

    @Override
    public void clear() {

    }

    @Override
    public String getTagName() {
        return null;
    }

    @Override
    public String getAttribute(String s) {
        return null;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }

    @Override
    public Point getLocation() {
        return null;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return null;
    }

    @Override
    public String getCssValue(String s) {
        return null;
    }


    /**
     *
     * @param driver
     * @throws IOException
     */
    public void getScreenshot(WebDriver driver) throws IOException {
        File srcFiler = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDirectory = System.getProperty("user.dir");
        File screenshot_directory = new File(currentDirectory + "/logs/screenshots");
        if (!screenshot_directory.exists()) {
            if (screenshot_directory.mkdirs())
                System.out.println("Created the screenshots directory under logs.");
            else
                System.out.println("Couldn't create a directory.");
        }
        Date date = new Date();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        FileUtils.copyFile(srcFiler, new File(currentDirectory + "/logs/screenshots/" + timestamp + ".png"));
    }
}