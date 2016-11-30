package com.about.cosmicos.aboutcosmicos;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_main);
        applyPrefs();
    }

    @Override
    public void onDestroy() {
        applyPrefs();
        super.onDestroy();

    }

    @Override
    public void onPause(){
        applyPrefs();
        super.onPause();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settingsintent = new Intent(this, SettingsActivity.class);
                startActivity(settingsintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void applyPrefs(){

        // Check if we should hide the launcher icon
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        if (settings.getBoolean("hide_icon", true)) {
            PackageManager p=this.getPackageManager();
            p.setComponentEnabledSetting(new ComponentName(this,AboutActivity.class), PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }else{
            PackageManager p = getPackageManager();
            ComponentName componentName = new ComponentName(this,AboutActivity.class);
            p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        }

    }
}
