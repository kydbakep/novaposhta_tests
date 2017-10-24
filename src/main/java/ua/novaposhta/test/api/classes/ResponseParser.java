package ua.novaposhta.test.api.classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tober on 27.06.17.
 * json response parser
 */
public class ResponseParser {
    private JSONParser parser = new JSONParser();
    private String node = null;
    private ArrayList list = null;

    public ResponseParser() throws IOException, ParseException {
    }

    private String path = "./src/main/java/ua/novaposhta/test/api/json/response/";

    Object getResponse(String type) throws IOException, ParseException {
        Object jsonResponse = parser.parse(new FileReader(path+"response.json"));
        if (type.equals("string")) {
            JSONObject response = (JSONObject) jsonResponse;
            return response.toString();
        }
        if (type.equals("object")) {
            return jsonResponse;
        }
        if (type.equals("jsonObject")) {
            return (JSONObject) jsonResponse;
        }
        if (type.equals("array")) {
            return (JSONArray) jsonResponse;
        } else return null;
    }

    public void printResponse() throws IOException, ParseException {
        System.out.println(getResponse("string"));
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    public ArrayList getList() {
        return this.list;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getKey() {
        return this.node;
    }

    public String getKey(String key, int number) throws ParseException {
        JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(getList().get(number)));
        Object node = jsonObject.get(key);
        return node.toString();
    }

    public String getKey(String key) throws IOException, ParseException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse((String) getResponse("string"));
            Object node = jsonObject.get(key);
            return node.toString();
        } catch (NullPointerException ex) {
            ArrayList list = (ArrayList) parser.parse(node);
            JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(list.get(0)));
            Object node = jsonObject.get(key);
            setList(list);
            return node.toString();
        }
    }

    public String getKey(String key, String nodeKey) throws IOException, ParseException {
        try {
            ArrayList list = (ArrayList) parser.parse(getKey(key));
            JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(list.get(0)));
            Object node = jsonObject.get(nodeKey);
            return node.toString();
        } catch (ClassCastException ex) {
            String s = getKey(key);
            JSONObject jsonObject = (JSONObject) parser.parse(s);
            Object node = jsonObject.get(nodeKey);
            return node.toString();
        }
    }

    public String getKey(String key, String nodeKey, String deepNodeKey) throws IOException, ParseException {
        try {
            String incoming = getKey(key, nodeKey);
            JSONObject jsonObject = (JSONObject) parser.parse(incoming);
            Object node = jsonObject.get(deepNodeKey);
            return node.toString();
        } catch (ClassCastException ex) {
            ArrayList list = (ArrayList) parser.parse(getKey(key, nodeKey));
            JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(list.get(0)));
            return String.valueOf(jsonObject.get(deepNodeKey));
        }
    }

    public void printKey(String key, String nodeKey, String deepNodeKey, String deepKey) throws IOException, ParseException {
        String incoming = getKey(key, nodeKey, deepNodeKey);
        ArrayList list = (ArrayList) parser.parse(incoming);

        for (Object x : list) {
            JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(x));
            Object node = jsonObject.get(deepKey);
            System.out.println(node);
        }
    }

    public JSONObject getResponseNodes() throws IOException, ParseException, JSONException {
        JSONObject jsonObject = (JSONObject) getResponse("object");
        if (jsonObject.keySet().size() > 0) {
            try {
                ArrayList<Object> list = new ArrayList<>();
                list.addAll(jsonObject.keySet());
                System.out.println("--------------------\nroot: ");
                for (Object aList : list) {
                    System.out.println(aList);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("--------------------");
        return jsonObject;
    }

    public void getNodes() throws IOException, ParseException, JSONException {
        JSONObject response = (JSONObject) getResponse("object");
        ArrayList<Object> keys = new ArrayList<>();
        keys.addAll(response.keySet());
        System.out.println("Response: " + response);
        System.out.println("Keys: " + keys);

        ArrayList<Object> data = new ArrayList<>();

        for (int i = 0; i < keys.size(); i++) {

            System.out.println(i + 1 + " " + keys.get(i) + ": " + getKey(keys.get(i).toString()));
            try {
                data.add(getKey(keys.get(i).toString()));
            } catch (Exception e) {
                e.getMessage();
            }
        }
        System.out.println(data.get(0));
    }

    public void getNodes(Object key) throws IOException, ParseException, JSONException {
        JSONObject jsonObject = (JSONObject) getResponse("object");
        System.out.println(jsonObject.keySet());
    }

    public ArrayList getList(String key) throws IOException, ParseException, JSONException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse((String) getResponse("string"));
            Object node = jsonObject.get(key);
            return (ArrayList) node;
        } catch (NullPointerException ex) {
            System.out.println("CATCH!");
            ArrayList list = (ArrayList) parser.parse(node);
            JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(list.get(0)));
            Object node = jsonObject.get(key);
            setList(list);
            return (ArrayList) node;
        }
    }

    public ArrayList getList(String key, String nodeKey) throws IOException, ParseException, JSONException {
        ArrayList list = getList(key);
        ArrayList<JSONObject>objects = new ArrayList<>();
        ArrayList<String>regions = new ArrayList<>();
        for (Object aList : list) {
            objects.add((JSONObject) parser.parse(String.valueOf(aList)));
        }

        for (JSONObject object : objects) {
            Object node = object.get(nodeKey);
            regions.add(String.valueOf(node));
        }
        return regions;
    }
}
