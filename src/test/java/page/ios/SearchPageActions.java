package page.ios;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


public class SearchPageActions extends AppiumBasePage {
    BrowsePageActions browsePageActions;

    public SearchPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Method to set the other page objects
     */
    public void setOtherPageObjects(BrowsePageActions bpActions) {
        browsePageActions = bpActions;
    }

    /***
     * Method to check Search page is loaded.
     *
     * @return if the Search page is loaded
     * @throws Exception
     */
    public boolean isSearchPageloaded() throws Exception {
        if (!isElementPresent(getBy(searchPageLocators.get("SearchField")))) {
            logger.warn("Search field not loaded in the Search screen.");
            return false;
        }
        if (!isElementPresent(getBy(searchPageLocators.get("CancelButton")))) {
            logger.warn("Cancel button not loaded in the Search screen.");
            return false;
        }
        logger.info("Loaded the Search screen.");
        return true;
    }

    /***
     * Method to check Search History page is loaded.
     *
     * @return if the Search History page is loaded
     * @throws Exception
     */
    public boolean isSearchHistoryPageloaded() throws Exception {
        if (!isElementPresent(getBy(searchPageLocators.get("History")))) {
            logger.warn("History not loaded in the Search History screen.");
            return false;
        }
        if (!isElementPresent(getBy(searchPageLocators.get("ClearButton")))) {
            logger.warn("Clear button not loaded in the Search History screen.");
            return false;
        }
        logger.info("Loaded the Search History screen.");
        return true;
    }

    /***
     * Method to click Cancel button.
     *
     * @throws Exception
     */
    public void clickCancelButton() throws Exception {
        WebElement cancel = driver.findElement(getBy(searchPageLocators.get("CancelButton")));
        cancel.click();
        logger.info("Clicked cancel button");
    }

    /***
     * Method to click Clear button.
     *
     * @throws Exception
     */
    public void clickClearButton() throws Exception {
        driver.findElement(getBy(searchPageLocators.get("SearchField"))).click();
        if(isElementPresent(getBy(searchPageLocators.get("ClearText")))) {
            WebElement clear = driver.findElement(getBy(searchPageLocators.get("ClearText")));
            clear.click();
            logger.info("Clicked clear button");
        }
    }

    /***
     * Method to click Clear History button.
     *
     * @throws Exception
     */
    public void clickClearHistoryButton() throws Exception {
        WebElement clear = driver.findElement(getBy(searchPageLocators.get("ClearButton")));
        clear.click();
        logger.info("Clicked clear history button");
    }

    /***
     * Method to enter search string.
     *
     * @param searchString for searching the products
     * @throws Exception
     */
    public void enterSearchString(String searchString) throws Exception {
        driver.findElement(getBy(searchPageLocators.get("SearchField"))).sendKeys(searchString);
        logger.info("Entered Search String");
    }

    /***
     * Method to enter Return string.
     *
     * @throws Exception
     */
    public void enterReturnKey() throws Exception {
        driver.getKeyboard().sendKeys("\uE007");
        logger.info("Entered Return Key");
    }


    /***
     * Method to search for products.
     *
     * @param searchString for searching the products
     * @return if the search was successful or not
     * @throws Exception
     */
    public boolean search(String searchString) throws Exception {
        clickClearButton();
        enterSearchString(searchString);
        enterReturnKey();
        if (browsePageActions.isBrowsePageloaded()) {
            logger.info("Browse page loaded after search");
            browsePageActions.clickBackButton();
            return true;
        }
        return false;
    }

    /***
     * Method to Clear the entered search string
     *
     * @param searchString for searching the products
     * @return if the clearing search string was successful or not
     * @throws Exception
     */
    public boolean clearSearchString(String searchString) throws Exception {
        clickClearButton();
        enterSearchString(searchString);
        clickClearButton();
        WebElement searchText = driver.findElement(getBy(searchPageLocators.get("SearchField")));
        if (searchText.getAttribute("value").equals("Search for product/brands           ")) {
            logger.info("Search Text Cleared");
            return true;
        }
        logger.warn("Search Text not Cleared");
        return false;
    }

