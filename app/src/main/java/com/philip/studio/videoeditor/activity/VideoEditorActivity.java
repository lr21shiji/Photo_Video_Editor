package com.philip.studio.videoeditor.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import com.philip.studio.videoeditor.R;

import java.util.ArrayList;


public class VideoEditorActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    String videoPath;
    FFmpeg ffmpeg;
    private static final int REQUEST_CODE = 123;

    VideoView videoView;
    TextView txtSaved;
    ImageView imgPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_editor);

        initView();

        Intent intent = getIntent();
        if (intent != null) {
            arrayList = intent.getStringArrayListExtra("list");
            videoPath = intent.getStringExtra("path");
        }

        imgPlay.setOnClickListener(v -> chooseVideoFromGallery());

    }

    private void chooseVideoFromGallery(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null){
            Uri uri = data.getData();
            videoView.setVideoURI(uri);
            videoView.start();
        }
    }



    private void playingVideo(String path){
        videoView.setVideoPath(path);
        videoView.start();
    }

    private void loadFFMpegBinary() {
        try {
            if (ffmpeg == null) {
                Log.d("error", "ffmpeg : era nulo");
                ffmpeg = FFmpeg.getInstance(this);
            }
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    showUnsupportedExceptionDialog();
                }

                @Override
                public void onSuccess() {
                    Log.d("success", "ffmpeg : correct Loaded");
                }
            });
        } catch (FFmpegNotSupportedException e) {
            showUnsupportedExceptionDialog();
        } catch (Exception e) {
            Log.d("error", "EXception no controlada : " + e);
        }
    }

    private void execFFmpegBinary(final String[] command) {
        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                }

                @Override
                public void onProgress(String message) {
                    super.onProgress(message);
                }

                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onSuccess(String message) {
                    super.onSuccess(message);
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
        }
    }

    private void showUnsupportedExceptionDialog() {
        new AlertDialog.Builder(VideoEditorActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Not Supported")
                .setMessage("Device Not Supported")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> VideoEditorActivity.this.finish())
                .create()
                .show();
    }


    public void onBack(View view) {
        finish();
    }

    private void initView() {
        videoView = findViewById(R.id.video_view);
        txtSaved = findViewById(R.id.text_view_saved);
        imgPlay = findViewById(R.id.image_view_play);

        arrayList = new ArrayList<>();
    }
}