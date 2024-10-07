package test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.DashboardPage;
import pages.IssueLinkPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestLinkBugToStory extends BaseTest {

    @Test
    public void testLinkBugToStory() throws IOException {

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.waitForCreateButtonToBeVisible();

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/test/resources/config.properties"));
        String lastCreatedBug = properties.getProperty("jira.last.created.bug");
        String lastCreatedStory = properties.getProperty("jira.last.created.story");

        String baseUrl = config.getProperty("jira.url");
        driver.get(baseUrl + "/browse/" + lastCreatedBug);

        IssueLinkPage issueLinkPage = new IssueLinkPage(driver, config);
        issueLinkPage.clickLinkIssueButton();

        issueLinkPage.inputStoryId(lastCreatedStory);

        issueLinkPage.clickLinkButton();

        String linkedStoryUrl = issueLinkPage.getLinkedStoryUrl(lastCreatedStory);
        System.out.println("Linked Story URL: " + linkedStoryUrl);

        WebElement linkedStoryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@data-testid='issue.issue-view.views.common.issue-line-card.issue-line-card-view.key' and text()='" + lastCreatedStory + "']")));

        assert linkedStoryElement.isDisplayed() : "Story was not successfully linked to the bug.";

        System.out.println("The story with ID " + lastCreatedStory + " was successfully linked to the bug.");
    }
}