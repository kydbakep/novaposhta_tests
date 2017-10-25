package ua.novaposhta.test.awis;

import org.junit.Before;
import org.junit.Test;
import ua.novaposhta.test.helper.Assertions;
import ua.novaposhta.test.awis.pages.Auth;
import ua.novaposhta.test.awis.pages.CreateEWPage;
import ua.novaposhta.test.awis.pages.EWListPage;
import ua.novaposhta.test.awis.pages.Main;
import ua.novaposhta.test.properties.Presets;

import java.io.IOException;

public class Awis_CreateEWTest {
    public Awis_CreateEWTest() throws IOException {
    }

    private Main main = new Main();
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

    @Test
    public void createEW() throws IOException, InterruptedException {
        main.moveTo("Документи", "Експрес-накладна");

        CreateEWPage createEWPage = new CreateEWPage();
        // Починаємо створення нової ЕН
        createEWPage.createNew();

        // Параметри відправника
        createEWPage.setCity("Sender", "м. Київ, Київська обл.");
        createEWPage.setCounterParty("Sender", "Приватна особа", "0111111111");
        createEWPage.setAddress("Sender", "Милославська, 31-б");

        // Параметри отримувача
        createEWPage.setCity("Recipient", "м. Бровари, Київська обл., Броварський р-н");
        createEWPage.setCounterParty("Recipient", "Організація", "0633200117");
        createEWPage.setAddress("Recipient", "Шевченка, 167");

        // Параметри платника
        createEWPage.setPayer("відправник", "готівка"); // відправник/отримувач/третя особа | готівка/безготівковий
        createEWPage.setBackwardDeliveryPayer("отримувач");
        createEWPage.setBackwardDeliveryCargo("Інше", "сало в шоколаді"); // Документи/Гроші/Інше/Кредитні документи

        // Параметри відправлення
        createEWPage.setCargoType("Посилка");
        createEWPage.setCargoParameters("1", "25");
        createEWPage.setDescription("соловей в дерев'яній клітці");

        // Присвоєння номера ЕН
        createEWPage.setNewNumber();

        // Збереження ЕН
        createEWPage.writeEN();

        // Оновити список ЕН
        EWListPage listPage = new EWListPage();
        listPage.pressButton("Освіжити");

        // Перевірити чи з'явилася в ньому ЕН з присвоєним раніше номером
        createEWPage.isEWInList();
    }
}
