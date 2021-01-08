package com.philip.studio.videoeditor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.callback.OnItemImageClickListener;
import com.philip.studio.videoeditor.model.ImageData;

import java.util.ArrayList;

public class ListImagesAdapter extends RecyclerView.Adapter<ListImagesAdapter.ViewHolder> {

    ArrayList<ImageData> arrayList;
    Context context;
    OnItemImageClickListener onItemImageClickListener;

    public ListImagesAdapter(ArrayList<ImageData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setOnItemImageClickListener(OnItemImageClickListener onItemImageClickListener) {
        this.onItemImageClickListener = onItemImageClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_images, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getImageData()).into(holder.imgImage);

        holder.imgImage.setOnClickListener(v -> {
            if (onItemImageClickListener != null) {
                onItemImageClickListener.onItemClick(position, holder.imgCheck);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgImage, imgCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgImage = itemView.findViewById(R.id.item_image);
            imgCheck = itemView.findViewById(R.id.item_check);
        }
    }
}
