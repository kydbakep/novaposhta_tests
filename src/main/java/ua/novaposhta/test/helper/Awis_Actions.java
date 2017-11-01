package ua.novaposhta.test.helper;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.novaposhta.test.helper.elements.Items;

import java.io.IOException;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Awis_Actions {
    public Awis_Actions() {
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
        $(menuItem).scrollTo().hover();

        WebElement submenuItem = $(By.xpath("(//span[.='" + item + "'])"));
        if (menuItem.isDisplayed()) {
            if (assertion.elementIsVisible($(By.xpath("(//span[.='" + item + "'])" + "[2]")), 300)) {
                submenuItem = $(By.xpath("(//span[.='" + item + "'])" + "[2]"));
            }
        }
        return submenuItem;
    }
// ------------------------------------------------------------------------------------------- END OF MAIN MENU ELEMENTS


    public void click(WebElement element) {
        if ((element.getAttribute("aria-disabled") == null)
                || (element.getAttribute("aria-disabled").equals("false"))) {
            $(element).waitUntil(Condition.visible,1000).click();
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

    public void clickOKButton(int time) throws InterruptedException {
        click(button.writeAndCloseButton(time));
        if (assertion.alert(500)) {
            acceptAlert();
        }
    }

    public void choiceFirstActualWrite() throws SQLException, IOException {
        WebElement firstActualWrite = $(By.xpath("(//img[contains(@class,'catalog-item')][not(contains(@class,'-deletion'))])[1]"));
        $(firstActualWrite).doubleClick();
    }

    public void choiceFirstActualWrite(String dbName, String tableName) throws SQLException, IOException, InterruptedException {
//        WebElement firstActualWrite = item.catalogueItem(1);
        WebElement firstActualWrite = $(By.xpath("(//img[contains(@class,'catalog-item')][not(contains(@class,'-deletion'))])[1]"));

        while (!firstActualWrite.isDisplayed()){
            if (assertion.elementIsVisible(firstActualWrite)) {
                $(firstActualWrite).scrollTo().hover().doubleClick();
            } else {
                DataBase db = new DataBase(dbName);
                $(db.validFolder(tableName)).waitUntil(Condition.visible,1000).doubleClick();
                $(firstActualWrite).doubleClick();
                if (assertion.alert(500)){
                    acceptAlert();
                    throw new IllegalMonitorStateException("ALERT");
                }
            }
        }
    }

    public void choiceFirstActualWrite(String dbName, String tableName, String sql) throws SQLException, IOException {
        DataBase db = new DataBase(dbName);
        WebElement firstActualWrite = $(By.xpath("(//img[contains(@class,'catalog-item') and not(contains(@class,'-deletion'))])[1]"));

        if (assertion.elementIsVisible(firstActualWrite)) {
            $(firstActualWrite).scrollTo().hover().doubleClick();
        } else {
            $(db.validFolder(tableName, sql)).doubleClick();
            $(firstActualWrite).doubleClick();
        }
    }

}
