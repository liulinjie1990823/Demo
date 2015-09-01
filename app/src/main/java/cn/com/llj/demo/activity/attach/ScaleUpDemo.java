package cn.com.llj.demo.activity.attach;

import com.common.library.llj.utils.ActivityAnimUtilLj;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/9/1.
 */
public class ScaleUpDemo extends DemoActivity {
    @Override
    public int getLayoutId() {
        return R.layout.scale_up_demo;
    }

    @Override
    public void finish() {
        super.finish();
        ActivityAnimUtilLj.slideInLeftAndRightOut(this);
    }
}
