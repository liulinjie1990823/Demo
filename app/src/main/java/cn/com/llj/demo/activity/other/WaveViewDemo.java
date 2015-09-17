package cn.com.llj.demo.activity.other;

import android.widget.SeekBar;

import com.john.waveview.WaveView;

import butterknife.Bind;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/9/17.
 */
public class WaveViewDemo extends DemoActivity {
    @Bind(R.id.seek_bar)
     SeekBar seekBar;
    @Bind(R.id.wave_view)
     WaveView waveView;

    @Override
    public int getLayoutId() {
        return R.layout.wave_view_demo;
    }

    @Override
    public void addListeners() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveView.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
