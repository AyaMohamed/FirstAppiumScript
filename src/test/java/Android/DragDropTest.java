package Android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;

public class DragDropTest {

    private AndroidDriver driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:platformVersion", "13"); // change to your actual version
        capabilities.setCapability("appium:deviceName", "Galaxy Note 10 Lite"); // or emulator name
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:udid", "RF8N327PL0Y"); // replace with actual UDID
        capabilities.setCapability("appium:app", System.getProperty("user.dir") + "/apps/ApiDemos-Debug.apk");
        capabilities.setCapability("appium:appWaitActivity", "io.appium.android.apis.*");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void dragDropTest() {
        // Open Views
        WebElement views = driver.findElement(AppiumBy.accessibilityId("Views"));
        views.click();

        // Tap on Drag and Drop
        WebElement dragDrop = driver.findElement(AppiumBy.accessibilityId("Drag and Drop"));
        dragDrop.click();

        // Locate drag and drop elements
        WebElement drag = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement drop = driver.findElement(By.id("io.appium.android.apis:id/drag_dot_2"));

        // Perform drag and drop using W3C actions
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragNDrop = new Sequence(finger, 1);

        int startX = drag.getLocation().getX() + drag.getSize().getWidth() / 2;
        int startY = drag.getLocation().getY() + drag.getSize().getHeight() / 2;

        int endX = drop.getLocation().getX() + drop.getSize().getWidth() / 2;
        int endY = drop.getLocation().getY() + drop.getSize().getHeight() / 2;

        dragNDrop.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        dragNDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), endX, endY));
        dragNDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(dragNDrop));
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
