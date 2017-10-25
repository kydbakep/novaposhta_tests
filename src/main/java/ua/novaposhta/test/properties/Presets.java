package ua.novaposhta.test.properties;

import com.codeborne.selenide.Configuration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Presets {

    public Presets() throws IOException {
    }

    private PropertyLoader browser = new PropertyLoader("browser");

    public void loadPresets() throws IOException {
        Configuration.browser = browser.load("browser");
        String os = System.getProperty("os.name");
        System.out.println("operating system is: " + os);
        if (os.toLowerCase().contains("windows")) {
            URL inputUrl = getClass().getResource("/drivers/chromedriver_2-33.exe");

            File driver = new File(browser.load("windows.chromedriver"));
            System.out.print("creating 'chromedriver' in 'Temp' folder...");
            FileUtils.copyURLToFile(inputUrl, driver);

            System.setProperty("webdriver.chrome.driver", driver.getAbsolutePath());
            getWebDriver().manage().window().maximize();

            driver.deleteOnExit();

        }
        if (os.toLowerCase().endsWith("nux") || os.toLowerCase().endsWith("nix") || os.toLowerCase().endsWith("bsd")) {
            System.setProperty("webdriver.chrome.driver", browser.load("linux.chromedriver"));
            Configuration.browserSize = browser.load("browserSize");
        }

        Configuration.holdBrowserOpen = Boolean.parseBoolean(browser.load("holdBrowserOpen"));
        Configuration.savePageSource = Boolean.parseBoolean(browser.load("savePageSource"));
        Configuration.timeout = Long.parseLong(browser.load("timeout"));
    }
}
