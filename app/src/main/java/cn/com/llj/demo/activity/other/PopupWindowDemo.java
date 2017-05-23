package cn.com.llj.demo.activity.other;

import android.view.View;
import android.widget.Button;

import com.common.library.llj.base.BasePopupWindow;

import butterknife.Bind;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.DemoApplication;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/12/3.
 */
public class PopupWindowDemo extends DemoActivity {
    @Bind(R.id.btn_popup)
    Button mPopupBtn;

    @Override
    public int getLayoutId() {
        return R.layout.popup_window_demo;
    }

    public void popupwindow(View view) {
        DemoApplication.getInstance().getQupaiService().showRecordPage(mBaseFragmentActivity, 121, true);

//        BasePopupWindow basePopupWindow = new BasePopupWindow(getLayoutInflater().inflate(R.layout.popup_window_layout, null), -1, -2, this);
//        basePopupWindow.setElevation(10.0f);
//        basePopupWindow.showAsDropDown(mPopupBtn);

    }
}
