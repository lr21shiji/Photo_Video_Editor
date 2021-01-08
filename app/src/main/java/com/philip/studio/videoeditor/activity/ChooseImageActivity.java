package com.philip.studio.videoeditor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.adapter.ListFolderAdapter;
import com.philip.studio.videoeditor.adapter.ListImageAdapter;
import com.philip.studio.videoeditor.model.Folder;

import java.io.File;
import java.util.ArrayList;

public class ChooseImageActivity extends AppCompatActivity {

    RecyclerView rVListImages, rVListFolder;
    ImageView imgSettings;
    TextView txtNameFolder;
    LinearLayout linearLayout;

    BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    ArrayList<String> listImage;
    ArrayList<Folder> listFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_image);

        initView();

        listImage = getAllImageFromGallery();
        setUpRecyclerViewListImage(listImage);

        txtNameFolder.setOnClickListener(v -> showBottomSheetListFolder());
    }

    private void showBottomSheetListFolder() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        setUpRecyclerViewListFolder();
    }

    private void setUpRecyclerViewListFolder() {
        rVListFolder.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rVListFolder.setLayoutManager(layoutManager);

        listFolder = getListFolder();
        ListFolderAdapter adapter = new ListFolderAdapter(listFolder, this);
        rVListFolder.setAdapter(adapter);

        adapter.setOnItemFolderClickListener((pos, path) -> {
            String link = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + path + "/";
            File file = new File(link);
            File[] files = file.listFiles();
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                arrayList.add(files[i].getAbsolutePath());
            }
            setUpRecyclerViewListImage(arrayList);
            txtNameFolder.setText(listFolder.get(pos).getName());
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
    }

    private void setUpRecyclerViewListImage(ArrayList<String> arrayList) {
        rVListImages.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rVListImages.setLayoutManager(gridLayoutManager);

        ListImageAdapter adapter = new ListImageAdapter(arrayList, this);
        rVListImages.setAdapter(adapter);
    }

    private void initView() {
        imgSettings = findViewById(R.id.image_view_setting);
        rVListImages = findViewById(R.id.recycler_view_list_images);
        rVListFolder = findViewById(R.id.recycler_view_list_folder);
        txtNameFolder = findViewById(R.id.text_view_name_folder);
        linearLayout = findViewById(R.id.linear_layout_folder);

        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);

        listImage = new ArrayList<>();
        listFolder = new ArrayList<>();
    }

    private ArrayList<Folder> getListFolder() {
        ArrayList<Folder> arrayList = new ArrayList<>();

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File[] files = file.listFiles();

        for (int i = 0; i < files.length; i++) {
            arrayList.add(new Folder(files[i].getName(), files[i].list().length, R.drawable.dieu_anh_biet));
        }
        return arrayList;
    }

    private ArrayList<String> getAllImageFromGallery() {
        Uri uri;
        Cursor cursor;
        int columnIndexData;
        ArrayList<String> listOfAllImages = new ArrayList<>();
        String pathOfImage;

        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA};

        cursor = this.getContentResolver().query(uri, projection, null, null, null);
        columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            pathOfImage = cursor.getString(columnIndexData);
            listOfAllImages.add(pathOfImage);
        }
        return listOfAllImages;
    }

    public void onBack(View view) {
        finish();
    }
}