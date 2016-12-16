package ru.ctddev.ifmo.year2013.foodsharing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.ctddev.ifmo.year2013.foodsharing.R;
import ru.ctddev.ifmo.year2013.foodsharing.model.User;


public class LoginActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 1;
    private static final String TAG = LoginActivity.class.getName();
    FirebaseAuth mAuth;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_swipe);
        initDrawer();

        mAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();

        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.AppTheme)
                        .setProviders(
                                AuthUI.EMAIL_PROVIDER,
                                AuthUI.GOOGLE_PROVIDER,
                                AuthUI.FACEBOOK_PROVIDER)
                        .build(),
                RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                this.addUserToDbIfNeeded();
                startActivity(new Intent(this, MainActivity.class));
                callbackManager.onActivityResult(requestCode, resultCode, data);
                finish();
            } else {
                // user is not signed in. Maybe just wait for the user to press
                // "sign in" again, or show a message
                Log.d(TAG, "Not signed in");
            }
        }
    }

    private void addUserToDbIfNeeded() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        User user = new User();
        DatabaseReference newRef = usersRef.child(currentUser.getUid());
        user.setId(currentUser.getUid());
        newRef.setValue(user);
    }

}
