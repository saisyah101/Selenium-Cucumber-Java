package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.Global_Vars;

import java.util.ArrayList;
import java.util.List;

public class EndToEnd_PO extends Base_PO{

    @FindBy(css = "h5[data-test='product-name']")
    private List<WebElement> productNames;

    @FindBy(xpath = "//p[@id='description']")
    private WebElement descriptionPDP;

    @FindBy(xpath = "//button[@id='btn-add-to-cart'] and @disabled")
    private WebElement addToCartDisabledButton;

    @FindBy(xpath = "//button[@id='btn-add-to-cart']")
    private WebElement addToCartEnabledButton;

    @FindBy(xpath = "//h1")
    private WebElement productNamePDP;

    @FindBy(css = "[aria-label='unit-price']")
    private WebElement productPricePDP;

    @FindBy(css = ".text-danger")
    private WebElement outOfStockLabel;

    @FindBy(xpath = "//div[@class='price-section']")
    private WebElement priceLabel;

    @FindBy(css = "[data-test='nav-cart']")
    private WebElement cartIcon;

    @FindBy(css = ".toast-message")
    private WebElement toastAlert;

    @FindBy(xpath = "//span[@id='lblCartCount']")
    private WebElement labelCount;

    @FindBy(xpath = "//input[@id='quantity-input']")
    private WebElement qtyInput;

    @FindBy(xpath = "//ul[@class='steps-4 steps-indicator']")
    private WebElement checkoutIndicator;

    @FindBy(xpath = "//button[@class='btn btn-secondary']")
    private WebElement continueShoppingButton;

    @FindBy(xpath = "//td[@class='col-md-1 align-middle']/a[1]")
    private WebElement removeProductButton;

    @FindBy(xpath = "//button[.='Proceed to checkout']")
    private WebElement proceedCheckoutButton1;

    @FindBy(xpath = "//span[@class='product-title']")
    private WebElement productTitleCheckout;

    @FindBy(css = "[data-test='product-price']")
    private WebElement productPriceCheckout;

    @FindBy(xpath = "//input[@class='form-control quantity']")
    private WebElement productQuantity;

    @FindBy(css = "[data-test='line-price']")
    private WebElement totalProductPrice;

    @FindBy(xpath = "//p[@class='ng-star-inserted']")
    private WebElement greetingText;

    @FindBy(css = "[data-test='proceed-2']")
    private WebElement proceedCheckoutButton2;

    @FindBy(css = "[data-test='proceed-3']")
    private WebElement proceedCheckoutButton3;

    @FindBy(xpath = "//h3[.='Billing Address']")
    private WebElement billingAddressTitle;

    @FindBy(xpath = "//input[@id='street']")
    private WebElement streetInput;

    @FindBy(xpath = "//input[@id='city']")
    private WebElement cityInput;

    @FindBy(xpath = "//input[@id='state']")
    private WebElement stateInput;

    @FindBy(xpath = "//input[@id='country']")
    private WebElement countryInput;

    @FindBy(xpath = "//input[@id='postal_code']")
    private WebElement postalCodeInput;

    @FindBy(xpath = "//h3[.='Payment']")
    private WebElement paymentTitle;

    @FindBy(xpath = "//select[@id='payment-method']")
    private WebElement paymentDropdown;

    @FindBy(xpath = "//button[contains(.,'Confirm') and @disabled]")
    private WebElement confirmButtonDisabled;

    @FindBy(xpath = "//button[contains(.,'Confirm')]")
    private WebElement confirmButtonEnabled;

    @FindBy(xpath = "//input[@id='bank_name']")
    private WebElement inputBankName;

    @FindBy(xpath = "//input[@id='account_name']")
    private WebElement inputAccountName;

    @FindBy(xpath = "//input[@id='account_number']")
    private WebElement inputAccountNumber;

    @FindBy(xpath = "//input[@id='credit_card_number']")
    private WebElement inputCCNumber;

