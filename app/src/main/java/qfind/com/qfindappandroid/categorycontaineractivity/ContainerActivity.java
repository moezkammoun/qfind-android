package qfind.com.qfindappandroid.categorycontaineractivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragment;

public class ContainerActivity extends AppCompatActivity implements ContainerActivityView {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    ContainerActivityPresenterImpl containerActivityPresenterImpl = new ContainerActivityPresenterImpl();
    Fragment fragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.favorite_categories_bottom_menu:
                    fragment = new CategoryFragment();
                    break;
                case R.id.qfind_us_menu:
                    fragment = new CategoryFragment();
                    break;
                case R.id.category_history_menu:
                    fragment = new CategoryFragment();
                    break;
            }
            if (fragment != null) {
                containerActivityPresenterImpl.loadFragmentOnButtonClick(fragment);
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        containerActivityPresenterImpl.loadFragmentOncreate(this,new CategoryFragment());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        // your code.
    }
}
