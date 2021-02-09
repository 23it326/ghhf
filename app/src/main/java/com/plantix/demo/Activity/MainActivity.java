package com.plantix.demo.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.plantix.demo.Beans.Example;
import com.plantix.demo.Beans.Recipe;
import com.google.gson.Gson;
import com.plantix.demo.R;
import com.plantix.demo.Service.APIService;
import com.plantix.demo.Utils.RetrofitManager;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plantix.demo.Service.APIService.BASE_URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    Button btnGetRandomList;
    APIService apiService;
    ProgressDialog progress;
    RetrofitManager retrofitManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initView();
        initListener();
        initMembers();

    }

    private void initView() {
        textView = findViewById(R.id.textView);
        btnGetRandomList = findViewById(R.id.button);
        progress = new ProgressDialog(this);
    }

    private void initListener() {
        btnGetRandomList.setOnClickListener(this);
    }

    private void initMembers() {
        progress.setMessage("Loading List... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        setupRetrofitAndOkHttp();
    }


    private void setupRetrofitAndOkHttp() {
        retrofitManager = new RetrofitManager(this);
        Retrofit retrofit = retrofitManager.getRetrofit(BASE_URL);
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        File httpCacheDirectory = new File(getCacheDir(), "offlineCache");
//
//        //10 MB
//        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
//
//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .cache(cache)
//                .addInterceptor(httpLoggingInterceptor)
//                .addNetworkInterceptor(provideCacheInterceptor())
//                .addInterceptor(provideOfflineCacheInterceptor())
//                .build();
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(new Gson()))
//                .client(httpClient)
//                .baseUrl(BASE_URL)
//                .build();

        apiService = retrofit.create(APIService.class);


    }

    public void getRandomListFromAPI() {
        progress.show();
        Observable<Example> observable = apiService.getRandomList("search");
        observable.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Example, List<Recipe>>() {
                    @Override
                    public List<Recipe> apply(Example example) throws Exception {
                        Log.d("MainActivity", "apply " + example.getRecipes());
                        return example.getRecipes();
                    }
                }).subscribe(new Observer<List<Recipe>>() {

            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(List<Recipe> recipes) {
                int count = recipes.size();
                Log.d("MainActivity", "count " + count);
                Log.d("MainActivity", "onNext " +new Gson().toJson(recipes));
                Intent listIntent = new Intent(MainActivity.this, ListActivity.class);
                listIntent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) recipes);
                startActivity(listIntent);

            }

            @Override
            public void onError(Throwable e) {
                if(progress !=null && progress.isShowing())
                    progress.dismiss();
                Toast.makeText(getApplicationContext(), "An error occurred in the Retrofit request. Perhaps no response/cache", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                if(progress !=null && progress.isShowing())
                    progress.dismiss();
                Log.d("MainActivity", "onComplete ");
            }
        }
        );

    }

//    private Interceptor provideCacheInterceptor() {
//
//        return new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                Response originalResponse = chain.proceed(request);
//                Log.d("MainActivity", "intercept " +new Gson().toJson(originalResponse));
//                String cacheControl = originalResponse.header("Cache-Control");
//
//                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
//                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")) {
//
//                    CacheControl cc = new CacheControl.Builder()
//                            .maxStale(1, TimeUnit.DAYS)
//                            .build();
//
//                    /*return originalResponse.newBuilder()
//                            .header("Cache-Control", "public, max-stale=" + 60 * 60 * 24)
//                            .build();*/
//
//
//                    request = request.newBuilder()
//                            .cacheControl(cc)
//                            .build();
//
//                    return chain.proceed(request);
//
//                } else {
//                    Log.d("MainActivity", "originalResponse " + new Gson().toJson(originalResponse));
//                    return originalResponse;
//                }
//            }
//        };
//
//    }
//
//
//    private Interceptor provideOfflineCacheInterceptor() {
//
//        return new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                try {
//                    return chain.proceed(chain.request());
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                    CacheControl cacheControl = new CacheControl.Builder()
//                            .onlyIfCached()
//                            .maxStale(1, TimeUnit.DAYS)
//                            .build();
//
//                    Request offlineRequest = chain.request().newBuilder()
//                            .cacheControl(cacheControl)
//                            .build();
//                    return chain.proceed(offlineRequest);
//                }
//            }
//        };
//    }

    @Override
    public void onClick(View v) {
        getRandomListFromAPI();
    }
}
