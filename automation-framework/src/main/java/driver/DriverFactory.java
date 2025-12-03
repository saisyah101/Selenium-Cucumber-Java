package driver;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DriverFactory {
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (webDriver.get() == null) {
            webDriver.set(createDriver());
        }
        return webDriver.get();
    }

    private static WebDriver createDriver() {
        WebDriver driver = null;
        boolean isHeadless = isHeadlessMode();

        switch (getBrowserType()) {
            case "chrome" -> {
//                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/main/java/driver/drivers/chromedriver");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("autofill.profile_enabled", false);
                prefs.put("autofill.address_enabled", false);
                prefs.put("autofill.credit_card_enabled", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("credentials_enable_service", false);

                chromeOptions.setExperimentalOption("prefs", prefs);

                chromeOptions.addArguments("--disable-save-password-bubble");
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.addArguments("--disable-notifications");

                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--disable-gpu");
                    System.out.println("Chrome Headless mode");
                } else {
                    System.out.println("Chrome Normal mode");
                }

                driver = new ChromeDriver(chromeOptions);
                break;
            }
            case "firefox" -> {
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/main/java/driver/drivers/geckodriver");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                    System.out.println("Firefox headless mode");
                } else {
                    System.out.println("Firefox normal mode");
                }

                driver = new FirefoxDriver(firefoxOptions);
                break;
            }
        }
        if (!isHeadless) {
            driver.manage().window().maximize();
        }
        return driver;
    }

    private static String getBrowserType() {
        String browserType = null;
        String browserTypeRemoteValue = System.getProperty("browserType");
        try {
            if (browserTypeRemoteValue == null || browserTypeRemoteValue.isEmpty()) {
                Properties properties = new Properties();
                FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/properties/config.properties");
                properties.load(file);
                browserType = properties.getProperty("browser").toLowerCase().trim();

            } else {
                browserType = browserTypeRemoteValue;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return browserType;
    }

    private static boolean isHeadlessMode() {
        String headlessValue = System.getProperty("headless");
        if (headlessValue != null) {
            return Boolean.parseBoolean(headlessValue);
        }

        try {
            Properties properties = new Properties();
            FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/properties/config.properties");
            properties.load(file);
            String headlessConfig = properties.getProperty("headless", "false");
            return Boolean.parseBoolean(headlessConfig);
        } catch (IOException ex) {
            return false;
        }

    }

    public static void cleanupDriver() {
        if (webDriver.get() != null) {
            webDriver.get().quit();
            webDriver.remove();
        }
    }
}
