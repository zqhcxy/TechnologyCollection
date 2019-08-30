package com.example.technologycollection;

import android.graphics.Color;
import android.os.Bundle;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseCommonActivity extends AppCompatActivity {

    private View contentView;

    private LinearLayout mToolbarParent;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(int layoutResID) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setExitTransition(new Explode());
        contentView = LayoutInflater.from(this).inflate(layoutResID, null);
        super.setContentView(contentView);
//        initToolbar();
    }

    public void initToolbar() {
        mToolbar = contentView.findViewById(R.id.toolbar_ly);
        mToolbarParent = contentView.findViewById(R.id.toolbar_parent_ly);
        if (mToolbar == null||mToolbarParent==null) return;
        mToolbar.setTitleTextColor(Color.WHITE);
//        mToolbarParent.setBackgroundColor(getResources().getColor(R.color.bground_cl));
//        mToolbar.setTitle("Metarial");
        setSupportActionBar(mToolbar);

    }

    public Toolbar getToolbar(){
        return mToolbar;
    }
}
