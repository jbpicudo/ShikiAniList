package com.example.shikianilist.ModelAnimeInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Image {

    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("preview")
    @Expose
    private String preview;
    @SerializedName("x96")
    @Expose
    private String x96;
    @SerializedName("x48")
    @Expose
    private String x48;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Image withOriginal(String original) {
        this.original = original;
        return this;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Image withPreview(String preview) {
        this.preview = preview;
        return this;
    }

    public String getX96() {
        return x96;
    }

    public void setX96(String x96) {
        this.x96 = x96;
    }

    public Image withX96(String x96) {
        this.x96 = x96;
        return this;
    }

    public String getX48() {
        return x48;
    }

    public void setX48(String x48) {
        this.x48 = x48;
    }

    public Image withX48(String x48) {
        this.x48 = x48;
        return this;
    }

}
