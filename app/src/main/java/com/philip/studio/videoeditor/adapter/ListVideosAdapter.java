package com.philip.studio.videoeditor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.callback.OnItemVideoClickListener;

import java.util.ArrayList;

public class ListVideosAdapter extends RecyclerView.Adapter<ListVideosAdapter.ViewHolder> {

    ArrayList<String> arrayList;
    Context context;

    OnItemVideoClickListener onItemVideoClickListener;

    public ListVideosAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setOnItemVideoClickListener(OnItemVideoClickListener onItemVideoClickListener) {
        this.onItemVideoClickListener = onItemVideoClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (arrayList.get(position) != null){
            holder.imageView.setImageResource(R.drawable.image1);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_video);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (onItemVideoClickListener != null){
                    onItemVideoClickListener.onItemClick(arrayList.get(pos));
                }
            });
        }
    }
}
