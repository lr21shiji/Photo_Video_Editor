package com.philip.studio.videoeditor.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.callback.OnItemFilterListener;

import java.util.List;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private final OnItemFilterListener filterListener;
    private final Context context;
    private final List<Pair<String, PhotoFilter>> pairList;
    String image;

    public FilterAdapter(OnItemFilterListener filterListener, Context context, List<Pair<String, PhotoFilter>> pairList, String image) {
        this.filterListener = filterListener;
        this.context = context;
        this.pairList = pairList;
        this.image = image;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<String, PhotoFilter> filterPair = pairList.get(position);
        Uri uri = Uri.parse(image);

        holder.photoEditorView.getSource().setImageURI(uri);

        Typeface typefaceText = ResourcesCompat.getFont(context, R.font.beyond_wonderland);

        PhotoEditor photoEditor = new PhotoEditor.Builder(context, holder.photoEditorView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(typefaceText)
                .build();

        photoEditor.setFilterEffect(filterPair.second);
        holder.txtNameFilter.setText(filterPair.second.name().replace("_", " "));
    }

    @Override
    public int getItemCount() {
        return pairList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameFilter;
        PhotoEditorView photoEditorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameFilter = itemView.findViewById(R.id.text_view_name_filter);
            photoEditorView = itemView.findViewById(R.id.item_photo_editor_view);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                filterListener.onItemFilterSelected(pairList.get(pos).second);
            });
        }
    }
}
