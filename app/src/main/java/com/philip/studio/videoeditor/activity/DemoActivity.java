package com.philip.studio.videoeditor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.adapter.GradientAdapter;

public class DemoActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnDraw;
    RecyclerView recyclerView;

    int[] backgroundColors = {R.drawable.gradient1, R.drawable.gradient2, R.drawable.gradient3, R.drawable.gradient4,
            R.drawable.gradient5, R.drawable.gradient6, R.drawable.gradient7,
            R.drawable.gradient8, R.drawable.gradient9, R.drawable.gradient10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_demo);
        initView();

        btnDraw.setOnClickListener(v -> drawBitmap(imageView));

        setUpRecyclerView();
    }

    private void drawBitmap(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        String nameFile = "IMG_" + System.currentTimeMillis() + ".png";
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, nameFile, "Description");
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setUpRecyclerView(){
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        GradientAdapter adapter = new GradientAdapter(backgroundColors);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemBackgroundGradientListener(res -> imageView.setBackground(getDrawable(res)));
    }

    private void initView(){
        imageView = findViewById(R.id.image_view);
        btnDraw = findViewById(R.id.button_draw);
        recyclerView = findViewById(R.id.recycler_view);
    }
}