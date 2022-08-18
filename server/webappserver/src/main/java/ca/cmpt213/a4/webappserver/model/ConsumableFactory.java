package ca.cmpt213.a4.webappserver.model;

/**
 * Class handles which item the user chose and create the correct item
 * with the data being passed in the parameters
 */
public class ConsumableFactory {
    public static Consumable getInstance(int choice, String name, String notes, double price, double size, String date) {
        if (choice == 1) {
            return new FoodInformation(choice, name, notes, price, size, date);
        } else if (choice == 2) {
            return new DrinkInformation(choice, name, notes, price, size, date);
        }
        return null;
    }

    public ConsumableFactory() {
    }
}
