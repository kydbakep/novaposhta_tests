package ua.novaposhta.test.web;

import ua.novaposhta.test.web.methods.*;
import ua.novaposhta.test.properties.PropertyLoader;
import ua.novaposhta.test.web.pages.*;
import com.codeborne.selenide.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Web_WebclientTests {
    private PropertyLoader property = new PropertyLoader("web");

    public Web_WebclientTests() throws IOException {
    }

    @Before
    public void loadPresets() {

        Configuration.browser = "chrome";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            System.setProperty("webdriver.chrome.driver", "D:\\webDrivers\\chromedriver.exe");
            getWebDriver().manage().window().maximize();
        }
        if (os.toLowerCase().endsWith("nux") || os.toLowerCase().endsWith("nix")) {
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            Configuration.browserSize = "1920x1080";
        }
        Configuration.holdBrowserOpen = true;
        Configuration.savePageSource = false;
        Configuration.timeout = 1500;
    }

    @After
    public void clearState() throws IOException, InterruptedException {
        Main_Page main = new Main_Page();
        if (main.logged()) {
            main.logOut();
        }
    }

    @Test // Test of login as loyal or corporate user
    public void loginAsLoyal() throws IOException {
        Auth_Page auth = new Auth_Page();
        auth.logInAs("loyal");
    }

    @Test // Test of login as loyal or corporate user
    public void loginAsCorporate() throws IOException {
        Auth_Page auth = new Auth_Page();
        auth.logInAs("corporate");
    }

    @Test // Відновлення пароля через пошту
    public void recoveryPasswordEmail() throws IOException, SQLException, AWTException, InterruptedException {
        PropertyLoader property = new PropertyLoader("web");
        ForgotPassword_Page page = new ForgotPassword_Page();
        page.forgotNumber(page.getPhone());
        page.insertCardNumber();
        page.sendMessage("email");
        BrowserDriver browserDriver = new BrowserDriver();
        EmailPage emailPage = new EmailPage();
        emailPage.openMessage();
        emailPage.followActivateLink();
        getWebDriver().switchTo().window(browserDriver.getPAGE_ID()).close();
        page.setNewPassword(property.load("recovery.password"));
    }

    @Test // Відновлення пароля через СМС
    public void recoveryPasswordSMS() throws IOException, SQLException, AWTException, InterruptedException {
        PropertyLoader property = new PropertyLoader("web");
        ForgotPassword_Page page = new ForgotPassword_Page();
        page.forgotNumber(page.getPhone());
        page.insertCardNumber();
        page.sendMessage("sms");
        page.setCode();
        page.setNewEmail();
        // Якщо через СМС, то нафіга знову слати на мило?!
        BrowserDriver browserDriver = new BrowserDriver();
        EmailPage emailPage = new EmailPage();
        emailPage.openMessage();
        emailPage.followActivateLink();
        getWebDriver().switchTo().window(browserDriver.getPAGE_ID()).close();
        page.setNewPassword(property.load("recovery.password"));
    }

    @Test // Registration
    public void register() throws IOException, InterruptedException {
        Register_Page register = new Register_Page();
        register.setCity(property.load("city"));
        register.setLastName(property.load("lastName"));
        register.setFirstName(property.load("firstName"));
        register.setSurName(property.load("surName"));
        register.setPhone(property.load("phone"));
        register.setEmail(property.load("email"));
        register.setBirthday(property.load("birthday"));
        register.done();
    }

    @Test // Registration: checking alerts
    public void checkForAlerts() throws IOException, InterruptedException {
        Register_Page registerPage = new Register_Page();
        register();
        registerPage.printSummary();
    }

    @Test // Create EN
    public void createEN() throws IOException, InterruptedException {
        loginAsLoyal();
        CreateEN_Page createEN = new CreateEN_Page();
        createEN.setCounterparty("sender", property.load("s.city"), property.load("s.warehouse"), property.load("s.phone"));
        createEN.setCounterparty("recipient", property.load("r.city"), property.load("r.warehouse"), property.load("r.phone"));
        createEN.setPayer("третя особа", property.load("edrpou"));
        createEN.setPayer("sender", "cash");
        createEN.setDate(property.load("desirableDays")); // Бажана дата доставки - через скільки днів
        createEN.setCargo("документи", "0.1", 2, 125, "Опис відправлення");
        createEN.printDocument("EN", "html"); // EN/ЕН - html/pdf; marking/маркування - html, html-zebra, pdf-zebra
        createEN.goTo("EN");
//        createEN.setRedeliveryObject("гроші");
    }

    //    @Test // Scope multiplication
    public void testScope() throws IOException, InterruptedException {
        for (int i = 0; i < 3; i++) {
            System.out.println("try: " + (i + 1));
            createEN();
            clearState();
            CreateEN_Page createEN_page = new CreateEN_Page();
        }
    }
}
