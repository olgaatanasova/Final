package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By createButton = By.id("createGlobalItem");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickCreateButton() {
        WebElement createButtonElement = wait.until(ExpectedConditions.elementToBeClickable(createButton));
        createButtonElement.click();
    }
    public void waitForCreateButtonToBeVisible() {
        WebElement createButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(createButton));
        assert createButtonElement.isDisplayed() : "Create button is not visible on the Dashboard.";
    }
}
