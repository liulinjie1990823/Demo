package cn.com.llj.demo;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liulj on 15/12/5.
 */
public class ToastHelper {
    ToastHelper() {
    }

    //@Inject
    //Utils utils;
    Toast toast = null;

    public void showToast(Context context, CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public void show(Context context) {
        // showToast(context, utils.getContent());
    }
}
