package stepdefinitions;

import Utilities.DriverFactory;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;

import java.time.Duration;
import java.util.Collections;

public class ScrollTestSteps {

    AndroidDriver driver = DriverFactory.getDriver();

    @Given("the app is launched")
    public void theAppIsLaunched() {
        Assert.assertNotNull(driver, "Driver is not initialized. App may not be launched.");
    }

    @When("the user taps on {string}")
    public void userTapsOn(String option) {
        WebElement element = driver.findElement(AppiumBy.accessibilityId(option));
        element.click();
    }

    @And("the user scrolls down")
    public void userScrollsDown() {
        scrollVertical(0.8, 0.2);
    }

    @Then("the user should see {string}")
    public void verifyElementVisible(String expected) {
        WebElement element = driver.findElement(AppiumBy.accessibilityId(expected));
        Assert.assertTrue(element.isDisplayed(), expected + " is not visible on screen.");
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
        scroll.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), centerX, endY));
        scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(scroll));
    }
}
