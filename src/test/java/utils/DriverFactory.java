package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {
    private static final ThreadLocal<AndroidDriver> driverThread = new ThreadLocal<>();

    public static AndroidDriver getDriver() {
        return driverThread.get();
    }

    public static void initializeDriver() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(ConfigReader.getProperty("platformName"));
        options.setAutomationName(ConfigReader.getProperty("automationName"));
        options.setDeviceName(ConfigReader.getProperty("deviceName"));
        options.setAppPackage(ConfigReader.getProperty("appPackage"));
        options.setAppWaitActivity(ConfigReader.getProperty("appWaitActivity"));
        options.setNoReset(true);
        options.amend("appium:forceAppLaunch", true);

        try {
            driverThread.set(new AndroidDriver(
                    new URL(ConfigReader.getProperty("appiumServerUrl")), options));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Appium Server URL'i hatalı!", e);
        } catch (Exception e) {
            throw new RuntimeException("Driver başlatılamadı!", e);
        }

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThread.remove();
        }
    }
}