    @FindBy(xpath = "//input[@id='expiration_date']")
    private WebElement inputExpDate;

    @FindBy(xpath = "//input[@id='cvv']")
    private WebElement inputCVV;

    @FindBy(xpath = "//input[@id='card_holder_name']")
    private WebElement inputCardHolderName;

    @FindBy(xpath = "//select[@id='monthly_installments']")
    private WebElement installmentsDropdown;

    @FindBy(xpath = "//input[@id='gift_card_number']")
    private WebElement inputGiftCardNumber;

    @FindBy(xpath = "//input[@id='validation_code']")
    private WebElement inputValidationCode;

    @FindBy(xpath = "//div[@class='help-block']")
    private WebElement successText;

    @FindBy(xpath = "//div[@id='order-confirmation']")
    private WebElement orderConfirmation;

    @FindBy(xpath = "//span[contains(.,'INV')]")
    private WebElement invoiceNumber;

    @FindBy(xpath = "//a[@id='menu']")
    private WebElement accountMenu;

    @FindBy(xpath = "//a[.='My invoices']")
    private WebElement myInvoicesMenu;

    @FindBy(css = "td:nth-of-type(1)")
    private WebElement myInvoiceNumberLabel;

    @FindBy(css = "td:nth-of-type(4)")
    private WebElement myInvoicePriceLabel;

    @FindBy(xpath = "//a[.='Details']")
    private WebElement myInvoiceDetailsButton;

    @FindBy(xpath = "//h3[.='General Information']")
    private WebElement generalInfoLabel;

    @FindBy(xpath = "//input[@id='invoice_number']")
    private WebElement detailInvoiceNumber;

    @FindBy(xpath = "//input[@id='payment_method']")
    private WebElement detailPaymentMethod;

    @FindBy(css = "td:nth-of-type(2)")
    private WebElement detailProductName;






    public EndToEnd_PO() {
        super();
    }

