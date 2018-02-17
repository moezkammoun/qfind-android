package qfind.com.qfindappandroid.informationFragment;

import com.google.gson.annotations.SerializedName;

import qfind.com.qfindappandroid.predictiveSearch.ServiceProviderResult;

public class ServiceProviderDataResponse {
    @SerializedName("response")
    private String response;
    @SerializedName("code")
    private String code;
    @SerializedName("result")
    private ServiceProviderResult result;

    public String getResponse() {
        return response;
    }

    public String getCode() {
        return code;
    }

    public ServiceProviderResult getResult() {
        return result;
    }
}
