package page.android;

import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import java.util.List;

public class SearchPageActions extends AppiumBasePage {

    public SearchPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Searches an item on the search page and land on browse page.
     *
     * @param searchString product/category to search
     * @return if product search is successful or not
     * @throws InterruptedException
     */
    public boolean search(String searchString) throws InterruptedException {
        driver.findElement(getBy(searchPageLocators.get("SearchBox"))).sendKeys(searchString + "\n"); //The /n acts as ENTER here
        logger.info("Searching for the products under " + searchString + "...");

        Thread.sleep(3000);
        if (isElementPresent(getBy(searchPageLocators.get("ContinueShopping"))) && isElementPresent(getBy(searchPageLocators.get("NullResultLayout")))) {
            logger.error("Search wasn't successful for " + searchString);
            return false;
        }
        while (isElementPresent(getBy(productPageLocators.get("ProductListCuetip")))) {
            driver.findElement(getBy(productPageLocators.get("ProductListCuetip"))).click();
            Thread.sleep(500);
        }
        if (isElementPresent(getBy(browsePageLocators.get("SortButtonLayout"))) && isElementPresent(getBy(browsePageLocators.get("FilterButtonLayout")))
                && isElementPresent(getBy(browsePageLocators.get("ToggleButtonLayout")))) {
            logger.info("Search was successful for " + searchString);
            return true;
        } else return false;
    }

    /***
     * Clears the search list and move back to the search page.
     *
     * @return if the search tag is cleared or not
     * @throws InterruptedException
     */
    public boolean clearSearchTag() throws InterruptedException {
        if (isElementPresent(getBy(searchPageLocators.get("AfterSearchClear")))) {
            waitForElementPresent(searchPageLocators.get("AfterSearchClear"), 8);
            driver.findElement(getBy(searchPageLocators.get("AfterSearchClear"))).click();
            logger.info("Clearing the searched content...");
            return isElementPresent(getBy(searchPageLocators.get("SearchLayout")));
        } else {
            logger.info("Search already cleared.");
            return true;
        }
    }

    /***
     * Selects an item from the auto-suggest list on search page.
     *
     * @param searchString product/category to be searched
     * @return if the auto-suggest search was successful or not
     * @throws InterruptedException
     */
    public boolean searchFromAutoSuggestList(String searchString) throws InterruptedException {
        waitForElementPresent(searchPageLocators.get("SearchBox"), 8);
        driver.findElement(getBy(searchPageLocators.get("SearchBox"))).sendKeys(searchString);
        Thread.sleep(2000);
        List<WebElement> autoCompleteList = driver.findElements(getBy(searchPageLocators.get("AutoCompleteText")));
        int numberOfListItems = autoCompleteList.size();
        int accessItem = randomNumber(numberOfListItems - 1);
        autoCompleteList.get(accessItem).click();
        logger.info("Selecting a random item from autoSuggest list...");
        Thread.sleep(2000);
        if (isElementPresent(getBy(searchPageLocators.get("ContinueShopping"))) && isElementPresent(getBy(searchPageLocators.get("NullResultLayout")))) {
            logger.error("Search wasn't successful for " + searchString);
            return false;
        }
        while (isElementPresent(getBy(productPageLocators.get("ProductListCuetip")))) {
            driver.findElement(getBy(productPageLocators.get("ProductListCuetip"))).click();
            Thread.sleep(500);
        }
        while (isElementPresent(getBy(productPageLocators.get("ProductListWithoutGuideCuetip")))) {
            driver.findElement(getBy(productPageLocators.get("ProductListWithoutGuideCuetip"))).click();
            Thread.sleep(500);
        }
        if (isElementPresent(getBy(browsePageLocators.get("SortButtonLayout"))) && isElementPresent(getBy(browsePageLocators.get("FilterButtonLayout")))
                && isElementPresent(getBy(browsePageLocators.get("ToggleButtonLayout")))) {
            logger.info("Search was successful for " + searchString);
            return true;
        } else return false;
    }

