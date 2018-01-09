package qfind.com.qfindappandroid.categorycontaineractivity;


import android.support.v4.app.Fragment;

/**
 * Created by dilee on 07-01-2018.
 */

public class ContainerActivityPresenterImpl {

    ContainerActivityView containerActivityView;

//    public ContainerActivityPresenterImpl(ContainerActivityView containerActivityView, Fragment fragment) {
//        this.containerActivityView = containerActivityView;
//        containerActivityView.loadFragment(fragment);
//    }
    public ContainerActivityPresenterImpl(){

    }
    public void loadFragmentOncreate(ContainerActivityView containerActivityView, Fragment fragment){
        this.containerActivityView = containerActivityView;
        containerActivityView.loadFragment(fragment);

    }
    public void loadFragmentOnButtonClick(Fragment fragment){
        containerActivityView.loadFragment(fragment);
    }
//    public ContainerActivityPresenterImpl(Fragment fragment){
//        this.containerActivityView = containerActivityView;
//
//    }
}
