package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProductListPage extends BasePage {

    private final By filterButton = AppiumBy.id("com.akakce.akakce:id/filter");
    private final By sortButton   = AppiumBy.id("com.akakce.akakce:id/sortText");
    private final By productCell  = AppiumBy.id("com.akakce.akakce:id/cellContainer");

    public ProductListPage(AndroidDriver driver) { super(driver); }

    public ProductDetailPage selectProductByIndex(int index) {
        waitForVisibility(productCell);

        int globalCount = 0;
        int maxSwipes   = 20;

        for (int swipe = 0; swipe < maxSwipes; swipe++) {
            List<WebElement> visibleProducts = driver.findElements(productCell);

            for (int i = 0; i < visibleProducts.size(); i++) {
                int cardNumber = globalCount + i + 1;
                if (cardNumber == index) {
                    visibleProducts.get(i).click();
                    return new ProductDetailPage(driver);
                }
            }

            globalCount += visibleProducts.size();
            swipeUp();
        }

        throw new RuntimeException(index + ". ürün bulunamadı.");
    }

    public FilterPage openFilter() {
        scrollToCenter(filterButton);
        waitForClickability(filterButton).click();
        return new FilterPage(driver);
    }

    public ProductListPage sortBy(String sortType) {
        waitForClickability(sortButton).click();
        scrollIntoViewByText(sortType).click();
        return this;
    }
}