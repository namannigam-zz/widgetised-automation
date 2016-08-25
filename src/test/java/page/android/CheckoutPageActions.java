package page.android;

import mobile.util.Product;
import page.AppiumBasePage;
import org.openqa.selenium.WebDriver;

public class CheckoutPageActions extends AppiumBasePage {

    //TODO : using richview to validate the order details and review order

    public CheckoutPageActions(WebDriver driver) {
        super(driver);
    }

    /***
     * Continues to pay as COD after the delivery address has been specified.
     *
     * @return if the COD for order was selected or not
     * @throws InterruptedException
     */
    public boolean continueToPay_COD() throws InterruptedException {
        waitForElementPresent(checkOutPageLocators.get("ProceedToPayButton"), 5);
        driver.findElement(getBy(checkOutPageLocators.get("ProceedToPayButton"))).click();
        logger.info("Proceeding to pay for the purchase..");
        Thread.sleep(4000);
        waitForElementPresent(checkOutPageLocators.get("CODLink"), 5);
        driver.findElement(getBy(checkOutPageLocators.get("CODLink"))).click();
        Thread.sleep(2000);
        if (isElementPresent(getBy(checkOutPageLocators.get("PlaceOrderButton")))) {
            logger.info("Successfully selected the COD payment mode for the transaction.");
            return true;
        } else return false;
    }

    /***
     * Logs user in using credentials from the checkout page.
     *
     * @param email    account email
     * @param password account password
     * @return if the login from checkout page was successful or not
     * @throws InterruptedException
     */
    public boolean loginFromCheckoutPage(String email, String password) throws InterruptedException {
        if (isElementPresent(getBy(checkOutPageLocators.get("CheckOutLoginIdChange")))) {
            driver.findElement(getBy(checkOutPageLocators.get("CheckOutLoginIdChange"))).click();
            logger.info("Changing the default number or email provided...");
            Thread.sleep(3000);
            driver.findElement(getBy(checkOutPageLocators.get("CheckOutLoginEmailMobileNumber"))).clear();
        }
        Thread.sleep(1000);
        waitForElementPresent(checkOutPageLocators.get("CheckOutLoginEmailMobileNumber"), 3);
        driver.findElement(getBy(checkOutPageLocators.get("CheckOutLoginEmailMobileNumber"))).sendKeys(email);
        Thread.sleep(1000);
        driver.findElement(getBy(checkOutPageLocators.get("CheckOutContinueSignInButton"))).click(); //Continue Button
        Thread.sleep(2000);
        waitForElementPresent(checkOutPageLocators.get("CheckOutLoginPassword"), 3);
        driver.findElement(getBy(checkOutPageLocators.get("CheckOutLoginPassword"))).sendKeys(password);
        logger.info("Login credentials entered.");
        Thread.sleep(1000);
        driver.findElement(getBy(checkOutPageLocators.get("CheckOutContinueSignInButton"))).click(); //Login Button
        waitUntilProgressBarIsDisplayed();
        Thread.sleep(3000);
        if (isElementPresent(getBy(checkOutPageLocators.get("CheckOutLoginText"))) ||
                isElementPresent(getBy(checkOutPageLocators.get("CheckOutDeliveryText"))) ||
                isElementPresent(getBy(checkOutPageLocators.get("CheckOutPaymentText")))) {
            logger.info("Successfully moved to the checkout delivery page.");
            return true;
        } else return false;
    }

    /***
     * Specifies the address if not present OR select existing address for delivery section of Checkout
     *
     * @return if there is an address correctly selected for delivery of product or not
     * @throws InterruptedException
     */
    public boolean addressOnCheckout() throws InterruptedException {
        if (isElementPresent(getBy(checkOutPageLocators.get("AddAddressTitle")))) {
            waitForElementPresent(checkOutPageLocators.get("AddressName"), 3);
            driver.findElement(getBy(checkOutPageLocators.get("AddressName"))).sendKeys("Flipping Automation");
            waitForElementPresent(checkOutPageLocators.get("AddressPincode"), 3);
            driver.findElement(getBy(checkOutPageLocators.get("AddressPincode"))).sendKeys("560037");
            waitForElementPresent(checkOutPageLocators.get("AddressAdress"), 3);
            driver.findElement(getBy(checkOutPageLocators.get("AddressAdress"))).sendKeys("Cessna Business Park");
            waitForElementPresent(checkOutPageLocators.get("AddressLandmark"), 3);
            driver.findElement(getBy(checkOutPageLocators.get("AddressLandmark"))).sendKeys("Outer Ring Road");
            waitForElementPresent(checkOutPageLocators.get("AddressLandmark"), 3);
            driver.findElement(getBy(checkOutPageLocators.get("AddressLandmark"))).sendKeys("7100400150");
            waitForElementPresent(checkOutPageLocators.get("SaveContinueButton"), 3);
            driver.findElement(getBy(checkOutPageLocators.get("SaveContinueButton"))).click();
            Thread.sleep(3000);
            if (isElementPresent(getBy(checkOutPageLocators.get("ProceedToPayButton")))) {
                logger.info("Successfully provided the address during checkout. Proceed to Payment.");
                return true;
            } else return false;
        }
        if (isElementPresent(getBy(checkOutPageLocators.get("ProceedToPayButton")))) {
            logger.info("Address saved for the delivery options.");
            return true;
        } else return false;
    }


