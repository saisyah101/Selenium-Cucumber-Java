package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Global_Vars;

public class Auth_PO extends Base_PO{
    @FindBy(xpath="//a[.='Home']")
    private WebElement homeMenu;

    @FindBy(xpath="//a[contains(.,'Categories')]")
    private WebElement categoriesMenu;

    @FindBy(xpath="//a[.='Contact']")
    private WebElement contactMenu;

    @FindBy(xpath="//a[.='Sign in']")
    private WebElement loginMenu;

    @FindBy(xpath="//h4[contains(.,'Sort')]")
    private WebElement sortText;

    @FindBy(xpath="//select[@class='form-select']")
    private WebElement sortSelection;

    @FindBy(xpath="//h4[contains(.,'Price Range')]")
    private WebElement priceRangeText;

    @FindBy(xpath="//h3[.='Login']")
    private WebElement loginText;

    @FindBy(xpath="//button[@class='google-sign-in-button']")
    private WebElement loginGoogle;

    @FindBy(xpath="//button[@class='btn btn-outline-secondary']")
    private WebElement viewPasswordButton;

    @FindBy(xpath="//h1[.='My account']")
    private WebElement myAccountText;

    @FindBy(xpath="//a[contains(.,'Favorites')]")
    private WebElement favoritesMenu;

    @FindBy(xpath="//a[contains(.,'Profile')]")
    private WebElement profileMenu;

    @FindBy(xpath="//a[contains(.,'Invoices')]")
    private WebElement invoicesMenu;

    @FindBy(xpath="//a[contains(.,'Messages')]")
    private WebElement messageMenu;

    @FindBy(xpath="//a[.='Register your account']")
    private WebElement registerText;

    @FindBy(id="first_name")
    private WebElement firstNameField;

    @FindBy(id="last_name")
    private WebElement lastNameField;

    @FindBy(id="dob")
    private WebElement dobField;

    @FindBy(id="street")
    private WebElement streetField;

    @FindBy(id="postal_code")
    private WebElement postalCodeField;

    @FindBy(id="city")
    private WebElement cityField;

    @FindBy(id="state")
    private WebElement stateField;

    @FindBy(id="country")
    private WebElement countryOption;

    @FindBy(id="phone")
    private WebElement phoneField;

    @FindBy(id="email")
    private WebElement emailField;

    @FindBy(id="password")
    private WebElement passwordField;

    @FindBy(xpath="//button[@class='btnSubmit mb-3']")
    private WebElement registerButton;

    @FindBy(xpath="//input[@class='btnSubmit']")
    private WebElement loginButton;

    @FindBy(xpath="//a[@id='menu']")
    private WebElement accountMenu;

    @FindBy(xpath="//a[.='Sign out']")
    private WebElement logoutMenu;

    @FindBy(xpath="//div[@class='help-block']")
    private WebElement errorMessage;

    @FindBy(id="password-error")
    private WebElement errorPassword;

    @FindBy(id="email-error")
    private WebElement errorEmail;

    @FindBy(xpath = "//h5[contains(.,'Combination Pliers')]")
    private WebElement productDefaultPlier;


    public Auth_PO(){
        super();
    }

    public void navigateToHomepage() {
        navigateToURL(Global_Vars.TOOLSHOP_URL);
        waitInSeconds(3);
    }

    public void navigateToRegisterPage(){
        navigateToURL(Global_Vars.TOOLSHOP_URL+"/auth/register");
    }

    public void validateHomepageLoaded() {
        waitForAndAssertAllDisplayed(priceRangeText,homeMenu,categoriesMenu,contactMenu,loginMenu);
        waitFor(productDefaultPlier);
    }

    public void goToLoginPage() {clickElement(loginMenu);}

    public void validateLoginPage() {
        waitForAndAssertAllDisplayed(loginText,loginGoogle,viewPasswordButton);
    }

    public void goToRegisterPage() {clickElement(registerText);}


