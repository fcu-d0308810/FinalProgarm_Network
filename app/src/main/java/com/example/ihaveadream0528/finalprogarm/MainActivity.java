package com.example.ihaveadream0528.finalprogarm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener
{
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private UserDAO userDAO;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全螢幕這開始
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //全螢幕到這
        super.onCreate(savedInstanceState);
        userDAO = new UserDAO(getApplicationContext());
        getLocalUser();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /////////////加入drawer的動畫/////////////
        final View mainView = (View) findViewById(R.id.allView);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                drawer.bringChildToFront(drawerView);
                drawer.requestLayout();
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ////////////////drawer動畫程式碼到這////////////////

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    public void getLocalUser(){
        user = userDAO.getUser();
        if(user == null){
            finish();
            startActivity(new Intent(MainActivity.this, Login_page.class));
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //logout function here
        if (id == R.id.action_logout) {
            userDAO.delete(user);
            user = null;
            finish();
            startActivity(new Intent(MainActivity.this, Login_page.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    int number = 0;
    MenuItem item1 = null;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        //Here is let the Menu item starup
        if (item.isChecked()) {
            item.setChecked(true);
        }
        else {
            if (number==0){
                item.setChecked(true);
                item1 = item;
                number = 1;
            }
            else {
                item1.setChecked(false);
                item.setChecked(true);
                item1 = item;
            }
        }
        if (id == R.id.nav_upload) {
            fragment = new picture_frament();
        }
        else if(id == R.id.nav_curriculum){
            fragment = new Curriculum_fragment();
        }
        else if(id == R.id.nav_download){
            fragment = new Download_fragment();
        }
        else if (id == R.id.nav_message) {
            fragment = new Message_fragment(user);
        }
        else if(id == R.id.nav_note){
            fragment = new Note_fragment();
        }
        else if (id == R.id.nav_other) {
            fragment = new OtherFunction_fragment();
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTrans = fragmentManager.beginTransaction();
        fragmentTrans.addToBackStack(null);
        fragmentTrans.replace(R.id.content_main, fragment);
        fragmentTrans.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
