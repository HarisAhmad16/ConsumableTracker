package ca.cmpt213.a4.webappserver.control;

import ca.cmpt213.a4.webappserver.model.Consumable;
import ca.cmpt213.a4.webappserver.model.ConsumableFactory;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

/**
 * Class to handle the consumables
 * Obtains the information from the server for user options
 */
public class ConsumableManager {

    private static final String HARDCODED_PATH = "./itemsList.json";
    private final static ArrayList<Consumable> itemList = new ArrayList<>();

    /**
     * Saves the items in json format
     */
    public static void saveItems() {
        Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter,
                                      LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }

                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                }).create();
        try {
            Writer file = new FileWriter(HARDCODED_PATH);
            myGson.toJson(itemList, file);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the items and stores it in the ArrayList from json
     * <p>
     * idea of saving more than one object in array
     * obtained from https://stackoverflow.com/questions/32451666/how-parse-json-array-with-multiple-objects-by-gson
     */
    public static void loadItems() {
        File file = new File(HARDCODED_PATH);
        try {
            JsonElement fileValue = JsonParser.parseReader(new FileReader(file));
            JsonArray jsonList = fileValue.getAsJsonArray();
            for (int i = 0; i < jsonList.size(); i++) {
                JsonObject itemObject = jsonList.get(i).getAsJsonObject();
                int choice = itemObject.get("choice").getAsInt();
                String name = itemObject.get("name").getAsString();
                String notes = itemObject.get("notes").getAsString();
                double price = itemObject.get("price").getAsDouble();
                String expiryDateString = itemObject.get("expiryDate").getAsString();
                double size = itemObject.get("size").getAsDouble();
                Consumable consumable = ConsumableFactory.getInstance(choice, name, notes, price, size, expiryDateString);
                itemList.add(consumable);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Exception: " + e);
        }
    }

    /**
     * Gets the itemList
     *
     * @return the itemList
     */
    public static List<Consumable> getItemList() {
        return itemList;
    }

    /**
     * Adds a consumable item to the itemList
     *
     * @param consumable being added
     */
    public static void addConsumable(Consumable consumable) {
        if (consumable != null) itemList.add(consumable);
    }

    /**
     * removing a consumable from the list
     *
     * @param index to be removed
     */
    public static void removeConsumable(int index) {
        if (itemList.size() == 0) return;
        if (index == 0) itemList.remove(0);
        if (index >= 1 && index <= itemList.size()) {
            for (int i = 0; i < itemList.size(); i++) {
                if (i == index) {
                    itemList.remove(i);
                }
            }
        }
    }

    /*
     * Getting the expired item
     */
    public static List<Consumable> expiredItem() {
        List<Consumable> list = new ArrayList<>();
        if (itemList.size() == 0) return list;
        Collections.sort(itemList);
        for (Consumable item : itemList) {
            if (item.expiringDays() < 0) list.add(item);
        }
        return list;
    }

    /**
     * Getting list of non-expired item
     */
    public static List<Consumable> notExpiredItem() {
        List<Consumable> list = new ArrayList<>();
        if (itemList.size() == 0) return list;
        Collections.sort(itemList);
        for (Consumable item : itemList) {
            if (item.expiringDays() >= 0) list.add(item);
        }
        return list;
    }

    /**
     * Getting list of items expiring in seven days
     */
    public static List<Consumable> expiringItemInAWeek() {
        List<Consumable> list = new ArrayList<>();
        if (itemList.size() == 0) return list;
        Collections.sort(itemList);
        for (Consumable item : itemList) {
            if (item.expiringDays() <= 7 && item.expiringDays() >= 0) list.add(item);
        }
        return list;
    }
}
