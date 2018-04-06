package qfind.com.qfindappandroid.informationFragment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MoongedePC on 02-Apr-18.
 */

public class ServiceProviderInfoTimeList {
    @SerializedName("day")
    private String serviceProviderTimeDay;
    @SerializedName("service_provider_opening_time")
    private String serviceProviderOpeningTime;
    @SerializedName("service_provider_opening_time_arabic")
    private String serviceProviderOpeningTimeArabic;
    @SerializedName("service_provider_closing_time")
    private String serviceProviderClosingTime;
    @SerializedName("service_provider_opening_title")
    private String serviceProviderOpeningTitle;
    @SerializedName("service_provider_closing_title")
    private String serviceProviderClosingTitle;
    @SerializedName("service_provider_opening_title_arabic")
    private String serviceProviderOpeningTitleArabic;
    @SerializedName("service_provider_closing_title_arabic")
    private String serviceProviderClosingTitleArabic;
    @SerializedName("service_provider_closing_time_arabic")
    private String serviceProviderClosingTimeArabic;

    public ServiceProviderInfoTimeList(String serviceProviderTimeDay, String serviceProviderOpeningTime,
                                       String serviceProviderOpeningTimeArabic,
                                       String serviceProviderClosingTime,
                                       String serviceProviderOpeningTitle, String serviceProviderClosingTitle,
                                       String serviceProviderOpeningTitleArabic,
                                       String serviceProviderClosingTitleArabic, String serviceProviderClosingTimeArabic) {
        this.serviceProviderTimeDay = serviceProviderTimeDay;
        this.serviceProviderOpeningTime = serviceProviderOpeningTime;
        this.serviceProviderOpeningTimeArabic = serviceProviderOpeningTimeArabic;
        this.serviceProviderClosingTime = serviceProviderClosingTime;
        this.serviceProviderOpeningTitle = serviceProviderOpeningTitle;
        this.serviceProviderClosingTitle = serviceProviderClosingTitle;
        this.serviceProviderOpeningTitleArabic = serviceProviderOpeningTitleArabic;
        this.serviceProviderClosingTitleArabic = serviceProviderClosingTitleArabic;
        this.serviceProviderClosingTimeArabic = serviceProviderClosingTimeArabic;
    }

    public String getServiceProviderTimeDay() {
        return serviceProviderTimeDay;
    }

    public String getServiceProviderOpeningTime() {
        return serviceProviderOpeningTime;
    }

    public String getServiceProviderOpeningTimeArabic() {
        return serviceProviderOpeningTimeArabic;
    }

    public String getServiceProviderClosingTime() {
        return serviceProviderClosingTime;
    }

    public String getServiceProviderOpeningTitle() {
        return serviceProviderOpeningTitle;
    }

    public String getServiceProviderClosingTitle() {
        return serviceProviderClosingTitle;
    }

    public String getServiceProviderOpeningTitleArabic() {
        return serviceProviderOpeningTitleArabic;
    }

    public String getServiceProviderClosingTitleArabic() {
        return serviceProviderClosingTitleArabic;
    }

    public String getServiceProviderClosingTimeArabic() {
        return serviceProviderClosingTimeArabic;
    }
}
