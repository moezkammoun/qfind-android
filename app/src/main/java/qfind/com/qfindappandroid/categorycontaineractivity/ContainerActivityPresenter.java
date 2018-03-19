package qfind.com.qfindappandroid.categorycontaineractivity;


import android.support.v4.app.Fragment;

/**
 * Created by dilee on 07-01-2018.
 */

public class ContainerActivityPresenter {

    ContainerActivityView containerActivityView;

    public ContainerActivityPresenter() {

    }

    public void loadFragmentOncreate(ContainerActivityView containerActivityView, Fragment fragment) {
        this.containerActivityView = containerActivityView;
        containerActivityView.loadFragment(fragment);
    }

    public void loadFragmentOnButtonClick(Fragment fragment) {
        containerActivityView.loadFragment(fragment);
    }
}
