package ca.cmpt213.a4.client.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * A class taking in all the information of the drink item
 *
 * @author MohammadHarisAhmad
 */
public class DrinkInformation implements Consumable {
    private final String name;
    private final String notes;
    private final double price;
    private final LocalDateTime expiryDate;
    private final double size;
    private final int choice;

    /**
     * Constructor
     *
     * @param name   the drink name
     * @param notes  the notes of the item
     * @param price  the price of the item
     * @param volume the volume of the item
     * @param date   the expiry date of the item
     */
    public DrinkInformation(int choice, String name, String notes, double price, double volume, LocalDateTime date) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.expiryDate = date;
        this.size = volume;
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
        DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return expiryDate.format(timePattern);
    }

    @Override
    public long expiringDays() {
        return DAYS.between(LocalDateTime.now().toLocalDate(), expiryDate.toLocalDate());
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
     * toString method to form the drink item
     *
     * @return the drink item's information
     */
    @Override
    public String toString() {

        LocalDateTime today = LocalDateTime.now();
        long daysLeft = DAYS.between(today.toLocalDate(), expiryDate.toLocalDate());

        DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String expiredFormatDate = expiryDate.format(timePattern);

        String strDouble = String.format("%.2f", getPrice());
        String volumeDouble = String.format("%.2f", getSize());

        String type = "This is a drink item.";
        String sizeOfItem = "Volume: " + volumeDouble;


        if (daysLeft == 0) {
            return type + "\n" +
                    "Name: " + getName() + "\n" +
                    "Notes: " + getNotes() + "\n" +
                    "Price: " + strDouble + "\n" +
                    sizeOfItem + "\n" +
                    "Expiry date: " + expiredFormatDate + "\n" +
                    "This drink item will expire today.\n"
                    + " \n";
        }

        if (daysLeft < 0) {

            daysLeft = Math.abs(daysLeft);

            return type + "\n" +
                    "Name: " + getName() + "\n" +
                    "Notes: " + getNotes() + "\n" +
                    "Price: " + strDouble + "\n" +
                    sizeOfItem + "\n" +
                    "Expiry date: " + expiredFormatDate + "\n" +
                    "This drink item is expired for " + daysLeft + " day(s).\n"
                    + " \n";
        }

        return type + "\n" +
                "Name: " + getName() + "\n" +
                "Notes: " + getNotes() + "\n" +
                "Price: " + strDouble + "\n" +
                sizeOfItem + "\n" +
                "Expiry date: " + expiredFormatDate + "\n" +
                "This drink item will expire in " + daysLeft + " day(s).\n"
                + " \n";
    }

}

