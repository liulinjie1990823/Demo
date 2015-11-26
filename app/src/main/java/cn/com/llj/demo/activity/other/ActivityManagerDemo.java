package cn.com.llj.demo.activity.other;

import com.common.library.llj.utils.ActivityManagerUtilLj;

import butterknife.OnClick;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/11/19.
 */
public class ActivityManagerDemo extends DemoActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_manager_demo;
    }

    @OnClick(R.id.button1)
    public void button1() {
        ActivityManagerUtilLj.isApplicationInForeground(this);
    }
}
