package com.demo.cblue.dagger;

import com.demo.cblue.MainActivity;
import com.demo.cblue.MainActivityFragment;
import com.demo.cblue.SetWallPaperActivity;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by Kros on 8/25/15.
 */
@Singleton
@Component(modules = {NetworkModule.class})
public interface BeanComponent {
    void inject(MainActivity activity);
    void inject(SetWallPaperActivity fragment);
    void inject(MainActivityFragment fragment);
}
