package Android;

import Base.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.clipboard.ClipboardContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class ClipboardTest extends BaseTest {

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:app", System.getProperty("user.dir") + "/apps/selendroid.apk");
        initDriver(capabilities);
    }

    @Test
    public void clipboardTest() {
        String inputText = "Clipboard test";
        // Set clipboard content
        driver.setClipboardText(inputText);
        // Locate input field and paste clipboard text
        WebElement nameField = driver.findElement(AppiumBy.accessibilityId("my_text_fieldCD"));
        nameField.clear();
        nameField.sendKeys(driver.getClipboardText());
        Assert.assertEquals(nameField.getText(), inputText);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
