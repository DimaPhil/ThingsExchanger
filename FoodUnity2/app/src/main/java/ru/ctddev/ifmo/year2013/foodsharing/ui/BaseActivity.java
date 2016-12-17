package ru.ctddev.ifmo.year2013.foodsharing.ui;

import android.content.Intent;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import ru.ctddev.ifmo.year2013.foodsharing.R;
import ru.ctddev.ifmo.year2013.foodsharing.ui.productlist.ProductListsActivity;

public class BaseActivity extends AppCompatActivity {

    public static String PHOTO_URL = "PHOTO_URL";
    public static String DISPLAY_NAME = "DISPLAY_NAME";
    public static String BUNDLE_NAME = "BUNDLE";
    public static final int DEFAULT_PHOTO = R.drawable.figure;
    public static final String ANONYMOUS = "Anonymous";
    FirebaseAuth mAuth;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar toolbar;
    private String username;
    private Uri photoUrl;

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        mAuth = FirebaseAuth.getInstance();
    }

    protected void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.profile_drawer);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(mNavigationView);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.show_drawer);

        RelativeLayout container = (RelativeLayout) mNavigationView.getHeaderView(0);

        Intent intent = getIntent();
        if(intent!= null && intent.getExtras() != null){
            Bundle bundle = intent.getExtras();
            this.username = (String)bundle.get(DISPLAY_NAME);
            this.photoUrl = (Uri)bundle.get(PHOTO_URL);
        }
        TextView profileName = (TextView) container.findViewById(R.id.name_text);
        profileName.setText("Hi, " + ((username != null)? username : ANONYMOUS) + "!");

        ImageView profilePicture = (ImageView) container.findViewById(R.id.profile_picture);
        if(photoUrl == null) {
            profilePicture.setImageResource(DEFAULT_PHOTO);
        }else{
            Picasso.with(this).load(photoUrl).into(profilePicture);
        }
        profilePicture.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int width =  view.getWidth();
                int height = view.getHeight();
                outline.setOval(0, 0, width, height);
            }
        });
        profilePicture.setClipToOutline(true);
    }


    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.sign_out_button:
                                AuthUI.getInstance().signOut(BaseActivity.this);
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.view_profile_button:
                                Intent intentProfile = new Intent(BaseActivity.this, ProductListsActivity.class);
                                startActivity(intentProfile);
                                break;
                        }
                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
