package hooks;

import io.appium.java_client.AppiumBy;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverFactory;
import utils.ScreenshotUtil;
import java.time.Duration;

public class Hooks {

    @Before
    public void setup() {
        DriverFactory.initializeDriver();
        handlePermissionPopup();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            ScreenshotUtil.attachToAllure("Hata Anı Ekran Görüntüsü");
        }
        DriverFactory.quitDriver();
    }

    private void handlePermissionPopup() {
        try {
            By allowButton = AppiumBy.id("com.android.permissioncontroller:id/permission_allow_button");
            WebDriverWait popupWait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(5));
            WebElement button = popupWait.until(ExpectedConditions.elementToBeClickable(allowButton));
            button.click();
        } catch (Exception ignored) {
            // Pop-up çıkmadıysa sessizce devam et
        }
    }
}