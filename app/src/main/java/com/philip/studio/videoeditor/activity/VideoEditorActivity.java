package com.philip.studio.videoeditor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.adapter.VideoFilterAdapter;
import com.philip.studio.videoeditor.filter.FilterType;

import java.util.ArrayList;
import java.util.List;

import VideoHandle.EpVideo;


public class VideoEditorActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    String videoPath;
    EpVideo epVideo;
    List<FilterType> filterTypes;

    VideoView videoView;
    TextView txtSaved;
    ImageView imgPlay;
    RecyclerView rVListFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_editor);

        initView();

        Intent intent = getIntent();
        if (intent != null) {
            videoPath = intent.getStringExtra("video");
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
        }

        filterTypes = FilterType.createFilterList();
        setUpRecyclerViewListFilter(filterTypes);

        imgPlay.setOnClickListener(v -> {
            if (videoView.isPlaying()){
                imgPlay.setImageResource(R.drawable.ic_baseline_play_arrow);
                videoView.pause();
            }
            else{
                imgPlay.setImageResource(R.drawable.ic_baseline_pause);
                videoView.start();
            }
        });
    }

    private void setUpRecyclerViewListFilter(List<FilterType> filterTypes){
        rVListFilter.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rVListFilter.setLayoutManager(layoutManager);

        VideoFilterAdapter adapter = new VideoFilterAdapter(filterTypes, this);
        rVListFilter.setAdapter(adapter);
    }

    public void onBack(View view) {
        finish();
    }

    private void initView() {
        videoView = findViewById(R.id.video_view);
        txtSaved = findViewById(R.id.text_view_saved);
        imgPlay = findViewById(R.id.image_view_play);
        rVListFilter = findViewById(R.id.recycler_view_list_filter);

        arrayList = new ArrayList<>();
        epVideo = new EpVideo(videoPath);
    }
}