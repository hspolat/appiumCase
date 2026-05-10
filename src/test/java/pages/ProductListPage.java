package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductListPage extends BasePage {
    private final By filterButton = AppiumBy.id("com.akakce.akakce:id/filter");
    private final By sortButton = AppiumBy.id("com.akakce.akakce:id/sortText");
    private final By productContainer = AppiumBy.id("com.akakce.akakce:id/cell_product_layout");
    private final By productCell = AppiumBy.id("com.akakce.akakce:id/cell_product_layout");

    public ProductListPage(AndroidDriver driver) { super(driver); }

    public ProductDetailPage selectProductByIndex(int index) {
        // setMaxSearchSwipes(10) -> 10 kereden fazla kaydırma, dur demek.
        String scrollableExpression = "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                ".setMaxSearchSwipes(10)" +
                ".scrollIntoView(new UiSelector().resourceId(\"com.akakce.akakce:id/cell_product_layout\").instance(" + (index - 1) + "))";

        driver.findElement(AppiumBy.androidUIAutomator(scrollableExpression)).click();
        return new ProductDetailPage(driver);
    }

    public FilterPage openFilter() {
        scrollIntoViewById("com.akakce.akakce:id/filter").click();
        return new FilterPage(driver);
    }

    public ProductListPage sortBy(String sortType) {
        waitForClickability(sortButton).click();
        scrollIntoViewByText(sortType).click();
        return this;
    }
}