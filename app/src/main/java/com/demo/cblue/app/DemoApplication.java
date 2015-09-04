package com.demo.cblue.app;

import android.app.Application;
import com.demo.cblue.dagger.BeanComponent;
import com.demo.cblue.dagger.DaggerBeanComponent;
import com.demo.cblue.dagger.NetworkModule;

/**
 * Created by Kros on 8/25/15.
 */
public class DemoApplication extends Application {
    private static DemoApplication INSTANCE;

    private BeanComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        component = DaggerBeanComponent.builder()
                .networkModule(new NetworkModule(this, "http://pic-collage.com/api/"))
                .build();
    }

    public static DemoApplication getInstance() {
        return INSTANCE;
    }

    public BeanComponent getComponent() {
        return component;
    }
}
