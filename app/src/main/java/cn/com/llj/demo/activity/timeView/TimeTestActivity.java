package cn.com.llj.demo.activity.timeView;

import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Months;
import org.joda.time.Years;

import java.util.TimeZone;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 16/8/5.
 */

public class TimeTestActivity extends DemoActivity {
    @Bind(R.id.jdk_tv)
    TextView mJdkTv;
    @Bind(R.id.jdk_time_tv)
    TextView mJdkTimeTv;
    @Bind(R.id.joda_tv)
    TextView mJodaTv;
    @Bind(R.id.joda_time_tv)
    TextView mJodaTimeTv;

    private long mMill = 1430267000000l;

    @Override
    public int getLayoutId() {
        return R.layout.time_test_activity;
    }

    private String formatJdk(long mill) {
        return DurationFormatUtils.formatPeriod(System.currentTimeMillis(), mill, "y-M-d-H", false, TimeZone.getDefault());
    }

    private String formatJoda(long mill) {
        DateTime end = new DateTime(mill);
        DateTime start = new DateTime(System.currentTimeMillis());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Years.yearsBetween(end, start).getYears()).append("-")
                .append(Months.monthsBetween(end, start).getMonths()).append("-")
                .append(Days.daysBetween(end, start).getDays()).append("-")
                .append(Hours.hoursBetween(end, start).getHours());

        return stringBuilder.toString();
    }

    @OnClick({R.id.jdk_tv, R.id.joda_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jdk_tv:
                mJdkTimeTv.setText(formatJdk(mMill));
                break;
            case R.id.joda_tv:
                mJodaTimeTv.setText(formatJoda(mMill));
                break;
        }
    }
}