    private String currentProductName;
    private String currentProductPrice;
    private Integer initialCount;
    private String paymentType;
    private String currentInvoiceNumber;


    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : productNames) {
            waitFor(element);
            names.add(element.getText().toLowerCase().trim());
        }
        System.out.println("Product names:" + names);
        return names;
    }

    public List<String> getAllProductNames() {
        List<String> names = getTextFromElements(productNames);
        System.out.println("Product names: " + names);
        return names;
    }

    public void goToProductDetailPage(WebElement product) {
        waitFor(product);
        clickElement(product);
    }

    public void goToPdpFirstProduct() {
        goToProductDetailPage(productNames.getFirst());
        waitInSeconds(1);
    }

    public void verifyProductDetailPage() {
        waitFor(descriptionPDP);
        currentProductName = productNamePDP.getText();
        currentProductPrice = productPricePDP.getText();
        boolean isOutOfStock = false;
        try {
            isOutOfStock = outOfStockLabel.isDisplayed();
        } catch (Exception e) {
            isOutOfStock = false;
        }
        if (isOutOfStock){
            waitForAndAssertAllDisplayed(outOfStockLabel, priceLabel, addToCartDisabledButton);
            Assert.assertFalse(addToCartDisabledButton.isEnabled());
        } else {
            waitForAndAssertAllDisplayed(priceLabel, addToCartEnabledButton);
            Assert.assertTrue(addToCartEnabledButton.isEnabled());
        }
    }

    public String getCurrentProductName() {
        return currentProductName;
    }

    public String getCurrentProductPrice() {
        return currentProductPrice;
    }

    public void clickAddToCart() {
        try {
            if (cartIcon.isDisplayed()) {
                initialCount = Integer.parseInt(labelCount.getText());
            }
        } catch (Exception e) {
            initialCount = 0;
        }

        int qtyToAdd = Integer.parseInt(qtyInput.getAttribute("value"));
        clickElement(addToCartEnabledButton);
        validateText(toastAlert,"Product added to shopping cart.");
        waitInSeconds(1);
        waitFor(cartIcon);
        int expectedCount = initialCount+ qtyToAdd;
        validateText(labelCount,String.valueOf(expectedCount));
        System.out.println("Added: " + qtyToAdd + "total:" + expectedCount);
    }

    public void clickCart() {
        clickElement(cartIcon);
    }

    public void verifyCheckout1() {
        waitForAndAssertAllDisplayed(checkoutIndicator, continueShoppingButton, proceedCheckoutButton1);
    }

    public void verifyAddedProduct() {
       String expectedName = getCurrentProductName().trim();
       String expectedPrice = getCurrentProductPrice().replace("$", "");
       String actualName =  productTitleCheckout.getText().trim();
       String actualPrice = productPriceCheckout.getText().replace("$", "");
       Integer productQty = Integer.parseInt(productQuantity.getAttribute("value"));
       String totalString = totalProductPrice.getText().replace("$", "");

       Assert.assertEquals(expectedPrice, actualPrice,"Error, expected: "+expectedPrice+" but get actual: "+actualPrice);
       Assert.assertEquals(expectedName, actualName,"Error, expected: "+expectedName+" but get actual: "+actualName);

       Double total = Double.parseDouble(actualPrice)*productQty;
       Assert.assertEquals(Double.parseDouble(totalString), total, 0.01);
       System.out.println("Verified: " + actualName + " | $" + actualPrice + " x " + productQty + " = $" + total);

    }

    public void clickProceed1() {
        clickElement(proceedCheckoutButton1);
        waitInSeconds(2);
    }

    public void  verifyCheckout2() {
        String greeting = "Hello "+ Global_Vars.FIRST_NAME+" "+ Global_Vars.LAST_NAME + ", you are already logged in. You can proceed to checkout.";
        waitForAndAssertAllDisplayed(checkoutIndicator, proceedCheckoutButton2, greetingText);
        Assert.assertEquals(greetingText.getText(),greeting);
    }

    public void clickProceed2() {
        clickElement(proceedCheckoutButton2);
        waitInSeconds(2);
    }

    public void verifyCheckout3() {
        waitForAndAssertAllDisplayed(checkoutIndicator, proceedCheckoutButton3, billingAddressTitle, streetInput, cityInput, stateInput, countryInput, postalCodeInput);
        String actualStreet = streetInput.getAttribute("value");
        String actualCity = cityInput.getAttribute("value");
        String actualState = stateInput.getAttribute("value");
        String actualCountry = countryInput.getAttribute("value");
        String actualPostalCode = postalCodeInput.getAttribute("value");
        Assert.assertEquals(actualStreet, Global_Vars.STREET);
        Assert.assertEquals(actualCity, Global_Vars.CITY);
        Assert.assertEquals(actualState, Global_Vars.STATE);
        Assert.assertEquals(actualCountry, Global_Vars.COUNTRYCODE);
        Assert.assertEquals(actualPostalCode, Global_Vars.POSTALCODE);
    }

    public void clickProceed3() {
        clickElement(proceedCheckoutButton3);
        waitInSeconds(2);
    }

    public void verifyCheckout4() {
        waitForAndAssertAllDisplayed(checkoutIndicator, paymentTitle, paymentDropdown, confirmButtonDisabled);
    }

    public void selectPayment(String selectedPayment) {
        paymentType = selectedPayment;
        waitFor(paymentDropdown);
        selectOption(paymentDropdown, paymentType);
        waitInSeconds(2);
    }

    public String getPaymentType(){
        return paymentType;
    }

    public void verifyPaymentField(){
        switch (paymentType){
            case  "Bank Transfer"-> waitForAndAssertAllDisplayed(inputBankName, inputAccountName , inputAccountNumber, confirmButtonDisabled);
            case  "Cash on Delivery"-> waitFor(confirmButtonEnabled);
            case  "Credit Card"-> waitForAndAssertAllDisplayed(inputCCNumber, inputExpDate, inputCVV, inputCardHolderName, confirmButtonEnabled);
            case  "Buy Now Pay Later"-> waitForAndAssertAllDisplayed(installmentsDropdown, confirmButtonDisabled);
            case  "Gift Card"-> waitForAndAssertAllDisplayed(inputGiftCardNumber, inputValidationCode, confirmButtonDisabled);
        }
    }

    public void entersPaymentData() {
        switch (paymentType){
            case  "Bank Transfer":
                sendKeys(inputBankName, Global_Vars.BANK_NAME);
                sendKeys(inputAccountName, Global_Vars.FIRST_NAME+" "+Global_Vars.LAST_NAME);
                sendKeys(inputAccountNumber, generateRandomNumber(16));
                assertNotDisplayed(confirmButtonDisabled);
                waitFor(confirmButtonEnabled);
                break;
            case  "Cash on Delivery":
                waitInSeconds(1);
                break;
            case  "Credit Card":
                sendKeys(inputCCNumber, Global_Vars.CC_NUMBER);
                sendKeys(inputExpDate, Global_Vars.EXP_DATE);
                sendKeys(inputCVV, generateRandomNumber(3));
                sendKeys(inputCardHolderName, Global_Vars.FIRST_NAME+" "+Global_Vars.LAST_NAME);
                assertNotDisplayed(confirmButtonDisabled);
                waitFor(confirmButtonEnabled);
                break;
            case  "Buy Now Pay Later":
                waitFor(installmentsDropdown);
                selectOption(installmentsDropdown, "3 Monthly Installments");
                waitInSeconds(2);
                assertNotDisplayed(confirmButtonDisabled);
                waitFor(confirmButtonEnabled);
                break;
            case  "Gift Card":
                sendKeys(inputGiftCardNumber, "TEST"+generateRandomNumber(8));
                sendKeys(inputValidationCode, generateRandomNumber(5));
                assertNotDisplayed(confirmButtonDisabled);
                waitFor(confirmButtonEnabled);
                break;
        }
    }


    public void clickConfirm() {
        clickElement(confirmButtonEnabled);
    }

    public void verifySuccessMessage() {
        waitFor(successText);
        validateText(successText, "Payment was successful");
        waitFor(confirmButtonEnabled);
    }

    public void verifySuccessCheckout() {
        waitFor(orderConfirmation);
        currentInvoiceNumber = invoiceNumber.getText();
        validateText(orderConfirmation,"Thanks for your order! Your invoice number is " + currentInvoiceNumber + ".");
    }

    public String getInvoiceNumber() {
        return currentInvoiceNumber;
    }

    public void verifyInvoiceNumber() {
        validateText(invoiceNumber,"INV-");
    }

    public void navigatesToMyInvoice() {
        clickElement(accountMenu);
        clickElement(myInvoicesMenu);
        waitInSeconds(2);
    }

    public void verifyMyInvoicePage() {
        waitForAndAssertAllDisplayed(myInvoiceNumberLabel, myInvoicePriceLabel, myInvoiceDetailsButton);
        validateText(myInvoiceNumberLabel,currentInvoiceNumber);
    }

    public void navigatesToDetailsPage() {
        clickElement(myInvoiceDetailsButton);
        waitInSeconds(2);
    }

    public void verifyDetailsPage() {
        waitForAndAssertAllDisplayed(detailInvoiceNumber, detailPaymentMethod, detailProductName);
        String expectedInvoiceNumber = currentInvoiceNumber;
        String actualInvoiceNumber = detailInvoiceNumber.getAttribute("value");
        Assert.assertEquals(expectedInvoiceNumber, actualInvoiceNumber);

        String actualPaymentMethod = detailPaymentMethod.getAttribute("value");
        Assert.assertEquals(paymentType,actualPaymentMethod);

        String actualProductName = detailProductName.getText().trim();
        Assert.assertEquals(currentProductName, actualProductName);

    }
}
