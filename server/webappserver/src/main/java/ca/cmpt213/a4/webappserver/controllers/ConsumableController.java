package ca.cmpt213.a4.webappserver.controllers;

import ca.cmpt213.a4.webappserver.control.ConsumableManager;
import ca.cmpt213.a4.webappserver.model.Consumable;
import ca.cmpt213.a4.webappserver.model.DrinkInformation;
import ca.cmpt213.a4.webappserver.model.FoodInformation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API
 */
@RestController
public class ConsumableController {

    public ConsumableController() {
    }

    @GetMapping("/ping")
    public String getSystemMessage() {
        return "System is up!";
    }

    @GetMapping("/listAll")
    public List<Consumable> listAll() {
        return ConsumableManager.getItemList();
    }

    @GetMapping("/listExpired")
    public List<Consumable> expiredItem() {
        return ConsumableManager.expiredItem();
    }

    @GetMapping("/listNonExpired")
    public List<Consumable> nonExpiredItems() {
        return ConsumableManager.notExpiredItem();
    }

    @GetMapping("/listExpiringIn7Days")
    public List<Consumable> expiringInAWeek() {
        return ConsumableManager.expiringItemInAWeek();
    }

    @GetMapping("/exit")
    public void exitSave() {
        ConsumableManager.saveItems();
    }

    @PostMapping("/addFood")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Consumable> addItem(@RequestBody FoodInformation item) {
        ConsumableManager.addConsumable(item);
        return ConsumableManager.getItemList();
    }

    @PostMapping("/addDrink")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Consumable> addItem(@RequestBody DrinkInformation item) {
        ConsumableManager.addConsumable(item);
        return ConsumableManager.getItemList();
    }

    @PostMapping("removeItem/{index}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Consumable> removeItem(@PathVariable("index") int index) {
        ConsumableManager.removeConsumable(index);
        return ConsumableManager.getItemList();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
            reason = "Request Consumable not found.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void errorConsumableHandler() {
    }
}