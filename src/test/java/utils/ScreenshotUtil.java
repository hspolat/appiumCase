package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.ByteArrayInputStream;

public class ScreenshotUtil {
    public static void attachToAllure(String name) {
        try {
            if (DriverFactory.getDriver() != null) {
                byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(name, "image/png",
                        new ByteArrayInputStream(screenshot), "png");
            }
        } catch (Exception e) {
            System.err.println("Screenshot alınamadı: " + e.getMessage());
        }
    }
}