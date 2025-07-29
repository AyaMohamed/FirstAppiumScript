package Android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;

public class SwipeTest {

    private AndroidDriver driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:platformVersion", "13"); // Use your real version
        capabilities.setCapability("appium:deviceName", "Galaxy Note 10 Lite"); // or emulator
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:udid", "RF8N327PL0Y"); // Use your device ID
        capabilities.setCapability("appium:app", System.getProperty("user.dir") + "/apps/ApiDemos-Debug.apk");
        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
    }

    @Test
    public void swipeTest() {
        // Tap Views
        WebElement views = driver.findElement(AppiumBy.accessibilityId("Views"));
        views.click();

        // Tap Gallery
        WebElement gallery = driver.findElement(AppiumBy.accessibilityId("Gallery"));
        gallery.click();

        // Tap 1. Photos
        WebElement photos = driver.findElement(AppiumBy.accessibilityId("1. Photos"));
        photos.click();

        // Locate first image
        WebElement firstImage = driver.findElements(By.className("android.widget.ImageView")).get(0);

        // Swipe from right to left across the image
        int startX = firstImage.getLocation().getX() + (int)(firstImage.getSize().getWidth() * 0.9);
        int endX = firstImage.getLocation().getX() + (int)(firstImage.getSize().getWidth() * 0.1);
        int centerY = firstImage.getLocation().getY() + (firstImage.getSize().getHeight() / 2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, centerY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, centerY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
