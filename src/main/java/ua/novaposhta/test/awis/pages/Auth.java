package ua.novaposhta.test.awis.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.novaposhta.test.awis.properties.PropertyLoader;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Auth {

    private PropertyLoader prop = new PropertyLoader("awis");
    private WebElement authFrame = $(By.xpath("//span[.='Вхід в систему']"));
    private WebElement userMenu = $(By.xpath("//span[@id='WorkspaceInfoLabel-btnInnerEl']"));

    public Auth() throws IOException {
        String url = prop.load("url");
        if (!getWebDriver().getCurrentUrl().equals(url)) {
            open(url);
//            $(authFrame).waitUntil(Condition.visible, 2000);
        } else $(authFrame).shouldBe(Condition.visible);
    }

    public Auth(String url) throws IOException {
        WebElement authFrame = $(By.xpath("//span[.='Вхід в систему']"));
        if (!getWebDriver().getCurrentUrl().equals(url)) {
            open(url);
            $(authFrame).waitUntil(Condition.visible, 2000);
        } else $(authFrame).shouldBe(Condition.visible);
    }

    public void logIn() throws IOException {
        WebElement loginInput = $(By.xpath("//input[@name='user']"));
        WebElement passwordInput = $(By.xpath("//input[@name='password']"));
        WebElement logInButton = $(By.xpath("//span[.='Вхід']"));
        $(loginInput).setValue(prop.load("login"));
        $(passwordInput).setValue(prop.load("password"));
        logInButton.click();
        $(authFrame).waitUntil(Condition.disappears, 10000);
        System.out.println("logged as: " + $(userMenu).getText());
    }

    public void logIn(String login, String password) {
        WebElement loginInput = $(By.xpath("//input[@name='user']"));
        WebElement passwordInput = $(By.xpath("//input[@name='password']"));
        WebElement logInButton = $(By.xpath("//span[.='Вхід']"));
        $(loginInput).setValue(login);
        $(passwordInput).setValue(password);
        $(logInButton).click();
        $(authFrame).waitUntil(Condition.disappears, 10000);
        System.out.println("logged as: " + $(userMenu).getText());
    }
}
