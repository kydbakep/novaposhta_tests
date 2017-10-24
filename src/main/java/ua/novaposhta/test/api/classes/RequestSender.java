package ua.novaposhta.test.api.classes;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

/**
 * Created by tober on 28.06.17.
 * json request sender to API 2.0
 */
public class RequestSender {

    public RequestSender() throws IOException, ParseException {
    }

    private JSONParser parser = new JSONParser();
    private String address = "https://api2.novaposhta.ua/v2.0/json/";
    private URL url = new URL(address);
    private String filePath = "./src/main/java/ua/novaposhta/test/api/json/";

    // 1. Будуємо шлях до файлу-запиту

    private String getFileFullPath(String jsonType, String fileName) {
        if (fileName.endsWith(".json")) {
            return filePath + jsonType + "/" + fileName;
        } else {
            return filePath + jsonType + "/" + fileName + ".json";
        }
    }

    // 2. Формуємо запит
    private String getRequestContent(String type, String name) throws IOException, ParseException, JSONException {
        Object requestDataFromFile = parser.parse(new FileReader(getFileFullPath(type, name)));
        return requestDataFromFile.toString();
    }

    // 3. Надсилаємо запит
    public void sendRequest(String type, String name) throws IOException, ParseException, JSONException {
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        String USER_AGENT = "Tober testing";
        connection.setRequestProperty("User-Agent", USER_AGENT);
        String parameters = getRequestContent(type, name);

        connection.setDoOutput(true);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
        out.write(parameters);
        out.flush();
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String jsonResponseFile = filePath+"/response/response.json";
        FileWriter writer = new FileWriter(jsonResponseFile, false);

        String text = StringEscapeUtils.unescapeJava(response.toString());
        writer.write(text);
        writer.flush();
    }
}
