package ua.novaposhta.test.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.novaposhta.test.helper.Assertions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Web_CreateEWPage {

    public Web_CreateEWPage() {
        String pageURL = "https://my.novaposhta.ua/newOrder/index";
        if (!getWebDriver().getCurrentUrl().equals(pageURL)) {
            Main_Page mainPage = new Main_Page();
            if (mainPage.logged()) { // If we are LOGGED IN
                open(pageURL);
            }
        }
    }

    private Assertions assertions = new Assertions();

    private void checkForRefreshed(WebElement element) {
        try {
            $(element).shouldBe(Condition.disappears);
            $(element).shouldBe(Condition.appears);
        } catch (Throwable th) {
            $(element).shouldBe(Condition.visible);
        }
    }

// SENDER AND RECIPIENT ================================================================================================

    //TODO: make this working and private
    private void startEditCounterparty(String type) {
        if (type.equals("sender")) {
            System.out.println("Обираю відправника");
            $(editSenderButton).shouldBe(Condition.visible).click();
            $(selectCounterPartyModal).shouldBe(Condition.appears);
        } else if (type.equals("recipient")) {
            System.out.println("Обираю отримувача");
            $(editRecipientButton).shouldBe(Condition.visible).click();
            $(selectCounterPartyModal).shouldBe(Condition.appears);
        }
    }

    private void setCity(String city) throws InterruptedException {
        WebElement cityEl = $(By.xpath(".//*[@id='cities_ul']//a[normalize-space(text()) = normalize-space('" + city + "')]"));
        $(By.xpath("//input[@id='filter_journal_cities']//../div")).shouldBe(Condition.visible).click();
        $(cityEl).waitUntil(Condition.visible, 2500);

        while (true) {
            if (assertions.elementIsVisible(citySelected, 2000)) {
                while (!$(citySelected).getText().equals(city)) {
                    $(cityEl).scrollTo().hover().click();
                }
                if ($(citySelected).getText().equals(city)) {
                    System.out.println("Обрано місто: " + $(citySelected).getText());
                    break;
                }
            } else break;
        }

        assert $(citySelected).getText().equals(city);
    }

    private void setWarehouse(String warehouse) {
        $(addressList).waitUntil(Condition.visible, 2500);

        WebElement wHouse = $(By.xpath(".//li[@data-warehouse='true']" +
                "[contains(@data-description,'№" + warehouse + ":') or " + //Двокрапка після номера відділення
                " contains(@data-description,'№ " + warehouse + " ') or " + //Номер відділення в пробілах
                " contains(@data-description,'№" + warehouse + " ') or " + //Номер відділення в пробілах
                " contains(@data-description,'№" + warehouse + " ')]")); //Пробіл після номера відділення
        $(wHouse).scrollTo().hover().click();

        System.out.println("Обрано відділення: " + wHouseSelected.getText());
    }

    private void setContactPersonByPhone(String phone) {
        WebElement personPhone = $(By.xpath("(//ul[@id='contacts_ul']/li[@data-phone='" + phone + "'])[1]"));
        $(personPhone).scrollTo().click();
        System.out.println("Обрано контактну особу: " + contactPersonSelected.getText());
    }

    public void setCounterparty(String type, String city, String warehouse, String phone) throws InterruptedException {
        startEditCounterparty(type);
        setCity(city);
        setWarehouse(warehouse);
        setContactPersonByPhone(phone);
        while (true) {
            $(selectCounterpartyButton).click(); //Питайте у веберів чому. З першого разу не тиснеться ;)
            if (!$(selectCounterPartyModal).isDisplayed()) {
                break;
            }
        }
    }

//END OF SENDER AND RECIPIENT ==========================================================================================

// THIRD PARTY PAYER AND PAYMENT FORM ==================================================================================

    private void setPayer(String payerType) {
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
            WebElement thirdPersonSelectButton = $(By.xpath("//a[@id='ThirdPersonSelectButton']"));
            $(thirdPersonSelectButton).scrollTo().click();
            $(selectThirdPersonModal).waitUntil(Condition.appears, 2000);
        }
    }

    public void setPayer(String payerType, String paymentForm) throws InterruptedException {
        if (paymentForm.equals("cash") || paymentForm.equals("готівка")) {
            $(cash).scrollTo().click();
        } else if (paymentForm.equals("nonCash") || paymentForm.equals("безготівковий") || (paymentForm.equals("non-cash"))) {
            $(nonCash).scrollTo().click();
        }
        setPayer(payerType);
    }

    public void setPayer(String payerType, String paymentForm, String EDRPOU) throws InterruptedException {
        WebElement thirdPartyPayer = $(By.xpath(".//ul[@id='counterparties_ul_counterparty']/li[@id][@data-edrpou='" + EDRPOU + "']"));
        setPayer(payerType, paymentForm);
        WebElement emptyCounterPartyList = $(By.xpath(".//p[@class='alert centered empty_list']"));

        $(selectThirdPersonModal).waitUntil(Condition.visible, 2000);

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

// ENF OF THIRD PARTY PAYER AND PAYMENT FORM ===========================================================================

    // SENDING AND DELIVERY (desired) DATE =================================================================================
    private void setDate(String date) throws InterruptedException {
        $(By.xpath(".//div[contains(@class,'datetimepicker-dropdown')]" +
                "[contains(@style,'display: block;')]" +
                "//td[contains(@class,'day')][not(contains(@class, 'disabled'))]" +
                "[.='" + date + "']"))
                .shouldBe(Condition.visible).click();
    }

    public Date getSendingDate() throws ParseException {
        WebElement sDate = $(By.xpath("//input[@id='DateTime']"));
        String sendingDate = $(sDate).getValue();
        DateFormat format = new SimpleDateFormat("d.M.y", Locale.getDefault());
        return format.parse(sendingDate);
    }

    public void setSendingDate(String days) throws InterruptedException {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, Integer.parseInt(days));
        Date day = calendar.getTime();

        $(dateFrom).scrollTo().click();
        days = String.valueOf(day.getDate());
        setDate(days);
    }

    public void setDeliveryDate(int days) throws InterruptedException, ParseException {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        $(dateTo).scrollTo().click();

        ArrayList<WebElement> list = new ArrayList<>();
        list.addAll(getWebDriver().findElements(By.xpath(".//div[contains(@class,'datetimepicker-dropdown')]" +
                "[contains(@style,'display: block;')]" +
                "//td[@class='day' or @class='day new']")));
        System.out.print("Доступні дати для бажаної доставки: ");
        for (WebElement el : list) {
            System.out.print(el.getText() + " ");
            assert el.isDisplayed();
        }
        $(list.get((days-1))).click();
    }
    public void setDeliveryDate(String days) throws InterruptedException, ParseException {
        setDeliveryDate(Integer.parseInt(days));
    }

