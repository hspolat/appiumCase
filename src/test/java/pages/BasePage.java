package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement scrollIntoViewByText(String text) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                        ".scrollIntoView(new UiSelector().text(\"" + text + "\"))"));
    }

    protected WebElement scrollInBottomSheetByText(String text) {
        int maxRetry = 6; // Panel kapanmadan yapılabilecek güvenli deneme sayısı

        while (maxRetry > 0) {
            try {
                // Element görünürse döndür
                WebElement element = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"" + text + "\")"));
                if (element.isDisplayed()) return element;
            } catch (Exception e) {
                // Element yoksa SADECE AŞAĞI kaydır (scrollForward)
                // Bu komut asla en yukarı gidip paneli kapatmaya çalışmaz.
                driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollForward()"));
                maxRetry--;
            }
        }
        throw new RuntimeException("Bottom Sheet içinde element bulunamadı: " + text);
    }

    protected WebElement scrollIntoViewById(String resourceId) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                        ".scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"))"));
    }

    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}