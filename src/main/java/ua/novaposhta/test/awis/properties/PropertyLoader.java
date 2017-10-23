package ua.novaposhta.test.awis.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class PropertyLoader {
    private String file;

    public PropertyLoader() throws IOException {
        setFile("predefined");
        InputStream inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(file);
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
        AtomicReference<Properties> properties = new AtomicReference<>(new Properties());
        properties.get().load(reader);
    }

    public PropertyLoader(String filename) throws IOException {
        setFile(filename);
        InputStream inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(file);
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
        AtomicReference<Properties> properties = new AtomicReference<>(new Properties());
        properties.get().load(reader);
    }

    private void setFile(String filename) {
        if (filename.endsWith(".properties")) {
            this.file = filename;
        } else if (!filename.endsWith(".properties")) {
            this.file = filename + ".properties";
        }
    }

    public String load(String property) throws IOException {
        InputStream inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(file);
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
        Properties properties = new Properties();
        properties.load(reader);
        return properties.getProperty(property);
    }
}
