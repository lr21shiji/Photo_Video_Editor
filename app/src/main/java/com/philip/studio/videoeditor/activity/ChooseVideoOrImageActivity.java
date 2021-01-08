package com.philip.studio.videoeditor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.adapter.ResourcePagerAdapter;
import com.philip.studio.videoeditor.callback.OnSendListImageListener;

import java.util.ArrayList;

public class ChooseVideoOrImageActivity extends AppCompatActivity implements OnSendListImageListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fabCheck;

    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_video_or_image);

        initView();

        fabCheck.setVisibility(View.VISIBLE);
        fabCheck.setOnClickListener(v -> {
            Intent intent1 = new Intent(ChooseVideoOrImageActivity.this, VideoEditorActivity.class);
            intent1.putStringArrayListExtra("list", arrayList);
            startActivity(intent1);
        });

        ResourcePagerAdapter resourcePagerAdapter = new ResourcePagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(resourcePagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void onBack(View view) {
        finish();
    }

    private void initView() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        fabCheck = findViewById(R.id.floating_action_button_check);
        fabCheck.setVisibility(View.GONE);

        arrayList = new ArrayList<>();
    }

    @Override
    public void onSend(ArrayList<String> arrayList) {
//        if (arrayList.size() != 0) {
//            this.arrayList = arrayList;
//            fabCheck.setVisibility(View.VISIBLE);
//        } else {
//            fabCheck.setVisibility(View.GONE);
//        }
    }
}