package cn.com.llj.demo;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liulj on 15/12/5.
 */
@Module
public class ApplicationModule {
    Application mApplication;

    ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application providesApplication() {
        return mApplication;
    }
}
