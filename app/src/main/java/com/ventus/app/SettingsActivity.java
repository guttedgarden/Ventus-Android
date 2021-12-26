package com.ventus.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ventus.app.tools.Shared;

public class SettingsActivity extends AppCompatActivity {

    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final FloatingActionButton float_btn = findViewById(R.id.floatingActionButton);
        final Boolean isNigthModeOn = Shared.getBoolPreferences(SettingsActivity.this,"NightMode",false);
        final TextView textWithVersionInfo = findViewById(R.id.TextWithVersionInfo);
        textWithVersionInfo.setText("Версия "+ BuildConfig.VERSION_CODE + " (" +BuildConfig.VERSION_NAME+")");
        if(isNigthModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        //Проверка SDK
        if(Build.VERSION.SDK_INT>=21){
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.fragmentBackground));
        }

        if ((Shared.existPreferences(this,"URL_REQUEST_CONFIG5"))){
            EditText ipConfigEditText = (EditText) findViewById(R.id.ipEditText);
            ipConfigEditText.setHint(Shared.getStringPreferences(this,"URL_REQUEST_CONFIG1"));
        }

        float_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isNigthModeOn){
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            Shared.saveBoolPreferences(SettingsActivity.this,"NightMode",false);
                            Intent intent = new Intent("android.intent.action.SettingsActivity");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.enter_activity,R.anim.exit_activity);
                        }
                        else{
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            Shared.saveBoolPreferences(SettingsActivity.this,"NightMode",true);
                            Intent intent = new Intent("android.intent.action.SettingsActivity");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.enter_activity,R.anim.exit_activity);
                        }
                    }
                }
        );
    }
    public void onClickBackButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void onClickSaveButton(View view) {
        EditText ipConfigEditText = (EditText) findViewById(R.id.ipEditText);
        if (ipConfigEditText.getText().toString().matches("")) {
            Toast.makeText(this, "Please insert the IP address in the line", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            ipConfigEditText.setHint(ipConfigEditText.getText().toString());
            Shared.saveStringPreferences(this,"URL_REQUEST_CONFIG1",ipConfigEditText.getText().toString());
            Shared.saveStringPreferences(this,"URL_REQUEST_CONFIG2",ipConfigEditText.getText().toString());
            Shared.saveStringPreferences(this,"URL_REQUEST_CONFIG3",ipConfigEditText.getText().toString());
            Shared.saveStringPreferences(this,"URL_REQUEST_CONFIG4",ipConfigEditText.getText().toString());
            Shared.saveStringPreferences(this,"URL_REQUEST_CONFIG5",ipConfigEditText.getText().toString());
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
