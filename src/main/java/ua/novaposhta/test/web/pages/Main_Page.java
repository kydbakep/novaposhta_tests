package ua.novaposhta.test.web.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;


public class Main_Page {

    public Main_Page() {
        String pageURL = "https://my.novaposhta.ua/orders/index";
        if (!getWebDriver().getCurrentUrl().equals(pageURL)) {
            if (logged()) {
                open(pageURL);
            }
        }
    }

    public boolean logged() {
        return $(exitButton).isDisplayed();
    }

    public void logOut() throws IOException {
        if (logged()) {
            WebElement enterButton = $(By.xpath(".//a[span='Вхід']"));
            try {
                $(exitButton).shouldBe(Condition.visible).scrollTo().click();
                $(exitConfirmButton).shouldBe(Condition.visible).click();
                $(exitConfirmModal).shouldBe(Condition.disappears);
            } catch (Throwable th){
                System.out.println(th.getMessage());
                $(exitConfirmButton).shouldBe(Condition.visible).click();
            }
            $(enterButton).shouldBe(Condition.appears);
        }
    }

    public void closeAllModals() throws InterruptedException {
        try {
            while ($(activeModalCloser).exists()) {
                $(activeModalCloser).click();
            }
        } catch (Throwable th) {
            System.out.println("can't close modal");
            Thread.sleep(1000);
            try {
                while ($(activeModalCloser).exists()) {
                    $(activeModalCloser).click();
                }
            } catch (Throwable thr) {
                System.out.println("no active modals present");
            }
        }
    }

//======================================================================================================================

    private WebElement exitButton = $(By.xpath("//a[@class='logo_in'][@id='exit']"));
    private WebElement exitConfirmButton = $(By.xpath("//div[@id='exitModal']//a[@href='/auth/logout']"));
    private WebElement exitConfirmModal = $(By.xpath("//div[@id='exitModal']"));

    private WebElement activeModalCloser = $(By.xpath("(.//div[contains(@style,'display: block')][@aria-hidden='false']/div/button[@data-dismiss='modal'])[last()]"));
}
