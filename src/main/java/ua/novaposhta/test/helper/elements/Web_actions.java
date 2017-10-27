package ua.novaposhta.test.helper.elements;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Web_actions {
    public Web_actions() {
    }

    JavascriptExecutor executor = (JavascriptExecutor)getWebDriver();

    public void jsClick(WebElement element){
        executor.executeScript("arguments[0].click();",element);
    }
}
