package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.File;

public class ConfigReader {
    private static Properties properties;

    static {
        // Dosya yolunu proje ana dizinine göre dinamik hale getiriyoruz (Best Practice)
        String path = System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "test" + File.separator + "resources" +
                File.separator + "config.properties";

        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            // Hata anında testi sessizce devam ettirmek yerine
            // anlamlı bir mesajla durdurmak en profesyonel yaklaşımdır.
            throw new RuntimeException("Konfigürasyon dosyası okunamadı! Yol: " + path, e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Properties dosyasında '" + key + "' anahtarı bulunamadı!");
        }
        return value;
    }
}