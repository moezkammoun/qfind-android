package qfind.com.qfindappandroid.historyPage;

/**
 * Created by MoongedePC on 19-Jan-18.
 */

public class HistoryPageDataModel {

    int id;
    String pageName;
    String url;
    String description;
    String pageNameArabic;
    String descriptionArabic;
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
    private int pageId;
    private String providerOpeningTimeArabic;

    public HistoryPageDataModel() {
    }
    

    public HistoryPageDataModel(String pageName, String url, String description,
                                String providerPhone, String providerWebsite,
                                String providerAddress, String providerOpeningTime, String providerMail,
                                String providerFacebook, String providerLinkedIn, String providerInstagram,
                                String providerTwitter, String providerSnapchat, String providerGooglePlus,
                                String providerLatlong,int pageId,String providerOpeningTimeArabic) {
        this.pageName = pageName;
        this.url = url;
        this.description = description;
        this.providerPhone=providerPhone;
        this.providerWebsite=providerWebsite;
        this.providerAddress=providerAddress;
        this.providerOpeningTime=providerOpeningTime;
        this.providerMail=providerMail;
        this.providerFacebook=providerFacebook;
        this.providerLinkedIn=providerLinkedIn;
        this.providerInstagram=providerInstagram;
        this.providerTwitter=providerTwitter;
        this.providerSnapchat=providerSnapchat;
        this.providerGooglePlus=providerGooglePlus;
        this.providerLatlong=providerLatlong;
        this.pageId=pageId;
        this.providerOpeningTimeArabic=providerOpeningTimeArabic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPageNameArabic() {
        return pageNameArabic;
    }

    public void setPageNameArabic(String pageNameArabic) {
        this.pageNameArabic = pageNameArabic;
    }

    public String getDescriptionArabic() {
        return descriptionArabic;
    }

    public void setDescriptionArabic(String descriptionArabic) {
        this.descriptionArabic = descriptionArabic;
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

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getProviderOpeningTimeArabic() {
        return providerOpeningTimeArabic;
    }

    public void setProviderOpeningTimeArabic(String providerOpeningTimeArabic) {
        this.providerOpeningTimeArabic = providerOpeningTimeArabic;
    }
}
