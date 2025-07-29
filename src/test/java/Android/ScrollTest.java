package Android;

import Base.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
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

public class ScrollTest extends BaseTest {

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("appium:app", System.getProperty("user.dir") + "/apps/ApiDemos-Debug.apk");
        initDriver(caps);
    }

    private void scrollVertical(double startRatio, double endRatio) {
        Dimension size = driver.manage().window().getSize();
        int centerX = size.width / 2;
        int startY = (int) (size.height * startRatio);
        int endY = (int) (size.height * endRatio);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scroll = new Sequence(finger, 1);

        scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, startY));
        scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        scroll.addAction(finger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), centerX, endY));
        scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(scroll));
    }

    @Test(description = "Scrolls down the Views menu and taps on Lists")
    public void scrollTest() {
        WebElement views = driver.findElement(AppiumBy.accessibilityId("Views"));
        views.click();

        scrollVertical(0.8, 0.2); // scroll down

        WebElement lists = driver.findElement(AppiumBy.accessibilityId("Lists"));
        lists.click();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
