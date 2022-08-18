package ca.cmpt213.a4.webappserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * A class taking in all the information of the food item
 *
 * @author MohammadHarisAhmad
 * <p>
 * String conversion to LocalDateTime found from
 * https://www.java67.com/2016/04/how-to-convert-string-to-localdatetime-in-java8-example.html
 */
public class FoodInformation implements Consumable {
    private final String name;
    private final String notes;
    private final double price;
    private final String expiryDate;
    private final double size;
    private final int choice;

    /**
     * Constructor
     *
     * @param name  the food item name
     * @param notes the notes on the item
     * @param price the price of the item
     * @param size  the size of the item
     * @param date  the expiry date of the item
     */
    /*
    JsonProperty idea found from
    https://stackoverflow.com/questions/12583638/when-is-the-jsonproperty-property-used-and-what-is-it-used-for
     */
    public FoodInformation(
            @JsonProperty("choice") int choice,
            @JsonProperty("name") String name,
            @JsonProperty("notes") String notes,
            @JsonProperty("price") double price,
            @JsonProperty("size") double size,
            @JsonProperty("expiryDate") String date) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.expiryDate = date;
        this.size = size;
        this.choice = choice;
    }

    /**
     * Abstract methods
     */
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getNotes() {
        return this.notes;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public double getSize() {
        return this.size;
    }

    @Override
    public int getChoice() {
        return this.choice;
    }

    @Override
    public String getExpiryDate() {
        return this.expiryDate;
    }

    @Override
    public long expiringDays() {
        DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(expiryDate, timePattern);
        return DAYS.between(LocalDateTime.now().toLocalDate(), date.toLocalDate());
    }

    // idea of return found from https://stackoverflow.com/questions/40543654/how-compareto-method-work-arraylist-sorting
    @Override
    public int compareTo(Consumable o) {

        long daysLeft = expiringDays();

        if (daysLeft < o.expiringDays()) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * toString method to form the food item
     *
     * @return the food item's information
     */
    @Override
    public String toString() {

        long daysLeft = expiringDays();

        DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(expiryDate, timePattern);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String expiredFormatDate = date.format(formatter);

        String strDouble = String.format("%.2f", getPrice());

        String type = "This is a food item.";

        if (daysLeft == 0) {
            return type + "\n" +
                    "Name: " + getName() + "\n" +
                    "Notes: " + getNotes() + "\n" +
                    "Price: " + strDouble + "\n" +
                    "Weight" + String.format("%.2f", getSize()) + "\n" +
                    "Expiry date: " + expiredFormatDate + "\n" +
                    "This food item will expire today.\n"
                    + " \n";
        }

        if (daysLeft < 0) {

            daysLeft = Math.abs(daysLeft);

            return type + "\n" +
                    "Name: " + getName() + "\n" +
                    "Notes: " + getNotes() + "\n" +
                    "Price: " + strDouble + "\n" +
                    "Weight" + String.format("%.2f", getSize()) + "\n" +
                    "Expiry date: " + expiredFormatDate + "\n" +
                    "This food item is expired for " + daysLeft + " day(s).\n"
                    + " \n";
        }

        return type + "\n" +
                "Name: " + getName() + "\n" +
                "Notes: " + getNotes() + "\n" +
                "Price: " + strDouble + "\n" +
                "Weight" + String.format("%.2f", getSize()) + "\n" +
                "Expiry date: " + expiredFormatDate + "\n" +
                "This food item will expire in " + daysLeft + " day(s).\n"
                + " \n";
    }
}
