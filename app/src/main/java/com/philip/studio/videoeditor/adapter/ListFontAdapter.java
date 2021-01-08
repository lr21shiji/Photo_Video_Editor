package com.philip.studio.videoeditor.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.callback.OnItemFontClickListener;

import java.util.ArrayList;

public class ListFontAdapter extends RecyclerView.Adapter<ListFontAdapter.ViewHolder> {

    ArrayList<String> arrayList;
    Context context;
    OnItemFontClickListener onItemFontClickListener;

    int[] fonts = {R.font.beyond_wonderland, R.font.bree_serif, R.font.gotham_thin, R.font.roboto_medium,
            R.font.cucho_bold};

    public ListFontAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setOnItemFontClickListener(OnItemFontClickListener onItemFontClickListener) {
        this.onItemFontClickListener = onItemFontClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_font, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtFont.setText(arrayList.get(position));
        Typeface typeface = ResourcesCompat.getFont(context, fonts[position]);
        holder.txtFont.setTypeface(typeface);

        holder.txtFont.setOnClickListener(v -> {
            if (onItemFontClickListener != null){
                onItemFontClickListener.onItemFontClick(fonts[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtFont;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFont = itemView.findViewById(R.id.item_text_view_font);
        }
    }
}
