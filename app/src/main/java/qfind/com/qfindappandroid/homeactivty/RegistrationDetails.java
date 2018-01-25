package qfind.com.qfindappandroid.homeactivty;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dilee on 25-01-2018.
 */

public class RegistrationDetails {
    @SerializedName("response")
    private String response;
    @SerializedName("code")
    private String code;
    @SerializedName("result")
    private String accessToken;

    public String getResponse() {
        return response;
    }

    public String getCode() {
        return code;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
