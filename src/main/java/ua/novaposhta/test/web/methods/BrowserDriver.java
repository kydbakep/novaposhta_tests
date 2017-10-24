package ua.novaposhta.test.web.methods;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BrowserDriver {

    private String PAGE_ID = null;

    public String getPAGE_ID() {
        return PAGE_ID;
    }

    public BrowserDriver() throws AWTException {
        savePageID();
    }

    public void newTab() throws AWTException {
        if (!(getWebDriver().getWindowHandle() == null)) {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_T);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.keyRelease(KeyEvent.VK_T);
            BrowserDriver browserDriver = new BrowserDriver();
            getWebDriver().switchTo().window(browserDriver.getPAGE_ID());
        }
    }

    private void savePageID() throws AWTException {
        PAGE_ID = getWebDriver().getWindowHandle();
        System.out.println(PAGE_ID);
    }
}
