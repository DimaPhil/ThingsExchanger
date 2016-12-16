package year2013.foodsharing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by demouser on 8/4/16.
 */

public class Product {

    Integer id;
    String productName;
    String address;
    Integer ownerId;
    List<Integer> reservedUsersId;

    public Product() {
    }

    Product(Integer id, String productName, String address, Integer ownerId) {
        this.id = id;
        this.productName = productName;
        this.address = address;
        this.ownerId = ownerId;
        reservedUsersId = new ArrayList<>();
    }

    public String toString() {
        return id + " " + productName + " " + address + " " + ownerId;
    }

}

