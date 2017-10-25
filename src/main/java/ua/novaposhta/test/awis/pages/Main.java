package ua.novaposhta.test.awis.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.novaposhta.test.helper.Actions;

import static com.codeborne.selenide.Selenide.$;

public class Main {
    public Main() {
    }

    private Actions action = new Actions();

    private WebElement currentUser = $(By.xpath("//span[@id='WorkspaceInfoLabel-btnInnerEl']"));

    public boolean logged() {
        try {
            $(currentUser).waitUntil(Condition.appears, 1000);
        } catch (ElementNotFound notFound) {
            System.out.println("Not logged in");
        }
        return $(currentUser).isDisplayed();
    }

    public void moveTo(String root) {
        WebElement element = action.getMenuItem(root);
        System.out.println(": " + element.getText());
        $(element).scrollTo().hover().click();
    }

    public void moveTo(String root, String menu) {
        WebElement element = action.getMenuItem(root, menu);
        System.out.println(": " + element.getText());
        $(element).scrollTo().hover().click();
    }

    public void moveTo(String root, String menu, String subMenu) throws InterruptedException {
        WebElement element = action.getMenuItem(root, menu, subMenu);
        System.out.println(": " + element.getText());
        $(element).scrollTo().hover().click();
    }

    public void logOut(){
        WebElement authFrame = $(By.xpath("//span[.='Вхід в систему']"));
        WebElement userMenu = $(By.xpath("//span[@id='WorkspaceInfoLabel-btnInnerEl']"));
        WebElement exitButton = $(By.xpath("//span[.='Вихід']"));

        if ($(userMenu).isDisplayed()){
            userMenu.click();
            $(exitButton).click();
            WebElement confirmationDialogButtonYes = $(By.xpath("//div[contains(@id,'messagebox')]" +
                    "[contains(@class,'window-closable')]" +
                    "//span[contains(@id,'button')][.='Yes']"));
            $(confirmationDialogButtonYes).click();
            $(confirmationDialogButtonYes).shouldBe(Condition.disappears);
            $(authFrame).waitUntil(Condition.appears,2000);
        }
    }
}
