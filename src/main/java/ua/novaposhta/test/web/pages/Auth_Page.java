package ua.novaposhta.test.web.pages;

import ua.novaposhta.test.properties.PropertyLoader;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Auth_Page {
    private PropertyLoader property = new PropertyLoader("web");

    public Auth_Page() throws IOException {
        Main_Page main = new Main_Page();
        if (!main.logged()){
            open(property.load("url"));
        }
    }

    public void logInAs(String userType) throws IOException {
        $(enterButton).shouldBe(Condition.visible).click();
        if (userType.equals("loyal")) {
            try {
                $(typeIsLoyal).click();
            } catch (NotFoundException notFound) {
                notFound.getMessage();
            }
            $(loginField).setValue(property.load("login.loyal"));
            $(passwordField).setValue(property.load("password.loyal"));
            $(loginButton).click();
        } else if (userType.equals("corporate")) {
            try {
                $(typeIsCorporate).click();
            } catch (NotFoundException notFound) {
                notFound.getMessage();
            }
            $(loginField).setValue(property.load("login.corporate"));
            $(passwordField).setValue(property.load("password.corporate"));
            $(loginButton).click();
        }
    }
    public void logInAs(String login, String password) throws IOException {
        $(enterButton).shouldBe(Condition.visible).click();
        $(loginField).setValue(login);
        $(passwordField).setValue(password);
        $(loginButton).click();
    }


    public void startRegister() {
        $(registerButton).shouldBe(Condition.visible).click();
    }

    public void startPasswordRecovery() {
        $(enterButton).shouldBe(Condition.visible).click();
        $(loginFrame).shouldBe(Condition.appears);
        $(forgotButton).shouldBe(Condition.visible).click();
    }

//======================================================================================================================

    // main elements
    private WebElement loginField = $(By.xpath(".//input[@id='inputEmail']"));
    private WebElement passwordField = $(By.xpath(".//input[@id='inputPassword']"));
    private WebElement loginButton = $(By.xpath(".//input[@value='Увійти']"));
    private WebElement enterButton = $(By.xpath(".//a[span='Вхід']"));
    private WebElement registerButton = $(By.xpath(".//a[.='реєстрація']"));
    private WebElement forgotButton = $(By.xpath(".//a[.='Потрібен пароль?']"));
    // user type
    private WebElement typeIsLoyal = $(By.xpath(".//input[@data-type='person']"));
    private WebElement typeIsCorporate = $(By.xpath(".//input[@data-type='business']"));
    // frames
    private WebElement loginFrame = $(By.xpath("//div[@id='popup_login']"));

}