package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class FOZPageActions extends AppiumBasePage {

    public FOZPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Validates the FOZ page using Offers and Horizontal Tabs.
     *
     * @return if the offers in products are present at FOZ or not
     * @throws InterruptedException
     */
    public boolean validateFOZ() throws InterruptedException {
        boolean status = false;
        if (isElementPresent(getBy(FOZLocators.get("HorizontalTabs"))))
            if (isElementPresent(getBy(homePageLocators.get("offerZoneHeader")))) {
                driver.findElement(getBy(homePageLocators.get("offerZoneHeader"))).click();
                Thread.sleep(5000);
                if (isElementPresent(getBy(browsePageLocators.get("TapToChangeProductToolTip"))))
                    driver.findElement(getBy(browsePageLocators.get("TapToChangeProductToolTip"))).click();
                if (!isElementPresent(getBy(homePageLocators.get("offerZoneHeader")))) {
                    HomePageActions hp = new HomePageActions(driver);
                    hp.navigateBackHome();
                    status = true;
                }
            } else return false;
        List<WebElement> offerList = driver.findElements(getBy(homePageLocators.get("Offers")));
        int numberOfOffers = offerList.size();
        if (numberOfOffers > 0) {
            for (WebElement anOfferList : offerList) {
                anOfferList.click();
                Thread.sleep(5000);
                if (isElementPresent(getBy(productPageLocators.get("NarrowDownToolTip"))))
                    driver.findElement(getBy(productPageLocators.get("NarrowDownToolTip"))).click();
                if (isElementPresent(getBy(productPageLocators.get("SwipeBetweenProducts"))))
                    driver.findElement(getBy(productPageLocators.get("SwipeBetweenProducts"))).click();
                if (!isElementPresent(getBy(homePageLocators.get("Offers")))) {
                    HomePageActions hp = new HomePageActions(driver);
                    hp.navigateBackHome();
                    status = true;
                } else
                    return false;
            }
        } else {
            logger.info("There were no offers shown in the Offers Zone");
            return false;
        }
        return status;
    }

    /***
     * Moves to the browse page after selecting an OMU product from BrowsePage
     *
     * @return if the browse page was displayed for the selected OMU
     * @throws InterruptedException
     */
    public boolean selectOMU_MoveToBrowsePage() throws InterruptedException {
        List<WebElement> productItem = driver.findElements(getBy(FOZLocators.get("ProductWidgetParent")));
        int numberOfListItems = productItem.size();
        int accessItem = randomNumber(numberOfListItems - 1);
        productItem.get(accessItem).click();
        Thread.sleep(2000);
        if ((isElementPresent(getBy(browsePageLocators.get("FilterButtonLayout"))) && isElementPresent(getBy(browsePageLocators.get("SortButtonLayout"))))) {
            logger.info("Selected a product from the tab and moved to Browse Page.");
            return true;
        } else return false;
    }

    /***
     * Gets the count of tabs displayed for FOZ the in the device used.
     *
     * @return number of horizontal tabs displayed for FOZ
     * @throws InterruptedException
     */
    public int numberOfTabsDisplayed() throws InterruptedException {
        int count = 0;
        while (!isElementPresent(getBy(FOZLocators.get("HorizontalTabs").replace("|i|", "" + 0 + "")))) {
            swipeRight(10);
        }
        swipeRight(10);
        for (int i = 0; i < 10; i++) {
            if (isElementPresent(getBy(FOZLocators.get("HorizontalTabs").replace("|i|", "" + i + ""))))
                count++;
            else break;
        }
        return count;
    }
}