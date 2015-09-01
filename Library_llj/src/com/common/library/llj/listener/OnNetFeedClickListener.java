package com.common.library.llj.listener;

import android.view.View;

import com.common.library.llj.utils.NetWorkUtilLj;
import com.common.library.llj.utils.ToastUtilLj;

/**
 * 有网络连接问题的反馈
 *
 * @author liulj
 */
public abstract class OnNetFeedClickListener extends OnMyClickListener {

    @Override
    public void onClick(View v) {
        // 检验自己的网络
        if (!NetWorkUtilLj.isNetworkAvailable(v.getContext())) {
            ToastUtilLj.show(v.getContext(), "您的网络不稳定，请检查您的网络！");
        } else {
            onCanClick(v);
        }
    }

    public abstract void onCanClick(View v);

}
