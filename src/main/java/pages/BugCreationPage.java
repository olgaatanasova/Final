package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class BugCreationPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ConfigReader config;

    private By modalIssueTypeDropdown = By.cssSelector("[id='issue-create\\.ui\\.modal\\.create-form\\.type-picker\\.issue-type-select'] > .css-573cd-control > .css-66zdpf > .css-1tbvomj");
    private By bugOption = By.cssSelector("#react-select-5-option-4 ._2lx21bp4 > div");
    private By summaryField = By.xpath("//*[@data-testid='issue-create-commons.common.ui.fields.base-fields.input-field.textfield']");
    private By descriptionField = By.xpath("//*[@data-testid='issue-create.ui.modal.create-form.layout-renderer.field-renderer.field.description']//p");
    private By createButton = By.xpath("//*[@data-testid='issue-create.common.ui.footer.create-button']");
    private By environmentField = By.xpath("//*[@data-testid='issue-create.ui.modal.create-form.layout-renderer.field-renderer.field.environment']//p");
    private By priorityOption = By.xpath("//div[@data-testid='issue-field-select-base.ui.format-option-label.c-label']");

    public BugCreationPage(WebDriver driver, ConfigReader config) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.config = config;
    }

    public void scrollToElementIfNotVisible(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        if (!element.isDisplayed()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        }
    }

    public void waitForModalToBeVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(summaryField));
    }

    public void selectBugIssueType() {
        waitForModalToBeVisible();  // Wait for modal

        WebElement issueTypeDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(modalIssueTypeDropdown));
        issueTypeDropdownElement.click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement bugOptionElement = wait.until(ExpectedConditions.elementToBeClickable(bugOption));
        bugOptionElement.click();
    }

    public void enterSummary(String summary) {
        WebElement summaryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(summaryField));
        summaryElement.sendKeys(summary);
    }

    public void enterDescription(String description) {
        WebElement descriptionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionField));
        descriptionElement.sendKeys(description);
        try {
            Thread.sleep(500);  // 500 milliseconds wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scrollToElementIfNotVisible(environmentField);  // Scroll to make the environment field visible
    }
    public void selectPriority(String priority) {
        WebElement priorityDropdownElement = wait.until(ExpectedConditions.presenceOfElementLocated(priorityOption));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", priorityDropdownElement);

        priorityDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(priorityOption));
        priorityDropdownElement.click();

        By priorityOption = By.xpath("//div[text()='" + priority + "']");

        WebElement priorityOptionElement = wait.until(ExpectedConditions.elementToBeClickable(priorityOption));
        priorityOptionElement.click();
    }

    public void enterEnvironment(String environment) {
        WebElement environmentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(environmentField));
        environmentElement.sendKeys(environment);
    }

    public void submitBug() {
        WebElement createButtonElement = wait.until(ExpectedConditions.elementToBeClickable(createButton));
        createButtonElement.click();
    }

    public void createBug(String summary, String description, String environment) {
        selectBugIssueType();
        enterSummary(summary);
        enterDescription(description);
        enterEnvironment(environment);
        submitBug();
    }
}
