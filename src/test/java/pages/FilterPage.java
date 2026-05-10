package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class FilterPage extends BasePage {
    private final By applyFilterBtn = AppiumBy.id("com.akakce.akakce:id/apply");

    public FilterPage() { super(); }

    public ProductListPage applyFilter(String filterText) {
        scrollInBottomSheetByText(filterText).click();
        waitForClickability(applyFilterBtn).click();
        return new ProductListPage();
    }
}