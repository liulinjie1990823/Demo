package cn.com.llj.demo.activity.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * 非沉浸模式
 * Created by liulj on 15/9/9.
 */
public class MaterialDesignDetailDemo extends DemoActivity {
    public static final String EXTRA_NAME = "cheese_name";

    @Override
    public int getLayoutId() {
        return R.layout.material_design_detail_demo;
    }

    @Override
    public void findViews(Bundle savedInstanceState) {

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        ;
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        loadBackdrop();
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }

}
