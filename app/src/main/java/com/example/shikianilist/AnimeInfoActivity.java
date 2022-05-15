package com.example.shikianilist;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shikianilist.ModelAnimeInfo.AnimeInfo;
import com.example.shikianilist.NetworkRetrofitApi.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeInfoActivity extends AppCompatActivity {

    private TextView tvNameRU;
    private TextView tvNameJP;
    private TextView tvDescription;
    private TextView tvKind;
    private TextView tvEpisodesAired;
    private TextView tvStatus;
    private TextView tvDate;
    private TextView tvGenres;
    private TextView tvScore;
    private ImageView ivAnimeInfoCover;
    private ProgressBar pbAnimeInfo;

    int animeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_info);

        tvNameRU = findViewById(R.id.tvNameRU);
        tvNameJP = findViewById(R.id.tvNameJP);
        tvDescription = findViewById(R.id.tvDescription);
        tvKind = findViewById(R.id.tvKind);
        tvEpisodesAired = findViewById(R.id.tvEpisodesAired);
        tvStatus = findViewById(R.id.tvStatus);
        tvDate = findViewById(R.id.tvDate);
        tvGenres = findViewById(R.id.tvGenres);
        tvScore = findViewById(R.id.tvScore);
        ivAnimeInfoCover = findViewById(R.id.ivAnimeInfoCover);
        pbAnimeInfo = findViewById(R.id.pbAnimeInfo);

        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            animeId = arguments.getInt("animeId");
        }

        showAnimeInfo();
    }

    public void showAnimeInfo() {
        RetrofitInstance.getInstance()
                .getShikiApi()
                .showAnimeInfo(animeId)
                .enqueue(new Callback<AnimeInfo>() {
                    @Override
                    public void onResponse(Call<AnimeInfo> call, Response<AnimeInfo> response) {
                        if (!response.isSuccessful()) {
                            tvNameRU.setText("Код " + response.code());
                            pbAnimeInfo.setVisibility(View.GONE);
                            return;
                        }

                        if (response.body() != null) {
                            AnimeInfo animeInfo = response.body();

                            pbAnimeInfo.setVisibility(View.GONE);
                            findViewById(R.id.preDescriptionVremenno).setVisibility(View.VISIBLE);

                            tvNameRU.setText(animeInfo.getRussian());
                            tvNameJP.setText(animeInfo.getName());
                            tvDescription.setText(Html.fromHtml(animeInfo.getDescriptionHtml()));

                            if (animeInfo.getKind() == null) {
                                tvKind.setVisibility(View.GONE);
                            } else if (animeInfo.getKind().equals("tv")) {
                                tvKind.setText(R.string.tv);
                            } else if (animeInfo.getKind().equals("movie")) {
                                tvKind.setText(R.string.movie);
                            } else if (animeInfo.getKind().equals("ova")) {
                                tvKind.setText(R.string.ova);
                            } else if (animeInfo.getKind().equals("ona")) {
                                tvKind.setText(R.string.ona);
                            } else if (animeInfo.getKind().equals("special")) {
                                tvKind.setText(R.string.special);
                            } else if (animeInfo.getKind().equals("music")) {
                                tvKind.setText(R.string.music);
                            }

                            if (animeInfo.getStatus().equals("released")) {
                                tvStatus.setText(R.string.released);
                            } else if (animeInfo.getStatus().equals("ongoing")) {
                                tvStatus.setText(R.string.ongoing);
                            } else if (animeInfo.getStatus().equals("anons")) {
                                tvStatus.setText(R.string.anons);
                            }

                            if (animeInfo.getAiredOn() == null && animeInfo.getReleasedOn() == null) {
                                tvDate.setVisibility(View.GONE);
                            } else if (animeInfo.getStatus().equals("released") && animeInfo.getReleasedOn() == null) {
                                tvDate.setText(animeInfo.getAiredOn());
                            } else if (animeInfo.getAiredOn() != null && animeInfo.getReleasedOn() == null) {
                                tvDate.setText("c " + animeInfo.getAiredOn());
                            } else {
                                tvDate.setText("c " + animeInfo.getAiredOn() + " по " + animeInfo.getReleasedOn());
                            }

                            tvEpisodesAired.setText(R.string.episodes_info);
                            if (animeInfo.getKind() == null) {
                                tvEpisodesAired.setVisibility(View.GONE);
                            } else if (animeInfo.getKind().equals("movie") && animeInfo.getEpisodes() == 1) {
                                tvEpisodesAired.setVisibility(View.GONE);
                            } else if (animeInfo.getStatus().equals("anons") && animeInfo.getEpisodes() == 0) {
                                tvEpisodesAired.setVisibility(View.GONE);
                            } else if (animeInfo.getStatus().equals("ongoing") && animeInfo.getEpisodes() == 0) {
                                tvEpisodesAired.append(animeInfo.getEpisodesAired().toString() + " / ?");
                            } else if (animeInfo.getStatus().equals("ongoing")) {
                                tvEpisodesAired.append(animeInfo.getEpisodesAired().toString() + " / " + animeInfo.getEpisodes().toString());
                            } else {
                                tvEpisodesAired.append(animeInfo.getEpisodes().toString());
                            }

                            tvGenres.setText(R.string.genre_info);
                            for (int i = 0; animeInfo.getGenres().size() > i; i++) {
                                tvGenres.append(animeInfo.getGenres().get(i).getRussian());
                                if (animeInfo.getGenres().size() - 1 > i) { // -1 сайз для того что бы ласт кома не ставилась
                                    tvGenres.append(", ");
                                }
                            }

                            tvScore.setText(R.string.score_info);
                            if (animeInfo.getScore().equals("0.0")) {
                                tvScore.setVisibility(View.GONE);
                            } else {
                                tvScore.append(animeInfo.getScore());
                            }

                            Glide.with(AnimeInfoActivity.this)
                                    .load(RetrofitInstance.getBaseUrl() + animeInfo.getImage().getOriginal())
                                    .into(ivAnimeInfoCover);
                        }
                    }

                    @Override
                    public void onFailure(Call<AnimeInfo> call, Throwable t) {
                        tvNameRU.setText("Код " + t.getMessage());
                        pbAnimeInfo.setVisibility(View.GONE);
                    }
                });
    }

}