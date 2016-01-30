package cn.com.llj.demo.activity.animation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;

import com.common.library.llj.utils.ActivityAnimUtilLj;

import butterknife.Bind;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;
import cn.com.llj.demo.activity.attach.ScaleUpDemo;

/**
 * 这是真的测试分支
 * Created by liulj on 15/9/1.
 */
public class ActivityAnimationDemo extends DemoActivity {
    @Bind(R.id.iv_image)
    ImageView image;

    @Override
    public int getLayoutId() {
        return R.layout.activity_animation_demo;
    }

    public void makeCustomAnimation(View view) {
        startActivity(new Intent(this, ScaleUpDemo.class));
        ActivityAnimUtilLj.slideInLeftAndRightOut(this);
    }

    public void makeThumbnailScaleUpAnimation(View view) {
        // bitmap用作新界面拉伸效果图，新activity的初始大小就是这个bitmap,这样图片大小就确定了
        // startX是新的activity的x初始位置，相对于view的左上角
        // startY是新的activity的y初始位置，相对于view的左上角
        image.setDrawingCacheEnabled(true);
        Bitmap bitmap = image.getDrawingCache();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(image, bitmap, 720, 100);
        Intent intent = new Intent(this, ScaleUpDemo.class);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    /**
     * @param view
     */
    public void scaleUpAnimation(View view) {
        // 通过view的左上角加上startX和startY算出起始点，向右startWidth距离，向下startHeight距离围成一个矩形，新的界面将从这个矩形开始向四周放大
        // 一般这样设置，有查看大图的过度效果
        // ActivityOptionsCompat options2 =
        // ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0,
        // view.getMeasuredWidth(), view.getMeasuredHeight());

        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, 360, 100, 0, 300);
        Intent intent = new Intent(this, ScaleUpDemo.class);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