// END OF SENDING AND DELIVERY (desired) DATE ==========================================================================

// PARAMETERS OF DEPARTURE =============================================================================================

    public void setCargoType(String cargoType) {
        $(cargoTypeSelectButton).scrollTo().shouldBe(Condition.visible).click();

        String val = "";
        if (cargoType.equals("Cargo") || cargoType.equals("вантаж")) {
            val = "0";
        }
        if (cargoType.equals("Documents") || cargoType.equals("документи")) {
            val = "1";
        }
        if (cargoType.equals("Pallet") || cargoType.equals("палети")) {
            val = "3";
        }
        if (cargoType.equals("TiresWheels") || cargoType.equals("шини-диски")) {
            val = "2";
        }
        if (cargoType.equals("Parcel") || cargoType.equals("посилка")) {
            val = "4";
        }

        WebElement type = $(By.xpath("//ul[@id='cargoTypesSelectBoxItOptions']/li[@data-id='" + val + "']"));
        if ($(type).getAttribute("style").equals("display: none;")) {
            type = $(By.xpath("//span[@id='cargoTypesSelectBoxIt']"));
        }
        $(type).waitUntil(Condition.visible, 2000).shouldBe(Condition.matchesText(type.getText())).click();
    }

    public void setAllTyresWheels(int count) {
        SelenideElement table = $(By.xpath(".//table[@id='tiresWheelsListTable']"));
        List<WebElement> list = table.findElements(By.xpath("//input[contains(@name,'tireWheel')]"));

        for (WebElement el : list) {
            $(el).setValue(String.valueOf(count));
        }
    }

    private void setWeight(String weight) {
        $(totalWeightButton).shouldBe(Condition.visible).click();

        WebElement type = $(By.xpath("//span[@id='cargoTypesSelectBoxIt']"));

        if (type.getText().equals("Документи")) {
            WebElement wh;
            String val = "";

            if (weight.equals("0.1")) {
                val = "1";
            }
            if (weight.equals("0.5")) {
                val = "2";
            }
            if (weight.equals("1")) {
                val = "3";
            } else if (val.equals("")) {
                throw new IllegalArgumentException("Вага документів може складати лише: '0.1','0.5' або '1'");
            }

            wh = $(By.xpath("//ul[@id='WeightDocumentsSelectBoxItOptions']/li[@data-id='" + val + "']"));
            if ($(wh).getAttribute("style").equals("display: none;")) {
                wh = $(By.xpath("//span[@id='WeightDocumentsSelectBoxIt']"));
            }
            $(wh).waitUntil(Condition.visible, 2000).shouldBe(Condition.matchesText(wh.getText())).click();
        }
        if (type.getText().equals("Вантаж") || type.getText().equals("Посилка")) {
            $(totalWeightInput).shouldBe(Condition.visible).setValue(weight);
        }
    }

    private void setSeatsAmount(int amount) {
        $(seatsAmountInput).setValue(String.valueOf(amount));
        System.out.println("Seats amount: " + amount);
    }

    public void setCost(int cost) {
        System.out.println("Cost of one seat: " + cost);
        cost *= Integer.parseInt($(seatsAmountInput).getValue());
        $(costInput).setValue(String.valueOf(cost));
        System.out.println("Total cost: " + cost);
    }

    private void setDescription(String description) {
        if ($(descriptionInput).getValue().isEmpty()) {
            $(descriptionInput).setValue(description);
            System.out.println("Description: " + description);
        } else {
            System.out.println("Description: " + $(descriptionInput).getValue());
        }
    }

    private void setParcel(int width, int length, int heigth, int weigth) {
        WebElement setParametersOfEachPlace = $(By.xpath("//a[@id='buttonOptionsSeat']"));
        WebElement selectOptionsSeatModal = $(By.xpath("//div[@id='selectOptionsSeatModal']"));
        WebElement iWidth = $(By.xpath("(//input[@class='mask-integer_4 optionsSeat_volumetricWidth'])[1]"));
        WebElement iLength = $(By.xpath("(//input[@class='mask-integer_4 optionsSeat_volumetricLength'])[1]"));
        WebElement iHeight = $(By.xpath("(//input[@class='mask-integer_4 optionsSeat_volumetricHeight'])[1]"));
        WebElement iWeight = $(By.xpath("(//input[@class='mask-double_4_2 optionsSeat_weight'])[1]"));
        WebElement confirm = $(By.xpath("//button[@id='selectVolumetricWeight']"));

        $(setParametersOfEachPlace).click();
        $(selectOptionsSeatModal).shouldBe(Condition.appears);

        $(iWidth).waitUntil(Condition.visible, 5000).setValue(String.valueOf(width));
        $(iLength).setValue(String.valueOf(length));
        $(iHeight).setValue(String.valueOf(heigth));
        $(iWeight).setValue(String.valueOf(weigth));
        $(confirm).click();
    }

    public void setCargo(String type, String weight, int seatsAmount, int cost, String description) {
        setCargoType(type);
        if (type.equals("посилка") || type.equals("parcel")) {
            setParcel(100, 100, 100, 50);
            setCost(cost);
            setSeatsAmount(seatsAmount);
            setDescription(description);
        }
        setWeight(weight);
        setSeatsAmount(seatsAmount);
        setCost(cost);
        setDescription(description);
        done();
    }

