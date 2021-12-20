package cm.intelso.dev.travelezi;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cm.intelso.dev.travelezi.data.model.SharedPrefs;
import cm.intelso.dev.travelezi.utils.DataUtils;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, SearchBusLinesFragment.OnFragmentInteractionListener
        , SearchBusStopsFragment.OnFragmentInteractionListener, SearchBusRidesFragment.OnFragmentInteractionListener, BusLineFragment.OnFragmentInteractionListener
        , BusStopFragment.OnFragmentInteractionListener, NearBusFragment.OnFragmentInteractionListener, FavStopFragment.OnFragmentInteractionListener
    , FavRideFragment.OnFragmentInteractionListener, HistRideFragment.OnFragmentInteractionListener, HistStopFragment.OnFragmentInteractionListener
    , PreferencesFragment.OnFragmentInteractionListener, PlanningFragment.OnFragmentInteractionListener, PlanningStartsFragment.OnFragmentInteractionListener
{

    private long pressedTime;
    private SharedPrefs settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        settings = new SharedPrefs();

        if (savedInstanceState == null) {
//            switchFragment(new TravelTypeFragment());
            String tkn = settings.getStringValue(getApplicationContext(), settings.PREFS_USER_TOKEN_KEY);
            if(tkn != null && !tkn.isEmpty()) {
                String role = DataUtils.isUserOrDriver(tkn);
                if (role != null){
                    if(role == "DRIVER"){
                        switchFragment(new PlanningFragment());
                    } else if(role == "USER") {
                        switchFragment(new NearBusFragment());
                    }
                } else{
                    // Exit app and ask credentials
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Fragment", "fragment");
    }

    // switching fragment
    private void switchFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.app_content_frame, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null).commit();
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
        if (id == R.id.action_settings) {
//            Intent setting = new Intent(this, SettingsActivity.class);
//            setting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(setting);
            switchFragment(new PreferencesFragment());
            return true;
        } else if (id == R.id.action_home) {
            switchFragment(new NearBusFragment());
            return true;
        } else if (id == R.id.action_fav_rides) {
            switchFragment(new FavRideFragment());
            return true;
        } else if (id == R.id.action_fav_stops) {
            switchFragment(new FavStopFragment());
            return true;
        } else if (id == R.id.action_ride_hist) {
            switchFragment(new HistRideFragment());
            return true;
        } else if (id == R.id.action_stop_hist) {
            switchFragment(new HistStopFragment());
            return true;
        } else if (id == R.id.action_sign) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();

        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}
