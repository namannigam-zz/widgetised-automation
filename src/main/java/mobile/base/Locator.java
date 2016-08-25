package mobile.base;

import org.openqa.selenium.By;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Locator {

    private static Map<String, String> paths = new HashMap<>();
    private static Map<String, Map<String, String>> locatorsMap = new HashMap<>();
    public String key;
    public String value;

    private static String platform = System.getProperty("platform"); //'Android' or 'iOS' for local

    public Locator(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /***
     * Maps the locators from the properties file in the resources
     *
     * @return the directory of locators
     */
    public static String getLocatorDirectory() {
        String srcDir = System.getProperty("user.dir") + File.separator;
        return srcDir + "src/" + "main/" + "resources/" + "locators/" + platform + "/";
    }

    /**
     * Seeks for the locators of a page eg HomePage
     *
     * @param pageName of the page to map the locators defined
     * @return map of page name and its locators
     */
    public static Map<String, String> fetchLocators(String pageName) {
        Map<String, String> locators = locatorsMap.get(pageName);
        paths.put(pageName, getLocatorDirectory() + pageName + ".properties");
        if (locators != null)
            return locators;
        locators = new HashMap<>();
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(paths.get(pageName));
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration enumeration = properties.keys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement().toString();
            locators.put(key, properties.getProperty(key));
        }
        locatorsMap.put(pageName, locators);
        return locators;
    }

    /***
     * Defines the locator strategy of accessing the elements
     *
     * @param locator defined in the properties file
     * @return the By method of accessing the elements
     */
    public static By getBy(String locator) {
        By by;
        if (locator.startsWith("//"))
            by = By.xpath(locator);
        else if (locator.startsWith("xpath="))
            by = By.xpath(locator.replace("xpath=", ""));
        else if (locator.startsWith("class="))
            by = By.className(locator.replace("class=", ""));
        else if (locator.startsWith("css="))
            by = By.cssSelector(locator.replace("css=", "").trim());
        else if (locator.startsWith("link="))
            by = By.linkText(locator.replace("link=", ""));
        else if (locator.startsWith("name="))
            by = By.name(locator.replace("name=", "").trim());
        else if (locator.startsWith("tag="))
            by = By.tagName(locator.replace("tag=", "").trim());
        else if (locator.startsWith("partialText="))
            by = By.partialLinkText(locator.replace("partialText=", ""));
        else if (locator.startsWith("id="))
            by = By.id(locator.replace("id=", ""));
        else if (locator.contains("(//"))
            by = By.xpath(locator);
        else
            by = By.id(locator);
        return by;
    }

    public static By getBy(String locator, String replace) {
        return getBy(locator.replace("~", replace));
    }
}