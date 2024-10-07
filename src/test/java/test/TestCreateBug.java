package test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BugCreationPage;
import pages.DashboardPage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCreateBug extends BaseTest {

    @Test
    public void testCreateBug() throws IOException {
        DashboardPage dashboardPage = new DashboardPage(driver);
        BugCreationPage bugCreationPage = new BugCreationPage(driver, config);

        dashboardPage.clickCreateButton();

        String bugSummary = "Login Page Crashes When Submitting Invalid Credentials";
        String bugDescription = "Steps to Reproduce:\n" +
                "1. Open the login page of the application.\n" +
                "2. Enter an invalid email (e.g., 'invalidemail@').\n" +
                "3. Enter a password.\n" +
                "4. Click the 'Login' button.\n\n" +
                "Expected Result: Error message like 'Please enter a valid email address.'\n" +
                "Actual Result: The application crashes with a '500 Internal Server Error.'";
        String environment = "QA";


        bugCreationPage.createBug(bugSummary, bugDescription, environment);

        WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@data-testid, 'platform.ui.flags.common.ui.common-flag-v2')]")
        ));
        assert popupElement.isDisplayed() : "Bug creation popup did not appear.";

        String popupText = popupElement.getText();
        System.out.println("Popup Text: " + popupText);  // Debugging output

        String projectKey = config.getProperty("jira.project.key");

        Pattern pattern = Pattern.compile(projectKey + "-\\d+");
        Matcher matcher = pattern.matcher(popupText);

        if (matcher.find()) {
            String issueId = matcher.group(0);
            System.out.println("Captured Issue ID: " + issueId);

            saveBugNumberToConfig(issueId);
        } else {
            throw new AssertionError("Bug creation popup did not contain a valid issue ID.");
        }
    }

    private void saveBugNumberToConfig(String bugNumber) throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

        properties.setProperty("jira.last.created.bug", bugNumber);

        try (FileOutputStream output = new FileOutputStream("src/test/resources/config.properties")) {
            properties.store(output, "Updated last created bug");
        }

        System.out.println("Bug number " + bugNumber + " has been saved to config.properties.");
    }
}
