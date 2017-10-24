package ua.novaposhta.test.awis.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$;

public class CounterPartiesPage {

    public String name;

    public CounterPartiesPage() {
        WebElement tab = $(By.xpath("//a[@title='Close Tab']/..//span[.='Контрагенти']"));
        WebElement tabCloser = $(By.xpath("//span[.='Контрагенти']/../../../a[@title='Close Tab']"));
        $(tabCloser).shouldBe(Condition.visible);
        $(tab).shouldBe(Condition.visible);
        assert tabIsActive();
    }

    private boolean tabIsActive() {
        WebElement activeTabFrame = $(By.xpath("//span[.='Контрагенти']/../../../../div[contains(@class,'x-tab-active')]"));
        return activeTabFrame.isDisplayed();
    }

    public void setByEDRPOU(String EDRPOU) {
        WebElement findByEDRPOUInput = $(By.xpath("//input[contains(@aria-describedby,'FilterFieldEDRPOU')]"));
        $(findByEDRPOUInput).setValue(EDRPOU).waitUntil(Condition.value(EDRPOU), 1000).pressEnter();

        WebElement payerEDRPOU = $(By.xpath("//td/div[contains(text(),'" + EDRPOU + "')]"));
        WebElement payerName = $(By.xpath("//td/div[contains(text(),'" + EDRPOU + "')]/../../td[15]"));
        WebElement payerManager = $(By.xpath("//td/div[contains(text(),'" + EDRPOU + "')]/../../td[17]"));
        WebElement payerAddress = $(By.xpath("//td/div[contains(text(),'" + EDRPOU + "')]/../../td[18]"));
        WebElement payerCode = $(By.xpath("//td/div[contains(text(),'" + EDRPOU + "')]/../../td[3]"));

        String info = "Менеджер: " + $(payerManager).getText() + ";"
                + " Адреса: " + $(payerAddress).getText() + ";"
                + " ЄДРПОУ: " + $(payerEDRPOU).getText() + ";"
                + " Код: " + $(payerCode).getText();

        System.out.println("counterparty: " + info);

        this.name = $(payerName).getText();

        $(payerCode).doubleClick();
    }
}
