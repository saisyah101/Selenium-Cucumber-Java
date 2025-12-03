package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObjects.Base_PO;
import pageObjects.Product_PO;

public class ProductPage_Steps extends Base_PO {
    private Product_PO product_po;

    public ProductPage_Steps(Product_PO product_po){this.product_po = product_po;}

    private String generatedEmail;

    private String password = "Autopass!888";

    @When("User enters {string} on search bar")
    public void user_enters_search_query(String query) {
        product_po.setQuery(query);
    }

    @And("User clicks on the search button")
    public void user_clicks_search_button() {
        product_po.clickSearch();
    }

    @Then("Search caption contains valid {string}")
    public void verify_search_caption(String query) {
        product_po.searchCaptionDisplayed(query);
    }

    @And("Products matching {string} are displayed")
    public void verify_expected_search_result(String query) {
        product_po.validateSearchResults(query);
    }


    @When("User enters valid query on search bar")
    public void user_enters_valid_query() {
        product_po.setQuery("Hammer");
    }

    @And("Valid products are displayed")
    public void verify_valid_search_result() {
        String query = "Hammer";
        product_po.searchCaptionDisplayed(query);
        product_po.validateSearchResults(query);
    }


    @And("User clicks on the reset button")
    public void user_clicks_on_the_reset_button() {
        product_po.clickReset();
    }

    @Then("Default products should be displayed")
    public void default_products_should_be_displayed() {
        product_po.searchCaptionNotDisplayed();
        product_po.defaultProductDisplayed();
    }

    @When("User sets price range from {string} to {string}")
    public void user_sets_price_range(String minPrice, String maxPrice) {
        product_po.setMinPriceSlider(minPrice);
        product_po.setMaxPriceSlider(maxPrice);
    }

    @Then("Price range should be {string} to {string}")
    public void price_range_should_be_displayed(String expectedMinPrice, String expectedMaxPrice) {
        String actualMinPrice = product_po.getMinPriceValue();
        String actualMaxPrice = product_po.getMaxPriceValue();
        Assert.assertEquals(actualMinPrice, expectedMinPrice,
                "Min price mismatch! Expected: " + expectedMaxPrice + ", Got: " + actualMinPrice);
        Assert.assertEquals(actualMaxPrice, expectedMaxPrice,
                "Max price mismatch! Expected: " + expectedMaxPrice + ", Got: " + actualMaxPrice);

    }

    @And("Expected product {string} and {string} should be displayed")
    public void expected_product_should_be_displayed(String minProduct, String maxProduct) {
        product_po.assertMinProductName(minProduct);
        product_po.assertMaxProductName(maxProduct);
    }

    @When("User sorts products by {string}")
    public void user_sorts_products(String sortOption){
        product_po.selectSortOption(sortOption);
    }

    @Then("Products should be sorted by {string} in {string}")
    public void user_sorts_products(String sortType, String sortOrder){
        product_po.verifyProductSorted(sortType,sortOrder);
    }

    @When("User selects {string}")
    public void user_selects_category(String category) {
        product_po.selectCategoryFilter(category);
    }

    @And("Category {string} is selected")
    public void verify_category_selected(String category) {
        product_po.verifySelectedCategory(category);
    }

    @Then("Expected product should include {string}")
    public void expected_product_should_be_displayed(String expected) {
        product_po.verifyProductsExistFromString(expected);
    }

    @And("Unexpected product {string} should not be displayed")
    public void unexpected_product_should_not_displayed(String unexpectedProduct) {
        product_po.verifyProductNotDisplayed(unexpectedProduct);
    }

    @And("The eco badges are displayed")
    public void verify_eco_badge(){
        product_po.ecobadgeDisplayed();
    }

    @When("User navigates to product detail page")
    public void user_go_to_pdp() {
        product_po.goToPdpFirstProduct();
    }

    @And("User should see product detail page displayed correctly")
    public void user_verify_pdp_page() {
        product_po.verifyProductDetailPage();
    }

    @And("User clicks the add to favorites button")
    public void user_clicks_add_to_favorite() {
        product_po.clickAddToFav();
    }

    @Then("Valid error alert failed add to favorites list should be displayed")
    public void valid_error_alert_fav_displayed() {
        product_po.errorAddFav();
    }

    @And("User successfully added product to favorites")
    public void success_add_fav(){
        product_po.successAddFav();
    }

    @And("User navigates to My Favorites page")
    public void navigates_my_fav_page(){
        product_po.navigateToFav();
    }

    @Then("My favorites page displayed correctly")
    public void verify_my_fav_page(){
        product_po.verifyFavPage();
    }

    @And("The added favorites product should be displayed")
    public void verify_added_fav_product(){
        product_po.verifyAddedFavProduct();
    }

    @And("User adds product to favorites")
    public void adds_product_to_fav(){
        product_po.goToPdpFirstProduct();
        product_po.verifyProductDetailPage();
        product_po.clickAddToFav();
        product_po.successAddFav();
    }

    @And("User removes product from favorites")
    public void remove_product_from_fav(){
        product_po.verifyFavPage();
        product_po.verifyAddedFavProduct();
        product_po.removeProductFav();
    }

    @Then("Product should be removed successfully")
    public void verify_product_removed(){
        product_po.verify_product_removed();
        product_po.verify_empty_fav();
    }

}
