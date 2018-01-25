package qfind.com.qfindappandroid.homeactivty;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdsData {

    @SerializedName("id")
    private Integer id;
    @SerializedName("image")
    private List<String> images;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
