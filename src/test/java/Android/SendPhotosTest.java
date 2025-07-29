package Android;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SendPhotosTest {

    private AndroidDriver driver;
    private static final By backupBtn = By.id("onboarding_action_button");
    //android.widget.Switch[@resource-id="com.google.android.apps.photos:id/onboarding_toggle"]
    //onboarding_action_button

    private static final By touchOutsideBtn = By.id("touch_outside");
    private static final By keepOffBtn = By.id("onboarding_toggle");
    private static By photo = By.xpath("Selector: //android.view.ViewGroup[contains(@contentDescription,'Photo taken')]");
    private File img;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:platformVersion", "13"); // Change to your actual version
        capabilities.setCapability("appium:deviceName", "Galaxy Note 10 Lite");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
            capabilities.setCapability("appium:appPackage", "com.google.android.apps.photos");
        capabilities.setCapability("appium:appActivity", ".home.HomeActivity");
        capabilities.setCapability("appium:autoGrantPermissions", true);
        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
    }

    @Test
    public void sendPhoto() throws IOException {
        // Resolve image path
        File classPath = new File(System.getProperty("user.dir"));
        File imageDir = new File(classPath, "resources/images");
        img = new File(imageDir.getCanonicalFile(), "TAU-logo.png");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
        wait.until(ExpectedConditions.presenceOfElementLocated(keepOffBtn)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(backupBtn)).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(touchOutsideBtn)).click();

        // Push image file to device Pictures directory
        String androidPhotoPath = "/sdcard/Pictures";
        driver.pushFile(androidPhotoPath + "/" + img.getName(), img);

        WebElement collectionTab = driver.findElement(By.id("tab_collections"));
        collectionTab.click();

        WebElement picturesAlbum=driver.findElement(By.xpath("//android.widget.TextView[@text='Pictures']"));
        picturesAlbum.click();

        // Wait until the photo element is detected
        wait.until(ExpectedConditions.visibilityOfElementLocated(photo));
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
