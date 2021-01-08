package com.philip.studio.videoeditor.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.adapter.ListImagesAdapter;
import com.philip.studio.videoeditor.callback.OnSendListImageListener;
import com.philip.studio.videoeditor.model.ImageData;

import java.util.ArrayList;

public class ListImagesFragment extends Fragment {

    RecyclerView rVListImages;

    OnSendListImageListener sendListImageListener;
    ArrayList<String> listImages = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_images, container, false);
        rVListImages = view.findViewById(R.id.recycler_view_list_images);

        ArrayList<ImageData> arrayList = getAllImageFromGallery();
        setUpRecyclerViewListImages(arrayList);

        return view;
    }

    private void setUpRecyclerViewListImages(ArrayList<ImageData> arrayList) {
        rVListImages.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        rVListImages.setLayoutManager(gridLayoutManager);

        ListImagesAdapter adapter = new ListImagesAdapter(arrayList, getContext());
        rVListImages.setAdapter(adapter);

        adapter.setOnItemImageClickListener((position, imageView) -> {
            boolean isDisplay = arrayList.get(position).isClick();
            if (!isDisplay){
                arrayList.get(position).setClick(true);
                imageView.setVisibility(View.VISIBLE);
                String imagePath = arrayList.get(position).getImageData();
                listImages.add(imagePath);
            }
            else{
                arrayList.get(position).setClick(false);
                imageView.setVisibility(View.GONE);
                listImages.remove(arrayList.get(position).getImageData());
            }

            if (sendListImageListener != null){
                sendListImageListener.onSend(listImages);
            }
        });
    }

    private ArrayList<ImageData> getAllImageFromGallery() {
        Uri uri;
        Cursor cursor;
        int columnIndexData;
        ArrayList<ImageData> listOfAllImages = new ArrayList<>();
        String pathOfImage;

        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA};

        cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            pathOfImage = cursor.getString(columnIndexData);
            listOfAllImages.add(new ImageData(pathOfImage, false));
        }
        return listOfAllImages;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        sendListImageListener = (OnSendListImageListener) context;
    }
}
