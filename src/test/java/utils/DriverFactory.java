package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {
    public static AndroidDriver driver;

    public static AndroidDriver initializeDriver() { // throws MalformedURLException kaldırıldı
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName(ConfigReader.getProperty("platformName"));
        options.setAutomationName(ConfigReader.getProperty("automationName"));
        options.setDeviceName(ConfigReader.getProperty("deviceName"));
        options.setAppPackage(ConfigReader.getProperty("appPackage"));
        options.setAppWaitActivity(ConfigReader.getProperty("appWaitActivity"));

        options.setNoReset(true);
        options.amend("appium:forceAppLaunch", true);

        try {
            driver = new AndroidDriver(
                    new URL(ConfigReader.getProperty("appiumServerUrl")),
                    options
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException("Appium Server URL'i hatalı! Lütfen config.properties dosyasını kontrol edin.", e);
        } catch (Exception e) {
            throw new RuntimeException("Appium Driver başlatılamadı. Sunucunun açık olduğundan emin olun!", e);
        }

        // Profesyonel Yaklaşım: Implicit wait'i 0 yaparak sadece Explicit wait (WebDriverWait) hakimiyeti sağlıyoruz.
        // Bu satırı silmek de aynıdır ama açıkça 0 yazmak "ben ne yaptığımı biliyorum" demektir.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // Driver'ı null'a çekmek bir sonraki testin temiz başlamasını sağlar
        }
    }
}