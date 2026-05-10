package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.DriverFactory;

import java.io.ByteArrayInputStream;

public class Hooks {

    @Before
    public void setup() {
        DriverFactory.initializeDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                if (DriverFactory.driver != null) {
                    byte[] screenshot = ((TakesScreenshot) DriverFactory.driver).getScreenshotAs(OutputType.BYTES);

                    Allure.addAttachment(
                            "Hata Anı Ekran Görüntüsü",
                            "image/png",
                            new ByteArrayInputStream(screenshot),
                            "png"
                    );
                }
            } catch (Exception e) {
                System.err.println("Allure ekran görüntüsü eklenirken hata oluştu: " + e.getMessage());
            }
        }

        DriverFactory.quitDriver();
    }
}