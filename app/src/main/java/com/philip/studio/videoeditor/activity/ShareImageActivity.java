package com.philip.studio.videoeditor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.philip.studio.videoeditor.MainActivity;
import com.philip.studio.videoeditor.R;

import java.io.File;

public class ShareImageActivity extends AppCompatActivity {

    ImageView imgHome, imgBack, imgImage, imgShare, imgMessenger, imgEmail,
            imgWhatsApp, imgInstagram, imgTwitter, imgFacebook;

    String image;
    Uri uri;
    private static final String FILE_PROVIDER_AUTHORITY = "com.philip.studio.videoeditor.fileprovider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_share_image);

        initView();

        Intent intent = getIntent();
        if (intent != null) {
            image = intent.getStringExtra("image");
            Glide.with(this).load(image).into(imgImage);
            uri = Uri.parse(image);
        }

        imgHome.setOnClickListener(listener);
        imgBack.setOnClickListener(listener);
        imgShare.setOnClickListener(listener);
        imgMessenger.setOnClickListener(listener);
        imgWhatsApp.setOnClickListener(listener);
        imgFacebook.setOnClickListener(listener);
        imgTwitter.setOnClickListener(listener);
        imgInstagram.setOnClickListener(listener);
    }

    private View.OnClickListener listener = v -> {
        switch (v.getId()) {
            case R.id.image_view_home:
                Intent intent = new Intent(ShareImageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.image_view_back:
                finish();
                break;
            case R.id.image_view_share:
                shareImage();
                break;
            case R.id.image_view_messenger:
                appInstalledOrNot("com.facebook.orca", "Messenger");
                break;
            case R.id.image_view_whatsapp:
                appInstalledOrNot("com.whatsapp", "WhatsApp");
                break;
            case R.id.image_view_facebook:
                appInstalledOrNot("com.facebook.katana", "Facebook");
                break;
            case R.id.image_view_twitter:
                appInstalledOrNot("com.twitter.android", "Twitter");
                break;
            case R.id.image_view_instagram:
                appInstalledOrNot("com.instagram.android", "Instagram");
                break;
        }
    };

    private void appInstalledOrNot(String uri, String name) {
        boolean isInstalled;
        PackageManager packageManager = getPackageManager();
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            isInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            isInstalled = false;
        }

        if (isInstalled){
            Toast.makeText(this, "Installed", Toast.LENGTH_SHORT).show();
        }
        else {
            showDialogNotification(uri, name);
        }
    }

    private void showDialogNotification(String uri, String name) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_dialog_notification);
        dialog.setCancelable(false);

        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);

        TextView txtDisplay, txtContent, txtCancel, txtInstall, txtShareOther;

        txtDisplay = dialog.findViewById(R.id.text1);
        txtContent = dialog.findViewById(R.id.text2);
        txtInstall = dialog.findViewById(R.id.text3);
        txtShareOther = dialog.findViewById(R.id.text4);
        txtCancel = dialog.findViewById(R.id.text5);

        txtDisplay.setText(name + " is not installed");
        txtContent.setText("Download and install " + name + " or share with other app");
        txtCancel.setOnClickListener(v -> dialog.cancel());
        txtInstall.setOnClickListener(v -> {
            String link = "https://play.google.com/store/apps/details?id="+uri+"&hl=vi&gl=US";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(link));
            startActivity(intent);
        });

        txtShareOther.setOnClickListener(v -> Toast.makeText(ShareImageActivity.this, "Shared with others", Toast.LENGTH_SHORT).show());
        dialog.show();
    }


    private void shareImage() {
        if (uri == null) {
            Toast.makeText(this, "Uri is null", Toast.LENGTH_SHORT).show();
        }

        Uri imageUri = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, new File(uri.getPath()));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(intent, "Share Image to"));
    }

    private void initView() {
        imgHome = findViewById(R.id.image_view_home);
        imgBack = findViewById(R.id.image_view_back);
        imgImage = findViewById(R.id.image_view_image);
        imgShare = findViewById(R.id.image_view_share);
        imgMessenger = findViewById(R.id.image_view_messenger);
        imgEmail = findViewById(R.id.image_view_email);
        imgWhatsApp = findViewById(R.id.image_view_whatsapp);
        imgFacebook = findViewById(R.id.image_view_facebook);
        imgInstagram = findViewById(R.id.image_view_instagram);
        imgTwitter = findViewById(R.id.image_view_twitter);
    }
}