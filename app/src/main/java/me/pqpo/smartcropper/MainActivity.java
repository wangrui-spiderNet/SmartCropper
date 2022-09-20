package me.pqpo.smartcropper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;

import me.pqpo.smartcropper.widget.TagContainerLayout;

public class MainActivity extends AppCompatActivity {

    Button btnTake;
    Button btnSelect;
    ImageView ivShow;

    File photoFile;
    TagContainerLayout tagContainerLayout;
    private static final int HORIZONTAL_COUNT = 6;
    private static final int VERTICAL_COUNT = 4;
    private static final int TAG_COUNT = HORIZONTAL_COUNT * VERTICAL_COUNT;

    //    private Point[] points = new Point[TAG_COUNT];
    private int picWidth, picHeight, cellWidth, cellHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTake = (Button) findViewById(R.id.btn_take);
        btnSelect = (Button) findViewById(R.id.btn_select);
        ivShow = (ImageView) findViewById(R.id.iv_show);
        tagContainerLayout = (TagContainerLayout) findViewById(R.id.tagLayout);
        photoFile = new File(getExternalFilesDir("img"), "scan.jpg");

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(CropActivity.getJumpIntent(MainActivity.this, false, photoFile), 100);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(CropActivity.getJumpIntent(MainActivity.this, true, photoFile), 100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 100 && photoFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath());
            ivShow.setImageBitmap(bitmap);
            tagContainerLayout.removeAllViews();
            ivShow.post(new Runnable() {
                @Override
                public void run() {
                    picWidth = ivShow.getMeasuredWidth();
                    picHeight = ivShow.getMeasuredHeight();

                    cellWidth = picWidth / HORIZONTAL_COUNT;
                    cellHeight = picHeight / VERTICAL_COUNT;

                    for (int i = 0; i < TAG_COUNT; i++) {

                        int c = i / 6; //列
                        int r = i % 6; //行

                        int x = cellWidth * r + cellWidth / 2;
                        int y = cellHeight * c + cellHeight / 2;

                        tagContainerLayout.addItem(x, y);
                    }
                }
            });
        }
    }
}
