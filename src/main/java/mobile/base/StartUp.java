package mobile.base;

import io.appium.java_client.remote.*;
import mobile.device.AppConstants;
import mobile.driver.CustomAndroidDriver;
import mobile.driver.CustomIOSDriver;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class StartUp {

    protected static CustomAndroidDriver androidDriver;
    protected static CustomIOSDriver iosDriver;

    protected static final Logger logger = Logger.getLogger(StartUp.class);
    private static String platform = System.getProperty("platform");
    private static DesiredCapabilities testCapabilities;


    private static StartUp instance = new StartUp();

    public static StartUp getInstance() {
        return instance;
    }

    @BeforeSuite
    public void setUp() {
        PropertyConfigurator.configure(StartUp.class.getClassLoader().getResource("log4j.properties"));
        try {
            if (platform.equalsIgnoreCase(MobilePlatform.IOS)) {
                testCapabilities = getIOSCapabilities();
                iosDriver = new CustomIOSDriver(new URL(SysConstants.SetUpConstants.HUB_URL), testCapabilities);
            } else {
                testCapabilities = getAndroidCapabilities();
                androidDriver = new CustomAndroidDriver(new URL(SysConstants.SetUpConstants.HUB_URL), testCapabilities);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void tearDown() {
        if (platform.equalsIgnoreCase(MobilePlatform.ANDROID)) {
            androidDriver.removeApp(AppConstants.FlipkartApplication.AndroidConstants.PACKAGE_NAME);
        }
        androidDriver.quit();
        iosDriver.quit();
    }


    /***
     * Defines the desired capabilities for Android device
     *
     * @return capabilities defined for Android
     */
    public DesiredCapabilities getAndroidCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        logger.debug("--Setting capabilities for Android--");
        File app = new File(System.getProperty("user.dir") + "/app/",
                AppConstants.FlipkartApplication.AndroidConstants.APK_NAME);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                AutomationName.APPIUM); //4.2 and above versions of Android
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
                SysConstants.DeviceConstants.ANDROID_DEVICE_NAME); //TODO  : to be set to a device which is available in the pool using API developed
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                AppConstants.FlipkartApplication.AndroidConstants.PACKAGE_NAME);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                AppConstants.FlipkartApplication.AndroidConstants.START_ACTIVITY);
        logger.debug("Capabilities for Android Set!");
        return capabilities;
    }

    /***
     * Defines the desired capabilities for IOS device
     *
     * @return capabilities defined for IOS
     */
    public DesiredCapabilities getIOSCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        logger.debug("--Setting capabilities for IOS--");
        File app = new File(System.getProperty("user.dir") + "/app/",
                AppConstants.FlipkartApplication.IOSConstants.IPA_NAME);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                AppConstants.FlipkartApplication.IOSConstants.IOS_PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, Boolean.TRUE);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, SysConstants.DeviceConstants.IOS_DEVICE_NAME);
        capabilities.setCapability(IOSMobileCapabilityType.APP_NAME, app);
        logger.debug("Capabilities for IOS Set!");
        return capabilities;
    }
}