package com.inc.fsi.fnn.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.inc.fsi.fnn.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private FrameLayout touchCaptureLayer;
    private long lastTouchTime;
    private float currentExpediency;
    NavController navController;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"TouchData");
        if(!fileDir.exists()){
            try{
                boolean result = fileDir.mkdir();

                Log.d("make directory", "result = " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +"TouchData"+File.separator+"DataSheet.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

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

        requestStoragePermission();

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.finger_print)
                        .setTitle("Pattern Recognition is Flagging You")
                        .setMessage("It looks like this isn't your account, you need extra authentication to continue.")
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Intent signOut = new Intent(MainActivity.this, RegistrationActivity.class);
                                startActivity(signOut);
                                MainActivity.this.finish();
                            }
                        }).show();
            }
        }, 1000 * 15);
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
//            String strLine = null;
//            String []data ;
//            FileInputStream fis = null;
//            try {
//                fis = new FileInputStream(file);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            BufferedReader r = new BufferedReader(new InputStreamReader(fis));
//            while (true)   {
//                try {
//                    if ((strLine = r.readLine()) == null) break;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                data = strLine.split(",");
//                String first = data[0];
//                String second = data[1];
//                String third = data[2];
//
//                Toast.makeText(this, first + ", " + second + ", " + third, Toast.LENGTH_SHORT).show();
//            }
//            try {
//                r.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
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
                        getSupportActionBar().hide();
                        navController.navigate(R.id.PayFragment);
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

            StringBuilder dataSet = new StringBuilder().append(xOff).append(",")
                    .append(yOff).append(",").append(currentExpediency).append(",").append(0);

            if(file.exists()){
                try {
                    FileWriter fileWriter  = new FileWriter(file, true);
                    BufferedWriter bfWriter = new BufferedWriter(fileWriter);
                    bfWriter.newLine();
                    bfWriter.write(dataSet.toString());
                    bfWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
        return false;
    }

    private final int RC_PERMISSION_STORAGE = 9001;

    private boolean requestStoragePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        RC_PERMISSION_STORAGE);
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        RC_PERMISSION_STORAGE);
                return false;
            }
            else{
                return true;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().show();
        super.onBackPressed();
    }
}
