package pageObjects;

import driver.DriverFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Global_Vars;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Base_PO {
    public Base_PO(){
        PageFactory.initElements(getDriver(), this);
    }

    public WebDriver getDriver(){
        return DriverFactory.getDriver();
    }

    public void navigateToURL(String url){
        getDriver().get(url);
    }

    public String generateRandomNumber(int length){
        return RandomStringUtils.random(length, 0, 0, false, true, "0123456789".toCharArray(), new Random());
    }

    //generate random lowercase string
    public String generateRandomLowercase(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }

    //input text to element by id/xpath
    public void sendKeys(By by, String textToType){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Global_Vars.DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(by)).sendKeys(textToType);
    }

    //input text to element by any identifier
    public void sendKeys(WebElement element, String textToType){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Global_Vars.DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(textToType);
    }

    //click element
    public void clickElement(By by){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Global_Vars.DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    //click element
    public void clickElement(WebElement element){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Global_Vars.DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    //validate alert text
    public void validateAlert(String text){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Global_Vars.DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = getDriver().switchTo().alert().getText();
        Assert.assertEquals(alertText, text);
    }

    //validate text of element
    public void validateText(WebElement element, String expectedText){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Global_Vars.DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.visibilityOf(element));
        String actualText = element.getText();
        Assert.assertTrue(actualText.contains(expectedText),"Expected: "+expectedText+"but got "+actualText);
    }

    //wait for element
    public void waitFor(By by){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Global_Vars.DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    //wait for element to be displayed
    public void waitFor(WebElement element){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Global_Vars.DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    //verify element is not displayed
    public void assertNotDisplayed(WebElement element) {
        try {
            Assert.assertFalse(element.isDisplayed(),
                    "Element should not be displayed");
        } catch (Exception e) {
            Assert.assertTrue(true, "Element not found - OK");
        }    }

    //wait and verify all elements are displayed
    public void waitForAndAssertAllDisplayed(WebElement... elements) {
        for (WebElement element : elements) {
            waitFor(element);
            Assert.assertTrue(element.isDisplayed(), "Element should be displayed");
        }
    }

    //wait and verify all elements are not displayed
    public void waitForAndAssertAllNotDisplayed(WebElement... elements) {
        for (WebElement element : elements) {
            try {
                Assert.assertFalse(element.isDisplayed(),
                        "Element should not be displayed");
            } catch (Exception e) {
                Assert.assertTrue(true, "Element not found - OK");
            }
        }
    }

    //add delays in seconds
    public void waitInSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //get text from List of elements
    public List<String> getTextFromElements(List<WebElement> elements){
        List<String> texts = new ArrayList<>();
        for (WebElement element : elements) {
            waitFor(element);
            texts.add(element.getText().toLowerCase());
        }
        return texts;
    }

    //assert list is not empty
    public void assertListNotEmpty(List<?> list, String message) {
        Assert.assertFalse(list.isEmpty(), message);
    }

    //assert List of items contains expected text
    public void assertAllContain(List<String> items, String keyword, String errorMessage) {
        for (String item: items) {
            Assert.assertTrue(item.contains(keyword.toLowerCase()),
                    errorMessage + ": '"+ item + "'should contain '" + keyword + "'");
        }
    }

    //move slider
    public void moveSlider(WebElement slider, WebElement fullSlider, String targetValue){
        waitFor(slider);

        String minAttr = slider.getAttribute("aria-valuemin");
        String maxAttr = slider.getAttribute("aria-valuemax");
        String currentAttr = slider.getAttribute("aria-valuenow");

        int min = Integer.parseInt(minAttr);
        int max = Integer.parseInt(maxAttr);
        int current = Integer.parseInt(currentAttr);
        int target = Integer.parseInt(targetValue);

        if (current==target) {
            System.out.println("Slider already at target:" + target);
            return;
        }
        int sliderWidth = fullSlider.getSize().getWidth();
        int range = max - min;
        int valueChange = target - current;

        double pixelPerValue = (double) sliderWidth/range;
        int pixelOffset = (int) Math.round(valueChange * pixelPerValue);

        int handleCompensation = 0;
        if (pixelOffset > 0) {
            handleCompensation = -8;
        } else if (pixelOffset < 0) {
            handleCompensation = 15;
        }
        pixelOffset += handleCompensation;

        Actions actions = new Actions(getDriver());
        actions.clickAndHold(slider)
                .moveByOffset(pixelOffset, 0)
                .release()
                .perform();

        waitInSeconds(2);

        String newValue = slider.getAttribute("aria-valuenow");
        int actualValue = Integer.parseInt(newValue);
        int difference = Math.abs(actualValue - target);

        System.out.println("Result: " + newValue + " (expected: " + target + ")");

        if (difference <= 1) {
            System.out.println("✅ Success! (tolerance: ±1)");
        } else {
            System.out.println("⚠️ Difference: " + difference + " (might need adjustment)");
        }

    }

    //get slider value
    public String getSliderValue(WebElement slider) {
        try {
            waitFor(slider);
            String value = slider.getAttribute("aria-valuenow");
            return value != null ? value : "0";
        } catch (Exception e) {
            System.out.println("ERROR getting slider value: " + e.getMessage());
            return "0";
        }
    }

    //select dropdown option
    public void selectOption(WebElement dropdown, String text){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Global_Vars.DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.visibilityOf(dropdown));

        Select select = new Select(dropdown);
        select.selectByVisibleText(text);
        waitInSeconds(2);
    }

    //verify selected dropdown
    public String getSelectedDropdownOption(WebElement dropdown) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Global_Vars.DEFAULT_TIMEOUT));
        wait.until(ExpectedConditions.visibilityOf(dropdown));

        Select select = new Select(dropdown);
        return select.getFirstSelectedOption().getText();
    }

    //verify sorted asc
    public boolean isListSortedAsc(List<? extends Comparable> list) {
        if (list == null || list.size() <=1) {
            return true;
        }

        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                System.out.println("NOT SORTED ASCENDING");
                return false;
            }
        }
        return true;
    }

    //verify sorted desc
    public boolean isListSortedDesc(List<? extends Comparable> list) {
        if (list == null || list.size() <=1) {
            return true;
        }

        for (int i = 0; i < list.size() - 1; i++){
            if (list.get(i).compareTo(list.get(i +1)) < 0) {
                System.out.println("NOT SORTED DESCENDING");
                return false;
            }
        }
        return true;
    }

    //get prices text of list
    public List<Double> getPricesFromElements(List<WebElement> elements) {
        List<Double> prices = new ArrayList<>();

        for (WebElement element : elements) {
            waitFor(element);
            String priceText = element.getText().trim();
            priceText = priceText.replaceAll("[^0-9.]", "");

            if (!priceText.isEmpty()){
                try {
                    prices.add(Double.parseDouble(priceText));
                } catch (NumberFormatException e) {
                    System.out.println("Couldn't parse price:" + element.getText());
                }
            }
        }
        return  prices;
    }

    //get price from element
    public Double getPriceFromElement(WebElement element) {
        waitFor(element);
        String priceText = element.getText().trim();
        priceText = priceText.replaceAll("[^0-9.]","");

        try {
            return Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            System.out.println("Couldn't parse price:" + element.getText());
            return 0.0;
        }
    }

    public void scrollToElement(WebElement element) {
        try{
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            waitInSeconds(1);
            System.out.println("Scrolled to element");
        } catch (Exception e) {
            System.out.println("Couldn't scroll to element");
        }
    }

    public void scrollToElement(By locator) {
        try {
            WebElement element = getDriver().findElement(locator);
            scrollToElement(element);
        } catch (Exception e) {
            System.out.println("Element not found for scrolling: " + e.getMessage());
        }
    }

    /**
     * Scroll element into view and wait for it to be visible
     */
    public void scrollToElementAndWait(WebElement element) {
        scrollToElement(element);
        waitFor(element);
    }

    /**
     * Scroll to top of page
     */
    public void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
        waitInSeconds(1);
        System.out.println("Scrolled to top");
    }

    /**
     * Scroll to bottom of page
     */
    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo({top: document.body.scrollHeight, behavior: 'smooth'});");
        waitInSeconds(1);
        System.out.println("Scrolled to bottom");
    }

    /**
     * Scroll by specific pixels
     */
    public void scrollByPixels(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, arguments[0]);", pixels);
        waitInSeconds(1);
    }

    public boolean isElementInViewport(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (boolean) js.executeScript(
                "var elem = arguments[0];" +
                       "var rect = elem.getBoundingClientRect();" +
                       "return (rect.top >= 0 && rect.bottom <= window.innerHeight);",
                element
        );
    }

    public void scrollToElementIfNeeded(WebElement element) {
        if (!isElementInViewport(element)) {
            System.out.println("Element not in viewport, scrolling...");
            scrollToElement(element);
        } else {
            System.out.println("✅ Element already in viewport");
        }
    }

    //click checkbox by label
    public void clickCheckboxByLabel(String label) {
        String xpath = String.format("//label[contains(.,'%s')]", label);
        WebElement checkbox = getDriver().findElement(By.xpath(xpath));
        scrollToElementIfNeeded(checkbox);
        clickElement(checkbox);
        waitInSeconds(2);
    }

    //verify is checkbox selected
    public boolean isCheckboxSelected(String label) {
        try {
            String xpath = String.format("//label[contains(text(), '%s')]//input[@type='checkbox']", label);
            WebElement checkbox = getDriver().findElement(By.xpath(xpath));
            scrollToElementIfNeeded(checkbox);
            return checkbox.isSelected();
        } catch (Exception e) {
            System.out.println("Checkbox not found");
            return false;
        }
    }

    //select checkbox if not selected yet
    public void selectCheckbox(String label) {
        if (!isCheckboxSelected(label)) {
            String xpath = String.format("//label[contains(.,'%s')]", label);
            WebElement checkbox = getDriver().findElement(By.xpath(xpath));

            // Scroll before clicking
            scrollToElementIfNeeded(checkbox);

            clickElement(checkbox);
            waitInSeconds(1);
            System.out.println("Selected checkbox: " + label);
        } else {
            System.out.println("Checkbox already selected: " + label);
        }
    }

    //unselect checkbox
    public void unselectCheckbox(String label) {
        if (isCheckboxSelected(label)) {
            String xpath = String.format("//label[contains(.,'%s')]", label);
            WebElement checkbox = getDriver().findElement(By.xpath(xpath));

            // Scroll before clicking
            scrollToElementIfNeeded(checkbox);

            clickElement(checkbox);
            waitInSeconds(1);
            System.out.println("Unselected checkbox: " + label);
        } else {
            System.out.println("Checkbox already unselected: " + label);
        }

    }


}
