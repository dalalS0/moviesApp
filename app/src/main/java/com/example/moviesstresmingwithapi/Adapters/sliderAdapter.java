package com.example.moviesstresmingwithapi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.Rotate;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviesstresmingwithapi.Domain.SliderItems;
import com.example.moviesstresmingwithapi.R;

import java.util.List;

public class sliderAdapter extends RecyclerView.Adapter<sliderAdapter.SliderViewHolder> {
    private List<SliderItems> sliderItems;
    private ViewPager2 viewPager2;
    private Context context;

    public sliderAdapter(List<SliderItems> sliderItems, ViewPager2 viewPager2, Context context) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public sliderAdapter.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull sliderAdapter.SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
        if(position==sliderItems.size()-2){
            viewPager2.post(runnable);
        }


    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageSlide);
        }

        void setImage(SliderItems sliderItems) {
            //RequestOptions is an object used for manipulating and configuring image loading requests

            RequestOptions requestOptions = new RequestOptions();//object which will be used to specify how the image should be loaded and displayed.
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(60));//the requestOptions object is being modified to include two transformations

            Glide.with(context)//Glide library to load an image into an imageView
                    .load(sliderItems.getImage())
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

        private Runnable runnable = new Runnable() {
            @Override
            public void run() {

                sliderItems.addAll(sliderItems);
                notifyDataSetChanged();
            }
            };

}
