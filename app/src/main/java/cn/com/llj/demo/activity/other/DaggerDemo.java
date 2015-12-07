package cn.com.llj.demo.activity.other;

import cn.com.llj.demo.ActivityComponent;
import cn.com.llj.demo.ActivityModule;
import cn.com.llj.demo.DaggerActivityComponent;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.DemoApplication;

/**
 * Created by liulj on 15/12/5.
 */
public class DaggerDemo extends DemoActivity {
    private ActivityComponent component;

    //    @Inject
//    ToastHelper toastHelper;
    @Override
    public int getLayoutId() {
        this.component = DaggerActivityComponent.builder()
                .applicationComponent(((DemoApplication) getApplication()).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
        this.component.injectActivity(this);
        return 0;
    }
}
