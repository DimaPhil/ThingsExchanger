package ru.ctddev.ifmo.year2013.foodsharing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import ru.ctddev.ifmo.year2013.foodsharing.R;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getName();

    FirebaseAuth mAuth;
    TextView textView;
    Button signInOut;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_swipe);

        textView = (TextView)findViewById(R.id.textView);
        signInOut = (Button)findViewById(R.id.signOut);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            redirectToHome();
        } else {
            redirectToLogin();
        }
        initDrawer();
    }

    private void redirectToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    protected void signOut(View v){
        AuthUI.getInstance().signOut(this);
        FirebaseAuth.getInstance().signOut();
        if (mAuth.getCurrentUser() == null) {
            super.setUsername(null);
            super.setPhotoUrl(null);
            redirectToLogin();
        }

        initDrawer();
    }

}
