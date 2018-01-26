package qfind.com.qfindappandroid.homeactivty;


import com.google.gson.annotations.SerializedName;

public class QFindOfTheDayDetails {
    @SerializedName("response")
    private String response;
    @SerializedName("code")
    private String code;
    @SerializedName("result")
    private AdsData adsData;

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

    public AdsData getAdsData() {
        return adsData;
    }

    public void setAdsData(AdsData adsData) {
        this.adsData = adsData;
    }

}
