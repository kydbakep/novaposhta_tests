package ua.novaposhta.test.awis.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ua.novaposhta.test.awis.helper.Actions;

import java.io.IOException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CreateEWPage {

    public CreateEWPage() throws IOException {
    }

    private Actions a = new Actions();

    public boolean isLoad() {
        WebElement tab = $(By.xpath("//span[.='ЕН: нова']"));
        return tab.isDisplayed();
    }

    // Обираємо вид ЕН (Покищо не доступно)
    public CreateEWPage(String type) {
        if (type.equals("RMO")) {
            System.out.println("new EN for RMO");
            WebElement tab = $(By.xpath("(//span[.='ЕН: нова'])[1]"));
            $(tab).waitUntil(visible, 3000);
        }
    }

    // Починаємо створення ЕН. Клікаємо кнопку "Створити" і очікуємо на відкриття відповідної вкладки
    public void createNew() {
        WebElement createButton = $(By.xpath("//span[.='Створити']"));
        System.out.println("Starting creation of new EW...");
        $(createButton).waitUntil(visible, 2000).click();
        WebElement tab = $(By.xpath("(//span[.='ЕН: нова'])[1]"));
        $(tab).waitUntil(Condition.appears, 2000);
    }

    public void setNewNumber() {
        WebElement newNumber = $(By.xpath("//span[contains(@id,'newNumber')][@class='x-btn-inner']"));
        $(newNumber).waitUntil(visible, 2000).click();
        WebElement number = $(By.xpath("//input[@name='Number']"));
        $(number).waitUntil(not(empty), 2000);
    }

    public void writeEN() {
        WebElement writeButton = $(By.xpath("//span[.='Записати'][last()]"));

        WebElement number = $(By.xpath("//input[@name='Number']"));
        WebElement tab = $(By.xpath("//span[contains(text(),'" + $(number).getValue() + "')][last()]"));

        $(writeButton).click(); // Записали
        $(tab).waitUntil(appears, 2000);

        System.out.println("EW created: " + tab.getText());

        WebElement OkButton = $(By.xpath("//span[contains(@id,'buttonWriteAndClose-btnIconEl')][last()]"));
        $(OkButton).click(); // ОКнули

        $(tab).waitUntil(disappears, 3000);
    }

// SENDER/RECIPIENT BLOCK ==============================================================================================

    // Обираємо місто
    public void setCity(String counterPartyType, String fullname) { // counterPartyType - Sender/Recipient
        WebElement cityInput = $(By.xpath("(//div[contains(@id,'City" + counterPartyType + "-bodyEl')]/input)[last()]"));

        $(cityInput).hover().click();
        $(cityInput).clear();

        $(cityInput).setValue(fullname.split(",")[0]).hover().sendKeys(" "); // Вводимо назву міста
        WebElement citiesList = $(By.xpath("(//div[@class='list-ct x-body-masked'])[last()]"));
        $(citiesList).shouldBe(Condition.appears); // Повинен з'явитись список

        WebElement targetCity = $(By.xpath("//div[contains (@id, 'boundlist') and not (contains(@style, 'display'))]/div//li[.='" + fullname + "']"));
        $(targetCity).waitUntil(visible, 1000).scrollTo().click();

        System.out.println(counterPartyType + " city: " + cityInput.getAttribute("value"));
    }

    // Обираємо контрагента
    // CreateEN classic
    public void setCounterParty(String counterParty, String type, String phone) throws InterruptedException {
        setCPHeader(counterParty, type, phone);
        setCPFooter(counterParty);
    }

    // Обираємо контрагента-організацію
    // CreateEN classic
    public void setCounterParty(String counterParty, String type, String phone, String name) throws InterruptedException {
        setCPHeader(counterParty, type, phone);
        setCPInjection(name);
        setCPFooter(counterParty);
    }

    private void setCPHeader(String counterParty, String type, String phone) {
        WebElement setCPButton = $(By.xpath("(//input[@name='Counterparty" + counterParty + "']/../div/div[1])[last()]"));
        WebElement cpTypeDialog = $(By.xpath("(//div[contains(@id,'CounterpartyTypeDialog')][contains(@class,'window-closable')])[last()]"));
        $(setCPButton).waitUntil(visible, 2000);
        while (true) {
            if (!cpTypeDialog.isDisplayed()) {
                try {
                    $(setCPButton).shouldBe(exist).click();
                    $(cpTypeDialog).shouldBe(appears);
                } catch (Throwable throwable) {
//                    System.out.println("\n catch: " + throwable.getMessage());
                    $(setCPButton).click();
                    $(cpTypeDialog).shouldBe(appears);
                }

            } else {
                break;
            }
        }
        WebElement cpType = $(By.xpath("(//span[.='" + type + "'])[last()]"));
        while (true) {
            if (cpTypeDialog.isDisplayed()) {
                try {
                    $(cpType).click();
                    $(cpTypeDialog).shouldBe(disappears);
                } catch (Throwable throwable) {
                    System.out.println(throwable.getMessage());
                    $(cpType).click();
                    $(cpTypeDialog).shouldBe(disappears);
                }
            } else break;
        }
        WebElement phoneNumber = $(By.xpath("//input[@name='PhoneNumber']"));
        $(phoneNumber).waitUntil(Condition.appear, 1000);
        $(phoneNumber).setValue(phone).pressEnter();
    }

    private void setCPInjection(String name) {
        WebElement cpName = $(By.xpath("(//div[.='" + name + "'])[last()]"));
        $(cpName).waitUntil(appears, 1000).doubleClick();
    }

    private void setCPFooter(String counterParty) {
        WebElement cpSelectionFrame = $(By.xpath("(//div[contains(@class, 'window-closable')]//span[contains(text(),'Пошук')])[last()]"));
        $(cpSelectionFrame).waitUntil(disappears, 1000);
        WebElement contact = $(By.xpath("//input[@name='" + counterParty + "Name']"));
        $(contact).waitUntil(Condition.not(empty), 1000);
        WebElement contactPhone = $(By.xpath("//input[@name='Phone" + counterParty + "']"));
        System.out.print(counterParty + " is: " + $(contact).getValue() + "; phone: " + $(contactPhone).getValue());
    }

    // Обираємо контрагента
    // CreateEN RMO
    public void setCParty(String counterParty, String phone) {
        WebElement setCPButton = $(By.xpath("(//input[@name='Counterparty" + counterParty + "']/../div/div[1])[last()]"));
        WebElement findCPFrame = $(By.xpath("(//div[contains(@class,'x-window-closable')]/div[contains(@id,'header')]//span[@class])[last()]"));

        try {
            $(setCPButton).waitUntil(visible, 2000).hover();
            $(setCPButton).click();
            $(findCPFrame).waitUntil(appears, 2000);
        } catch (ElementNotFound notFound) {
            $(setCPButton).waitUntil(visible, 2000).hover();
            $(setCPButton).click();
            $(findCPFrame).waitUntil(appears, 2000);
        }

        WebElement phoneInput = $(By.xpath("(//input[@name='PhoneNumber'])[last()]"));
        $(phoneInput).setValue(phone).pressEnter();
    }

    // Обираємо адресу
    public void setAddress(String counterPartyType, String address) throws InterruptedException {
        WebElement addressChooseButton = $(By.xpath("//input[@name='" + counterPartyType + "Address']/../div/div[1]"));
        WebElement addressesTab = $(By.xpath("//span[.='Адреси']"));

        try {
            do {
                $(addressChooseButton).click();
            } while (!addressesTab.isDisplayed());
        } catch (Throwable th) {
            WebElement tab = $(By.xpath("//span[.='Адреси']"));
            $(tab).waitUntil(Condition.visible, 1000).getText();
            System.out.println("\n exc: tab '" + tab.getText() + "' is opened");
        }

        AddressPage addressPage = new AddressPage();
        addressPage.fillAddressField(address);
        addressPage.selectAddressFromTable(address);

        WebElement addressField = $(By.xpath("//input[@name='" + counterPartyType + "Address']"));
        $(addressField).waitUntil(not(empty), 2000);
        System.out.println("; address: " + $(addressField).getValue());

        // Маршрут
        checkRoute();
    }

    private void checkRoute() {
        WebElement routeInput = $(By.xpath("//input[@name='Route']"));
        System.out.println("route: " + $(routeInput).getValue());
    }

    // Обираємо платника та форму розрахунку
    private void payerIs(String payer) {
        WebElement payerDropDown = $(By.xpath("//input[@name='PayerType']/../../input/../..//div[contains(@class,'arrow')]"));
        WebElement payerTypeSender = $(By.xpath("//div[contains(@id,'boundlist')]//li[.='Відправник']"));
        WebElement payerTypeRecipient = $(By.xpath("//div[contains(@id,'boundlist')]//li[.='Отримувач']"));
        WebElement payerTypeThirdPerson = $(By.xpath("//div[contains(@id,'boundlist')]//li[.='Третя особа']"));

        $(payerDropDown).click();
        $(payerTypeSender).waitUntil(appears, 2000);
        if (payer.equals("відправник")) {
            System.out.println("setting payer: sender");
            $(payerTypeSender).shouldBe(visible).hover().click();
            $(payerTypeSender).waitUntil(disappears, 2000);
        }
        if (payer.equals("отримувач")) {
            System.out.println("setting payer: recipient");
            $(payerTypeRecipient).shouldBe(visible).hover().click();
            $(payerTypeRecipient).waitUntil(disappears, 2000);
        }
        if (payer.equals("третя особа")) {
            System.out.println("setting payer: third person");
            $(payerTypeThirdPerson).shouldBe(visible).hover().click();
            $(payerTypeThirdPerson).waitUntil(disappears, 2000);
        }
        if (payer.equals("")) {
            System.out.println("payer: default");
            $(payerDropDown).click();
            $(payerTypeSender).shouldBe(disappears);
        }
    }

    private void setPayerByEDRPOU(String EDRPOU) throws InterruptedException {
        WebElement thirdPartyPayerSelectButton = $(By.xpath("//div[contains(@id,'CounterpartyThirdPerson')]/div[@role='button'][contains(@class,'choice')]"));
        $(thirdPartyPayerSelectButton).click();
        CounterPartiesPage counterParties = new CounterPartiesPage();
        counterParties.setByEDRPOU(EDRPOU);

        WebElement thirdPartyPayerInput = $(By.xpath("//div[contains(@id,'CounterpartyThirdPerson')]/../input"));
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until(ExpectedConditions.attributeToBeNotEmpty(thirdPartyPayerInput, "value"));

        String nam = counterParties.name.trim();
        String val = $(thirdPartyPayerInput).getValue().trim();
        assert nam.equals(val);
    }

    private void paymentFormIs(String paymentForm) {
        WebElement paymentFormDropDown = $(By.xpath("//input[@name='PaymentMethod']/../../input/../..//div[contains(@class,'arrow')]"));
        WebElement paymentFormCash = $(By.xpath("//div[contains(@id,'boundlist')]//li[.='Готівка']"));
        WebElement paymentFormNonCash = $(By.xpath("//div[contains(@id,'boundlist')]//li[.='Безготівковий']"));

        $(paymentFormDropDown).click();
        $(paymentFormCash).waitUntil(appears, 2000);
        if (paymentForm.equals("готівка")) {
            System.out.println("setting payment form: cash");
            $(paymentFormCash).shouldBe(visible).hover().click();
            $(paymentFormCash).waitUntil(disappears, 2000);
        }
        if (paymentForm.equals("безготівковий")) {
            System.out.println("setting payment form: non cash");
            $(paymentFormNonCash).shouldBe(visible).hover().click();
            $(paymentFormNonCash).waitUntil(disappears, 2000);
        }
    }

    public void setPayer(String payer, String payment) {
        payerIs(payer);
        paymentFormIs(payment);
    }

    public void setPayer(String payerType, String payment, String EDRPOU) throws InterruptedException {
        payerIs(payerType);
        paymentFormIs(payment);
        setPayerByEDRPOU(EDRPOU);
    }

// END OF SENDER/RECIPIENT BLOCK =======================================================================================

// BACKWARD/INTERNATIONAL DELIVERY BLOCK ===============================================================================

    public void setBackwardDeliveryPayer(String payer) { // payer: відправник/отримувач
        WebElement backwardCheckbox = $(By.xpath("//div[contains(@id,'checkbox')]/label[.='Зворотня доставка:']/../div/input"));
        WebElement payerIsSenderRadio = $(By.xpath("//label[.='Відправник']/../input"));
        WebElement payerIsRecipientRadio = $(By.xpath("//label[.='Отримувач']/../input"));

        WebElement selectPayerFrame = $(By.xpath("//span[.='Оберіть платника звор. доставки']"));
        WebElement selectPayerFrameCloser = $(By.xpath("//span[.='Оберіть платника звор. доставки']/../../div[@role='button']/img[@class='x-tool-close']"));

        $(backwardCheckbox).click();
        $(selectPayerFrame).shouldBe(appears);
        $(selectPayerFrameCloser).click();
        $(selectPayerFrame).shouldBe(disappears);

        if (payer.equals("відправник")) {
            $(payerIsSenderRadio).click();
            System.out.println("backward delivery payer: sender");
        }
        if (payer.equals("отримувач")) {
            $(payerIsRecipientRadio).click();
            System.out.println("backward delivery payer: recipient");
        }
    }

    public void setBackwardDeliveryCargo(String cargo, String description) {
        WebElement cargoTypeDropDownActivator = $(By.xpath("//div[contains(@id,'GridBackwardDeliveryData-body')]//td[2]"));
        $(cargoTypeDropDownActivator).hover().doubleClick();

        WebElement cargoType = $(By.xpath("//li[@role='option'][.='" + cargo + "']"));
        $(cargoType).click();
        $(cargoType).shouldBe(disappears);

        WebElement deliveryDescription = $(By.xpath("//div[contains(@id,'GridBackwardDeliveryData-body')]//td[3]"));

        if (cargo.equals("Гроші")) {
            WebElement sumFrame = $(By.xpath("//div[contains(@id,'PaymentCardsWindow-body')]"));
            $(sumFrame).shouldBe(appears);
            WebElement sumInput = $(By.xpath("//div[contains(@id,'PaymentCardsWindow-body')]//input[@name='SumMoney']"));
            $(sumInput).setValue("100");
            WebElement buttonOK = $(By.xpath("//div[contains(@id,'PaymentCardsWindow-body')]//button[1]"));
            $(buttonOK).click();
            $(sumFrame).waitUntil(disappears, 1000);
        }
        if (cargo.equals("Документи") || cargo.equals("Інше")) {
            $(deliveryDescription).hover().doubleClick();
            WebElement deliveryDescriptionInput = $(By.xpath("//div[contains(@id,'GridBackwardDeliveryDataDescription-bodyEl')]/input"));
            $(deliveryDescriptionInput).setValue(description).pressEnter();
        }

        System.out.println("description: " + $(deliveryDescription).getText());
    }
// END OF BACKWARD/INTERNATIONAL DELIVERY BLOCK ========================================================================

// ABOUT CARGO INFORMATION BLOCK =======================================================================================

    public void setCargoType(String cargoType) {
        WebElement menu = $(By.xpath("(//div[contains(@id,'CargoType')]//div[@role='button'][1])[1]"));
        WebElement type = $(By.xpath("(//li[@role='option'][contains(@class,'x-boundlist-item')][.='" + cargoType + "'])[last()]"));
        $(menu).shouldBe(visible).click();
        $(type).waitUntil(visible, 1000).hover().click();
    }

    public void setCargoParameters(String count, String weight) throws InterruptedException {
        WebElement menu = $(By.xpath("(//div[contains(@id,'CargoType-bodyEl')]/input)[1]"));
        String menuItem = $(menu).getValue();
        WebElement seatsAmount = $(By.xpath("//div[contains(@class,'x-form-item-body')]/input[@name='SeatsAmount']"));
        WebElement weightInput = $(By.xpath("//div[contains(@class,'x-form-item-body')]/input[@name='DocumentWeight']"));
        WebElement weightFactInput = $(By.xpath("//div[contains(@class,'x-form-item-body')]/input[@name='FactualWeight']"));
        WebElement weightFactRadio = $(By.xpath("//label[.='Фактична']/../input"));
        WebElement weightVolumeInput = $(By.xpath("//div[contains(@class,'x-form-item-body')]/input[@name='VolumetricWeight']"));
        WebElement weightVolumeRadio = $(By.xpath("//label[.=\"Об'ємна\"]/../input"));
        WebElement cargoCost = $(By.xpath("//input[@name='Cost']"));

        switch (menuItem) {
            case "Посилка":
                System.out.println("Вид вантажу: " + menuItem);
                do {
                    $(seatsAmount).click();
                } while (!$(seatsAmount).is(focused));
                $(seatsAmount).setValue(count);

                do {
                    $(weightInput).click();
                } while (!$(weightInput).is(focused));
                $(weightInput).setValue(weight);

                $(weightFactRadio).click();
                $(weightFactRadio).shouldBe(focused);
                $(weightFactInput).setValue(weight);

                // Встановлюємо об'ємну вагу більшу за фактичну. Перевіряємо чи збільшилося значення у полі "Вага" (очікуємо, що так).
                $(weightVolumeRadio).hover().click();
                $(weightVolumeInput).setValue(String.valueOf(Integer.parseInt(weight) + 1)).pressEnter();
                do {
                    $(weightInput).waitUntil(visible, 1000);
                } while (Integer.parseInt($(weightInput).getValue()) == Integer.parseInt(weight));
                System.out.println("weight: " + weight + "; new weight: " + $(weightInput).getValue());

                $(cargoCost).click();
                $(cargoCost).shouldBe(focused).setValue("1000");
                break;
            case "Документи":
                System.out.println("Вид вантажу: " + menuItem);
                do {
                    $(seatsAmount).click();
                } while (!$(seatsAmount).is(focused));
                $(seatsAmount).setValue(count);

                do {
                    $(weightInput).click();
                } while (!$(weightInput).is(focused));
                $(weightInput).setValue(weight);

                $(cargoCost).click();
                $(cargoCost).shouldBe(focused).setValue("1000");
                break;
            case "Палети":
                System.out.println("Вид вантажу: " + menuItem);
                do {
                    $(seatsAmount).click();
                } while (!$(seatsAmount).is(focused));
                $(seatsAmount).setValue(count).pressTab();

                WebElement paramsFrame = $(By.xpath("(//span[.='Введіть параметри вантажу'])[1]"));
                $(paramsFrame).waitUntil(appears, 1000);
                WebElement add = $(By.xpath("(//span[.='Додати'])[last()]"));

                WebElement fill = $(By.xpath("//button[contains(@id,'fillAllRowsLikeFirst')]"));
                WebElement ok = $(By.xpath("//button[span='Ок']"));

                $(add).click();
                String expand = "//div[contains(@id,'MeasuresGrid-body')]/div[contains(@id,'gridview')]";
                $(By.xpath(expand)).click();
                expand += "/table";
                $(By.xpath(expand)).click();
                expand += "/tbody";
                $(By.xpath(expand)).click();
                expand += "/tr[contains(@class,'x-grid')][not(contains(@class,'header'))]";
                $(By.xpath(expand)).click();
                expand = "(" + expand + ")[1]";

                WebElement wid = $(By.xpath(expand + "/td[2]"));
                WebElement len = $(By.xpath(expand + "/td[3]"));
                WebElement hei = $(By.xpath(expand + "/td[4]"));
                WebElement wei = $(By.xpath(expand + "/td[5]"));
                WebElement vol = $(By.xpath(expand + "/td[7]"));

                $(wid).waitUntil(visible, 1000);
                $(wid).doubleClick();
                $(By.xpath("//input[@name='Width']")).setValue("50");
                $(len).doubleClick();
                $(By.xpath("//input[@name='Length']")).setValue("50");
                $(hei).doubleClick();
                $(By.xpath("//input[@name='Height']")).setValue("50");
                $(wei).doubleClick();
                $(By.xpath("//input[@name='ActualWeight']")).setValue("50").pressEnter();

                $(vol).waitUntil(Condition.not(empty), 1000);
                System.out.println("Об'ємна вага: " + vol.getText());

                $(fill).click();
                $(ok).click();
                $(paramsFrame).shouldBe(disappears);

                $(cargoCost).click();
                System.out.println("weight: " + $(weightInput).getValue());
                $(cargoCost).shouldBe(focused).setValue("14999").waitUntil(Condition.not(empty), 1000);
                break;
        }
    }

    public void setDescription(String description) {
        WebElement cargoDescriptionInput = $(By.xpath("//input[@name='CargoDescriptionString']"));
        $(cargoDescriptionInput).setValue(description).pressEnter();
        try {
            while (true) {
                $(cargoDescriptionInput).getValue();
                if ($(cargoDescriptionInput).getValue().length() > 0) {
                    Thread.sleep(500);
                    if ($(cargoDescriptionInput).getValue().length() > 0) {
                        break;
                    }
                } else {
                    throw new Exception("description '" + description + "' not in database");
                }
            }
            System.out.println("description: '" + $(cargoDescriptionInput).getValue() + "'");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            description = "відеокамера";
            WebElement cargoDescriptionSelectButton = $(By.xpath("(//input[@name='CargoDescriptionString']/../div[@role]/div[@role='button'])[1]"));
            $(cargoDescriptionSelectButton).click();
            CargoDescriptionsPage descriptionsPage = new CargoDescriptionsPage();
            descriptionsPage.find(description);

            WebElement descriptionElement = $(By.xpath("(//div[.='" + description + "'])[1]"));
            $(descriptionElement).shouldBe(visible).doubleClick();
            WebElement descriptionTab = $(By.xpath("(//span[.='Описи вантажів'])[last()]"));
            $(descriptionTab).shouldBe(disappears);
            System.out.println("new test description: '" + $(cargoDescriptionInput).getValue() + "'");
        }
    }

// END OF ABOUT CARGO INFORMATION BLOCK ================================================================================

// ADDITIONAL SERVICES BLOCK ===========================================================================================

    public void toPalette(int count) throws InterruptedException {
        WebElement paletteCheckbox = $(By.xpath("//label[.='Палетування:']/..//input"));
        $(paletteCheckbox).shouldBe(visible).click();
        WebElement paletteCount = $(By.xpath("//input[@name='PalletsAmount']"));
        $(paletteCount).waitUntil(Condition.enabled, 1000).clear();
        $(paletteCount).setValue(String.valueOf(count));

        try {
            WebElement alert = $(By.xpath("(//div[contains(@id,'messagebox')])[last()]"));
            if (alert.isDisplayed()) {
                String message = alert.getText();
                WebElement accept = $(By.xpath("(//span[.='OK'])[last()]"));
                accept.click();
                throw new IllegalArgumentException(message);
            }
        } catch (ElementNotFound notFound) {
            System.out.println("no alert in toPalette() " + notFound.getMessage());
        }
    }

    public void toHands() {
        WebElement toHandsCheckbox = $(By.xpath("//label[.='Особисто в руки:']/..//input"));
        $(toHandsCheckbox).shouldBe(visible).click();
    }

    public void lift(String state, int level) { // state: up/down

        if (state.equals("up")) {
            liftUp(level);
        }
        if (state.equals("down")) {
            liftDown(level);
        } else if (!state.equals("up") && !state.equals("down")) {
            throw new IllegalArgumentException("state must be 'up' or 'down'");
        }
    }

    private void liftUp(int toLevel) {
        System.out.println("moving UP to " + toLevel + " level");
        WebElement level = $(By.xpath("//input[@name='NumberOfFloorsLifting']"));
        $(level).setValue(String.valueOf(toLevel));
        WebElement checkbox = $(By.xpath("//input[@type='button'][@role='checkbox'][contains(@aria-describedby,'ElevatorRecipient')]"));
        $(checkbox).click();
    }

    private void liftDown(int fromLevel) {
        System.out.println("moving DOWN from " + fromLevel + " level");
        WebElement level = $(By.xpath("//input[@name='NumberOfFloorsDescent']"));
        $(level).setValue(String.valueOf(fromLevel));
        WebElement checkbox = $(By.xpath("//input[@type='button'][@role='checkbox'][contains(@aria-describedby,'ElevatorSender')]"));
        $(checkbox).click();
    }

    public void itIsExpressPallet() {
        WebElement expressPalletCheckbox = $(By.xpath("//label[.='Експрес палета']/../input"));
        $(expressPalletCheckbox).click();
    }

    public void setDeliveryTime(String interval) {
        WebElement selectButton = $(By.xpath("//input[contains(@aria-describedby,'DeliveryTimeframe')]/..//div[contains(@class,'x-trigger')]"));
        WebElement timeInterval = $(By.xpath("//li[@role='option'][.='Без часових інтервалів']"));
        $(selectButton).click();
        $(timeInterval).waitUntil(appears, 1000);

        try {
            if (interval.equals("9-12")) {
                timeInterval = $(By.xpath("//li[@role='option'][.='09:00 - 12:00']"));
                $(timeInterval).click();
            }
            if (interval.equals("12-15")) {
                timeInterval = $(By.xpath("//li[@role='option'][.='12:01 - 15:00']"));
                $(timeInterval).click();
            }
            if (interval.equals("15-18")) {
                timeInterval = $(By.xpath("//li[@role='option'][.='15:01 - 18:00']"));
                $(timeInterval).click();
            }
            if (interval.equals("18-21")) {
                timeInterval = $(By.xpath("//li[@role='option'][.='18:01 - 21:00']"));
                $(timeInterval).click();
            }
        } catch (ElementNotFound notFound) {
            System.out.println(notFound.getMessage());
            timeInterval = $(By.xpath("//li[@role='option'][.='Без часових інтервалів']"));
            $(timeInterval).click();
        }

        $(timeInterval).shouldBe(disappears);

        WebElement time = $(By.xpath("//input[contains(@aria-describedby,'DeliveryTimeframe')]"));
        System.out.println("delivery time interval: " + $(time).getValue());
    }

    public void setPaymentControl(String summa) {
        WebElement paymentControlInput = $(By.xpath("//input[@name='AfterpaymentOnGoodsCost']"));
        $(paymentControlInput).setValue(summa);
    }

    public void setDownTime(String minutes) {
        WebElement downTimeInput = $(By.xpath("//input[@name='Downtime']"));
        $(downTimeInput).setValue(minutes);
    }

// END OF ADDITIONAL SERVICES BLOCK ====================================================================================


// OTHER METHODS BLOCK =================================================================================================

    public void tryToWrite(String number) throws InterruptedException {
        EWListPage listPage = new EWListPage();
        listPage.choiceEN(number);
        WebElement writeButton = $(By.xpath("//span[.='Записати'][last()]"));
        try {
            WebElement warningFrame = $(By.xpath("//div[contains(text(),'Документ відкрито вами в іншому вікні!')]"));
            String warning = $(warningFrame).getText();
            System.out.println("red alert: " + warning);
            throw new IllegalMonitorStateException("Red alert is present. Can't click 'write' button!");
        } catch (ElementNotFound notFound) {
            $(writeButton).waitUntil(Condition.visible, 1000).click();
            a.acceptAlert();
        }
    }

    public void write() {
        System.out.print("writing...");
        WebElement writeButton = $(By.xpath("//span[.='Записати'][last()]"));
        $(writeButton).click();
        WebElement requestIndicator = $(By.xpath("//div[@id='requestIndicator']/div[@id='requestIndicator-bar']/div"));
        $(requestIndicator).shouldBe(Condition.appears);
        assert $(requestIndicator).getText().equals("Зачекайте...");
        $(requestIndicator).waitUntil(Condition.disappears, 2000);
        System.out.println("OK");
        try {
            Alert alert = getWebDriver().switchTo().alert();
            System.out.println("Accepting alert: " + alert.getText());
            alert.accept();
        } catch (NoAlertPresentException noAlert) {
            System.out.println("all is OK. No alert");
        }
    }

    public void writeAndClose() throws InterruptedException {

        WebElement close = $(By.xpath("//span[contains(@id,'buttonWriteAndClose')]"));
        $(close).click();
        a.acceptAlert();

        WebElement activeTabCloseButton = $(By.xpath("//div[contains(@class,'x-tab-active')]/a"));
        $(activeTabCloseButton).click();
        a.acceptAlert();
    }

    public void close() throws IOException {
        WebElement activeTabEW = $(By.xpath("//div[contains(@class,'x-tab-active')]//span[contains(text(),'ЕН ')][contains(text(),'від ')]"));
        WebElement activeTabEWCloseButton = $(By.xpath("//div[contains(@class,'x-tab-active')]//span[contains(text(),'ЕН ')][contains(text(),'від ')]/../../../a"));
        assert $(activeTabEW).isDisplayed();
        assert $(activeTabEWCloseButton).isDisplayed();

        $(activeTabEWCloseButton).click();
        $(activeTabEW).waitUntil(disappears, 1000);
    }

// OTHER METHODS BLOCK =================================================================================================
}