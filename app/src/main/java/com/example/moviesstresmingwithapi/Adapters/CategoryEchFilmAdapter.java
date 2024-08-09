package com.example.moviesstresmingwithapi.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesstresmingwithapi.Domain.GenresItems;
import com.example.moviesstresmingwithapi.R;

import java.util.ArrayList;
import java.util.List;


public class CategoryEchFilmAdapter extends RecyclerView.Adapter<CategoryEchFilmAdapter.ViewHolder> {
    List<String> items;
    Context context;

    public CategoryEchFilmAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryEchFilmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryEchFilmAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
    holder.titleTxt.setText(items.get(position));


        holder.itemView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.textTitle);
        }
    }
}
