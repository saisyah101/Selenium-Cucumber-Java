package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.Auth_PO;
import pageObjects.Base_PO;

public class Auth_Steps extends Base_PO {
    private Auth_PO auth_po;

    public Auth_Steps(Auth_PO auth_po){this.auth_po = auth_po;}

    private String generatedEmail;

    private String password = "Autopass!888";

    @Given("User accesses the Toolshop homepage")
    public void user_accesses_the_toolshop_homepage() {
        auth_po.navigateToHomepage();
        auth_po.validateHomepageLoaded();
    }

    @And("User accesses the Toolshop login page")
    public void user_accesses_the_toolshop_login_page() {
        auth_po.goToLoginPage();
    }

    @When("User clicks on register your account")
    public void user_clicks_register_your_account() {
        auth_po.goToRegisterPage();
        auth_po.validateRegisterForm();
    }

    @And("User enters valid first name")
    public void user_enters_valid_first_name() {
        auth_po.setFirstName("Test");
    }

    @And("User enters valid last name")
    public void user_enters_valid_last_name() {
        auth_po.setLastName("Automation");
    }

    @And("User enters valid date of Birth")
    public void user_enters_valid_date_of_birth() {
        auth_po.setDob("1999-05-25");
    }

    @And("User enters valid street")
    public void user_enters_valid_street() {
        auth_po.setStreet("Lorem Ipsum Street "+generateRandomNumber(2));
    }

    @And("User enters valid postal code")
    public void user_enters_valid_postal_code() {
        auth_po.setPostalCode(generateRandomNumber(5));
    }

    @And("User enters valid city")
    public void user_enters_valid_city() {
        auth_po.setCity("Leinster");
    }

    @And("User enters valid state")
    public void user_enters_valid_state() {
        auth_po.setState("Dublin");
    }

    @And("User selects valid country")
    public void user_selects_valid_country() {
        auth_po.setCountry("Ireland");
    }

    @And("User enters valid phone number")
    public void user_enters_valid_phone_number() {
        auth_po.setPhone("353"+ generateRandomNumber(7));
    }

    @And("User enters valid email address")
    public void user_enters_valid_email_address() {
        generatedEmail = "automationtest"+ generateRandomNumber(3)+"@mailinator.com";
        auth_po.setEmail(generatedEmail);
        System.out.println(generatedEmail);
    }

    @And("User enters valid password")
    public void user_enters_valid_password() {
        auth_po.setPassword(password);
    }

    @And("User clicks on the register button")
    public void user_clicks_on_the_register_button() {
        auth_po.submitRegister();
    }

    @And("User successfully registered directs to login page")
    public void user_successfully_registered() {
        auth_po.validateLoginPage();
    }

    @And("User enters valid email login")
    public void user_enters_valid_email_login() {
        auth_po.setEmail(generatedEmail);
    }

    @And("User clicks on the login button")
    public void user_clicks_login_button() {
        auth_po.submitLogin();
    }

    @Then("User successfully login")
    public void user_successfully_login() {
        auth_po.validateAccountPage();
    }

    @When("User is already registered")
    public void user_is_already_registered(){
        generatedEmail = "automationtest"+ generateRandomNumber(3)+"@mailinator.com";
        auth_po.register(generatedEmail);
    }

    @And("User enters valid email")
    public void user_enters_valid_email_() {
        auth_po.setEmail(generatedEmail);
    }

    @And("User successfully login directs to account page")
    public void user_verify_account_page() {
        auth_po.validateAccountPage();
    }

    @And("User clicks logout")
    public void user_clicks_signout() {
        auth_po.logout();
    }

    @Then("User successfully logout")
    public void user_successfully_logout() {
        auth_po.validateLogout();
    }

    @When("User enters invalid email {string}")
    public void user_enters_invalid_email(String email){
        auth_po.setEmail(email);
    }

    @And("User enters invalid password {string}")
    public void user_enters_invalid_password(String password){
        auth_po.setPassword(password);
    }

    @Then("Verify error message {string}")
    public void verify_error_message(String expectedError){
        auth_po.validateErrorMessage(expectedError);
    }

    @When("User is logged in")
    public void user_is_logged_in(){
        generatedEmail = "automationtest"+ generateRandomNumber(3)+"@mailinator.com";
        auth_po.registerAndLogin("353"+ generateRandomNumber(7),generatedEmail);
        System.out.println(generatedEmail);
    }

    @And("User accesses the homepage")
    public void user_accesses_homepage_logged_in(){
        auth_po.navigateToHomepage();
        auth_po.validateHomepageLoggedIn();
    }

}
