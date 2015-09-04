package com.demo.cblue;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.demo.cblue.app.BaseFragment;
import com.demo.cblue.app.DemoApplication;
import com.demo.cblue.model.WebPhoto;
import com.demo.cblue.retrofit.ApiService;
import com.demo.cblue.view.CollageAdapter;
import com.demo.cblue.view.MarginDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    public MainActivityFragment() {
    }

    @Inject ApiService apiService;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.progressBar) ProgressBar progressBar;

    private CollageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        DemoApplication.getInstance().getComponent().inject(this);

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MarginDecoration(20));

        adapter = new CollageAdapter(this);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);
        bind(fetchNearestToilet())
                .finallyDo(() -> progressBar.setVisibility(View.GONE))
                .subscribe(adapter::reset,
                        throwable -> ViewHelper.showError(getActivity(), throwable));
    }

    private Observable<List<WebPhoto>> fetchNearestToilet() {
        return apiService.listFeaturedCollages(20, 0)
                .flatMap(response -> Observable.from(response.getCollages().getPhotos()))
                .toSortedList(this::compare);
    }

    private int compare(WebPhoto t1, WebPhoto t2) {
        return t1.getLikeNum() - t2.getLikeNum();
    }
}
