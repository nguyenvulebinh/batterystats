package android.com.batterystats.view.activity;

import android.com.batterystats.R;
import android.com.batterystats.view.fragment.BatteryHistoryFragment;
import android.com.batterystats.view.fragment.DetailStatsFragment;
import android.com.batterystats.view.fragment.EstimatedPowerUseFragment;
import android.com.batterystats.view.fragment.NotificationFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showFragment(R.id.nav_notification);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        showFragment(id);
        return true;
    }

    /**
     * Show specific fragment
     *
     * @param id
     */
    private void showFragment(int id) {
        Fragment fragment = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        String title = (String) toolbar.getTitle();
        if (id == R.id.nav_notification) {
            title = getResources().getString(R.string.notification);
            fragment = new NotificationFragment();
        } else if (id == R.id.nav_battery_history) {
            title = getResources().getString(R.string.battery_history);
            fragment = new BatteryHistoryFragment();
        } else if (id == R.id.nav_estimated) {
            title = getResources().getString(R.string.estimated_power_use);
            fragment = new EstimatedPowerUseFragment();
        } else if (id == R.id.nav_detail) {
            title = getResources().getString(R.string.detailed_stats);
            fragment = new DetailStatsFragment();
        }

        if (fragment != null) {
            ft.replace(R.id.main_contain, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            toolbar.setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
