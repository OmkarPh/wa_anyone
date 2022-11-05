package com.omkarph.wa_anyone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;
import com.omkarph.wa_anyone.db.Entry;
import com.omkarph.wa_anyone.db.HistoryHandler;
import com.omkarph.wa_anyone.ui.SettingsActivity;
import com.omkarph.wa_anyone.ui.about.AboutFragment;
import com.omkarph.wa_anyone.ui.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        navigationView.setNavigationItemSelectedListener(menu -> {
//            Log.d("Nav", "Navigation item selected:"+menu.getItemId());
//            switch(menu.getItemId()){
//                case R.id.nav_share:
//                    API.shareApp(this);
//                    return false;
//                case R.id.nav_rate:
//                    API.rateApp(this);
//                    return false;
//                default:
//                    return false;
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//         Handle item selection
        try{
            switch (item.getItemId()) {
                case R.id.share:
                    API.shareApp(this);
                    return true;
                case R.id.rate:
//                    API.rateApp(this);
                    Toast.makeText(getBaseContext(), "You can rate our app soon !!", Toast.LENGTH_SHORT).show();
                    return true;
//                case R.id.action_settings:
//                    Intent intent = new Intent(this, SettingsActivity.class);
//                    this.startActivity(intent);
//                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }catch(Exception e){
            return false;
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}