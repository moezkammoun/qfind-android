package qfind.com.qfindappandroid.InformationPage;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import qfind.com.qfindappandroid.historyPage.HistoryFragment;
import qfind.com.qfindappandroid.searchResultsFragment.SearchResultsFragment;
import qfind.com.qfindappandroid.settingspagefragment.SettingsFragment;
import qfind.com.qfindappandroid.termsandconditionfragment.TermsandConditionFragment;

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
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.normal_toolbar)
    LinearLayout normalToolbar;
    @BindView(R.id.info_toolbar)
    LinearLayout infoToolbar;
    @BindView(R.id.info_container)
    LinearLayout infoContainer;
    @BindView(R.id.frame_container)
    FrameLayout frameLayout;
    @BindView(R.id.info_toolbar_layout)
    LinearLayout infoToolbarContainer;


    ArrayList<InformationPageModel> informationPages;
    ActionBarDrawerToggle toggle;
    Fragment fragment;
    Menu bottomNavigationMenu;
    ArrayAdapter<String> adapter;
    ImageView searchButton, sideMenu;
    AutoCompleteTextView autoCompleteTextView;
    View keyboard;
    InputMethodManager imm;


    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.favorite_categories_bottom_menu:
                    fragment = new CategoryFragment();
                    break;
                case R.id.qfind_us_menu:
                    Intent intent = new Intent(getApplicationContext(), InformationPage.class);
                    startActivity(intent);
                    break;
                case R.id.category_history_menu:
                    fragment = new HistoryFragment();
                    break;
            }
            if (fragment != null) {
                loadFragment(fragment);
                hideInfoView();
            }
            return true;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        autoCompleteTextView = (AutoCompleteTextView) infoToolbarContainer.findViewById(R.id.autoCompleteEditText);
        searchButton = (ImageView) infoToolbarContainer.findViewById(R.id.search_icon);
        sideMenu = (ImageView) infoToolbarContainer.findViewById(R.id.sideMenu);
        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerOpenCloseHandler();
            }
        });
        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerOpenCloseHandler();
            }
        });
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

        String[] FINDINGS = new String[]{
                "Hotel", "Hotel", "Hotel", "Hotel", "Bar", "Dentist", "Exterior Designer",
                "Restaurant", "الفندق", "الفندق", "الفندق"
        };
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, FINDINGS);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setDropDownBackgroundResource(R.color.color_white);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!autoCompleteTextView.getText().toString().equals("")) {
                    Toast.makeText(InformationPage.this, "Finding...", Toast.LENGTH_SHORT).show();
                    keyboard = getCurrentFocus();
                    if (keyboard != null) {
                        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(keyboard.getWindowToken(), 0);
                    }
                    fragment = new SearchResultsFragment();
                    loadFragment(fragment);
                }
            }
        });

        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(this);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //creating recyclerview adapter
        InformationPageAdapter adapter = new InformationPageAdapter(this, informationPages);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected boolean useToolbar() {
        return false;
    }

    @Override
    protected boolean useBottomBar() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void setupSideMenuItemClickListener() {
        sideMenuAboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullView.closeDrawer(GravityCompat.END);
            }
        });
        sideMenuQFinderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullView.closeDrawer(GravityCompat.END);
            }
        });
        sideMenuTermsAndConditionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if (!(fragment instanceof TermsandConditionFragment)) {
                    InformationPage.this.fragment = new TermsandConditionFragment();
                    loadFragment(InformationPage.this.fragment);
                }
                fullView.closeDrawer(GravityCompat.END);
                hideInfoView();
            }
        });
        sideMenuContactUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fullView.closeDrawer(GravityCompat.END);
            }
        });
        sideMenuSettingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if (!(fragment instanceof SettingsFragment)) {
                    InformationPage.this.fragment = new SettingsFragment();
                    loadFragment(InformationPage.this.fragment);
                }
                fullView.closeDrawer(GravityCompat.END);
                hideInfoView();
            }
        });

    }

    public void hideInfoView() {
        infoToolbar.setVisibility(View.GONE);
        infoContainer.setVisibility(View.GONE);
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
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

