package qfind.com.qfindappandroid.historyPage;

/**
 * Created by MoongedePC on 28-Jan-18.
 */

public class HistoryItem {
    private int id;
    private String day;
    private String titke;
    private String image;
    private String description;
    private int pageId;
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

    public HistoryItem() {
    }

    public HistoryItem(int id) {
        this.id = id;
    }

    public HistoryItem(String day, String titke, String image, String description, int pageId,
                       String providerThumbnail, String providerPhone, String providerWebsite,
                       String providerAddress, String providerOpeningTime, String providerMail,
                       String providerFacebook, String providerLinkedIn, String providerInstagram,
                       String providerTwitter, String providerSnapchat, String providerGooglePlus,
                       String providerLatlong) {
        this.day = day;
        this.titke = titke;
        this.image = image;
        this.description = description;
        this.pageId = pageId;
        this.providerThumbnail=providerThumbnail;
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
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitke() {
        return titke;
    }

    public void setTitke(String titke) {
        this.titke = titke;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
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
}
