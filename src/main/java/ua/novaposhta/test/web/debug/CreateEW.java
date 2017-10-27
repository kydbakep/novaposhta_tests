package ua.novaposhta.test.web.debug;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.novaposhta.test.helper.Assertions;
import ua.novaposhta.test.web.pages.Main_Page;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CreateEW {
    public CreateEW() {
        String pageURL = "https://my.novaposhta.ua/newOrder/index";
        if (!getWebDriver().getCurrentUrl().equals(pageURL)) {
            Main_Page mainPage = new Main_Page();
            if (mainPage.logged()) { // If we are LOGGED IN
                open(pageURL);
            }
        }
    }

    private Assertions assertions = new Assertions();

    public void setPayer(String payerType) {
        if (payerType.equals("sender") || payerType.equals("відправник")) {
            $(payerIsSender).click();
        }
        if (payerType.equals("recipient") || payerType.equals("отримувач")) {
            $(payerIsRecipient).click();
        }
        if (payerType.equals("third person") || payerType.equals("третя особа")) {

            WebElement payerIsThirdPerson = $(By.xpath("//button[@id='ThirdPersonPayer']"));
            $(payerIsThirdPerson).click();
            WebElement thirdPersonInfoBlock = $(By.xpath("//dl[@id='ThirdPersonInfoTable']"));
            $(thirdPersonInfoBlock).shouldBe(Condition.appears);
            $(thirdPersonSelectButton).scrollTo().click();
            $(selectThirdPersonModal).waitUntil(Condition.appears, 2000);
        }
    }

    public void setPayer(String payerType, String paymentForm) {

        if (paymentForm.equals("cash") || paymentForm.equals("готівка")) {
            $(cash).click();
        } else if (paymentForm.equals("nonCash") || paymentForm.equals("безготівковий") || (paymentForm.equals("non-cash"))) {
            $(nonCash).click();
        }
        setPayer(payerType);
    }

    public void setPayer(String payerType, String paymentForm, String EDRPOU) throws InterruptedException {
        WebElement thirdPartyPayer = $(By.xpath(".//ul[@id='counterparties_ul_counterparty']/li[@id][@data-edrpou='" + EDRPOU + "']"));
        setPayer(payerType, paymentForm);
        WebElement emptyCounterPartyList = $(By.xpath(".//p[@class='alert centered empty_list']"));

        $(selectThirdPersonModal).waitUntil(Condition.visible,2000);

        if (assertions.elementIsVisible(emptyCounterPartyList, 1000)) {
            createThirdPartyPayer(EDRPOU);
        }

        $(thirdPartyPayer).waitUntil(Condition.visible, 1000).scrollTo().hover().click();
        $(confirmChooseButton).click();
    }

    private void createThirdPartyPayer(String EDRPOU) throws InterruptedException {
        System.out.println("Створення платника...");
        WebElement thirdPartyPayer = $(By.xpath(".//ul[@id='counterparties_ul_counterparty']/li[@id][@data-edrpou='" + EDRPOU + "']"));

        $(counterPartyAddPayerButton).click();
        $(counterpartyEDRPOU).shouldBe(Condition.appears).setValue(EDRPOU);
        $(counterPartyAddCreateButton).click();

        try {
            $(counterpartyEDRPOU).shouldBe(Condition.disappears);
        } catch (Throwable throwable) {
            System.out.println(throwable.getMessage());
            while (true) {
                if (counterpartyEDRPOU.isDisplayed()) {
                    Thread.sleep(100);
                } else break;
            }
        }

        System.out.println("Створено та обрано: " + thirdPartyPayer.getText() + "; ЄДРПОУ: " + EDRPOU);
        $(thirdPartyPayer).click();
        $(confirmChooseButton).click();
    }

    // Payer
    private WebElement payerIsSender = $(By.xpath("//button[@id='SenderPayer']"));
    private WebElement payerIsRecipient = $(By.xpath("//button[@id='RecipientPayer']"));


    // Third party payer block
    private WebElement thirdPersonSelectButton = $(By.xpath("//a[@id='ThirdPersonSelectButton']"));
    private WebElement selectThirdPersonModal = $(By.xpath("//div[@id='selectThirdPersonModal']"));

    private WebElement counterPartyAddPayerButton = $(By.xpath(".//a[@id='createCounterpartyThirdPerson']"));
    private WebElement counterpartyEDRPOU = $(By.xpath(".//input[@id='counterpartyEDRPOU']"));
    private WebElement counterPartyAddCreateButton = $(By.xpath(".//button[@id='counterpartyThirdPersonSaveButton']"));
    private WebElement confirmChooseButton = $(By.xpath(".//button[@id='selectCounterpartyOnlyButton']"));

    // Payment forms
    private WebElement cash = $(By.xpath("//button[@data-type='Cash']"));
    private WebElement nonCash = $(By.xpath("//button[@data-type='NonCash']"));
}
