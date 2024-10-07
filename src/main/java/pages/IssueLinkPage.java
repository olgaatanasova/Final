package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class IssueLinkPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ConfigReader config;

    private By containerLeft = By.xpath("//div[@data-testid='issue.views.issue-details.issue-layout.container-left']");
    private By linkIssueButton = By.xpath("//button[@data-testid='issue.issue-view.views.issue-base.foundation.quick-add.quick-add-item.link-issue']");
    private By linkInputField = By.cssSelector(".issue-links-search__input-container input");  // Using CSS selector for the input field
    private By linkButton = By.cssSelector("[data-testid='issue.issue-view.views.issue-base.content.issue-links.add.issue-links-add-view.link-button']");
    private By linkedStoryElement(String storyId) {
        return By.xpath("//a[@data-testid='issue.issue-view.views.common.issue-line-card.issue-line-card-view.key' and text()='" + storyId + "']");

    }


    public IssueLinkPage(WebDriver driver, ConfigReader config) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.config = config;
    }

    public void waitForContainerToLoad() {
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(containerLeft));
        assert container.isDisplayed() : "Issue page container did not load correctly.";
    }

    public void clickLinkIssueButton() {

        waitForContainerToLoad();
        WebElement linkIssueButtonElement = wait.until(ExpectedConditions.elementToBeClickable(linkIssueButton));
        linkIssueButtonElement.click();
    }

    public void waitForLinkInputField() {
        WebElement linkInputFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(linkInputField));
        assert linkInputFieldElement.isDisplayed() : "Link input field did not become visible after clicking 'Link Issue'.";
    }

    public void inputStoryId(String storyId) {
        waitForLinkInputField();  // Ensure the input field is visible before interacting with it
        WebElement linkInputFieldElement = driver.findElement(linkInputField);
        linkInputFieldElement.clear(); // Clear any existing placeholder text
        linkInputFieldElement.sendKeys(storyId);
        linkInputFieldElement.sendKeys(Keys.RETURN);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        }
    public void clickLinkButton() {
        WebElement linkButtonElement = wait.until(ExpectedConditions.elementToBeClickable(linkButton));
        linkButtonElement.click();
    }

    public boolean isStoryLinked(String storyId) {
        WebElement linkedStoryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(linkedStoryElement(storyId)));
        return linkedStoryElement.isDisplayed();
    }

    public String getLinkedStoryUrl(String storyId) {
        WebElement linkedStoryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(linkedStoryElement(storyId)));
        return linkedStoryElement.getAttribute("href");  // Returns the URL of the linked story
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(@data-testid,'issue.views.issue-base.foundation.summary.heading')]")));
    }
}
