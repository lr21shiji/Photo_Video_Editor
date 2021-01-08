package com.philip.studio.videoeditor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.callback.OnItemFolderClickListener;
import com.philip.studio.videoeditor.model.Folder;

import java.util.ArrayList;

public class ListFolderAdapter extends RecyclerView.Adapter<ListFolderAdapter.ViewHolder> {

    ArrayList<Folder> arrayList;
    Context context;
    OnItemFolderClickListener onItemFolderClickListener;

    public ListFolderAdapter(ArrayList<Folder> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setOnItemFolderClickListener(OnItemFolderClickListener onItemFolderClickListener) {
        this.onItemFolderClickListener = onItemFolderClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_folder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNameFolder.setText(arrayList.get(position).getName());
        holder.txtSize.setText(String.valueOf(arrayList.get(position).getSize()));
        holder.imgImage.setImageResource(arrayList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameFolder, txtSize;
        ImageView imgImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameFolder = itemView.findViewById(R.id.item_text_view_name);
            txtSize = itemView.findViewById(R.id.item_text_view_size);
            imgImage = itemView.findViewById(R.id.item_image_view_image);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (onItemFolderClickListener != null){
                    onItemFolderClickListener.onItemFolderClick(pos, arrayList.get(pos).getName());
                }
            });
        }
    }
}
