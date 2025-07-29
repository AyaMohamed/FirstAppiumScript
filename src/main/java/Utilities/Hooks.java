package Utilities;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;

public class Hooks {
    @Before
    public void setUp(Scenario scenario) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Galaxy Note 10 Lite");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("udid", "RF8N327PL0Y");
        if (scenario.getSourceTagNames().contains("@apidemos")) {
            caps.setCapability("app", System.getProperty("user.dir") + "/apps/ApiDemos-debug.apk");
        } else if (scenario.getSourceTagNames().contains("@clipboard")) {
            caps.setCapability("app", System.getProperty("user.dir") + "/apps/selendroid.apk");
        }
        caps.setCapability("autoGrantPermissions", true);
        caps.setCapability("noReset", true);

        DriverFactory.initDriver(caps);
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
