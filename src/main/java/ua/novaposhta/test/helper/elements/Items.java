package ua.novaposhta.test.helper.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.$;

public class Items {
    public Items() {
    }

    private PathFinder pathFinder = new PathFinder();

    private String writeAndCloseButtonPath() {
        String path = "//span[contains(@style,'disk--arrow.png')]/..";
        ArrayList<String> list = new ArrayList<>();
        list.add("(//button[contains(@id,'buttonWriteAndClose')])[1]");
        list.add("(//button[contains(@id,'WriteAndClose')])[1]");
        list.add("//div[contains(@id,'button')]/em[contains(@id,'Wrap')]//span[contains(text(),'Закінчити')]/..");
        list.add("//span[contains(@id,'BtnRefreshDataForEW')]");
        list.add("//button[@role='button']/span[.='Записати та закрити']");
        list.add("(//span[.='Ok']/..)[last()]");

        return pathFinder.path(path, list);
    }

    private String writeAndCloseButtonPath(int time) {
        String path = "//span[contains(@style,'disk--arrow.png')]/..";
        ArrayList<String> list = new ArrayList<>();
        list.add("(//button[contains(@id,'buttonWriteAndClose')])[1]");
        list.add("(//button[contains(@id,'WriteAndClose')])[1]");
        list.add("//div[contains(@id,'button')]/em[contains(@id,'Wrap')]//span[contains(text(),'Закінчити')]/..");
        list.add("//span[contains(@id,'BtnRefreshDataForEW')]");
        list.add("//button[@role='button']/span[.='Записати та закрити']");
        list.add("(//span[.='Ok']/..)[last()]");

        return pathFinder.path(path, list, time);
    }

    public WebElement writeAndCloseButton() {
        return $(By.xpath(writeAndCloseButtonPath()));
    }

    public WebElement writeAndCloseButton(int time) {
        return $(By.xpath(writeAndCloseButtonPath(time)));
    }

    public boolean clickable(WebElement button) {
        return button.getAttribute("aria-disabled").equals("true");
    }

    private String catalogItemPath(int itemNumber) {
//        String path = "//tr[@class='x-grid-row'][" + itemNumber + "]/td[2]/div[1]";
        String path = "(//img[contains(@class,'catalog-item')][not(contains(@class,'deletion'))][not(contains(@class,'open'))])[1]";
        ArrayList<String> list = new ArrayList<>();
        list.add("(//img[contains(@class,'catalog-item')][not(contains(@class,'deletion'))][not(contains(@class,'open'))])[" + itemNumber + "]/../../../td[4]");
        list.add("//tr[contains(@class,'x-grid-row')][not(contains(@class,'header'))][" + itemNumber + "]/td[2]/div[1]");

        return pathFinder.path(path, list);
    }

    public WebElement catalogueItem(int number) {
        return $(By.xpath(catalogItemPath(number)));
    }

    public String documentInFolder() {
        String document = "(//img[contains(@class,'catalog-item')])[1]";
        ArrayList<String> list = new ArrayList<>();
        list.add("(//img[contains(@class,'catalog-item-deletionmark')])[1]");

        return pathFinder.path(document, list);
    }
}
