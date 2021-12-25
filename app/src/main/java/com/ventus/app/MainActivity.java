package com.ventus.app;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
public class MainActivity extends AppCompatActivity {


    Window window;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //Проверка SDK
        if(Build.VERSION.SDK_INT>=21){
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.fragmentBackground));
        }

        //Проверка WiFi
        if (checkWifiOnAndConnected() == false){
            Toast.makeText(this,R.string.wifiError,Toast.LENGTH_SHORT).show();
        }


        //Проверка на IPConfig
        if ((Shared.existPreferences(this,"URL_REQUEST_CONFIG5"))){
            //Toast.makeText(this,"Подключенно к "+Shared.getStringPreferences(this,"URL_REQUEST_CONFIG5"),Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Ошибка подключения",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent("android.intent.action.SettingsActivity");
            startActivity(intent);
        }
        getAll();
        /**
         *
         */

        final Boolean isNigthModeOn = Shared.getBoolPreferences(MainActivity.this,"NightMode",false);
        if(isNigthModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                View decorView = this.getWindow().getDecorView();
                int systemUIVisibFlags = decorView.getSystemUiVisibility();
                systemUIVisibFlags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(systemUIVisibFlags);
            }
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        /**
         *
         */
        setUpViewPager();
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new MQ4Fragment()).commit();

    }

    private void init(){
        bottomNavigationView = findViewById(R.id.bottomNav);
        viewPager = findViewById(R.id.view_pager);
    }


    private void getAll(){
        okHTTP getAllInfo = new okHTTP();
        getAllInfo.run("http://"+Shared.getStringPreferences(this,"URL_REQUEST_CONFIG1")+"/all", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    if (this != null) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);
                                    String DHT11T = convert.getTemperature(myResponse);
                                    String DHT11H = convert.getHum(myResponse);
                                    String MQ135 = convert.getMQ135(myResponse);
                                    String MQ4 = convert.getMQ4(myResponse);
                                    String MQ7 = convert.getMQ7(myResponse);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment fragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.mq4:
                            //fragment=new MQ4Fragment();
                            viewPager.setCurrentItem(0);
                            break;

                        case R.id.mq7:
                            //fragment=new MQ7Fragment();
                            viewPager.setCurrentItem(1);
                            break;

                        case R.id.mq135:
                            //fragment=new MQ135Fragment();
                            viewPager.setCurrentItem(2);
                            break;

                        case R.id.dht11:
                            //fragment=new DHT11Fragment();
                            viewPager.setCurrentItem(3);
                            break;
                    }
                    //getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                    return true;
                }
            };
    private void setUpViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.mq4).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.mq7).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.mq135).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.dht11).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public boolean checkWifiOnAndConnected() {

        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connMgr.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities capabilities = connMgr.getNetworkCapabilities(network);
            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } else {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }
    }
}
