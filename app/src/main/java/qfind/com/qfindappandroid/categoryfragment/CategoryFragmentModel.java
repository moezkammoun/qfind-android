package qfind.com.qfindappandroid.categoryfragment;

import java.util.ArrayList;
import java.util.List;

import cn.lightsky.infiniteindicator.Page;
import qfind.com.qfindappandroid.R;

/**
 * Created by dilee on 07-01-2018.
 */

public class CategoryFragmentModel {
    private List<Categories> categoriesList;


    public ArrayList<Page> getAdsImages() {
        ArrayList<Page> ads = new ArrayList<>();
        ads.add(new Page("", R.drawable.banner));
        ads.add(new Page("", R.drawable.car_service));
        ads.add(new Page("", R.drawable.cloth_stores));
        ads.add(new Page("", R.drawable.dentist));
        ads.add(new Page("", R.drawable.exterior_designers));
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
    public List<Categories> getSubCategoriesDetails() {

        String[] subCategoryItems = new String[]{
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
        String[] subCategoryItemsDescription = new String[]{
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
        Categories categories;
        categoriesList = new ArrayList<>();
        for (int i = 0; i < subCategoryItems.length; i++) {
            categories= new Categories( subCategoryItems[i],subCategoryItemsDescription[i],thumbnails[i]);
            categoriesList.add(categories);
        }
        return categoriesList;
    }

}
