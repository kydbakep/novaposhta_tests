package ua.novaposhta.test.web.pages;

import General.PropertyLoader;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class Register_Page {

    PropertyLoader propery = new PropertyLoader("web");

    public Register_Page() throws IOException {
        String pageURL = "https://my.novaposhta.ua/registration/index";
        Main_Page main = new Main_Page();
        if (!main.logged()) {
            open(pageURL);
        }
    }

    public void setCity(String city) {
        $(citySelectBox).click();
        $(cityBoxOpened).shouldBe(Condition.appears);
        $(By.xpath(".//li/a[.='" + city + "']")).shouldBe(Condition.visible).scrollTo().click();
    }

    public void setLastName(String lastName) {
        $(lastNameInput).setValue(lastName);
    }

    public void setFirstName(String firstName) {
        $(firstNameInput).setValue(firstName);
    }

    public void setSurName(String middleName) {
        $(middleNameInput).setValue(middleName);
    }

    public void setPhone(String phone) {
        $(phoneInput).hover().setValue(phone);
    }

    public void setEmail(String email) {
        $(emailInput).setValue(email);
    }

    public void setBirthday(String birthday) {
        $(calendarButton).scrollTo().click();

        String year = birthday.split("\\.")[2];
        String month = birthday.split("\\.")[1];
        String day = birthday.split("\\.")[0];

        $(years).shouldBe(Condition.visible);

        WebElement targetYear = $(By.xpath("//span[contains(@class,'year')][.='" + year + "']"));
        while (true) {
            if ($(targetYear).exists()) {
                $(targetYear).click();
                break;
            } else {
                $(yearsBackButton).click();
            }
        }
        WebElement months = $(By.xpath("(.//div[@class='datetimepicker-months']//tbody//td/span)[" + month + "]"));
        WebElement days = $(By.xpath("//div[@class='datetimepicker-days']//td[@class='day'][.='" + day + "']"));
        $(months).shouldBe(Condition.appears).click();
        $(days).shouldBe(Condition.appears).click();
    }

    public void iHaveCode(String code) throws IOException {
        $(enterCodeLink).shouldBe(Condition.visible).click();
        $(activationPhoneInput).setValue(propery.load("phone"));
        $(activationCodeInput).setValue(code);
        $(registerButton).click();
    }

    public void printSummary(){
        System.out.println($(summary).getText());
    }

    public void done(){
        $(registerButton).scrollTo().click();
    }

//======================================================================================================================

    // City
    private WebElement citySelectBox = $(By.xpath("//span[@id='NovaposhtaRegistrationRequestForm_CityRefSelectBoxItText']"));
    private WebElement cityBoxOpened = $(By.xpath("//ul[@id='NovaposhtaRegistrationRequestForm_CityRefSelectBoxItOptions']"));

    // Registration fields
    private WebElement lastNameInput = $(By.xpath(".//input[@id='NovaposhtaRegistrationRequestForm_LastName']"));
    private WebElement firstNameInput = $(By.xpath(".//input[@id='NovaposhtaRegistrationRequestForm_FirstName']"));
    private WebElement middleNameInput = $(By.xpath(".//input[@id='NovaposhtaRegistrationRequestForm_MiddleName']"));
    private WebElement phoneInput = $(By.xpath(".//input[@id='NovaposhtaRegistrationRequestForm_Phone']"));
    private WebElement emailInput = $(By.xpath(".//input[@id='NovaposhtaRegistrationRequestForm_Email']"));

    // Calendar
    private WebElement calendarButton = $(By.xpath(".//span[@class='add-on btn btn-calendar-registration']"));
    private WebElement years = $(By.xpath("//span[contains(@class,'year')]"));
    private WebElement yearsBackButton = $(By.xpath(".//div[@class='datetimepicker-years']//i[@class='icon-arrow-left']"));

    // Final
    private WebElement enterCodeLink = $(By.xpath("//a[@href='/registration/activation']"));
    private WebElement activationPhoneInput = $(By.xpath("//input[@id='ActivateRegistrationRequestForm_Phone']"));
    private WebElement activationCodeInput = $(By.xpath(""));
    private WebElement registerButton = $(By.xpath("//input[@type='submit'][@name]"));

    // Error summary
    private WebElement summary = $(By.xpath("//div[@class='errorSummary']"));
}
