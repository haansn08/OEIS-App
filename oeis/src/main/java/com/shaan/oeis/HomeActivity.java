package com.shaan.oeis;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Constructor;

/**
 * The startup activity
 * Handles mostly the left drawer menu
 * Content is in the respective fragments
 */
public class HomeActivity extends Activity {
    private ActionBarDrawerToggle drawerToggle;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.home_layout);

        setupDrawer();

        if (savedInstance == null)
            swapFragment(0);

    }

    /**
     * Get the fragment that is associated with the i-th menu item
     * @param i Number of the menu item
     * @return Respective fragment
     */
    private Fragment getFragment(int i)
    {

        String fragmentClassNames[] = getResources().getStringArray(R.array.home_drawer_fragments);
        try {
            Class<?>requestedClass = Class.forName(fragmentClassNames[i]);
            Constructor<?> constructor = requestedClass.getConstructor();
            return (Fragment)constructor.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Displays the fragment of the i-th menu item
     * @param i Number of the menu item
     */
    private void swapFragment(int i){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.home_content, getFragment(i))
                .commit();
    }

    /**
     * Display and enable home button,
     * fill the listview with menu items
     */
    private void setupDrawer() {
        //Enable Home-button to act as a trigger for the drawer
        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.home_drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.app_name,
                R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Fill ListView with navigation items
        final ListView drawer = (ListView)findViewById(R.id.home_drawer);
        HomeDrawerAdapter drawerAdapter = new HomeDrawerAdapter(this);
        drawer.setAdapter(drawerAdapter);
        drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                swapFragment(i);
                drawerLayout.closeDrawer(drawer);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}
