package com.inc.fsi.fnn.activities;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.inc.fsi.fnn.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private FrameLayout touchCaptureLayer;
    private long lastTouchTime;
    private float currentExpediency;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchCaptureLayer = findViewById(R.id.frame_gestureCapture);
        Toolbar toolbar = findViewById(R.id.toolbar);

        FragmentContainerView navHostFragment = findViewById(R.id.nav_host_fragment);

        navController = Navigation.findNavController(navHostFragment);

        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        initializeSideNav(toolbar);

        touchCaptureLayer.setOnTouchListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_messages) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeSideNav(Toolbar mToolbar){
        ActionBarDrawerToggle drawerToggle;
        final NavigationView sideNavView;
        final DrawerLayout drawerLayout = findViewById(R.id.layout_mainActivity);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.open, R.string.close);


        drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();

        sideNavView = findViewById(R.id.side_nav_view);

        drawerToggle.setDrawerIndicatorEnabled(false);

        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_dots);

        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        sideNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.side_nav_receive:
                        getSupportActionBar().hide();
                        navController.navigate(R.id.ReceiveFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.side_nav_pay:
                        /*Start camera activity*/
                        drawerLayout.closeDrawers();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            float yOff = event.getY();
            float xOff = event.getX();
            currentExpediency = 1.0f/(event.getDownTime() - lastTouchTime);
            lastTouchTime = event.getDownTime();
            Log.d("Motion Event", "Touched At: (" + xOff + ", " + yOff + ", " + currentExpediency + ")");
            return false;
        }
        return false;
    }
}
