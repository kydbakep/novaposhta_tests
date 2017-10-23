package ua.novaposhta.test.awis.helper;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.novaposhta.test.awis.helper.elements.Items;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Actions {
    public Actions() {
    }

    private Items item = new Items();
    private Items button = new Items();
    private Assertions assertion = new Assertions();

    // MAIN MENU ELEMENTS ==================================================================================================
    public WebElement getMenuItem(String name) {
        return $(By.xpath("//span[.='" + name + "']"));
    }

    public WebElement getMenuItem(String root, String name) {
        getMenuItem(root).click();
        return $(By.xpath("//span[.='" + name + "']"));
    }

    public WebElement getMenuItem(String root, String name, String item) {
        getMenuItem(root).click();

        WebElement menuItem = $(By.xpath("(//span[.='" + name + "'])"));
        $(menuItem).shouldBe(visible).scrollTo().hover();

        WebElement submenuItem = $(By.xpath("(//span[.='" + item + "'])"));
        if (menuItem.isDisplayed()) {
            try {
                submenuItem = $(By.xpath("(//span[.='" + item + "'])" + "[2]"));
                $(submenuItem).shouldBe(visible);
            } catch (ElementNotFound notFound) {
                submenuItem = $(By.xpath("(//span[.='" + item + "'])"));
            }
        }
        return submenuItem;
    }
// ------------------------------------------------------------------------------------------- END OF MAIN MENU ELEMENTS


    public void click(WebElement element) {
        if ((element.getAttribute("aria-disabled") == null)
                || (element.getAttribute("aria-disabled").equals("false"))) {
            $(element).click();
        } else if (element.getAttribute("aria-disabled").equals("true")) {
            throw new IllegalMonitorStateException("element '" + element.getText().trim() + "' is not active");
        }
    }

    public void acceptAlert() {
        Alert alert = getWebDriver().switchTo().alert();
        System.out.print("accepting alert: " + alert.getText() + "...");
        alert.accept();
        System.out.println("OK");
    }

    public void closeAllTabs() {
        System.out.print("closing tabs...");
        WebElement activeTabCloser = $(By.xpath("(//div[contains(@id,'tab-')][contains(@class,'x-tab-closable')])[last()]/..//a"));
        Assertions assertion = new Assertions();
        if (assertion.elementIsVisible(activeTabCloser)) {
            try {
                while (assertion.elementIsVisible(activeTabCloser)) {
                    $(activeTabCloser).click();
                }
            } catch (ElementNotFound ignored) {
            }
            System.out.println("OK");
        }
    }

    public void closeAllFrames() {
        WebElement frameCloser = $(By.xpath("(//img[@class='x-tool-close'])[1]"));
        Assertions assertion = new Assertions();
        if (assertion.elementIsVisible(frameCloser)) {
            System.out.print("closing frames...");
            try {
                while (assertion.elementIsVisible(frameCloser)) {
                    $(frameCloser).click();
                }
            } catch (ElementNotFound ignored) {
            }
            System.out.println("OK");
        }
    }

    public void choiceTableElement(int number) {
        try {
            WebElement element = item.catalogueItem(number);
            $(element).waitUntil(Condition.visible, 500).scrollTo().hover().doubleClick();
        } catch (ElementNotFound notFound) {
            throw new IllegalMonitorStateException("no table of elements found");
        }
    }

    public void choiceTableDocument() {
        WebElement element = $(By.xpath(item.documentInFolder()));
        $(element).scrollTo().hover().doubleClick();
    }

    public void clickOKButton() throws InterruptedException {
        click(button.writeAndCloseButton());
        if (assertion.alert(500)) {
            acceptAlert();
        }
    }
}