// END OF PARAMETERS OF DEPARTURE ======================================================================================


// BACKWARD DELIVERY ===================================================================================================

    public void setRedeliveryObject(String object) {
        WebElement redeliveryList = $(By.xpath("//span[@id='redeliveryTypesSelectBoxItArrowContainer']"));
        $(redeliveryList).scrollTo().click();
        String val = "";
        if (object.equals("documents") || object.equals("документи")) {
            val = "1";
        }
        if (object.equals("money") || object.equals("гроші")) {
            val = "2";
        }
        if (object.equals("other") || object.equals("інше")) {
            val = "3";
        }
        WebElement type = $(By.xpath("//ul[@id='redeliveryTypesSelectBoxItOptions']/li[@data-id='" + val + "']"));
        if ($(type).getAttribute("style").equals("display: none;")) {
            type = $(By.xpath("//span[@id='cargoTypesSelectBoxIt']"));
        }
        $(type).waitUntil(Condition.visible, 2000).shouldBe(Condition.matchesText(type.getText())).click();
    }

    public void setBackwardDelivery(String object) {
    }

// END OF BACKWARD DELIVERY ============================================================================================

    public void done() {
        $(createDocumentButton).shouldBe(Condition.enabled).click();
        $(documentCreatedModal).shouldBe(Condition.appears);
        $(documentInfo).shouldBe(Condition.appears).shouldBe(Condition.visible);
        System.out.println("Document created: " + $(documentInfo).getText());
    }

    public void printDocument(String type, String format) throws InterruptedException {
        $(printMenu).shouldBe(Condition.visible).click();
        if (type.equals("EN") || type.equals("ЕН")) {
            if (format.equals("html")) {
                $(printEN_HTML).shouldBe(Condition.visible).click();
            } else if (format.equals("pdf")) {
                $(printEN_PDF).shouldBe(Condition.visible).click();
            }
        }
        if (type.equals("marking") || type.equals("маркування")) {
            if (format.equals("html")) {
                $(printMarkings_HTML).shouldBe(Condition.visible).click();
            }
            if (format.equals("html-zebra")) {
                $(printMarkings_HTML_Zebra).shouldBe(Condition.visible).click();
            } else if (format.equals("pdf-zebra")) {
                $(printMarkings_PDF_Zebra).shouldBe(Condition.visible).click();
            }
        }
//        getWebDriver().switchTo().window(tabs.get(1));
//        getWebDriver().close();
        ArrayList<String> tabs = new ArrayList<>(getWebDriver().getWindowHandles());
        getWebDriver().switchTo().window(tabs.get(0));
    }

    public void goTo(String action) {
        if (action.equals("EN")) {
            $(documentCreatedButton).click();
            $(documentCreatedModal).waitUntil(Condition.disappears, 2000);
        }
        if (action.equals("list")) {
            $(documentListButton).click();
        }
        if (action.equals("next")) {
            $(createNextENButton).click();
        }
        if (action.equals("sms")) {
            $(sendSMSWithNumberButton).click();
        }
    }


