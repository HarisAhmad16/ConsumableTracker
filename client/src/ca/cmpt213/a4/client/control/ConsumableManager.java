package ca.cmpt213.a4.client.control;

import ca.cmpt213.a4.client.model.Consumable;
import ca.cmpt213.a4.client.model.ConsumableFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class to handle the consumables
 * Obtains the information from the server for user options
 */
public class ConsumableManager {

    private final static List<Consumable> itemList = new ArrayList<>();
    private final static ConsumableFactory factory = new ConsumableFactory();

    /**
     * method gets the array from the server
     *
     * @throws IOException HTTP get requests found from
     *                     https://www.baeldung.com/java-http-request
     *                     https://www.tabnine.com/code/java/methods/java.net.HttpURLConnection/setRequestMethod
     *                     https://stackoverflow.com/questions/6184157/java-one-liner-scanner-from-url-text-file
     *                     https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
     */
    public static void getArrayFromServer() throws IOException {
        URL url = new URL("http://localhost:8080/listAll");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        if (con.getResponseCode() == 200 || con.getResponseCode() == 201) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            StringBuilder inputFromServer = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                inputFromServer.append(inputLine);
            in.close();

            JsonElement fileValue = JsonParser.parseString(inputFromServer.toString());
            JsonArray jsonList = fileValue.getAsJsonArray();
            populateList(jsonList, itemList);
        } else {
            throw new RuntimeException("HTTP Response Code: " + con.getResponseCode());
        }
    }

    /**
     * method gets the expired list from the server
     *
     * @param list consumable list storing the expired items
     */
    public static void getExpiredListFromServer(List<Consumable> list) throws IOException {
        URL url = new URL("http://localhost:8080/listExpired");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        if (con.getResponseCode() == 200 || con.getResponseCode() == 201) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            StringBuilder inputFromServer = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                inputFromServer.append(inputLine);
            in.close();

            JsonElement fileValue = JsonParser.parseString(inputFromServer.toString());
            JsonArray jsonList = fileValue.getAsJsonArray();
            populateList(jsonList, list);
        } else {
            throw new RuntimeException("HTTP Response Code: " + con.getResponseCode());
        }
    }

    /**
     * method gets the non expired list from the server
     *
     * @param list consumable list storing the non expired items
     */
    public static void getNonExpiredItemsFromServer(List<Consumable> list) throws IOException {
        URL url = new URL("http://localhost:8080/listNonExpired");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        if (con.getResponseCode() == 200 || con.getResponseCode() == 201) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            StringBuilder inputFromServer = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                inputFromServer.append(inputLine);
            in.close();

            JsonElement fileValue = JsonParser.parseString(inputFromServer.toString());
            JsonArray jsonList = fileValue.getAsJsonArray();
            populateList(jsonList, list);
        } else {
            throw new RuntimeException("HTTP Response Code: " + con.getResponseCode());
        }

    }

    /**
     * method gets the non expired list in a week from the server
     *
     * @param list consumable list storing the non expired items in a week
     */
    public static void getExpiringInAWeekItemsFromServer(List<Consumable> list) throws IOException {
        URL url = new URL("http://localhost:8080/listExpiringIn7Days");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        if (con.getResponseCode() == 200 || con.getResponseCode() == 201) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            StringBuilder inputFromServer = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                inputFromServer.append(inputLine);
            in.close();

            JsonElement fileValue = JsonParser.parseString(inputFromServer.toString());
            JsonArray jsonList = fileValue.getAsJsonArray();
            populateList(jsonList, list);
        } else {
            throw new RuntimeException("HTTP Response Code: " + con.getResponseCode());
        }
    }

    /**
     * method adds the user's item to the server
     *
     * @param choice     if it is a food or drink
     * @param consumable the item being added
     * @throws IOException Get POST Request found from
     *                     https://zetcode.com/java/getpostrequest/
     *                     https://www.edureka.co/community/5406/how-to-send-http-post-requests-on-java
     *                     https://www.baeldung.com/httpurlconnection-post
     */
    public static void addItemToServer(int choice, String consumable) throws IOException {
        URL url = new URL("http://localhost:8080/addFood");
        if (choice == 2) {
            url = new URL("http://localhost:8080/addDrink");
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.valueOf(url)))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(consumable))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * method removes the index of the in the server
     *
     * @param index being removed
     * @throws IOException Post requests for path variable found from
     *                     https://stackoverflow.com/questions/3324717/sending-http-post-request-in-java
     */
    public static void removeItemFromServer(int index) throws IOException {
        URL url = new URL("http://localhost:8080/removeItem/" + index);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.valueOf(url)))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(index)))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * method to exit from the server and save the list
     */
    public static void getExitFromServer() throws IOException {
        URL url = new URL("http://localhost:8080/exit");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        if (con.getResponseCode() == 200 || con.getResponseCode() == 201) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            AtomicReference<StringBuilder> inputFromServer = new AtomicReference<>(new StringBuilder());
            while ((inputLine = in.readLine()) != null)
                inputFromServer.get().append(inputLine);
            in.close();
        } else {
            throw new RuntimeException("HTTP Response Code: " + con.getResponseCode());
        }
        con.disconnect();
    }

    /**
     * populates the itemlist from the json array
     *
     * @param jsonList json array from server
     * @param list     being populated from the json array
     */
    public static void populateList(JsonArray jsonList, List<Consumable> list) {
        for (int i = 0; i < jsonList.size(); i++) {
            JsonObject itemObject = jsonList.get(i).getAsJsonObject();
            int choice = itemObject.get("choice").getAsInt();
            String name = itemObject.get("name").getAsString();
            String notes = itemObject.get("notes").getAsString();
            double price = itemObject.get("price").getAsDouble();
            String expiryDateString = itemObject.get("expiryDate").getAsString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime date = LocalDateTime.parse(expiryDateString, formatter);
            double size = itemObject.get("size").getAsDouble();
            Consumable consumable = factory.getInstance(choice, name, notes, price, size, date);
            list.add(consumable);
        }
    }

    /**
     * refreshes the list field
     */
    public void refreshArrayState() {
        itemList.clear();
        try {
            getArrayFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the itemList
     *
     * @return the itemList
     */
    public List<Consumable> getArrayList() {
        return itemList;
    }

    /**
     * creates the list holding the expired items
     *
     * @return the list
     */
    public List<Consumable> listingExpired() {
        List<Consumable> list = new ArrayList<>();
        try {
            getExpiredListFromServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * creates the list holding the non expired items
     *
     * @return the list
     */
    public List<Consumable> listingNonExpired() {
        List<Consumable> list = new ArrayList<>();
        try {
            getNonExpiredItemsFromServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * creates the list holding the non expiring items in a week
     *
     * @return the list
     */
    public List<Consumable> listingExpiringInAWeek() {
        List<Consumable> list = new ArrayList<>();
        try {
            getExpiringInAWeekItemsFromServer(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * gets the item list from the server
     */
    public void getServerList() {
        try {
            getArrayFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls the exit on the server and saves the items
     */
    public void onExitClick() {
        try {
            getExitFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a consumable item from the itemList
     *
     * @param index to be removed
     */
    public void removeConsumable(int index) {
        if (itemList.size() == 0) return;

        if (index >= 1 && index <= itemList.size()) {
            index--;
            try {
                removeItemFromServer(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
