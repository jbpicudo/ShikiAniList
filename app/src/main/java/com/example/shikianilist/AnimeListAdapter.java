package com.example.shikianilist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shikianilist.ModelAnimeList.Anime;
import com.example.shikianilist.NetworkRetrofitApi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.ViewHolder> {

    private static final int VIEW_TYPE_ANIME = 1;
    private static final int VIEW_TYPE_LOADING = 2;

    private List<AnimeMarker> itemList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == VIEW_TYPE_ANIME) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_item, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeListAdapter.ViewHolder holder, int position) {
        AnimeMarker animeMarker = itemList.get(position);

        if (animeMarker instanceof Anime) {
            Anime anime = (Anime) animeMarker;

            holder.tvKind.setVisibility(View.VISIBLE);
            holder.tvEpisodesAired.setVisibility(View.VISIBLE);
            holder.tvScore.setVisibility(View.VISIBLE);

            Glide.with(holder.itemView.getContext())
                    .load(RetrofitInstance.getBaseUrl() + anime.getImage().getPreview())
                    .into(holder.ivAnimeCover);

            if (anime.getRussian().isEmpty()) {
                holder.tvTitle.setText(anime.getName());
            } else {
                holder.tvTitle.setText(anime.getRussian());
            }

            if (anime.getKind() == null) {
                holder.tvKind.setVisibility(View.GONE);
            } else if (anime.getKind().equals("tv")) {
                holder.tvKind.setText(R.string.tv);
            } else if (anime.getKind().equals("movie")) {
                holder.tvKind.setText(R.string.movie);
            } else if (anime.getKind().equals("ova")) {
                holder.tvKind.setText(R.string.ova);
            } else if (anime.getKind().equals("ona")) {
                holder.tvKind.setText(R.string.ona);
            } else if (anime.getKind().equals("special")) {
                holder.tvKind.setText(R.string.special);
            } else if (anime.getKind().equals("music")) {
                holder.tvKind.setText(R.string.music);
            }

            if (anime.getStatus().equals("released")) {
                holder.tvStatus.setText(R.string.released);
            } else if (anime.getStatus().equals("ongoing")) {
                holder.tvStatus.setText(R.string.ongoing);
            } else if (anime.getStatus().equals("anons")) {
                holder.tvStatus.setText(R.string.anons);
            }

            if (anime.getKind() == null) {
                holder.tvEpisodesAired.setVisibility(View.GONE);
            } else if (anime.getKind().equals("movie") && anime.getEpisodes() == 1) {
                holder.tvEpisodesAired.setVisibility(View.GONE);
            } else if (anime.getStatus().equals("anons") && anime.getEpisodes() == 0) {
                holder.tvEpisodesAired.setVisibility(View.GONE);
            } else if (anime.getStatus().equals("ongoing") && anime.getEpisodes() == 0) {
                holder.tvEpisodesAired.setText("Эпизоды: " + anime.getEpisodesAired().toString() + " / ?");
            } else if (anime.getStatus().equals("ongoing")) {
                holder.tvEpisodesAired.setText("Эпизоды: " + anime.getEpisodesAired().toString() + " / " + anime.getEpisodes().toString());
            } else {
                holder.tvEpisodesAired.setText("Эпизоды: " + anime.getEpisodes().toString());
            }

            if (anime.getScore().equals("0.0")) {
                holder.tvScore.setVisibility(View.GONE);
            } else {
                holder.tvScore.setText("Оценка: " + anime.getScore());
            }

            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), AnimeInfoActivity.class);
                    intent.putExtra("animeId", anime.getId());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position) instanceof Anime) {
            return VIEW_TYPE_ANIME;
        } else if (itemList.get(position) instanceof LoadingItem) {
            return VIEW_TYPE_LOADING;
        } else {
            throw new IllegalStateException("Unknown View Type");
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(List<AnimeMarker> itemList) {
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void setAnimeListNewPage(List<AnimeMarker> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void removeLoading() {
        this.itemList.remove(itemList.size() - 1);
        notifyItemRemoved(itemList.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAnimeCover;
        TextView tvTitle;
        TextView tvKind;
        TextView tvEpisodesAired;
        TextView tvStatus;
        TextView tvScore;
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            ivAnimeCover = itemView.findViewById(R.id.ivAnimeCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvKind = itemView.findViewById(R.id.tvKind);
            tvEpisodesAired = itemView.findViewById(R.id.tvEpisodesAired);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvScore = itemView.findViewById(R.id.tvScore);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }

    public static class LoadingHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;

        public LoadingHolder(View itemView) {
            super(itemView);

            pbLoading = itemView.findViewById(R.id.pbLoading);
        }
    }
}
