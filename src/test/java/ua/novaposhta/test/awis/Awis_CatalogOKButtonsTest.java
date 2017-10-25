package ua.novaposhta.test.awis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.novaposhta.test.helper.Actions;
import ua.novaposhta.test.helper.Assertions;
import ua.novaposhta.test.awis.pages.Auth;
import ua.novaposhta.test.awis.pages.Main;
import ua.novaposhta.test.properties.Presets;

import java.io.IOException;

public class Awis_CatalogOKButtonsTest {
    public Awis_CatalogOKButtonsTest() throws IOException {
    }

    private Main main = new Main();
    private Actions action = new Actions();
    private Assertions assertion = new Assertions();
    private Presets presets = new Presets();

    @Before
    public void loadPresets() throws IOException {
        presets.loadPresets();
        if (!assertion.loggedIn()) {
            Auth auth = new Auth();
            auth.logIn();
        }
    }

    @After
    public void closeTabs() throws InterruptedException {
        action.closeAllFrames();
        action.closeAllTabs();
    }

    @Test
    public void conglomerates() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Конгломерати");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void counterparties() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Контрагенти");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void cashRegisters() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Каси");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void cashRegistersCatalogue() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Реєстр комірок поштоматів");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void cashRegistersReservedCatalogue() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Реєстр заброньованих комірок поштоматів");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void fiscalRegisters() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Фіскальні реєстратори");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void cashRegistersForOperators() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Каси операторів");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void settlements_settlements() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Населені пункти", "Населені пункти");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void settlements_administrativeStructure() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Населені пункти", "Адміністративний устрій");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void settlements_types() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Населені пункти", "Типи населених пунктів");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void settlements_regionTypes() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Населені пункти", "Типи регіонів");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void settlements_streets() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Населені пункти", "Вулиці");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void settlements_buildings() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Населені пункти", "Будівлі");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void settlements_indexTypes() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Населені пункти", "Типи поштових індексів");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void warehouses() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Склади");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void warehouseAreas() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Ділянки складів");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void operationNormsOnWarehouse() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Норми операцій на склад");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void operationTypesOnWarehouse() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Види операцій на складі");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void warehouseZonesForRecipient() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Зони складів для складу отримувача");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void cargoCategories() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Категорії вантажу");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void valueCollections() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Ділянки складів");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void loyalty_clientCards() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Лояльність. Картки клієнтів");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void autoScales() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Автоматичні ваги");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void IP_cameras() throws InterruptedException, IOException {
        main.moveTo("Довідники", "IP камери");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void informTemplates() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Шаблони інформування");
        action.choiceTableElement(15);
        action.clickOKButton();
    }

    @Test
    public void write_ofParametersCMV() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Параметры списания ТМЦ");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void areaRedefining() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Перевизначення районів");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void parametersOfNonStandardEWBasedOf() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Параметри нестандартних ЕН на підставі");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void wallets() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Грошові гаманці");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void conditions() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Умови");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void cargoTrackingTemplates() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Шаблони відстеження вантажу");
        action.choiceTableElement(7);
        action.clickOKButton();
    }

    @Test
    public void proxies() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Довіреності");
        action.choiceTableElement(3);
        action.clickOKButton();
    }

    @Test
    public void banks() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Банки");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void pickUpServices() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Сервіси PickUp");
        action.choiceTableElement(2);
        action.clickOKButton();
    }

    @Test
    public void serviceConstructor() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Конструктор послуг");
        action.choiceTableElement(8);
        action.clickOKButton();
    }

}


