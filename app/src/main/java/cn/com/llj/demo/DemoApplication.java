package cn.com.llj.demo;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.common.library.llj.base.BaseApplication;

/**
 * Created by liulj on 15/9/1.
 */
public class DemoApplication extends BaseApplication {
    private        DaggerApplicationComponent component;
    private static DemoApplication            demoApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        demoApplication = this;
        this.component = (DaggerApplicationComponent) DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        component.injectApplication(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 返回当前类的实例对象
     *
     * @return 返回当前类的实例对象
     */
    public static DemoApplication getInstance() {
        return demoApplication;
    }

    public DaggerApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void uploadThrowable(Throwable throwable) {

    }
}
