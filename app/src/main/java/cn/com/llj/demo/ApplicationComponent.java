package cn.com.llj.demo;

import dagger.Component;

/**
 * Created by liulj on 15/12/5.
 */
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    DemoApplication injectApplication(DemoApplication application);
}
