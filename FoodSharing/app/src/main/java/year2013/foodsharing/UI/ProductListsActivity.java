package year2013.foodsharing.UI;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import year2013.foodsharing.Product;
import year2013.foodsharing.R;
import year2013.foodsharing.User;

/**
 * Created by Daria Yakovleva.
 */
public class ProductListsActivity extends FragmentActivity implements CallBack {

    public static String USERS = "users";
    String userID = "-KOOrnYrzKryy84k77tr"; //TODO where?
    public Map<String, User> usersMap = new HashMap<>();
    public Map<String, Product> donationsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("PRODUCT LIST ACTIVITY", "Start create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_list_activity);
        Log.d("PRODUCT LIST ACTIVITY", "End create");
    }

    @Override
    public void onItemClick(String itemName) {
        Log.d("PRODUCT LIST ACTIVITY", "Start Item Click");
        Log.d("PRODUCT LIST ACTIVITY", "End Item Click");
    }

    public Map<String, User> getUsers() {
        return usersMap;
    }
    public  Map<String, Product> getDonations() {
        return donationsMap;
    }

    public String getUserID() {
        return userID;
    }
}