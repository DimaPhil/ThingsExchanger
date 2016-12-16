package year2013.foodsharing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daria Yakovleva
 */
public class User {
    Integer id;
    String userName;
    String userAddress;
    String userPhone;
    private List<Product> userProducts;
    private List<Product> reserveList;

    public User() {
    }

    public User(Integer id, String userName, String userAddress, String userPhone) {
        this.id = id;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        userProducts = new ArrayList<>();
        reserveList = new ArrayList<>();
    }

    public String toString() {
        return id + " " + userName + " " + userAddress + " " + userPhone;
    }
}
