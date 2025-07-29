package Android;

import Base.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Collections;

public class SwipeTest extends BaseTest {

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:app", System.getProperty("user.dir") + "/apps/ApiDemos-Debug.apk");
        initDriver(capabilities);
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

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
