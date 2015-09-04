package com.demo.cblue;

import android.app.WallpaperManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.demo.cblue.app.BaseActivity;
import com.demo.cblue.app.DemoApplication;
import com.demo.cblue.model.WebPhoto;
import com.demo.cblue.retrofit.ApiService;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import bolts.Continuation;
import bolts.Task;
import rx.Observable;

public class SetWallPaperActivity extends BaseActivity {

    @Inject ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wall_paper);
        DemoApplication.getInstance().getComponent().inject(this);
        bind(apiService.listFeaturedCollages(20, 0)).subscribe(data -> {
            List<WebPhoto> photos = data.getCollages().getPhotos();
            final WebPhoto photo = photos.get(new Random().nextInt(photos.size()));
            Task.callInBackground(() -> {
                InputStream is = new URL(photo.getOriginalImageUrl()).openConnection().getInputStream();
                WallpaperManager.getInstance(SetWallPaperActivity.this).setStream(is);
                return null;
            }).continueWith(task -> {
                if (task.isFaulted() || task.isCancelled()) {
                    ViewHelper.showError(SetWallPaperActivity.this, task.getError());
                    return null;
                }
                Toast.makeText(SetWallPaperActivity.this, R.string.set_wallpaper_successful, Toast.LENGTH_SHORT).show();
                return null;
            }, Task.UI_THREAD_EXECUTOR).continueWith(task -> {
                finish();
                return null;
            });
        }, throwable -> ViewHelper.showError(this, throwable));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_wall_paper, menu);
        return true;
    }
}
