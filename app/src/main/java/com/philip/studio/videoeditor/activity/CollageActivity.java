package com.philip.studio.videoeditor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.adapter.ListImagesAdapter;
import com.philip.studio.videoeditor.model.ImageData;
import com.philip.studio.videoeditor.util.PuzzleUtils;
import com.xiaopo.flying.puzzle.PuzzleLayout;
import com.xiaopo.flying.puzzle.PuzzleView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CollageActivity extends AppCompatActivity {

    TextView txtDisplay, txtFrame, txtCollage;
    ImageView imgCheck;
    RecyclerView rVListImages;
    PuzzleView puzzleView;

    ArrayList<String> listImages;
    ArrayList<Bitmap> listBitmaps;
    List<PuzzleLayout> listPuzzleLayout;
    List<Integer> listImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collage);

        initView();

        puzzleView.setPiecePadding(3.0f);

        setUpRecyclerViewListImages();

        txtFrame.setOnClickListener(listener);
        txtCollage.setOnClickListener(listener);
        imgCheck.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text_view_frame:
                    puzzleView.setVisibility(View.VISIBLE);
                    txtFrame.setTextColor(Color.parseColor("#FB693B"));
                    txtCollage.setTextColor(Color.WHITE);
                    break;
                case R.id.text_view_collage:
                    puzzleView.setVisibility(View.GONE);
                    txtCollage.setTextColor(Color.parseColor("#FB693B"));
                    txtFrame.setTextColor(Color.WHITE);
                    break;
                case R.id.image_view_check:
                    saveImageFile(puzzleView);
                    break;
            }
        }
    };

    private void saveImageFile(View view) {
        Bitmap bitmap;

        if (listBitmaps.size() != 0) {
            bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);

            String nameFile = "IMG_" + System.currentTimeMillis() + ".png";
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Video Editor/" + nameFile);
            try {
                file.createNewFile();
//                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, nameFile, "Description");
                FileOutputStream outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpRecyclerViewListImages() {
        rVListImages.setHasFixedSize(true);
        rVListImages.setItemAnimator(new DefaultItemAnimator());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rVListImages.setLayoutManager(gridLayoutManager);

        ArrayList<ImageData> arrayList = getAllImageFromGallery();
        ListImagesAdapter adapter = new ListImagesAdapter(arrayList, this);
        rVListImages.setAdapter(adapter);

        adapter.setOnItemImageClickListener((position, imageView) -> {
            Bitmap bitmap = null;
            boolean isDisplay = arrayList.get(position).isClick();
            if (!isDisplay) {
                arrayList.get(position).setClick(true);
                imageView.setVisibility(View.VISIBLE);
                String imagePath = arrayList.get(position).getImageData();
                bitmap = convertStringToBitmap(imagePath);
                txtDisplay.setVisibility(View.GONE);
                listBitmaps.add(bitmap);
            } else {
                arrayList.get(position).setClick(false);
                imageView.setVisibility(View.GONE);
                listBitmaps.remove(bitmap);
            }
            adapter.notifyDataSetChanged();

            if (listBitmaps.size() != 0) {
                setUpPuzzleView(listBitmaps);
            } else {
                txtDisplay.setVisibility(View.VISIBLE);
            }
        });
    }

    private Bitmap convertStringToBitmap(String path) {
        Bitmap bitmap;
        File file = new File(path);
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            return null;
        }
        return bitmap;
    }

    private void setUpPuzzleView(ArrayList<Bitmap> bitmaps) {
        puzzleView.setTouchEnable(true);
        puzzleView.setNeedDrawLine(false);
        puzzleView.setNeedDrawOuterLine(false);
        puzzleView.setLineSize(4);
        puzzleView.setLineColor(Color.WHITE);
        puzzleView.setSelectedLineColor(Color.WHITE);
        puzzleView.setHandleBarColor(Color.WHITE);
        puzzleView.setAnimateDuration(300);
        puzzleView.setBackgroundColor(Color.WHITE);

        listPuzzleLayout = PuzzleUtils.getPuzzleLayouts(bitmaps.size());
        PuzzleLayout puzzleLayout = listPuzzleLayout.get(bitmaps.size());
        puzzleView.setPuzzleLayout(puzzleLayout);
        puzzleView.addPieces(bitmaps);
    }

    private ArrayList<ImageData> getAllImageFromGallery() {
        Uri uri;
        Cursor cursor;
        int columnIndexData;
        ArrayList<ImageData> listOfAllImages = new ArrayList<>();
        String pathOfImage;

        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns._ID};

        cursor = getContentResolver().query(uri, projection, null, null, null);
        columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            pathOfImage = cursor.getString(columnIndexData);
            listOfAllImages.add(new ImageData(pathOfImage, false));
        }
        return listOfAllImages;
    }

    public void onBack(View view) {
        finish();
    }

    private void initView() {
        txtDisplay = findViewById(R.id.text_view_display);
        txtCollage = findViewById(R.id.text_view_collage);
        txtFrame = findViewById(R.id.text_view_frame);
        imgCheck = findViewById(R.id.image_view_check);
        rVListImages = findViewById(R.id.recycler_view_list_images);
        puzzleView = findViewById(R.id.square_puzzle_view);

        listImages = new ArrayList<>();
        listBitmaps = new ArrayList<>();
        listPuzzleLayout = new ArrayList<>();
        listImage = new ArrayList<>();
    }
}