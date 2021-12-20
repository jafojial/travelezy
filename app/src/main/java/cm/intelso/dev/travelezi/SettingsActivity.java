package cm.intelso.dev.travelezi;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import cm.intelso.dev.travelezi.data.model.SharedPrefs;
import cm.intelso.dev.travelezi.utils.DataUtils;


/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity implements PreferencesFragment.OnFragmentInteractionListener{

    private ActionBar actionBar;
    Fragment newContent;

    SharedPrefs settings;
    DataUtils dataUtils = new DataUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataUtils.setLocale(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("LANGUAGE", "DEFAULT"));
        setContentView(R.layout.activity_settings);

        settings = new SharedPrefs();

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        //actionBar.setTitle(R.string.parameters);
        actionBar.setIcon(R.mipmap.ic_touristique);
        actionBar.setDisplayShowTitleEnabled(true);*/

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new PreferencesFragment() ).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null).commit();

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_home).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
//        menu.findItem(R.id.action_logout).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*
        if (id == android.R.id.home) {
            // Respond to the action bar's Up/Home button
            Intent goToParent = new Intent(this, LoginActivity.class);
            goToParent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(goToParent);
            return true;
        }
        */
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
