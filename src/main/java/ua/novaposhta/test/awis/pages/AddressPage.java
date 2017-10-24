package ua.novaposhta.test.awis.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$;

public class AddressPage {
    public AddressPage() {
        WebElement tabCloser = $(By.xpath("//span[.='Адреси']/../../../a[@title='Close Tab']"));
        $(tab).waitUntil(Condition.visible, 2000);
        $(tabCloser).shouldBe(Condition.visible);
    }

    public void fillAddressField(String address) throws InterruptedException {
        WebElement filterInput = $(By.xpath("(//div[contains(@id,'FilterField')]//input)[last()]"));
        do {
            try {
                $(filterInput).clear();
                $(filterInput).setValue(address).pressTab();
            } catch (Throwable throwable) {
                $(filterInput).clear();
                $(filterInput).setValue(address).shouldBe(Condition.value(address)).pressTab();
            }
        } while (!$(filterInput).getValue().equals(address));
    }

    public void selectAddressFromTable(String address) throws InterruptedException {
        do {
            try {
                $(address(address)).hover().doubleClick();
                $(tab).waitUntil(Condition.disappears, 2000);
            } catch (Throwable throwable) {
                System.out.println(throwable.getMessage());
                $(address(address)).hover().doubleClick();
                $(tab).waitUntil(Condition.disappears, 2000);
            }
        } while (tab.isDisplayed());
    }

    private WebElement address(String address) {
        return $(By.xpath("(//div[@unselectable='on'][contains(text(),'" + address + "')])[last()]"));
    }

//  ====================================================================================================================

    private WebElement tab = $(By.xpath("//span[.='Адреси']"));
}
