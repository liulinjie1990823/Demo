package cn.com.llj.demo.activity.other;

import butterknife.OnClick;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/11/25.
 */
public class PermissionDemo extends DemoActivity {
    @Override
    public int getLayoutId() {
        return R.layout.permission_demo;
    }

    @OnClick(R.id.btn_permission)
    public void startActivityWithPermission() {

    }
}
