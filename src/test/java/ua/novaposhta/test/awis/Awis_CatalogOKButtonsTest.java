package ua.novaposhta.test.awis;

import com.codeborne.selenide.ex.ElementNotFound;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ua.novaposhta.test.awis.pages.Auth;
import ua.novaposhta.test.awis.pages.Main;
import ua.novaposhta.test.helper.Assertions;
import ua.novaposhta.test.helper.Awis_Actions;
import ua.novaposhta.test.properties.Presets;

import java.io.IOException;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.$;

public class Awis_CatalogOKButtonsTest {
    public Awis_CatalogOKButtonsTest() throws IOException {
    }

    @Rule
    public TestName name = new TestName();

    private Main main = new Main();
    private Awis_Actions action = new Awis_Actions();
    private Assertions assertion = new Assertions();
    private Presets presets = new Presets();
    private String DATABASE = "awis.test";

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

    private void findAndClickOKButton(String tableName) throws IOException, SQLException, InterruptedException {
        action.choiceFirstActualWrite(DATABASE, tableName);
        try {
            action.clickOKButton();
        } catch (ElementNotFound notFound) {
            action.clickOKButton(1500);
        }
    }

    @Test
    public void conglomerates() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Конгломерати");
        findAndClickOKButton("CatalogConglomerates");
    }

    @Test
    public void counterparties() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Контрагенти");
        findAndClickOKButton("CatalogCounterparties");
    }

    @Test
    public void cashRegisters() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Каси");
        findAndClickOKButton("CatalogClientCashDesks");
    }

    @Test
    public void cashRegistersCatalogue() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Реєстр комірок поштоматів");
        findAndClickOKButton("CatalogPostomatCells");
    }

    @Test
    public void cashRegistersReservedCatalogue() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Реєстр заброньованих комірок поштоматів");
        WebElement el = $(By.xpath("(//tr[contains(@class,'x-grid-row')][not(contains(@class,'header'))]/td[6]/div[text()>0])[1]"));
        $(el).doubleClick();
        if (assertion.alert(500)){
            action.acceptAlert();
            action.clickOKButton();
        }
    }

    @Test
    public void fiscalRegisters() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Фіскальні реєстратори");
        String tableName = "CatalogFiscalRegisters";
        findAndClickOKButton(tableName);
    }

    @Test
    public void cashRegistersForOperators() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Каси операторів");
        String tableName = "CatalogOperatorCashDesks";
        findAndClickOKButton(tableName);
    }

    @Test
    public void settlements_settlements() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Населені пункти", "Населені пункти");
        String tableName = "CatalogSettlements";
        findAndClickOKButton(tableName);
    }

    @Test
    public void settlements_administrativeStructure() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Населені пункти", "Адміністративний устрій");
        String tableName = "CatalogSettlementsHierarchical";
        findAndClickOKButton(tableName);
    }

    @Test
    public void settlements_types() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Населені пункти", "Типи населених пунктів");
        String tableName = "CatalogSettlementTypes";
        findAndClickOKButton(tableName);
    }

    @Test
    public void settlements_regionTypes() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Населені пункти", "Типи регіонів");
        String tableName = "CatalogCountryRegionTypes";
        findAndClickOKButton(tableName);
    }

    @Test
    public void settlements_streets() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Населені пункти", "Вулиці");
        String tableName = "CatalogSettlementStreets";
        findAndClickOKButton(tableName);
    }

    @Test
    public void settlements_buildings() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Населені пункти", "Будівлі");
        String tableName = "CatalogStreetBuildings";
        findAndClickOKButton(tableName);
    }

    @Test
    public void settlements_streetTypes() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Населені пункти", "Типи вулиць");
        String tableName = "CatalogSettlementStreetTypes";
        findAndClickOKButton(tableName);
    }

    @Test
    public void settlements_indexTypes() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Населені пункти", "Типи поштових індексів");
        String tableName = "CatalogPostcodeTypes";
        findAndClickOKButton(tableName);
    }

    @Test
    public void warehouses() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Склади");
        findAndClickOKButton("CatalogWarehouses");
    }

    @Test
    public void operationNormsOnWarehouse() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Норми операцій на склад");
        findAndClickOKButton("CatalogWarehouseOperationNorms");
    }

    @Test
    public void operationTypesOnWarehouse() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Види операцій на складі");
        findAndClickOKButton("CatalogWarehouseOperationTypes");
    }

    @Test
    public void warehouseZonesForRecipient() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Зони складів для складу отримувача");
        findAndClickOKButton("CatalogIntercityZonesForWarehouseRecipient");
    }

    @Test
    public void valueCollections() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Набори значень");
        findAndClickOKButton("CatalogSetsOfValues");
    }

    @Test
    public void warehouseAreas() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Ділянки складів");
        findAndClickOKButton("CatalogWarehouseParts");
    }

    @Test
    public void cityAreas() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Райони міста");
        findAndClickOKButton("CatalogWarehouseParts");
    }

    @Test
    public void cargoCategories() throws InterruptedException, IOException {
        main.moveTo("Довідники", "Категорії вантажу");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void loyalty_clientCards() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Лояльність. Картки клієнтів");
        findAndClickOKButton("CatalogCustomerLoyaltyCard");
    }

    @Test
    public void autoScales() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Автоматичні ваги");
        findAndClickOKButton("CatalogAutomaticScales");
    }

    @Test
    public void IP_cameras() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "IP камери");
        findAndClickOKButton("CatalogIPCameras");
    }

    @Test
    public void informTemplates() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Шаблони інформування");
        findAndClickOKButton("CatalogInformingTemplates");
    }

    @Test
    public void write_ofParametersCMV() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Параметры списания ТМЦ");
        findAndClickOKButton("CatalogMaterialAssetsAccountingParameters");
    }

    @Test
    public void areaRedefining() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Перевизначення районів");
        findAndClickOKButton("CatalogCityDistrictsOverride");
    }

    @Test
    public void parametersOfNonStandardEWBasedOf() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Параметри нестандартних ЕН на підставі");
        findAndClickOKButton("CatalogCustomBackwardDeliveryParameters");
    }

    @Test
    public void wallets() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Грошові гаманці");
        findAndClickOKButton("CatalogMoneyWallets");
    }

    @Test
    public void conditions() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Умови");
        findAndClickOKButton("CatalogConditions");
    }

    @Test
    public void cargoTrackingStatuses() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Статуси відстеження вантажу");
        findAndClickOKButton("CatalogTrackingStatuses");
    }

    @Test
    public void cargoTrackingTemplates() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Шаблони відстеження вантажу");
        findAndClickOKButton("CatalogTrackingTemplates");
    }

    @Test
    public void cargoTrackingRequestTemplates() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Шаблони відстеження вантажу");
        findAndClickOKButton("CatalogRequestForTransportationTemplates");
    }

    @Test
    public void attorneys() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Довіреності");
        findAndClickOKButton("CatalogAttorneys");
    }

    @Test
    public void banks() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Банки");
        findAndClickOKButton("CatalogBanks");
    }

    @Test
    public void pickUpServices() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Сервіси PickUp");
        findAndClickOKButton("CatalogPickUpServices");
    }

    @Test
    public void serviceConstructor() throws InterruptedException, IOException, SQLException {
        main.moveTo("Довідники", "Конструктор послуг");
        findAndClickOKButton("CatalogConditionsOfProvidedServices");
    }


    //======================================================================================================================
//    @Test
    public void clickActualFolder() throws IOException, SQLException, InterruptedException {
        main.moveTo("Довідники", "Перевизначення районів");
        String tableName = "CatalogCityDistrictsOverride";
        action.choiceFirstActualWrite(DATABASE, tableName);
        action.clickOKButton();
    }

    //    @Test
    public void loggingTest() throws IOException, SQLException, InterruptedException {
        main.moveTo("Довідники", "Фізичні особи");
        String tableName = "CatalogStaffs";
        action.choiceFirstActualWrite(DATABASE, tableName);
        action.clickOKButton();
    }
}



