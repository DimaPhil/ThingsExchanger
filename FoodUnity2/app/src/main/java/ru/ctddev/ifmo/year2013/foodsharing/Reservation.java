package ru.ctddev.ifmo.year2013.foodsharing;

/**
 * Created by demouser on 8/5/16.
 */
public class Reservation {
    public String iden;
    public Integer quantity;
    public Reservation() {}
    public Reservation(String iden, Integer quantity) {
        this.iden = iden;
        this.quantity = quantity;
    }

    public void setIden(String iden) {
        this.iden = iden;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