    public void validateRegisterForm() {
        waitForAndAssertAllDisplayed(firstNameField,lastNameField,dobField,streetField,countryOption,phoneField,emailField,passwordField,registerButton);
    }

    public void setFirstName(String firstName){
        sendKeys(firstNameField, firstName);
    }

    public void setLastName(String lastName){
        sendKeys(lastNameField, lastName);
    }

    public void setDob(String dob) {sendKeys(dobField, dob);}

    public void setStreet(String street){
        sendKeys(streetField, street);
    }

    public void setPostalCode(String postalCode) { sendKeys(postalCodeField, postalCode);}

    public void setCity(String city) {sendKeys(cityField, city);}

    public void setState(String state) {sendKeys(stateField, state);}

    public void setCountry(String country) {sendKeys(countryOption, country);}

    public void setPhone(String phone){
        sendKeys(phoneField, phone);
    }

    public void setEmail(String email){
        sendKeys(emailField, email);
    }

    public void setPassword(String password){
        sendKeys(passwordField, password);
    }

    public void submitRegister() {clickElement(registerButton);}

    public void submitLogin() {clickElement(loginButton);}

    public void validateAccountPage() {waitForAndAssertAllDisplayed(myAccountText,favoritesMenu,profileMenu,invoicesMenu,messageMenu);}

    public void register(String generatedEmail){
        goToLoginPage();
        goToRegisterPage();
        setFirstName(Global_Vars.FIRST_NAME);
        setLastName(Global_Vars.LAST_NAME);
        setDob(Global_Vars.DOB);
        setStreet(Global_Vars.STREET);
        setPostalCode(Global_Vars.POSTALCODE);
        setCity(Global_Vars.CITY);
        setState(Global_Vars.STATE);
        setCountry(Global_Vars.COUNTRY);
        setPhone(Global_Vars.PHONE);
        setEmail(generatedEmail);
        setPassword(Global_Vars.PASSWORD);
        submitRegister();
        validateLoginPage();
    }

    public void registerAndLogin(String phone, String generatedEmail){
        navigateToRegisterPage();
        setFirstName(Global_Vars.FIRST_NAME);
        setLastName(Global_Vars.LAST_NAME);
        setDob(Global_Vars.DOB);
        setStreet(Global_Vars.STREET);
        setPostalCode(Global_Vars.POSTALCODE);
        setCity(Global_Vars.CITY);
        setState(Global_Vars.STATE);
        setCountry(Global_Vars.COUNTRY);
        setPhone(phone);
        setEmail(generatedEmail);
        setPassword(Global_Vars.PASSWORD);
        submitRegister();
        waitInSeconds(1);
        validateLoginPage();
        setEmail(generatedEmail);
        setPassword(Global_Vars.PASSWORD);
        submitLogin();
        validateAccountPage();
    }

    public void logout(){
        clickElement(accountMenu);
        clickElement(logoutMenu);
    }

    public void validateLogout(){
        waitForAndAssertAllNotDisplayed(accountMenu,logoutMenu);
    }

    public void validateErrorMessage(String expectedError){
        switch (expectedError) {
            case  Global_Vars.ERROR_GENERAL-> validateText(errorMessage, expectedError);
            case  Global_Vars.ERROR_EMAIL_REQUIRED-> validateText(errorEmail, expectedError);
            case  Global_Vars.ERROR_PASS_REQUIRED-> validateText(errorPassword, expectedError);
            case  Global_Vars.ERROR_EMAIL_INVALID-> validateText(errorEmail, expectedError);
            case  Global_Vars.ERROR_PASS_INVALID-> validateText(errorPassword, expectedError);
        }
    }

    public void validateHomepageLoggedIn() {
        waitInSeconds(3);
        waitForAndAssertAllDisplayed(homeMenu,categoriesMenu,contactMenu,accountMenu);
        waitFor(productDefaultPlier);
    }


}

