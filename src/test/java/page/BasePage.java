package page;

import mobile.base.Locator;
import mobile.util.Cart;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import java.util.Map;

public class BasePage {
    protected static final Logger logger = Logger.getLogger(BasePage.class);
    public static Cart cart = new Cart();
    public static Map<String, String> advertisementLocators, browsePageLocators, cartPageLocators, checkOutPageLocators,
            FOZLocators, helpCentreLocators,
            homePageLocators, legalPageLocators, loginPageLocators, notificationPageLocators,
            orderConfirmationPageLocators, productPageLocators,
            profilePageLocators, searchPageLocators, signupPageLocators, socialCollabLocators, trackOrderPageLocators,
            wishlistPageLocators, dgEvents;

    public BasePage() {
        initPageLocators();
    }

    /***
     * Initializes the page locator
     */
    private static void initPageLocators() {
        advertisementLocators = Locator.fetchLocators("Advertisement");
        browsePageLocators = Locator.fetchLocators("BrowsePage");
        cartPageLocators = Locator.fetchLocators("CartPage");
        checkOutPageLocators = Locator.fetchLocators("CheckoutPage");
        FOZLocators = Locator.fetchLocators("FOZ");
        helpCentreLocators = Locator.fetchLocators("HelpCentre");
        homePageLocators = Locator.fetchLocators("HomePage");
        legalPageLocators = Locator.fetchLocators("LegalPage");
        loginPageLocators = Locator.fetchLocators("LoginPage");
        notificationPageLocators = Locator.fetchLocators("NotificationPage");
        orderConfirmationPageLocators = Locator.fetchLocators("OrderConfirmationPage");
        productPageLocators = Locator.fetchLocators("ProductPage");
        profilePageLocators = Locator.fetchLocators("ProfilePage");
        searchPageLocators = Locator.fetchLocators("SearchPage");
        signupPageLocators = Locator.fetchLocators("SignupPage");
        socialCollabLocators = Locator.fetchLocators("SocialCollab");
        trackOrderPageLocators = Locator.fetchLocators("TrackOrderPage");
        wishlistPageLocators = Locator.fetchLocators("WishlistPage");
        dgEvents = Locator.fetchLocators("DgEvents");
    }

    /***
     * GetBy method to access objects
     *
     * @param locator to access the element
     * @return the By method of accessing the element
     */
    public By getBy(String locator) {
        return Locator.getBy(locator);
    }
}