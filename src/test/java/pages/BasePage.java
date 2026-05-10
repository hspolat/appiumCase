package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import java.time.Duration;
import java.util.Collections;

public class BasePage {
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicitWait"))));
    }

    protected WebElement scrollIntoViewByText(String text) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                        ".scrollIntoView(new UiSelector().text(\"" + text + "\"))"));
    }

    protected WebElement scrollInBottomSheetByText(String text) {
        int maxRetry = 6;
        while (maxRetry > 0) {
            try {
                WebElement element = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"" + text + "\")"));
                if (element.isDisplayed()) return element;
            } catch (Exception e) {
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

    protected void swipeUp() {
        org.openqa.selenium.Dimension size = driver.manage().window().getSize();
        int startX = size.getWidth() / 2;
        int startY = (int) (size.getHeight() * 0.75);
        int endY   = (int) (size.getHeight() * 0.30);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO,
                        PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(600),
                        PointerInput.Origin.viewport(), startX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    protected void scrollToCenter(By locator) {
        int maxSwipes = 5;
        org.openqa.selenium.Dimension screen = driver.manage().window().getSize();
        int screenCenterY = screen.getHeight() / 2;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebElement element = driver.findElement(locator);

                if (element.isDisplayed()) {
                    int elementCenterY = element.getLocation().getY() + (element.getSize().getHeight() / 2);
                    int screenQuarter  = screen.getHeight() / 4;
                    if (elementCenterY > screenQuarter && elementCenterY < screenQuarter * 3) return;
                }

                int elementCenterY = element.getLocation().getY() + (element.getSize().getHeight() / 2);
                int diff   = elementCenterY - screenCenterY;
                int startY = Math.max(50, Math.min(screenCenterY + diff, screen.getHeight() - 50));

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 1)
                        .addAction(finger.createPointerMove(Duration.ZERO,
                                PointerInput.Origin.viewport(), screen.getWidth() / 2, startY))
                        .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                        .addAction(finger.createPointerMove(Duration.ofMillis(400),
                                PointerInput.Origin.viewport(), screen.getWidth() / 2, screenCenterY))
                        .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(swipe));

            } catch (Exception ignored) {
                swipeUp();
            }
        }
    }
}