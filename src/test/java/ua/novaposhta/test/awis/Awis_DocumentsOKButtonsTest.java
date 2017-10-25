package ua.novaposhta.test.awis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.novaposhta.test.awis.helper.Actions;
import ua.novaposhta.test.awis.helper.Assertions;
import ua.novaposhta.test.awis.pages.Auth;
import ua.novaposhta.test.awis.pages.Main;
import ua.novaposhta.test.properties.Presets;

import java.io.IOException;

public class Awis_DocumentsOKButtonsTest {
    public Awis_DocumentsOKButtonsTest() throws IOException {
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
    public void rollScan() throws InterruptedException, IOException {
        main.moveTo("Документи", "Відомість сканування");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void acceptanceTransferAct() throws InterruptedException, IOException {
        main.moveTo("Документи", "Акт приймання-передачі відповідальності");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void expressWaybill() throws InterruptedException, IOException {
        main.moveTo("Документи", "Експрес-накладна");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void moneyTransfer() throws InterruptedException, IOException {
        main.moveTo("Документи", "Грошовий переказ");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void additionalServices() throws InterruptedException, IOException {
        main.moveTo("Документи", "Додаткові послуги");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void clientOrder() throws InterruptedException, IOException {
        main.moveTo("Документи", "Замовлення клієнта");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void partnersTradePlatformSettings() throws InterruptedException, IOException {
        main.moveTo("Документи", "Встановлення налаштувань торгівельних площадок партнерів");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void cargoProblem() throws InterruptedException, IOException {
        main.moveTo("Документи", "Проблема з вантажем");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void workSchedule() throws InterruptedException, IOException {
        main.moveTo("Документи", "Плановий графік роботи");
        action.choiceTableElement(1);
        action.clickOKButton();
    }

    @Test
    public void logistic_additionalServiceOrder() throws InterruptedException, IOException {
        main.moveTo("Документи", "Логістика", "Замовлення на додаткові послуги");
        action.choiceTableElement(1);
        action.clickOKButton();
    }
}


