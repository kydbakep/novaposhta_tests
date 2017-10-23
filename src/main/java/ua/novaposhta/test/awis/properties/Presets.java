package ua.novaposhta.test.awis.properties;

import com.codeborne.selenide.Configuration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Presets {
    private PropertyLoader browser = new PropertyLoader("browser");

    public Presets() throws IOException {
    }

    public void loadPresets() throws IOException {
        Configuration.browser = browser.load("browser");
        String os = System.getProperty("os.name");
        System.out.println("operating system is: " + os);
        if (os.toLowerCase().contains("windows")) {
            URL inputUrl = getClass().getResource("/drivers/chromedriver.exe");

            File driver = new File("C:\\Windows\\Temp\\chromedriver.exe");
            System.out.print("creating 'chromedriver' in 'Temp' folder...");
            FileUtils.copyURLToFile(inputUrl, driver);

            System.setProperty("webdriver.chrome.driver", driver.getAbsolutePath());
            getWebDriver().manage().window().maximize();

            driver.deleteOnExit();

        }
        if (os.toLowerCase().endsWith("nux") || os.toLowerCase().endsWith("nix") || os.toLowerCase().endsWith("bsd")) {

//            Set<PosixFilePermission> permission = new HashSet<>();
//            permission.add(PosixFilePermission.OWNER_READ);
//            permission.add(PosixFilePermission.OWNER_WRITE);
//            permission.add(PosixFilePermission.OWNER_EXECUTE);
//
//            permission.add(PosixFilePermission.GROUP_READ);
//            permission.add(PosixFilePermission.GROUP_WRITE);
//            permission.add(PosixFilePermission.GROUP_EXECUTE);
//
//            permission.add(PosixFilePermission.OTHERS_READ);
//            permission.add(PosixFilePermission.OTHERS_WRITE);
//            permission.add(PosixFilePermission.OTHERS_EXECUTE);
//
//            URL inputUrl = getClass().getResource("/drivers/chromedriver");

//            try {
//                File driver = new File("/tmp/chromedriver");
//                System.out.print("creating 'chromedriver' in '/tmp' folder...");
//                FileUtils.copyURLToFile(inputUrl, driver);
//                System.out.print("OK\nSetting permissions for " + driver.getAbsolutePath() + "...");
//                Files.setPosixFilePermissions(driver.toPath(), permission);
//                System.out.println("OK");

                System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
//                driver.deleteOnExit();

//            } catch (FileNotFoundException notFound){
//                System.out.println(notFound.getMessage());
//            }
            Configuration.browserSize = browser.load("browserSize");
        }

        Configuration.holdBrowserOpen = Boolean.parseBoolean(browser.load("holdBrowserOpen"));
        Configuration.savePageSource = Boolean.parseBoolean(browser.load("savePageSource"));
        Configuration.timeout = Long.parseLong(browser.load("timeout"));
    }
}
