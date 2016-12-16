package ru.ctddev.ifmo.year2013.foodsharing;

/**
 * Created by demouser on 8/4/16.
 */

public class Donation implements Item {

    public String food_description;
    public Boolean vegetarian;
    public String optional_info;
    private boolean allergens, gluten;
    private int minutes_left;
    private String address;
    private double latitude;
    private double longitude;
    String iden;
    String expiry_date;
    int reserved = 0;
    private int quantity = 0;
    private String display_name;


    public Donation() {
        this("1", "food", "26/10/2020", "good", "1", 3);
    }
    Donation(String id, String name, String expiry_date, String description, String ownerID, int count) {
        this.iden = id;
        this.food_description = name;
        this.expiry_date = expiry_date;
        this.optional_info = description;
        this.display_name = ownerID;
        this.quantity = count;
    }

    public Donation(Boolean vegetarian, Boolean allergens, String address, String optional_info, String description, double latitude, double longitude, int quantity, String display_name, String expiry_date) {
        this.vegetarian = vegetarian;
        this.allergens = allergens;
        this.address = address;
        this.optional_info = optional_info;
        this.food_description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.quantity = quantity;
        this.display_name = display_name;
        this.expiry_date = expiry_date;
    }

    @Override
    public String toString() {
        return iden + " " + food_description + " " + expiry_date + " " + optional_info + " " + display_name + " " + quantity + " " + reserved;
    }

    public String getOptional_info() {
        return optional_info;
    }

    public Boolean getVegetarian() {

        return vegetarian;
    }

    public String getDescription() {

        return food_description;
    }

    public boolean isAllergens() {
        return allergens;
    }

    public void setAllergens(boolean allergens) {
        this.allergens = allergens;
    }

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }

    public int getMinutes_left() {
        return minutes_left;
    }

    public void setMinutes_left(int minutes_left) {
        this.minutes_left = minutes_left;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDisplay_name() {
        return display_name;
    }


    public int getFree() {
        return quantity - reserved;
    }

    public int getReserved() {
        return reserved;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public void setFood_description(String food_description) {
        this.food_description = food_description;
    }

    public void setIden(String iden) {
        this.iden = iden;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setOptional_info(String optional_info) {
        this.optional_info = optional_info;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public String getFood_description() {
        return food_description;
    }

    public String getIden() {
        return iden;
    }
}