    /***
     * Uses guided search for searched string and validate.
     *
     * @return if the guided search filter is applied or not
     * @throws InterruptedException
     */
    public boolean guidedSearch() throws InterruptedException {
        waitForElementPresent(searchPageLocators.get("GuidedSearchScrollView"), 3);
        swipeLeft(6);
        if (isElementPresent(getBy(searchPageLocators.get("GuidedSearchScrollView")))) {
            List<WebElement> guidedSearchSuggestions = driver.findElements(getBy(searchPageLocators.get("GuidedSearchSuggestions")));
            int accessItem = randomNumber(guidedSearchSuggestions.size() - 1);
            String guideAdded = guidedSearchSuggestions.get(accessItem).getText();
            guidedSearchSuggestions.get(accessItem).click();
            waitForElementPresent(browsePageLocators.get("SortButtonLayout"), 3);
            List<String> customSearchTags = customSearchComponentsList();
            if (customSearchTags.contains(guideAdded) && isElementPresent(getBy(browsePageLocators.get("FilterButtonImage")))) {
                logger.error("Guided search filter applied successfully.");
                return true;
            } else return false;
        } else {
            logger.error("No horizontal scroll view displayed for the search suggestions.");
            return false;
        }
    }

    /***
     * Moves to searching using the camera search option on search page.
     *
     * @return if the camera is opened using the option or not
     * @throws InterruptedException
     */
    public boolean navigateToCamera() throws InterruptedException {
        waitForElementPresent(searchPageLocators.get("ImageSearchButton"), 8);
        driver.findElement(getBy(searchPageLocators.get("ImageSearchButton"))).click();
        logger.info("Opening camera for image search..");
        Thread.sleep(3000);
        if (isElementPresent(getBy(searchPageLocators.get("AfterImageSearch"))) || isElementPresent(getBy(searchPageLocators.get("CaptureImage")))) {
            driver.navigate().back();
            Thread.sleep(2000);
            logger.info("Camera opened for image search.");
            return true;
        } else return false;
    }

    /***
     * Searches a sub-category element for a category search.
     *
     * @param searchString     search category
     * @param suggestionString expected sub-category
     * @return if the sub category search was successful or not
     * @throws InterruptedException
     */
    public boolean subCategorySearch(String searchString, String suggestionString) throws InterruptedException {
        Thread.sleep(2000);
        waitForElementPresent(searchPageLocators.get("SearchBox"), 8);
        driver.findElement(getBy(searchPageLocators.get("SearchBox"))).sendKeys(searchString);
        logger.info("Populating the subcategories for " + searchString);
        Thread.sleep(3000);
        List<WebElement> autoCompleteList = driver.findElements(getBy(searchPageLocators.get("AutoCompleteText")));
        int numberOfListItems = driver.findElements(getBy(searchPageLocators.get("AutoCompleteText"))).size();
        for (int i = 0; i < numberOfListItems; i++) {
            String suggestedText = autoCompleteList.get(i).getText();
            logger.info("Sub-category + " + i + 1 + " for " + searchString + " is " + suggestedText);
            if (suggestedText.contains(suggestionString)) {
                autoCompleteList.get(i).click();
                logger.info("Found the sub category " + suggestionString + " for " + searchString);
                break;
            }
        }
        Thread.sleep(4000);
        if (isElementPresent(getBy(searchPageLocators.get("ContinueShopping"))) && isElementPresent(getBy(searchPageLocators.get("NullResultLayout")))) {
            logger.error("Search wasn't successful for " + searchString);
            return false;
        }
        while (isElementPresent(getBy(productPageLocators.get("ProductListCuetip")))) {
            driver.findElement(getBy(productPageLocators.get("ProductListCuetip"))).click();
            Thread.sleep(500);
        }
        while (isElementPresent(getBy(productPageLocators.get("ProductListWithoutGuideCuetip")))) {
            driver.findElement(getBy(productPageLocators.get("ProductListWithoutGuideCuetip"))).click();
            Thread.sleep(500);
        }
        if (isElementPresent(getBy(browsePageLocators.get("SortButtonLayout"))) && isElementPresent(getBy(browsePageLocators.get("FilterButtonLayout")))
                && isElementPresent(getBy(browsePageLocators.get("ToggleButtonLayout")))) {
            logger.info("Sub Category search was successful for " + searchString);
            return true;
        } else return false;
    }

