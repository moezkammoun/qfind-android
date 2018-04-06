package qfind.com.qfindappandroid.categoryfragment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MoongedePC on 26-Mar-18.
 */

public class ServiceProviderTimeList {

    @SerializedName("day")
    private String serviceProviderDay;
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

    public ServiceProviderTimeList(String day,String serviceProviderOpeningTime, String serviceProviderOpeningTimeArabic,
                                   String serviceProviderClosingTime, String serviceProviderOpeningTitle,
                                   String serviceProviderClosingTitle, String serviceProviderOpeningTitleArabic,
                                   String serviceProviderClosingTitleArabic, String serviceProviderClosingTimeArabic) {
        this.serviceProviderDay=day;
        this.serviceProviderOpeningTime = serviceProviderOpeningTime;
        this.serviceProviderOpeningTimeArabic = serviceProviderOpeningTimeArabic;
        this.serviceProviderClosingTime = serviceProviderClosingTime;
        this.serviceProviderOpeningTitle = serviceProviderOpeningTitle;
        this.serviceProviderClosingTitle = serviceProviderClosingTitle;
        this.serviceProviderOpeningTitleArabic = serviceProviderOpeningTitleArabic;
        this.serviceProviderClosingTitleArabic = serviceProviderClosingTitleArabic;
        this.serviceProviderClosingTimeArabic = serviceProviderClosingTimeArabic;
    }

    public String getServiceProviderDay() {
        return serviceProviderDay;
    }

    public void setServiceProviderDay(String serviceProviderDay) {
        this.serviceProviderDay = serviceProviderDay;
    }

    public String getServiceProviderOpeningTime() {
        return serviceProviderOpeningTime;
    }

    public void setServiceProviderOpeningTime(String serviceProviderOpeningTime) {
        this.serviceProviderOpeningTime = serviceProviderOpeningTime;
    }

    public String getServiceProviderOpeningTimeArabic() {
        return serviceProviderOpeningTimeArabic;
    }

    public void setServiceProviderOpeningTimeArabic(String serviceProviderOpeningTimeArabic) {
        this.serviceProviderOpeningTimeArabic = serviceProviderOpeningTimeArabic;
    }

    public String getServiceProviderClosingTime() {
        return serviceProviderClosingTime;
    }

    public void setServiceProviderClosingTime(String serviceProviderClosingTime) {
        this.serviceProviderClosingTime = serviceProviderClosingTime;
    }

    public String getServiceProviderOpeningTitle() {
        return serviceProviderOpeningTitle;
    }

    public void setServiceProviderOpeningTitle(String serviceProviderOpeningTitle) {
        this.serviceProviderOpeningTitle = serviceProviderOpeningTitle;
    }

    public String getServiceProviderClosingTitle() {
        return serviceProviderClosingTitle;
    }

    public void setServiceProviderClosingTitle(String serviceProviderClosingTitle) {
        this.serviceProviderClosingTitle = serviceProviderClosingTitle;
    }

    public String getServiceProviderOpeningTitleArabic() {
        return serviceProviderOpeningTitleArabic;
    }

    public void setServiceProviderOpeningTitleArabic(String serviceProviderOpeningTitleArabic) {
        this.serviceProviderOpeningTitleArabic = serviceProviderOpeningTitleArabic;
    }

    public String getServiceProviderClosingTitleArabic() {
        return serviceProviderClosingTitleArabic;
    }

    public void setServiceProviderClosingTitleArabic(String serviceProviderClosingTitleArabic) {
        this.serviceProviderClosingTitleArabic = serviceProviderClosingTitleArabic;
    }

    public String getServiceProviderClosingTimeArabic() {
        return serviceProviderClosingTimeArabic;
    }

    public void setServiceProviderClosingTimeArabic(String serviceProviderClosingTimeArabic) {
        this.serviceProviderClosingTimeArabic = serviceProviderClosingTimeArabic;
    }
}


