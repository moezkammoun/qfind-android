package qfind.com.qfindappandroid.informationFragment;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import qfind.com.qfindappandroid.predictiveSearch.ServiceProviderTimeList;


public class ServiceProviderData {
    @SerializedName("service_provider_name")
    private String serviceProviderName;
    @SerializedName("service_provider_address")
    private String serviceProviderAddress;
    @SerializedName("service_provider_location")
    private String serviceProviderLocation;
    @SerializedName("service_provider_category")
    private String serviceProviderCategory;
    @SerializedName("service_provider_name_arabic")
    private String serviceProviderNameArabic;
    @SerializedName("service_provider_address_arabic")
    private String serviceProviderAddressArabic;
    @SerializedName("service_provider_location_arabic")
    private String serviceProviderLocationArabic;
    @SerializedName("service_provider_category_arabic")
    private String serviceProviderCategoryArabic;
    @SerializedName("id")
    private int serviceProviderId;
    @SerializedName("service_provider_mail_account")
    private String serviceProviderMail;
    @SerializedName("service_provider_website")
    private String serviceProviderWebsite;
    @SerializedName("service_provider_mobile_number")
    private String serviceProviderMobile;
    @SerializedName("service_provider_map_location")
    private String serviceProviderMapLocation;
    @SerializedName("service_provider_facebook_page")
    private String serviceProviderFacebook;
    @SerializedName("service_provider_linkdin_page")
    private String serviceProviderLinkedin;
    @SerializedName("service_provider_instagram_page")
    private String serviceProviderInstagram;
    @SerializedName("service_provider_twitter_page")
    private String serviceProviderTwitter;
    @SerializedName("service_provider_snapchat_page")
    private String serviceProviderSnapchat;
    @SerializedName("service_provider_googleplus_page")
    private String serviceProviderGoogleplus;
    @SerializedName("service_provider_logo")
    private String serviceProviderLogo;
    @SerializedName("service_provider_time")
    ArrayList<ServiceProviderInfoTimeList> serviceProviderTimeLists;


    public ServiceProviderData(String serviceProviderName, String serviceProviderAddress, String serviceProviderLocation,
                               String serviceProviderCategory, String serviceProviderNameArabic,
                               String serviceProviderAddressArabic, String serviceProviderLocationArabic,
                               String serviceProviderCategoryArabic,
                               int serviceProviderId, String serviceProviderMail,
                               String serviceProviderWebsite, String serviceProviderMobile,
                               String serviceProviderMapLocation, String serviceProviderFacebook,
                               String serviceProviderLinkedin,
                               String serviceProviderInstagram, String serviceProviderTwitter,
                               String serviceProviderSnapchat, String serviceProviderGoogleplus,
                               String serviceProviderLogo,
                               ArrayList<ServiceProviderInfoTimeList> serviceProviderTimeLists) {
        this.serviceProviderName = serviceProviderName;
        this.serviceProviderAddress = serviceProviderAddress;
        this.serviceProviderLocation = serviceProviderLocation;
        this.serviceProviderCategory = serviceProviderCategory;
        this.serviceProviderNameArabic = serviceProviderNameArabic;
        this.serviceProviderAddressArabic = serviceProviderAddressArabic;
        this.serviceProviderLocationArabic = serviceProviderLocationArabic;
        this.serviceProviderCategoryArabic = serviceProviderCategoryArabic;
        this.serviceProviderId = serviceProviderId;
        this.serviceProviderMail = serviceProviderMail;
        this.serviceProviderWebsite = serviceProviderWebsite;
        this.serviceProviderMobile = serviceProviderMobile;
        this.serviceProviderMapLocation = serviceProviderMapLocation;
        this.serviceProviderFacebook = serviceProviderFacebook;
        this.serviceProviderLinkedin = serviceProviderLinkedin;
        this.serviceProviderInstagram = serviceProviderInstagram;
        this.serviceProviderTwitter = serviceProviderTwitter;
        this.serviceProviderSnapchat = serviceProviderSnapchat;
        this.serviceProviderGoogleplus = serviceProviderGoogleplus;
        this.serviceProviderLogo = serviceProviderLogo;
        this.serviceProviderTimeLists = serviceProviderTimeLists;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public String getServiceProviderAddress() {
        return serviceProviderAddress;
    }

    public String getServiceProviderLocation() {
        return serviceProviderLocation;
    }

    public String getServiceProviderCategory() {
        return serviceProviderCategory;
    }

    public String getServiceProviderNameArabic() {
        return serviceProviderNameArabic;
    }

    public String getServiceProviderAddressArabic() {
        return serviceProviderAddressArabic;
    }

    public String getServiceProviderLocationArabic() {
        return serviceProviderLocationArabic;
    }

    public String getServiceProviderCategoryArabic() {
        return serviceProviderCategoryArabic;
    }

    public int getServiceProviderId() {
        return serviceProviderId;
    }

    public String getServiceProviderMail() {
        return serviceProviderMail;
    }

    public String getServiceProviderWebsite() {
        return serviceProviderWebsite;
    }

    public String getServiceProviderMobile() {
        return serviceProviderMobile;
    }

    public String getServiceProviderMapLocation() {
        return serviceProviderMapLocation;
    }

    public String getServiceProviderFacebook() {
        return serviceProviderFacebook;
    }

    public String getServiceProviderLinkedin() {
        return serviceProviderLinkedin;
    }

    public String getServiceProviderInstagram() {
        return serviceProviderInstagram;
    }

    public String getServiceProviderTwitter() {
        return serviceProviderTwitter;
    }

    public String getServiceProviderSnapchat() {
        return serviceProviderSnapchat;
    }

    public String getServiceProviderGoogleplus() {
        return serviceProviderGoogleplus;
    }

    public String getServiceProviderLogo() {
        return serviceProviderLogo;
    }

    public ArrayList<ServiceProviderInfoTimeList> getServiceProviderTimeLists() {
        return serviceProviderTimeLists;
    }
}
