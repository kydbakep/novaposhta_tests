package ua.novaposhta.test.awis;

import org.junit.Before;
import org.junit.Test;
import ua.novaposhta.test.awis.helper.Actions;
import ua.novaposhta.test.awis.helper.Assertions;
import ua.novaposhta.test.awis.pages.Auth;
import ua.novaposhta.test.awis.pages.CreateEWPage;
import ua.novaposhta.test.awis.pages.Main;
import ua.novaposhta.test.awis.properties.Presets;

import java.io.IOException;

public class Awis_CreateEWTest {
    public Awis_CreateEWTest() throws IOException {}

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

    @Test
    public void createEW() throws IOException {
        Main main = new Main();
        main.moveTo("Документи", "Експрес-накладна");

        CreateEWPage createEWPage = new CreateEWPage();
        createEWPage.createNew();
        createEWPage.setCity("Sender","м. Київ, Київська обл.");
    }
}
