package qfind.com.qfindappandroid.predictiveSearch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultsResponse {
    @SerializedName("response")
    private String response;
    @SerializedName("code")
    private String code;
    @SerializedName("result")
    private List<ServiceProviderResult> result;

    public String getResponse() {
        return response;
    }

    public String getCode() {
        return code;
    }

    public List<ServiceProviderResult> getResult() {
        return result;
    }

}