    /***
     * Method to click entry from search guide of the entered search string
     *
     * @param searchString for searching the products
     * @return if the search using search guide was successful or not
     * @throws Exception
     */
    public boolean searchGuide(String searchString) throws Exception {
        clickClearButton();
        enterSearchString(searchString);
        WebElement search_guide = driver.findElement(getBy(searchPageLocators.get("SearchGuide")));
        List<WebElement> ele_list = search_guide.findElements(getBy(searchPageLocators.get("TableCellClass")));
        int ele_count = ele_list.size();
        if(ele_count == 0) {
            logger.warn("No search guide for the search string '" + searchString + "'");
            return false;
        }
        int index = 0;
        if(ele_count > 4) {
            index = 4;
        }
        ele_list.get(index).click();
        if (browsePageActions.isBrowsePageloaded()) {
            logger.info("Browse page loaded after search");
            browsePageActions.clickBackButton();
            return true;
        }
        return false;
    }

    /***
     * Method to autofill from search guide of the entered search string
     *
     * @param searchString for searching the products
     * @return if the autofill from search guide was successful or not
     * @throws Exception
     */
    public boolean searchGuideAutofill(String searchString) throws Exception {
        clickClearButton();
        enterSearchString(searchString);
        WebElement search_guide = driver.findElement(getBy(searchPageLocators.get("SearchGuide")));
        List<WebElement> ele_list = search_guide.findElements(getBy(searchPageLocators.get("TableCellClass")));
        int ele_count = ele_list.size();
        if(ele_count == 0) {
            logger.warn("No search guide for the search string '" + searchString + "'");
            return false;
        }
        int i;
        for(i=1; i<5; i++) {
            String ele_xpath = searchPageLocators.get("SearchGuide") + "/UIATableCell[" + (i+1) + "]/UIAButton[1]";
            if(isElementPresent(getBy(ele_xpath))) {
                WebElement search_guide_cell = driver.findElement(getBy(ele_xpath));
                search_guide_cell.click();
                break;
            }
        }
        if(i==5) {
            logger.warn("No search guide entry with autofill option");
            return false;
        }
        String searchText = driver.findElement(getBy(searchPageLocators.get("SearchField"))).getAttribute("value");
        String search_guide_cell_text = driver.findElement(getBy(searchPageLocators.get("SearchGuide") + "/UIATableCell[" + (i+1) + "]/UIAStaticText[1]")).getText();
        if(!searchText.equals(search_guide_cell_text)) {
            logger.warn("The entry clicked in search guide and the search text value doesn't match");
            return false;
        }
        driver.findElement(getBy(searchPageLocators.get("SearchField"))).click();
        enterReturnKey();
        if (browsePageActions.isBrowsePageloaded()) {
            logger.info("Browse page loaded after search");
            browsePageActions.clickBackButton();
            return true;
        }
        return false;
    }

    /***
     * Method to Clear the search history
     *
     * @return if the clear search history was successful or not
     * @throws Exception
     */
    public boolean clearSearchHistory() throws Exception {
        clickClearButton();
        if(!isSearchHistoryPageloaded()) {
            return false;
        }
        clickClearHistoryButton();
        if (isElementPresent(getBy(searchPageLocators.get("EmptyTable")))) {
            logger.info("Search History Cleared");
            return true;
        }
        logger.warn("Search History not Cleared");
        return false;
    }

    /***
     * Method to navigate to browse page.
     *
     * @param searchString for searching the products
     * @return if the navigating to browse page was successful or not
     * @throws Exception
     */
    public boolean navigateToBrowsePage(String searchString) throws Exception {
        clickClearButton();
        enterSearchString(searchString);
        enterReturnKey();
        if (browsePageActions.isBrowsePageloaded()) {
            logger.info("Browse page loaded after search");
            return true;
        }
        return false;
    }
}