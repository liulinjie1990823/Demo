package cn.com.llj.demo;

import android.app.Activity;

import dagger.Module;

/**
 * Created by liulj on 15/12/5.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    Activity provideActivity() {
        return mActivity;
    }
}
