package com.philip.studio.videoeditor.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.activity.VideoEditorActivity;
import com.philip.studio.videoeditor.adapter.ListVideosAdapter;

import java.util.ArrayList;

public class ListVideosFragment extends Fragment {

    RecyclerView rVListVideos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_videos, container, false);
        rVListVideos = view.findViewById(R.id.recycler_view_list_videos);

        ArrayList<String> arrayList = getAllVideoFromGallery();
        setUpRecyclerViewListVideo(arrayList);

        return view;
    }

    private void setUpRecyclerViewListVideo(ArrayList<String> arrayList) {
        rVListVideos.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        rVListVideos.setLayoutManager(gridLayoutManager);

        ListVideosAdapter adapter = new ListVideosAdapter(arrayList, getContext());
        rVListVideos.setAdapter(adapter);

        adapter.setOnItemVideoClickListener(videoPath -> {
            Intent intent = new Intent(getContext(), VideoEditorActivity.class);
            intent.putExtra("video", videoPath);
            startActivity(intent);
        });
    }

    private ArrayList<String> getAllVideoFromGallery() {
        Uri uri;
        Cursor cursor;
        int columnIndexData;
        ArrayList<String> listOfAllImages = new ArrayList<>();
        String pathOfImage;

        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA};

        cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            pathOfImage = cursor.getString(columnIndexData);
            listOfAllImages.add(pathOfImage);
        }
        return listOfAllImages;
    }
}
