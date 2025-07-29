package Base;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseTest {

    protected AndroidDriver driver;

    //Call this from child classes in @BeforeMethod or @BeforeTest to start the driver.
    protected void initDriver(DesiredCapabilities caps) throws MalformedURLException {
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:platformVersion", "13");
        caps.setCapability("appium:deviceName", "Galaxy Note 10 Lite");
        caps.setCapability("appium:udid", "RF8N327PL0Y"); // Replace with your real device's UDID
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:autoGrantPermissions", true);

        // Either app or appPackage/appActivity must be defined in caps before calling this
        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), caps);

        dismissUpdatePopupIfPresent(); // optional helper
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    //Dismisses a generic OK popup if it appears.
    protected void dismissUpdatePopupIfPresent() {
        By okButton = By.id("android:id/button1"); // Standard ID for 'OK' in Android dialogs
        try {
            if (driver.findElement(okButton).isDisplayed())
            {
                driver.findElement(okButton).click();
            }
        } catch (Exception ignored) {
        }
    }
}
