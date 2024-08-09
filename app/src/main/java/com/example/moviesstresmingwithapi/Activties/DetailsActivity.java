package com.example.moviesstresmingwithapi.Activties;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesstresmingwithapi.Adapters.ActorsListAdapter;
import com.example.moviesstresmingwithapi.Adapters.CategoryEchFilmAdapter;
import com.example.moviesstresmingwithapi.Domain.Filmitem;
import com.example.moviesstresmingwithapi.R;
import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {
 private RequestQueue requestQueue;
 private StringRequest stringRequest;
 private ProgressBar progressBar;
 private TextView titleTxt,movieRateTxt,movieTimeTxt,movieSummary,moveActor;
 private  int idFilm;
 private ImageView pic2,backImg;
 private RecyclerView.Adapter adapterActorList,adapterCategory;
 private RecyclerView recyclerViewActors,recyclerViewCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        idFilm = getIntent().getIntExtra("id",0);
        initView();
        sendRequest();
    }

    private void sendRequest(){
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET,"https://moviesapi.ir/api/v1/movies/"+idFilm,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Filmitem filmitem = gson.fromJson(response,Filmitem.class);

                RequestOptions requestOptions = new RequestOptions();//object which will be used to specify how the image should be loaded and displayed.
                requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(60));//the requestOptions object is being modified to include two transformations

                Glide.with(DetailsActivity.this)
                        .load(filmitem.getPoster())
                        .apply(requestOptions)
                        .into(pic2);

                titleTxt.setText(filmitem.getTitle());
                movieRateTxt.setText(filmitem.getImdbRating());
                movieTimeTxt.setText(filmitem.getRuntime());
                movieSummary.setText(filmitem.getPlot());
                moveActor.setText(filmitem.getActors());

                if(filmitem.getImages()!=null){
                    adapterActorList = new ActorsListAdapter(filmitem.getImages());
                    recyclerViewActors.setAdapter(adapterActorList);

                }
                if(filmitem.getGenres() !=null){
                    adapterCategory = new CategoryEchFilmAdapter(filmitem.getGenres());
                    recyclerViewCategory.setAdapter(adapterCategory);
                }

            }

        }, (Response.ErrorListener) error -> {

        });
        requestQueue.add(stringRequest);
    }

    private void initView() {
        titleTxt = findViewById(R.id.title_txt);
        pic2 = findViewById(R.id.details_img);
        movieRateTxt = findViewById(R.id.rate_txt);
        movieTimeTxt = findViewById(R.id.year_txt);
        movieSummary = findViewById(R.id.summry_txt);
        moveActor = findViewById(R.id.actors);
        backImg = findViewById(R.id.go_back);
        recyclerViewCategory = findViewById(R.id.view_category);
        recyclerViewActors = findViewById(R.id.rec_actor);


        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        backImg.setOnClickListener(view -> finish());
    }


}