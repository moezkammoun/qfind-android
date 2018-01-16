package qfind.com.qfindappandroid.categoryfragment;

import java.util.ArrayList;
import java.util.List;

import qfind.com.qfindappandroid.R;

/**
 * Created by dilee on 07-01-2018.
 */

public class CategoryFragmentModel {
    private List<Categories> categoriesList;


    public ArrayList<String> getAdsImages() {
        ArrayList<String> ads = new ArrayList<String>();
        ads.add("http://androidblog.esy.es/images/cupcake-1.png");
        ads.add("http://androidblog.esy.es/images/donut-2.png");
        ads.add("http://androidblog.esy.es/images/eclair-3.png");
        ads.add("http://androidblog.esy.es/images/froyo-4.png");
        ads.add("http://androidblog.esy.es/images/gingerbread-5.png");
        return ads;
    }

    public List<Categories> getCategoriesDetails() {
        int[] thumbnails = new int[]{
                R.drawable.car_service,
                R.drawable.cloth_stores,
                R.drawable.dentist,
                R.drawable.exterior_designers,
                R.drawable.hypermarket,
                R.drawable.interior_designers,
                R.drawable.restaurents,
                R.drawable.toy_store,
                R.drawable.car_service,
                R.drawable.cloth_stores,
                R.drawable.dentist,
                R.drawable.car_service,
                R.drawable.cloth_stores
        };
        String[] categoryItems = new String[]{
                "Ministries",
                "Restaurants",
                "Exterior Designers",
                "Hospitals",
                "Clothing Stores",
                "Hypermarkets",
                "Hotels and Resorts",
                "Interior Designers",
                "Car Service",
                "Shopping malls",
                "toy stores",
                "Dentist"

        };
        Categories categories;
        categoriesList = new ArrayList<>();
        for (int i = 0; i < categoryItems.length; i++) {
             categories= new Categories( categoryItems[i],thumbnails[i]);
            categoriesList.add(categories);
        }
        return categoriesList;
    }


}