    /***
     * Validates order details from the checkout page.
     *
     * @param paymentMode method to be selected for payment
     * @return if the order details are correct or not
     * @throws InterruptedException
     */
    public boolean validateOrderDetails(String paymentMode) throws InterruptedException {
        boolean status = true;
        String temp = "", title = "";
        waitForElementPresent(orderConfirmationPageLocators.get("OROrderStatus"), 5);
        temp = temp.substring(temp.indexOf("order_id=") + 9);
        String transactionId = temp.substring(0, temp.indexOf("&")).trim();
        if (!driver.findElement(getBy(orderConfirmationPageLocators.get("OROrderStatus"))).getText().contains("Order Placed Successfully!")) {
            logger.info("Order is not placed successfully for transaction id: " + transactionId);
            status = false;
        }
        if (!isElementPresent(getBy(orderConfirmationPageLocators.get("OROrderDate")))) {
            logger.info("Order Date is not visible for transaction id: " + transactionId);
            status = false;
        }
        if (paymentMode.equalsIgnoreCase("Cash On Delievery")) {
            if (!isElementPresent(getBy(orderConfirmationPageLocators.get("ORPaymentMode")))) {
                logger.info("Payment Mode is not visible for COD order for transaction id: " + transactionId);
                status = false;
            }
            if (!driver.findElement(getBy(orderConfirmationPageLocators.get("ORStatus"))).getText().contains("Approved")) {
//                    logger.info("Status is expected Approved but not found for COD order for transaction id: "+transactionId);
                status = false;
            }
        }
        if (!isElementPresent(getBy(orderConfirmationPageLocators.get("ORGrandTotal")))) {
//                logger.info("Grand Total is not visible for COD order for transaction id: "+transactionId);
            status = false;
        } else {
            temp = driver.findElement(getBy(orderConfirmationPageLocators.get("ORGrandTotal"))).getText();
            int total = Integer.parseInt(temp.substring(temp.indexOf(".") + 1).trim());
            if (total != cart.amountPayable) {
//                    logger.info("Grand Total mis-match between cart and confirmation page for transaction id: "+transactionId);
                status = false;
            }
        }

        for (int i = 1; i <= driver.findElements(getBy(orderConfirmationPageLocators.get("OROrdersClosed"))).size(); i++) {
            if (!isElementPresent(getBy(orderConfirmationPageLocators.get("OROrder").replace("-i-", "" + i + ""))))
                driver.findElement(getBy(orderConfirmationPageLocators.get("OROrderClosed").replace("-i-", "" + i + ""))).click();
        }
        int count = 0;
        for (int i = 1; i <= driver.findElements(getBy(orderConfirmationPageLocators.get("OROrders"))).size(); i++) {
            if (!isElementPresent(getBy(orderConfirmationPageLocators.get("OROrderId").replace("-i-", "" + i + "")))) {
//                    logger.info("Order Id is not displayed for transaction id: "+transactionId);
                status = false;
            }
            temp = driver.findElement(getBy(orderConfirmationPageLocators.get("OROrderItems").replace("-i-", "" + i + ""))).getText();
            count += Integer.parseInt(temp.substring(1, temp.indexOf("I")).trim());
            if (!isElementPresent(getBy(orderConfirmationPageLocators.get("OROrderTotal").replace("-i-", "" + i + "")))) {
//                    logger.info("Order Id is not displayed for transaction id: "+transactionId);
                status = false;
            }
            String sellerName = driver.findElement(getBy(orderConfirmationPageLocators.get("ORSellerName").replace("-i-", "" + i + ""))).getText();
            if (!cart.isContainSeller(sellerName)) {
//                    logger.info("Seller: "+sellerName+" not added for transaction id "+transactionId);
                status = false;
            } else {
                if (!sellerName.equalsIgnoreCase("WS Retail")) {
                    if (!driver.findElement(getBy(orderConfirmationPageLocators.get("OROtherSellerDeliveryCharge"))).getText().replace("-i-", "" + i + "").contains("Delivery charges per item")) {
                        logger.info("\"Delivery charges per item\" missing for non wsr seller.");
                        status = false;
                    }
                }
            }
            for (int j = 1; j <= driver.findElements(getBy(orderConfirmationPageLocators.get("ORItems").replace("-i-", "" + i + ""))).size(); j++) {
                title = driver.findElement(getBy(orderConfirmationPageLocators.get("ORItemTitle").replace("-i-", "" + i + "").replace("-j-", "" + j + ""))).getText();
                Product item = cart.getProductByTitle(sellerName, title);
                if (!driver.findElement(getBy(orderConfirmationPageLocators.get("ORItemTitle").replace("-i-", "" + i + ""))).getText().replace("-j-", "" + j + "").contains(item.getTitle())) {
                    logger.info("Product Title mis-match for product: " + title);
                    status = false;
                }
                int finalUnitPrice = item.getFinalPrice();
                int quantity = Integer.parseInt(driver.findElement(getBy(orderConfirmationPageLocators.get("ORQuantity").replace("-i-", "" + i + "").replace("-j-", "" + j + ""))).getText());
                temp = driver.findElement(getBy(orderConfirmationPageLocators.get("ORSubtotal").replace("-i-", "" + i + "").replace("-j-", "" + j + ""))).getText();
                int subTotal = Integer.parseInt(temp.substring(temp.indexOf(".") + 1).trim());
                int shippingCharge = 0;
                //clickButton(orderConfirmationPageLocators.get("SellerProductDetailTab").replace("-i-",""+i+"").replace("-j-",""+j+""));
                if (!sellerName.equalsIgnoreCase("WS Retail")) {
                    if (item.isShipping())
                        shippingCharge = item.getShippingCharge();
                }
                if (subTotal != ((finalUnitPrice + shippingCharge) * quantity)) {
                    logger.info("Observed total price is not equal to the final price * quantity for product : " + title);
                    status = false;
                }
                if (!isElementPresent(getBy(orderConfirmationPageLocators.get("ORExpectedShippingDate").replace("-i-", "" + i + "").replace("-j-", "" + j + "")))) {
//                        logger.info("Expected shipping date is not visible for transaction id: "+transactionId);
                    status = false;
                }
                if (!isElementPresent(getBy(orderConfirmationPageLocators.get("ORExpectedDeliveryDate").replace("-i-", "" + i + "").replace("-j-", "" + j + "")))) {
//                        logger.info("Expected delivery date is not visible for transaction id: "+transactionId);
                    status = false;
                }
            }
        }
        if (count != cart.itemCount) {
            logger.info("Item count mis-match from confirmation to cart.");
            status = false;
        }
        driver.findElement(getBy(orderConfirmationPageLocators.get("ORContinueShopping"))).click();
        Thread.sleep(10000);
        return status;
    }

