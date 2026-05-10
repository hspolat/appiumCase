package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import com.google.common.collect.ImmutableMap;

public class HomePage extends BasePage {
    private final By searchBox = AppiumBy.id("com.akakce.akakce:id/searchTextView");

    public HomePage() { super(); }

    public ProductListPage searchForProduct(String productName) {
        waitForClickability(searchBox).click();
        waitForVisibility(searchBox).sendKeys(productName);
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
        return new ProductListPage();
    }
}