package util;

import org.testng.annotations.DataProvider;
import java.util.*;

public class DataProviders {

    @DataProvider(name = "signUpTests")
    public static Object[][] signUpData() {
        HashMap<String, String> signupHashMap = new HashMap();
        signupHashMap.put("existing_user_mobile_number", "9535147379");
        Object[][] signUpDataList = {{userEmailCredential(), signupHashMap}};
        return signUpDataList;
    }

    @DataProvider(name = "loginTests")
    public static Object[][] loginData() {
        HashMap<String, String> loginHashMap = new HashMap();
        loginHashMap.put("mobile_login", "7100400151");
        loginHashMap.put("mobile_password", "tester1234");
        loginHashMap.put("inter_country", "USA");
        loginHashMap.put("inter_number_login", "6622581567");
        loginHashMap.put("inter_number_password", "tester123");
        Object[][] loginDataList = {{userEmailCredential(), loginHashMap}};
        return loginDataList;
    }

    @DataProvider(name = "pingTests")
    public static Object[][] pingData() {
        Object[][] pingDataList = {{userEmailCredential()}};
        return pingDataList;
    }

    @DataProvider(name = "homePageTests")
    public static Object[][] homePageData() {
        Object[][] homePageDataList = {{userEmailCredential()}};
        return homePageDataList;
    }

    @DataProvider(name = "searchTests")
    public static Object[][] searchData() {
        String[] searchStrings = {"shoes", "camera", "watches", "books", "power bank"};
        Object[][] searchDataList = {{searchStrings}};
        return searchDataList;
    }

    @DataProvider(name = "visualSearchTests")
    public static Object[][] visualSearchData() {
        String[] visualSearchStrings = {"visual", "search"};
        Object[][] visualSearchDataList = {{visualSearchStrings}};
        return visualSearchDataList;
    }

    @DataProvider(name = "browsePageTests")
    public static Object[][] browsePageData() {
        String[] browseSearchStrings = {"books", "jeans"};
        Object[][] browseDataList = {{userEmailCredential(), browseSearchStrings}};
        return browseDataList;
    }

    @DataProvider(name = "adsTest")
    public static Object[][] adsData() {
        String[] adsSearchStrings = {"shoes", "watches"};
        Object[][] adsDataList = {{adsSearchStrings}};
        return adsDataList;
    }

    @DataProvider(name = "clpTests")
    public static Object[][] clpData() {
        String[] clpAppliances = {"Televisions"};
        Object[][] clpDataList = {{clpAppliances}};
        return clpDataList;
    }

    @DataProvider(name = "productPageV3Tests")
    public static Object[][] dataForProductPageV3Tests() throws Exception {
        String[] searchStrings = {"shoes", "camera", "watches", "books", "power bank"};
        String[] pincodes = {"560103", "900001"};
        Object[][] productPageDataList = {{userEmailCredential(), searchStrings, pincodes}};
        return productPageDataList;
    }


    @DataProvider(name = "wishlistTests")
    public static Object[][] wishlistData() {
        String[] wishlistData = {"shoes", "watches", "books"};
        Object[][] wishlistDataList = {{userEmailCredential(), wishlistData}};
        return wishlistDataList;
    }

    @DataProvider(name = "cartTests")
    public static Object[][] cartData() {
        String[] cartData = {"mobiles", "shirts", "tv", "books", "shoes"};
        Object[][] cartList = {{userEmailCredential(), cartData}};
        return cartList;
    }

    @DataProvider(name = "checkoutTests")
    public static Object[][] checkoutData() {
        String[] checkoutData = {"mobiles", "shirts", "tv", "books", "shoes"};
        Object[][] checkoutList = {{userEmailCredential(), checkoutData}};
        return checkoutList;
    }

    @DataProvider(name = "myAccountsTests")
    public static Object[][] myAccountsData() {
        Object[][] myAccountsDataList = {{userEmailCredential()}};
        return myAccountsDataList;
    }

    @DataProvider(name = "myOrdersTests")
    public static Object[][] myOrdersData() {
        Object[][] myOrdersDataList = {{userEmailCredential()}};
        return myOrdersDataList;
    }

    @DataProvider(name = "notificationTests")
    public static Object[][] notificationData() {
        Object[][] notificationDataList = {{userEmailCredential()}};
        return notificationDataList;
    }

    /***
     * Method to store the email login credentials of a user to log in
     *
     * @return the map of email credentials
     */
    public static HashMap<String, String> userEmailCredential() {
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("email", "autoflipauto@gmail.com");
        credentials.put("password", "tester1234");
        return credentials;
    }

    /***
     * Method to store the mobile login credentials of a user to log in
     *
     * @return the map of mobile credentials
     */
    public static HashMap<String, String> userMobileCredential() {
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("mobile", "7100400151");
        credentials.put("password", "tester1234");
        return credentials;
    }
}
