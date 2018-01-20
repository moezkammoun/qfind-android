package qfind.com.qfindappandroid.searchResultsFragment;

import java.util.ArrayList;
import java.util.List;

import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.categoryfragment.Categories;

public class SearchResultsModel {
    private List<Categories> serachResultsList;

    public List<Categories> getSearchResults() {
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
        serachResultsList = new ArrayList<>();
        for (int i = 0; i < categoryItems.length; i++) {
             categories= new Categories( categoryItems[i],thumbnails[i]);
            serachResultsList.add(categories);
        }
        return serachResultsList;
    }


}
