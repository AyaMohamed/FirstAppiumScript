package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {"pretty", "html:target/cucumber-report.html"},
        tags = "@scroll and @apidemos"
)

public class TestRunner extends AbstractTestNGCucumberTests {
}
