package com.exam.ztes.ViewImage;

import android.content.Context;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.exam.ztes.R;

public class DetailImage extends AppCompatActivity {
    Context cntx;
    ConstraintLayout constraintLayout;
    TouchImageViewNew iv_viewimage;
    TextInputLayout til_;
    int til_height = 113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);

        cntx = DetailImage.this;

        Bundle bundle = getIntent().getExtras();
        PictureItem item = null;
        if (bundle != null) {
            item = bundle.getParcelable("PictureItem");
        }

        iv_viewimage = findViewById(R.id.iv_viewimage);
        if (item != null) {
            iv_viewimage.setImageURI(item.uri);
        }
        til_ = findViewById(R.id.til_);

        constraintLayout = findViewById(R.id.cl_full);
//        constraintLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (til_.getHeight() != 0) {
//                    til_height = til_.getHeight();
//                }
//                double height_iv = 0;
//                final Rect r = new Rect();
//                constraintLayout.getWindowVisibleDisplayFrame(r);
//                int screenHeightFull = constraintLayout.getRootView().getHeight();
//                int keypadHeight = screenHeightFull - r.bottom;
//                int screenHeight = screenHeightFull - keypadHeight;
//
//                if (keypadHeight > screenHeightFull * 0.15) {
//                    Toast.makeText(cntx, "Keyboard is showing", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(cntx, "keyboard closed", Toast.LENGTH_LONG).show();
//                }
//                height_iv = (50 * screenHeight) / 100;
//
//                iv_viewimage.getLayoutParams().height = (int)height_iv;
//                Log.e("asdgfbjhiytetwroioi", screenHeightFull + " | " + screenHeight + " | " + keypadHeight + " | " + height_iv);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}