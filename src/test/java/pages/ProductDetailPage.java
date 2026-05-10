package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class ProductDetailPage extends BasePage {

    private final By goToProductBtn = AppiumBy.id("com.akakce.akakce:id/detailBtn");
    private final By goToSellerBtn = AppiumBy.id("com.akakce.akakce:id/sellerBtn");

    public ProductDetailPage(AndroidDriver driver) { super(driver); }

    public ProductDetailPage clickGoToProduct() {

        waitForClickability(goToProductBtn).click();
        return this;
    }

    public boolean isSellerButtonDisplayed() {

        return waitForVisibility(goToSellerBtn).isDisplayed();
    }
}