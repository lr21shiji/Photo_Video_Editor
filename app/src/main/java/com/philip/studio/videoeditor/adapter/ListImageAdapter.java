package com.philip.studio.videoeditor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.activity.ImageEditorActivity;
import com.philip.studio.videoeditor.callback.OnItemImageClickListener;

import java.util.ArrayList;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.ViewHolder> {

    ArrayList<String> arrayList;
    Context context;

    public ListImageAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position)).into(holder.imgImage);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgImage = itemView.findViewById(R.id.item_image);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                Intent intent = new Intent(context, ImageEditorActivity.class);
                intent.putExtra("image", arrayList.get(pos));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        }
    }
}
