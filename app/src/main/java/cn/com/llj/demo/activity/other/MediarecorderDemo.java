package cn.com.llj.demo.activity.other;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.TextureView;
import android.widget.Button;

import com.common.library.llj.utils.CameraUtil;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/11/26.
 */
public class MediarecorderDemo extends DemoActivity {
    @Bind(R.id.surface_view)
    TextureView mTextureView;
    @Bind(R.id.button_capture)
    Button mCaptureBtn;
    private static final String TAG = "MediarecorderDemo";
    private boolean isRecording = false;//是否正在录制
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;

    @Override
    public int getLayoutId() {
        return R.layout.mediarecorder_demo;
    }

    @OnClick(R.id.button_capture)
    public void captureBtn() {
        if (isRecording) {
            //结束播放视频
            // BEGIN_INCLUDE(stop_release_media_recorder)

            // stop recording and release camera
            mMediaRecorder.stop();  // stop the recording
            releaseMediaRecorder(); // release the MediaRecorder object
            // inform the user that recording has stopped
            setCaptureButtonText("Capture");
            isRecording = false;
            releaseCamera();
            // END_INCLUDE(stop_release_media_recorder)

        } else {
            //开始播放视频
            // BEGIN_INCLUDE(prepare_start_media_recorder)
            new MediaPrepareTask().execute(null, null, null);
            // END_INCLUDE(prepare_start_media_recorder)

        }
    }

    class MediaPrepareTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            // initialize video camera
            if (prepareVideoRecorder()) {
                // Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                mMediaRecorder.start();

                isRecording = true;
            } else {
                // prepare didn't work, release the camera
                releaseMediaRecorder();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                finish();
            }
            // inform the user that recording has started
            setCaptureButtonText("Stop");

        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private boolean prepareVideoRecorder() {
        // BEGIN_INCLUDE (configure_preview)
        //获得后置摄像头实例
        mCamera = CameraUtil.getDefaultCameraInstance();

        Camera.Parameters parameters = mCamera.getParameters();

        //我们需要确保我们的预览和录制视频大小相机支持。查询相机找到所有的大小和选择给我们的预览表面的尺寸的最佳尺寸。
        List<Camera.Size> mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();
        Camera.Size optimalSize = CameraUtil.getOptimalPreviewSize(mSupportedPreviewSizes, mTextureView.getWidth(), mTextureView.getHeight());

        //获得高质量的录像协议
        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        //设置视频像素
        profile.videoFrameWidth = optimalSize.width;
        profile.videoFrameHeight = optimalSize.height;

        //设置预览图片的尺寸
        parameters.setPreviewSize(profile.videoFrameWidth, profile.videoFrameHeight);
        mCamera.setParameters(parameters);
        try {
            // Requires API level 11+, For backward compatibility use {@link setPreviewDisplay}
            // with {@link SurfaceView}
            //设置播放显示
            mCamera.setPreviewTexture(mTextureView.getSurfaceTexture());
        } catch (IOException e) {
            Log.e(TAG, "Surface texture is unavailable or unsuitable" + e.getMessage());
            return false;
        }
        // END_INCLUDE (configure_preview)


        // BEGIN_INCLUDE (configure_media_recorder)
        mMediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(profile);

        // Step 4: Set output file
        mMediaRecorder.setOutputFile(CameraUtil.getOutputMediaFile(CameraUtil.MEDIA_TYPE_VIDEO).toString());
        // END_INCLUDE (configure_media_recorder)

        // Step 5: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    /**
     * 释放音视频记录器
     */
    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            // clear recorder configuration
            mMediaRecorder.reset();
            // release the recorder object
            mMediaRecorder.release();
            mMediaRecorder = null;
            // Lock camera for later use i.e taking it back from MediaRecorder.
            // MediaRecorder doesn't need it anymore and we will release it if the activity pauses.
            mCamera.lock();
        }
    }

    /**
     * 释放摄像头实例
     */
    private void releaseCamera() {
        if (mCamera != null) {
            // release the camera for other applications
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // if we are using MediaRecorder, release it first
        releaseMediaRecorder();
        // release the camera immediately on pause event
        releaseCamera();
    }

    private void setCaptureButtonText(String title) {
        mCaptureBtn.setText(title);
    }
}
