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

    public ServiceProviderDataResponse(String response, String code, ServiceProviderResult result) {
        this.response = response;
        this.code = code;
        this.result = result;
    }

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