    /***
     * Clears the search history from a device.
     *
     * @return if the search history is cleared or not
     * @throws InterruptedException
     */
    public boolean clearSearchHistory() throws InterruptedException, TimeoutException, NullPointerException {
        waitForElementPresent(searchPageLocators.get("SearchBox"), 8);
        driver.findElement(getBy(searchPageLocators.get("SearchBox"))).click();
        logger.info("Looking for the entire searched history under the dropdown...");
        Thread.sleep(3000);
        if (isElementPresent(getBy(searchPageLocators.get("SearchHistoryList")))) {
            List<WebElement> searchHistory = driver.findElements(getBy(searchPageLocators.get("SearchHistoryList")));
            for (int i = 0; i < searchHistory.size(); i++) {
                searchHistory.get(0).click();
                logger.info("Cleared the first element from the search history.");
                Thread.sleep(1000);
            }
        } else logger.info("There is no search history currently for the application.");
        return (!(isElementPresent(getBy(searchPageLocators.get("SearchHistoryList")))));
    }

    /***
     * Gets the count of elements included in the custom search.
     *
     * @return the count of custom search components
     */
    public int countCustomSearchComponent() {
        if (isElementPresent(getBy(searchPageLocators.get("CustomSearchTags")))) {
            List<WebElement> guidedSearchElements = driver.findElements(getBy(searchPageLocators.get("CustomSearchTags")));
            logger.info("Total guided search elements in the custom search bar are : " + guidedSearchElements.size());
            return guidedSearchElements.size();
        } else return 0;
    }

    /***
     * Creates a list of all the custom search components text.
     *
     * @return the list of custom components texts
     */
    public List<String> customSearchComponentsList() {
        List<String> customSearchElements = null;
        List<WebElement> customSearchComponents = driver.findElements(getBy(searchPageLocators.get("CustomSearchTexts")));
        for (int i = 0; i < customSearchComponents.size(); i++)
            customSearchElements.add(i, customSearchComponents.get(i).getText());
        return customSearchElements;
    }

    /***
     * Moves to searching using the camera search icon on homepage for Tablets
     *
     * @return if the camera using widget for tablets is opened or not
     * @throws InterruptedException
     */
    public boolean navigateToCameraUsingWidget_Tablets() throws InterruptedException {
        waitForElementPresent(searchPageLocators.get("SearchWidgetImage"), 8);
        driver.findElement(getBy(searchPageLocators.get("SearchWidgetImage"))).click();
        logger.info("Opening camera for image search..");
        Thread.sleep(3000);
        if (isElementPresent(getBy(searchPageLocators.get("AfterImageSearch"))) || isElementPresent(getBy(searchPageLocators.get("CaptureImage")))) {
            driver.navigate().back();
            Thread.sleep(2000);
            return true;
        } else return false;
    }

    /***
     * Removes a component form the custom search tags.
     *
     * @return if the custom search component was removed successfully or not
     * @throws InterruptedException
     */
    public boolean removeCustomSearchElement() throws InterruptedException {
        List<WebElement> removeCustomSearchTag = driver.findElements(getBy(searchPageLocators.get("CustomSearchCancel")));
        if (removeCustomSearchTag.size() > 1) {
            removeCustomSearchTag.get(removeCustomSearchTag.size() - 1).click();
            if (isElementPresent(getBy(browsePageLocators.get("SortButtonLayout"))) && isElementPresent(getBy(browsePageLocators.get("FilterButtonLayout")))
                    && isElementPresent(getBy(browsePageLocators.get("ToggleButtonLayout")))) {
                logger.info("Successfully cleared the last custom search tag.");
                return true;
            } else {
                logger.warn("Search not displayed properly after removing the last custom search tag.");
                return false;
            }
        } else if (removeCustomSearchTag.size() == 1) {
            removeCustomSearchTag.get(0).click();
            if (isElementPresent(getBy(searchPageLocators.get("SearchSuggestionListView"))) && isElementPresent(getBy(searchPageLocators.get("AutoCompleteTapHead")))) {
                logger.info("Successfully cleared the only custom search tag.");
                return true;
            } else {
                logger.warn("Suggestions not displayed properly after removing the only custom search tag.");
                return false;
            }
        } else {
            logger.warn("No custom search tag displayed on the search page.");
            return false;
        }
    }
}