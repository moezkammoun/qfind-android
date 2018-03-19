package qfind.com.qfindappandroid.categoryfragment;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by dilee on 01-02-2018.
 */

public class ServiceProviderList {
    @SerializedName("response")
    private String response;
    @SerializedName("code")
    private String code;
    @SerializedName("result")
    private ArrayList<ServiceProviderListDetails> serviceProviderListDetails;

    public String getResponse() {
        return response;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<ServiceProviderListDetails> getServiceProviderListDetails() {
        return serviceProviderListDetails;
    }


}
