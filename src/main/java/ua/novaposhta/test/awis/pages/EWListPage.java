package ua.novaposhta.test.awis.pages;

import ua.novaposhta.test.awis.helper.Actions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;

public class EWListPage {

    public void choiceEN(String number) throws InterruptedException {
        Actions a = new Actions();

        WebElement numberFilterField = $(By.xpath("//input[contains(@aria-describedby,'NumberFilter')]"));
        $(numberFilterField).waitUntil(Condition.visible,2000).setValue(number).pressEnter();

        WebElement en = $(By.xpath("//div[.='" + number + "']"));
        $(en).waitUntil(Condition.visible, 2000);
        en.click();

        System.out.print("trying to choice EN №: " + number);
        $(en).doubleClick();

        try {WebElement tab = $(By.xpath("//span[contains(text(),'" + number + "')]"));
            $(tab).waitUntil(Condition.appears, 2000);
            System.out.println(" ... OK");
        } catch (ElementNotFound notFound) {
            a.acceptAlert();
        }
    }

    public void close() throws IOException {
        WebElement tab = $(By.xpath("//div[contains(@class,'x-tab-active')]//span[.='Документи \"Експрес-накладна\"']"));
        WebElement tabCloseButton = $(By.xpath("//div[contains(@class,'x-tab-active')]//span[.='Документи \"Експрес-накладна\"']/../../../a"));
        assert $(tab).isDisplayed();
        assert $(tabCloseButton).isDisplayed();

        $(tabCloseButton).click();
        $(tab).waitUntil(Condition.disappears,1000);

        Main mainPage = new Main();
        assert mainPage.logged();
    }
}
