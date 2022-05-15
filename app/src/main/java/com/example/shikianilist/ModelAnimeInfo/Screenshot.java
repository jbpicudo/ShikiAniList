package com.example.shikianilist.ModelAnimeInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Screenshot {

    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("preview")
    @Expose
    private String preview;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Screenshot withOriginal(String original) {
        this.original = original;
        return this;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Screenshot withPreview(String preview) {
        this.preview = preview;
        return this;
    }

}
