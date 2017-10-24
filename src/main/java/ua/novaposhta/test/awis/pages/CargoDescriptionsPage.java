package ua.novaposhta.test.awis.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$;

public class CargoDescriptionsPage {

    private WebElement filterCleaner = $(By.xpath("//div[contains(@id,'FilterField-triggerWrap')]/div[@role='button']"));
    private WebElement filterInput = $(By.xpath("//div[contains(@id,'FilterField-triggerWrap')]/../input"));

    public CargoDescriptionsPage() {
        String tabName = "Описи вантажів";
        WebElement tab = $(By.xpath("(//span[.='" + tabName + "'])[last()]"));
        $(tab).shouldBe(Condition.visible).click();
    }

    public void find(String text){
        $(filterCleaner).shouldBe(Condition.visible).click();
        do {
            $(filterInput).setValue(text);
        } while (!$(filterInput).getValue().equals(text));
        $(filterInput).pressEnter();
    }
}
