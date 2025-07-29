package Android;

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

public class ClipboardTest {

    private AndroidDriver driver;
    By okButton = By.id("android:id/button1"); // Standard ID for 'OK' in Android dialogs


    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:platformVersion", "13"); // Your Galaxy Note 10 Lite's version
        capabilities.setCapability("appium:deviceName", "Galaxy Note 10 Lite");
        capabilities.setCapability("appium:udid", "RF8N327PL0Y"); // Replace with your actual ADB device ID
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:autoGrantPermissions", true); // Grants clipboard and storage permissions
        capabilities.setCapability("appium:app", System.getProperty("user.dir") + "/apps/selendroid.apk");

        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
    }

    @Test
    public void clipboardTest() {
        acceptUpdatesPrompt();
        String inputText = "Clipboard test";

        // Set clipboard content
        driver.setClipboardText(inputText);

        // Locate input field and paste clipboard text
        WebElement nameField = driver.findElement(AppiumBy.accessibilityId("my_text_fieldCD"));
        nameField.clear();
        nameField.sendKeys(driver.getClipboardText());

        // Verify
        Assert.assertEquals(nameField.getText(), inputText);
    }

    private void acceptUpdatesPrompt()
    {
        WebElement ok=driver.findElement(okButton);
        ok.click();
    }
    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
