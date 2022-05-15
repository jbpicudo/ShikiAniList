package com.example.shikianilist.ModelAnimeInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class UserRate {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("text")
    @Expose
    private Object text;
    @SerializedName("episodes")
    @Expose
    private Integer episodes;
    @SerializedName("chapters")
    @Expose
    private Integer chapters;
    @SerializedName("volumes")
    @Expose
    private Integer volumes;
    @SerializedName("text_html")
    @Expose
    private String textHtml;
    @SerializedName("rewatches")
    @Expose
    private Integer rewatches;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserRate withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public UserRate withScore(Integer score) {
        this.score = score;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserRate withStatus(String status) {
        this.status = status;
        return this;
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }

    public UserRate withText(Object text) {
        this.text = text;
        return this;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public UserRate withEpisodes(Integer episodes) {
        this.episodes = episodes;
        return this;
    }

    public Integer getChapters() {
        return chapters;
    }

    public void setChapters(Integer chapters) {
        this.chapters = chapters;
    }

    public UserRate withChapters(Integer chapters) {
        this.chapters = chapters;
        return this;
    }

    public Integer getVolumes() {
        return volumes;
    }

    public void setVolumes(Integer volumes) {
        this.volumes = volumes;
    }

    public UserRate withVolumes(Integer volumes) {
        this.volumes = volumes;
        return this;
    }

    public String getTextHtml() {
        return textHtml;
    }

    public void setTextHtml(String textHtml) {
        this.textHtml = textHtml;
    }

    public UserRate withTextHtml(String textHtml) {
        this.textHtml = textHtml;
        return this;
    }

    public Integer getRewatches() {
        return rewatches;
    }

    public void setRewatches(Integer rewatches) {
        this.rewatches = rewatches;
    }

    public UserRate withRewatches(Integer rewatches) {
        this.rewatches = rewatches;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public UserRate withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserRate withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

}
