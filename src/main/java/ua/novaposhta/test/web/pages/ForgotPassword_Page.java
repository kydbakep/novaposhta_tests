package ua.novaposhta.test.web.pages;

import ua.novaposhta.test.helper.DataBase;
import ua.novaposhta.test.properties.PropertyLoader;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ForgotPassword_Page {

    private PropertyLoader property = new PropertyLoader("web");
    private String EMAIL = property.load("recovery.email");
    private String PHONE = property.load("phone");

    public ForgotPassword_Page() throws IOException {
        String pageURL = "https://my.novaposhta.ua/auth/forgotPassword";
        if (!getWebDriver().getCurrentUrl().equals(pageURL)) {
            Main_Page mainPage = new Main_Page();
            if (!mainPage.logged()) {
                open(pageURL);
            }
        }
    }

    public String getEmail() {
        return EMAIL;
    }
    public String getPhone() {
        return PHONE;
    }

    private String getPersonalNumber() throws IOException, SQLException {
        DataBase db = new DataBase("awis");
        return db.getCode(getPhone());
    }

    private boolean weAreOnPage() {
        return $(cardNumberMessage).isDisplayed();
    }

    private void setCardNumber() throws IOException, SQLException {
        if (weAreOnPage()){
            $(cardNumberInput).shouldBe(Condition.visible).setValue(getPersonalNumber());
            $(submitButton).click();
        }
    }
    private void setCardNumber(String number) {
        if (weAreOnPage()) {
            $(cardNumberInput).shouldBe(Condition.visible).setValue(number);
            $(submitButton).click();
            if (!summary.getText().isEmpty()) {
                throw new IllegalArgumentException(summary.getText());
            }
        }
    }

    public void insertCardNumber() {
        try {
            setCardNumber();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public void insertCardNumber(String number) {
        try {
            setCardNumber(number);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void forgotNumber(String phoneNumber) {
        $(resetButton).shouldBe(Condition.visible).click();
        $(phoneEnterFormMessage).shouldBe(Condition.appears);
        $(phoneEnterFormInput).setValue(phoneNumber);
        $(sendButton).click();
    }

    private void sendMessageSMS() throws IOException {
        WebElement radioSMS = $(By.xpath("//input[@type='radio'][@id='phone']"));
        WebElement phoneInput = $(By.xpath("//input[@id='forgotPasswordPhone']"));
        $(radioSMS).shouldBe(Condition.visible).click();
        $(phoneInput).shouldBe(Condition.appears).setValue(property.load("phone"));
    }
    private void sendMessageEmail(){
        WebElement radioEmail = $(By.xpath("//input[@type='radio'][@id='email']"));
        $(radioEmail).shouldBe(Condition.visible).click();
        WebElement emailInput = $(By.xpath("//input[@name='restoreFieldValueCheck']"));
        $(emailInput).setValue(EMAIL);
    }
    public void sendMessage(String to) throws IOException {
        if (to.equals("sms") || to.equals("SMS")){
            sendMessageSMS();
        } else if (to.equals("email") || to.equals("Email")){
            sendMessageEmail();
        }
        $(sendButton).click();
        WebElement alertSuccess = $(By.xpath("//div[@class='alert alert-success']"));
        $(alertSuccess).shouldBe(Condition.appears);
        System.out.println(alertSuccess.getText());
    }

    public void setCode() throws IOException, SQLException {
        DataBase db = new DataBase("web");
        ArrayList<String> codes = new ArrayList<>();
        codes.addAll(db.getResponse("Code", "wloyaltycardpasswordresetrequest", "Phone", "380633200117"));
        WebElement codeInput = $(By.xpath("//input[@id='ActivatePasswordResetRequestForm_Code']"));
        $(codeInput).setValue(codes.get(codes.size()-1));
        $(submitButton).click();
        $(newEmailInput).shouldBe(Condition.appears);
    }

    public void setNewEmail() throws IOException {
        $(newEmailInput).setValue(property.load("recovery.email"));
        $(submitButton).click();
    }
    public void setNewEmail(String email) throws IOException {
        $(newEmailInput).setValue(email);
        $(submitButton).click();
    }

    public void setNewPassword(String password) throws IOException {
        PropertyLoader property = new PropertyLoader("web");
        handleActiveTab();
        WebElement passwordInput = $(By.xpath("//input[@id='PasswordResetForm_password']"));
        WebElement passwordConfirmInput = $(By.xpath("//input[@id='PasswordResetForm_passwordConfirm']"));
        $(passwordInput).waitUntil(Condition.visible,2000);
        WebElement alertSuccess = $(By.xpath("//div[@class='alert alert-success']"));
        System.out.println(alertSuccess.getText());
        $(passwordInput).setValue(password); $(passwordConfirmInput).setValue(password);
        $(submitButton).click();
        handleActiveTab();
        System.out.println(alertSuccess.getText());
        handleActiveTab();
        WebElement loginField = $(By.xpath("//input[@id='LoginForm_username']"));
        WebElement passwordField = $(By.xpath("//input[@id='LoginForm_password']"));
        $(loginField).setValue(property.load("recovery.email"));
        $(passwordField).setValue(property.load("recovery.password"));
        $(submitButton).click();
        $(By.xpath("//span[.='t.test@i.ua']")).waitUntil(Condition.visible,2000).shouldBe(Condition.visible);
        System.out.println("Login with new password - OK. Test done.");
    }
    private void handleActiveTab(){
        for (String handle : getWebDriver().getWindowHandles()){
            getWebDriver().switchTo().window(handle); // Переходимо на останню активну вкладку
        }
    }

//======================================================================================================================

    private WebElement cardNumberMessage = $(By.xpath("//form[@class='form-horizontal shadowed_box']/h3"));
    private WebElement cardNumberInput = $(By.xpath("//input[@id='ForgotPasswordForm_Card']"));
    private WebElement submitButton = $(By.xpath("//input[@type='submit'][@class]"));
    private WebElement sendButton = $(By.xpath("//input[@type='submit'][@name]"));
    private WebElement resetButton = $(By.xpath("//input[@type='button'][@class='reset ']"));
    private WebElement phoneEnterFormMessage = $(By.xpath("//form[@action='/auth/forgotCard']/h3"));
    private WebElement phoneEnterFormInput = $(By.xpath("//form[@action='/auth/forgotCard']//input[@id]"));

    // Activate new Email
    private WebElement newEmailInput = $(By.xpath("//input[@id='ActivateEmailForm_email']"));

    // Error summary
    private WebElement summary = $(By.xpath("//div[@class='errorSummary']"));
}
