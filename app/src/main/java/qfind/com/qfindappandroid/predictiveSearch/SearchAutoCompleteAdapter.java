package qfind.com.qfindappandroid.predictiveSearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.homeactivty.SearchData;


public class SearchAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<SearchData> resultList = new ArrayList<SearchData>();
    private SharedPreferences qFindPreferences;
    private String accessToken;
    private int language;
    String BASE_URL = "http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/";

    public SearchAutoCompleteAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public SearchData getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.predictive_dropdown_item, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.predictive_text)).setText(getItem(position).getSearchName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<SearchData> predictiveData = getPrediction(constraint.toString());
                    filterResults.values = predictiveData;
                    filterResults.count = predictiveData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<SearchData>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    public List<SearchData> getPrediction(String sName) {
        List<SearchData> ListData = new ArrayList<SearchData>();
        try {
            String searchKey = sName.trim();
            searchKey = searchKey.replace(" ", "%20");
            qFindPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            accessToken = qFindPreferences.getString("AccessToken", null);
            language = qFindPreferences.getInt("AppLanguage", 1);
            URL js = new URL(BASE_URL + "api/search?token=" + accessToken + "&search_key=" + searchKey + "&language=" + language);
            URLConnection jc = js.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));
            String line = reader.readLine();
            JSONObject jsonResponse = new JSONObject(line);
            JSONArray jsonArray = jsonResponse.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject r = jsonArray.getJSONObject(i);
                SearchData data = new SearchData();
                data.setItemId(r.getInt("item_id"));
                data.setSearchName(r.getString("search_name"));
                data.setSearchType(r.getInt("search_type"));
                ListData.add(data);
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;
    }
}
