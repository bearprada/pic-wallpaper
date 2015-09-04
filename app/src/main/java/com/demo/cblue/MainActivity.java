package com.demo.cblue;

import android.os.Bundle;
import com.demo.cblue.app.BaseActivity;
import com.demo.cblue.app.DemoApplication;
import com.demo.cblue.retrofit.ApiService;
import rx.Observable;
import rx.functions.Action1;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Inject ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DemoApplication.getInstance().getComponent().inject(this);
    }
}
