package com.camp.android.luciana.foodunity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daria Yakovleva on 8/4/16.
 */
public class User implements Item {
    String iden;
    String name;
    String address;
    String phone;
    private List<String> donations;
    private List<Reservation> reservations;
    //TODO image

    public User() {
        iden = "1";
        name = "User";
        address = "Double Tree";
        phone = "79990343466";
        donations = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public User(String id, String name, String address, String phone) {
        this.iden = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        donations = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public void addDonation(String id) {
        donations.add(id);
    }

    public void addReservation(Reservation id) {
        reservations.add(id);
    }

    public void setId(String id) {
        this.iden = id;
    }
    public void setDonations(List<String> donations) {
        this.donations = donations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<String> getDonations() {
        return donations;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public String toString() {
        return iden + " " + name + " " + address + " " + phone + " " +
                Arrays.toString(donations.toArray()) + " " + Arrays.toString(reservations.toArray());
    }
}
