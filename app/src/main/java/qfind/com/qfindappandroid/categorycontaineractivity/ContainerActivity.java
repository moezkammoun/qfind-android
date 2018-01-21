package qfind.com.qfindappandroid.categorycontaineractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.InformationPage.InformationPage;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragment;
import qfind.com.qfindappandroid.historyPage.HistoryFragment;
import qfind.com.qfindappandroid.settingspagefragment.SettingsFragment;
import qfind.com.qfindappandroid.termsandconditionfragment.TermsandConditionFragment;

public class ContainerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ContainerActivityView {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.sideMenu)
    ImageView hambergerMenu;
    ContainerActivityPresenter containerActivityPresenter = new ContainerActivityPresenter();
    Fragment fragment;
    Menu bottomNavigationMenu;
    DrawerLayout drawer;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.favorite_categories_bottom_menu:
//                    bottomNavigationMenu.findItem(R.id.qfind_us_menu).setIcon(R.drawable.ic_home_black_24dp);
//                    bottomNavigationMenu.findItem(R.id.category_history_menu).setIcon(R.drawable.ic_home_black_24dp);
                    fragment = new CategoryFragment();
                    break;
                case R.id.qfind_us_menu:
//                    fragment = new CategoryFragment();
                    Intent intent= new Intent(getApplicationContext(), InformationPage.class);
                    startActivity(intent);
                    break;
                case R.id.category_history_menu:
                    fragment = new HistoryFragment();
                    break;
            }
            if (fragment != null) {
                containerActivityPresenter.loadFragmentOnButtonClick(fragment);
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottomNavigationMenu = navigation.getMenu();
        bottomNavigationMenu.findItem(R.id.favorite_categories_bottom_menu).setIcon(R.drawable.ic_home_black_24dp);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // set navigation view width as half of screen
        int width = getResources().getDisplayMetrics().widthPixels / 3;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) navigationView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - width;
        navigationView.setLayoutParams(params);

        containerActivityPresenter.loadFragmentOncreate(this, new CategoryFragment());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setVisibility(View.VISIBLE);
        hambergerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(Gravity.END)) {
                    drawer.closeDrawer(Gravity.END);
                } else {
                    drawer.openDrawer(Gravity.END);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.about_us_menu_item) {
            // Handle the camera action
        } else if (id == R.id.qfinder_menu_item) {

        } else if (id == R.id.terms_menu_item) {
            fragment = new TermsandConditionFragment();
            navigation.setVisibility(View.GONE);
            containerActivityPresenter.loadFragmentOnButtonClick(fragment);
        } else if (id == R.id.contact_menu_item) {

        } else if (id == R.id.settings_menu_item) {
            fragment = new SettingsFragment();
            navigation.setVisibility(View.GONE);
            containerActivityPresenter.loadFragmentOnButtonClick(fragment);
        }
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
