package ua.novaposhta.test.web;

import org.junit.Before;
import org.junit.Test;
import ua.novaposhta.test.properties.Presets;
import ua.novaposhta.test.properties.PropertyLoader;
import ua.novaposhta.test.web.methods.BrowserDriver;
import ua.novaposhta.test.web.pages.*;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Web_ClientTests {

    public Web_ClientTests() throws IOException {
    }

    private PropertyLoader property = new PropertyLoader("web");
    private Presets presets = new Presets();

    @Before
    public void loadPresets() throws IOException {
        presets.loadPresets();
    }

    //    @After
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
    public void createEW() throws IOException, InterruptedException, ParseException {
        //INFO: Tested and working
        loginAsLoyal();
        Web_CreateEWPage createEW = new Web_CreateEWPage();
        createEW.setCounterparty("sender", property.load("s.city"), property.load("s.warehouse"), property.load("s.phone"));
        createEW.setCounterparty("recipient", property.load("r.city"), property.load("r.warehouse"), property.load("r.phone"));
        //info

        //Платник
//        createEW.setPayer("третя особа", "безготівковий", property.load("edrpou"));
        //BUG: Злітає фокус з 'безготівковий' при виборі платника 'відправник' після вибору форми оплати
//        createEW.setPayer("sender", "non-cash");
        //bug

        //INFO: Tested and working
        createEW.setSendingDate(property.load("desirableDays")); // Дата відправки
        createEW.setDeliveryDate(property.load("desirableDays")); // Бажана дата доставки - через скільки днів
        createEW.setDeliveryDate(2); // Бажана дата доставки - через скільки днів
        //info

        createEW.setCargo("документи", "0.1", 2, 125, "Опис відправлення");
        createEW.printDocument("EN", "html"); // EN/ЕН - html/pdf; marking/маркування - html, html-zebra, pdf-zebra
        createEW.goTo("EN");
//        createEW.setRedeliveryObject("гроші");
    }

        @Test // Scope multiplication
    public void testScope() throws IOException, InterruptedException, ParseException {
        for (int i = 0; i < 3; i++) {
            System.out.println("try: " + (i + 1));
            createEW();
            clearState();
//            CreateEN_Page createEW_page = new CreateEN_Page();
        }
    }
}
