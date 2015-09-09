package cn.com.llj.demo.activity.imageview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.common.library.llj.views.SelectableRoundedImageView;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 *
 */
public class SelectableRoundedImageViewDemo extends DemoActivity {

    @Override
    public int getLayoutId() {
        return R.layout.selectable_rounded_imageview_demo;
    }

    @Override
    public void findViews(Bundle savedInstanceState) {
        setContentView(R.layout.selectable_rounded_imageview_demo);
        // All properties can be set in xml.
        SelectableRoundedImageView iv0 = (SelectableRoundedImageView) findViewById(R.id.image0);

        // You can set image with resource id.
        SelectableRoundedImageView iv1 = (SelectableRoundedImageView) findViewById(R.id.image1);
        iv1.setScaleType(ScaleType.CENTER_CROP);
        iv1.setOval(true);
        iv1.setImageDrawable(new ColorDrawable(Color.BLUE));

        // Also, You can set image with Picasso.
        // This is a normal rectangle imageview.
        SelectableRoundedImageView iv2 = (SelectableRoundedImageView) findViewById(R.id.image2);
        iv2.setScaleType(ScaleType.CENTER);
        Glide.with(this).load(R.drawable.photo2).into(iv2);

        // Of course, you can set round radius in code.
        SelectableRoundedImageView iv3 = (SelectableRoundedImageView) findViewById(R.id.image3);
        iv3.setImageDrawable(getResources().getDrawable(R.drawable.photo3));
        iv3.setCornerRadiiDP(4, 4, 0, 0);
    }


}
