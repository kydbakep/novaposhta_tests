package ua.novaposhta.test.awis;

import org.junit.Before;
import org.junit.Test;
import ua.novaposhta.test.helper.Assertions;
import ua.novaposhta.test.awis.pages.Auth;
import ua.novaposhta.test.awis.pages.Awis_CreateEWPage;
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

        Awis_CreateEWPage awisCreateEWPage = new Awis_CreateEWPage();
        // Починаємо створення нової ЕН
        awisCreateEWPage.createNew();

        // Параметри відправника
        awisCreateEWPage.setCity("Sender", "м. Київ, Київська обл.");
        awisCreateEWPage.setCounterParty("Sender", "Приватна особа", "0111111111");
        awisCreateEWPage.setAddress("Sender", "Милославська, 31-б");

        // Параметри отримувача
        awisCreateEWPage.setCity("Recipient", "м. Бровари, Київська обл., Броварський р-н");
        awisCreateEWPage.setCounterParty("Recipient", "Організація", "0633200117");
        awisCreateEWPage.setAddress("Recipient", "Шевченка, 167");

        // Параметри платника
        awisCreateEWPage.setPayer("відправник", "готівка"); // відправник/отримувач/третя особа | готівка/безготівковий
        awisCreateEWPage.setBackwardDeliveryPayer("отримувач");
        awisCreateEWPage.setBackwardDeliveryCargo("Інше", "сало в шоколаді"); // Документи/Гроші/Інше/Кредитні документи

        // Параметри відправлення
        awisCreateEWPage.setCargoType("Посилка");
        awisCreateEWPage.setCargoParameters("1", "25");
        awisCreateEWPage.setDescription("соловей в дерев'яній клітці");

        // Присвоєння номера ЕН
        awisCreateEWPage.setNewNumber();

        // Збереження ЕН
        awisCreateEWPage.writeEN();

        // Оновити список ЕН
        EWListPage listPage = new EWListPage();
        listPage.pressButton("Освіжити");

        // Перевірити чи з'явилася в ньому ЕН з присвоєним раніше номером
        awisCreateEWPage.isEWInList();
    }
}
