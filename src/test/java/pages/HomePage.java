package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.By;

public class HomePage extends BasePage {
    private final By searchBoxTrigger = AppiumBy.id("com.akakce.akakce:id/searchTextView");
    private final By searchBoxInput = AppiumBy.id("com.akakce.akakce:id/searchTextView");

    public HomePage(AndroidDriver driver) { super(driver); }

    public ProductListPage searchForProduct(String productName) {
        waitForClickability(searchBoxTrigger).click();
        waitForVisibility(searchBoxInput).sendKeys(productName);
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
        return new ProductListPage(driver);
    }
}