package com.example.shikianilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shikianilist.ModelAnimeList.Anime;
import com.example.shikianilist.NetworkRetrofitApi.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tv_name_ru;
    private TextView tv_name_jp;
    private TextView tv_score;
    private TextView tv_episodes;
    private TextView tv_kind;
    private ImageView animeImage;
    private Button generateAnime;
    private ProgressBar pbRandomAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_name_ru = findViewById(R.id.tv_name_ru);
        tv_name_jp = findViewById(R.id.tv_name_jp);
        tv_score = findViewById(R.id.tv_score);
        tv_episodes = findViewById(R.id.tv_episodes);
        tv_kind = findViewById(R.id.tv_kind);
        animeImage = findViewById(R.id.animeImage);
        generateAnime = findViewById(R.id.generateAnime);
        pbRandomAnime = findViewById(R.id.pbRandomAnime);

        generateAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomAnime();
            }
        });

        findViewById(R.id.toAnimeListActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        generateRandomAnime();
    }

    private void generateRandomAnime() {
        generateAnime.setEnabled(false);
        pbRandomAnime.setVisibility(View.VISIBLE);

        RetrofitInstance.getInstance()
                .getShikiApi()
                .getAnimeList(1, 1, "random", null)
                .enqueue(new Callback<List<Anime>>() {
                    @Override
                    public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                        if (!response.isSuccessful()) {
                            tv_name_ru.setText("Код " + response.code());
                            pbRandomAnime.setVisibility(View.GONE);
                            generateAnime.setEnabled(true);
                            return;
                        }

                        if (response.body() != null && response.body().size() > 0) {
                            Anime anime = response.body().get(0);

                            tv_name_ru.setText(anime.getRussian());
                            tv_name_jp.setText(anime.getName());
                            tv_score.setText(anime.getScore());
                            tv_kind.setText(anime.getKind());
                            tv_episodes.setText(anime.getEpisodes().toString());

                            Glide.with(MainActivity.this)
                                    .load(RetrofitInstance.getBaseUrl() + anime.getImage().getOriginal())
                                    .into(animeImage);

                            pbRandomAnime.setVisibility(View.GONE);
                            generateAnime.setEnabled(true);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Anime>> call, Throwable t) {
                        tv_name_ru.setText(t.getMessage());
                        pbRandomAnime.setVisibility(View.GONE);
                        generateAnime.setEnabled(true);
                    }
                });

    }
}