package com.demo.cblue.view;

import android.app.WallpaperManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.demo.cblue.R;
import com.demo.cblue.ViewHelper;
import com.demo.cblue.model.WebPhoto;
import com.demo.cblue.view.CollageAdapter.ToiletViewHolder;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bolts.Task;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wish8 on 8/26/15.
 */
public class CollageAdapter extends RecyclerView.Adapter<ToiletViewHolder> {

    public static class ToiletViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.collage_image_view) ImageView imageView;

        public ToiletViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private List<WebPhoto> photos = new ArrayList<>();
    private final Fragment fragment;

    public CollageAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public ToiletViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_toilet, viewGroup, false);
        return new ToiletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToiletViewHolder vh, int i) {
        final WebPhoto photo = photos.get(i);
        DrawableTypeRequest<String> thumbReq = Glide.with(fragment).load(photo.getThumbnailImageUrl());
        Glide.with(fragment).load(photo.getMediumImageUrl()).thumbnail(thumbReq).into(vh.imageView);
        vh.itemView.setOnClickListener(v -> {
            Task.callInBackground(() -> {
                InputStream is = new URL(photo.getOriginalImageUrl()).openConnection().getInputStream();
                WallpaperManager.getInstance(fragment.getActivity()).setStream(is);
                return null;
            }).continueWith(task -> {
                if (task.isFaulted() || task.isCancelled()) {
                    ViewHelper.showError(fragment.getActivity(), task.getError());
                    return null;
                }
                Toast.makeText(fragment.getActivity(), R.string.set_wallpaper_successful, Toast.LENGTH_SHORT).show();
                return null;
            }, Task.UI_THREAD_EXECUTOR);
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void reset(List<WebPhoto> collages) {
        this.photos.clear();
        this.photos.addAll(collages);
        notifyDataSetChanged();
    }

}
