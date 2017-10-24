package ua.novaposhta.test.web.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class EmailPage {
    public EmailPage() throws AWTException {
        open("http://www.i.ua/");
        WebElement loginInput = $(By.xpath("//input[@name='login']"));
        $(loginInput).setValue("t.test");
        WebElement passwordInput = $(By.xpath("//input[@name='pass']"));
        $(passwordInput).setValue("bloodx5");
        WebElement enterButton = $(By.xpath("//input[@type='submit'][@tabindex]"));
        $(enterButton).click();
    }

    public void openMessage() throws InterruptedException {
        WebElement iframe = $(By.xpath("//div[@class='message_body']/iframe"));
        WebElement novaPoshtaLastMessage = $(By.xpath("(//span[.='NovaPoshta'])[1]"));
        WebElement reclama = $(By.xpath(""));

        waitForActiveMessage();
        $(novaposhtaLastUnreadMessage).click();
        getWebDriver().switchTo().frame(iframe);
    }

    private boolean unreadMessagePresent() throws InterruptedException {
        return $(novaposhtaLastUnreadMessage).isDisplayed();
    }

    private void waitForActiveMessage() throws InterruptedException {
        if (!unreadMessagePresent()) {
            System.out.println("Waiting for new E-mail");
            do {
                $(By.xpath("//a[contains(@href,'/list/INBOX')]")).click();
            } while (!$(novaposhtaLastUnreadMessage).isDisplayed());
        }
    }

    public void followActivateLink() {
        $(By.cssSelector("body > a")).waitUntil(Condition.visible, 5000).click();
    }

//  ====================================================================================================================

    private WebElement novaposhtaLastUnreadMessage = $(By.xpath("(//span/i[@class='m'][@onclick]/../..//span[.='NovaPoshta'])[1]"));

}