    /***
     * Reviews the order placed during the checkout.
     *
     * @return if the order placed has correct details or not
     * @throws InterruptedException
     */
    public boolean reviewOrder() throws InterruptedException {
        boolean status = true;
        String temp, sellerName = null, title;
        int finalUnitPrice, obervedUnitFinalPrice = 0, quantity = 0, observedTotalPrice = 0, amount, amountPayable;
        amountPayable = 0;
        if (isElementPresent(getBy(checkOutPageLocators.get("CollapsibleClosed")))) {
            driver.findElement(getBy(checkOutPageLocators.get("CollapsibleClosed"))).click();
        }
        waitForElementPresent(checkOutPageLocators.get("SellerGroup"), 2);
        for (int i = 1; i <= driver.findElements(getBy(checkOutPageLocators.get("SellerGroup"))).size(); i++) {
            temp = driver.findElement(getBy(checkOutPageLocators.get("SellerName").replace("-i-", "" + i + ""))).getText();
            temp = temp.substring(temp.indexOf(":") + 1).trim();
            if (temp.contains("Delivery"))
                sellerName = temp.substring(0, temp.indexOf("Delivery")).trim();
            else
                sellerName = temp;
            if (!cart.isContainSeller(sellerName)) {
                logger.info("Seller: " + sellerName + " not added.");
                status = false;
            } else {
                amount = 0;
                for (int j = 1; j <= driver.findElements(getBy(checkOutPageLocators.get("SellerProductRows").replace("-i-", "" + i + ""))).size(); j++) {
                    title = driver.findElement(getBy(checkOutPageLocators.get("SellerProductTitle"))).getText();
                    Product item = cart.getProductByTitle(sellerName, title);
                    if (!title.contains(item.getTitle()) && !item.getTitle().contains(title)) {
                        logger.info("Product Title mis-match for product Found: " + title + " Expected: " + item.getTitle());
                        status = false;
                    }
                    finalUnitPrice = item.getFinalPrice();
                    temp = driver.findElement(getBy(checkOutPageLocators.get("SellerProductUnitPrice").replace("-i-", "" + i + ""))).getText();
                    obervedUnitFinalPrice = Integer.parseInt(temp.substring(temp.indexOf(".") + 1).trim());
                    if (finalUnitPrice != obervedUnitFinalPrice) {
                        logger.info("Price mis-match between the summary and the product page for product: " + title);
                        status = false;
                    }
                    temp = driver.findElement(getBy(checkOutPageLocators.get("SellerProductQty").replace("-i-", "" + i + ""))).getText();
                    quantity = item.getQuantity();
                    temp = driver.findElement(getBy(checkOutPageLocators.get("SellerProductSubTotal").replace("-i-", "" + i + ""))).getText();
                    temp = temp.substring(temp.indexOf(".") + 1).trim();
                    if (temp.contains("Free"))
                        observedTotalPrice = Integer.parseInt(temp.substring(0, temp.indexOf("Free")).trim());
                    else if (temp.contains("including"))
                        observedTotalPrice = Integer.parseInt(temp.substring(0, temp.indexOf("including")).trim());
                    else
                        observedTotalPrice = Integer.parseInt(temp.trim());
                    int shippingCharge = 0;
                    if (!sellerName.equalsIgnoreCase("WS Retail")) {
                        if (item.isShipping()) {
                            shippingCharge = item.getShippingCharge();
                        }
                        if (!driver.findElement(getBy(checkOutPageLocators.get("SellerProductShippingText"))).getText().replace("-i-", "" + i + "").contains("Delivery")) {
                            logger.info("Shipping text missing for non-wsr product with product: " + title);
                            status = false;
                        }
                    } else if (observedTotalPrice != ((finalUnitPrice + shippingCharge) * quantity)) {
                        logger.info("Observed total price is not equal to the final price * quantity for product : " + title + " Final: " + finalUnitPrice + " shipping: " + shippingCharge + " quantity: " + quantity + " observed price: " + observedTotalPrice);
                        status = false;
                    }

                    amount += observedTotalPrice;
                }
                if (sellerName.equalsIgnoreCase("WS Retail")) {
                    if (amount > 500) {
                        if (!driver.findElement(getBy(checkOutPageLocators.get("SellerProductShippingText"))).getText().replace("-i-", "" + i + "").contains("")) {
                            logger.info("For WSR total amount is greater than 500 \"Free Delivery text\" is not there on cart.");
                            status = false;
                        }
                    } else {
                        if (!driver.findElement(getBy(checkOutPageLocators.get("SellerProductShippingText"))).getText().replace("-i-", "" + i + "").contains("Delivery Charge")) {
                            logger.info("For WSR total amount is less than 501 \"Delivery Charges: Rs.40\" is not there on cart." +
                                    driver.findElement(getBy(cartPageLocators.get("SellerDeliveryChargeText").replace("-i-", "" + i + ""))).getText() + "*");
                            status = false;
                        }
                    }
                }
                amountPayable += amount;
            }
        }
        temp = driver.findElement(getBy(checkOutPageLocators.get("AmountPayable"))).getText();
        int observedAmountPayable = Integer.parseInt(temp.substring(temp.indexOf(".") + 1).trim());
        if (observedAmountPayable != amountPayable) {
            logger.info("Total sum of prices mis-match on the cart.");
            status = false;
        }
        if (cart.amountPayable == 0)
            cart.amountPayable = observedAmountPayable;
        return status;
    }
}