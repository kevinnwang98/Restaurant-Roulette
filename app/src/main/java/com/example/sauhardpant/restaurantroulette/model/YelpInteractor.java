package com.example.sauhardpant.restaurantroulette.model;

import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.yelp.SearchYelpQuery;
import com.example.sauhardpant.restaurantroulette.BuildConfig;
import com.example.sauhardpant.restaurantroulette.ViewModel.Utils.BusinessResultListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YelpInteractor {
    private static final String TAG = YelpInteractor.class.getSimpleName();
    private static final String BASE_URL = "https://api.yelp.com/v3/graphql";

    private ApolloClient mClient;

    public YelpInteractor() {
        if (mClient == null) {
            mClient = setUpApolloClient();
        }
    }

    private ApolloClient setUpApolloClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder builder = request.newBuilder()
                                .method(request.method(), request.body())
                                .addHeader("Authorization",
                                        "Bearer " + BuildConfig.YelpApiKey);
                        return chain.proceed(builder.build());
                    }
                }).build();
        return ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();
    }

    public void getNearbyRestaurants(Location location, final BusinessResultListener callback) {

        mClient.query(SearchYelpQuery.builder()
                .location("Waterloo, ON")
                .term("food")
                .build()).enqueue(new ApolloCall.Callback<SearchYelpQuery.Data>() {
            @Override
            public void onResponse(@NotNull com.apollographql.apollo.api.Response<SearchYelpQuery.Data> response) {
                List<SearchYelpQuery.Business> businesses = new ArrayList<>();
                if (response.data() != null) {
                    for (int i = 0; i < response.data().search().business().size(); i++) {
                        businesses.add(response.data().search().business().get(i));
                    }
                }
                callback.onResult(businesses);
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
    }
}
