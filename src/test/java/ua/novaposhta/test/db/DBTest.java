package ua.novaposhta.test.db;

import org.junit.Before;
import org.junit.Test;
import ua.novaposhta.test.helper.DataBase;
import ua.novaposhta.test.properties.Presets;
import ua.novaposhta.test.web.pages.EmailPage;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBTest {
    public DBTest() throws IOException {
    }
    private Presets presets = new Presets();

    @Before
    public void configure() throws IOException {
        presets.loadPresets();
    }

    @Test
    public void printPassword() throws IOException, SQLException {
        DataBase db = new DataBase("web");
        System.out.println(db.getPassword("50cdd"));
    }

    @Test
    public void printCode() throws IOException, SQLException {
        DataBase db = new DataBase("awis");
        System.out.println(db.getCode("380633200117"));
    }

    @Test
    public void email() throws AWTException {
        EmailPage emailPage = new EmailPage();
    }

    @Test
    public void printRecoveryCode() throws IOException, SQLException {
        DataBase db = new DataBase("web");
        ArrayList<String> codes = new ArrayList<>();
        codes.addAll(db.getResponse("Code", "wloyaltycardpasswordresetrequest", "Phone", "380633200117"));
        System.out.println(codes.get(codes.size()-1));
    }

//    @Test
//    public void takeActiveElement() throws IOException, SQLException {
//        DataBase db = new DataBase("awis.test");
//        String response = db.getResponse("SELECT Description FROM CatalogCityDistrictsOverride WHERE Ref = " +
//                "(SELECT Parent FROM CatalogCityDistrictsOverride " +
//                "WHERE DeletionMark = FALSE " +
//                "AND IsFolder = FALSE " +
//                "AND Parent <> '00000000-0000-0000-0000-000000000000' " +
//                "LIMIT 1)").get(0);
//
//        System.out.println(response);
//    }

    @Test
    public void validFolderGet() throws SQLException, IOException {
        DataBase db = new DataBase("awis.test");
        System.out.println(db.getValidFolder("InfoRegPostomatCellsUsed"));
    }

}
