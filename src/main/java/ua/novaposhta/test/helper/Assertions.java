package ua.novaposhta.test.helper;

import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Assertions {
    public Assertions() {
    }

    public boolean loggedIn() {
        WebElement userMenu = $(By.xpath("//span[@id='WorkspaceInfoLabel-btnInnerEl']"));
        return userMenu.isDisplayed();
    }

    private WebElement serviceMessage = $(By.xpath("//div[contains(@id,'serviceMessage')]/div[@style='color:red;']"));

    public boolean elementIsVisible(WebElement element) {
        try {
            $(element).waitUntil(visible, 500).isDisplayed();
            return true;
        } catch (ElementNotFound | ElementShould notVisible) {
            return false;
        }
    }

    public boolean elementIsVisible(WebElement element, int time) {
        try {
            return $(element).waitUntil(visible, time).isDisplayed();
        } catch (ElementNotFound | ElementShould notVisible) {
            return false;
        }
    }

    public boolean tableIsPresent() {
        try {
            WebElement table = $(By.xpath("//table[contains(@class,'x-grid-table')]"));
            $(table).waitUntil(visible, 1000).isDisplayed();
            return true;
        } catch (ElementNotFound | ElementShould notVisible) {
            return false;
        }
    }

    public boolean buttonIsActive(WebElement button) {
        return !button.getAttribute("aria-disabled").equals("true");
    }

    public boolean dialog() {
        WebElement dialogFrame = $(By.xpath("(//div[@role='dialog'])[last()]/div[contains(@id,'body')]/div"));
        return $(dialogFrame).isDisplayed();
    }

    public boolean alert(int millis) throws InterruptedException {
        try {
            Thread.sleep(millis);
            Alert alert = getWebDriver().switchTo().alert();
            assert alert.getText() != null;
            return true;
        } catch (NoAlertPresentException noAlert) {
            return false;
        }
    }
}
