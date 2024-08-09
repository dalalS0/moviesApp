package com.example.moviesstresmingwithapi.Activties;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesstresmingwithapi.Adapters.CategoryAdapter;
import com.example.moviesstresmingwithapi.Adapters.FilmListAdapter;
import com.example.moviesstresmingwithapi.Adapters.sliderAdapter;
import com.example.moviesstresmingwithapi.Domain.Genres;
import com.example.moviesstresmingwithapi.Domain.GenresItems;
import com.example.moviesstresmingwithapi.Domain.ListFilm;
import com.example.moviesstresmingwithapi.Domain.SliderItems;
import com.example.moviesstresmingwithapi.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterBestMovies,AdapterUpComing,adapterCategory;
    private RecyclerView recyclerBestMovies,recyclerUpComing,recyclerCategory;
    private RequestQueue requestQueue;
    private StringRequest stringRequest,stringRequest2,stringRequest3;
    ProgressBar loading1,loading2,loading3;
    private ViewPager2 viewPager2;
    private Handler slidHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        banners();
        sendRequestBestmovies();
        sendRequestUpcoming();
        sendRequestCategory();

    }

    private void sendRequestCategory() {
        requestQueue = Volley.newRequestQueue(this);//Volley, an Android library for handling network requests.
        stringRequest2 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/genres", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                loading2.setVisibility(View.GONE);

                ArrayList<GenresItems> genresItems = gson.fromJson(response,new TypeToken<ArrayList<GenresItems>>(){}.getType());
                //gson.fromJson(response, new TypeToken<ArrayList<GenresItems>>(){}.getType()):
                // This line parses the response (which is presumably a JSON string) into an ArrayList<GenresItems>.
                // The TypeToken is used to capture the generic type information.
                adapterCategory = new CategoryAdapter(genresItems);
                recyclerCategory.setAdapter(adapterCategory);
            }
        }, error -> {
            loading2.setVisibility(View.GONE);
            Log.i("TAG","onErrorResponse"+ error.toString());

        });
        requestQueue.add(stringRequest2);
    }

    private void sendRequestUpcoming() {
        requestQueue = Volley.newRequestQueue(this);
        loading3.setVisibility(View.VISIBLE);
        stringRequest3 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                loading3.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response,ListFilm.class);
                AdapterUpComing = new FilmListAdapter(items);
                recyclerUpComing.setAdapter(AdapterUpComing);
            }
        }, error -> {
            loading3.setVisibility(View.GONE);
            Log.i("TAG","onErrorResponse"+ error.toString());

        });
        requestQueue.add(stringRequest3);
    }

    private void sendRequestBestmovies() {
        requestQueue = Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                loading1.setVisibility(View.GONE);

                ListFilm items = gson.fromJson(response,ListFilm.class);
                adapterBestMovies = new FilmListAdapter(items);
                recyclerBestMovies.setAdapter(adapterBestMovies);
            }
        }, error -> {
            loading1.setVisibility(View.GONE);
            Log.i("TAG","onErrorResponse"+ error.toString());

        });
        requestQueue.add(stringRequest);
    }

    private void initView() {
        viewPager2 = findViewById(R.id.viewPagerSlider);
        recyclerBestMovies = findViewById(R.id.recyclerBestMovies);
        recyclerCategory = findViewById(R.id.recyclerCategory);
        recyclerUpComing = findViewById(R.id.UpcomingMovies);

        recyclerBestMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerUpComing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        loading1 = findViewById(R.id.progressBar);
        loading2 = findViewById(R.id.progressBar1);
        loading3 = findViewById(R.id.progressBar2);

    }

    private void banners() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide));
        sliderItems.add(new SliderItems(R.drawable.wide1));
        sliderItems.add(new SliderItems(R.drawable.wide3));

        viewPager2.setAdapter(new sliderAdapter(sliderItems,viewPager2,this));//provides the views for each page
        viewPager2.setClipToPadding(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        //The CompositePageTransformer is introduced in ViewPager2 to allow combining multiple Page Transformer instances
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));// This adds a margin of 40 pixels around each page in the ViewPager2
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });


        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slidHandler.removeCallbacks(slideRunnable);
            }
        });

    }
    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slidHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slidHandler.postDelayed(slideRunnable,2000);
    }

}