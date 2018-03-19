package qfind.com.qfindappandroid.searchResultsFragment;

public class SearchedItem {
    private String providerName;
    private String providerLocation;
    private String providerNameArabic;
    private String providerLocationArabic;
    private String providerThumbnail;
    private String providerPhone;
    private String providerWebsite;
    private String providerAddress;
    private String providerOpeningTime;
    private String providerMail;
    private String providerFacebook;
    private String providerLinkedIn;
    private String providerInstagram;
    private String providerTwitter;
    private String providerSnapchat;
    private String providerGooglePlus;
    private String providerLatlong;
    private String providerLogo;
    private int providerId;
    private String providerOpeningTimeArabic;
    private String providerAddressArabic;
    private String providerClosingTime;
    private String providerClosingTimeArabic;
    private String providerOpeningTitle;
    private String providerClosingTitle;
    private String providerOpeningTitleArabic;
    private String providerClosingTitleArabic;

    public SearchedItem() {
    }


    public SearchedItem(String item, String itemDescription, String itemArabic, String itemDescriptionArabic,
                        String thumbnail, String phone, String website,
                        String address, String openingTime, String mail, String facebook, String linkedIn,
                        String instagram, String twitter, String snapchat, String googlePlus, String latLong, String logo,
                        int id, String openingTimeArabic, String addressArabic, String closingTime, String closingTimeArabic,
                        String openingTitle, String closingTitle, String openingTitleArabic, String closingTitleArabic) {
        this.providerName = item;
        this.providerLocation = itemDescription;
        this.providerNameArabic = itemArabic;
        this.providerLocationArabic = itemDescriptionArabic;
        this.providerThumbnail = thumbnail;
        this.providerPhone = phone;
        this.providerWebsite = website;
        this.providerAddress = address;
        this.providerOpeningTime = openingTime;
        this.providerMail = mail;
        this.providerFacebook = facebook;
        this.providerLinkedIn = linkedIn;
        this.providerInstagram = instagram;
        this.providerTwitter = twitter;
        this.providerSnapchat = snapchat;
        this.providerGooglePlus = googlePlus;
        this.providerLatlong = latLong;
        this.providerLogo = logo;
        this.providerId = id;
        this.providerOpeningTimeArabic = openingTimeArabic;
        this.providerAddressArabic = addressArabic;
        this.providerClosingTime = closingTime;
        this.providerClosingTimeArabic = closingTimeArabic;
        this.providerOpeningTitle = openingTitle;
        this.providerClosingTitle = closingTitle;
        this.providerOpeningTitleArabic = openingTitleArabic;
        this.providerClosingTitleArabic = closingTitleArabic;

    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderLocation() {
        return providerLocation;
    }

    public String getProviderNameArabic() {
        return providerNameArabic;
    }

    public String getProviderLocationArabic() {
        return providerLocationArabic;
    }

    public String getProviderThumbnail() {
        return providerThumbnail;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    public String getProviderWebsite() {
        return providerWebsite;
    }

    public String getProviderAddress() {
        return providerAddress;
    }

    public String getProviderOpeningTime() {
        return providerOpeningTime;
    }

    public String getProviderMail() {
        return providerMail;
    }

    public String getProviderFacebook() {
        return providerFacebook;
    }

    public String getProviderLinkedIn() {
        return providerLinkedIn;
    }

    public String getProviderInstagram() {
        return providerInstagram;
    }

    public String getProviderTwitter() {
        return providerTwitter;
    }

    public String getProviderSnapchat() {
        return providerSnapchat;
    }

    public String getProviderGooglePlus() {
        return providerGooglePlus;
    }

    public String getProviderLatlong() {
        return providerLatlong;
    }

    public String getProviderLogo() {
        return providerLogo;
    }

    public int getProviderId() {
        return providerId;
    }

    public String getProviderOpeningTimeArabic() {
        return providerOpeningTimeArabic;
    }

    public String getProviderAddressArabic() {
        return providerAddressArabic;
    }

    public String getProviderClosingTime() {
        return providerClosingTime;
    }

    public String getProviderClosingTimeArabic() {
        return providerClosingTimeArabic;
    }

    public String getProviderOpeningTitle() {
        return providerOpeningTitle;
    }

    public String getProviderClosingTitle() {
        return providerClosingTitle;
    }

    public String getProviderOpeningTitleArabic() {
        return providerOpeningTitleArabic;
    }

    public String getProviderClosingTitleArabic() {
        return providerClosingTitleArabic;
    }
}
