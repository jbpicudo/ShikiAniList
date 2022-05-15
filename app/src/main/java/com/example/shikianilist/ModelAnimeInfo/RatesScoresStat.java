package com.example.shikianilist.ModelAnimeInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class RatesScoresStat {

    @SerializedName("name")
    @Expose
    private Integer name;
    @SerializedName("value")
    @Expose
    private Integer value;

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public RatesScoresStat withName(Integer name) {
        this.name = name;
        return this;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public RatesScoresStat withValue(Integer value) {
        this.value = value;
        return this;
    }

}
