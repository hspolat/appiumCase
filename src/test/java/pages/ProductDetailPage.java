package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class ProductDetailPage extends BasePage {

    private final By goToProductBtn = AppiumBy.id("com.akakce.akakce:id/detailBtnLayout");
    private final By goToSellerBtn  = AppiumBy.accessibilityId("Satıcıya Git");

    public ProductDetailPage() { super(); }

    public ProductDetailPage clickGoToProduct() {
        scrollToCenter(goToProductBtn);
        waitForClickability(goToProductBtn).click();
        return this;
    }

    public boolean isSellerButtonDisplayed() {
        scrollToCenter(goToSellerBtn);
        return waitForVisibility(goToSellerBtn).isDisplayed();
    }
}