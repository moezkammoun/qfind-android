package qfind.com.qfindappandroid.informationFragment;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("response")
    private String response;
    @SerializedName("code")
    private String code;
    @SerializedName("result")
    private ServiceProviderData result;

    public String getResponse() {
        return response;
    }

    public String getCode() {
        return code;
    }

    public ServiceProviderData getResult() {
        return result;
    }
}
