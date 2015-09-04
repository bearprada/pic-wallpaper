package com.demo.cblue.retrofit;

import com.demo.cblue.model.CBCollagesResponse;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ApiService {

    @GET("/collages/feed?includes=user,like_total,echoes_total")
    Observable<CBCollagesResponse> listFeaturedCollages(@Query("limit") int limit,
                                       @Query("offset") int offset);
}
