package com.example.shikianilist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shikianilist.ModelAnimeList.Anime;
import com.example.shikianilist.NetworkRetrofitApi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeListActivity extends AppCompatActivity {

    private RecyclerView rvAnimeList;
    private AnimeListAdapter animeListAdapter;

    private TextView tvEndList;
    private TextView tvPages;

    private int page = 1;
    private int beginPage = 1;
    private String searchText;
    public boolean reachedEnd = false;
    public boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_list);

        rvAnimeList = findViewById(R.id.rvAnimeList);
        animeListAdapter = new AnimeListAdapter();

        List<AnimeMarker> list = new ArrayList<>();
        list.add(new LoadingItem());
        animeListAdapter.setItemList(list);

        tvEndList = findViewById(R.id.tvEndList);
        tvPages = findViewById(R.id.tvPages);

        rvAnimeList.setLayoutManager(new LinearLayoutManager(this));
        rvAnimeList.setAdapter(animeListAdapter);

        rvAnimeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!reachedEnd && !isLoading) {
                        page++;
                        showAnimeList();
                    }
                }
            }
        });

        showNewAnimeList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.nav_seach);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Введите название");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searched) {
                searchText = searched;
                page = 1;
                showNewAnimeList();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchText = s;

                Handler mHandler = new Handler();
                mHandler.removeCallbacksAndMessages(null);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        showNewAnimeList();
                    }
                }, 600);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.randomizeAnime:
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void showAnimeList() {
//        isLoading = true;

        RetrofitInstance.getInstance()
                .getShikiApi()
                .getAnimeList(page, 20, "ranked", searchText)
                .enqueue(new Callback<List<Anime>>() {
                    @Override
                    public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                        if (response.body() != null) {
                            List<AnimeMarker> list = new ArrayList<>();
                            list.addAll(response.body());
                            list.add(new LoadingItem());

                            animeListAdapter.removeLoading();
                            animeListAdapter.setItemList(list);

                            tvPages.setText("Страницы " + beginPage + "-" + page);
//                            isLoading = false;

                            if (response.body().size() < 20) {
                                reachedEnd = true;
                                animeListAdapter.removeLoading();

                                tvEndList.setVisibility(View.VISIBLE); // с этим разобраться
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Anime>> call, Throwable t) {

                    }
                });
    }

    public void showNewAnimeList() {
        rvAnimeList.smoothScrollToPosition(0);

        RetrofitInstance.getInstance()
                .getShikiApi()
                .getAnimeList(page, 20, "ranked", searchText)
                .enqueue(new Callback<List<Anime>>() {
                    @Override
                    public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                        if (response.body() != null) {
                            List<AnimeMarker> list = new ArrayList<>();
                            list.addAll(response.body());
                            list.add(new LoadingItem());

                            animeListAdapter.setAnimeListNewPage(list);

                            tvPages.setText("Страница " + beginPage);
                            reachedEnd = false;

                            if (response.body().size() < 20) {
                                reachedEnd = true;
                                animeListAdapter.removeLoading();

                                tvEndList.setVisibility(View.VISIBLE); // с этим разобраться
                            } else {
                                tvEndList.setVisibility(View.GONE); // с этим разобраться
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Anime>> call, Throwable t) {

                    }
                });
    }
}
