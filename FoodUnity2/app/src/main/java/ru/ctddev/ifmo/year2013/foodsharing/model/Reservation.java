package ru.ctddev.ifmo.year2013.foodsharing.model;

public class Reservation {
    public String id;
    public Integer quantity;
    public Reservation() {}
    public Reservation(String id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
