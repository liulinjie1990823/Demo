package com.common.library.llj.imagehelp;

import android.app.Activity;
import android.content.Intent;

/**
 * 图片操控类
 * Created by liulj on 15/12/3.
 */
public class ImageSelectHandler {
    private ImageCaptureHelper mImageCaptureHelper;
    private ImagePickHelper mImagePickHelper;

    public ImageSelectHandler() {
        init();
    }


    private void init() {
        mImageCaptureHelper = new ImageCaptureHelper();
        mImagePickHelper = new ImagePickHelper();
    }

    public void pickImage(Activity activity) {
        mImagePickHelper.pickImage(activity);
    }

    public void captureImage(Activity activity) {
        mImageCaptureHelper.captureImage(activity);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent, ImageHelper.OnGetFileListener onGetFileListener) {
        mImageCaptureHelper.onActivityResult(requestCode, resultCode, intent, onGetFileListener);
        mImagePickHelper.onActivityResult(requestCode, resultCode, intent, onGetFileListener);
    }
}
