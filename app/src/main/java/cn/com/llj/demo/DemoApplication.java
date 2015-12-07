package cn.com.llj.demo;

import com.common.library.llj.base.BaseApplication;

/**
 * Created by liulj on 15/9/1.
 */
public class DemoApplication extends BaseApplication {
    private DaggerApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        this.component = (DaggerApplicationComponent) DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        component.injectApplication(this);
    }


    public DaggerApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void uploadThrowable(Throwable throwable) {

    }
}
