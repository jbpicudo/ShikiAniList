package com.example.shikianilist.NetworkRetrofitApi;

import com.example.shikianilist.ModelAnimeInfo.AnimeInfo;
import com.example.shikianilist.ModelAnimeList.Anime;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShikiApi {

    @Headers({
            "Content-Type: application/json",
            "User-Agent: ShikiAniList",
            "Cache-Control: max-age=640000"
    })
    @GET("/api/animes")
    Call<List<Anime>> getAnimeList(@Query("page")int page, @Query("limit") int limit, @Query("order")String order, @Query("search")String search);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: ShikiAniList",
            "Cache-Control: max-age=640000"
    })
    @GET("/api/animes/{id}")
    Call<AnimeInfo> showAnimeInfo(@Path("id") int id);

}
