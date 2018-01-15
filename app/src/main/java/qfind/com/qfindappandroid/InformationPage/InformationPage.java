package qfind.com.qfindappandroid.InformationPage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.SimpleDividerItemDecoration;

public class InformationPage extends AppCompatActivity  implements  NavigationView.OnNavigationItemSelectedListener{

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
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    ArrayList<InformationPageModel> informationPages;
    ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerOpen(Gravity.END)){
                    drawerLayout.closeDrawer(Gravity.END);
                }
                else{
                    drawerLayout.openDrawer(Gravity.END);
                }
            }
        });



        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // hide back button
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//                // show hamberger icon
//                toggle.setDrawerIndicatorEnabled(true);
                toggle.syncState();
            }
        });



//        mainTitle.setText("Thiruvananthapuram Museum");
//        subTitle.setText("Pattom,Thiruvanathapuram");

        informationPages = new ArrayList<>();

        informationPages.add(new InformationPageModel(R.drawable.ic_menu_gallery,
                R.drawable.ic_menu_camera, "qwerty", R.drawable.ic_menu_send));
        informationPages.add(new InformationPageModel(R.drawable.ic_menu_gallery,
                R.drawable.ic_menu_camera, "asdfg", R.drawable.ic_menu_send));
        informationPages.add(new InformationPageModel(R.drawable.ic_menu_gallery,
                R.drawable.ic_menu_camera, "123454", R.drawable.ic_menu_send));
        informationPages.add(new InformationPageModel(R.drawable.ic_menu_gallery,
                R.drawable.ic_menu_camera, "fgfdg", R.drawable.ic_menu_send));
        informationPages.add(new InformationPageModel(R.drawable.ic_menu_gallery,
                R.drawable.ic_menu_camera, "ggfkyw", R.drawable.ic_menu_send));
        informationPages.add(new InformationPageModel(R.drawable.ic_menu_gallery,
                R.drawable.ic_menu_camera, "ywrtiyq", R.drawable.ic_menu_send));


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
        if(drawerLayout.isDrawerOpen(Gravity.END)){
            drawerLayout.closeDrawer(Gravity.END);
        }
        else{
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
}

