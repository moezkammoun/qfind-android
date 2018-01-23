package qfind.com.qfindappandroid.InformationPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.MainActivity;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.SimpleDividerItemDecoration;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivityPresenter;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivityView;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragment;

public class InformationPage extends BaseActivity {

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.hamburger_menu)
    ImageView hamburgerMenu;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.sub_title)
    TextView subTitle;
//    @BindView(R.id.drawer_layout)
//    DrawerLayout drawerLayout;
//    @BindView(R.id.navigation)
//    BottomNavigationView navigation;
    ArrayList<InformationPageModel> informationPages;
    ActionBarDrawerToggle toggle;
    Fragment fragment;
    Menu bottomNavigationMenu;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.favorite_categories_bottom_menu:
////                    bottomNavigationMenu.findItem(R.id.qfind_us_menu).setIcon(R.drawable.ic_home_black_24dp);
////                    bottomNavigationMenu.findItem(R.id.category_history_menu).setIcon(R.drawable.ic_home_black_24dp);
////                    fragment = new CategoryFragment();
//                    Intent homeIntent= new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(homeIntent);
//                    break;
//                case R.id.qfind_us_menu:
//
////                    fragment = new CategoryFragment();
//                    Intent intent= new Intent(getApplicationContext(), InformationPage.class);
//                    startActivity(intent);
//                    break;
//                case R.id.category_history_menu:
////                    fragment = new CategoryFragment();
//                    Intent conteIntent= new Intent(getApplicationContext(), ContainerActivity.class);
//                    startActivity(conteIntent);
//                    break;
//            }
//            if (fragment != null) {
////                containerActivityPresenter.loadFragmentOnButtonClick(fragment);
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_container, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//            return true;
//        }
//
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

//        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.setDrawerListener(toggle);
//        toggle.setDrawerIndicatorEnabled(false);
//        toggle.syncState();
//        bottomNavigationMenu = navigation.getMenu();
//        bottomNavigationMenu.findItem(R.id.favorite_categories_bottom_menu)
//                .setIcon(R.drawable.ic_home_black_24dp);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerOpenCloseHandler();}
        });


//        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // hide back button
//                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
////                // show hamberger icon
//                toggle.syncState();
//            }
//        });


//        mainTitle.setText("Thiruvananthapuram Museum");
//        subTitle.setText("Pattom,Thiruvanathapuram");

        informationPages = new ArrayList<>();

        informationPages.add(new InformationPageModel(R.drawable.phone_icon,
                R.drawable.dot_icon, "00974 5551 5566", R.drawable.right_arrow));
        informationPages.add(new InformationPageModel(R.drawable.web_icon,
                R.drawable.dot_icon, "www.4season.com", R.drawable.right_arrow));
        informationPages.add(new InformationPageModel(R.drawable.location_icon,
                R.drawable.dot_icon, "Doha", R.drawable.right_arrow));
        informationPages.add(new InformationPageModel(R.drawable.clock_icon,
                R.drawable.dot_icon, "9:30 am-6:30 pm", R.drawable.right_arrow));
        informationPages.add(new InformationPageModel(R.drawable.mail_icon,
                R.drawable.dot_icon, "mo@hotmail.com", R.drawable.right_arrow));
        informationPages.add(new InformationPageModel(R.drawable.facebook_icon,
                R.drawable.dot_icon, "4season", R.drawable.right_arrow));



        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(this);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //creating recyclerview adapter
        InformationPageAdapter adapter = new InformationPageAdapter(this, informationPages);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (fullView.isDrawerOpen(Gravity.END)) {
            fullView.closeDrawer(Gravity.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.END);
//        return true;
//    }


//    @Override
//    public void loadFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}

