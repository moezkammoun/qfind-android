package qfind.com.qfindappandroid.homeactivty;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import qfind.com.qfindappandroid.informationFragment.ServiceProviderData;

public class SearchResponse {
    @SerializedName("response")
    private String response;
    @SerializedName("code")
    private String code;
    @SerializedName("result")
    private List<SearchData> result;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<SearchData> getResult() {
        return result;
    }

    public void setResult(List<SearchData> result) {
        this.result = result;
    }
}
