package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class Product_PO extends Base_PO{

    @FindBy(id="search-query")
    private WebElement searchQuery;

    @FindBy(xpath = "//button[@class='btn btn-secondary']")
    private WebElement searchButton;

    @FindBy(css = "[data-test='search-caption']")
    private WebElement searchCaptionElement;

    @FindBy(id="email-error")
    private WebElement errorEmail;

    @FindBy(css = "h5[data-test='product-name']")
    private List<WebElement> productNames;

    @FindBy(xpath = "//button[@class='btn btn-warning']")
    private WebElement resetButton;

    @FindBy(xpath = "//h5[contains(.,'Combination Pliers')]")
    private WebElement productDefaultPlier;

    @FindBy(css = ".ngx-slider-pointer-min")
    private WebElement sliderMin;

    @FindBy(css = ".ngx-slider-pointer-max")
    private WebElement sliderMax;

    @FindBy(css = ".ngx-slider-full-bar")
    private WebElement fullSlider;

    @FindBy(xpath = "(//h5[@data-test='product-name'])[1]")
    private WebElement firstProductName;

    @FindBy(xpath = "(//h5[@data-test='product-name'])[last()]")
    private WebElement lastProductName;

    @FindBy(xpath = "//select[@class='form-select']")
    private WebElement sortDropdown;

    @FindBy(css = "span[data-test='product-price']")
    private List<WebElement> productPrices;

    @FindBy(xpath = "//a[@class='card']")
    private List<WebElement> productCards;

    @FindBy(xpath = "//span[@class='eco-badge position-absolute top-0 end-0 m-2']")
    private List<WebElement> ecoBadges;

    @FindBy(xpath = "//div[@class='price-section']")
    private WebElement priceLabel;

    @FindBy(xpath = "//button[@id='btn-add-to-cart']")
    private WebElement addToCartEnabledButton;

    @FindBy(xpath = "//button[@id='btn-add-to-favorites']")
    private WebElement addToFavButton;

    @FindBy(css = ".text-danger")
    private WebElement outOfStockLabel;

    @FindBy(xpath = "//button[@id='btn-add-to-cart'] and @disabled")
    private WebElement addToCartDisabledButton;

    @FindBy(xpath = "//p[@id='description']")
    private WebElement descriptionPDP;

    @FindBy(css = "#toast-container")
    private WebElement toastAlert;

    @FindBy(xpath = "//a[@id='menu']")
    private WebElement accountMenu;

    @FindBy(xpath = "//a[.='My favorites']")
    private WebElement myFavMenu;

    @FindBy(xpath = "//h1[.='Favorites']")
    private WebElement favPageTitle;

    @FindBy(xpath = "//p[@class='card-text']")
    private WebElement descFav;

    @FindBy(xpath = "//h5")
    private List<WebElement> productTitleFav;

    @FindBy(xpath = "//h1")
    private WebElement productNamePDP;

    @FindBy(xpath = "//button[@class='btn-danger btn mb-3']")
    private WebElement removeFavButton;

    @FindBy(xpath = "//div[@class='ng-star-inserted']")
    private WebElement emptyFavText;


    private String currentProductName;
    private Integer initialFavCount;

    public Product_PO(){
        super();
    }

    public void setQuery(String query) {
        sendKeys(searchQuery, query);
    }

    public void clickSearch() {
        clickElement(searchButton);

    }

    public void searchCaptionDisplayed(String query) {
        String expectedCaption = "Searched for: " + query;
        validateText(searchCaptionElement, expectedCaption);

    }

    public void validateSearchResults (String query) {
        waitInSeconds(3);
        List<String> names = getTextFromElements(productNames);
        System.out.println("DEBUG: Found " + names.size() + " products");
        System.out.println("DEBUG: Product names: " + names);

        assertListNotEmpty(names, "Should have at least one product");

        assertAllContain(names, query, "Product Name");

        System.out.println("Found " +names.size() + " products matching:" + query);
    }

    public void clickReset() {
        clickElement(resetButton);
    }

    public void searchCaptionNotDisplayed() {
        assertNotDisplayed(searchCaptionElement);
    }

    public void defaultProductDisplayed() {
        waitInSeconds(4);
        waitFor(productDefaultPlier);
    }

    public void setMinPriceSlider(String targetValue) {
        moveSlider(sliderMin, fullSlider, targetValue);
    }

    public void setMaxPriceSlider(String targetValue) {
        moveSlider(sliderMax, fullSlider, targetValue);
    }

    public String getMinPriceValue() {
        waitFor(sliderMin);
        return getSliderValue(sliderMin);
    }

    public String getMaxPriceValue() {
        waitFor(sliderMax);
        return getSliderValue(sliderMax);
    }

    public void assertMinProductName(String productName){
        validateText(firstProductName, productName);
    }

    public void assertMaxProductName(String productName){
        validateText(lastProductName, productName);
    }

    public void selectSortOption(String sortOption) {
        waitFor(sortDropdown);
        selectOption(sortDropdown, sortOption);
        waitInSeconds(2);
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : productNames) {
            waitFor(element);
            names.add(element.getText().toLowerCase().trim());
        }
        System.out.println("Product names:" + names);
        return names;
    }

    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement element : productPrices) {
            waitFor(element);
            String priceText = element.getText().trim();
            priceText = priceText.replaceAll("[^0-9.]", "");
            if (!priceText.isEmpty()) {
                prices.add(Double.parseDouble(priceText));
            }
        }
        System.out.println("Product prices:" + prices);
        return prices;
    }

    public void verifyProductSorted(String sortType, String sortOrder) {
        boolean isSorted = false;
        String actualData = "";

        switch (sortOrder.toLowerCase()) {
            case "ascending":
            case "asc":
                switch (sortType.toLowerCase()) {
                    case "name":
                        List<String> nameAsc = getProductNames();
                        assertListNotEmpty(nameAsc, "Product names should not be empty");
                        isSorted = isListSortedAsc(nameAsc);
                        actualData = nameAsc.toString();
                        break;
                    case "price":
                        List<Double> priceAsc = getProductPrices();
                        assertListNotEmpty(priceAsc, "Product prices should not be empty");
                        isSorted = isListSortedAsc(priceAsc);
                        actualData = priceAsc.toString();
                        break;
                    default:
                        Assert.fail("Unknown sort type: " + sortType);
                }
                break;

            case "descending":
            case "desc":
                switch (sortType.toLowerCase()) {
                    case "name":
                        List<String> nameDesc = getProductNames();
                        assertListNotEmpty(nameDesc, "Product names should not be empty");
                        isSorted = isListSortedDesc(nameDesc);
                        actualData = nameDesc.toString();
                        break;
                    case "price":
                        List<Double> priceDesc = getProductPrices();
                        assertListNotEmpty(priceDesc, "Product prices should not be empty");
                        isSorted = isListSortedDesc(priceDesc);
                        actualData = priceDesc.toString();
                        break;
                    default:
                        Assert.fail("Unknown sort order: " + sortOrder);
                }

                Assert.assertTrue(isSorted, "Product Should be sorted by " + sortType + " " + sortOrder + ". Actual: " + actualData);
        }
    }

    public int getProductCount() {
        int count = productCards.size();
        return count;
    }

    public void selectCategoryFilter(String category) {
        selectCheckbox(category);
        waitInSeconds(2);
    }

    public void verifySelectedCategory(String category) {
        isCheckboxSelected(category);
        scrollToElement(sortDropdown);
    }


    public void validateProductsContainKeyword (String query) {
        waitInSeconds(3);
        List<String> names = getTextFromElements(productNames);
        System.out.println("DEBUG: Found " + names.size() + " products");
        System.out.println("DEBUG: Product names: " + names);

        assertListNotEmpty(names, "Should have at least one product");

        assertAllContain(names, query, "Product Name");

        System.out.println("Found " +names.size() + " products matching:" + query);
    }

    public List<String> getAllProductNames() {
        List<String> names = getTextFromElements(productNames);
        System.out.println("Product names: " + names);
        return names;
    }

    public void verifyProductsExist(String... expectedProducts) {
        List<String> actualNames = getAllProductNames();
        assertListNotEmpty(actualNames,"Product list should not be empty");

        List<String> notFound = new ArrayList<>();

        for (String expected: expectedProducts) {
            boolean found = false;
            for (String actual : actualNames) {
                if (actual.equalsIgnoreCase(expected.trim().toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                notFound.add(expected);
            }
        }
        Assert.assertTrue(notFound.isEmpty(),
                "Product not found: " + notFound + ". Actual: " +actualNames);
    }


    public void verifyProductsExistFromString (String productString) {
        String[] products = productString.split(",");
        verifyProductsExist(products);
    }

    public void verifyProductNotDisplayed(String unexpected) {
        List<String> actualNames = getAllProductNames();
        for (String name : actualNames) {
            Assert.assertFalse(name.toLowerCase().contains(unexpected.toLowerCase()));
        }

        System.out.println("No products contain keyword: " + unexpected);
    }

    public void ecobadgeDisplayed() {
        waitFor(ecoBadges.getFirst());
        int productsCount = getProductCount();
        int badgesCount = ecoBadges.size();
        Assert.assertEquals(badgesCount,productsCount);
    }

    public void goToProductDetailPage(WebElement product) {
        waitFor(product);
        clickElement(product);
    }

    public void goToPdpFirstProduct() {
        goToProductDetailPage(productNames.getFirst());
        waitInSeconds(2);
    }

    public void verifyProductDetailPage() {
        waitFor(descriptionPDP);
        currentProductName = productNamePDP.getText();
        boolean isOutOfStock = false;
        try {
            isOutOfStock = outOfStockLabel.isDisplayed();
        } catch (Exception e) {
            isOutOfStock = false;
        }
        if (isOutOfStock){
            waitForAndAssertAllDisplayed(outOfStockLabel, priceLabel, addToCartDisabledButton, addToFavButton);
            Assert.assertFalse(addToCartDisabledButton.isEnabled());
        } else {
            waitForAndAssertAllDisplayed(priceLabel, addToCartEnabledButton,addToFavButton);
            Assert.assertTrue(addToCartEnabledButton.isEnabled());
        }
    }

    public String getCurrentProductName() {
        return currentProductName;
    }

    public void clickAddToFav() {
        clickElement(addToFavButton);
    }

    public void errorAddFav() {
        validateText(toastAlert,"Unauthorized, can not add product to your favorite list");
    }

    public void successAddFav() {
        validateText(toastAlert,"Product added to your favorites list.");
    }

    public void navigateToFav() {
        waitInSeconds(2);
        clickElement(accountMenu);
        clickElement(myFavMenu);
        waitInSeconds(1);
    }

    public void verifyFavPage() {
        waitForAndAssertAllDisplayed(favPageTitle,descFav,removeFavButton);
        initialFavCount = productTitleFav.size();
    }

    public void verifyAddedFavProduct() {
        validateText(productTitleFav.getFirst(), currentProductName);
    }

    public void removeProductFav() {
        clickElement(removeFavButton);
        waitInSeconds(5);
    }

    public void verify_product_removed() {
        waitInSeconds(2);
        int currentCount = productTitleFav.size();
        int expectedCount = initialFavCount - 1;
        System.out.println("Product Fav Count: "+currentCount);
        Assert.assertEquals(currentCount,expectedCount);
    }

    public void verify_empty_fav() {
        waitFor(emptyFavText);
        validateText(emptyFavText, "There are no favorites yet. In order to add favorites, please go to the product listing and mark some products as your favorite.");
    }

}