//======================================================================================================================

    // Main
    private WebElement editSenderButton = $(By.xpath(".//a[@id='SenderSelectButton']"));
    private WebElement editRecipientButton = $(By.xpath(".//a[@id='RecipientSelectButton']"));
    private WebElement createDocumentButton = $(By.xpath("//input[@id='submitNewOrderButton']"));


    // City
    private WebElement citySelected = $(By.xpath("//*[@id='cities_ul']/li[@class='browser_element_main active']"));

    // Warehouse
    private WebElement addressList = $(By.xpath(".//ul[@id='address_ul']"));
    private WebElement wHouseSelected = $(By.xpath("//*[@id='address_ul']/li[@class='browser_element_main active']"));

    // Contact person
    private WebElement contactPersonSelected = $(By.xpath("//*[@id='contacts_ul']/li[@class='browser_element_main  active']")); // Зайвий пробіл в коді сторінки

    // CounterParty frame
    private WebElement selectCounterpartyButton = $(By.xpath("//button[@id='selectCounterpartyButton']"));
    private WebElement selectCounterPartyModal = $(By.xpath("//div[@id='selectCounterpartyModal']"));

    // Payer
    private WebElement payerIsSender = $(By.xpath("//button[@id='SenderPayer']"));
    private WebElement payerIsRecipient = $(By.xpath("//button[@id='RecipientPayer']"));

    // Third party payer block
    private WebElement selectThirdPersonModal = $(By.xpath("//div[@id='selectThirdPersonModal']"));
    private WebElement counterPartyAddPayerButton = $(By.xpath(".//a[@id='createCounterpartyThirdPerson']"));
    private WebElement counterpartyEDRPOU = $(By.xpath(".//input[@id='counterpartyEDRPOU']"));
    private WebElement counterPartyAddCreateButton = $(By.xpath(".//button[@id='counterpartyThirdPersonSaveButton']"));
    private WebElement confirmChooseButton = $(By.xpath(".//button[@id='selectCounterpartyOnlyButton']"));

    //  Send date
    private WebElement dateFrom = $(By.xpath(".//*[@id='dateFrom']/span[@class='add-on btn btn-calendar']"));
    //  Desirable delivery date and time
    private WebElement dateTo = $(By.xpath(".//*[@id='dateTo']/span[@class='add-on btn btn-calendar']"));

    //  Параметри відправлення (тип вантажу)
    private WebElement cargoTypeSelectButton = $(By.xpath(".//span[@id='cargoTypesSelectBoxItArrowContainer']"));

    //  Параметри відправлення (загальна вага)
    private WebElement totalWeightInput = $(By.xpath(".//input[@id='Weight']"));
    private WebElement totalWeightButton = $(By.xpath(".//span[@id='WeightDocumentsSelectBoxItText']"));

    //  Параметри відправлення (об'ємна вага)
    private WebElement volumeWeightInput = $(By.xpath(".//input[@id='Weight']"));

    //  Параметри відправлення (загальний об'єм відправлення)
    private WebElement volumeGeneralInput = $(By.xpath(".//input[@id='VolumeGeneral']"));

    //  Параметри відправлення (кількість місць)
    private WebElement seatsAmountInput = $(By.xpath(".//input[@id='seatsAmount']"));

    //  Параметри відправлення (оголошена вартість)
    private WebElement costInput = $(By.xpath(".//input[@id='cost']"));

    //  Параметри відправлення (опис відправлення)
    private WebElement descriptionInput = $(By.xpath(".//input[@id='Description']"));

    // Document created
    private WebElement documentCreatedModal = $(By.xpath("//div[@id='documentCreatedModal']"));
    private WebElement documentInfo = $(By.xpath("//div[@id='documentCreatedModal']/div[@class='modal-body']/p"));
    private WebElement documentNumber = $(By.xpath("(//div[@id='documentCreatedModal']/div[@class='modal-body']/p/b)[1]"));
    private WebElement documentCreatedButton = $(By.xpath("//a[@id='createdDocumentLink']"));
    private WebElement documentListButton = $(By.xpath("//a[@href='/orders/index/filter/clear']"));
    private WebElement createNextENButton = $(By.xpath("//a[@id='cloneDocumentLink']"));
    private WebElement sendSMSWithNumberButton = $(By.xpath("//a[@id='sendSMS']"));

    private WebElement printButton = $(By.xpath("//div[@id='printButtonsGroup']/a"));
    private WebElement printMenu = $(By.xpath("//div[@id='printButtonsGroup']/button"));
    private WebElement printEN_PDF = $(By.xpath("//a[@data-url='printDocument'][@target][@data-type='pdf']"));
    private WebElement printEN_HTML = $(By.xpath("//a[@data-url='printDocument'][@target][@data-type='html']"));
    private WebElement printMarkings_HTML = $(By.xpath("//a[@data-url='printDocument'][@target][@data-type='html']"));
    private WebElement printMarkings_PDF_Zebra = $(By.xpath("//a[@data-url='printMarkings'][@target][contains(@title,'зебра')][@data-type='pdf']"));
    private WebElement printMarkings_HTML_Zebra = $(By.xpath("//a[@data-url='printMarkings'][@target][contains(@title,'зебра')][@data-type='html']"));

    // Payment forms
    private WebElement cash = $(By.xpath("//button[@data-type='Cash']"));
    private WebElement nonCash = $(By.xpath("//button[@data-type='NonCash']"));

    // Additional Services

}
