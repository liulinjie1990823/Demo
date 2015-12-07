package cn.com.llj.demo;

import dagger.Component;

/**
 * Created by liulj on 15/12/7.
 */
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    DemoActivity injectActivity(DemoActivity activity);

    ToastHelper getToastHelper();
}
