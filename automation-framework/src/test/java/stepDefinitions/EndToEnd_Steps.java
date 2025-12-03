package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.Base_PO;
import pageObjects.EndToEnd_PO;


public class EndToEnd_Steps extends Base_PO {
    private EndToEnd_PO e2e_po;

    public EndToEnd_Steps(EndToEnd_PO e2e_po){this.e2e_po = e2e_po;}

    @When("User adds product to cart")
    public void user_adds_product() {
        e2e_po.goToPdpFirstProduct();
        e2e_po.verifyProductDetailPage();
        e2e_po.clickAddToCart();
    }

    @And("User clicks cart icon")
    public void user_clicks_cart_icon() {
        e2e_po.clickCart();
    }

    @And("User navigates to Checkout page section 1: Cart")
    public void user_navigates_to_checkout_page_1() {
        e2e_po.verifyCheckout1();
    }

    @And("Detail of added product should be displayed correctly")
    public void verify_detail_added_product() {
        e2e_po.verifyAddedProduct();

    }

    @And("User clicks proceed checkout button 1")
    public void user_clicks_proceed_checkout1() {
        e2e_po.clickProceed1();
    }

    @And("User clicks proceed checkout button 2")
    public void user_clicks_proceed_checkout2() {
        e2e_po.clickProceed2();
    }

    @And("User clicks proceed checkout button 3")
    public void user_clicks_proceed_checkout3() {
        e2e_po.clickProceed3();
    }

    @And("User navigates to Checkout page section 2: Sign In")
    public void user_navigates_to_checkout_page_2() {
        e2e_po.verifyCheckout2();
    }

    @And("User navigates to Checkout page section 3: Billing Address")
    public void user_navigates_to_checkout_page_3() {
        e2e_po.verifyCheckout3();
    }

    @And("User navigates to Checkout page section 4: Payment")
    public void user_navigates_to_checkout_page_4() {
        e2e_po.verifyCheckout4();
    }

    @And("User selects payment {string}")
    public void user_selects_payment_type(String paymentType) {
        e2e_po.selectPayment(paymentType);
    }

    @And("Valid input fields displayed correctly")
    public void verify_payment_input_field() {
        e2e_po.verifyPaymentField();
    }

    @And("User enters valid payment data")
    public void user_enters_payment_data() {
        e2e_po.entersPaymentData();
    }

    @And("User clicks Confirm button")
    public void user_clicks_confirm_button() {
        e2e_po.clickConfirm();
    }

    @And("Verify success message displayed correctly")
    public void verify_success_message() {
        e2e_po.verifySuccessMessage();
    }

    @And("User successfully checkout the product")
    public void verify_successfully_checkout() {
        e2e_po.verifySuccessCheckout();
    }

    @And("Verify invoice number displayed correctly")
    public void verify_invoice_number() {
        e2e_po.verifyInvoiceNumber();
    }

    @And("User navigates to My Invoice page")
    public void navigates_my_invoice_page() {
        e2e_po.navigatesToMyInvoice();
    }

    @And("My Invoice page displayed correctly")
    public void verify_my_invoice_page() {
        e2e_po.verifyMyInvoicePage();
    }

    @And("User navigates to Details page")
    public void navigates_details_page() {
        e2e_po.navigatesToDetailsPage();
    }

    @Then("Details page displayed correctly")
    public void verify_details_page() {
        e2e_po.verifyDetailsPage();
    }








}
