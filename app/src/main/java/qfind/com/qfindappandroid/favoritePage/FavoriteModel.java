package qfind.com.qfindappandroid.favoritePage;

/**
 * Created by MoongedePC on 23-Jan-18.
 */

public class FavoriteModel {

    int id;
    private String item;
    private String itemDescription;
    private String url;
    private int pageId;
    private String datetime;
    private String itemArabic;
    private String itemDescriptionArabic;
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
    private String providerOpeningTimeArabic;
    private String providerAddressArabic;
    private String providerClosingTime;
    private String providerClosingTimeArabic;
    private String providerOpeningTitle;
    private String providerClosingTitle;
    private String providerOpeningTitleArabic;
    private String providerClosingTitleArabic;

    public FavoriteModel() {
    }

    public FavoriteModel(String item, String itemDescription, String itemArabic, String itemDescriptionArabic,
                         String url, int pageId, String providerPhone, String providerWebsite,
                         String providerAddress, String providerOpeningTime, String providerMail,
                         String providerFacebook, String providerLinkedIn, String providerInstagram,
                         String providerTwitter, String providerSnapchat, String providerGooglePlus,
                         String providerLatlong, String datetime, String providerOpeningTimeArabic,
                         String providerAddressArabic, String providerClosingTime, String providerClosingTimeArabic,
                         String providerOpeningTitle, String providerClosingTitle, String providerOpeningTitleArabic,
                         String providerClosingTitleArabic) {
        this.item = item;
        this.itemDescription = itemDescription;
        this.itemArabic = itemArabic;
        this.itemDescriptionArabic = itemDescriptionArabic;
        this.url = url;
        this.pageId = pageId;
        this.providerPhone = providerPhone;
        this.providerWebsite = providerWebsite;
        this.providerAddress = providerAddress;
        this.providerOpeningTime = providerOpeningTime;
        this.providerMail = providerMail;
        this.providerFacebook = providerFacebook;
        this.providerLinkedIn = providerLinkedIn;
        this.providerInstagram = providerInstagram;
        this.providerTwitter = providerTwitter;
        this.providerSnapchat = providerSnapchat;
        this.providerGooglePlus = providerGooglePlus;
        this.providerLatlong = providerLatlong;
        this.datetime = datetime;
        this.providerOpeningTimeArabic = providerOpeningTimeArabic;
        this.providerAddressArabic = providerAddressArabic;
        this.providerClosingTime = providerClosingTime;
        this.providerClosingTimeArabic = providerClosingTimeArabic;
        this.providerOpeningTitle = providerOpeningTitle;
        this.providerClosingTitle = providerClosingTitle;
        this.providerOpeningTitleArabic = providerOpeningTitleArabic;
        this.providerClosingTitleArabic = providerClosingTitleArabic;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemArabic() {
        return itemArabic;
    }

    public void setItemArabic(String itemArabic) {
        this.itemArabic = itemArabic;
    }

    public String getItemDescriptionArabic() {
        return itemDescriptionArabic;
    }

    public void setItemDescriptionArabic(String itemDescriptionArabic) {
        this.itemDescriptionArabic = itemDescriptionArabic;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getProviderThumbnail() {
        return providerThumbnail;
    }

    public void setProviderThumbnail(String providerThumbnail) {
        this.providerThumbnail = providerThumbnail;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }

    public String getProviderWebsite() {
        return providerWebsite;
    }

    public void setProviderWebsite(String providerWebsite) {
        this.providerWebsite = providerWebsite;
    }

    public String getProviderAddress() {
        return providerAddress;
    }

    public void setProviderAddress(String providerAddress) {
        this.providerAddress = providerAddress;
    }

    public String getProviderOpeningTime() {
        return providerOpeningTime;
    }

    public void setProviderOpeningTime(String providerOpeningTime) {
        this.providerOpeningTime = providerOpeningTime;
    }

    public String getProviderMail() {
        return providerMail;
    }

    public void setProviderMail(String providerMail) {
        this.providerMail = providerMail;
    }

    public String getProviderFacebook() {
        return providerFacebook;
    }

    public void setProviderFacebook(String providerFacebook) {
        this.providerFacebook = providerFacebook;
    }

    public String getProviderLinkedIn() {
        return providerLinkedIn;
    }

    public void setProviderLinkedIn(String providerLinkedIn) {
        this.providerLinkedIn = providerLinkedIn;
    }

    public String getProviderInstagram() {
        return providerInstagram;
    }

    public void setProviderInstagram(String providerInstagram) {
        this.providerInstagram = providerInstagram;
    }

    public String getProviderTwitter() {
        return providerTwitter;
    }

    public void setProviderTwitter(String providerTwitter) {
        this.providerTwitter = providerTwitter;
    }

    public String getProviderSnapchat() {
        return providerSnapchat;
    }

    public void setProviderSnapchat(String providerSnapchat) {
        this.providerSnapchat = providerSnapchat;
    }

    public String getProviderGooglePlus() {
        return providerGooglePlus;
    }

    public void setProviderGooglePlus(String providerGooglePlus) {
        this.providerGooglePlus = providerGooglePlus;
    }

    public String getProviderLatlong() {
        return providerLatlong;
    }

    public void setProviderLatlong(String providerLatlong) {
        this.providerLatlong = providerLatlong;
    }

    public String getProviderOpeningTimeArabic() {
        return providerOpeningTimeArabic;
    }

    public void setProviderOpeningTimeArabic(String providerOpeningTimeArabic) {
        this.providerOpeningTimeArabic = providerOpeningTimeArabic;
    }

    public String getProviderAddressArabic() {
        return providerAddressArabic;
    }

    public void setProviderAddressArabic(String providerAddressArabic) {
        this.providerAddressArabic = providerAddressArabic;
    }

    public String getProviderClosingTime() {
        return providerClosingTime;
    }

    public void setProviderClosingTime(String providerClosingTime) {
        this.providerClosingTime = providerClosingTime;
    }

    public String getProviderClosingTimeArabic() {
        return providerClosingTimeArabic;
    }

    public void setProviderClosingTimeArabic(String providerClosingTimeArabic) {
        this.providerClosingTimeArabic = providerClosingTimeArabic;
    }

    public String getProviderOpeningTitle() {
        return providerOpeningTitle;
    }

    public void setProviderOpeningTitle(String providerOpeningTitle) {
        this.providerOpeningTitle = providerOpeningTitle;
    }

    public String getProviderClosingTitle() {
        return providerClosingTitle;
    }

    public void setProviderClosingTitle(String providerClosingTitle) {
        this.providerClosingTitle = providerClosingTitle;
    }

    public String getProviderOpeningTitleArabic() {
        return providerOpeningTitleArabic;
    }

    public void setProviderOpeningTitleArabic(String providerOpeningTitleArabic) {
        this.providerOpeningTitleArabic = providerOpeningTitleArabic;
    }

    public String getProviderClosingTitleArabic() {
        return providerClosingTitleArabic;
    }

    public void setProviderClosingTitleArabic(String providerClosingTitleArabic) {
        this.providerClosingTitleArabic = providerClosingTitleArabic;
    }
}
