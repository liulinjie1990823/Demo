package cn.com.llj.demo.activity.other;

import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import com.common.library.llj.utils.MemoryUtil;

import butterknife.Bind;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/12/4.
 */
public class MemoryDemo extends DemoActivity {
    @Bind(R.id.textview1)
    TextView textView1;
    @Bind(R.id.textview2)
    TextView textView2;
    @Bind(R.id.textview3)
    TextView textView3;
    @Bind(R.id.textview4)
    TextView textView4;
    @Bind(R.id.textview5)
    TextView textView5;
    @Bind(R.id.textview6)
    TextView textView6;

    @Override
    public int getLayoutId() {
        return R.layout.memory_demo;
    }

    public void allInnerMemory(View view) {
        textView1.setText("总内存:" + Formatter.formatFileSize(this, MemoryUtil.getTotalInternalMemorySize()));
    }

    public void remainInnerMemory(View view) {
        textView2.setText("可用内存:" + Formatter.formatFileSize(this, MemoryUtil.getAvailableInternalMemorySize()));
    }

    public void allExMemory(View view) {
        textView3.setText("总外存:" + Formatter.formatFileSize(this, MemoryUtil.getTotalExternalMemorySize()));
    }

    public void remainExMemory(View view) {
        textView4.setText("可用外存:" + Formatter.formatFileSize(this, MemoryUtil.getAvailableExternalMemorySize()));
    }

    public void qweqe(View view) {
        textView5.setText("可用外存:" + MemoryUtil.getAvailMemory(this));
    }

    public void erqqeq(View view) {
        textView6.setText("可用外存:" + MemoryUtil.printMemoryInfo(this));
    }
}
