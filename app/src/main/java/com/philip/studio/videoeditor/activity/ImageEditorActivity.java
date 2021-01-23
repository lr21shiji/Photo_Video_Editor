package com.philip.studio.videoeditor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.fragment.ImageFragment;

public class ImageEditorActivity extends AppCompatActivity {

    FrameLayout frameLayout;

    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_editor);

        Intent intent = getIntent();
        if (intent != null) {
            image = intent.getStringExtra("image");
        }

        frameLayout = findViewById(R.id.frame_layout_container);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_container, new ImageFragment(image))
                .commit();
    }
}