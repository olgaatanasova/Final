package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class StoryCreationPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ConfigReader config;

    private By modalIssueTypeDropdown = By.cssSelector("[id='issue-create\\.ui\\.modal\\.create-form\\.type-picker\\.issue-type-select'] > .css-573cd-control > .css-66zdpf > .css-1tbvomj");
    private By storyOption = By.xpath("//div[text()='Story']");
    private By summaryField = By.xpath("//*[@data-testid='issue-create-commons.common.ui.fields.base-fields.input-field.textfield']");
    private By descriptionField = By.xpath("//*[@data-testid='issue-create.ui.modal.create-form.layout-renderer.field-renderer.field.description']//p");
    private By createButton = By.xpath("//*[@data-testid='issue-create.common.ui.footer.create-button']");

    public StoryCreationPage(WebDriver driver, ConfigReader config) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        this.config = config;
    }

    public void waitForModalToBeVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(summaryField));
    }

    public void enterSummary(String summary) {
        WebElement summaryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(summaryField));
        summaryElement.sendKeys(summary);
    }

    public void enterDescription(String description) {
        WebElement descriptionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionField));
        descriptionElement.sendKeys(description);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", descriptionElement);
    }

    public void selectStoryIssueType() {
        waitForModalToBeVisible();  // Wait for modal

        WebElement issueTypeDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(modalIssueTypeDropdown));
        issueTypeDropdownElement.click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement storyOptionElement = wait.until(ExpectedConditions.elementToBeClickable(storyOption));
        storyOptionElement.click();
    }

    public void selectPriority() {
    }

    public void submitStory() {
        WebElement createButtonElement = wait.until(ExpectedConditions.elementToBeClickable(createButton));
        createButtonElement.click();
    }

    public void createStory(String summary, String description) {
        selectStoryIssueType();
        enterSummary(summary);
        enterDescription(description);
        selectPriority();
        submitStory();
    }
}
