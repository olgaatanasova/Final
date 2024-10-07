package test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.StoryCreationPage;
import pages.DashboardPage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCreateStory extends BaseTest {

    @Test
    public void testCreateStory() throws IOException {
        DashboardPage dashboardPage = new DashboardPage(driver);
        StoryCreationPage storyCreationPage = new StoryCreationPage(driver, config);

        dashboardPage.clickCreateButton();

        storyCreationPage.createStory(
                "Implement a Login Form",
                "### Objective:\n" +
                        "The goal of this task is to design and implement a user-friendly login form for the application.\n\n" +
                        "### Functional Requirements:\n" +
                        "1. **Input Fields:**\n" +
                        "2. **Buttons:**\n" +
                        "3. **Validation:**\n" +
                        "4. **Security:**\n" +
                        "5. **User Feedback:**\n\n"
        );


        WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@data-testid, 'platform.ui.flags.common.ui.common-flag-v2')]")
        ));
        assert popupElement.isDisplayed() : "Story creation popup did not appear.";

        String popupText = popupElement.getText();
        System.out.println("Popup Text: " + popupText);

        String projectKey = config.getProperty("jira.project.key");

        Pattern pattern = Pattern.compile(projectKey + "-\\d+");
        Matcher matcher = pattern.matcher(popupText);

        if (matcher.find()) {
            String storyId = matcher.group(0);
            System.out.println("Captured Story ID: " + storyId);

            saveStoryNumberToConfig(storyId);
        } else {
            throw new AssertionError("Story creation popup did not contain a valid issue ID.");
        }
    }

    private void saveStoryNumberToConfig(String storyNumber) throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

        properties.setProperty("jira.last.created.story", storyNumber);

        try (FileOutputStream output = new FileOutputStream("src/test/resources/config.properties")) {
            properties.store(output, "Updated last created story");
        }

        System.out.println("Story number " + storyNumber + " has been saved to config.properties.");
    }
}
