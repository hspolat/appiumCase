# Akakçe Mobil Test Otomasyonu

Akakçe Android uygulaması için Java, Appium, Cucumber BDD ve Allure Report kullanılarak geliştirilmiş mobil test otomasyon projesi.

---

## Kullanılan Teknolojiler

| Teknoloji | Versiyon | Amaç |
|---|---|---|
| Java | 17 | Ana programlama dili |
| Appium | 9.2.3 | Mobil test otomasyonu |
| Selenium | 4.21.0 | WebDriver altyapısı |
| Cucumber | 7.15.0 | BDD (Davranış Güdümlü Geliştirme) |
| TestNG | 7.10.2 | Test çalıştırıcı |
| Allure | 2.24.0 | Test raporlama |
| Maven | - | Bağımlılık yönetimi |

---

## Proje Yapısı

```
src/
└── test/
    ├── java/
    │   ├── hooks/
    │   │   └── Hooks.java              # Test öncesi/sonrası aksiyonlar
    │   ├── pages/
    │   │   ├── BasePage.java           # Tüm page'lerin türediği temel sınıf
    │   │   ├── HomePage.java           # Ana sayfa
    │   │   ├── FilterPage.java         # Filtre sayfası
    │   │   ├── ProductListPage.java    # Ürün listesi sayfası
    │   │   └── ProductDetailPage.java  # Ürün detay sayfası
    │   ├── runners/
    │   │   └── TestRunner.java         # Cucumber test çalıştırıcı
    │   ├── stepDefinitions/
    │   │   └── AkakceSteps.java        # Cucumber step tanımları
    │   └── utils/
    │       ├── ConfigReader.java       # config.properties okuyucu
    │       ├── DriverFactory.java      # Appium driver yönetimi
    │       └── ScreenshotUtil.java     # Hata anı ekran görüntüsü
    └── resources/
        ├── features/
        │   └── akakce.feature          # Test senaryosu
        └── config.properties           # Cihaz ve ortam ayarları
```

---

## Test Senaryosu

```gherkin
Scenario: Search laptop with 4K filter and verify seller button
  Given User opens Akakce app
  When User searches for "Laptop"
  And User clicks on Filter button
  And User selects "4K" filter and clicks show products
  And User sorts products by "En Yüksek Fiyat"
  And User clicks on the 10. product in the results
  And User clicks on Go to Product button
  Then User verifies that Go to Seller button is displayed
```

Senaryo adım adım şunları yapar:
- Akakçe uygulamasını açar
- Arama kutusuna "Laptop" yazar ve aratır
- Filtrele butonuna tıklar, "4K" filtresini seçer
- Sonuçları En Yüksek Fiyat'a göre sıralar
- 10. ürüne tıklar ve Ürüne Git butonuna basar
- Satıcıya Git butonunun görüntülendiğini doğrular

---

## Kurulum ve Çalıştırma

### Gereksinimler

- Java 17+
- Maven
- Node.js
- Appium Server (`npm install -g appium`)
- UiAutomator2 sürücüsü (`appium driver install uiautomator2`)
- Bağlı Android cihaz veya emülatör
- Akakçe uygulaması yüklü olmalı

### 1. Cihaz Ayarlarını Yapılandır

`src/test/resources/config.properties` dosyasını kendi cihazına göre düzenle:

```properties
platformName=Android
automationName=UiAutomator2
deviceName=CIHAZ_ID         # adb devices komutuyla öğrenebilirsin
appPackage=com.akakce.akakce
appWaitActivity=com.akakce.*
explicitWait=15
appiumServerUrl=http://127.0.0.1:4723
```

### 2. Appium Server'ı Başlat

```bash
appium --keep-alive-timeout 60
```

### 3. Testleri Çalıştır

```bash
mvn test
```

### 4. Allure Raporunu Görüntüle

```bash
allure serve allure-results
```

---

## Mimari Kararlar

**Page Object Model (POM):** Her ekran ayrı bir sınıfla temsil edilir. Locator'lar ve aksiyonlar sayfa sınıflarında tutulur, test mantığı step'lerde kalır.

**BasePage:** Tüm sayfa sınıflarının türediği temel sınıf. Scroll, swipe ve bekleme metodları burada tanımlıdır; tekrar yazılmaz.

**ThreadLocal Driver:** `DriverFactory` driver'ı `ThreadLocal` ile yönetir. Paralel test çalıştırmaya hazır bir yapı sağlar.

**Explicit Wait:** Projede `Thread.sleep()` kullanılmaz. Tüm beklemeler `WebDriverWait` ile yönetilir; bu sayede testler hem güvenilir hem hızlıdır.

**Hooks:** Her test öncesinde driver başlatılır, sonrasında kapatılır. Test başarısız olursa Allure raporuna otomatik ekran görüntüsü eklenir. Android sistem izin pop-up'ları otomatik olarak kapatılır.

---

## Notlar

- Uygulama ilk açılışta bildirim izni isteyebilir — bu otomatik olarak kabul edilir.
- `noReset: true` ayarı sayesinde uygulama her testte sıfırlanmaz, oturum korunur.
- Test başarısız olduğunda Allure raporunda hata anının ekran görüntüsü bulunur.
- Geliştirme yapılırken yapay zekadan faydalanılmıştır. 
