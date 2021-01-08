package com.philip.studio.videoeditor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.callback.OnItemEmojiClickListener;

import java.util.ArrayList;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder> {

    ArrayList<String> arrayList;
    Context context;

    OnItemEmojiClickListener onItemEmojiClickListener;

    public EmojiAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setOnItemEmojiClickListener(OnItemEmojiClickListener onItemEmojiClickListener) {
        this.onItemEmojiClickListener = onItemEmojiClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emoji, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtEmoji.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtEmoji;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtEmoji = itemView.findViewById(R.id.item_text_view_emoji);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (onItemEmojiClickListener != null){
                    onItemEmojiClickListener.onItemClick(arrayList.get(position));
                }
            });
        }
    }
}
