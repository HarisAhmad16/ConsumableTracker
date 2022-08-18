package ca.cmpt213.a4.webappserver.model;

import java.time.LocalDateTime;

/**
 * Interface consisting of abstract methods
 * Extends to comparable
 */
// interface idea obtained from https://www.tutorialspoint.com/design_pattern/factory_pattern.htm
public interface Consumable extends Comparable<Consumable> {

    String getName();

    String getNotes();

    double getPrice();

    int getChoice();

    double getSize();

    String getExpiryDate();

    long expiringDays();
}
